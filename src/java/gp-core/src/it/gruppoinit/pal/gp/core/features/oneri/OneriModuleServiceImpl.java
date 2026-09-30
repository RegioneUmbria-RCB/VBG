package it.gruppoinit.pal.gp.core.features.oneri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoSubentroEffettuato;
import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoDataScadenzaDettPosizioneDebitoriaModificata;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoPosizioneDebitoriaAnnullata;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoPosizioneDebitoriaPagata;

@Service
public class OneriModuleServiceImpl extends EventBusModule {

    @Autowired
    public OneriModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoPosizioneDebitoriaPagata.class, SottoscrittorePosizioneDebitoriaPagataServiceImpl.class);
	eventPublisher.add(EventoPosizioneDebitoriaAnnullata.class, SottoscrittorePosizioneDebitoriaAnnullataServiceImpl.class);
	eventPublisher.add(EventoSubentroEffettuato.class, SottoscrittoreSubentriServiceImpl.class);
	eventPublisher.add(EventoDataScadenzaDettPosizioneDebitoriaModificata.class, SottoscrittoreAggDataScadenzaDettPosDebServiceImpl.class);
	eventPublisher.add(EventoPosizioneDebitoriaPagata.class, SottoscrittoreEventoPosizioneDebitoriaPagataPerAnnullamentoServiceImpl.class);
	eventPublisher.add(EventoPosizioneDebitoriaPagata.class, SottoscrittorePosizioneDebitoriaPagataBorsellinoServiceImpl.class);
    }
}
