package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Taskbase;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface TaskbaseDAO extends BaseDAO<Taskbase, String> {

    /**
     * Ricerca tutti i Tasbase ordinandoli per task
     */
    public List<Taskbase> findAll(Integer firstResult, Integer maxResult);
}
