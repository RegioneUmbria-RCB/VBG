package it.gruppoinit.pal.gp.core.features.scheduler;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;

/**
 * 
 * @author Luca Proietti
 */
public interface TaskschedulerparametriDAO extends BaseDAO<Taskschedulerparametri, PkId> {

    /**
     * Ricerca tutti i Taskschedulerparametri ordinandoli per id.parametro
     */
    public List<Taskschedulerparametri> findAll(Integer firstResult, Integer maxResult);

    public List<Taskschedulerparametri> findByTaskId(Integer codice);
}
