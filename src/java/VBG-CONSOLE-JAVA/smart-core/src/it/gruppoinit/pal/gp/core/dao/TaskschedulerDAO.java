package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskscheduler;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface TaskschedulerDAO extends BaseDAO<Taskscheduler, PkId> {

    /**
     * Ricerca tutti i Taskscheduler ordinandoli per descrizione
     */
    public List<Taskscheduler> findAll(Integer firstResult, Integer maxResult);
}
