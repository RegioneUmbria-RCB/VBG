package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OConfigurazionetipionere;
import it.gruppoinit.pal.gp.core.domain.OConfigurazionetipionereId;

import java.util.List;

/**
 * 
 * @author
 */
public interface OConfigurazionetipionereDAO extends BaseDAO<OConfigurazionetipionere, OConfigurazionetipionereId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OConfigurazionetipionere> findAll(Integer firstResult, Integer maxResult);
}
