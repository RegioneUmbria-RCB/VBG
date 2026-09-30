package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CollaudoDAO;
import it.gruppoinit.pal.gp.core.domain.Collaudo;
import it.gruppoinit.pal.gp.core.domain.CollaudoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CollaudoService extends BaseService<Collaudo, CollaudoId> {

    /**
     * @see CollaudoDAO#findAll(Integer, Integer)
     */
    public List<Collaudo> findAll(Integer firstResult, Integer maxResult);
}
