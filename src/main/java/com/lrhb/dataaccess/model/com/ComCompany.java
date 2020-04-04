package com.lrhb.dataaccess.model.com;

import lombok.Data;

@Data
public class ComCompany {
    private String cId;

    private String cName;

    private String cAddress;

    private String cPhone;

    private String cPostcode;

    private String cLongitude;

    private String cLatitude;

    private String cCode;

    private String cLicCode;

    private String cSocialCreditCode;

    private String cType;

    private String cSrc;
    
  //-----------行政区域详细地址
    private String province;
    
    private String provinceCode;
    
    private String city;
    
    private String cityCode;
    
    private String county;
    
    private String address;
    
    private String countyCode;
    
    private String refAddress;
    
    

}