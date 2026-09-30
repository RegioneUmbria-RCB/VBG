package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Permcdsinvitati;
import it.gruppoinit.pal.gp.core.domain.PermcdsinvitatiId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface PermcdsinvitatiDAO extends BaseDAO<Permcdsinvitati, PermcdsinvitatiId> {

    /**
     * Restituisce la lista di Permcdsinvitati filtrati per idcomune
     * 
     */
    public List<Permcdsinvitati> findAll(Integer firstResult, Integer maxResult);
}
