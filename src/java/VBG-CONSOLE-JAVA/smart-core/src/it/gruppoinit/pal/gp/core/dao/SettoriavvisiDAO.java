package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Settoriavvisi;

import java.util.List;

/**
 * 
 * @author gianpaolot
 * 
 */
public interface SettoriavvisiDAO extends BaseDAO<Settoriavvisi, PkId> {

    /**
     * Restituisce i settori (filtrando per idcomune e software) ordinandole per il campo id
     */
    public List<Settoriavvisi> findAll(Integer firstResult, Integer maxResult);

    /**
     * Restituisce una lista di settori avvisi filtrati tramite l'oggetto filter di tipo settoriavvisi
     * 
     * @param filter
     *            filtri attivi: 1 - Settore
     * @return Lista di settoriavvisi
     */
    public List<Settoriavvisi> findByFilter(Settoriavvisi filter);
}
