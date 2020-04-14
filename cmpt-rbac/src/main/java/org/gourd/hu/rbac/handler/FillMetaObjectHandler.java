package org.gourd.hu.rbac.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.gourd.hu.rbac.constant.RbacConstant;
import org.gourd.hu.rbac.auth.jwt.JwtUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 元对象字段填充控制器
 * 自定义填充公共字段 ,即没有传的字段自动填充
 *
 * @author gourd
 **/
@Component
public class FillMetaObjectHandler implements MetaObjectHandler {
    /**
     *  新增填充
     *
     *  @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = JwtUtil.getCurrentUser() == null? 0L:(JwtUtil.getCurrentUser().getUserId()==null?0L:JwtUtil.getCurrentUser().getUserId());
        fillCreateMeta(metaObject, userId);
        fillUpdateMeta(metaObject, userId);
        fillCommonMeta(metaObject);
    }

    /**
     * 更新填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = JwtUtil.getCurrentUser() == null? 0L:(JwtUtil.getCurrentUser().getUserId()==null?0L:JwtUtil.getCurrentUser().getUserId());
        fillUpdateMeta(metaObject,userId);
    }

    private void fillCommonMeta(MetaObject metaObject) {
        if (metaObject.hasGetter(RbacConstant.META_VERSION)) {
            setFieldValByName(RbacConstant.META_VERSION,1L,metaObject);
        }
        if(metaObject.hasGetter(RbacConstant.META_DELETED)){
            setFieldValByName(RbacConstant.META_DELETED,false,metaObject);
        }
    }

    private void fillCreateMeta(MetaObject metaObject, Long userId) {
        if (metaObject.hasGetter(RbacConstant.META_CREATED_BY) && metaObject.hasGetter(RbacConstant.META_CREATED_TIME)) {
            setFieldValByName(RbacConstant.META_CREATED_BY, userId, metaObject);
            setFieldValByName(RbacConstant.META_CREATED_TIME, new Date(), metaObject);
        }
    }
    private void fillUpdateMeta(MetaObject metaObject, Long userId) {
        if (metaObject.hasGetter(RbacConstant.META_UPDATED_BY) && metaObject.hasGetter(RbacConstant.META_UPDATED_TIME)) {
            setFieldValByName(RbacConstant.META_UPDATED_BY, userId, metaObject);
            setFieldValByName(RbacConstant.META_UPDATED_TIME, new Date(), metaObject);
        }
    }
}