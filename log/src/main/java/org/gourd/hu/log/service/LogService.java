package org.gourd.hu.log.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.gourd.hu.log.entity.LogPO;

import java.util.List;

/**
 * @author gourd
 * @date 2018-11-24
 */
public interface LogService extends IService<LogPO> {
    /**
     * 物理删除日志
     * @param ids
     */
    void deleteLogs(List<Long> ids);
}
