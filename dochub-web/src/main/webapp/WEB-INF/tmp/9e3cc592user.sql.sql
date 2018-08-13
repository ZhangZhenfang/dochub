/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50637
Source Host           : 127.0.0.1:3306
Source Database       : test15373

Target Server Type    : MYSQL
Target Server Version : 50637
File Encoding         : 65001

Date: 2018-06-28 00:08:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userid` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` bigint(20) NOT NULL,
  `studentnumber` char(16) COLLATE utf8_bin NOT NULL,
  `name` char(16) COLLATE utf8_bin NOT NULL,
  `password` char(16) COLLATE utf8_bin NOT NULL,
  `school` char(16) COLLATE utf8_bin DEFAULT NULL,
  `institute` char(16) COLLATE utf8_bin DEFAULT NULL,
  `major` char(16) COLLATE utf8_bin DEFAULT NULL,
  `classnumber` char(16) COLLATE utf8_bin DEFAULT NULL,
  `phonenumber` char(11) COLLATE utf8_bin DEFAULT NULL,
  `sex` char(4) COLLATE utf8_bin DEFAULT NULL,
  `email` text COLLATE utf8_bin,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '11503070303', '11503070303', '张振方', 'afang', '重庆理工大学', '计算机科学与工程学院', '计算机科学与工程', '115030703', '17839911994', 'm', 0x313232383433363631374071712E636F6D);
