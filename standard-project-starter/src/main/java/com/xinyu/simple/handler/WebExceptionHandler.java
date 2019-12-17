package com.xinyu.simple.handler;

import com.xinyu.simple.common.exception.BizException;
import com.xinyu.simple.common.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *@Author xinyu
 *@Description controller层统一异常捕获处理类（spring切面技术）
 *@Date 16:24 2019/12/17
 **/
@ControllerAdvice(basePackages = {"com.xinyu.simpler.web"})
public class WebExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler(value= BizException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseVo bizError(BizException e){
        logger.error("业务异常，错误信息：",e);
        return ResponseVo.failResponse(HttpStatus.CONFLICT.toString(),e.getMessage());
    }

    @ExceptionHandler(value=Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseVo globalError(Exception e){
        logger.error("程序异常，错误信息：",e);
        return ResponseVo.failResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(),"服务异常");
    }

}
