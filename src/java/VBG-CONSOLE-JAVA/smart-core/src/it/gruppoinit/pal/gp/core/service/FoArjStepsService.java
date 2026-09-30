package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoArjStepsDAO;
import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface FoArjStepsService extends BaseService<FoArjSteps, PkId> {

    /**
     * @see FoArjStepsDAO#findAll(Integer, Integer)
     */
    public List<FoArjSteps> findAll(Integer firstResult, Integer maxResult);

    public List<FoArjSteps> findByTestata(String idComuneTestata, Integer codiceTestata);

    public List<FoArjSteps> findByTestataAndNomeStepBase(String idComunetestata, Integer codiceTestata, String nomeStepBase);

    public String verificaConfigurazioneStep(String idComuneTestata, Integer codiceTestata);

    public void updateStepSpostaOrdine(String idComuneTestata, Integer codiceTestata, Integer codiceStep, boolean isUp);

    public List<FoArjSteps> findAttivi(String idComuneTestata, Integer codiceTestata);
}
