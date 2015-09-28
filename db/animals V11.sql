CREATE DATABASE  IF NOT EXISTS `animals` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `animals`;
-- MySQL dump 10.13  Distrib 5.6.23, for Win32 (x86)
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
-- Table structure for table `animalbreeds`
--

DROP TABLE IF EXISTS `animalbreeds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animalbreeds` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `breedUa` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `breedRu` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `breedEn` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `animalTypeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fkAnimalType_idx` (`animalTypeId`),
  CONSTRAINT `fkAnimalType` FOREIGN KEY (`animalTypeId`) REFERENCES `animaltypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=622 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Породи тварин (коти, собаки)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animalbreeds`
--

LOCK TABLES `animalbreeds` WRITE;
/*!40000 ALTER TABLE `animalbreeds` DISABLE KEYS */;
INSERT INTO `animalbreeds` VALUES (1,'Австралійська хорт','Австралийская борзая','Australian greyhound',1),(2,'Австралійська гонча','Австралийская гончая','Australian Hound',1),(3,'Австралійська вівчарка','Австралийская овчарка','Australian Shepherd',1),(4,'Австралійська пастуша собака','Австралийская пастушья собака','Australian Cattle Dog',1),(5,'Австралійський тер\'єр','Австралийский терьер','Aussie',1),(6,'Австралійський шовковистий тер\'єр','Австралийский шелковистый терьер','Australian Silky Terrier',1),(7,'Азавак','Азавак','Azawak',1),(8,'Айну','Айну','Ainu',1),(9,'Акіта','Акита','Akita',1),(10,'Альпійський ТАКСОПОДІБНИЙ бракк','Альпийский таксообразный бракк','Alpine taksoobrazny Brakk',1),(11,'Аляскинський маламут','Аляскинский маламут','Alaskan Malamute',1),(12,'Американська блакитна гасконська гонча','Американская голубая гасконская гончая','American Blue Gascon Hound',1),(13,'Американська гонча','Американская гончая','American foxhound',1),(14,'Американська єнотова гонча','Американская енотовая гончая','American raccoon hound',1),(15,'Американський бультер\'єр','Американский бультерьер','American Bull Terrier',1),(16,'Американський кокер','Американский кокер','American Cocker',1),(17,'Американський пітбультер\'єр','Американский питбультерьер','American Pit Bull',1),(18,'Американський стаффордширський тер\'єр','Американский стаффордширский терьер','American Staffordshire Terrier',1),(19,'Американський Харьер','Американский харьер','American harer',1),(20,'Анатолійське вівчарка','Анатолийская овчарка','Anatolian Shepherd',1),(21,'Англійська єнотова гонча','Английская енотовая гончая','English raccoon hound',1),(22,'Англійська вівчарка','Английская овчарка','English Shepherd',1),(23,'Англійські лягаві','Английские легавые','English Pointing',1),(24,'Англійська бульдог','Английский бульдог','English Bulldog',1),(25,'Англійський сетер','Английский сеттер','English Setter',1),(26,'Англійська спрингер-спаніель','Английский спрингер-спаниель','English Springer Spaniel',1),(27,'Англійський той-спаніель','Английский той-спаниель','English Toy Spaniel',1),(28,'Англійська тойтер\'єр','Английский тойтерьер','English Toy',1),(29,'Англійська фоксхаунд','Английский фоксхаунд','English Foxhound',1),(30,'Аппенцеллер','Аппенцеллер','Appenzeller',1),(31,'Апсо','Апсо','Apso',1),(32,'Артезиан-нормандський бассет','Артезиано-нормандский бассет','Artezian-Norman Basset',1),(33,'Артуазская гонча','Артуазская гончая','Artuazskaya Hound',1),(34,'Арьежская гонча','Арьежская гончая','Arezhskaya Hound',1),(35,'Астурійська вівчарка','Астурийская овчарка','Consumption Shepherd',1),(36,'Афганський хорт','Афганская борзая','Afghan Hound',1),(37,'Африканська піщана собачка','Африканская песчаная собачка','African sand dog',1),(38,'Аффен-пінчер','Аффен-пинчер','Affen pinscher',1),(39,'Баварська гірська гонча','Баварская горная гончая','Bavarian Mountain Hound',1),(40,'Балканська гонча','Балканская гончая','Balkan Hound',1),(41,'Басенджи','Басенджи','Basenji',1),(42,'Баскська вівчарка','Баскская овчарка','Basque Shepherd',1),(43,'Бассет','Бассет','Basset',1),(44,'Бассет-хаунд','Бассет-хаунд','Basset Hound',1),(45,'Бедлінгтонтерьер','Бедлингтонтерьер','Bedlingtonterer',1),(46,'Білий високогірний тер\'єр','Белый высокогорный терьер','Alpine White Terrier',1),(47,'Бельгійська вівчарка','Бельгийская овчарка','Belgian Shepherd',1),(48,'Бельгійський бракк','Бельгийский бракк','Belgian Brakk',1),(49,'Бернська гонча','Бернская гончая','Berne Hound',1),(50,'Бернська пастуша собака','Бернская пастушья собака','Berne Cattle Dog',1),(51,'Бігль','Бигль','Beagle',1),(52,'Бігль-Харьер','Бигль-харьер','Beagle-harer',1),(53,'Бийи','Бийи','Biya',1),(54,'Бішон-лион','Бишон-лион','Bichon-Lyon',1),(55,'Бішон-фризі','Бишон-фризе','Bichon Vries',1),(56,'Бладхаунд','Бладхаунд','Bloodhound',1),(57,'Богемський тер\'єр','Богемский терьер','Bohemian Terrier',1),(58,'Боксер','Боксер','Boxer',1),(59,'Велика англо-французька гонча','Большая англо-французская гончая','Most Anglo-French Hound',1),(60,'Велика гасконских-сентонжская гонча','Большая гасконско-сентонжская гончая','Large-sentonzhskaya Gascon Hound',1),(61,'Велика блакитна гасконська гонча','Большая голубая гасконская гончая','Great Blue Gascon Hound',1),(62,'Велика деревна гонча','Большая древесная гончая','Large tree hound',1),(63,'Велика іспанська гонча','Большая испанская гончая','Large Spanish Hound',1),(64,'Велика швейцарська гірська пастуший собака','Большая швейцарская горная пастушья собака','Great Swiss Mountain and Cattle Dog',1),(65,'Великий зенненхунд','Большой зенненхунд','Big Mountain Dog',1),(66,'Великий мюнстерлендер','Большой мюнстерлендер','Big myunsterlender',1),(67,'Великий португальська поденгу','Большой португальский поденгу','Large Portuguese podengu',1),(68,'Бонготерьер','Бонготерьер','Bongoterer',1),(69,'Бордер-коллі','Бордер-колли','Border-collie',1),(70,'Бордертерьер','Бордертерьер','Borderterer',1),(71,'Бордоський дог','Бордоский дог','Dogue de Bordeaux',1),(72,'Хорти зі стоячими вухами','Борзые со стоячими ушами','Greyhounds with erect ears',1),(73,'Бородата коллі','Бородатая колли','Bearded Collie',1),(74,'Босерон','Босерон','Beauceron',1),(75,'Боснійська грубошерста гонча','Боснийская грубошерстная гончая','Bosnian coarse-wooled Hound',1),(76,'Бостонський тер\'єр','Бостонский терьер','Boston Terrier',1),(77,'Брандл-бракк','Брандл-бракк','Brundle Brakk',1),(78,'Бріар','Бриар','Briard',1),(79,'Брюссельський гріффон','Брюссельский гриффон','Brussels Griffon',1),(80,'Бульмастиф','Бульмастиф','Bullmastiff',1),(81,'Бультер\'єр','Бультерьер','Bullterrier',1),(82,'Вандейский бассет-гріффон','Вандейский бассет-гриффон','Basset Griffon Vendee',1),(83,'Вельштерьер','Вельштерьер','Velshterer',1),(84,'Угорська вівчарка','Венгерская овчарка','Hungarian sheepdog',1),(85,'Угорська кувач','Венгерский кувач','Hungarian kuvach',1),(86,'Вестготськая собака','Вестготская собака','West Gothic Dog',1),(87,'Вестфальський ТАКСОПОДІБНИЙ бракк','Вестфальский таксообразный бракк','Westphalian taksoobrazny Brakk',1),(88,'Висловухі хорти','Вислоухие борзые','Fold Greyhounds',1),(89,'Вовчий шпіц','Волчий шпиц','Wolf Spitz',1),(90,'Вольпино','Вольпино','Volpino',1),(91,'Східноєвропейська вівчарка','Восточноевропейская овчарка','East European Shepherd',1),(92,'Видрова гонча','Выдровая гончая','Otter hound',1),(93,'Гаванський бишон','Гаванский бишон','Havana Bichon',1),(94,'Гамільтонстеваре','Гамильтонстеваре','Gamiltonstevare',1),(95,'Ганноверська гонча','Ганноверская гончая','Hanover Hound',1),(96,'Герта-пойнтер','Герта-пойнтер','Gert-Pointer',1),(97,'Гладкошерстий ретрівер','Гладкошерстный ретривер','Smooth Retriever',1),(98,'Глен-оф-Имаал-тер\'єр','Глен-оф-имаал-терьер','Glen-of-imaal Terrier',1),(99,'Голий собака інків','Голая собака инков','Inca Hairless Dog',1),(100,'Голландська вівчарка','Голландская овчарка','Dutch Shepherd Dog',1),(101,'Блакитний гасконський бассет','Голубой гасконский бассет','Blue Gascony Basset',1),(102,'Блакитний Пікардійська епаньоль','Голубой пикардийский эпаньоль','Blue Picardie epanol',1),(103,'Гонча Плотта','Гончая Плотта','Hound Plotta',1),(104,'Гонча Стефана','Гончая Стефана','Hound Stephen',1),(105,'Гонча Шиллера','Гончая Шиллера','Schiller Hound',1),(106,'гончаи','Гончие','Canes',1),(107,'Гірська гонча','Горная гончая','Mountain Hound',1),(108,'Готтентотскіе собака','Готтентотская собака','Hottentot dog',1),(109,'Грейхаунд','Грейхаунд','Greyhound',1),(110,'Грецька заяча гонча','Греческая заячья гончая','Greek rabbit hound',1),(111,'Гриффон','Гриффон','Griffon',1),(112,'Далматин','Далматин','Dalmatian',1),(113,'Датська таксообразна гонча','Датская таксообразная гончая','Danish Dachsbracke',1),(114,'Данська брохольмер','Датский брохольмер','Danish broholmer',1),(115,'Денді-динмонт-тер\'єр','Денди-динмонт-терьер','Dandie Dinmont Terrier',1),(116,'Джек-рассел-тер\'єр','Джек-рассел-терьер','Jack Russell Terrier',1),(117,'Дінго','Динго','Dingo',1),(118,'Дірхаунд','Дирхаунд','Deerhound',1),(119,'Доберман','Доберман','Doberman',1),(120,'Древер','Древер','Drever',1),(121,'Дункер','Дункер','Dunker',1),(122,'Єнотова гонча','Енотовая гончая','Raccoon hound',1),(123,'Жовта з чорною маскою південна гонча','Желтая с черной маской южная гончая','Yellow with a black mask southern hound',1),(124,'Західносибірська лайка','Западносибирская лайка','West Siberian Laika',1),(125,'Зауерландського бракк','Зауэрландский бракк','Zauerlandsky Brakk',1),(126,'Золотистий ретрівер','Золотистый ретривер','Golden Retriever',1),(127,'Иллірійська вівчарка','Иллирийская овчарка','Illyria Shepherd',1),(128,'Ірландський водяний спанієль','Ирландский водяной спаниель','Irish Water Spaniel',1),(129,'Ірландський вовкодав','Ирландский волкодав','Irish Wolfhound',1),(130,'Ірландський сетер','Ирландский сеттер','Irish Setter',1),(131,'Ірландський тер\'єр','Ирландский терьер','Irish Terrier',1),(132,'Ісландська вівчарка','Исландская овчарка','Icelandic Sheepdog',1),(133,'Іспанська гальго','Испанский гальго','Spanish galgo',1),(134,'Іспанська мастиф','Испанский мастиф','Spanish Mastiff',1),(135,'Істрійська гонча','Истрийская гончая','Istrian Hound',1),(136,'Італійська гонча','Итальянская гончая','Italian Hound',1),(137,'Італійська левретка','Итальянская левретка','Italian whippet',1),(138,'Йемтхунд','Йемтхунд','Yemthund',1),(139,'Йоркшіртерьер','Йоркширтерьер','Yorkshirterer',1),(140,'Кавказька вівчарка','Кавказская овчарка','Caucasian Shepherd',1),(141,'Кай','Кай','Kai',1),(142,'Камчатська їздова','Камчатская ездовая','Kamchatka sled',1),(143,'Канаанская собака','Канаанская собака','Kanaanskaya dog',1),(144,'Карело-фінська лайка','Карело-финская лайка','Karelian-Finnish Laika',1),(145,'Карельська ведмежа собака','Карельская медвежья собака','Karelian Bear Dog',1),(146,'Карликова чубата собачка','Карликовая хохлатая собачка','Dwarf crested dog',1),(147,'Карликовий пінчер','Карликовый пинчер','Miniature Pinscher',1),(148,'Карликовий пудель','Карликовый пудель','Dwarf poodle',1),(149,'Карликовий шпіц','Карликовый шпиц','Dwarf Spitz',1),(150,'Каталонська вівчарка','Каталонская овчарка','Catalan Sheepdog',1),(151,'Кеесхонд','Кеесхонд','Keeshond',1),(152,'Келпи','Келпи','Kelpie',1),(153,'Кернтерьер','Кернтерьер','Kernterer',1),(154,'Керрі-бігль','Керри-бигль','Kerry Beagle',1),(155,'Керрі-блю-тер\'єр','Керри-блю-терьер','Kerry Blue Terrier',1),(156,'Кисю','Кисю','Kisyu',1),(157,'Китайський шарпей','Китайский шарпей','Chinese Shar',1),(158,'Клумбер-спаніель','Клумбер-спаниель','Klumber Spaniel',1),(159,'Коікерхондье','Коикерхондье','Koikerhonde',1),(160,'Лабрадор','Лабрадор','Labrador',1),(161,'Лабрадорскій ретрівер','Лабрадорский ретривер','Labrador Retriever',1),(162,'Ландсир','Ландсир','Landsir',1),(163,'Лапландский шпіц','Лапландский шпиц','Lapland spitz',1),(164,'Латвійська гонча','Латвийская гончая','Latvian Hound',1),(165,'Лауфхунд','Лауфхунд','Laufhund',1),(166,'Левеск','Левеск','Levesque',1),(167,'Лейклендтерьер','Лейклендтерьер','Leyklendterer',1),(168,'Леонбергер','Леонбергер','Leonberger',1),(169,'Леопардова гонча','Леопардовая гончая','Leopard Hound',1),(170,'Леопардовий собака катахула','Леопардовая собака Катахулы','Leopard dog Katahuly',1),(171,'Литовська гонча','Литовская гончая','Lithuanian Hound',1),(172,'Лхасский апсо','Лхасский апсо','Lhasa apso',1),(173,'Люцернер лауфхунд','Люцернер лауфхунд','Lyutserner laufhund',1),(174,'Мадяр-агар','Мадьяр-агар','Magyar agar',1),(175,'Мала англо-французька гонча','Малая англо-французская гончая','Low Anglo-French Hound',1),(176,'Малий вандейский бассет-гріффон','Малый вандейский бассет-гриффон','Small Basset Griffon Vendee',1),(177,'Мальтійська болонка','Мальтийская болонка','Maltese',1),(178,'Манчестерський тер\'єр','Манчестерский терьер','Manchester Terrier',1),(179,'Маремма','Маремма','Maremma',1),(180,'Мастино неаполитано','Мастино неаполитано','Mastino Neapolitana',1),(181,'Мастиф','Мастиф','Mastiff',1),(182,'Міттельшнауцер','Миттельшнауцер','Schnauzer',1),(183,'Мопс','Мопс','Pug',1),(184,'Московська сторожова','Московская сторожевая','Moscow watch',1),(185,'Московський тойтер\'єр','Московский тойтерьер','Moscow Toy',1),(186,'Муді','Муди','Moody',1),(187,'Мягкошерстний пшеничний тер\'єр','Мягкошерстный пшеничный терьер','Soft Coated Wheaten Terrier',1),(188,'Неаполітанський мастиф','Неаполитанский мастиф','Neapolitan Mastiff',1),(189,'Німецька грубошерста лягава','Немецкая грубошерстная легавая','German coarse-wooled pointer',1),(190,'Німецька довгошерста лягава','Немецкая длинношерстная легавая','German longhaired pointer',1),(191,'Німецька вівчарка','Немецкая овчарка','German Shepherd',1),(192,'Німецький дог','Немецкий дог','German Dog',1),(193,'Німецький мисливський тер\'єр','Немецкий охотничий терьер','German hunting terrier',1),(194,'Ненецька оленегонних лайка','Ненецкая оленегонная лайка','Nenets Laika olenegonnaya',1),(195,'Новошотландський ретрівер','Новошотландский ретривер','Novoshotlandsky Retriever',1),(196,'Норботтен-шпіц','Норботтен-шпиц','Norrbotten Spitz -',1),(197,'Норвезька бухунд','Норвежский бухунд','Norwegian buhund',1),(198,'Норвічтерьер','Норвичтерьер','Norvichterer',1),(199,'Норфолктерьер','Норфолктерьер','Norfolkterer',1),(200,'Ньюфаундленд','Ньюфаундленд','Newfoundland',1),(201,'Ойразір','Ойразир','Oyrazir',1),(202,'Оттерхаунд','Оттерхаунд','Otterhaund',1),(203,'Палевий бретонська бассет-гріффон','Палевый бретонский бассет-гриффон','Fawn Breton Basset Griffon',1),(204,'Папільон і фален','Папильон и фален','Papillon and Fahlen',1),(205,'Пекінес','Пекинес','Pekingese',1),(206,'Перуанська орхідея інків','Перуанская орхидея инков','Peruvian Inca Orchid',1),(207,'Пікардійська вівчарка','Пикардийская овчарка','Picardie Shepherd',1),(208,'Пінчер арлекін','Пинчер арлекин','Harlequin Pinscher',1),(209,'Піренейський гірський собака','Пиренейская горная собака','Pyrenean mountain dog',1),(210,'Піренейська вівчарка','Пиренейская овчарка','Pyrenean Shepherd',1),(211,'Піренейський мастиф','Пиренейский мастиф','Pyrenean Mastiff',1),(212,'Пойнтер','Пойнтер','Pointer',1),(213,'Польська низинна вівчарка','Польская низинная овчарка','Polish lowland sheepdog',1),(214,'Польська подгалянска вівчарка','Польская подгалянская овчарка','Polish podgalyanskaya Shepherd',1),(215,'Португальська гонча','Португальская гончая','Portuguese Hound',1),(216,'Португальська гірська вівчарка','Португальская горная овчарка','Portuguese Mountain Shepherd',1),(217,'Прямошерстний ретрівер','Прямошерстный ретривер','Pryamosherstny Retriever',1),(218,'Пуатвен','Пуатвен','Puatven',1),(219,'Пудель','Пудель','Poodle',1),(220,'Пудель-пойнтер','Пудель-пойнтер','Poodle-Pointer',1),(221,'Кулі','Пули','Bullets',1),(222,'Пумі','Пуми','Pumi',1),(223,'Растреадор Бразилейро','Растреадор бразилейру','Rastreador Brasileiro',1),(224,'Рафейро ду Алентехо','Рафейру ду Алентехо','Rafeyru do Alentejo',1),(225,'Риджбек','Риджбек','Ridgeback',1),(226,'Ризеншнауцер','Ризеншнауцер','Giant',1),(227,'Ротвейлер','Ротвейлер','Rottweiler',1),(228,'Румунська вівчарка','Румынская овчарка','Romanian Shepherd',1),(229,'Російський хорт','Русская борзая','Russian wolfhound',1),(230,'Російська кольорова болонка','Русская цветная болонка','Russian color spaniel',1),(231,'Російський мисливський спанієль','Русский охотничий спаниель','Russian Spaniel',1),(232,'Російський чорний тер\'єр','Русский черный терьер','Black Russian Terrier',1),(233,'Російсько-європейська лайка','Русско-европейская лайка','Russian-European Laika',1),(234,'Салюки','Салюки','Saluki',1),(235,'Самоїдська лайка','Самоедская лайка','Samoyed Laika',1),(236,'Сенбернар','Сенбернар','St. Bernard',1),(237,'Сетер-гордон','Сеттер-гордон','Gordon Setter',1),(238,'Сіліхемтерьер','Силихэмтерьер','Silihemterer',1),(239,'Скайтерьер','Скайтерьер','Skayterer',1),(240,'Скотчтерьєра','Скотчтерьер','Skotchterer',1),(241,'Слюги','Слюги','Slyugi',1),(242,'Смоландстеваре','Смоландстеваре','Smolandstevare',1),(243,'Середньоазіатська вівчарка','Среднеазиатская овчарка','Central Asian Shepherd',1),(244,'Стаффордширський бультер\'єр','Стаффордширский бультерьер','Staffordshire Bull Terrier',1),(245,'Стаффордширський тер\'єр','Стаффордширский терьер','Staffordshire Terrier',1),(246,'Суссекс-спаніель','Суссекс-спаниель','Sussex Spaniel',1),(247,'Такса','Такса','Dachshund',1),(248,'Татранська вівчарка','Татранская овчарка','Tatranska Shepherd',1),(249,'Тибетський мастиф','Тибетский мастиф','Khyi',1),(250,'Тибетський спанієль','Тибетский спаниель','Tibetan Spaniel',1),(251,'Тибетський тер\'єр','Тибетский терьер','Tibetan Terrier',1),(252,'Той-пудель','Той-пудель','Toy-poodle',1),(253,'Тоса-іну','Тоса-ину','Tosa Inu',1),(254,'Уиппет','Уиппет','Whippet',1),(255,'Уельський тер\'єр','Уэльский терьер','Wales Terrier',1),(256,'Вест-хайленд-уайт-тер\'єр','Уэст-хайленд-уайт-терьер','West Highland White Terrier',1),(257,'Фараонова собака','Фараонова собака','Pharaoh dog',1),(258,'Філд-спаніель','Филд-спаниель','Field Spaniel',1),(259,'Фландрский бувье','Фландрский бувье','Bouvier des Flanders',1),(260,'Фокстер\'єр','Фокстерьер','Fox',1),(261,'Французька болонка','Французская болонка','French spaniel',1),(262,'Французький бульдог','Французский бульдог','French Bulldog',1),(263,'Хердер','Хердер','Herder',1),(264,'Цверкшнауцер','Цверкшнауцер','Tsverkshnautser',1),(265,'Чау-чау','Чау-чау','Chow-chow',1),(266,'Чорно-підпалий кунхаунд','Черноподпалый кунхаунд','Black and tan kunhaund',1),(267,'Чесапік-бей-ретривер','Чесапик-бей-ретривер','Chesapeake Bay Retriever',1),(268,'Чеський тер\'єр','Чешский терьер','Czech Terrier',1),(269,'Чихуахуа','Чихуахуа','Chihuahua',1),(270,'Чукотська їздова','Чукотская ездовая','Chukchi sled',1),(271,'Шапендус','Шапендус','Shapendus',1),(272,'Швейцарська юрська гонча','Швейцарская юрская гончая','Swiss Jurassic Hound',1),(273,'Шелти','Шелти','Sheltie',1),(274,'Ши-тцу','Ши-тцу','Shih Tzu',1),(275,'Шипперке','Шипперке','Shipperke',1),(277,'Шотландський тер\'єр','Шотландский терьер','Scottish Terrier',1),(278,'Штірскій бракк','Штирский бракк','Shtirsky Brakk',1),(279,'Ельгхунд','Эльгхунд','Elghund',1),(280,'Ердельтер\'єр','Эрдельтерьер','Airedale',1),(281,'Ескімоська лайка','Эскимосская лайка','Husky',1),(282,'Ештрельская вівчарка','Эштрельская овчарка','Eshtrelskaya Shepherd',1),(283,'Південноросійська вівчарка','Южнорусская овчарка','South Russian Shepherd Dog',1),(284,'Японський спанієль','Японский спаниель','Japanese Spaniel',1),(285,'Японський хін','Японский хин','Japanese Chin',1),(286,'Американський довгошерстий керл','Американский длинношерстный керл ','American Curl longhair',2),(287,'Абісинська кішка','Абиссинская кошка','Abyssinian',2),(288,'Австралійська димчата кішка','Австралийская дымчатая кошка','Australian smoky cat',2),(289,'Азіатська димчаста','Азиатская дымчатая','Asian smoky',2),(290,'Азіатська таббі','Азиатская табби','Tempra',2),(291,'Американська жорсткошерста кішка','Американская жесткошерстная кошка','American haired cat',2),(292,'Американська короткошерста кішка','Американская короткошерстная кошка','American Shorthair',2),(293,'Американський бобтейл (довгошерсте)','Американский бобтейл (длинношерстный)','American Bobtail (long coat)',2),(294,'Американський бобтейл (короткошерстий)','Американский бобтейл (короткошерстный)','American Bobtail (short-haired)',2),(295,'Американський довгошерсте Рінгтейл','Американский длинношерстный Рингтейл','American longhaired Ringtail',2),(296,'Американський керл','Американский керл','American Curl',2),(297,'Американський Рінгтейл','Американский Рингтейл ','American Ringtail',2),(298,'Аналостанка Королевська','Аналостанка Королевская','Royal Analostanka',2),(299,'Анатолійське кішка (Турецька короткошерста)','Анатолийская кошка (Турецкая короткошерстная)','Anatolian Shepherd Dog (Turkish Shorthair)',2),(300,'Ангорская кішка (яванез, довгошерста орієнтал','Ангорская кошка (яванез, длинношерстная ориен','Angora cat (yavanez, oriental longhair)',2),(301,'Аравійський мау - тимчасово визнана порода','Аравийский мау - временно признанная порода','Arabian Mau - provisionally recognized breed',2),(302,'Балінезійська кішка (балінез)','Балинезийская кошка (балинез)','Balinese cat (Balinese)',2),(303,'Бамбіно, експериментальна порода','Бамбино, эксперементальная порода','Bambino, an experimental rock',2),(304,'Бенгальська кішка (бенгалі)','Бенгальская кошка (бенгал)','Bengal (Bengal)',2),(305,'Бірма','Бирма','Burma',2),(306,'Бобтейл','Бобтэйл','Bobteyl',2),(307,'Богемський рекс','Богемский рекс','Bohemian Rex',2),(308,'Бомбей','Бомбей','Bombay',2),(309,'Бразильська короткошерста','Бразильская короткошерстная','Brazilian Shorthair',2),(310,'Брістоль','Бристоль','Bristol',2),(311,'Британська довгошерста кішка','Британская длинношёрстная кошка ','British Longhair',2),(312,'Британська короткошерста кішка','Британская короткошерстная кошка','British Shorthair',2),(313,'Брембл','Брэмбл','Bramble',2),(314,'Бурма американська','Бурма американская','Burma U.S.',2),(315,'Бурма європейська','Бурма европейская','Burma European',2),(316,'Бурмілла','Бурмилла','Burmilla',2),(317,'Бурмілла довгошерста','Бурмилла длинношерстная','Burmilla Longhair',2),(318,'Бурмуар','Бурмуар','Burmuar',2),(319,'Відень Вудс або Венсікй ліс (VIENNA WOODS)','Вена Вудс или Венсикй лес (VIENNA WOODS)','Vienna Woods or Vensiky forest (VIENNA WOODS)',2),(320,'Східні кішки','Восточные кошки','Oriental cats',2),(321,'Гавана-браун','Гавана-браун','Havana Brown',2),(322,'Герман-рекс (Німецька рекс, блакитний теббі)','Герман-рекс (Немецкий рекс, голубой тэбби)','German Rex (German Rex, blue tabby)',2),(323,'Гімалайський кіт','Гималайская кошка','Himalayan cat',2),(324,'Двельф','Двэльф','Dvelf',2),(325,'Девон-рекс','Девон-рекс','Devon Rex',2),(326,'Домашня короткошерста кішка','Домашняя короткошерстная кошка','Domestic shorthair cat',2),(327,'Європейська бурманских','Европейская бурманская','European burmanskaya',2),(328,'Європейська короткошерста кішка','Европейская короткошерстная кошка ','European Shorthair cat',2),(329,'Єгипетська мау','Египетская мау','Egyptian',2),(330,'Ірімото','Иримото ','Irimoto',2),(331,'Йоркська шоколадна кішка','Йоркская шоколадная кошка','York Chocolate Cat',2),(332,'Каліфорнійська спенглед (сяюча)','Калифорнийская спенглед (сияющая)','California spengled (shining)',2),(333,'Камишева кіт','Камышевый кот','Reed cat',2),(334,'Канаане','Канаани','Canaan',2),(335,'Канадський сфінкс','Канадский сфинкс','Sphynx',2),(336,'Карельський бобтейл','Карельский бобтейл ','Karelian Bobtail',2),(337,'Каумені','Каумэни','Kaumeni',2),(338,'Кимрик','Кимрик','Kimrik',2),(339,'Кінкелоу','Кинкелоу ','Kinkelou',2),(340,'Копер (мідна) кішка','Копер (медная) кошка','Koper (copper) cat',2),(341,'Корат','Корат','Korat',2),(342,'Корніш рекс','Корниш рекс','Cornish Rex',2),(343,'Курильський бобтейл','Курильский бобтейл','Kuril Bobtail',2),(344,'Курильський довгошерсте бобтейл','Курильский длинношерстный бобтейл','Kuril Bobtail longhair',2),(345,'Ла Перм - нова порода кішок','Ла Перм – новая порода кошек','La Perm - a new breed of cat',2),(346,'Лаперм короткошерстий','Лаперм короткошерстный','Laperm shorthair',2),(347,'Лемкін','Лэмкин','Lemkin',2),(348,'Мандалай','Мандалай','Mandalay',2),(349,'Манкс (менській безхвоста кішка)','Манкс (мэнская бесхвостая кошка)','Manx (tailless Manx cat)',2),(350,'Манчкін (довгошерсте)','Манчкин (длинношерстный)','Munchkin (long coat)',2),(351,'Манчкін (короткошерстий)','Манчкин (короткошерстный)','Munchkin (short-haired)',2),(352,'Меконгській бобтейл','Меконгский бобтейл','Mekong Bobtail',2),(353,'Минскин','Минскин','Minskin',2),(354,'Мейн кун','Мэйн кун','Maine Coon',2),(355,'Наполеон','Наполеон','Napoleon',2),(356,'Невська маскарадна','Невская маскарадная ','Neva Masquerade',2),(357,'Німецький рекс','Немецкий рекс ','German Rex',2),(358,'Нібелунг','Нибелунг','Nibelung',2),(359,'Норвезька лісова кішка','Норвежская лесная кошка','Norwegian Forest Cat',2),(360,'Орієнтальна короткошерста кішка','Ориентальная короткошерстная кошка','Oriental Shorthair',2),(361,'Орієнтальна напівдовгошерста','Ориентальная полудлинношёрстная','Oriental SLH',2),(362,'Охос азулес (голубоглазка)','Охос азулес (голубоглазка)','Ojos azules (Goluboglazka)',2),(363,'Охос азулес довгошерсте','Охос азулес длинношерстный','Ojos azules longhair',2),(364,'Оцікет','Оцикэт','Ocicat',2),(365,'Пантеретта','Пантеретта ','Panteretta',2),(366,'Персидська довгошерста кішка','Персидская длинношерстная кошка','Persian Longhair',2),(367,'Персидська димчата','Персидская дымчатая','Persian smoky',2),(368,'Персидська червона мармурова','Персидская красная мраморная','Persian red marble',2),(369,'Персидська порода кішок','Персидская порода кошек ','The Persian cat breed',2),(370,'Персидська сріблясто-блакитна мармурова','Персидская серебристо-голубая мраморная','Persian silver-blue marble',2),(371,'Персидська черепаховая з білим','Персидская черепаховая с белым','Persian tortie with white',2),(372,'Персидська чорна мармурова','Персидская черная мраморная','Persian black marble',2),(373,'Персидська шиншила','Персидская шиншилла','Persian chinchilla',2),(374,'Петерболд, Петербурзький Сфінкс','Петерболд, Петербургский Сфинкс','Peterbald, Petersburg Sphinx',2),(375,'Піксібоб (довгошерсте)','Пиксибоб (длинношерстный)','Piksibob (long coat)',2),(376,'Піксібоб - нова порода кішок','Пиксибоб - новая порода кошек','Piksibob - a new breed of cat',2),(377,'Пуделькет','Пуделькэт','Pudelket',2),(378,'Рагамаффін','Рагамаффин','RagaMuffin',2),(380,'Російська блакитна кішка','Русская голубая кошка','Russian Blue Cat',2),(381,'Регдолл','Рэгдолл','Ragdoll',2),(382,'Cфінкс (канадська безшерста)','Cфинкс (канадская бесшерстная) ','Cfinks (Canadian Hairless)',2),(383,'Саванна','Саванна','Savanna',2),(384,'Сафарі','Сафари','Safari',2),(385,'Селкирк рекс','Селкирк рекс','Selkirk Rex',2),(386,'Селкирк-рекс довгошерстий','Селкирк-рекс длинношерстный','Selkirk Rex longhair',2),(387,'Серенгеті','Серенгети','Serengeti',2),(388,'Сіамська кішка','Сиамская кошка','Siamese',2),(389,'Сибірська кішка','Сибирская кошка','Siberian cat',2),(390,'Сінгапуру','Сингапура','Singapore',2),(391,'Скоттиш фолд','Скоттиш фолд','Scottish Fold',2),(392,'Скоттиш фолд (Шотландська висловуха) довгошер','Скоттиш фолд (Шотландская вислоухая) длинноше','Scottish Fold (Scottish Fold) Longhair',2),(393,'Скукум','Скукум ','Skukum',2),(394,'Сноу шу (белоснежка)','Сноу шу (белоснежка)','Snow shu (Snow White)',2),(395,'Сомалійська дикого забарвлення','Сомалийская дикого окраса','Somali wild color',2),(396,'Сомалійські кішки','Сомалийские кошки','Somali cats',2),(397,'Соукок (Кенійська лісова кішка)','Соукок (Кенийская лесная кошка)','Soukok (Kenya Forest Cat)',2),(398,'Тайська кішка','Тайская кошка','Thai',2),(399,'Теннесі-рекс','Теннеси-рекс','Tennessee Rex',2),(400,'Тіффані: порода кішок','Тиффани: порода кошек','Tiffany: breed cats',2),(401,'Тойбоб (Скіф-Тай-Дон, Скіф-Той-Боб)','Тойбоб (Скиф-Тай-Дон, Скиф-Той-Боб)','Toybob (Skiff Tai Don, Skiff Toybob)',2),(402,'Тойгер','Тойгер','Toyger',2),(403,'Тонкинская кішка, Токінез','Тонкинская кошка, Токинез ','Tonkinese cat Tokinez',2),(404,'Турецька ангора','Турецкая ангора','Turkish Angora',2),(405,'Турецька ван','Турецкий ван','Turkish Van',2),(406,'Український левкой','Украинский левкой','Ukrainian gillyflower',2),(407,'Уссурійська кішка (Уссурі)','Уссурийская кошка (Уссури)','Ussuri cat (Ussuri)',2),(408,'Флеппіг','Флэппиг','Fleppig',2),(409,'Форін вайт','Форин вайт','Foreign White',2),(410,'Хайленд фолд','Хайленд фолд ','Highland Fold',2),(411,'Хайлендер','Хайлендер','Highlander',2),(412,'Хайлендер довгошерстий','Хайлендер длинношерстный','Highlander longhaired',2),(413,'Цейлонська кішка','Цейлонская кошка','Ceylon cat',2),(414,'Чаус (хауса, ШАУЗ)','Чауси (хауси, шаузи)','Xaus (Hausa, shauzi)',2),(415,'Черноногая кішка','Черноногая кошка','Blackfoot cat',2),(416,'Чіто','Чито','Chito',2),(417,'Шантіллі','Шантилли','Chantilly',2),(418,'Шартрез (картезіанська порода кішок)','Шартрез (картезианская порода кошек)','Chartreuse (Cartesian breed cats)',2),(419,'Шиншила золотиста екзотична короткошерста','Шиншилла золотистая экзотическая короткошерст','Chinchilla Golden exotic shorthair',2),(420,'Екзотична короткошерста кішка','Экзотическая короткошерстная кошка','Exotic Shorthair',2),(421,'Ельф','Эльф','Elf',2),(422,'Яванез','Яванез','Yavanez',2),(423,'Японський бобтейл','Японский бобтейл','Japanese Bobtail',2),(424,'Японський довгошерсте бобтейл','Японский длинношерстный бобтейл','Japanese Bobtail longhair',2),(425,'Сибірський хаскі','Сибирский хаски','Siberian husky',1),(426,'Не визначено','Не известно','None',1),(427,'Відсутня','Отсутствует','No',2),(604,'tXejdS5bBh',NULL,'N0gzAZjSxL',1),(606,'kEKZcPQyao',NULL,'k6c0ekbDvs',1),(608,'z5AISPInHN',NULL,'dBNt2oxbvm',1),(610,'hawBxlQg7e',NULL,'ClGqRMmkc0',1),(612,'',NULL,NULL,3),(613,NULL,NULL,'',3),(614,NULL,NULL,'',3),(615,NULL,NULL,'',3),(616,NULL,NULL,'',3),(617,NULL,NULL,'',3),(618,'BGUbljdAgW',NULL,'PB88azyTpz',1),(620,'P1RV895J1q',NULL,'t3SszmhSTm',1);
/*!40000 ALTER TABLE `animalbreeds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `animalowners`
--

DROP TABLE IF EXISTS `animalowners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animalowners` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Ім''я власника',
  `surname` varchar(45) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Прізвище власника',
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Телефон власника',
  `address` varchar(45) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Адреса власника',
  `description` varchar(120) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Додаткова інформація про власника',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Власники тварин';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animalowners`
--

LOCK TABLES `animalowners` WRITE;
/*!40000 ALTER TABLE `animalowners` DISABLE KEYS */;
/*!40000 ALTER TABLE `animalowners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `animalrequestforadoption`
--

DROP TABLE IF EXISTS `animalrequestforadoption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animalrequestforadoption` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Ключ',
  `userId` int(11) NOT NULL COMMENT 'Код користувача, що відправив запит',
  `animalId` int(11) NOT NULL COMMENT 'Код тварини',
  `description` varchar(60) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Інформація від користувача',
  PRIMARY KEY (`ID`),
  KEY `userd_idx` (`userId`),
  KEY `animalId_idx` (`animalId`),
  KEY `adopuserd_idx` (`userId`),
  KEY `adopanimalId_idx` (`animalId`),
  CONSTRAINT `fkAdopAnimalId` FOREIGN KEY (`animalId`) REFERENCES `animals` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkAdopUserId` FOREIGN KEY (`userId`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Запити користувачів на адопцію';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animalrequestforadoption`
--

LOCK TABLES `animalrequestforadoption` WRITE;
/*!40000 ALTER TABLE `animalrequestforadoption` DISABLE KEYS */;
/*!40000 ALTER TABLE `animalrequestforadoption` ENABLE KEYS */;
UNLOCK TABLES;

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
  `breed` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Порода',
  `transpNumber` varchar(15) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Номер мікрочіпа',
  `tokenNumber` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Номер бірки',
  `dateOfRegister` date NOT NULL COMMENT 'Дата реєстрації',
  `dateOfBirth` date DEFAULT NULL COMMENT 'Дата народження',
  `dateOfSterilization` date DEFAULT NULL COMMENT 'Дата стерилізації',
  `color` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT 'Окрас',
  `userId` int(11) DEFAULT NULL COMMENT 'Код реєстратора',
  `address` varchar(120) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Адреса перебування тварини',
  `isActive` tinyint(1) DEFAULT NULL COMMENT 'Статус (жива/ні)',
  `image` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Шлях до фотографії',
  `serviceId` int(11) NOT NULL COMMENT 'Код сервісу',
  `description` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Опис (примітки)',
  `dateOfTwitter` date DEFAULT NULL COMMENT 'Дата публікації на Twitter',
  `dateOfFacebook` date DEFAULT NULL COMMENT 'Дата публікації на Facebook',
  `ownerId` int(11) DEFAULT NULL COMMENT 'Код власника тварини (якщо відрізняється від userId)',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `transpNumber_UNIQUE` (`transpNumber`),
  KEY `fkKind_idx` (`typeId`),
  KEY `fkServices_idx` (`serviceId`),
  KEY `fkUser_idx` (`userId`),
  KEY `fkOwner_idx` (`ownerId`),
  CONSTRAINT `fkOwner` FOREIGN KEY (`ownerId`) REFERENCES `animalowners` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkServices` FOREIGN KEY (`serviceId`) REFERENCES `animalservices` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkType` FOREIGN KEY (`typeId`) REFERENCES `animaltypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkUser` FOREIGN KEY (`userId`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=612 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Перелік тварин';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animals`
--

LOCK TABLES `animals` WRITE;
/*!40000 ALTER TABLE `animals` DISABLE KEYS */;
INSERT INTO `animals` VALUES (71,'FEMALE',2,'MIDDLE','NONE','286','2542542',NULL,'2015-03-14',NULL,NULL,'',75,'м.Львів, Чорновола 25/3',1,'images/20150928055901150.jpg',3,'dafsfasd afsdfadsfad віфаівівафа','2011-04-10','2015-08-29',NULL),(78,'MALE',2,'NONE','NONE',NULL,'4578345',NULL,'2015-01-11',NULL,NULL,'',75,'м.Львів, Чорновола 25/3',1,NULL,3,NULL,'2015-08-29','2011-04-10',NULL),(80,'MALE',2,'SMALL','NONE',NULL,'4253889',NULL,'2015-01-22',NULL,NULL,'',270,'м.Львів, Чорновола 25/3',1,NULL,3,NULL,'2011-04-10','2011-04-10',NULL),(81,'FEMALE',2,'LARGE','NONE',NULL,'2452437',NULL,'2015-07-08',NULL,NULL,'',75,'м.Львів, Чорновола 25/3',1,NULL,3,NULL,'2015-08-29','2015-08-29',NULL),(83,'NONE',2,'SMALL','NONE',NULL,'4524873',NULL,'2015-07-09',NULL,NULL,'',75,'м.Львів, Чорновола 25/3',1,NULL,3,NULL,'2015-08-29','2015-08-29',NULL),(84,'FEMALE',2,'MIDDLE','NONE',NULL,'4528783',NULL,'2015-02-18',NULL,NULL,'',75,'м.Львів, Чорновола 25/3',1,NULL,3,NULL,'2011-04-10','2011-04-10',NULL),(85,'MALE',2,'SMALL','NONE',NULL,'452783783',NULL,'2015-02-23',NULL,NULL,'',75,'м.Львів, Чорновола 25/3',1,NULL,3,NULL,'2015-09-04','2015-09-04',NULL),(86,'FEMALE',2,'SMALL','NONE',NULL,'452843524',NULL,'2015-05-30',NULL,NULL,'',75,'м.Львів, Чорновола 25/3',1,NULL,3,NULL,'2015-09-04','2015-09-08',NULL),(87,'MALE',2,'MIDDLE','NONE',NULL,'4525638',NULL,'2015-06-17',NULL,NULL,'',75,'м.Львів, Чорновола 25/3',1,NULL,1,NULL,'2015-09-04','2011-04-10',NULL),(88,'FEMALE',2,'SMALL','NONE',NULL,'24537897',NULL,'2015-08-02',NULL,NULL,'',75,'м.Львів, Чорновола 25/3',1,NULL,3,NULL,'2015-09-04','2015-09-09',NULL),(89,'MALE',2,'LARGE','NONE',NULL,'254324378',NULL,'2015-06-14',NULL,NULL,'',75,'м.Львів, Чорновола 25/3',1,NULL,1,NULL,'2015-09-23','2015-09-23',NULL),(198,'MALE',4,'SMALL','NONE',NULL,NULL,NULL,'2015-08-17','2011-06-08',NULL,'Черпачний(коричнева)',NULL,'йуцййуц віваф уйцуйу 76000',1,NULL,3,'','2015-09-09','2011-04-10',NULL),(199,'MALE',9,'SMALL','CITES2',NULL,NULL,NULL,'2015-08-17','2015-08-06',NULL,'Чорний',NULL,'wqeqw ewqeeqwe qewweq eqweq',1,NULL,3,NULL,'2015-09-09','2015-09-09',NULL),(200,'MALE',9,'SMALL','CITES3',NULL,NULL,NULL,'2015-08-17','2013-01-31',NULL,'Чорний',NULL,'Молдова Кишинів Карпатське лісництво 65000',1,NULL,1,'Злий','2011-04-10','2015-09-24',NULL),(201,'FEMALE',2,'SMALL','CITES3','287',NULL,NULL,'2015-08-17','2015-06-03',NULL,'Коричневий',NULL,'Україна Львів Хмельницька 2а 60005',1,NULL,3,NULL,'2015-09-23',NULL,NULL),(202,'MALE',9,'SMALL','NONE',NULL,NULL,NULL,'2015-08-17','2015-07-30',NULL,'Плямистий',NULL,'dasas dasd dasd dasdqas',1,NULL,2,'adfsfasd','2015-09-23',NULL,NULL),(204,'MALE',4,'MIDDLE','CITES1',NULL,NULL,NULL,'2015-08-17','2015-07-31',NULL,'Рябий',NULL,'dssa qweq eqweq eqweqw',1,NULL,3,NULL,'2015-09-23',NULL,NULL),(205,'FEMALE',4,'MIDDLE','NONE',NULL,NULL,NULL,'2015-08-18','2015-08-07',NULL,'Рудий',NULL,'ewqe ewqeq eqwe ewqeq',1,NULL,2,'sdfsdfsdfsdf','2015-09-23',NULL,NULL),(425,'FEMALE',1,'LARGE',NULL,NULL,NULL,NULL,'2015-09-16',NULL,NULL,'9WFTUcHhbW',NULL,'e6Q0haQGpd',NULL,NULL,3,NULL,'2015-09-23',NULL,NULL),(426,'FEMALE',1,'LARGE',NULL,NULL,NULL,NULL,'2015-09-16',NULL,NULL,'5SSNTExYvO',NULL,'i0GeOGN9X6',NULL,NULL,3,NULL,'2015-09-23',NULL,NULL),(427,'FEMALE',1,'LARGE',NULL,NULL,NULL,NULL,'2015-09-16',NULL,NULL,'vYJqHuIBcD',NULL,'OF0U9HiIjr',NULL,NULL,3,NULL,'2015-09-23',NULL,NULL),(428,'FEMALE',1,'LARGE',NULL,NULL,NULL,NULL,'2015-09-16',NULL,NULL,'4zFxodr6wB',NULL,'B7xux2SvmL',NULL,NULL,3,NULL,'2015-09-23',NULL,NULL),(430,'FEMALE',1,'LARGE',NULL,NULL,NULL,NULL,'2015-09-16',NULL,NULL,'jsL3AKgXZU',270,'2lZUk3qvXx',NULL,NULL,3,NULL,'2015-09-23','2015-09-16',NULL),(451,'FEMALE',1,'LARGE',NULL,NULL,NULL,NULL,'2015-09-16',NULL,NULL,'UqIdA7apPK',NULL,'E39Ava9b7m',NULL,NULL,3,NULL,'2015-09-23',NULL,NULL),(474,'MALE',2,'MIDDLE','NONE','319',NULL,NULL,'2015-09-17','2015-09-20',NULL,'Тигровий',175,'dfsd eqwe asdsa ',1,NULL,3,NULL,'2015-09-23',NULL,NULL),(483,'FEMALE',1,'LARGE',NULL,NULL,NULL,NULL,'2015-09-17',NULL,NULL,'XJ1jJ8N4TZ',NULL,'aHss4Bsuem',NULL,NULL,3,NULL,'2015-09-23',NULL,NULL),(484,'FEMALE',1,'LARGE',NULL,NULL,NULL,NULL,'2015-09-17',NULL,NULL,'0dm4ijuE0M',NULL,'sH6glgmgBa',NULL,NULL,3,NULL,'2015-09-23',NULL,NULL),(485,'FEMALE',1,'LARGE',NULL,NULL,NULL,NULL,'2015-09-17',NULL,NULL,'s2NuI9N6Ge',NULL,'arcIYSuRO6',NULL,NULL,3,NULL,'2015-09-23',NULL,NULL),(486,'FEMALE',1,'LARGE',NULL,NULL,NULL,NULL,'2015-09-17',NULL,NULL,'M11wykr6Nb',NULL,'Cem4PZT6lx',NULL,NULL,3,NULL,NULL,NULL,NULL),(487,'FEMALE',1,'LARGE',NULL,NULL,NULL,NULL,'2015-09-17',NULL,NULL,'5BhR4Kr4zp',NULL,'8wOrnhMlyX',NULL,NULL,3,NULL,'2015-09-23','2015-09-23',NULL),(516,'MALE',2,'SMALL','CITES1','287',NULL,NULL,'2015-09-18','2015-09-06','2015-08-31','Red',175,'test test test ',1,NULL,4,NULL,'2015-09-23','2015-09-23',NULL),(517,'FEMALE',1,'SMALL','CITES1','33',NULL,NULL,'2015-09-18','2015-09-04',NULL,'Brown',175,'test test estt ',1,NULL,4,NULL,NULL,'2015-09-23',NULL),(527,'MALE',2,'MIDDLE','CITES1','291',NULL,NULL,'2015-09-18','2015-09-01','2015-09-01','White',175,'jjgyfjgf jgkuiki gjgjgjgj ',1,NULL,4,'gjjggjgj','2015-09-23','2015-09-23',NULL),(528,'MALE',2,'SMALL','CITES1','289',NULL,NULL,'2015-09-18','2015-08-31','2015-08-31','Tri-color',175,'jjjjj jjjj jn,n, ',1,NULL,4,NULL,'2015-09-23','2015-09-23',NULL),(532,'MALE',5,'MIDDLE','CITES2',NULL,'232345678768567','32432','2015-09-21','2015-09-15','2015-09-09','Рудий',339,'Україна Івано-Франківськ Тут же 76000',1,NULL,4,'Просто тварина','2015-09-23','2015-09-23',NULL),(533,'FEMALE',4,'LARGE','CITES3',NULL,NULL,NULL,'2015-09-21','2015-09-20',NULL,'Чорний',175,'Зімбабве хома-сіті хома-сіті ',1,NULL,4,'жопа, який злобний....','2015-09-23','2015-09-23',NULL),(534,'FEMALE',4,'MIDDLE','CITES2',NULL,NULL,NULL,'2015-09-21','2015-08-30','2015-08-30','Рудий',341,'Мадагаскар хохмачок-сіті хохмачок-сіті ',1,NULL,4,NULL,'2015-09-23','2015-09-23',NULL),(535,'MALE',1,'SMALL','CITES1',NULL,NULL,NULL,'2015-09-21','2015-09-10',NULL,'Рябий',175,'test test test ',1,NULL,2,NULL,'2015-09-23','2015-09-23',NULL),(536,'MALE',1,'MIDDLE','CITES1',NULL,NULL,NULL,'2015-09-21','2015-10-02',NULL,'Чорний',175,'test test test ',1,NULL,2,NULL,NULL,NULL,NULL),(538,'MALE',1,'SMALL','NONE',NULL,NULL,NULL,'2015-09-22','2015-09-17',NULL,'Рудий',175,'tets test test ',1,NULL,2,NULL,NULL,NULL,NULL),(539,'MALE',2,'LARGE','CITES1','411',NULL,NULL,'2015-09-22','2015-09-30',NULL,'Чорний',175,'test test test ',1,NULL,2,NULL,NULL,NULL,NULL),(543,'MALE',1,'SMALL','CITES2','506',NULL,NULL,'2015-09-22','2015-09-11',NULL,'Рябий',175,'kjbg fgdfd fdgfd ',1,NULL,2,NULL,NULL,'2015-09-24',NULL),(544,'MALE',1,'MIDDLE','CITES2','2',NULL,NULL,'2015-09-23','2015-09-17',NULL,'Рудий',175,'retre qrtre qretre ',1,NULL,2,NULL,NULL,NULL,NULL),(545,'MALE',2,'SMALL','NONE','298',NULL,NULL,'2015-09-23','2015-09-06',NULL,'Рябий',189,'test test test ',1,NULL,4,NULL,NULL,NULL,NULL),(546,'MALE',2,'MIDDLE','NONE','326',NULL,NULL,'2015-09-23','2015-09-16',NULL,'Триколірний',189,'test test tses ',1,NULL,2,NULL,NULL,NULL,NULL),(547,'MALE',2,'SMALL','CITES3','286',NULL,NULL,'2015-09-23','2015-09-10',NULL,'Вовчий',175,'test test test ',1,NULL,2,NULL,NULL,NULL,NULL),(554,'MALE',1,'SMALL','NONE','8',NULL,NULL,'2015-09-24','2015-09-10',NULL,'Рябий',175,'тест еуіе еуіе ',1,NULL,2,NULL,NULL,NULL,NULL),(557,'FEMALE',2,'MIDDLE','NONE','345',NULL,'555','2015-09-25','2015-09-27',NULL,'Arson',369,'україна івано--франківськ 760000 ',1,NULL,2,NULL,NULL,NULL,NULL),(558,'FEMALE',1,'LARGE',NULL,NULL,NULL,NULL,'2015-09-26',NULL,NULL,'cBKMu7h0XRdOYKSk9zHG',NULL,'',NULL,NULL,1,NULL,NULL,NULL,NULL),(570,'MALE',2,'SMALL','NONE','288',NULL,NULL,'2015-09-26','2015-09-03',NULL,'Рудий',175,'test test test ',1,'images/1443283401545.png',3,NULL,NULL,NULL,NULL),(571,'MALE',1,'LARGE','CITES1','5',NULL,NULL,'2015-09-26','2015-09-24',NULL,'Grey',175,'Test Test Test ',1,'images/1443286182340.png',2,NULL,NULL,NULL,NULL),(590,'MALE',2,'SMALL','NONE','289',NULL,NULL,'2015-09-26','2015-08-31',NULL,'Вовчий',493,'test test tests ',1,'images/1443298587958.png',4,NULL,NULL,NULL,NULL),(610,'FEMALE',2,'SMALL','CITES1','287',NULL,NULL,'2015-09-28','2015-09-03',NULL,'Рябий',175,'test test test ',1,'images/1443396833255.png',2,NULL,NULL,NULL,NULL),(611,'MALE',1,'MIDDLE','CITES1','2',NULL,NULL,'2015-09-28','2015-09-03',NULL,'Коричневий',175,'test test test ',1,'images/1443396884718.png',2,NULL,NULL,NULL,NULL);
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
  `serviceEn` varchar(15) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Вид сервісу English',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `service_UNIQUE` (`service`),
  UNIQUE KEY `serviceEn_UNIQUE` (`serviceEn`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Види сервісу/робіт над тваринами';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animalservices`
--

LOCK TABLES `animalservices` WRITE;
/*!40000 ALTER TABLE `animalservices` DISABLE KEYS */;
INSERT INTO `animalservices` VALUES (1,'адопція','adoption'),(2,'знайдена','found'),(3,'втрачена','lost'),(4,'власна','own');
/*!40000 ALTER TABLE `animalservices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `animalstatuses`
--

DROP TABLE IF EXISTS `animalstatuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animalstatuses` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Ключ',
  `status` varchar(45) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Назва статусу',
  `statusEn` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Можливі стани (роботи) перебування тварини';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animalstatuses`
--

LOCK TABLES `animalstatuses` WRITE;
/*!40000 ALTER TABLE `animalstatuses` DISABLE KEYS */;
INSERT INTO `animalstatuses` VALUES (10,'Вилікувана','Cured'),(11,'Обрізана','Cropped'),(12,'Дезинфікована','Disinfected'),(13,'Відпущена','Released'),(14,'Упіймана','Catched'),(15,'Верифікована','Vverified'),(16,'Вакцинована','Vaccinated'),(17,'На адопцію','In adoption'),(18,'На опікунство','In custody'),(19,'Хвора','Sick'),(20,'Новонароджена','Newborn'),(21,'Налякана','Scared'),(22,'Поранена','Injured');
/*!40000 ALTER TABLE `animalstatuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `animalstatusesloger`
--

DROP TABLE IF EXISTS `animalstatusesloger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animalstatusesloger` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Ключ',
  `statusId` int(11) NOT NULL COMMENT 'Код статусу',
  `animalId` int(11) NOT NULL COMMENT 'Ід тварини',
  `userId` int(11) NOT NULL COMMENT 'Id користувача',
  `date` datetime NOT NULL COMMENT 'Дата зміни статусу',
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Додаткова інформація',
  PRIMARY KEY (`ID`),
  KEY `statusId_idx` (`statusId`),
  KEY `fkAnimal_idx` (`animalId`),
  KEY `fkUser_idx` (`userId`),
  CONSTRAINT `fkUserst` FOREIGN KEY (`userId`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkAnimal` FOREIGN KEY (`animalId`) REFERENCES `animals` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkStatus` FOREIGN KEY (`statusId`) REFERENCES `animalstatuses` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Стани (роботи) , в/під яких перебувала тварина';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animalstatusesloger`
--

LOCK TABLES `animalstatusesloger` WRITE;
/*!40000 ALTER TABLE `animalstatusesloger` DISABLE KEYS */;
INSERT INTO `animalstatusesloger` VALUES (13,10,201,175,'2015-09-10 00:00:00','test'),(14,22,201,175,'2015-09-10 00:00:00','est'),(15,19,88,175,'2015-09-10 00:00:00','test'),(16,12,78,175,'2015-09-10 00:00:00','etsr'),(79,19,200,189,'2015-09-08 00:00:00','Важка стадія грипу'),(80,22,200,189,'2015-09-11 00:00:00','Трішки поранена'),(86,13,533,189,'2015-09-11 00:00:00','ggg'),(87,12,85,189,'2015-09-11 00:00:00','fdf'),(90,11,71,189,'2015-09-28 00:00:00','sdcadscasdc');
/*!40000 ALTER TABLE `animalstatusesloger` ENABLE KEYS */;
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
  `typeEn` varchar(15) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Вид тварини (English version)',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `kind_UNIQUE` (`type`),
  UNIQUE KEY `typeEn_UNIQUE` (`typeEn`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Види тварин';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animaltypes`
--

LOCK TABLES `animaltypes` WRITE;
/*!40000 ALTER TABLE `animaltypes` DISABLE KEYS */;
INSERT INTO `animaltypes` VALUES (1,'собака','dog'),(2,'кішка','cat'),(3,'птах','bird'),(4,'гризун','rodent'),(5,'куницеві','marten'),(6,'рептилія','replicant'),(7,'риба','fish'),(8,'кінь','horse'),(9,'ведмідь','bear');
/*!40000 ALTER TABLE `animaltypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userroles`
--

DROP TABLE IF EXISTS `userroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userroles` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Код ролі користувача',
  `userRole` varchar(11) CHARACTER SET utf8 NOT NULL COMMENT 'Назва ролі користувача',
  `userRoleEn` varchar(11) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Назва ролі користувача',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Ролі користувачів';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userroles`
--

LOCK TABLES `userroles` WRITE;
/*!40000 ALTER TABLE `userroles` DISABLE KEYS */;
INSERT INTO `userroles` VALUES (1,'moderator',''),(2,'registrator',''),(3,'guest',''),(4,'doctor','');
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
  `name` varchar(35) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Імя',
  `surname` varchar(45) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Прізвище',
  `dateOfRegistration` date NOT NULL COMMENT 'Дата реєстрації',
  `userTypeId` int(11) NOT NULL COMMENT 'Код виду користувача',
  `userRoleId` int(11) NOT NULL COMMENT 'Код ролі користувача',
  `phone` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Телефон користувача',
  `address` varchar(120) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Поштова адреса користувача',
  `email` varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Адреса е-мейл',
  `socialLogin` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Логін в Фейсбуці чи інших соц-мережах',
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Пароль',
  `organizationName` varchar(70) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Назва організації',
  `organizationInfo` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Інформація про організацію',
  `isActive` tinyint(1) DEFAULT NULL COMMENT 'Індикація блокування користувача',
  `facebookId` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Логін в Фейсбуці',
  `twitterId` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Логін в Твіттері',
  `googleId` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Логін в Гуглі',
  `socialPhoto` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Фото з соцмережі(лінк)',
  `emailVerificationString` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Код верифікації приреєстрації користувача',
  PRIMARY KEY (`ID`),
  KEY `fkUserRoles_idx` (`userRoleId`),
  KEY `fkUserKinds_idx` (`userTypeId`),
  CONSTRAINT `fkUserKinds` FOREIGN KEY (`userTypeId`) REFERENCES `usertypes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkUserRoles` FOREIGN KEY (`userRoleId`) REFERENCES `userroles` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=528 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Перелік користувачів';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (75,'Петро','Стодола','2010-04-30',2,2,'000-0000000','32','yra1d908@ukr.net','petro','PetroSyla','Ветиринарна обсерваторія','Дім Бровка',0,NULL,NULL,NULL,NULL,NULL),(175,'root','rooot','2015-08-17',1,1,'000-0000001','Franik','ifjava056@gmail.com','root','63a9f0ea7bb98050796b649e85481845','if-056','N/A',1,'118270105186473','3407817185','115696934259110998208','http://pbs.twimg.com/profile_images/536849624/4119137726_72b5f9c988.jpg',NULL),(179,'root','1frtyuip','2015-08-04',1,3,'000-0111122','N/A','rt@rt.rt','root5','28c8edde3d61a0411511d3b1866f0636','N/A','N/A',1,NULL,NULL,NULL,NULL,NULL),(187,'Yuriy','Chikh','2015-08-24',1,3,'000-0111111','Franikdsfsfsfsfs','root@root','yra1908','63a9f0ea7bb98050796b649e85481845','qqqqqqqqqqqqqqwwwqwqwqw','N/A',0,NULL,NULL,NULL,'http://pbs.twimg.com/profile_images/536849624/4119137726_72b5f9c988.jpg',NULL),(189,'Doctor','Doctor','2015-08-28',1,4,NULL,'N/A','test@test.com','doctor','f9f16d97c90d8c6f2cab37bb6d1f1992','N/A','N/A',1,NULL,NULL,NULL,NULL,NULL),(252,'Юра','Баклицький','2015-09-09',1,3,'000-9379992','N/A','yurab@email.com','Юра Баклицький','Юра Баклицький','N/A','N/A',1,NULL,NULL,NULL,'https://scontent.xx.fbcdn.net/hprofile-xap1/v/t1.0-1/s200x200/1480598_1387376791510439_15471001_n.jpg?oh=a0ce8376a57ba0e134846da22190d099&oe=56605A37',NULL),(253,'Yurii','N/A','2015-09-09',1,3,'N/A','N/A','N/A','Yurii','Yurii','N/A','N/A',1,NULL,NULL,NULL,'http://pbs.twimg.com/profile_images/440546863502991360/GpdhLQVk.jpeg',NULL),(264,'Yuriy Chikh','N/A','2015-09-09',1,3,'000-0111111','N/A','NA@na.na','Yuriy Chikh','Yuriy Chikh','N/A','N/A',1,NULL,'70100199',NULL,'http://pbs.twimg.com/profile_images/536849624/4119137726_72b5f9c988.jpg',NULL),(265,'Vitaliy  Rodymiuk','N/Axsxs','2015-09-09',2,4,'000-0000555','N/Awsx','ytr@rty.vcr','Vitaliy  Rodymiuk','Vitaliy  Rodymiuk','N/Axsa','N/Aere',1,'805448682883682',NULL,NULL,'https://scontent.xx.fbcdn.net/hprofile-xpf1/v/t1.0-1/p200x200/11900030_797618193666731_8379248966324501978_n.jpg?oh=d05104ad8f6e18b2d79e5b46f3bb0120&oe=5679580E',NULL),(267,'Chih Yuriy','N/A','2015-09-11',1,3,'N/A','N/A','N/A','Chih Yuriy','Chih Yuriy','N/A','N/A',1,'10206126814782634',NULL,NULL,'https://scontent.xx.fbcdn.net/hprofile-xfp1/v/t1.0-1/1653967_10202169152643554_1861611583_n.jpg?oh=4f403e1de1e54ee845dfd2233f86e8a5&oe=56606C47',NULL),(270,'Vova Yevdoshenko','N/A','2015-09-11',1,3,'N/A','N/A','narcodyller@gmail.com','Vova Yevdoshenko','Vova Yevdoshenko','N/A','N/A',1,'1017532261612508',NULL,NULL,'https://scontent.xx.fbcdn.net/hprofile-xpa1/v/t1.0-1/p200x200/10660350_819679078064495_6800320289565234256_n.jpg?oh=b1474ed614e4ee5d8c6b59f7a133a932&oe=56A3187B',NULL),(274,'Oleg Svyryd','N/A','2015-09-12',1,3,'N/A','N/A','robannnnn@gmail.com','Oleg Svyryd','Oleg Svyryd','N/A','N/A',1,NULL,NULL,'101934570862476397709','https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg',NULL),(296,'Victor','Kopach','2015-09-16',1,3,'000-6662222','rrr','animal.id.info@gmail.com','Victor','25d55ad283aa400af464c76d713c07ad','N/A','N/A',1,NULL,NULL,NULL,NULL,'7800d73b-3e77-4b20-bc6d-fe995d4d88d0'),(307,'111','333','2015-09-18',1,3,NULL,'N/A','dupenko@tuhes.if.ua',NULL,'d41d8cd98f00b204e9800998ecf8427e','N/A','N/A',0,NULL,NULL,NULL,NULL,'58cea9fa-fe81-44d0-b8db-e242463bb8ec'),(339,'Yuri Tym','N/A','2015-09-21',1,3,'N/A','N/A','N/A','Yuri Tym','Yuri Tym','N/A','N/A',1,NULL,'735487800',NULL,'http://abs.twimg.com/sticky/default_profile_images/default_profile_5.png',NULL),(341,'redant','redant','2015-09-21',1,3,'098-6436703','sdfgdfg','redantif@gmail.com','redant','09b73589cc8ef6673ab8e713edc7cd67','N/A','N/A',1,NULL,NULL,NULL,NULL,'17e2b6b5-8a12-417f-8f4e-d429c2b6c6c0'),(365,'Yurij Chih','N/A','2015-09-23',1,3,'N/A','N/A','ndpidesigner@gmail.com','Yurij Chih','Yurij Chih','N/A','N/A',1,NULL,NULL,'110903679401908476560','https://lh5.googleusercontent.com/-GncdNbk3iqw/AAAAAAAAAAI/AAAAAAAAC5E/sJrsrkdXA_M/photo.jpg',NULL),(366,'unknown','N/A','2015-09-23',1,3,'N/A','N/A','','unknown','unknown','N/A','N/A',1,NULL,NULL,NULL,'https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg',NULL),(367,'Yuriy','Chikh','2015-09-23',1,3,'000-0111111','N/A','yra1908@gukr.net','yra19088','b0baee9d279d34fa1dfd71aadb908c3f','N/A','N/A',0,NULL,NULL,NULL,NULL,'fed5a832-eb7d-4dc0-96f3-bf04c45489ac'),(369,'root','rot','2015-08-17',1,3,'000-0000001','Franik','ifjava555056@gmail.com','root1908','63a9f0ea7bb98050796b649e85481845','if-056','N/A',1,NULL,NULL,NULL,NULL,'9b8faf01-2217-4c1a-aee6-276de8966cef'),(377,'test','test','2015-09-24',1,3,NULL,'N/A','test@rtttest.test','testds','96e79218965eb72c92a549dd5a330112','N/A','N/A',0,NULL,NULL,NULL,NULL,'70d43444-9a04-41e7-8a63-724b7d9d35fc'),(378,'test','test','2015-09-24',1,3,NULL,'N/A','test@test.test','testtest','96e79218965eb72c92a549dd5a330112','N/A','N/A',0,NULL,NULL,NULL,NULL,'154b4eac-4b69-4a20-9561-d021ff262ad4'),(379,'Ifjava Same','N/A','2015-09-25',1,3,'N/A','N/A','N/A','Ifjava Same','Ifjava Same','N/A','N/A',1,NULL,NULL,NULL,'https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpf1/v/t1.0-1/s200x200/10354686_10150004552801856_220367501106153455_n.jpg?oh=295fb593caac181f8bf03ed7a216d7b2&oe=56994C50&__gda__=1449242428_0f790f64b513b097ccc94dce20c2beb1',NULL),(492,'test','test','2015-09-26',1,3,'111-1111111','N/A','test1@test.test','tsettt','96e79218965eb72c92a549dd5a330112','N/A','N/A',1,NULL,NULL,NULL,NULL,'36d2e755-db9a-4455-b153-b74c7d1ddd5c'),(493,'oleg','svyryd','2015-09-26',1,3,'000-0000000','N/A','robannnnn@bigmir.net','3Doleg','96e79218965eb72c92a549dd5a330112','N/A','N/A',1,NULL,NULL,NULL,NULL,'29260927-4b60-491a-9f04-ed9ff556439f'),(513,'Yuriy Chih','N/A','2015-09-27',1,3,'000-0000003','N/A','yra1908@gmail.com','Yuriy Chih','Yuriy Chih','N/A','N/A',1,NULL,NULL,'109292332862237373664','https://lh6.googleusercontent.com/-zLorIFbTKWI/AAAAAAAAAAI/AAAAAAAABjw/IxLTWb9FCq0/photo.jpg',NULL),(514,'222222','222222','2015-09-27',1,3,'000-0111111','N/A','yra1908@tvnet.if.ua','yra1908@ukr.net','b0baee9d279d34fa1dfd71aadb908c3f','N/A','N/A',1,NULL,NULL,NULL,NULL,'ded4a305-fa5f-42f9-84c4-ac15122c9f31');
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
  `typeUa` varchar(19) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Типи користувачів';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertypes`
--

LOCK TABLES `usertypes` WRITE;
/*!40000 ALTER TABLE `usertypes` DISABLE KEYS */;
INSERT INTO `usertypes` VALUES (1,'owner','власник'),(2,'vet','ветеринар'),(3,'cynologist union','спілка кінологів'),(4,'organization','організація'),(5,'misc','інше');
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

-- Dump completed on 2015-09-28  9:01:02
