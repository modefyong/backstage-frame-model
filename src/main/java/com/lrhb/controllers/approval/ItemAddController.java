package com.lrhb.controllers.approval;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lrhb.dataaccess.model.intergral.InterItemAdd;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.beans.response.PageResultBean;
import com.lrhb.mgrframework.beans.response.ResultBean;
import com.lrhb.service.api.approval.ItemAddServcie;

import lombok.extern.slf4j.Slf4j;

/**
 * @classname: ItemAddController
 * @description: 项目增加项
 * @author: mzy
 * @date: 2020年1月4日 下午3:52:28
 */

@Slf4j
@RestController
@RequestMapping("/approval")
public class ItemAddController {
	
	@Autowired
	ItemAddServcie itemAddServcie;
	
	/**
	 * 项目增加项列表
	 * @param page
	 * @param limit
	 * @return Collection<InterItemAdd>
	 */
	@RequestMapping(value = "/getItemAddList.do", method = RequestMethod.POST)
	public IResult getItemAddList(String page, String limit, String loginName, String roleFlag, String checkType) {
		return new PageResultBean<Collection<InterItemAdd>>(
				itemAddServcie.getItemAddPageList(page, limit, loginName, roleFlag, checkType),
				itemAddServcie.getItemAddListCount(loginName, roleFlag, checkType));
	}
	
	/**
	 * 项目增加项
	 * @param id
	 * @return InterItemAdd
	 */
	@RequestMapping(value = "/getItemAdd.do", method = RequestMethod.GET)
	public IResult getItemAdd(String id) {
		return new ResultBean<InterItemAdd>(itemAddServcie.getItemAddById(id));
	}
	
	/**
	 * 审批
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/approvalItemAdd.do", method = RequestMethod.POST)
	public IResult approvalItemAdd(@RequestBody InterItemAdd interItemAdd) {
		return new ResultBean<Integer>(itemAddServcie.approvalItemAdd(interItemAdd));
	}
	
	/**
	 * 撤销
	 * @param id
	 * @return Integer
	 */
	@RequestMapping(value = "/revokeItemAdd.do", method = RequestMethod.POST)
	public IResult revokeItemAdd(String id) {
		return new ResultBean<Integer>(itemAddServcie.revokeItemAddById(id));
	}
	
}