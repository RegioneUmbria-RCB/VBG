package it.gruppoinit.nlaenti.service.mailservice;

import it.gruppoinit.schema.mailservice.MailMessageType;
import it.gruppoinit.schema.mailservice.MessageRequest;
import it.gruppoinit.schema.mailservice.MessageResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;

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
	    log.error(
		    "sendMail: codicemovimento={}, token={}, software={}, url={}, destinatari={}, oggetto={}, message-id={}, ERROR={}",
		    new Object[] { codicemovimento, token, software, url, mailMessage.getDestinatari(), mailMessage.getOggetto(),
			    mailMessage.getMessageID(), e.getMessage() });
	    throw new RuntimeException(e.getMessage());
	}
    }

    public WebServiceTemplate getWebServiceTemplate() {

	return webServiceTemplate;
    }

    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {

	this.webServiceTemplate = webServiceTemplate;
    }
}
