package com.lrhb.dataaccess.model.com;

import java.util.Date;

import lombok.Data;

@Data
public class ComDuty {
    private String dId;

    private String dCode;

    private String dName;

    private String dStatus;

    private String dCreateUser;

    private Date dCreateDatetime;
}