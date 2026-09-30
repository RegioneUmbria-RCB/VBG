package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OValiditacoefficientiDAO extends BaseDAO<OValiditacoefficienti, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OValiditacoefficienti> findAll(Integer firstResult, Integer maxResult);
}
