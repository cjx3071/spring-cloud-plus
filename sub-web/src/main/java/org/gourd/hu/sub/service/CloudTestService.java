package org.gourd.hu.sub.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *@Description 测试service
 *@Author gourd
 *@Date 2019/12/11 18:13
 *@Version 1.0
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CloudTestService{
    private final JdbcTemplate jdbcTemplate;


    public CloudTestService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void testSeata(){
        jdbcTemplate.update(
                "update rbac_depart set name = ? where id = ?",new Object[] { "seata1", 1 });
    }
}
