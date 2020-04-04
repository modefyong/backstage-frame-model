package com.lrhb.controllers.approval;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lrhb.dataaccess.model.intergral.InterPersonApply;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.beans.response.PageResultBean;
import com.lrhb.mgrframework.beans.response.ResultBean;
import com.lrhb.service.api.approval.PersonApplyServcie;

import lombok.extern.slf4j.Slf4j;

/**
 * @classname: PersonApplyController
 * @description: 自主申请
 * @author: mzy
 * @date: 2020年1月4日 下午3:52:28
 */

@Slf4j
@RestController
@RequestMapping("/approval")
public class PersonApplyController {
	
	@Autowired
	PersonApplyServcie personApplyServcie;
	
	/**
	 * 自主申请列表
	 * @param page
	 * @param limit
	 * @return Collection<InterPersonApply>
	 */
	@RequestMapping(value = "/getPersonApplyList.do", method = RequestMethod.POST)
	public IResult getPersonApplyList(String page, String limit, String loginName, String roleFlag, String checkType) {
		return new PageResultBean<Collection<InterPersonApply>>(
				personApplyServcie.getPersonApplyPageList(page, limit, loginName, roleFlag, checkType),
				personApplyServcie.getPersonApplyListCount(loginName, roleFlag, checkType));
	}
	
	/**
	 * 自主申请
	 * @param id
	 * @return InterPersonApply
	 */
	@RequestMapping(value = "/getPersonApply.do", method = RequestMethod.GET)
	public IResult getPersonApply(String id) {
		return new ResultBean<InterPersonApply>(personApplyServcie.getPersonApplyById(id));
	}
	
	/**
	 * 审批
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/approvalPersonApply.do", method = RequestMethod.POST)
	public IResult approvalPersonApply(@RequestBody InterPersonApply interPersonApply) {
		return new ResultBean<Integer>(personApplyServcie.approvalPersonApply(interPersonApply));
	}
	
	/**
	 * 撤销
	 * @param id
	 * @return Integer
	 */
	@RequestMapping(value = "/revokePersonApply.do", method = RequestMethod.POST)
	public IResult revokePersonApply(String id) {
		return new ResultBean<Integer>(personApplyServcie.revokePersonApplyById(id));
	}
	
}