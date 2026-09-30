package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Fidejussionestati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface FidejussionestatiDAO extends BaseDAO<Fidejussionestati, PkId> {

    /**
     * Torna la lista di stati fidejussione ordinati per descrizione asc e per il software corrente
     * 
     */
    public List<Fidejussionestati> findAll(Integer firstResult, Integer maxResult);
}
