package com.lrhb.service.impl.approval;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.lrhb.dataaccess.dao.intergral.InterItemAddMapper;
import com.lrhb.dataaccess.dao.system.UserMapper;
import com.lrhb.dataaccess.model.intergral.InterItemAdd;
import com.lrhb.dataaccess.model.system.User;
import com.lrhb.service.api.approval.ItemAddServcie;
import com.lrhb.service.beans.system.PageRange;

@Service
public class ItemAddServcieImpl implements ItemAddServcie {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	InterItemAddMapper interItemAddMapper;
	
	@Override
	public List<InterItemAdd> getItemAddPageList(String page, String limit, String loginName, String roleFlag, String checkType) {
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
				return interItemAddMapper.selectItemAddBySelfPageList(pageRange.getStart(), pageRange.getEnd(), userId);
			} else {
				return null;
			}
			//角色为主管
		} else if ("1".equals(roleFlag)) {
			//获取主管下的所有员工id
			List<String> staffIdList = userMapper.getStaffIdListByDeptId(userId, deptId);
			//待我审批
			if ("0".equals(checkType)) {
				return interItemAddMapper.selectItemAddForSelfPageList(pageRange.getStart(), pageRange.getEnd(), staffIdList);
				//我已审批
			} else if ("1".equals(checkType)) {
				return interItemAddMapper.selectItemAddHaveSelfPageList(pageRange.getStart(), pageRange.getEnd(), staffIdList);
				//我发起的
			} else if ("2".equals(checkType)) {
				return interItemAddMapper.selectItemAddBySelfPageList(pageRange.getStart(), pageRange.getEnd(), userId);
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
				return interItemAddMapper.selectItemAddForPageList(pageRange.getStart(), pageRange.getEnd());
				//我已审批
			} else if ("1".equals(checkType)) {
				return interItemAddMapper.selectItemAddHavePageList(pageRange.getStart(), pageRange.getEnd());
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
	public Integer getItemAddListCount(String loginName, String roleFlag, String checkType) {
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
				return interItemAddMapper.selectItemAddBySelfListCount(userId);
			} else {
				return null;
			}
			//角色为主管
		} else if ("1".equals(roleFlag)) {
			//获取主管下的所有员工id
			List<String> staffIdList = userMapper.getStaffIdListByDeptId(userId, deptId);
			//待我审批
			if ("0".equals(checkType)) {
				return interItemAddMapper.selectItemAddForSelfListCount(staffIdList);
				//我已审批
			} else if ("1".equals(checkType)) {
				return interItemAddMapper.selectItemAddHaveSelfListCount(staffIdList);
				//我发起的
			} else if ("2".equals(checkType)) {
				return interItemAddMapper.selectItemAddBySelfListCount(userId);
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
				return interItemAddMapper.selectItemAddForListCount();
				//我已审批
			} else if ("1".equals(checkType)) {
				return interItemAddMapper.selectItemAddHaveListCount();
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
	public InterItemAdd getItemAddById(String id) {
		return interItemAddMapper.selectItemAddById(id);
	}

	@Override
	public Integer approvalItemAdd(InterItemAdd interItemAdd) {
		interItemAdd.setCheckTime(new Date());
		return interItemAddMapper.updateByPrimaryKeySelective(interItemAdd);
	}

	@Override
	public Integer revokeItemAddById(String id) {
		InterItemAdd interItemAdd = interItemAddMapper.selectByPrimaryKey(id);
		interItemAdd.setCheckState("3");
		return interItemAddMapper.updateByPrimaryKeySelective(interItemAdd);
	}

}