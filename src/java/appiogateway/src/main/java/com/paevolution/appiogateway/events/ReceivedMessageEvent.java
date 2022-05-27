package com.paevolution.appiogateway.events;

import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.events.interfaces.IEvent;

import lombok.Data;

@Data
public class ReceivedMessageEvent implements IEvent {

    private MessaggiDTO message;
    private boolean success;

    public ReceivedMessageEvent(MessaggiDTO message) {

	this.message = message;
    }
}
