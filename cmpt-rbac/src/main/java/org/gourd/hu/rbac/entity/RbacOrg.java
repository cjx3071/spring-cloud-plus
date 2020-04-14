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
 * 组织表
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rbac_org")
@ApiModel(value = "RbacOrg对象", description = "组织表")
public class RbacOrg extends BaseEntity<RbacOrg> {

    @ApiModelProperty(value = "组织名")
    private String name;

    @ApiModelProperty(value = "组织代码")
    private String code;

    @ApiModelProperty(value = "组织级联路径(格式：父path_当前path)")
    private String path;

    @ApiModelProperty(value = "父id")
    private Long parentId;

    @ApiModelProperty(value = "备注")
    private String description;


}
