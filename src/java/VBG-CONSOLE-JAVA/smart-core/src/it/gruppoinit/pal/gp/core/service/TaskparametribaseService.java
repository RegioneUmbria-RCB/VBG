package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TaskparametribaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskparametribase;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface TaskparametribaseService extends BaseService<Taskparametribase, PkId> {

    /**
     * @see TaskparametribaseDAO#findAll(Integer, Integer)
     */
    public List<Taskparametribase> findAll(Integer firstResult, Integer maxResult);
}
