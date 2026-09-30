package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AutorizzazioniCsi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AutorizzazioniCsiDAO extends BaseDAO<AutorizzazioniCsi, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<AutorizzazioniCsi> findAll(Integer firstResult, Integer maxResult);
}
