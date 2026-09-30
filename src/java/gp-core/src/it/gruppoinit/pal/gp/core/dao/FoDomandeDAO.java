package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoDomandeDAO extends BaseDAO<FoDomande, PkId> {

    /**
     * Restituisce la lista di tutte le FoDomande filtrate per idcomune e software
     * 
     */
    public List<FoDomande> findAll(Integer firstResult, Integer maxResult);
}
