package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CanoniRiduzioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CanoniRiduzioniDAO extends BaseDAO<CanoniRiduzioni, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CanoniRiduzioni> findAll(Integer firstResult, Integer maxResult);
}
