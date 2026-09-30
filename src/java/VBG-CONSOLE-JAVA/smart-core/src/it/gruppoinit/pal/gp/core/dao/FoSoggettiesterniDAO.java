package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoSoggettiesterni;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface FoSoggettiesterniDAO extends BaseDAO<FoSoggettiesterni, Integer> {

    /**
     * Restituisce la lista dei soggetti esterni
     * 
     */
    public List<FoSoggettiesterni> findAll(Integer firstResult, Integer maxResult);
}
