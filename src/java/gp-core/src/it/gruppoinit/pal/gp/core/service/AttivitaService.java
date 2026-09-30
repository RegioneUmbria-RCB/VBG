/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.AttivitaDAO;
import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
public interface AttivitaService extends BaseService<Attivita, AttivitaId> {

    /**
     * @param codicesettore
     * @return List<Attivita> : lista delle attività di un settore
     */
    public List<Attivita> findAttivitaBySettore(String codicesettore, String orderByProperty);

    /**
     * @see AttivitaDAO#findByFilter(Attivita attivita)
     */
    public List<Attivita> findByFilter(Attivita attivita);

    /**
     * @see AttivitaDAO#findAll(Integer, Integer)
     */
    public List<Attivita> findAll(Integer firstResult, Integer maxResult);

    /**
     * Cerca se ci sono record sulla tabella per la filter table passata. Se non sono stati trovati records allora cerca
     * se nella configurazione è stato settato il parametro USAATTIVITA_TT. Se il parametro è true allora esegue una
     * ricerca dei records anche nel software TT
     * 
     * @see BaseDAO#existsRecords(FilterTable)
     */
    public boolean existsRecords();

    @DeletableCacheElements
    public void resetObjectCached();
}
