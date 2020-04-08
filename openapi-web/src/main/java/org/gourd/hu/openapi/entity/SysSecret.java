package org.gourd.hu.openapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.gourd.hu.base.common.entity.BaseEntity;

/**
 * <p>
 * 权限密钥表
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_secret")
public class SysSecret extends BaseEntity {

    /**
     * 模块名
     */
    private String appKey;

    /**
     * 业务名
     */
    private String secretKey;

    /**
     * 过期时间，过期时长，单位毫秒，默认5分钟
     */
    private Integer expireTimes = 300000;

}
