package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.DehorsLog;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface DehorsLogDAO extends BaseDAO<DehorsLog, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<DehorsLog> findAll(Integer firstResult, Integer maxResult);
}
