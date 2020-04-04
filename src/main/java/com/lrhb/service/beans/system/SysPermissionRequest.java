package com.lrhb.service.beans.system;

import lombok.Data;

@Data
public class SysPermissionRequest {
    
    /**地址*/
    private String url;
    /**角色*/
    private String roles;
    /**排序*/
    private int sort;

}
