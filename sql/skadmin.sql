/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : eladmin

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 16/07/2020 19:47:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for code_column_config
-- ----------------------------
DROP TABLE IF EXISTS `code_column_config`;
CREATE TABLE `code_column_config` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(255) DEFAULT NULL,
  `column_name` varchar(255) DEFAULT NULL,
  `column_type` varchar(255) DEFAULT NULL,
  `dict_name` varchar(255) DEFAULT NULL,
  `extra` varchar(255) DEFAULT NULL,
  `form_show` bit(1) DEFAULT NULL,
  `form_type` varchar(255) DEFAULT NULL,
  `key_type` varchar(255) DEFAULT NULL,
  `list_show` bit(1) DEFAULT NULL,
  `not_null` bit(1) DEFAULT NULL,
  `query_type` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `date_annotation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`column_id`) USING BTREE,
  KEY `idx_table_name` (`table_name`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成字段信息存储';

-- ----------------------------
-- Records of code_column_config
-- ----------------------------
BEGIN;
INSERT INTO `code_column_config` VALUES (187, 'sys_roles_menus', 'menu_id', 'bigint', NULL, '', b'1', NULL, 'PRI', b'1', b'1', NULL, '菜单ID', NULL);
INSERT INTO `code_column_config` VALUES (188, 'sys_roles_menus', 'role_id', 'bigint', NULL, '', b'1', NULL, 'PRI', b'1', b'1', NULL, '角色ID', NULL);
INSERT INTO `code_column_config` VALUES (189, 'sys_log', 'log_id', 'bigint', NULL, 'auto_increment', b'1', NULL, 'PRI', b'1', b'0', NULL, 'ID', NULL);
INSERT INTO `code_column_config` VALUES (190, 'sys_log', 'description', 'varchar', 'user_status', '', b'1', 'Input', '', b'1', b'0', 'Like', '描述', 'CreationTimestamp');
INSERT INTO `code_column_config` VALUES (191, 'sys_log', 'log_type', 'varchar', 'user_status', '', b'1', 'Select', 'MUL', b'1', b'0', '=', '日志类型', 'CreationTimestamp');
INSERT INTO `code_column_config` VALUES (192, 'sys_log', 'method', 'varchar', NULL, '', b'1', NULL, '', b'1', b'0', NULL, '', NULL);
INSERT INTO `code_column_config` VALUES (193, 'sys_log', 'params', 'text', NULL, '', b'1', NULL, '', b'1', b'0', NULL, '', NULL);
INSERT INTO `code_column_config` VALUES (194, 'sys_log', 'request_ip', 'varchar', NULL, '', b'1', NULL, '', b'1', b'0', NULL, '', NULL);
INSERT INTO `code_column_config` VALUES (195, 'sys_log', 'time', 'bigint', NULL, '', b'1', NULL, '', b'1', b'0', NULL, '', NULL);
INSERT INTO `code_column_config` VALUES (196, 'sys_log', 'username', 'varchar', NULL, '', b'1', NULL, '', b'1', b'0', NULL, '', NULL);
INSERT INTO `code_column_config` VALUES (197, 'sys_log', 'address', 'varchar', NULL, '', b'1', NULL, '', b'1', b'0', NULL, '', NULL);
INSERT INTO `code_column_config` VALUES (198, 'sys_log', 'browser', 'varchar', NULL, '', b'1', NULL, '', b'1', b'0', NULL, '', NULL);
INSERT INTO `code_column_config` VALUES (199, 'sys_log', 'exception_detail', 'text', NULL, '', b'1', NULL, '', b'1', b'0', NULL, '', NULL);
INSERT INTO `code_column_config` VALUES (200, 'sys_log', 'create_time', 'datetime', NULL, '', b'1', NULL, 'MUL', b'1', b'0', NULL, '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for code_gen_config
-- ----------------------------
DROP TABLE IF EXISTS `code_gen_config`;
CREATE TABLE `code_gen_config` (
  `config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(255) DEFAULT NULL COMMENT '表名',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `cover` bit(1) DEFAULT NULL COMMENT '是否覆盖',
  `module_name` varchar(255) DEFAULT NULL COMMENT '模块名称',
  `pack` varchar(255) DEFAULT NULL COMMENT '至于哪个包下',
  `path` varchar(255) DEFAULT NULL COMMENT '前端代码生成的路径',
  `api_path` varchar(255) DEFAULT NULL COMMENT '前端Api文件路径',
  `prefix` varchar(255) DEFAULT NULL COMMENT '表前缀',
  `api_alias` varchar(255) DEFAULT NULL COMMENT '接口名称',
  PRIMARY KEY (`config_id`) USING BTREE,
  KEY `idx_table_name` (`table_name`(100))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成器配置';

