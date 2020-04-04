package com.lrhb.service.impl.approval;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.lrhb.dataaccess.dao.intergral.InterAccuseAdviceMapper;
import com.lrhb.dataaccess.dao.system.UserMapper;
import com.lrhb.dataaccess.model.intergral.InterAccuseAdvice;
import com.lrhb.dataaccess.model.system.User;
import com.lrhb.service.api.approval.AccuseAdviceServcie;
import com.lrhb.service.beans.system.PageRange;

@Service
public class AccuseAdviceServcieImpl implements AccuseAdviceServcie {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	InterAccuseAdviceMapper interAccuseAdviceMapper;
	
	@Override
	public List<InterAccuseAdvice> getAccuseAdvicePageList(String page, String limit, String loginName, String roleFlag, String checkType) {
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
				return interAccuseAdviceMapper.selectAccuseAdviceBySelfPageList(pageRange.getStart(), pageRange.getEnd(), userId);
			} else {
				return null;
			}
			//角色为主管
		} else if ("1".equals(roleFlag)) {
			//获取主管下的所有员工id
			List<String> staffIdList = userMapper.getStaffIdListByDeptId(userId, deptId);
			//待我审批
			if ("0".equals(checkType)) {
				return interAccuseAdviceMapper.selectAccuseAdviceForSelfPageList(pageRange.getStart(), pageRange.getEnd(), staffIdList);
				//我已审批
			} else if ("1".equals(checkType)) {
				return interAccuseAdviceMapper.selectAccuseAdviceHaveSelfPageList(pageRange.getStart(), pageRange.getEnd(), staffIdList);
				//我发起的
			} else if ("2".equals(checkType)) {
				return interAccuseAdviceMapper.selectAccuseAdviceBySelfPageList(pageRange.getStart(), pageRange.getEnd(), userId);
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
				return interAccuseAdviceMapper.selectAccuseAdviceForPageList(pageRange.getStart(), pageRange.getEnd());
				//我已审批
			} else if ("1".equals(checkType)) {
				return interAccuseAdviceMapper.selectAccuseAdviceHavePageList(pageRange.getStart(), pageRange.getEnd());
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
	public Integer getAccuseAdviceListCount(String loginName, String roleFlag, String checkType) {
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
				return interAccuseAdviceMapper.selectAccuseAdviceBySelfListCount(userId);
			} else {
				return null;
			}
			//角色为主管
		} else if ("1".equals(roleFlag)) {
			//获取主管下的所有员工id
			List<String> staffIdList = userMapper.getStaffIdListByDeptId(userId, deptId);
			//待我审批
			if ("0".equals(checkType)) {
				return interAccuseAdviceMapper.selectAccuseAdviceForSelfListCount(staffIdList);
				//我已审批
			} else if ("1".equals(checkType)) {
				return interAccuseAdviceMapper.selectAccuseAdviceHaveSelfListCount(staffIdList);
				//我发起的
			} else if ("2".equals(checkType)) {
				return interAccuseAdviceMapper.selectAccuseAdviceBySelfListCount(userId);
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
				return interAccuseAdviceMapper.selectAccuseAdviceForListCount();
				//我已审批
			} else if ("1".equals(checkType)) {
				return interAccuseAdviceMapper.selectAccuseAdviceHaveListCount();
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
	public InterAccuseAdvice getAccuseAdviceById(String id) {
		return interAccuseAdviceMapper.selectAccuseAdviceById(id);
	}

	@Override
	public Integer approvalAccuseAdvice(InterAccuseAdvice interAccuseAdvice) {
		return interAccuseAdviceMapper.updateByPrimaryKeySelective(interAccuseAdvice);
	}

	@Override
	public Integer revokeAccuseAdviceById(String id) {
		InterAccuseAdvice interAccuseAdvice = interAccuseAdviceMapper.selectByPrimaryKey(id);
		interAccuseAdvice.setCheckState("3");
		return interAccuseAdviceMapper.updateByPrimaryKeySelective(interAccuseAdvice);
	}

}