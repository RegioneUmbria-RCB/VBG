package it.sgp.middleware.security.service;

import it.sgp.middleware.security.dao.ComunisecurityConnectionDAO;
import it.sgp.middleware.security.domain.ComunisecurityConnection;
import it.sgp.middleware.security.domain.ComunisecurityConnectionId;

import java.util.List;

/**
 * 
 * @author
 */
public interface ComunisecurityConnectionService extends BaseService<ComunisecurityConnection, ComunisecurityConnectionId> {

    /**
     * @see ComunisecurityConnectionDAO#findAll(Integer, Integer)
     */
    public List<ComunisecurityConnection> findAll(Integer firstResult, Integer maxResult);
}
