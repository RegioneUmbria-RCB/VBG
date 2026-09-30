package it.gruppoinit.pal.gp.pay.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.pay.service.NotificheBackendService;
import it.gruppoinit.pal.gp.pay.service.async.NotificaBackendAsyncService;

@Service
public class NotificheBackendServiceImpl implements NotificheBackendService {

    private static final Logger log = LoggerFactory.getLogger(NotificheBackendServiceImpl.class);
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void notificaApiBackend(Integer idPosizione, String cfCodiceProfilo) throws Exception {

	log.debug("notificaApiBackend# entro nel metodo {},{}", idPosizione, cfCodiceProfilo);
	try {
	    taskExecutor.execute(new NotificaBackendAsyncService(applicationContext, idPosizione, cfCodiceProfilo));
	} catch (Exception e) {
	    log.error("eseguiTask# Errore nell' esecuzione della comunicazione per la posizione " + idPosizione + "-" + cfCodiceProfilo +
		      ", errore: " + e.getMessage(),
		    e);
	}
	log.debug("Fine notifica posizione {},{}", idPosizione, cfCodiceProfilo);
    }
}
