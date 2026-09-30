package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiCampiBase;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoVisuraContestiCampiBaseDAO extends BaseDAO<FoVisuraContestiCampiBase, PkId> {

    /**
     * Restituisce la lista completa di FoVisuraContestiCampiBase
     * 
     */
    public List<FoVisuraContestiCampiBase> findAll(Integer firstResult, Integer maxResult);
}
