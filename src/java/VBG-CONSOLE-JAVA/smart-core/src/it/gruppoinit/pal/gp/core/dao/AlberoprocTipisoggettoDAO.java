package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisoggetto;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AlberoprocTipisoggettoDAO extends BaseDAO<AlberoprocTipisoggetto, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<AlberoprocTipisoggetto> findAll(Integer firstResult, Integer maxResult);
}
