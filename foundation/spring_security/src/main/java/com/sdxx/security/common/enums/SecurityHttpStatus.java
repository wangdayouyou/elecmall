package com.sdxx.security.common.enums;

public enum SecurityHttpStatus {
    /**
     * 客户端看到401状态码时，应该重新登陆
     */
    UNAUTHORIZED(401, "未授权"),

    ACCOUNT_NOT_REGISTER(476, "account not register"),
    ;


    private final int value;

    private final String msg;


    SecurityHttpStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }


    /**
     * Return the integer value of this status code.
     */
    public int value() {
        return this.value;
    }

    /**
     * Return the msg of this status code.
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return this.value + " " + name();
    }


    public static SecurityHttpStatus valueOf(int statusCode) {
        SecurityHttpStatus status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("没有找到该Http状态码包含状态 [" + statusCode + "]");
        }
        return status;
    }

    public static SecurityHttpStatus resolve(int statusCode) {
        for (SecurityHttpStatus status : values()) {
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }
}
