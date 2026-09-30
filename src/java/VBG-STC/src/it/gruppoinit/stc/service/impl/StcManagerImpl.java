package it.gruppoinit.stc.service.impl;

import it.gruppoinit.stc.domain.Messaggiattivita;
import it.gruppoinit.stc.domain.Pratiche;
import it.gruppoinit.stc.service.StcManager;
import it.gruppoinit.stc.service.StcService;
import it.init.sigepro.rte.NotificaAttivitaRequest;
import it.init.sigepro.rte.NotificaAttivitaResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StcManagerImpl implements StcManager {

    private static final Logger log = LoggerFactory.getLogger(StcManagerImpl.class);
    @Autowired
    private StcService stcService;

    @Override
    public NotificaAttivitaResponse notificaAttivita(NotificaAttivitaRequest request) {

	log.debug("STC - notificaAttivita");
	Pratiche[] pratiche = stcService.notificaAttivitaGestionePratiche(request);
	// 2015.12.01 UNA VOLTA CREATO IL RECORD DELLA PRATICA MITTENTE CREA IL RECORD DELLA PRATICA DESTINATARIO
	Messaggiattivita mp = stcService.notificaAttivitaCollegaAttivita(request, pratiche[0], pratiche[1]);
	NotificaAttivitaResponse notificaAttivitaResponse = stcService.notificaAttivitaGestioneAttivita(request, mp);
	return notificaAttivitaResponse;
    }
}
