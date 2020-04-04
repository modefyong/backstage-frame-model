package com.lrhb.controllers.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lrhb.dataaccess.model.system.User;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.beans.response.ResultBean;
import com.lrhb.service.api.system.LoginService;
import com.lrhb.service.beans.system.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@Slf4j
@Controller
public class LoginController {

    /**
     * 一个controller对应一个service，方便问题定位
     * */
    @Autowired
    LoginService loginServiceImpl;

    /**
     * 登录请求
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public IResult login(String name, String pwd, String code,HttpServletRequest request) {
        //返回视图的，均采用ModelUtil.fillModelAndReturnView()进行代码简化
        return new ResultBean<LoginResponse>(loginServiceImpl.login(name,pwd,code));
    }

    /**
     * 登出
     * */
    @RequestMapping(value = "/logout.do", method = RequestMethod.POST)
    @ResponseBody
    public IResult logout(){
        return new ResultBean<String>(loginServiceImpl.logout());
    }

    /**
     * 未授权
     * */
    @RequestMapping(value = "/401", method = RequestMethod.GET)
    @ResponseBody
    public IResult unauthorization(HttpServletResponse response){
        //前端通过http状态判断
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ResultBean<String>();
    }

    /**
     * 未登录
     * */
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    @ResponseBody
    public IResult needLogin(HttpServletResponse response){
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return new ResultBean<String>();
    }
    
    /**
     * 测试React登录请求
     */
    @RequestMapping(value = "/loginReact", method = RequestMethod.POST)
    @ResponseBody
    public IResult loginReact(@RequestBody LoginResponse user) {
        //返回视图的，均采用ModelUtil.fillModelAndReturnView()进行代码简化
        return new ResultBean<LoginResponse>(loginServiceImpl.loginReact(user.getUsername(), user.getPassword()));
    }
}
