package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ConfigurazioneEmailDAO;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneEmail;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface ConfigurazioneEmailService extends BaseService<ConfigurazioneEmail, PkId> {

    /**
     * @see ConfigurazioneEmailDAO#findAll(Integer, Integer)
     */
    public List<ConfigurazioneEmail> findAll(Integer firstResult, Integer maxResult);

    public ConfigurazioneEmail findByInstallazione();
}
