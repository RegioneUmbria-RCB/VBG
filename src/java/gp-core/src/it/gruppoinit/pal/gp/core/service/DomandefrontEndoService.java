package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.DomandefrontEndoDAO;
import it.gruppoinit.pal.gp.core.domain.DomandefrontEndo;
import it.gruppoinit.pal.gp.core.domain.DomandefrontEndoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface DomandefrontEndoService extends BaseService<DomandefrontEndo, DomandefrontEndoId> {

    /**
     * @see DomandefrontEndoDAO#findAll(Integer, Integer)
     */
    public List<DomandefrontEndo> findAll(Integer firstResult, Integer maxResult);
}
