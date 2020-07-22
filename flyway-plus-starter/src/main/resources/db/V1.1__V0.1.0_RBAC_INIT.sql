-- ----------------------------
-- Table structure for rbac_group_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `rbac_group_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '承租人id',
  `group_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户组id',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色id',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否已删除',
  `attribute` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余属性',
  `version` bigint(20) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_role_id`(`group_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户组角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rbac_group_role
-- ----------------------------
INSERT INTO `rbac_group_role` VALUES (1, 1, 1, 3, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_group_role` VALUES (2, 1, 2, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for rbac_group_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `rbac_group_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '承租人id',
  `group_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户组id',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户id',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否已删除',
  `attribute` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余属性',
  `version` bigint(20) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_user_id`(`user_id`, `group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户组用户关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rbac_group_user
-- ----------------------------
INSERT INTO `rbac_group_user` VALUES (1, 1, 1, 2, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_group_user` VALUES (2, 1, 2, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for rbac_org
-- ----------------------------
CREATE TABLE IF NOT EXISTS `rbac_org`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '承租人id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名',
  `code` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织代码',
  `path` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织级联路径(格式：父path_当前path)',
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '父id',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `attribute` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余属性',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否已删除',
  `version` bigint(20) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rbac_org
-- ----------------------------
INSERT INTO `rbac_org` VALUES (1, 1, '葫芦科技', 'hlkj', '01', 0, NULL, NULL, 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_org` VALUES (2, 1, '技术部', 'jsb', '01_01', 1, NULL, NULL, 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_org` VALUES (3, 1, '产品部', 'cpb', '01_02', 1, NULL, NULL, 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_org` VALUES (4, 1, '研发部', 'dev', '01_01_01', 2, NULL, NULL, 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_org` VALUES (5, 1, '测试部', 'test', '01_01_02', 2, NULL, NULL, 0, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for rbac_org_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `rbac_org_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '承租人id',
  `org_id` bigint(20) UNSIGNED NOT NULL COMMENT '组织id',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色id',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否已删除',
  `attribute` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余属性',
  `version` bigint(20) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_org_role_id`(`org_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rbac_org_role
-- ----------------------------
INSERT INTO `rbac_org_role` VALUES (1, 1, 2, 3, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_org_role` VALUES (2, 1, 2, 4, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_org_role` VALUES (3, 1, 4, 4, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_org_role` VALUES (4, 1, 5, 5, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_org_role` VALUES (5, 1, 1, 4, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_org_role` VALUES (6, 1, 1, 5, 0, NULL, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for rbac_org_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `rbac_org_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '承租人id',
  `org_id` bigint(20) UNSIGNED NOT NULL COMMENT '组织id',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户id',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否已删除',
  `attribute` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余属性',
  `version` bigint(20) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_org_id`(`user_id`, `org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户组织关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rbac_org_user
-- ----------------------------
INSERT INTO `rbac_org_user` VALUES (1, 1, 4, 2, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_org_user` VALUES (2, 1, 1, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for rbac_permission
-- ----------------------------
CREATE TABLE IF NOT EXISTS `rbac_permission`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '承租人id',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否已删除',
  `attribute` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余属性',
  `version` bigint(20) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rbac_permission
-- ----------------------------
INSERT INTO `rbac_permission` VALUES (1, 1, 'READ', '阅读权限', NULL, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_permission` VALUES (2, 1, 'WRITE', '写权限', NULL, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_permission` VALUES (3, 1, 'DEV', '开发权限', NULL, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_permission` VALUES (4, 1, 'TEST', '测试权限', NULL, 0, NULL, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for rbac_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `rbac_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '承租人id',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否已删除',
  `attribute` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余属性',
  `version` bigint(20) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rbac_role
-- ----------------------------
INSERT INTO `rbac_role` VALUES (1, 1, 'ADMIN', '管理员角色', NULL, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role` VALUES (2, 1, 'EMPLOYEE', '员工角色', NULL, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role` VALUES (3, 1, 'USER', '用户角色', '注册的普通用户', 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role` VALUES (4, 1, 'DEV', '开发角色', NULL, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role` VALUES (5, 1, 'TEST', '测试角色', NULL, 0, NULL, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for rbac_role_permission
-- ----------------------------
CREATE TABLE IF NOT EXISTS `rbac_role_permission`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '承租人id',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) UNSIGNED NOT NULL COMMENT '权限id',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否已删除',
  `attribute` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余属性',
  `version` bigint(20) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_per_id`(`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rbac_role_permission
-- ----------------------------
INSERT INTO `rbac_role_permission` VALUES (1, 1, 1, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role_permission` VALUES (2, 1, 1, 2, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role_permission` VALUES (3, 1, 2, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role_permission` VALUES (4, 1, 3, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role_permission` VALUES (5, 1, 1, 3, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role_permission` VALUES (6, 1, 4, 3, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role_permission` VALUES (7, 1, 5, 4, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role_permission` VALUES (8, 1, 1, 4, 0, NULL, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for rbac_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `rbac_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '承租人id',
  `age` int(5) UNSIGNED NULL DEFAULT NULL COMMENT '年龄',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `pin_yin` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别（M-男，F-女，X-未知）',
  `birth` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `photo_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否已删除',
  `attribute` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余属性',
  `version` bigint(20) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rbac_user
-- ----------------------------
INSERT INTO `rbac_user` VALUES (1, 1, 100, '管理员', 'GuanLiYuan', 'M', '2020-01-01 01:01:01', 'admin', '66bafb9fb704934fd46fdd25d1d38326', 'admin', NULL, '110110', NULL, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_user` VALUES (2, 1, 28, '葫芦胡', 'HuLuHu', 'M', '2020-01-01 01:01:01', 'gourd', '8739597c7a09b9f5bff69760fd0b571b', 'gourd', NULL, '13584278267', NULL, 0, NULL, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for rbac_user_group
-- ----------------------------
CREATE TABLE IF NOT EXISTS `rbac_user_group`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '承租人id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户组名',
  `code` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户组代码',
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '父id',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否已删除',
  `attribute` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余属性',
  `version` bigint(20) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rbac_user_group
-- ----------------------------
INSERT INTO `rbac_user_group` VALUES (1, 1, '普通用户', 'common_user', 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_user_group` VALUES (2, 1, 'VIP用户', 'vip_user', 0, 0, NULL, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for rbac_user_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `rbac_user_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '承租人id',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户id',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色id',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否已删除',
  `attribute` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余属性',
  `version` bigint(20) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_role_id`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rbac_user_role
-- ----------------------------
INSERT INTO `rbac_user_role` VALUES (1, 1, 1, 1, 0, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_user_role` VALUES (2, 1, 2, 3, 0, NULL, 0, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_tenant`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '号码',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否已删除',
  `attribute` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余属性',
  `version` bigint(20) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '承租人表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES (1, '1001', 'gourd', '葫芦胡', '葫芦胡', 0, NULL, 1, '2020-01-01 13:31:09', 1, '2020-01-01 13:31:33', 1);
