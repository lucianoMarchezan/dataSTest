SELECT models.idsmodels as model_id,model_name,models.size as model_size, type as group_type,count(group_name) as "groups",
round(avg(group_results.size),2) as average ,
round(std(group_results.size),2) as std
FROM sosym_25.group_results,sosym_25.models where model_name = models.name
group by model_name,type order by models.size desc,group_type;