package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Cittadinanza;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface CittadinanzaDAO extends BaseDAO<Cittadinanza, Integer> {

    /**
     * Ricerca la lista delle cittadinanze ordinate per il campo cittadinanza
     * 
     */
    public List<Cittadinanza> findAll(Integer firstResult, Integer maxResult);
}
