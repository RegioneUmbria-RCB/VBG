package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ControlloverificheDAO;
import it.gruppoinit.pal.gp.core.domain.Controlloverifiche;
import it.gruppoinit.pal.gp.core.domain.ControlloverificheId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface ControlloverificheService extends BaseService<Controlloverifiche, ControlloverificheId> {

    /**
     * @see ControlloverificheDAO#findAll(Integer, Integer)
     */
    public List<Controlloverifiche> findAll(Integer firstResult, Integer maxResult);
}
