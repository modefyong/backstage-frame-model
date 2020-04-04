package com.lrhb.controllers.system;

import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lrhb.dataaccess.model.system.SysRole;
import com.lrhb.mgrframework.annotation.BizOperLog;
import com.lrhb.mgrframework.beans.constant.OperType;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.beans.response.PageResultBean;
import com.lrhb.mgrframework.beans.response.ResultBean;
import com.lrhb.service.api.system.SysRoleService;
import com.lrhb.service.api.system.SysUserService;
import com.lrhb.service.beans.system.SysUserResponse;
import com.lrhb.service.beans.system.UserUpdateRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 个人中心
 */
@Slf4j
@Service
@RequestMapping("/personCenter")
public class PersonCenterController {

    @Autowired
    SysUserService sysUserServiceImpl;

    @Autowired
    SysRoleService sysRoleServiceImpl;

    /**
     * 获取个人信息
     * */
    @RequestMapping(value = "/get.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult getUser(String id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<SysUserResponse>(sysUserServiceImpl.getById(id));
    }

    /**
     * 更新个人信息
     * */
    @RequestMapping(value = "/update.do",method = RequestMethod.POST)
    @ResponseBody
    @BizOperLog(operType = OperType.UPDATE,memo = "更新个人信息")
    public IResult updateUser(@RequestBody @Valid UserUpdateRequest user, BindingResult result){//传递了数组，前台放在payload里面了，后台通过@RequestBody获取
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Boolean>(sysUserServiceImpl.updateUser(user));
    }

    /**
     * 修改密码
     * */
    @RequestMapping(value = "/changePwd.do",method = RequestMethod.POST)
    @ResponseBody
    @BizOperLog(operType = OperType.UPDATE,memo = "更新个人密码")
    public IResult changePwd(String loginName,String oldPwd,String newPwd){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Boolean>(sysUserServiceImpl.changePwd(loginName,oldPwd,newPwd));
    }

    /**
     * 获取所有角色列表
     * @param page 页序
     * @param limit 分页大小
     * */
    @RequestMapping(value = "/roleList.do",method = RequestMethod.POST)
    @ResponseBody
    //@BizOperLog(operType = OperType.Query,memo = "获取所有角色列表")
    public IResult getRoleList(String page,String limit,String roleName,String isSuper){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new PageResultBean<Collection<SysRole>>(sysRoleServiceImpl.getAll(page,limit,roleName,isSuper),sysRoleServiceImpl.countGetAll(roleName,isSuper));
    }
}
