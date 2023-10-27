SELECT models.idsmodels,sum(repairs) as total,
round(avg(repairs),2) as average ,
round(std(repairs),2) as std
 FROM saner_24.repair_results, saner_24.models where models.name=repair_results.model_name
 group by model_name order by idsmodels;