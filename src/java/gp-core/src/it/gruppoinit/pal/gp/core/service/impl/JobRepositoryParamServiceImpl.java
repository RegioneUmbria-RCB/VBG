package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.JobRepositoryParamDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.JobRepository;
import it.gruppoinit.pal.gp.core.domain.JobRepositoryParam;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.JobRepositoryParamService;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class JobRepositoryParamServiceImpl extends BaseServiceImpl<JobRepositoryParam, Integer> implements JobRepositoryParamService {

    public static final Logger log = LoggerFactory.getLogger(JobRepositoryParamServiceImpl.class);
    private JobRepositoryParamDAO jobrepositoryparamDAO;

    @Autowired
    public void setJobRepositoryParamDAO(JobRepositoryParamDAO jobrepositoryparamDAO) {

	this.jobrepositoryparamDAO = jobrepositoryparamDAO;
    }

    @Override
    protected Class<JobRepositoryParam> getEntityClass() {

	return JobRepositoryParam.class;
    }

    @Override
    public List<JobRepositoryParam> findAll(Integer firstResult, Integer maxResult) {

	return jobrepositoryparamDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(JobRepositoryParam entity) {

	if (validateEntity(entity)) {
	    jobrepositoryparamDAO.insert(entity);
	}
    }

    @Override
    public JobRepositoryParam findById(Integer id) {

	return jobrepositoryparamDAO.findById(id);
    }

    @Override
    public void update(JobRepositoryParam entity) {

	if (validateEntity(entity)) {
	    jobrepositoryparamDAO.update(entity);
	}
    }

    @Override
    public void delete(JobRepositoryParam entity) {

	if (isDeleteAllowed(entity)) {
	    jobrepositoryparamDAO.delete(entity);
	}
    }

    @Override
    public void insertListParametri(Set<JobRepositoryParam> jobRepositoryParams) {

	for (JobRepositoryParam jobRepositoryParam : jobRepositoryParams) {
	    jobrepositoryparamDAO.insert(jobRepositoryParam);
	}
    }

    @Override
    public void insertOrUpdateListParametri(Set<JobRepositoryParam> jobRepositoryParams) {

	for (JobRepositoryParam jobRepositoryParam : jobRepositoryParams) {
	    if (jobRepositoryParam.getId() != null) {
		if (StringUtils.isNotBlank(jobRepositoryParam.getValore())) {
		    jobrepositoryparamDAO.update(jobRepositoryParam);
		} else {
		    jobrepositoryparamDAO.delete(jobRepositoryParam);
		}
	    } else {
		if (StringUtils.isNotBlank(jobRepositoryParam.getValore())) {
		    jobrepositoryparamDAO.insert(jobRepositoryParam);
		}
	    }
	}
    }

    @Override
    public void deleteListParametri(Set<JobRepositoryParam> jobRepositoryParams) {

	for (JobRepositoryParam jobRepositoryParam : jobRepositoryParams) {
	    if (jobRepositoryParam.getId() != null) {
		jobrepositoryparamDAO.delete(jobRepositoryParam);
	    }
	}
    }

    //    protected boolean isDeleteAllowed(JobRepositoryParam entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
    @Override
    public Set<JobRepositoryParam> recuperaParametriJob(JobRepository jobrepository) {

	Set<JobRepositoryParam> jobRepositoryParams = new HashSet<JobRepositoryParam>();
	JobRepositoryParam jobRepositoryParam = null;
	Class<?> c;
	if (jobrepository != null && StringUtils.isNotBlank(jobrepository.getJobClassName())) {
	    try {
		c = Class.forName(jobrepository.getJobClassName());
		Object obj = c.newInstance();
		Class noparams[] = {};
		Method method = c.getDeclaredMethod("getParam", noparams);
		Map<String, String> m = (Map<String, String>) method.invoke(obj, null);
		for (Map.Entry<String, String> entry : m.entrySet()) {
		    jobRepositoryParam = new JobRepositoryParam();
		    jobRepositoryParam.setEtichetta(entry.getKey());
		    jobRepositoryParam.setDescrizione(entry.getValue());
		    jobRepositoryParam.setJobRepository(jobrepository);
		    jobRepositoryParams.add(jobRepositoryParam);
		}
	    } catch (Exception e) {
		log.error("Errore durante il recupero dei parametri del job {} . Errore: {}",
			new Object[] { jobrepository.getJobClassName(), e.getMessage(), e });
		throw new RuntimeException(
			"Errore durante il recupero dei parametri del job" + jobrepository.getJobClassName() + ".Errore: " + e.getMessage(), e);
	    }
	}
	return jobRepositoryParams;
    }

    @Override
    public List<JobRepositoryParam> findByJobRepository(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id", codice, "jobRepository", Integer.class));
	ft.addRestriction(fr);
	return jobrepositoryparamDAO.findByFilterTable(ft);
    }
}
