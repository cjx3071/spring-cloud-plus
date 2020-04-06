package org.gourd.hu.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gourd.hu.log.dao.LogDao;
import org.gourd.hu.log.entity.LogPO;
import org.gourd.hu.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gourd
 * @date 2018-11-24
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogDao, LogPO> implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public void deleteLogs(List<Long> ids){
        logDao.deleteByIds(ids);
    }


}
