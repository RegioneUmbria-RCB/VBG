package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.LavoritipiCausalioneriDAO;
import it.gruppoinit.pal.gp.core.domain.Lavoritipi;
import it.gruppoinit.pal.gp.core.domain.LavoritipiCausalioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface LavoritipiCausalioneriService extends BaseService<LavoritipiCausalioneri, PkId> {

    /**
     * @see LavoritipiCausalioneriDAO#findAll(Integer, Integer)
     */
    public List<LavoritipiCausalioneri> findAll(Integer firstResult, Integer maxResult);

    /**
     * 
     * @see LavoritipiCausalioneriDAO#findByLavoritipi(Lavoritipi)
     */
    public List<LavoritipiCausalioneri> findByLavoritipi(Lavoritipi lavoritipi);

    /**
     * Cerca se ci sono record sulla tabella per la filter table passata.
     * 
     * @see BaseDAO#existsRecords(FilterTable)
     */
    public boolean existsRecords();

    @DeletableCacheElements
    public void resetObjectCached();
}
