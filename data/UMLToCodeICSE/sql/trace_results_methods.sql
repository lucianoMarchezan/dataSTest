SELECT model_name,systems.java_methods_count as java_methods, rowCount as traced_java_methods, systems.uml_methods_count as uml_methods, columnCount as traced_uml_methods, totalTime,averageTime
FROM traces_results 
INNER JOIN systems ON systems.name=traces_results.model_name where traceMatrixName = 'matrixMethod' group by model_name;