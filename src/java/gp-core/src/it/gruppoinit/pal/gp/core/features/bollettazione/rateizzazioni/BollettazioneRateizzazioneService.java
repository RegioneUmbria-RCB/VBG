package it.gruppoinit.pal.gp.core.features.bollettazione.rateizzazioni;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import it.gruppoinit.pal.gp.core.domain.BollCfgTipoRate;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.bollettazione.IBollCfgTipoRateService;

public class BollettazioneRateizzazioneService implements IBollettazioneRateizzazioneService {

    private IBollCfgTipoRateService serviceConfigurazioneRate;

    public BollettazioneRateizzazioneService(IBollCfgTipoRateService serviceConfigurazioneRate) {

	this.serviceConfigurazioneRate = serviceConfigurazioneRate;
    }

    @Override
    public Set<BollettazioneRataBean> getPianoRateizzazioneByIdTipologia(int idBollCfgTipo) {

	List<BollCfgTipoRate> lista = this.serviceConfigurazioneRate.findByTipo(idBollCfgTipo);
	TreeSet<BollettazioneRataBean> pianoRateizzazione = new TreeSet<BollettazioneRataBean>(new BollettazioneRataBeanComparator());
	for (BollCfgTipoRate rata : lista) {
	    pianoRateizzazione.add(BollettazioneRataBean.fromBollCfgTipoRate(rata));
	}
	return pianoRateizzazione;
    }
}
