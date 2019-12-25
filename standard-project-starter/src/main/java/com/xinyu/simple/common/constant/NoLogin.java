package com.xinyu.simple.common.constant;

import java.lang.annotation.*;

/**
 *@Author xinyu
 *@Date 16:30 2019/12/25
 *@Description  登陆校验，标识不需要登陆校验的接口
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE})
public @interface NoLogin {
}
