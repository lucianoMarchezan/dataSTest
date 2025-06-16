SELECT crd_name, sum(number_of_cre_dependencies) as total, round(avg(number_of_cre_dependencies),2) as average, 
round(std(number_of_cre_dependencies),2) as std 
FROM dependency_evaluation.cre_results where result = 0;
-- group by crd_name;