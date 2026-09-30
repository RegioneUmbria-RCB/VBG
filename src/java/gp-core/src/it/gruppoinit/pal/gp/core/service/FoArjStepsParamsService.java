package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoArjStepsParamsDAO;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsParams;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface FoArjStepsParamsService extends BaseService<FoArjStepsParams, PkId> {

    /**
     * @see FoArjStepsParamsDAO#findAll(Integer, Integer)
     */
    public List<FoArjStepsParams> findAll(Integer firstResult, Integer maxResult);

    public List<FoArjStepsParams> findByIdStep(Integer codiceStep);

    public FoArjStepsParams findByIdStepAndNomeParametro(Integer codiceStep, String nomeParametro);
}
