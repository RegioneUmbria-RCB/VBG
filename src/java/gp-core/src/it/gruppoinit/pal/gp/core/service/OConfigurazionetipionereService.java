package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OConfigurazionetipionereDAO;
import it.gruppoinit.pal.gp.core.domain.OConfigurazionetipionere;
import it.gruppoinit.pal.gp.core.domain.OConfigurazionetipionereId;

import java.util.List;

/**
 * 
 * @author
 */
public interface OConfigurazionetipionereService extends BaseService<OConfigurazionetipionere, OConfigurazionetipionereId> {

    /**
     * @see OConfigurazionetipionereDAO#findAll(Integer, Integer)
     */
    public List<OConfigurazionetipionere> findAll(Integer firstResult, Integer maxResult);
}
