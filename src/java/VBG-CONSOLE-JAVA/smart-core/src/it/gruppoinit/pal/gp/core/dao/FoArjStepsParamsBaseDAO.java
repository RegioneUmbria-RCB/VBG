package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoArjStepsParamsBase;

import java.util.List;

/**
 * 
 * @author
 */
public interface FoArjStepsParamsBaseDAO extends BaseDAO<FoArjStepsParamsBase, Integer> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<FoArjStepsParamsBase> findAll(Integer firstResult, Integer maxResult);
}
