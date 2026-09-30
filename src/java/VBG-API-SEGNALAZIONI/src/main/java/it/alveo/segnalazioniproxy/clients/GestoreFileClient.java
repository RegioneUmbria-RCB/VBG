package it.alveo.segnalazioniproxy.clients;

import it.alveo.oggetti.*;
import jakarta.activation.DataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.math.BigInteger;

import static it.alveo.segnalazioniproxy.utils.Utils.convertDataHandlerToBase64;

@Component
public class GestoreFileClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(GestoreFileClient.class);

    @Value("${gestorefile.url}")
    private String gestoreFileUrl;

    public OggettiInsertResponse oggettiInsert(String token, String filename, String mimeType, DataHandler content) {
        OggettiInsertRequest request = new OggettiInsertRequest();
        request.setToken(token);
        request.setFileName(filename);
        request.setMimeType(mimeType);
        request.setBinaryData(content);

        log.info("OggettiInsertRequest inviata: {}", request);
        OggettiInsertResponse response = (OggettiInsertResponse)
                getWebServiceTemplate().marshalSendAndReceive(gestoreFileUrl, request,
                        new SoapActionCallback("oggettiInsert"));
        log.info("OggettiInsertResponse {}", response);

        return response;
    }

    public String oggettiFind(String token, BigInteger riferimentoEsternoUuid) {
        OggettiFindRequest request = new OggettiFindRequest();
        request.setToken(token);
        request.setId(riferimentoEsternoUuid);

        log.info("OggettiFindRequest inviata: {}", request);
        OggettiFindResponse response = (OggettiFindResponse)
                getWebServiceTemplate().marshalSendAndReceive(gestoreFileUrl, request,
                        new SoapActionCallback("oggettiFind"));
        log.info("OggettiFindResponse {}", response);

        DataHandler dataHandler = response.getBinaryData();
        return convertDataHandlerToBase64(dataHandler);
    }

    public OggettiDeleteResponse oggettiDelete(String token, BigInteger riferimentoEsternoUuid) {
        OggettiDeleteRequest request = new OggettiDeleteRequest();
        request.setToken(token);
        request.setId(riferimentoEsternoUuid);

        log.info("OggettiDeleteRequest inviata: {}", request);
        OggettiDeleteResponse response = (OggettiDeleteResponse)
                getWebServiceTemplate().marshalSendAndReceive(gestoreFileUrl, request,
                        new SoapActionCallback("oggettiDelete"));
        log.info("OggettiDeleteResponse {}", response);

        return response;
    }
}
