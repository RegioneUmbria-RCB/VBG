package it.gruppoinit.nlapec.service;

import java.io.File;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Quota;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.ParseException;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.HeaderTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.net.ssl.SSLSocket;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.imap.IMAPStore;
import com.sun.mail.util.MailSSLSocketFactory;

import it.gruppoinit.nlapec.daticert.PecDaticert;
import it.gruppoinit.nlapec.service.eventi.EventiWSClient;
import it.gruppoinit.nlapec.service.helper.GestoreCasellaMailHelper;
import it.gruppoinit.nlapec.service.movimentimail.MovimentiMailWSClient;
import it.gruppoinit.nlapec.service.sigepro.SigeproService;
import it.gruppoinit.nlapec.service.sigepro.SigeproWebServiceClient;
import it.gruppoinit.nlapec.service.sigeprosecurity.SigeproSecurityWebServiceClient;
import it.gruppoinit.nlapec.service.stc.StcWebServiceClient;
import it.gruppoinit.nlapec.util.AllegatiUtil;
import it.gruppoinit.nlapec.util.NLAPecConstant;
import it.gruppoinit.nlapec.util.PECMessage;
import it.gruppoinit.nlapec.util.PECMessageInfos;
import it.gruppoinit.nlapec.util.RepairFI;
import it.gruppoinit.nlapec.util.ReportBean;
import it.gruppoinit.nlapec.util.ReportDettaglioBean;
import it.gruppoinit.sigepro.schemas.messages.eventi.EventoBackofficeInsertRequest;
import it.gruppoinit.sigepro.schemas.messages.mailconfig.ActionType;
import it.gruppoinit.sigepro.schemas.messages.mailconfig.MailConfigResponse2;
import it.gruppoinit.sigepro.schemas.messages.mailconfig.ProtocolType;
import it.gruppoinit.sigepro.schemas.messages.movimentimail.MovimentiMailResponse2;
import it.init.sigepro.rte.InserimentoPraticaResponse;

public class PECReader {

    private static Logger log = LoggerFactory.getLogger(PECReader.class);
    //private static String fileSeparator = System.getProperty("file.separator");
    private SigeproService sigeproService;
    private SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient;
    private StcWebServiceClient stcWebServiceClient;
    private SigeproWebServiceClient sigeproWebServiceClient;
    private MovimentiMailWSClient movimentiMailWebServiceClient;
    private EventiWSClient eventiWebServiceClient;
    //    private EmailSender emailSender;
    private PECManager pecManager;
    private String baseTmpPath;
    private String idcomunealias;
    private static final String CARTELLA_MAIL = "CARTELLA-MAIL";
    // da eliminare -------
    //    private String software;
    //    private String pecHost;
    //    private String pecProtocol;
    //    private String pecUser;
    //    private String pecPassword;
    // --------------------
    // private static final String WSHOSTURL_JAVA = "WSHOSTURL_JAVA";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    public static void main(String[] args) throws Exception {

