package org.gourd.hu.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gourd.hu.base.exceptions.BusinessException;
import org.gourd.hu.core.constant.MessageConstant;
import org.gourd.hu.rbac.dao.SysTenantDao;
import org.gourd.hu.rbac.entity.SysTenant;
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
        if(tenant == null){
            throw new BusinessException(MessageConstant.TENANT_NOT_FOUND);
        }
        return tenant;
    }

}
