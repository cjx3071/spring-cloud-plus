-- 权限密钥表
CREATE TABLE IF NOT EXISTS `sys_secret` (
  `id` bigint(20) unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_key` varchar(100) DEFAULT NULL COMMENT 'appkey',
  `secret_key` varchar(100) DEFAULT NULL COMMENT '秘钥',
  `expire_times` int(10) unsigned  DEFAULT NULL COMMENT '过期时长，单位毫秒',
  `is_deleted` tinyint(1) unsigned  DEFAULT '0' COMMENT '是否已删除',
  `version` bigint(20) unsigned  DEFAULT '1' COMMENT '版本号',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) unsigned  DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) unsigned  DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_key` (`app_key`,`secret_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='权限密钥表';