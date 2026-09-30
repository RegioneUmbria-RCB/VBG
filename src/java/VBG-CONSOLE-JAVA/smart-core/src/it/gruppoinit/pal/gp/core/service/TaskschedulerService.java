package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TaskschedulerDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskscheduler;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface TaskschedulerService extends BaseService<Taskscheduler, PkId> {

    /**
     * @see TaskschedulerDAO#findAll(Integer, Integer)
     */
    public List<Taskscheduler> findAll(Integer firstResult, Integer maxResult);

    /**
     * Effettua il salvataggio dei parametri dell'operazione pianificata
     * 
     * @param entity
     */
    public void saveParametri(Taskscheduler entity);
}
