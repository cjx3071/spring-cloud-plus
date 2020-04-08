package org.gourd.hu.openapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.gourd.hu.openapi.entity.SysSecret;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 权限密钥表 Mapper 接口
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-02
 */
@Repository
public interface SysSecretDao extends BaseMapper<SysSecret> {

}
