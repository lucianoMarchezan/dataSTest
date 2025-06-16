SELECT crd_name, sum(number_of_cre_dependencies) as total, round(avg(number_of_cre_dependencies),2) as average ,
round(std(number_of_cre_dependencies),2) as std
 FROM sosym_25.cre_results
group by crd_name
order by crd_name asc;

