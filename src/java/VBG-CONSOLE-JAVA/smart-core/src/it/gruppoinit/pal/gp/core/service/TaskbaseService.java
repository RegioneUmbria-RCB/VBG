package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TaskbaseDAO;
import it.gruppoinit.pal.gp.core.domain.Taskbase;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface TaskbaseService extends BaseService<Taskbase, String> {

    /**
     * @see TaskbaseDAO#findAll(Integer, Integer)
     */
    public List<Taskbase> findAll(Integer firstResult, Integer maxResult);
}
