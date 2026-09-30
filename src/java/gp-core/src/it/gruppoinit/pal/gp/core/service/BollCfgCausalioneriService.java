package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BollCfgCausalioneriDAO;
import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneri;
import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneriId;

import java.util.List;

/**
 * 
 * @author
 */
public interface BollCfgCausalioneriService extends BaseService<BollCfgCausalioneri, BollCfgCausalioneriId> {

    /**
     * @see BollCfgCausalioneriDAO#findAll(Integer, Integer)
     */
    public List<BollCfgCausalioneri> findAll(Integer firstResult, Integer maxResult);

    public List<BollCfgCausalioneri> findByBollCfgTipo(Integer bollcfgTipo, Integer firstResult, Integer maxResult);
}
