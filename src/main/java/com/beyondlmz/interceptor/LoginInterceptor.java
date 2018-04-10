package com.beyondlmz.interceptor;

import com.beyondlmz.controller.IndexController;
import com.beyondlmz.controller.UserController;
import com.beyondlmz.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liumingzhong on 2017/11/27.
 */
public class LoginInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info(">>>LoginInterceptor>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
        String path =  request.getContextPath();

        request.getSession().setAttribute("openId","oYkFlw8XprAQRTWS9vDVbaDQYc0Q");
        request.setAttribute("base",path);
        HandlerMethod hm=(HandlerMethod)handler;
        Object classes = hm.getBean();
        Package pck = classes.getClass().getPackage();
        String url = request.getRequestURI();
        SysUser user = (SysUser)request.getSession().getAttribute("user");
        if(classes instanceof UserController || classes instanceof IndexController ) {
            if ((null == user || user.getAccount() == null)) {
                if (url.contains("index.action") || url.contains("register.action")
                        || url.contains("mycenter.action")
                        || url.contains("shopcart.action")
                        || url.contains("productList.action")
                        || url.contains("login.action")
                        || url.contains("classify.action")
                        ) {
                    response.sendRedirect("/login.action");
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                          ModelAndView modelAndView) throws Exception {
        logger.info(">>>LoginInterceptor>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.info(">>>LoginInterceptor>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }

}