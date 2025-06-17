SELECT models.idsmodels,sum(repair_actions) as total,
round(avg(repair_actions),2) as average ,
round(std(repair_actions),2) as std
 FROM sosym_25.repair_tree_results, sosym_25.models where models.name=repair_tree_results.model_name
 group by model_name order by idsmodels;