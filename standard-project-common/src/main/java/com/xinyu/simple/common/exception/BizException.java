package com.xinyu.simple.common.exception;

/**
 *@Author xinyu
 *@Description  自定义异常类
 *@Date 18:03 2019/12/17
 **/
public class BizException extends RuntimeException {
    private String errorCode;
    private Object[] args;

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BizException(String errorCode, Object[] args) {
        this.errorCode = errorCode;
        this.args = args;
    }

    public BizException(String message, String errorCode, Object[] args) {
        super(message);
        this.errorCode = errorCode;
        this.args = args;
    }

    public BizException(String message, Throwable cause, String errorCode, Object[] args) {
        super(message, cause);
        this.errorCode = errorCode;
        this.args = args;
    }

    public BizException(Throwable cause, String errorCode, Object[] args) {
        super(cause);
        this.errorCode = errorCode;
        this.args = args;
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode, Object[] args) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.args = args;
    }
}
