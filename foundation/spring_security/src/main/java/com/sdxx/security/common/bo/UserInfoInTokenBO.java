package com.sdxx.security.common.bo;

import lombok.Data;

import java.util.Set;

/**
 * @author wd
 *
 * @description 保存在token中的用户信息
 */
@Data
public class UserInfoInTokenBO {
    /**
     * 用户在自己系统的用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String nickName;

    /**
     * 系统类型
     *
     * 0.普通用户 1.三方用户 2.系统管理
     */
    private Integer sysType;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 状态 1 正常 0 无效
     */
    private Boolean enabled;
}
