package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoMessaggiDAO;
import it.gruppoinit.pal.gp.core.domain.FoMessaggi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoMessaggiService extends BaseService<FoMessaggi, PkId> {

    /**
     * @see FoMessaggiDAO#findAll(Integer, Integer)
     */
    public List<FoMessaggi> findAll(Integer firstResult, Integer maxResult);
}
