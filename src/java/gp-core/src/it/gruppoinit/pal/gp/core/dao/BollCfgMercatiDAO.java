package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.BollCfgMercati;
import it.gruppoinit.pal.gp.core.domain.BollCfgMercatiId;

import java.util.List;

/**
 * 
 * @author
 */
public interface BollCfgMercatiDAO extends BaseDAO<BollCfgMercati, BollCfgMercatiId> {

    /**
     * 
     * 
     */
    public List<BollCfgMercati> findAll(Integer firstResult, Integer maxResult);
}
