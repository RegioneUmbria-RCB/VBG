package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.JobRepository;

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

    public boolean isJobAttivo(String className);

    public List<JobRepository> findByClassName(String className);
}
