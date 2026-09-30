package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ProtocolloConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazione;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazioneId;

import java.util.List;

/**
 * 
 * @author
 */
public interface ProtocolloConfigurazioneService extends BaseService<ProtocolloConfigurazione, ProtocolloConfigurazioneId> {

    /**
     * @see ProtocolloConfigurazioneDAO#findAll(Integer, Integer)
     */
    public List<ProtocolloConfigurazione> findAll(Integer firstResult, Integer maxResult);
}
