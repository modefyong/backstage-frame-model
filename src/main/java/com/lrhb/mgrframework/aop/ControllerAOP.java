package com.lrhb.mgrframework.aop;

import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.lrhb.dataaccess.model.system.User;
import com.lrhb.mgrframework.annotation.BizOperLog;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.utils.CheckUtil;
import com.lrhb.mgrframework.utils.IPUtil;
import com.lrhb.service.api.system.SysOperLogService;
import lombok.extern.slf4j.Slf4j;



/**
 * 处理统一返回、日志切面、参数校验
 */
@Slf4j
@Aspect
@Component
public class ControllerAOP {

	@Autowired
	private SysOperLogService sysOperLogServiceImpl;

	/**
	 * 创建返回切点,这里只切返回IResult的方法，没有切返回String和void的方法。
	 * */
	@Pointcut("execution(public com.lrhb.mgrframework.beans.response.IResult *(..))")
	public void resultPtCut() {}

	/**
	 * 创建日志切点
	 * */
	@Pointcut("@annotation(com.lrhb.mgrframework.annotation.BizOperLog)")
	public void operLogCut() {}

	/**
	 * 环绕通知处理
	 * */
	@Around("resultPtCut()")
	public Object handlerControllerMethods(ProceedingJoinPoint pjp) throws Throwable{
		long startTime = System.currentTimeMillis();
		log.info(pjp.getSignature()+"请求参数:{}",pjp.getArgs());
		IResult result;//业务返回结果

		CheckUtil.checkModel(pjp);//校验model上面的参数

		result = (IResult) pjp.proceed();//业务处理

		log.info("返回结果:{}",result);
		log.info(pjp.getSignature() + "处理耗费时间:" + (System.currentTimeMillis() - startTime)+"ms");
		return result;
	}

	/**
	 * 应用日志存储
	 * */
	@After("operLogCut() && @annotation(operLog)")
	public void logAdvisor(BizOperLog operLog){
		log.info("进入操作日志切面");
		// 添加记录日志
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		User user = (User)SecurityUtils.getSubject().getPrincipal();
		String userid = user.getId();// 操作员ID
		String loginName = user.getLoginName();
		String ipAddr = IPUtil.getIpAddr(request);// 访问段ip

		//从注解中获取操作类型和备注
		String opertype =  operLog.operType().getValue();
		String memo = operLog.memo();
		sysOperLogServiceImpl.insertOperLog(userid,loginName,ipAddr,opertype,memo);
		log.info("记录操作日志成功");
	}

}
