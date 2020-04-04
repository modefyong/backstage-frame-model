package com.lrhb.service.beans.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


/**
 * 定义角色扩展实体类 —— 用于React项目
 * @author modefy
 *
 */
@Data
public class RolesResponseReact implements Serializable{

    /**
     *角色id
     * */
    private String id;
    
//    角色名称
    private String name;
    
//    创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // timezone 时区：东八区。pattern：时间格式
    private Date createTime;
    
//    授权人
    private String authUser;
    
//    授权时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date authTime;

}
