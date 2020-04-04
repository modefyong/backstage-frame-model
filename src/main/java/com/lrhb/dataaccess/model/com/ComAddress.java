package com.lrhb.dataaccess.model.com;

import lombok.Data;

@Data
public class ComAddress {
    private String id;

    private String province;

    private String provinceCode;

    private String city;

    private String cityCode;

    private String county;

    private String countyCode;

    private String subdistrict;

    private String address;

    private String foreignKey;

}