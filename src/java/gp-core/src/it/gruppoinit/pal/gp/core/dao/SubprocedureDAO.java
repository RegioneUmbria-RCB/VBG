package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Subprocedure;

import java.util.List;

/**
 * 
 * @author
 */
public interface SubprocedureDAO extends BaseDAO<Subprocedure, PkId> {

    /**
     * Ritorna una lista di sub procedure filtrate per idcomune
     * 
     */
    public List<Subprocedure> findAll(Integer firstResult, Integer maxResult);
}
