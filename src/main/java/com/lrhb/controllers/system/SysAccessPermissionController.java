package com.lrhb.controllers.system;

import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lrhb.dataaccess.model.system.SysAccessPermission;
import com.lrhb.mgrframework.annotation.BizOperLog;
import com.lrhb.mgrframework.beans.constant.OperType;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.beans.response.ResultBean;
import com.lrhb.service.api.system.SysAccessPermissionService;
import com.lrhb.service.beans.system.SysPermissionRequest;
import com.lrhb.service.beans.system.SysPermissionResponse;
import com.lrhb.service.beans.system.SysPermissionUpdateRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统日志
 */
@Slf4j
@Controller
@RequestMapping("/sysPermission")
public class SysAccessPermissionController {
    
    @Autowired
    SysAccessPermissionService sysAccessPermissionServiceImpl;
    
    @RequestMapping(value = "/permissionList.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult getUserList(String page, String limit, String loginName, String realName, String status){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Collection<SysAccessPermission>>(sysAccessPermissionServiceImpl.getAll());
    }
    
    /**
     * 添加权限
     * */
    @RequestMapping(value = "/add.do",method = RequestMethod.POST)
    @ResponseBody
    @BizOperLog(operType = OperType.ADD,memo = "添加权限")
    public IResult addPermission(@RequestBody SysPermissionRequest request){
        //返回json至前端的均返回ResultBean或者PageResultBean
        log.debug("request");
        return new ResultBean<String>(sysAccessPermissionServiceImpl.addPermission(request));
    }
    
    /**
     * 删除用户
     * */
    @RequestMapping(value = "/delete.do",method = RequestMethod.POST)
    @ResponseBody
    @BizOperLog(operType = OperType.DELETE,memo = "删除权限")
    public IResult deleteUser(String id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Boolean>(sysAccessPermissionServiceImpl.deleteById(id));
    }
    
    /**
     * 获取权限
     * */
    @RequestMapping(value = "/get.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult getPermission(String id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<SysPermissionResponse>(sysAccessPermissionServiceImpl.getById(id));
    }

    /**
     * 更新权限
     * */
    @RequestMapping(value = "/update.do",method = RequestMethod.POST)
    @ResponseBody
    @BizOperLog(operType = OperType.UPDATE,memo = "更新权限")
    public IResult updatePermission(@RequestBody @Valid SysPermissionUpdateRequest permission, BindingResult result){//传递了数组，前台放在payload里面了，后台通过@RequestBody获取
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Boolean>(sysAccessPermissionServiceImpl.updatePermission(permission));
    }


}
