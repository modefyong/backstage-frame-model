package com.lrhb.dataaccess.model.com;

import java.util.Date;

import lombok.Data;

@Data
public class ComBusinessLic {
    private String blId;

    private String blScCode;

    private String blName;

    private String blType;

    private String blHome;

    private String blCorporation;

    private Double blRegisteredCapital;

    private Date blEstablishDate;

    private String blBusiSope;

    private Date blStartDate;

    private Date blEndDate;

    private String blIssuAuth;

    private Date blAuthDate;

}