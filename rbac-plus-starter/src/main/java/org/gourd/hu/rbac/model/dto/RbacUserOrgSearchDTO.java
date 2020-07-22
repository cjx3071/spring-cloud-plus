package org.gourd.hu.rbac.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.gourd.hu.core.base.dto.BaseFindDTO;

/**
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Data
@ApiModel(value = "部门用户搜索对象", description = "部门用户搜索对象")
public class RbacUserOrgSearchDTO extends BaseFindDTO {

    @ApiModelProperty("组织id")
    private Long orgId;

    @ApiModelProperty("组织名")
    private Long orgName;

    @ApiModelProperty("组织code")
    private Long orgCode;

}