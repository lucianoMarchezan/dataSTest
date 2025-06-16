CREATE TABLE `group_repair_results` (
  `idgrepairs` int NOT NULL AUTO_INCREMENT,
  `model_name` varchar(45) DEFAULT NULL,
  `group_name` varchar(500) DEFAULT NULL,   
  `number_of_incon` int DEFAULT NULL,
  `inconsistencies` text DEFAULT NULL,
  `conflicting_repairs` int DEFAULT NULL,
  `overlapping_repairs` int DEFAULT NULL,
  `total_repairs` int DEFAULT NULL,
  `shared_locations` int DEFAULT NULL, 
  `total_locations` int DEFAULT NULL, 
  PRIMARY KEY (`idgrepairs`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


