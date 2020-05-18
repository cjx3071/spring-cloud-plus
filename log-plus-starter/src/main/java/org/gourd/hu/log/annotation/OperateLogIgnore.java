package org.gourd.hu.log.annotation;

import java.lang.annotation.*;

/**
 * 忽略操作日志注解
 *
 * @author gourd.hu
 * @date 2018-11-24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLogIgnore {

}
