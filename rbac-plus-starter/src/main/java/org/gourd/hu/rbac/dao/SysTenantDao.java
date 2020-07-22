package org.gourd.hu.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.gourd.hu.rbac.model.entity.SysTenant;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 承租人表 Mapper 接口
 * </p>
 *
 * @author gourd.hu
 * @since 2020-01-09
 */
@Repository
public interface SysTenantDao extends BaseMapper<SysTenant> {

    SysTenant findByCodeOrNumber(@Param("tenantItem") String tenantItem);

}
