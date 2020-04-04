package com.lrhb.dataaccess.dao.com;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lrhb.dataaccess.model.com.ComCompany;
import com.lrhb.dataaccess.model.com.ComDivision;
import com.lrhb.service.beans.common.Company;
import com.lrhb.service.beans.common.CompanyInfoRsp;

public interface ComDivisionMapper {
	
    int deleteByPrimaryKey(String id);

    int insertSelective(ComDivision record);

    ComDivision getDivision(String id);
    
    int updateByPrimaryKeySelective(ComDivision record);
    //@Param("divisionCode") String divisionCode,
    List<ComDivision> selectAll(@Param("start") int start,
            @Param("end") int end,
            @Param("divisionName") String divisionName,
            @Param("parentId") String parentId);
    
    int countSelectAll(@Param("divisionName") String divisionName,
            @Param("parentId") String parentId);

	List<ComDivision> getProvinceList();

	List<ComDivision> getCityList(String parentId);

	int getCount(String divisionName);

	String getCode(String parentName);
}