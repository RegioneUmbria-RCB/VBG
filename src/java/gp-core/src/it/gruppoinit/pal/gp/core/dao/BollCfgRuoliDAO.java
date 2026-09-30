package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.BollCfgRuoli;
import it.gruppoinit.pal.gp.core.domain.BollCfgRuoliId;

import java.util.List;

/**
 * 
 * @author
 */
public interface BollCfgRuoliDAO extends BaseDAO<BollCfgRuoli, BollCfgRuoliId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<BollCfgRuoli> findAll(Integer firstResult, Integer maxResult);
}
