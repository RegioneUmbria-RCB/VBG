package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CommedilizieAllegatiDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface CommedilizieAllegatiService extends BaseService<CommedilizieAllegati, PkId> {

    /**
     * @see CommedilizieAllegatiDAO#findAll(Integer, Integer)
     */
    public List<CommedilizieAllegati> findAll(Integer firstResult, Integer maxResult);

    public List<CommedilizieAllegati> findByFilterTable(FilterTable filterTable);
}
