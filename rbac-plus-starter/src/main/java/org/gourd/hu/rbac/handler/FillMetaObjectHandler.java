package org.gourd.hu.rbac.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.gourd.hu.rbac.auth.jwt.JwtToken;
import org.gourd.hu.rbac.auth.jwt.JwtUtil;
import org.gourd.hu.rbac.constant.RbacConstant;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 元对象字段填充控制器
 * 自定义填充公共字段 ,即没有传的字段自动填充
 *
 * @author gourd.hu
 **/
@Component
@Slf4j
public class FillMetaObjectHandler implements MetaObjectHandler {
    /**
     *  新增填充
     *
     *  @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        JwtToken currentUser = JwtUtil.getCurrentUser();
        // 创建填充
        fillCreateMeta(metaObject, currentUser);
        // 更新填充
        fillUpdateMeta(metaObject, currentUser);
    }

    /**
     * 更新填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        JwtToken currentUser = JwtUtil.getCurrentUser();
        fillUpdateMeta(metaObject,currentUser);
    }

    /**
     * 填充创建数据
     * @param metaObject
     * @param currentUser
     */
    private void fillCreateMeta(MetaObject metaObject, JwtToken  currentUser) {
        Long userId = currentUser == null ? 1L: currentUser.getUserId();
        Long tenantId = currentUser == null ? 1L: currentUser.getTenantId();
        String userName = currentUser == null ? "007": currentUser.getUserName();
        if (metaObject.hasGetter(RbacConstant.META_CREATED_BY) && metaObject.hasGetter(RbacConstant.META_CREATED_TIME)) {
            this.strictInsertFill(metaObject,RbacConstant.META_CREATED_BY,Long.class, userId);
            this.strictInsertFill(metaObject,RbacConstant.META_CREATED_TIME,LocalDateTime.class, LocalDateTime.now());
            this.strictInsertFill(metaObject,RbacConstant.META_DELETED,Boolean.class,Boolean.FALSE);
            this.strictInsertFill(metaObject,RbacConstant.META_VERSION,Integer.class,1);
        }
        if(metaObject.hasGetter(RbacConstant.COLUMN_TENANT_ID)){
            this.strictInsertFill(metaObject,RbacConstant.COLUMN_TENANT_ID, Long.class,tenantId);
        }
        if(metaObject.hasGetter(RbacConstant.META_CREATED_NAME)){
            this.strictInsertFill(metaObject,RbacConstant.META_CREATED_NAME,String.class, userName);
        }
        // 插入数据，Integer默认设置0,String类型默认设置空字符串
        List<TableFieldInfo> fieldList = this.findTableInfo(metaObject).getFieldList();
        Object originalObject = metaObject.getOriginalObject();
        for (TableFieldInfo fieldInfo : fieldList) {
            if(Integer.class.equals(fieldInfo.getPropertyType())){
                try {
                    Object o = fieldInfo.getField().get(originalObject);
                    if(o == null){
                        this.strictInsertFill(metaObject,fieldInfo.getProperty(), Integer.class,0);
                    }
                } catch (IllegalAccessException e) {
                    log.error("Integer类型属性值填充异常");
                }
            }
            if(String.class.equals(fieldInfo.getPropertyType())){
                try {
                    Object o = fieldInfo.getField().get(originalObject);
                    if(o == null){
                        this.strictInsertFill(metaObject,fieldInfo.getProperty(), String.class,"");
                    }
                } catch (IllegalAccessException e) {
                    log.error("String类型属性值填充异常");
                }
            }
        }
    }

    /**
     * 填充更新数据
     * @param metaObject
     * @param currentUser
     */
    private void fillUpdateMeta(MetaObject metaObject, JwtToken currentUser) {
        Long userId = currentUser == null ? 1L: currentUser.getUserId();
        String userName = currentUser == null ? "007": currentUser.getUserName();
        if (metaObject.hasGetter(RbacConstant.META_UPDATED_BY) && metaObject.hasGetter(RbacConstant.META_UPDATED_TIME)) {
            // fillStrategy方法会判断属性是否有值，如果有值就不会覆盖，所以调整为setFieldValByName方法
            this.strictUpdateFill(metaObject,RbacConstant.META_UPDATED_BY,Long.class, userId);
            this.strictUpdateFill(metaObject,RbacConstant.META_UPDATED_TIME,LocalDateTime.class, LocalDateTime.now());
        }
        if(metaObject.hasGetter(RbacConstant.META_UPDATED_NAME)){
            this.strictUpdateFill(metaObject,RbacConstant.META_UPDATED_NAME,String.class, userName);
        }
    }
}