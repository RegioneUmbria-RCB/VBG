package com.paevolution.appioproducer.taskscheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.paevolution.appioproducer.core.domain.MovimentiIoComunicazioni;
import com.paevolution.appioproducer.core.domain.helper.MessageToSendHelper;
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

    @Scheduled(fixedDelay = 1000)
    // @Scheduled(cron = "${cron.expression}")
    public void scheduleSendMessageTask() {

	log.debug("scheduledTask: Start POST");
	List<MessageToSendHelper> messageToSendHelpers = messageToSendRepository.findMessageToSend();
	if (!messageToSendHelpers.isEmpty()) {
	    for (MessageToSendHelper messageToSendHelper : messageToSendHelpers) {
		senderService.sendMessage(messageToSendHelper);
	    }
	} else {
	    log.debug("scheduledTask: Non ci sono messaggi da inviare.");
	}
	log.debug("scheduledTask: End POST");
	/*
	 *  Viene inserito un delay per evitare che la chiamata al get di un messaggio 
	 *  sia effettuata prima che appiogateway abbia ricevuto id_transazione dal Connettore
	 */
	try {
	    Thread.sleep(10000);
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    Thread.currentThread().interrupt();
	    e.printStackTrace();
	}
	log.debug("scheduledTask: Start GET");
	List<MovimentiIoComunicazioni> movimentiIoComunicazionis = (List<MovimentiIoComunicazioni>) movimentiIoComunicazioniRepository
		.findAllMessageToGetNotification();
	if (!movimentiIoComunicazionis.isEmpty()) {
	    for (MovimentiIoComunicazioni movimentiIoComunicazioni : movimentiIoComunicazionis) {
		senderService.getMessage(movimentiIoComunicazioni);
	    }
	} else {
	    log.debug("scheduledTask: Non ci sono notifiche da richiedere.");
	}
	log.debug("scheduledTask: End GET");
    }

    // @Scheduled(fixedDelay = 10000)
    // @Scheduled(cron = "${cron.expression}")
    public void scheduleGetMessageTask() {

	//	log.info("scheduleGetMessageTask: Start");	
	//	List<MovimentiIoComunicazioni> movimentiIoComunicazionis = (List<MovimentiIoComunicazioni>) movimentiIoComunicazioniRepository
	//		.findAllMessageToGetNotification();
	//	if (!movimentiIoComunicazionis.isEmpty()) {
	//	    for (MovimentiIoComunicazioni movimentiIoComunicazioni : movimentiIoComunicazionis) {
	//		senderService.getMessage(movimentiIoComunicazioni);
	//	    }
	//	} else {
	//	    log.info("scheduleGetMessageTask: Non ci sono notifiche da richiedere.");
	//	}
	//	log.info("scheduleGetMessageTask: End");
    }
}