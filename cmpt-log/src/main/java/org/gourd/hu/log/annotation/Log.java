package org.gourd.hu.log.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志记录
 *
 * @author gourd
 * @date 2018-11-24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
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
