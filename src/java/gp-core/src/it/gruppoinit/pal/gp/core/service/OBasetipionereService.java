package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OBasetipionereDAO;
import it.gruppoinit.pal.gp.core.domain.OBasetipionere;

import java.util.List;

/**
 * 
 * @author
 */
public interface OBasetipionereService extends BaseService<OBasetipionere, String> {

    /**
     * @see OBasetipionereDAO#findAll(Integer, Integer)
     */
    public List<OBasetipionere> findAll(Integer firstResult, Integer maxResult);
}
