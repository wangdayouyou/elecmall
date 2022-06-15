package com.sdxx.security.common.utils;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;

/**
 * @author wd
 *
 * @description JWK生成工具类
 */
public class RsaJsonWebKeyUtil {
    private static RsaJsonWebKey rsaJsonWebKey = null;

    private RsaJsonWebKeyUtil(){}

    /**
     * 生成RSA秘钥对，用于签署和验证JWT，包装在JWK中
     * @return
     */
    public static RsaJsonWebKey getInstance(){
        if(rsaJsonWebKey == null){
            try{
                rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
                rsaJsonWebKey.setKeyId("JWT");
            }catch (JoseException e){

            }
        }
        return rsaJsonWebKey;
    }
}
