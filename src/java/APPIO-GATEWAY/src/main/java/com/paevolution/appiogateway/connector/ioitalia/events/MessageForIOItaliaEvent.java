package com.paevolution.appiogateway.connector.ioitalia.events;

import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.events.interfaces.IEvent;

import lombok.Data;

@Data
public class MessageForIOItaliaEvent implements IEvent {

    private MessaggiDTO message;
    private boolean success;

    public MessageForIOItaliaEvent(MessaggiDTO message) {

	this.message = message;
    }
}
