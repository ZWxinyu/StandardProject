package com.xinyu.simple.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *@Author xinyu
 *@Date 14:24 2019/12/27
 *@Description  springmvc 接口传参特殊字段类型处理，针对@RequestBody之外所有情况的入参。（针对@RequestBody的入参处理，请参照：WebConfiguration.java）
 *
 * 注：对于@RequestParam和@RequestBody以及@PathVariable注解的参数，SpringMVC会使用不同的参数解析器进行数据绑定！
 *  分两种情况：
 *      第一：直接入参至接口方法形参字段，不封装进对象，此时作为普通请求参数传入时，通过配置Converter实现参数转换，转换用的是GenericConversionService。
 *      第二：请求content-type为application/json时，接口方法形参用实体类接收（@RequestBody），此时后端底层使用的是Json序列化Jackson工具，通过配置ObjectMapper，
 *      （MappingJackson2HttpMessageConverter）来实现Json格式数据的序列化（Serializer）和反序列化（Deserializer）；
 **/
@Configuration
public class MappingConverterAdapter {
    /** 默认日期时间格式 */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 默认日期格式 */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /** 默认时间格式 */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    //Local日期类型转换器，用于转换RequestParam和PathVariable修饰的参数,或者没有注解修饰的实体类参数中的属性
    @PostConstruct //jDK5增加的注解，修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。
    public void addConversionConfig() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter.getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService)initializer.getConversionService();
            genericConversionService.addConverter(new Converter<String, LocalDateTime>() {
                @Override
                public LocalDateTime convert(String source) {
                    return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
                }
            });
            genericConversionService.addConverter(new Converter<String, LocalDate>() {
                @Override
                public LocalDate convert(String source) {
                    return LocalDate.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
                }
            });
            genericConversionService.addConverter(new Converter<String, LocalTime>() {
                @Override
                public LocalTime convert(String source) {
                    return LocalTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
                }
            });
            genericConversionService.addConverter(new Converter<String, Date>() {
                @Override
                public Date convert(String source) {
                    SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
                    try {
                        return format.parse(source);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }
}
