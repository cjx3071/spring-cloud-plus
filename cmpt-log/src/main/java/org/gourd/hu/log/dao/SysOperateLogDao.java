package org.gourd.hu.log.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.gourd.hu.log.entity.SysOperateLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gourd
 * @date 2018-11-24
 */
@Repository
public interface SysOperateLogDao extends BaseMapper<SysOperateLog> {

    void deleteByIds(@Param("ids") List<Long> ids);

}
