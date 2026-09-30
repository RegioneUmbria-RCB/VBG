package it.gruppoinit.mailservice.helper;

import it.gruppoinit.mailservice.oggetti.MailServerConfigBean;
import it.gruppoinit.mailservice.oggetti.MyMimeMessage;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.ProtocolType;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.util.MailSSLSocketFactory;

public class MailHelper2 {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public MailServerConfigBean populateServerMailConfigBean(Properties p) throws Exception {

	logger.debug("populateServerMailConfigBean");
	MailServerConfigBean mailServerConfig = new MailServerConfigBean();
	mailServerConfig.setSmtpHost(p.getProperty("mailserver.host"));
	mailServerConfig.setSmtpPassword(p.getProperty("mailserver.password"));
	mailServerConfig.setSmtpPort(p.getProperty("mailserver.port"));
	mailServerConfig.setSmtpSender(p.getProperty("mailserver.emailFrom"));
	mailServerConfig.setSmtpUserId(p.getProperty("mailserver.user"));
	mailServerConfig.setSmtpUseAuth(BooleanUtils.toBoolean(p.getProperty("mailserver.useAuth")));
	String protocol = p.getProperty("mailserver.protocol");
	if (ProtocolType.SMTP.getValue().equalsIgnoreCase(protocol)) {
	    mailServerConfig.setSmtpUseSSL(false);
	    mailServerConfig.setSmtpUseTLS(false);
	} else if (ProtocolType.SMTP_SSL.getValue().equalsIgnoreCase(protocol)) {
	    mailServerConfig.setSmtpUseSSL(true);
	    mailServerConfig.setSmtpUseTLS(false);
	} else if (ProtocolType.SSMTP_SMTPS.getValue().equalsIgnoreCase(protocol)) {
	    mailServerConfig.setSmtpUseSSL(false);
	    mailServerConfig.setSmtpUseTLS(true);
	}
	return mailServerConfig;
    }

    public Session getSession(MailServerConfigBean serverMailConfigBean, Properties propServerMail) throws Exception {

	logger.debug("getSession");
	try {
	    Authenticator auth = null;
	    if (serverMailConfigBean.isSmtpUseAuth()) {
		auth = getAuthenticator(serverMailConfigBean.getSmtpUserId(), serverMailConfigBean.getSmtpPassword());
	    }
	    return Session.getInstance(propServerMail, auth);
	} catch (Exception e) {
	    logger.error("getSession", e);
	    throw new Exception("Errore in fase di autenticazione: " + e.getMessage());
	}
    }

