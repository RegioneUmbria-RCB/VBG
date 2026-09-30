package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MovimentiAttiDAO;
import it.gruppoinit.pal.gp.core.domain.MovimentiAtti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface MovimentiAttiService extends BaseService<MovimentiAtti, PkId> {

    /**
     * @see MovimentiAttiDAO#findAll(Integer, Integer)
     */
    public List<MovimentiAtti> findAll(Integer firstResult, Integer maxResult);

    public MovimentiAtti findByMovimento(Integer codiceMovimento);
}
