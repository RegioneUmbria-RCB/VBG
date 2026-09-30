package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CanoniConfigurazione;
import it.gruppoinit.pal.gp.core.domain.CanoniConfigurazioneId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CanoniConfigurazioneDAO extends BaseDAO<CanoniConfigurazione, CanoniConfigurazioneId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CanoniConfigurazione> findAll(Integer firstResult, Integer maxResult);
}
