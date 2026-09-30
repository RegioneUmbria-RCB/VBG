package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazione;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazioneId;

import java.util.List;

/**
 * 
 * @author
 */
public interface ProtocolloConfigurazioneDAO extends BaseDAO<ProtocolloConfigurazione, ProtocolloConfigurazioneId> {

    /**
     * 
     */
    public List<ProtocolloConfigurazione> findAll(Integer firstResult, Integer maxResult);
}
