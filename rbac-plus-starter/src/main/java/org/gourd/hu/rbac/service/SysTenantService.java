package org.gourd.hu.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.gourd.hu.rbac.model.entity.SysTenant;

/**
 * <p>
 * 承租人表 服务类
 * </p>
 *
 * @author gourd.hu
 * @since 2020-01-09
 */
public interface SysTenantService extends IService<SysTenant> {

    SysTenant checkGetTenant(String tenantItem);

}
