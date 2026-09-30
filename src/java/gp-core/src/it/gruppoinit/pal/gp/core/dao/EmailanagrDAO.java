package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Emailanagr;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface EmailanagrDAO extends BaseDAO<Emailanagr, PkId> {

    /**
     * Ritorna la lista delle mail di tutte le anagrafica oridnate per data
     * 
     */
    public List<Emailanagr> findAll(Integer firstResult, Integer maxResult);
}
