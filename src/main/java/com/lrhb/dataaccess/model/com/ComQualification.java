package com.lrhb.dataaccess.model.com;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ComQualification {
    private String qId;

    private String qCode;

    private String qlicIssuAuth;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date qauthDate;

    private String qName;

    private String qCorporation;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date qvaliDateStart;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date qvaliDateEnd;

    private String qAuthHwType;

    private String qLegalRep;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date qlicFirstDate;

    private String qType;

    private String qBusiSope;

    private String qClass;

    private String qCompCode;

}