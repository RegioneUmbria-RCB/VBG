package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CanoniCategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CanoniCategorieDAO extends BaseDAO<CanoniCategorie, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CanoniCategorie> findAll(Integer firstResult, Integer maxResult);
}
