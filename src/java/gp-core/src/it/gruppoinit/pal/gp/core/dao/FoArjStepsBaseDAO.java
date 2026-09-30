package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoArjStepsBase;

import java.util.List;

/**
 * 
 * @author
 */
public interface FoArjStepsBaseDAO extends BaseDAO<FoArjStepsBase, String> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<FoArjStepsBase> findAll(Integer firstResult, Integer maxResult);
}
