package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.SitCartechDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SitCartech;

import java.util.List;

/**
 * 
 * @author 
 */
public interface SitCartechService extends BaseService<SitCartech, PkId> {

    /**
     * @see SitCartechDAO#findAll(Integer, Integer)
     */
    public List<SitCartech> findAll(Integer firstResult, Integer maxResult);
}
