package it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio;

import it.gruppoinit.pal.gp.core.domain.Sorteggidettagliomovimenti;
import it.gruppoinit.pal.gp.core.domain.SorteggidettagliomovimentiId;
import it.gruppoinit.pal.gp.core.service.BaseService;

import java.util.List;

/**
 * 
 * @author
 */
public interface SorteggidettagliomovimentiService extends BaseService<Sorteggidettagliomovimenti, SorteggidettagliomovimentiId> {

    /**
     * @see SorteggidettagliomovimentiDAO#findAll(Integer, Integer)
     */
    public List<Sorteggidettagliomovimenti> findAll(Integer firstResult, Integer maxResult);
}
