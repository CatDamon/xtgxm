/*
Navicat MySQL Data Transfer

Source Server         : cms
Source Server Version : 50627
Source Host           : localhost:3306
Source Database       : cms

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2017-11-15 11:51:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ry_permission`
-- ----------------------------
DROP TABLE IF EXISTS `ry_permission`;
CREATE TABLE `ry_permission` (
  `per_id` varchar(32) NOT NULL COMMENT '权限ID',
  `per_name` varchar(50) DEFAULT NULL COMMENT '权限名',
  `parentid` varchar(32) DEFAULT NULL COMMENT '所属上级权限ID',
  `parentname` varchar(50) DEFAULT NULL COMMENT '所属上级权限名称',
  `url` varchar(255) DEFAULT NULL COMMENT '菜单链接',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `orders` int(11) DEFAULT '0' COMMENT '所在目录顺序',
  `isheader` int(11) DEFAULT '0' COMMENT '0表示根目录,1表示非根目录',
  `ismenuorpoint` int(11) DEFAULT '0' COMMENT '0代表菜单权限,1代表页面权限点',
  PRIMARY KEY (`per_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of ry_permission
-- ----------------------------
INSERT INTO `ry_permission` VALUES ('035b29eef4e44df08ae030fc63d2e1a3', '激活', '2c41b50f6ae84e7f9c16e64ce3de2224', '人员管理', '/system/userManage/activativeAccount', null, '5', '1', '1');
INSERT INTO `ry_permission` VALUES ('08dd4c40be934b7b98c2ef3dfb44ad89', '分配角色', '2c41b50f6ae84e7f9c16e64ce3de2224', '人员管理', '/system/userManage/toChmodPage', null, '1', '1', '1');
INSERT INTO `ry_permission` VALUES ('2c41b50f6ae84e7f9c16e64ce3de2224', '人员管理', '95b38701de9644b397bb8e8e51675fa2', '系统管理', '/system/userManage/toUserManage', null, '1', '0', '0');
INSERT INTO `ry_permission` VALUES ('30ecf90757d84fb98a12526a9b56a812', '删除', '2c41b50f6ae84e7f9c16e64ce3de2224', '人员管理', '/system/userManage/delUser', null, '1', '1', '1');
INSERT INTO `ry_permission` VALUES ('6208b48fe5fd4cf89005fe7870ae3e92', '修改', '2c41b50f6ae84e7f9c16e64ce3de2224', '人员管理', '/system/userManage/toEditUser', null, '1', '1', '1');
INSERT INTO `ry_permission` VALUES ('7606e619466a4273a3bd0e07655feab2', '修改', 'e5dd7b9d3d4444d0b1a212867d7e8bcb', '角色管理', '/system/RoleManageCtrl/toEditRole', null, '1', '1', '1');
INSERT INTO `ry_permission` VALUES ('7aa0768fe817484ea6e92d2d75a0177e', '删除', 'e5dd7b9d3d4444d0b1a212867d7e8bcb', '角色管理', '/system/RoleManageCtrl/delRole', null, '1', '1', '1');
INSERT INTO `ry_permission` VALUES ('7acd94ed5a0048119ec3899350a53af4', '分配权限', 'e5dd7b9d3d4444d0b1a212867d7e8bcb', '角色管理', '/system/RoleManageCtrl/toChmodRolePri', null, '1', '1', '1');
INSERT INTO `ry_permission` VALUES ('8e4e0ab2d89b41ee94ed5dad3fe191d0', '保存', '2c41b50f6ae84e7f9c16e64ce3de2224', '人员管理', '/system/userManage/toAddUser', null, '1', '1', '1');
INSERT INTO `ry_permission` VALUES ('95b38701de9644b397bb8e8e51675fa2', '系统管理', 'b5f01e3e1d374e9097e07b22737ac558', '主菜单', null, null, '2', '0', '0');
INSERT INTO `ry_permission` VALUES ('9e62e612f4fe48b3819629247a070fc1', '菜单管理', '95b38701de9644b397bb8e8e51675fa2', '系统管理', '/system/MenuManageCtrl/toMenuIndex', null, '3', '0', '0');
INSERT INTO `ry_permission` VALUES ('afb0c814454041369d212beb9cc3429d', '业务管理', 'b5f01e3e1d374e9097e07b22737ac558', '主菜单', null, null, '2', '0', '0');
INSERT INTO `ry_permission` VALUES ('b5f01e3e1d374e9097e07b22737ac558', '主菜单', null, null, null, null, '1', '1', '0');
INSERT INTO `ry_permission` VALUES ('e5dd7b9d3d4444d0b1a212867d7e8bcb', '角色管理', '95b38701de9644b397bb8e8e51675fa2', '系统管理', '/system/RoleManageCtrl/toRoleIndex', null, '2', '0', '0');
INSERT INTO `ry_permission` VALUES ('e9aecdfe225d4b289f94806b9129bbaa', '添加', 'e5dd7b9d3d4444d0b1a212867d7e8bcb', '角色管理', '/system/RoleManageCtrl/toAddRole', null, '1', '1', '1');

-- ----------------------------
-- Table structure for `ry_role`
-- ----------------------------
DROP TABLE IF EXISTS `ry_role`;
CREATE TABLE `ry_role` (
  `roleid` varchar(32) NOT NULL COMMENT '角色ID',
  `rolename` varchar(50) NOT NULL COMMENT '角色名',
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of ry_role
-- ----------------------------
INSERT INTO `ry_role` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', '超级管理员');

-- ----------------------------
-- Table structure for `ry_roleper`
-- ----------------------------
DROP TABLE IF EXISTS ry_roleper;
CREATE TABLE ry_roleper (
  roleid	VARCHAR(32) NOT NULL COMMENT	'角色ID',
  per_id	VARCHAR(32) NOT NULL COMMENT	'权限ID',
  PRIMARY KEY (roleid,per_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '角色权限表';

-- ----------------------------
-- Records of ry_roleper
-- ----------------------------
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', 'b5f01e3e1d374e9097e07b22737ac558');
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', '95b38701de9644b397bb8e8e51675fa2');
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', '2c41b50f6ae84e7f9c16e64ce3de2224');
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', '08dd4c40be934b7b98c2ef3dfb44ad89');
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', '30ecf90757d84fb98a12526a9b56a812');
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', '6208b48fe5fd4cf89005fe7870ae3e92');
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', '8e4e0ab2d89b41ee94ed5dad3fe191d0');
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', '035b29eef4e44df08ae030fc63d2e1a3');
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', 'e5dd7b9d3d4444d0b1a212867d7e8bcb');
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', '7606e619466a4273a3bd0e07655feab2');
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', '7aa0768fe817484ea6e92d2d75a0177e');
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', '7acd94ed5a0048119ec3899350a53af4');
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', 'e9aecdfe225d4b289f94806b9129bbaa');
INSERT INTO `ry_roleper` VALUES ('fe935765ebb9454ca93ad45127b1ef9e', '9e62e612f4fe48b3819629247a070fc1');

-- ----------------------------
-- Table structure for `ry_user`
-- ----------------------------
DROP TABLE IF EXISTS `ry_user`;
CREATE TABLE `ry_user` (
  `userid` varchar(32) NOT NULL COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `pre_logintime` datetime DEFAULT NULL COMMENT '上次登录时间',
  `ipadress` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `available` int(1) NOT NULL DEFAULT '0' COMMENT '可用状态',
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of ry_user
-- ----------------------------
INSERT INTO `ry_user` VALUES ('25f45466-0d8b-11e7-887c-b46d8339', 'admin', '6986c8875303cd0ffc4ac6b0ebf5a0af', '2017-03-13 00:00:00', '192.168.1.22', '1');

-- ----------------------------
-- Table structure for `ry_userrole`
-- ----------------------------
DROP TABLE IF EXISTS ry_userrole;
CREATE TABLE ry_userrole (
  userid	VARCHAR(32) NOT NULL COMMENT	'用户ID',
  roleid	VARCHAR(32) NOT NULL COMMENT	'角色ID',
  PRIMARY KEY (userid,roleid)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '用户角色表';

-- ----------------------------
-- Records of ry_userrole
-- ----------------------------
INSERT INTO `ry_userrole` VALUES ('25f45466-0d8b-11e7-887c-b46d8339', 'fe935765ebb9454ca93ad45127b1ef9e');
