/*
 Navicat Premium Data Transfer

 Source Server         : TimeBlock
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 140.143.78.135:3307
 Source Schema         : timeblock

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 22/05/2020 00:57:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for achieve
-- ----------------------------
DROP TABLE IF EXISTS `achieve`;
CREATE TABLE `achieve`  (
  `achieve_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '0-2 金银铜计划，3-5 金银铜待办',
  `type` int(0) NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `note` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`achieve_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of achieve
-- ----------------------------
INSERT INTO `achieve` VALUES (1, 5, '浅尝辄止', '累计完成待办1天');
INSERT INTO `achieve` VALUES (2, 2, '走马观花', '累计进行计划1小时');
INSERT INTO `achieve` VALUES (3, 5, '学贵有恒', '累计完成待办7天');
INSERT INTO `achieve` VALUES (4, 2, '目不转睛', '累计进行计划24小时');
INSERT INTO `achieve` VALUES (5, 5, '锲而不舍', '累计完成待办30天');
INSERT INTO `achieve` VALUES (6, 2, '聚精会神', '累计进行计划50小时');
INSERT INTO `achieve` VALUES (7, 4, '持之以恒', '累计完成待办100天');
INSERT INTO `achieve` VALUES (8, 1, '全神贯注', '累计进行计划100小时');
INSERT INTO `achieve` VALUES (9, 4, '细水长流', '累计完成待办500天');
INSERT INTO `achieve` VALUES (10, 1, '心无旁骛', '累计进行计划500小时');
INSERT INTO `achieve` VALUES (11, 3, '聚沙成塔', '累计完成待办1000天');
INSERT INTO `achieve` VALUES (12, 0, '废寝忘食', '累计进行计划1000小时');

-- ----------------------------
-- Table structure for chart
-- ----------------------------
DROP TABLE IF EXISTS `chart`;
CREATE TABLE `chart`  (
  `chart_id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pass` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`chart_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `log_id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` int(0) NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for note
-- ----------------------------
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note`  (
  `note_id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `place` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `time` time(0) NULL DEFAULT NULL,
  PRIMARY KEY (`note_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for plan
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan`  (
  `plan_id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `urgency` int(0) NULL DEFAULT NULL,
  `pass` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `time` time(0) NULL DEFAULT NULL,
  PRIMARY KEY (`plan_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for todo
-- ----------------------------
DROP TABLE IF EXISTS `todo`;
CREATE TABLE `todo`  (
  `todo_id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `isChecked` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`todo_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avatar` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `todo_day` int(0) NULL DEFAULT NULL,
  `plan_time` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `friend` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `prize_plan` int(0) NULL DEFAULT NULL,
  `prize_todo` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'jc', 'jc', 'a', 30, '20m', '\'jc\',\'jc02\',\'jc01\',\'jc03\',', 12, 5);
INSERT INTO `user` VALUES (2, 'jc01', 'jc01', 'v', 50, '1h15m', '\'jc01\',\'jc\',', 0, -1);
INSERT INTO `user` VALUES (3, 'jc02', 'jc02', 'c', 60, '20h0m', '\'jc02\',\'jc\',', 6, 7);
INSERT INTO `user` VALUES (4, 'jc03', 'jc03', 's', 70, '2h10m', '\'jc03\',\'jc\',', 4, 5);
INSERT INTO `user` VALUES (5, 'jc04', 'jc04', '5', 10, '1m', '\'jc04\',', 2, 5);
INSERT INTO `user` VALUES (6, 'jc05', 'jc05', '5', 30, '40m', '\'jc05\',', 2, 5);

SET FOREIGN_KEY_CHECKS = 1;
