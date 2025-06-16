CREATE TABLE `cre_results` (
  `idcres` int NOT NULL AUTO_INCREMENT,
  `model_name` varchar(45) DEFAULT NULL,
  `cre` varchar(1500) DEFAULT NULL,
  `crd_name` varchar(50) DEFAULT NULL,
  `instance` varchar(500) DEFAULT NULL,
  `result` boolean DEFAULT NULL, 
  `time` bigint DEFAULT NULL,
  `number_of_cre_dependencies` int DEFAULT NULL, 
  `number_of_incon_dependencies` int DEFAULT NULL, 
  `dependencies` text DEFAULT NULL,
  PRIMARY KEY (`idcres`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


