package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface OneritipirateizzazioneService extends BaseService<Oneritipirateizzazione, PkId> {

    /**
     * Metodo per determinare tutti gli oneri tipi rateizzazione che hanno il flag flagInteressiLegali uguale a false
     * 
     * @return
     */
    public List<Oneritipirateizzazione> findAllSenzaInteressiLegali();

    /**
     * 
     * @param tipomovimento
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Oneritipirateizzazione> findByTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult);
}
