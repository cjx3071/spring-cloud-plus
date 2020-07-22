package org.gourd.hu.rbac.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.rbac.dao.RbacPermissionDao;
import org.gourd.hu.rbac.model.entity.RbacPermission;
import org.gourd.hu.rbac.service.RbacPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 权限service
 *
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Service
@Slf4j
public class RbacPermissionServiceImpl extends ServiceImpl<RbacPermissionDao, RbacPermission> implements RbacPermissionService {

    @Autowired
    private RbacPermissionDao rbacPermissionMapper;

    @Override
    @DS("slave")
    public List<RbacPermission> findByRoleIds(Set<Long> roleIds){
        return  rbacPermissionMapper.findByRoleIds(roleIds);
    }

}
