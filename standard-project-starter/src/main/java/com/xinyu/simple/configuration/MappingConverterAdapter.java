package com.xinyu.simple.configuration;

import com.xinyu.simple.web.controller.form.QueryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
 *
 *   扩展：jdk自带的序列化，本质是java对象和字节序列之间的转换，不是json化，这样的好处是更省存储空间，但是不方面可视化。
 *          jdk实现序列化和反序列化的类为：对象输入流（ObjectInputStream）和对象输出流（ObjectOutputStream）
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



    /*
     *  @PostConstruct注解：
     *      1、jDK5规范中增加的注解，修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次
     *      2、用途：所在类的对象被创建，并加载完依赖注入后，开始执行被此注解修饰的方法
     *      3、因spring是支持依赖注入，所以是spring自己来实现@PostConstruct的解析器功能
     *      4、在spring项目，一个bean初始化过程中，方法执行先后顺序：Constructor > @Autowired > @PostConstruct,
     *          即先执行完构造方法，再注入依赖，最后执行被@PostConstruct修饰的方法
     *      5、对应注解@PreDestroy，则是bean销毁时执行
     *      6、使用条件：只有非静态方法；不得有任何形参；返回值必须为void；不得抛出异常；方法只会被执行一次
     **/
    @PostConstruct
    public void postConstruct() {
        //执行对象或程序初始化工作，如：加载数据字典，修改程序启动参数等

        //利用依赖注入后再执行的特性，修改springboot自动装配对象的属性，如上面addConversionConfig()方法、springboot各种组件配置的修改等

    }

    /**
     *  @Primary注解：
     *      1、问题：当一个接口有2个不同实现时,使用@Autowired注解时会报NoUniqueBeanDefinitionException异常信息。解决方案有两种-
     *          ①使用@Qualifier注解，选择一个对象的名称指定某个唯一对象，比较常用
     *          ②使用@Primary注解，指定某个类产生的对象进行优先注入
     *      2、作用：当发生依赖注入时一个bean有多个候选者，则会挑选被标注为@Primary的bean
     *      3、使用目标：方法或者类上
     **/
    @Bean
    @Primary
    public QueryForm createQueryForm(){
        return new QueryForm();
    }

}
