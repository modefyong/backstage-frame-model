package com.lrhb.service.impl.approval;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.lrhb.dataaccess.dao.intergral.InterManageAuthorityMapper;
import com.lrhb.dataaccess.dao.system.UserMapper;
import com.lrhb.dataaccess.model.intergral.InterManageAuthority;
import com.lrhb.dataaccess.model.system.User;
import com.lrhb.service.api.approval.ManageAuthorityServcie;
import com.lrhb.service.beans.system.PageRange;

@Service
public class ManageAuthorityServcieImpl implements ManageAuthorityServcie {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	InterManageAuthorityMapper interManageAuthorityMapper;
	
	@Override
	public List<InterManageAuthority> getManageAuthorityPageList(String page, String limit, String loginName, String roleFlag, String checkType) {
		String userId = null;
		String deptId = null;
		if (!StringUtils.isEmpty(loginName)) {
			User user = userMapper.getByLoginName(loginName);
			userId = user.getId();
			deptId = user.getOrgId();
		}
		PageRange pageRange = new PageRange(page, limit);
		//角色为普通用户,只有我发起的
		if ("0".equals(roleFlag)) {
			//我发起的
			if ("2".equals(checkType)) {
				return interManageAuthorityMapper.selectManageAuthorityBySelfPageList(pageRange.getStart(), pageRange.getEnd(), userId);
			} else {
				return null;
			}
			//角色为主管
		} else if ("1".equals(roleFlag)) {
			//获取主管下的所有员工id
			List<String> staffIdList = userMapper.getStaffIdListByDeptId(userId, deptId);
			//待我审批
			if ("0".equals(checkType)) {
				return interManageAuthorityMapper.selectManageAuthorityForSelfPageList(pageRange.getStart(), pageRange.getEnd(), staffIdList);
				//我已审批
			} else if ("1".equals(checkType)) {
				return interManageAuthorityMapper.selectManageAuthorityHaveSelfPageList(pageRange.getStart(), pageRange.getEnd(), staffIdList);
				//我发起的
			} else if ("2".equals(checkType)) {
				return interManageAuthorityMapper.selectManageAuthorityBySelfPageList(pageRange.getStart(), pageRange.getEnd(), userId);
				//抄送我的
			} else if ("3".equals(checkType)) {
				return null;
			} else {
				return null;
			}
			//角色为管理员
		} else {
			//待我审批
			if ("0".equals(checkType)) {
				return interManageAuthorityMapper.selectManageAuthorityForPageList(pageRange.getStart(), pageRange.getEnd());
				//我已审批
			} else if ("1".equals(checkType)) {
				return interManageAuthorityMapper.selectManageAuthorityHavePageList(pageRange.getStart(), pageRange.getEnd());
				//我发起的
			} else if ("2".equals(checkType)) {
				return null;
				//抄送我的
			} else if ("3".equals(checkType)) {
				return null;
			} else {
				return null;
			}
		}
	}

	@Override
	public Integer getManageAuthorityListCount(String loginName, String roleFlag, String checkType) {
		String userId = null;
		String deptId = null;
		if (!StringUtils.isEmpty(loginName)) {
			User user = userMapper.getByLoginName(loginName);
			userId = user.getId();
			deptId = user.getOrgId();
		}
		//角色为普通用户,只有我发起的
		if ("0".equals(roleFlag)) {
			//我发起的
			if ("2".equals(checkType)) {
				return interManageAuthorityMapper.selectManageAuthorityBySelfListCount(userId);
			} else {
				return null;
			}
			//角色为主管
		} else if ("1".equals(roleFlag)) {
			//获取主管下的所有员工id
			List<String> staffIdList = userMapper.getStaffIdListByDeptId(userId, deptId);
			//待我审批
			if ("0".equals(checkType)) {
				return interManageAuthorityMapper.selectManageAuthorityForSelfListCount(staffIdList);
				//我已审批
			} else if ("1".equals(checkType)) {
				return interManageAuthorityMapper.selectManageAuthorityHaveSelfListCount(staffIdList);
				//我发起的
			} else if ("2".equals(checkType)) {
				return interManageAuthorityMapper.selectManageAuthorityBySelfListCount(userId);
				//抄送我的
			} else if ("3".equals(checkType)) {
				return null;
			} else {
				return null;
			}
			//角色为管理员
		} else {
			//待我审批
			if ("0".equals(checkType)) {
				return interManageAuthorityMapper.selectManageAuthorityForListCount();
				//我已审批
			} else if ("1".equals(checkType)) {
				return interManageAuthorityMapper.selectManageAuthorityHaveListCount();
				//我发起的
			} else if ("2".equals(checkType)) {
				return null;
				//抄送我的
			} else if ("3".equals(checkType)) {
				return null;
			} else {
				return null;
			}
		}
	}

	@Override
	public InterManageAuthority getManageAuthorityById(String id) {
		return interManageAuthorityMapper.selectManageAuthorityById(id);
	}

	@Override
	public Integer approvalManageAuthority(InterManageAuthority interManageAuthority) {
		return interManageAuthorityMapper.updateByPrimaryKeySelective(interManageAuthority);
	}

	@Override
	public Integer revokeManageAuthorityById(String id) {
		InterManageAuthority interManageAuthority = interManageAuthorityMapper.selectByPrimaryKey(id);
		interManageAuthority.setCheckState("3");
		return interManageAuthorityMapper.updateByPrimaryKeySelective(interManageAuthority);
	}

}