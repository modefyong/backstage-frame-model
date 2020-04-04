package com.lrhb.service.impl.system;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lrhb.dataaccess.dao.system.SysAccessPermissionMapper;
import com.lrhb.dataaccess.model.system.SysAccessPermission;
import com.lrhb.mgrframework.utils.CheckUtil;
import com.lrhb.mgrframework.utils.UserInfoUtil;
import com.lrhb.service.api.system.SysAccessPermissionService;
import com.lrhb.service.beans.system.SysPermissionRequest;
import com.lrhb.service.beans.system.SysPermissionResponse;
import com.lrhb.service.beans.system.SysPermissionUpdateRequest;
import com.lrhb.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;

/**
 */
@Slf4j
@Service
public class SysAccessPermissionServiceImpl implements SysAccessPermissionService {

    @Autowired
    SysAccessPermissionMapper sysAccessPermissionDao;

    public List<SysAccessPermission> getAll(){
        return sysAccessPermissionDao.selectAll();
    }
    
    /**
       *  添加权限
     * @param permission 记录
     * @return String 主键id
     * */
    @Transactional(rollbackForClassName = "Exception")
    public String addPermission(SysPermissionRequest permission){
        CheckUtil.notNull(permission,"添加权限为空");
        CheckUtil.notBlank(permission.getUrl(),"地址为空");
        CheckUtil.notBlank(permission.getRoles(),"角色为空");

        //登录名不能重复
//        User old = dao.getByLoginName(user.getLoginName());
//        CheckUtil.check(old == null,"该用户名已经存在");

        SysAccessPermission newPermission = new SysAccessPermission();
        newPermission.setId(UUIDUtil.getUUID());
        newPermission.setRoles(permission.getRoles());
        newPermission.setUrl(permission.getUrl());
        newPermission.setSort(permission.getSort());
        newPermission.setIsDeleted("0");
        newPermission.setCreateUser(UserInfoUtil.getUser().toString());
        newPermission.setCreateTime(new Date());
        sysAccessPermissionDao.insertSelective(newPermission);

        log.info("权限新增成功，开始关联用户角色");
//        List<String> roleIdList = user.getRoleIdList();
//        addUserRole(newUser.getId(),roleIdList);

        return newPermission.getId();
    }
    
    /**
     * 根据id删除权限
     * @param id 用户id
     * */
    public Boolean deleteById(String id){
        CheckUtil.notBlank(id,"权限id为空");

//        sysAccessPermissionDao.deleteByPrimaryKey(id);
        SysAccessPermission permission = sysAccessPermissionDao.selectByPrimaryKey(id);
        permission.setIsDeleted("1");
        sysAccessPermissionDao.updateByPrimaryKey(permission);
        return true;
    }

    /**
     * 根据id获取记录
     * @param id 主键
     * */
    public SysPermissionResponse getById(String id) {
        CheckUtil.notBlank(id,"用户id为空");
        SysPermissionResponse permissionResponse = new SysPermissionResponse();
        SysAccessPermission permission = sysAccessPermissionDao.selectByPrimaryKey(id);
        permissionResponse.setId(permission.getId());
        permissionResponse.setUrl(permission.getUrl());
        permissionResponse.setRoles(permission.getRoles());
        permissionResponse.setSort(permission.getSort());
        permissionResponse.setIsDeleted(permission.getIsDeleted());
        return permissionResponse;
    }

    /**
     * 全量更新
     * @param permissionRequest 记录
     * */
    public Boolean updatePermission(SysPermissionUpdateRequest permissionRequest) {
        CheckUtil.notNull(permissionRequest,"更新权限为空");
        SysAccessPermission permission = sysAccessPermissionDao.selectByPrimaryKey(permissionRequest.getId());
        permission.setRoles(permissionRequest.getRoles());
        permission.setUrl(permissionRequest.getUrl());
        permission.setIsDeleted(permissionRequest.getIsDeleted());
        permission.setSort(permissionRequest.getSort());
        sysAccessPermissionDao.updateByPrimaryKey(permission);
        
        return true;
    }


}
