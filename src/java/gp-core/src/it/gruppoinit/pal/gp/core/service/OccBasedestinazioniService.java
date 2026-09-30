package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OccBasedestinazioniDAO;
import it.gruppoinit.pal.gp.core.domain.OccBasedestinazioni;

import java.util.List;

/**
 * 
 * @author
 */
public interface OccBasedestinazioniService extends BaseService<OccBasedestinazioni, String> {

    /**
     * @see OccBasedestinazioniDAO#findAll(Integer, Integer)
     */
    public List<OccBasedestinazioni> findAll(Integer firstResult, Integer maxResult);
}
