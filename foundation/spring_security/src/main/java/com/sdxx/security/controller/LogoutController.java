package com.sdxx.security.controller;

import cn.hutool.core.util.StrUtil;
import com.sdxx.security.common.manager.TokenManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

public class LogoutController {
    @Autowired
    private TokenManager tokenManager;

    @PostMapping("/logOut")
    @ApiOperation(value = "退出登陆", notes = "点击退出登陆，清除token，清除菜单缓存")
    public ResponseEntity<Void> logOut(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if (StrUtil.isBlank(accessToken)) {
            return ResponseEntity.ok().build();
        }
        // 删除该用户在该系统当前的token
        tokenManager.deleteCurrentToken(accessToken);
        return ResponseEntity.ok().build();
    }
}
