package com.lrhb.service.beans.system;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 新增用户请求bean
 */
@Data
public class UserAddRequest implements Serializable{

    /**登录名*/
    private String loginName;
    
    /**真实姓名*/
    private String realName;
    
    /**所属机构id*/
    private String orgId;
    
    /**所属机构名称*/
    private String orgName;
    
    /**手机号码*/
    private String mobile;
    
    /**
     * 学历
     */
    private String educationQualify;
    
    /**
     * 职称
     */
    private String professionalName;
    
    /**
     * 入职时间
     */
    private String entryTime;
    
    private String sex;
    /**头像*/
    private String icon;
    
    /**所属角色列表*/
    private List<String> roleIdList;
}
