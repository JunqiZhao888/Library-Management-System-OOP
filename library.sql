

SET FOREIGN_KEY_CHECKS=0;

create database `library`;
use database `library`;

DROP TABLE IF EXISTS `admin_code`;
CREATE TABLE `admin_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `count` int(11) NOT NULL DEFAULT '2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `admin_code` VALUES ('1', '12345678', '999');


DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`aid`),
  UNIQUE KEY `unique` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `t_admin` VALUES ('1', 'admin', 'admin', 'Junqi');


DROP TABLE IF EXISTS `t_books`;
CREATE TABLE `t_books` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookname` varchar(30) NOT NULL,
  `author` varchar(30) NOT NULL,
  `number` bigint(20) NOT NULL,
  `borrow` varchar(10) NOT NULL,
  `location` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `unique` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

INSERT INTO `t_user` VALUES ('1', '12345', '12345', 'Hanlin');

