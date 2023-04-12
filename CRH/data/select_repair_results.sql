SELECT cr1,sum(overlapping) as total,max(overlapping) as max,
round(std(overlapping),2) as std, round(avg(overlapping),2) as average,
sum(conflicting) as total_c,max(conflicting) as max_c,
round(std(conflicting),2) as std_c, round(avg(conflicting),2) as average_c
FROM dependency_evaluation.repair_results 
group by cr1
order by total desc;