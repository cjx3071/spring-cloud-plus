package org.gourd.hu.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gourd.hu.core.base.entity.BaseEntity;

/**
 * <p>
 * 组织角色关系表
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rbac_org_role")
@ApiModel(value = "RbacOrgRole对象", description = "组织角色关系表")
public class RbacOrgRole extends BaseEntity<RbacOrgRole> {

    @ApiModelProperty(value = "组织id")
    private Long orgId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

}
