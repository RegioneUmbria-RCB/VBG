package it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.DettaglioBollettazione;

public interface PosizioniDebitorieDaBollettazioneService {

    public List<PosizioneDebitoriaBollettazioneBean> build(DettaglioBollettazione boll);
}
