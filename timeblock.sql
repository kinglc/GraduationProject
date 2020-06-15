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

 Date: 15/06/2020 10:41:04
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
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chart
-- ----------------------------
INSERT INTO `chart` VALUES (34, '3d3bfb889a2247a08936f91fc2598753', '2020-05-01', 60, 60, 30, 0);
INSERT INTO `chart` VALUES (35, '3d3bfb889a2247a08936f91fc2598753', '2020-05-20', 300, 60, 30, 90);
INSERT INTO `chart` VALUES (36, '3d3bfb889a2247a08936f91fc2598753', '2020-06-13', 301, 60, 30, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (38, '3d3bfb889a2247a08936f91fc2598753', 1, '经过5小时01分钟，完成计划“毕设”', '2020-06-13');
INSERT INTO `log` VALUES (43, 'cbc165849278440184d68efaf3681a89', 0, '完成3项待办', '2020-06-13');
INSERT INTO `log` VALUES (44, 'cbc165849278440184d68efaf3681a89', 2, '达成成就”浅尝辄止”', '2020-06-13');
INSERT INTO `log` VALUES (49, '3d3bfb889a2247a08936f91fc2598753', 0, '完成4项待办', '2020-06-13');
INSERT INTO `log` VALUES (50, '3d3bfb889a2247a08936f91fc2598753', 2, '达成成就”浅尝辄止”', '2020-06-13');

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
) ENGINE = InnoDB AUTO_INCREMENT = 125 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of note
-- ----------------------------
INSERT INTO `note` VALUES (118, '3d3bfb889a2247a08936f91fc2598753', '中期', '', '', '2020-05-01', '11:00:00');
INSERT INTO `note` VALUES (119, '3d3bfb889a2247a08936f91fc2598753', '后端', '', '', '2020-05-15', '10:00:00');
INSERT INTO `note` VALUES (120, '3d3bfb889a2247a08936f91fc2598753', '核酸检测', '', '中医院', '2020-07-01', '15:00:00');
INSERT INTO `note` VALUES (121, '3d3bfb889a2247a08936f91fc2598753', '旅游', 'nature', '广州', '2020-07-10', '12:00:00');
INSERT INTO `note` VALUES (123, '3d3bfb889a2247a08936f91fc2598753', '答辩', '', '', '2020-06-16', '10:00:00');
INSERT INTO `note` VALUES (124, '3d3bfb889a2247a08936f91fc2598753', '历史', '', '', '2020-06-13', '19:54:00');

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
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plan
-- ----------------------------
INSERT INTO `plan` VALUES (28, 'cbc165849278440184d68efaf3681a89', '1', '2', 0, '0m', 0);
INSERT INTO `plan` VALUES (29, 'cbc165849278440184d68efaf3681a89', '2', '3', 0, '0m', 0);
INSERT INTO `plan` VALUES (34, '3d3bfb889a2247a08936f91fc2598753', '毕设', 'ppt', 2, '0m', 0);
INSERT INTO `plan` VALUES (36, '3d3bfb889a2247a08936f91fc2598753', '毕设', '展示视频', 0, '5h01m', 1);
INSERT INTO `plan` VALUES (37, '3d3bfb889a2247a08936f91fc2598753', '租房', '', 3, '0m', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 201 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of todo
-- ----------------------------
INSERT INTO `todo` VALUES (157, '3d3bfb889a2247a08936f91fc2598753', '练琴', '2020-06-10', 0);
INSERT INTO `todo` VALUES (158, '3d3bfb889a2247a08936f91fc2598753', '幸福北理打卡', '2020-06-10', 0);
INSERT INTO `todo` VALUES (159, '3d3bfb889a2247a08936f91fc2598753', '凑数', '2020-06-10', 1);
INSERT INTO `todo` VALUES (191, '3d3bfb889a2247a08936f91fc2598753', '1', '2020-06-13', 1);
INSERT INTO `todo` VALUES (192, '3d3bfb889a2247a08936f91fc2598753', '2', '2020-06-13', 1);
INSERT INTO `todo` VALUES (193, '3d3bfb889a2247a08936f91fc2598753', '3', '2020-06-13', 1);
INSERT INTO `todo` VALUES (198, '3d3bfb889a2247a08936f91fc2598753', '练琴', '2020-06-14', 0);
INSERT INTO `todo` VALUES (199, '3d3bfb889a2247a08936f91fc2598753', '幸福北理打卡', '2020-06-14', 0);
INSERT INTO `todo` VALUES (200, '3d3bfb889a2247a08936f91fc2598753', '凑数', '2020-06-14', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (9, 'f48078a86be1452a8754db80361d50ec', 'linanyj', 0, '1m', '\'f48078a86be1452a8754db80361d50ec\'\'1d6c2fd7c1cb40b09999a9b4de75f523\',\'fe42887edbd24a3ab800b5e0bf599248\',\'efd17b00a3434cc1baf3b0f44d9a6a65\',', 0, -1);
INSERT INTO `user` VALUES (10, '413192b4ed8744c380fdc68c5c6804c4', 'jc', 1, '1h1m', '\'413192b4ed8744c380fdc68c5c6804c4\',\'1d6c2fd7c1cb40b09999a9b4de75f523\',\'fe42887edbd24a3ab800b5e0bf599248\',\'4d0dc63499304f5bb939215d9a5a47e0\',\'efd17b00a3434cc1baf3b0f44d9a6a65\',\'db66802c919541aeb9ea023a443ec6df\',\'18730fd612714b23bc2cdb861964899d\',\'99df17cd6b2b49458f505bc7ee8b2bc2\',', 2, 1);
INSERT INTO `user` VALUES (16, '785340f8c7b241e19108b26660ebc9e6', 'kingc', 0, '101h3m', '\'785340f8c7b241e19108b26660ebc9e6\',\'fe42887edbd24a3ab800b5e0bf599248\',\'db66802c919541aeb9ea023a443ec6df\',\'18730fd612714b23bc2cdb861964899d\',\'99df17cd6b2b49458f505bc7ee8b2bc2\',', 2, -1);
INSERT INTO `user` VALUES (17, '2d5d4d184e4f4fc0ac460250281ef169', '金晨', 0, '30h0m', '\'2d5d4d184e4f4fc0ac460250281ef169\',\'jc01\',\'jc02\',\'1d6c2fd7c1cb40b09999a9b4de75f523\',\'fe42887edbd24a3ab800b5e0bf599248\',\'4d0dc63499304f5bb939215d9a5a47e0\',\'cbc165849278440184d68efaf3681a89\',\'db66802c919541aeb9ea023a443ec6df\',\'18730fd612714b23bc2cdb861964899d\',\'99df17cd6b2b49458f505bc7ee8b2bc2\',', 3, -1);
INSERT INTO `user` VALUES (18, '1d6c2fd7c1cb40b09999a9b4de75f523', 'test', 1, '0m', '\'1d6c2fd7c1cb40b09999a9b4de75f523\',\'f48078a86be1452a8754db80361d50ec\',\'2d5d4d184e4f4fc0ac460250281ef169\',\'413192b4ed8744c380fdc68c5c6804c4\',\'fe42887edbd24a3ab800b5e0bf599248\',\'cbc165849278440184d68efaf3681a89\',', 0, 1);
INSERT INTO `user` VALUES (19, 'fe42887edbd24a3ab800b5e0bf599248', '路人甲', 3, '500h1m', '\'fe42887edbd24a3ab800b5e0bf599248\',\'f48078a86be1452a8754db80361d50ec\',\'413192b4ed8744c380fdc68c5c6804c4\',\'2d5d4d184e4f4fc0ac460250281ef169\',\'1d6c2fd7c1cb40b09999a9b4de75f523\',\'785340f8c7b241e19108b26660ebc9e6\',\'4d0dc63499304f5bb939215d9a5a47e0\',\'cbc165849278440184d68efaf3681a89\',\'db66802c919541aeb9ea023a443ec6df\',\'18730fd612714b23bc2cdb861964899d\',\'99df17cd6b2b49458f505bc7ee8b2bc2\',', 0, 3);
INSERT INTO `user` VALUES (20, 'dbedd932320142a6ad4037fd2110c9ab', '路人乙', 0, '0m', '\'dbedd932320142a6ad4037fd2110c9ab\',', 0, -1);
INSERT INTO `user` VALUES (21, 'cbc165849278440184d68efaf3681a89', 'aaa', 1, '0m', '\'cbc165849278440184d68efaf3681a89\',\'4d0dc63499304f5bb939215d9a5a47e0\',\'fe42887edbd24a3ab800b5e0bf599248\',\'1d6c2fd7c1cb40b09999a9b4de75f523\',\'2d5d4d184e4f4fc0ac460250281ef169\',', 0, 1);
INSERT INTO `user` VALUES (22, '4d0dc63499304f5bb939215d9a5a47e0', '临安', 0, '16h', '\'4d0dc63499304f5bb939215d9a5a47e0\',\'413192b4ed8744c380fdc68c5c6804c4\',\'fe42887edbd24a3ab800b5e0bf599248\',\'2d5d4d184e4f4fc0ac460250281ef169\',\'cbc165849278440184d68efaf3681a89\',\'db66802c919541aeb9ea023a443ec6df\',\'18730fd612714b23bc2cdb861964899d\',\'99df17cd6b2b49458f505bc7ee8b2bc2\',', 12, -1);
INSERT INTO `user` VALUES (32, '3d3bfb889a2247a08936f91fc2598753', '临安雨霁', 1, '16h1m', '\'3d3bfb889a2247a08936f91fc2598753\',\'413192b4ed8744c380fdc68c5c6804c4\',\'785340f8c7b241e19108b26660ebc9e6\',\'2d5d4d184e4f4fc0ac460250281ef169\',\'fe42887edbd24a3ab800b5e0bf599248\',\'4d0dc63499304f5bb939215d9a5a47e0\',', 12, 1);

SET FOREIGN_KEY_CHECKS = 1;
