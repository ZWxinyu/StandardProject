package com.xinyu.ximple.web;

import com.alibaba.fastjson.JSON;
import com.xinyu.simple.Application;
import com.xinyu.simple.service.IDictCodeService;
import com.xinyu.simple.service.dto.DictCodeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 *@Author xinyu
 *@Date 16:48 2019/12/20
 *@Description  springboot项目单元测试类
 *  步骤为：
 *      ①引入依赖spring-boot-starter-test
 *      ②编写此测试类，添加注解：@RunWith @SpringBootTest  @Test
 *
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)//当测试项目包含websocket，注解需要加入属性：webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
public class DictCodeTest {
    private static final Logger logger = LoggerFactory.getLogger(DictCodeTest.class);

    @Autowired
    private IDictCodeService dictCodeService;

    @Test
    public void test_queryByCode(){
        List<DictCodeDto> dictCodeDtos = dictCodeService.queryByCode("543");
        logger.info("结果为："+ JSON.toJSON(dictCodeDtos));
    }
}
