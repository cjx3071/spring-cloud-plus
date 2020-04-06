package org.gourd.hu.base.handler;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.stereotype.Component;

/**
 * 实体类id自动填充
 * @author gourd
 */
@Component
public class EntityIdGeneratorHandler implements IdentifierGenerator {

    @Override
    public Long nextId(Object entity) {
        return IdWorker.getId();
    }
}