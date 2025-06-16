SELECT crd,sum(broken_cre_count) as total,max(broken_cre_count) as max,
round(std(broken_cre_count),2) as std, round(avg(broken_cre_count),2) as average  FROM dependency_evaluation.broken_cre_results
where type = "anti" 
group by crd
order by average desc;