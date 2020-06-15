package com.lrhb.mgrframework.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.lrhb.mgrframework.utils.UserInfoUtil;

/**
 * 用户信息filter
 */
public class UserFilter implements Filter {


    public void init(FilterConfig filterConfig) throws ServletException {}

    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        fillUserInfo((HttpServletRequest) request);

        chain.doFilter(request, response);

        clearAllUserInfo();//必须清理，否则tomcat会重用，导致保留的是上次的用户信息
    }

    /**
     * 清空用户信息
     * */
    private void clearAllUserInfo() {
        UserInfoUtil.clearAllUserInfo();
    }

    /**
     * 保存用户信息
     * */
    private void fillUserInfo(HttpServletRequest request) {
        // 用户信息
        Object user = getUserFromSession(request);

        if (user != null) {
            UserInfoUtil.setUser(user);
        }
    }

    /**
     * 从SESSION获取用户信息
     * */
    private Object getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        if (session == null) {
            return null;
        }

        // 从session中获取用户信息放到thread local中
        return session.getAttribute(UserInfoUtil.KEY_USER);
    }

}