package org.gourd.hu.cache.annotation;

import java.lang.annotation.*;


/**
 * @功能描述 防止重复提交标记注解
 * @author gourd.hu
 * @date 2018-08-26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRepeatSubmit {
    /**
     * 重复统计时长
     * @return
     */
    int value() default 1;
}