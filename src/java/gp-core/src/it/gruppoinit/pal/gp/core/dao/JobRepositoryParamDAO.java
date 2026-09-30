package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.JobRepositoryParam;

import java.util.List;

/**
 * 
 * @author
 */
public interface JobRepositoryParamDAO extends BaseDAO<JobRepositoryParam, Integer> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<JobRepositoryParam> findAll(Integer firstResult, Integer maxResult);
}
