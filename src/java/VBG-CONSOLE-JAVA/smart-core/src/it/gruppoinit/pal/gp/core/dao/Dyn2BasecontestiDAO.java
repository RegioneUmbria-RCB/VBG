package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Dyn2Basecontesti;

import java.util.List;

/**
 * 
 * @author
 */
public interface Dyn2BasecontestiDAO extends BaseDAO<Dyn2Basecontesti, String> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Dyn2Basecontesti> findAll(Integer firstResult, Integer maxResult);
}
