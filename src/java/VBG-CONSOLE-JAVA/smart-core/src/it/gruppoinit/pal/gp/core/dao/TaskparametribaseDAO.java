package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskparametribase;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface TaskparametribaseDAO extends BaseDAO<Taskparametribase, PkId> {

    /**
     * Ricerca tutti i Taskparametribase ordinati per il campo ordine
     */
    public List<Taskparametribase> findAll(Integer firstResult, Integer maxResult);
}
