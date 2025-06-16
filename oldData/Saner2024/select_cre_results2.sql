SELECT crd_name, count(crd_name) 
FROM saner_24.cre_results where cre
group by crd_name
order by crd_name asc;