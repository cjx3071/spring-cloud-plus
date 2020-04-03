package org.gourd.hu.base.handler;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.gourd.hu.core.utils.IdWorker;
import org.springframework.stereotype.Component;

/**
 * 实体类id自动填充
 * @author gourd
 */
@Component
public class EntityIdGeneratorHandler implements IdentifierGenerator {

    private static final IdWorker idWorker = new IdWorker(0,1);

    @Override
    public Long nextId(Object entity) {
        return idWorker.nextId();
    }
}