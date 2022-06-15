package com.sdxx.security.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sdxx.security.common.bo.UserInfoInTokenBO;
import com.sdxx.security.common.enums.SysTypeEnum;
import com.sdxx.security.common.manager.PasswordManager;
import com.sdxx.security.common.manager.TokenManager;
import com.sdxx.security.common.vo.TokenInfoVO;
import com.sdxx.security.entity.ElecUser;
import com.sdxx.security.param.UserRegisterParam;
import com.sdxx.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.jose4j.lang.JoseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/user")
@Api(tags = "用户注册相关接口")
@AllArgsConstructor
public class RegisterController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final TokenManager tokenManager;

    private final PasswordManager passwordManager;

    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "用户注册或绑定手机号接口")
    public ResponseEntity<TokenInfoVO> register(@Valid @RequestBody UserRegisterParam userRegisterParam) throws JoseException {
        // 正在进行申请注册
        if (userService.count(new LambdaQueryWrapper<ElecUser>().eq(ElecUser::getUserName,
                userRegisterParam.getUserName())) > 0) {
            // 该用户名已注册，无法重新注册
            throw new SecurityException("该用户名已注册，无法重新注册");
        }
        Date now = new Date();
        ElecUser user = new ElecUser();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setEnabled(true);
        user.setEmail(userRegisterParam.getUserMail());
        String encryptPassword = passwordManager.encryptPassword(userRegisterParam.getPassWord());
        user.setPassword(passwordEncoder.encode(encryptPassword));
        userService.save(user);
        // 2. 登录
        UserInfoInTokenBO userInfoInTokenBO = new UserInfoInTokenBO();
        userInfoInTokenBO.setUserId(user.getId());
        userInfoInTokenBO.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInTokenBO.setEnabled(true);
        return ResponseEntity.ok(tokenManager.storeAndGetVo(userInfoInTokenBO));
    }


    @PutMapping("/updatePwd")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public ResponseEntity<Void> updatePwd(@Valid @RequestBody UserRegisterParam userPwdUpdateParam) {
        ElecUser user = userService.getOne(new LambdaQueryWrapper<ElecUser>().eq(ElecUser::getUserName,
                userPwdUpdateParam.getUserName()));
        if (user == null) {
            // 无法获取用户信息
            throw new SecurityException("无法获取用户信息");
        }
        String encryptPassword = passwordManager.encryptPassword(userPwdUpdateParam.getPassWord());
        if (StrUtil.isBlank(encryptPassword)) {
            // 新密码不能为空
            throw new SecurityException("新密码不能为空");
        }
        if (StrUtil.equals(encryptPassword, user.getPassword())) {
            // 新密码不能与原密码相同
            throw new SecurityException("新密码不能与原密码相同");
        }
        user.setUpdateTime(new Date());
        user.setPassword(encryptPassword);
        userService.updateById(user);
        return ResponseEntity.ok().build();
    }
}
