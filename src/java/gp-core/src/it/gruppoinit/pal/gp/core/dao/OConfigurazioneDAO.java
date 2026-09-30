package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OConfigurazione;
import it.gruppoinit.pal.gp.core.domain.OConfigurazioneId;

import java.util.List;

/**
 * 
 * @author
 */
public interface OConfigurazioneDAO extends BaseDAO<OConfigurazione, OConfigurazioneId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OConfigurazione> findAll(Integer firstResult, Integer maxResult);
}
