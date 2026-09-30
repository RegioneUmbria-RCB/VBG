package com.paevolution.appiogateway.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.paevolution.appiogateway.core.service.EventDispatcherService;
import com.paevolution.appiogateway.events.StatusMessageEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatusMessageListener {

    private EventDispatcherService<StatusMessageEvent> eventDispatcherService;

    @Autowired
    public void setEventDispatcherService(
	    @Qualifier("statusMessageDispatcherServiceImpl") EventDispatcherService<StatusMessageEvent> eventDispatcherService) {

	this.eventDispatcherService = eventDispatcherService;
    }

    @Async
    @EventListener
    public void onStatusMessageEvent(StatusMessageEvent event) {

	log.info("onStatusMessageEvent# Security Context inside @Async Logic "
		+ SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	this.eventDispatcherService.dispatch(event);
    }
}
