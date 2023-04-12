SELECT crd_name, sum(number_of_cre_dependencies) as total,max(number_of_cre_dependencies) as max,
round(std(number_of_cre_dependencies),2) as std, round(avg(number_of_cre_dependencies),2) as average 
 FROM dependency_evaluation.cre_results
group by crd_name
order by total desc;

