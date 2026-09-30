package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoSoggettiesterniDAO;
import it.gruppoinit.pal.gp.core.domain.FoSoggettiesterni;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface FoSoggettiesterniService extends BaseService<FoSoggettiesterni, Integer> {

    /**
     * @see FoSoggettiesterniDAO#findAll(Integer, Integer)
     */
    public List<FoSoggettiesterni> findAll(Integer firstResult, Integer maxResult);
}
