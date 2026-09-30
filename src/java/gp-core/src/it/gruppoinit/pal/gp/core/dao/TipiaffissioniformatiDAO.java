package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Tipiaffissioniformati;
import it.gruppoinit.pal.gp.core.domain.TipiaffissioniformatiId;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipiaffissioniformatiDAO extends BaseDAO<Tipiaffissioniformati, TipiaffissioniformatiId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Tipiaffissioniformati> findAll(Integer firstResult, Integer maxResult);
}
