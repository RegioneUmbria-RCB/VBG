package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoArjStepsParamsBaseDAO;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsParamsBase;

import java.util.List;

/**
 * 
 * @author
 */
public interface FoArjStepsParamsBaseService extends BaseService<FoArjStepsParamsBase, Integer> {

    /**
     * @see FoArjStepsParamsBaseDAO#findAll(Integer, Integer)
     */
    public List<FoArjStepsParamsBase> findAll(Integer firstResult, Integer maxResult);
    /**
     * Ritorna la lista di FoArjStepsParamsBase filtrata per FoArjStepsBase passato
     * @param idFoArjStepsBase
     * @return
     */
    public List<FoArjStepsParamsBase> findByFoArjStepsBase(String idFoArjStepsBase);
}
