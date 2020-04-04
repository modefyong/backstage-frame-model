package com.lrhb.controllers.approval;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lrhb.dataaccess.model.intergral.InterManageAuthority;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.beans.response.PageResultBean;
import com.lrhb.mgrframework.beans.response.ResultBean;
import com.lrhb.service.api.approval.ManageAuthorityServcie;

import lombok.extern.slf4j.Slf4j;

/**
 * @classname: ManageAuthorityController
 * @description: 主管权限分
 * @author: mzy
 * @date: 2020年1月4日 下午3:52:28
 */

@Slf4j
@RestController
@RequestMapping("/approval")
public class ManageAuthorityController {
	
	@Autowired
	ManageAuthorityServcie manageAuthorityServcie;
	
	/**
	 * 主管权限分列表
	 * @param page
	 * @param limit
	 * @return Collection<InterManageAuthority>
	 */
	@RequestMapping(value = "/getManageAuthorityList.do", method = RequestMethod.POST)
	public IResult getManageAuthorityList(String page, String limit, String loginName, String roleFlag, String checkType) {
		return new PageResultBean<Collection<InterManageAuthority>>(
				manageAuthorityServcie.getManageAuthorityPageList(page, limit, loginName, roleFlag, checkType),
				manageAuthorityServcie.getManageAuthorityListCount(loginName, roleFlag, checkType));
	}
	
	/**
	 * 主管权限分
	 * @param id
	 * @return InterManageAuthority
	 */
	@RequestMapping(value = "/getManageAuthority.do", method = RequestMethod.GET)
	public IResult getManageAuthority(String id) {
		return new ResultBean<InterManageAuthority>(manageAuthorityServcie.getManageAuthorityById(id));
	}
	
	/**
	 * 审批
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/approvalManageAuthority.do", method = RequestMethod.POST)
	public IResult approvalManageAuthority(@RequestBody InterManageAuthority interManageAuthority) {
		return new ResultBean<Integer>(manageAuthorityServcie.approvalManageAuthority(interManageAuthority));
	}
	
	/**
	 * 撤销
	 * @param id
	 * @return Integer
	 */
	@RequestMapping(value = "/revokeManageAuthority.do", method = RequestMethod.POST)
	public IResult revokeManageAuthority(String id) {
		return new ResultBean<Integer>(manageAuthorityServcie.revokeManageAuthorityById(id));
	}
	
}