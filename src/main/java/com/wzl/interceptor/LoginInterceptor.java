package com.wzl.interceptor;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wang
 * @version 1.0
 * 登录过滤器
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    //没登陆之前你进行拦截
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if(request.getSession().getAttribute("user")==null||(request.getSession().getAttribute("quanxian")!="1")){
//            重定向到登录页面
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
