package com.paevolution.appiogateway.events.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.paevolution.appiogateway.events.interfaces.IEvent;
import com.paevolution.appiogateway.events.interfaces.IEventPublisher;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EventPublisher implements IEventPublisher {

    private ApplicationEventPublisher publisher;

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {

	this.publisher = publisher;
    }

    @Override
    public <T extends IEvent> void publish(T event) {

	this.publisher.publishEvent(event);
    }
}
