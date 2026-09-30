package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneBloccata;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneInserita;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoRichiestaBloccoAutorizzazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoRichiestaSbloccoAutorizzazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.eventi.EventoAllegatiCaricati;
import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;

@Service
public class WSAttiModuleServiceImpl extends EventBusModule {

    @Autowired
    protected WSAttiModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoAllegatiCaricati.class, SottoscrittoreEventoAllegatiCaricatiServiceImpl.class);
	eventPublisher.add(EventoRichiestaBloccoAutorizzazione.class, SottoscrittoreEventoRichiestaBloccoAutorizzazioneServiceImpl.class);
	eventPublisher.add(EventoRichiestaSbloccoAutorizzazione.class, SottoscrittoreEventoRichiestaSbloccoAutorizzazioneServiceImpl.class);
	eventPublisher.add(EventoAutorizzazioneBloccata.class, SottoscrittoreEventoAutorizzazioneBloccataServiceImpl.class);
	eventPublisher.add(EventoAutorizzazioneInserita.class, SottoscrittoreEventoAutorizzazioneInseritaServiceImpl.class);
    }
}
