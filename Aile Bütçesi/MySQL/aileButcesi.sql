CREATE DATABASE  IF NOT EXISTS `ailebutcesi` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ailebutcesi`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: ailebutcesi
-- ------------------------------------------------------
-- Server version	5.6.20

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
-- Table structure for table `degisimler`
--

DROP TABLE IF EXISTS `degisimler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `degisimler` (
  `degisim_id` int(11) NOT NULL AUTO_INCREMENT,
  `tip` char(5) NOT NULL,
  `miktar` double NOT NULL,
  `aciklama` varchar(40) NOT NULL,
  `kategori_no` int(11) NOT NULL,
  `sene` int(11) NOT NULL,
  `ay` int(11) NOT NULL,
  `ekleme_tarihi` date NOT NULL,
  PRIMARY KEY (`degisim_id`),
  KEY `fk_kategori` (`kategori_no`),
  CONSTRAINT `fk_kategori` FOREIGN KEY (`kategori_no`) REFERENCES `kategoriler` (`kategori_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `degisimler`
--

LOCK TABLES `degisimler` WRITE;
/*!40000 ALTER TABLE `degisimler` DISABLE KEYS */;
INSERT INTO `degisimler` VALUES (4,'Gelir',200,'açıklama',1,2015,1,'2015-05-27'),(5,'Gelir',300,'açıklama',1,2015,1,'2015-05-27'),(6,'Gider',300,'açıklama',7,2015,1,'2015-05-27'),(7,'Gider',300,'açıklama',9,2015,1,'2015-05-27'),(8,'Gelir',300,'xyz',9,2015,1,'2015-03-05'),(9,'Gelir',300,'açıklama',1,2015,1,'2015-05-27'),(11,'Gider',366,'açıklama',7,2015,5,'2015-05-27'),(12,'Gider',366,'açıklama',7,2014,5,'2015-05-27'),(13,'Gelir',260,'açıklama',1,2013,4,'2015-05-27');
/*!40000 ALTER TABLE `degisimler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategoriler`
--

DROP TABLE IF EXISTS `kategoriler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kategoriler` (
  `kategori_id` int(11) NOT NULL AUTO_INCREMENT,
  `ad` varchar(20) NOT NULL,
  `tip` char(5) NOT NULL,
  PRIMARY KEY (`kategori_id`),
  UNIQUE KEY `uc_k` (`ad`,`tip`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategoriler`
--

LOCK TABLES `kategoriler` WRITE;
/*!40000 ALTER TABLE `kategoriler` DISABLE KEYS */;
INSERT INTO `kategoriler` VALUES (1,'Diğer','Gelir'),(2,'Diğer','Gider'),(7,'Eğitim','Gider'),(12,'Fatura','Gider'),(15,'Haha','Gider'),(11,'Kira','Gider'),(3,'Maaş','Gelir'),(9,'Market','Gider'),(10,'Pazar','Gider'),(4,'Prim','Gelir'),(8,'Sağlık','Gider'),(5,'Satış','Gelir'),(6,'Ulaşım','Gider');
/*!40000 ALTER TABLE `kategoriler` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-27 10:06:50
