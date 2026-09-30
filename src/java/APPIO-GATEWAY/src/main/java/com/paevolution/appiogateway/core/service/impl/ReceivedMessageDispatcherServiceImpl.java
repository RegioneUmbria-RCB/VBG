package com.paevolution.appiogateway.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.paevolution.appiogateway.connector.ioitalia.events.MessageForIOItaliaEvent;
import com.paevolution.appiogateway.connector.ud.events.MessageForUDEvent;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.core.service.EventDispatcherService;
import com.paevolution.appiogateway.events.ReceivedMessageEvent;
import com.paevolution.appiogateway.events.interfaces.IEventPublisher;
import com.paevolution.appiogateway.utils.WebConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReceivedMessageDispatcherServiceImpl implements EventDispatcherService<ReceivedMessageEvent> {

    private IEventPublisher publisher;

    @Autowired
    public void setPublisher(@Qualifier("eventPublisher") IEventPublisher publisher) {

	this.publisher = publisher;
    }

    @Override
    public void dispatch(ReceivedMessageEvent event) {

	// extract message
	MessaggiDTO message = event.getMessage();
	switch (message.getConnettore()) {
	case WebConstants.UD_CONNECTOR:
	    publisher.publish(new MessageForUDEvent(message));
	    break;
	case WebConstants.IO_CONNECTOR:
	    publisher.publish(new MessageForIOItaliaEvent(message));
	    break;
	default:
	    log.info("No events published");
	    break;
	}
    }
}
