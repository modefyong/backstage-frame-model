package com.lrhb.service.api.approval;

import java.util.List;

import com.lrhb.dataaccess.model.intergral.InterAccuseAdvice;

public interface AccuseAdviceServcie {

	/**
	 * 投诉建议列表
	 * @param page
	 * @param limit
	 * @return List<InterAccuseAdvice>
	 */
	List<InterAccuseAdvice> getAccuseAdvicePageList(String page, String limit, String loginName, String roleFlag, String checkType);
	
	/**
	 * 投诉建议列表数量
	 * @return Integer
	 */
	Integer getAccuseAdviceListCount(String loginName, String roleFlag, String checkType);

	/**
	 * 根据id获取投诉建议对象
	 * @param id
	 * @return InterAccuseAdvice
	 */
	InterAccuseAdvice getAccuseAdviceById(String id);

	/**
	 * 审批
	 * @param InterAccuseAdvice
	 * @return Integer
	 */
	Integer approvalAccuseAdvice(InterAccuseAdvice interAccuseAdvice);

	/**
	 * 撤销
	 * @param id
	 * @return Integer
	 */
	Integer revokeAccuseAdviceById(String id);
	
}
