-- MySQL dump 10.13  Distrib 5.1.62, for unknown-linux-gnu (x86_64)
--
-- Host: localhost    Database: roiexp_med
-- ------------------------------------------------------
-- Server version	5.1.62-cll

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
-- Table structure for table `complaints`
--

DROP TABLE IF EXISTS `complaints`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complaints` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) DEFAULT '10' COMMENT 'for joins to master table',
  `left_x` decimal(10,0) DEFAULT NULL,
  `top_y` decimal(10,0) DEFAULT NULL,
  `width` decimal(10,0) DEFAULT NULL,
  `height` decimal(10,0) DEFAULT NULL,
  `front_back` tinyint(4) NOT NULL DEFAULT '0',
  `complaint` text NOT NULL,
  `tstamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ip` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=94 DEFAULT CHARSET=latin1 ROW_FORMAT=FIXED;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaints`
--

LOCK TABLES `complaints` WRITE;
/*!40000 ALTER TABLE `complaints` DISABLE KEYS */;
INSERT INTO `complaints` (`id`, `patient_id`, `left_x`, `top_y`, `width`, `height`, `front_back`, `complaint`, `tstamp`, `ip`) VALUES (14,10,'342','138','47','99',1,'Kidney pain','2012-02-11 15:50:49',''),(16,10,'208','298','119','84',1,'Knee hurts','2012-02-13 12:00:37',''),(17,10,'121','157','31','39',0,'Stomach ache','2012-02-17 14:11:23',''),(18,10,'347','174','63','22',1,'Low back pain','2012-02-18 12:01:42',''),(21,10,'179','138','24','33',0,'Left bicep pain','2012-02-20 16:13:54',''),(24,10,'354','344','10','78',1,'Leg muscle pain','2012-03-07 12:15:19',''),(25,10,'95','108','39','60',0,'Heart ache','2012-03-08 12:16:47',''),(30,10,'150','116','8','9',0,'Heart','2012-03-13 03:33:13',''),(33,10,'131','144','28','24',0,'Chest pain','2012-03-13 10:14:15',''),(32,10,'366','56','19','9',1,'the back of my head hurts','2012-03-14 20:08:39',''),(34,10,'202','194','12','16',0,'Pain in my left arm','2012-03-15 11:23:53',''),(36,10,'353','94','56','57',1,'Spine pain','2012-03-17 01:34:42',''),(41,10,'398','96','24','27',1,'my back sholder hurts','2012-03-18 02:00:50',''),(42,10,'437','169','6','14',1,'Right elbow swollen','2012-03-18 06:09:09',''),(43,10,'154','322','20','28',0,'my right knee hurts','2012-03-18 02:58:19',''),(40,10,'130','160','20','15',0,'My Stomach hurts','2012-03-18 03:30:59',''),(44,10,'342','100','15','54',1,'Back pain','2012-03-18 06:13:28',''),(45,10,'107','114','15','59',0,'Chest pain','2012-03-18 06:11:35',''),(46,10,'345','243','64','101',1,'Legs pain','2012-03-18 05:59:25',''),(47,10,'271','258','7','4',1,'My Middle Finger is sore','2012-03-19 01:01:05',''),(48,10,'358','122','47','64',1,'Pain in the low back','2012-03-19 13:12:02',''),(49,10,'109','141','66','95',0,'Stomach ache','2012-03-19 13:13:18',''),(50,10,'83','110','44','60',0,'sharp chest pain','2012-03-19 13:15:10',''),(51,10,'270','228','21','31',1,'Palm numness','2012-03-19 13:18:33',''),(52,10,'84','101','63','60',0,'Chest pain','2012-03-19 13:24:17',''),(53,10,'275','223','18','31',1,'Arm numness','2012-03-19 13:24:37',''),(54,10,'365','73','22','7',1,'Back of my neck hurts','2012-03-20 21:30:44',''),(55,10,'145','112','22','17',0,'chest pain','2012-03-21 21:36:46',''),(56,10,'165','56','5','20',0,'Ear pain','2012-03-27 10:41:26',''),(57,10,'143','48','19','22',0,'ear infection','2012-03-27 20:09:02','50.131.134.141'),(58,10,'432','160','31','41',1,'right elbow pain','2012-03-28 13:06:51',''),(59,10,'103','134','59','59',0,'Stomach pain','2012-03-28 13:07:51',''),(60,10,'5','5','32','40',0,'test of IP address insertion','2012-03-29 07:11:36','50.131.134.141'),(61,10,'359','28','33','41',1,'headache','2012-03-30 22:49:23','50.131.134.141'),(62,10,'117','31','33','48',0,'cara','2012-03-31 01:06:23','50.17.53.47'),(63,10,'43','168','26','49',0,'right forearm','2012-03-31 02:25:21','107.20.50.246'),(64,10,'343','22','69','77',1,'back of head, offset wrong again!?','2012-03-31 06:01:20','50.131.134.141'),(65,10,'335','178','21','27',1,'Elbow pain','2012-04-01 09:41:03',''),(66,10,'302','155','24','32',1,'Elbow pain','2012-04-01 09:46:54',''),(67,10,'320','100','22','34',1,'left shoulder rear, testing offset bug again','2012-04-03 09:18:34','50.131.134.141'),(68,10,'103','332','26','28',0,'right knee front','2012-05-04 00:08:46','50.131.134.141'),(69,10,'355','117','55','113',1,'Back ache','2012-05-04 09:14:55','115.77.128.27'),(70,10,'329','314','66','51',1,'leg pain','2012-05-04 09:15:26','115.77.128.27'),(71,10,'208','207','40','56',0,'Pain','2012-05-04 09:16:26','115.77.128.27'),(72,10,'399','235','47','72',1,'Ache','2012-05-04 09:16:35','115.77.128.27'),(73,10,'356','47','12','17',1,'Ear pain','2012-05-04 09:16:50','115.77.128.27'),(74,10,'148','337','15','19',0,'Knee pain','2012-05-04 09:17:07','115.77.128.27'),(75,10,'104','328','22','34',0,'Knee pain','2012-05-31 16:01:22','93.178.204.227'),(76,10,'115','371','11','34',0,'leg pain','2012-05-31 16:01:41','93.178.204.227'),(77,10,'122','171','21','21',0,'Stomach ache','2012-05-31 16:26:45','93.178.204.227'),(78,10,'149','126','25','22',0,'Heart ache','2012-05-31 16:27:07','93.178.204.227'),(79,10,'105','308','27','48',0,'Knee','2012-06-01 18:08:26','93.178.204.226'),(80,10,'101','299','30','74',0,'Knee pain','2012-06-01 18:09:31','93.178.204.226'),(81,10,'103','327','27','28',0,'Knee pain','2012-06-01 18:39:19','93.178.204.226'),(82,10,'21','251','7','5',0,'Finger pain','2012-06-01 18:39:38','93.178.204.226'),(83,10,'146','322','25','28',0,'Knee pain','2012-06-02 11:48:11','93.178.204.226'),(84,10,'104','318','24','28',0,'knee pain','2012-06-02 11:50:36','93.178.204.226'),(85,10,'213','214','14','16',0,'Arm pain','2012-06-02 11:50:48','93.178.204.226'),(86,10,'104','317','22','27',0,'Knee pain','2012-06-02 11:53:21','93.178.204.226'),(87,10,'211','221','24','16',0,'Arm pain','2012-06-02 11:53:30','93.178.204.226'),(88,10,'117','171','24','13',0,'Stomach ache','2012-06-02 11:53:42','93.178.204.226'),(89,10,'108','328','20','26',0,'Knee pain','2012-06-02 11:54:56','93.178.204.226'),(90,10,'117','165','27','23',0,'Stomach ache','2012-06-02 11:55:08','93.178.204.226'),(91,10,'109','311','18','44',0,'knee pain','2012-06-02 12:04:39','93.178.204.226'),(92,10,'278','232','15','17',1,'arm pain','2012-06-02 12:04:50','93.178.204.226'),(93,10,'122','30','28','10',0,'headache','2012-06-04 18:43:35','');
/*!40000 ALTER TABLE `complaints` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prv_users`
--

DROP TABLE IF EXISTS `prv_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prv_users` (
  `username` varchar(32) NOT NULL,
  `password` tinytext NOT NULL,
  `expires` int(10) NOT NULL DEFAULT '0',
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prv_users`
--

LOCK TABLES `prv_users` WRITE;
/*!40000 ALTER TABLE `prv_users` DISABLE KEYS */;
INSERT INTO `prv_users` (`username`, `password`, `expires`) VALUES ('demo10','55583a44',1337339713),('andrew','ae1c7d13',1339094076),('demo34','058f5e19',0),('demo654','07cf2679',1337568055),('vovka','a7376892',1338029551),('tt','123',1137982755),('meka','eda6b312',1338033071),('ya','badf4785',1338140765),('yay','de0076b0',1338362736),('zaraz','7a6842f2',1338738963),('kak','42721774',1338921699);
/*!40000 ALTER TABLE `prv_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timeline`
--

DROP TABLE IF EXISTS `timeline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timeline` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` smallint(10) NOT NULL DEFAULT '10',
  `first_appointment` date NOT NULL,
  `start_treatment` date NOT NULL,
  `current_date` date NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timeline`
--

LOCK TABLES `timeline` WRITE;
/*!40000 ALTER TABLE `timeline` DISABLE KEYS */;
INSERT INTO `timeline` (`ID`, `patient_id`, `first_appointment`, `start_treatment`, `current_date`) VALUES (1,10,'2012-01-02','2012-01-08','2012-03-17');
/*!40000 ALTER TABLE `timeline` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-06-15  9:14:04
