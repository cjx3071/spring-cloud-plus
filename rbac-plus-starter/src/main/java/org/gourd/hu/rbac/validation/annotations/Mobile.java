package org.gourd.hu.rbac.validation.annotations;

import org.gourd.hu.rbac.validation.validators.MobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 手机号校验注解
 *
 * @author gourd.hu
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {MobileValidator.class}
)
public @interface Mobile {

    boolean required() default false;

    String message() default "手机号格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
