package it.gruppoinit.nlapec.service.mailtipo;

import it.gruppoinit.sigepro.schemas.messages.mailtipo.MailtipoFrontendRequest;
import it.gruppoinit.sigepro.schemas.messages.mailtipo.MailtipoResponse;
import it.init.sigepro.rte.types.DettaglioPraticaType;

import java.util.Iterator;

import javax.xml.transform.dom.DOMResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class MailtipoWSClient {

    private static final Logger log = LoggerFactory.getLogger(MailtipoWSClient.class);
    private WebServiceTemplate webServiceTemplate;

    public MailtipoResponse mailtipoFrontend(String url, String token, int codicemailtipo, DettaglioPraticaType dettaglioPraticaType)
	    throws Exception {

	log.debug("       --> mailtipoFrontend: token={}, url={}", new Object[] { token, url });
	MailtipoFrontendRequest request = new MailtipoFrontendRequest();
	request.setCodicemailtipo(codicemailtipo);
	request.setDettaglioPratica(dettaglioPraticaType);
	request.setToken(token);
	try {
	    MailtipoResponse movimentiMailResponse = (MailtipoResponse) webServiceTemplate.marshalSendAndReceive(url, request);
	    return movimentiMailResponse;
	} catch (Exception e) {
	    String soapErr = getSOAPFAULT(e);
	    log.error("mailConfig: token={}, url={}, codicemailtipo={}, error={}, soapFault={}", new Object[] { token, url, codicemailtipo, e,
		    soapErr });
	    throw e;
	}
    }

    @SuppressWarnings("rawtypes")
    protected String getSOAPFAULT(Exception e) {

	StringBuffer details = new StringBuffer();
	details.append(" ");
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

    public WebServiceTemplate getWebServiceTemplate() {

	return webServiceTemplate;
    }

    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {

	this.webServiceTemplate = webServiceTemplate;
    }
}
