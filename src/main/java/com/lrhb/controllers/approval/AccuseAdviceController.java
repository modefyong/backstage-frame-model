package com.lrhb.controllers.approval;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lrhb.dataaccess.model.intergral.InterAccuseAdvice;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.beans.response.PageResultBean;
import com.lrhb.mgrframework.beans.response.ResultBean;
import com.lrhb.service.api.approval.AccuseAdviceServcie;

import lombok.extern.slf4j.Slf4j;

/**
 * @classname: AccuseAdviceController
 * @description: 投诉建议
 * @author: mzy
 * @date: 2020年1月4日 下午3:52:28
 */

@Slf4j
@RestController
@RequestMapping("/approval")
public class AccuseAdviceController {
	
	@Autowired
	AccuseAdviceServcie accuseAdviceServcie;
	
	/**
	 * 投诉建议列表
	 * @param page
	 * @param limit
	 * @return Collection<InterAccuseAdvice>
	 */
	@RequestMapping(value = "/getAccuseAdviceList.do", method = RequestMethod.POST)
	public IResult getAccuseAdviceList(String page, String limit, String loginName, String roleFlag, String checkType) {
		return new PageResultBean<Collection<InterAccuseAdvice>>(
				accuseAdviceServcie.getAccuseAdvicePageList(page, limit, loginName, roleFlag, checkType),
				accuseAdviceServcie.getAccuseAdviceListCount(loginName, roleFlag, checkType));
	}
	
	/**
	 * 投诉建议
	 * @param id
	 * @return InterAccuseAdvice
	 */
	@RequestMapping(value = "/getAccuseAdvice.do", method = RequestMethod.GET)
	public IResult getAccuseAdvice(String id) {
		return new ResultBean<InterAccuseAdvice>(accuseAdviceServcie.getAccuseAdviceById(id));
	}
	
	/**
	 * 审批
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/approvalAccuseAdvice.do", method = RequestMethod.POST)
	public IResult approvalAccuseAdvice(@RequestBody InterAccuseAdvice interAccuseAdvice) {
		return new ResultBean<Integer>(accuseAdviceServcie.approvalAccuseAdvice(interAccuseAdvice));
	}
	
	/**
	 * 撤销
	 * @param id
	 * @return Integer
	 */
	@RequestMapping(value = "/revokeAccuseAdvice.do", method = RequestMethod.POST)
	public IResult revokeAccuseAdvice(String id) {
		return new ResultBean<Integer>(accuseAdviceServcie.revokeAccuseAdviceById(id));
	}
	
}