package it.gruppoinit.nlapec.service.eventi;

import it.gruppoinit.sigepro.schemas.messages.eventi.CategorieEventiBaseType;
import it.gruppoinit.sigepro.schemas.messages.eventi.EsitoOperazioneType;
import it.gruppoinit.sigepro.schemas.messages.eventi.EventoBackofficeInsertRequest;
import it.gruppoinit.sigepro.schemas.messages.eventi.EventoIstanzaInsertRequest;
import it.gruppoinit.sigepro.schemas.messages.eventi.EventoMovimentoInsertRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;

public class EventiWSClient {

    private static final Logger log = LoggerFactory.getLogger(EventiWSClient.class);
    private WebServiceTemplate webServiceTemplate;

    public EsitoOperazioneType eventoIstanzaInsert(EventoIstanzaInsertRequest request) {

	// not implemented
	return null;
    }

    public EsitoOperazioneType eventoMovimentoInsert(EventoMovimentoInsertRequest request) {

	// not implemented
	return null;
    }

    public EsitoOperazioneType eventoBackofficeInsert(String urlWS, String token, String software, String descrizioneMessaggio) {

	EsitoOperazioneType response = null;
	try {
	    log.debug("eventoBackofficeInsert()...");
	    Date date = new Date();
	    SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm");
	    SimpleDateFormat dt2 = new SimpleDateFormat("dd/MM/yyyy");
	    String ora = dt1.format(date);
	    String data = dt2.format(date);
	    EventoBackofficeInsertRequest request = new EventoBackofficeInsertRequest();
	    request.setMessaggio(descrizioneMessaggio);
	    request.setSoftware(software);
	    request.setToken(token);
	    request.setCategoriaEvento(CategorieEventiBaseType.AVVERTIMENTI);
	    response = (EsitoOperazioneType) webServiceTemplate.marshalSendAndReceive(urlWS, request);
	    log.debug("eventoBackofficeInsert()... response : " + response.getEsito());
	} catch (Exception e) {
	}
	return response;
    }

    public EsitoOperazioneType eventoIstanzaInsert(String urlWS, String token, String software, String idIstanza, String errorMsg) {

	EsitoOperazioneType response = null;
	try {
	    log.debug("eventoIstanzaInsert()...");
	    EventoIstanzaInsertRequest request = new EventoIstanzaInsertRequest();
	    request.setCodiceistanza(null);
	    request.setMessaggio(errorMsg);
	    request.setToken(token);
	    request.setCategoriaEvento(CategorieEventiBaseType.AVVERTIMENTI);
	    response = (EsitoOperazioneType) webServiceTemplate.marshalSendAndReceive(urlWS, request);
	    log.debug("eventoIstanzaInsert()... response : " + response.getEsito());
	} catch (Exception e) {
	}
	return response;
    }

    public WebServiceTemplate getWebServiceTemplate() {

	return webServiceTemplate;
    }

    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {

	this.webServiceTemplate = webServiceTemplate;
    }
}
