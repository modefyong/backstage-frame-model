package com.lrhb.service.impl.system;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lrhb.dataaccess.dao.intergral.InterDetailListMapper;
import com.lrhb.dataaccess.dao.intergral.InterDetailMapper;
import com.lrhb.dataaccess.dao.intergral.InterDetailkindMapper;
import com.lrhb.dataaccess.dao.intergral.InterIntergralListMapper;
import com.lrhb.dataaccess.dao.system.SysUserRoleMapper;
import com.lrhb.dataaccess.dao.system.UserMapper;
import com.lrhb.dataaccess.model.intergral.InterDetailList;
import com.lrhb.dataaccess.model.intergral.InterDetailkind;
import com.lrhb.dataaccess.model.intergral.InterIntergralList;
import com.lrhb.dataaccess.model.system.SysRole;
import com.lrhb.dataaccess.model.system.SysUserRole;
import com.lrhb.dataaccess.model.system.User;
import com.lrhb.mgrframework.utils.CheckUtil;
import com.lrhb.mgrframework.utils.UserInfoUtil;
import com.lrhb.service.api.system.SysUserService;
import com.lrhb.service.beans.intergral.IntergralDetailExtend;
import com.lrhb.service.beans.intergral.IntergralListExtend;
import com.lrhb.service.beans.system.PageRange;
import com.lrhb.service.beans.system.SysUserResponse;
import com.lrhb.service.beans.system.UserAddRequest;
import com.lrhb.service.beans.system.UserUpdateRequest;
import com.lrhb.utils.Constants;
import com.lrhb.utils.PasswordUtil;
import com.lrhb.utils.SHA1;
import com.lrhb.utils.UUIDUtil;

import lombok.extern.slf4j.Slf4j;