-- ----------------------------
-- Records of code_gen_config
-- ----------------------------
BEGIN;
INSERT INTO `code_gen_config` VALUES (6, 'sys_log', 'dxj', b'0', 'test', 'com.dxj.test', 'api', 'api/', NULL, 'TestApi');
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '上级部门',
  `sub_count` int(5) DEFAULT '0' COMMENT '子部门数目',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `dept_sort` int(5) DEFAULT '999' COMMENT '排序',
  `enabled` bit(1) NOT NULL COMMENT '状态',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE,
  KEY `inx_pid` (`pid`),
  KEY `inx_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (2, 7, 0, '研发部', 3, b'1', NULL, 'admin', '2019-03-25 09:15:32', '2020-05-10 17:37:58');
INSERT INTO `sys_dept` VALUES (5, 7, 0, '运维部', 4, b'1', NULL, NULL, '2019-03-25 09:20:44', NULL);
INSERT INTO `sys_dept` VALUES (6, 8, 0, '测试部', 6, b'1', NULL, NULL, '2019-03-25 09:52:18', NULL);
INSERT INTO `sys_dept` VALUES (7, NULL, 2, '华南分部', 0, b'1', NULL, 'admin', '2019-03-25 11:04:50', '2020-05-10 19:59:12');
INSERT INTO `sys_dept` VALUES (8, NULL, 2, '华北分部', 1, b'1', NULL, 'admin', '2019-03-25 11:04:53', '2020-05-14 12:54:00');
INSERT INTO `sys_dept` VALUES (15, 8, 0, 'UI部门', 7, b'1', 'admin', 'admin', '2020-05-13 22:56:53', '2020-05-14 12:54:13');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '字典名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES (1, 'user_status', '用户状态', NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict` VALUES (4, 'dept_status', '部门状态', NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict` VALUES (5, 'job_status', '岗位状态', NULL, NULL, '2019-10-27 20:31:36', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail` (
  `detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dict_id` bigint(11) DEFAULT NULL COMMENT '字典id',
  `label` varchar(255) NOT NULL COMMENT '字典标签',
  `value` varchar(255) NOT NULL COMMENT '字典值',
  `dict_sort` int(5) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`detail_id`) USING BTREE,
  KEY `FK5tpkputc6d9nboxojdbgnpmyb` (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典详情';

-- ----------------------------
-- Records of sys_dict_detail
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_detail` VALUES (1, 1, '激活', 'true', 1, NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict_detail` VALUES (2, 1, '禁用', 'false', 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_detail` VALUES (3, 4, '启用', 'true', 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_detail` VALUES (4, 4, '停用', 'false', 2, NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict_detail` VALUES (5, 5, '启用', 'true', 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_detail` VALUES (6, 5, '停用', 'false', 2, NULL, NULL, '2019-10-27 20:31:36', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '岗位名称',
  `enabled` bit(1) NOT NULL COMMENT '岗位状态',
  `job_sort` int(5) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`job_id`) USING BTREE,
  UNIQUE KEY `uniq_name` (`name`),
  KEY `inx_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='岗位';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
BEGIN;
INSERT INTO `sys_job` VALUES (8, '人事专员', b'1', 3, NULL, NULL, '2019-03-29 14:52:28', NULL);
INSERT INTO `sys_job` VALUES (10, '产品经理', b'1', 4, NULL, NULL, '2019-03-29 14:55:51', NULL);
INSERT INTO `sys_job` VALUES (11, '全栈开发', b'1', 2, NULL, 'admin', '2019-03-31 13:39:30', '2020-05-05 11:33:43');
INSERT INTO `sys_job` VALUES (12, '软件测试', b'1', 5, NULL, 'admin', '2019-03-31 13:39:43', '2020-05-10 19:56:26');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `description` varchar(255) DEFAULT NULL,
  `log_type` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `params` text,
  `request_ip` varchar(255) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `browser` varchar(255) DEFAULT NULL,
  `exception_detail` text,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`log_id`) USING BTREE,
  KEY `log_create_time_index` (`create_time`),
  KEY `inx_log_type` (`log_type`)
) ENGINE=InnoDB AUTO_INCREMENT=2743 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` VALUES (2573, '查询字典', 'INFO', 'com.dxj.module.system.controller.DictController.queryAll()', '{ }', '192.168.31.247', 63, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:23:07');
INSERT INTO `sys_log` VALUES (2574, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 49, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:25:52');
INSERT INTO `sys_log` VALUES (2575, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 39, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:25:52');
INSERT INTO `sys_log` VALUES (2576, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 20, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:25:52');
INSERT INTO `sys_log` VALUES (2577, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=8, pidIsNull=null, createTime=null)  }', '192.168.31.247', 9, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:25:56');
INSERT INTO `sys_log` VALUES (2578, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=7, pidIsNull=null, createTime=null)  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:26:00');
INSERT INTO `sys_log` VALUES (2579, '查询角色', 'INFO', 'com.dxj.module.system.controller.RoleController.query()', '{RoleQuery(blurry=null, createTime=null) Page request [number: 0, size 10, sort: level: ASC]  }', '192.168.31.247', 30, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:26:17');
INSERT INTO `sys_log` VALUES (2580, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:26:36');
INSERT INTO `sys_log` VALUES (2581, '删除菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.delete()', '{[90]  }', '192.168.31.247', 345, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:26:40');
INSERT INTO `sys_log` VALUES (2582, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:26:40');
INSERT INTO `sys_log` VALUES (2583, '删除菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.delete()', '{[21]  }', '192.168.31.247', 78, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:26:46');
INSERT INTO `sys_log` VALUES (2584, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:26:46');
INSERT INTO `sys_log` VALUES (2585, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=null, pid=10)  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:26:49');
INSERT INTO `sys_log` VALUES (2586, '删除菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.delete()', '{[10]  }', '192.168.31.247', 83, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:26:53');
INSERT INTO `sys_log` VALUES (2587, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:26:53');
INSERT INTO `sys_log` VALUES (2588, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 3, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:26:57');
INSERT INTO `sys_log` VALUES (2589, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=null, pid=36)  }', '192.168.31.247', 7, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:27:07');
INSERT INTO `sys_log` VALUES (2590, '删除菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.delete()', '{[19]  }', '192.168.31.247', 21, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:27:15');
INSERT INTO `sys_log` VALUES (2591, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:27:15');
INSERT INTO `sys_log` VALUES (2592, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=null, pid=36)  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:27:17');
INSERT INTO `sys_log` VALUES (2593, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=null, pid=6)  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:27:53');
INSERT INTO `sys_log` VALUES (2594, '删除菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.delete()', '{[80]  }', '192.168.31.247', 18, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:27:58');
INSERT INTO `sys_log` VALUES (2595, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 3, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:27:58');
INSERT INTO `sys_log` VALUES (2596, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 3, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:28:01');
INSERT INTO `sys_log` VALUES (2597, '查询图片', 'INFO', 'com.dxj.controller.PictureController.query()', '{PictureQuery(filename=null, username=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 7, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-04 15:28:12');
INSERT INTO `sys_log` VALUES (2598, '用户登录', 'INFO', 'com.dxj.module.security.controller.AuthController.login()', '{{username=admin, password= ******} SecurityContextHolderAwareRequestWrapper[ org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@780ebf7c]  }', '192.168.31.247', 562, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:12:23');
INSERT INTO `sys_log` VALUES (2599, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 29, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:12:31');
INSERT INTO `sys_log` VALUES (2600, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 55, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:12:31');
INSERT INTO `sys_log` VALUES (2601, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 68, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:12:31');
INSERT INTO `sys_log` VALUES (2602, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=7, pidIsNull=null, createTime=null)  }', '192.168.31.247', 7, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:12:34');
INSERT INTO `sys_log` VALUES (2603, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[2], blurry=null, enabled=null, deptId=2, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 35, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:12:35');
INSERT INTO `sys_log` VALUES (2604, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[5], blurry=null, enabled=null, deptId=5, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 11, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:12:37');
INSERT INTO `sys_log` VALUES (2605, '查询角色', 'INFO', 'com.dxj.module.system.controller.RoleController.query()', '{RoleQuery(blurry=null, createTime=null) Page request [number: 0, size 10, sort: level: ASC]  }', '192.168.31.247', 22, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:12:41');
INSERT INTO `sys_log` VALUES (2606, '查询角色', 'INFO', 'com.dxj.module.system.controller.RoleController.query()', '{RoleQuery(blurry=null, createTime=null) Page request [number: 0, size 10, sort: level: ASC]  }', '192.168.31.247', 94, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:30:06');
INSERT INTO `sys_log` VALUES (2607, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 36, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:30:13');
INSERT INTO `sys_log` VALUES (2608, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 19, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:30:13');
INSERT INTO `sys_log` VALUES (2609, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 41, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:30:13');
INSERT INTO `sys_log` VALUES (2610, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 25, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:30:16');
INSERT INTO `sys_log` VALUES (2611, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 7, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:30:16');
INSERT INTO `sys_log` VALUES (2612, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.getSuperior()', '{[2]  }', '192.168.31.247', 50, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:30:16');
INSERT INTO `sys_log` VALUES (2613, '用户登录', 'INFO', 'com.dxj.module.security.controller.AuthController.login()', '{{username=admin, password= ******} SecurityContextHolderAwareRequestWrapper[ org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@5971dbd8]  }', '192.168.31.247', 225, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:31:01');
INSERT INTO `sys_log` VALUES (2614, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:31:01');
INSERT INTO `sys_log` VALUES (2615, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 16, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:31:01');
INSERT INTO `sys_log` VALUES (2616, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:31:01');
INSERT INTO `sys_log` VALUES (2617, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 7, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:31:04');
INSERT INTO `sys_log` VALUES (2618, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.getSuperior()', '{[2]  }', '192.168.31.247', 21, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:31:04');
INSERT INTO `sys_log` VALUES (2619, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:31:04');
INSERT INTO `sys_log` VALUES (2620, '查询角色', 'INFO', 'com.dxj.module.system.controller.RoleController.query()', '{RoleQuery(blurry=null, createTime=null) Page request [number: 0, size 10, sort: level: ASC]  }', '192.168.31.247', 17, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:31:54');
INSERT INTO `sys_log` VALUES (2621, '修改角色', 'INFO', 'com.dxj.module.system.controller.RoleController.update()', '{com.dxj.module.system.domain.entity.Role@5dbc9524[id=1,\n,users=<null>,\n,menus=[com.dxj.module.system.domain.entity.Menu@7572e48b[id=1,\n,roles=<null>,\n,title=系统管理,\n,componentName=<null>,\n,menuSort=1,\n,component=<null>,\n,path=system,\n,type=0,\n,permission=<null>,\n,icon=system,\n,cache=false,\n,hidden=false,\n,pid=<null>,\n,subCount=7,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@471fd30a[id=2,\n,roles=<null>,\n,title=用户管理,\n,componentName=User,\n,menuSort=2,\n,component=system/user/index,\n,path=user,\n,type=1,\n,permission=user:list,\n,icon=peoples,\n,cache=false,\n,hidden=false,\n,pid=1,\n,subCount=3,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@48e957a1[id=3,\n,roles=<null>,\n,title=角色管理,\n,componentName=Role,\n,menuSort=3,\n,component=system/role/index,\n,path=role,\n,type=1,\n,permission=roles:list,\n,icon=role,\n,cache=false,\n,hidden=false,\n,pid=1,\n,subCount=3,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@402a99f0[id=35,\n,roles=<null>,\n,title=部门管理,\n,componentName=Dept,\n,menuSort=6,\n,component=system/dept/index,\n,path=dept,\n,type=1,\n,permission=dept:list,\n,icon=dept,\n,cache=false,\n,hidden=false,\n,pid=1,\n,subCount=3,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@221e7bc2[id=36,\n,roles=<null>,\n,title=系统工具,\n,componentName=<null>,\n,menuSort=30,\n,component=,\n,path=sys-tools,\n,type=0,\n,permission=<null>,\n,icon=sys-tools,\n,cache=false,\n,hidden=false,\n,pid=<null>,\n,subCount=7,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@1b207b08[id=5,\n,roles=<null>,\n,title=菜单管理,\n,componentName=Menu,\n,menuSort=5,\n,component=system/menu/index,\n,path=menu,\n,type=1,\n,permission=menu:list,\n,icon=menu,\n,cache=false,\n,hidden=false,\n,pid=1,\n,subCount=3,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@19766392[id=37,\n,roles=<null>,\n,title=岗位管理,\n,componentName=Job,\n,menuSort=7,\n,component=system/job/index,\n,path=job,\n,type=1,\n,permission=job:list,\n,icon=Steve-Jobs,\n,cache=false,\n,hidden=false,\n,pid=1,\n,subCount=3,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@d878c55[id=6,\n,roles=<null>,\n,title=系统监控,\n,componentName=<null>,\n,menuSort=10,\n,component=<null>,\n,path=monitor,\n,type=0,\n,permission=<null>,\n,icon=monitor,\n,cache=false,\n,hidden=false,\n,pid=<null>,\n,subCount=4,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@5c96364c[id=38,\n,roles=<null>,\n,title=接口文档,\n,componentName=Swagger,\n,menuSort=36,\n,component=tools/swagger/index,\n,path=swagger2,\n,type=1,\n,permission=<null>,\n,icon=swagger,\n,cache=false,\n,hidden=false,\n,pid=36,\n,subCount=0,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@44f6feed[id=7,\n,roles=<null>,\n,title=操作日志,\n,componentName=Log,\n,menuSort=11,\n,component=monitor/log/index,\n,path=logs,\n,type=1,\n,permission=<null>,\n,icon=log,\n,cache=false,\n,hidden=false,\n,pid=6,\n,subCount=0,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@6eb67f12[id=39,\n,roles=<null>,\n,title=字典管理,\n,componentName=Dict,\n,menuSort=8,\n,component=system/dict/index,\n,path=dict,\n,type=1,\n,permission=dict:list,\n,icon=dictionary,\n,cache=false,\n,hidden=false,\n,pid=1,\n,subCount=3,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@5fe2a3b5[id=9,\n,roles=<null>,\n,title=SQL监控,\n,componentName=Sql,\n,menuSort=18,\n,component=monitor/sql/index,\n,path=druid,\n,type=1,\n,permission=<null>,\n,icon=sqlMonitor,\n,cache=false,\n,hidden=false,\n,pid=6,\n,subCount=0,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@322bda53[id=41,\n,roles=<null>,\n,title=在线用户,\n,componentName=OnlineUser,\n,menuSort=10,\n,component=monitor/online/index,\n,path=online,\n,type=1,\n,permission=<null>,\n,icon=Steve-Jobs,\n,cache=false,\n,hidden=false,\n,pid=6,\n,subCount=0,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@41595b6d[id=14,\n,roles=<null>,\n,title=邮件工具,\n,componentName=Email,\n,menuSort=35,\n,component=tools/email/index,\n,path=email,\n,type=1,\n,permission=<null>,\n,icon=email,\n,cache=false,\n,hidden=false,\n,pid=36,\n,subCount=0,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@1c4ae2bb[id=16,\n,roles=<null>,\n,title=图床管理,\n,componentName=Pictures,\n,menuSort=33,\n,component=tools/picture/index,\n,path=pictures,\n,type=1,\n,permission=pictures:list,\n,icon=image,\n,cache=false,\n,hidden=false,\n,pid=36,\n,subCount=2,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@56ff058c[id=18,\n,roles=<null>,\n,title=存储管理,\n,componentName=Storage,\n,menuSort=34,\n,component=tools/storage/index,\n,path=storage,\n,type=1,\n,permission=storage:list,\n,icon=qiniu,\n,cache=false,\n,hidden=false,\n,pid=36,\n,subCount=3,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@3aeaa442[id=82,\n,roles=<null>,\n,title=生成配置,\n,componentName=GeneratorConfig,\n,menuSort=33,\n,component=generator/config,\n,path=generator/config/:tableName,\n,type=1,\n,permission=,\n,icon=dev,\n,cache=true,\n,hidden=true,\n,pid=36,\n,subCount=0,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@31631241[id=116,\n,roles=<null>,\n,title=生成预览,\n,componentName=Preview,\n,menuSort=999,\n,component=generator/preview,\n,path=generator/preview/:tableName,\n,type=1,\n,permission=<null>,\n,icon=java,\n,cache=true,\n,hidden=true,\n,pid=36,\n,subCount=0,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@7af771ec[id=28,\n,roles=<null>,\n,title=任务调度,\n,componentName=Timing,\n,menuSort=999,\n,component=system/timing/index,\n,path=timing,\n,type=1,\n,permission=timing:list,\n,icon=timing,\n,cache=false,\n,hidden=false,\n,pid=1,\n,subCount=3,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@540ad85b[id=30,\n,roles=<null>,\n,title=代码生成,\n,componentName=GeneratorIndex,\n,menuSort=32,\n,component=generator/index,\n,path=generator,\n,type=1,\n,permission=<null>,\n,icon=dev,\n,cache=true,\n,hidden=false,\n,pid=36,\n,subCount=0,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@3badbe2c[id=32,\n,roles=<null>,\n,title=异常日志,\n,componentName=ErrorLog,\n,menuSort=12,\n,component=monitor/log/errorLog,\n,path=errorLog,\n,type=1,\n,permission=<null>,\n,icon=error,\n,cache=false,\n,hidden=false,\n,pid=6,\n,subCount=0,\n,iFrame=false,\n]],\n,depts=[],\n,name=超级管理员,\n,dataScope=全部,\n,level=1,\n,description=---,\n]  }', '192.168.31.247', 107, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:01');
INSERT INTO `sys_log` VALUES (2622, '查询角色', 'INFO', 'com.dxj.module.system.controller.RoleController.query()', '{RoleQuery(blurry=null, createTime=null) Page request [number: 0, size 10, sort: level: ASC]  }', '192.168.31.247', 14, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:01');
INSERT INTO `sys_log` VALUES (2623, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.getSuperior()', '{[7]  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:04');
INSERT INTO `sys_log` VALUES (2624, '修改角色', 'INFO', 'com.dxj.module.system.controller.RoleController.update()', '{com.dxj.module.system.domain.entity.Role@49768d12[id=2,\n,users=<null>,\n,menus=[com.dxj.module.system.domain.entity.Menu@7407550[id=1,\n,roles=<null>,\n,title=系统管理,\n,componentName=<null>,\n,menuSort=1,\n,component=<null>,\n,path=system,\n,type=0,\n,permission=<null>,\n,icon=system,\n,cache=false,\n,hidden=false,\n,pid=<null>,\n,subCount=7,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@40d322a[id=2,\n,roles=<null>,\n,title=用户管理,\n,componentName=User,\n,menuSort=2,\n,component=system/user/index,\n,path=user,\n,type=1,\n,permission=user:list,\n,icon=peoples,\n,cache=false,\n,hidden=false,\n,pid=1,\n,subCount=3,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@7aacb197[id=3,\n,roles=<null>,\n,title=角色管理,\n,componentName=Role,\n,menuSort=3,\n,component=system/role/index,\n,path=role,\n,type=1,\n,permission=roles:list,\n,icon=role,\n,cache=false,\n,hidden=false,\n,pid=1,\n,subCount=3,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@73955d59[id=36,\n,roles=<null>,\n,title=系统工具,\n,componentName=<null>,\n,menuSort=30,\n,component=,\n,path=sys-tools,\n,type=0,\n,permission=<null>,\n,icon=sys-tools,\n,cache=false,\n,hidden=false,\n,pid=<null>,\n,subCount=7,\n,iFrame=false,\n], com.dxj.module.system.domain.entity.Menu@777b5c8e[id=5,\n,roles=<null>,\n,title=菜单管理,\n,componentName=Menu,\n,menuSort=5,\n,component=system/menu/index,\n,path=menu,\n,type=1,\n,permission=menu:list,\n,icon=menu,\n,cache=false,\n,hidden=false,\n,pid=1,\n,subCount=3,\n,iFrame=false,\n]],\n,depts=[com.dxj.module.system.domain.entity.Dept@35b6f55e[id=7,\n,roles=toString builder encounter an error]],\n,name=普通用户,\n,dataScope=自定义,\n,level=2,\n,description=----,\n]  }', '192.168.31.247', 24, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:08');
INSERT INTO `sys_log` VALUES (2625, '查询角色', 'INFO', 'com.dxj.module.system.controller.RoleController.query()', '{RoleQuery(blurry=null, createTime=null) Page request [number: 0, size 10, sort: level: ASC]  }', '192.168.31.247', 17, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:08');
INSERT INTO `sys_log` VALUES (2626, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:10');
INSERT INTO `sys_log` VALUES (2627, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=null, pid=36)  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:16');
INSERT INTO `sys_log` VALUES (2628, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.getSuperior()', '{[30]  }', '192.168.31.247', 17, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:19');
INSERT INTO `sys_log` VALUES (2629, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=null, pid=1)  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:28');
INSERT INTO `sys_log` VALUES (2630, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:33');
INSERT INTO `sys_log` VALUES (2631, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 13, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:33');
INSERT INTO `sys_log` VALUES (2632, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:33');
INSERT INTO `sys_log` VALUES (2633, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 2, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:43');
INSERT INTO `sys_log` VALUES (2634, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=true, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 7, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:32:43');
INSERT INTO `sys_log` VALUES (2635, '用户登录', 'ERROR', 'me.zhengjie.modules.security.rest.AuthorizationController.login()', '{{username=admin, password= ******} SecurityContextHolderAwareRequestWrapper[ FirewalledRequest[ org.apache.catalina.connector.RequestFacade@3c100b10]]  }', '192.168.31.247', 220, 'admin', '内网IP', 'Chrome 8', 'org.springframework.security.authentication.InternalAuthenticationServiceException: com.alibaba.fastjson.JSONObject cannot be cast to me.zhengjie.modules.system.service.dto.UserDto\n	at org.springframework.security.authentication.dao.DaoAuthenticationProvider.retrieveUser(DaoAuthenticationProvider.java:123)\n	at org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider.authenticate(AbstractUserDetailsAuthenticationProvider.java:144)\n	at org.springframework.security.authentication.ProviderManager.authenticate(ProviderManager.java:174)\n	at me.zhengjie.modules.security.rest.AuthorizationController.login(AuthorizationController.java:91)\n	at me.zhengjie.modules.security.rest.AuthorizationController$$FastClassBySpringCGLIB$$f65d16c0.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:746)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88)\n	at me.zhengjie.aspect.LogAspect.logAround(LogAspect.java:68)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)\n	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)\n	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\n	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:93)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:688)\n	at me.zhengjie.modules.security.rest.AuthorizationController$$EnhancerBySpringCGLIB$$cddda5eb.login(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:215)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:142)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:102)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:800)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1038)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:942)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:998)\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:901)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:660)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:875)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:741)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:101)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at com.alibaba.druid.support.http.WebStatFilter.doFilter(WebStatFilter.java:124)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:320)\n	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:127)\n	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:91)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:119)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:137)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:111)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:170)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at me.zhengjie.modules.security.security.TokenFilter.doFilter(TokenFilter.java:90)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:96)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:116)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:66)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:105)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:56)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:215)\n	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:178)\n	at org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:357)\n	at org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:270)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:199)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:490)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408)\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\n	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:770)\n	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1415)\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:748)\nCaused by: java.lang.ClassCastException: com.alibaba.fastjson.JSONObject cannot be cast to me.zhengjie.modules.system.service.dto.UserDto\n	at me.zhengjie.modules.system.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$e76d39f1.findByName(<generated>)\n	at me.zhengjie.modules.security.service.UserDetailsServiceImpl.loadUserByUsername(UserDetailsServiceImpl.java:66)\n	at me.zhengjie.modules.security.service.UserDetailsServiceImpl.loadUserByUsername(UserDetailsServiceImpl.java:37)\n	at org.springframework.security.authentication.dao.DaoAuthenticationProvider.retrieveUser(DaoAuthenticationProvider.java:108)\n	... 106 more\n', '2020-07-09 19:37:20');
INSERT INTO `sys_log` VALUES (2636, '用户登录', 'ERROR', 'me.zhengjie.modules.security.rest.AuthorizationController.login()', '{{username=admin, password= ******} SecurityContextHolderAwareRequestWrapper[ FirewalledRequest[ org.apache.catalina.connector.RequestFacade@3c100b10]]  }', '192.168.31.247', 7, 'admin', '内网IP', 'Chrome 8', 'me.zhengjie.exception.BadRequestException: 验证码不存在或已过期\n	at me.zhengjie.modules.security.rest.AuthorizationController.login(AuthorizationController.java:84)\n	at me.zhengjie.modules.security.rest.AuthorizationController$$FastClassBySpringCGLIB$$f65d16c0.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:746)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88)\n	at me.zhengjie.aspect.LogAspect.logAround(LogAspect.java:68)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)\n	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)\n	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\n	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:93)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:688)\n	at me.zhengjie.modules.security.rest.AuthorizationController$$EnhancerBySpringCGLIB$$cddda5eb.login(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:215)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:142)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:102)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:800)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1038)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:942)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:998)\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:901)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:660)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:875)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:741)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:101)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at com.alibaba.druid.support.http.WebStatFilter.doFilter(WebStatFilter.java:124)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:320)\n	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:127)\n	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:91)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:119)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:137)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:111)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:170)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at me.zhengjie.modules.security.security.TokenFilter.doFilter(TokenFilter.java:90)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:96)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:116)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:66)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:105)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:56)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:215)\n	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:178)\n	at org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:357)\n	at org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:270)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:199)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:490)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408)\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\n	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:770)\n	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1415)\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:748)\n', '2020-07-09 19:37:32');
INSERT INTO `sys_log` VALUES (2637, '用户登录', 'INFO', 'me.zhengjie.modules.security.rest.AuthorizationController.login()', '{{username=admin, password= ******} SecurityContextHolderAwareRequestWrapper[ FirewalledRequest[ org.apache.catalina.connector.RequestFacade@3c100b10]]  }', '192.168.31.247', 248, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:37:38');
INSERT INTO `sys_log` VALUES (2638, '查询部门', 'INFO', 'me.zhengjie.modules.system.rest.DeptController.query()', '{DeptQueryCriteria(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 24, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:37:41');
INSERT INTO `sys_log` VALUES (2639, '查询字典详情', 'INFO', 'me.zhengjie.modules.system.rest.DictDetailController.query()', '{DictDetailQueryCriteria(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 48, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:37:41');
INSERT INTO `sys_log` VALUES (2640, '查询用户', 'INFO', 'me.zhengjie.modules.system.rest.UserController.query()', '{UserQueryCriteria(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 58, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:37:41');
INSERT INTO `sys_log` VALUES (2641, '查询岗位', 'INFO', 'me.zhengjie.modules.system.rest.JobController.query()', '{JobQueryCriteria(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 17, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:37:43');
INSERT INTO `sys_log` VALUES (2642, '查询部门', 'INFO', 'me.zhengjie.modules.system.rest.DeptController.getSuperior()', '{[2]  }', '192.168.31.247', 32, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:37:43');
INSERT INTO `sys_log` VALUES (2643, '查询岗位', 'INFO', 'me.zhengjie.modules.system.rest.JobController.query()', '{JobQueryCriteria(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 9, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-09 19:37:43');
INSERT INTO `sys_log` VALUES (2644, '用户登录', 'INFO', 'me.zhengjie.modules.security.rest.AuthorizationController.login()', '{{username=admin, password= ******} SecurityContextHolderAwareRequestWrapper[ FirewalledRequest[ org.apache.catalina.connector.RequestFacade@63a07d47]]  }', '192.168.31.247', 592, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:19:03');
INSERT INTO `sys_log` VALUES (2645, '查询部门', 'INFO', 'me.zhengjie.modules.system.rest.DeptController.query()', '{DeptQueryCriteria(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 36, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:19:06');
INSERT INTO `sys_log` VALUES (2646, '查询字典详情', 'INFO', 'me.zhengjie.modules.system.rest.DictDetailController.query()', '{DictDetailQueryCriteria(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 51, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:19:06');
INSERT INTO `sys_log` VALUES (2647, '查询用户', 'INFO', 'me.zhengjie.modules.system.rest.UserController.query()', '{UserQueryCriteria(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 60, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:19:06');
INSERT INTO `sys_log` VALUES (2648, '查询部门', 'INFO', 'me.zhengjie.modules.system.rest.DeptController.query()', '{DeptQueryCriteria(name=null, enabled=true, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 9, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:19:10');
INSERT INTO `sys_log` VALUES (2649, '查询岗位', 'INFO', 'me.zhengjie.modules.system.rest.JobController.query()', '{JobQueryCriteria(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 16, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:19:10');
INSERT INTO `sys_log` VALUES (2650, '查询部门', 'INFO', 'me.zhengjie.modules.system.rest.DeptController.query()', '{DeptQueryCriteria(name=null, enabled=true, pid=7, pidIsNull=null, createTime=null)  }', '192.168.31.247', 10, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:19:34');
INSERT INTO `sys_log` VALUES (2651, '查询角色', 'INFO', 'me.zhengjie.modules.system.rest.RoleController.query()', '{RoleQueryCriteria(blurry=null, createTime=null) Page request [number: 0, size 10, sort: level: ASC]  }', '192.168.31.247', 22, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:29:14');
INSERT INTO `sys_log` VALUES (2652, '查询字典详情', 'INFO', 'me.zhengjie.modules.system.rest.DictDetailController.query()', '{DictDetailQueryCriteria(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:29:23');
INSERT INTO `sys_log` VALUES (2653, '查询用户', 'INFO', 'me.zhengjie.modules.system.rest.UserController.query()', '{UserQueryCriteria(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 14, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:29:23');
INSERT INTO `sys_log` VALUES (2654, '查询部门', 'INFO', 'me.zhengjie.modules.system.rest.DeptController.query()', '{DeptQueryCriteria(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:29:23');
INSERT INTO `sys_log` VALUES (2655, '查询菜单', 'INFO', 'me.zhengjie.modules.system.rest.MenuController.query()', '{MenuQueryCriteria(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:29:26');
INSERT INTO `sys_log` VALUES (2656, '新增菜单', 'INFO', 'me.zhengjie.modules.system.rest.MenuController.create()', '{me.zhengjie.modules.system.domain.Menu@6efbaa84[id=120,\n,roles=<null>,\n,title=www,\n,componentName=<null>,\n,menuSort=999,\n,component=<null>,\n,path=www,\n,type=0,\n,permission=<null>,\n,icon=alipay,\n,cache=false,\n,hidden=false,\n,pid=<null>,\n,subCount=0,\n,iFrame=false,\n]  }', '192.168.31.247', 64, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:29:37');
INSERT INTO `sys_log` VALUES (2657, '查询菜单', 'INFO', 'me.zhengjie.modules.system.rest.MenuController.query()', '{MenuQueryCriteria(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:29:37');
INSERT INTO `sys_log` VALUES (2658, '查询菜单', 'INFO', 'me.zhengjie.modules.system.rest.MenuController.getSuperior()', '{[120]  }', '192.168.31.247', 12, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:29:40');
INSERT INTO `sys_log` VALUES (2659, '删除菜单', 'INFO', 'me.zhengjie.modules.system.rest.MenuController.delete()', '{[120]  }', '192.168.31.247', 51, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:29:44');
INSERT INTO `sys_log` VALUES (2660, '查询菜单', 'INFO', 'me.zhengjie.modules.system.rest.MenuController.query()', '{MenuQueryCriteria(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:29:44');
INSERT INTO `sys_log` VALUES (2661, '查询字典详情', 'INFO', 'me.zhengjie.modules.system.rest.DictDetailController.query()', '{DictDetailQueryCriteria(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:29:48');
INSERT INTO `sys_log` VALUES (2662, '查询用户', 'INFO', 'me.zhengjie.modules.system.rest.UserController.query()', '{UserQueryCriteria(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 17, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:29:48');
INSERT INTO `sys_log` VALUES (2663, '查询部门', 'INFO', 'me.zhengjie.modules.system.rest.DeptController.query()', '{DeptQueryCriteria(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 14:29:48');
INSERT INTO `sys_log` VALUES (2664, '用户登录', 'ERROR', 'com.dxj.module.security.controller.AuthController.login()', '{{username=admin, password= ******} SecurityContextHolderAwareRequestWrapper[ org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@75403f80]  }', '192.168.31.247', 261, 'admin', '内网IP', 'Chrome 8', 'org.springframework.security.authentication.InternalAuthenticationServiceException: com.alibaba.fastjson.JSONObject cannot be cast to com.dxj.module.system.domain.dto.UserDTO\n	at org.springframework.security.authentication.dao.DaoAuthenticationProvider.retrieveUser(DaoAuthenticationProvider.java:123)\n	at org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider.authenticate(AbstractUserDetailsAuthenticationProvider.java:144)\n	at org.springframework.security.authentication.ProviderManager.authenticate(ProviderManager.java:175)\n	at com.dxj.module.security.controller.AuthController.login(AuthController.java:82)\n	at com.dxj.module.security.controller.AuthController$$FastClassBySpringCGLIB$$f96c6e65.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88)\n	at com.dxj.aspect.LogAspect.logAround(LogAspect.java:53)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)\n	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)\n	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\n	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:93)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:688)\n	at com.dxj.module.security.controller.AuthController$$EnhancerBySpringCGLIB$$9eb88283.login(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:892)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1039)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:942)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1005)\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:908)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:660)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:882)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:741)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:103)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at com.alibaba.druid.support.http.WebStatFilter.doFilter(WebStatFilter.java:124)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:320)\n	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:127)\n	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:91)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:119)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:137)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:111)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:170)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at com.dxj.module.security.domain.entity.TokenFilter.doFilter(TokenFilter.java:75)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:96)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:109)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:116)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:74)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:109)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:105)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:56)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:109)\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\n	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:215)\n	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:178)\n	at org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:357)\n	at org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:270)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:109)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:490)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408)\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\n	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:853)\n	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1587)\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:748)\nCaused by: java.lang.ClassCastException: com.alibaba.fastjson.JSONObject cannot be cast to com.dxj.module.system.domain.dto.UserDTO\n	at com.dxj.module.system.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$4d01d2e4.findByName(<generated>)\n	at com.dxj.module.security.service.UserDetailsServiceImpl.loadUserByUsername(UserDetailsServiceImpl.java:55)\n	at com.dxj.module.security.service.UserDetailsServiceImpl.loadUserByUsername(UserDetailsServiceImpl.java:26)\n	at org.springframework.security.authentication.dao.DaoAuthenticationProvider.retrieveUser(DaoAuthenticationProvider.java:108)\n	... 106 more\n', '2020-07-12 15:08:42');
INSERT INTO `sys_log` VALUES (2665, '用户登录', 'INFO', 'com.dxj.module.security.controller.AuthController.login()', '{{username=admin, password= ******} SecurityContextHolderAwareRequestWrapper[ org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@24bac366]  }', '192.168.31.247', 250, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:09:46');
INSERT INTO `sys_log` VALUES (2666, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 18, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:09:49');
INSERT INTO `sys_log` VALUES (2667, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 46, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:09:49');
INSERT INTO `sys_log` VALUES (2668, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 56, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:09:49');
INSERT INTO `sys_log` VALUES (2669, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 15, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:09:51');
INSERT INTO `sys_log` VALUES (2670, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.getSuperior()', '{[2]  }', '192.168.31.247', 36, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:09:51');
INSERT INTO `sys_log` VALUES (2671, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 8, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:09:51');
INSERT INTO `sys_log` VALUES (2672, '修改用户', 'INFO', 'com.dxj.module.system.controller.UserController.update()', '{com.dxj.module.system.domain.entity.User@4e6ef824[id=2,\n,roles=[com.dxj.module.system.domain.entity.Role@2dcfa2f6[id=2,\n,users=toString builder encounter an error]],\n,jobs=[com.dxj.module.system.domain.entity.Job@2205bb1a[id=12,\n,name=软件测试,\n,jobSort=5,\n,enabled=true,\n]],\n,dept=com.dxj.module.system.domain.entity.Dept@37af2ab6[id=2,\n,roles=<null>,\n,deptSort=<null>,\n,name=研发部,\n,enabled=<null>,\n,pid=<null>,\n,subCount=0,\n],\n,username=test1,\n,nickName=测试,\n,email=231@qq.com,\n,phone=18888888888,\n,gender=男,\n,avatarName=<null>,\n,avatarPath=<null>,\n,password=<null>,\n,enabled=true,\n,isAdmin=false,\n,pwdResetTime=<null>,\n]  }', '192.168.31.247', 129, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:09:57');
INSERT INTO `sys_log` VALUES (2673, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 15, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:09:57');
INSERT INTO `sys_log` VALUES (2674, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:09:59');
INSERT INTO `sys_log` VALUES (2675, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:09:59');
INSERT INTO `sys_log` VALUES (2676, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.getSuperior()', '{[2]  }', '192.168.31.247', 22, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:09:59');
INSERT INTO `sys_log` VALUES (2677, '修改用户', 'INFO', 'com.dxj.module.system.controller.UserController.update()', '{com.dxj.module.system.domain.entity.User@5b2ad87f[id=2,\n,roles=[com.dxj.module.system.domain.entity.Role@675eb317[id=2,\n,users=toString builder encounter an error]],\n,jobs=[com.dxj.module.system.domain.entity.Job@373aeae7[id=12,\n,name=软件测试,\n,jobSort=5,\n,enabled=true,\n]],\n,dept=com.dxj.module.system.domain.entity.Dept@7e19330c[id=2,\n,roles=<null>,\n,deptSort=<null>,\n,name=研发部,\n,enabled=<null>,\n,pid=<null>,\n,subCount=0,\n],\n,username=test,\n,nickName=测试,\n,email=231@qq.com,\n,phone=18888888888,\n,gender=男,\n,avatarName=<null>,\n,avatarPath=<null>,\n,password=<null>,\n,enabled=true,\n,isAdmin=false,\n,pwdResetTime=<null>,\n]  }', '192.168.31.247', 37, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:10:02');
INSERT INTO `sys_log` VALUES (2678, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 16, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:10:02');
INSERT INTO `sys_log` VALUES (2679, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=7, pidIsNull=null, createTime=null)  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:10:37');
INSERT INTO `sys_log` VALUES (2680, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=8, pidIsNull=null, createTime=null)  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:10:38');
INSERT INTO `sys_log` VALUES (2681, '查询角色', 'INFO', 'com.dxj.module.system.controller.RoleController.query()', '{RoleQuery(blurry=null, createTime=null) Page request [number: 0, size 10, sort: level: ASC]  }', '192.168.31.247', 16, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:10:46');
INSERT INTO `sys_log` VALUES (2682, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:10:48');
INSERT INTO `sys_log` VALUES (2683, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=null, pid=1)  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:10:50');
INSERT INTO `sys_log` VALUES (2684, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=null, pid=6)  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:10:52');
INSERT INTO `sys_log` VALUES (2685, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:10:54');
INSERT INTO `sys_log` VALUES (2686, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 14, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:10:54');
INSERT INTO `sys_log` VALUES (2687, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 3, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:10:54');
INSERT INTO `sys_log` VALUES (2688, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=7, pidIsNull=null, createTime=null)  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:10:56');
INSERT INTO `sys_log` VALUES (2689, '查询定时任务', 'INFO', 'com.dxj.module.quartz.controller.QuartzController.query()', '{QuartzJobQuery(jobName=null, isSuccess=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 9, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:11:04');
INSERT INTO `sys_log` VALUES (2690, '查询字典', 'INFO', 'com.dxj.module.system.controller.DictController.query()', '{DictQuery(blurry=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 17, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:11:11');
INSERT INTO `sys_log` VALUES (2691, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=job_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:11:12');
INSERT INTO `sys_log` VALUES (2692, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=null, createTime=null) Page request [number: 0, size 10, sort: jobSort: ASC,id: DESC]  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:11:12');
INSERT INTO `sys_log` VALUES (2693, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 3, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:11:14');
INSERT INTO `sys_log` VALUES (2694, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=dept_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-12 15:11:14');
INSERT INTO `sys_log` VALUES (2695, '用户登录', 'INFO', 'com.dxj.module.security.controller.AuthController.login()', '{{username=admin, password= ******} SecurityContextHolderAwareRequestWrapper[ org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@27d3a44]  }', '192.168.31.247', 397, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:00');
INSERT INTO `sys_log` VALUES (2696, '查询角色', 'INFO', 'com.dxj.module.system.controller.RoleController.query()', '{RoleQuery(blurry=null, createTime=null) Page request [number: 0, size 10, sort: level: ASC]  }', '192.168.31.247', 47, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:22');
INSERT INTO `sys_log` VALUES (2697, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 32, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:24');
INSERT INTO `sys_log` VALUES (2698, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 38, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:24');
INSERT INTO `sys_log` VALUES (2699, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 16, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:24');
INSERT INTO `sys_log` VALUES (2700, '查询菜单', 'INFO', 'com.dxj.module.system.controller.MenuController.query()', '{MenuQuery(blurry=null, createTime=null, pidIsNull=true, pid=null)  }', '192.168.31.247', 12, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:26');
INSERT INTO `sys_log` VALUES (2701, '查询角色', 'INFO', 'com.dxj.module.system.controller.RoleController.query()', '{RoleQuery(blurry=null, createTime=null) Page request [number: 0, size 10, sort: level: ASC]  }', '192.168.31.247', 19, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:29');
INSERT INTO `sys_log` VALUES (2702, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:30');
INSERT INTO `sys_log` VALUES (2703, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=dept_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:30');
INSERT INTO `sys_log` VALUES (2704, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=7, pidIsNull=null, createTime=null)  }', '192.168.31.247', 9, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:32');
INSERT INTO `sys_log` VALUES (2705, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:34');
INSERT INTO `sys_log` VALUES (2706, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 21, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:34');
INSERT INTO `sys_log` VALUES (2707, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 7, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:34');
INSERT INTO `sys_log` VALUES (2708, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=7, pidIsNull=null, createTime=null)  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:35');
INSERT INTO `sys_log` VALUES (2709, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=8, pidIsNull=null, createTime=null)  }', '192.168.31.247', 7, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:05:37');
INSERT INTO `sys_log` VALUES (2710, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 17, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:12:29');
INSERT INTO `sys_log` VALUES (2711, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:12:29');
INSERT INTO `sys_log` VALUES (2712, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:12:29');
INSERT INTO `sys_log` VALUES (2713, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 6, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:14:19');
INSERT INTO `sys_log` VALUES (2714, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 15, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:14:19');
INSERT INTO `sys_log` VALUES (2715, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:14:19');
INSERT INTO `sys_log` VALUES (2716, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 5, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:16:00');
INSERT INTO `sys_log` VALUES (2717, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 14, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:16:00');
INSERT INTO `sys_log` VALUES (2718, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:16:00');
INSERT INTO `sys_log` VALUES (2719, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 3, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:16:02');
INSERT INTO `sys_log` VALUES (2720, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 17, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:16:02');
INSERT INTO `sys_log` VALUES (2721, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:16:02');
INSERT INTO `sys_log` VALUES (2722, '用户登录', 'INFO', 'com.dxj.module.security.controller.AuthController.login()', '{{username=admin, password= ******} SecurityContextHolderAwareRequestWrapper[ org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@683e4fd3]  }', '192.168.31.247', 241, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:18:20');
INSERT INTO `sys_log` VALUES (2723, '查询角色', 'INFO', 'com.dxj.module.system.controller.RoleController.query()', '{RoleQuery(blurry=null, createTime=null) Page request [number: 0, size 10, sort: level: ASC]  }', '192.168.31.247', 14, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:26:26');
INSERT INTO `sys_log` VALUES (2724, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:26:37');
INSERT INTO `sys_log` VALUES (2725, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 15, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:26:37');
INSERT INTO `sys_log` VALUES (2726, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 3, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:26:37');
INSERT INTO `sys_log` VALUES (2727, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 14, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:26:42');
INSERT INTO `sys_log` VALUES (2728, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.getSuperior()', '{[2]  }', '192.168.31.247', 27, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:26:42');
INSERT INTO `sys_log` VALUES (2729, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 3, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:26:42');
INSERT INTO `sys_log` VALUES (2730, '用户登录', 'INFO', 'com.dxj.module.security.controller.AuthController.login()', '{{username=test, password= ******} SecurityContextHolderAwareRequestWrapper[ org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@c975e05]  }', '192.168.31.247', 128, 'test', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:27:00');
INSERT INTO `sys_log` VALUES (2731, '用户登录', 'INFO', 'com.dxj.module.security.controller.AuthController.login()', '{{username=admin, password= ******} SecurityContextHolderAwareRequestWrapper[ org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@1ad7d479]  }', '192.168.31.247', 285, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:34:57');
INSERT INTO `sys_log` VALUES (2732, '查询角色', 'INFO', 'com.dxj.module.system.controller.RoleController.query()', '{RoleQuery(blurry=null, createTime=null) Page request [number: 0, size 10, sort: level: ASC]  }', '192.168.31.247', 11, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:35:01');
INSERT INTO `sys_log` VALUES (2733, '用户登录', 'INFO', 'com.dxj.module.security.controller.AuthController.login()', '{{username=admin, password= ******} SecurityContextHolderAwareRequestWrapper[ org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@6fb04ab4]  }', '192.168.31.247', 289, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:37:02');
INSERT INTO `sys_log` VALUES (2734, '查询字典详情', 'INFO', 'com.dxj.module.system.controller.DictDetailController.query()', '{DictDetailQuery(label=null, dictName=user_status) Page request [number: 0, size 2000, sort: dictSort: ASC]  }', '192.168.31.247', 3, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:37:35');
INSERT INTO `sys_log` VALUES (2735, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 15, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:37:35');
INSERT INTO `sys_log` VALUES (2736, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.query()', '{DeptQuery(name=null, enabled=null, pid=null, pidIsNull=true, createTime=null)  }', '192.168.31.247', 3, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:37:35');
INSERT INTO `sys_log` VALUES (2737, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 3, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:37:36');
INSERT INTO `sys_log` VALUES (2738, '查询部门', 'INFO', 'com.dxj.module.system.controller.DeptController.getSuperior()', '{[2]  }', '192.168.31.247', 15, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:37:36');
INSERT INTO `sys_log` VALUES (2739, '查询岗位', 'INFO', 'com.dxj.module.system.controller.JobController.query()', '{JobQuery(name=null, enabled=true, createTime=null) Page request [number: 0, size 2000, sort: UNSORTED]  }', '192.168.31.247', 4, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:37:36');
INSERT INTO `sys_log` VALUES (2740, '修改用户', 'INFO', 'com.dxj.module.system.controller.UserController.update()', '{com.dxj.module.system.domain.entity.User@639ccd7a[id=2,\n,roles=[com.dxj.module.system.domain.entity.Role@4d36157a[id=2,\n,users=toString builder encounter an error]],\n,jobs=[com.dxj.module.system.domain.entity.Job@62fefca1[id=12,\n,name=软件测试,\n,jobSort=5,\n,enabled=true,\n]],\n,dept=com.dxj.module.system.domain.entity.Dept@50bbab9a[id=2,\n,roles=<null>,\n,deptSort=<null>,\n,name=研发部,\n,enabled=<null>,\n,pid=<null>,\n,subCount=0,\n],\n,username=test,\n,nickName=测试,\n,email=231@qq.com,\n,phone=18888888889,\n,gender=男,\n,avatarName=<null>,\n,avatarPath=<null>,\n,password=<null>,\n,enabled=true,\n,isAdmin=false,\n,pwdResetTime=<null>,\n]  }', '192.168.31.247', 111, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:37:40');
INSERT INTO `sys_log` VALUES (2741, '查询用户', 'INFO', 'com.dxj.module.system.controller.UserController.query()', '{UserQuery(id=null, deptIds=[], blurry=null, enabled=null, deptId=null, createTime=null) Page request [number: 0, size 10, sort: id: DESC]  }', '192.168.31.247', 10, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:37:40');
INSERT INTO `sys_log` VALUES (2742, '用户登录', 'INFO', 'com.dxj.module.security.controller.AuthController.login()', '{{username=admin, password= ******} SecurityContextHolderAwareRequestWrapper[ org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@37f73ea4]  }', '192.168.31.247', 287, 'admin', '内网IP', 'Chrome 8', NULL, '2020-07-16 19:46:00');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '上级菜单ID',
  `sub_count` int(5) DEFAULT '0' COMMENT '子菜单数目',
  `type` int(11) DEFAULT NULL COMMENT '菜单类型',
  `title` varchar(255) DEFAULT NULL COMMENT '菜单标题',
  `name` varchar(255) DEFAULT NULL COMMENT '组件名称',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `menu_sort` int(5) DEFAULT NULL COMMENT '排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `path` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `i_frame` bit(1) DEFAULT NULL COMMENT '是否外链',
  `cache` bit(1) DEFAULT b'0' COMMENT '缓存',
  `hidden` bit(1) DEFAULT b'0' COMMENT '隐藏',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE,
  UNIQUE KEY `uniq_title` (`title`),
  UNIQUE KEY `uniq_name` (`name`),
  KEY `inx_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, NULL, 7, 0, '系统管理', NULL, NULL, 1, 'system', 'system', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-18 15:11:29', NULL);
INSERT INTO `sys_menu` VALUES (2, 1, 3, 1, '用户管理', 'User', 'system/user/index', 2, 'peoples', 'user', b'0', b'0', b'0', 'user:list', NULL, NULL, '2018-12-18 15:14:44', NULL);
INSERT INTO `sys_menu` VALUES (3, 1, 3, 1, '角色管理', 'Role', 'system/role/index', 3, 'role', 'role', b'0', b'0', b'0', 'roles:list', NULL, NULL, '2018-12-18 15:16:07', NULL);
INSERT INTO `sys_menu` VALUES (5, 1, 3, 1, '菜单管理', 'Menu', 'system/menu/index', 5, 'menu', 'menu', b'0', b'0', b'0', 'menu:list', NULL, NULL, '2018-12-18 15:17:28', NULL);
INSERT INTO `sys_menu` VALUES (6, NULL, 4, 0, '系统监控', NULL, NULL, 10, 'monitor', 'monitor', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-18 15:17:48', NULL);
INSERT INTO `sys_menu` VALUES (7, 6, 0, 1, '操作日志', 'Log', 'monitor/log/index', 11, 'log', 'logs', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-18 15:18:26', NULL);
INSERT INTO `sys_menu` VALUES (9, 6, 0, 1, 'SQL监控', 'Sql', 'monitor/sql/index', 18, 'sqlMonitor', 'druid', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-18 15:19:34', NULL);
INSERT INTO `sys_menu` VALUES (14, 36, 0, 1, '邮件工具', 'Email', 'tools/email/index', 35, 'email', 'email', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-27 10:13:09', NULL);
INSERT INTO `sys_menu` VALUES (16, 36, 2, 1, '图床管理', 'Pictures', 'tools/picture/index', 33, 'image', 'pictures', b'0', b'0', b'0', 'pictures:list', NULL, NULL, '2018-12-28 09:36:53', NULL);
INSERT INTO `sys_menu` VALUES (18, 36, 3, 1, '存储管理', 'Storage', 'tools/storage/index', 34, 'qiniu', 'storage', b'0', b'0', b'0', 'storage:list', NULL, NULL, '2018-12-31 11:12:15', NULL);
INSERT INTO `sys_menu` VALUES (28, 1, 3, 1, '任务调度', 'Timing', 'system/timing/index', 999, 'timing', 'timing', b'0', b'0', b'0', 'timing:list', NULL, NULL, '2019-01-07 20:34:40', NULL);
INSERT INTO `sys_menu` VALUES (30, 36, 0, 1, '代码生成', 'GeneratorIndex', 'generator/index', 32, 'dev', 'generator', b'0', b'1', b'0', NULL, NULL, NULL, '2019-01-11 15:45:55', NULL);
INSERT INTO `sys_menu` VALUES (32, 6, 0, 1, '异常日志', 'ErrorLog', 'monitor/log/errorLog', 12, 'error', 'errorLog', b'0', b'0', b'0', NULL, NULL, NULL, '2019-01-13 13:49:03', NULL);
INSERT INTO `sys_menu` VALUES (35, 1, 3, 1, '部门管理', 'Dept', 'system/dept/index', 6, 'dept', 'dept', b'0', b'0', b'0', 'dept:list', NULL, NULL, '2019-03-25 09:46:00', NULL);
INSERT INTO `sys_menu` VALUES (36, NULL, 7, 0, '系统工具', NULL, '', 30, 'sys-tools', 'sys-tools', b'0', b'0', b'0', NULL, NULL, NULL, '2019-03-29 10:57:35', NULL);
INSERT INTO `sys_menu` VALUES (37, 1, 3, 1, '岗位管理', 'Job', 'system/job/index', 7, 'Steve-Jobs', 'job', b'0', b'0', b'0', 'job:list', NULL, NULL, '2019-03-29 13:51:18', NULL);
INSERT INTO `sys_menu` VALUES (38, 36, 0, 1, '接口文档', 'Swagger', 'tools/swagger/index', 36, 'swagger', 'swagger2', b'0', b'0', b'0', NULL, NULL, NULL, '2019-03-29 19:57:53', NULL);
INSERT INTO `sys_menu` VALUES (39, 1, 3, 1, '字典管理', 'Dict', 'system/dict/index', 8, 'dictionary', 'dict', b'0', b'0', b'0', 'dict:list', NULL, NULL, '2019-04-10 11:49:04', NULL);
INSERT INTO `sys_menu` VALUES (41, 6, 0, 1, '在线用户', 'OnlineUser', 'monitor/online/index', 10, 'Steve-Jobs', 'online', b'0', b'0', b'0', NULL, NULL, NULL, '2019-10-26 22:08:43', NULL);
INSERT INTO `sys_menu` VALUES (44, 2, 0, 2, '用户新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'user:add', NULL, NULL, '2019-10-29 10:59:46', NULL);
INSERT INTO `sys_menu` VALUES (45, 2, 0, 2, '用户编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'user:edit', NULL, NULL, '2019-10-29 11:00:08', NULL);
INSERT INTO `sys_menu` VALUES (46, 2, 0, 2, '用户删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'user:del', NULL, NULL, '2019-10-29 11:00:23', NULL);
INSERT INTO `sys_menu` VALUES (48, 3, 0, 2, '角色创建', NULL, '', 2, '', '', b'0', b'0', b'0', 'roles:add', NULL, NULL, '2019-10-29 12:45:34', NULL);
INSERT INTO `sys_menu` VALUES (49, 3, 0, 2, '角色修改', NULL, '', 3, '', '', b'0', b'0', b'0', 'roles:edit', NULL, NULL, '2019-10-29 12:46:16', NULL);
INSERT INTO `sys_menu` VALUES (50, 3, 0, 2, '角色删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'roles:del', NULL, NULL, '2019-10-29 12:46:51', NULL);
INSERT INTO `sys_menu` VALUES (52, 5, 0, 2, '菜单新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'menu:add', NULL, NULL, '2019-10-29 12:55:07', NULL);
INSERT INTO `sys_menu` VALUES (53, 5, 0, 2, '菜单编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'menu:edit', NULL, NULL, '2019-10-29 12:55:40', NULL);
INSERT INTO `sys_menu` VALUES (54, 5, 0, 2, '菜单删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'menu:del', NULL, NULL, '2019-10-29 12:56:00', NULL);
INSERT INTO `sys_menu` VALUES (56, 35, 0, 2, '部门新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'dept:add', NULL, NULL, '2019-10-29 12:57:09', NULL);
INSERT INTO `sys_menu` VALUES (57, 35, 0, 2, '部门编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'dept:edit', NULL, NULL, '2019-10-29 12:57:27', NULL);
INSERT INTO `sys_menu` VALUES (58, 35, 0, 2, '部门删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'dept:del', NULL, NULL, '2019-10-29 12:57:41', NULL);
INSERT INTO `sys_menu` VALUES (60, 37, 0, 2, '岗位新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'job:add', NULL, NULL, '2019-10-29 12:58:27', NULL);
INSERT INTO `sys_menu` VALUES (61, 37, 0, 2, '岗位编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'job:edit', NULL, NULL, '2019-10-29 12:58:45', NULL);
INSERT INTO `sys_menu` VALUES (62, 37, 0, 2, '岗位删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'job:del', NULL, NULL, '2019-10-29 12:59:04', NULL);
INSERT INTO `sys_menu` VALUES (64, 39, 0, 2, '字典新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'dict:add', NULL, NULL, '2019-10-29 13:00:17', NULL);
INSERT INTO `sys_menu` VALUES (65, 39, 0, 2, '字典编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'dict:edit', NULL, NULL, '2019-10-29 13:00:42', NULL);
INSERT INTO `sys_menu` VALUES (66, 39, 0, 2, '字典删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'dict:del', NULL, NULL, '2019-10-29 13:00:59', NULL);
INSERT INTO `sys_menu` VALUES (70, 16, 0, 2, '图片上传', NULL, '', 2, '', '', b'0', b'0', b'0', 'pictures:add', NULL, NULL, '2019-10-29 13:05:34', NULL);
INSERT INTO `sys_menu` VALUES (71, 16, 0, 2, '图片删除', NULL, '', 3, '', '', b'0', b'0', b'0', 'pictures:del', NULL, NULL, '2019-10-29 13:05:52', NULL);
INSERT INTO `sys_menu` VALUES (73, 28, 0, 2, '任务新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'timing:add', NULL, NULL, '2019-10-29 13:07:28', NULL);
INSERT INTO `sys_menu` VALUES (74, 28, 0, 2, '任务编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'timing:edit', NULL, NULL, '2019-10-29 13:07:41', NULL);
INSERT INTO `sys_menu` VALUES (75, 28, 0, 2, '任务删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'timing:del', NULL, NULL, '2019-10-29 13:07:54', NULL);
INSERT INTO `sys_menu` VALUES (77, 18, 0, 2, '上传文件', NULL, '', 2, '', '', b'0', b'0', b'0', 'storage:add', NULL, NULL, '2019-10-29 13:09:09', NULL);
INSERT INTO `sys_menu` VALUES (78, 18, 0, 2, '文件编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'storage:edit', NULL, NULL, '2019-10-29 13:09:22', NULL);
INSERT INTO `sys_menu` VALUES (79, 18, 0, 2, '文件删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'storage:del', NULL, NULL, '2019-10-29 13:09:34', NULL);
INSERT INTO `sys_menu` VALUES (82, 36, 0, 1, '生成配置', 'GeneratorConfig', 'generator/config', 33, 'dev', 'generator/config/:tableName', b'0', b'1', b'1', '', NULL, NULL, '2019-11-17 20:08:56', NULL);
INSERT INTO `sys_menu` VALUES (116, 36, 0, 1, '生成预览', 'Preview', 'generator/preview', 999, 'java', 'generator/preview/:tableName', b'0', b'1', b'1', NULL, NULL, NULL, '2019-11-26 14:54:36', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_job`;
CREATE TABLE `sys_quartz_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bean_name` varchar(255) DEFAULT NULL COMMENT 'Spring Bean名称',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'cron 表达式',
  `is_pause` bit(1) DEFAULT NULL COMMENT '状态：1暂停、0启用',
  `job_name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `method_name` varchar(255) DEFAULT NULL COMMENT '方法名称',
  `params` varchar(255) DEFAULT NULL COMMENT '参数',
  `description` varchar(255) DEFAULT NULL COMMENT '备注',
  `person_in_charge` varchar(100) DEFAULT NULL COMMENT '负责人',
  `email` varchar(100) DEFAULT NULL COMMENT '报警邮箱',
  `sub_task` varchar(100) DEFAULT NULL COMMENT '子任务ID',
  `pause_after_failure` bit(1) DEFAULT NULL COMMENT '任务失败后是否暂停',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`job_id`) USING BTREE,
  KEY `inx_is_pause` (`is_pause`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='定时任务';

-- ----------------------------
-- Records of sys_quartz_job
-- ----------------------------
BEGIN;
INSERT INTO `sys_quartz_job` VALUES (2, 'testTask', '0/5 * * * * ?', b'1', '测试1', 'run1', 'test', '带参测试，多参使用json', '测试', NULL, NULL, NULL, NULL, 'admin', '2019-08-22 14:08:29', '2020-05-05 17:26:19');
INSERT INTO `sys_quartz_job` VALUES (3, 'testTask', '0/5 * * * * ?', b'1', '测试', 'run', '', '不带参测试', 'Zheng Jie', '', '2,6', b'1', NULL, 'admin', '2019-09-26 16:44:39', '2020-05-05 20:45:39');
INSERT INTO `sys_quartz_job` VALUES (5, 'Test', '0/5 * * * * ?', b'1', '任务告警测试', 'run', NULL, '测试', 'test', '', NULL, b'1', 'admin', 'admin', '2020-05-05 20:32:41', '2020-05-05 20:36:13');
INSERT INTO `sys_quartz_job` VALUES (6, 'testTask', '0/5 * * * * ?', b'1', '测试3', 'run2', NULL, '测试3', 'Zheng Jie', '', NULL, b'1', 'admin', 'admin', '2020-05-05 20:35:41', '2020-05-05 20:36:07');
COMMIT;

-- ----------------------------
-- Table structure for sys_quartz_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_log`;
CREATE TABLE `sys_quartz_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bean_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `cron_expression` varchar(255) DEFAULT NULL,
  `exception_detail` text,
  `is_success` bit(1) DEFAULT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  `method_name` varchar(255) DEFAULT NULL,
  `params` varchar(255) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='定时任务日志';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `level` int(255) DEFAULT NULL COMMENT '角色级别',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `data_scope` varchar(255) DEFAULT NULL COMMENT '数据权限',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE KEY `uniq_name` (`name`),
  KEY `role_name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '超级管理员', 1, '---', '全部', NULL, 'admin', '2018-11-23 11:04:37', '2020-07-09 19:32:01');
INSERT INTO `sys_role` VALUES (2, '普通用户', 2, '----', '自定义', NULL, 'admin', '2018-11-23 13:09:06', '2020-07-09 19:32:08');
COMMIT;

-- ----------------------------
-- Table structure for sys_roles_depts
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_depts`;
CREATE TABLE `sys_roles_depts` (
  `role_id` bigint(20) NOT NULL,
  `dept_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`dept_id`) USING BTREE,
  KEY `FK7qg6itn5ajdoa9h9o78v9ksur` (`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色部门关联';

-- ----------------------------
-- Records of sys_roles_depts
-- ----------------------------
BEGIN;
INSERT INTO `sys_roles_depts` VALUES (2, 7);
COMMIT;

-- ----------------------------
-- Table structure for sys_roles_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_menus`;
CREATE TABLE `sys_roles_menus` (
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`menu_id`,`role_id`) USING BTREE,
  KEY `FKcngg2qadojhi3a651a5adkvbq` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色菜单关联';

-- ----------------------------
-- Records of sys_roles_menus
-- ----------------------------
BEGIN;
INSERT INTO `sys_roles_menus` VALUES (1, 1);
INSERT INTO `sys_roles_menus` VALUES (2, 1);
INSERT INTO `sys_roles_menus` VALUES (3, 1);
INSERT INTO `sys_roles_menus` VALUES (5, 1);
INSERT INTO `sys_roles_menus` VALUES (6, 1);
INSERT INTO `sys_roles_menus` VALUES (7, 1);
INSERT INTO `sys_roles_menus` VALUES (9, 1);
INSERT INTO `sys_roles_menus` VALUES (14, 1);
INSERT INTO `sys_roles_menus` VALUES (16, 1);
INSERT INTO `sys_roles_menus` VALUES (18, 1);
INSERT INTO `sys_roles_menus` VALUES (28, 1);
INSERT INTO `sys_roles_menus` VALUES (30, 1);
INSERT INTO `sys_roles_menus` VALUES (32, 1);
INSERT INTO `sys_roles_menus` VALUES (35, 1);
INSERT INTO `sys_roles_menus` VALUES (36, 1);
INSERT INTO `sys_roles_menus` VALUES (37, 1);
INSERT INTO `sys_roles_menus` VALUES (38, 1);
INSERT INTO `sys_roles_menus` VALUES (39, 1);
INSERT INTO `sys_roles_menus` VALUES (41, 1);
INSERT INTO `sys_roles_menus` VALUES (82, 1);
INSERT INTO `sys_roles_menus` VALUES (116, 1);
INSERT INTO `sys_roles_menus` VALUES (1, 2);
INSERT INTO `sys_roles_menus` VALUES (2, 2);
INSERT INTO `sys_roles_menus` VALUES (3, 2);
INSERT INTO `sys_roles_menus` VALUES (5, 2);
INSERT INTO `sys_roles_menus` VALUES (36, 2);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门名称',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `avatar_name` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `avatar_path` varchar(255) DEFAULT NULL COMMENT '头像真实路径',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `is_admin` bit(1) DEFAULT b'0' COMMENT '是否为admin账号',
  `enabled` bigint(20) DEFAULT NULL COMMENT '状态：1启用、0禁用',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新着',
  `pwd_reset_time` datetime DEFAULT NULL COMMENT '修改密码的时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `UK_kpubos9gc2cvtkb0thktkbkes` (`email`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `uniq_username` (`username`),
  UNIQUE KEY `uniq_email` (`email`),
  KEY `FK5rwmryny6jthaaxkogownknqp` (`dept_id`) USING BTREE,
  KEY `FKpq2dhypk2qgt68nauh2by22jb` (`avatar_name`) USING BTREE,
  KEY `inx_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 2, 'admin', '管理员', '男', '18888888888', '201507802@qq.com', NULL, NULL, '$2a$10$Egp1/gvFlt7zhlXVfEFw4OfWQCGPw0ClmMcc6FjTnvXNRVf9zdMRa', b'1', 1, NULL, 'admin', '2020-05-03 16:38:31', '2018-08-23 09:11:56', '2020-05-05 10:12:21');
INSERT INTO `sys_user` VALUES (2, 2, 'test', '测试', '男', '18888888889', '231@qq.com', NULL, NULL, '$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK', b'0', 1, 'admin', 'admin', NULL, '2020-05-05 11:15:49', '2020-07-16 19:37:40');
COMMIT;

-- ----------------------------
-- Table structure for sys_users_jobs
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_jobs`;
CREATE TABLE `sys_users_jobs` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `job_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_users_jobs
-- ----------------------------
BEGIN;
INSERT INTO `sys_users_jobs` VALUES (1, 11);
INSERT INTO `sys_users_jobs` VALUES (1, 12);
INSERT INTO `sys_users_jobs` VALUES (2, 12);
COMMIT;

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE,
  KEY `FKq4eq273l04bpu4efj0jd0jb98` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色关联';

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
BEGIN;
INSERT INTO `sys_users_roles` VALUES (1, 1);
INSERT INTO `sys_users_roles` VALUES (2, 2);
COMMIT;

-- ----------------------------
-- Table structure for tool_email_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_email_config`;
CREATE TABLE `tool_email_config` (
  `config_id` bigint(20) NOT NULL COMMENT 'ID',
  `from_user` varchar(255) DEFAULT NULL COMMENT '收件人',
  `host` varchar(255) DEFAULT NULL COMMENT '邮件服务器SMTP地址',
  `pass` varchar(255) DEFAULT NULL COMMENT '密码',
  `port` varchar(255) DEFAULT NULL COMMENT '端口',
  `user` varchar(255) DEFAULT NULL COMMENT '发件者用户名',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='邮箱配置';

-- ----------------------------
-- Table structure for tool_local_storage
-- ----------------------------
DROP TABLE IF EXISTS `tool_local_storage`;
CREATE TABLE `tool_local_storage` (
  `storage_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `real_name` varchar(255) DEFAULT NULL COMMENT '文件真实的名称',
  `name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `suffix` varchar(255) DEFAULT NULL COMMENT '后缀',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  `type` varchar(255) DEFAULT NULL COMMENT '类型',
  `size` varchar(100) DEFAULT NULL COMMENT '大小',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`storage_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='本地存储';

-- ----------------------------
-- Table structure for tool_picture
-- ----------------------------
DROP TABLE IF EXISTS `tool_picture`;
CREATE TABLE `tool_picture` (
  `picture_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `filename` varchar(255) DEFAULT NULL COMMENT '图片名称',
  `md5code` varchar(255) DEFAULT NULL COMMENT '文件的MD5值',
  `size` varchar(255) DEFAULT NULL COMMENT '图片大小',
  `url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `delete_url` varchar(255) DEFAULT NULL COMMENT '删除的URL',
  `height` varchar(255) DEFAULT NULL COMMENT '图片高度',
  `width` varchar(255) DEFAULT NULL COMMENT '图片宽度',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `create_time` datetime DEFAULT NULL COMMENT '上传日期',
  PRIMARY KEY (`picture_id`) USING BTREE,
  UNIQUE KEY `uniq_md5_code` (`md5code`),
  KEY `inx_url` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='Sm.Ms图床';

-- ----------------------------
-- Table structure for tool_qiniu_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_qiniu_config`;
CREATE TABLE `tool_qiniu_config` (
  `config_id` bigint(20) NOT NULL COMMENT 'ID',
  `access_key` text COMMENT 'accessKey',
  `bucket` varchar(255) DEFAULT NULL COMMENT 'Bucket 识别符',
  `host` varchar(255) NOT NULL COMMENT '外链域名',
  `secret_key` text COMMENT 'secretKey',
  `type` varchar(255) DEFAULT NULL COMMENT '空间类型',
  `zone` varchar(255) DEFAULT NULL COMMENT '机房',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='七牛云配置';

-- ----------------------------
-- Table structure for tool_qiniu_content
-- ----------------------------
DROP TABLE IF EXISTS `tool_qiniu_content`;
CREATE TABLE `tool_qiniu_content` (
  `content_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bucket` varchar(255) DEFAULT NULL COMMENT 'Bucket 识别符',
  `name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `size` varchar(255) DEFAULT NULL COMMENT '文件大小',
  `type` varchar(255) DEFAULT NULL COMMENT '文件类型：私有或公开',
  `url` varchar(255) DEFAULT NULL COMMENT '文件url',
  `suffix` varchar(255) DEFAULT NULL COMMENT '文件后缀',
  `update_time` datetime DEFAULT NULL COMMENT '上传或同步的时间',
  PRIMARY KEY (`content_id`) USING BTREE,
  UNIQUE KEY `uniq_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='七牛云文件存储';

SET FOREIGN_KEY_CHECKS = 1;
