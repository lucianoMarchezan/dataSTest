SET @row_number = 0;   

SELECT  (@row_number:=@row_number + 1) AS Id ,size,
cres,concat(cre_dependencies, concat(concat("\\bluewhitebar{",Round((cre_dependencies)/cres,3)),"}")) as CREs_percent, 
inconsistencies,concat(incon_dependencies,concat(concat("\\redwhitebar{",Round((incon_dependencies)/inconsistencies,3)),"}")) as incon_percent,
groups_cres,concat(groups_w_inco,concat(concat("\\greenwhitebar{",Round((groups_w_inco)/groups_cres,3)),"}")) as groups_percent,
round(cre_depen_time/cres/1000,2) as avg_time_ms FROM sosym_25.models order by size desc;