INSERT INTO `user` VALUES ('2', '11503070313', '11503070313', '何洪玉', '18875052686', '重庆理工大学', '计算机科学与工程学院', '计算机科学与工程', '115030703', '18875052686', 'f', 0x323432373230343431304071712E636F6D);
INSERT INTO `user` VALUES ('3', '11503070337', '11503070337', '王吉英', '11503070337', '重庆理工大学', '计算机科学与工程学院', '计算机科学与工程', '115030703', '1234567890', 'f', 0x333B383B313B33373B);
INSERT INTO `user` VALUES ('4', '11503070324', '11503070324', '邓芮', '11503070324', '重庆理工大学', '计算机科学与工程学院', '计算机科学与工程', '115030703', '1234567890', 'f', 0x343B313B);
INSERT INTO `user` VALUES ('5', '11503070311', '11503070311', '皮艳萍', '11503070311', '重庆理工大学', '计算机科学与工程学院', '计算机科学与工程', '115030703', '1234567890', 'f', 0x353B313B393B323B383B343B);
INSERT INTO `user` VALUES ('7', '0', '0', 'xxx', '0', '0', '0', '0', '0', '1234567890', 'f', 0x373B313B323B333B343B353B363B373B383B393B31303B31313B31323B31333B31343B31353B31363B31373B31383B31393B32303B32313B32323B32333B32343B32353B32363B32373B32383B32393B33303B33313B33323B33333B33343B33353B33363B33373B33383B33393B34303B);
INSERT INTO `user` VALUES ('8', '11503070316', '11503070316', '李敏', '11503070316', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'f', 0x313B383B343B);
INSERT INTO `user` VALUES ('9', '11503070314', '11503070314', '廖秀艳', '11503070314', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'f', 0x393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('11', '11503070315', '11503070315', '尹小芳', '11503070315', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'f', 0x393B313B323B343B353B383B31313B);
INSERT INTO `user` VALUES ('13', '11503070318', '11503070318', '张桃', '11503070318', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x393B313B323B343B353B383B31333B);
INSERT INTO `user` VALUES ('14', '11503070321', '11503070321', '陈俊宇', '11503070321', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x31343B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('16', '11503070322', '11503070322', '张清', '11503070322', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x31363B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('17', '11503070323', '11503070323', '袁芙蓉', '11503070323', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'f', 0x31373B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('18', '11503070325', '11503070325', '田军', '11503070325', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x31383B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('19', '11503070326', '11503070326', '张麒鑫', '11503070326', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x31393B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('20', '11503070327', '11503070327', '赵小锋', '11503070327', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x32303B393B313B323B353B383B343B);
INSERT INTO `user` VALUES ('21', '11503070328', '11503070328', '刘一菠', '11503070328', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x32313B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('22', '11503070329', '11503070329', '张蓓蕾', '11503070329', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x32323B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('23', '11503070330', '11503070330', '张宸源', '11503070330', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x32333B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('25', '11503070332', '11503070332', '胡春', '11503070332', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x32353B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('26', '11503070333', '11503070333', '康凯宁', '11503070333', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x32363B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('27', '11503070334', '11503070334', '王鑫鑫', '11503070334', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x32373B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('28', '11503070335', '11503070335', '王慢慢', '11503070335', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x32383B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('29', '11503070336', '11503070336', '王旭', '11503070336', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x32393B32393B313B343B353B);
INSERT INTO `user` VALUES ('30', '11503070338', '11503070338', '谭中正', '11503070338', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '17323964176', 'm', 0x313738393830333833374071712E636F6D);
INSERT INTO `user` VALUES ('31', '11503070339', '11503070339', '林少瑜', '11503070339', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('33', '11503070301', '11503070301', '付珊', '11503070301', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x33333B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('34', '11503070302', '11503070302', '尹祎堃', '11503070302', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x33343B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('36', '11503070306', '11503070306', '王嘉怡', '11503070306', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x33363B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('37', '11503070308', '11503070308', '张洁', '11503070308', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x33373B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('38', '11503070309', '11503070309', '蔡文鑫', '11503070309', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x33383B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('39', '11503070310', '11503070310', '郭鑫', '11503070310', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x33393B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('40', '11503070312', '11503070312', '洪渝涛', '11503070312', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x34303B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('41', '11403070332', '11403070332', '朱坤', '11403070332', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x34313B393B313B323B343B353B383B);
INSERT INTO `user` VALUES ('42', '11503070305', '11503070305', '赵昀康', '11503070305', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x313B343B34323B);
INSERT INTO `user` VALUES ('43', '11503070317', '11503070317', '周杰', '11503070317', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x34333B313B343B);
INSERT INTO `user` VALUES ('44', '11503070320', '11503070320', '秦宇航', '11503070320', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x34343B313B343B);
INSERT INTO `user` VALUES ('45', '11503070331', '11503070331', '文显胜', '11503070331', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x313B343B34353B);
INSERT INTO `user` VALUES ('46', '11111111', '11111111', '11111111', 'afang17839911994', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x313B);
INSERT INTO `user` VALUES ('68', '11503070000', '11503070303', '???', '123456789987', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('70', '11503070000', '11503070303', '???', '123456789987', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('72', '11303070305', '11303070305', '熊壮', '11303070305', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '17764897801', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('73', '11303070315', '11303070315', '杜剑侠', '11303070315', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('74', '11503070136', '11503070136', '叶壮', '11503070136', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030703', '18323158253', 'm', 0x3735393737343132314071712E636F6D31323334353637383930);
INSERT INTO `user` VALUES ('156', '11503070401', '11503070401', '李青丰', '11503070401', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('157', '11503070402', '11503070402', '付恒坤', '11503070402', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x3132333435363738393031323334353637383930);
INSERT INTO `user` VALUES ('158', '11503070403', '11503070403', '陈梦晗', '11503070403', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('159', '11503070404', '11503070404', '陈雷登', '11503070404', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('160', '11503070405', '11503070405', '敖翔', '11503070405', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('161', '11503070406', '11503070406', '吴雄飞', '11503070406', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('162', '11503070407', '11503070407', '聂婉霞', '11503070407', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('163', '11503070408', '11503070408', '熊强', '11503070408', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('164', '11503070409', '11503070409', '余泽健', '11503070409', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('165', '11503070410', '11503070410', '邹世宁', '11503070410', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('166', '11503070411', '11503070411', '周世梁', '11503070411', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('167', '11503070412', '11503070412', '袁远福', '11503070412', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('168', '11503070413', '11503070413', '张浩宇', '11503070413', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('169', '11503070414', '11503070414', '黎敏', '11503070414', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('170', '11503070415', '11503070415', '秦云兰', '11503070415', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('171', '11503070416', '11503070416', '余镇辞', '11503070416', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('172', '11503070417', '11503070417', '文开俊', '11503070417', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('173', '11503070418', '11503070418', '谭博秋云', '11503070418', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('174', '11503070419', '11503070419', '黄强', '11503070419', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('175', '11503070420', '11503070420', '王秀丽', '11503070420', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('176', '11503070421', '11503070421', '贺仁彬', '11503070421', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('177', '11503070422', '11503070422', '何玲', '11503070422', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('178', '11503070423', '11503070423', '晏文英', '11503070423', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('179', '11503070424', '11503070424', '朱波', '11503070424', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('180', '11503070425', '11503070425', '黎萍铃', '11503070425', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('181', '11503070426', '11503070426', '童芮', '11503070426', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('182', '11503070427', '11503070427', '陈治庆', '11503070427', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('183', '11503070428', '11503070428', '杨帆', '11503070428', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('184', '11503070429', '11503070429', '黄海琳', '11503070429', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('185', '11503070430', '11503070430', '梁寿乾', '11503070430', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('186', '11503070431', '11503070431', '莫佳迪', '11503070431', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('187', '11503070432', '11503070432', '邹惠', '11503070432', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('188', '11503070433', '11503070433', '王源', '11503070433', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('189', '11503070434', '11503070434', '安涛', '11503070434', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('190', '11503070435', '11503070435', '姚泽', '11503070435', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('191', '11503070436', '11503070436', '谭峻', '11503070436', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('192', '11503070437', '11503070437', '李琳莉', '11503070437', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('193', '11503070438', '11503070438', '李鹏龙', '11503070438', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('194', '11503070439', '11503070439', '陈腾飞', '11503070439', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);
INSERT INTO `user` VALUES ('196', '11403070412', '11403070412', '陈伟', '11403070412', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '15923481783', 'm', 0x313531343637383530314071712E636F6D);
INSERT INTO `user` VALUES ('197', '11403070320', '11403070320', '黄鹏飞', '11403070320', '重庆理工大学', '计算机科学与工程学院', '计算机科学与技术', '115030704', '1234567890', 'm', 0x31323334353637383930);