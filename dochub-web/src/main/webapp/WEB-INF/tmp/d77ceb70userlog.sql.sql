/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50637
Source Host           : 127.0.0.1:3306
Source Database       : dochub

Target Server Type    : MYSQL
Target Server Version : 50637
File Encoding         : 65001

Date: 2018-06-28 00:14:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for userlog
-- ----------------------------
DROP TABLE IF EXISTS `userlog`;
CREATE TABLE `userlog` (
  `userlogid` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) DEFAULT NULL,
  `time` datetime NOT NULL,
  `useragent` text NOT NULL,
  `ip` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`userlogid`),
  KEY `FKowdimi8shdyiqhkxu218c0rfe` (`userid`),
  CONSTRAINT `FK_Relationship_9` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`),
  CONSTRAINT `FKowdimi8shdyiqhkxu218c0rfe` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userlog
-- ----------------------------
INSERT INTO `userlog` VALUES ('1', '1', '2018-06-27 23:27:31', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36', '0:0:0:0:0:0:0:1');
