package com.lrhb.controllers.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lrhb.service.api.system.VCodeService;
import lombok.extern.slf4j.Slf4j;

/**
 * 图片验证码控制器
 */
@Controller
@Slf4j
public class VCodeController {

    @Autowired
    VCodeService vCodeServiceImpl;

    static {
        //防止二维码显示不了
        System.setProperty("java.awt.headless","true");
    }

    /**
     * 获取验证码
     * @param response
     */
    @RequestMapping(value="/getVCode",method= RequestMethod.GET)
    public void getGifCode(HttpServletResponse response, HttpServletRequest request) throws Exception{
       vCodeServiceImpl.outPutVCode(request,response);
    }
}
