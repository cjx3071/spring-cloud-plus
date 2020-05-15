package org.gourd.hu.rbac.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gourd.hu.base.common.vo.BaseVO;
import org.gourd.hu.core.annotation.SensitiveInfo;
import org.gourd.hu.core.enums.SensitiveTypeEnum;
import org.gourd.hu.core.enums.SexEnum;

import java.time.LocalDateTime;

/**
 * @author gourd
 *
 * 返回给前端的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO extends BaseVO {
    /**
     * 名称
     */
    private String name;

    /**
     * 姓名拼音
     */
    private String pinYin;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private LocalDateTime birth;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    @SensitiveInfo(SensitiveTypeEnum.EMAIL)
    private String email;

    /**
     * 手机
     */
    @SensitiveInfo(SensitiveTypeEnum.MOBILE_PHONE)
    private String mobilePhone;

    /**
     * 头像地址
     */
    private String photoUrl;


}