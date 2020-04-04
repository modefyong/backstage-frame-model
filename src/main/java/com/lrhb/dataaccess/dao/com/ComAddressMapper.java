package com.lrhb.dataaccess.dao.com;

import com.lrhb.dataaccess.model.com.ComAddress;

public interface ComAddressMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComAddress record);

    int insertSelective(ComAddress record);

    ComAddress selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComAddress record);

    int updateByPrimaryKey(ComAddress record);
}