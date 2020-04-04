package com.lrhb.service.beans.system;

import lombok.Data;

@Data
public class SysPermissionResponse {
    
    private String id;
    /**地址*/
    private String url;
    /**角色*/
    private String roles;
    /**排序*/
    private int sort;
    /**是否删除*/
    private String isDeleted;

}
