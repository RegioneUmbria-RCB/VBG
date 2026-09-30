package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OBasetipionere;

import java.util.List;

/**
 * 
 * @author
 */
public interface OBasetipionereDAO extends BaseDAO<OBasetipionere, String> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OBasetipionere> findAll(Integer firstResult, Integer maxResult);
}
