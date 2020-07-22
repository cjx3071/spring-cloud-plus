package org.gourd.hu.doc.excel.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 性别枚举类
 *
 * @author gourd.hu
 * @create 2018-07-04 15:41
 **/
public enum SexEnum implements IEnum<String> {
    M("M","男"),
    F("F","女"),
    X("X","未知");

    @Getter
    @Setter
    @EnumValue
    private String value;

    @Getter
    @Setter
    private String label;

    SexEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据label获取枚举
     * @param label
     * @return
     */
    public static SexEnum getEnumByLabel (String label){
        if(StringUtils.isBlank(label)){
            return null;
        }
        for (SexEnum sexEnum : SexEnum.values()) {
            if(sexEnum.getLabel().equals(label)){
                return sexEnum;
            }
        }
        return null;

    }
}