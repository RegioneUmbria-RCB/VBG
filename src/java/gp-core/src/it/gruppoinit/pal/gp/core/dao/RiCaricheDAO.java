package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.RiCariche;

import java.util.List;

/**
 * 
 * @author
 */
public interface RiCaricheDAO extends BaseDAO<RiCariche, String> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<RiCariche> findAll(Integer firstResult, Integer maxResult);
}
