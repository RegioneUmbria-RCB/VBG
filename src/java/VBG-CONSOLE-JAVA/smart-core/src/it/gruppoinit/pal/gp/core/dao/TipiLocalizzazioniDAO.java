package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipiLocalizzazioni;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipiLocalizzazioniDAO extends BaseDAO<TipiLocalizzazioni, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<TipiLocalizzazioni> findAll(Integer firstResult, Integer maxResult);
}
