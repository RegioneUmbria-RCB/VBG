package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.JobRepositoryParamDAO;
import it.gruppoinit.pal.gp.core.domain.JobRepository;
import it.gruppoinit.pal.gp.core.domain.JobRepositoryParam;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author
 */
public interface JobRepositoryParamService extends BaseService<JobRepositoryParam, Integer> {

    /**
     * @see JobRepositoryParamDAO#findAll(Integer, Integer)
     */
    public List<JobRepositoryParam> findAll(Integer firstResult, Integer maxResult);

    public void insertListParametri(Set<JobRepositoryParam> jobRepositoryParams);

    public void insertOrUpdateListParametri(Set<JobRepositoryParam> jobRepositoryParams);

    public void deleteListParametri(Set<JobRepositoryParam> jobRepositoryParams);

    public Set<JobRepositoryParam> recuperaParametriJob(JobRepository jobrepository);

    public List<JobRepositoryParam> findByJobRepository(Integer codice);
}
