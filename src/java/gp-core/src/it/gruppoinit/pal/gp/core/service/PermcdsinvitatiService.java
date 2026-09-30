package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.PermcdsinvitatiDAO;
import it.gruppoinit.pal.gp.core.domain.Permcdsinvitati;
import it.gruppoinit.pal.gp.core.domain.PermcdsinvitatiId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface PermcdsinvitatiService extends BaseService<Permcdsinvitati, PermcdsinvitatiId> {

    /**
     * @see PermcdsinvitatiDAO#findAll(Integer, Integer)
     */
    public List<Permcdsinvitati> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle Permcdsinvitati di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Permcdsinvitati> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);
}
