-- 建表sql（role）
CREATE TABLE IF NOT EXISTS `sys_tenant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `number` varchar(4)  NOT NULL COMMENT '号码',
  `code` varchar(50)  NOT NULL COMMENT '代码',
  `name` varchar(100)  NOT NULL COMMENT '名称',
  `description` varchar(100)  COMMENT '描述',
  `is_deleted` tinyint(1) default '0' COMMENT '是否已删除',
  `attribute` varchar(500)  default null COMMENT '冗余属性',
  `version` bigint(20) DEFAULT '1' COMMENT '版本号',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='承租人表';


CREATE TABLE IF NOT EXISTS `rbac_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '承租人id',
  `age` int(5) DEFAULT NULL COMMENT '年龄',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `pin_yin` varchar(255) DEFAULT NULL COMMENT '拼音',
  `sex` char(1) DEFAULT NULL COMMENT '性别（M-男，F-女，X-未知）',
  `birth` datetime DEFAULT NULL COMMENT '生日',
  `account` varchar(20)  NOT NULL COMMENT '账号',
  `password` varchar(500) NOT NULL COMMENT '密码',
  `nick_name` varchar(20)  COMMENT '昵称',
  `email` varchar(100)  COMMENT '邮箱',
  `mobile_phone` varchar(20) COMMENT '手机',
  `photo_url` varchar(500) COMMENT '头像地址',
  `is_deleted` tinyint(1) default '0' COMMENT '是否已删除',
  `attribute` varchar(500)  default null COMMENT '冗余属性',
  `version` bigint(20) DEFAULT '1' COMMENT '版本号',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';



-- 建表sql（role）
CREATE TABLE IF NOT EXISTS `rbac_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '承租人id',
  `code` varchar(50)  NOT NULL COMMENT '代码',
  `name` varchar(100)  NOT NULL COMMENT '名称',
  `description` varchar(100)  COMMENT '描述',
  `is_deleted` tinyint(1) default '0' COMMENT '是否已删除',
  `attribute` varchar(500)  default null COMMENT '冗余属性',
  `version` bigint(20) DEFAULT '1' COMMENT '版本号',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';


-- 建表sql（permission）
CREATE TABLE IF NOT EXISTS `rbac_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '承租人id',
  `code` varchar(50)  NOT NULL COMMENT '代码',
  `name` varchar(100)  NOT NULL COMMENT '名称',
  `description` varchar(500) COMMENT '描述' ,
  `is_deleted` tinyint(1) default '0' COMMENT '是否已删除',
  `attribute` varchar(500)  default null COMMENT '冗余属性',
  `version` bigint(20) DEFAULT '1' COMMENT '版本号',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';


-- 建表sql（user_role）
CREATE TABLE IF NOT EXISTS `rbac_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '承租人id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `is_deleted` tinyint(1) default '0' COMMENT '是否已删除',
  `attribute` varchar(500)  default null COMMENT '冗余属性',
  `version` bigint(20) DEFAULT '1' COMMENT '版本号',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';


-- 建表sql（role_permission）
CREATE TABLE IF NOT EXISTS `rbac_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '承租人id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
	`permission_id` bigint(20) NOT NULL COMMENT '权限id',
	`is_deleted` tinyint(1) default '0' COMMENT '是否已删除',
	`attribute` varchar(500)  default null COMMENT '冗余属性',
	`version` bigint(20) DEFAULT '1' COMMENT '版本号',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
	PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- 初始化承租人
INSERT INTO `sys_tenant`(`id`, `number`,`code`, `name`, `description`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`)
 VALUES (1, '1001','gourd', '葫芦胡', '葫芦胡', 0, 1, '2020-01-01 13:31:09', 1, '2020-01-01 13:31:33', 1);


-- 初始化用户
INSERT INTO `rbac_user`(`id`,`tenant_id`,`age`,`sex`, `name`, `pin_yin`,`birth`, `account`, `password`, `nick_name`, `email`,  `mobile_phone`, `photo_url`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`)
VALUES (1,1, 100, 'M','管理员','GuanLiYuan', '2020-01-01 01:01:01', 'admin', '$2a$10$o1avyPI98TdBco3m7JgCTuhPQaasSy/J2EqDH9XX46rxggjviWMzO', 'admin', NULL, '13584278267', NULL, 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_user`(`id`,`tenant_id`,`age`,`sex`, `name`, `pin_yin`,`birth`, `account`, `password`, `nick_name`, `email`,  `mobile_phone`, `photo_url`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`)
VALUES (3,1, 100, 'M','管理员','GuanLiYuan', '2020-01-01 01:01:01', 'adminShiro', 'b4b13764094be09ca79830b829d03de3', 'adminShiro', NULL, '13584278267', NULL, 0, 1, NULL, NULL, NULL, NULL);


INSERT INTO `rbac_user`(`id`,`tenant_id`,`age`,`sex`, `name`, `pin_yin`,`birth`, `account`, `password`, `nick_name`, `email`,  `mobile_phone`, `photo_url`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`)
VALUES (2,1, 28, 'M','葫芦胡', 'HuLuHu','2020-01-01 01:01:01', 'gourd', '$2a$10$o1avyPI98TdBco3m7JgCTuhPQaasSy/J2EqDH9XX46rxggjviWMzO', 'gourd', NULL, '13584278267', NULL, 0, 1, NULL, NULL, NULL, NULL);

-- 初始化角色
INSERT INTO `rbac_role`(`id`,`tenant_id`, `code`, `name`, `description`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`, `is_deleted`) VALUES (1,1, 'ADMIN', '管理员', NULL, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `rbac_role`(`id`,`tenant_id`,`code`, `name`, `description`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`, `is_deleted`) VALUES (2,1, 'EMPLOYEE', '员工', NULL, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `rbac_role`(`id`,`tenant_id`,`code`, `name`, `description`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`, `is_deleted`) VALUES (3,1,'USER', '用户', '注册的普通用户', 1, NULL, NULL, NULL, NULL, 0);

-- 初始化用户角色
INSERT INTO `rbac_user_role`(`id`,`tenant_id`, `user_id`, `role_id`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`, `is_deleted`) VALUES (1,1, 1, 1, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `rbac_user_role`(`id`,`tenant_id`,`user_id`, `role_id`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`, `is_deleted`) VALUES (2,1, 2, 3, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `rbac_user_role`(`id`,`tenant_id`,`user_id`, `role_id`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`, `is_deleted`) VALUES (3,1, 3, 1, 0, NULL, NULL, NULL, NULL, 0);

-- 初始化权限
INSERT INTO `rbac_permission`(`id`,`tenant_id`, `code`, `name`, `description`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES (1, 1,'READ', '阅读权限', NULL, 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_permission`(`id`,`tenant_id`, `code`, `name`, `description`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES (2, 1,'WRITE', '写权限', NULL, 0, 1, NULL, NULL, NULL, NULL);

-- 初始化角色权限
INSERT INTO `rbac_role_permission`(`id`,`tenant_id`, `role_id`, `permission_id`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES (1,1, 1, 1, 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role_permission`(`id`,`tenant_id`, `role_id`, `permission_id`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES (2,1, 1, 2, 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role_permission`(`id`,`tenant_id`, `role_id`, `permission_id`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES (3,1, 2, 1, 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `rbac_role_permission`(`id`,`tenant_id`, `role_id`, `permission_id`, `is_deleted`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES (4,1, 3, 1, 0, 1, NULL, NULL, NULL, NULL);



