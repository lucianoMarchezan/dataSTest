SELECT models.idsmodels as model_id,models.size as model_size, type as group_type,count(group_name) as "groups",
round(avg(group_results.size),2) as average ,
round(std(group_results.size),2) as std

FROM saner_24.group_results,saner_24.models where model_name = models.name
group by model_name,type order by models.size desc,group_type;