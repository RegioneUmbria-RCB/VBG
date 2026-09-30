package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BollCfgMercatiDAO;
import it.gruppoinit.pal.gp.core.domain.BollCfgMercati;
import it.gruppoinit.pal.gp.core.domain.BollCfgMercatiId;

import java.util.List;

/**
 * 
 * @author
 */
public interface BollCfgMercatiService extends BaseService<BollCfgMercati, BollCfgMercatiId> {

    /**
     * @see BollCfgMercatiDAO#findAll(Integer, Integer)
     */
    public List<BollCfgMercati> findAll(Integer firstResult, Integer maxResult);

    public List<BollCfgMercati> findByBollcfgTipo(Integer codiceBollTipo, Integer firstResult, Integer maxResult);
}
