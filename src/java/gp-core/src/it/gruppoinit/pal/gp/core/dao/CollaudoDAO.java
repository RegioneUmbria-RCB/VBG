package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Collaudo;
import it.gruppoinit.pal.gp.core.domain.CollaudoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CollaudoDAO extends BaseDAO<Collaudo, CollaudoId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Collaudo> findAll(Integer firstResult, Integer maxResult);
}
