package com.xinyu.simple.configuration.interceptor;

import com.xinyu.simple.common.constant.NoLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 *@Author xinyu
 *@Date 16:34 2019/12/25
 *@Description  登陆校验权限控制类
 **/
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //过滤掉跨域的OPTIONS请求
        if("OPTIONS".equalsIgnoreCase(request.getMethod()))
            return true;

        //过滤掉不需要经过登陆校验的接口
        Class clazz = handler.getClass();
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(clazz.isAnnotationPresent(NoLogin.class) || method.isAnnotationPresent(NoLogin.class))
            return true;

        boolean isLogin = false;
        //进行登陆权限校验
        isLogin = true;
        if(isLogin){//校验通过
            return true;
        }else{
            //抛出权限校验失败的异常
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //Do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
