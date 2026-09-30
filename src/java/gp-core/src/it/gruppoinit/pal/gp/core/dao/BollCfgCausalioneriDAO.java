package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneri;
import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneriId;

import java.util.List;

/**
 * 
 * @author
 */
public interface BollCfgCausalioneriDAO extends BaseDAO<BollCfgCausalioneri, BollCfgCausalioneriId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<BollCfgCausalioneri> findAll(Integer firstResult, Integer maxResult);
}
