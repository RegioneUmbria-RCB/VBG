package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MessaggiDAO;
import it.gruppoinit.pal.gp.core.domain.Messaggi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface MessaggiService extends BaseService<Messaggi, PkId> {

    /**
     * @see MessaggiDAO#findAll(Integer, Integer)
     */
    public List<Messaggi> findAll(Integer firstResult, Integer maxResult);
}
