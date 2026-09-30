package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoArjStepsBaseDAO;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsBase;

import java.util.List;

/**
 * 
 * @author
 */
public interface FoArjStepsBaseService extends BaseService<FoArjStepsBase, String> {

    /**
     * @see FoArjStepsBaseDAO#findAll(Integer, Integer)
     */
    public List<FoArjStepsBase> findAll(Integer firstResult, Integer maxResult);
}
