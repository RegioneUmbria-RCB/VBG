package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ImpiantiprocedureDAO;
import it.gruppoinit.pal.gp.core.domain.Impiantiprocedure;
import it.gruppoinit.pal.gp.core.domain.ImpiantiprocedureId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface ImpiantiprocedureService extends BaseService<Impiantiprocedure, ImpiantiprocedureId> {

    /**
     * @see ImpiantiprocedureDAO#findAll(Integer, Integer)
     */
    public List<Impiantiprocedure> findAll(Integer firstResult, Integer maxResult);
}
