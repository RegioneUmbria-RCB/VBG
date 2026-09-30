package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OccBasetipointervento;

import java.util.List;

/**
 * 
 * @author
 */
public interface OccBasetipointerventoDAO extends BaseDAO<OccBasetipointervento, String> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OccBasetipointervento> findAll(Integer firstResult, Integer maxResult);
}
