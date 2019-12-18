package com.xinyu.simple.common.utils;

import com.xinyu.simple.common.vo.ValidationResult;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *@Author xinyu
 *@Description hibernate校验器工具类
 *@Date 14:52 2019/12/18
 **/
public class ValidateUtil {
    private static final Validator FAILFAST_VALIDATOR;
    private static final Validator FAILOVER_VALIDATOR;
    //初始化2个hibernate validator，分别适用于failfast和failover场景
    static{
        FAILFAST_VALIDATOR=Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory()
                .getValidator();
        FAILOVER_VALIDATOR=Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(false)
                .buildValidatorFactory()
                .getValidator();
    }

    /**
     *@Author xinyu
     *@Date 15:21 2019/12/18
     *@Description 校验单个实体，使用快速失败场景
     **/
    public static <T> ValidationResult validateEntity(T obj){
        return validateEntitys(new Object[]{(Object) obj},true);
    }

    /**
     *@Author xinyu
     *@Date 15:15 2019/12/18
     *@Description 同时校验多个实体类，failFast 是否使用快速失败场景
     **/
    public static <T> ValidationResult validateEntity(T obj,boolean failFast){
        return validateEntitys(new Object[]{(Object) obj},failFast);
    }

    /**
     *@Author xinyu
     *@Date 15:15 2019/12/18
     *@Description 同时校验多个实体类，failFast 是否使用快速失败场景
     **/
    public static <T> ValidationResult validateEntitys(T[] objs,boolean failFast){
        if(objs==null)
            return new ValidationResult();

        Validator validator = failFast ? FAILFAST_VALIDATOR : FAILOVER_VALIDATOR;
        Set<ConstraintViolation<T>> set = new HashSet();
        for(T obj : objs){
            Set<ConstraintViolation<T>> validateSet = validator.validate(obj, Default.class);
            if(failFast && CollectionUtils.isNotEmpty(validateSet))
                return buildValidationResult(validateSet);
            set.addAll(validateSet);
        }

        return buildValidationResult(set);
    }

    /**
     *@Author xinyu
     *@Date 15:22 2019/12/18
     *@Description  校验指定实体的指定属性是否存在异常
     **/
    public static <T> ValidationResult validateProperty(T obj, String propertyName) {
        Set<ConstraintViolation<T>> validateSet = FAILFAST_VALIDATOR.validateProperty(obj, propertyName, Default.class);
        return buildValidationResult(validateSet);
    }

    /**
     * 将异常结果封装返回
     *
     * @param validateSet
     * @param <T>
     * @return
     */
    private static <T> ValidationResult buildValidationResult(Set<ConstraintViolation<T>> validateSet) {
        ValidationResult validationResult = new ValidationResult();
        if (CollectionUtils.isNotEmpty(validateSet)) {
            validationResult.setHasErrors(true);
            Map<String, String> errorMsgMap = new HashMap<>();
            for (ConstraintViolation<T> constraintViolation : validateSet) {
                errorMsgMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            validationResult.setErrorMsg(errorMsgMap);
        }
        return validationResult;
    }
}
