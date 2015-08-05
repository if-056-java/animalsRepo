# MySQL-Front 5.0  (Build 1.0)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: localhost    Database: animals
# ------------------------------------------------------
# Server version 5.6.25

DROP DATABASE IF EXISTS `animals`;
CREATE DATABASE `animals` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `animals`;

#
# Table structure for table addresses
#

CREATE TABLE `addresses` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Адреси користувачів, власників та перебування тварин',
  `country` varchar(25) DEFAULT NULL COMMENT 'Місто',
  `region` varchar(30) DEFAULT NULL COMMENT 'Вулиця',
  `town` varchar(25) DEFAULT NULL COMMENT 'Номер будинку',
  `street` varchar(30) DEFAULT NULL COMMENT 'Номер квартири',
  `postIndex` int(11) DEFAULT NULL COMMENT 'Поштовий індекс',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
INSERT INTO `addresses` VALUES (23,'Country','lvyv','lvyv','test',86354);
INSERT INTO `addresses` VALUES (24,'ukr','lvyv','lvyv','test',86354);
INSERT INTO `addresses` VALUES (25,'ukr','lvyv','lvyv','test',86354);
INSERT INTO `addresses` VALUES (28,'ukr','lvyv','lvyv','test',86354);
INSERT INTO `addresses` VALUES (29,'ukr','lvyv','lvyv','test',86354);
INSERT INTO `addresses` VALUES (30,'ukr','lvyv','lvyv','test',86354);
INSERT INTO `addresses` VALUES (31,'ukr','lvyv','lvyv','test',86354);
INSERT INTO `addresses` VALUES (32,'ukr','lvyv','lvyv','test',86354);
INSERT INTO `addresses` VALUES (33,'ukr','lvyv','lvyv','test',86354);
INSERT INTO `addresses` VALUES (34,'ukr','lvyv','lvyv','test',86354);
INSERT INTO `addresses` VALUES (35,'ukr','lvyv','lvyv','test',86354);
INSERT INTO `addresses` VALUES (36,'ukr','lvyv','lvyv','test',86354);
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

#
# Table structure for table animalcitestypes
#

