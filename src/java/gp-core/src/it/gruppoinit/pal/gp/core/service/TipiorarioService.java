package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.TipiorarioDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiorario;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author lucap
 */
public interface TipiorarioService extends BaseService<Tipiorario, PkId> {

    /**
     * @see TipiorarioDAO#findAll(Integer, Integer)
     */
    public List<Tipiorario> findAll(Integer firstResult, Integer maxResult);

    /**
     * Cerca se ci sono record sulla tabella per la filter table passata.
     * 
     * @see BaseDAO#existsRecords(FilterTable)
     */
    public boolean existsRecords();

    @DeletableCacheElements
    public void resetObjectCached();

    /**
     * @see TipiorarioDAO#findByFilter(Tipiorario entity)
     */
    public List<Tipiorario> findByFilter(Tipiorario entity);
}
