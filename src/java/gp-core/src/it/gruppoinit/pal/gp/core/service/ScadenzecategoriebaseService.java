package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ScadenzecategoriebaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Scadenzecategoriebase;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface ScadenzecategoriebaseService extends BaseService<Scadenzecategoriebase, PkId> {

    /**
     * @see ScadenzecategoriebaseDAO#findAll(Integer, Integer)
     */
    public List<Scadenzecategoriebase> findAll(Integer firstResult, Integer maxResult);
}
