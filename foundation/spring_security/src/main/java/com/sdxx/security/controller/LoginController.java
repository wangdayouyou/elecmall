package com.sdxx.security.controller;

import com.sdxx.security.common.manager.PasswordCheckManager;
import com.sdxx.security.common.manager.PasswordManager;
import com.sdxx.security.common.manager.TokenManager;
import com.sdxx.security.dao.UserMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

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
}
