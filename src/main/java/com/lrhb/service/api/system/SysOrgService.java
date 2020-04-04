package com.lrhb.service.api.system;

import java.util.List;
import com.lrhb.dataaccess.model.system.SysOrganize;
import com.lrhb.service.beans.system.OrgNodeResponse;

/**
 * 组织机构服务
 */
public interface SysOrgService {

    /**
     * 获取所有组织机构
     * */
    List<OrgNodeResponse> getAll();

    /**
     * 根据id获取组织机构
     * @param id 主键
     * */
    SysOrganize getById(String id);

    /**
     * 更新组织机构
     * @param org 组织机构信息
     * */
    Boolean update(SysOrganize org);

    /**
     * 删除组织机构
     * @param id 组织机构id
     * */
    Boolean deleteById(String id);

    /**
     * 添加组织机构
     * @param org 组织机构信息
     * */
    Boolean add(SysOrganize org);

}
