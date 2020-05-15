package org.gourd.hu.openapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gourd.hu.core.base.entity.BaseEntity;

/**
 * <p>
 * 权限密钥表
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_secret")
@ApiModel(value = "secret对象", description = "鉴权表")
public class SysSecret extends BaseEntity {

    @ApiModelProperty(value = "appkey")
    private String appKey;

    @ApiModelProperty(value = "秘钥")
    private String secretKey;

    @ApiModelProperty(value = "app过期时间，过期时长，单位毫秒，默认5分钟key")
    private Integer expireTimes = 300000;

}