/**
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    UserMapper dao;
    
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    
    @Autowired
	InterDetailMapper interDetailMapper;
    
    @Autowired
	InterDetailkindMapper interDetailKindMapper;
    
    @Autowired
	InterDetailListMapper interDetailListMapper;
    
    @Autowired
	InterIntergralListMapper interIntergralListMapper;
    
    
    /**
     * 验证用户
     * @param loginName 登录名
     * @param password 密码（为了不增加复杂度，这里不进行加密，使用明文）
     * */
    public Boolean validateUser(String loginName,String password){
        int n = dao.countUserByNameAndPwd(loginName,password);
        return n>0?true:false;
    }

    /**
     * 根据登录名获取用户
     * @param loginName 登录名
     * */
    public User getByLoginName(String loginName){
        return dao.getByLoginName(loginName);
    }

    /**
     * 获取用户角色列表
     * @param userId 用户id
     * */
    public List<String> getRoleNames(String userId){
        return sysUserRoleMapper.getRoleNamesByUserId(userId);
    }

    /**
     * 分页获取用户列表
     *
     * @param page         页序
     * @param limit        分页大小
     * @param loginName     登录名
     * @param realName     姓名
     * @param status       状态 0-禁用 1-启用
     */
    public List<User> getAll(String page, String limit, String loginName, String realName, String status) {
        PageRange pageRange = new PageRange(page, limit);
        List<User> userList =  dao.getAll(pageRange.getStart(), pageRange.getEnd(), loginName, realName, status);
        
//        根据登录用户筛选排除超级管理员后的用户集合。
//        如果登录用户不是超级管理员，则进行筛选，排除掉超级管理员。
        User loginUser = dao.getByLoginName(UserInfoUtil.getUser().toString());
//        易出错！如果数据库user表中超级管理员的登录名修改，此处也应该修改！
        User adminUser = dao.getByLoginName("admin");
        List<String> roleNames = sysUserRoleMapper.getRoleNamesByUserId(loginUser.getId());
        for(String roleName : roleNames) {
        	if(!roleName.equals("admin")) {
        		return dao.getAllExcludeSuperUser(pageRange.getStart(), pageRange.getEnd(),adminUser.getId(), loginName, realName, status);
        	}
        }
        return userList;
    }

    /**
     * 获取用户列表大小
     *
     * @param loginName     登录名
     * @param realName     姓名
     * @param status       状态 0-禁用 1-启用
     */
    public int countGetAll(String loginName, String realName, String status) {
        return dao.countGetAll(loginName, realName, status);
    }

    /**
     * 根据id删除用户
     * @param id 用户id
     * */
    public Boolean deleteById(String id){
        CheckUtil.notBlank(id,"用户id为空");

        dao.deleteByPrimaryKey(id);
        //删除用户角色关系
        sysUserRoleMapper.deleteByUserId(id);

        return true;
    }

    /**
     * 根据id获取记录
     * @param id 主键
     * */
    public SysUserResponse getById(String id){
        CheckUtil.notBlank(id,"用户id为空");

        SysUserResponse userResponse = new SysUserResponse();
        User user = dao.selectByPrimaryKey(id);
        List<SysRole> sysRoleTestList = sysUserRoleMapper.getRolesByUserId(id);

        //获取角色id列表
        if(sysRoleTestList != null&&!sysRoleTestList.isEmpty()){
            List<String> roleIdList = new ArrayList<String>();
            for(SysRole role:sysRoleTestList){
                roleIdList.add(role.getId());
            }
            userResponse.setRoleIdList(roleIdList);
        }

        userResponse.setId(user.getId());
        userResponse.setLoginName(user.getLoginName());
        userResponse.setMobile(user.getMobile());
        userResponse.setOrgId(user.getOrgId());
        userResponse.setOrgName(user.getOrgName());
        userResponse.setRealName(user.getRealName());

        return userResponse;
    }

    /**
     * 全量更新
     * @param userRequest 记录
     * */
    public Boolean updateUser(UserUpdateRequest userRequest){
        CheckUtil.notNull(userRequest,"更新用户为空");

        //登录名不能重复
        User old = dao.getByLoginName(userRequest.getLoginName());
        CheckUtil.check(old == null||old.getId().equals(userRequest.getId()),"用户名已存在");

        //更新用户
        User user = dao.selectByPrimaryKey(userRequest.getId());
        user.setOrgName(userRequest.getOrgName());
        user.setOrgId(userRequest.getOrgId());
        user.setLoginName(userRequest.getLoginName());
        user.setRealName(userRequest.getRealName());
        user.setMobile(userRequest.getMobile());
        user.setIcon(user.getIcon());
        dao.updateByPrimaryKey(user);

        //更新用户角色
        addUserRole(user.getId(),userRequest.getRoleIdList());

        return true;
    }

    /**
     * 添加用户
     * @param user 记录
     * @return String 主键id
     * */
    @Transactional(rollbackForClassName = "Exception")
    public String addUser(UserAddRequest user){
        CheckUtil.notNull(user,"添加用户为空");
        CheckUtil.notBlank(user.getLoginName(),"登录名为空");
        CheckUtil.notBlank(user.getOrgId(),"组织机构为空");
        CheckUtil.notBlank(user.getOrgName(),"组织机构为空");
        CheckUtil.notBlank(user.getEducationQualify(),"学历为空");
        CheckUtil.notBlank(user.getProfessionalName(),"职称为空");
        CheckUtil.notBlank(user.getEntryTime(),"入职时间为空");

        //登录名不能重复
        User old = dao.getByLoginName(user.getLoginName());
        CheckUtil.check(old == null,"该用户名已经存在");

        User newUser = new User();
        newUser.setId(UUIDUtil.getUUID());
        newUser.setLoginName(user.getLoginName());
        newUser.setIsForbidden("0");
        String sha1Pwd = (new SHA1()).getDigestOfString(Constants.INIT_PWD);//SHA1加密
        newUser.setPassword(sha1Pwd);
        newUser.setMobile(user.getMobile());
        newUser.setOrgId(user.getOrgId());
        newUser.setOrgName(user.getOrgName());
        newUser.setIcon(user.getIcon());
        newUser.setRealName(user.getRealName());
        newUser.setEducationQualify(user.getEducationQualify());
        newUser.setProfessionalName(user.getProfessionalName());
        newUser.setEntryTime(user.getEntryTime());
        newUser.setSex(user.getSex());
        dao.insertSelective(newUser);

        log.info("用户新增成功，开始关联用户角色");
        List<String> roleIdList = user.getRoleIdList();
        addUserRole(newUser.getId(),roleIdList);
        
//        添加用户时，将主管的主管权限分添加到积分列表
        
//      根据用户id从user-role关联表获取角色（集合）,判断该用户是否是主管
      List<String> roleNames = sysUserRoleMapper.getRoleNamesByUserId(newUser.getId());
      
      CheckUtil.notNull(roleNames, "未查询到角色信息！");
      
      for(String roleName : roleNames) {
      	if(roleName.equals("主管")) {
//      		若是主管，则根据"主管权限分"内容，获取细则，然后获取主管权限分值，添加到积分列表（需要主管权限分、用户姓名、部门）
      		IntergralDetailExtend interDetailExtend = interDetailMapper.getInterDetailIdByContont("主管权限分");
      		CheckUtil.notNull(interDetailExtend,"无主管权限分细则！");
      		String magAuthScore = interDetailExtend.getScore();
      		InterDetailkind magAuthDetailkind = interDetailKindMapper.getInterDetailKind("主管权限分");
      		CheckUtil.notNull(interDetailExtend,"无主管权限分类别！");
      		String magAuthDetailkindId = magAuthDetailkind.getId();
      		
    		List<IntergralListExtend> magInterListMapper = interIntergralListMapper.getInterListByUserId(newUser.getId());
    		CheckUtil.notNull(magInterListMapper, "未查询到积分列表信息！");
    		if(magInterListMapper != null && magInterListMapper.size() != 0) {
//        		遍历比较同一个用户的积分类别
        		for(IntergralListExtend intergral:magInterListMapper) {
//        			如果原有表里已经有了该类别，则只将分数加到属于该类别的分数里，更新数据库积分列表。
        			if(intergral.getDetailKindId().equals(magAuthDetailkindId)) {
        				magAuthScore += Integer.valueOf(intergral.getAllSc());
        				InterIntergralList interListUpdate = new InterIntergralList();
        				interListUpdate.setAllSc(magAuthScore + "");
        				interListUpdate.setId(intergral.getId());
        				interIntergralListMapper.updateByPrimaryKeySelective(interListUpdate);
        				
        				return newUser.getId();
        			}
        		}
    		}

//    		否则该用户积分信息或新增种类在积分表不存在，则往积分表里插入类别、分数等数据，添加至数据库积分表
    		InterIntergralList magInterList = new InterIntergralList();
      		magInterList.setId(UUIDUtil.getUUID());
      		magInterList.setAllSc(magAuthScore);
      		magInterList.setDetailKindId(magAuthDetailkindId);
      		magInterList.setUserId(newUser.getId());
      		magInterList.setDepId(newUser.getOrgId());
      		magInterList.setCreateTime(new Date());
      		
      		interIntergralListMapper.insertSelective(magInterList);
      	}
      }
        
      
//        添加用户的同时添加学历分，职称分和工龄分，以及计算员工的工龄。工龄分直接加到积分列表。
        String educationQualify = user.getEducationQualify();
        String professionalName = user.getProfessionalName();
        
//        工龄分
//        String workYearsScore = ""; 
//        获取入职年限
        String staffTime = user.getEntryTime();// 2020-01-03
        int oldYear = Integer.valueOf(staffTime.split("-")[0]);
//        获取现在年限
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);//2020
//        System.out.println("入职时间为：" + oldYear +  ',' + "今年是：" + currentYear + "," + "工龄分为：" + ((currentYear - oldYear)*10));
        
