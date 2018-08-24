-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: taxiapp
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `taxi_movement_data`
--

DROP TABLE IF EXISTS `taxi_movement_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `taxi_movement_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `longitude` double NOT NULL,
  `lattitude` double NOT NULL,
  `tracked_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taxi_movement_data`
--

LOCK TABLES `taxi_movement_data` WRITE;
/*!40000 ALTER TABLE `taxi_movement_data` DISABLE KEYS */;
INSERT INTO `taxi_movement_data` VALUES (1,103.932405,1.345482,'2018-07-20 08:00:00'),(2,103.932405,1.345482,'2018-07-20 08:00:00');
/*!40000 ALTER TABLE `taxi_movement_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_pickup_locations`
--

DROP TABLE IF EXISTS `user_pickup_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_pickup_locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `lattitude` double NOT NULL,
  `longitude` double NOT NULL,
  `location_name` varchar(255) NOT NULL,
  `date_added` datetime NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_unique` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_pickup_locations`
--

LOCK TABLES `user_pickup_locations` WRITE;
/*!40000 ALTER TABLE `user_pickup_locations` DISABLE KEYS */;
INSERT INTO `user_pickup_locations` VALUES (1,1,1.445482,103.032405,'School','2018-07-07 20:09:00','Work'),(4,1,1.3,101.3,'My Location','2018-07-20 00:00:00','Personal'),(5,1,1.3,101.3,'My Location 2','2018-07-20 00:00:00','Personal'),(10,1,1.345482,103.932405,'School','2018-07-20 00:00:00','Work'),(11,1,1.345482,103.932405,'School','2018-07-20 00:00:00','Work');
/*!40000 ALTER TABLE `user_pickup_locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(10) NOT NULL,
  `password` varchar(16) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `last_failed_login_date` datetime DEFAULT NULL,
  `failed_login_count` int(11) DEFAULT '0',
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `id_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'user1','password1','Adam','Lee',NULL,0,'user'),(2,'user2','password1','Jane','Lee',NULL,0,'user'),(7,'gtan1980','tanIsgood','Gary','Tan',NULL,0,'user'),(8,'admin','admin123','admin','person',NULL,0,'admin'),(9,'test1','password','test','1',NULL,0,NULL),(10,'test2','password','test','2',NULL,0,NULL),(11,'test3','password','test','3',NULL,0,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-08 19:20:52
