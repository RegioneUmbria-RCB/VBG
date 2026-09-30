package it.gruppoinit.mailservice.helper;

import it.gruppoinit.mailservice.oggetti.MailServerConfigBean;
import it.gruppoinit.mailservice.oggetti.MyMimeMessage;
import it.gruppoinit.mailservice.oggetti.VerticalizzazioniMailServiceBean;
import it.gruppoinit.mailservice.oggetti.VerticalizzazioniMailServiceBean.TipoRicevuta;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.mailservice.schemas.messages.MessageRequest;
import it.gruppoinit.mailservice.schemas.messages.MessageRequest2;
import it.gruppoinit.sigepro.definitions.eventi.EventiWsServiceStub;
import it.gruppoinit.sigepro.definitions.eventi.EventiWsServiceStub.CategorieEventiBaseType;
import it.gruppoinit.sigepro.definitions.eventi.EventiWsServiceStub.EventoBackofficeInsertRequest;
import it.gruppoinit.sigepro.definitions.eventi.EventiWsServiceStub.EventoInsertResponse;
import it.gruppoinit.sigepro.definitions.eventi.EventiWsServiceStub.Messaggio_type5;
import it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub;
import it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.ActionType;
import it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest;
import it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest2;
import it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse;
import it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse2;
import it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.ProtocolType;
import it.gruppoinit.sigepro.definitions.movimentimail.MovimentiMailServiceStub;
import it.gruppoinit.sigepro.definitions.movimentimail.MovimentiMailServiceStub.AllegatoType;
import it.gruppoinit.sigepro.definitions.movimentimail.MovimentiMailServiceStub.MovimentiMailRequest;
import it.gruppoinit.sigepro.definitions.movimentimail.MovimentiMailServiceStub.MovimentiMailRequest2;
import it.gruppoinit.sigepro.definitions.movimentimail.MovimentiMailServiceStub.MovimentiMailResponse;
import it.gruppoinit.sigepro.definitions.movimentimail.MovimentiMailServiceStub.MovimentiMailResponse2;
import it.gruppoinit.sigepro.definitions.regole.RegoleWsServiceStub;
import it.gruppoinit.sigepro.definitions.regole.RegoleWsServiceStub.GetParametroRegolaRequest;
import it.gruppoinit.sigepro.definitions.regole.RegoleWsServiceStub.GetParametroRegolaResponse;
import it.gruppoinit.sigepro.definitions.regole.RegoleWsServiceStub.ParametroRegolaRequest;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoRequest;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoResponse;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;
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
import javax.net.ssl.SSLSocket;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.commons.lang.StringUtils;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;
import org.apache.rampart.RampartMessageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.util.MailSSLSocketFactory;

public class MailHelper implements Runnable {

    public final static Logger logger = LoggerFactory.getLogger(MailHelper.class);
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final long TIMEOUT = 60000;
    private static final String deployProperties = "deploy.properties";
    private static final String WSHOSTURL_JAVA = "WSHOSTURL_JAVA";
    private String urlWSRecuperoParametriMail = "";
    private String urlWSInserimentoMovimentoMail = "";
    private String urlWSsigeproSecurity = "";
    private String urlWSEventi = "";
    private String urlWSRegole = "";
    private String wsTokenUser = "";
    private String wsTokenPwd = "";
    //
    private Thread t;
    private MailServerConfigBean mailServerConfig = null;
    private MessageRequest2 messageRequest2 = null;
    private String threadName;
    private long threadSleepFirstAttemp = 5000;
    private long threadSleepLastAttemp = 25000;
    private static String mailSmtpFqn = "";

    public MailHelper() {

    }

    public MailHelper(MessageRequest2 messageRequest) {

	this.messageRequest2 = messageRequest;
	this.threadName = UUID.randomUUID().toString();
    }

    public void setMailServerConfig(MailServerConfigBean mailServerConfig) {

	this.mailServerConfig = mailServerConfig;
    }

    private static Policy loadPolicy(String name) throws XMLStreamException {

	ClassLoader loader = MailHelper.class.getClassLoader();
	InputStream resource = loader.getResourceAsStream(name);
	StAXOMBuilder builder = new StAXOMBuilder(resource);
	return PolicyEngine.getPolicy(builder.getDocumentElement());
    }

