package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OInterventi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OInterventiDAO extends BaseDAO<OInterventi, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OInterventi> findAll(Integer firstResult, Integer maxResult);
}
