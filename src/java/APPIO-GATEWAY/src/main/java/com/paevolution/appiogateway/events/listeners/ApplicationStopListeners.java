package com.paevolution.appiogateway.events.listeners;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import io.netty.util.internal.InternalThreadLocalMap;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ApplicationStopListeners {

    @EventListener
    public void contextClosedListener(ContextClosedEvent contextClosedEvent) {

	InternalThreadLocalMap.remove();
	log.info("Removed inheritedThreadLocal..");
    }
}
