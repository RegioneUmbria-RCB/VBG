package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CanoniRiduzioniomi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CanoniRiduzioniomiDAO extends BaseDAO<CanoniRiduzioniomi, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CanoniRiduzioniomi> findAll(Integer firstResult, Integer maxResult);
}
