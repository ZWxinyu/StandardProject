package com.xinyu.simple.configuration.interceptor;

import com.xinyu.simple.common.constant.WebConstants;
import com.xinyu.simple.common.util.InetAddressHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

@Component
public class HttpLogInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(HttpLogInterceptor.class);
    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            startTime.set(System.currentTimeMillis());
            String requestInfo = "----------->clientip#{0} gcid#{1} {2} {3} request for [{4}] processing";
            String url = StringUtils.substringAfter(request.getRequestURL().toString(), WebConstants.API_PREFIX);
            String message = MessageFormat.format(requestInfo, InetAddressHelper.getIpAddrByRequest(request),
                    "",request.getMethod(),request.getContentType(),url);

            logger.info(message);
        }catch (Exception e){
            logger.info("HttpLogInterceptor error");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //Do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String responseInfo = "----------->clientip#{0} gcid#{1} {2} {3} request for [{4}] processing done time#{5}ms status#{6}";
        long executionTime = System.currentTimeMillis() - (startTime.get()==null ? System.currentTimeMillis() : startTime.get());
        String url = StringUtils.substringAfter(request.getRequestURL().toString(), WebConstants.API_PREFIX);
        String message = MessageFormat.format(responseInfo, InetAddressHelper.getIpAddrByRequest(request),
                "",request.getMethod(),request.getContentType(),url,executionTime,response.getStatus());

        logger.info(message);
    }
}
