package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Dyn2Basetipitesto;

import java.util.List;

/**
 * 
 * @author
 */
public interface Dyn2BasetipitestoDAO extends BaseDAO<Dyn2Basetipitesto, String> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Dyn2Basetipitesto> findAll(Integer firstResult, Integer maxResult);
}
