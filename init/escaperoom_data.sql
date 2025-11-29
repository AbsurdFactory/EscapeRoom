-- MySQL dump 10.13  Distrib 8.0.43, for macos15 (x86_64)
--
-- Host: localhost    Database: escape_room
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `certificate`
--

DROP TABLE IF EXISTS `certificate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certificate` (
  `id_certificate` int NOT NULL AUTO_INCREMENT,
  `text_body` text,
  `reward` varchar(45) DEFAULT NULL,
  `player_id_player` int NOT NULL,
  `room_id_room` int NOT NULL,
  PRIMARY KEY (`id_certificate`,`player_id_player`,`room_id_room`),
  UNIQUE KEY `id_certificate_UNIQUE` (`id_certificate`),
  KEY `fk_certificate_player1_idx` (`player_id_player`),
  KEY `fk_certificate_room1_idx` (`room_id_room`),
  CONSTRAINT `fk_certificate_player1` FOREIGN KEY (`player_id_player`) REFERENCES `player` (`id_player`),
  CONSTRAINT `fk_certificate_room1` FOREIGN KEY (`room_id_room`) REFERENCES `room` (`id_room`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certificate`
--

LOCK TABLES `certificate` WRITE;
/*!40000 ALTER TABLE `certificate` DISABLE KEYS */;
INSERT INTO `certificate` VALUES (1,'Completed Haunted Mansion in 58 minutes!','Bronze Medal',1,1),(2,'Found all hidden treasures in Pirate Cove!','Silver Medal',2,2),(3,'Solved the lab mystery perfectly!','Gold Medal',3,3),(4,'Escaped the Magic Forest with magic!','Magic Medal',4,4);
/*!40000 ALTER TABLE `certificate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clue`
--

DROP TABLE IF EXISTS `clue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clue` (
  `id_clue` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `text` text NOT NULL,
  `theme` varchar(45) NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_clue`),
  UNIQUE KEY `idclue_UNIQUE` (`id_clue`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clue`
--

LOCK TABLES `clue` WRITE;
/*!40000 ALTER TABLE `clue` DISABLE KEYS */;
INSERT INTO `clue` VALUES (1,'Bloody Key','A key covered in something red… opens a metal box.','Horror',4.00),(2,'Ancient Map','A map showing hidden tunnels.','Adventure',2.00),(3,'Chemical Formula','A ruined formula needed for solving the puzzle.','Science',4.00),(4,'Magic Rune','A glowing rune with mysterious symbols.','Fantasy',3.00);
/*!40000 ALTER TABLE `clue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `decoration_object`
--

DROP TABLE IF EXISTS `decoration_object`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `decoration_object` (
  `id_decoration_object` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(250) NOT NULL,
  `material` varchar(45) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_decoration_object`),
  UNIQUE KEY `id_decoration_object_UNIQUE` (`id_decoration_object`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `decoration_object`
--

LOCK TABLES `decoration_object` WRITE;
/*!40000 ALTER TABLE `decoration_object` DISABLE KEYS */;
INSERT INTO `decoration_object` VALUES (1,'Skull Candle','A candle shaped like a skull.','Wax',10.00),(2,'Treasure Chest','A wooden pirate-style chest.','Wood',25.00),(3,'Lab Tubes Set','A set of colorful test tubes.','Glass',15.00),(4,'Enchanted Tree','A small glowing fantasy tree.','Plastic',30.00);
/*!40000 ALTER TABLE `decoration_object` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `escape`
--

DROP TABLE IF EXISTS `escape`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `escape` (
  `id_escape` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_escape`),
  UNIQUE KEY `idescape_UNIQUE` (`id_escape`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `escape`
--

LOCK TABLES `escape` WRITE;
/*!40000 ALTER TABLE `escape` DISABLE KEYS */;
INSERT INTO `escape` VALUES (1),(2),(3);
/*!40000 ALTER TABLE `escape` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `escape_has_room`
--

DROP TABLE IF EXISTS `escape_has_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `escape_has_room` (
  `escape_id_escape` int NOT NULL,
  `room_id_room` int NOT NULL,
  PRIMARY KEY (`escape_id_escape`,`room_id_room`),
  KEY `fk_escape_has_room_room1_idx` (`room_id_room`),
  KEY `fk_escape_has_room_escape1_idx` (`escape_id_escape`),
  CONSTRAINT `fk_escape_has_room_escape1` FOREIGN KEY (`escape_id_escape`) REFERENCES `escape` (`id_escape`),
  CONSTRAINT `fk_escape_has_room_room1` FOREIGN KEY (`room_id_room`) REFERENCES `room` (`id_room`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `escape_has_room`
--

LOCK TABLES `escape_has_room` WRITE;
/*!40000 ALTER TABLE `escape_has_room` DISABLE KEYS */;
INSERT INTO `escape_has_room` VALUES (1,1),(1,2),(2,3),(3,4);
/*!40000 ALTER TABLE `escape_has_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player` (
  `id_player` int NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(45) NOT NULL,
  `email` varchar(120) NOT NULL,
  `subscribed` boolean NOT NULL DEFAULT false,
  PRIMARY KEY (`id_player`),
  UNIQUE KEY `id_player_UNIQUE` (`id_player`),
  UNIQUE KEY `nick_name_UNIQUE` (`nick_name`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (1,'GhostHunter','ghost@example.com', false),(2,'CaptainJack','jack@example.com', false),(3,'LabMaster','lab@example.com', false),(4,'FairyQueen','fairy@example.com', true);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id_room` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `difficulty_level` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_room`,`price`),
  UNIQUE KEY `idroom_UNIQUE` (`id_room`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'Haunted Mansion',30.00,'Hard'),(2,'Pirate Cove',25.00,'Medium'),(3,'Secret Laboratory',35.00,'Hard'),(4,'Magic Forest',20.00,'Easy');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_has_clues`
--

DROP TABLE IF EXISTS `room_has_clues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_has_clues` (
  `clue_id_clue` int NOT NULL,
  `room_id_room` int NOT NULL,
  PRIMARY KEY (`clue_id_clue`,`room_id_room`),
  KEY `fk_clue_has_room_room1_idx` (`room_id_room`),
  KEY `fk_clue_has_room_clue1_idx` (`clue_id_clue`),
  CONSTRAINT `fk_clue_has_room_clue1` FOREIGN KEY (`clue_id_clue`) REFERENCES `clue` (`id_clue`),
  CONSTRAINT `fk_clue_has_room_room1` FOREIGN KEY (`room_id_room`) REFERENCES `room` (`id_room`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_has_clues`
--

LOCK TABLES `room_has_clues` WRITE;
/*!40000 ALTER TABLE `room_has_clues` DISABLE KEYS */;
INSERT INTO `room_has_clues` VALUES (1,1),(2,2),(3,3),(4,4);
/*!40000 ALTER TABLE `room_has_clues` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_has_decoration_object`
--

DROP TABLE IF EXISTS `room_has_decoration_object`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_has_decoration_object` (
  `room_id_room` int NOT NULL,
  `decoration_object_id_decoration_object` int NOT NULL,
  PRIMARY KEY (`room_id_room`,`decoration_object_id_decoration_object`),
  KEY `fk_room_has_decoration_object_decoration_object1_idx` (`decoration_object_id_decoration_object`),
  KEY `fk_room_has_decoration_object_room1_idx` (`room_id_room`),
  CONSTRAINT `fk_room_has_decoration_object_decoration_object1` FOREIGN KEY (`decoration_object_id_decoration_object`) REFERENCES `decoration_object` (`id_decoration_object`),
  CONSTRAINT `fk_room_has_decoration_object_room1` FOREIGN KEY (`room_id_room`) REFERENCES `room` (`id_room`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_has_decoration_object`
--

LOCK TABLES `room_has_decoration_object` WRITE;
/*!40000 ALTER TABLE `room_has_decoration_object` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_has_decoration_object` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `id_ticket` int NOT NULL AUTO_INCREMENT,
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `date_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `player_id_player` int NOT NULL,
  `room_id_room` int NOT NULL,
  `room_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_ticket`,`player_id_player`,`room_id_room`,`room_price`),
  UNIQUE KEY `id_ticket_UNIQUE` (`id_ticket`),
  KEY `fk_ticket_player_idx` (`player_id_player`),
  KEY `fk_ticket_room1_idx` (`room_id_room`,`room_price`),
  CONSTRAINT `fk_ticket_player` FOREIGN KEY (`player_id_player`) REFERENCES `player` (`id_player`),
  CONSTRAINT `fk_ticket_room1` FOREIGN KEY (`room_id_room`, `room_price`) REFERENCES `room` (`id_room`, `price`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,30.00,'2025-11-25 23:01:34',1,1,30.00),(2,25.00,'2025-11-25 23:01:34',2,2,25.00),(3,35.00,'2025-11-25 23:01:34',3,3,35.00),(4,20.00,'2025-11-25 23:01:34',4,4,20.00);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-26  0:13:35
