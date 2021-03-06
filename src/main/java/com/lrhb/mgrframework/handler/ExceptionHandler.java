package com.lrhb.mgrframework.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.beans.response.ResultBean;
import com.lrhb.mgrframework.exceptions.BizException;
import com.lrhb.mgrframework.exceptions.CheckException;
import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CheckException.class)
    @ResponseBody
    public IResult handleCheckException(CheckException ce, HttpServletRequest request, HttpServletResponse response){
        log.error("CheckException:{"+ce.getErrorCode()+","+ce.getMessage()+"}",ce);
        ResultBean<?> result = new ResultBean();
        result.setMsg(ce.getMessage());
        result.setCode(ce.getErrorCode());
        return result;
   }

    @org.springframework.web.bind.annotation.ExceptionHandler(BizException.class)
    @ResponseBody
    public IResult handleBizException(BizException be, HttpServletRequest request,HttpServletResponse response){
        log.error("BizException:{"+be.getErrorCode()+","+be.getMessage()+"}",be);
        ResultBean<?> result = new ResultBean();
        result.setMsg(be.getMessage());
        result.setCode(be.getErrorCode());
        return result;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public IResult handleException(DataAccessException e, HttpServletRequest request,HttpServletResponse response){
        log.error("DataAccessException:{"+ResultBean.SYSTEM_FAIL+","+e.getMessage()+"}",e);
        ResultBean<?> result = new ResultBean();
        result.setMsg("数据库访问异常");
        result.setCode(ResultBean.SYSTEM_FAIL);
        return result;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Throwable.class)
    @ResponseBody
    public IResult handleException(Throwable e, HttpServletRequest request,HttpServletResponse response){
        log.error("Throwable:{"+ResultBean.SYSTEM_FAIL+","+e.getMessage()+"}",e);
        ResultBean<?> result = new ResultBean();
        result.setMsg(e.getMessage());
        result.setCode(ResultBean.SYSTEM_FAIL);
        return result;
    }
}
