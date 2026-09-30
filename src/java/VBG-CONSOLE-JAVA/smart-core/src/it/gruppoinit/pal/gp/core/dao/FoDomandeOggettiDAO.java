package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggettiId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoDomandeOggettiDAO extends BaseDAO<FoDomandeOggetti, FoDomandeOggettiId> {

    /**
     * Restituisce la lista di FoDomandeOggetti filtrata per idcomune
     * 
     */
    public List<FoDomandeOggetti> findAll(Integer firstResult, Integer maxResult);
}
