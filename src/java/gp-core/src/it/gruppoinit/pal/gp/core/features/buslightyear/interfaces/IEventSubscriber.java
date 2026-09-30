package it.gruppoinit.pal.gp.core.features.buslightyear.interfaces;

import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;

/**
 * 
 * Interfaccia di un generico subscriber
 *
 * @param <T>
 */
public interface IEventSubscriber<T extends IEvent> extends IEventSubcriberBase {

    /**
     * Il metodo viene invocato al publish dei messaggi sottoscritti
     * 
     * @param e
     */
    void onEvent(T e) throws EventAbortedException;
}
