package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.LayoutpaginebaseDAO;
import it.gruppoinit.pal.gp.core.domain.Layoutpaginebase;

import java.util.List;

/**
 * 
 * @author
 */
public interface LayoutpaginebaseService extends BaseService<Layoutpaginebase, String> {

    /**
     * @see LayoutpaginebaseDAO#findAll(Integer, Integer)
     */
    public List<Layoutpaginebase> findAll(Integer firstResult, Integer maxResult);
}
