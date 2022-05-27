package com.paevolution.appiogateway.events;

import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.events.interfaces.IEvent;

import lombok.Data;

@Data
public class StatusMessageEvent implements IEvent {

    private MessaggiDTO message;
    private boolean success;

    public StatusMessageEvent(MessaggiDTO message) {

	this.message = message;
    }
}
