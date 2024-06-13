CREATE DATABASE  IF NOT EXISTS `institucion` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `institucion`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: institucion
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `alumno`
--

DROP TABLE IF EXISTS `alumno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumno` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `id_curso` int DEFAULT NULL,
  `foto` varchar(255) DEFAULT NULL,
  `dni` varchar(9) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_curso` (`id_curso`),
  CONSTRAINT `alumno_ibfk_1` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumno`
--

LOCK TABLES `alumno` WRITE;
/*!40000 ALTER TABLE `alumno` DISABLE KEYS */;
INSERT INTO `alumno` VALUES (1,'Luis','Gómez Gutiérrez',1,'https://firebasestorage.googleapis.com/v0/b/pruebatycep.appspot.com/o/Images%2Fa1a99bfe-a558-4b2b-8941-578f1b59da6f?alt=media&token=de352a7a-bcbc-4aff-8857-e1bf588a45a8','71789217F'),(2,'Ana','Fernández Pérez',1,'https://firebasestorage.googleapis.com/v0/b/pruebatycep.appspot.com/o/Images%2F80ce50af-c4f5-4ddc-9386-d30f95c3b96b?alt=media&token=618053de-547f-4072-a76f-8ba086e27136','12345678Z'),(3,'Carlos','Ruiz García',2,'https://firebasestorage.googleapis.com/v0/b/pruebatycep.appspot.com/o/Images%2F00d82307-61af-4b4f-ba3d-2d4190582b12?alt=media&token=2709d44c-c3e8-4dd3-a3b8-0d2ddb106afc','71789219D'),(4,'Maria','Fernandez',5,NULL,'09441567K');
/*!40000 ALTER TABLE `alumno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asignatura`
--

DROP TABLE IF EXISTS `asignatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asignatura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignatura`
--

LOCK TABLES `asignatura` WRITE;
/*!40000 ALTER TABLE `asignatura` DISABLE KEYS */;
INSERT INTO `asignatura` VALUES (1,'Mates'),(2,'Lengua'),(3,'Sociales'),(4,'Historia'),(5,'Inglés'),(6,'Matemáticas 2 ESO'),(7,'Biología 4 ESO');
/*!40000 ALTER TABLE `asignatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso`
--

DROP TABLE IF EXISTS `curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `curso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `foto` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso`
--

LOCK TABLES `curso` WRITE;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
INSERT INTO `curso` VALUES (1,'DAM2B',NULL),(2,'DAW 1A',NULL),(3,'3º ESO A',NULL),(4,'3 ESO D',''),(5,'3 ESO A',''),(6,' 3 ESO C',''),(7,'1 BACH A','');
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faltas`
--

DROP TABLE IF EXISTS `faltas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faltas` (
  `hora` int NOT NULL,
  `fecha` date DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `id_alumno` int DEFAULT NULL,
  `justificada` tinyint(1) DEFAULT NULL,
  `asignatura` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_alumno` (`id_alumno`),
  CONSTRAINT `faltas_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `alumno` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faltas`
--

LOCK TABLES `faltas` WRITE;
/*!40000 ALTER TABLE `faltas` DISABLE KEYS */;
INSERT INTO `faltas` VALUES (1,'2024-03-01',1,1,1,'Mates'),(2,'2024-03-02',2,2,1,'Mates'),(3,'2024-03-03',3,3,1,'Mates'),(9,'2024-05-01',4,NULL,0,'Mates'),(9,'2024-05-01',5,NULL,0,'Mates'),(9,'2024-05-01',6,NULL,0,'Mates'),(9,'2024-05-01',7,NULL,1,'Mates'),(10,'2024-05-01',8,NULL,0,'Mates'),(10,'2024-05-01',9,NULL,0,'Mates'),(10,'2024-05-01',11,3,0,'Mates'),(9,'2024-05-01',18,3,0,'Mates'),(9,'2024-05-01',19,1,0,'Mates'),(9,'2024-05-01',21,3,0,'Mates'),(9,'2024-05-01',22,1,0,'Mates'),(9,'2024-05-01',23,2,0,'Mates'),(9,'2024-05-01',24,3,1,'Mates'),(9,'2024-05-01',25,1,0,'Mates'),(9,'2024-05-01',26,3,1,'Mates'),(9,'2024-05-01',28,3,1,'Mates'),(9,'2024-05-01',31,3,1,'Mates'),(9,'2024-05-01',32,2,1,'Mates'),(9,'2024-05-01',33,1,1,'Mates'),(9,'2024-05-12',50,1,1,'Asignatura(id=3'),(9,'2024-05-12',51,3,0,'Asignatura(id=3'),(2,'2024-05-13',52,1,0,'Asignatura(id=2'),(1,'2024-05-13',59,2,0,'Mates'),(1,'2024-05-19',62,2,0,'Mates'),(1,'2024-05-19',63,1,1,'Mates'),(6,'2024-05-28',64,2,0,'Lengua'),(6,'2024-05-28',65,1,1,'Lengua'),(3,'2024-05-28',66,2,0,'Historia'),(4,'2024-06-02',68,2,0,'Historia'),(4,'2024-06-02',69,1,1,'Historia'),(1,'2024-06-08',70,1,0,'Mates'),(1,'2024-06-08',71,2,0,'Mates'),(2,'2024-06-08',72,1,0,'Lengua'),(2,'2024-06-08',73,2,0,'Lengua'),(4,'2024-06-11',74,3,0,'Inglés');
/*!40000 ALTER TABLE `faltas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horario`
--

DROP TABLE IF EXISTS `horario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `horario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dia` enum('L','M','X','J','V') DEFAULT NULL,
  `hora` int NOT NULL,
  `id_curso` int DEFAULT NULL,
  `id_asignatura` int DEFAULT NULL,
  `aula` int DEFAULT NULL,
  `id_profesor` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_curso` (`id_curso`),
  KEY `FK_Horario_Asignatura` (`id_asignatura`),
  KEY `FK_Horario_Profesor` (`id_profesor`),
  CONSTRAINT `FK_Horario_Asignatura` FOREIGN KEY (`id_asignatura`) REFERENCES `asignatura` (`id`),
  CONSTRAINT `FK_Horario_Profesor` FOREIGN KEY (`id_profesor`) REFERENCES `profesor` (`usuario_id`),
  CONSTRAINT `horario_ibfk_1` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horario`
--

LOCK TABLES `horario` WRITE;
/*!40000 ALTER TABLE `horario` DISABLE KEYS */;
INSERT INTO `horario` VALUES (5,'L',1,1,1,1,14),(6,'L',2,1,2,2,14),(7,'L',3,1,3,3,14),(8,'L',4,1,4,4,14),(9,'L',5,1,5,5,14),(10,'L',6,1,1,6,14),(11,'M',1,1,2,7,14),(12,'M',2,1,3,8,14),(13,'M',3,1,4,9,14),(14,'M',4,1,5,10,14),(15,'M',5,1,1,11,14),(16,'M',6,1,2,12,14),(17,'X',1,1,3,13,14),(18,'X',2,1,4,14,14),(19,'X',3,1,5,15,14),(20,'X',4,1,1,16,14),(22,'X',6,1,3,18,14),(23,'J',1,1,4,19,14),(24,'J',2,1,5,20,14),(25,'J',3,1,1,21,14),(26,'J',4,1,2,22,14),(27,'J',5,1,3,23,14),(28,'J',6,1,4,24,14),(29,'V',1,1,5,25,14),(30,'V',2,1,1,26,14),(31,'V',3,1,2,27,14),(32,'V',4,1,3,28,14),(33,'V',5,1,4,29,14),(34,'V',6,1,5,30,14),(35,'L',1,2,1,31,14),(36,'L',2,2,2,32,14),(37,'L',3,2,3,33,14),(38,'L',4,2,4,34,14),(39,'L',5,2,5,35,14),(40,'L',6,2,1,36,14),(41,'M',1,2,2,37,14),(42,'M',2,2,3,38,14),(43,'M',3,2,4,39,14),(44,'M',4,2,5,40,14),(45,'M',5,2,1,41,14),(46,'M',6,2,2,42,14),(47,'X',1,2,3,43,14),(48,'X',2,2,4,44,14),(49,'X',3,2,5,45,14),(50,'X',4,2,1,46,14),(51,'X',5,2,2,47,14),(52,'X',6,2,3,48,14),(53,'J',1,2,4,49,14),(54,'J',2,2,5,50,14),(55,'J',3,2,1,51,14),(56,'J',4,2,2,52,14),(57,'J',5,2,3,53,14),(58,'J',6,2,4,54,14),(59,'V',1,2,5,55,14),(60,'V',2,2,1,56,14),(61,'V',3,2,2,57,14),(62,'V',4,2,3,58,14),(63,'V',5,2,4,59,14),(64,'V',6,2,5,60,14),(65,'X',5,1,4,222,29);
/*!40000 ALTER TABLE `horario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesor`
--

DROP TABLE IF EXISTS `profesor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesor` (
  `usuario_id` int NOT NULL,
  `dni` varchar(20) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `foto` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  CONSTRAINT `profesor_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesor`
--

LOCK TABLES `profesor` WRITE;
/*!40000 ALTER TABLE `profesor` DISABLE KEYS */;
INSERT INTO `profesor` VALUES (4,'12345678A','juan@example.com','a'),(5,'87654321B','maria@example.com','a'),(13,'12345678A','profesor@example.com','a'),(14,'12345678A','profesor@example.com','https://firebasestorage.googleapis.com/v0/b/pruebatycep.appspot.com/o/Images%2F8caf7e85-d185-4591-846f-42784fc02256?alt=media&token=fbef1870-2e59-4ddf-855d-4fda662139dd'),(29,'71789219D','paquitoChocolatero@gmail.com',NULL);
/*!40000 ALTER TABLE `profesor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesor_asignatura`
--

DROP TABLE IF EXISTS `profesor_asignatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesor_asignatura` (
  `id_profesor` int NOT NULL,
  `id_asignatura` int NOT NULL,
  PRIMARY KEY (`id_profesor`,`id_asignatura`),
  KEY `id_asignatura` (`id_asignatura`),
  CONSTRAINT `profesor_asignatura_ibfk_1` FOREIGN KEY (`id_profesor`) REFERENCES `profesor` (`usuario_id`),
  CONSTRAINT `profesor_asignatura_ibfk_2` FOREIGN KEY (`id_asignatura`) REFERENCES `asignatura` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesor_asignatura`
--

LOCK TABLES `profesor_asignatura` WRITE;
/*!40000 ALTER TABLE `profesor_asignatura` DISABLE KEYS */;
INSERT INTO `profesor_asignatura` VALUES (14,1),(29,1),(14,2),(14,3),(14,4),(29,5);
/*!40000 ALTER TABLE `profesor_asignatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesor_curso`
--

DROP TABLE IF EXISTS `profesor_curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesor_curso` (
  `id_profesor` int NOT NULL,
  `id_curso` int NOT NULL,
  PRIMARY KEY (`id_profesor`,`id_curso`),
  KEY `id_curso` (`id_curso`),
  CONSTRAINT `profesor_curso_ibfk_1` FOREIGN KEY (`id_profesor`) REFERENCES `profesor` (`usuario_id`),
  CONSTRAINT `profesor_curso_ibfk_2` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesor_curso`
--

LOCK TABLES `profesor_curso` WRITE;
/*!40000 ALTER TABLE `profesor_curso` DISABLE KEYS */;
INSERT INTO `profesor_curso` VALUES (4,1),(14,1),(5,2),(14,2);
/*!40000 ALTER TABLE `profesor_curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutor_legal`
--

DROP TABLE IF EXISTS `tutor_legal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutor_legal` (
  `usuario_id` int NOT NULL,
  `telef_contacto` varchar(20) DEFAULT NULL,
  `domicilio` varchar(255) DEFAULT NULL,
  `dni` varchar(9) DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  CONSTRAINT `tutor_legal_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutor_legal`
--

LOCK TABLES `tutor_legal` WRITE;
/*!40000 ALTER TABLE `tutor_legal` DISABLE KEYS */;
INSERT INTO `tutor_legal` VALUES (1,'123456789','Calle Mayor, 123','71789217F'),(3,'555555555','Plaza España, 789','09441567K'),(7,'982767432','Fuentealbilla','33445566D'),(9,'982767432','Fuentealbilla','44556677B'),(30,'983456234','Calle casa dios a la derecha','71789219D');
/*!40000 ALTER TABLE `tutor_legal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutor_legal_alumno`
--

DROP TABLE IF EXISTS `tutor_legal_alumno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutor_legal_alumno` (
  `id_tutor_legal` int NOT NULL,
  `id_alumno` int NOT NULL,
  PRIMARY KEY (`id_tutor_legal`,`id_alumno`),
  KEY `id_alumno` (`id_alumno`),
  CONSTRAINT `tutor_legal_alumno_ibfk_1` FOREIGN KEY (`id_tutor_legal`) REFERENCES `tutor_legal` (`usuario_id`),
  CONSTRAINT `tutor_legal_alumno_ibfk_2` FOREIGN KEY (`id_alumno`) REFERENCES `alumno` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutor_legal_alumno`
--

LOCK TABLES `tutor_legal_alumno` WRITE;
/*!40000 ALTER TABLE `tutor_legal_alumno` DISABLE KEYS */;
INSERT INTO `tutor_legal_alumno` VALUES (1,1),(9,1),(30,1),(9,2),(30,2),(3,3);
/*!40000 ALTER TABLE `tutor_legal_alumno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario` varchar(255) DEFAULT NULL,
  `contrasena` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `dtype` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `usuario_chk_1` CHECK ((`dtype` in (_utf8mb4'P',_utf8mb4'T',_utf8mb4'A')))
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'usuario1','password1','Juan','Gómez','P'),(3,'usuario3','password3','Pedro','Martínez','P'),(4,'usuario4','password4','Jose','Fernández','T'),(5,'usuario5','password5','Eduardo','Martínez','P'),(6,'ejemplo_usuario','contrasena_segura','Ejemplo','Usuario','T'),(7,'ejemplo_usuario','contrasena_segura','Ejemplo','Usuario','T'),(8,'ejemplo_usuario','contrasena_segura','Ejemplo','Usuario','A'),(9,'manu','{bcrypt}$2a$10$EyvrqSbAu10kotYUMxVnI.t3yw4ZWZV9ZELIhFELHZhZ9JEP8ffom','Ejemplo','Usuario','T'),(13,'Tinho','{bcrypt}$2a$10$MzFh0lzpHzdyH0Irz6gPF.gmGz9m.zpA1xYuYpJh2m2ouzdxsqHXq','Roberto','Garcia','P'),(14,'tito','{bcrypt}$2a$10$rUixpzMzukmeV3FfLgABPu8/THW6maBvsJLLSAe9IWljtE7Cf3Mmq','Roberto','Garcia','P'),(20,'rro_60','{bcrypt}$2a$10$QKHHQreDIcd6V38qj4r9LOg9.qBS6E8u4VJetU3p5.ow/YtF2YtOe','root','root','A'),(21,'PFe_42','{bcrypt}$2a$10$S5wS7ZHG7FoS4afMN6XMH.0FtbKDIGxbkUVDRA1LrOvaj9VafqcT2','Paco','Fernández','A'),(22,'rro_71','{bcrypt}$2a$10$R1iI9wGZTFI1VV/XWrNQ/.cznZLm82Tf2ARJfXY8P5tUgdqMiwAt.','roto','roto','A'),(23,'rro_80','{bcrypt}$2a$10$DbjBG1//m4JVF./S0s4ipOQqThLc3Ee1YGt4c8tY3yWsLJMKzvCXe','rootoo','rootoo','A'),(29,'MDH_28','{bcrypt}$2a$10$ZezX7V6rIVLk08nSmlGMqua1qWwMfIZpENDGJXXKazCFLAto5vzYO','Manolito','De Huerva Fernández','P'),(30,'PDL_64','{bcrypt}$2a$10$tlyvVGBmDFakL65YmxiqtuLgSFZDkEc8MfEoqCNDtmRgzFsxhhfR2','Paco','De Lucia Fernández','T');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-11 23:26:27
