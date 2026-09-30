package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface FoArjStepsDAO extends BaseDAO<FoArjSteps, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<FoArjSteps> findAll(Integer firstResult, Integer maxResult);
}
