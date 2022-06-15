package com.sdxx.security.common.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author wd
 *
 * @description 刷新token
 */
public class RefreshTokenDTO {
    @NotBlank(message = "refreshToken不能为空")
    @ApiModelProperty(value = "refreshToken", required = true)
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "RefreshTokenDTO{" + "refreshToken='" + refreshToken + '\'' + '}';
    }
}
