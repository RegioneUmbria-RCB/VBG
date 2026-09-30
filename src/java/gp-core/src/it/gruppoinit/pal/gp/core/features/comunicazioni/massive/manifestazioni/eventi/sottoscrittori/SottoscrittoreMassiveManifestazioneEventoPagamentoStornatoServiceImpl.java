package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.eventi.sottoscrittori;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.EventoPagamentoStornato;

@Service
public class SottoscrittoreMassiveManifestazioneEventoPagamentoStornatoServiceImpl implements IEventSubscriber<EventoPagamentoStornato> {

    private static final Logger log = LoggerFactory.getLogger(SottoscrittoreMassiveManifestazioneEventoPagamentoStornatoServiceImpl.class);
    @Autowired
    private CancellazioneComunicazioneAsincrona cancellazioneComunicazioneAsincrona;

    @Override
    public void onEvent(EventoPagamentoStornato e) throws EventAbortedException {

	try {
	    cancellazioneComunicazioneAsincrona.eseguiTask(e.getIdMercatiPresenzeD(), ORMHelper.getIdcomuneAlias(), ORMHelper.getIdcomune(),
		    ORMHelper.getSoftware(), ORMHelper.getToken(), ORMHelper.getHibernateSFKeyUrl());
	} catch (Exception ex) {
	    log.error("Errore nell'esecuzione del task comunicazioneAperturaPosizioneDebitoriaAsincrona.eseguiTask per la presenza {},{}",
		    new Object[] { e.getIdMercatiPresenzeD(), ORMHelper.getIdcomune() }, e);
	}
    }
}
