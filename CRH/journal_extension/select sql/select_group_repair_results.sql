SELECT models.idsmodels, model_name,round(avg(number_of_incon),2) as avg_n_incon,
round(min(total_repairs),2) as min_repair,round(max(total_repairs),2) as max_repair,
round(avg(total_repairs),2) as avg_repairs, round(std(total_repairs),2) as std_repair,
round(min(overlapping_repairs),2) as min_over, round(max(overlapping_repairs),2) as max_over, 
round(avg(overlapping_repairs),2) as avg_over, round(std(overlapping_repairs),2) as std_over, 
round(min(conflicting_repairs),2) as min_conflicting, round(max(conflicting_repairs),2) as max_conflicting,
round(avg(conflicting_repairs),2) as avg_conflicting, round(std(conflicting_repairs),2) as std_conflicting,
round(min(shared_locations),2) as min_shared_locations, round(max(shared_locations),2) as max_shared_locations,
round(avg(shared_locations),2) as avg_shared_locations, round(std(shared_locations),2) as std_shared_locations
FROM sosym_25.group_repair_results,  sosym_25.models where model_name = name
group by model_name
order by models.idsmodels;