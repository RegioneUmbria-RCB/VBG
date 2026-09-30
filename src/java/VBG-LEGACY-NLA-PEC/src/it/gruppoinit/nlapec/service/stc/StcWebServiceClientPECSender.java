package it.gruppoinit.nlapec.service.stc;

import it.init.sigepro.rte.AllegatoBinarioRequest;
import it.init.sigepro.rte.AllegatoBinarioResponse;
import it.init.sigepro.rte.CheckTokenRequest;
import it.init.sigepro.rte.CheckTokenResponse;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.LoginRequest;
import it.init.sigepro.rte.LoginResponse;
import it.init.sigepro.rte.types.RiferimentiAllegatoType;

import java.util.Iterator;

import javax.xml.transform.dom.DOMResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class StcWebServiceClientPECSender {

    private static final Logger log = LoggerFactory.getLogger(StcWebServiceClientPECSender.class);
    private String stcWsUrl;
    private String nlaUserId;
    private String nlaPassword;
    private WebServiceTemplate webServiceTemplate;

    public String login() throws Exception {

	log.debug("login()...");
	LoginRequest request = new LoginRequest();
	request.setUsername(getNlaUserId());
	request.setPassword(getNlaPassword());
	LoginResponse response = (LoginResponse) webServiceTemplate.marshalSendAndReceive(getStcWsUrl(), request);
	String token = "";
	if (response.isResult()) {
	    token = response.getToken();
	}
	if (StringUtils.isBlank(token)) {
	    log.error("Errore nel metodo login(): token nullo");
	    throw new Exception("Errore durante la login a STC: token nullo");
	}
	log.debug("login()...done! token={}", token);
	return token;
    }

    public void checkToken(String token) {

	CheckTokenRequest request = new CheckTokenRequest();
	request.setToken(token);
	try {
	    CheckTokenResponse response = (CheckTokenResponse) webServiceTemplate.marshalSendAndReceive(getStcWsUrl(), request);
	    if (!response.isResult()) {
		log.error("checkToken({}): Errore durante la validazione del token: Token non valido", token);
		throw new RuntimeException("Errore durante la validazione del token: Token non valido");
	    }
	} catch (Exception e) {
	    log.error("checkToken({}): Errore durante la validazione del token: {}", token, e.getMessage());
	    throw new RuntimeException("Errore durante la validazione del token: " + e.getMessage());
	}
    }

    public AllegatoBinarioResponse getAllegatoBinario(String token, InserimentoPraticaNLARequest insPraNLArequest, RiferimentiAllegatoType rifAllegato)
	    throws Exception {

	AllegatoBinarioResponse response = new AllegatoBinarioResponse();
	AllegatoBinarioRequest request = new AllegatoBinarioRequest();
	request.setSportelloMittente(insPraNLArequest.getSportelloDestinatario());
	request.setSportelloDestinatario(insPraNLArequest.getSportelloMittente());
	request.setRiferimentiAllegato(rifAllegato);
	request.setToken(token);
	try {
	    response = (AllegatoBinarioResponse) webServiceTemplate.marshalSendAndReceive(getStcWsUrl(), request);
	} catch (Exception e) {
	    String soapFaultDetails = getSOAPFAULT(e);
	    if (StringUtils.isNotBlank(soapFaultDetails)) {
		soapFaultDetails = ", dettaglio: " + soapFaultDetails;
	    }
	    log.error("getAllegatoBinario(): Errore ritornato dalla chiamata al WS STC allegatoBinario: {}{}", e.getMessage(), soapFaultDetails);
	    throw new Exception("Errore ritornato dalla chiamata al WS STC allegatoBinario: " + e.getMessage() + soapFaultDetails);
	}
	return response;
    }

    @SuppressWarnings("rawtypes")
    private String getSOAPFAULT(Exception e) {

	StringBuffer details = new StringBuffer();
	if (e instanceof SoapFaultClientException) {
	    SoapFaultClientException we = (SoapFaultClientException) e;
	    SoapFaultDetail detail = we.getSoapFault().getFaultDetail();
	    if (detail != null) {
		for (Iterator iterator = detail.getDetailEntries(); iterator.hasNext();) {
		    SoapFaultDetailElement el = (SoapFaultDetailElement) iterator.next();
		    if (el.getResult() != null && el.getResult() instanceof DOMResult) {
			DOMResult res = (DOMResult) el.getResult();
			if (res.getNode() != null) {
			    details.append("\n").append(res.getNode().getTextContent());
			}
		    }
		}
	    }
	}
	return details.toString();
    }

    public String getStcWsUrl() {

	return stcWsUrl;
    }

    public void setStcWsUrl(String stcWsUrl) {

	this.stcWsUrl = stcWsUrl;
    }

    public WebServiceTemplate getWebServiceTemplate() {

	return webServiceTemplate;
    }

    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {

	this.webServiceTemplate = webServiceTemplate;
    }

    public String getNlaUserId() {

	return nlaUserId;
    }

    public void setNlaUserId(String nlaUserId) {

	this.nlaUserId = nlaUserId;
    }

    public String getNlaPassword() {

	return nlaPassword;
    }

    public void setNlaPassword(String nlaPassword) {

	this.nlaPassword = nlaPassword;
    }
}
