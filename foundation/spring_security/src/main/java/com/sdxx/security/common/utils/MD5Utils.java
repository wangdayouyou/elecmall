package com.sdxx.security.common.utils;

import cn.hutool.crypto.SecureUtil;

import java.util.UUID;

/**
 * @author wd
 *
 * @description md5加密工具类
 */
public class MD5Utils {
    /**
     * md5加密 via hutool
     *
     * @param src
     * @return
     */
    public static String md5(String src){
        return SecureUtil.md5(src);
    }

    /**
     * 获取后端加密盐值
     *
     * @return
     */
    private static String createSalt(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String Encrypt(String password){
        String salt = createSalt();
        String passwordWithSalt = salt.charAt(7) + "" + salt.charAt(9) + password + salt.charAt(1) + "" + salt.charAt(5);
        passwordWithSalt = md5(passwordWithSalt);
        return passwordWithSalt;
    }
}
