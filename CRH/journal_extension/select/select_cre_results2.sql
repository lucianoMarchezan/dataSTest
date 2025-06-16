SELECT crd_name, count(crd_name) 
FROM sosym_25.cre_results
group by crd_name
order by crd_name asc;