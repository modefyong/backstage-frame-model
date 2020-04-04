package com.lrhb.service.beans.system;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 返回给前端的用户信息
 */
@Data
public class SysUserResponse implements Serializable{
    private String id;
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
    /**所属角色列表*/
    private List<String> roleIdList;

    /**
     * 构造函数
     * */
    public SysUserResponse(){
        this.roleIdList = new ArrayList<String>();
    }
}
