package com.lrhb.service.beans.common;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ComStaffRequest {
    private String stId;

    private String stName;

    private Integer stSex;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date stBirthday;

    private String stJobNum;

    private String stEmail;

    private String stMobile;

    private String stPhone;

    private String stCompCode;

    private String stUserCode;

    private String stDuty;
    
    private String fileUrl;
    
    private String fileName;

}