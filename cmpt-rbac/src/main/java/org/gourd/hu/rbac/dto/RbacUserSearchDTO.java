package org.gourd.hu.rbac.dto;

import lombok.Data;
import org.gourd.hu.base.common.dto.BaseFindDTO;

/**
 * @author gourd
 * @date 2019-04-02 17:26:16
 */
@Data
public class RbacUserSearchDTO extends BaseFindDTO {

    /**
     * 账号
     */
    private  String account;

    /**
     * 姓名
     */
    private  String name;

}