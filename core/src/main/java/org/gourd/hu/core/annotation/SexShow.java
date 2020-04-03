package org.gourd.hu.core.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.gourd.hu.core.enums.FieldShowEnum;
import org.gourd.hu.core.serializer.SexSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 性别展示注解类
 *
 * @author gourd
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SexSerialize.class)
public @interface SexShow {

  FieldShowEnum type();
}