    public void init(String token) throws Exception {

	logger.debug("init: token={}", token);
	Properties props = new Properties();
	try {
	    InputStream is = this.getClass().getClassLoader().getResourceAsStream(deployProperties);
	    props.load(is);
	    try {
		is.close();
	    } catch (Exception e) {
	    }
	} catch (Exception e) {
	    logger.error("init: errore nella lettura di deploy.properties", e);
	    throw new Exception("Errore durante il caricamento del file deploy.properties");
	}
	this.urlWSsigeproSecurity = props.getProperty("ws.token.url");
	this.wsTokenUser = props.getProperty("ws.token.user");
	this.wsTokenPwd = props.getProperty("ws.token.pwd");
	mailSmtpFqn = props.getProperty("mail.smtp.fully-qualified-hostname");
	SigeproSecurityServiceStub stub = new SigeproSecurityServiceStub(this.urlWSsigeproSecurity);
	try {
	    ServiceClient client = stub._getServiceClient();
	    Options options = client.getOptions();
	    client.engageModule("rampart");
	    options.setProperty(RampartMessageData.KEY_RAMPART_POLICY, loadPolicy("policy.xml"));
	    options.setUserName(this.wsTokenUser);
	    options.setPassword(this.wsTokenPwd);
	} catch (Exception e) {
	    logger.error("init: errore nella inizializzazione del client axis2", e);
	    throw new Exception("Errore durante l'inizializzazione del web service client per la chiamata al servizio Security.");
	}
	CheckTokenResponse checkTokenResponse = null;
	try {
	    CheckTokenRequest checkTokenRequest = new CheckTokenRequest();
	    checkTokenRequest.setToken(token);
	    checkTokenRequest.setTokenInfo(false);
	    checkTokenResponse = stub.checkToken(checkTokenRequest);
	} catch (Exception e) {
	    logger.error("init: errore durante la checkToken di ibcsecurity", e);
	    throw new Exception("Errore durante la verifica del token del servizio Security", e);
	}
	String wsHostUrlJava = null;
	if (StringUtils.isNotBlank(props.getProperty("wsHostUrlJava"))) {
	    wsHostUrlJava = props.getProperty("wsHostUrlJava");
	} else {
	    if (checkTokenResponse != null && checkTokenResponse.getValid()) {
		try {
		    GetApplicationInfoRequest infoRequest = new GetApplicationInfoRequest();
		    infoRequest.setParam(WSHOSTURL_JAVA);
		    GetApplicationInfoResponse infoResponse = stub.getApplicationInfo(infoRequest);
		    wsHostUrlJava = infoResponse.getApplicationInfo()[0].getValue();
		} catch (Exception e) {
		    logger.error("init: Errore durante il recupero del parametro WSHOSTURL_JAVA da ibcsecurity", e);
		    throw new Exception("Errore durante il recupero del parametro WSHOSTURL_JAVA dal servizio Security");
		}
	    } else {
		logger.error("init: la checkToken di ibcsecurty ha restituito checkTokenResponse = null");
		throw new Exception("Errore durante la comunicazione con il servizio Security");
	    }
	}
	if (StringUtils.isNotBlank(wsHostUrlJava)) {
	    if (!wsHostUrlJava.endsWith("/")) {
		wsHostUrlJava += "/";
	    }
	    this.urlWSRecuperoParametriMail = wsHostUrlJava + "services/mailconfig?wsdl";
	    this.urlWSInserimentoMovimentoMail = wsHostUrlJava + "services/movimentimail?wsdl";
	    this.urlWSEventi = wsHostUrlJava + "services/eventi?wsdl";
	    this.urlWSRegole = wsHostUrlJava + "services/regole?wsdl";
	} else {
	    logger.error("init: Il parametro WSHOSTURL_JAVA restituito da ibcsecurity non risulta valorizzato");
	    throw new Exception("Il parametro WSHOSTURL_JAVA restituito dal Security non risulta valorizzato");
	}
    }

