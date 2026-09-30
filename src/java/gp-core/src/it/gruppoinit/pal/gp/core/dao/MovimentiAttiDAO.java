package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MovimentiAtti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface MovimentiAttiDAO extends BaseDAO<MovimentiAtti, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<MovimentiAtti> findAll(Integer firstResult, Integer maxResult);
}
