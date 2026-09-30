package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.TipiarchivioistanzeDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

public interface TipiarchivioistanzeService extends BaseService<Tipiarchivioistanze, PkId> {

    /**
     * @see TipiarchivioistanzeDAO#findAll(Integer, Integer)
     */
    public List<Tipiarchivioistanze> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see BaseDAO#findByFilterTable(FilterTable)
     */
    public List<Tipiarchivioistanze> findByFilterTable(FilterTable filterTable);

    /**
     * @see BaseDAO#existsRecords(FilterTable)
     */
    public boolean existsRecords();

    @DeletableCacheElements
    public void resetObjectCached();
}
