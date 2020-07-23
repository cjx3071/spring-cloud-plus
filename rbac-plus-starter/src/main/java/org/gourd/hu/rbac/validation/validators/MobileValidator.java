package org.gourd.hu.rbac.validation.validators;

import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.core.constant.RegexConstant;
import org.gourd.hu.core.utils.MatcherUtil;
import org.gourd.hu.rbac.validation.annotations.Mobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * 手机号校验
 *
 * @author gourd.hu
 */
public class MobileValidator implements ConstraintValidator<Mobile,String> {
 
    private boolean require = false;
 
    @Override
    public void initialize(Mobile constraintAnnotation) {
        require = constraintAnnotation.required();
    }
 
    @Override
    public boolean isValid(String mobilePhone, ConstraintValidatorContext constraintValidatorContext) {
        if(require){
            return MatcherUtil.match(RegexConstant.REGEX_MOBILE_EXACT,mobilePhone);
        }else {
           if (StringUtils.isEmpty(mobilePhone)) {
               return true;
           }else {
               return MatcherUtil.match(RegexConstant.REGEX_MOBILE_EXACT,mobilePhone);
           }
        }
    }
}