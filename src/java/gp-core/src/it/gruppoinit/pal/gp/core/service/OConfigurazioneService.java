package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.domain.OConfigurazione;
import it.gruppoinit.pal.gp.core.domain.OConfigurazioneId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OConfigurazioneService extends BaseService<OConfigurazione, OConfigurazioneId> {

    /**
     * @see OConfigurazioneDAO#findAll(Integer, Integer)
     */
    public List<OConfigurazione> findAll(Integer firstResult, Integer maxResult);
}
