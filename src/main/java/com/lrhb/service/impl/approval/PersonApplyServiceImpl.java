package com.lrhb.service.impl.approval;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.lrhb.dataaccess.dao.intergral.InterPersonApplyMapper;
import com.lrhb.dataaccess.dao.system.UserMapper;
import com.lrhb.dataaccess.model.intergral.InterPersonApply;
import com.lrhb.dataaccess.model.system.User;
import com.lrhb.service.api.approval.PersonApplyServcie;
import com.lrhb.service.beans.system.PageRange;

@Service
public class PersonApplyServiceImpl implements PersonApplyServcie {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	InterPersonApplyMapper interPersonApplyMapper;
	
	@Override
	public List<InterPersonApply> getPersonApplyPageList(String page, String limit, String loginName, String roleFlag, String checkType) {
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
				return interPersonApplyMapper.selectPersonApplyBySelfPageList(pageRange.getStart(), pageRange.getEnd(), userId);
			} else {
				return null;
			}
			//角色为主管
		} else if ("1".equals(roleFlag)) {
			//获取主管下的所有员工id
			List<String> staffIdList = userMapper.getStaffIdListByDeptId(userId, deptId);
			//待我审批
			if ("0".equals(checkType)) {
				return interPersonApplyMapper.selectPersonApplyForSelfPageList(pageRange.getStart(), pageRange.getEnd(), staffIdList);
				//我已审批
			} else if ("1".equals(checkType)) {
				return interPersonApplyMapper.selectPersonApplyHaveSelfPageList(pageRange.getStart(), pageRange.getEnd(), staffIdList);
				//我发起的
			} else if ("2".equals(checkType)) {
				return interPersonApplyMapper.selectPersonApplyBySelfPageList(pageRange.getStart(), pageRange.getEnd(), userId);
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
				return interPersonApplyMapper.selectPersonApplyForPageList(pageRange.getStart(), pageRange.getEnd());
				//我已审批
			} else if ("1".equals(checkType)) {
				return interPersonApplyMapper.selectPersonApplyHavePageList(pageRange.getStart(), pageRange.getEnd());
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
	public Integer getPersonApplyListCount(String loginName, String roleFlag, String checkType) {
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
				return interPersonApplyMapper.selectPersonApplyBySelfListCount(userId);
			} else {
				return null;
			}
			//角色为主管
		} else if ("1".equals(roleFlag)) {
			//获取主管下的所有员工id
			List<String> staffIdList = userMapper.getStaffIdListByDeptId(userId, deptId);
			//待我审批
			if ("0".equals(checkType)) {
				return interPersonApplyMapper.selectPersonApplyForSelfListCount(staffIdList);
				//我已审批
			} else if ("1".equals(checkType)) {
				return interPersonApplyMapper.selectPersonApplyHaveSelfListCount(staffIdList);
				//我发起的
			} else if ("2".equals(checkType)) {
				return interPersonApplyMapper.selectPersonApplyBySelfListCount(userId);
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
				return interPersonApplyMapper.selectPersonApplyForListCount();
				//我已审批
			} else if ("1".equals(checkType)) {
				return interPersonApplyMapper.selectPersonApplyHaveListCount();
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
	public InterPersonApply getPersonApplyById(String id) {
		return interPersonApplyMapper.selectPersonApplyById(id);
	}

	@Override
	public Integer approvalPersonApply(InterPersonApply interPersonApply) {
		interPersonApply.setCheckTime(new Date());
		return interPersonApplyMapper.updateByPrimaryKeySelective(interPersonApply);
	}

	@Override
	public Integer revokePersonApplyById(String id) {
		InterPersonApply interPersonApply = interPersonApplyMapper.selectByPrimaryKey(id);
		interPersonApply.setCheckState("3");
		return interPersonApplyMapper.updateByPrimaryKeySelective(interPersonApply);
	}

}