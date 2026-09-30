package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CommedilizieVotazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface CommedilizieVotazioniDAO extends BaseDAO<CommedilizieVotazioni, PkId> {

    /**
     * Lista dei presenti alle votazioni
     * 
     */
    public List<CommedilizieVotazioni> findAll(Integer firstResult, Integer maxResult);
}
