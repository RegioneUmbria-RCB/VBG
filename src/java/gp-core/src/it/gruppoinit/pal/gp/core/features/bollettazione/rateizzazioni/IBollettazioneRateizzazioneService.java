package it.gruppoinit.pal.gp.core.features.bollettazione.rateizzazioni;

import java.util.Set;

public interface IBollettazioneRateizzazioneService {

    public Set<BollettazioneRataBean> getPianoRateizzazioneByIdTipologia(int idBollCfgTipo);
}
