package com.xinyu.simple.common.vo;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Map;

/**
 *@Author xinyu
 *@Description  hibernate校验器校验结果类
 *@Date 14:49 2019/12/18
 **/
public class ValidationResult {
    /**
     * 是否有异常
     */
    private boolean hasErrors = false;

    /**
     * 异常消息记录
     */
    private Map<String, String> errorMsg;

    /**
     * 获取异常消息组装
     *
     * @return
     */
    public String getMessage() {
        if (errorMsg == null || errorMsg.isEmpty()) {
            return StringUtils.EMPTY;
        }
        StringBuilder message = new StringBuilder();
        errorMsg.forEach((key, value) -> {
            message.append(MessageFormat.format("{0}:{1} \r\n", key, value));
        });
        return message.toString();
    }


    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Map<String, String> errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                "hasErrors=" + hasErrors +
                ", errorMsg=" + errorMsg +
                '}';
    }
}
