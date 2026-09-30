package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.VwIAttivitaautorizzazioni;
import it.gruppoinit.pal.gp.core.domain.VwIAttivitaautorizzazioniId;

import java.util.List;

/**
 * 
 * @author
 */
public interface VwIAttivitaautorizzazioniDAO extends BaseDAO<VwIAttivitaautorizzazioni, VwIAttivitaautorizzazioniId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<VwIAttivitaautorizzazioni> findAll(Integer firstResult, Integer maxResult);
}
