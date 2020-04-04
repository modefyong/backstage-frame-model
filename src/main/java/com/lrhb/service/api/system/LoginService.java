package com.lrhb.service.api.system;

import com.lrhb.dataaccess.model.system.User;
import com.lrhb.service.beans.system.LoginResponse;

/**
 * 登录业务服务
 */
public interface LoginService {

    /**
     * 登录
     * @param loginName 登录名
     * @param pwd 密码
     * */
    LoginResponse login(String loginName, String pwd, String code);

    /**
     * 登出
     * */
    String logout();
    
    LoginResponse loginReact(String username, String password);
}
