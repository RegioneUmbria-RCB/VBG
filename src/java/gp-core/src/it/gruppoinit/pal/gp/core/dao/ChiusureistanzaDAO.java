package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Chiusureistanza;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface ChiusureistanzaDAO extends BaseDAO<Chiusureistanza, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Chiusureistanza> findAll(Integer firstResult, Integer maxResult);
}
