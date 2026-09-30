package it.gruppoinit.pal.gp.core.features.sorteggi.testata;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestatainfo;

import java.util.List;

/**
 * 
 * @author
 */
public interface SorteggitestatainfoDAO extends BaseDAO<Sorteggitestatainfo, PkId> {

    /**
     * Restituisce la lista di tutti i Sorteggitestatainfo ordinati per il campo ordine
     * 
     */
    public List<Sorteggitestatainfo> findAll(Integer firstResult, Integer maxResult);
}
