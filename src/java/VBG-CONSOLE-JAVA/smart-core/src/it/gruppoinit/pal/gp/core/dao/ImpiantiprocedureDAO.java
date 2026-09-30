package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Impiantiprocedure;
import it.gruppoinit.pal.gp.core.domain.ImpiantiprocedureId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface ImpiantiprocedureDAO extends BaseDAO<Impiantiprocedure, ImpiantiprocedureId> {

    /**
     * Non Implementato
     * 
     */
    public List<Impiantiprocedure> findAll(Integer firstResult, Integer maxResult);
}
