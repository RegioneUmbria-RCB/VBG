package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoSottoscrizioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoSottoscrizioniDAO extends BaseDAO<FoSottoscrizioni, PkId> {

    /**
     * Restituisce la lista di FoSottoscrizioni filtrata per idcomune
     * 
     */
    public List<FoSottoscrizioni> findAll(Integer firstResult, Integer maxResult);
}
