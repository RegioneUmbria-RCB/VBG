package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OccBasetipointerventoDAO;
import it.gruppoinit.pal.gp.core.domain.OccBasetipointervento;

import java.util.List;

/**
 * 
 * @author
 */
public interface OccBasetipointerventoService extends BaseService<OccBasetipointervento, String> {

    /**
     * @see OccBasetipointerventoDAO#findAll(Integer, Integer)
     */
    public List<OccBasetipointervento> findAll(Integer firstResult, Integer maxResult);
}
