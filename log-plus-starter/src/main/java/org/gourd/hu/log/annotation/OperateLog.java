package org.gourd.hu.log.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author gourd.hu
 * @date 2018-11-24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {
	@AliasFor("description")
	String value() default "";

	/**
	 * 描述
	 * @return
	 */
	String description() default "";

	/**
	 * 模块名
	 * @return
	 */
	String moduleName() default "";

	/**
	 * 业务名
	 * @return
	 */
	String businessName() default "";
}
