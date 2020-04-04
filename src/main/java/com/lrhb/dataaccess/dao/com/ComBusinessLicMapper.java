package com.lrhb.dataaccess.dao.com;

import com.lrhb.dataaccess.model.com.ComBusinessLic;

public interface ComBusinessLicMapper {
    int deleteByPrimaryKey(String blId);

    int insert(ComBusinessLic record);

    int insertSelective(ComBusinessLic record);

    ComBusinessLic selectByPrimaryKey(String blId);

    int updateByPrimaryKeySelective(ComBusinessLic record);

    int updateByPrimaryKey(ComBusinessLic record);
}