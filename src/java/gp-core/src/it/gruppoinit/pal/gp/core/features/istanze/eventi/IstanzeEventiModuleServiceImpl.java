package it.gruppoinit.pal.gp.core.features.istanze.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.istanze.riepilogo.SottoscrittoreRiepilogoInserimentoIstanzaServiceImpl;
import it.gruppoinit.pal.gp.core.features.oneri.EventoOneriCopiati;
import it.gruppoinit.pal.gp.core.features.rabbitmq.eventi.EventoInserimentoIstanza;
import it.gruppoinit.pal.gp.core.features.suapxml.eventi.SottoscrittoreSuapXMLInserimentoIstanzaServiceImpl;

@Service
public class IstanzeEventiModuleServiceImpl extends EventBusModule {

    @Autowired
    public IstanzeEventiModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoOneriCopiati.class, EventoOneriCopiatiSottoscrittoreServiceImpl.class);
	eventPublisher.add(EventoStatoIstanzaModificato.class, SottoscrittoreEventoStatoIstanzaModificatoServiceImpl.class);
	eventPublisher.add(EventoInserimentoIstanza.class, SottoscrittoreSuapXMLInserimentoIstanzaServiceImpl.class);
	eventPublisher.add(EventoInserimentoIstanza.class, SottoscrittoreRiepilogoInserimentoIstanzaServiceImpl.class);
    }
}
