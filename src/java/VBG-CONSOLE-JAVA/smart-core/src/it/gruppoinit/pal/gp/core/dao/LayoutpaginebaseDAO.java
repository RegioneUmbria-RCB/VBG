package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Layoutpaginebase;

import java.util.List;

/**
 * 
 * @author
 */
public interface LayoutpaginebaseDAO extends BaseDAO<Layoutpaginebase, String> {

    /**
     * tutti i record della tabella
     * 
     */
    public List<Layoutpaginebase> findAll(Integer firstResult, Integer maxResult);
}
