package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.JobRepository;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface JobRepositoryDAO extends BaseDAO<JobRepository, Integer> {

    /**
     * metodo per recuperare tutti i job schedulati
     * 
     */
    public List<JobRepository> findAll(Integer firstResult, Integer maxResult);
}
