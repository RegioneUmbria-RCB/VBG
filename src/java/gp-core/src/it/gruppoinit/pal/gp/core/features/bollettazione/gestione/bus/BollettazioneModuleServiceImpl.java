package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazioneInizializzazioneEvento;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazionePosizioneElabEvento;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazioneStartEvento;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazioneStopEvento;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.sottoscrittori.SottoscrittoreBollAggDataScadenzaDettPosDebServiceImpl;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.sottoscrittori.SottoscrittoreProcInvioNodoBollInitServiceImpl;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.sottoscrittori.SottoscrittoreProcInvioNodoBollPosElabServiceImpl;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.sottoscrittori.SottoscrittoreProcInvioNodoBollStartServiceImpl;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.sottoscrittori.SottoscrittoreProcInvioNodoBollStopServiceImpl;
import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoDataScadenzaDettPosizioneDebitoriaModificata;

@Service
public class BollettazioneModuleServiceImpl extends EventBusModule {

    @Autowired
    public BollettazioneModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoDataScadenzaDettPosizioneDebitoriaModificata.class, SottoscrittoreBollAggDataScadenzaDettPosDebServiceImpl.class);
	eventPublisher.add(ProceduraInvioNodoBollettazioneInizializzazioneEvento.class, SottoscrittoreProcInvioNodoBollInitServiceImpl.class);
	eventPublisher.add(ProceduraInvioNodoBollettazioneStartEvento.class, SottoscrittoreProcInvioNodoBollStartServiceImpl.class);
	eventPublisher.add(ProceduraInvioNodoBollettazionePosizioneElabEvento.class, SottoscrittoreProcInvioNodoBollPosElabServiceImpl.class);
	eventPublisher.add(ProceduraInvioNodoBollettazioneStopEvento.class, SottoscrittoreProcInvioNodoBollStopServiceImpl.class);
    }
}
