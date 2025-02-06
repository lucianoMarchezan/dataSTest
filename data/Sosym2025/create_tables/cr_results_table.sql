CREATE TABLE `cr_results` (
  `idcrs` int NOT NULL AUTO_INCREMENT,
  `model_name` varchar(45) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `context` varchar(500) DEFAULT NULL,
  `def` varchar(1500) DEFAULT NULL,
  `number_of_dependencies` int DEFAULT NULL,
  `dependencies` varchar(1500) DEFAULT NULL,
  PRIMARY KEY (`idcrs`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


