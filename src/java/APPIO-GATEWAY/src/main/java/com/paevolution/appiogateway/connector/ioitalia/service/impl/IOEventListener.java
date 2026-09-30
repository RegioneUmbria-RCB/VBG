package com.paevolution.appiogateway.connector.ioitalia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.paevolution.appiogateway.connector.ioitalia.events.MessageForIOItaliaEvent;
import com.paevolution.appiogateway.connector.ioitalia.events.StatusMessageForIOItaliaEvent;
import com.paevolution.appiogateway.connector.ioitalia.service.IOConnectorService;
import com.paevolution.appiogateway.events.interfaces.IEventListener;

@Service
public class IOEventListener implements IEventListener {

    private IOConnectorService ioConnectorService;

    @Autowired
    public void setIoConnectorService(IOConnectorService ioConnectorService) {

	this.ioConnectorService = ioConnectorService;
    }

    @Async
    @EventListener
    public void onMessageForIOEvent(MessageForIOItaliaEvent event) {

	this.ioConnectorService.process(event.getMessage());
    }

    @Async
    @EventListener
    public void onMessageStatusForIOEvent(StatusMessageForIOItaliaEvent event) {

	this.ioConnectorService.status(event.getMessage());
    }
}
