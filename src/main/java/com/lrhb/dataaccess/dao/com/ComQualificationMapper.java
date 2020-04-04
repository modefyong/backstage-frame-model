package com.lrhb.dataaccess.dao.com;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lrhb.dataaccess.model.com.ComQualification;

public interface ComQualificationMapper {
    int deleteByPrimaryKey(String qId);

    int insert(ComQualification record);

    int insertSelective(ComQualification record);

    ComQualification selectByPrimaryKey(String qId);

    int updateByPrimaryKeySelective(ComQualification record);

    int updateByPrimaryKey(ComQualification record);
    
    List<ComQualification> selectAll(@Param("start") int start,
            @Param("end") int end,@Param("qname") String qname,@Param("qcode") String qcode);
    int countSelectAll(@Param("qname") String qname,@Param("qcode") String qcode);
}