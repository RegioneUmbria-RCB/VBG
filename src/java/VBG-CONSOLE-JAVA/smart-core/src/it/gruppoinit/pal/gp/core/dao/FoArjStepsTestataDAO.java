package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface FoArjStepsTestataDAO extends BaseDAO<FoArjStepsTestata, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<FoArjStepsTestata> findAll(Integer firstResult, Integer maxResult);
}
