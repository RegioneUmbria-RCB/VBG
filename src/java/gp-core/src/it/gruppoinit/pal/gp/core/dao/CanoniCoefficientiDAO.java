package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CanoniCoefficienti;
import it.gruppoinit.pal.gp.core.domain.CanoniCoefficientiId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CanoniCoefficientiDAO extends BaseDAO<CanoniCoefficienti, CanoniCoefficientiId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CanoniCoefficienti> findAll(Integer firstResult, Integer maxResult);
}
