一、项目整体结构，maven管理
    框架：springboot（spring+springmvc）、mybatisPlus（基于mybatis）
    依赖组件：mysql、redis、nexus
    包含：swagger2、logback
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

三、jar启动命令
    java -Xms256m -Xmx1024m -Xmn2g -Xss256k -XX:MaxPermSize=128m -XX:NewRatio=4 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=10
        -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/usr/local/app/standardProject
        -Dspring.profiles.active=dev -Dspring.application.name=standardProject -Dserver.port=8899
        -Dloader.path=/usr/local/app/standardProject/conf
        -Dlogging.path=/usr/local/app/standardProject/logs -Dspring.config.location=classpath:/application.yml
        -Djava.net.preferIPv4Stack=true
        -server -cp /usr/local/app/standardProject/bin/* -jar /usr/local/app/standardProject/standardProject.jar >> api.out
解析：
    ①-Xmx1024m：设置JVM最大可用内存为1024M。
     -Xms256m：设置JVM初始内存为256m。此值可以设置与-Xmx相同，以避免每次垃圾回收完成后JVM重新分配内存。
     -Xmn2g：设置年轻代大小为2G。整个JVM堆内存大小=年轻代大小+年老代大小+持久代大小。持久代一般固定大小为64m，所以增大年轻代后，将会减小年老代大小。
            此值对系统性能影响较大，Sun官方推荐配置为整个堆的3/8。
     -Xss256k：设置每个线程的堆栈大小。JDK5.0以后每个线程堆栈大小为1M，以前每个线程堆栈大小为256K。根据应用的线程所需内存大小进行调整。
            在相同物理内存下，减小这个值能生成更多的线程。但是操作系统对一个进程内的线程数还是有限制的，不能无限生成，经验值在3000~5000左右。
     -XX:NewRatio=4：设置年轻代（包括Eden和两个Survivor区）与年老代的比值（除去持久代）。设置为4则年轻代与年老代所占比值为1:4，年轻代占整个堆栈的1/5
     -XX:SurvivorRatio=8：设置年轻代中Eden区与Survivor区的大小比值。设置为8则两个Survivor区与一个Eden区比值为2:8，一个Survivor区占整个年轻代的1/10
     -XX:MaxPermSize=128m：设置最大非堆内存的大小，默认是物理内存的1/4。。
     -XX:MaxTenuringThreshold=10：设置垃圾最大年龄。如果设置为10的话，则年轻代对象不经过Survivor区，直接进入年老代。对于年老代比较多的应用，
        可以提高效率。如果将此值设置为一个较大值，则年轻代对象会在Survivor区进行多次复制，这样可以增加对象在年轻代的存活时间。
    ②-XX:+PrintGCDetails：设置打印GC执行详细信息日志
     -XX:+PrintGCTimeStamps：打印CG日志时发生的时间戳
     -XX:+HeapDumpOnOutOfMemoryError：参数表示当JVM发生OOM时，自动生成内存快照DUMP文件。
     -XX:HeapDumpPath：指定内存快至DUMP文件保存的位置
    ③-Dspring.profiles.active：根据运行环境指定加载application配置文件。因为按照springboot约定命名格式：application-{profile}.properties，
        当app启动时，项目会先从application-{profile}.properties加载配置，再从application.properties配置文件加载配置，如果有重复的配置，
        则会以application.properties的配置为准。如果active赋予的参数没有与使用该命名约定格式文件相匹配的话，app则会默认从名为
        application-default.properties 的配置文件加载配置。
     -Dspring.application.name：动态设置容器应用名称，会覆盖application文件设置的值
     -Dserver.port：设置应用程序对外开放的端口号
     -Dloader.path：指定应用程序加载的文件夹
     -Dlogging.path：动态设置日志文件输出的位置，会覆盖application文件设置的值
     -Dspring.config.location：指定加载springboot配置文件位置
    ④-Djava.net.preferIPv4Stack：程序只支持IPV4
     -server：
     -cp：加载指定目录下的外部配置文件（或外部jar包），指定多个位置使用分隔符;(windows系统)、:(linux系统)
     -jar：指定需要启动程序的jar包，会用到目录META-INF\MANIFEST.MF文件中指定的main主类启动
     >> ：指定应用程序控制台输出的日志文件位置






===========================》业务不规范，开发两行泪 《=================================