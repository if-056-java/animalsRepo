CREATE DATABASE  IF NOT EXISTS `animals` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `animals`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: animals
-- ------------------------------------------------------
-- Server version	5.6.15

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
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `addresses` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Адреси користувачів, власників та перебування тварин',
  `country` varchar(25) DEFAULT NULL COMMENT 'Місто',
  `region` varchar(30) DEFAULT NULL COMMENT 'Вулиця',
  `town` varchar(25) DEFAULT NULL COMMENT 'Номер будинку',
  `street` varchar(30) DEFAULT NULL COMMENT 'Номер квартири',
  `postIndex` int(11) DEFAULT NULL COMMENT 'Поштовий індекс',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (1,'ukr','lvyv','lvyv','test',86354),(2,'Great Britain','London','London','Street of Glory',6564);
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `animalcitestypes`
--

DROP TABLE IF EXISTS `animalcitestypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animalcitestypes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код виду класифікації CITES',
  `type` varchar(15) NOT NULL COMMENT 'Назва класифікації CITES',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `type_UNIQUE` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animalcitestypes`
--

LOCK TABLES `animalcitestypes` WRITE;
/*!40000 ALTER TABLE `animalcitestypes` DISABLE KEYS */;
INSERT INTO `animalcitestypes` VALUES (2,'CITES I'),(3,'CITES II'),(4,'CITES III'),(1,'не відноситься');
/*!40000 ALTER TABLE `animalcitestypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `animals`
--

DROP TABLE IF EXISTS `animals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animals` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код',
  `sexTypeId` int(11) NOT NULL COMMENT 'Стать',
  `typeId` int(11) NOT NULL COMMENT 'Код виду',
  `sizeId` int(11) NOT NULL COMMENT 'Код розміру',
  `citesId` int(11) NOT NULL COMMENT 'Код по довіднику CITES',
  `sort` varchar(30) DEFAULT NULL COMMENT 'Порода',
  `transpNumber` varchar(15) DEFAULT NULL COMMENT 'Номер мікрочіпа',
  `tokenNumber` varchar(12) DEFAULT NULL COMMENT 'Номер бірки',
  `dateOfRegister` date NOT NULL COMMENT 'Дата реєстрації',
  `dateOfBirth` date DEFAULT NULL COMMENT 'Дата народження',
  `dateOfSterilization` date DEFAULT NULL COMMENT 'Дата стерилізації',
  `color` varchar(20) NOT NULL COMMENT 'Окрас',
  `userId` int(11) DEFAULT NULL COMMENT 'Код реєстратора',
  `addressId` int(11) NOT NULL COMMENT 'Код адреси перебування тварини',
  `isActive` tinyint(1) DEFAULT NULL COMMENT 'Статус (жива/ні)',
  `image` varchar(50) DEFAULT NULL COMMENT 'Шлях до фотографії',
  `serviceId` int(11) NOT NULL COMMENT 'Код сервісу',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `transpNumber_UNIQUE` (`transpNumber`),
  KEY `fkSex_idx` (`sexTypeId`),
  KEY `fkSize_idx` (`sizeId`),
  KEY `fkCites_idx` (`citesId`),
  KEY `fkKind_idx` (`typeId`),
  KEY `fkServices_idx` (`serviceId`),
  KEY `fkUser_idx` (`userId`),
  KEY `fkAdress_idx` (`addressId`),
  KEY `fkAdress_idx1` (`ID`,`addressId`),
  CONSTRAINT `FK_AddressId_Address_Id` FOREIGN KEY (`addressId`) REFERENCES `addresses` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkCites` FOREIGN KEY (`citesId`) REFERENCES `animalcitestypes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkServices` FOREIGN KEY (`serviceId`) REFERENCES `animalservices` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkSex` FOREIGN KEY (`sexTypeId`) REFERENCES `animalsextypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkSize` FOREIGN KEY (`sizeId`) REFERENCES `animalsizes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkType` FOREIGN KEY (`typeId`) REFERENCES `animaltypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkUser` FOREIGN KEY (`userId`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animals`
--

LOCK TABLES `animals` WRITE;
/*!40000 ALTER TABLE `animals` DISABLE KEYS */;
INSERT INTO `animals` VALUES (1,2,9,2,1,'wtf??','53423423','realy?','2015-07-21','2000-03-12',NULL,'red',2,2,1,NULL,3);
/*!40000 ALTER TABLE `animals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `animalservices`
--

DROP TABLE IF EXISTS `animalservices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animalservices` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код',
  `service` varchar(15) NOT NULL COMMENT 'Вид сервісу',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `service_UNIQUE` (`service`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animalservices`
--

LOCK TABLES `animalservices` WRITE;
/*!40000 ALTER TABLE `animalservices` DISABLE KEYS */;
INSERT INTO `animalservices` VALUES (1,'адопція'),(4,'власна'),(3,'втрачена'),(2,'знайдена');
/*!40000 ALTER TABLE `animalservices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `animalsextypes`
--

DROP TABLE IF EXISTS `animalsextypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animalsextypes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код статі',
  `sex` varchar(15) NOT NULL COMMENT 'Стать тварини',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animalsextypes`
--

LOCK TABLES `animalsextypes` WRITE;
/*!40000 ALTER TABLE `animalsextypes` DISABLE KEYS */;
INSERT INTO `animalsextypes` VALUES (1,'не визначено'),(2,'самець'),(3,'самка');
/*!40000 ALTER TABLE `animalsextypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `animalsizes`
--

DROP TABLE IF EXISTS `animalsizes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animalsizes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код розміру',
  `size` varchar(25) NOT NULL COMMENT 'Опис розміру',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `size_UNIQUE` (`size`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animalsizes`
--

LOCK TABLES `animalsizes` WRITE;
/*!40000 ALTER TABLE `animalsizes` DISABLE KEYS */;
INSERT INTO `animalsizes` VALUES (3,'велика (від 65 см)'),(1,'мала (до 45 см)'),(2,'середня (45 - 65 см)');
/*!40000 ALTER TABLE `animalsizes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `animaltypes`
--

DROP TABLE IF EXISTS `animaltypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animaltypes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код виду тварини',
  `type` varchar(15) NOT NULL COMMENT 'Вид тварини',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `kind_UNIQUE` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animaltypes`
--

LOCK TABLES `animaltypes` WRITE;
/*!40000 ALTER TABLE `animaltypes` DISABLE KEYS */;
INSERT INTO `animaltypes` VALUES (9,'ведмідь'),(4,'гризун'),(8,'кінь'),(2,'кішка'),(5,'куницеві'),(3,'птах'),(6,'рептилія'),(7,'риба'),(1,'собака');
/*!40000 ALTER TABLE `animaltypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useroperationslogger`
--

DROP TABLE IF EXISTS `useroperationslogger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useroperationslogger` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код операції',
  `date` datetime NOT NULL COMMENT 'Дата операції',
  `userId` int(11) NOT NULL COMMENT 'Код користувача, що виконав операцію',
  `operationId` int(11) NOT NULL COMMENT 'Код типу операції',
  `animalId` int(11) NOT NULL COMMENT 'Код тварини',
  PRIMARY KEY (`ID`),
  KEY `fkUsers_idx` (`userId`),
  KEY `fkOperationKinds_idx` (`operationId`),
  CONSTRAINT `fkOperationKinds` FOREIGN KEY (`operationId`) REFERENCES `useroperationtypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkUserId` FOREIGN KEY (`userId`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useroperationslogger`
--

LOCK TABLES `useroperationslogger` WRITE;
/*!40000 ALTER TABLE `useroperationslogger` DISABLE KEYS */;
INSERT INTO `useroperationslogger` VALUES (1,'2015-07-24 00:00:00',2,3,1),(2,'2015-07-24 00:00:00',2,1,1);
/*!40000 ALTER TABLE `useroperationslogger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useroperationtypes`
--

DROP TABLE IF EXISTS `useroperationtypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useroperationtypes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код типу операції користувача',
  `type` varchar(11) NOT NULL COMMENT 'Назва операції',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useroperationtypes`
--

LOCK TABLES `useroperationtypes` WRITE;
/*!40000 ALTER TABLE `useroperationtypes` DISABLE KEYS */;
INSERT INTO `useroperationtypes` VALUES (1,'додавання'),(2,'виправлення'),(3,'видалення');
/*!40000 ALTER TABLE `useroperationtypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userroles`
--

DROP TABLE IF EXISTS `userroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userroles` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код ролі користувача',
  `userRole` varchar(10) NOT NULL COMMENT 'Назва ролі користувача',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userroles`
--

LOCK TABLES `userroles` WRITE;
/*!40000 ALTER TABLE `userroles` DISABLE KEYS */;
INSERT INTO `userroles` VALUES (1,'модератор'),(2,'реєстратор'),(3,'гість');
/*!40000 ALTER TABLE `userroles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код користувача',
  `name` varchar(15) NOT NULL COMMENT 'Імя',
  `surname` varchar(15) NOT NULL COMMENT 'Прізвище',
  `dateOfRegistration` date NOT NULL COMMENT 'Дата реєстрації',
  `userTypeId` int(11) NOT NULL COMMENT 'Код виду користувача',
  `userRoleId` int(11) NOT NULL COMMENT 'Код ролі користувача',
  `phone` varchar(15) DEFAULT NULL COMMENT 'Телефон користувача',
  `addressId` int(11) DEFAULT NULL COMMENT 'Код поштової адреси користувача',
  `email` varchar(30) NOT NULL COMMENT 'Адреса е-мейл',
  `socialLogin` varchar(30) DEFAULT NULL COMMENT 'Логін в Фейсбуці чи інших соц-мережах',
  `password` varchar(30) NOT NULL COMMENT 'Пароль',
  `organizationName` varchar(50) DEFAULT NULL COMMENT 'Назва організації',
  `organizationInfo` varchar(70) DEFAULT NULL COMMENT 'Інформація про організацію',
  `isActive` tinyint(1) DEFAULT NULL COMMENT 'Індикація блокування користувача',
  PRIMARY KEY (`ID`),
  KEY `fkUserRoles_idx` (`userRoleId`),
  KEY `fkUserKinds_idx` (`userTypeId`),
  KEY `fkAdress_idx` (`addressId`),
  CONSTRAINT `fkAdressId` FOREIGN KEY (`addressId`) REFERENCES `addresses` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkUserKinds` FOREIGN KEY (`userTypeId`) REFERENCES `usertypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkUserRoles` FOREIGN KEY (`userRoleId`) REFERENCES `userroles` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Василь','Махно','2015-07-23',1,2,NULL,1,'mama1@i.ua',NULL,'mama1@i.ua',NULL,NULL,NULL),(2,'Андрій','Петрович','2015-07-23',5,2,NULL,1,'mama2@i.ua',NULL,'mama2@i.ua',NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertypes`
--

DROP TABLE IF EXISTS `usertypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usertypes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код типу користувача',
  `type` varchar(19) NOT NULL COMMENT 'Вид користувача',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertypes`
--

LOCK TABLES `usertypes` WRITE;
/*!40000 ALTER TABLE `usertypes` DISABLE KEYS */;
INSERT INTO `usertypes` VALUES (1,'власник'),(2,'ветеринар'),(3,'спілка кінологів'),(4,'організація'),(5,'інше');
/*!40000 ALTER TABLE `usertypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'animals'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-07-24 23:35:28
