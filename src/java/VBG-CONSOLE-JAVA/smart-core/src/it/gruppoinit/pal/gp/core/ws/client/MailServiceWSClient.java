package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.mailservice.MailService;
import it.gruppoinit.mailservice.MailServicePortType;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.mailservice.schemas.messages.MessageRequest;
import it.gruppoinit.mailservice.schemas.messages.MessageResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;

import java.net.URL;

import javax.xml.ws.soap.MTOMFeature;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MailServiceWSClient extends BaseWsClient {

    private static final Logger log = LoggerFactory.getLogger(MailServiceWSClient.class);

    public String sendMail(Integer codicemovimento, String software, String token, MailMessageType mailMessage) {

	MessageRequest messageRequest = new MessageRequest();
	messageRequest.setToken(token);
	messageRequest.setSoftware(software);
	if (codicemovimento != null) {
	    messageRequest.setCodicemovimento(codicemovimento.toString());
	}
	messageRequest.setMailMessage(mailMessage);
	String url = WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_MAILSERVICE);
	log.debug("sendMail: codicemovimento={}, token={}, software={}, url={}, destinatari={}, oggetto={}, message-id={}", new Object[] {
		codicemovimento, token, software, url, mailMessage.getDestinatari(), mailMessage.getOggetto(), mailMessage.getMessageID() });
	try {
	    MailServicePortType port = getMailServiceWsPort(url);
	    MessageResponse messageResponse = port.sendMail(messageRequest);
	    log.debug("sendMail: response={}", messageResponse.getEsito());
	    return messageResponse.getEsito();
	} catch (Exception e) {
	    log.error("sendMail: codicemovimento={}, token={}, software={}, url={}, destinatari={}, oggetto={}, message-id={}", new Object[] {
		    codicemovimento, token, software, url, mailMessage.getDestinatari(), mailMessage.getOggetto(), mailMessage.getMessageID(), e });
	    throw new RuntimeException(e.getMessage());
	}
    }

    private MailServicePortType getMailServiceWsPort(String wsUrl) throws Exception {

	MailService client = new MailService(new URL(wsUrl));
	MTOMFeature mtomFeature = new MTOMFeature(true, 0);
	MailServicePortType port = client.getMailServicePort(mtomFeature);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	// HTTPClientPolicy - Properties used to configure a client-side HTTP port  
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(12000); // Line #2  
	httpClientPolicy.setReceiveTimeout(360000); // Line #3  
	conduit.setClient(httpClientPolicy);
	return port;
    }
}
