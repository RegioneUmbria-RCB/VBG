package com.paevolution.appiogateway.connector.ud.events;

import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.events.interfaces.IEvent;

import lombok.Data;

@Data
public class StatusMessageForUDEvent implements IEvent {

    private MessaggiDTO message;
    private boolean success;

    public StatusMessageForUDEvent(MessaggiDTO message) {

	this.message = message;
    }
}
