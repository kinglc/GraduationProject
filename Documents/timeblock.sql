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

 Date: 08/06/2020 21:56:45
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
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `pass_red` int(0) NULL DEFAULT NULL,
  `pass_yellow` int(0) NULL DEFAULT NULL,
  `pass_green` int(0) NULL DEFAULT NULL,
  `pass_blue` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`chart_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `chart` VALUES (13, 'fe42887edbd24a3ab800b5e0bf599248', '2020-05-01', 60, 0, 0, 0);
INSERT INTO `chart` VALUES (14, '1d6c2fd7c1cb40b09999a9b4de75f523', '2020-06-10', 60, 20, 30, 40);
INSERT INTO `chart` VALUES (15, '1d6c2fd7c1cb40b09999a9b4de75f523', '2020-06-21', 0, 10, 0, 0);
INSERT INTO `chart` VALUES (16, 'fe42887edbd24a3ab800b5e0bf599248', '2020-05-14', 120, 60, 60, 60);
INSERT INTO `chart` VALUES (17, 'fe42887edbd24a3ab800b5e0bf599248', '2020-06-17', 56, 12, 0, 0);

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `log_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` int(0) NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (11, 'jc', 0, '经过20m，完成计划“ee”', '2020-05-24');
INSERT INTO `log` VALUES (12, 'jc', 0, '经过20分钟，完成计划“ee”', '2020-05-25');
INSERT INTO `log` VALUES (13, 'jc', 2, '达成成就”走马观花”', '2020-05-25');
INSERT INTO `log` VALUES (14, 'jc', 0, '经过7分钟，完成计划“aa”', '2020-05-25');
INSERT INTO `log` VALUES (15, 'jc', 0, '经过5分钟，完成计划“cc”', '2020-05-25');
INSERT INTO `log` VALUES (16, 'jc', 0, '经过7分钟，完成计划“aa”', '2020-05-25');
INSERT INTO `log` VALUES (17, 'jc', 0, '经过5分钟，完成计划“cc”', '2020-05-25');
INSERT INTO `log` VALUES (18, 'jc', 0, '经过20分钟，完成计划“ee”', '2020-05-25');
INSERT INTO `log` VALUES (19, 'jc', 0, '经过7分钟，完成计划“aa”', '2020-05-25');
INSERT INTO `log` VALUES (20, '1d6c2fd7c1cb40b09999a9b4de75f523', 0, '完成2项待办', '2020-05-30');
INSERT INTO `log` VALUES (21, '1d6c2fd7c1cb40b09999a9b4de75f523', 2, '达成成就”浅尝辄止”', '2020-05-30');
INSERT INTO `log` VALUES (22, '413192b4ed8744c380fdc68c5c6804c4', 0, '完成10项待办', '2020-05-30');
INSERT INTO `log` VALUES (23, '413192b4ed8744c380fdc68c5c6804c4', 2, '达成成就”浅尝辄止”', '2020-05-30');
INSERT INTO `log` VALUES (24, 'fe42887edbd24a3ab800b5e0bf599248', 0, '完成3项待办', '2020-06-01');
INSERT INTO `log` VALUES (25, 'fe42887edbd24a3ab800b5e0bf599248', 2, '达成成就”浅尝辄止”', '2020-06-01');
INSERT INTO `log` VALUES (26, 'fe42887edbd24a3ab800b5e0bf599248', 1, '经过1小时2分钟，完成计划“系统bug”', '2020-06-02');
INSERT INTO `log` VALUES (27, 'fe42887edbd24a3ab800b5e0bf599248', 2, '达成成就”走马观花”', '2020-06-02');
INSERT INTO `log` VALUES (28, 'fe42887edbd24a3ab800b5e0bf599248', 0, '完成1项待办', '2020-06-03');
INSERT INTO `log` VALUES (29, 'fe42887edbd24a3ab800b5e0bf599248', 0, '完成2项待办', '2020-06-06');

-- ----------------------------
-- Table structure for note
-- ----------------------------
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note`  (
  `note_id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `place` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `time` time(0) NULL DEFAULT NULL,
  PRIMARY KEY (`note_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `note` VALUES (20, '1d6c2fd7c1cb40b09999a9b4de75f523', '5', '6', '7', '2020-05-19', '00:17:44');
INSERT INTO `note` VALUES (24, '1d6c2fd7c1cb40b09999a9b4de75f523', '10', '11', '12', '2020-05-31', '17:54:00');
INSERT INTO `note` VALUES (25, '413192b4ed8744c380fdc68c5c6804c4', '啊', '哦', 'e', '2020-06-01', '02:13:00');
INSERT INTO `note` VALUES (26, '413192b4ed8744c380fdc68c5c6804c4', '2', '的', '和', '2020-06-02', '01:13:00');
INSERT INTO `note` VALUES (27, 'fe42887edbd24a3ab800b5e0bf599248', '毕设', '论文', '', '2020-06-02', '16:16:00');
INSERT INTO `note` VALUES (28, 'fe42887edbd24a3ab800b5e0bf599248', '幸福北理', '打卡', '', '2020-06-02', '09:15:00');
INSERT INTO `note` VALUES (29, 'fe42887edbd24a3ab800b5e0bf599248', '聚会', '班聚', '公园', '2020-06-01', '13:10:00');
INSERT INTO `note` VALUES (30, 'fe42887edbd24a3ab800b5e0bf599248', '租房', '', '浙江', '2020-06-23', '15:17:00');
INSERT INTO `note` VALUES (31, 'fe42887edbd24a3ab800b5e0bf599248', '手续', '', '', '2020-06-18', '15:15:00');
INSERT INTO `note` VALUES (33, '1d6c2fd7c1cb40b09999a9b4de75f523', '1', '2', '3', '2020-06-02', '07:23:00');

-- ----------------------------
-- Table structure for plan
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan`  (
  `plan_id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `urgency` int(0) NULL DEFAULT NULL,
  `pass` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `finish` int(1) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  PRIMARY KEY (`plan_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plan
-- ----------------------------
INSERT INTO `plan` VALUES (4, 'jc', 'aa', 'bb', 2, '7m', 1);
INSERT INTO `plan` VALUES (5, 'jc', 'cc', 'dd', 1, '5m', 0);
INSERT INTO `plan` VALUES (6, 'jc', 'ee', 'ff', 0, '20m', 0);
INSERT INTO `plan` VALUES (8, '413192b4ed8744c380fdc68c5c6804c4', '毕设', '论文', 0, '0m', 0);
INSERT INTO `plan` VALUES (9, '413192b4ed8744c380fdc68c5c6804c4', '2', '3', 2, '0m', 0);
INSERT INTO `plan` VALUES (10, '413192b4ed8744c380fdc68c5c6804c4', '3', '了', 1, '0m', 0);
INSERT INTO `plan` VALUES (11, '413192b4ed8744c380fdc68c5c6804c4', '三', '记事', 1, '0m', 0);
INSERT INTO `plan` VALUES (12, 'fe42887edbd24a3ab800b5e0bf599248', '系统bug', '主页', 1, '0m', 0);
INSERT INTO `plan` VALUES (13, 'fe42887edbd24a3ab800b5e0bf599248', '格式整理', '外文翻译', 3, '0m', 0);
INSERT INTO `plan` VALUES (14, 'fe42887edbd24a3ab800b5e0bf599248', '论文', '参考文献', 2, '0m', 0);
INSERT INTO `plan` VALUES (15, 'fe42887edbd24a3ab800b5e0bf599248', '论文', '设计部分', 0, '2m', 1);

-- ----------------------------
-- Table structure for todo
-- ----------------------------
DROP TABLE IF EXISTS `todo`;
CREATE TABLE `todo`  (
  `todo_id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `isChecked` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`todo_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of todo
-- ----------------------------
INSERT INTO `todo` VALUES (37, '413192b4ed8744c380fdc68c5c6804c4', '111', '2020-05-30', 1);
INSERT INTO `todo` VALUES (38, '413192b4ed8744c380fdc68c5c6804c4', '112', '2020-05-30', 1);
INSERT INTO `todo` VALUES (39, '413192b4ed8744c380fdc68c5c6804c4', '3', '2020-05-30', 1);
INSERT INTO `todo` VALUES (41, '413192b4ed8744c380fdc68c5c6804c4', '3', '2020-05-30', 1);
INSERT INTO `todo` VALUES (42, '413192b4ed8744c380fdc68c5c6804c4', '3', '2020-05-30', 1);
INSERT INTO `todo` VALUES (43, '413192b4ed8744c380fdc68c5c6804c4', '4', '2020-05-30', 1);
INSERT INTO `todo` VALUES (44, '413192b4ed8744c380fdc68c5c6804c4', '5', '2020-05-30', 1);
INSERT INTO `todo` VALUES (45, '413192b4ed8744c380fdc68c5c6804c4', '9', '2020-05-30', 1);
INSERT INTO `todo` VALUES (46, '413192b4ed8744c380fdc68c5c6804c4', '10', '2020-05-30', 1);
INSERT INTO `todo` VALUES (47, '413192b4ed8744c380fdc68c5c6804c4', '11', '2020-05-30', 1);
INSERT INTO `todo` VALUES (48, '413192b4ed8744c380fdc68c5c6804c4', '12', '2020-05-30', 0);
INSERT INTO `todo` VALUES (49, '1d6c2fd7c1cb40b09999a9b4de75f523', '1', '2020-05-30', 0);
INSERT INTO `todo` VALUES (53, '1d6c2fd7c1cb40b09999a9b4de75f523', '3', '2020-05-30', 0);
INSERT INTO `todo` VALUES (57, '1d6c2fd7c1cb40b09999a9b4de75f523', '1', '2020-05-31', 0);
INSERT INTO `todo` VALUES (58, '1d6c2fd7c1cb40b09999a9b4de75f523', '3', '2020-05-31', 0);
INSERT INTO `todo` VALUES (59, '1d6c2fd7c1cb40b09999a9b4de75f523', '1', '2020-06-01', 0);
INSERT INTO `todo` VALUES (60, '1d6c2fd7c1cb40b09999a9b4de75f523', '3', '2020-06-01', 0);
INSERT INTO `todo` VALUES (61, '1d6c2fd7c1cb40b09999a9b4de75f523', '9', '2020-05-31', 0);
INSERT INTO `todo` VALUES (62, 'fe42887edbd24a3ab800b5e0bf599248', '幸福北理打卡', '2020-06-02', 1);
INSERT INTO `todo` VALUES (63, 'fe42887edbd24a3ab800b5e0bf599248', '练琴', '2020-06-02', 1);
INSERT INTO `todo` VALUES (64, 'fe42887edbd24a3ab800b5e0bf599248', '接放学', '2020-06-02', 1);
INSERT INTO `todo` VALUES (65, 'fe42887edbd24a3ab800b5e0bf599248', '123', '2020-06-03', 1);
INSERT INTO `todo` VALUES (66, 'fe42887edbd24a3ab800b5e0bf599248', '1', '2020-06-06', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `todo_day` int(0) NULL DEFAULT NULL,
  `plan_time` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `friend` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `prize_plan` int(0) NULL DEFAULT NULL,
  `prize_todo` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (9, 'f48078a86be1452a8754db80361d50ec', 'linanyj', 0, '1m', '\'f48078a86be1452a8754db80361d50ec\'\'1d6c2fd7c1cb40b09999a9b4de75f523\',\'fe42887edbd24a3ab800b5e0bf599248\',', 0, -1);
INSERT INTO `user` VALUES (10, '413192b4ed8744c380fdc68c5c6804c4', 'jc', 1, '1h1m', '\'413192b4ed8744c380fdc68c5c6804c4\',\'1d6c2fd7c1cb40b09999a9b4de75f523\',\'fe42887edbd24a3ab800b5e0bf599248\',', 2, 1);
INSERT INTO `user` VALUES (16, '785340f8c7b241e19108b26660ebc9e6', 'kingc', 0, '101h3m', '\'785340f8c7b241e19108b26660ebc9e6\',\'fe42887edbd24a3ab800b5e0bf599248\',', 2, -1);
INSERT INTO `user` VALUES (17, '2d5d4d184e4f4fc0ac460250281ef169', '金晨', 0, '30h0m', '\'2d5d4d184e4f4fc0ac460250281ef169\',\'jc01\',\'jc02\',\'1d6c2fd7c1cb40b09999a9b4de75f523\',\'fe42887edbd24a3ab800b5e0bf599248\',', 3, -1);
INSERT INTO `user` VALUES (18, '1d6c2fd7c1cb40b09999a9b4de75f523', 'test', 1, '0m', '\'1d6c2fd7c1cb40b09999a9b4de75f523\',\'f48078a86be1452a8754db80361d50ec\',\'2d5d4d184e4f4fc0ac460250281ef169\',\'413192b4ed8744c380fdc68c5c6804c4\',\'fe42887edbd24a3ab800b5e0bf599248\',', 0, 1);
INSERT INTO `user` VALUES (19, 'fe42887edbd24a3ab800b5e0bf599248', '临安雨霁', 3, '2m', '\'fe42887edbd24a3ab800b5e0bf599248\',\'f48078a86be1452a8754db80361d50ec\',\'413192b4ed8744c380fdc68c5c6804c4\',\'2d5d4d184e4f4fc0ac460250281ef169\',\'1d6c2fd7c1cb40b09999a9b4de75f523\',\'785340f8c7b241e19108b26660ebc9e6\',', 12, 3);

SET FOREIGN_KEY_CHECKS = 1;
