package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CollaudoverificheDAO;
import it.gruppoinit.pal.gp.core.domain.Collaudoverifiche;
import it.gruppoinit.pal.gp.core.domain.CollaudoverificheId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CollaudoverificheService extends BaseService<Collaudoverifiche, CollaudoverificheId> {

    /**
     * @see CollaudoverificheDAO#findAll(Integer, Integer)
     */
    public List<Collaudoverifiche> findAll(Integer firstResult, Integer maxResult);
}
