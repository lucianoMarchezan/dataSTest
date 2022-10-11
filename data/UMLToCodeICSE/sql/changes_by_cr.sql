SELECT crd,change_type,type, avg(time) as time,sum(result=false) as inconsistencies, count(cre) as cres
FROM changes_results group by crd,change_type;