	String protocol = "imap";
	protocol = "imap_ssl";
	String mailserver = "mail.telecompost.it";
	Properties propServerMail = populatePropertyMail(protocol, "993", mailserver);
	String password = "";
	String userid = "";
	Session session = getSession(propServerMail, userid, password);
	System.out.println(propServerMail);
	session.setDebug(true);
	Store store = session.getStore();
	store.connect(mailserver, userid, password);
	log.info("     --> store connected.");
	Folder folder = store.getFolder(GestoreCasellaMailHelper.INBOX_FOLDER_DEFAULT);
	folder.open(Folder.READ_WRITE);
	//se la casella di posta non è acceduta da altri potrei recuperare i soli messaggi non letti (ovviamente collegandomi in modalità READ_WRITE)
	log.info("     --> getListaMessaggi()");
	Map<String, String> altriParametriVerticalizzazione = new HashMap<String, String>();
	altriParametriVerticalizzazione.put("STRATEGIA_DI_LETTURA_MESSAGGI", "0");
	Message[] messages = folder.search(new HeaderTerm("Message-ID", "<opec270.20120803111826.07649.09.1.14.1@pec.aruba.it>"));
	System.out.println(messages);
    }

    public List<ReportBean> mainRun() {

	return mainRun(null);
    }
    
    public List<ReportBean> mainRun(String alias) {

	log.debug("#######################################################################");
	log.info("## mainRun(): start [at {}]", formatter.format(new Date()));
	// reallineamentoPECFirenze();
	Map<String, String> listaComuniAttivi;
	List<ReportBean> listaReport = new ArrayList<ReportBean>();
	try {
	    String stcToken = stcWebServiceClient.login();
	    log.debug("## Token STC : {}", stcToken);
	    Map<String, String> wsBaseUrlMap = sigeproSecurityWebServiceClient.getParams(NLAPecConstant.WSHOSTURL_JAVA);
	    String urlWsServiceMailConfig = getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_MAILCONFIG_WSDL);
	    String urlWsServiceNotificaMail = getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_MOVIMENTIMAIL_WSDL);
	    String urlWsServiceEventi = getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_NOTIFICA_EVENTI);
	    String urlWsServiceOggetti = getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_INSERIMENTO_OGGETTI_WSDL);
	    log.debug("## Url WS MailConfig : {}", urlWsServiceMailConfig);
	    log.debug("## Url WS NotificaMail : {}", urlWsServiceNotificaMail);
	    // interrogazione SigeproSecurity per recupeare la lista dei comuni attivi
	    Set<String> set = new HashSet<String>();
	    listaComuniAttivi = sigeproSecurityWebServiceClient.getListaComuniAttivi();
	    if (StringUtils.isBlank(alias)) {
		log.info("## Lista Comuni Attivi : {}", listaComuniAttivi);
		set = listaComuniAttivi.keySet();
	    } else {
		log.info("## processo : {}", alias);
		set.add(alias);
	    }
	    // in fase di debug, prendo solo un comune di test, in produzione le 2 riga qui sotto vanno commentate!!
	    // listaComuniAttivi = new HashMap<String, String>();
	    // listaComuniAttivi.put("E256", "E256");
	    Iterator<String> it = set.iterator();
	    log.debug("## ...inizio iterazione1 (per ogni comune attivo trovato)");
	    while (it.hasNext()) {
		String desc = "n.d.";
		String idcomuneAlias = (String) it.next();
		ReportBean report = new ReportBean(idcomuneAlias, desc);
		try {
		    String token = sigeproSecurityWebServiceClient.loginAPP(idcomuneAlias);
		    log.debug("## Token sigepro Security ({}) : {}", new Object[] { idcomuneAlias, token });
		    String descrizioneEnte = (String) listaComuniAttivi.get(idcomuneAlias);
		    log.info("   --> Comune : {} ({})", new Object[] { desc, idcomuneAlias });
		    report.setDescrizioneEnte(descrizioneEnte);
		    // per ogni comune recupero i parametri per la connessione al DB
		    Properties connectionDBProps = sigeproSecurityWebServiceClient.getConnectionProperties(idcomuneAlias, token);
		    report.setIdComune(connectionDBProps.getProperty("db_idcomune"));
		    // recupero i software che hanno attivato la verticalizzazione 'NLA-RICEZIONE-PEC' escluso TT
		    List<String> listaSoftwareAttivi = sigeproService.checkVerticalizzazioneAttiva(connectionDBProps);
		    // decommentare sotto per limitare l'elaborazione a specifici SW
		    //listaSoftwareAttivi = new ArrayList<String>();
		    //listaSoftwareAttivi.add("SS");
		    log.info("   --> Software con verticalizzazione 'NLA-RICEZIONE-PEC' attiva : {}", listaSoftwareAttivi.toString());
		    if (listaComuniAttivi == null || listaComuniAttivi.size() == 0) {
			log.info("   --> Nessun software con la verticalizzazione 'NLA-RICEZIONE-PEC' attiva per questo comune");
			report.setWarn("Nessun software con la verticalizzazione 'NLA-RICEZIONE-PEC' attiva per questo comune");
		    }
		    for (String softwareAttivo : listaSoftwareAttivi) {
			log.info("     --> Elaborazione per il software " + softwareAttivo);
			ReportDettaglioBean dettaglioReport = new ReportDettaglioBean();
			dettaglioReport.setSoftware(softwareAttivo);
			// recupero parametri per decidere quali PEC processare
			Map<String, String> parametriTipologieProcessamentoPEC = sigeproService.getParametriTipologiePEC(connectionDBProps,
				softwareAttivo);
			Map<String, String> altriParametriVerticalizzazione = sigeproService.getAltriParametriVerticalizzazione(connectionDBProps,
				softwareAttivo);
			dettaglioReport.setListaTipologiePECProcessate(parametriTipologieProcessamentoPEC);
			if (parametriTipologieProcessamentoPEC != null && parametriTipologieProcessamentoPEC.size() > 0) {
			    log.debug("     --> Tipologie PEC da processare : {}", parametriTipologieProcessamentoPEC.toString());
			    // recupero parametri configurazione server PEC da passare al metodo 'readPECInbox"			    
			    //			    MailConfigResponse _mailConfigResponse = sigeproWebServiceClient.mailConfig(urlWsServiceMailConfig, token,
			    //				    softwareAttivo, ActionType.READ);
			    // Nuova verticalizzazione in cui vengono configurati quali sono gli account lo scarico delle pec schedulato
			    String accountIdDaProcessarePerSoftware = altriParametriVerticalizzazione.get("LISTA_ACCOUNT_DA_PROCESSARE");
			    // Lista di accounti da processare vengono recuperati dalla verticalizzazione
			    // 1. NLA-RICEZIONE-PEC.LISTA_ACCOUNT_DA_PROCESSARE
			    // 2. Se non presenti nella verticalizzazione, per mantenere la retrocompatibilità viene recuperato
			    // l'account per il software. Non viene considerato TT in questo caso. Se non c'è per il software 
			    // in esame non scarichiamo i messaggi di pec
			    List<MailConfigResponse2> mailConfigResponses = new ArrayList<MailConfigResponse2>();
			    if (StringUtils.isNotBlank(accountIdDaProcessarePerSoftware)) {
				log.debug("mainRun# --> Parametro NLA-RICEZIONE-PEC.LISTA_ACCOUNT_DA_PROCESSARE = {}",
					accountIdDaProcessarePerSoftware);
				String[] accounts = StringUtils.split(accountIdDaProcessarePerSoftware, ",");
				for (int i = 0; i < accounts.length; i++) {
				    BigInteger account = null;
				    try {
					account = new BigInteger(accounts[i]);
				    } catch (NumberFormatException e) {
					log.error(
						"mainRun# Errore nel parametro NLA-RICEZIONE-PEC.LISTA_ACCOUNT_DA_PROCESSARE per il software = {}. " +
						  "Controllare che tutti i valori censiti siano di tipo numerico ",
						softwareAttivo);
				    }
				    if (account != null) {
					MailConfigResponse2 mailConfigResponse = sigeproWebServiceClient.mailConfig2(urlWsServiceMailConfig, token,
						softwareAttivo, null, account, ActionType.READ);
					if (mailConfigResponse != null) {
					    log.debug("mainRun# add account da processare. IdAccount = {}, software = {}",
						    mailConfigResponse.getIdAccount(), mailConfigResponse.getCodiceSoftware());
					    mailConfigResponses.add(mailConfigResponse);
					}
				    }
				}
			    } else {
				log.debug(
					"mainRun# --> Parametro NLA-RICEZIONE-PEC.LISTA_ACCOUNT_DA_PROCESSARE = NULL. L'account per processate per software = {}",
					softwareAttivo);
				MailConfigResponse2 mailConfigResponse = sigeproWebServiceClient.mailConfig2(urlWsServiceMailConfig, token,
					softwareAttivo, null, null, ActionType.READ);
				if (mailConfigResponse != null && StringUtils.isNotBlank(mailConfigResponse.getCodiceSoftware())) {
				    log.debug("mainRun# add account da processare. IdAccount = {}, software = {}", mailConfigResponse.getIdAccount(),
					    mailConfigResponse.getCodiceSoftware());
				    mailConfigResponses.add(mailConfigResponse);
				}
			    }
			    // Non verrà più processato un solo account, ma potranno essere processati da 1 ad N accout per lo stesso softare
			    for (MailConfigResponse2 mailConfigResponse2 : mailConfigResponses) {
				log.debug("     --> Parametri Server Mail (HostMail:{} - Porta:{} - User:{} - Password:{} - Protocollo:{}",
					new Object[] { mailConfigResponse2.getUrl(), mailConfigResponse2.getPort(), mailConfigResponse2.getUser(),
						StringUtils.repeat("*", StringUtils.defaultIfEmpty(mailConfigResponse2.getPassword(), "").length()),
						mailConfigResponse2.getProtocol().name() });
				readPECInbox(mailConfigResponse2, idcomuneAlias, softwareAttivo, token, urlWsServiceNotificaMail, urlWsServiceOggetti,
					parametriTipologieProcessamentoPEC, dettaglioReport, stcToken, baseTmpPath, connectionDBProps,
					altriParametriVerticalizzazione, urlWsServiceEventi);
			    }
			} else {
			    log.debug("     --> Tipologie PEC da processare : ...nessuna tipologia trovata");
			}
			report.addDettaglio(dettaglioReport);
		    }
		} catch (Exception ex) {
		    log.error("Errore nell'elaborazione delle PEC per il Comune ({} - {}). Error Message : {}",
			    new Object[] { idcomuneAlias, desc, ex.getMessage() });
		    if (report != null) {
			report.setError(ex.getMessage());
		    }
		}
		listaReport.add(report);
	    }
	    log.debug("## ...fine iterazione1.");
	} catch (Exception e) {
	    log.error("mainRun(): {}", e.getMessage());
	} finally {
	}
	//printReport(listaReport);
	log.info("## mainRun(): stop [at {}]", formatter.format(new Date()));
	log.debug("#######################################################################");
	return listaReport;
    }

    public List<PECMessage> run() throws Exception {

	log.info("run(): start");
	PECReader pecReader = new PECReader();
	List<String> msgs = new ArrayList<String>();
	List<PECMessage> listPECProcessate = new ArrayList<PECMessage>();
	/*
	try {
	    String token = sigeproSecurityWebServiceClient.loginAPP(idcomunealias);
	    String stcToken = stcWebServiceClient.login();
	    Properties connectionProps = sigeproSecurityWebServiceClient.getConnectionProperties(idcomunealias, token);
	    //recupero la lista dei messaggi processati correttamente
	    List<SigeproPECInbox> sigeproPECInboxList = sigeproService.leggiPECInbox(connectionProps, software);
	    //recupero i messaggi della casella pec
	    List<PECMessage> pecMessages = pecReader.readPECInbox(pecHost, pecProtocol, pecUser, pecPassword);
	    for (PECMessage pecMessage : pecMessages) {
		SigeproPECInbox sigeproPECInbox = new SigeproPECInbox();
		sigeproPECInbox.setMessageId(pecMessage.getId());
		if (!sigeproPECInboxList.contains(sigeproPECInbox)) {
		    //processo il messaggio
		    if ("posta-certificata".equals(pecMessage.getTipo())) {
			//TODO utilizzare parametro per verificare se devo processare le PEC
			// 1. convertire pec in stc
			DettaglioPraticaType praticaSTC = PECProcessor.process(pecMessage);
			// 2. chiamare STC inserimentoPratica
			try {
			    if (praticaSTC != null) {
				InserimentoPraticaResponse inserimentoPraticaResponse = stcWebServiceClient.inserisciPratica(stcToken, praticaSTC);
				if (inserimentoPraticaResponse.getDettaglioErrore() == null
					|| inserimentoPraticaResponse.getDettaglioErrore().isEmpty()) {
				    // 3. salvare su TABLE PEC_INBOX il messaggio processato
				    sigeproService.insertPECInbox(connectionProps, software, pecMessage);
				    // 4. aggiungere a lista PEC processate
				    listPECProcessate.add(pecMessage);
				} else {
				    for (ErroreType e : inserimentoPraticaResponse.getDettaglioErrore()) {
					log.error("Errore in STC insericiPratica per il messaggio con MessageID={}, errore=", new Object[] {
						pecMessage.getId(), e.getNumeroErrore(), e.getDescrizione() });
				    }
				}
			    } else {
				log.error("Errore in PECProcessor: messaggio non processato: MessageID={}", pecMessage.getId());
			    }
			} catch (Exception e) {
			    log.error("Errore in STC insericiPratica: {}", e.getMessage());
			    msgs.add("Messaggio PEC non processato, MessageID=" + pecMessage.getId() + ", errore=" + e.getMessage());
			}
		    } else {
			//TODO processo le ricevute
			//TODO utilizzare parametro per verificare se devo processare le ricevute
			//vanno inviate a sigepro tramite ws movimentimailWS
		    }
		}
	    }
	    FileUtil.deleteAllFiles();
	    log.info("Messaggi PEC processati: {}", listPECProcessate.size());
	    if (listPECProcessate.size() > 0) {
		msgs.add("PEC processate: " + listPECProcessate.size());
	    }
	} catch (Exception e) {
	    log.error("run(): {}", e.getMessage());
	    msgs.add("Errore durante la gestione della PEC: " + e.getMessage());
	    throw e;
	} finally {
	    if (emailSender != null) {
		if (!msgs.isEmpty()) {
		    emailSender.send(msgs);
		}
	    }
	}
	*/
	log.info("run(): stop");
	return listPECProcessate;
    }

    public void readPECInbox(MailConfigResponse2 mailConfigResponse, String idComuneAlias, String softwareAttivo, String token,
	    String urlWsServiceNotificaMail, String urlWsServiceOggetti, Map<String, String> parametriTipologieProcessamentoPEC,
	    ReportDettaglioBean dettaglioReport, String stcToken, String tmpBasePath, Properties connectionDBProps,
	    Map<String, String> altriParametriVerticalizzazione, String urlWsServiceEventi) throws Exception {

	log.info("     --> Lettura casella pec [{}]", mailConfigResponse.getUrl());
	String casellaPec = GestoreCasellaMailHelper.mailInboxFromParametriVerticalizzazione(altriParametriVerticalizzazione);
	try {
	    Session session = Session.getDefaultInstance(System.getProperties(), null);
	    session.setDebug(false);
	    String protocol = "imap";
	    if (mailConfigResponse.getProtocol().equals(ProtocolType.POP_3)) {
		protocol = "pop3";
	    } else if (mailConfigResponse.getProtocol().equals(ProtocolType.SSL_POP_3)) {
		protocol = "pop3_ssl";
	    } else if (mailConfigResponse.getProtocol().equals(ProtocolType.SSL_IMAP)) {
		protocol = "imap_ssl";
	    }
	    Properties propServerMail = populatePropertyMail(protocol, mailConfigResponse.getPort().toString(), mailConfigResponse.getUrl());
	    session = getSession(propServerMail, mailConfigResponse.getUser(), mailConfigResponse.getPassword());
	    Store store = session.getStore();
	    log.info("     --> store.connects({},{},{})", new Object[] { mailConfigResponse.getUrl(), mailConfigResponse.getUser(),
		    StringUtils.repeat("*", StringUtils.defaultIfEmpty(mailConfigResponse.getPassword(), "").length()) });
	    store.connect(mailConfigResponse.getUrl(), mailConfigResponse.getUser(), mailConfigResponse.getPassword());
	    log.info("     --> store connected.");
	    Folder folder = store.getFolder(casellaPec);
	    folder.open(Folder.READ_WRITE);
	    //se la casella di posta non è acceduta da altri potrei recuperare i soli messaggi non letti (ovviamente collegandomi in modalità READ_WRITE)
	    log.info("     --> getListaMessaggi()");
	    Message[] messages = getListaMessaggi(folder, altriParametriVerticalizzazione);
	    // Message[] messages = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
	    // Message[] messages = folder.search(new HeaderTerm("Message-ID", "<opec270.20120803111826.07649.09.1.14.1@pec.aruba.it>"));
	    //Message[] messages = folder.getMessages();
	    log.info("     --> Messaggi da elaborare presenti in " + casellaPec + ": {}", messages.length);
	    dettaglioReport.setNumeroMessaggiTotali(messages.length);
	    boolean debugConnections = false;
	    if (messages != null && messages.length > 0 && !debugConnections) {
		int i = 0;
		for (Message msg : messages) {
		    //msg.setFlag(Flag.SEEN, false);
		    log.info("       --> Messaggio n.{}", ++i);
		    //LION perché si verifica di nuovo il token passato come argomento che si sa già essere valido perché usato con successo per unvocare security.login()
		    //eliminando questa chiamata migliorerebbero le prestazioni perchè si elimina una chiamata a WS per ogni messaggio processato
		    token = sigeproSecurityWebServiceClient.refresToken(token, idComuneAlias);
		    String dettaglio = "";
		    if (!folder.isOpen()) {
			if (!store.isConnected()) {
			    store.connect(mailConfigResponse.getUrl(), mailConfigResponse.getUser(), mailConfigResponse.getPassword());
			    folder = store.getFolder(casellaPec);
			}
			folder.open(Folder.READ_WRITE);
		    }
		    String messageID = getMessageID(msg);
		    boolean isRead = msg.isSet(Flags.Flag.SEEN);
		    log.debug("       --> Message-ID : {}", messageID);
		    String sessionTmpMsg = String.valueOf((new Date()).getTime());
		    String tmpPath = null;
		    boolean processato = sigeproService.isProcessed(connectionDBProps, softwareAttivo, messageID);
		    log.debug("       --> Messaggio già processato : " + processato);
		    if (!(msg instanceof MimeMessage)) {
			try {
			    log.warn("Messaggio non in formato MIME");
			    dettaglio += "ERRORE [" + messageID + "] DATE: " +
					 (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(msg.getSentDate()) + " - FROM:" +
					 msg.getFrom()[0].toString() + " - SUBJECT:" + msg.getSubject();
			    dettaglioReport.addDettaglio(dettaglio + " >> Messaggio non in formato MIME");
			    dettaglioReport.setNumeroMessageConErrori(dettaglioReport.getNumeroMessageConErrori() + 1);
			    log.warn("           " + dettaglio + " >> Messaggio non in formato MIME (passo al messaggio successivo)");
			} catch (Exception e) {
			    log.error("Eccezione nella creazione del report - Messaggio non in formato MIME");
			}
		    } else if (processato) {
			try {
			    log.warn(" Messaggio già processato");
			    dettaglio += "ERRORE [" + messageID + "] DATE: " +
					 (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(msg.getSentDate()) + " - FROM:" +
					 msg.getFrom()[0].toString() + " - SUBJECT:" + msg.getSubject();
			    dettaglioReport.addDettaglio(dettaglio + " >> Messaggio già processato");
			    dettaglioReport.setNumeroMessageConErrori(dettaglioReport.getNumeroMessageConErrori() + 1);
			    log.warn("           " + dettaglio + " >> Messaggio già processato  (passo al messaggio successivo)");
			} catch (Exception e) {
			    log.error("Eccezione nella creazione del report - Messaggio già processato");
			}
		    } else {
			try {
			    log.debug("Inizio processamento del messaggio");
			    tmpPath = AllegatiUtil.buildTmpPath(tmpBasePath, sessionTmpMsg);
			    dettaglioReport.setNumeroMimeMessage(dettaglioReport.getNumeroMimeMessage() + 1);
			    String from = "";
			    for (Address address : msg.getFrom()) {
				InternetAddress ia = (InternetAddress) address;
				from = from + ia.getAddress() + ";";
			    }
			    log.debug("       --> recupero informazioni dal file daticert.xml : ");
			    PECMessageInfos pecMessageInfos = new PECMessageInfos();
			    pecMessageInfos.setCertificate(AllegatiUtil.getDatiCertXML(msg));
			    log.debug("       --> daticert.xml recuperato.");
			    PecDaticert pecDaticert = pecMessageInfos.getDatiCertDaXML();
			    log.debug("       --> daticert.xml elaborato.");
			    dettaglio += "[" + messageID + "] DATE: " + (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(msg.getSentDate()) +
					 " - FROM:" + from + " - SUBJECT:" + msg.getSubject();
			    if (pecDaticert.getReturnValue() == 1) {
				log.info("           [tipo: {}, errore: {}, mittente: {}, oggetto: {}]",
					new Object[] { pecDaticert.getTipo(), pecDaticert.getErrore(), msg.getFrom()[0], msg.getSubject() });
				PECMessage pecMessage = new PECMessage();
				pecMessage.setId(pecDaticert.getDati().getIdentificativo());
				pecMessage.setTipo(pecDaticert.getTipo());
				pecMessage.setRifMsgId(pecDaticert.getDati().getMsgid());
				pecMessage.setFrom(new Address[] { new InternetAddress(pecDaticert.getIntestazione().getMittente()) });
				try {
				    pecMessage.setSubject(javax.mail.internet.MimeUtility.decodeWord(msg.getSubject()));
				} catch (ParseException ex1) {
				    pecMessage.setSubject(msg.getSubject());
				}
				pecMessage.setTo(msg.getRecipients(RecipientType.TO));
				pecMessage.setCc(msg.getRecipients(RecipientType.CC));
				pecMessage.setDate(pecDaticert.getDati().getData());
				pecMessage.setSeen(isRead);
				log.debug("       --> Recupero Corpo mail : ");
				if (pecDaticert.getTipo().equalsIgnoreCase("posta-certificata")) {
				    pecMessage.setBody(AllegatiUtil.getBodyTextPEC(msg));
				} else {
				    pecMessage.setBody(AllegatiUtil.getBodyTextNotifica(msg));
				}
				log.debug("       --> " + pecMessage.getBody());
				dettaglio += elaboraMessaggioPEC(parametriTipologieProcessamentoPEC, pecMessage, urlWsServiceNotificaMail,
					urlWsServiceOggetti, urlWsServiceEventi, token, softwareAttivo, tmpPath, msg, idComuneAlias, stcToken,
					connectionDBProps, messageID, altriParametriVerticalizzazione, isRead, mailConfigResponse.getUser(),
					mailConfigResponse.getIdAccount());
			    } else {
				dettaglio = "ERRORE " + dettaglio + " >> Messaggio di anomalia.";
				log.error("La struttura del file daticert.xml del messaggio n.{} non è corretta (Messaggio di anomalia)", i);
				dettaglioReport.setNumeroMessageConErrori(dettaglioReport.getNumeroMessageConErrori() + 1);
			    }
			    AllegatiUtil.deleteEntireDirectory(new File(tmpPath));
			} catch (Exception e) {
			    try {
				rollBack(msg, messageID, mailConfigResponse, isRead);
				dettaglio = "ERRORE [" + messageID + "] DATE: " +
					    (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(msg.getSentDate()) + " - FROM:" +
					    msg.getFrom()[0].toString() + " - SUBJECT:" + msg.getSubject();
				dettaglio += " >> " + e.getMessage();
				dettaglioReport.setNumeroMessageConErrori(dettaglioReport.getNumeroMessageConErrori() + 1);
				log.error("           " + dettaglio);
				AllegatiUtil.deleteEntireDirectory(new File(tmpPath));
			    } catch (Exception ex) {
				log.error("ERRORE Gerico");
				dettaglio = "ERRORE Gerico";
				dettaglioReport.setNumeroMessageConErrori(dettaglioReport.getNumeroMessageConErrori() + 1);
			    }
			}
			dettaglioReport.addDettaglio(dettaglio);
		    }
		    log.debug("       --> <--");
		    // //
		}
	    }
	    // se il parametro di verticalizzazione INVIA_EVENTO_N_MSG_NONLETTI è attivo invio l'evento al backoffice comunicando il numero di messaggi non letti presenti nella PEC
	    String notificaMessaggiNonLetti = altriParametriVerticalizzazione.get("INVIA_EVENTO_N_MSG_NONLETTI");
	    if (notificaMessaggiNonLetti == null || notificaMessaggiNonLetti.equalsIgnoreCase("S")) {
		int num = getNumeroMessaggiNonLetti(folder);
		if (num > 0) {
		    Date date = new Date();
		    SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm");
		    SimpleDateFormat dt2 = new SimpleDateFormat("dd/MM/yyyy");
		    String ora = dt1.format(date);
		    String data = dt2.format(date);
		    EventoBackofficeInsertRequest request = new EventoBackofficeInsertRequest();
		    String descrizioneMessaggio = String.valueOf(num) + " messaggi da leggere per la casella PEC alle ore " + ora + " del " + data;
		    eventiWebServiceClient.eventoBackofficeInsert(urlWsServiceEventi, token, softwareAttivo, descrizioneMessaggio);
		}
	    }
	    long percentuale = checkQuotaStorage(store);
	    if (percentuale > 70) {
		String descrizioneMessaggio = "ATTENZIONE : lo spazio della casella mail risulta usato per il " + percentuale + "%";
		eventiWebServiceClient.eventoBackofficeInsert(urlWsServiceEventi, token, softwareAttivo, descrizioneMessaggio);
	    }
	    folder.close(false);
	    store.close();
	} catch (Exception e) {
	    log.error("readPECInbox(): {}", e.getMessage());
	    dettaglioReport.setError(e.getMessage());
	}
    }

    private long checkQuotaStorage(Store store) {

	long usagepct = -1;
	try {
	    Quota[] quotas = ((IMAPStore) store).getQuota(GestoreCasellaMailHelper.INBOX_FOLDER_DEFAULT);
	    if (quotas != null) {
		for (Quota quota : quotas) {
		    if (quota.quotaRoot.equalsIgnoreCase("ROOT")) {
			Quota.Resource[] resources = quota.resources;
			for (int j = 0; j < resources.length; j++) {
			    if ("STORAGE".equalsIgnoreCase(resources[j].name)) {
				//System.out.println("limit" + resources[j].limit);
				//System.out.println("usage" + resources[j].usage);
				usagepct = Math.round(100 * resources[j].usage / (double) resources[j].limit);
				log.info("usagepct: " + usagepct);
			    }
			}
		    }
		}
	    }
	} catch (Exception e) {
	    log.error("Errore durante il calcolo dello spazio disponibile nella casella PEC");
	}
	return usagepct;
    }

    private int getNumeroMessaggiNonLetti(Folder folder) {

	int ret = -1;
	try {
	    javax.mail.Message[] msg = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
	    ret = msg.length;
	} catch (Exception e) {
	    log.error("Errore nel recupero del numero dei messaggi non letti");
	}
	return ret;
    }

    private void rollBack(Message msg, String messageID, MailConfigResponse2 mailConfigResponse, boolean isRead) {

	try {
	    msg.setFlag(Flag.SEEN, isRead);
	} catch (FolderClosedException fce) {
	    try {
		Session session = Session.getDefaultInstance(System.getProperties(), null);
		session.setDebug(false);
		String protocol = "imap";
		if (mailConfigResponse.getProtocol().equals(ProtocolType.POP_3)) {
		    protocol = "pop3";
		} else if (mailConfigResponse.getProtocol().equals(ProtocolType.SSL_POP_3)) {
		    protocol = "pop3_ssl";
		} else if (mailConfigResponse.getProtocol().equals(ProtocolType.SSL_IMAP)) {
		    protocol = "imap_ssl";
		}
		Properties propServerMail = populatePropertyMail(protocol, mailConfigResponse.getPort().toString(), mailConfigResponse.getUrl());
		session = getSession(propServerMail, mailConfigResponse.getUser(), mailConfigResponse.getPassword());
		Store store = session.getStore();
		store.connect(mailConfigResponse.getUrl(), mailConfigResponse.getUser(), mailConfigResponse.getPassword());
		Folder folder = store.getFolder(GestoreCasellaMailHelper.INBOX_FOLDER_DEFAULT);
		folder.open(Folder.READ_WRITE);
		HeaderTerm ht = new HeaderTerm("Message-ID", messageID);
		Message[] messages = folder.search(ht);
		if (messages != null && messages.length == 1) {
		    messages[0].setFlag(Flag.SEEN, isRead);
		}
		folder.close(false);
		store.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	} catch (Exception fce) {
	    fce.printStackTrace();
	}
    }

    private String getMessageID(Message msg) {

	String ret = "";
	try {
	    String[] msgID = msg.getHeader("Message-ID");
	    if (msgID != null && msgID.length > 0) {
		ret = msgID[0];
	    }
	} catch (Exception e) {
	}
	return ret;
    }

    private String elaboraMessaggioPEC(Map<String, String> parametriTipologieProcessamentoPEC, PECMessage pecMessage, String urlWsServiceNotificaMail,
	    String urlWsServiceOggetti, String urlWsServiceEventi, String token, String softwareAttivo, String tmpPath, Message msg,
	    String idComuneAlias, String stcToken, Properties connectionProps, String messageID, Map<String, String> altriParametriVerticalizzazione,
	    boolean isRead, String loginNameMail, BigInteger idAccount) throws Exception {

	String ret = " [PEC non elaborata]";
	String processaRicevute = parametriTipologieProcessamentoPEC.get("PEC_RICEVUTE_CONTROLLO");
	if (StringUtils.isNotBlank(pecMessage.getTipo()) && !pecMessage.getTipo().equalsIgnoreCase("posta-certificata")) {
	    // si tratta di un messaggio di notifica
	    //	    MovimentiMailResponse resp = null;
	    MovimentiMailResponse2 resp = null;
	    if (StringUtils.isNotBlank(processaRicevute) && processaRicevute.equalsIgnoreCase("S")) {
		// la verticalizzazione è attiva
		String valore = altriParametriVerticalizzazione.get("NOTIFICA_CON_ALLEGATI");
		ArrayList<String> listaFileAttachment = null;
		if (valore != null && valore.equalsIgnoreCase("S")) {
		    listaFileAttachment = AllegatiUtil.saveAttachment(msg, tmpPath);
		}
		log.debug("       --> Invocazione WS Movimenti Mail");
		//		resp = movimentiMailWebServiceClient.movimentiMail(urlWsServiceNotificaMail, token, softwareAttivo, pecMessage, tmpPath,
		//			listaFileAttachment, null, null);
		resp = movimentiMailWebServiceClient.movimentiMail2(urlWsServiceNotificaMail, token, softwareAttivo, idAccount, pecMessage, tmpPath,
			listaFileAttachment, null, null);
		ret = "   >> [ID retuned from WS : " + (((resp.getId() == null) ? "null" : resp.getId()) + "]");
		log.info("           Movimenti Mail WS returned : <ID>{}</ID>", resp.getId());
	    }
	    if (resp == null || resp.getId() == null) {
		msg.setFlag(Flag.SEEN, isRead);
	    } else {
		sigeproService.setProcessed(connectionProps, softwareAttivo, messageID, pecMessage, loginNameMail, idAccount);
	    }
	} else {
	    InserimentoPraticaResponse response = pecManager.analyzer(idComuneAlias, softwareAttivo, token, pecMessage, tmpPath, msg,
		    parametriTipologieProcessamentoPEC, altriParametriVerticalizzazione, stcToken, connectionProps, urlWsServiceNotificaMail,
		    urlWsServiceOggetti, urlWsServiceEventi, idAccount);
	    if (response != null) {
		sigeproService.setProcessed(connectionProps, softwareAttivo, messageID, pecMessage, loginNameMail, idAccount);
		if (response.getDettaglioPratica() != null && response.getDettaglioPratica().getIdPratica() != null) {
		    sigeproService.setCodicePratica(connectionProps, softwareAttivo, messageID, pecMessage,
			    response.getDettaglioPratica().getIdPratica());
		    ret = " >> [VBG - idPratica: " + response.getDettaglioPratica().getIdPratica() + " - NumPratica: " +
			  response.getDettaglioPratica().getNumeroPratica() + "]";
		} else {
		    ret = " >> [VBG - ...generato contromovimento]";
		}
	    } else {
		ret = " [PEC non elaborata]";
		msg.setFlag(Flag.SEEN, isRead);
	    }
	    log.info("           " + ret);
	}
	return ret;
    }

    public static String getUrlWsService(Map<String, String> wsBaseUrlMap, String path) {

	String urlWsServiceMailConfig = "";
	if (wsBaseUrlMap != null && wsBaseUrlMap.containsKey(NLAPecConstant.WSHOSTURL_JAVA)) {
	    urlWsServiceMailConfig = wsBaseUrlMap.get(NLAPecConstant.WSHOSTURL_JAVA);
	    if (urlWsServiceMailConfig != null && !urlWsServiceMailConfig.equalsIgnoreCase("")) {
		if (!urlWsServiceMailConfig.endsWith("/")) {
		    urlWsServiceMailConfig = urlWsServiceMailConfig + "/";
		}
		urlWsServiceMailConfig = urlWsServiceMailConfig + path;
	    }
	}
	return urlWsServiceMailConfig;
    }

    public void setSigeproService(SigeproService sigeproService) {

	this.sigeproService = sigeproService;
    }

    public void setSigeproSecurityWebServiceClient(SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient) {

	this.sigeproSecurityWebServiceClient = sigeproSecurityWebServiceClient;
    }

    public void setStcWebServiceClient(StcWebServiceClient stcWebServiceClient) {

	this.stcWebServiceClient = stcWebServiceClient;
    }

    //    public void setEmailSender(EmailSender emailSender) {
    //
    //	this.emailSender = emailSender;
    //    }
    public void setIdcomunealias(String idcomunealias) {

	this.idcomunealias = idcomunealias;
    }

    //    public void setSoftware(String software) {
    //
    //	this.software = software;
    //    }
    //
    //    public void setPecHost(String pecHost) {
    //
    //	this.pecHost = pecHost;
    //    }
    //
    //    public void setPecProtocol(String pecProtocol) {
    //
    //	this.pecProtocol = pecProtocol;
    //    }
    //
    //    public void setPecUser(String pecUser) {
    //
    //	this.pecUser = pecUser;
    //    }
    //
    //    public void setPecPassword(String pecPassword) {
    //
    //	this.pecPassword = pecPassword;
    //    }
    public void setSigeproWebServiceClient(SigeproWebServiceClient sigeproWebServiceClient) {

	this.sigeproWebServiceClient = sigeproWebServiceClient;
    }

    public SigeproSecurityWebServiceClient getSigeproSecurityWebServiceClient() {

	return sigeproSecurityWebServiceClient;
    }

    public MovimentiMailWSClient getMovimentiMailWebServiceClient() {

	return movimentiMailWebServiceClient;
    }

    public void setMovimentiMailWebServiceClient(MovimentiMailWSClient movimentiMailWebServiceClient) {

	this.movimentiMailWebServiceClient = movimentiMailWebServiceClient;
    }

    public static Properties populatePropertyMail(String storeProtocol, String storePort, String storeHost) {

	Properties props = new Properties();
	String protocollRead = "pop3";
	if (storeProtocol != null) {
	    if (storeProtocol.startsWith("imap")) {
		protocollRead = "imap";
	    }
	}
	if (storeProtocol.endsWith("_ssl")) {
	    protocollRead += "s";
	    props.put("mail.debug", "false");
	    props.setProperty("mail." + protocollRead + ".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    props.setProperty("mail." + protocollRead + ".socketFactory.fallback", "false");
	    //disabilito il controllo sulla catena dei certificati
	    try {
		MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
		socketFactory.setTrustAllHosts(true);
		props.put("mail." + protocollRead + ".ssl.socketFactory", socketFactory);
		//to switch the connection to a TLS-protected connection before issuing any login commands
		props.put("mail." + protocollRead + ".starttls.enable", "true");
		SSLSocket socket = (SSLSocket) socketFactory.createSocket();
		String supportedProtocols = supportedProtocolsToString(socket.getSupportedProtocols());
		String supportedciphersuite = supportedProtocolsToString(socket.getSupportedCipherSuites());
		props.put("mail." + protocollRead + ".ssl.ciphersuites", supportedciphersuite);
		//test per forzare TLS 1.2
		//supportedProtocols = "TLSv1.2";
		props.put("mail." + protocollRead + ".ssl.protocols", supportedProtocols);
	    } catch (Exception e) {
		log.error(e.getMessage());
	    }
	}
	if (protocollRead.startsWith("pop3")) {
	    //Useful with POP3 servers that implicitly mark all messages that are read as "deleted"; this will prevent such messages from being deleted and expunged unless the client requests so
	    props.put("mail." + protocollRead + ".rsetbeforequit", "true");
	} else if (protocollRead.startsWith("imap")) {
	    //If set to true, attempt to use the javax.security.sasl package to choose an authentication mechanism for login. This can activate if needed the CRAM-MD5 and DIGEST-MD5 authentication mechanisms otherwise disabled by default in javamail
	    props.put("mail." + protocollRead + ".sasl.enable", "true");
	    props.put("mail.imap.sasl.enable", "true");
	}
	if (storePort != null) {
	    props.setProperty("mail." + protocollRead + ".port", storePort);
	    props.setProperty("mail." + protocollRead + ".socketFactory.port", storePort);
	}
	if (storeHost != null) {
	    props.put("mail." + protocollRead + ".host", storeHost);//
	}
	props.put("mail.store.protocol", protocollRead);
	return props;
    }

    public static String supportedProtocolsToString(String[] supportedProtocols) {

	String ret = "";
	if (supportedProtocols != null) {
	    for (int i = 0; i < supportedProtocols.length; i++) {
		ret += supportedProtocols[i] + " ";
	    }
	}
	ret = ret.trim();
	return ret;
    }

    public static Session getSession(Properties propServerMail, String userId, String password) {

	Authenticator auth = null;
	//if (serverMailConfig.getParametriSmtp().isSmtpUseAuth()) {
	auth = getAuthenticator(userId, password);
	// }
	return Session.getInstance(propServerMail, auth);
    }

    private static Authenticator getAuthenticator(final String userName, final String password) {

	Authenticator auth = new Authenticator() {

	    protected PasswordAuthentication getPasswordAuthentication() {

		return new PasswordAuthentication(userName, password);
	    }
	};
	return auth;
    }

    private static Message[] getListaMessaggi(Folder folder, Map<String, String> altriParametriVerticalizzazione) throws Exception {

	Message[] messages = null;
	// TODO Auto-generated method stub
	String strategia = altriParametriVerticalizzazione.get("STRATEGIA_DI_LETTURA_MESSAGGI");
	if (strategia != null && strategia.equalsIgnoreCase("1")) {
	    messages = folder.getMessages();
	} else if (strategia != null && strategia.equalsIgnoreCase("2")) {
	    Date d1 = new Date();
	    Date d0 = new Date(d1.getTime() - 604800000);
	    SearchTerm olderThen = new ReceivedDateTerm(ComparisonTerm.GT, d0);
	    messages = folder.search(olderThen);
	} else {
	    messages = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
	}
	return messages;
    }

    public PECManager getPecManager() {

	return pecManager;
    }

    public void setPecManager(PECManager pecManager) {

	this.pecManager = pecManager;
    }

    public String getBaseTmpPath() {

	return baseTmpPath;
    }

    public void setBaseTmpPath(String baseTmpPath) {

	this.baseTmpPath = baseTmpPath;
    }

    public void reallineamentoPECFirenze() {

	log.debug("#######################################################################");
	log.info("## reallineamentoPECFirenze(): start [at {}]", formatter.format(new Date()));
	try {
	    String sigeproSecurityToken = sigeproSecurityWebServiceClient.loginAPP(idcomunealias);
	    log.debug("## Token sigepro Security ({}) : {}", new Object[] { idcomunealias, sigeproSecurityToken });
	    String stcToken = stcWebServiceClient.login();
	    stcWebServiceClient.checkToken(stcToken);
	    log.debug("## Token STC : {}", stcToken);
	    Map<String, String> wsBaseUrlMap = sigeproSecurityWebServiceClient.getParams(NLAPecConstant.WSHOSTURL_JAVA);
	    String urlWsServiceMailConfig = getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_MAILCONFIG_WSDL);
	    String urlWsServiceNotificaMail = getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_MOVIMENTIMAIL_WSDL);
	    log.debug("## Url WS MailConfig : {}", urlWsServiceMailConfig);
	    log.debug("## Url WS NotificaMail : {}", urlWsServiceNotificaMail);
	    Properties connectionDBProps = sigeproSecurityWebServiceClient.getConnectionProperties(idcomunealias, sigeproSecurityToken);
	    RepairFI rf = new RepairFI();
	    rf.processaMessaggiEML(stcToken, sigeproSecurityToken, sigeproService, connectionDBProps, movimentiMailWebServiceClient,
		    urlWsServiceNotificaMail);
	    log.info("____________________");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public EventiWSClient getEventiWebServiceClient() {

	return eventiWebServiceClient;
    }

    public void setEventiWebServiceClient(EventiWSClient eventiWebServiceClient) {

	this.eventiWebServiceClient = eventiWebServiceClient;
    }
}
