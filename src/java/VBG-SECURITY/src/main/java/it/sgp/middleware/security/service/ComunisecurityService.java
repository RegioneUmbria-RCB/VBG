package it.sgp.middleware.security.service;

import java.util.List;

import it.sgp.middleware.security.dao.ComunisecurityDAO;
import it.sgp.middleware.security.domain.Comunisecurity;

/**
 * 
 * @author
 */
public interface ComunisecurityService extends BaseService<Comunisecurity, String> {

    /**
     * @see ComunisecurityDAO#findAll(Integer, Integer)
     */
    public List<Comunisecurity> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see ComunisecurityDAO#findByDescrizioneOrAlias(String)
     */
    public List<Comunisecurity> findByDescrizioneOrAlias(String descrizione);
}
