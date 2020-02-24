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


-- Dumping database structure for grid-auditdb
CREATE DATABASE IF NOT EXISTS `grid-auditdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `grid-auditdb`;

-- Dumping structure for table grid-auditdb.sys_log
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE IF NOT EXISTS `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `time` int(11) DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- Dumping data for table grid-auditdb.sys_log: ~63 rows (approximately)
DELETE FROM `sys_log`;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
INSERT INTO `sys_log` (`id`, `user_id`, `username`, `operation`, `time`, `method`, `params`, `ip`, `gmt_create`) VALUES
	(1, 0, '', '登录', 1154, 'com.softcloud.grid.privadmin.controller.LoginController.login()', NULL, '127.0.0.1', '2019-12-09 13:44:06'),
	(2, 0, '', '登录', 43, 'com.softcloud.grid.privadmin.controller.LoginController.login()', NULL, '127.0.0.1', '2019-12-09 13:44:13'),
	(3, 0, '', '登录', 37, 'com.softcloud.grid.privadmin.controller.LoginController.login()', NULL, '127.0.0.1', '2019-12-09 13:44:25'),
	(4, 0, '', '登录', 28, 'com.softcloud.grid.privadmin.controller.LoginController.login()', NULL, '127.0.0.1', '2019-12-09 13:45:14'),
	(5, 0, '', '登录', 9, 'com.softcloud.grid.privadmin.controller.LoginController.login()', NULL, '127.0.0.1', '2019-12-09 13:45:56'),
	(6, 0, '', '登录', 25, 'com.softcloud.grid.privadmin.controller.LoginController.login()', NULL, '127.0.0.1', '2019-12-09 13:46:09'),
	(7, 0, '', '登录', 34, 'com.softcloud.grid.privadmin.controller.LoginController.login()', NULL, '127.0.0.1', '2019-12-09 13:48:09'),
	(8, 0, '', '登录', 1115, 'com.softcloud.grid.privadmin.controller.LoginController.login()', NULL, '127.0.0.1', '2019-12-09 14:13:55'),
	(9, 1, 'admin', '获取部门列表', 30, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 14:14:01'),
	(10, 1, 'admin', '获取用户列表', 23, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 14:14:02'),
	(11, 1, 'admin', '获取用户列表', 22, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 14:14:06'),
	(12, 1, 'admin', '获取部门列表', 113, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 14:14:06'),
	(13, 1, 'admin', '获取部门列表', 16, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 14:20:09'),
	(14, 1, 'admin', '获取用户列表', 20, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 14:20:21'),
	(15, 1, 'admin', '获取用户列表', 11, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 14:20:29'),
	(16, 1, 'admin', '获取用户列表', 19, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 14:20:31'),
	(17, 1, 'admin', '获取部门列表', 108, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 14:20:31'),
	(18, 0, '', '登录', 176, 'com.softcloud.grid.privadmin.controller.LoginController.login()', NULL, '127.0.0.1', '2019-12-09 19:59:11'),
	(19, 0, '', '登录', 417, 'com.softcloud.grid.privadmin.controller.LoginController.login()', NULL, '127.0.0.1', '2019-12-09 19:59:15'),
	(20, 1, 'admin', '获取部门列表', 43, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 19:59:50'),
	(21, 1, 'admin', '获取用户列表', 18, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 19:59:58'),
	(22, 1, 'admin', '获取部门列表', 164, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 19:59:58'),
	(23, 1, 'admin', '获取用户列表', 15, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 20:00:01'),
	(24, 1, 'admin', '获取部门列表', 104, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:00:01'),
	(25, 1, 'admin', '获取部门列表', 13, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:12:56'),
	(26, 1, 'admin', '获取用户列表', 19, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 20:14:21'),
	(27, 1, 'admin', '获取部门列表', 108, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:14:21'),
	(28, 0, '', '登录', 34, 'com.softcloud.grid.privadmin.controller.LoginController.login()', NULL, '127.0.0.1', '2019-12-09 20:18:04'),
	(29, 1, 'admin', '获取用户列表', 23, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 20:20:38'),
	(30, 1, 'admin', '获取部门列表', 92, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:20:38'),
	(31, 1, 'admin', '获取用户列表', 14, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 20:21:53'),
	(32, 1, 'admin', '获取部门列表', 100, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:21:53'),
	(33, 1, 'admin', '获取用户列表', 13, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 20:22:15'),
	(34, 1, 'admin', '获取部门列表', 107, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:22:16'),
	(35, 1, 'admin', '获取指定查询条件的部门授权列表', 36, 'com.softcloud.grid.privadmin.controller.UserDeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:22:20'),
	(36, 1, 'admin', '获取用户列表', 15, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 20:22:34'),
	(37, 1, 'admin', '获取部门列表', 94, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:22:34'),
	(38, 1, 'admin', '获取用户列表', 15, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 20:22:45'),
	(39, 1, 'admin', '获取用户列表', 16, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 20:22:50'),
	(40, 1, 'admin', '获取部门列表', 99, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:22:50'),
	(41, 1, 'admin', '获取用户列表', 11, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 20:22:50'),
	(42, 1, 'admin', '获取部门列表', 12, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:22:51'),
	(43, 1, 'admin', '获取部门列表', 11, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:26:09'),
	(44, 1, 'admin', '获取部门列表', 9, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:35:14'),
	(45, 1, 'admin', '获取用户列表', 15, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 20:35:58'),
	(46, 1, 'admin', '获取用户列表', 17, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 20:36:03'),
	(47, 1, 'admin', '获取部门列表', 105, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:36:03'),
	(48, 1, 'admin', '获取指定查询条件的部门授权列表', 21, 'com.softcloud.grid.privadmin.controller.UserDeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:36:06'),
	(49, 1, 'admin', '获取指定查询条件的部门授权列表', 20, 'com.softcloud.grid.privadmin.controller.UserDeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:36:37'),
	(50, 1, 'admin', '获取指定查询条件的部门授权列表', 18, 'com.softcloud.grid.privadmin.controller.UserDeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:36:41'),
	(51, 1, 'admin', '获取指定查询条件的部门授权列表', 14, 'com.softcloud.grid.privadmin.controller.UserDeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:36:42'),
	(52, 1, 'admin', '获取用户列表', 30, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 20:37:01'),
	(53, 1, 'admin', '获取部门列表', 138, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:37:01'),
	(54, 1, 'admin', '获取指定查询条件的部门授权列表', 18, 'com.softcloud.grid.privadmin.controller.UserDeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:37:04'),
	(55, 1, 'admin', '获取用户列表', 15, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 20:37:34'),
	(56, 1, 'admin', '获取部门列表', 96, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:37:34'),
	(57, 1, 'admin', '获取指定查询条件的部门授权列表', 24, 'com.softcloud.grid.privadmin.controller.UserDeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:37:35'),
	(58, 1, 'admin', '获取指定查询条件的部门授权列表', 14, 'com.softcloud.grid.privadmin.controller.UserDeptController.list()', NULL, '127.0.0.1', '2019-12-09 20:38:15'),
	(59, 1, 'admin', '获取部门列表', 31, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-09 21:20:27'),
	(60, 1, 'admin', '获取用户列表', 32, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-09 21:20:28'),
	(61, 0, '', '登录', 1149, 'com.softcloud.grid.privadmin.controller.LoginController.login()', NULL, '127.0.0.1', '2019-12-10 14:19:03'),
	(62, 1, 'admin', '获取部门列表', 39, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-10 14:23:06'),
	(63, 1, 'admin', '获取用户列表', 19, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-10 14:23:07'),
	(64, 1, 'admin', '获取用户列表', 22, 'com.softcloud.grid.privadmin.controller.UserController.listByPage()', NULL, '127.0.0.1', '2019-12-10 14:23:09'),
	(65, 1, 'admin', '获取部门列表', 167, 'com.softcloud.grid.privadmin.controller.DeptController.list()', NULL, '127.0.0.1', '2019-12-10 14:23:09');
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
