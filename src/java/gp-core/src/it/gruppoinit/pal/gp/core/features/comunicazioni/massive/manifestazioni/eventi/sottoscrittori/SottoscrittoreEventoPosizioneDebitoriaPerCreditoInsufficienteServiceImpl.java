package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.eventi.sottoscrittori;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.EventoPosizioneDebitoriaPerCreditoInsufficiente;

@Service
public class SottoscrittoreEventoPosizioneDebitoriaPerCreditoInsufficienteServiceImpl
	implements IEventSubscriber<EventoPosizioneDebitoriaPerCreditoInsufficiente> {

    private static final Logger log = LoggerFactory.getLogger(SottoscrittoreEventoPosizioneDebitoriaPerCreditoInsufficienteServiceImpl.class);
    @Autowired
    private ComunicazioneAperturaPosizioneDebitoriaAsincrona comunicazioneAperturaPosizioneDebitoriaAsincrona;

    @Override
    public void onEvent(EventoPosizioneDebitoriaPerCreditoInsufficiente e) throws EventAbortedException {

	try {
	    comunicazioneAperturaPosizioneDebitoriaAsincrona.eseguiTask(e.getIdMercatiPresenzeD(), ORMHelper.getIdcomuneAlias(),
		    ORMHelper.getIdcomune(), ORMHelper.getSoftware(), ORMHelper.getToken(), ORMHelper.getHibernateSFKeyUrl());
	} catch (Exception ex) {
	    log.error("Errore nell'esecuzione del task comunicazioneAperturaPosizioneDebitoriaAsincrona.eseguiTask per la presenza " +
		      e.getIdMercatiPresenzeD() + "-" + ORMHelper.getIdcomune(),
		    e);
	}
    }
}
