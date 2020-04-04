package com.lrhb.service.api.approval;

import java.util.List;

import com.lrhb.dataaccess.model.intergral.InterItemAdd;

public interface ItemAddServcie {

	/**
	 * 项目增加项列表
	 * @param page
	 * @param limit
	 * @return List<InterItemAdd>
	 */
	List<InterItemAdd> getItemAddPageList(String page, String limit, String loginName, String roleFlag, String checkType);
	
	/**
	 * 项目增加项列表数量
	 * @return Integer
	 */
	Integer getItemAddListCount(String loginName, String roleFlag, String checkType);

	/**
	 * 根据id获取项目增加项对象
	 * @param id
	 * @return InterItemAdd
	 */
	InterItemAdd getItemAddById(String id);

	/**
	 * 审批
	 * @param InterItemAdd
	 * @return Integer
	 */
	Integer approvalItemAdd(InterItemAdd interItemAdd);

	/**
	 * 撤销
	 * @param id
	 * @return Integer
	 */
	Integer revokeItemAddById(String id);
	
}