//        1.增加学历分和职称分到积分详情表。包括细则类别id、细则id、备注、userId、创建时间。还需要添加到积分列表······
        	  //根据细则内容获取细则id（学历分）
        IntergralDetailExtend interDetailExtend = interDetailMapper.getInterDetailIdByContont(educationQualify);
//        判断学历分是否存在？
        CheckUtil.notNull(interDetailExtend,"学历填写错误！");
//		获取积分加分分数
		String onlyScore = interDetailExtend.getScore(); 
//		转变成整型
		int eduScore = Integer.valueOf(onlyScore.split("/")[0]);
        String eduQuaDetailRuleId = interDetailExtend.getId();
        String detailKind = interDetailExtend.getMark();
        InterDetailkind interDetailkind = interDetailKindMapper.getInterDetailKind(detailKind);
        
        CheckUtil.notNull(interDetailkind,"未查询到积分类别！");
        
        String eduQuaDetailKindId = interDetailkind.getId();
        
        InterDetailList eduQuaInterPartList = new InterDetailList();
        eduQuaInterPartList.setId(UUIDUtil.getUUID());
        eduQuaInterPartList.setDetailRuleId(eduQuaDetailRuleId);
        eduQuaInterPartList.setTypeId(eduQuaDetailKindId);
        eduQuaInterPartList.setNotes("用户注册-学历分！");
        eduQuaInterPartList.setUserId(newUser.getId());
        eduQuaInterPartList.setCreateTime(new Date());
        interDetailListMapper.insertSelective(eduQuaInterPartList);
        
