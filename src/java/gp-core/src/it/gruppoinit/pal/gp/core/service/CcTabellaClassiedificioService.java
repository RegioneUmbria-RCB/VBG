package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcTabellaClassiedificioDAO;
import it.gruppoinit.pal.gp.core.domain.CcTabellaClassiedificio;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CcTabellaClassiedificioService extends BaseService<CcTabellaClassiedificio, PkId> {

    /**
     * @see CcTabellaClassiedificioDAO#findAll(Integer, Integer)
     */
    public List<CcTabellaClassiedificio> findAll(Integer firstResult, Integer maxResult);
    
    public List<CcTabellaClassiedificio> listByIntervallo();
    /**
     * Ritorna l'oggetto che ha come descrizione quella passata, se non esiste ritorna null
     * 
     * @param descrizione
     * @return
     */
    public CcTabellaClassiedificio findByEqualsDescrizione(String descrizione);
}
