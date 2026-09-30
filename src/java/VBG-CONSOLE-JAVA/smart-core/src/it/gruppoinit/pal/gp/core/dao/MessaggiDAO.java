package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Messaggi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface MessaggiDAO extends BaseDAO<Messaggi, PkId> {

    /**
     * Restituisce la lista dei messaggi filtrata per Idcomune e ordinata per dataMessaggio DESC
     * 
     */
    public List<Messaggi> findAll(Integer firstResult, Integer maxResult);
}
