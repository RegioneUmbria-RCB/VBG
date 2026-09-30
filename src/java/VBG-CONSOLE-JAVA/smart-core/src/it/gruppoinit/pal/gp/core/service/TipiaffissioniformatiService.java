package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipiaffissioniformatiDAO;
import it.gruppoinit.pal.gp.core.domain.Tipiaffissioniformati;
import it.gruppoinit.pal.gp.core.domain.TipiaffissioniformatiId;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipiaffissioniformatiService extends BaseService<Tipiaffissioniformati, TipiaffissioniformatiId> {

    /**
     * @see TipiaffissioniformatiDAO#findAll(Integer, Integer)
     */
    public List<Tipiaffissioniformati> findAll(Integer firstResult, Integer maxResult);
}
