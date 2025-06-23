SELECT  idsmodels AS Id ,Round(cre_dependencies/cres,3) as CREs_percent, 
Round(incon_dependencies/inconsistencies,3) as incon_percent,Round(groups_w_inco/groups_cres,3) as groups_percent
FROM sosym_25.models order by size desc;