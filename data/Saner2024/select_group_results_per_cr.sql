SELECT repair_results.cre, round(avg(number_of_incon),2) as avg_n_incon
,round(avg(total_repairs),2) as avg_repairs, 
round(std(total_repairs),2) as std_repair,
round(avg(overlapping_repairs),2) as avg_over, round(std(overlapping_repairs),2) as std_over, 
round(avg(conflicting_repairs),2) as avg_conflicting, round(std(conflicting_repairs),2) as std_conflicting,
round(avg(shared_locations),2) as avg_shared_locations, round(std(shared_locations),2) as std_shared_locations
FROM saner_24.group_repair_results,  saner_24.repair_results where total_repairs = repairs
group by repair_results.cre
order by repair_results.cre;