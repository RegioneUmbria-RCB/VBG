package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Domandefront;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface DomandefrontDAO extends BaseDAO<Domandefront, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Domandefront> findAll(Integer firstResult, Integer maxResult);
}
