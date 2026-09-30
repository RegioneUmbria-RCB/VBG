package com.paevolution.appiogateway.connector.ud.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.paevolution.appiogateway.connector.ud.client.service.UDConnectorService;
import com.paevolution.appiogateway.connector.ud.events.MessageForUDEvent;
import com.paevolution.appiogateway.connector.ud.events.StatusMessageForUDEvent;
import com.paevolution.appiogateway.events.interfaces.IEventListener;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UDEventListener implements IEventListener {

    private UDConnectorService udConnectorService;

    @Autowired
    public void setUdConnectorService(UDConnectorService udConnectorService) {

	this.udConnectorService = udConnectorService;
    }

    @Async
    @EventListener
    public void onMessageForUDEvent(MessageForUDEvent event) {

	this.udConnectorService.process(event.getMessage());
    }

    @Async
    @EventListener
    public void onMessageStatusForUDEvent(StatusMessageForUDEvent event) {

	this.udConnectorService.status(event.getMessage());
    }
}
