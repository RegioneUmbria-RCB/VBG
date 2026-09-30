package it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettagliomovimenti;
import it.gruppoinit.pal.gp.core.domain.SorteggidettagliomovimentiId;

import java.util.List;

/**
 * 
 * @author
 */
public interface SorteggidettagliomovimentiDAO extends BaseDAO<Sorteggidettagliomovimenti, SorteggidettagliomovimentiId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Sorteggidettagliomovimenti> findAll(Integer firstResult, Integer maxResult);
}
