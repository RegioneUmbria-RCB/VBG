package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.VwProvinceDAO;
import it.gruppoinit.pal.gp.core.domain.VwProvince;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author
 */
public interface VwProvinceService extends BaseService<VwProvince, String> {

    /**
     * @see VwProvinceDAO#findAll(Integer, Integer)
     */
    public List<VwProvince> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see VwProvinceDAO#findByFilterTable(FilterTable filterTable)
     */
    public List<VwProvince> findByFilterTable(FilterTable filterTable);
}
