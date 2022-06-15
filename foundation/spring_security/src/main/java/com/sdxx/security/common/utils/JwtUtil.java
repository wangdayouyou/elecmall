package com.sdxx.security.common.utils;

import com.sdxx.security.common.bo.UserInfoInTokenBO;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

/**
 * @author wd
 *
 * @description jwt工具类
 */
public class JwtUtil {

    public static RsaJsonWebKey rsaJsonWebKey = RsaJsonWebKeyUtil.getInstance();

    /**
     * 生成jwt
     *
     * @param user
     * @param expiration
     * @return
     * @throws JoseException
     */
    public static String createSignedJwt(UserInfoInTokenBO user,long expiration) throws JoseException {
        JwtClaims claims = new JwtClaims();
        claims.setClaim("nickName",user.getNickName());
        //以用id作为issuer
        claims.setIssuer(user.getUserId());
        claims.setAudience(System.getProperty("os.name"));
        //expiration分钟后失效
        claims.setExpirationTimeMinutesInTheFuture(expiration);
        claims.setGeneratedJwtId();
        //token当即发布
        claims.setIssuedAtToNow();
        //jwt立即生效
        claims.setNotBeforeMinutesInThePast(0);
        //设置主题
        claims.setSubject("login");

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        String jwt = jws.getCompactSerialization();
        return jwt;
    }

    /**
     * 验证jwt
     *
     * @param token
     * @param userId
     * @return
     */
    public static boolean checkJwt(String token,String userId){
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setMaxFutureValidityInMinutes(300) // but the  expiration time can't be too crazy
                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                .setRequireSubject() // the JWT must have a subject claim
                .setExpectedIssuer(userId) // whom the JWT needs to have been issued by
                .setExpectedAudience(System.getProperty("os.name")) // to whom the JWT is intended for
                .setVerificationKey(rsaJsonWebKey.getPublicKey()) // verify the signature with the public key
                .build(); // create the JwtConsumer instance
        try{
            JwtClaims claims = jwtConsumer.processToClaims(token);
            return true;
        } catch (InvalidJwtException e) {
            return false;
        }
    }
}
