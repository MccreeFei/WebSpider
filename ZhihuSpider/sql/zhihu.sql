create database zhihu;
#创建知乎用户表#
CREATE TABLE `zhihu_user` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
   `character_url` varchar(50) DEFAULT NULL COMMENT '特征url',
   `head_url` varchar(200) DEFAULT NULL COMMENT '头像地址',
   `simple_description` varchar(200) DEFAULT NULL COMMENT '一句话简介',
   `thanks` int(11) DEFAULT NULL COMMENT '感谢数',
   `agrees` int(11) DEFAULT NULL COMMENT '赞同数',
   `collects` int(11) DEFAULT NULL COMMENT '被赞同树',
   `followers` int(11) DEFAULT NULL COMMENT '关注者数',
   `followees` int(11) DEFAULT NULL COMMENT '关注了个数',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `character_url` (`character_url`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


#创建知乎文章表#
CREATE TABLE `zhihu_article` (
   `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `character_url` VARCHAR(50) DEFAULT NULL COMMENT '特征url',
   `article_id` INT(11) DEFAULT NULL COMMENT '文章id',
   `article_url` VARCHAR(200) DEFAULT NULL COMMENT '文章url',
   `article_title` VARCHAR(200) DEFAULT NULL COMMENT '文章标题',
   `agrees` INT(11) DEFAULT NULL COMMENT '赞同数',
   `comments` INT(11) DEFAULT NULL COMMENT '评论数',
   `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
   `modify_time` DATETIME DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `article_id` (`article_id`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8;

#创建知乎回答表#
CREATE TABLE `zhihu_answer` (
   `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `character_url` VARCHAR(50) DEFAULT NULL COMMENT '特征url',
   `answer_url` VARCHAR(200) DEFAULT NULL COMMENT '回答url',
   `question_id` INT(11) DEFAULT NULL COMMENT '问题id',
   `question_title` VARCHAR(200) DEFAULT NULL COMMENT '问题标题',
   `answer_id` INT(11) DEFAULT NULL COMMENT '回答id',
   `agrees` INT(11) DEFAULT NULL COMMENT '赞同数',
   `comments` INT(11) DEFAULT NULL COMMENT '评论数',
   `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
   `modify_time` DATETIME DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY (`question_id`, `answer_id`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8;