//      将UUID、积分种类、同一个人的同一类别分数和、realName、部门、添加时间（new Date()），同时添加到积分列表
		InterIntergralList eduQuaInterList = new InterIntergralList();
		eduQuaInterList.setId(UUIDUtil.getUUID());
//		1.根据用户id查询积分列表信息
		List<IntergralListExtend> eduQuaInterListMapper = interIntergralListMapper.getInterListByUserId(newUser.getId());
		CheckUtil.notNull(eduQuaInterListMapper,"未查询到积分列表信息！");
//		2.遍历比较同一个用户的积分类别
		for(IntergralListExtend intergral:eduQuaInterListMapper) {
//			3.如果原有表里已经有了该类别，则只将分数加到属于该类别的分数里，更新数据库积分列表。
			if(intergral.getDetailKindId().equals(eduQuaDetailKindId)) {
				eduScore += Integer.valueOf(intergral.getAllSc());
				InterIntergralList interListUpdate = new InterIntergralList();
				interListUpdate.setAllSc(eduScore + "");
				interListUpdate.setId(intergral.getId());
				interIntergralListMapper.updateByPrimaryKeySelective(interListUpdate);
				
//		                    增加工龄分到积分展示列表。包括细则种类id、工龄分、userId、部门id、创建时间。
		        InterDetailkind staffYearInterDetailkind = interDetailKindMapper.getInterDetailKind("工龄分");
		        
		        CheckUtil.notNull(staffYearInterDetailkind,"未查询到积分列表信息！");
		        
		        String workYearDetailKindId = staffYearInterDetailkind.getId();
		        String workYearScore = String.valueOf(((currentYear - oldYear)*10));
		        InterIntergralList workYearInterList = new InterIntergralList();
		        workYearInterList.setId(UUIDUtil.getUUID());
		        workYearInterList.setDetailKindId(workYearDetailKindId);
		        workYearInterList.setAllSc(workYearScore);
		        workYearInterList.setUserId(newUser.getId());
		        workYearInterList.setDepId(newUser.getOrgId());
		        workYearInterList.setCreateTime(new Date());
		        interIntergralListMapper.insertSelective(workYearInterList);
				
				return eduQuaInterList.getId();
			}
		}
//		4.否则新增种类在积分表不存在，则往积分表里插入类别、分数等数据，添加至数据库积分表
		eduQuaInterList.setDetailKindId(eduQuaDetailKindId);
		eduQuaInterList.setAllSc(eduScore + "");
		eduQuaInterList.setUserId(newUser.getId());
		eduQuaInterList.setDepId(newUser.getOrgId());
		eduQuaInterList.setCreateTime(new Date());
		
		interIntergralListMapper.insertSelective(eduQuaInterList);
        
        	//根据细则内容获取细则id（职称分）
        IntergralDetailExtend proInterDetailExtend = interDetailMapper.getInterDetailIdByContont(professionalName);
//      判断职称分是否存在？
        CheckUtil.notNull(proInterDetailExtend,"职称填写错误！");
//		获取积分加分分数
		String proOnlyScore = proInterDetailExtend.getScore(); 
//		转变成整型
		int proScore = Integer.valueOf(proOnlyScore.split("/")[0]);
        String proDetailRuleId = proInterDetailExtend.getId();
        String proDetailKind = proInterDetailExtend.getMark();
        InterDetailkind proInterDetailkind = interDetailKindMapper.getInterDetailKind(proDetailKind);
        
        CheckUtil.notNull(proInterDetailkind,"未获取到积分类别！");
        
        String proDetailKindId = proInterDetailkind.getId();
        InterDetailList proInterPartList = new InterDetailList();
        proInterPartList.setId(UUIDUtil.getUUID());
        proInterPartList.setDetailRuleId(proDetailRuleId);
        proInterPartList.setTypeId(proDetailKindId);
        proInterPartList.setNotes("用户注册-职称分！");
        proInterPartList.setUserId(newUser.getId());
        proInterPartList.setCreateTime(new Date());
        interDetailListMapper.insertSelective(proInterPartList);
        	
        
//      将UUID、积分种类、同一个人的同一类别分数和、realName、部门、添加时间（new Date()），同时添加到积分列表
		InterIntergralList proInterList = new InterIntergralList();
		proInterList.setId(UUIDUtil.getUUID());
//		1.根据用户id查询积分列表信息
		List<IntergralListExtend> proInterListMapper = interIntergralListMapper.getInterListByUserId(newUser.getId());
		CheckUtil.notNull(proInterListMapper,"未获取到积分列表信息！");
