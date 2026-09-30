package com.paevolution.appiogateway.core.service;

/**
 * Interfaccia di base per i connettori gestori degli eventi
 * 
 * @author simone.vernata
 *
 */
public interface BaseConnectorEventProcessor<T> {

    void process(T payload);
}
