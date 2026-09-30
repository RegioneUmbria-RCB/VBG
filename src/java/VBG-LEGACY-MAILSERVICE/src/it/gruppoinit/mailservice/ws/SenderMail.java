package it.gruppoinit.mailservice.ws;

import it.gruppoinit.mailservice.helper.MailHelper;
import it.gruppoinit.mailservice.helper.MailHelper2;
import it.gruppoinit.mailservice.oggetti.MailServerConfigBean;
import it.gruppoinit.mailservice.oggetti.MyMimeMessage;
import it.gruppoinit.mailservice.oggetti.VerticalizzazioniMailServiceBean;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.mailservice.schemas.messages.MessageRequest;
import it.gruppoinit.mailservice.schemas.messages.MessageResponse;
import it.gruppoinit.sigepro.definitions.movimentimail.MovimentiMailServiceStub.MovimentiMailResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderMail {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MessageResponse sendMail(MessageRequest messageRequest) throws Exception {

	logger.debug("sendMail");
	logger.debug("{}", messageRequestToString(messageRequest));
	MailServerConfigBean serverMailConfig;
	MailServerConfigBean serverMailInputConfig = null;
	MessageResponse response = new MessageResponse();
	Properties propServerMail;
	Properties propServerMailInput = null;
	Properties deployProperties = getDeployProperties();
	Session session;
	Session sessionInput = null;
	String esito = "";
	MailHelper helper = null;
	MailHelper2 helper2 = null;
	MyMimeMessage mimeMessage = null;
	if (StringUtils.isNotBlank(deployProperties.getProperty("mailserver.host"))) {
	    //
	    try {
		logger.debug("sendMail mailserver.host={}", deployProperties.getProperty("mailserver.host"));
		helper2 = new MailHelper2();
		serverMailConfig = helper2.populateServerMailConfigBean(deployProperties);
		helper2.populateSocksInfo(serverMailConfig, deployProperties);
		propServerMail = helper2.populatePropertyMail(serverMailConfig);
		session = helper2.getSession(serverMailConfig, propServerMail);
		mimeMessage = new MyMimeMessage(session);
		helper2.buildMyMimeMessage(mimeMessage, messageRequest.getMailMessage(), serverMailConfig);
		mimeMessage.saveChanges();
		Transport tr = session.getTransport();
		tr.connect();
		Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
		tr.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		tr.close();
		esito = "OK";
	    } catch (Exception e) {
		logger.error("sendMail", e);
		throw new Exception("Errore nell'invio della mail: " + e.getMessage());
	    }
	} else {
	    // Recupero le configurazione per inviare mail in uscita
	    logger.debug("sendMail:Recupero le configurazioni della casella di posta per inviare l'email");
	    helper = new MailHelper();
	    logger.debug("sendMail: MessageRequest token={}, software={}", messageRequest.getToken(), messageRequest.getSoftware());
	    helper.init(messageRequest.getToken());
	    logger.debug("sendMail: helper.populateServerMailConfig - Action: SEND");
	    serverMailConfig = helper.populateServerMailConfig(messageRequest.getToken(), messageRequest.getSoftware());
	    helper.populateSocksInfo(serverMailConfig, deployProperties);
	    logger.debug("sendMail: helper.populatePropertyMail");
	    propServerMail = MailHelper.populatePropertyMail(serverMailConfig);
	    logger.debug("sendMail: helper.populateVerticalizzazioniMailService");
	    VerticalizzazioniMailServiceBean verticalizzazioni = helper.populateVerticalizzazioni(messageRequest.getToken(),
		    messageRequest.getSoftware());
	    logger.debug("sendMail: helper.getSession");
	    session = helper.getSession(serverMailConfig, propServerMail);
	    logger.debug("sendMail: new MyMimeMessage");
	    mimeMessage = new MyMimeMessage(session);
	    logger.debug("sendMail: buildMyMimeMessage");
	    helper.buildMyMimeMessage(mimeMessage, messageRequest.getMailMessage(), serverMailConfig, verticalizzazioni);
	    try {
		// Se presente nella verticalizzazione MAIL_SERVICE la forlder dove salvare le email inviate
		//devo caricare le impostazioni di input di posta per salvare l'email che stiamo inviando
		boolean isSaveEMail = false;
		Folder f = null;
		if (verticalizzazioni != null && StringUtils.isNotBlank(verticalizzazioni.getFolder())) {
		    isSaveEMail = true;
		    logger.debug("sendMail:Attivata funzionalità che salva mail inviata nella cartella {}", verticalizzazioni.getFolder());
		    logger.debug("sendMail:Recupero le configurazioni della casella di posta per salvare l'email inviata (Configurazione mail Input)");
		    logger.debug("sendMail: helper.populateServerMailConfigInput - Action: READ");
		    serverMailInputConfig = helper.populateServerMailConfigInput(messageRequest.getToken(), messageRequest.getSoftware());
		    logger.debug("sendMail: helper.populatePropertyMailInput");
		    propServerMailInput = helper.populatePropertyMailInput(serverMailInputConfig);
		    logger.debug("sendMail: helper.getSession - parametri session lettura casella mail");
		    sessionInput = helper.getSession(serverMailInputConfig, propServerMailInput);
		    logger.debug("sendMail: load  store");
		    Store store = sessionInput.getStore();
		    logger.debug("sendMail: connect to store");
		    store.connect(serverMailInputConfig.getImapUserId(), serverMailInputConfig.getImapPassword());
		    // obtain reference to "Sent" folder
		    logger.debug("sendMail: load folder store");
		    f = store.getFolder(verticalizzazioni.getFolder());
		    logger.debug("sendMail: check folder exsist");
		}
		// Se la folder è impostata, ma non corretta devo ritornare un errore che blocca anche l'invio email
		// Se configurata significa che si vuole salvare la mail quindi se non posso salvarla non dovrò neache inviarla
		if (isSaveEMail) {
		    if (f == null || !f.exists()) {
			logger.debug("sendMail: add message folder not exsist");
			throw new Exception(
				"Errore nell'invio della mail: nella verticalizzazione MAIL_SERVICE è stata configurata una POSTA_USCITA_FOLDERNAME non esistente");
		    }
		}
		logger.debug("sendMail: mimeMessage.saveChanges()");
		mimeMessage.saveChanges();
		logger.debug("sendMail: session.getTransport()");
		Transport tr = session.getTransport();
		logger.debug("sendMail: tr.connect to host: {}, port: {}", serverMailConfig.getSmtpHost(), serverMailConfig.getSmtpPort());
		tr.connect(serverMailConfig.getSmtpHost(), serverMailConfig.getSmtpUserId(), serverMailConfig.getSmtpPassword());
		logger.debug("sendMail: tr.sendMessage");
		tr.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		
		logger.debug("sendMail: tr.close");
		tr.close();
		if (isSaveEMail) {
		    logger.debug("sendMail: add message folder {}", verticalizzazioni.getFolder());
		    f.appendMessages(new Message[] { mimeMessage });
		}
	    } catch (AuthenticationFailedException e) {
		logger.error("sendMail AuthenticationFailedException", e);
		throw new Exception("Errore nell'invio della mail, AuthenticationFailedException: " + e.getMessage());
	    } catch (Exception e) {
		logger.error("sendMail", e);
		throw new Exception("Errore nell'invio della mail: " + e.getMessage());
	    }
	    logger.debug("sendMail: esito=OK");
	    esito = "OK";
	    if (StringUtils.isNotBlank(messageRequest.getCodicemovimento())) {
		logger.debug("sendMail: helper.notificaMail");
		MovimentiMailResponse notificaResponse = helper.notificaMail(messageRequest, serverMailConfig);
		if (notificaResponse != null) {
		    esito = notificaResponse.getId().toString();
		} else {
		    String tmp = "n.d.";
		    try {
			tmp = messageRequest.getMailMessage().getMessageID();
		    } catch (Exception ex) {
		    }
		    esito = "La mail e' stata inviata correttamente ma non e' stato possibile inserirla nell’archivio mail (l'informazione e' stata salvata negli eventi di sistema "
			    + messageRequest.getCodicemovimento() + ") - Id message :" + tmp + ")";
		}
	    }
	    //
	}
	response.setEsito(esito);
	logger.debug("sendMail: return {}", response.getEsito());
	//	// Controllare se farlo
	//	try {
	//	    if (mimeMessage != null && verticalizzazioni != null && StringUtils.isNotBlank(verticalizzazioni.getFolder())) {
	//		logger.debug("sendMail: helper.populateServerMailConfigInput - Action: READ");
	//		serverMailInputConfig = helper.populateServerMailConfigInput(messageRequest.getToken(), messageRequest.getSoftware());
	//		logger.debug("sendMail: helper.populatePropertyMailInput");
	//		propServerMailInput = helper.populatePropertyMailInput(serverMailInputConfig);
	//		logger.debug("sendMail: helper.getSession - parametri session lettura casella mail");
	//		session = helper.getSession(serverMailInputConfig, propServerMailInput);
	//		logger.debug("sendMail: helper.getSession - parametri session lettura casella mail");
	//		logger.debug("sendMail: load  store");
	//		Store store = session.getStore();
	//		logger.debug("sendMail: connect to store");
	//		store.connect(serverMailInputConfig.getImapUserId(), serverMailInputConfig.getImapPassword());
	//		// obtain reference to "Sent" folder
	//		logger.debug("sendMail: load folder store");
	//		Folder f = store.getFolder(verticalizzazioni.getFolder());
	//		logger.debug("sendMail: check folder exsist");
	//		if (f.exists()) {
	//		    logger.debug("sendMail: add message folder exsist");
	//		    f.appendMessages(new Message[] { mimeMessage });
	//		} else {
	//		    logger.debug("sendMail: add message folder not exsist");
	//		}
	//	    }
	//	} catch (AuthenticationFailedException e) {
	//	    logger.error("sendMail AuthenticationFailedException", e);
	//	    throw new Exception("Errore nell'invio della mail, AuthenticationFailedException: " + e.getMessage());
	//	} catch (Exception e) {
	//	    logger.error("sendMail", e);
	//	    throw new Exception("Errore nell'invio della mail: " + e.getMessage());
	//	}
	return response;
    }

    private String messageRequestToString(MessageRequest messageRequest) {

	String result = "{MessageRequest:\n";
	if (messageRequest != null) {
	    result += "Token=" + messageRequest.getToken();
	    result += "\nSoftware=" + messageRequest.getSoftware();
	    result += "\nCodiceMovimento=" + messageRequest.getCodicemovimento();
	    if (messageRequest.getMailMessage() != null) {
		result += "\nMessageID=" + messageRequest.getMailMessage().getMessageID();
		result += "\nMittente=" + messageRequest.getMailMessage().getMittente();
		result += "\nDestinatari=" + messageRequest.getMailMessage().getDestinatari();
	    }
	}
	return result += "}";
    }

    private Properties getDeployProperties() {

	InputStream is = null;
	try {
	    is = this.getClass().getClassLoader().getResourceAsStream("deploy.properties");
	    Properties p = new Properties();
	    p.load(is);
	    return p;
	} catch (Exception e) {
	    logger.error("getDeployProperties: errore durante il caricamento del file deploy.properties", e);
	} finally {
	    if (is != null) {
		try {
		    is.close();
		} catch (IOException e) {
		    // no log
		}
	    }
	}
	return null;
    }

    public static void main(String[] args) throws Exception {

	SenderMail sender = new SenderMail();
	MessageRequest req = new MessageRequest();
	MailMessageType msg = new MailMessageType();
	msg.setCorpoMail("corpo della mail");
	msg.setOggetto("oggetto della mail");
	msg.setDestinatari("fabrizio.corsetti@gruppoinit.it");
	// msg.setMittente("riccardo.bocci@gruppoinit.it");
	msg.setInviaComeHtml(true);
	req.setMailMessage(msg);
	MessageResponse resp = sender.sendMail(req);
	System.out.println(resp.getEsito());
    }
}
