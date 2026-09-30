package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.BatchScadIstanze;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface BatchScadIstanzeDAO extends BaseDAO<BatchScadIstanze, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<BatchScadIstanze> findAll(Integer firstResult, Integer maxResult);
}
