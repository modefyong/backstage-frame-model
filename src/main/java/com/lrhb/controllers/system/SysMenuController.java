package com.lrhb.controllers.system;

import java.util.Collection;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lrhb.dataaccess.model.system.SysMenu;
import com.lrhb.dataaccess.model.system.User;
import com.lrhb.mgrframework.annotation.BizOperLog;
import com.lrhb.mgrframework.beans.constant.OperType;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.beans.response.PageResultBean;
import com.lrhb.mgrframework.beans.response.ResultBean;
import com.lrhb.service.api.system.SysMenuService;
import com.lrhb.service.beans.system.MenuTreeResponse;
import com.lrhb.service.beans.system.MenuUpdateRequest;
import com.lrhb.service.beans.system.SysMenuExtend;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统菜单controller
 */
@Controller
@Slf4j
@RequestMapping("/menu")
public class SysMenuController {
    @Autowired
    SysMenuService sysMenuServiceImpl;

    /**
     * 获取所有菜单列表
     * @param page 页序
     * @param limit 分页大小
     * */
    @RequestMapping(value = "/menuList.do",method = RequestMethod.POST)
    @ResponseBody
    //@BizOperLog(operType = OperType.Query,memo = "获取所有菜单列表")
    public IResult getMenuList(String page, String limit, String menuName, String menuCode, String parentMenuId){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new PageResultBean<Collection<SysMenuExtend>>(sysMenuServiceImpl.getAll(page,limit,menuName,menuCode,parentMenuId),sysMenuServiceImpl.countGetAll(menuName,menuCode,parentMenuId));
    }

    /**
     * 获取所有一级菜单列表
     * */
    @RequestMapping(value = "/firstClassMenus.do",method = RequestMethod.POST)
    @ResponseBody
    //@BizOperLog(operType = OperType.Query,memo = "获取所有一级菜单列表")
    public IResult getFirstMenuList(){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Collection<SysMenu>>(sysMenuServiceImpl.getFirstClassMenus());
    }

    /**
     * 获取角色可见菜单列表
     * */
    @RequestMapping(value = "/menuList.do",method = RequestMethod.GET)
    @ResponseBody
    //@BizOperLog(operType = OperType.Query,memo = "获取角色菜单列表")
    public IResult getMenuList(){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Collection<MenuTreeResponse>>(sysMenuServiceImpl.getMenuByRoles((User) SecurityUtils.getSubject().getPrincipal()));
    }

    /**
     * 添加菜单
     * */
    @RequestMapping(value = "/add.do",method = RequestMethod.POST)
    @ResponseBody
    @BizOperLog(operType = OperType.ADD,memo = "添加菜单")
    public IResult addMenu(String menuName,String menuUrl,String menuType,String parentMenuId,String requestUrl,String sort){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<String>(sysMenuServiceImpl.addMenu(menuName,menuUrl,menuType,parentMenuId,requestUrl,sort));
    }

    /**
     * 删除菜单
     * */
    @RequestMapping(value = "/delete.do",method = RequestMethod.POST)
    @ResponseBody
    @BizOperLog(operType = OperType.DELETE,memo = "删除菜单")
    public IResult deleteMenu(String menuId){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Boolean>(sysMenuServiceImpl.deleteMenu(menuId));
    }

    /**
     * 获取菜单
     * */
    @RequestMapping(value = "/get.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult getMenu(String id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<SysMenuExtend>(sysMenuServiceImpl.getById(id));
    }

    /**
     * 更新菜单
     * @param id 菜单id
     * @param menuName 菜单名称
     * @param menuUrl 菜单访问链接
     * @param requestUrl 后台访问路径
     * @param idList 角色id列表
     * @param sort 排序号
     * */
    @RequestMapping(value = "/update.do",method = RequestMethod.POST)
    @ResponseBody
    @BizOperLog(operType = OperType.UPDATE,memo = "更新菜单")
    public IResult updateMenu(@RequestBody MenuUpdateRequest menu){//传递了数组，前台放在payload里面了，后台通过@RequestBody获取
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Boolean>(sysMenuServiceImpl.updateMenu(menu.getId(),menu.getMenuName(),menu.getMenuUrl(),menu.getRequestUrl(),menu.getSort(),menu.getRoleIdList()));
    }
}
