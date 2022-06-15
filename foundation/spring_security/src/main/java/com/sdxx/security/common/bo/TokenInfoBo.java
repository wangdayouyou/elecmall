package com.sdxx.security.common.bo;

import lombok.Data;

/**
 * @author wd
 *
 * @description token信息，保存在Redis中
 */
@Data
public class TokenInfoBo {
    /**
     * 保存在token信息里面的用户信息
     */
    private UserInfoInTokenBO userInfoInToken;

    private String accessToken;

    private String refreshToken;

    private Long expiresIn;
}
