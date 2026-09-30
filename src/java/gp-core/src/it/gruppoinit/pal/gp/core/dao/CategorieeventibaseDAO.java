package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Categorieeventibase;

import java.util.List;

/**
 * 
 * @author
 */
public interface CategorieeventibaseDAO extends BaseDAO<Categorieeventibase, String> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Categorieeventibase> findAll(Integer firstResult, Integer maxResult);
}
