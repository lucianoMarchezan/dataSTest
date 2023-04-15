SELECT cr1, round((avg(cre1_repairs)+avg(cre2_repairs))/2,2) as avg_repair, 
round((std(cre1_repairs)+std(cre2_repairs))/2,2) as std_repair,
round(avg(overlapping),2) as avg_over,
round(std(overlapping),2) as std_over, round(avg(conflicting),2) as average_c,
round(std(conflicting),2) as std_c
FROM dependency_evaluation.repair_results 
group by cr1
order by cr1;