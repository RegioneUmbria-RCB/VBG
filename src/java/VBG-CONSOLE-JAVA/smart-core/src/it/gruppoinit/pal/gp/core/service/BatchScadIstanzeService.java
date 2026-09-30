package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BatchScadIstanzeDAO;
import it.gruppoinit.pal.gp.core.domain.BatchScadIstanze;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface BatchScadIstanzeService extends BaseService<BatchScadIstanze, PkId> {

    /**
     * @see BatchScadIstanzeDAO#findAll(Integer, Integer)
     */
    public List<BatchScadIstanze> findAll(Integer firstResult, Integer maxResult);
}
