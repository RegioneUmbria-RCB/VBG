package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoVisuraCampi;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampiId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoVisuraCampiDAO extends BaseDAO<FoVisuraCampi, FoVisuraCampiId> {

    /**
     * Restituisce la lista di campi filtrati per idcomune e software e ordinati per posizione
     */
    public List<FoVisuraCampi> findAll(Integer firstResult, Integer maxResult);
}
