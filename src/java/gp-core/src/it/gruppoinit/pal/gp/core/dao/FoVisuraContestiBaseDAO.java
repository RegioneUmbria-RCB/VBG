package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiBase;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoVisuraContestiBaseDAO extends BaseDAO<FoVisuraContestiBase, String> {

    /**
     * Restituisce la lista di tutti i contesti ordinata per il campo contesto
     */
    public List<FoVisuraContestiBase> findAll(Integer firstResult, Integer maxResult);
}
