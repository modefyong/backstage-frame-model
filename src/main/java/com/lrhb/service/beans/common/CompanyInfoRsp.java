package com.lrhb.service.beans.common;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CompanyInfoRsp {
	
	private String id;

    private String name;

    private String address;

    private String phone;

    private String postcode;

    private String qcode;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date valiDateEnd;

    private String authHwType;

}
