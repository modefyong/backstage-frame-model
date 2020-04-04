package com.lrhb.dataaccess.model.com;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ComDivision{
    private String id;
    
    private String cityCode;
    
    private String divisionName;
    
    private String divisionCode;
    
    private String postcode;
    
    private String parentId;
    
    private String parentDivisionCode;
    
    private String province;
    
    private String prefectureLevelCity;
    
    private String countryLevelCity;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    private String createUser;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    private String updateUser;

}