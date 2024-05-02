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
  PRIMARY KEY (`id`),
  KEY `id_curso` (`id_curso`),
  CONSTRAINT `alumno_ibfk_1` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumno`
--

LOCK TABLES `alumno` WRITE;
/*!40000 ALTER TABLE `alumno` DISABLE KEYS */;
INSERT INTO `alumno` VALUES (1,'Luis','Gómez Gutiérrez',1,'hola'),(2,'Ana','Fernández Pérez',1,'hola'),(3,'Carlos','Ruiz García',2,'hola');
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignatura`
--

LOCK TABLES `asignatura` WRITE;
/*!40000 ALTER TABLE `asignatura` DISABLE KEYS */;
INSERT INTO `asignatura` VALUES (1,'Mates'),(2,'Lengua'),(3,'Sociales'),(4,'Historia'),(5,'Inglés');
/*!40000 ALTER TABLE `asignatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `boletin` tinyint(1) DEFAULT NULL,
  `nombreChat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
INSERT INTO `chat` VALUES (1,0,'Panas'),(2,0,'Panas2'),(3,1,'Panas3');
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso`
--

LOCK TABLES `curso` WRITE;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
INSERT INTO `curso` VALUES (1,'DAM2B',NULL),(2,'DAW 1A',NULL),(3,'3º ESO A',NULL);
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
  PRIMARY KEY (`id`),
  KEY `id_alumno` (`id_alumno`),
  CONSTRAINT `faltas_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `alumno` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faltas`
--

LOCK TABLES `faltas` WRITE;
/*!40000 ALTER TABLE `faltas` DISABLE KEYS */;
INSERT INTO `faltas` VALUES (1,'2024-03-01',1,1,1),(2,'2024-03-02',2,2,1),(3,'2024-03-03',3,3,1),(9,'2024-05-01',4,NULL,0),(9,'2024-05-01',5,NULL,0),(9,'2024-05-01',6,NULL,0),(9,'2024-05-01',7,NULL,1),(10,'2024-05-01',8,NULL,0),(10,'2024-05-01',9,NULL,0),(10,'2024-05-01',10,1,0),(10,'2024-05-01',11,3,0),(10,'2024-05-01',12,2,1),(9,'2024-05-01',16,1,0),(9,'2024-05-01',17,2,0),(9,'2024-05-01',18,3,0),(9,'2024-05-01',19,1,0),(9,'2024-05-01',20,2,0),(9,'2024-05-01',21,3,0),(9,'2024-05-01',22,1,0),(9,'2024-05-01',23,2,0),(9,'2024-05-01',24,3,1),(9,'2024-05-01',25,1,0),(9,'2024-05-01',26,3,1),(9,'2024-05-01',27,2,1),(9,'2024-05-01',28,3,1),(9,'2024-05-01',29,2,1),(9,'2024-05-01',30,1,1),(9,'2024-05-01',31,3,1),(9,'2024-05-01',32,2,1),(9,'2024-05-01',33,1,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horario`
--

LOCK TABLES `horario` WRITE;
/*!40000 ALTER TABLE `horario` DISABLE KEYS */;
INSERT INTO `horario` VALUES (1,'L',1,1,1,222,14),(2,'M',2,1,1,123,14),(3,'X',3,1,1,453,14),(4,'L',2,1,2,98,14);
/*!40000 ALTER TABLE `horario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensaje`
--

DROP TABLE IF EXISTS `mensaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mensaje` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int DEFAULT NULL,
  `id_chat` int DEFAULT NULL,
  `contenido` text,
  `fecha` date DEFAULT NULL,
  `nombre_usuario` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_chat` (`id_chat`),
  KEY `FK_Mensaje_Usuario` (`id_usuario`),
  CONSTRAINT `FK_Mensaje_Usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `mensaje_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `mensaje_ibfk_2` FOREIGN KEY (`id_chat`) REFERENCES `chat` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensaje`
--

LOCK TABLES `mensaje` WRITE;
/*!40000 ALTER TABLE `mensaje` DISABLE KEYS */;
INSERT INTO `mensaje` VALUES (1,1,1,'Hola, ¿Como te va?','2024-03-01','tito'),(2,2,1,'Bien gracias, ¿Y a ti?.','2024-03-01','usuario2'),(3,3,1,'¿Alguien puede ayudarme con la tarea?','2024-03-02','root'),(4,NULL,1,'awdawdawawdawda','2024-04-24','tito'),(5,NULL,1,'dwaadwawd','2024-04-24','tito'),(6,NULL,1,'lalalalala','2024-04-24','tito'),(7,2,1,'Re dios que complicao','2024-03-02','usuario2'),(8,2,1,'Hola?','2024-03-02','usuario2'),(9,NULL,1,'aaaaaaaa','2024-04-24','tito'),(10,NULL,1,'awubawidbawibaw','2024-04-25','tito'),(11,NULL,1,'wadawaw','2024-04-28','tito'),(12,NULL,1,'Hasta la prxima','2024-04-28','tito'),(13,NULL,1,'hasta la prox','2024-04-28','tito'),(14,NULL,1,'Esto funciona en verdad?','2024-04-28','tito');
/*!40000 ALTER TABLE `mensaje` ENABLE KEYS */;
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
  PRIMARY KEY (`usuario_id`),
  CONSTRAINT `profesor_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesor`
--

LOCK TABLES `profesor` WRITE;
/*!40000 ALTER TABLE `profesor` DISABLE KEYS */;
INSERT INTO `profesor` VALUES (4,'12345678A','juan@example.com'),(5,'87654321B','maria@example.com'),(10,'12345678A','profesor@example.com'),(11,'12345678A','profesor@example.com'),(12,'12345678A','profesor@example.com'),(13,'12345678A','profesor@example.com'),(14,'12345678A','profesor@example.com');
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
INSERT INTO `profesor_asignatura` VALUES (14,1),(14,2),(14,3),(14,4);
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
INSERT INTO `profesor_curso` VALUES (4,1),(10,1),(14,1),(5,2),(14,2);
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
  PRIMARY KEY (`usuario_id`),
  CONSTRAINT `tutor_legal_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutor_legal`
--

LOCK TABLES `tutor_legal` WRITE;
/*!40000 ALTER TABLE `tutor_legal` DISABLE KEYS */;
INSERT INTO `tutor_legal` VALUES (1,'123456789','Calle Mayor, 123'),(2,'987654321','Avenida Libertad, 456'),(3,'555555555','Plaza España, 789'),(7,'982767432','Fuentealbilla'),(9,'982767432','Fuentealbilla');
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
INSERT INTO `tutor_legal_alumno` VALUES (1,1),(9,1),(2,2),(9,2),(3,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'usuario1','password1','Juan','Gómez','P'),(2,'usuario2','password2','María','López','T'),(3,'usuario3','password3','Pedro','Martínez','P'),(4,'usuario4','password4','Jose','Fernández','T'),(5,'usuario5','password5','Eduardo','Martínez','P'),(6,'ejemplo_usuario','contrasena_segura','Ejemplo','Usuario','T'),(7,'ejemplo_usuario','contrasena_segura','Ejemplo','Usuario','T'),(8,'ejemplo_usuario','contrasena_segura','Ejemplo','Usuario','A'),(9,'manu','{bcrypt}$2a$10$EyvrqSbAu10kotYUMxVnI.t3yw4ZWZV9ZELIhFELHZhZ9JEP8ffom','Ejemplo','Usuario','T'),(10,'root','{bcrypt}$2a$10$akY.9ZzhjSKJbAdSHSwoROGJizXgALyvWxMG3ltUQWXDOt32Ge.iS','Manuel','Segovia','P'),(11,'root','{bcrypt}$2a$10$vJ9z5DWT9XveNoS8Ob0QOOmCVcHXambyBKOEMRgZ.gx1jUqfqF4l.','Manuel','Segovia','P'),(12,'root','{bcrypt}$2a$10$oBRFRSLuL4gpCMK/jQ4I3ONcrgPy/xxpzuQPcFp7MSqcW1jTBJg4W','Manuel','Segovia','P'),(13,'Tinho','{bcrypt}$2a$10$MzFh0lzpHzdyH0Irz6gPF.gmGz9m.zpA1xYuYpJh2m2ouzdxsqHXq','Roberto','Garcia','P'),(14,'tito','{bcrypt}$2a$10$rUixpzMzukmeV3FfLgABPu8/THW6maBvsJLLSAe9IWljtE7Cf3Mmq','Roberto','Garcia','P');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_chat`
--

DROP TABLE IF EXISTS `usuario_chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_chat` (
  `id_usuario` int NOT NULL,
  `id_chat` int NOT NULL,
  PRIMARY KEY (`id_usuario`,`id_chat`),
  KEY `id_chat` (`id_chat`),
  CONSTRAINT `FK_Usuario_Chat_Usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `usuario_chat_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `usuario_chat_ibfk_2` FOREIGN KEY (`id_chat`) REFERENCES `chat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_chat`
--

LOCK TABLES `usuario_chat` WRITE;
/*!40000 ALTER TABLE `usuario_chat` DISABLE KEYS */;
INSERT INTO `usuario_chat` VALUES (1,1),(2,1),(9,1),(10,1),(14,1),(3,2);
/*!40000 ALTER TABLE `usuario_chat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-01 20:47:36
