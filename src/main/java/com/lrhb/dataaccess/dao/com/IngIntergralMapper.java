package com.lrhb.dataaccess.dao.com;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lrhb.dataaccess.model.com.IngIntergral;
import com.lrhb.service.beans.intergral.IngIntergralExtend;
import com.lrhb.service.beans.system.SysMenuExtend;

public interface IngIntergralMapper {
    int deleteByPrimaryKey(String id);

    int insert(IngIntergral record);

    int insertSelective(IngIntergral record);

    IngIntergral selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(IngIntergral record);

    int updateByPrimaryKey(IngIntergral record);
    
    /**
     * 分页获取积分
     * */
    List<IngIntergralExtend> getAll(@Param("start") int start,
                                   @Param("end") int end,
                                   @Param("userName") String userName);

    /**
     * 获取积分页大小
     * */
    int countGetAll( @Param("userName") String interName);
    
    /**
     * 获取积分管理中的用户名
     * @param interName
     * @return
     */
    IngIntergral getIntergralName(@Param("userName") String interName);
    
    /**根据ID获取积分项列表
     * @param id
     * @return
     */
    IngIntergralExtend getById(@Param("id") String id);
}