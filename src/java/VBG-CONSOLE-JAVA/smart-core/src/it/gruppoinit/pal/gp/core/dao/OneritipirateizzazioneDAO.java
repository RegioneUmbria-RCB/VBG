package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface OneritipirateizzazioneDAO extends BaseDAO<Oneritipirateizzazione, PkId> {

    /**
     * Metodo per determinare tutti gli oneri tipi rateizzazione che hanno il flag flagInteressiLegali uguale a false
     * 
     * @return
     */
    public List<Oneritipirateizzazione> findAllSenzaInteressiLegali();
}
