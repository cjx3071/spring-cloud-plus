package org.gourd.hu.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.rbac.dao.SysTenantDao;
import org.gourd.hu.rbac.model.entity.SysTenant;
import org.gourd.hu.rbac.service.SysTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 承租人表 服务实现类
 * </p>
 *
 * @author gourd.hu
 * @since 2020-01-09
 */
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantDao, SysTenant> implements SysTenantService {

    @Autowired
    private SysTenantDao sysTenantDao;

    @Override
    public SysTenant checkGetTenant(String tenantItem){
        SysTenant tenant = sysTenantDao.findByCodeOrNumber(tenantItem);
        // 断言承租人存在
        ResponseEnum.TENANT_NOT_FOUND.assertNotNull(tenant);
        return tenant;
    }

}
