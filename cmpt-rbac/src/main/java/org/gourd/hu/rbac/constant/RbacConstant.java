package org.gourd.hu.rbac.constant;

import java.util.Arrays;
import java.util.List;

/**
 * RBAC常量
 * @author gourd.hu
 */
public class RbacConstant {

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

    public static final List NO_FILTER_TABLES = Arrays.asList("sys_tenant","sys_operate_log");

    public static final List NO_FILTER_SQLS = Arrays.asList(
            "org.gourd.hu.rbac.dao.RbacUserDao.getByAccountAndTenantId",
            "org.gourd.hu.rbac.dao.RbacRoleDao.findByUserId",
            "org.gourd.hu.rbac.dao.RbacPermissionDao.findByRoleIds"
    );
}
