package com.xinyu.simple.common.vo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *@Author xinyu
 *@Description 接口返回vo实体类
 *@Date 17:30 2019/12/17
 **/
@Data
@ApiModel(description = "接口返回VO")
public class ResponseVo<V> {
    @ApiModelProperty(value = "异常code",example="null")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCOde="0";

    @ApiModelProperty("是否调用成功")
    private boolean success;
    @ApiModelProperty("错误字段信息集合")
    private List<FieldExceptionVo> fieldErrors;
    @ApiModelProperty("全局出错信息")
    private String msg;
    @ApiModelProperty("接口返回内容")
    private V data;

    public ResponseVo() {
        super();
    }

    public ResponseVo(boolean success, List<FieldExceptionVo> fieldErrors, String msg, V data) {
        this.success = success;
        this.fieldErrors = fieldErrors;
        this.msg = msg;
        this.data = data;
    }

    public ResponseVo(String errorCOde, boolean success, List<FieldExceptionVo> fieldErrors, String msg, V data) {
        this.errorCOde = errorCOde;
        this.success = success;
        this.fieldErrors = fieldErrors;
        this.msg = msg;
        this.data = data;
    }

    public ResponseVo(boolean success, List<FieldExceptionVo> fieldErrors, String msg) {
        this.success = success;
        this.fieldErrors = fieldErrors;
        this.msg = msg;
    }

    public static <T> ResponseVo<T> response(boolean success,List<FieldExceptionVo> fieldErrors,String msg,T data){
        return new ResponseVo<>(success,fieldErrors,msg,data);
    }
    public static <T> ResponseVo<T> response(boolean success,List<FieldExceptionVo> fieldErrors,String msg){
        return new ResponseVo<>(success,fieldErrors,msg);
    }
    public static <T> ResponseVo<T> failResponse(String errorCOde,String msg){
        return new ResponseVo(errorCOde,false,new ArrayList<>(),msg,new JSONObject());
    }
    public static <T> ResponseVo<T> successResponse(){
        return response(true,new ArrayList<>(),"请求成功");
    }
    public static <T> ResponseVo<T> successResponse(T data){
        return response(true,new ArrayList(),"请求成功",data);
    }

    public void addFieldError(String fieldName,String errormsg){
        if(this.getFieldErrors()==null)
            this.fieldErrors = new ArrayList<>();
        this.fieldErrors.add(new FieldExceptionVo(fieldName,errormsg));
    }
}
