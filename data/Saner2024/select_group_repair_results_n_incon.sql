SELECT models.idsmodels, number_of_incon,
round(avg(total_repairs),2) as avg_repairs, 
round(std(total_repairs),2) as std_repair,
round(avg(overlapping_repairs),2) as avg_over, round(std(overlapping_repairs),2) as std_over, 
round(avg(conflicting_repairs),2) as avg_conflicting, round(std(conflicting_repairs),2) as std_conflicting,
round(avg(shared_locations),2) as avg_shared_locations, round(std(shared_locations),2) as std_shared_locations
FROM saner_24.group_repair_results,  saner_24.models where model_name = name
group by model_name,number_of_incon
order by models.idsmodels,number_of_incon;