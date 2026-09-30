package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.domain.CcConfigurazione;
import it.gruppoinit.pal.gp.core.domain.CcConfigurazioneId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CcConfigurazioneService extends BaseService<CcConfigurazione, CcConfigurazioneId> {

    /**
     * @see CcConfigurazioneDAO#findAll(Integer, Integer)
     */
    public List<CcConfigurazione> findAll(Integer firstResult, Integer maxResult);
}
