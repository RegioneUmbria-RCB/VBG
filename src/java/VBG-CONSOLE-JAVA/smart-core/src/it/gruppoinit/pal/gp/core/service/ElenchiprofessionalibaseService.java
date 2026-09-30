package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.ElenchiprofessionalibaseDAO;
import it.gruppoinit.pal.gp.core.domain.Elenchiprofessionalibase;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface ElenchiprofessionalibaseService extends BaseService<Elenchiprofessionalibase, Integer> {

    /**
     * @see ElenchiprofessionalibaseDAO#findAll(Integer, Integer)
     */
    public List<Elenchiprofessionalibase> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see BaseDAO#findByFilterTable(FilterTable)
     */
    public List<Elenchiprofessionalibase> findByFilterTable(FilterTable filterTable);
}
