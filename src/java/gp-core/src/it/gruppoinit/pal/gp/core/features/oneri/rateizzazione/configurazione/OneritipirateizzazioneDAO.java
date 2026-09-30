package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface OneritipirateizzazioneDAO extends BaseDAO<Oneritipirateizzazione, PkId> {

    /**
     * Metodo per determinare tutti gli oneri tipi rateizzazione che hanno il flag flagInteressiLegali uguale a false
     * 
     * @return
     */
    public List<Oneritipirateizzazione> findAllSenzaInteressiLegali();

    public List<OneriTipiRateizzazioneListBean> findAllByIdcomuneSoftware(String idcomune, String software);

    public OneriTipiRateizzazioneBean findById(int id);
}
