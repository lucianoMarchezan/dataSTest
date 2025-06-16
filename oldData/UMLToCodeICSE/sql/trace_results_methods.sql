SELECT model_name,systems.java_methods_count as java_elements, rowCount as traced_java_methods, systems.uml_methods_count as uml_elements, columnCount as traced_uml_methods, totalTime
FROM traces_results 
INNER JOIN systems ON systems.name=traces_results.model_name where traceMatrixName = 'matrixMethod' group by model_name;