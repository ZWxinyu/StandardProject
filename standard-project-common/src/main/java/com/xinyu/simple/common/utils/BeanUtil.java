package com.xinyu.simple.common.utils;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *@Author xinyu
 *@Date 15:08 2020/1/2
 *@Description  copy类对象，使用ReflectAsm高性能反射原理，性能比spring BeanUtils要好
 * 
 *  高性能反射机制工具包ReflectASM（不是java本身的反射包处理）
 *    1、简介：ReflectASM是一个很小的java类库，提供了非常高性能的属性操作、方法调用、构造方法调用（MethodAccess、FieldAccess、ConstructorAccess），
 *            它在底层使用了asm动态构建出字节码代理类，这相比于反射，直接方法的调用性能高出很多。
 *    2、原理：通过asm生成SomeClass(目标类）的代理类(ByteCode)，实现了MethodAccess的invoke方法，方法的内容是生成SomeClass的所有方法的调用index，
 *            这样可以通过指定方法名称的方式调用类上的方法。直接调用类上方法的速度肯定要快于反射调用。
 *    3、注意：
 *        ①、MethodAccess.get()方法比较耗时的,特别是类方法比较多的时候，如果生成的反射类用到的地方比较多或者会多次调用，建议缓存下来，
 **           如果使用次数很少建议还是使用反射来完成功能
 *        ②、目前好像无法反射出私有属性的功能
 *        ③、相对于java本身反射，功能还是过于简单，
 **/
public class BeanUtil {
    private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);
    //定义线程安全Map，存放已生成的字节码代理类
    private static final ConcurrentMap<Class, MethodAccess> methodLocalCache = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class, Map<String, Integer>> methodIndexOfGetLocalCache = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class, Map<String, Integer>> methodIndexOfSetLocalCache = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class, Map<String, String>> methodIndexOfTypeLocalCache = new ConcurrentHashMap<>();

    //生成指定类的字节码代理类，因此过程非常消耗性能，故设计存放在map缓存起来，方便下次快速获取。
    private static MethodAccess methodAccessFactory(Class clazz) {
        MethodAccess descMethodAccess = methodLocalCache.get(clazz);
        if (descMethodAccess == null) {
            synchronized (clazz) {
                descMethodAccess = methodLocalCache.get(clazz);
                if (descMethodAccess != null) {
                    return descMethodAccess;
                }
                Class<?> c = clazz;
                MethodAccess methodAccess = MethodAccess.get(c);
                Field[] fields = c.getDeclaredFields();
                Map<String, Integer> indexofget = new HashMap<>();
                Map<String, Integer> indexofset = new HashMap<>();
                Map<String, String> indexoftype = new HashMap<>();
                for (Field field : fields) {
                    if (Modifier.isPrivate(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) { // 私有非静态
                        String fieldName = StringUtils.capitalize(field.getName()); // 获取属性名称
                        int getIndex = methodAccess.getIndex("get" + fieldName); // 获取get方法的下标
                        int setIndex = methodAccess.getIndex("set" + fieldName); // 获取set方法的下标
                        indexofget.put(fieldName, getIndex);
                        indexofset.put(fieldName, setIndex);
                        indexoftype.put(fieldName, field.getType().getName());
                    }
                }
                methodIndexOfGetLocalCache.put(c, indexofget);
                methodIndexOfSetLocalCache.put(c, indexofset);
                methodIndexOfTypeLocalCache.put(c, indexoftype);
                methodLocalCache.put(c, methodAccess);
                return methodAccess;
            }
        }
        return descMethodAccess;
    }

    /**
     * 利用ReflectAsm，实现类对象copy功能
     * 浅copy类属性,根据属性名匹配，而不是类型+属性匹配，当类型不同且原属性值为null时，不变动目标类此属性值
     *
     * @param desc  接收复制参数的类
     * @param orgi  原始类
     */
    public static void copyProperties(Object desc, Object orgi) {
        MethodAccess descMethodAccess = methodAccessFactory(desc.getClass());
        MethodAccess orgiMethodAccess = methodAccessFactory(orgi.getClass());
        Map<String, Integer> get = methodIndexOfGetLocalCache.get(orgi.getClass());
        Map<String, Integer> set = methodIndexOfSetLocalCache.get(desc.getClass());
        Map<String, String> oritypemap = methodIndexOfTypeLocalCache.get(orgi.getClass());
        Map<String, String> desctypemap = methodIndexOfTypeLocalCache.get(desc.getClass());

        List<String> sameField = null;
        if (get.size() < set.size()) {
            sameField = new ArrayList<>(get.keySet());
            sameField.retainAll(set.keySet());
        } else {
            sameField = new ArrayList<>(set.keySet());
            sameField.retainAll(get.keySet());
        }
        for (String field : sameField) {
            Integer setIndex = set.get(field);
            Integer getIndex = get.get(field);
            String oritype = oritypemap.get(field);
            String desctype = desctypemap.get(field);
            Object value = orgiMethodAccess.invoke(orgi, getIndex);
            try {
                if (!oritype.equalsIgnoreCase(desctype)) {
                    if (value == null)
                        continue;

                    switch (desctype) {
                        case "java.lang.String":
                            descMethodAccess.invoke(desc, setIndex.intValue(), value.toString());
                            break;
                        case "java.lang.Integer":
                        case "int":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Integer.valueOf(value.toString()));
                            break;
                        case "java.lang.Long":
                        case "long":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Long.valueOf(value.toString()));
                            break;
                        case "java.lang.Float":
                        case "float":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Long.valueOf(value.toString()));
                            break;
                        case "java.lang.Boolean":
                        case "boolean":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Boolean.valueOf(value.toString()));
                            break;
                        case "java.lang.Double":
                        case "double":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Double.valueOf(value.toString()));
                            break;
                        case "java.lang.Byte":
                        case "byte":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Byte.valueOf(value.toString()));
                            break;
                        case "java.lang.Short":
                        case "short":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Short.valueOf(value.toString()));
                            break;
                        default:
                            break;
                    }
                } else {
                    descMethodAccess.invoke(desc, setIndex.intValue(), value);
                }
            } catch (Exception e) {
                logger.warn("Object field({}) copy warn:{}",field,e.getMessage());
            }
        }
    }
}