CREATE TABLE `animalcitestypes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код виду класифікації CITES',
  `type` varchar(15) NOT NULL COMMENT 'Назва класифікації CITES',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `type_UNIQUE` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
INSERT INTO `animalcitestypes` VALUES (1,'не відноситься');
INSERT INTO `animalcitestypes` VALUES (2,'CITES I');
INSERT INTO `animalcitestypes` VALUES (3,'CITES II');
INSERT INTO `animalcitestypes` VALUES (4,'CITES III');
/*!40000 ALTER TABLE `animalcitestypes` ENABLE KEYS */;
UNLOCK TABLES;

#
# Table structure for table animals
#

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
  KEY `fkAdress_idx1` (`ID`,`addressId`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
INSERT INTO `animals` VALUES (1,1,1,2,3,'wtf','213121','312321221','2015-07-22',NULL,NULL,'red',1,31,1,NULL,1);
INSERT INTO `animals` VALUES (5,2,3,1,3,'ara','p215412',NULL,'2002-10-25','2001-10-25',NULL,'black',1,24,1,NULL,2);
INSERT INTO `animals` VALUES (7,3,2,2,1,'dvorka','1231312','1231432','1986-05-18','1986-05-18',NULL,'gray',1,29,1,NULL,4);
INSERT INTO `animals` VALUES (9,3,2,2,1,'dvorka','1234545254','1231432','1986-05-18','1986-05-18',NULL,'gray',1,29,1,NULL,4);
INSERT INTO `animals` VALUES (10,3,2,2,1,'dvorka','242524','1231432','1986-05-18','1986-05-18',NULL,'gray',1,29,1,NULL,4);
INSERT INTO `animals` VALUES (11,3,2,2,1,'dvorka','4528987','1231432','1986-05-18','1986-05-18',NULL,'gray',1,29,1,NULL,4);
INSERT INTO `animals` VALUES (12,3,2,2,1,'dvorka','452228725','1231432','1986-05-18','1986-05-18',NULL,'gray',1,29,1,NULL,4);
INSERT INTO `animals` VALUES (13,3,2,2,1,'dvorka','45254827','1231432','1986-05-18','1986-05-18',NULL,'gray',1,29,1,NULL,4);
INSERT INTO `animals` VALUES (14,3,2,2,1,'dvorka','452524','1231432','1986-05-18','1986-05-18',NULL,'gray',1,29,1,NULL,4);
INSERT INTO `animals` VALUES (15,3,2,2,1,'dvorka','452782424','1231432','1986-05-18','1986-05-18',NULL,'gray',1,29,1,NULL,4);
INSERT INTO `animals` VALUES (16,3,2,2,1,'dvorka','25428284','1231432','1986-05-18','1986-05-18',NULL,'gray',1,29,1,NULL,4);
INSERT INTO `animals` VALUES (17,3,2,2,1,'dvorka','24548982','1231432','1986-05-18','1986-05-18',NULL,'gray',1,29,1,NULL,4);
INSERT INTO `animals` VALUES (18,3,2,2,1,'dvorka','2452582424','1231432','1986-05-18','1986-05-18',NULL,'gray',1,29,1,NULL,4);
/*!40000 ALTER TABLE `animals` ENABLE KEYS */;
UNLOCK TABLES;

#
# Table structure for table animalservices
#

CREATE TABLE `animalservices` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код',
  `service` varchar(15) NOT NULL COMMENT 'Вид сервісу',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `service_UNIQUE` (`service`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
INSERT INTO `animalservices` VALUES (1,'адопція');
INSERT INTO `animalservices` VALUES (2,'знайдена');
INSERT INTO `animalservices` VALUES (3,'втрачена');
INSERT INTO `animalservices` VALUES (4,'власна');
/*!40000 ALTER TABLE `animalservices` ENABLE KEYS */;
UNLOCK TABLES;

#
# Table structure for table animalsextypes
#

CREATE TABLE `animalsextypes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код статі',
  `sex` varchar(15) NOT NULL COMMENT 'Стать тварини',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
INSERT INTO `animalsextypes` VALUES (1,'не визначено');
INSERT INTO `animalsextypes` VALUES (2,'самець');
INSERT INTO `animalsextypes` VALUES (3,'самка');
/*!40000 ALTER TABLE `animalsextypes` ENABLE KEYS */;
UNLOCK TABLES;

#
# Table structure for table animalsizes
#

CREATE TABLE `animalsizes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код розміру',
  `size` varchar(25) NOT NULL COMMENT 'Опис розміру',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `size_UNIQUE` (`size`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
INSERT INTO `animalsizes` VALUES (1,'мала (до 45 см)');
INSERT INTO `animalsizes` VALUES (2,'середня (45 - 65 см)');
INSERT INTO `animalsizes` VALUES (3,'велика (від 65 см)');
/*!40000 ALTER TABLE `animalsizes` ENABLE KEYS */;
UNLOCK TABLES;

#
# Table structure for table animaltypes
#

CREATE TABLE `animaltypes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код виду тварини',
  `type` varchar(15) NOT NULL COMMENT 'Вид тварини',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `kind_UNIQUE` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
INSERT INTO `animaltypes` VALUES (1,'собака');
INSERT INTO `animaltypes` VALUES (2,'кішка');
INSERT INTO `animaltypes` VALUES (3,'птах');
INSERT INTO `animaltypes` VALUES (4,'гризун');
INSERT INTO `animaltypes` VALUES (5,'куницеві');
INSERT INTO `animaltypes` VALUES (6,'рептилія');
INSERT INTO `animaltypes` VALUES (7,'риба');
INSERT INTO `animaltypes` VALUES (8,'кінь');
INSERT INTO `animaltypes` VALUES (9,'ведмідь');
/*!40000 ALTER TABLE `animaltypes` ENABLE KEYS */;
UNLOCK TABLES;

#
# Table structure for table useroperationslogger
#

