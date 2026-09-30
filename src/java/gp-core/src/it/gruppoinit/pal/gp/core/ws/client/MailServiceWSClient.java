package it.gruppoinit.pal.gp.core.ws.client;

import java.math.BigInteger;
import javax.xml.ws.BindingProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import it.gruppoinit.mailservice.MailServicePortType;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.mailservice.schemas.messages.MessageRequest;
import it.gruppoinit.mailservice.schemas.messages.MessageRequest2;
import it.gruppoinit.mailservice.schemas.messages.MessageResponse;
import it.gruppoinit.mailservice.schemas.messages.MessageResponse2;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;

@Component
public class MailServiceWSClient extends BaseWsClient {

    private static final Logger log = LoggerFactory.getLogger(MailServiceWSClient.class);
    private static long DEFAULT_CONNECT_TIMEOUT = 12000;
    private static long DEFAULT_RECEIVE_TIMEOUT = 360000;

    public String sendMail(Integer codicemovimento, String software, String token, MailMessageType mailMessage) {

	return this.sendMail(codicemovimento, software, token, mailMessage, DEFAULT_CONNECT_TIMEOUT, DEFAULT_RECEIVE_TIMEOUT);
    }

    public String sendMail(Integer codicemovimento, String software, String token, MailMessageType mailMessage, long connectTimeout,
	    long receiveTimeout) {

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
	    MailServicePortType port = getMailServiceWsPort(url, connectTimeout, receiveTimeout);
	    MessageResponse messageResponse = port.sendMail(messageRequest);
	    log.debug("sendMail: response={}", messageResponse.getEsito());
	    return messageResponse.getEsito();
	} catch (Exception e) {
	    log.error("sendMail: codicemovimento={}, token={}, software={}, url={}, destinatari={}, oggetto={}, message-id={}", new Object[] {
		    codicemovimento, token, software, url, mailMessage.getDestinatari(), mailMessage.getOggetto(), mailMessage.getMessageID(), e });
	    throw new RuntimeException(e.getMessage());
	}
    }

    public String sendMail2(Integer codicemovimento, String software, Integer accountId, String token, MailMessageType mailMessage)
	    throws FunzioneBusinessRemotaException {

	return this.sendMail2(codicemovimento, software, accountId, null, token, mailMessage, DEFAULT_CONNECT_TIMEOUT, DEFAULT_RECEIVE_TIMEOUT);
    }

    public String sendMail2(Integer codicemovimento, String software, Integer accountId, String codiceComune, String token,
	    MailMessageType mailMessage) throws FunzioneBusinessRemotaException {

	return this.sendMail2(codicemovimento, software, accountId, codiceComune, token, mailMessage, DEFAULT_CONNECT_TIMEOUT,
		DEFAULT_RECEIVE_TIMEOUT);
    }

    public String sendMail2(Integer codicemovimento, String software, Integer accountId, String codiceComune, String token,
	    MailMessageType mailMessage, long connectTimeout, long receiveTimeout) throws FunzioneBusinessRemotaException {

	MessageRequest2 messageRequest2 = new MessageRequest2();
	messageRequest2.setToken(token);
	messageRequest2.setSoftware(software);
	if (accountId != null) {
	    messageRequest2.setAccountid(new BigInteger(accountId.toString()));
	}
	if (StringUtils.isNotBlank(codiceComune)) {
	    messageRequest2.setCodicecomune(codiceComune);
	}
	if (codicemovimento != null) {
	    messageRequest2.setCodicemovimento(codicemovimento.toString());
	}
	messageRequest2.setMailMessage(mailMessage);
	String url = WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_MAILSERVICE);
	log.debug(
		"sendMail2: codicemovimento={}, token={}, software={},idaccount={},codicecomune={}, url={}, destinatari={}, oggetto={}, message-id={}",
		new Object[] { codicemovimento, token, software, messageRequest2.getAccountid(), codiceComune, url, mailMessage.getDestinatari(),
			mailMessage.getOggetto(), mailMessage.getMessageID() });
	try {
	    MailServicePortType port = getMailServiceWsPort(url, connectTimeout, receiveTimeout);
	    MessageResponse2 messageResponse2 = port.sendMail2(messageRequest2);
	    log.debug("sendMail2: response={}", messageResponse2.getEsito());
	    return messageResponse2.getEsito();
	} catch (Exception e) {
	    log.error("sendMail2: codicemovimento={}, token={}, software={}, url={}, destinatari={}, oggetto={}, message-id={}", new Object[] {
		    codicemovimento, token, software, url, mailMessage.getDestinatari(), mailMessage.getOggetto(), mailMessage.getMessageID(), e });
	    throw new FunzioneBusinessRemotaException(e.getMessage());
	}
    }

    private MailServicePortType getMailServiceWsPort(String wsUrl, long connectTimeout, long receiveTimeout) throws Exception {

	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(MailServicePortType.class);
	factory.setAddress(wsUrl);
	MailServicePortType port = (MailServicePortType) factory.create();
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	BindingProvider bp = (BindingProvider) port;
	bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsUrl);
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(connectTimeout); // Line #2  
	httpClientPolicy.setReceiveTimeout(receiveTimeout); // Line #3  
	conduit.setClient(httpClientPolicy);
	return port;
    }
    //    @Override
    //    public MessageResponse sendMail(MessageRequest messageIn) {
    //
    //	// TODO Auto-generated method stub
    //	return null;
    //    }
    //
    //    @Override
    //    public MessageResponse2 sendMail2(MessageRequest2 messageIn) {
    //
    //	// TODO Auto-generated method stub
    //	return null;
    //    }
}
