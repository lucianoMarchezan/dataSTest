SELECT model_name,systems.java_fields_count as java_elements, rowCount as traced_java_fields, systems.uml_fields_count as uml_elements, columnCount as traced_uml_fields, totalTime
FROM traces_results 
INNER JOIN systems ON systems.name=traces_results.model_name where traceMatrixName = 'matrixField' group by model_name;