package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.sottoscrittori;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazioneInizializzazioneEvento;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.ProcBollElaborazioneInvioNodo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.ProcBollElaborazioneInvioNodoBean;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreProcInvioNodoBollInitServiceImpl implements IEventSubscriber<ProceduraInvioNodoBollettazioneInizializzazioneEvento> {

    @Override
    public void onEvent(ProceduraInvioNodoBollettazioneInizializzazioneEvento e) {

	ProcBollElaborazioneInvioNodoBean elaborazione = new ProcBollElaborazioneInvioNodoBean(e.getIdBollettazione());
	ProcBollElaborazioneInvioNodo.elaborazioniInCorso.put(e.getKeyBollettazione(), elaborazione);
    }
}
