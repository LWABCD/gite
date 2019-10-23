/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : jianshu_tables

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2019-07-10 22:44:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `articleId` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章id，是一篇文章的唯一标识',
  `author` int(11) DEFAULT NULL COMMENT '作者, 外键, 引用user.uid',
  `title` varchar(255) DEFAULT NULL COMMENT '文章标题',
  `content` text COMMENT '文章内容',
  `totalDiamond` float(8,2) DEFAULT NULL COMMENT '此处是获得钻石总计数',
  `postTime` datetime DEFAULT NULL COMMENT '文章的发表时间',
  `wordNum` int(11) DEFAULT NULL COMMENT '文章攥写字数统计',
  `readNum` int(11) DEFAULT NULL COMMENT '文章被阅读数量',
  `commentNum` int(11) DEFAULT NULL COMMENT '文章被评论的数量',
  `likeNum` int(11) DEFAULT NULL COMMENT '文章被喜欢的数量',
  PRIMARY KEY (`articleId`),
  KEY `FK_UserPostArticle` (`author`),
  CONSTRAINT `FK_UserPostArticle` FOREIGN KEY (`author`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `commentId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论的唯一标识id',
  `uid` int(11) DEFAULT NULL COMMENT '外键，发布评论的用户id',
  `articleId` int(11) DEFAULT NULL COMMENT '外键，发布评论的所属文章id',
  `commentTime` datetime DEFAULT NULL COMMENT '评论的发布时间',
  `content` text COMMENT '评论信息',
  `zan` int(11) DEFAULT NULL COMMENT '评论的被赞数',
  `floor` int(11) DEFAULT NULL COMMENT '评论信息所属楼层',
  PRIMARY KEY (`commentId`),
  KEY `FK_ArticleHasComment` (`articleId`),
  KEY `FK_UserPostComment` (`uid`),
  CONSTRAINT `FK_ArticleHasComment` FOREIGN KEY (`articleId`) REFERENCES `article` (`articleId`),
  CONSTRAINT `FK_UserPostComment` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `replyId` int(11) NOT NULL AUTO_INCREMENT COMMENT '回复信息的唯一标识id',
  `uid` int(11) DEFAULT NULL COMMENT '回复评论的用户id，外键',
  `commentId` bigint(20) DEFAULT NULL COMMENT '进行回复的评论信息id，外键',
  `replyTime` datetime DEFAULT NULL COMMENT '回复的时间',
  `content` text COMMENT '回复的内容信息',
  `to` int(11) DEFAULT NULL COMMENT '如果to为null, 表示这条回复是直接针对comment的回复;\r\n            如果to不为null, 表示引用了某个特定reply的replyId',
  PRIMARY KEY (`replyId`),
  KEY `FK_CommentHasReply` (`commentId`),
  KEY `FK_UserPostReply` (`uid`),
  CONSTRAINT `FK_CommentHasReply` FOREIGN KEY (`commentId`) REFERENCES `comment` (`commentId`),
  CONSTRAINT `FK_UserPostReply` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reply
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id，是用户的唯一标识',
  `nickName` varchar(50) DEFAULT NULL COMMENT '用户的昵称',
  `phone` varchar(50) DEFAULT NULL COMMENT '用户的手机号码，可使用该信息进行登录',
  `pwd` varchar(100) DEFAULT NULL COMMENT '用户登录的密码，在数据库中为加密状态',
  `email` varchar(100) DEFAULT NULL COMMENT '用户的邮箱地址，可用作登陆账户',
  `headImg` varchar(255) DEFAULT NULL COMMENT '用户的头像信息，本属性将采用本地存储的目录/文件名现资源的查找',
  `gender` char(2) DEFAULT NULL COMMENT '性别',
  `info` varchar(1000) DEFAULT NULL COMMENT '个人简介信息',
  `totalWordNum` int(11) unsigned DEFAULT NULL COMMENT '用户发表文章总字数',
  `totalLikeNum` int(11) DEFAULT NULL COMMENT '用户被喜欢总数',
  `totalFollowNum` int(11) DEFAULT NULL COMMENT '用户关注了多少人',
  `totalFansNum` int(11) DEFAULT NULL COMMENT '用户被多少人关注',
  `totalReceiveDiamonds` int(11) DEFAULT NULL COMMENT '用户总共收到的被打赏钻石数额',
  `diamondBalance` float(8,2) DEFAULT NULL COMMENT '用户钱包钻石余额',
  `totalArticleNum` int(11) DEFAULT NULL COMMENT '用户发表文章数目',
  `registerTime` datetime DEFAULT NULL COMMENT '用户的注册时间',
  `ext1` varchar(1000) DEFAULT NULL,
  `ext2` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for userfollowuser
-- ----------------------------
DROP TABLE IF EXISTS `userfollowuser`;
CREATE TABLE `userfollowuser` (
  `userA` int(11) NOT NULL COMMENT '关注者',
  `userB` int(11) NOT NULL COMMENT '被关注者',
  `followTime` datetime DEFAULT NULL COMMENT '用户A关注用户B的时间',
  PRIMARY KEY (`userA`,`userB`),
  KEY `FK_UserFollowUser2` (`userB`),
  CONSTRAINT `FK_UserFollowUser` FOREIGN KEY (`userA`) REFERENCES `user` (`uid`),
  CONSTRAINT `FK_UserFollowUser2` FOREIGN KEY (`userB`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userfollowuser
-- ----------------------------

-- ----------------------------
-- Table structure for usergivediamondtoarticle
-- ----------------------------
DROP TABLE IF EXISTS `usergivediamondtoarticle`;
CREATE TABLE `usergivediamondtoarticle` (
  `dsid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户进行单词打赏的id    是用户单词打赏的唯一标识',
  `uid` int(11) NOT NULL COMMENT '进行打赏的用户id 即该用户将对某篇文章进行打赏',
  `articleId` int(11) NOT NULL COMMENT '被打赏的文章id',
  `diamond` float(8,2) DEFAULT NULL COMMENT '用户打赏文章的钻石金额数目',
  PRIMARY KEY (`dsid`),
  KEY `FK_UserGiveDiamondToArticle` (`articleId`),
  KEY `FK_UserGiveDiamondToArticle2` (`uid`),
  CONSTRAINT `FK_UserGiveDiamondToArticle` FOREIGN KEY (`articleId`) REFERENCES `article` (`articleId`),
  CONSTRAINT `FK_UserGiveDiamondToArticle2` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usergivediamondtoarticle
-- ----------------------------

-- ----------------------------
-- Table structure for userlikearticle
-- ----------------------------
DROP TABLE IF EXISTS `userlikearticle`;
CREATE TABLE `userlikearticle` (
  `uid` int(11) NOT NULL COMMENT '喜欢文章的用户id',
  `articleId` int(11) NOT NULL COMMENT '用户喜欢的文章id',
  PRIMARY KEY (`articleId`,`uid`),
  KEY `FK_UserLikeArticle2` (`uid`),
  CONSTRAINT `FK_UserLikeArticle` FOREIGN KEY (`articleId`) REFERENCES `article` (`articleId`),
  CONSTRAINT `FK_UserLikeArticle2` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userlikearticle
-- ----------------------------
