package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipiprocedureDocumentiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDocumenti;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipiprocedureDocumentiService extends BaseService<TipiprocedureDocumenti, PkId> {

    /**
     * @see TipiprocedureDocumentiDAO#findAll(Integer, Integer)
     */
    public List<TipiprocedureDocumenti> findAll(Integer firstResult, Integer maxResult);
}
