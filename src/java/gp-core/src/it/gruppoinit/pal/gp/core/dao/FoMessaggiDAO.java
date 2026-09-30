package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoMessaggi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoMessaggiDAO extends BaseDAO<FoMessaggi, PkId> {

    /**
     * Restituisce la lista di FoMessaggi filtrata per idcomune e software
     * 
     */
    public List<FoMessaggi> findAll(Integer firstResult, Integer maxResult);
}
