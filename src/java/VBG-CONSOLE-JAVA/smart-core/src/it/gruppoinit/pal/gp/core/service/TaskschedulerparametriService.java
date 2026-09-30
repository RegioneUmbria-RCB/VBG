package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TaskschedulerparametriDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface TaskschedulerparametriService extends BaseService<Taskschedulerparametri, PkId> {

    /**
     * @see TaskschedulerparametriDAO#findAll(Integer, Integer)
     */
    public List<Taskschedulerparametri> findAll(Integer firstResult, Integer maxResult);
}
