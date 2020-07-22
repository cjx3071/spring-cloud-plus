package org.gourd.hu.rbac.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.rbac.dao.RbacRoleDao;
import org.gourd.hu.rbac.model.entity.RbacRole;
import org.gourd.hu.rbac.service.RbacRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色服务
 *
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 * Created by CodeGen .
 */
@Service
@Slf4j
public class RbacRoleServiceImpl extends ServiceImpl<RbacRoleDao, RbacRole> implements RbacRoleService {

    @Autowired
    private RbacRoleDao rbacRoleDao;

    @Override
    @DS("slave")
    public List<RbacRole> findByUserId(Long userId){
        return rbacRoleDao.findByUserId(userId);
    }
}
