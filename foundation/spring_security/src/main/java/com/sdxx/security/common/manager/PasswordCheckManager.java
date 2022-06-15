package com.sdxx.security.common.manager;

import cn.hutool.core.util.StrUtil;
import com.sdxx.security.common.enums.SysTypeEnum;
import com.sdxx.security.common.utils.IPHelper;
import com.sdxx.security.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wd
 *
 * @description 密码校验管理器
 */
@Component
public class PasswordCheckManager {
    @Autowired
    private PasswordManager passwordManager;
    /**
     * 半小时内最多错误10次
     */
    private static final int MOST_CHECK_TIMES_EVERY_THIRTY_MINUTES = 10;

    /**
     * 检查用户
     */
    private static final String CHECK_INPUT_ERROR_PREFIX = "checkUserInputErrorPassword_";

    public void checkPassword(SysTypeEnum sysTypeEnum, String userNameOrMobile, String rawPassword, String encodedPassword) {
        String checkPrefix = sysTypeEnum.value() + CHECK_INPUT_ERROR_PREFIX + IPHelper.getIpAddr();

        int count = 0;
        if(RedisUtil.hasKey(checkPrefix + userNameOrMobile)){
            count = RedisUtil.get(checkPrefix + userNameOrMobile);
        }
        if(count > MOST_CHECK_TIMES_EVERY_THIRTY_MINUTES){
            throw new RuntimeException("半小时内密码输入错误十次，已限制登录30分钟");
        }
        // 半小时后失效
        RedisUtil.set(checkPrefix + userNameOrMobile,count,1800);
        // 密码不正确
        if (StrUtil.isBlank(encodedPassword) || !passwordManager.matches(rawPassword,encodedPassword)){
            count++;
            // 半小时后失效
            RedisUtil.set(checkPrefix + userNameOrMobile,count,1800);
            throw new RuntimeException("账号或密码不正确");
        }
    }
}