    public MailServerConfigBean populateServerMailConfig(String token, String software) throws Exception {

	logger.debug("MailHelper.populateServerMailConfig(" + token + "," + software + ")");
	MailServerConfigBean mailServerConfig = new MailServerConfigBean();
	try {
	    MailConfigServiceStub service = new MailConfigServiceStub(this.urlWSRecuperoParametriMail);
	    MailConfigRequest request = new MailConfigRequest();
	    request.setSoftware(software);
	    request.setToken(token);
	    request.setAction(ActionType.SEND);
	    MailConfigResponse response = service.mailConfig(request);
	    mailServerConfig.setSmtpHost(response.getUrl());
	    mailServerConfig.setSmtpPassword(response.getPassword());
	    mailServerConfig.setSmtpPort(response.getPort().toString());
	    mailServerConfig.setSmtpSender(response.getSenderEmailAddress());
	    mailServerConfig.setSmtpUserId(response.getUser());
	    mailServerConfig.setSmtpUseAuth(response.getUseAuthentication());
	    if (ProtocolType.SMTP.equals(response.getProtocol())) {
		mailServerConfig.setSmtpUseSSL(false);
		mailServerConfig.setSmtpUseTLS(false);
	    } else if (ProtocolType.SMTP_SSL.equals(response.getProtocol())) {
		mailServerConfig.setSmtpUseSSL(true);
		mailServerConfig.setSmtpUseTLS(false);
	    } else if (ProtocolType.SSMTP_SMTPS.equals(response.getProtocol())) {
		mailServerConfig.setSmtpUseSSL(false);
		mailServerConfig.setSmtpUseTLS(true);
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    throw new Exception("Errore nel recupero delle configurazioni del Server Mail (" + e.getMessage() + ")", e);
	}
	return mailServerConfig;
    }

    /**
     * Metodo utilizzato per la gestione del recupero mailconfig in modalità multi account
     * 
     * @param token
     * @param software
     * @return
     * @throws Exception
     */
    public MailServerConfigBean populateServerMailConfig2(String token, BigInteger accountId, String codicecomune, String software) throws Exception {

	logger.debug("MailHelper.populateServerMailConfig(" + token + "," + software + ")");
	MailServerConfigBean mailServerConfig = new MailServerConfigBean();
	try {
	    MailConfigServiceStub service = new MailConfigServiceStub(this.urlWSRecuperoParametriMail);
	    MailConfigRequest2 request = new MailConfigRequest2();
	    request.setSoftware(software);
	    request.setId_account(accountId);
	    request.setCodicecomune(codicecomune);
	    request.setToken(token);
	    request.setAction(ActionType.SEND);
	    MailConfigResponse2 response = service.mailConfig2(request);
	    mailServerConfig.setSmtpHost(response.getUrl());
	    mailServerConfig.setSmtpPassword(response.getPassword());
	    mailServerConfig.setSmtpPort(response.getPort().toString());
	    mailServerConfig.setSmtpSender(response.getSenderEmailAddress());
	    mailServerConfig.setSmtpUserId(response.getUser());
	    mailServerConfig.setSmtpUseAuth(response.getUseAuthentication());
	    if (ProtocolType.SMTP.equals(response.getProtocol())) {
		mailServerConfig.setSmtpUseSSL(false);
		mailServerConfig.setSmtpUseTLS(false);
	    } else if (ProtocolType.SMTP_SSL.equals(response.getProtocol())) {
		mailServerConfig.setSmtpUseSSL(true);
		mailServerConfig.setSmtpUseTLS(false);
	    } else if (ProtocolType.SSMTP_SMTPS.equals(response.getProtocol())) {
		mailServerConfig.setSmtpUseSSL(false);
		mailServerConfig.setSmtpUseTLS(true);
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    throw new Exception("Errore nel recupero delle configurazioni del Server Mail (" + e.getMessage() + ")", e);
	}
	return mailServerConfig;
    }

    public MailServerConfigBean populateServerMailConfigInput(String token, String software) throws Exception {

	logger.debug("MailHelper.populateServerMailConfig(" + token + "," + software + ")");
	MailServerConfigBean mailServerConfig = new MailServerConfigBean();
	try {
	    MailConfigServiceStub service = new MailConfigServiceStub(this.urlWSRecuperoParametriMail);
	    MailConfigRequest request = new MailConfigRequest();
	    request.setSoftware(software);
	    request.setToken(token);
	    request.setAction(ActionType.READ);
	    MailConfigResponse response = service.mailConfig(request);
	    mailServerConfig.setImapHost(response.getUrl());
	    mailServerConfig.setImapPassword(response.getPassword());
	    mailServerConfig.setImapPort(response.getPort().toString());
	    mailServerConfig.setImapSender(response.getSenderEmailAddress());
	    mailServerConfig.setImapUserId(response.getUser());
	    mailServerConfig.setImapUseAuth(response.getUseAuthentication());
	    if (ProtocolType.IMAP.equals(response.getProtocol())) {
		mailServerConfig.setImapUseSSL(false);
		mailServerConfig.setImapUseTLS(false);
	    } else if (ProtocolType.SSL_IMAP.equals(response.getProtocol())) {
		mailServerConfig.setImapUseSSL(true);
		mailServerConfig.setImapUseTLS(false);
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    throw new Exception(
		    "Errore nel recupero delle configurazioni del Server Mail in ingresso; potrebbe essere necessaria perchè, attiva la funzionalità"
			    + "di salvataggio email inviate in una specifica cartella (" + e.getMessage() + ") ", e);
	}
	return mailServerConfig;
    }

    public static Properties populatePropertyMail(MailServerConfigBean serverMailConfig) throws Exception {

	logger.debug("MailHelper.populatePropertyMail(MailServerConfigBean serverMailConfig)");
	Properties props = new Properties();
	try {
	    String transport_protocol = "";
	    if (serverMailConfig.isSmtpUseSSL() || serverMailConfig.isSmtpUseTLS()) {
		transport_protocol = "smtps";
	    } else {
		transport_protocol = "smtp";
	    }
	    if (StringUtils.isNotBlank(mailSmtpFqn)) {
		props.put("mail.smtp.localhost", mailSmtpFqn);
	    }
	    //props.put("mail.debug", "true");
	    // System.setProperty("javax.net.debug", "all");
	    props.put("mail.transport.protocol", transport_protocol);
	    props.put("mail." + transport_protocol + ".host", serverMailConfig.getSmtpHost());
	    if (serverMailConfig.isSmtpUseTLS()) {
		props.put("mail.smtp.host", serverMailConfig.getSmtpHost());
	    }
	    props.put("mail." + transport_protocol + ".port", serverMailConfig.getSmtpPort());
	    if (serverMailConfig.isSmtpUseTLS()) {
		props.put("mail.smtp.port", serverMailConfig.getSmtpPort());
	    }
	    MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
	    socketFactory.setTrustAllHosts(true);
	    SSLSocket socket = (SSLSocket) socketFactory.createSocket();
	    props.put("mail." + transport_protocol + ".ssl.socketFactory", socketFactory);
	    if (serverMailConfig.isSmtpUseTLS()) {
		props.put("mail.smtps.starttls.enable", "true");
		props.put("mail.smtp.starttls.enable", "true");
		String supportedProtocols = supportedProtocolsToString(socket.getSupportedProtocols());
		props.put("mail.smtps.ssl.protocols", supportedProtocols);
		props.put("mail.smtp.ssl.protocols", supportedProtocols);
	    }
	    if (serverMailConfig.isSmtpUseAuth()) {
		props.put("mail." + transport_protocol + ".auth", "true");
		if (serverMailConfig.isSmtpUseTLS()) {
		    props.put("mail.smtp.auth", "true");
		}
	    }
	    if (StringUtils.isNotBlank(serverMailConfig.getSocksHost())) {
		if (serverMailConfig.isSmtpUseTLS()) {
		    props.put("mail.smtp.socks.host", serverMailConfig.getSocksHost());
		    props.put("mail.smtp.socks.port", serverMailConfig.getSocksPort());
		}
		props.put("mail." + transport_protocol + ".socks.host", serverMailConfig.getSocksHost());
		props.put("mail." + transport_protocol + ".socks.port", serverMailConfig.getSocksPort());
		if (StringUtils.isNotBlank(serverMailConfig.getSocksUsername())) {
		    System.setProperty("java.net.socks.username", serverMailConfig.getSocksUsername());
		    System.setProperty("java.net.socks.password", serverMailConfig.getSocksPassword());
		}
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    throw new Exception("Errore nel caricamento dei parametri del Server Mail (" + e.getMessage() + ")", e);
	}
	try {
	    MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
	    socketFactory.setTrustAllHosts(true);
	    props.put("mail.imap.ssl.socketFactory", socketFactory);
	} catch (Exception e) {
	    e.printStackTrace();
	    //log.error(e.getMessage());
	}
	return props;
    }

    private static String supportedProtocolsToString(String[] supportedProtocols) {

	String ret = "";
	if (supportedProtocols != null) {
	    for (int i = 0; i < supportedProtocols.length; i++) {
		ret += supportedProtocols[i] + " ";
	    }
	}
	ret = ret.trim();
	return ret;
    }

    public static Properties populatePropertyMailInput(MailServerConfigBean serverMailConfig) throws Exception {

	logger.debug("MailHelper.populatePropertyMailInput(MailServerConfigBean serverMailConfig)");
	Properties props = new Properties();
	try {
	    String transport_protocol = "";
	    if (serverMailConfig.isImapUseSSL()) {
		transport_protocol = "imaps";
	    } else {
		transport_protocol = "imap";
	    }
	    //System.setProperty("javax.net.debug", "all");
	    props.setProperty("mail." + transport_protocol + ".port", serverMailConfig.getImapPort());
	    props.setProperty("mail." + transport_protocol + ".socketFactory.port", serverMailConfig.getImapPort());
	    props.put("mail." + transport_protocol + ".host", serverMailConfig.getImapHost());
	    props.put("mail.store.protocol", transport_protocol);
	    if (serverMailConfig.isImapUseSSL()) {
		props.setProperty("mail." + transport_protocol + ".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail." + transport_protocol + ".socketFactory.fallback", "false");
		try {
		    MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
		    socketFactory.setTrustAllHosts(true);
		    props.put("mail." + transport_protocol + ".ssl.socketFactory", socketFactory);
		    props.put("mail." + transport_protocol + ".ssl.socketFactory", socketFactory);
		    props.put("mail." + transport_protocol + ".starttls.enable", "true");
		    SSLSocket socket = (SSLSocket) socketFactory.createSocket();
		    String supportedProtocols = supportedProtocolsToString(socket.getSupportedProtocols());
		    props.put("mail." + transport_protocol + ".ssl.protocols", supportedProtocols);
		    //If set to true, attempt to use the javax.security.sasl package to choose an authentication mechanism for login. This can activate if needed the CRAM-MD5 and DIGEST-MD5 authentication mechanisms otherwise disabled by default in javamail
		    props.put("mail." + transport_protocol + ".sasl.enable", "true");
		    //props.put("mail.debug", "true");
		} catch (Exception e) {
		    e.printStackTrace();
		    //log.error(e.getMessage());
		}
	    }
	    if (serverMailConfig.isImapUseAuth()) {
		props.put("mail." + transport_protocol + ".auth", "true");
	    }
	    if (StringUtils.isNotBlank(serverMailConfig.getSocksHost())) {
		props.put("mail." + transport_protocol + ".socks.host", serverMailConfig.getSocksHost());
		props.put("mail." + transport_protocol + ".socks.port", serverMailConfig.getSocksPort());
		if (StringUtils.isNotBlank(serverMailConfig.getSocksUsername())) {
		    System.setProperty("java.net.socks.username", serverMailConfig.getSocksUsername());
		    System.setProperty("java.net.socks.password", serverMailConfig.getSocksPassword());
		}
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    throw new Exception("Errore nel caricamento dei parametri del Server Mail (" + e.getMessage() + ")", e);
	}
	return props;
    }

    public Session getSession(MailServerConfigBean serverMailConfig, Properties propServerMail) throws Exception {

	logger.debug("MailHelper.populatePropertyMail(MailServerConfigBean serverMailConfig, Properties propServerMail)");
	try {
	    Authenticator auth = null;
	    if (serverMailConfig.isSmtpUseAuth()) {
		auth = getAuthenticator(serverMailConfig.getSmtpUserId(), serverMailConfig.getSmtpPassword());
	    }
	    return Session.getInstance(propServerMail, auth);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    throw new Exception("Errore in fase di autenticazione (" + e.getMessage() + ")", e);
	}
    }

    public void buildMyMimeMessage(MyMimeMessage mimeMessage, MailMessageType mailMessage, MailServerConfigBean mailServerConfig,
	    VerticalizzazioniMailServiceBean verticalizzazioni) throws Exception {

	try {
	    mimeMessage.setHeader("MIME-Version", "1.0");
	    mimeMessage.setHeader("Content-Type", "multipart/mixed");
	    if (verticalizzazioni.getTipoRicevutaConsegna() != null) {
		mimeMessage.setHeader("X-TipoRicevuta", verticalizzazioni.getTipoRicevutaConsegna().toString());
	    }
	    // setting sender
	    if (validateEmail(mailMessage.getMittente())) {
		if (!validateEmail(mailMessage.getMittente())) {
		    logger.error("Mittente non valorizzato o non valido (" + mailMessage.getMittente() + ")");
		    throw new Exception("Mittente non valorizzato o non valido (" + mailMessage.getMittente() + ")");
		}
		mimeMessage.setFrom(new InternetAddress(mailMessage.getMittente()));
	    } else {
		if (!validateEmail(mailServerConfig.getSmtpSender())) {
		    logger.error("Mittente non valorizzato o non valido (" + mailServerConfig.getSmtpSender() + ")");
		    throw new Exception("Mittente non valorizzato o non valido (" + mailServerConfig.getSmtpSender() + ")");
		}
		mimeMessage.setFrom(new InternetAddress(mailServerConfig.getSmtpSender()));
	    }
	    // setting destinatari
	    String[] tokenTo = mailMessage.getDestinatari().split(";");
	    ArrayList<String> listaDestinatari = new ArrayList<String>();
	    for (int i = 0; i < tokenTo.length; i++) {
		if (isSet(tokenTo[i])) {
		    if (validateEmail(tokenTo[i].trim())) {
			listaDestinatari.add(tokenTo[i].trim());
		    } else {
			logger.error("Destinatario non valido (" + tokenTo[i].trim() + ")");
			throw new Exception("Destinatario non valido (" + tokenTo[i].trim() + ")");
		    }
		}
	    }
	    if (listaDestinatari.size() == 0) {
		logger.error("Destinatario non valorizzato o non valido (" + mailMessage.getDestinatari() + ")");
		throw new Exception("Destinatario non valorizzato o non valido (" + mailMessage.getDestinatari() + ")");
	    }
	    InternetAddress to[] = new InternetAddress[listaDestinatari.size()];
	    int indexTo = 0;
	    for (Iterator<String> iterator = listaDestinatari.iterator(); iterator.hasNext();) {
		to[indexTo] = new InternetAddress(iterator.next());
		indexTo++;
	    }
	    mimeMessage.setRecipients(Message.RecipientType.TO, to);
	    // setting DestinatariInCopia
	    if (mailMessage.getDestinatariInCopia() != null && !mailMessage.getDestinatariInCopia().equalsIgnoreCase("")) {
		String[] tokenCC = mailMessage.getDestinatariInCopia().split(";");
		ArrayList<String> listaDestinatariInCopia = new ArrayList<String>();
		for (int i = 0; i < tokenCC.length; i++) {
		    if (isSet(tokenCC[i])) {
			if (validateEmail(tokenCC[i].trim())) {
			    listaDestinatariInCopia.add(tokenCC[i].trim());
			} else {
			    logger.error("Destinatario in CC non valido (" + tokenCC[i].trim() + ")");
			    throw new Exception("Destinatario in CC non valido (" + tokenCC[i].trim() + ")");
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
	    // setting DestinatariInCopiaNascosta
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
	    // settings OggettoMail
	    mimeMessage.setSubject(buildSubject(mailMessage.getOggetto(), mailMessage.getMessageID(), verticalizzazioni));
	    // settings Data
	    mimeMessage.setSentDate(new Date());
	    // settings Message-ID
	    if (mailMessage.getMessageID() != null && !mailMessage.getMessageID().equalsIgnoreCase("")) {
		mimeMessage.setMyMessageId(mailMessage.getMessageID());
	    }
	    // corpo mail (2)
	    MimeBodyPart mbp1 = new MimeBodyPart();
	    if (mailMessage.getInviaComeHtml()) {
		mbp1.setContent(mailMessage.getCorpoMail(), "text/html");
	    } else {
		mbp1.setText(mailMessage.getCorpoMail());
	    }
	    Multipart multipart = new MimeMultipart("mixed");
	    multipart.addBodyPart(mbp1);
	    // allegati (2)
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
		logger.error("Errore durante il caricamento degli allegati (" + e.getMessage() + ")");
		throw new Exception("Errore durante il caricamento degli allegati (" + e.getMessage() + ")", e);
	    }
	    mimeMessage.setContent(multipart);
	} catch (Exception e) {
	    logger.error("Errore durante la costruzione del Mime Message (" + e.getMessage() + ")");
	    throw new Exception("Errore durante la costruzione del Mime Message (" + e.getMessage() + ")", e);
	}
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

    private String buildSubject(String oggetto, String messageID, VerticalizzazioniMailServiceBean verticalizzazioni) {

	String ret = oggetto;
	if (verticalizzazioni.isCheckReplayAttivo() && isSet(messageID)) {
	    ret = oggetto + " (ID:" + messageID + ")";
	}
	return ret;
    }

    public MovimentiMailResponse notificaMail(MessageRequest message, MailServerConfigBean serverMailConfig) throws Exception {

	MovimentiMailResponse response = null;
	try {
	    MovimentiMailServiceStub service = new MovimentiMailServiceStub(this.urlWSInserimentoMovimentoMail);
	    MovimentiMailRequest request = new MovimentiMailRequest();
	    request.setToken(message.getToken());
	    request.setSoftware(message.getSoftware());
	    request.setOggetto(message.getMailMessage().getOggetto());
	    if (validateEmail(message.getMailMessage().getMittente())) {
		request.setMittente(message.getMailMessage().getMittente());
	    } else {
		request.setMittente(serverMailConfig.getSmtpSender());
	    }
	    request.setMessageId(message.getMailMessage().getMessageID());
	    request.setDestinatariocc(message.getMailMessage().getDestinatariInCopia());
	    request.setDestinatariobcc(message.getMailMessage().getDestinatariInCopiaNascosta());
	    request.setDestinatario(message.getMailMessage().getDestinatari());
	    request.setCorpo(message.getMailMessage().getCorpoMail());
	    request.setCodicemovimento(message.getCodicemovimento());
	    if (message.getMailMessage().getAttachments() != null && message.getMailMessage().getAttachments().getAttachment() != null) {
		AllegatoType[] allegati = new AllegatoType[message.getMailMessage().getAttachments().getAttachment().length];
		for (int i = 0; i < allegati.length; i++) {
		    allegati[i] = new AllegatoType();
		    if (message.getMailMessage().getAttachments().getAttachment()[i].getId() == null
			    || message.getMailMessage().getAttachments().getAttachment()[i].getId().equalsIgnoreCase("")) {
			allegati[i].setBinaryData(message.getMailMessage().getAttachments().getAttachment()[i].getBinaryData());
		    } else {
			allegati[i].setId(new BigInteger(message.getMailMessage().getAttachments().getAttachment()[i].getId()));
		    }
		    allegati[i].setFileName(message.getMailMessage().getAttachments().getAttachment()[i].getFileName());
		    allegati[i].setMimeType(message.getMailMessage().getAttachments().getAttachment()[i].getMimeType());
		    allegati[i].setDescrizione(message.getMailMessage().getAttachments().getAttachment()[i].getDescrizione());
		}
		request.setAllegati(allegati);
	    }
	    service._getServiceClient().getOptions().setTimeOutInMilliSeconds(TIMEOUT);
	    response = service.movimentiMail(request);
	} catch (Exception e) {
	    logger.error("Errore nella chiamata al ws movimentimail del backend (" + e.getMessage() + ")");
	    //non rilancio l'eccezione altrimenti sembra che la mail non sia stata inviata
	    logger.error("MessageID   : " + message.getMailMessage().getMessageID());
	    logger.error("Oggetto     : " + message.getMailMessage().getOggetto());
	    logger.error("Destinatari : " + message.getMailMessage().getDestinatari());
	    try {
		EventiWsServiceStub service = new EventiWsServiceStub(urlWSEventi);
		EventoBackofficeInsertRequest req = new EventoBackofficeInsertRequest();
		req.setToken(message.getToken());
		req.setSoftware(message.getSoftware());
		req.setCategoriaEvento(CategorieEventiBaseType.value5);
		Messaggio_type5 msg = new Messaggio_type5();
		msg.setMessaggio_type4("La mail è stata inviata correttamente ma non è stato possibile inserirla nell’archivio mail. Non saranno recuperate le ricevute di ritorno");
		req.setMessaggio(msg);
		EventoInsertResponse resp = service.eventoBackofficeInsert(req);
		logger.error("Esito notifica di backoffice : " + resp.getEventoInsertResponse().getEsito());
	    } catch (Exception ex) {
		logger.error("Errore in fase di notifica evento al backoffice " + ex.getMessage());
	    }
	}
	return response;
    }

    /**
     * Notifica invio email e registrazione dell'evento su movimentiemail per multi account
     * 
     * @param message
     * @param serverMailConfig
     * @return
     * @throws Exception
     */
    public MovimentiMailResponse2 notificaMail2(MessageRequest2 message, MailServerConfigBean serverMailConfig) throws Exception {

	MovimentiMailResponse2 response = null;
	try {
	    MovimentiMailServiceStub service = new MovimentiMailServiceStub(this.urlWSInserimentoMovimentoMail);
	    MovimentiMailRequest2 request = new MovimentiMailRequest2();
	    request.setToken(message.getToken());
	    request.setSoftware(message.getSoftware());
	    request.setOggetto(message.getMailMessage().getOggetto());
	    if (message.getAccountid() != null) {
		request.setIdaccount(message.getAccountid());
	    }
	    logger.debug("notificaMail2# Id account={}", message.getAccountid());
	    request.setIdaccount(message.getAccountid());
	    if (validateEmail(message.getMailMessage().getMittente())) {
		request.setMittente(message.getMailMessage().getMittente());
	    } else {
		request.setMittente(serverMailConfig.getSmtpSender());
	    }
	    request.setMessageId(message.getMailMessage().getMessageID());
	    request.setDestinatariocc(message.getMailMessage().getDestinatariInCopia());
	    request.setDestinatariobcc(message.getMailMessage().getDestinatariInCopiaNascosta());
	    request.setDestinatario(message.getMailMessage().getDestinatari());
	    request.setCorpo(message.getMailMessage().getCorpoMail());
	    request.setCodicemovimento(message.getCodicemovimento());
	    if (message.getMailMessage().getAttachments() != null && message.getMailMessage().getAttachments().getAttachment() != null) {
		AllegatoType[] allegati = new AllegatoType[message.getMailMessage().getAttachments().getAttachment().length];
		for (int i = 0; i < allegati.length; i++) {
		    allegati[i] = new AllegatoType();
		    if (message.getMailMessage().getAttachments().getAttachment()[i].getId() == null
			    || message.getMailMessage().getAttachments().getAttachment()[i].getId().equalsIgnoreCase("")) {
			allegati[i].setBinaryData(message.getMailMessage().getAttachments().getAttachment()[i].getBinaryData());
		    } else {
			allegati[i].setId(new BigInteger(message.getMailMessage().getAttachments().getAttachment()[i].getId()));
		    }
		    allegati[i].setFileName(message.getMailMessage().getAttachments().getAttachment()[i].getFileName());
		    allegati[i].setMimeType(message.getMailMessage().getAttachments().getAttachment()[i].getMimeType());
		    allegati[i].setDescrizione(message.getMailMessage().getAttachments().getAttachment()[i].getDescrizione());
		}
		request.setAllegati(allegati);
	    }
	    service._getServiceClient().getOptions().setTimeOutInMilliSeconds(TIMEOUT);
	    response = service.movimentiMail2(request);
	} catch (Exception e) {
	    logger.error("Errore nella chiamata al ws movimentimail del backend (" + e.getMessage() + ")");
	    //non rilancio l'eccezione altrimenti sembra che la mail non sia stata inviata
	    logger.error("MessageID   : " + message.getMailMessage().getMessageID());
	    logger.error("Oggetto     : " + message.getMailMessage().getOggetto());
	    logger.error("Destinatari : " + message.getMailMessage().getDestinatari());
	    try {
		EventiWsServiceStub service = new EventiWsServiceStub(urlWSEventi);
		EventoBackofficeInsertRequest req = new EventoBackofficeInsertRequest();
		req.setToken(message.getToken());
		req.setSoftware(message.getSoftware());
		req.setCategoriaEvento(CategorieEventiBaseType.value5);
		Messaggio_type5 msg = new Messaggio_type5();
		msg.setMessaggio_type4("La mail è stata inviata correttamente ma non è stato possibile inserirla nell’archivio mail. Non saranno recuperate le ricevute di ritorno");
		req.setMessaggio(msg);
		EventoInsertResponse resp = service.eventoBackofficeInsert(req);
		logger.error("Esito notifica di backoffice : " + resp.getEventoInsertResponse().getEsito());
	    } catch (Exception ex) {
		logger.error("Errore in fase di notifica evento al backoffice " + ex.getMessage());
	    }
	}
	return response;
    }

    private static boolean validateEmail(String email) {

	if (email == null || email.equalsIgnoreCase("")) {
	    return false;
	}
	Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	Matcher matcher = pattern.matcher(email);
	return matcher.matches();
    }

    public static boolean isSet(String val) {

	if (val == null || val.trim().equalsIgnoreCase("")) {
	    return false;
	}
	return true;
    }

    private static Authenticator getAuthenticator(final String userName, final String password) {

	Authenticator auth = new Authenticator() {

	    protected PasswordAuthentication getPasswordAuthentication() {

		return new PasswordAuthentication(userName, password);
	    }
	};
	return auth;
    }

    public VerticalizzazioniMailServiceBean populateVerticalizzazioni(String token, String software) {

	VerticalizzazioniMailServiceBean vert = new VerticalizzazioniMailServiceBean();
	vert.setTipoRicevutaConsegna(TipoRicevuta.standard);
	try {
	    RegoleWsServiceStub stub = new RegoleWsServiceStub(this.urlWSRegole);
	    GetParametroRegolaRequest request = new GetParametroRegolaRequest();
	    // recupero parametro relativo al tipo di ricevuta (breve|sintetica|completa) che il gestore PEC deve generare
	    ParametroRegolaRequest parametroRequest = new ParametroRegolaRequest();
	    parametroRequest.setToken(token);
	    parametroRequest.setSoftware(software);
	    parametroRequest.setNomeRegola("MAIL_SERVICE");
	    parametroRequest.setNomeParametro("TIPO_RICEVUTA");
	    request.setGetParametroRegolaRequest(parametroRequest);
	    GetParametroRegolaResponse response = stub.getParametroRegola(request);
	    if (response.getGetParametroRegolaResponse().getParametroRegolaResponseChoice_type0().getParametro() != null
		    && response.getGetParametroRegolaResponse().getParametroRegolaResponseChoice_type0().getParametro().getValore() != null) {
		String valoreVerticalizzazione = response.getGetParametroRegolaResponse().getParametroRegolaResponseChoice_type0().getParametro()
			.getValore();
		if (valoreVerticalizzazione.equalsIgnoreCase("breve")) {
		    vert.setTipoRicevutaConsegna(TipoRicevuta.breve);
		} else if (valoreVerticalizzazione.equalsIgnoreCase("sintetica")) {
		    vert.setTipoRicevutaConsegna(TipoRicevuta.sintetica);
		}
	    }
	    // Recupero folder dove salvare le mail inviate
	    parametroRequest.setToken(token);
	    parametroRequest.setSoftware(software);
	    parametroRequest.setNomeRegola("MAIL_SERVICE");
	    parametroRequest.setNomeParametro("POSTA_USCITA_FOLDERNAME");
	    request.setGetParametroRegolaRequest(parametroRequest);
	    response = stub.getParametroRegola(request);
	    if (response.getGetParametroRegolaResponse().getParametroRegolaResponseChoice_type0().getParametro() != null
		    && response.getGetParametroRegolaResponse().getParametroRegolaResponseChoice_type0().getParametro().getValore() != null) {
		String valoreVerticalizzazione = response.getGetParametroRegolaResponse().getParametroRegolaResponseChoice_type0().getParametro()
			.getValore();
		vert.setFolder(valoreVerticalizzazione);
	    }
	    // recupero parametro CHECK_REPLY nella verticalizzazione NLA-PEC per decidere se nell'oggetto della mail devo includere ID della pec 
	    // (parametro necessario per generare il contromovimento in automatico)
	    parametroRequest.setToken(token);
	    parametroRequest.setSoftware(software);
	    parametroRequest.setNomeRegola("NLA-RICEZIONE-PEC");
	    parametroRequest.setNomeParametro("CHECK_REPLY");
	    request.setGetParametroRegolaRequest(parametroRequest);
	    response = stub.getParametroRegola(request);
	    if (response.getGetParametroRegolaResponse().getParametroRegolaResponseChoice_type0().getParametro() != null
		    && response.getGetParametroRegolaResponse().getParametroRegolaResponseChoice_type0().getParametro().getValore() != null) {
		String valoreVerticalizzazione = response.getGetParametroRegolaResponse().getParametroRegolaResponseChoice_type0().getParametro()
			.getValore();
		if (valoreVerticalizzazione != null && valoreVerticalizzazione.equalsIgnoreCase("S")) {
		    vert.setCheckReplayAttivo(true);
		}
	    }
	    logger.debug("Tipo ricevuta : " + vert.getTipoRicevutaConsegna().toString());
	} catch (Exception e) {
	    logger.warn("Errore nel recupero delle verticalizzazioni del Mail Service");
	}
	return vert;
    }

    @Override
    public void run() {

	MovimentiMailResponse2 notificaResponse = null;
	try {
	    // logger.debug("run: sleep for {} millis...", threadSleepFirstAttemp);
	    // Thread.sleep(threadSleepFirstAttemp);
	    // logger.debug("run: ws movimenti mail...");
	    logger.debug("run: ....");
	    notificaResponse = this.notificaMail2(messageRequest2, mailServerConfig);
	    if (notificaResponse == null || notificaResponse.getId() == null || (notificaResponse.getId().compareTo(BigInteger.ZERO) == 0)) {
		throw new Exception("null");
	    }
	    logger.debug("run: ws movimenti mail...response={}", notificaResponse.getId());
	} catch (Exception e) {
	    logger.debug("run: ws movimenti mail...response={}", e.getMessage());
	    try {
		logger.debug("run2: sleep for {} millis...", threadSleepFirstAttemp);
		Thread.sleep(threadSleepFirstAttemp);
		logger.debug("run2: ws movimenti mail...");
		notificaResponse = this.notificaMail2(messageRequest2, mailServerConfig);
		logger.debug("run2: ws movimenti mail...response={}", notificaResponse.getId());
	    } catch (Exception e1) {
		logger.warn("run2: ws movimenti mail...response={}", e1.getMessage());
		try {
		    logger.debug("run3: sleep for {} millis...", threadSleepLastAttemp);
		    Thread.sleep(threadSleepLastAttemp);
		    logger.debug("run3: ws movimenti mail...");
		    notificaResponse = this.notificaMail2(messageRequest2, mailServerConfig);
		    logger.debug("run3: ws movimenti mail...response={}", notificaResponse.getId());
		} catch (Exception e2) {
		    logger.warn("run3: ws movimenti mail...response={}", e2.getMessage());
		}
	    }
	}
    }

    public void start() {

	logger.debug("start: thread={}", threadName);
	if (t == null) {
	    t = new Thread(this, threadName);
	    t.start();
	}
    }
}
