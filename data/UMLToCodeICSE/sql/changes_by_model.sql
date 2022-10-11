SELECT model_name,systems.total_size as size,change_type, changes_results.type, avg(time) as time,sum(result=false) as inconsistencies, count(cre) as cres
FROM changes_results 
INNER JOIN systems ON systems.name=changes_results.model_name group by model_name,change_type;