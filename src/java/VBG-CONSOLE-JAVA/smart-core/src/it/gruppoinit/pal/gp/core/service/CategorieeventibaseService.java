package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CategorieeventibaseDAO;
import it.gruppoinit.pal.gp.core.domain.Categorieeventibase;

import java.util.List;

/**
 * 
 * @author
 */
public interface CategorieeventibaseService extends BaseService<Categorieeventibase, String> {

    /**
     * @see CategorieeventibaseDAO#findAll(Integer, Integer)
     */
    public List<Categorieeventibase> findAll(Integer firstResult, Integer maxResult);
}
