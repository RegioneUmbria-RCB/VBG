package com.paevolution.appiogateway.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.paevolution.appiogateway.core.service.EventDispatcherService;
import com.paevolution.appiogateway.events.ReceivedMessageEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReceivedMessageListener {

    private EventDispatcherService<ReceivedMessageEvent> eventDispatcherService;

    @Autowired
    public void setEventDispatcherService(
	    @Qualifier("receivedMessageDispatcherServiceImpl") EventDispatcherService<ReceivedMessageEvent> eventDispatcherService) {

	this.eventDispatcherService = eventDispatcherService;
    }

    @Async
    @EventListener
    public void onReceivedMessageEvent(ReceivedMessageEvent event) {

	if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
	    log.info("onReceivedMessageEvent# Security Context inside @Async Logic "
		    + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}
	this.eventDispatcherService.dispatch(event);
    }
}
