package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneBloccata;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneInserita;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoRichiestaBloccoAutorizzazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoRichiestaSbloccoAutorizzazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.SottoscrittoreEventoAllegatiCaricatiServiceImpl;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.eventi.EventoAllegatiCaricati;
import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.sottoscrittori.SottoscrittoreEventoLocCancellataServiceImpl;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.sottoscrittori.SottoscrittoreEventoLocInseritaServiceImpl;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.sottoscrittori.SottoscrittoreEventoLocAggiornataServiceImpl;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.eventi.EventoLocalizzazioneIstanzaDel;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.eventi.EventoLocalizzazioneIstanzaIns;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.eventi.EventoLocalizzazioneIstanzaUpd;

@Service
public class IstanzeLocInsUpdtDelServiceImpl extends EventBusModule {

    @Autowired
    protected IstanzeLocInsUpdtDelServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {
	eventPublisher.add(EventoLocalizzazioneIstanzaIns.class, SottoscrittoreEventoLocInseritaServiceImpl.class);
	eventPublisher.add(EventoLocalizzazioneIstanzaUpd.class, SottoscrittoreEventoLocAggiornataServiceImpl.class);
	eventPublisher.add(EventoLocalizzazioneIstanzaDel.class, SottoscrittoreEventoLocCancellataServiceImpl.class);
    }
}
