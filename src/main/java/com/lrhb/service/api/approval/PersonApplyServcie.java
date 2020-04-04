package com.lrhb.service.api.approval;

import java.util.List;

import com.lrhb.dataaccess.model.intergral.InterPersonApply;

public interface PersonApplyServcie {

	/**
	 * 自主申请待我审批列表
	 * @param page
	 * @param limit
	 * @return List<InterPersonApply>
	 */
	List<InterPersonApply> getPersonApplyPageList(String page, String limit, String loginName, String roleFlag, String checkType);
	
	/**
	 * 自主申请待我审批数量
	 * @return Integer
	 */
	Integer getPersonApplyListCount(String loginName, String roleFlag, String checkType);

	/**
	 * 根据id获取自主申请审批对象
	 * @param id
	 * @return InterPersonApply
	 */
	InterPersonApply getPersonApplyById(String id);

	/**
	 * 审批
	 * @param interPersonApply
	 * @return Integer
	 */
	Integer approvalPersonApply(InterPersonApply interPersonApply);
	
	/**
	 * 撤销
	 * @param id
	 * @return Integer
	 */
	Integer revokePersonApplyById(String id);
	
}
