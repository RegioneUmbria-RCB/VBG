package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CanoniCategorieDAO;
import it.gruppoinit.pal.gp.core.domain.CanoniCategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CanoniCategorieService extends BaseService<CanoniCategorie, PkId> {

    /**
     * @see CanoniCategorieDAO#findAll(Integer, Integer)
     */
    public List<CanoniCategorie> findAll(Integer firstResult, Integer maxResult);
}
