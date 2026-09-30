package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

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

    public List<OneriTipiRateizzazioneListBean> findAllByIdcomuneSoftware();

    public List<TipoScadenzaBean> getTipiScadenzaConsentiti();

    public OneriTipiRateizzazioneBean findById(int id);

    public void update(OneriTipiRateizzazioneBean tipirateizzazione);

    public void delete(Integer id);

    
    public List<Integer> findAllCodici();

    public void insert(OneriTipiRateizzazioneBean tipirateizzazione);
}