    public void buildMyMimeMessage(MyMimeMessage mimeMessage, MailMessageType mailMessage, MailServerConfigBean mailServerConfig) throws Exception {

	logger.debug("buildMyMimeMessage");
	try {
	    mimeMessage.setHeader("MIME-Version", "1.0");
	    mimeMessage.setHeader("Content-Type", "multipart/mixed");
	    // Mittente
	    if (validateEmail(mailMessage.getMittente())) {
		mimeMessage.setFrom(new InternetAddress(mailMessage.getMittente()));
	    } else {
		if (!validateEmail(mailServerConfig.getSmtpSender())) {
		    logger.error("buildMyMimeMessage: Mittente non valorizzato");
		    throw new Exception("Mittente non specificato o non valido: " + mailServerConfig.getSmtpSender());
		}
		mimeMessage.setFrom(new InternetAddress(mailServerConfig.getSmtpSender()));
	    }
	    // Destinatari
	    String[] tokenTo = mailMessage.getDestinatari().split(";");
	    ArrayList<String> listaDestinatari = new ArrayList<String>();
	    for (int i = 0; i < tokenTo.length; i++) {
		if (StringUtils.isNotBlank(tokenTo[i])) {
		    if (validateEmail(tokenTo[i])) {
			listaDestinatari.add(tokenTo[i].trim());
		    } else {
			logger.error("buildMyMimeMessage: Destinatario non valido (" + tokenTo[i] + ")");
			throw new Exception("Destinatario non specificato o non valido: " + tokenTo[i]);
		    }
		}
	    }
	    if (listaDestinatari.size() == 0) {
		logger.error("buildMyMimeMessage: Lista destinatari vuota");
		throw new Exception("Nessun destinatario specificato");
	    }
	    InternetAddress to[] = new InternetAddress[listaDestinatari.size()];
	    int indexTo = 0;
	    for (Iterator<String> iterator = listaDestinatari.iterator(); iterator.hasNext();) {
		to[indexTo] = new InternetAddress(iterator.next());
		indexTo++;
	    }
	    mimeMessage.setRecipients(Message.RecipientType.TO, to);
	    // Destinatari in copia
	    if (mailMessage.getDestinatariInCopia() != null && !mailMessage.getDestinatariInCopia().equalsIgnoreCase("")) {
		String[] tokenCC = mailMessage.getDestinatariInCopia().split(";");
		ArrayList<String> listaDestinatariInCopia = new ArrayList<String>();
		for (int i = 0; i < tokenCC.length; i++) {
		    if (StringUtils.isNotBlank(tokenCC[i])) {
			if (validateEmail(tokenCC[i])) {
			    listaDestinatariInCopia.add(tokenCC[i].trim());
			} else {
			    logger.error("buildMyMimeMessage: Destinatario in CC non valido (" + tokenCC[i] + ")");
			    throw new Exception("Destinatario in CC non valido: " + tokenCC[i]);
			}
		    }
		}
		if (listaDestinatariInCopia.size() > 0) {
		    InternetAddress cc[] = new InternetAddress[listaDestinatariInCopia.size()];
		    int indexCC = 0;
		    for (Iterator<String> iterator = listaDestinatariInCopia.iterator(); iterator.hasNext();) {
			cc[indexCC] = new InternetAddress(iterator.next());
			indexCC++;
		    }
		    mimeMessage.setRecipients(Message.RecipientType.CC, cc);
		}
	    }
	    // Destinatari in copia nascosta
	    if (mailMessage.getDestinatariInCopiaNascosta() != null && !mailMessage.getDestinatariInCopiaNascosta().equalsIgnoreCase("")) {
		String[] tokenBCC = mailMessage.getDestinatariInCopiaNascosta().split(";");
		ArrayList<String> listaDestinatariInCopiaNascosta = new ArrayList<String>();
		for (int i = 0; i < tokenBCC.length; i++) {
		    if (validateEmail(tokenBCC[i].trim())) {
			listaDestinatariInCopiaNascosta.add(tokenBCC[i].trim());
		    }
		}
		if (listaDestinatariInCopiaNascosta.size() > 0) {
		    InternetAddress bcc[] = new InternetAddress[listaDestinatariInCopiaNascosta.size()];
		    int indexBCC = 0;
		    for (Iterator<String> iterator = listaDestinatariInCopiaNascosta.iterator(); iterator.hasNext();) {
			bcc[indexBCC] = new InternetAddress(iterator.next());
			indexBCC++;
		    }
		    mimeMessage.setRecipients(Message.RecipientType.BCC, bcc);
		}
	    }
	    // Oggetto
	    mimeMessage.setSubject(mailMessage.getOggetto());
	    // Data
	    mimeMessage.setSentDate(new Date());
	    // Corpo
	    MimeBodyPart mbp1 = new MimeBodyPart();
	    if (mailMessage.getInviaComeHtml()) {
		mbp1.setContent(mailMessage.getCorpoMail(), "text/html");
	    } else {
		mbp1.setText(mailMessage.getCorpoMail());
	    }
	    Multipart multipart = new MimeMultipart("mixed");
	    multipart.addBodyPart(mbp1);
	    // Allegati
	    try {
		if (mailMessage.getAttachments() != null && mailMessage.getAttachments().getAttachment() != null
			&& mailMessage.getAttachments().getAttachment().length > 0) {
		    for (int i = 0; i < mailMessage.getAttachments().getAttachment().length; i++) {
			MimeBodyPart attachFilePart = new MimeBodyPart();
			attachFilePart.setDataHandler(mailMessage.getAttachments().getAttachment()[i].getBinaryData());
			attachFilePart.setFileName(mailMessage.getAttachments().getAttachment()[i].getFileName());
			attachFilePart.setDisposition(Part.ATTACHMENT);
			multipart.addBodyPart(attachFilePart);
		    }
		}
	    } catch (Exception e) {
		logger.error("buildMyMimeMessage: Errore durante il caricamento degli allegati", e);
		throw new Exception("Errore durante il caricamento degli allegati: " + e.getMessage());
	    }
	    mimeMessage.setContent(multipart);
	} catch (Exception e) {
	    logger.error("buildMyMimeMessage", e);
	    throw new Exception("Errore durante la preparazione della mail: " + e.getMessage());
	}
    }

    public Properties populatePropertyMail(MailServerConfigBean serverMailConfig) throws Exception {

	return MailHelper.populatePropertyMail(serverMailConfig);
    }

    public void populateSocksInfo(MailServerConfigBean serverMailConfig, Properties p) {

	String socksHost = p.getProperty("socks.host");
	String socksPort = p.getProperty("socks.port");
	String socksUsername = p.getProperty("socks.username");
	String socksPassword = p.getProperty("socks.password");
	logger.debug("populateSocksInfo: host=" + socksHost);
	serverMailConfig.setSocksHost(socksHost);
	serverMailConfig.setSocksPort(socksPort);
	serverMailConfig.setSocksUsername(socksUsername);
	serverMailConfig.setSocksPassword(socksPassword);
    }

    private Authenticator getAuthenticator(final String userName, final String password) {

	Authenticator auth = new Authenticator() {

	    protected PasswordAuthentication getPasswordAuthentication() {

		return new PasswordAuthentication(userName, password);
	    }
	};
	return auth;
    }

    private boolean validateEmail(String email) {

	if (StringUtils.isBlank(email)) {
	    return false;
	}
	Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	Matcher matcher = pattern.matcher(email);
	return matcher.matches();
    }
}
