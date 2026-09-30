package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.sottoscrittori;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazioneBase;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazioneStopEvento;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.ProcBollElaborazioneInvioNodo;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreProcInvioNodoBollStopServiceImpl implements IEventSubscriber<ProceduraInvioNodoBollettazioneStopEvento> {

    @Override
    public void onEvent(ProceduraInvioNodoBollettazioneStopEvento e) {

	ProceduraInvioNodoBollettazioneBase.LOGGER_BOLL_INVIO.debug("{}", e);
	if (ProcBollElaborazioneInvioNodo.elaborazioniInCorso.containsKey(e.getKeyBollettazione())) {
	    ProcBollElaborazioneInvioNodo.elaborazioniInCorso.remove(e.getKeyBollettazione());
	}
    }
}
