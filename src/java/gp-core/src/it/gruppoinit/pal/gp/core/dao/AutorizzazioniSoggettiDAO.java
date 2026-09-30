package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSoggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AutorizzazioniSoggettiDAO extends BaseDAO<AutorizzazioniSoggetti, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<AutorizzazioniSoggetti> findAll(Integer firstResult, Integer maxResult);
}
