

CREATE TABLE IF NOT EXISTS `rbac_depart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '承租人id',
  `name` varchar(255) DEFAULT NULL COMMENT '部门名',
  `code` char(10)  NOT NULL COMMENT '部门代码',
  `path` char(10)  NOT NULL COMMENT '部门级联路径(格式：父path_当前path)',
  `parent_id` bigint(20) NOT NULL COMMENT '父id',
  `description` varchar(500)  COMMENT '备注',
  `attribute` varchar(500)  default null COMMENT '冗余属性',
  `is_deleted` tinyint(1) default '0' COMMENT '是否已删除',
  `version` bigint(20) DEFAULT '1' COMMENT '版本号',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

CREATE TABLE IF NOT EXISTS `rbac_user_depart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '承租人id',
  `dept_id` bigint(20) NOT NULL COMMENT '部门id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `is_deleted` tinyint(1) default '0' COMMENT '是否已删除',
  `attribute` varchar(500)  default null COMMENT '冗余属性',
  `version` bigint(20) DEFAULT '1' COMMENT '版本号',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户部门关系表';

-- 初始化部门
INSERT INTO `rbac_depart`(`id`, `tenant_id`, `name`, `code`, `path`, `parent_id`, `description`, `attribute`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES (1, 1, '葫芦科技', '01', '01', 0, '葫芦科技', NULL, 0, 1, '2020-01-01 13:48:01', 1, '2020-01-01 13:48:09', 1);
INSERT INTO `rbac_depart`(`id`, `tenant_id`, `name`, `code`, `path`, `parent_id`, `description`, `attribute`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES (2, 1, '技术部', '0101', '01-01', 1, '技术部', NULL, 0, 1, '2020-01-01 13:49:01', 1, '2020-01-01 13:49:06', 1);
INSERT INTO `rbac_depart`(`id`, `tenant_id`, `name`, `code`, `path`, `parent_id`, `description`, `attribute`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES (3, 1, '产品部', '0102', '01-02', 1, '产品部', NULL, 0, 1, '2020-01-01 13:49:01', 1, '2020-01-01 13:49:06', 1);

-- 初始化部门用户关系
INSERT INTO `rbac_user_depart`(`id`, `tenant_id`, `dept_id`, `user_id`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES (1, 1, 1, 1, 0, 1, '2020-01-01 13:52:41', 1, '2020-01-09 13:52:46', 1);
INSERT INTO `rbac_user_depart`(`id`, `tenant_id`, `dept_id`, `user_id`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES (2, 1, 2, 2, 0, 1, '2020-01-01 13:52:41', 1, '2020-01-09 13:52:46', 1);
