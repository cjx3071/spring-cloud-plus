package org.gourd.hu.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.gourd.hu.rbac.entity.RbacRole;

import java.util.List;

/**
 * 角色
 *
 * @author gourd
 * @date 2019-04-02 17:26:16
 * Created by CodeGen .
 */
public interface RbacRoleService extends IService<RbacRole> {

    List<RbacRole> findByUserId(Long userId);


}