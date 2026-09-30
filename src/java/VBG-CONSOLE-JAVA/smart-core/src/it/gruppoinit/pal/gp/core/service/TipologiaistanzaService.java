/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.TipologiaistanzaDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaistanza;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface TipologiaistanzaService extends BaseService<Tipologiaistanza, PkId> {

    /**
     * @see TipologiaistanzaDAO#findAll(Integer, Integer)
     */
    public List<Tipologiaistanza> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see BaseDAO#existsRecords(FilterTable)
     */
    public boolean existsRecords();

    /**
     * @see BaseDAO#findByFilterTable(FilterTable)
     */
    public List<Tipologiaistanza> findByFilterTable(FilterTable filterTable);

    @DeletableCacheElements
    public void resetObjectCached();
}
