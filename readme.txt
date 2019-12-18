一、项目整体结构，maven管理
    框架：springboot（spring+springmvc）、mybatisPlus（基于mybatis）
    依赖组件：mysql、redis、nexus
    包含：swagger2、logback、分页插件
    module关系：
        ①父pom项目包括：配置公共依赖包、管理子module依赖包版本、管理子module插件
        ②standard-project-common：包含一些通用常量枚举、util类、自定义通用Exception、通用dto等
        ③standard-project-api：包括一些Interface和dto实体类；用于service层对外部系统提供服务，进行接口定义，供外部系统依赖
        ④standard-project-service：包含service层、dao层，用于处理业务逻辑和数据库持久化
        ⑤standard-project-starter: 包含springboot启用类及加载配置、日志配置文件、springmvc接口等
        ⑥generator：包含mybatis-plus代码生成器



二、实体类区分：
    form实体类：springMVC接口方法形参，用于接收接口参数；
    vo实体类：springMVC接口返回值实体，用于返回调用者数据；
    dto实体类：service层接口接收和返回参数；
    entity实体类：用户mybatis访问数据库，用于表和实体映射接收数据；