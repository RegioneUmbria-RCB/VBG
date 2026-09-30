package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.PuFormati;

import java.util.List;

/**
 * 
 * @author 
 */
public interface PuFormatiDAO extends BaseDAO<PuFormati, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<PuFormati> findAll(Integer firstResult, Integer maxResult);
}
