package com.xinyu.simple.configuration;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.xinyu.simple.common.vo.FieldExceptionVo;
import com.xinyu.simple.common.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 *@Author xinyu
 *@Date 10:58 2019/12/18
 *@Description  swagger2接口说明配置类
 *      ①、引入依赖springfox-swagger2   springfox-swagger-ui   guava
 *      ②、创建此SwaggerConfiguration配置类
 *      ③、WebConfiguration类中，配置静态资源映射，配置servlet处理
 *      ④、controller类，使用注解：@Api  @ApiOperation @ApiImplicitParams  @ApiImplicitParam
 *      ⑤、接口实体类，使用注解：@ApiModel  @ApiModelProperty
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    private static final String splitor = ";";//定义分隔符，配置Swagger多包

    @Autowired
    private TypeResolver typeResolver;



    /**
     * 创建API
     */
    @Bean
    public Docket createDictCodeApi() {
        return createDocket("com.xinyu.simple.web.controller","数据字典模块API");
    }

    private Docket createDocket(String basePackage,String groupName){
        return createDocket(RequestHandlerSelectors.basePackage(basePackage),groupName);
    }

    private Docket createDocket(Predicate<RequestHandler> requestHandler,String groupName){
        List<Parameter> headerParameter = new ArrayList<>();
        return new Docket(DocumentationType.SWAGGER_2).select().apis(requestHandler).paths(PathSelectors.any()).build()
                .groupName(groupName)
                .globalOperationParameters(headerParameter).apiInfo(apiInfo()).useDefaultResponseMessages(false)
                .additionalModels(typeResolver.resolve(ResponseVo.class),typeResolver.resolve(FieldExceptionVo.class))
                //.globalResponseMessage(RequestMethod.POST,null)
                .enable(true);//可控制不同环境是否开启swagger文档
    }

    /**
     * 添加摘要信息
     * 这里是接口的描述配置，不重要
     */
    private ApiInfo apiInfo(){
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                .title("标准项目使用swagger2构建RestFull API")
                .description("用于XXX统开发组生成RESTapi风格的接口...")
//                .contact(new Contact(Global.getName(), null, null))
//                .version("版本号:" + Global.getVersion())
                .build();
    }


    /**
     * 重写basePackage方法，使能够实现多包访问，复制贴上去
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage){
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }

}
