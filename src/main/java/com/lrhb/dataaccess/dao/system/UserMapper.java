package com.lrhb.dataaccess.dao.system;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.lrhb.dataaccess.model.system.User;

/**
 */
public interface UserMapper {

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 查找用户
     *
     * @param loginName 登录名
     * @param password  密码（为了不增加复杂度，这里不进行加密，使用明文）
     */
    Integer countUserByNameAndPwd(@Param("loginName") String loginName,
                                  @Param("password") String password);

    /**
     * 根据loginName获取用户
     *
     * @param loginName 登录名
     */
    User getByLoginName(@Param("loginName") String loginName);

    /**
     * 分页获取用户列表
     */
    List<User> getAll(
            @Param("start") int start,
            @Param("end") int end,
            @Param("loginName") String loginName,
            @Param("realName") String realName,
            @Param("isForbidden") String isForbidden);

    /**
     * 获取用户列表大小
     */
    int countGetAll(@Param("loginName") String loginName,
                    @Param("realName") String realName,
                    @Param("isForbidden") String isForbidden);
    
    /**
     * 根据真实姓名获取用户id
     * @param realName
     * @return
     */
    User getuserIdByRealName(@Param("realName") String realName);
    
    
    /**根据登录名获取用户id
     * @param loginName
     * @return
     */
//    User getUserIdByLoginName(@Param("loginName") String loginName);
    
    
    /**
     * 根据部门获取部门内全部员工
     * @param department
     * @return
     */
    List<User> getUsersByDepartment(@Param("department") String department);
    
    /**
     * 根据部门id获取部门内全部员工id(不包括主管)
     * @param department
     * @return
     */
	List<String> getStaffIdListByDeptId(@Param("userId") String userId, @Param("deptId") String deptId);
    
    /**
     * 分页获取用户列表(排除超级管理员)
     */
    List<User> getAllExcludeSuperUser(
            @Param("start") int start,
            @Param("end") int end,
            @Param("id") String id,
            @Param("loginName") String loginName,
            @Param("realName") String realName,
            @Param("isForbidden") String isForbidden);
}
