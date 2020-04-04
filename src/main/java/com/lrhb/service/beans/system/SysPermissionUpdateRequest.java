package com.lrhb.service.beans.system;

import org.hibernate.validator.constraints.NotBlank;
import lombok.Data;

@Data
public class SysPermissionUpdateRequest {
   
    @NotBlank
    private String id;
    /**地址*/
    @NotBlank
    private String url;
    /**角色*/
    @NotBlank
    private String roles;
    /**排序*/
    private int sort;
    /**是否删除*/
    private String isDeleted;

}
