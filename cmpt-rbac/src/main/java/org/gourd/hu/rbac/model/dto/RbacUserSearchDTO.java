package org.gourd.hu.rbac.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.gourd.hu.core.base.dto.BaseFindDTO;

/**
 * @author gourd
 * @date 2019-04-02 17:26:16
 */
@Data
@ApiModel(value = "用户搜索对象", description = "用户搜索对象")
public class RbacUserSearchDTO extends BaseFindDTO {

    @ApiModelProperty("账号")
    private  String account;

    @ApiModelProperty("姓名")
    private  String name;

}