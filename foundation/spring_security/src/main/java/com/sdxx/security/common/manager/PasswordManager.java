package com.sdxx.security.common.manager;

import cn.hutool.core.util.StrUtil;
import com.sdxx.security.common.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wd
 *
 * @description 密码管理器
 */
@Component
public class PasswordManager {
    Logger logger = LoggerFactory.getLogger(PasswordManager.class);

    /**
     * MD5 密码加密
     * @param password
     * @return
     */
    public String encryptPassword(String password){
        String encryptPassword;
        try{
            encryptPassword = MD5Utils.Encrypt(password);
        }catch (Exception e){
            logger.error("Exception: ",e);
            throw new RuntimeException();
        }
        return encryptPassword;
    }

    public boolean matches(String rawPassword,String encodePassword){
        return StrUtil.equals(encryptPassword(rawPassword),encodePassword);
    }
}
