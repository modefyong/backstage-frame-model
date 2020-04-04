package com.lrhb.service.api.system;

import java.util.List;
import com.lrhb.dataaccess.model.system.SysAccessPermission;
import com.lrhb.service.beans.system.SysPermissionRequest;
import com.lrhb.service.beans.system.SysPermissionResponse;
import com.lrhb.service.beans.system.SysPermissionUpdateRequest;

/**
 * 访问权限服务
 */
public interface SysAccessPermissionService {

    /**
                 * 获取所有访问权限配置
     * */
    List<SysAccessPermission> getAll();
    
    /**
             * 添加权限
     * @param user SysPermissionRequest
     * @return String 主键id
     * */
    String addPermission(SysPermissionRequest request);
    
    /**
     * 根据id删除权限
     * @param id 权限id
     * */
    Boolean deleteById(String id);
    
    /**
     *   根据id获取权限
     * @param id 权限id
     * */
    SysPermissionResponse getById(String id);
    
    
    /**
     *    更新权限
     * @param id 权限id
     * */
    Boolean updatePermission(SysPermissionUpdateRequest permission);
}
