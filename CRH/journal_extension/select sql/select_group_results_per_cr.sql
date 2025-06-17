SELECT repair_results.cre,
round(avg(total_repairs),2) as avg_repairs, 
round(std(total_repairs),2) as std_repair,
round(avg(overlapping_repairs),2) as avg_over, round(std(overlapping_repairs),2) as std_over, 
round(avg(conflicting_repairs),2) as avg_conflicting, round(std(conflicting_repairs),2) as std_conflicting,
round(avg(shared_locations),2) as avg_shared_locations, round(std(shared_locations),2) as std_shared_locations
FROM sosym_25.group_repair_results,  sosym_25.repair_results where number_of_incon > 2 and number_of_incon < 12
group by repair_results.cre
order by repair_results.cre;