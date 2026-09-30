package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.SubprocedureDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Subprocedure;

import java.util.List;

/**
 * 
 * @author
 */
public interface SubprocedureService extends BaseService<Subprocedure, PkId> {

    /**
     * @see SubprocedureDAO#findAll(Integer, Integer)
     */
    public List<Subprocedure> findAll(Integer firstResult, Integer maxResult);
}
