SELECT model_name,systems.java_fields_count as java_fields, rowCount as traced_java_fields, systems.uml_fields_count as uml_fields, columnCount as traced_uml_fields, totalTime,averageTime
FROM traces_results 
INNER JOIN systems ON systems.name=traces_results.model_name where traceMatrixName = 'matrixField' group by model_name;