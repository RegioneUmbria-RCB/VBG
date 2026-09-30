package com.paevolution.appioproducer.taskscheduler;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.paevolution.appioproducer.core.domain.AppIoCoda;
import com.paevolution.appioproducer.core.domain.MovimentiIoComunicazioni;
import com.paevolution.appioproducer.core.domain.helper.MessageToSendHelper;
import com.paevolution.appioproducer.core.repository.AppIoCodaRepository;
import com.paevolution.appioproducer.core.repository.MessageToSendRepository;
import com.paevolution.appioproducer.core.repository.MovimentiIoComunicazioniRepository;
import com.paevolution.appioproducer.core.service.ISenderService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TaskSchedulerWrapper {

    @Autowired
    private MessageToSendRepository messageToSendRepository;
    @Autowired
    private MovimentiIoComunicazioniRepository movimentiIoComunicazioniRepository;
    @Autowired
    private ISenderService senderService;
    @Autowired
    private AppIoCodaRepository appIoCodaRepository;

    @Scheduled(fixedDelay = 1000)
    // @Scheduled(cron = "${cron.expression}")
    public void scheduleSendMessageTask() {

	log.debug("scheduleSendMessageTask: Start POST");
	List<AppIoCoda> appIoCodas = appIoCodaRepository.findAllMessageToSend(new Date());
	if (!appIoCodas.isEmpty()) {
	    for (AppIoCoda appIoCoda : appIoCodas) {
		senderService.sendMessage(appIoCoda);
	    }
	} else {
	    log.debug("scheduleSendMessageTask: Non ci sono messaggi da inviare.");
	}
	log.debug("scheduleSendMessageTask: End POST");
	/*
	 *  Viene inserito un delay per evitare che la chiamata al get di un messaggio 
	 *  sia effettuata prima che appiogateway abbia ricevuto id_transazione dal Connettore
	 */
	try {
	    Thread.sleep(5000);
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    Thread.currentThread().interrupt();
	    e.printStackTrace();
	}
	log.debug("scheduleSendMessageTask: Start GET");
	List<AppIoCoda> lisAppIoCodaToNotify = appIoCodaRepository.findAllMessageToNotify();
	if (!lisAppIoCodaToNotify.isEmpty()) {
	    for (AppIoCoda appIoCodaToNotify : lisAppIoCodaToNotify) {
		senderService.getMessage(appIoCodaToNotify);
	    }
	} else {
	    log.debug("scheduleSendMessageTask: Non ci sono notifiche da richiedere.");
	}
	log.debug("scheduleSendMessageTask: End GET");
	/*
	 *  Viene inserito un delay per evitare che la chiamata al resend di un messaggio 
	 *  sia effettuata prima che appiogateway abbia terminato il get
	 */
	try {
	    Thread.sleep(5000);
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    Thread.currentThread().interrupt();
	    e.printStackTrace();
	}
	log.debug("scheduleSendMessageTask: Start Resend");
	// Recupero tutti i messaggi con errore OutOfMemoryError ed effettuo il ritenta invio
	List<AppIoCoda> appIoCodaToResend = appIoCodaRepository.findAllMessageToResend();
	if (!appIoCodaToResend.isEmpty()) {
	    for (AppIoCoda appIoCoda : appIoCodaToResend) {
		senderService.resendMessage(appIoCoda);
	    }
	} else {
	    log.debug("scheduleSendMessageTask: Non ci sono messaggi da inviare.");
	}
	log.debug("scheduleSendMessageTask: End Resend");
    }
    
    /*
     *  Task per ripetere l'invio dei messaggi che hanno restituito un errore OutOfMemoryError
     *  il messaggio è stato salvato con stato errore sia sul gateway che sul producer
     *  1. eseguo l'API ritentaInvio (che semplicemente modifica lo stato del messaggio sul gateway)
     *  2. aggiorno lo stato in INVIATA_A_GATEWAY (in modo tale da ripetere la richiesta della notifica)
     */
    
    // @Scheduled(cron = "${cron.expression}")
    // @Scheduled(cron = "${cron.expression}")
//    public void scheduleErrorMessageTask() {
//
//	log.debug("scheduleErrorMessageTask: Start POST");
//	// Recupero tutti i messaggi con errore OutOfMemoryError ed effettuo il ritenta invio
//	List<AppIoCoda> appIoCodas = appIoCodaRepository.findAllMessageToResend();
//	if (!appIoCodas.isEmpty()) {
//	    for (AppIoCoda appIoCoda : appIoCodas) {
//		senderService.resendMessage(appIoCoda);
//	    }
//	} else {
//	    log.debug("scheduleErrorMessageTask: Non ci sono messaggi da inviare.");
//	}
//	log.debug("scheduleErrorMessageTask: End POST");
//	
//    }
}