package org.gourd.hu.log.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.gourd.hu.log.entity.SysOperateLog;

import java.util.List;

/**
 * @author gourd.hu
 * @date 2018-11-24
 */
public interface SysOperateLogDao extends BaseMapper<SysOperateLog> {

    void deleteByIds(@Param("ids") List<Long> ids);

}
