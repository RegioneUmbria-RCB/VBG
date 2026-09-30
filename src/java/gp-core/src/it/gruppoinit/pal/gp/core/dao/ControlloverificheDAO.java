package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Controlloverifiche;
import it.gruppoinit.pal.gp.core.domain.ControlloverificheId;

import java.util.List;

/**
 * 
 * @author
 */
public interface ControlloverificheDAO extends BaseDAO<Controlloverifiche, ControlloverificheId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Controlloverifiche> findAll(Integer firstResult, Integer maxResult);
}