//		2.遍历比较同一个用户的积分类别
		for(IntergralListExtend intergral:proInterListMapper) {
//			3.如果原有表里已经有了该类别，则只将分数加到属于该类别的分数里，更新数据库积分列表。
			if(intergral.getDetailKindId().equals(proDetailKindId)) {
				proScore += Integer.valueOf(intergral.getAllSc());
				InterIntergralList interListUpdate = new InterIntergralList();
				interListUpdate.setAllSc(proScore + "");
				interListUpdate.setId(intergral.getId());
				interIntergralListMapper.updateByPrimaryKeySelective(interListUpdate);
				
//		                    增加工龄分到积分展示列表。包括细则种类id、工龄分、userId、部门id、创建时间。
		        InterDetailkind staffYearInterDetailkind = interDetailKindMapper.getInterDetailKind("工龄分");
		        CheckUtil.notNull(staffYearInterDetailkind,"未获取到积分列表信息！");
		        String workYearDetailKindId = staffYearInterDetailkind.getId();
		        String workYearScore = String.valueOf(((currentYear - oldYear)*10));
		        InterIntergralList workYearInterList = new InterIntergralList();
		        workYearInterList.setId(UUIDUtil.getUUID());
		        workYearInterList.setDetailKindId(workYearDetailKindId);
		        workYearInterList.setAllSc(workYearScore);
		        workYearInterList.setUserId(newUser.getId());
		        workYearInterList.setDepId(newUser.getOrgId());
		        workYearInterList.setCreateTime(new Date());
		        interIntergralListMapper.insertSelective(workYearInterList);
				
				return proInterList.getId();
			}
		}
//		4.否则新增种类在积分表不存在，则往积分表里插入类别、分数等数据，添加至数据库积分表
		proInterList.setDetailKindId(proDetailKindId);
		proInterList.setAllSc(proScore + "");
		proInterList.setUserId(newUser.getId());
		proInterList.setDepId(newUser.getOrgId());
		proInterList.setCreateTime(new Date());
		
		interIntergralListMapper.insertSelective(proInterList);
        
        
        return newUser.getId();
    }

    /**
     * 初始化密码
     * @param userId 用户id
     * */
    public String initPwd(String userId){
        String pwd = PasswordUtil.getRandomPwd();
        String sha1Pwd = (new SHA1()).getDigestOfString(pwd);//SHA1加密

        User user = new User();
        user.setId(userId);
        user.setPassword(sha1Pwd);
        dao.updateByPrimaryKeySelective(user);

        return pwd;
    }

    /**
     * 更换密码
     * @param  loginName 登录名
     * @param  newPwd 新密码
     * */
    public Boolean changePwd(String loginName,String oldPwd,String newPwd){
        CheckUtil.notBlank(loginName,"登录名为空");
        CheckUtil.notBlank(oldPwd,"旧密码为空");
        CheckUtil.notBlank(newPwd,"新密码为空");

        User user = dao.getByLoginName(loginName);
        CheckUtil.notNull(user,"该用户不存在");

        int n = dao.countUserByNameAndPwd(loginName,oldPwd);
        CheckUtil.check(n>0,"密码错误");

        user.setPassword(newPwd);
        dao.updateByPrimaryKeySelective(user);

        return true;
    }


    /*************************私有方法区***********************************/
    /**
     * 关联用户角色
     * @param id 用户id
     * @param roleIdList 角色id列表
     * */
    private Boolean addUserRole(String id,List<String> roleIdList){
        CheckUtil.notBlank(id,"用户id为空");

        //先批量删除再添加
        sysUserRoleMapper.deleteByUserId(id);

        if(roleIdList == null||roleIdList.isEmpty()){
            return true;
        }

        List<SysUserRole> userRoleTestList = new Stack<SysUserRole>();
        for(String roleId:roleIdList){
            SysUserRole userRoleTest = new SysUserRole();
            userRoleTest.setId(UUIDUtil.getUUID());
            userRoleTest.setUserId(id);
            userRoleTest.setRoleId(roleId);
            userRoleTestList.add(userRoleTest);
        }

        //批量插入
        sysUserRoleMapper.batchInsert(userRoleTestList);
        return true;
    }

	/**
	 *根据真实姓名获取用户id
	 */
	@Override
	public User getuserIdByRealName(String realName) {
		return dao.getuserIdByRealName(realName);
	}

	@Override
	public List<User> getUsersByDepartment(String department) {
		return dao.getUsersByDepartment(department);
	}
}
