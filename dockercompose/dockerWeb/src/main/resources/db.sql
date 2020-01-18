/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : shirodemo

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2016-08-12 16:17:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_authc_map
-- ----------------------------
DROP TABLE IF EXISTS `t_authc_map`;
CREATE TABLE `t_authc_map` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`authcType`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`val`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=3

;

-- ----------------------------
-- Records of t_authc_map
-- ----------------------------
BEGIN;
INSERT INTO `t_authc_map` VALUES ('1', 'perms', '/user', 'user:query'), ('2', 'authc', '/**', null);
COMMIT;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`role_id`  int(11) NULL DEFAULT NULL ,
`dataDomain`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`dataType`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`operation`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK9E830A7A74F44EC2` (`role_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=8

;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
BEGIN;
INSERT INTO `t_permission` VALUES ('1', '2', null, 'user', 'add'), ('3', '2', null, 'user', 'update'), ('4', '3', null, 'user', 'query'), ('6', '1', null, 'system', 'query,add,update,del'), ('7', '1', null, 'user', 'del');
COMMIT;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`rolename`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`createTime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=4

;

-- ----------------------------
-- Records of t_role
-- ----------------------------
BEGIN;
INSERT INTO `t_role` VALUES ('1', 'admin', null), ('2', 'manager', null), ('3', 'normal', null);
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`password`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`username`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`createTime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=16

;

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES ('1', '111111', 'tom', null), ('2', '111111', 'jack', null), ('3', '111111', 'rose', null), ('7', '111', '111', null), ('8', 'qqq', 'qqq', '2016-08-12 14:23:37'), ('15', 'admin', 'admin', '2016-08-12 15:49:30');
COMMIT;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
`role_id`  int(11) NOT NULL ,
`user_id`  int(11) NOT NULL ,
FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK331DEE5F74F44EC2` (`role_id`) USING BTREE ,
INDEX `FK331DEE5F1A1F12A2` (`user_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
BEGIN;
INSERT INTO `t_user_role` VALUES ('1', '1'), ('2', '2'), ('3', '2'), ('3', '3'), ('1', '15');
COMMIT;

-- ----------------------------
-- Auto increment value for t_authc_map
-- ----------------------------
ALTER TABLE `t_authc_map` AUTO_INCREMENT=3;

-- ----------------------------
-- Auto increment value for t_permission
-- ----------------------------
ALTER TABLE `t_permission` AUTO_INCREMENT=8;

-- ----------------------------
-- Auto increment value for t_role
-- ----------------------------
ALTER TABLE `t_role` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for t_user
-- ----------------------------
ALTER TABLE `t_user` AUTO_INCREMENT=16;
