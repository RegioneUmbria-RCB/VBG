package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzefrontofficeDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzefrontoffice;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzefrontofficeService extends BaseService<Istanzefrontoffice, PkId> {

    /**
     * @see IstanzefrontofficeDAO#findAll(Integer, Integer)
     */
    public List<Istanzefrontoffice> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see IstanzefrontofficeDAO#findByFilterTable(FilterTable)
     */
    public List<Istanzefrontoffice> findByFilterTable(FilterTable filterTable);
}
