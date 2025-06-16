SELECT model_name,systems.java_classes_count + systems.java_methods_count + systems.java_fields_count as size, sum(rowCount) as traced, sum(totalTime) as total_time,
sum(totalTime)/(systems.java_classes_count + systems.java_methods_count + systems.java_fields_count) as avg_time
FROM traces_results 
INNER JOIN systems ON systems.name=traces_results.model_name  group by model_name;