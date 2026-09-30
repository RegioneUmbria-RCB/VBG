package it.gruppoinit.pal.gp.core.features.scheduler;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskscheduler;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * 
 * @author Luca Proietti
 */
public interface TaskschedulerService extends BaseService<Taskscheduler, PkId> {

    /**
     * @see TaskschedulerDAO#findAll(Integer, Integer)
     */
    public List<Taskscheduler> findAll(Integer firstResult, Integer maxResult);

    public List<TaskBean> findAll();

    /**
     * Effettua il salvataggio dei parametri dell'operazione pianificata
     * 
     * @param entity
     */
    public void saveParametri(Taskscheduler entity);

    public void elabora(Taskscheduler task, boolean aggiornaProssimaEsecuzione);

    public void elabora(boolean aggiornaProssimaEsecuzione);
}
