package com.sdxx.security.common.manager;

import cn.hutool.core.util.StrUtil;
import com.sdxx.security.common.bo.TokenInfoBo;
import com.sdxx.security.common.bo.UserInfoInTokenBO;
import com.sdxx.security.common.enums.SecurityHttpStatus;
import com.sdxx.security.common.exception.SecurityException;
import com.sdxx.security.common.utils.JwtUtil;
import com.sdxx.security.common.vo.TokenInfoVO;
import org.jose4j.lang.JoseException;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class TokenManager {

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisSerializer<Object> redisSerializer;

    private final StringRedisTemplate stringRedisTemplate;

    private static long accessTokenExpireTime = 60;

    private static long refreshTokenExpireTime = 60 * 3;

    public TokenManager(RedisTemplate<String, Object> redisTemplate,
                      StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisSerializer = new GenericJackson2JsonRedisSerializer();
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 保存token至redis中
     *
     * @param user
     * @return
     * @throws JoseException
     */
    public TokenInfoBo storeAccessToken(UserInfoInTokenBO user) throws JoseException {
        TokenInfoBo tokenInfoBo = new TokenInfoBo();

        tokenInfoBo.setUserInfoInToken(user);

        String accessToken = createAccessToken(user);
        String refreshToken = createRefreshToken(user);
        tokenInfoBo.setAccessToken(accessToken);
        tokenInfoBo.setRefreshToken(refreshToken);
        tokenInfoBo.setExpiresIn(accessTokenExpireTime);

        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {

            byte[] refreshKey = refreshToken.getBytes(StandardCharsets.UTF_8);
            byte[] accessKey = accessToken.getBytes(StandardCharsets.UTF_8);

            // 通过refresh_token获取用户的access_token从而刷新token
            connection.setEx(refreshKey,refreshTokenExpireTime,accessKey);

            // 通过access_token保存用户相关信息
            connection.setEx(accessKey, accessTokenExpireTime, Objects.requireNonNull(redisSerializer.serialize(user)));

            return null;
        });
        return tokenInfoBo;
    }

    /**
     * 根据accessToken获取用户信息
     *
     * @param accessToken
     * @return
     */
    public UserInfoInTokenBO getUserInfoByAccessToken(String accessToken) {
        if (StrUtil.isBlank(accessToken)) {
            throw new SecurityException(SecurityHttpStatus.UNAUTHORIZED,"accessToken is blank");
        }
        UserInfoInTokenBO userInfoInTokenBO = (UserInfoInTokenBO) redisTemplate.opsForValue()
                .get(accessToken);

        if (userInfoInTokenBO == null) {
            throw new SecurityException(SecurityHttpStatus.UNAUTHORIZED,"accessToken 已过期");
        }
        return userInfoInTokenBO;
    }

    /**
     * 刷新token
     *
     * @param refreshToken
     * @return
     * @throws JoseException
     */
    public TokenInfoBo refreshToken(String refreshToken) throws JoseException {
        if (StrUtil.isBlank(refreshToken)) {
            throw new SecurityException(SecurityHttpStatus.UNAUTHORIZED,"refreshToken is blank");
        }
        String accessToken = stringRedisTemplate.opsForValue().get(refreshToken);

        if (StrUtil.isBlank(accessToken)) {
            throw new SecurityException(SecurityHttpStatus.UNAUTHORIZED,"refreshToken 已过期");
        }

        UserInfoInTokenBO userInfoInTokenBO = getUserInfoByAccessToken(accessToken);

        // 删除旧的refresh_token
        stringRedisTemplate.delete(refreshToken);
        // 删除旧的access_token
        stringRedisTemplate.delete(accessToken);
        // 保存一份新的token
        return storeAccessToken(userInfoInTokenBO);
    }

    /**
     * 保存并返回token
     *
     * @param userInfoInToken
     * @return
     * @throws JoseException
     */
    public TokenInfoVO storeAndGetVo(UserInfoInTokenBO userInfoInToken) throws JoseException {
        TokenInfoBo tokenInfoBO = storeAccessToken(userInfoInToken);

        TokenInfoVO tokenInfoVO = new TokenInfoVO();
        tokenInfoVO.setAccessToken(tokenInfoBO.getAccessToken());
        tokenInfoVO.setRefreshToken(tokenInfoBO.getRefreshToken());
        tokenInfoVO.setExpiresIn(tokenInfoBO.getExpiresIn());
        return tokenInfoVO;
    }

    public String createAccessToken(UserInfoInTokenBO user) throws JoseException {
        return JwtUtil.createSignedJwt(user,accessTokenExpireTime);
    }

    public String createRefreshToken(UserInfoInTokenBO user) throws JoseException {
        return JwtUtil.createSignedJwt(user,refreshTokenExpireTime);
    }
}
