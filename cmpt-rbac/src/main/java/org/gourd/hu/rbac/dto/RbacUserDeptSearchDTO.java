package org.gourd.hu.rbac.dto;

import lombok.Data;
import org.gourd.hu.base.common.dto.BaseFindDTO;

/**
 * @author gourd
 * @date 2019-04-02 17:26:16
 */
@Data
public class RbacUserDeptSearchDTO extends BaseFindDTO {

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 部门名
     */
    private Long deptName;

    /**
     * 部门code
     */
    private Long deptCode;

}