package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDocumenti;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipiprocedureDocumentiDAO extends BaseDAO<TipiprocedureDocumenti, PkId> {

    /**
     * Lista documenti per le procedure filtrate per Idcomune e ordinate per descrizione (ASC)
     * 
     */
    public List<TipiprocedureDocumenti> findAll(Integer firstResult, Integer maxResult);
}
