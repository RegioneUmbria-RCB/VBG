package it.gruppoinit.mailservice.ws;

import it.gruppoinit.mailservice.helper.MailHelper;
import it.gruppoinit.mailservice.helper.MailHelper2;
import it.gruppoinit.mailservice.oggetti.MailServerConfigBean;
import it.gruppoinit.mailservice.oggetti.MyMimeMessage;
import it.gruppoinit.mailservice.oggetti.VerticalizzazioniMailServiceBean;
import it.gruppoinit.mailservice.schemas.messages.MessageRequest2;
import it.gruppoinit.mailservice.schemas.messages.MessageResponse2;

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

public class SenderMail2 {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MessageResponse2 sendMail2(MessageRequest2 messageRequest2) throws Exception {

	logger.debug("sendMail2");
	logger.debug("{}", messageRequestToString(messageRequest2));
	MailServerConfigBean serverMailConfig;
	MailServerConfigBean serverMailInputConfig = null;
	MessageResponse2 response = new MessageResponse2();
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
	    logger.debug("sendMail2# mailserver.host recuperato dal deploy.properties");
	    try {
		logger.debug("sendMail2# mailserver.host={}", deployProperties.getProperty("mailserver.host"));
		helper2 = new MailHelper2();
		serverMailConfig = helper2.populateServerMailConfigBean(deployProperties);
		helper2.populateSocksInfo(serverMailConfig, deployProperties);
		propServerMail = helper2.populatePropertyMail(serverMailConfig);
		session = helper2.getSession(serverMailConfig, propServerMail);
		mimeMessage = new MyMimeMessage(session);
		helper2.buildMyMimeMessage(mimeMessage, messageRequest2.getMailMessage(), serverMailConfig);
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
		logger.error("sendMail2", e);
		throw new Exception("Errore nell'invio della mail: " + e.getMessage());
	    }
	} else {
	    // Recupero le configurazione per inviare mail in uscita
	    logger.debug("sendMail2# le configurazioni della casella di posta per inviare l'email");
	    helper = new MailHelper(messageRequest2);
	    logger.debug("sendMail2# MessageRequest token={}, idAccount={}, codicecomune={}, software={}", new Object[] { messageRequest2.getToken(),
		    messageRequest2.getAccountid(), messageRequest2.getCodicecomune(), messageRequest2.getSoftware() });
	    helper.init(messageRequest2.getToken());
	    logger.debug("sendMail2# helper.populateServerMailConfig - Action: SEND");
	    // mod
	    serverMailConfig = helper.populateServerMailConfig2(messageRequest2.getToken(), messageRequest2.getAccountid(),
		    messageRequest2.getCodicecomune(), messageRequest2.getSoftware());
	    helper.setMailServerConfig(serverMailConfig);
	    helper.populateSocksInfo(serverMailConfig, deployProperties);
	    logger.debug("sendsendMail2Mail# helper.populatePropertyMail");
	    propServerMail = helper.populatePropertyMail(serverMailConfig);
	    logger.debug("sendMail2: helper.populateVerticalizzazioniMailService");
	    // la verticalizzazione dovrà essere filtrata anche per codice comune!?!?!? FIXME 
	    VerticalizzazioniMailServiceBean verticalizzazioni = helper.populateVerticalizzazioni(messageRequest2.getToken(),
		    messageRequest2.getSoftware());
	    logger.debug("sendMail2: helper.getSession");
	    session = helper.getSession(serverMailConfig, propServerMail);
	    logger.debug("sendMail2: new MyMimeMessage");
	    mimeMessage = new MyMimeMessage(session);
	    logger.debug("sendMail: buildMyMimeMessage");
	    helper.buildMyMimeMessage(mimeMessage, messageRequest2.getMailMessage(), serverMailConfig, verticalizzazioni);
	    try {
		// Se presente nella verticalizzazione MAIL_SERVICE la forlder dove salvare le email inviate
		//devo caricare le impostazioni di input di posta per salvare l'email che stiamo inviando
		boolean isSaveEMail = false;
		Folder f = null;
		if (verticalizzazioni != null && StringUtils.isNotBlank(verticalizzazioni.getFolder())) {
		    isSaveEMail = true;
		    logger.debug("sendMail2 funzionalità che salva mail inviata nella cartella {}", verticalizzazioni.getFolder());
		    logger.debug("sendMail2 le configurazioni della casella di posta per salvare l'email inviata (Configurazione mail Input)");
		    logger.debug("sendMail2: helper.populateServerMailConfigInput - Action: READ");
		    serverMailInputConfig = helper.populateServerMailConfigInput(messageRequest2.getToken(), messageRequest2.getSoftware());
		    logger.debug("sendMail2: helper.populatePropertyMailInput");
		    propServerMailInput = helper.populatePropertyMailInput(serverMailInputConfig);
		    logger.debug("sendMail2: helper.getSession - parametri session lettura casella mail");
		    sessionInput = helper.getSession(serverMailInputConfig, propServerMailInput);
		    logger.debug("sendMail2: load  store");
		    Store store = sessionInput.getStore();
		    logger.debug("sendMail2: connect to store");
		    store.connect(serverMailInputConfig.getImapUserId(), serverMailInputConfig.getImapPassword());
		    // obtain reference to "Sent" folder
		    logger.debug("sendMail2: load folder store");
		    f = store.getFolder(verticalizzazioni.getFolder());
		    logger.debug("sendMail2: check folder exsist");
		}
		// Se la folder è impostata, ma non corretta devo ritornare un errore che blocca anche l'invio email
		// Se configurata significa che si vuole salvare la mail quindi se non posso salvarla non dovrò neache inviarla
		if (isSaveEMail) {
		    if (f == null || !f.exists()) {
			logger.debug("sendMail2: add message folder not exsist");
			throw new Exception(
				"Errore nell'invio della mail: nella verticalizzazione MAIL_SERVICE è stata configurata una POSTA_USCITA_FOLDERNAME non esistente");
		    }
		}
		logger.debug("sendMail2: mimeMessage.saveChanges()");
		mimeMessage.saveChanges();
		logger.debug("sendMail2: session.getTransport()");
		/**
		 * SMTP protocol: smtps (port 465) v. msa (port 587) Ports 465 and 587 are intended for email client to
		 * email server communication - sending out email using SMTP protocol.
		 * 
		 * Port 465 is for smtps SSL encryption is started automatically before any SMTP level communication.
		 * 
		 * Port 587 is for msa It is almost like standard SMTP port. MSA should accept email after
		 * authentication (e.g. after SMTP AUTH). It helps to stop outgoing spam when netmasters of DUL ranges
		 * can block outgoing connections to SMTP port (port 25). SSL encryption may be started by STARTTLS
		 * command at SMTP level if server supports it and your ISP does not filter server's EHLO reply
		 * (reported 2014).
		 */
		if (serverMailConfig.getSmtpPort().equals("587")) { // FIX LUCCA
		    Transport.send(mimeMessage);
		} else {
		    Transport tr = session.getTransport();
		    logger.debug("sendMail2: tr.connect to host: {}, port: {}", serverMailConfig.getSmtpHost(), serverMailConfig.getSmtpPort());
		    if (serverMailConfig.isSmtpUseAuth()) {
			tr.connect(serverMailConfig.getSmtpHost(), serverMailConfig.getSmtpUserId(), serverMailConfig.getSmtpPassword());
		    } else {
			tr.connect();
		    }
		    logger.debug("sendMail2: tr.sendMessage");
		    tr.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		    logger.debug("sendMail2: tr.close");
		    tr.close();
		}
		if (isSaveEMail) {
		    logger.debug("sendMail2: add message folder {}", verticalizzazioni.getFolder());
		    f.appendMessages(new Message[] { mimeMessage });
		}
	    } catch (AuthenticationFailedException e) {
		logger.error("sendMail2 AuthenticationFailedException", e);
		throw new Exception("Errore nell'invio della mail, AuthenticationFailedException: " + e.getMessage());
	    } catch (Exception e) {
		logger.error("sendMail2", e);
		throw new Exception("Errore nell'invio della mail: " + e.getMessage());
	    }
	    logger.debug("sendMail2: esito=OK");
	    esito = "OK";
	    if (StringUtils.isNotBlank(messageRequest2.getCodicemovimento())) {
		// Attiva thread separato che prova per tre volte ad agganciare l'email al movimento.
		// E' necessario in caso di invio email  
		try {
		    logger.debug("sendMail2: helper.notificaMail");
		    helper.start();
		} catch (Exception ex) {
		    esito = "La mail e' stata inviata correttamente ma non e' stato possibile inserirla nell’archivio mail (l'informazione e' stata salvata negli eventi di sistema "
			    + messageRequest2.getCodicemovimento() + ")";
		}
		//		logger.debug("sendMail2: helper.notificaMail");
		//		MovimentiMailResponse2 notificaResponse2 = helper.notificaMail2(messageRequest2, serverMailConfig);
		//		if (notificaResponse2 != null) {
		//		    esito = notificaResponse2.getId().toString();
		//		} else {
		//		    String tmp = "n.d.";
		//		    try {
		//			tmp = messageRequest2.getMailMessage().getMessageID();
		//		    } catch (Exception ex) {
		//		    }
		//		    esito = "La mail e' stata inviata correttamente ma non e' stato possibile inserirla nell’archivio mail (l'informazione e' stata salvata negli eventi di sistema "
		//			    + messageRequest2.getCodicemovimento() + ") - Id message :" + tmp + ")";
		//		}
	    }
	    //
	}
	response.setEsito(esito);
	logger.debug("sendMail2: return {}", response.getEsito());
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

    private String messageRequestToString(MessageRequest2 messageRequest2) {

	String result = "{MessageRequest:\n";
	if (messageRequest2 != null) {
	    result += "Token=" + messageRequest2.getToken();
	    result += "IdAccount=" + messageRequest2.getAccountid() != null ? messageRequest2.getAccountid() : null;
	    result += "Token=" + StringUtils.defaultIfEmpty(messageRequest2.getCodicecomune(), "");
	    result += "\nSoftware=" + messageRequest2.getSoftware();
	    result += "\nCodiceMovimento=" + messageRequest2.getCodicemovimento();
	    if (messageRequest2.getMailMessage() != null) {
		result += "\nMessageID=" + messageRequest2.getMailMessage().getMessageID();
		result += "\nMittente=" + messageRequest2.getMailMessage().getMittente();
		result += "\nDestinatari=" + messageRequest2.getMailMessage().getDestinatari();
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
}
