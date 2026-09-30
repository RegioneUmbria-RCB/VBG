package com.paevolution.appiogateway.taskscheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.core.service.MessaggiService;
import com.paevolution.appiogateway.events.ReceivedMessageEvent;
import com.paevolution.appiogateway.events.interfaces.IEventPublisher;
import com.paevolution.appiogateway.utils.StatiMessaggioEnum;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TaskSchedulerWrapper {

    private MessaggiService messaggiService;
    private IEventPublisher publisher;

    @Autowired
    public void setMessaggiService(MessaggiService messaggiService) {

	this.messaggiService = messaggiService;
    }

    @Autowired
    public void setPublisher(@Qualifier("eventPublisher") IEventPublisher publisher) {

	this.publisher = publisher;
    }

    /*
     * Eseguito ogni 10 minuti effettua un nuovo invio del messaggio
     */
//    @Scheduled(cron = "${cron.expression}")
//    public void scheduleRitentaInvioTask() {
//
//	log.info("scheduleRitentaInvioTask: Start");
//	List<MessaggiDTO> messaggiDTOs = messaggiService.findAllByStatoMessaggio(StatiMessaggioEnum.RITENTA_INVIO);
//	if (messaggiDTOs != null && !messaggiDTOs.isEmpty()) {
//	    for (MessaggiDTO messaggiDTO : messaggiDTOs) {
//		publisher.publish(new ReceivedMessageEvent(messaggiDTO));
//		log.info("scheduleRitentaInvioTask: inviato messaggio con messageId: {}", messaggiDTO.getMessageId());
//	    }
//	} else {
//	    log.info("scheduleRitentaInvioTask: Non ci sono messaggi da inviare.");
//	}
//	log.info("scheduleRitentaInvioTask: End");
//    }
}