CREATE TABLE `useroperationslogger` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код операції',
  `date` datetime NOT NULL COMMENT 'Дата операції',
  `userId` int(11) NOT NULL COMMENT 'Код користувача, що виконав операцію',
  `operationId` int(11) NOT NULL COMMENT 'Код типу операції',
  `animalId` int(11) NOT NULL COMMENT 'Код тварини',
  PRIMARY KEY (`ID`),
  KEY `fkUsers_idx` (`userId`),
  KEY `fkOperationKinds_idx` (`operationId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
INSERT INTO `useroperationslogger` VALUES (1,'2015-06-12',1,2,1);
INSERT INTO `useroperationslogger` VALUES (2,'2015-06-22',1,3,1);
/*!40000 ALTER TABLE `useroperationslogger` ENABLE KEYS */;
UNLOCK TABLES;

#
# Table structure for table useroperationtypes
#

CREATE TABLE `useroperationtypes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код типу операції користувача',
  `type` varchar(11) NOT NULL COMMENT 'Назва операції',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
INSERT INTO `useroperationtypes` VALUES (1,'додавання');
INSERT INTO `useroperationtypes` VALUES (2,'виправлення');
INSERT INTO `useroperationtypes` VALUES (3,'видалення');
/*!40000 ALTER TABLE `useroperationtypes` ENABLE KEYS */;
UNLOCK TABLES;

#
# Table structure for table userroles
#

CREATE TABLE `userroles` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код ролі користувача',
  `userRole` varchar(10) NOT NULL COMMENT 'Назва ролі користувача',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
INSERT INTO `userroles` VALUES (1,'модератор');
INSERT INTO `userroles` VALUES (2,'реєстратор');
INSERT INTO `userroles` VALUES (3,'гість');
/*!40000 ALTER TABLE `userroles` ENABLE KEYS */;
UNLOCK TABLES;

#
# Table structure for table users
#

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
  KEY `fkAdress_idx` (`addressId`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
INSERT INTO `users` VALUES (1,'Ігор','Ботвин','2015-07-22',1,3,NULL,30,'Igor@bigmir.et',NULL,'password',NULL,NULL,1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

#
# Table structure for table usertypes
#

CREATE TABLE `usertypes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код типу користувача',
  `type` varchar(19) NOT NULL COMMENT 'Вид користувача',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
INSERT INTO `usertypes` VALUES (1,'власник');
INSERT INTO `usertypes` VALUES (2,'ветеринар');
INSERT INTO `usertypes` VALUES (3,'спілка кінологів');
INSERT INTO `usertypes` VALUES (4,'організація');
INSERT INTO `usertypes` VALUES (5,'інше');
/*!40000 ALTER TABLE `usertypes` ENABLE KEYS */;
UNLOCK TABLES;

#
#  Foreign keys for table animals
#

ALTER TABLE `animals`
ADD CONSTRAINT `FK_AddressId_Address_Id` FOREIGN KEY (`addressId`) REFERENCES `addresses` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkCites` FOREIGN KEY (`citesId`) REFERENCES `animalcitestypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkServices` FOREIGN KEY (`serviceId`) REFERENCES `animalservices` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkSex` FOREIGN KEY (`sexTypeId`) REFERENCES `animalsextypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkSize` FOREIGN KEY (`sizeId`) REFERENCES `animalsizes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkType` FOREIGN KEY (`typeId`) REFERENCES `animaltypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkUser` FOREIGN KEY (`userId`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

#
#  Foreign keys for table useroperationslogger
#

ALTER TABLE `useroperationslogger`
ADD CONSTRAINT `fkOperationKinds` FOREIGN KEY (`operationId`) REFERENCES `useroperationtypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkUserId` FOREIGN KEY (`userId`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

#
#  Foreign keys for table users
#

ALTER TABLE `users`
ADD CONSTRAINT `fkAdressId` FOREIGN KEY (`addressId`) REFERENCES `addresses` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkUserKinds` FOREIGN KEY (`userTypeId`) REFERENCES `usertypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkUserRoles` FOREIGN KEY (`userRoleId`) REFERENCES `userroles` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;


/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
