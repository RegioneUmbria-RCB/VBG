package it.gruppoinit.pal.gp.core.features.scheduler;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * 
 * @author Luca Proietti
 */
public interface TaskschedulerparametriService extends BaseService<Taskschedulerparametri, PkId> {

    /**
     * @see TaskschedulerparametriDAO#findAll(Integer, Integer)
     */
    public List<Taskschedulerparametri> findAll(Integer firstResult, Integer maxResult);

    public List<Taskschedulerparametri> findByTaskId(Integer codice);
}
