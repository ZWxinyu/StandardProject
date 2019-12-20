package com.xinyu.simple;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.xinyu.simple"})
@MapperScan("com.xinyu.simple.**.dao")//mybatis扫描mapper接口文件
@EnableTransactionManagement//开启spring申明式事务管理，需要事务的方法使用注解：@Transactional
@EnableCaching//开启spring缓存方法调用到redis ，结合注解@Cacheable使用，用于查询数据库的数据不经常变化的操作，如：字典表、区域表、常量表等
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
        logger.info("程序启动成功");
    }
}
