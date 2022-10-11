SELECT model_name,systems.java_classes_count as java_elements, rowCount as traced_java_classes, systems.uml_classes_count as uml_elements, columnCount as traced_uml_classes, totalTime
FROM traces_results 
INNER JOIN systems ON systems.name=traces_results.model_name where traceMatrixName = 'matrixClass' group by model_name;