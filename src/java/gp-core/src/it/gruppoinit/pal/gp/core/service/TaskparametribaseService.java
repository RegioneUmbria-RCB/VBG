package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.TaskparametribaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskparametribase;

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
