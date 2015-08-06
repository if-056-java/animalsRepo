CREATE DATABASE  IF NOT EXISTS `animals` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `animals`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: tym.dp.ua    Database: animals
-- ------------------------------------------------------
-- Server version	5.5.44-0+deb7u1-log

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
-- Table structure for table `animals`
--

DROP TABLE IF EXISTS `animals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animals` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код',
  `sex` varchar(15) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Стать',
  `typeId` int(11) NOT NULL COMMENT 'Код виду',
  `size` varchar(25) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Розмір',
  `citesType` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Код по довіднику CITES',
  `sort` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Порода',
  `transpNumber` varchar(15) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Номер мікрочіпа',
  `tokenNumber` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Номер бірки',
  `dateOfRegister` date DEFAULT NULL COMMENT 'Дата реєстрації',
  `dateOfBirth` date DEFAULT NULL COMMENT 'Дата народження',
  `dateOfSterilization` date DEFAULT NULL COMMENT 'Дата стерилізації',
  `color` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT 'Окрас',
  `userId` int(11) DEFAULT NULL COMMENT 'Код реєстратора',
  `address` varchar(120) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Адреса перебування тварини',
  `isActive` tinyint(1) DEFAULT NULL COMMENT 'Статус (жива/ні)',
  `image` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Шлях до фотографії',
  `serviceId` int(11) NOT NULL COMMENT 'Код сервісу',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `transpNumber_UNIQUE` (`transpNumber`),
  KEY `fkKind_idx` (`typeId`),
  KEY `fkServices_idx` (`serviceId`),
  KEY `fkUser_idx` (`userId`),
  CONSTRAINT `fkServices` FOREIGN KEY (`serviceId`) REFERENCES `animalservices` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkType` FOREIGN KEY (`typeId`) REFERENCES `animaltypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkUser` FOREIGN KEY (`userId`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animals`
--

LOCK TABLES `animals` WRITE;
/*!40000 ALTER TABLE `animals` DISABLE KEYS */;
INSERT INTO `animals` VALUES (36,'3',3,'1','1',NULL,NULL,'2345','2015-02-20',NULL,'2015-02-20','чорний',25,'31',1,NULL,1),(37,'3',4,'2','1',NULL,NULL,NULL,'2015-02-20',NULL,NULL,'коричневий',25,'32',1,NULL,2),(38,'2',5,'3','2',NULL,'999999999999999',NULL,'2015-02-20',NULL,NULL,'зелений',25,'33',1,NULL,3);
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
  `service` varchar(15) CHARACTER SET utf8 NOT NULL COMMENT 'Вид сервісу',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `service_UNIQUE` (`service`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
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
-- Table structure for table `animaltypes`
--

DROP TABLE IF EXISTS `animaltypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animaltypes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код виду тварини',
  `type` varchar(15) CHARACTER SET utf8 NOT NULL COMMENT 'Вид тварини',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `kind_UNIQUE` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useroperationslogger`
--

LOCK TABLES `useroperationslogger` WRITE;
/*!40000 ALTER TABLE `useroperationslogger` DISABLE KEYS */;
INSERT INTO `useroperationslogger` VALUES (7,'2015-07-28 00:00:00',25,3,38);
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
  `type` varchar(11) CHARACTER SET utf8 NOT NULL COMMENT 'Назва операції',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
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
  `userRole` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT 'Назва ролі користувача',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
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
  `name` varchar(35) CHARACTER SET utf8 NOT NULL COMMENT 'Імя',
  `surname` varchar(45) CHARACTER SET utf8 NOT NULL COMMENT 'Прізвище',
  `dateOfRegistration` date NOT NULL COMMENT 'Дата реєстрації',
  `userTypeId` int(11) NOT NULL COMMENT 'Код виду користувача',
  `userRoleId` int(11) NOT NULL COMMENT 'Код ролі користувача',
  `phone` varchar(15) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Телефон користувача',
  `address` varchar(120) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Поштова адреса користувача',
  `email` varchar(30) CHARACTER SET utf8 NOT NULL COMMENT 'Адреса е-мейл',
  `socialLogin` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Логін в Фейсбуці чи інших соц-мережах',
  `password` varchar(30) CHARACTER SET utf8 NOT NULL COMMENT 'Пароль',
  `organizationName` varchar(70) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Назва організації',
  `organizationInfo` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Інформація про організацію',
  `isActive` tinyint(1) DEFAULT NULL COMMENT 'Індикація блокування користувача',
  PRIMARY KEY (`ID`),
  KEY `fkUserRoles_idx` (`userRoleId`),
  KEY `fkUserKinds_idx` (`userTypeId`),
  CONSTRAINT `fkUserKinds` FOREIGN KEY (`userTypeId`) REFERENCES `usertypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkUserRoles` FOREIGN KEY (`userRoleId`) REFERENCES `userroles` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (25,'Іван','Жуковський','2015-04-30',2,2,NULL,'32','mama1@i.au',NULL,'mama1@i.au','Ветиринарна клініка','в приміщенні аптеки',1),(43,'Денис','Петренко','2015-07-22',1,3,NULL,'31','mama1@i.au',NULL,'mama1@i.au',NULL,NULL,1),(75,'Петро','Стодола','2010-04-30',2,2,NULL,'32','mama1@gmail.com',NULL,'PetroSyla','Ветиринарна обсерваторія','Дім Бровка',1);
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
  `type` varchar(19) CHARACTER SET utf8 NOT NULL COMMENT 'Вид користувача',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertypes`
--

LOCK TABLES `usertypes` WRITE;
/*!40000 ALTER TABLE `usertypes` DISABLE KEYS */;
INSERT INTO `usertypes` VALUES (1,'власник'),(2,'ветеринар'),(3,'спілка кінологів'),(4,'організація'),(5,'інше');
/*!40000 ALTER TABLE `usertypes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-08-05  1:04:09
