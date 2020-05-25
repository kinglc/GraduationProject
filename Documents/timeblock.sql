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

 Date: 26/05/2020 06:06:35
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
  `date` date NULL DEFAULT NULL,
  `pass_red` int(0) NULL DEFAULT NULL,
  `pass_yellow` int(0) NULL DEFAULT NULL,
  `pass_green` int(0) NULL DEFAULT NULL,
  `pass_blue` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`chart_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chart
-- ----------------------------
INSERT INTO `chart` VALUES (6, 'jc', '2019-01-01', 60, 120, 180, 240);
INSERT INTO `chart` VALUES (7, 'jc', '2019-02-04', 10, 0, 0, 0);
INSERT INTO `chart` VALUES (8, 'jc', '2020-01-01', 10, 30, 50, 70);
INSERT INTO `chart` VALUES (9, 'jc', '2020-01-02', 70, 50, 30, 10);
INSERT INTO `chart` VALUES (10, 'jc', '2020-05-22', 50, 60, 0, 0);
INSERT INTO `chart` VALUES (11, 'jc', '2020-05-23', 1, 60, 30, 0);
INSERT INTO `chart` VALUES (12, 'jc', '2020-05-25', 0, 1, 7, 0);

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `log_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` int(0) NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (8, 'jc', 0, '完成7项待办', '2020-05-23');
INSERT INTO `log` VALUES (9, 'jc', 2, '达成成就“浅尝辄止”', '2020-05-23');
INSERT INTO `log` VALUES (10, 'jc', 0, '经过1h30m，完成计划“gg”', '2020-05-24');
INSERT INTO `log` VALUES (11, 'jc', 0, '经过20m，完成计划“ee”', '2020-05-24');
INSERT INTO `log` VALUES (12, 'jc', 0, '经过20分钟，完成计划“ee”', '2020-05-25');
INSERT INTO `log` VALUES (13, 'jc', 2, '达成成就”走马观花”', '2020-05-25');
INSERT INTO `log` VALUES (14, 'jc', 0, '经过7分钟，完成计划“aa”', '2020-05-25');
INSERT INTO `log` VALUES (15, 'jc', 0, '经过5分钟，完成计划“cc”', '2020-05-25');
INSERT INTO `log` VALUES (16, 'jc', 0, '经过7分钟，完成计划“aa”', '2020-05-25');
INSERT INTO `log` VALUES (17, 'jc', 0, '经过5分钟，完成计划“cc”', '2020-05-25');
INSERT INTO `log` VALUES (18, 'jc', 0, '经过20分钟，完成计划“ee”', '2020-05-25');
INSERT INTO `log` VALUES (19, 'jc', 0, '经过7分钟，完成计划“aa”', '2020-05-25');

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
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of note
-- ----------------------------
INSERT INTO `note` VALUES (2, 'jc', '春去也', 'bcd', 's', '2020-05-24', '23:35:00');
INSERT INTO `note` VALUES (3, 'jc', 'ccc', 'cde', 'o', '2020-06-01', '13:35:21');
INSERT INTO `note` VALUES (4, 'jc', 'ddd', 'def', 'm', '2020-06-25', '13:35:42');
INSERT INTO `note` VALUES (5, 'jc', 'feeef', 'efg', 'e', '2020-05-01', '13:36:00');
INSERT INTO `note` VALUES (6, 'jc', 'fff', 'fgh', 'w', '2020-05-31', '13:36:16');
INSERT INTO `note` VALUES (7, 'jc', 'ggg', 'ghi', 'h', '2020-04-30', '13:36:32');
INSERT INTO `note` VALUES (8, 'jc', '会议', 'a', '201', '2020-05-14', '14:25:05');
INSERT INTO `note` VALUES (9, 'jc', 'todo111', '记事', '会议室', '2020-05-23', '14:50:00');
INSERT INTO `note` VALUES (12, 'jc', '流水', 'a', 'a', '2020-05-24', '23:03:33');
INSERT INTO `note` VALUES (14, 'jc', 'a', 'b', 'c', '2020-05-24', '00:17:47');
INSERT INTO `note` VALUES (16, 'jc', 'a', '', '', '2020-05-24', '00:15:57');

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
  `finish` int(1) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  PRIMARY KEY (`plan_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plan
-- ----------------------------
INSERT INTO `plan` VALUES (4, 'jc', 'aa', 'bb', 2, '7m', 1);
INSERT INTO `plan` VALUES (5, 'jc', 'cc', 'dd', 1, '5m', 0);
INSERT INTO `plan` VALUES (6, 'jc', 'ee', 'ff', 0, '20m', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of todo
-- ----------------------------
INSERT INTO `todo` VALUES (1, 'jc', 'todo111', '2020-05-21', 0);
INSERT INTO `todo` VALUES (7, 'jc', 'a', '2020-05-20', 0);
INSERT INTO `todo` VALUES (9, 'jc', 'a', '2020-05-21', 0);
INSERT INTO `todo` VALUES (12, 'jc', 'edit', '2020-05-22', 1);
INSERT INTO `todo` VALUES (13, 'jc', 'addte', '2020-05-22', 1);
INSERT INTO `todo` VALUES (14, 'jc', 'c', '2020-05-21', 0);
INSERT INTO `todo` VALUES (22, 'jc', '5', '2020-05-23', 1);
INSERT INTO `todo` VALUES (23, 'jc', '1', '2020-05-23', 1);
INSERT INTO `todo` VALUES (24, 'jc', '2', '2020-05-23', 1);
INSERT INTO `todo` VALUES (26, 'jc', '4', '2020-05-23', 1);
INSERT INTO `todo` VALUES (27, 'jc', '5', '2020-05-23', 1);
INSERT INTO `todo` VALUES (28, 'jc', '6', '2020-05-23', 1);
INSERT INTO `todo` VALUES (29, 'jc', '444', '2020-05-23', 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'jc', 'jc', 'a', 1, '14m', '\'jc\',\'jc02\',\'jc01\',\'jc03\',', 0, 1);
INSERT INTO `user` VALUES (2, 'jc01', 'jc01', 'v', 50, '1h15m', '\'jc01\',\'jc\',', 0, -1);
INSERT INTO `user` VALUES (3, 'jc02', 'jc02', 'c', 60, '20h0m', '\'jc02\',\'jc\',', 6, 7);
INSERT INTO `user` VALUES (4, 'jc03', 'jc03', 's', 70, '2h10m', '\'jc03\',\'jc\',', 4, 5);
INSERT INTO `user` VALUES (5, 'jc04', 'jc04', '5', 10, '1m', '\'jc04\',', 2, 5);
INSERT INTO `user` VALUES (6, 'jc05', 'jc05', '5', 30, '40m', '\'jc05\',', 2, 5);

SET FOREIGN_KEY_CHECKS = 1;
