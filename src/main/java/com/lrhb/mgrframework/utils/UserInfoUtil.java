package com.lrhb.mgrframework.utils;

import org.slf4j.MDC;
import com.lrhb.mgrframework.beans.response.AbstractResult;
import com.lrhb.mgrframework.exceptions.UnloginException;

/**
 * 用户信息工具类，保存用户信息到threadlocal，方便打印日志
 */
public class UserInfoUtil {

    private final static ThreadLocal<Object> tlUser = new ThreadLocal<Object>();

    /**user key*/
    public static final String KEY_USER = "sysUser";//同放入session的用户信息Key

    public static void setUser(Object user) {
        tlUser.set(user);
        // 把需要打印日志的用户信息放到slf4j MDC
        MDC.put(KEY_USER, user.toString());
    }

    /**
     * 如果没有登录会抛出异常
     * @return
     */
    public static Object getUser() {
        Object user = tlUser.get();

        if (user == null) {
            throw new UnloginException(AbstractResult.SYSTEM_FAIL,"user unlogin");
        }

        return user;
    }

    /**
     * 如果没有登录，返回null
     * @return
     */
    public static Object getUserIfLogin() {
        return tlUser.get();
    }

    /**
     * 清空用户信息
     * */
    public static void clearAllUserInfo() {
        tlUser.remove();
        MDC.remove(KEY_USER);
    }
}
