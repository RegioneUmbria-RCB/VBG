package com.paevolution.appiogateway.connector.ioitalia.service;

import com.paevolution.appiogateway.connector.ioitalia.client.model.InlineResponse201;
import com.paevolution.appiogateway.connector.ioitalia.client.model.MessageResponseWithContent;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.exceptions.WebClientExceptionsWrapper;

public interface IOBaseConnectorService {

    public void process(MessaggiDTO message);

    public void status(MessaggiDTO message);

    public void tryToPostNewMessage(MessaggiDTO message);

    public MessaggiDTO getMessageStatus(MessaggiDTO message);

    /**
     * Permette di chiamare direttamente il servizio messages di IO Italia, per creare un nuovo messaggio, tramite il
     * metodo POST .
     * 
     * @param message
     * @return
     */
    public InlineResponse201 directPOSTMessage(MessaggiDTO message) throws WebClientExceptionsWrapper, Exception;

    /**
     * Permette di chiamare direttamente il servizio messages di IO Italia, per ottenere il messaggio creato, tramite il
     * metodo GET.
     * 
     * @param message
     * @return
     */
    public MessageResponseWithContent directGETMessage(MessaggiDTO message);
}
