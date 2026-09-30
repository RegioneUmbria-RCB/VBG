package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CanoniCoefficientiDAO;
import it.gruppoinit.pal.gp.core.domain.CanoniCoefficienti;
import it.gruppoinit.pal.gp.core.domain.CanoniCoefficientiId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CanoniCoefficientiService extends BaseService<CanoniCoefficienti, CanoniCoefficientiId> {

    /**
     * @see CanoniCoefficientiDAO#findAll(Integer, Integer)
     */
    public List<CanoniCoefficienti> findAll(Integer firstResult, Integer maxResult);
}
