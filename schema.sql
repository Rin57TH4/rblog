/*
Navicat MySQL Result Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : db_mblog

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2019-01-18 22:17:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mto_channel
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE channel (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key_` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `status` int(5) NOT NULL,
  `thumbnail` varchar(128) DEFAULT NULL,
  `weight` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mto_channel
-- ----------------------------
INSERT INTO channel VALUES ('1', 'banner', 'banner', '1', '', '3');
INSERT INTO channel VALUES ('2', 'blog', 'Blog', '0', '', '2');
INSERT INTO channel VALUES ('3', 'jotting', 'Tiểu luận', '0', '', '1');
INSERT INTO channel VALUES ('4', 'share', 'Chia sẻ', '0', '', '0');

-- ----------------------------
-- Table structure for mto_options
-- ----------------------------
DROP TABLE IF EXISTS `options`;
CREATE TABLE options (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key_` varchar(32) DEFAULT NULL,
  `type` int(5) DEFAULT 0,
  `value` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mto_options
-- ----------------------------
INSERT INTO options VALUES ('1', 'site_name', '0', 'Rin Blog');
INSERT INTO options VALUES ('2', 'site_domain', '0', 'http://blog.hanoidev.net');
INSERT INTO options VALUES ('3', 'site_keywords', '0', 'Rin,Blog,Cộng đồng');
INSERT INTO options VALUES ('4', 'site_description', '0', 'Rin, Trở thành một cộng đồng công nghệ bao gồm');
INSERT INTO options VALUES ('5', 'site_metas', '0', '');
INSERT INTO options VALUES ('6', 'site_copyright', '0', 'Copyright © Rin Blog');
INSERT INTO options VALUES ('7', 'site_icp', '0', '');
INSERT INTO options VALUES ('8', 'qq_callback', '0', '');
INSERT INTO options VALUES ('9', 'qq_app_id', '0', '');
INSERT INTO options VALUES ('10', 'qq_app_key', '0', '');
INSERT INTO options VALUES ('11', 'weibo_callback', '0', '');
INSERT INTO options VALUES ('12', 'weibo_client_id', '0', '');
INSERT INTO options VALUES ('13', 'weibo_client_sercret', '0', '');
INSERT INTO options VALUES ('14', 'github_callback', '0', '');
INSERT INTO options VALUES ('15', 'github_client_id', '0', '');
INSERT INTO options VALUES ('16', 'github_secret_key', '0', '');

-- ----------------------------
-- Table structure for mto_user
-- ----------------------------
DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE blog_user (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `avatar` varchar(128) DEFAULT '/dist/images/ava/default.png',
  `email` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `status` int(5) NOT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `gender` int(5) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `comments` int(11) NOT NULL,
  `posts` int(11) NOT NULL,
  `signature` varchar(140) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USERNAME` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mto_user
-- ----------------------------
INSERT INTO blog_user VALUES ('1', 'admin', 'Đậu Adzuki', 'https://en.gravatar.com/userimage/154673030/b9a54b5b990a61cc074668b2e2a0b8c0.png', 'example@com', '3TGCQF25BLHU9R7IQUITN0FCC5', '0', '2017-08-06 17:52:41', '2017-07-26 11:08:36', '2017-10-17 13:24:13', '0', '1', '0', '0', '');

-- ----------------------------
-- Table structure for mto_user_oauth
-- ----------------------------
DROP TABLE IF EXISTS `user_oauth`;
CREATE TABLE user_oauth (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `access_token` varchar(128) DEFAULT NULL,
  `expire_in` varchar(128) DEFAULT NULL,
  `oauth_code` varchar(128) DEFAULT NULL,
  `oauth_type` int(11) DEFAULT NULL,
  `oauth_user_id` varchar(128) DEFAULT NULL,
  `refresh_token` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mto_user_oauth
-- ----------------------------

-- ----------------------------
-- Table structure for shiro_permission
-- ----------------------------
DROP TABLE IF EXISTS `shiro_permission`;
CREATE TABLE `shiro_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(140) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  `parent_id` bigint(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `weight` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shiro_permission
-- ----------------------------
INSERT INTO `shiro_permission` VALUES ('1', 'Nhập nền', 'admin', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('2', 'Bộ phận quản lý', 'channel:list', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('3', 'Chỉnh sửa phần', 'channel:update', '2', '0', '0');
INSERT INTO `shiro_permission` VALUES ('4', 'Xóa phần', 'channel:delete', '2', '0', '0');
INSERT INTO `shiro_permission` VALUES ('5', 'Điều quản lý', 'post:list', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('6', 'Chỉnh sửa bài viết', 'post:update', '5', '0', '0');
INSERT INTO `shiro_permission` VALUES ('7', 'Xóa bài viết', 'post:delete', '5', '0', '0');
INSERT INTO `shiro_permission` VALUES ('8', 'Quản lý bình luận', 'comment:list', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('9', 'Xóa bình luận', 'comment:delete', '8', '0', '0');
INSERT INTO `shiro_permission` VALUES ('10', 'Quản lý người dùng', 'user:list', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('11', 'Ủy quyền người dùng', 'user:role', '10', '0', '0');
INSERT INTO `shiro_permission` VALUES ('12', 'Thay đổi mật khẩu', 'user:pwd', '10', '0', '0');
INSERT INTO `shiro_permission` VALUES ('13', 'Kích hoạt người dùng', 'user:open', '10', '0', '0');
INSERT INTO `shiro_permission` VALUES ('14', 'Đóng người dùng', 'user:close', '10', '0', '0');
INSERT INTO `shiro_permission` VALUES ('15', 'Quản lý vai trò', 'role:list', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('16', 'Sửa đổi vai trò', 'role:update', '15', '0', '0');
INSERT INTO `shiro_permission` VALUES ('17', 'Xóa vai trò', 'role:delete', '15', '0', '0');
INSERT INTO `shiro_permission` VALUES ('18', 'Quản lý chủ đề', 'theme:index', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('19', 'Cấu hình hệ thống', 'options:index', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('20', 'Sửa đổi cấu hình', 'options:update', '19', '0', '0');

-- ----------------------------
-- Table structure for shiro_role
-- ----------------------------
DROP TABLE IF EXISTS `shiro_role`;
CREATE TABLE `shiro_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(140) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shiro_role
-- ----------------------------
INSERT INTO `shiro_role` VALUES ('1', null, 'admin', '0');

-- ----------------------------
-- Table structure for shiro_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `shiro_role_permission`;
CREATE TABLE `shiro_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shiro_role_permission
-- ----------------------------
INSERT INTO `shiro_role_permission` VALUES ('1', '1', '1');
INSERT INTO `shiro_role_permission` VALUES ('2', '2', '1');
INSERT INTO `shiro_role_permission` VALUES ('3', '3', '1');
INSERT INTO `shiro_role_permission` VALUES ('4', '4', '1');
INSERT INTO `shiro_role_permission` VALUES ('5', '5', '1');
INSERT INTO `shiro_role_permission` VALUES ('6', '6', '1');
INSERT INTO `shiro_role_permission` VALUES ('7', '7', '1');
INSERT INTO `shiro_role_permission` VALUES ('8', '8', '1');
INSERT INTO `shiro_role_permission` VALUES ('9', '9', '1');
INSERT INTO `shiro_role_permission` VALUES ('10', '10', '1');
INSERT INTO `shiro_role_permission` VALUES ('11', '11', '1');
INSERT INTO `shiro_role_permission` VALUES ('12', '12', '1');
INSERT INTO `shiro_role_permission` VALUES ('13', '13', '1');
INSERT INTO `shiro_role_permission` VALUES ('14', '14', '1');
INSERT INTO `shiro_role_permission` VALUES ('15', '15', '1');
INSERT INTO `shiro_role_permission` VALUES ('16', '16', '1');
INSERT INTO `shiro_role_permission` VALUES ('17', '17', '1');
INSERT INTO `shiro_role_permission` VALUES ('18', '18', '1');
INSERT INTO `shiro_role_permission` VALUES ('19', '19', '1');
INSERT INTO `shiro_role_permission` VALUES ('20', '20', '1');
-- ----------------------------
-- Table structure for shiro_user_role
-- ----------------------------
DROP TABLE IF EXISTS `shiro_user_role`;
CREATE TABLE `shiro_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shiro_user_role
-- ----------------------------
INSERT INTO `shiro_user_role` VALUES ('1', '1', '1');
