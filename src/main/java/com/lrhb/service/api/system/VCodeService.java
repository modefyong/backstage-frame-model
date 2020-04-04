package com.lrhb.service.api.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码服务
 */
public interface VCodeService {

    /**
     * 输出验证码到客户端
     * */
    void outPutVCode(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
