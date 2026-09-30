package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FDomande;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FDomandeDAO extends BaseDAO<FDomande, PkId> {

    /**
     * Restituisce la lista di FDomande filtrate per idcomune
     * 
     */
    public List<FDomande> findAll(Integer firstResult, Integer maxResult);
}
