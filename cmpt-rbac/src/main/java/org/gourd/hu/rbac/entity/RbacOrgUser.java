package org.gourd.hu.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import org.gourd.hu.base.common.entity.BaseEntity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户组织关系表
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rbac_org_user")
@ApiModel(value = "RbacOrgUser对象", description = "用户组织关系表")
public class RbacOrgUser extends BaseEntity<RbacOrgUser> {

    @ApiModelProperty(value = "组织id")
    private Long orgId;

    @ApiModelProperty(value = "用户id")
    private Long userId;


}
