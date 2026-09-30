package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.VwIAttivitaautorizzazioniDAO;
import it.gruppoinit.pal.gp.core.domain.VwIAttivitaautorizzazioni;
import it.gruppoinit.pal.gp.core.domain.VwIAttivitaautorizzazioniId;

import java.util.List;

/**
 * 
 * @author
 */
public interface VwIAttivitaautorizzazioniService extends BaseService<VwIAttivitaautorizzazioni, VwIAttivitaautorizzazioniId> {

    /**
     * @see VwIAttivitaautorizzazioniDAO#findAll(Integer, Integer)
     */
    public List<VwIAttivitaautorizzazioni> findAll(Integer firstResult, Integer maxResult);

    /**
     * Rirtorna il numero delle VwIattivitaautorizzazioni legate all'attività
     * 
     * @param codiceAttivita
     * @returnù
     * 
     */
    public int countByAttivita(Integer codiceAttivita);
}
