/*
MySQL Data Transfer
Source Host: localhost
Source Database: cc_next
Target Host: localhost
Target Database: cc_next
Date: 11/20/2013 3:04:28 PM
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for logger
-- ----------------------------
CREATE TABLE `logger` (
  `Sno` int(11) NOT NULL auto_increment,
  `email` varchar(50) NOT NULL,
  `sessionkey` varchar(20) NOT NULL,
  `event_type` varchar(255) default NULL,
  `level` int(11) NOT NULL,
  `time` varchar(30) NOT NULL,
  PRIMARY KEY  (`Sno`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for scores
-- ----------------------------
CREATE TABLE `scores` (
  `sessionkey` varchar(20) NOT NULL,
  `level` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `perf_score` int(11) default NULL,
  `timecapture` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_details
-- ----------------------------
CREATE TABLE `user_details` (
  `Sno` int(11) NOT NULL auto_increment,
  `universityid` varchar(50) default NULL,
  `university` varchar(50) NOT NULL,
  `academic` varchar(20) NOT NULL,
  `sem` int(11) NOT NULL,
  PRIMARY KEY  (`Sno`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for users
-- ----------------------------
CREATE TABLE `users` (
  `Sno` int(11) NOT NULL auto_increment,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `pwd` varchar(50) NOT NULL,
  `sessionkey` varchar(50) NOT NULL,
  PRIMARY KEY  (`Sno`),
  UNIQUE KEY `email_ind` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `logger` VALUES ('1', 'a@a.com', '1878785910', null,'1', '2013-11-14 18:24:19');
INSERT INTO `logger` VALUES ('2', 'a@a.com', '1954259408', null,'1', '2013-11-14 19:34:05');
INSERT INTO `logger` VALUES ('3', 'a@a.com', '1217795518', null,'3', '2013-11-15 10:32:32');
INSERT INTO `logger` VALUES ('20', 'a@a.com', '1609887144', null,'2', '2013-11-15 14:27:15');
INSERT INTO `logger` VALUES ('21', 'a@a.com', '1994334639', null,'1', '2013-11-15 14:27:36');
INSERT INTO `user_details` VALUES ('24', 'a', 'a', 'a', '1');
INSERT INTO `users` VALUES ('24', 'a', 'a', 'a@a.com', 'a','123');
