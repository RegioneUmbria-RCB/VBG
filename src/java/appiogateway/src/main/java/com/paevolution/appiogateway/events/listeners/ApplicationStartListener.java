package com.paevolution.appiogateway.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.paevolution.appiogateway.core.service.MessaggiService;
import com.paevolution.appiogateway.events.interfaces.IEventPublisher;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ApplicationStartListener {

    private MessaggiService messaggiService;
    private IEventPublisher publisher;

    public ApplicationStartListener() {

    }

    @Autowired
    public void setMessaggiService(MessaggiService messaggiService) {

	this.messaggiService = messaggiService;
    }

    @Autowired
    public void setPublisher(@Qualifier("eventPublisher") IEventPublisher publisher) {

	this.publisher = publisher;
    }

    @Async
    @EventListener
    public void contextRefreshedEventListener(ContextRefreshedEvent event) {

    }
}
