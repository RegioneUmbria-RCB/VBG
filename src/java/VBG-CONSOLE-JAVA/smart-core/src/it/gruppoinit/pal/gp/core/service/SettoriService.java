/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.SettoriDAO;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.SettoriId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
public interface SettoriService extends BaseService<Settori, SettoriId> {

    /**
     * @see SettoriDAO#findAll(Integer, Integer)
     */
    public List<Settori> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see SettoriDAO#findByFilter(Settori entity)
     */
    public List<Settori> findByFilter(Settori entity);

    public List<Settori> findByFilterTable(FilterTable filterTable);

    /**
     * <pre>
     * Ritorna tutta la lista dei settori configurati filtrati per idcomune software.
     * @param flagDisattivi:
     * 
     * 	1- True solo quelli non attivi
     *  2- False solo quelli attivi
     * </pre>
     */
    public List<Settori> findAllSectoriAttiviOrDisattivi(boolean flagDisattivi);

    /**
     * Cerca se ci sono record sulla tabella per la filter table passata. Se non sono stati trovati records allora cerca
     * dei records anche nel software TT
     * 
     * @see BaseDAO#existsRecords(FilterTable)
     */
    public boolean existsRecords();

    @DeletableCacheElements
    public void resetObjectCached();
}
