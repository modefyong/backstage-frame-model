package com.lrhb.service.beans.system;

import java.io.Serializable;
import java.util.List;
import com.lrhb.dataaccess.model.system.SysMenu;
import lombok.Data;

/**
 * 返回给客户端的菜单信息
 */
@Data
public class SysMenuExtend extends SysMenu implements Serializable{

    /**
     * 父菜单编码
     * */
    private String parentMenuCode;

    /**
     * 父菜单名称
     * */
    private String parentMenuName;

    /**
     * 菜单所属角色id列表
     * */
    private List<String> roleIdList;

}
