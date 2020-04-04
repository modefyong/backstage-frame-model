package com.lrhb.service.beans.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 登录成功返回bean
 */
@Data
public class LoginResponse implements Serializable{

    /**
     * 用户id
     * */
    private String userId;
    /**
     * 登录名
     * */
    private String loginName;

    /**
     * web上下文
     * */
    private String webContext;
    
    /**
     * 用户角色
     */
    List<String> roles = new ArrayList<String>();
    
//    测试react所需字段
    private String username;
    
    private String password;
    
    private String realName;
    
    private String message;
}
