-- MySQL dump 10.13  Distrib 8.4.3, for Linux (x86_64)
--
-- Host: localhost    Database: sst_db
-- ------------------------------------------------------
-- Server version	8.4.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `total_price` decimal(10,2) NOT NULL,
  `end_date` datetime(6) NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `id` binary(16) NOT NULL,
  `payment_id` binary(16) DEFAULT NULL,
  `tool_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `status` enum('CANCELLED','COMPLETED','CONFIRMED','PENDING') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfv61wputonkip8a2m66ivrujq` (`tool_id`),
  KEY `FKeyog2oic85xg7hsu2je2lx3s6` (`user_id`),
  CONSTRAINT `FKeyog2oic85xg7hsu2je2lx3s6` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKfv61wputonkip8a2m66ivrujq` FOREIGN KEY (`tool_id`) REFERENCES `tools` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (65000.00,'2024-03-02 18:00:00.000000','2024-03-01 08:00:00.000000',_binary '��\��j\�\�&+\�',NULL,_binary '����������������',_binary 'DDDDDDDDDDDDDDDD','COMPLETED'),(130000.00,'2024-03-07 18:00:00.000000','2024-03-05 08:00:00.000000',_binary '����j\�\�&+\�',NULL,_binary '����������������',_binary 'UUUUUUUUUUUUUUUU','COMPLETED'),(325000.00,'2024-04-15 18:00:00.000000','2024-04-10 08:00:00.000000',_binary '��^�j\�\�&+\�',NULL,_binary '����������������',_binary 'DDDDDDDDDDDDDDDD','CONFIRMED'),(130000.00,'2024-05-03 18:00:00.000000','2024-05-01 08:00:00.000000',_binary '��~dj\�\�&+\�',NULL,_binary '����������������',_binary 'DDDDDDDDDDDDDDDD','COMPLETED'),(65000.00,'2024-05-06 18:00:00.000000','2024-05-05 08:00:00.000000',_binary '���Sj\�\�&+\�',NULL,_binary '����������������',_binary 'UUUUUUUUUUUUUUUU','COMPLETED'),(325000.00,'2024-05-15 18:00:00.000000','2024-05-10 08:00:00.000000',_binary '���(j\�\�&+\�',NULL,_binary '����������������',_binary 'DDDDDDDDDDDDDDDD','COMPLETED'),(130000.00,'2024-06-03 18:00:00.000000','2024-06-01 08:00:00.000000',_binary '����j\�\�&+\�',NULL,_binary '����������������',_binary 'UUUUUUUUUUUUUUUU','COMPLETED'),(130000.00,'2024-06-12 18:00:00.000000','2024-06-10 08:00:00.000000',_binary '���\0j\�\�&+\�',NULL,_binary '����������������',_binary 'DDDDDDDDDDDDDDDD','COMPLETED'),(260000.00,'2024-07-05 18:00:00.000000','2024-07-01 08:00:00.000000',_binary '���Zj\�\�&+\�',NULL,_binary '����������������',_binary 'UUUUUUUUUUUUUUUU','COMPLETED'),(65000.00,'2024-07-16 18:00:00.000000','2024-07-15 08:00:00.000000',_binary '����j\�\�&+\�',NULL,_binary '����������������',_binary 'DDDDDDDDDDDDDDDD','COMPLETED'),(65000.00,'2024-08-02 18:00:00.000000','2024-08-01 08:00:00.000000',_binary '���j\�\�&+\�',NULL,_binary '����������������',_binary 'UUUUUUUUUUUUUUUU','COMPLETED'),(325000.00,'2024-08-15 18:00:00.000000','2024-08-10 08:00:00.000000',_binary '����j\�\�&+\�',NULL,_binary '����������������',_binary 'DDDDDDDDDDDDDDDD','COMPLETED'),(260000.00,'2024-09-05 18:00:00.000000','2024-09-01 08:00:00.000000',_binary '���\�j\�\�&+\�',NULL,_binary '����������������',_binary 'UUUUUUUUUUUUUUUU','COMPLETED'),(195000.00,'2024-01-13 18:00:00.000000','2024-01-10 08:00:00.000000',_binary '�\0\0\0\0\0\0\0\0\0\0\0\0\0',NULL,_binary '����������������',_binary 'DDDDDDDDDDDDDDDD','COMPLETED'),(325000.00,'2024-02-06 18:00:00.000000','2024-02-01 08:00:00.000000',_binary '�\0\0\0\0\0\0\0\0\0\0\0\0\0',NULL,_binary '����������������',_binary 'UUUUUUUUUUUUUUUU','COMPLETED');
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `damage_reports`
--

DROP TABLE IF EXISTS `damage_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `damage_reports` (
  `is_repaired` bit(1) NOT NULL,
  `repair_cost` decimal(10,2) DEFAULT NULL,
  `report_date` datetime(6) NOT NULL,
  `booking_id` binary(16) NOT NULL,
  `id` binary(16) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `damage_reports`
--

LOCK TABLES `damage_reports` WRITE;
/*!40000 ALTER TABLE `damage_reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `damage_reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `amount` decimal(10,2) NOT NULL,
  `payment_date` datetime(6) NOT NULL,
  `booking_id` binary(16) NOT NULL,
  `id` binary(16) NOT NULL,
  `method` enum('CASH','CREDIT_CARD','PAYPAL','TRANSFER') NOT NULL,
  `status` enum('COMPLETED','FAILED','PENDING','REFUNDED') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (195000.00,'2024-01-10 09:00:00.000000',_binary '�\0\0\0\0\0\0\0\0\0\0\0\0\0',_binary '���Lj\�\�&+\�','CREDIT_CARD','COMPLETED'),(325000.00,'2024-02-01 08:30:00.000000',_binary '�\0\0\0\0\0\0\0\0\0\0\0\0\0',_binary '���[j\�\�&+\�','PAYPAL','COMPLETED'),(65000.00,'2024-03-01 08:00:00.000000',_binary '��\��j\�\�&+\�',_binary '��9j\�\�&+\�','CASH','COMPLETED'),(130000.00,'2024-03-05 08:00:00.000000',_binary '����j\�\�&+\�',_binary '��I�j\�\�&+\�','TRANSFER','COMPLETED'),(325000.00,'2024-04-10 08:00:00.000000',_binary '��^�j\�\�&+\�',_binary '���\�j\�\�&+\�','CREDIT_CARD','COMPLETED');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tools`
--

DROP TABLE IF EXISTS `tools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tools` (
  `price_per_day` decimal(10,2) NOT NULL,
  `stock` int NOT NULL DEFAULT '1',
  `id` binary(16) NOT NULL,
  `provider_id` binary(16) NOT NULL,
  `description` text,
  `image_url` longtext,
  `name` varchar(255) NOT NULL,
  `status` enum('AVAILABLE','DELETED','MAINTENANCE','RENTED') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tools`
--

LOCK TABLES `tools` WRITE;
/*!40000 ALTER TABLE `tools` DISABLE KEYS */;
INSERT INTO `tools` VALUES (45000.00,8,_binary '���\�j\�\�&+\�',_binary '\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"','Taladro industrial 20V MAX XR de alto rendimiento. Ideal para concreto y mampostería pesada. Incluye 2 baterías y cargador rápido.','/herramientas/taladro.jpeg','Taladro Percutor Dewalt','AVAILABLE'),(60000.00,5,_binary '���\�j\�\�&+\�',_binary '\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"','Sierra de 7-1/4 pulgadas, motor de 1800W. Corte preciso en madera y aglomerados. Guía láser incorporada para cortes rectos.','/herramientas/sierra.jpeg','Sierra Circular Makita','AVAILABLE'),(35000.00,6,_binary '���]j\�\�&+\�',_binary '\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"','Lijadora roto-orbital profesional con filtro microfilter. Perfecta para acabados finos en madera y metal. Velocidad variable.','/herramientas/lijadora.jpeg','Lijadora Orbital Bosch','AVAILABLE'),(180000.00,0,_binary '����j\�\�&+\�',_binary '\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"','Rompedor de concreto TE-3000. La bestia para demolición de pisos y losas. Sistema de reducción de vibración AVR.','/herramientas/demoledor.jpeg','Martillo Demoledor Hilti','RENTED'),(12000.00,50,_binary '����j\�\�&+\�',_binary '\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"','Cuerpo de andamio certificado estándar 1.5m x 1.5m. Incluye crucetas. Ideal para trabajos en altura y fachada.','/herramientas/andamio.png','Andamio Tubular (Cuerpo)','AVAILABLE'),(25000.00,4,_binary '���=j\�\�&+\�',_binary '\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"','Escalera de aluminio extensible hasta 6 metros. Ligera y fácil de transportar. Patas antideslizantes de seguridad.','/herramientas/escalera.jpeg','Escalera Telescópica','AVAILABLE'),(120000.00,1,_binary '���xj\�\�&+\�',_binary '\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"','Planta eléctrica a gasolina 5000W. Arranque manual y eléctrico. Autonomía de 8 horas. Ideal para obras sin conexión a red.','/herramientas/generador.jpeg','Generador Eléctrico Honda','MAINTENANCE'),(55000.00,3,_binary '����j\�\�&+\�',_binary '\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"','Compresor de 50 Litros, motor 2HP. Ideal para pintura y herramientas neumáticas ligeras. Presión máxima 115 PSI.','/herramientas/compresor.jpeg','Compresor de Aire','AVAILABLE'),(90000.00,2,_binary '���\�j\�\�&+\�',_binary '\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"','Máquina de lavado a presión 3000 PSI a gasolina. Perfecta para limpieza de fachadas, maquinaria y pisos industriales.','/herramientas/hidrolavadora.jpeg','Hidrolavadora Industrial','AVAILABLE'),(75000.00,4,_binary '���%j\�\�&+\�',_binary '\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"','Trompo mezclador de 1 bulto con motor eléctrico 110V/220V. Chasis reforzado para trabajo pesado en obra.','/herramientas/mezcladora.jpeg','Mezcladora de Concreto','AVAILABLE'),(40000.00,6,_binary '����j\�\�&+\�',_binary '3333333333333333','Pulidora grande para trabajo pesado. Disco de 9 pulgadas. Mango antivibración. Ideal para corte de metal estructural.','/herramientas/pulidora.jpeg','Pulidora Angular 9\"','AVAILABLE'),(35000.00,2,_binary '���\�j\�\�&+\�',_binary '3333333333333333','Nivel de líneas cruzadas con alcance de 20m. Autonivelante. Incluye trípode y estuche protector.','/herramientas/nivellaser.jpeg','Nivel Láser Bosch','AVAILABLE'),(28000.00,5,_binary '���j\�\�&+\�',_binary '3333333333333333','Cortadora manual profesional de 60cm. Rodel de carburo de tungsteno. Cortes precisos en cerámica y porcelanato.','/herramientas/cortadora.png','Cortadora de Baldosa','AVAILABLE'),(70000.00,2,_binary '���Aj\�\�&+\�',_binary '3333333333333333','Vibrador a gasolina con manguera de 4 metros. Indispensable para evitar burbujas de aire en el fundido de losas y columnas.','/herramientas/vibrador.jpeg','Vibrador de Concreto','AVAILABLE'),(20000.00,4,_binary '���wj\�\�&+\�',_binary '3333333333333333','Pistola térmica 1800W con temperatura variable (50°C - 600°C). Ideal para decapar pintura y moldear plásticos.','/herramientas/pistola.jpeg','Pistola de Calor','AVAILABLE'),(15000.00,3,_binary '����j\�\�&+\�',_binary '3333333333333333','Cortadora de varilla y pernos. Capacidad de corte hasta 1/2 pulgada. Brazos largos para mayor palanca.','/herramientas/cizalla.jpeg','Cizalla Manual','AVAILABLE'),(12000.00,15,_binary '���\�j\�\�&+\�',_binary '3333333333333333','Carretilla plástica profunda tipo buggy. Capacidad 100 litros. Llanta neumática reforzada.','/herramientas/carretilla.jpeg','Carretilla Buggy','AVAILABLE'),(15000.00,10,_binary '���#j\�\�&+\�',_binary '3333333333333333','Cable encauchetado calibre 12 de 30 metros. Tomas industriales a prueba de agua. Soporta maquinaria pesada.','/herramientas/extension.jpeg','Extensión Eléctrica Ind.','AVAILABLE'),(5000.00,30,_binary '���[j\�\�&+\�',_binary '3333333333333333','Casco certificado tipo ingeniero. Suspensión rachet de 4 puntos. Dieléctrico. Colores variados.','/herramientas/casco.jpeg','Casco de Seguridad','AVAILABLE'),(65000.00,3,_binary '����������������',_binary '3333333333333333','Equipo soldador 200A bi-voltaje (110/220V). Suelda electrodos 6011, 6013 y 7018. Pantalla digital.','/herramientas/soldador.jpeg','Soldador Inverter Elite','AVAILABLE');
/*!40000 ALTER TABLE `tools` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` binary(16) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','CUSTOMER','PROVIDER') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (_binary '','admin@construrenta.com','Super','Admin','$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq','ADMIN'),(_binary '@B\�Q7G��YPE\n�','admin@construrrenta.com','Super','Admin','$2a$10$fEMlhdJbJV/qzMbnI.zI9OWxahP5l43KAQ8Xw7eJSsh//puV6OKa2','ADMIN'),(_binary '\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"','marcela@proveedor.com','Marcela','Albarracin','$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq','PROVIDER'),(_binary '3333333333333333','sebastian@proveedor.com','Sebastian','Jaimes','$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq','PROVIDER'),(_binary 'DDDDDDDDDDDDDDDD','marcela@cliente.com','Marcela','Albarracin','$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq','CUSTOMER'),(_binary 'DDDDDDDDDDDDDDDE','victor@proveedor.com','Victor','Moreno','$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq','PROVIDER'),(_binary 'UUUUUUUUUUUUUUUU','sebastian@cliente.com','Sebastian','Jaimes','$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq','CUSTOMER'),(_binary 'ffffffffffffffff','victor@cliente.com','Victor','Moreno','$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq','CUSTOMER');
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

-- Dump completed on 2026-02-09  3:56:07
