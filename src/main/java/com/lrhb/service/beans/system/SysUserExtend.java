package com.lrhb.service.beans.system;

import java.io.Serializable;

import com.lrhb.dataaccess.model.system.User;

import lombok.Data;

/**
 * 返回给客户端的用户信息
 */
@Data
public class SysUserExtend extends User implements Serializable{

    /**
     * 公司名称
     */
    private String  companyName;
    
    /**
     * 性别
     */
    private String  sex;
    
    /**
     * 入职时间
     */
    private String  entryTime;
    
    /**
     * 学历
     */
    private String  educationQualify;
    
    /**
     * 职称
     */
    private String  professionalName;


}
