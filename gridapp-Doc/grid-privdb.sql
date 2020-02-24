-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- Server version:               10.2.7-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL 版本:                  10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for grid-privdb
CREATE DATABASE IF NOT EXISTS `grid-privdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `grid-privdb`;

-- Dumping structure for table grid-privdb.cms_file
DROP TABLE IF EXISTS `cms_file`;
CREATE TABLE IF NOT EXISTS `cms_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL COMMENT '文件类型',
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- Dumping data for table grid-privdb.cms_file: ~6 rows (approximately)
DELETE FROM `cms_file`;
/*!40000 ALTER TABLE `cms_file` DISABLE KEYS */;
INSERT INTO `cms_file` (`id`, `type`, `url`, `create_date`) VALUES
	(144, 1, 'http://localhost:8004/files/11/20/9fb01430-28e6-4a18-90ec-3e784ba23a6a', '2018-05-25 15:20:04'),
	(145, 1, 'http://localhost:8004/files/45/44/26f1385f-c227-482a-84ad-4d97ee25ef0d', '2018-05-25 15:20:29'),
	(146, 1, 'http://localhost:8004/files/99/45/c8ea7174-1ebb-4c7c-864d-8a1defa57ff8', '2018-05-25 15:21:16'),
	(147, 1, 'http://localhost:8004/files/97/22/293e1fe3-d69e-4a98-b2e5-a7dfae6b05c0', '2019-02-12 21:41:36'),
	(148, 1, 'http://localhost:8004/files/14/39/ebf7dec0-97d5-41a5-b8b4-0c47811e3934', '2019-02-12 11:40:46'),
	(149, 1, 'http://localhost:8004/files/97/59/4e0a2ec5-98aa-49d3-be73-645dd2301ee1', '2019-04-02 15:15:26');
/*!40000 ALTER TABLE `cms_file` ENABLE KEYS */;

-- Dumping structure for function grid-privdb.queryChildrenAreaInfo
DROP FUNCTION IF EXISTS `queryChildrenAreaInfo`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `queryChildrenAreaInfo`(
	`areaId` INT



) RETURNS varchar(4000) CHARSET utf8
leave_name:BEGIN
DECLARE sTemp VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);

SET sTemp='-1';
SET sTempChd = CAST(areaId AS CHAR);

 
WHILE sTempChd IS NOT NULL DO
SET sTemp= CONCAT(sTemp,',',sTempChd);
SELECT GROUP_CONCAT(dept_id) INTO sTempChd FROM sys_dept WHERE FIND_IN_SET(parent_id,sTempChd)>0 ;

END WHILE;

RETURN sTemp;
END//
DELIMITER ;

-- Dumping structure for function grid-privdb.queryParentAreaInfo
DROP FUNCTION IF EXISTS `queryParentAreaInfo`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `queryParentAreaInfo`(
	`areaId` INT

) RETURNS varchar(4000) CHARSET utf8
BEGIN
DECLARE sTemp VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);

SET sTemp='$';
SET sTempChd = CAST(areaId AS CHAR);
SET sTemp = CONCAT(sTemp,',',sTempChd);

SELECT parent_Id INTO sTempChd FROM sys_dept WHERE dept_id = sTempChd;
SET sTemp = CONCAT(sTemp,',',sTempChd);
WHILE sTempChd <> 0 DO

SELECT parent_Id INTO sTempChd FROM sys_dept WHERE dept_id = sTempChd;
SET sTemp = CONCAT(sTemp,',',sTempChd);
END WHILE;
RETURN sTemp;
END//
DELIMITER ;

