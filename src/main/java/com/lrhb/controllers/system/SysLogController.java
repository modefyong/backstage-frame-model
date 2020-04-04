package com.lrhb.controllers.system;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lrhb.dataaccess.model.system.SysOperLog;
import com.lrhb.mgrframework.annotation.BizOperLog;
import com.lrhb.mgrframework.beans.constant.OperType;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.beans.response.PageResultBean;
import com.lrhb.mgrframework.beans.response.ResultBean;
import com.lrhb.service.api.system.SysOperLogService;
import com.lrhb.service.beans.system.LogBatchDelRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统日志
 */
@Slf4j
@Controller
@RequestMapping("/log")
public class SysLogController {

    @Autowired
    SysOperLogService sysOperLogServiceImpl;

    /**
     * 获取所有日志列表
     * @param page 页序
     * @param limit 分页大小
     * */
    @RequestMapping(value = "/logList.do",method = RequestMethod.POST)
    @ResponseBody
    //@BizOperLog(operType = OperType.Query,memo = "获取所有日志列表")
    public IResult getLogList(String page, String limit, String loginName, String operType, String startTime, String endTime){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new PageResultBean<Collection<SysOperLog>>(sysOperLogServiceImpl.getAll(page,limit,loginName,operType,startTime,endTime),sysOperLogServiceImpl.countGetAll(loginName,operType,startTime,endTime));
    }

    /**
     * 批量删除
     * */
    @RequestMapping(value = "/batchDelete.do",method = RequestMethod.POST)
    @ResponseBody
    @BizOperLog(operType = OperType.DELETE,memo = "批量删除日志")
    public IResult batchDelete(@RequestBody LogBatchDelRequest request){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Boolean>(sysOperLogServiceImpl.batchDelete(request));
    }

    /**
     * 删除
     * @param id 日志Id
     * */
    @RequestMapping(value = "/delete.do",method = RequestMethod.POST)
    @ResponseBody
    @BizOperLog(operType = OperType.DELETE,memo = "删除日志")
    public IResult delete(String id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Boolean>(sysOperLogServiceImpl.delete(id));
    }



}
