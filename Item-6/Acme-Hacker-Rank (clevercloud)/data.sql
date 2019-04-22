drop database if exists `bz0vcevszgzld02rwdd0`;
CREATE DATABASE `bz0vcevszgzld02rwdd0` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `bz0vcevszgzld02rwdd0`;


-- MySQL dump 10.13  Distrib 5.7.25, for Linux (x86_64)
--
-- Host: localhost    Database: Acme-Hacker-Rank
-- ------------------------------------------------------
-- Server version	5.7.25-0ubuntu0.16.04.2

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
-- Table structure for table `actor_messages`
--

DROP TABLE IF EXISTS `actor_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_messages` (
  `actor` int(11) NOT NULL,
  `messages` int(11) NOT NULL,
  KEY `FK_b1336eo2tc3f26b38f2drhyk8` (`messages`),
  CONSTRAINT `FK_b1336eo2tc3f26b38f2drhyk8` FOREIGN KEY (`messages`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_messages`
--

LOCK TABLES `actor_messages` WRITE;
/*!40000 ALTER TABLE `actor_messages` DISABLE KEYS */;
INSERT INTO `actor_messages` VALUES (270,298),(274,296),(274,297),(276,296),(276,297),(300,294),(300,295),(300,298),(301,294),(301,295);
/*!40000 ALTER TABLE `actor_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_banned` bit(1) DEFAULT NULL,
  `is_spammer` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `vat` int(11) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7ohwsa2usmvu0yxb44je2lge` (`user_account`),
  CONSTRAINT `FK_7ohwsa2usmvu0yxb44je2lge` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (270,0,'admin 18','378721273855309','admin@gmail.com',NULL,NULL,'admin','+34656256697',NULL,'surname1','admin',1,265);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (282,0,'https://www.piensasolutions.com/','This text is the solution for any problem');
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creation_moment` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `submit_moment` datetime DEFAULT NULL,
  `answer` int(11) DEFAULT NULL,
  `curricula` int(11) NOT NULL,
  `hacker` int(11) DEFAULT NULL,
  `position` int(11) NOT NULL,
  `problem` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hsw5exxa4qe3jxi8xdhnn0dl5` (`curricula`),
  KEY `FK_lvkbgejfw0xfg2m80h1bjjmau` (`answer`),
  KEY `FK_3hgwemcpn15ns7bi2upsq613y` (`hacker`),
  KEY `FK_cqpb54jon3yjef814oj6g6o4n` (`position`),
  KEY `FK_7gn6fojv7rim6rxyglc6n9mt2` (`problem`),
  CONSTRAINT `FK_3hgwemcpn15ns7bi2upsq613y` FOREIGN KEY (`hacker`) REFERENCES `hacker` (`id`),
  CONSTRAINT `FK_7gn6fojv7rim6rxyglc6n9mt2` FOREIGN KEY (`problem`) REFERENCES `problem` (`id`),
  CONSTRAINT `FK_cqpb54jon3yjef814oj6g6o4n` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_hsw5exxa4qe3jxi8xdhnn0dl5` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_lvkbgejfw0xfg2m80h1bjjmau` FOREIGN KEY (`answer`) REFERENCES `answer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (311,0,'2019-03-27 00:00:00','SUBMITTED',NULL,282,302,300,278,283),(312,0,'2019-02-18 00:00:00','PENDING',NULL,NULL,303,300,279,284);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_banned` bit(1) DEFAULT NULL,
  `is_spammer` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `vat` int(11) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `commercial_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pno7oguspp7fxv0y2twgplt0s` (`user_account`),
  CONSTRAINT `FK_pno7oguspp7fxv0y2twgplt0s` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (274,0,'company1 18','378721273855309','company1@gmail.com',NULL,NULL,'company1','+34656256697',NULL,'company1',NULL,13,267,'company1 Co., Inc.'),(276,0,'Av Bird 18','378721273855309','company2@gmail.com',NULL,NULL,'company2','+34625677697',NULL,'company2',NULL,9,269,'company2 Co., Inc.');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurations`
--

DROP TABLE IF EXISTS `configurations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurations` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cache_time` int(11) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `english_message` varchar(255) DEFAULT NULL,
  `finder_max_result` int(11) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `spanish_message` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurations`
--

LOCK TABLES `configurations` WRITE;
/*!40000 ALTER TABLE `configurations` DISABLE KEYS */;
INSERT INTO `configurations` VALUES (299,0,1,'+34','Welcome to Acme hacker Rank! We’re IT hacker’s favourite job marketplace!',10,'https://i.imgur.com/7b8lu4b.png ','¡Bienvenidos a Acme Hacker Rank! ¡Somos el mercado de trabajo favorito de los profesionales de las TICs!','Acme Hacker Rank Co., Inc.');
/*!40000 ALTER TABLE `configurations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurations_spam_words`
--

DROP TABLE IF EXISTS `configurations_spam_words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurations_spam_words` (
  `configurations` int(11) NOT NULL,
  `spam_words` varchar(255) DEFAULT NULL,
  KEY `FK_1mwxou3h8fn5uxuwtsbhw7g1e` (`configurations`),
  CONSTRAINT `FK_1mwxou3h8fn5uxuwtsbhw7g1e` FOREIGN KEY (`configurations`) REFERENCES `configurations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurations_spam_words`
--

LOCK TABLES `configurations_spam_words` WRITE;
/*!40000 ALTER TABLE `configurations_spam_words` DISABLE KEYS */;
INSERT INTO `configurations_spam_words` VALUES (299,'sex'),(299,'viagra'),(299,'cialis'),(299,'one million'),(299,'you\'ve been selected'),(299,'Nigeria'),(299,'sexo'),(299,'un millón'),(299,'ha sido seleccionado');
/*!40000 ALTER TABLE `configurations_spam_words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula`
--

DROP TABLE IF EXISTS `curricula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `applied` bit(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `hacker` int(11) NOT NULL,
  `personal_data` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cmpmwnbib50w71nx0wjlp8x79` (`hacker`),
  KEY `FK_67ossaqcrfgcegesi91m20iik` (`personal_data`),
  CONSTRAINT `FK_67ossaqcrfgcegesi91m20iik` FOREIGN KEY (`personal_data`) REFERENCES `personal_data` (`id`),
  CONSTRAINT `FK_cmpmwnbib50w71nx0wjlp8x79` FOREIGN KEY (`hacker`) REFERENCES `hacker` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula`
--

LOCK TABLES `curricula` WRITE;
/*!40000 ALTER TABLE `curricula` DISABLE KEYS */;
INSERT INTO `curricula` VALUES (302,0,_binary '','Applied curricula 1',300,NULL),(303,0,_binary '','Applied curricula 2',300,NULL),(304,1,_binary '\0','first curricula of hacker 1',300,306),(305,1,_binary '\0','first curricula of hacker 2',301,307);
/*!40000 ALTER TABLE `curricula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `education_data`
--

DROP TABLE IF EXISTS `education_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `education_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `mark` double DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `curricula` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b1949439gwkx4pjayup8gn2d6` (`curricula`),
  CONSTRAINT `FK_b1949439gwkx4pjayup8gn2d6` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `education_data`
--

LOCK TABLES `education_data` WRITE;
/*!40000 ALTER TABLE `education_data` DISABLE KEYS */;
INSERT INTO `education_data` VALUES (309,0,'Engineering',NULL,'MIT',7.75,'2015-10-10 00:00:00',304);
/*!40000 ALTER TABLE `education_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `deadline` datetime DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `last_update` datetime DEFAULT NULL,
  `max_salary` int(11) DEFAULT NULL,
  `min_salary` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
INSERT INTO `finder` VALUES (272,0,NULL,NULL,'2000-01-01 00:00:00',NULL,NULL),(273,0,NULL,NULL,'2000-01-01 00:00:00',NULL,NULL);
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_positions`
--

DROP TABLE IF EXISTS `finder_positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_positions` (
  `finder` int(11) NOT NULL,
  `positions` int(11) NOT NULL,
  KEY `FK_3d46gil0nks2dhgg7cyhv2m39` (`positions`),
  KEY `FK_l0b3qg4nly59oeqhe8ig5yssc` (`finder`),
  CONSTRAINT `FK_3d46gil0nks2dhgg7cyhv2m39` FOREIGN KEY (`positions`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_l0b3qg4nly59oeqhe8ig5yssc` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_positions`
--

LOCK TABLES `finder_positions` WRITE;
/*!40000 ALTER TABLE `finder_positions` DISABLE KEYS */;
INSERT INTO `finder_positions` VALUES (273,278),(273,279);
/*!40000 ALTER TABLE `finder_positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hacker`
--

DROP TABLE IF EXISTS `hacker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hacker` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_banned` bit(1) DEFAULT NULL,
  `is_spammer` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `vat` int(11) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `finder` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_eohjws41xqofou03dl7t7aku4` (`finder`),
  UNIQUE KEY `UK_pechhr6iex4b7l2rymmx1xi38` (`user_account`),
  CONSTRAINT `FK_eohjws41xqofou03dl7t7aku4` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_pechhr6iex4b7l2rymmx1xi38` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hacker`
--

LOCK TABLES `hacker` WRITE;
/*!40000 ALTER TABLE `hacker` DISABLE KEYS */;
INSERT INTO `hacker` VALUES (300,0,'hacker1 18','378721273855309','hacker1@gmail.com',NULL,NULL,'hacker1','+34656256697',NULL,'hacker1','hacker1',13,266,272),(301,0,'hacker2 18','378721273855309','hacker2@gmail.com',NULL,_binary '','hacker2','+34656256697',NULL,'hacker2','hacker2',13,268,273);
/*!40000 ALTER TABLE `hacker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('domain_entity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `is_notification` bit(1) DEFAULT NULL,
  `is_spam` bit(1) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `sender` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (294,1,'body example',NULL,NULL,'2019-02-12 00:00:00','LOW','subject example',300),(295,1,'You have to use cialis',NULL,NULL,'2019-02-12 00:00:00','LOW','subject example',301),(296,0,'You have to use viagra',NULL,NULL,'2019-02-12 00:00:00','LOW','subject example',274),(297,0,'This is fantastic, amazing',NULL,NULL,'2019-02-12 00:00:00','LOW','subject example',276),(298,0,'This is bad, horrible',NULL,NULL,'2019-02-12 00:00:00','LOW','subject example',270);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_recipients`
--

DROP TABLE IF EXISTS `message_recipients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_recipients` (
  `message` int(11) NOT NULL,
  `recipients` int(11) NOT NULL,
  KEY `FK_1odmg2n3n487tvhuxx5oyyya2` (`message`),
  CONSTRAINT `FK_1odmg2n3n487tvhuxx5oyyya2` FOREIGN KEY (`message`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_recipients`
--

LOCK TABLES `message_recipients` WRITE;
/*!40000 ALTER TABLE `message_recipients` DISABLE KEYS */;
INSERT INTO `message_recipients` VALUES (294,301),(295,300),(296,276),(297,274),(298,300);
/*!40000 ALTER TABLE `message_recipients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_tags`
--

DROP TABLE IF EXISTS `message_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_tags` (
  `message` int(11) NOT NULL,
  `tags` varchar(255) DEFAULT NULL,
  KEY `FK_suckduhsrrtot7go5ejeev57w` (`message`),
  CONSTRAINT `FK_suckduhsrrtot7go5ejeev57w` FOREIGN KEY (`message`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_tags`
--

LOCK TABLES `message_tags` WRITE;
/*!40000 ALTER TABLE `message_tags` DISABLE KEYS */;
INSERT INTO `message_tags` VALUES (294,'WORK'),(294,'IMPORTAN');
/*!40000 ALTER TABLE `message_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_data`
--

DROP TABLE IF EXISTS `miscellaneous_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `curricula` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_h48spqfrohqicpoi2y2kmdvhb` (`curricula`),
  CONSTRAINT `FK_h48spqfrohqicpoi2y2kmdvhb` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_data`
--

LOCK TABLES `miscellaneous_data` WRITE;
/*!40000 ALTER TABLE `miscellaneous_data` DISABLE KEYS */;
INSERT INTO `miscellaneous_data` VALUES (310,0,'I have several experience with some things',304);
/*!40000 ALTER TABLE `miscellaneous_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_data_attachments`
--

DROP TABLE IF EXISTS `miscellaneous_data_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_data_attachments` (
  `miscellaneous_data` int(11) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `target_id` int(11) NOT NULL,
  KEY `FK_e1oadh6x6vsemmnrwp19vocmr` (`miscellaneous_data`),
  CONSTRAINT `FK_e1oadh6x6vsemmnrwp19vocmr` FOREIGN KEY (`miscellaneous_data`) REFERENCES `miscellaneous_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_data_attachments`
--

LOCK TABLES `miscellaneous_data_attachments` WRITE;
/*!40000 ALTER TABLE `miscellaneous_data_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `miscellaneous_data_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_data`
--

DROP TABLE IF EXISTS `personal_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `git_hub` varchar(255) DEFAULT NULL,
  `linked_in` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `curricula` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9cabl85nl8no5idl3srdofe4c` (`curricula`),
  CONSTRAINT `FK_9cabl85nl8no5idl3srdofe4c` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_data`
--

LOCK TABLES `personal_data` WRITE;
/*!40000 ALTER TABLE `personal_data` DISABLE KEYS */;
INSERT INTO `personal_data` VALUES (306,0,'Hacker 1','https://www.google.com/','https://www.google.com/','632587412','statement',304),(307,0,'Hacker 2','https://www.google.com/','https://www.google.com/','632888412','statement',305);
/*!40000 ALTER TABLE `personal_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cancelled` bit(1) DEFAULT NULL,
  `deadline` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `final_mode` bit(1) DEFAULT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `skills` varchar(255) DEFAULT NULL,
  `technologies` varchar(255) DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `company` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_15390c4j2aeju6ha0iwvi6mc5` (`ticker`),
  KEY `FK_7qlfn4nye234rrm4w83nms1g8` (`company`),
  CONSTRAINT `FK_7qlfn4nye234rrm4w83nms1g8` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (278,0,NULL,'2019-06-12 00:00:00','Se busca programador en javascript',_binary '','Programador',20000,'skill1, skill2, skill3','tech1, tech2, tech3','comp-1234','Programador en  javascript',274),(279,0,NULL,'2019-07-12 00:00:00','Puesto de trabajo para consultor en seguridad',_binary '','Consultor',34000,'skill1, skill2, skill3','tech1, tech2, tech3','posi-4239','Consultor de seguridad',274),(280,0,NULL,'2019-06-20 00:00:00','Se busca analista de sistemas web',_binary '','Programador',17000,'skill1, skill2, skill3','tech1, tech2, tech3','abcd-4739','Analista de sistemas web',276),(281,0,_binary '\0','2019-07-18 00:00:00','Puesto de trabajo para especialistas en big data',_binary '\0','Consultor',24000,'skill1, skill2, skill3','tech1, tech2, tech3','posi-4999','Especialista en Big Data',274);
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_data`
--

DROP TABLE IF EXISTS `position_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `curricula` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_50pf203n1ob4iihr5n18eeoxf` (`curricula`),
  CONSTRAINT `FK_50pf203n1ob4iihr5n18eeoxf` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_data`
--

LOCK TABLES `position_data` WRITE;
/*!40000 ALTER TABLE `position_data` DISABLE KEYS */;
INSERT INTO `position_data` VALUES (308,0,'I was CEO of sony',NULL,'2015-10-10 00:00:00','CEO',304);
/*!40000 ALTER TABLE `position_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_problems`
--

DROP TABLE IF EXISTS `position_problems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_problems` (
  `position` int(11) NOT NULL,
  `problems` int(11) NOT NULL,
  KEY `FK_7pe330ganri24wsftsajlm4l9` (`problems`),
  KEY `FK_iji6l3ytrjgytbgo6oi061elj` (`position`),
  CONSTRAINT `FK_7pe330ganri24wsftsajlm4l9` FOREIGN KEY (`problems`) REFERENCES `problem` (`id`),
  CONSTRAINT `FK_iji6l3ytrjgytbgo6oi061elj` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_problems`
--

LOCK TABLES `position_problems` WRITE;
/*!40000 ALTER TABLE `position_problems` DISABLE KEYS */;
INSERT INTO `position_problems` VALUES (278,283),(278,284),(278,285),(279,289),(279,290),(279,291),(280,286),(280,287),(280,288),(281,286),(281,287),(281,288);
/*!40000 ALTER TABLE `position_problems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem`
--

DROP TABLE IF EXISTS `problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `final_mode` bit(1) DEFAULT NULL,
  `hint` varchar(255) DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `company` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1dla8eoii1nn6emoo4yv86pgb` (`company`),
  CONSTRAINT `FK_1dla8eoii1nn6emoo4yv86pgb` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem`
--

LOCK TABLES `problem` WRITE;
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
INSERT INTO `problem` VALUES (283,0,_binary '',NULL,'Creación de codigo en javascript','Bugs en codigo javascript en Aplicacion web',274),(284,0,_binary '',NULL,'Realizacion de pruebas en javascript','validacion de codigo javascript en Aplicacion web',274),(285,0,_binary '\0',NULL,'Se han de implementar funciones diversas al producto','Implementacion de nuevas funciones',274),(286,0,_binary '',NULL,'Creación de codigo en php','Bugs en codigo php en Aplicacion web',276),(287,0,_binary '',NULL,'Realizacion de pruebas en C#','validacion de codigo C# en Aplicacion web',276),(288,0,_binary '','Hinst for problem 6','Statement problem 6','problem6',274),(289,0,_binary '','Hinst for problem 7','Statement problem 7','problem7',274),(290,0,_binary '','Hinst for problem 8','Statement problem 8','problem8',274),(291,0,_binary '','Hinst for problem 9','Statement problem 9','problem9',274);
/*!40000 ALTER TABLE `problem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem_attachments`
--

DROP TABLE IF EXISTS `problem_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_attachments` (
  `problem` int(11) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `target_id` int(11) NOT NULL,
  KEY `FK_mhrgqo77annlexxubmtv4qvf7` (`problem`),
  CONSTRAINT `FK_mhrgqo77annlexxubmtv4qvf7` FOREIGN KEY (`problem`) REFERENCES `problem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem_attachments`
--

LOCK TABLES `problem_attachments` WRITE;
/*!40000 ALTER TABLE `problem_attachments` DISABLE KEYS */;
INSERT INTO `problem_attachments` VALUES (283,'http://www.attachments.com/1',0),(283,'http://www.attachments.com/2',0),(288,'http://www.attachments.com/1',0),(288,'http://www.attachments.com/2',0),(288,'http://www.attachments.com/3',0),(289,'http://www.attachments.com/1',0),(289,'http://www.attachments.com/2',0),(289,'http://www.attachments.com/3',0),(290,'http://www.attachments.com/1',0),(290,'http://www.attachments.com/2',0),(290,'http://www.attachments.com/3',0),(291,'http://www.attachments.com/1',0),(291,'http://www.attachments.com/2',0),(291,'http://www.attachments.com/3',0);
/*!40000 ALTER TABLE `problem_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem_positions`
--

DROP TABLE IF EXISTS `problem_positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_positions` (
  `problem` int(11) NOT NULL,
  `positions` int(11) NOT NULL,
  KEY `FK_ivrsr904uy11k43g8v0lnbtyg` (`positions`),
  KEY `FK_1bxlrdbreiq1fcldwcihp2hpx` (`problem`),
  CONSTRAINT `FK_1bxlrdbreiq1fcldwcihp2hpx` FOREIGN KEY (`problem`) REFERENCES `problem` (`id`),
  CONSTRAINT `FK_ivrsr904uy11k43g8v0lnbtyg` FOREIGN KEY (`positions`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem_positions`
--

LOCK TABLES `problem_positions` WRITE;
/*!40000 ALTER TABLE `problem_positions` DISABLE KEYS */;
INSERT INTO `problem_positions` VALUES (283,278),(284,278),(286,280),(286,281),(287,280),(287,281),(288,280),(288,281),(289,279),(290,279),(291,279);
/*!40000 ALTER TABLE `problem_positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_profile`
--

DROP TABLE IF EXISTS `social_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social_profile` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `social_network` varchar(255) DEFAULT NULL,
  `actor` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_profile`
--

LOCK TABLES `social_profile` WRITE;
/*!40000 ALTER TABLE `social_profile` DISABLE KEYS */;
INSERT INTO `social_profile` VALUES (271,0,'http://www.facebook/admin','admin','facebook',270),(275,0,'http://www.facebook/member1','company1','facebook',274),(277,0,'http://www.facebook/member2','company2','facebook',276),(292,1,'http://www.facebook/brotherhood1','hacker1','facebook',300),(293,1,'http://www.facebook/brotherhood2','hacker2','facebook',301);
/*!40000 ALTER TABLE `social_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (265,0,'21232f297a57a5a743894a0e4a801fc3','admin'),(266,0,'2ba2a8ac968a7a2b0a7baa7f2fef18d2','hacker1'),(267,0,'df655f976f7c9d3263815bd981225cd9','company1'),(268,0,'91af68b69a50a98dbc0800942540342c','hacker2'),(269,0,'d196a28097115067fefd73d25b0c0be8','company2');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_authorities`
--

DROP TABLE IF EXISTS `user_account_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_authorities` (
  `user_account` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_pao8cwh93fpccb0bx6ilq6gsl` (`user_account`),
  CONSTRAINT `FK_pao8cwh93fpccb0bx6ilq6gsl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (265,'ADMIN'),(266,'HACKER'),(267,'COMPANY'),(268,'HACKER'),(269,'COMPANY');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-22 19:58:13
