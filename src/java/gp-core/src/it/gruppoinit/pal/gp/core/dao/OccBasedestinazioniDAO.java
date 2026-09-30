package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OccBasedestinazioni;

import java.util.List;

/**
 * 
 * @author
 */
public interface OccBasedestinazioniDAO extends BaseDAO<OccBasedestinazioni, String> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OccBasedestinazioni> findAll(Integer firstResult, Integer maxResult);
}
