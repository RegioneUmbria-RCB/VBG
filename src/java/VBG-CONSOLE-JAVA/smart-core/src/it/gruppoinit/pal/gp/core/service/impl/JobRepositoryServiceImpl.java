package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.JobRepositoryDAO;
import it.gruppoinit.pal.gp.core.domain.JobRepository;
import it.gruppoinit.pal.gp.core.service.JobRepositoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author fabrizioc
 */
@Service
public class JobRepositoryServiceImpl extends BaseServiceImpl<JobRepository, Integer> implements JobRepositoryService {

    private JobRepositoryDAO jobrepositoryDAO;

    @Autowired
    public void setJobRepositoryDAO(JobRepositoryDAO jobrepositoryDAO) {

	this.jobrepositoryDAO = jobrepositoryDAO;
    }

    @Override
    protected Class<JobRepository> getEntityClass() {

	return JobRepository.class;
    }

    @Override
    public List<JobRepository> findAll(Integer firstResult, Integer maxResult) {

	return jobrepositoryDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(JobRepository entity) {

	if (validateEntity(entity)) {
	    jobrepositoryDAO.insert(entity);
	}
    }

    @Override
    public JobRepository findById(Integer id) {

	return jobrepositoryDAO.findById(id);
    }

    @Override
    public void update(JobRepository entity) {

	if (validateEntity(entity)) {
	    jobrepositoryDAO.update(entity);
	}
    }

    @Override
    public void delete(JobRepository entity) {

	if (isDeleteAllowed(entity)) {
	    jobrepositoryDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(JobRepository entity) {

	return true;
    }
}
