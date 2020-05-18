package org.gourd.hu.rbac.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gourd.hu.core.annotation.SensitiveInfo;
import org.gourd.hu.core.base.vo.BaseVO;
import org.gourd.hu.core.enums.SensitiveTypeEnum;
import org.gourd.hu.core.enums.SexEnum;

import java.time.LocalDateTime;

/**
 * 返回给前端的实体类
 *
 * @author gourd.hu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户对象", description = "用户对象")
public class UserVO extends BaseVO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("姓名拼音")
    private String pinYin;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("性别")
    private SexEnum sex;

    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private LocalDateTime birth;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("邮箱")
    @SensitiveInfo(SensitiveTypeEnum.EMAIL)
    private String email;

    @ApiModelProperty("手机")
    @SensitiveInfo(SensitiveTypeEnum.MOBILE_PHONE)
    private String mobilePhone;

    @ApiModelProperty("头像地址")
    private String photoUrl;


}