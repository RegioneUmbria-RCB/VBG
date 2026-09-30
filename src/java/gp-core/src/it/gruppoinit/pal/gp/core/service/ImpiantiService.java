package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.ImpiantiDAO;
import it.gruppoinit.pal.gp.core.domain.Impianti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

/**
 * 
 * @author francescop
 */
public interface ImpiantiService extends BaseService<Impianti, PkId> {

    /**
     * @see ImpiantiDAO#findAll(Integer, Integer)
     */
    public List<Impianti> findAll(Integer firstResult, Integer maxResult);

    /**
     * Cerca se ci sono record sulla tabella per la filter table passata.
     * 
     * @see BaseDAO#existsRecords(FilterTable)
     */
    public boolean existsRecords();

    @DeletableCacheElements
    public void resetObjectCached();

    /**
     * trova tutti i record della tabella impianti filtrando per descrizione ed ordinati per la descrizione in modo
     * crescente
     * 
     * @param textToSearch
     * @return
     */
    public List<Impianti> findByDescrizione(String textToSearch);
}