-- Dumping structure for table grid-privdb.sys_dept
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE IF NOT EXISTS `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级资源组ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '资源组名称',
  `dept_level` varchar(3) DEFAULT NULL COMMENT '资源组级别',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT 0 COMMENT '是否删除  -1：已删除  0：正常',
  `remark` varchar(100) DEFAULT NULL,
  `onwer_perms` int(3) DEFAULT 255 COMMENT '拥有者权限',
  `group_perms` int(3) DEFAULT 133 COMMENT '同组权限',
  `other_perms` int(3) DEFAULT 0 COMMENT '其它用户',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='资源组表，用来对系统内各种资源分组。每一组有独立的操作权限。';

-- Dumping data for table grid-privdb.sys_dept: ~16 rows (approximately)
DELETE FROM `sys_dept`;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` (`dept_id`, `parent_id`, `name`, `dept_level`, `order_num`, `del_flag`, `remark`, `onwer_perms`, `group_perms`, `other_perms`, `gmt_create`, `gmt_modified`) VALUES
	(0, -1, '总部', NULL, NULL, 0, NULL, 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(6, 0, '研发部', NULL, 1, 0, '发而发的', 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(7, 6, '研發一部', NULL, 1, 0, '阿迪斯发士大夫', 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(8, 6, '研发二部', NULL, 2, 0, NULL, 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(9, 0, '销售部', NULL, 2, 0, NULL, 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(10, 9, '销售一部', NULL, 1, 0, '的撒发射点发', 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(13, 0, '测试部', NULL, 5, 0, 'aaaaaaaaaaaa', 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(22, 13, 'ceshi', NULL, NULL, NULL, 'adfadafdfa', 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(25, 22, 'ddadfad', NULL, NULL, 0, 'asdfasd', 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(27, 0, 'dsfads', NULL, NULL, NULL, 'asdfasdfadf', 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(28, 0, 'dsfads', NULL, NULL, NULL, 'asdfasdfadf', 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(29, 0, 'fudl', NULL, NULL, NULL, 'asdfadfasfasdfasfasdfasfasdfa', 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(30, 22, 'fffffffffff', NULL, NULL, NULL, 'ffffffffffffff', 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(31, 22, 'zzzzzz', NULL, NULL, NULL, 'aaaaaa啊啊', 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(32, 6, 'dsfadfasd', NULL, NULL, NULL, 'asdfasf地发动反攻', 255, 133, 0, NULL, '2019-04-17 13:39:58'),
	(33, 0, 'asdfasdf', NULL, NULL, NULL, 'asdfadf1', 255, 133, 0, NULL, '2019-04-17 13:39:58');
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;

-- Dumping structure for table grid-privdb.sys_menu
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` int(3) DEFAULT 0 COMMENT '二进制菜单类型，用于授权',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮  3：跳转',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `component` varchar(20) DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `redirect` varchar(20) DEFAULT NULL,
  `plug_id` bigint(20) DEFAULT NULL COMMENT '插件ID',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- Dumping data for table grid-privdb.sys_menu: ~62 rows (approximately)
DELETE FROM `sys_menu`;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `component`, `gmt_modified`, `redirect`, `plug_id`) VALUES
	(2, 3, '系统菜单', '/admin/menu', 128, 1, 'fa fa-th-list', 2, '2017-08-09 22:55:15', 'Menu', NULL, NULL, NULL),
	(3, 0, '平台管理', NULL, 0, 0, 'fa fa-desktop', 99, '2017-08-09 00:00:00', NULL, '2019-04-26 12:22:45', NULL, NULL),
	(6, 3, '用户管理', '/admin/user', 128, 1, 'fa fa-user', 0, '2017-08-10 00:00:00', 'User', NULL, NULL, NULL),
	(7, 3, '角色管理', '/admin/role', 128, 1, 'fa fa-paw', 1, '2017-08-10 00:00:00', 'Role', NULL, NULL, NULL),
	(12, 6, '新增', '/priv-admin/user/create', 64, 2, '', 0, '2017-08-14 00:00:00', 'User', NULL, NULL, NULL),
	(13, 6, '编辑', '/priv-admin/user/update', 32, 2, '', 0, '2017-08-14 00:00:00', 'User', NULL, NULL, NULL),
	(14, 6, '删除', '/priv-admin/user/delete', 16, 2, NULL, 0, '2017-08-14 00:00:00', 'User', NULL, NULL, NULL),
	(15, 7, '新增', '/priv-admin/role/create', 64, 2, '', 0, '2017-08-14 00:00:00', 'Role', NULL, NULL, NULL),
	(20, 2, '新增', '/priv-admin/menu/create', 64, 2, '', 0, '2017-08-14 00:00:00', 'Menu', NULL, NULL, NULL),
	(21, 2, '编辑', '/priv-admin/menu/update', 32, 2, '', 0, '2017-08-14 00:00:00', 'Menu', NULL, NULL, NULL),
	(22, 2, '删除', '/priv-admin/menu/delete', 16, 2, '', 0, '2017-08-14 00:00:00', 'Menu', NULL, NULL, NULL),
	(24, 6, '批量删除', '/priv-admin/user/batchRemove', 16, 2, '', 0, '2017-08-14 00:00:00', 'User', NULL, NULL, NULL),
	(25, 6, '重置密码', '/priv-admin/user/chpasswd', 1, 2, NULL, 0, '2017-08-14 00:00:00', 'User', NULL, NULL, NULL),
	(26, 6, '管理员改密', '/priv-admin/user/resetPwd', 32, 2, '', 0, '2017-08-14 00:00:00', 'User', NULL, NULL, NULL),
	(55, 7, '编辑', '/priv-admin/role/update', 32, 2, '', NULL, NULL, 'Role', NULL, NULL, NULL),
	(56, 7, '删除', '/priv-admin/role/delete', 16, 2, NULL, NULL, NULL, 'Role', NULL, NULL, NULL),
	(61, 2, '批量删除', '/priv-admin/menu/batchRemove', 16, 2, NULL, NULL, NULL, 'Menu', NULL, NULL, NULL),
	(62, 7, '批量删除', '/priv-admin/role/batchRemove', 0, 2, NULL, NULL, NULL, 'Role', NULL, NULL, NULL),
	(74, 73, '清除缓存', '/admin/api/menu/clearCache', 0, 1, NULL, 1, NULL, NULL, NULL, NULL, NULL),
	(76, 73, '编辑', '/priv-admin/user/currentUser', 1, 2, NULL, 3, NULL, 'User', NULL, NULL, NULL),
	(77, 2, '树列表', '/priv-admin/menu/read', 128, 2, NULL, NULL, NULL, 'Menu', NULL, NULL, NULL),
	(78, 0, '内容管理', NULL, 0, 0, 'fa fa-th-list', 80, NULL, NULL, NULL, NULL, NULL),
	(80, 79, '数据字典', '/a', 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(81, 78, '文件管理', '/cms/file', 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(82, 81, '列表', '/api-cms/file', 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(83, 78, '博客管理', '/cms/content', 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(84, 0, '基础信息', NULL, 0, 0, 'fa fa-bars', NULL, NULL, NULL, NULL, NULL, NULL),
	(85, 84, '系统日志', '/base/log', 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(86, 85, '列表', '/grid-base/log', 4, 2, NULL, NULL, NULL, NULL, '2019-12-09 20:15:58', NULL, NULL),
	(87, 81, '上传', '/file/upload', 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(88, 0, '系统运维', NULL, 0, 0, 'fa fa-video-camera', 98, NULL, NULL, NULL, NULL, NULL),
	(89, 88, '服务注册', '/eureka', 0, 1, NULL, NULL, NULL, NULL, '2019-04-29 13:53:26', NULL, NULL),
	(90, 0, '系统工具', NULL, 0, 0, 'fa fa-wrench', 70, NULL, NULL, NULL, NULL, NULL),
	(91, 90, 'test', '/test', 0, 1, 'fa fa-paw', NULL, NULL, NULL, NULL, NULL, NULL),
	(92, 3, '机构管理', '/admin/dept', 128, 1, 'fa fa-users', NULL, NULL, 'Dept', NULL, NULL, NULL),
	(93, 92, '查询', '/priv-admin/dept/read', 128, 2, NULL, NULL, NULL, 'Dept', NULL, NULL, NULL),
	(94, 92, '编辑', '/priv-admin/dept/update', 32, 2, NULL, NULL, NULL, 'Dept', NULL, NULL, NULL),
	(95, 3, '机构授权', '/admin/userDeptPermission', 128, 1, 'el-icon-tickets', 2, NULL, 'userDeptPermission', '2019-04-26 13:54:01', NULL, NULL),
	(97, 92, '删除', '/priv-admin/dept/delete', 16, 2, NULL, NULL, NULL, 'Dept', NULL, NULL, NULL),
	(98, 92, '新增', '/priv-admin/dept/create', 64, 2, NULL, NULL, NULL, 'Dept', NULL, NULL, NULL),
	(99, 7, '查询', '/priv-admin/role/read', 128, 2, NULL, NULL, NULL, 'Role', NULL, NULL, NULL),
	(100, 92, '查部门树', '/priv-admin/dept/childTree', 1, 2, NULL, NULL, NULL, 'Dept', NULL, NULL, NULL),
	(101, 2, '当前用户菜单', '/priv-admin/menu/currentUserMenus', 1, 2, NULL, NULL, NULL, 'Menu', NULL, NULL, NULL),
	(102, 2, '角色权限', '/priv-admin/menu/roleId', 1, 2, NULL, NULL, NULL, 'Menu', NULL, NULL, NULL),
	(103, 2, '菜单树', '/priv-admin/menu/tree', 1, 2, NULL, NULL, NULL, 'Menu', NULL, NULL, NULL),
	(104, 2, '菜单列表', '/priv-admin/menu/list', 1, 2, NULL, NULL, NULL, 'Menu', NULL, NULL, NULL),
	(107, 6, '分页查询', '/priv-admin/user/listByPage', 128, 2, NULL, NULL, NULL, 'User', NULL, NULL, NULL),
	(108, 6, '查询', '/priv-admin/user/read', 128, 2, NULL, NULL, NULL, 'User', NULL, NULL, NULL),
	(112, 95, '机构授权增加', '/priv-admin/userDeptPermission/create', 64, 2, NULL, NULL, NULL, 'userDeptPermission', NULL, NULL, NULL),
	(113, 95, '机构授权删除', '/priv-admin/userDeptPermission/delete', 16, 2, NULL, NULL, NULL, 'userDeptPermission', NULL, NULL, NULL),
	(114, 95, '机构授权修改', '/priv-admin/userDeptPermission/update', 32, 2, NULL, NULL, NULL, 'userDeptPermission', NULL, NULL, NULL),
	(115, 95, '分页查询', '/priv-admin/userDeptPermission/listbypage', 128, 2, NULL, NULL, NULL, 'userDeptPermission', NULL, NULL, NULL),
	(116, 3, '系统参数', '/admin/sysPara', 0, 1, 'el-icon-info', NULL, '2019-04-18 14:53:50', 'SysPara', NULL, NULL, NULL),
	(117, 116, '增加', '/priv-admin/sysPara/create', 64, 2, NULL, NULL, '2019-04-18 00:00:00', 'SysPara', '2019-04-18 15:03:38', NULL, NULL),
	(118, 116, '查询', '/priv-admin/sysPara/read', 128, 2, NULL, NULL, '2019-04-18 00:00:00', 'SysPara', '2019-04-18 15:03:26', NULL, NULL),
	(119, 116, '删除', '/priv-admin/sysPara/delete', 16, 2, NULL, NULL, '2019-04-18 15:02:36', 'SysPara', NULL, NULL, NULL),
	(120, 116, '修改', '/priv-admin/sysPara/update', 32, 2, NULL, NULL, '2019-04-18 15:04:17', 'SysPara', NULL, NULL, NULL),
	(121, 116, '按参数类型查询', '/priv-admin/sysPara/listParaType', 1, 2, NULL, NULL, '2019-04-18 15:05:22', 'SysPara', NULL, NULL, NULL),
	(122, 116, '列出参数类型', '/priv-admin/sysPara/listByParaType', 1, 2, NULL, NULL, '2019-04-18 18:46:27', 'SysPara', NULL, NULL, NULL),
	(123, 0, 'API文档', NULL, 0, 0, NULL, NULL, '2019-05-06 10:29:42', NULL, '2019-05-06 11:57:57', NULL, NULL),
	(124, 123, '设备管理API', '/api-device/swagger-ui.html', 1, 1, NULL, NULL, '2019-05-06 12:01:53', 'deviceManager', '2019-05-06 12:03:19', NULL, NULL),
	(125, 123, 'API-JSON-DOC', '/api-device/v2/api-docs', 1, 1, NULL, NULL, '2019-05-06 13:20:20', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

-- Dumping structure for table grid-privdb.sys_para
DROP TABLE IF EXISTS `sys_para`;
CREATE TABLE IF NOT EXISTS `sys_para` (
  `para_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `para_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '标签名',
  `value` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '数据值',
  `para_type` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `type_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `order_num` int(11) DEFAULT NULL COMMENT '排序（升序）',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '父级编号',
  `user_id_create` bigint(20) DEFAULT NULL COMMENT '创建者',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `user_id_modified` bigint(20) DEFAULT NULL COMMENT '更新者',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '删除标记 0标识删除',
  PRIMARY KEY (`para_id`),
  UNIQUE KEY `sys_dict_value` (`para_type`,`value`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统参数字典表';

-- Dumping data for table grid-privdb.sys_para: ~112 rows (approximately)
DELETE FROM `sys_para`;
/*!40000 ALTER TABLE `sys_para` DISABLE KEYS */;
INSERT INTO `sys_para` (`para_id`, `para_name`, `value`, `para_type`, `type_desc`, `order_num`, `parent_id`, `user_id_create`, `gmt_create`, `user_id_modified`, `gmt_modified`, `remark`, `del_flag`) VALUES
	(1, '正常', '0', 'del_flag', '删除标记', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(3, '显示', '1', 'show_hide', '显示/隐藏', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(4, '隐藏', '0', 'show_hide', '显示/隐藏', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(5, '是', '1', 'yes_no', '是/否', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(6, '否', '0', 'yes_no', '是/否', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(7, '红色', 'red', 'color', '颜色值', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(8, '绿色', 'green', 'color', '颜色值', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(9, '蓝色', 'blue', 'color', '颜色值', 30, 0, 1, NULL, 1, NULL, NULL, '0'),
	(10, '黄色', 'yellow', 'color', '颜色值', 40, 0, 1, NULL, 1, NULL, NULL, '0'),
	(11, '橙色', 'orange', 'color', '颜色值', 50, 0, 1, NULL, 1, NULL, NULL, '0'),
	(12, '默认主题', 'default', 'theme', '主题方案', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(13, '天蓝主题', 'cerulean', 'theme', '主题方案', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(14, '橙色主题', 'readable', 'theme', '主题方案', 30, 0, 1, NULL, 1, NULL, NULL, '0'),
	(15, '红色主题', 'united', 'theme', '主题方案', 40, 0, 1, NULL, 1, NULL, NULL, '0'),
	(16, 'Flat主题', 'flat', 'theme', '主题方案', 60, 0, 1, NULL, 1, NULL, NULL, '0'),
	(17, '国家', '1', 'sys_area_type', '区域类型', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(18, '省份、直辖市', '2', 'sys_area_type', '区域类型', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(19, '地市', '3', 'sys_area_type', '区域类型', 30, 0, 1, NULL, 1, NULL, NULL, '0'),
	(20, '区县', '4', 'sys_area_type', '区域类型', 40, 0, 1, NULL, 1, NULL, NULL, '0'),
	(21, '公司', '5', 'sys_office_type', '机构类型', 60, 0, 1, NULL, 1, NULL, NULL, '0'),
	(22, '部门', '6', 'sys_office_type', '机构类型', 70, 0, 1, NULL, 1, NULL, NULL, '0'),
	(23, '小组', '7', 'sys_office_type', '机构类型', 80, 0, 1, NULL, 1, NULL, NULL, '0'),
	(24, '其它', '10', 'sys_office_type', '机构类型', 90, 0, 1, NULL, 1, NULL, NULL, '0'),
	(28, '一级', '1', 'sys_office_grade', '机构等级', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(29, '二级', '2', 'sys_office_grade', '机构等级', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(30, '三级', '3', 'sys_office_grade', '机构等级', 30, 0, 1, NULL, 1, NULL, NULL, '0'),
	(31, '四级', '4', 'sys_office_grade', '机构等级', 40, 0, 1, NULL, 1, NULL, NULL, '0'),
	(42, '基础主题', 'basic', 'cms_theme', '站点主题', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(43, '蓝色主题', 'blue', 'cms_theme', '站点主题', 20, 0, 1, NULL, 1, NULL, NULL, '1'),
	(44, '红色主题', 'red', 'cms_theme', '站点主题', 30, 0, 1, NULL, 1, NULL, NULL, '1'),
	(45, '文章模型', 'article', 'cms_module', '栏目模型', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(46, '图片模型', 'picture', 'cms_module', '栏目模型', 20, 0, 1, NULL, 1, NULL, NULL, '1'),
	(47, '下载模型', 'download', 'cms_module', '栏目模型', 30, 0, 1, NULL, 1, NULL, NULL, '1'),
	(48, '链接模型', 'link', 'cms_module', '栏目模型', 40, 0, 1, NULL, 1, NULL, NULL, '0'),
	(49, '专题模型', 'special', 'cms_module', '栏目模型', 50, 0, 1, NULL, 1, NULL, NULL, '1'),
	(50, '默认展现方式', '0', 'cms_show_modes', '展现方式', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(51, '首栏目内容列表', '1', 'cms_show_modes', '展现方式', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(52, '栏目第一条内容', '2', 'cms_show_modes', '展现方式', 30, 0, 1, NULL, 1, NULL, NULL, '0'),
	(53, '发布', '0', 'cms_del_flag', '内容状态', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(54, '删除', '1', 'cms_del_flag', '内容状态', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(55, '审核', '2', 'cms_del_flag', '内容状态', 15, 0, 1, NULL, 1, NULL, NULL, '0'),
	(56, '首页焦点图', '1', 'cms_posid', '推荐位', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(57, '栏目页文章推荐', '2', 'cms_posid', '推荐位', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(58, '咨询', '1', 'cms_guestbook', '留言板分类', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(59, '建议', '2', 'cms_guestbook', '留言板分类', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(60, '投诉', '3', 'cms_guestbook', '留言板分类', 30, 0, 1, NULL, 1, NULL, NULL, '0'),
	(61, '其它', '4', 'cms_guestbook', '留言板分类', 40, 0, 1, NULL, 1, NULL, NULL, '0'),
	(62, '公休', '1', 'oa_leave_type', '请假类型', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(63, '病假', '2', 'oa_leave_type', '请假类型', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(64, '事假', '3', 'oa_leave_type', '请假类型', 30, 0, 1, NULL, 1, NULL, NULL, '0'),
	(65, '调休', '4', 'oa_leave_type', '请假类型', 40, 0, 1, NULL, 1, NULL, NULL, '0'),
	(66, '婚假', '5', 'oa_leave_type', '请假类型', 60, 0, 1, NULL, 1, NULL, NULL, '0'),
	(67, '接入日志', '1', 'sys_log_type', '日志类型', 30, 0, 1, NULL, 1, NULL, NULL, '0'),
	(68, '异常日志', '2', 'sys_log_type', '日志类型', 40, 0, 1, NULL, 1, NULL, NULL, '0'),
	(69, '请假流程', 'leave', 'act_type', '流程类型', 10, 0, 1, NULL, 1, NULL, NULL, '1'),
	(70, '审批测试流程', 'test_audit', 'act_type', '流程类型', 20, 0, 1, NULL, 1, NULL, NULL, '1'),
	(71, '分类1', '1', 'act_category', '流程分类', 10, 0, 1, NULL, 1, '2019-04-18 17:08:51', NULL, '0'),
	(72, '分类2', '2', 'act_category', '流程分类', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(73, '增删改查', 'crud', 'gen_category', '代码生成分类', 10, 0, 1, NULL, 1, NULL, NULL, '1'),
	(74, '增删改查（包含从表）', 'crud_many', 'gen_category', '代码生成分类', 20, 0, 1, NULL, 1, NULL, NULL, '1'),
	(75, '树结构', 'tree', 'gen_category', '代码生成分类', 30, 0, 1, NULL, 1, NULL, NULL, '1'),
	(76, '=', '=', 'gen_query_type', '查询方式', 10, 0, 1, NULL, 1, NULL, NULL, '1'),
	(77, '!=', '!=', 'gen_query_type', '查询方式', 20, 0, 1, NULL, 1, NULL, NULL, '1'),
	(78, '&gt;', '&gt;', 'gen_query_type', '查询方式', 30, 0, 1, NULL, 1, NULL, NULL, '1'),
	(79, '&lt;', '&lt;', 'gen_query_type', '查询方式', 40, 0, 1, NULL, 1, NULL, NULL, '1'),
	(80, 'Between', 'between', 'gen_query_type', '查询方式', 50, 0, 1, NULL, 1, NULL, NULL, '1'),
	(81, 'Like', 'like', 'gen_query_type', '查询方式', 60, 0, 1, NULL, 1, NULL, NULL, '1'),
	(82, 'Left Like', 'left_like', 'gen_query_type', '查询方式', 70, 0, 1, NULL, 1, NULL, NULL, '1'),
	(83, 'Right Like', 'right_like', 'gen_query_type', '查询方式', 80, 0, 1, NULL, 1, NULL, NULL, '1'),
	(84, '文本框', 'input', 'gen_show_type', '字段生成方案', 10, 0, 1, NULL, 1, NULL, NULL, '1'),
	(85, '文本域', 'textarea', 'gen_show_type', '字段生成方案', 20, 0, 1, NULL, 1, NULL, NULL, '1'),
	(86, '下拉框', 'select', 'gen_show_type', '字段生成方案', 30, 0, 1, NULL, 1, NULL, NULL, '1'),
	(87, '复选框', 'checkbox', 'gen_show_type', '字段生成方案', 40, 0, 1, NULL, 1, NULL, NULL, '1'),
	(88, '单选框', 'radiobox', 'gen_show_type', '字段生成方案', 50, 0, 1, NULL, 1, NULL, NULL, '1'),
	(89, '日期选择', 'dateselect', 'gen_show_type', '字段生成方案', 60, 0, 1, NULL, 1, NULL, NULL, '1'),
	(90, '人员选择', 'userselect', 'gen_show_type', '字段生成方案', 70, 0, 1, NULL, 1, NULL, NULL, '1'),
	(91, '部门选择', 'officeselect', 'gen_show_type', '字段生成方案', 80, 0, 1, NULL, 1, NULL, NULL, '1'),
	(92, '区域选择', 'areaselect', 'gen_show_type', '字段生成方案', 90, 0, 1, NULL, 1, NULL, NULL, '1'),
	(93, 'String', 'String', 'gen_java_type', 'Java类型', 10, 0, 1, NULL, 1, NULL, NULL, '1'),
	(94, 'Long', 'Long', 'gen_java_type', 'Java类型', 20, 0, 1, NULL, 1, NULL, NULL, '1'),
	(95, '仅持久层', 'dao', 'gen_category', '代码生成分类', 40, 0, 1, NULL, 1, NULL, NULL, '1'),
	(96, '男', '1', 'sex', '性别', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(97, '女', '2', 'sex', '性别', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(98, 'Integer', 'Integer', 'gen_java_type', 'Java类型', 30, 0, 1, NULL, 1, NULL, NULL, '1'),
	(99, 'Double', 'Double', 'gen_java_type', 'Java类型', 40, 0, 1, NULL, 1, NULL, NULL, '1'),
	(100, 'Date', 'java.util.Date', 'gen_java_type', 'Java类型', 50, 0, 1, NULL, 1, NULL, NULL, '1'),
	(104, 'Custom', 'Custom', 'gen_java_type', 'Java类型', 90, 0, 1, NULL, 1, NULL, NULL, '1'),
	(105, '会议通告', '1', 'oa_notify_type', '通知通告类型', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(106, '奖惩通告', '2', 'oa_notify_type', '通知通告类型', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(107, '活动通告', '3', 'oa_notify_type', '通知通告类型', 30, 0, 1, NULL, 1, NULL, NULL, '0'),
	(108, '草稿', '0', 'oa_notify_status', '通知通告状态', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(109, '发布', '1', 'oa_notify_status', '通知通告状态', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(110, '未读', '0', 'oa_notify_read', '通知通告状态', 10, 0, 1, NULL, 1, NULL, NULL, '0'),
	(111, '已读', '1', 'oa_notify_read', '通知通告状态', 20, 0, 1, NULL, 1, NULL, NULL, '0'),
	(118, '关于', 'about', 'blog_type', '博客类型', NULL, NULL, NULL, NULL, NULL, NULL, '全url是:/blog/open/page/about', ''),
	(119, '交流', 'communication', 'blog_type', '博客类型', NULL, NULL, NULL, NULL, NULL, NULL, '', ''),
	(120, '文章', 'article', 'blog_type', '博客类型', NULL, NULL, NULL, NULL, NULL, NULL, '', ''),
	(121, '编码', 'code', 'hobby', '爱好', NULL, NULL, NULL, NULL, NULL, NULL, '', ''),
	(122, '删除', '1', 'del_flag', '删除标记', NULL, 0, NULL, NULL, 1, '2019-04-18 16:45:58', NULL, '0'),
	(123, '分类3', '5', 'act_category', '流程分类', 5, NULL, 1, '2019-04-18 00:00:00', 1, '2019-04-18 17:11:42', '啊手动阀打发啊手动阀打发', '1'),
	(124, '部门管理模块', 'Dept', 'func_component', '功能模型', 1, 0, NULL, NULL, NULL, NULL, NULL, '1'),
	(125, '用户管理模块', 'User', 'func_component', '功能模型', 2, 0, NULL, NULL, NULL, NULL, NULL, '1'),
	(126, '菜单管理模块', 'Menu', 'func_component', '功能模型', 3, 0, NULL, NULL, NULL, NULL, NULL, '1'),
	(127, '角色管理模块', 'Role', 'func_component', '功能模型', 4, 0, NULL, NULL, NULL, NULL, NULL, '1'),
	(128, '参数管理模块', 'SysPara', 'func_component', '功能模型', 5, 0, NULL, NULL, NULL, NULL, NULL, '1'),
	(129, '数据授权模块', 'userDeptPermission', 'func_component', '功能模型', 6, 0, NULL, NULL, NULL, NULL, NULL, '1'),
	(130, '设备管理模块', 'deviceManager', 'func_component', '功能模型', NULL, NULL, 1, '2019-05-06 12:01:05', 1, NULL, '', '1'),
	(131, '中心DBA专家组', '1', 'DBA', 'DBA组', 1, 0, NULL, NULL, NULL, NULL, NULL, '1'),
	(132, '部门DBA组', '2', 'DBA', 'DBA组', 2, 0, NULL, NULL, NULL, NULL, NULL, '1'),
	(133, '中心安全小组', '1', 'secgrp', '安全组', NULL, 0, NULL, NULL, NULL, NULL, NULL, '1'),
	(134, '部门安全小组', '2', 'secgrp', '安全组', NULL, 0, NULL, NULL, NULL, NULL, NULL, '1'),
	(135, '团队安全小组', '3', 'secgrp', '安全组', NULL, 0, NULL, NULL, NULL, NULL, NULL, '1');
/*!40000 ALTER TABLE `sys_para` ENABLE KEYS */;

-- Dumping structure for table grid-privdb.sys_plus
DROP TABLE IF EXISTS `sys_plus`;
CREATE TABLE IF NOT EXISTS `sys_plus` (
  `plug_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用来觉得现实在那个菜单下',
  `user_id_create` bigint(20) NOT NULL COMMENT '创建用户',
  `plug_name` varchar(100) NOT NULL COMMENT '插件名称',
  `menu_name` varchar(50) NOT NULL COMMENT '前台现实名称',
  `url` varchar(200) NOT NULL COMMENT '菜单URL',
  `menu_type` int(11) NOT NULL COMMENT '类型   0：目录   1：菜单   2：按钮  3：跳转',
  `menu_iron` varchar(50) NOT NULL COMMENT '现实图标',
  `order_num` int(11) NOT NULL COMMENT '现实排序',
  `version` varchar(50) DEFAULT NULL COMMENT '插件版本',
  `package_name` varchar(200) DEFAULT NULL COMMENT '软件包名称',
  `start_cmd` varchar(200) DEFAULT NULL COMMENT '启动命令',
  `stop_cmd` varchar(200) DEFAULT NULL,
  `status_cmd` varchar(200) DEFAULT NULL,
  `db_script` varchar(200) DEFAULT NULL COMMENT '数据库初始化脚本',
  `gmt_create` datetime DEFAULT NULL COMMENT '安装时间',
  PRIMARY KEY (`plug_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能插件表，每个功能模块归属一个菜单项。用来在右上展示所有系统具备的功能模块。实现上，功能模块就是插件。一个功能插件可以有多个menu功能菜单。';

-- Dumping data for table grid-privdb.sys_plus: ~0 rows (approximately)
DELETE FROM `sys_plus`;
/*!40000 ALTER TABLE `sys_plus` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_plus` ENABLE KEYS */;

-- Dumping structure for table grid-privdb.sys_role
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE IF NOT EXISTS `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(100) DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(20) DEFAULT NULL COMMENT '创建用户userid',
  `dept_id_create` bigint(20) DEFAULT NULL COMMENT '创建用户所在deptid',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '创建时间',
  `plug_id` bigint(20) DEFAULT NULL COMMENT '插件ID',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COMMENT='角色';

-- Dumping data for table grid-privdb.sys_role: ~5 rows (approximately)
DELETE FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`role_id`, `role_name`, `role_sign`, `remark`, `user_id_create`, `dept_id_create`, `gmt_create`, `gmt_modified`, `plug_id`) VALUES
	(1, '管理员角色', 'admin', '拥有最高权限', 1, 0, '2017-08-12 00:00:00', '2019-12-09 20:17:55', NULL),
	(48, '钻石会员', NULL, '高级用户', 1, 0, NULL, '2019-05-05 11:05:13', NULL),
	(56, '普通用户', NULL, '普通用户，没啥权限', NULL, NULL, NULL, NULL, NULL),
	(64, '11111111', NULL, '1111111111111', 9, 9, '2019-04-17 00:00:00', '2019-04-17 15:17:03', NULL),
	(65, '测试', NULL, '啊', 1, 0, '2019-04-26 13:54:14', NULL, NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- Dumping structure for table grid-privdb.sys_role_menu
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4674 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- Dumping data for table grid-privdb.sys_role_menu: ~117 rows (approximately)
DELETE FROM `sys_role_menu`;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES
	(3811, 64, 88),
	(3812, 64, 89),
	(3813, 64, 84),
	(3814, 64, 85),
	(3815, 64, 86),
	(4228, 65, 90),
	(4229, 65, 91),
	(4230, 65, 88),
	(4231, 65, 89),
	(4232, 65, 78),
	(4233, 65, 83),
	(4234, 65, 81),
	(4235, 65, 87),
	(4236, 65, 82),
	(4347, 48, 3),
	(4348, 48, 116),
	(4349, 48, 122),
	(4350, 48, 121),
	(4351, 48, 120),
	(4352, 48, 119),
	(4353, 48, 118),
	(4354, 48, 117),
	(4355, 48, 95),
	(4356, 48, 115),
	(4357, 48, 114),
	(4358, 48, 113),
	(4359, 48, 112),
	(4360, 48, 92),
	(4361, 48, 100),
	(4362, 48, 98),
	(4363, 48, 97),
	(4364, 48, 94),
	(4365, 48, 93),
	(4366, 48, 7),
	(4367, 48, 99),
	(4368, 48, 62),
	(4369, 48, 56),
	(4370, 48, 55),
	(4371, 48, 15),
	(4372, 48, 6),
	(4373, 48, 108),
	(4374, 48, 107),
	(4375, 48, 26),
	(4376, 48, 25),
	(4377, 48, 24),
	(4378, 48, 14),
	(4379, 48, 13),
	(4380, 48, 12),
	(4381, 48, 2),
	(4382, 48, 104),
	(4383, 48, 103),
	(4384, 48, 102),
	(4385, 48, 101),
	(4386, 48, 77),
	(4387, 48, 61),
	(4388, 48, 22),
	(4389, 48, 21),
	(4390, 48, 20),
	(4615, 1, 123),
	(4616, 1, 125),
	(4617, 1, 124),
	(4618, 1, 90),
	(4619, 1, 91),
	(4620, 1, 88),
	(4621, 1, 89),
	(4622, 1, 84),
	(4623, 1, 85),
	(4624, 1, 86),
	(4625, 1, 78),
	(4626, 1, 83),
	(4627, 1, 81),
	(4628, 1, 87),
	(4629, 1, 82),
	(4630, 1, 3),
	(4631, 1, 116),
	(4632, 1, 122),
	(4633, 1, 121),
	(4634, 1, 120),
	(4635, 1, 119),
	(4636, 1, 118),
	(4637, 1, 117),
	(4638, 1, 95),
	(4639, 1, 115),
	(4640, 1, 114),
	(4641, 1, 113),
	(4642, 1, 112),
	(4643, 1, 92),
	(4644, 1, 100),
	(4645, 1, 98),
	(4646, 1, 97),
	(4647, 1, 94),
	(4648, 1, 93),
	(4649, 1, 7),
	(4650, 1, 99),
	(4651, 1, 62),
	(4652, 1, 56),
	(4653, 1, 55),
	(4654, 1, 15),
	(4655, 1, 6),
	(4656, 1, 108),
	(4657, 1, 107),
	(4658, 1, 26),
	(4659, 1, 25),
	(4660, 1, 24),
	(4661, 1, 14),
	(4662, 1, 13),
	(4663, 1, 12),
	(4664, 1, 2),
	(4665, 1, 104),
	(4666, 1, 103),
	(4667, 1, 102),
	(4668, 1, 101),
	(4669, 1, 77),
	(4670, 1, 61),
	(4671, 1, 22),
	(4672, 1, 21),
	(4673, 1, 20);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;

-- Dumping structure for table grid-privdb.sys_task
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE IF NOT EXISTS `sys_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'cron表达式',
  `method_name` varchar(255) DEFAULT NULL COMMENT '任务调用的方法名',
  `is_concurrent` varchar(255) DEFAULT NULL COMMENT '任务是否有状态',
  `description` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `bean_class` varchar(255) DEFAULT NULL COMMENT '任务执行时调用哪个类的方法 包名+类名',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `job_status` varchar(255) DEFAULT NULL COMMENT '任务状态',
  `job_group` varchar(255) DEFAULT NULL COMMENT '任务分组',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `spring_bean` varchar(255) DEFAULT NULL COMMENT 'Spring bean',
  `job_name` varchar(255) DEFAULT NULL COMMENT '任务名',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Dumping data for table grid-privdb.sys_task: 1 rows
DELETE FROM `sys_task`;
/*!40000 ALTER TABLE `sys_task` DISABLE KEYS */;
INSERT INTO `sys_task` (`id`, `cron_expression`, `method_name`, `is_concurrent`, `description`, `update_by`, `bean_class`, `create_date`, `job_status`, `job_group`, `update_date`, `create_by`, `spring_bean`, `job_name`) VALUES
	(2, '0/10 * * * * ?', 'run1', '1', '', '4028ea815a3d2a8c015a3d2f8d2a0002', 'com.bootdo.common.task.WelcomeJob', '2017-05-19 18:30:56', '0', 'group1', '2017-05-19 18:31:07', NULL, '', 'welcomJob');
/*!40000 ALTER TABLE `sys_task` ENABLE KEYS */;

-- Dumping structure for table grid-privdb.sys_user
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `name` varchar(100) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `dept_id` bigint(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(255) DEFAULT NULL COMMENT '状态 0:禁用，1:正常',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `sex` bigint(32) DEFAULT NULL COMMENT '性别',
  `birth` datetime DEFAULT NULL COMMENT '出身日期',
  `pic_id` bigint(32) DEFAULT NULL,
  `live_address` varchar(500) DEFAULT NULL COMMENT '现居住地',
  `hobby` varchar(255) DEFAULT NULL COMMENT '爱好',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '所在城市',
  `district` varchar(255) DEFAULT NULL COMMENT '所在地区',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `idx_name` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8;

-- Dumping data for table grid-privdb.sys_user: ~7 rows (approximately)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`user_id`, `username`, `name`, `password`, `dept_id`, `email`, `mobile`, `status`, `user_id_create`, `gmt_create`, `gmt_modified`, `sex`, `birth`, `pic_id`, `live_address`, `hobby`, `province`, `city`, `district`) VALUES
	(1, 'admin', '超级管理员', '27bd386e70f280e24c2f4f2a549b82cf', 0, 'admin@example.com', '1111', 1, 1, '2017-08-15 00:00:00', '2017-08-15 00:00:00', 96, '2017-12-14 00:00:00', 138, 'ccc', '', '北京市', '北京市市辖区', '东城区'),
	(2, 'lsy', '临时用户', '6cf3bb3deba2aadbd41ec9a22511084e', 9, 'test@bootdo.com', NULL, 0, 1, '2017-08-14 00:00:00', '2017-08-14 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(36, 'ldh', '刘德华', 'bfd9394475754fbe45866eba97738c36', 7, 'ldh@bootdo.com', NULL, 1, NULL, NULL, NULL, NULL, '2019-02-05 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL),
	(124, 'wyf', '吴亦凡', 'e179e6f687bbd57b9d7efc4746c8090a', 32, 'wyf@bootdo.com', NULL, 1, NULL, NULL, NULL, NULL, '2018-04-11 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL),
	(130, 'lh', '鹿晗', '7924710cd673f68967cde70e188bb097', 8, 'lh@bootdo.com', NULL, 0, NULL, NULL, NULL, NULL, '2018-04-11 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL),
	(132, 'lyf', '刘亦菲', 'bfdb4e80df85c2073caa8750cdbdc46f', 32, 'lyf@bootdo.com', NULL, 1, NULL, NULL, NULL, NULL, '2018-03-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL),
	(158, 'aaaaaa', 'asdfasdfasdf', '45463b2b95c72f66ab3f64b88fe69c40', 0, 'adfadf@163.com', NULL, 0, NULL, NULL, NULL, NULL, '2019-03-24 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- Dumping structure for table grid-privdb.sys_user_dept
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE IF NOT EXISTS `sys_user_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `dept_id` bigint(20) NOT NULL,
  `permission` int(3) NOT NULL DEFAULT 0,
  `transmit` int(1) NOT NULL DEFAULT 0 COMMENT '拥有该区域的操作权限是否传递到该区域的下级区域：1传递；0不传递',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- Dumping data for table grid-privdb.sys_user_dept: ~4 rows (approximately)
DELETE FROM `sys_user_dept`;
/*!40000 ALTER TABLE `sys_user_dept` DISABLE KEYS */;
INSERT INTO `sys_user_dept` (`id`, `user_id`, `dept_id`, `permission`, `transmit`) VALUES
	(10, 1, 0, 0, 0),
	(12, 1, 32, 0, 0),
	(13, 158, 30, 224, 0),
	(15, 158, 28, 0, 0);
/*!40000 ALTER TABLE `sys_user_dept` ENABLE KEYS */;

-- Dumping structure for table grid-privdb.sys_user_role
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=213 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- Dumping data for table grid-privdb.sys_user_role: ~11 rows (approximately)
DELETE FROM `sys_user_role`;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES
	(124, NULL, 48),
	(185, 132, 1),
	(186, 132, 48),
	(187, 132, 56),
	(189, 124, 56),
	(190, 124, 48),
	(192, 36, 48),
	(196, 1, 1),
	(207, 158, 1),
	(209, 2, 1),
	(210, 130, 1);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

-- Dumping structure for table grid-privdb.sys_user_token
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE IF NOT EXISTS `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';

-- Dumping data for table grid-privdb.sys_user_token: ~0 rows (approximately)
DELETE FROM `sys_user_token`;
/*!40000 ALTER TABLE `sys_user_token` DISABLE KEYS */;
INSERT INTO `sys_user_token` (`user_id`, `token`, `expire_time`, `update_time`) VALUES
	(1, 'b87fcf0a-1139-4079-a34f-5bc27957e458', '2018-01-18 11:29:29', '2018-01-18 10:59:29');
/*!40000 ALTER TABLE `sys_user_token` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
