CREATE DATABASE  IF NOT EXISTS `examination_system` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci */;
USE `examination_system`;
-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: localhost    Database: examination_system
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity_approval_request`
--

DROP TABLE IF EXISTS `activity_approval_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_approval_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `task_id` varchar(45) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '审批情况(0:还未审批，1:通过，2:失败)',
  `submit_teacherNumber` int(11) NOT NULL COMMENT '提交考试信息的老师编号',
  `comments` varchar(45) DEFAULT NULL COMMENT '申请说明',
  `eiid` int(11) DEFAULT NULL COMMENT '关联试卷内在因素id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_approval_request`
--

LOCK TABLES `activity_approval_request` WRITE;
/*!40000 ALTER TABLE `activity_approval_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_approval_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `aid` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `name` varchar(45) NOT NULL COMMENT '管理员真实姓名',
  `institute` int(11) NOT NULL COMMENT '管理员学院',
  `phone` int(11) NOT NULL COMMENT '管理员手机号码',
  `uid` int(11) NOT NULL COMMENT '账号id',
  PRIMARY KEY (`aid`),
  UNIQUE KEY `aid_UNIQUE` (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='管理员信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (1,'aa',1,5,20);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checking_question`
--

DROP TABLE IF EXISTS `checking_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checking_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(200) NOT NULL COMMENT '判断题题问题',
  `answer_a` varchar(200) NOT NULL COMMENT '判断题答案a',
  `answer_b` varchar(200) NOT NULL COMMENT '判断题答案b',
  `true_answer` char(1) NOT NULL COMMENT '正确答案',
  `level` int(11) NOT NULL COMMENT '题目难易等级(1,2,3级)',
  `subject_id` int(11) NOT NULL COMMENT '题目所对应的科目',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='判断题题库';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checking_question`
--

LOCK TABLES `checking_question` WRITE;
/*!40000 ALTER TABLE `checking_question` DISABLE KEYS */;
INSERT INTO `checking_question` VALUES (1,'它','1','2','2',2,1),(2,'hjk','2','1','5',3,1);
/*!40000 ALTER TABLE `checking_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examination_external`
--

DROP TABLE IF EXISTS `examination_external`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examination_external` (
  `eeid` int(11) NOT NULL AUTO_INCREMENT COMMENT '试卷外在因素id',
  `exam_time` datetime NOT NULL COMMENT '考试时间',
  `exam_place` varchar(45) NOT NULL COMMENT '考试地点',
  `teacher_number` int(11) NOT NULL COMMENT '监考老师',
  `institute` int(11) NOT NULL,
  `eiid` int(11) NOT NULL COMMENT '考试试卷',
  PRIMARY KEY (`eeid`),
  UNIQUE KEY `eeid_UNIQUE` (`eeid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='考试外在信息（时间地点人物）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examination_external`
--

LOCK TABLES `examination_external` WRITE;
/*!40000 ALTER TABLE `examination_external` DISABLE KEYS */;
/*!40000 ALTER TABLE `examination_external` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examination_internal`
--

DROP TABLE IF EXISTS `examination_internal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examination_internal` (
  `eiid` int(11) NOT NULL AUTO_INCREMENT COMMENT '试卷内在因素id',
  `single_number` int(11) NOT NULL COMMENT '单选题数量',
  `single_score` int(11) NOT NULL COMMENT '单选题总分值',
  `multiple_number` int(11) NOT NULL COMMENT '多选题数量',
  `multiple_score` int(11) NOT NULL COMMENT '多选题总分值',
  `checking_number` int(11) NOT NULL COMMENT '判断题数量',
  `checking_score` int(11) NOT NULL COMMENT '判断题总分值',
  `paper_level` int(11) NOT NULL COMMENT '试卷难度（1,2,3,1难度最小）',
  `paper_kind` int(11) NOT NULL COMMENT '试卷生成类型（0：生成a、b卷，1：生成每个人都不同的试卷）',
  `subject_id` int(11) NOT NULL COMMENT '考试的科目id',
  `student_class` int(11) NOT NULL COMMENT '参加考试的人群',
  `examination_time` int(11) NOT NULL COMMENT '考试持续事件',
  PRIMARY KEY (`eiid`),
  UNIQUE KEY `eeid_UNIQUE` (`eiid`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='考试试卷相关信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examination_internal`
--

LOCK TABLES `examination_internal` WRITE;
/*!40000 ALTER TABLE `examination_internal` DISABLE KEYS */;
/*!40000 ALTER TABLE `examination_internal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(10) NOT NULL COMMENT '用户账号',
  `password` varchar(45) NOT NULL COMMENT '用户密码',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid_UNIQUE` (`uid`),
  UNIQUE KEY `user_name_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='用户登陆信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (1,'student','CY9rzUYh03PK3k6DJie09g=='),(2,'teacher','CY9rzUYh03PK3k6DJie09g=='),(20,'test','CY9rzUYh03PK3k6DJie09g==');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `multiple_choice`
--

DROP TABLE IF EXISTS `multiple_choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `multiple_choice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(200) NOT NULL COMMENT '多选题问题',
  `answer_a` varchar(200) NOT NULL COMMENT '多选题答案a',
  `answer_b` varchar(200) NOT NULL COMMENT '多选题答案b',
  `answer_c` varchar(200) NOT NULL COMMENT '多选题答案c',
  `answer_d` varchar(200) NOT NULL COMMENT '多选题答案d',
  `true_answer` char(4) NOT NULL COMMENT '正确答案',
  `level` int(11) NOT NULL COMMENT '题目难易等级(1,2,3级)',
  `subject_id` int(11) NOT NULL COMMENT '题目所对应的科目',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='多选题题库';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `multiple_choice`
--

LOCK TABLES `multiple_choice` WRITE;
/*!40000 ALTER TABLE `multiple_choice` DISABLE KEYS */;
INSERT INTO `multiple_choice` VALUES (1,'你','1','2','3','4','23',2,1),(2,'w','5','4','12','2','14',2,1);
/*!40000 ALTER TABLE `multiple_choice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `pid` int(11) NOT NULL COMMENT '权限id',
  `permission` varchar(45) NOT NULL COMMENT '权限url位置',
  `description` varchar(45) NOT NULL COMMENT '权限注释',
  PRIMARY KEY (`pid`),
  UNIQUE KEY `permission_UNIQUE` (`permission`),
  UNIQUE KEY `pid_UNIQUE` (`pid`),
  UNIQUE KEY `description_UNIQUE` (`description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (0,'administrator','管理员url'),(1,'teacher','教师url'),(2,'student','学生url');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `rid` int(11) NOT NULL COMMENT '身份id',
  `role` varchar(45) NOT NULL COMMENT '身份英文',
  `description` varchar(45) NOT NULL COMMENT '身份注释',
  PRIMARY KEY (`rid`),
  UNIQUE KEY `role_UNIQUE` (`role`),
  UNIQUE KEY `description_UNIQUE` (`description`),
  UNIQUE KEY `rid_UNIQUE` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='身份信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (0,'administrator','管理员'),(1,'teacher','教师'),(2,'student','学生');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_login`
--

DROP TABLE IF EXISTS `role_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_login` (
  `rid` int(11) NOT NULL,
  `uid` varchar(45) NOT NULL,
  UNIQUE KEY `uid_UNIQUE` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='身份和登陆者关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_login`
--

LOCK TABLES `role_login` WRITE;
/*!40000 ALTER TABLE `role_login` DISABLE KEYS */;
INSERT INTO `role_login` VALUES (2,'1'),(1,'2'),(0,'20');
/*!40000 ALTER TABLE `role_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission` (
  `rid` int(11) NOT NULL COMMENT '角色id',
  `pid` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`pid`,`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='身份和权限关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (0,0),(1,1),(2,2);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `single_choice`
--

DROP TABLE IF EXISTS `single_choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `single_choice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(200) NOT NULL COMMENT '单选题问题',
  `answer_a` varchar(200) NOT NULL COMMENT '单选题答案a',
  `answer_b` varchar(200) NOT NULL COMMENT '单选题答案b',
  `answer_c` varchar(200) NOT NULL COMMENT '单选题答案c',
  `answer_d` varchar(200) NOT NULL COMMENT '单选题答案d',
  `true_answer` char(1) NOT NULL COMMENT '正确答案',
  `level` int(11) NOT NULL COMMENT '题目难易等级(1,2,3级)',
  `subject_id` int(11) NOT NULL COMMENT '题目所对应的科目',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='单选题题库';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `single_choice`
--

LOCK TABLES `single_choice` WRITE;
/*!40000 ALTER TABLE `single_choice` DISABLE KEYS */;
INSERT INTO `single_choice` VALUES (1,'我','1','2','3','4','2',2,1),(2,'我','1','2','3','4','2',3,1),(3,'我','1','2','3','4','2',1,1),(4,'我','1','2','3','4','2',2,1),(5,'我','1','2','3','4','2',2,2),(6,'你','2','3','5','7','2',1,1);
/*!40000 ALTER TABLE `single_choice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `sid` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生id',
  `name` varchar(45) NOT NULL COMMENT '学生真实姓名',
  `student_number` int(11) NOT NULL COMMENT '学号',
  `student_class` int(11) NOT NULL,
  `major` int(11) NOT NULL COMMENT '学生专业',
  `institute` int(11) NOT NULL,
  `phone` int(11) DEFAULT '0' COMMENT '学生手机号码',
  `uid` int(11) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`sid`),
  UNIQUE KEY `sid_UNIQUE` (`sid`),
  UNIQUE KEY `student_number_UNIQUE` (`student_number`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='学生信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'aa',123,1,2,1,12,1);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `tid` int(11) NOT NULL AUTO_INCREMENT COMMENT '教师id',
  `name` varchar(45) NOT NULL COMMENT '教师真实姓名',
  `teacher_number` int(11) NOT NULL,
  `major` int(11) NOT NULL COMMENT '教师专业',
  `institute` int(11) NOT NULL COMMENT '教师学院',
  `phone` int(11) NOT NULL COMMENT '教师手机号码',
  `uid` int(11) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`tid`),
  UNIQUE KEY `tid_UNIQUE` (`tid`),
  UNIQUE KEY `teacher_number_UNIQUE` (`teacher_number`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='教师信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,'aa',111,1,1,1,2);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'examination_system'
--

--
-- Dumping routines for database 'examination_system'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-25 18:53:33
