package com.sdxx.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sdxx.security.common.bo.UserInfoInTokenBO;
import com.sdxx.security.common.dto.AuthenticationDTO;
import com.sdxx.security.common.enums.SysTypeEnum;
import com.sdxx.security.common.manager.PasswordCheckManager;
import com.sdxx.security.common.manager.PasswordManager;
import com.sdxx.security.common.manager.TokenManager;
import com.sdxx.security.common.utils.PrincipalUtil;
import com.sdxx.security.common.vo.TokenInfoVO;
import com.sdxx.security.dao.UserMapper;
import com.sdxx.security.entity.ElecUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "登录")
public class LoginController {
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordCheckManager passwordCheckManager;

    @Autowired
    private PasswordManager passwordManager;

    @PostMapping("/login")
    @ApiOperation(value = "账号密码(用于前端登录)", notes = "通过账号/手机号/用户名密码登录")
    public ResponseEntity<TokenInfoVO> login(
            @Valid @RequestBody AuthenticationDTO authenticationDTO) throws JoseException {
        String mobileOrUserName = authenticationDTO.getUserName();
        ElecUser user = getUser(mobileOrUserName);

        String encryptPassword = passwordManager.encryptPassword(authenticationDTO.getPassWord());
        // 半小时内密码输入错误十次，已限制登录30分钟
        passwordCheckManager.checkPassword(SysTypeEnum.ORDINARY, authenticationDTO.getUserName(),
                authenticationDTO.getPassWord(),
                user.getPassword());

        UserInfoInTokenBO userInfoInToken = new UserInfoInTokenBO();
        userInfoInToken.setUserId(user.getId());
        userInfoInToken.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInToken.setEnabled(user.getEnabled());
        // 存储token返回vo
        TokenInfoVO tokenInfoVO = tokenManager.storeAndGetVo(userInfoInToken);
        return ResponseEntity.ok(tokenInfoVO);
    }

    private ElecUser getUser(String mobileOrUserName) {
        ElecUser user = null;
        // 手机验证码登陆，或传过来的账号很像手机号
        if (PrincipalUtil.isMobile(mobileOrUserName)) {
            user = userMapper.selectOne(new LambdaQueryWrapper<ElecUser>().eq(ElecUser::getPhoneNumber, mobileOrUserName));
        }
        // 如果不是手机验证码登陆， 找不到手机号就找用户名
        if  (user == null) {
            user = userMapper.selectOne(new LambdaQueryWrapper<ElecUser>().eq(ElecUser::getUserName, mobileOrUserName));
        }
        if (user == null) {
            throw new SecurityException("账号或密码不正确");
        }
        return user;
    }
}
