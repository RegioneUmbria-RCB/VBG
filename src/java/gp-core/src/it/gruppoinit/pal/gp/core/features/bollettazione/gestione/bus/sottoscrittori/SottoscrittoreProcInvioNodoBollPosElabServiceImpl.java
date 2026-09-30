package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.sottoscrittori;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazioneBase;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazionePosizioneElabEvento;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.ProcBollElaborazioneInvioNodo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.ProcBollElaborazioneInvioNodoBean;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreProcInvioNodoBollPosElabServiceImpl implements IEventSubscriber<ProceduraInvioNodoBollettazionePosizioneElabEvento> {

    @Override
    public void onEvent(ProceduraInvioNodoBollettazionePosizioneElabEvento e) {

	ProceduraInvioNodoBollettazioneBase.LOGGER_BOLL_INVIO.debug("{}", e);
	if (ProcBollElaborazioneInvioNodo.elaborazioniInCorso.containsKey(e.getKeyBollettazione())) {
	    ProcBollElaborazioneInvioNodoBean b = ProcBollElaborazioneInvioNodo.elaborazioniInCorso.get(e.getKeyBollettazione());
	    b.aggiungiElaborato();
	}
    }
}
