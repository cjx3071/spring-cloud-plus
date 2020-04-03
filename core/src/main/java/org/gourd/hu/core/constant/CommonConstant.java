package org.gourd.hu.core.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author gourd
 */
public class CommonConstant {

    public static final String TX_NUMBER = "txNumber";

    public static final String MQ_TAG_STR = "tagStr";

    public static final String MQ_TAG_OBJECT = "tagObject";

    public static final String META_ID = "id";

    public static final String META_CREATED_BY = "createdBy";

    public static final String META_CREATED_TIME = "createdTime";

    public static final String META_UPDATED_BY = "updatedBy";

    public static final String META_UPDATED_TIME = "updatedTime";

    public static final String META_DELETED = "deleted";

    public static final String META_VERSION = "version";

    public static final String COLUMN_TENANT_ID = "tenant_id";

    public static final List NO_FILTER_TABLES = Arrays.asList("sys_tenant");

    public static final List NO_FILTER_SQLS = Arrays.asList(
            "com.gourd.hu.security.rbac.dao.RbacUserDao.getByAccountAndTenantId",
            "com.gourd.hu.security.rbac.dao.RbacRoleDao.findByUserId",
            "com.gourd.hu.security.rbac.dao.RbacPermissionDao.findByRoleIds",
            "com.gourd.hu.shiro.rbac.dao.RbacUserDao.getByAccountAndTenantId",
            "com.gourd.hu.shiro.rbac.dao.RbacRoleDao.findByUserId",
            "com.gourd.hu.shiro.rbac.dao.RbacPermissionDao.findByRoleIds"
            );
}
