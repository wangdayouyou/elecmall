package com.sdxx.commonutils.exception;

public enum BizCodeEnum {
    SMS_CODE_EXCEPTION(10002,"短信验证码获取频率太高，稍后再试"),
    UNKNOWN_EXCEPTION(10000,"系统未知异常"),
    VALID_EXCEPTION(10001,"参数格式校验失败"),
    SMS_CODE_ERROR(10003,"验证码错误"),
    SMS_CODE_GET_ERROR(10004,"验证码错误,请获取验证码并输入"),
    PHONE_EXIST_EXCEPTION(15001,"注册失败，手机号已注册"),
    PHONE_CHANGE_EXIST_EXCEPTION(15008,"更换失败，该手机号已注册"),
    PHONE_EXIST_REGISTER(15004,"登录失败，手机号未注册"),
    EMAIL_EXIST_EXCEPTION(15002,"绑定失败，邮箱已绑定"),
    LOGIN_PHONE_PASSWORD_EXCEPTION(15003,"账号或密码错误"),
    LOGIN_EXPIRED_EXCEPTION(28004,"登陆过期"),
    ACCOUNT_ALREADY_DISABLE (28005,"该账户已经禁用");
    private int code;
    private String msg;
    BizCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
