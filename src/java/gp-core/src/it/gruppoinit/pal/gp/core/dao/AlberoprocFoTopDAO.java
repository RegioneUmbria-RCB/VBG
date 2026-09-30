package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlberoprocFoTop;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AlberoprocFoTopDAO extends BaseDAO<AlberoprocFoTop, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<AlberoprocFoTop> findAll(Integer firstResult, Integer maxResult);
}
