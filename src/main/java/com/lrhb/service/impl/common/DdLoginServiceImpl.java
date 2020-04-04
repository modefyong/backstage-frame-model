package com.lrhb.service.impl.common;

import com.lrhb.mgrframework.beans.response.AppResult;
import com.lrhb.service.api.common.DdLoginService;		
import com.lrhb.utils.list.JedisClientPool;
import com.taobao.api.ApiException;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 登录服务
 */
@Slf4j
@Service
public class DdLoginServiceImpl implements DdLoginService{

    @Autowired
    JedisClientPool redis;
    
    
	private String tokenKey = "ddTokenKey";
	
	private String userUrl="https://oapi.dingtalk.com/user/getuserinfo";
    /**
     * 登录
     * @param loginName 登录名
     * @param pwd 密码
     * @throws ApiException 
     * */
    public OapiUserGetResponse login(String code){
    	//返回封装
    	  
    	  OapiUserGetResponse userInfoResponse =new OapiUserGetResponse();
    	  //2.获取access_token
    	  String accessToken = redis.get(tokenKey);
    	  //3.获取用户userid
    	  String userIdUrl =userUrl + "?access_token=" + accessToken + "&code=" + code;
    	  try {
    	  DingTalkClient client = new DefaultDingTalkClient(userUrl);
    	  OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
    	  request.setCode(code);
    	  request.setHttpMethod("GET");
    	  OapiUserGetuserinfoResponse response;
		
			response = client.execute(request, accessToken);
		
    	  String userId = response.getUserid();
    	  //根据用户id获取当前用户
    	  DingTalkClient userInfo = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
    	  OapiUserGetRequest userInfoRequest = new OapiUserGetRequest();
    	  userInfoRequest.setUserid(userId);
    	  userInfoRequest.setHttpMethod("GET");
    	  userInfoResponse = userInfo.execute(userInfoRequest, accessToken);
    	
    	  } catch (ApiException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
    	  //通过手机号获取该用户
//    	  SysUser sysUser = 。。。。。;
//    	  if(sysUser == null){
//    	   //不存在该用户
//    	   return WebResponse.resFail("您无权限访问", null);
//    	  }
//    	  
//    	  
//    	  //钉钉发送免登成功消息给用户
//    	  sendMessage(accessToken, userId, userInfo.get("name").asText());
		return userInfoResponse;
    }

    /**
     * 登出并清理session
     * */
    public String logout(){
        SecurityUtils.getSubject().logout();//登出
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().removeAttribute("sysUser");//清理session
        return null;
    }


}
