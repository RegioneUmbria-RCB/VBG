package it.gruppoinit.nlapec.service.mailservice;

import it.gruppoinit.nlapec.schema.mailservice.MailMessageType;
import it.gruppoinit.nlapec.schema.mailservice.MessageRequest;
import it.gruppoinit.nlapec.schema.mailservice.MessageRequest2;
import it.gruppoinit.nlapec.schema.mailservice.MessageResponse;
import it.gruppoinit.nlapec.schema.mailservice.MessageResponse2;

import java.math.BigInteger;
import java.util.Iterator;

import javax.xml.transform.dom.DOMResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class MailServiceWSClient {

    private static final Logger log = LoggerFactory.getLogger(MailServiceWSClient.class);
    private WebServiceTemplate webServiceTemplate;

    public String sendMail(String url, Integer codicemovimento, String software, String token, MailMessageType mailMessage) {

	MessageRequest messageRequest = new MessageRequest();
	messageRequest.setToken(token);
	messageRequest.setSoftware(software);
	if (codicemovimento != null) {
	    messageRequest.setCodicemovimento(codicemovimento.toString());
	}
	messageRequest.setMailMessage(mailMessage);
	log.debug("sendMail: codicemovimento={}, token={}, software={}, url={}, destinatari={}, oggetto={}, message-id={}", new Object[] {
		codicemovimento, token, software, url, mailMessage.getDestinatari(), mailMessage.getOggetto(), mailMessage.getMessageID() });
	try {
	    MessageResponse messageResponse = (MessageResponse) webServiceTemplate.marshalSendAndReceive(url, messageRequest);
	    log.debug("sendMail: response={}", messageResponse.getEsito());
	    return messageResponse.getEsito();
	} catch (Exception e) {
	    log.error("sendMail: codicemovimento={}, token={}, software={}, url={}, destinatari={}, oggetto={}, message-id={}", new Object[] {
		    codicemovimento, token, software, url, mailMessage.getDestinatari(), mailMessage.getOggetto(), mailMessage.getMessageID() });
	    log.error("sendMail: error detail", e);
	    throw new RuntimeException(e.getMessage());
	}
    }

    public String sendMail2(String url, Integer codicemovimento, String software, BigInteger idAccount, String codicecomune, String token,
	    MailMessageType mailMessage) {

	MessageRequest2 messageRequest2 = new MessageRequest2();
	messageRequest2.setToken(token);
	messageRequest2.setSoftware(software);
	if (idAccount != null) {
	    messageRequest2.setAccountid(idAccount);
	}
	if (StringUtils.isNotBlank(codicecomune)) {
	    messageRequest2.setCodicecomune(codicecomune);
	} else {
	    messageRequest2.setCodicecomune(null);
	}
	if (codicemovimento != null) {
	    messageRequest2.setCodicemovimento(codicemovimento.toString());
	}
	messageRequest2.setMailMessage(mailMessage);
	log.debug(
		"sendMail: codicemovimento={}, token={}, software={},idaccount={}, codicecomune={}, url={}, destinatari={}, oggetto={}, message-id={}",
		new Object[] { codicemovimento, token, software, idAccount, codicecomune, url, mailMessage.getDestinatari(),
			mailMessage.getOggetto(), mailMessage.getMessageID() });
	try {
	    MessageResponse2 messageResponse2 = (MessageResponse2) webServiceTemplate.marshalSendAndReceive(url, messageRequest2);
	    log.debug("sendMail: response={}", messageResponse2.getEsito());
	    return messageResponse2.getEsito();
	} catch (Exception e) {
	    log.error(
		    "sendMail: codicemovimento={}, token={}, software={},idaccount={}, codicecomune={}, url={}, destinatari={}, oggetto={}, message-id={}",
		    new Object[] { codicemovimento, token, software, idAccount, codicecomune, url, mailMessage.getDestinatari(),
			    mailMessage.getOggetto(), mailMessage.getMessageID() });
	    log.error("sendMail: error detail", e);
	    throw new RuntimeException(e.getMessage());
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
