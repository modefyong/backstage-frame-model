package com.lrhb.dataaccess.dao.com;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lrhb.dataaccess.model.com.ComStaff;

public interface ComStaffMapper {
    int deleteByPrimaryKey(String stId);

    int insert(ComStaff record);

    int insertSelective(ComStaff record);

    ComStaff selectByPrimaryKey(String stId);

    int updateByPrimaryKeySelective(ComStaff record);

    int updateByPrimaryKey(ComStaff record);
    
    List<ComStaff> selectAll(@Param("start") int start,
            @Param("end") int end,
            @Param("stName") String stName,
            @Param("stSex") String stSex,
            @Param("compCode") String compCode,
            @Param("duty") String duty);
    int countSelectAll(@Param("stName") String stName,
            @Param("stSex") String stSex,
            @Param("compCode") String compCode,
            @Param("duty") String duty);
}