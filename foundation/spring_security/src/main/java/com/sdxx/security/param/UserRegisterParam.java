package com.sdxx.security.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "设置用户信息")
public class UserRegisterParam {
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "密码")
    private String passWord;

    @ApiModelProperty(value = "邮箱")
    private String userMail;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "头像")
    private String img;
}
