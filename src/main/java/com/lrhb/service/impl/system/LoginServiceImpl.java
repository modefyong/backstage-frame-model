package com.lrhb.service.impl.system;

import static com.lrhb.mgrframework.utils.CheckUtil.notBlank;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lrhb.dataaccess.dao.intergral.InterDetailMapper;
import com.lrhb.dataaccess.dao.intergral.InterIntergralListMapper;
import com.lrhb.dataaccess.dao.system.SysUserRoleMapper;
import com.lrhb.dataaccess.dao.system.UserMapper;
import com.lrhb.dataaccess.model.intergral.InterIntergralList;
import com.lrhb.dataaccess.model.system.User;
import com.lrhb.mgrframework.beans.response.AbstractResult;
import com.lrhb.mgrframework.exceptions.BizException;
import com.lrhb.service.api.system.LoginService;
import com.lrhb.service.api.system.SysUserService;
import com.lrhb.service.beans.intergral.IntergralDetailExtend;
import com.lrhb.service.beans.system.LoginResponse;
import com.lrhb.utils.Constants;
import com.lrhb.utils.SHA1;
import com.lrhb.utils.UUIDUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录服务
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    UserMapper userMapper;
    
    @Autowired
    private SysUserService userServiceImpl;
    
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    
    /**
     * 登录
     * @param loginName 登录名
     * @param pwd 密码
     * */
    public LoginResponse login(String loginName, String pwd, String code){
        LoginResponse loginResponse = new LoginResponse();
        //参数校验
//        notBlank(code,"验证码为空");
        notBlank(loginName,"用户名为空");
        notBlank(pwd,"密码为空");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //验证验证码
//        String vcode =  request.getSession().getAttribute("vcode").toString();
//        check(code.equalsIgnoreCase(vcode),"验证码错误");

        //当前用户
        Subject currentUser = SecurityUtils.getSubject();
        
        //获取基于用户名和密码的令牌(生产环境下密码需要转换为加密后的密码)
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, pwd);
        try {
            //提交认证，会调用Realm的doGetAuthenticationInfo，进行认证
            currentUser.login(token);
        } catch(UnknownAccountException e){
            log.info("用户不存在");
            throw new BizException(AbstractResult.BIZ_FAIL,"用户不存在");
        }catch(IncorrectCredentialsException e){
            log.info("用户名或密码错误");
            throw new BizException(AbstractResult.BIZ_FAIL,"用户名或密码错误");

        }catch (AuthenticationException e) {
            log.error("认证异常",e);
            throw new BizException(AbstractResult.BIZ_FAIL,"认证异常");
        }

        //验证是否通过
        if(currentUser.isAuthenticated()){
            log.info("验证成功！");
            //还可以把用户信息放入session中
            request.getSession().setAttribute("sysUser",loginName);

            //拼接返回信息
            User userTest = userMapper.getByLoginName(loginName);
            loginResponse.setUserId(userTest.getId());
            loginResponse.setLoginName(loginName);
            String baseUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
            loginResponse.setWebContext(baseUrl);
            loginResponse.setRoles(userServiceImpl.getRoleNames(userTest.getId()));
            return loginResponse;
        }

        log.info("用户名或密码错误,name:{},pwd:{}",loginName,pwd);
        throw new BizException(AbstractResult.BIZ_FAIL,"用户名或密码错误");
    }

    /**
     * 登出并清理session
     * */
    public String logout(){
        SecurityUtils.getSubject().logout();//登出
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().removeAttribute("sysUser");//清理session
        return null;
    }

	@Override
	public LoginResponse loginReact(String username, String password) {
		notBlank(username,"用户名为空");
        notBlank(password,"密码为空");
        
        User userTest = userMapper.getByLoginName(username);
        if(userTest == null) {
        	LoginResponse response = new LoginResponse();
        	response.setMessage("用户名或密码不正确！");
        	return response;
        }
        String newPassword =  (new SHA1()).getDigestOfString(password);
        if(userTest.getPassword().equals(newPassword)) {
        	LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserId(userTest.getId());
            loginResponse.setUsername(username);
            loginResponse.setPassword(newPassword);
            loginResponse.setRealName(userTest.getRealName());
            return loginResponse;
        }else {
        	LoginResponse response1 = new LoginResponse();
        	response1.setMessage("用户名或密码不正确！");
        	return response1;
        }
        
	}
}
