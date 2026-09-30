package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CanoniConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.domain.CanoniConfigurazione;
import it.gruppoinit.pal.gp.core.domain.CanoniConfigurazioneId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CanoniConfigurazioneService extends BaseService<CanoniConfigurazione, CanoniConfigurazioneId> {

    /**
     * @see CanoniConfigurazioneDAO#findAll(Integer, Integer)
     */
    public List<CanoniConfigurazione> findAll(Integer firstResult, Integer maxResult);

    public boolean existRecordByCurrentSoftware();
}
