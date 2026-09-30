package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.JobRepository;

/**
 * 
 * @author fabrizioc
 */
public interface JobRepositoryService extends BaseService<JobRepository, Integer> {

    public List<JobRepository> findAll(Integer firstResult, Integer maxResult);
}
