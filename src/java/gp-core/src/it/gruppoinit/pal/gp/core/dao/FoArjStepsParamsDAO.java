package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoArjStepsParams;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface FoArjStepsParamsDAO extends BaseDAO<FoArjStepsParams, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<FoArjStepsParams> findAll(Integer firstResult, Integer maxResult);
}
