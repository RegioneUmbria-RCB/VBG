package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcConfigurazione;
import it.gruppoinit.pal.gp.core.domain.CcConfigurazioneId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CcConfigurazioneDAO extends BaseDAO<CcConfigurazione, CcConfigurazioneId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcConfigurazione> findAll(Integer firstResult, Integer maxResult);
}
