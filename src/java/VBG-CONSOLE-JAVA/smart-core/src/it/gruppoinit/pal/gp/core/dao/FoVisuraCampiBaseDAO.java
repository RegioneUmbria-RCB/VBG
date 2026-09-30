package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoVisuraCampiBase;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoVisuraCampiBaseDAO extends BaseDAO<FoVisuraCampiBase, String> {

    /**
     * Restituisce una lista di CampiBase ordinati per campo
     */
    public List<FoVisuraCampiBase> findAll(Integer firstResult, Integer maxResult);
}
