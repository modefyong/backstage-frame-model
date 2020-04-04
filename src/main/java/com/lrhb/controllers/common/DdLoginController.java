package com.lrhb.controllers.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dingtalk.api.response.OapiUserGetResponse;
import com.lrhb.controllers.system.LoginController;
import com.lrhb.mgrframework.beans.response.AppResult;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.beans.response.ResultBean;
import com.lrhb.service.api.common.DdLoginService;
import com.lrhb.service.api.system.LoginService;
import com.lrhb.service.beans.system.LoginResponse;
import com.lrhb.utils.list.JedisClient;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
public class DdLoginController {
	 @Autowired
	 DdLoginService ddLoginService;
	/**
	 * redis 请求测试
	 */
	public static void main(String[] args) {
//	     String path1 = Thread.currentThread().getContextClassLoader().getResource("").getPath();//获取当前资源的虚拟路径
//
//	      System.out.println(path1);
//		//初始化Spring容器
//				ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-redis.xml");
//				//从容器中获得JedisClient对象
//				JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
//				jedisClient.set("first", "100");
//				String result = jedisClient.get("first");
//				System.out.println(result);		
	}
	
	 /**
     * 登录请求
     */
    @RequestMapping(value = "/noLogin", method = RequestMethod.GET)
    @ResponseBody
    public IResult noLogin(String code,HttpServletRequest request) {
        return new ResultBean<OapiUserGetResponse>(ddLoginService.login(code));
    }
}
