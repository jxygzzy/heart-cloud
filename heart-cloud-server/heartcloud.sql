/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : heartcloud

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 23/05/2022 13:55:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dir
-- ----------------------------
DROP TABLE IF EXISTS `dir`;
CREATE TABLE `dir`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '目录名',
  `parentId` bigint(0) NULL DEFAULT NULL COMMENT '父目录，为空说明是根目录',
  `userId` bigint(0) NOT NULL COMMENT '用户，表明属于哪个用户创建',
  `status` int(0) NOT NULL COMMENT '状态，0=正常，1=回收站，2=永久删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  INDEX `parentId`(`parentId`) USING BTREE,
  CONSTRAINT `dir_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `dir_ibfk_2` FOREIGN KEY (`parentId`) REFERENCES `dir` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '目录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dir
-- ----------------------------
INSERT INTO `dir` VALUES (1, '测试', NULL, 1, 0);
INSERT INTO `dir` VALUES (2, '新建文件夹', NULL, 1, 1);

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名',
  `key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件key',
  `dirId` bigint(0) NULL DEFAULT NULL COMMENT '为空则在根目录',
  `userId` bigint(0) NOT NULL COMMENT '表名属于哪个用户',
  `createTime` datetime(0) NOT NULL,
  `status` int(0) NOT NULL COMMENT '状态，0=正常，1=移至回收站，2=永久删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `dirId`(`dirId`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  CONSTRAINT `file_ibfk_1` FOREIGN KEY (`dirId`) REFERENCES `dir` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `file_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES (5, 'picto-ubuntu-orange - 副本.png', '359114c3-7344-4080-81df-384b40e1a5c5-picto-ubuntu-orange - 副本.png', NULL, 1, '2022-05-22 12:27:10', 0);
INSERT INTO `file` VALUES (6, '1209425.png', '9b132b76-2314-414a-82d1-c01a8c46b62b-1209425.png', 1, 1, '2022-05-22 13:26:06', 0);

-- ----------------------------
-- Table structure for recycle
-- ----------------------------
DROP TABLE IF EXISTS `recycle`;
CREATE TABLE `recycle`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `userId` bigint(0) NOT NULL COMMENT '用户Id',
  `fileId` bigint(0) NULL DEFAULT NULL COMMENT '文件id',
  `dirId` bigint(0) NULL DEFAULT NULL COMMENT '目录id 两者不能同时为空 ',
  `createTime` datetime(0) NOT NULL,
  `clearTime` datetime(0) NOT NULL COMMENT '清除时间，一般回收站保存30天',
  `status` int(0) NOT NULL COMMENT '状态，0=回收站，1=永久删除，2=已恢复',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fileId`(`fileId`) USING BTREE,
  INDEX `dirId`(`dirId`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  CONSTRAINT `recycle_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `file` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `recycle_ibfk_2` FOREIGN KEY (`dirId`) REFERENCES `dir` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `recycle_ibfk_3` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '回收站' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recycle
-- ----------------------------
INSERT INTO `recycle` VALUES (1, 1, NULL, 2, '2022-05-22 11:53:00', '2022-06-21 11:53:00', 0);

-- ----------------------------
-- Table structure for share
-- ----------------------------
DROP TABLE IF EXISTS `share`;
CREATE TABLE `share`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `fileId` bigint(0) NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '明文存储，一般较短',
  `token` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问token，用自增id会被暴力获取',
  `type` int(0) NOT NULL COMMENT '0=无密码，1=需要密码',
  `status` int(0) NOT NULL COMMENT '0=正在分享，1=已过期，2=分享已撤销',
  `createTime` datetime(0) NOT NULL,
  `expireTime` datetime(0) NOT NULL COMMENT '过期时间',
  `userId` bigint(0) NOT NULL COMMENT '分享人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fileId`(`fileId`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  CONSTRAINT `share_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `file` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `share_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分享文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of share
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `userId` bigint(0) NOT NULL COMMENT '用户id',
  `loginTime` datetime(0) NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  CONSTRAINT `sys_log_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统登录日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 1, '2022-05-15 08:41:32');
INSERT INTO `sys_log` VALUES (2, 1, '2022-05-15 09:33:20');
INSERT INTO `sys_log` VALUES (3, 1, '2022-05-15 09:33:49');
INSERT INTO `sys_log` VALUES (4, 1, '2022-05-20 05:55:34');
INSERT INTO `sys_log` VALUES (5, 1, '2022-05-20 06:38:53');
INSERT INTO `sys_log` VALUES (6, 2, '2022-05-20 06:39:26');
INSERT INTO `sys_log` VALUES (7, 1, '2022-05-22 07:10:05');
INSERT INTO `sys_log` VALUES (8, 1, '2022-05-22 07:10:11');
INSERT INTO `sys_log` VALUES (9, 1, '2022-05-22 10:20:05');
INSERT INTO `sys_log` VALUES (10, 1, '2022-05-22 12:27:44');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码（加密后）',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '状态 0=未审核，1=审核通过，2=审核拒绝',
  `role` int(0) NOT NULL DEFAULT 0 COMMENT '角色 0=用户，1=管理员',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `tel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `enable` int(0) NOT NULL DEFAULT 1 COMMENT '是否启用 0=禁用，1=启用 默认1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'heart', '0a27077bcbf947d7a8cc3fdec35b554d', '心跳用户', 1, 0, NULL, NULL, 1);
INSERT INTO `user` VALUES (2, 'heartadmin', 'c1a064f8b2dd29e7868cdca393f7ee8f', '心跳管理员', 1, 1, NULL, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
