package it.gruppoinit.pal.gp.core.dao.impl;

import java.security.InvalidParameterException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.JobRepositoryDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.JobRepository;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

/**
 * 
 * @author fabrizioc
 */
@Repository
public class JobRepositoryDAOImpl extends BaseDAOImpl<JobRepository, Integer> implements JobRepositoryDAO {

    @Override
    public Class<JobRepository> getEntityClass() {

	return JobRepository.class;
    }

    @Override
    public List<JobRepository> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "jobName", DAOOrderTypeEnum.ASC);
    }

    @Override
    public boolean isJobAttivo(String className) {

	if (StringUtils.isBlank(className)) {
	    return false;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("alias", ORMHelper.getIdcomuneAlias(), String.class));
	fr.addFilterField(FilterUtils.equals("jobClassName", className, String.class));
	fr.addFilterField(FilterUtils.equals("active", true, Boolean.class));
	ft.addRestriction(fr);
	List<JobRepository> jobs = findByFilterTable(ft);
	return !jobs.isEmpty();
    }

    @Override
    public List<JobRepository> findByClassName(String className) {

	if (StringUtils.isBlank(className)) {
	    throw new InvalidParameterException("Impossibile utilizzare findByClassName senza passare il parametro className");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("jobClassName", className, String.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }
}
