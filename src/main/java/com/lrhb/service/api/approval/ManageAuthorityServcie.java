package com.lrhb.service.api.approval;

import java.util.List;

import com.lrhb.dataaccess.model.intergral.InterManageAuthority;

public interface ManageAuthorityServcie {

	/**
	 * 主管权限分列表
	 * @param page
	 * @param limit
	 * @return List<InterManageAuthority>
	 */
	List<InterManageAuthority> getManageAuthorityPageList(String page, String limit, String loginName, String roleFlag, String checkType);
	
	/**
	 * 主管权限分列表数量
	 * @return Integer
	 */
	Integer getManageAuthorityListCount(String loginName, String roleFlag, String checkType);

	/**
	 * 根据id获取主管权限分对象
	 * @param id
	 * @return InterItemAdd
	 */
	InterManageAuthority getManageAuthorityById(String id);

	/**
	 * 审批
	 * @param InterItemAdd
	 * @return Integer
	 */
	Integer approvalManageAuthority(InterManageAuthority interManageAuthority);

	/**
	 * 撤销
	 * @param id
	 * @return Integer
	 */
	Integer revokeManageAuthorityById(String id);
	
}
