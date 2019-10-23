package com.grbk.blog.Interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //用户未登录，跳转到登录页面
        if(request.getSession().getAttribute("user")==null){
            response.sendRedirect("/admin");
            return false;   //不继续往下执行
        }
        //继续往下执行
        return true;
    }
}
