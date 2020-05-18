package org.gourd.hu.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gourd.hu.core.base.entity.BaseEntity;

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
