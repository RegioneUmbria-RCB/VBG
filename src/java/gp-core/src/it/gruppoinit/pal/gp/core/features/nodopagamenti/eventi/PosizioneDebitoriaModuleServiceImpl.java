package it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.sottoscrittori.SottoscrittoreEventoModificaDataFVPosizioneDebitoriaServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.sottoscrittori.SottoscrittoreEventoModificaDataScadenzaPosizioneDebitoriaServiceImpl;
import it.gruppoinit.pal.gp.core.features.oneri.SottoscrittoreDocPosizioneDebitoriaDispServiceImpl;

@Service
public class PosizioneDebitoriaModuleServiceImpl extends EventBusModule {

    @Autowired
    protected PosizioneDebitoriaModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoDocumentoPosizioneDebitoriaDisponibile.class, SottoscrittoreDocPosizioneDebitoriaDispServiceImpl.class);
	eventPublisher.add(EventoModificaDataScadenzaPosizioneDebitoriaSuNodo.class,
		SottoscrittoreEventoModificaDataScadenzaPosizioneDebitoriaServiceImpl.class);
	eventPublisher.add(EventoModificaDataFVPosizioneDebitoriaSuNodo.class,
		SottoscrittoreEventoModificaDataFVPosizioneDebitoriaServiceImpl.class);
    }
}
