package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisogBack;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AlberoprocTipisogBackDAO extends BaseDAO<AlberoprocTipisogBack, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<AlberoprocTipisogBack> findAll(Integer firstResult, Integer maxResult);
}
