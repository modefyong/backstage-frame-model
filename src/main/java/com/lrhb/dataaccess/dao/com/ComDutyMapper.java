package com.lrhb.dataaccess.dao.com;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lrhb.dataaccess.model.com.ComDuty;

public interface ComDutyMapper {
    int deleteByPrimaryKey(String dId);

    int insert(ComDuty record);

    int insertSelective(ComDuty record);

    ComDuty selectByPrimaryKey(String dId);

    int updateByPrimaryKeySelective(ComDuty record);

    int updateByPrimaryKey(ComDuty record);
    
    List<ComDuty> dutyList();
    
    List<ComDuty> selectAll(@Param("start") int start,
            @Param("end") int end,
            @Param("dName") String dName,
            @Param("dStatus") String dStatus);
    int countSelectAll(@Param("dName") String dName,
            @Param("dStatus") String dStatus);
}