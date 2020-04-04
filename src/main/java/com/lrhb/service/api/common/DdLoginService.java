package com.lrhb.service.api.common;


import com.dingtalk.api.response.OapiUserGetResponse;
import com.lrhb.mgrframework.beans.response.AppResult;

/**
 * 登录业务服务
 */
public interface DdLoginService {

    /**
     * 登录
     * @param loginName 登录名
     * @param pwd 密码
     * */
	OapiUserGetResponse login(String code);

    /**
     * 登出
     * */
    String logout();
}
