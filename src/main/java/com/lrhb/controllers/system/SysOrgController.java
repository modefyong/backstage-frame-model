package com.lrhb.controllers.system;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lrhb.dataaccess.model.system.SysOrganize;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.beans.response.ResultBean;
import com.lrhb.service.api.system.SysOrgService;
import com.lrhb.service.beans.system.OrgNodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 组织机构
 */
@Controller
@Slf4j
@RequestMapping("/org")
public class SysOrgController {

    @Autowired
    SysOrgService sysOrgServiceImpl;

    /**
     * 获取所有组织机构
     * */
    @RequestMapping(value = "/getAll.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult getAll(){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Collection<OrgNodeResponse>>(sysOrgServiceImpl.getAll());
    }

    /**
     * 获取组织机构
     * */
    @RequestMapping(value = "/get.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult get(String id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<SysOrganize>(sysOrgServiceImpl.getById(id));
    }

    /**
     * 更新组织机构
     * @param org 组织机构信息
     * */
    @RequestMapping(value = "/update.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult update(SysOrganize org){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Boolean>(sysOrgServiceImpl.update(org));
    }

    /**
     * 删除组织机构
     * @param id 组织机构id
     * */
    @RequestMapping(value = "/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult delete(String id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Boolean>(sysOrgServiceImpl.deleteById(id));
    }

    /**
     * 添加组织机构
     * @param org 组织机构信息
     * */
    @RequestMapping(value = "/add.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult add(SysOrganize org){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Boolean>(sysOrgServiceImpl.add(org));
    }

}
