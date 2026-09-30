package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.sottoscrittori;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazioneBase;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazioneStartEvento;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.ProcBollElaborazioneInvioNodo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.ProcBollElaborazioneInvioNodoBean;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreProcInvioNodoBollStartServiceImpl implements IEventSubscriber<ProceduraInvioNodoBollettazioneStartEvento> {

    @Override
    public void onEvent(ProceduraInvioNodoBollettazioneStartEvento e) {

	ProceduraInvioNodoBollettazioneBase.LOGGER_BOLL_INVIO.debug("{}", e);
	ProcBollElaborazioneInvioNodoBean elaborazione = ProcBollElaborazioneInvioNodo.elaborazioniInCorso.get(e.getKeyBollettazione());
	elaborazione.setTotaleRecord(e.getTotalePosizioni());
	ProcBollElaborazioneInvioNodo.elaborazioniInCorso.put(e.getKeyBollettazione(), elaborazione);
    }
}
