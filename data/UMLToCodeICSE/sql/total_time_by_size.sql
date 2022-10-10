SELECT time, model_name, 
systems.total_size
From cres_results 
INNER JOIN systems 
ON systems.name = cres_results.model_name;