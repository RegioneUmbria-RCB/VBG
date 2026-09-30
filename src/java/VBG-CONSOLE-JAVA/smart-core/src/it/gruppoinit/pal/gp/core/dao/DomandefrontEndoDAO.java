package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.DomandefrontEndo;
import it.gruppoinit.pal.gp.core.domain.DomandefrontEndoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface DomandefrontEndoDAO extends BaseDAO<DomandefrontEndo, DomandefrontEndoId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<DomandefrontEndo> findAll(Integer firstResult, Integer maxResult);
}
