package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.StradariocoloreDAO;
import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.StradariocoloreId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface StradariocoloreService extends BaseService<Stradariocolore, StradariocoloreId> {

    /**
     * @see StradariocoloreDAO#findAll(Integer, Integer)
     */
    public List<Stradariocolore> findAll(Integer firstResult, Integer maxResult);

    public List<Stradariocolore> findAll();

    /**
     * @see BaseDAO#existsRecords(FilterTable)
     */
    public boolean existsRecords();

    @DeletableCacheElements
    public void resetObjectCached();
}
