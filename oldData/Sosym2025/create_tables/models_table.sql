CREATE TABLE `models` (
  `idsmodels` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `language` varchar(45) DEFAULT NULL,
  `size` int DEFAULT NULL, 
  `cres` int DEFAULT NULL,  
  `inconsistencies` int DEFAULT NULL,  
  `cr_dependencies` int DEFAULT NULL,  
  `cre_dependencies` int DEFAULT NULL, 
  `incon_dependencies` int DEFAULT NULL,    
  `groups_cres` int DEFAULT NULL,   
  `groups_w_inco` int DEFAULT NULL,
  `groups_only_inco` int DEFAULT NULL,
  `cr_depen_time` bigint DEFAULT NULL,
  `cre_depen_time` bigint DEFAULT NULL,
  PRIMARY KEY (`idsmodels`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
