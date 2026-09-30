package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Collaudoverifiche;
import it.gruppoinit.pal.gp.core.domain.CollaudoverificheId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CollaudoverificheDAO extends BaseDAO<Collaudoverifiche, CollaudoverificheId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Collaudoverifiche> findAll(Integer firstResult, Integer maxResult);
}
