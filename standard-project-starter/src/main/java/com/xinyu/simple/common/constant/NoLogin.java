package com.xinyu.simple.common.constant;

import java.lang.annotation.*;

/**
 *@Author xinyu
 *@Date 16:30 2019/12/25
 *@Description  登陆校验，标识不需要登陆校验的接口
 *  注解简介：
 *      ①注解的本质就是一个继承了 Annotation 接口的接口
 *      ②目前主流的系统参数配置方式有『注解』和『XML』两种，各有各的优劣，注解可以提供更大的便捷性，易于维护修改，但耦合度高，而 XML 相对于注解则是相反的。
 *          追求低耦合就要抛弃高效率，追求效率必然会遇到耦合，看使用场景
 *      ③一个注解准确意义上来说，只不过是一种特殊的注释而已，如果没有解析它的代码，它可能连注释都不如
 *  注解解析：
 *      ①有两种形式，一种是编译期直接的扫描，一种是运行期反射。
 *      ②第一种形式：编译器的扫描指的是虚拟机编译器在对 java 代码编译字节码的过程中，会检测到某个类或者方法被一些注解修饰，这时它就会对于这些注解进行某些处理。
 *          典型的就是注解 @Override，这一种情况只适用于那些编译器已经熟知的注解类，比如 JDK 内置的几个注解（@Override、@Deprecated、@SuppressWarnings）。
 *  元注解：
 *      ①『元注解』是用于修饰注解的注解，通常用在注解的定义上，『元注解』一般用于指定某个注解生命周期以及作用目标等信息：
 *          @Target：指明注解的作用（修饰）目标，包括：TYPE类、接口和枚举；FIELD属性字段；METHOD方法；PARAMETER方法参数；CONSTRUCTOR构造器；LOCAL_VARIABLE本地局部变量；ANNOTATION_TYPE注解；PACKAGE包。
 *          @Retention：注解的生命周期,包括：SOURCE-当前注解编译期可见，不会写入class文件；CLASS-类加载阶段丢弃，会写入class文件；RUNTIME-永久保存，可以反射获取。
 *          @Documented：注解是否应当被包含在 JavaDoc 文档中
 *          @Inherited：是否允许子类继承该注解
 *  注解与反射：
 *      ①Class 类中提供了以下一些方法用于反射注解，利用这些方法可以在运行期通过反射机制实现注解的解析器：
 *          getAnnotation：返回指定的注解的实例
 *          isAnnotationPresent：判定当前元素是否被指定注解修饰
 *          getAnnotations：返回所有的注解的实例
 *          getDeclaredAnnotation：返回本元素的指定注解实例
 *          getDeclaredAnnotations：返回本元素的所有注解实例，不包含父类继承而来的
 *      ②当通过反射，也就是getAnnotation方法去获取一个注解类实例的时候，其实 JDK 是通过动态代理机制生成一个实现注解（本质是接口）的代理类。
 *      ③代理类中的 AnnotationInvocationHandler 是 JAVA 中专门用于处理注解的 Handler，代理类代理了 注解（接口）中所有的方法。
 *  反射注解的工作原理：
 *      ①首先，自定义注解在目标上使用，通过键值对的形式可以为注解属性赋值，像这样：@NoLogin（value = "yes"）。
 *      ②接着，用注解修饰某个元素，编译器将在编译期扫描每个类或者方法上的注解，会做一个基本的检查，你的这个注解是否允许作用在当前位置，最后会将注解信息写入元素的属性表。
 *      ③然后，当你进行反射的时候，虚拟机将所有生命周期在 RUNTIME 的注解取出来放到一个 map 中，并创建一个 AnnotationInvocationHandler 实例，把这个 map 传递给它。
 *      ④最后，虚拟机将采用 JDK 动态代理机制生成一个目标注解的代理类，并初始化好处理器。
 *
 *参考文档：https://www.cnblogs.com/yangming1996/p/9295168.html
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE})
public @interface NoLogin {
}
