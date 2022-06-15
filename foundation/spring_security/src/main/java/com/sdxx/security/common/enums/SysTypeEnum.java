package com.sdxx.security.common.enums;

/**
 * @author wd
 *
 * @description 用户类型
 */
public enum SysTypeEnum {
    /**
     * 普通用户系统
     */
    ORDINARY(0),

    /**
     * 外部用户
     */
    EXTERNEL(1),
    /**
     * 后台
     */
    ADMIN(2),
    ;

    private final Integer value;

    public Integer value() {
        return value;
    }

    SysTypeEnum(Integer value) {
        this.value = value;
    }
}
