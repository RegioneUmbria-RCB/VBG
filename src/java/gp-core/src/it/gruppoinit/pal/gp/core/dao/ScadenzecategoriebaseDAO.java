package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Scadenzecategoriebase;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface ScadenzecategoriebaseDAO extends BaseDAO<Scadenzecategoriebase, PkId> {

    /**
     * Lista delle scadenza categorie base
     * 
     */
    public List<Scadenzecategoriebase> findAll(Integer firstResult, Integer maxResult);
}
