package com.xinyu.simple;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.xinyu.simple"})
@EnableTransactionManagement//开启spring申明式事务管理，需要事务的方法使用注解：@Transactional
@MapperScan("com.xinyu.simple.**.dao")//mybatis扫描mapper接口文件
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
        logger.info("程序启动成功");
    }
}
