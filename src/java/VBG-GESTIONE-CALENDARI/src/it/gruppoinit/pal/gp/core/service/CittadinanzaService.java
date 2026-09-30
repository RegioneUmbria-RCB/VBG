package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CittadinanzaDAO;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface CittadinanzaService extends BaseService<Cittadinanza, Integer> {

    /**
     * @see CittadinanzaDAO#findAll(Integer, Integer)
     */
    public List<Cittadinanza> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see CittadinanzaDAO#findByFilterTable(FilterTable filterTable)
     */
    public List<Cittadinanza> findByFilterTable(FilterTable filterTable);

    public List<Cittadinanza> findByDescrizione(String term);
}
