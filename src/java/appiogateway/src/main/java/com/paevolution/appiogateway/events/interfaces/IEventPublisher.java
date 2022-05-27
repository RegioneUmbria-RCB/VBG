package com.paevolution.appiogateway.events.interfaces;

public interface IEventPublisher {

    <T extends IEvent> void publish(T event);
}
