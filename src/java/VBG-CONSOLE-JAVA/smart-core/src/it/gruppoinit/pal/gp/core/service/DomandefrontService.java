package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.DomandefrontDAO;
import it.gruppoinit.pal.gp.core.domain.Domandefront;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface DomandefrontService extends BaseService<Domandefront, PkId> {

    /**
     * @see DomandefrontDAO#findAll(Integer, Integer)
     */
    public List<Domandefront> findAll(Integer firstResult, Integer maxResult);
}
