CREATE TABLE `group_results` (
  `idgroups` int NOT NULL AUTO_INCREMENT,
  `group_name` text DEFAULT NULL,  
  `model_name` varchar(45) DEFAULT NULL,   
  `type` int DEFAULT NULL,
  `size` int DEFAULT NULL,
  PRIMARY KEY (`idgroups`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
