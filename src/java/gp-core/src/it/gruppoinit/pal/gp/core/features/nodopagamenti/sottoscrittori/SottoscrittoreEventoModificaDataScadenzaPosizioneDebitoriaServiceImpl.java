package it.gruppoinit.pal.gp.core.features.nodopagamenti.sottoscrittori;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoDataScadenzaDettPosizioneDebitoriaModificata;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoModificaDataScadenzaPosizioneDebitoriaSuNodo;

@Service
public class SottoscrittoreEventoModificaDataScadenzaPosizioneDebitoriaServiceImpl
	implements IEventSubscriber<EventoModificaDataScadenzaPosizioneDebitoriaSuNodo> {

    private static final Logger log = LoggerFactory.getLogger(SottoscrittoreEventoModificaDataScadenzaPosizioneDebitoriaServiceImpl.class);
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    private IEventPublisher publisher;

    @Autowired
    public SottoscrittoreEventoModificaDataScadenzaPosizioneDebitoriaServiceImpl(DettPosizioneDebitoriaService dettPosizioneDebitoriaService,
	    IEventPublisher publisher) {

	super();
	this.dettPosizioneDebitoriaService = dettPosizioneDebitoriaService;
	this.publisher = publisher;
    }

    @Override
    public void onEvent(EventoModificaDataScadenzaPosizioneDebitoriaSuNodo e) {

	// aggiorna la data scadenza di dettPosizioneDebitoria 
	log.debug("Data scadenza modificata per la posizione {}==>({}) aggiorno il dato.", e.getIdDettPosizioneDebitoria(), e.getDataScadenza());
	DettPosizioneDebitoria dett = dettPosizioneDebitoriaService.findById(new PkId(e.getIdDettPosizioneDebitoria()));
	dett.setDataScadenza(e.getDataScadenza());
	dettPosizioneDebitoriaService.update(dett);
	log.debug("Data scadenza modificata per la posizione {}==>({}) rilancio l'evento EventoDataScadenzaDettPosizioneDebitoriaModificata.",
		e.getIdDettPosizioneDebitoria(), e.getDataScadenza());
	// rilancia altro evento
	publisher.publish(new EventoDataScadenzaDettPosizioneDebitoriaModificata(e.getIdDettPosizioneDebitoria(), e.getDataScadenza()));
    }
}
