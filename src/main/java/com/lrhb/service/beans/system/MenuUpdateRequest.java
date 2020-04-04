package com.lrhb.service.beans.system;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新菜单请求bean
 */
@Data
public class MenuUpdateRequest implements Serializable{

    /**菜单id*/
    private String id;
    /**菜单名称*/
    private String menuName;
    /**菜单访问链接*/
    private String menuUrl;
    /**后台访问路径*/
    private String requestUrl;
    /**排序号*/
    private String sort;
    /**角色id列表*/
    private List<String> roleIdList;
}
