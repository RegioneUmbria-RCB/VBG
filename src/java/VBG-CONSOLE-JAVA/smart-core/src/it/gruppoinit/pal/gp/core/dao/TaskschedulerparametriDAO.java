package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface TaskschedulerparametriDAO extends BaseDAO<Taskschedulerparametri, PkId> {

    /**
     * Ricerca tutti i Taskschedulerparametri ordinandoli per id.parametro
     */
    public List<Taskschedulerparametri> findAll(Integer firstResult, Integer maxResult);
}
