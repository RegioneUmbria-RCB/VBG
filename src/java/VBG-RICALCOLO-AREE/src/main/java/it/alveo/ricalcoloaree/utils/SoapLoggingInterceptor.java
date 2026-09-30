package it.alveo.ricalcoloaree.utils;

import jakarta.activation.DataHandler;
import jakarta.xml.soap.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class SoapLoggingInterceptor implements ClientInterceptor {

    private static final Logger log = LoggerFactory.getLogger(SoapLoggingInterceptor.class);

    @Override
    public boolean handleRequest(MessageContext messageContext) {
        logSoapMessage("Request", messageContext.getRequest());
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) {
        logSoapMessage("Response", messageContext.getResponse());
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) {
        logSoapMessage("Fault", messageContext.getResponse());
        return true;
    }

    private void logSoapMessage(String messageType, org.springframework.ws.WebServiceMessage message) {
        if (message instanceof SoapMessage) {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                message.writeTo(out);
                log.info("{} SOAP Message: {}", messageType, out.toString(StandardCharsets.UTF_8));
            } catch (IOException e) {
                log.error("Errore durante la log della {} SOAP Message: {}", messageType, e.getMessage());
            }
        }
    }


    private DataHandler extractDataHandlerFromSoapMessage(SOAPMessage soapMessage) {
        try {
            // Ottiene il corpo del messaggio SOAP
            SOAPBody body = soapMessage.getSOAPBody();
            // Naviga nel corpo per trovare l'elemento "documentContent"
            for (Iterator<?> it = body.getChildElements(); it.hasNext(); ) {
                Object element = it.next();
                if (element instanceof SOAPElement soapElement) {
                    // Ricerca ricorsiva dell'elemento "documentContent"
                    DataHandler dataHandler = findDataHandlerInElement(soapElement);
                    if (dataHandler != null) {
                        return dataHandler;
                    }
                }
            }
        } catch (SOAPException e) {
            log.error("Errore durante l'accesso al corpo del messaggio SOAP: {}", e.getMessage());
        }
        return null;
    }

    private DataHandler findDataHandlerInElement(SOAPElement element) {
        try {
            // Controlla se l'elemento corrente è "documentContent"
            if ("documentContent".equals(element.getLocalName())) {
                // Controlla se l'elemento contiene un oggetto DataHandler
                Node dataNode = (Node) element.getFirstChild();
                if (dataNode != null && dataNode instanceof AttachmentPart) {
                    // Ritorna il DataHandler contenuto nell'AttachmentPart
                    return ((AttachmentPart) dataNode).getDataHandler();
                }
            }
            // Cerca ricorsivamente nei sotto-elementi
            for (Iterator<?> it = element.getChildElements(); it.hasNext(); ) {
                Object child = it.next();
                if (child instanceof SOAPElement) {
                    DataHandler dataHandler = findDataHandlerInElement((SOAPElement) child);
                    if (dataHandler != null) {
                        return dataHandler;
                    }
                }
            }
        } catch (SOAPException e) {
            log.error("Errore durante la ricerca di DataHandler: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) {
        // Pulizia se necessario
    }
}
