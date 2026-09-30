package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.DehorsAree;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface DehorsAreeDAO extends BaseDAO<DehorsAree, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<DehorsAree> findAll(Integer firstResult, Integer maxResult);
}
