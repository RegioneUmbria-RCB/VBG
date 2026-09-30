package it.gruppoinit.nlapec.service.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.AndTerm;
import javax.mail.search.BodyTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.nlapec.schema.wsnlapec.AllegatoMailType;
import it.gruppoinit.nlapec.schema.wsnlapec.FiltroType;
import it.gruppoinit.nlapec.schema.wsnlapec.ListaMessaggiResponse;
import it.gruppoinit.nlapec.schema.wsnlapec.MessaggioType;
import it.gruppoinit.nlapec.schema.wsnlapec.ScaricaAllegatiMessaggioResponse;
import it.gruppoinit.nlapec.schema.wsnlapec.ScaricaMessaggioBinarioResponse;
import it.gruppoinit.nlapec.schema.wsnlapec.ScaricaMessaggioInviatoBinarioResponse;
import it.gruppoinit.nlapec.schema.wsnlapec.SetFlagLetturaMessaggioResponse;
import it.gruppoinit.nlapec.service.PECReader;
import it.gruppoinit.nlapec.util.AllegatiUtil;
import it.gruppoinit.nlapec.util.NLAPecConstant;
import it.gruppoinit.sigepro.schemas.messages.mailconfig.MailConfigResponse2;
import it.gruppoinit.sigepro.schemas.messages.mailconfig.ProtocolType;

public class GestoreCasellaMailHelper {

    public static final String INBOX_FOLDER_DEFAULT = "INBOX";
    private static Logger log = LoggerFactory.getLogger(GestoreCasellaMailHelper.class);
    private static final Pattern NON_ASCII_PATTERN = Pattern.compile("[\\u0000-\\u001f]");
    public static final String EMPTY_CACHED_FINAL_STRING = "";
    private static final String CARTELLA_MAIL = "CARTELLA-MAIL";

    public GestoreCasellaMailHelper() {

    }

    public ScaricaMessaggioBinarioResponse getMessaggioBinario(MailConfigResponse2 mailConfigResponse, String identificativoMessaggio,
	    Map<String, String> altriParametriVerticalizzazione) throws Exception {

	log.debug("(getMessaggioBinario)...");
	ScaricaMessaggioBinarioResponse response = new ScaricaMessaggioBinarioResponse();
	try {
	    Store storeConnection = getStoreConnection(mailConfigResponse);
	    Folder folderSession = getFolderSession(storeConnection, altriParametriVerticalizzazione, Folder.READ_ONLY);
	    // Il metodo insertQuotes mette tra parentesi angolari (<>) il message id. Questo perchè nelle ricevute generate
	    // dal sistema di posta pec (file presente in datacert.xml) il message id viene riportato sempre tra parentesi angolari
	    Message[] messages = folderSession.search(new MessageIDTerm(insertQuotes(identificativoMessaggio)));
	    if (messages != null && messages.length > 0) {
		// File emlFile = new File("c:\\tmp\\" + getNomeFile(messages[0].getSubject()) + ".eml");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		messages[0].writeTo(baos);
		DataHandler dataHandler = new DataHandler(baos.toByteArray(), "application/octet-stream");
		//		InputStream is = messages[0].getInputStream();
		//		DataHandler dataHandler = inputStream2DataHandler(is);
		String subject = "";
		try {
		    subject = javax.mail.internet.MimeUtility.decodeWord(messages[0].getSubject());
		} catch (Exception e) {
		    subject = messages[0].getSubject();
		}
		if (subject == null || subject.equalsIgnoreCase("")) {
		    subject = "(nessun oggetto)";
		}
		String nomeFile = getNomeFile(eliminaCaratteriNonAscii(subject));
		if (nomeFile.length() > 150) {
		    nomeFile = nomeFile.substring(0, 150);
		}
		response.setNomeFile(nomeFile + ".eml");
		response.setContentType(dataHandler.getContentType());
		response.setContent(dataHandler);
		// is.close();
		baos.close();
	    }
	    return response;
	} catch (Exception e) {
	    log.error("Errore nel recupero del messaggio binario : " + e.getMessage());
	    throw e;
	}
    }

    public ScaricaMessaggioInviatoBinarioResponse getMessaggioInviatoBinario(MailConfigResponse2 mailConfigResponse,
	    String identificativoBackofficeMessaggio, Map<String, String> altriParametriVerticalizzazione) throws Exception {

	log.debug("(getMessaggioInviatoBinario)...");
	ScaricaMessaggioInviatoBinarioResponse response = new ScaricaMessaggioInviatoBinarioResponse();
	try {
	    Store storeConnection = getStoreConnection(mailConfigResponse);
	    Folder folderSession = getFolderSession(storeConnection, altriParametriVerticalizzazione, Folder.READ_ONLY);
	    // In fase di invio nel file daticert.xml il message id passato non viene modificato 
	    //(non viene messo tra parentesi angolari come nelle ricevuto di accettazione e ricezione generate)
	    Message[] messages = folderSession.search(new MessageIDTerm(identificativoBackofficeMessaggio));
	    if (messages != null && messages.length > 0) {
		// File emlFile = new File("c:\\tmp\\" + getNomeFile(messages[0].getSubject()) + ".eml");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		messages[0].writeTo(baos);
		DataHandler dataHandler = new DataHandler(baos.toByteArray(), "application/octet-stream");
		//		InputStream is = messages[0].getInputStream();
		//		DataHandler dataHandler = inputStream2DataHandler(is);
		String subject = "";
		try {
		    subject = javax.mail.internet.MimeUtility.decodeWord(messages[0].getSubject());
		} catch (Exception e) {
		    subject = messages[0].getSubject();
		}
		if (subject == null || subject.equalsIgnoreCase("")) {
		    subject = "(nessun oggetto)";
		}
		String nomeFile = getNomeFile(eliminaCaratteriNonAscii(subject));
		if (nomeFile.length() > 150) {
		    nomeFile = nomeFile.substring(0, 150);
		}
		response.setNomeFile(nomeFile + ".eml");
		response.setContentType(dataHandler.getContentType());
		response.setContent(dataHandler);
		// is.close();
		baos.close();
	    }
	    return response;
	} catch (Exception e) {
	    log.error("Errore nel recupero del messaggio inviato binario : " + e.getMessage());
	    throw e;
	}
    }

    private String getNomeFile(String subject) {

	String ret = subject.replace("\\", "").replace("/", "").replace(":", "").replace("*", "").replace("?", "").replace("\"", "").replace("<", "")
		.replace(">", "").replace("|", "");
	return ret;
    }

    private String removeQuotes(String messageID) {

	String ret = messageID;
	if (ret.startsWith("<")) {
	    ret = ret.substring(1);
	}
	if (ret.endsWith(">")) {
	    ret = ret.substring(0, ret.length() - 1);
	}
	return ret;
    }

    private String insertQuotes(String messageID) {

	String ret = messageID;
	if (!ret.startsWith("<")) {
	    ret = "<".concat(ret);
	}
	if (!ret.endsWith(">")) {
	    ret = ret.concat(">");
	}
	return ret;
    }

    public ListaMessaggiResponse getListaMessaggi(List<FiltroType> listaFiltri, MailConfigResponse2 mailConfigResponse,
	    Map<String, String> altriParametriVerticalizzazione) throws Exception {

	log.debug("(getListaMessaggi)...");
	try {
	    ListaMessaggiResponse response = new ListaMessaggiResponse();
	    javax.mail.Message[] msg = null;
	    Store storeConnection = getStoreConnection(mailConfigResponse);
	    Folder folderSession = getFolderSession(storeConnection, altriParametriVerticalizzazione, Folder.READ_ONLY);
	    SearchTerm filtroRicerca = configuraFiltri(listaFiltri);
	    log.debug("getMessages()...");
	    if (filtroRicerca == null) {
		msg = folderSession.getMessages();
	    } else {
		msg = folderSession.search(filtroRicerca);
	    }
	    if (msg == null || msg.length == 0) {
		log.debug("Nessun messaggio trovato");
	    } else {
		log.debug("Trovati {} messaggi " + msg.length);
		for (int i = (msg.length - 1); i >= 0; i--) {
		    //printAllValueOfHeader(msg[i], "References");
		    //printAllValueOfHeader(msg[i], "Message-ID");
		    //printAll(msg[i]);
		    MessaggioType messaggio = new MessaggioType();
		    GregorianCalendar receivedDate = new GregorianCalendar();
		    if (msg[i].getReceivedDate() != null) {
			receivedDate.setTime(msg[i].getReceivedDate());
		    }
		    messaggio.setDataRicezione(DatatypeFactory.newInstance().newXMLGregorianCalendar(receivedDate));
		    GregorianCalendar sentDate = new GregorianCalendar();
		    if (msg[i].getSentDate() != null) {
			sentDate.setTime(msg[i].getSentDate());
		    }
		    messaggio.setDataSpedizione(DatatypeFactory.newInstance().newXMLGregorianCalendar(sentDate));
		    if (msg[i].getRecipients(RecipientType.TO) != null) {
			for (int j = 0; j < msg[i].getRecipients(RecipientType.TO).length; j++) {
			    InternetAddress address = (InternetAddress) msg[i].getRecipients(RecipientType.TO)[j];
			    messaggio.getDestinatari().add(address.getAddress());
			}
		    }
		    if (msg[i].getRecipients(RecipientType.CC) != null) {
			for (int j = 0; j < msg[i].getRecipients(RecipientType.CC).length; j++) {
			    InternetAddress address = (InternetAddress) msg[i].getRecipients(RecipientType.CC)[j];
			    messaggio.getDestinataricc().add(address.getAddress());
			}
		    }
		    if (msg[i].getReplyTo() != null) {
			for (int j = 0; j < msg[i].getReplyTo().length; j++) {
			    InternetAddress address = (InternetAddress) msg[i].getReplyTo()[j];
			    messaggio.getMittenti().add(address.getAddress());
			}
		    } else if (msg[i].getFrom() != null) {
			for (int j = 0; j < msg[i].getFrom().length; j++) {
			    InternetAddress address = (InternetAddress) msg[i].getFrom()[j];
			    messaggio.getMittenti().add(address.getAddress());
			}
		    }
		    messaggio.setDimensione(new Long(msg[i].getSize()));
		    if (msg[i].getHeader("Message-ID") != null && msg[i].getHeader("Message-ID").length > 0) {
			messaggio.setIdentificativo(removeQuotes(msg[i].getHeader("Message-ID")[0]));
		    } else {
			messaggio.setIdentificativo(null);
		    }
		    messaggio.setLetto(msg[i].isSet(Flags.Flag.SEEN));
		    try {
			messaggio.setOggetto(eliminaCaratteriNonAscii(javax.mail.internet.MimeUtility.decodeWord(msg[i].getSubject())));
		    } catch (Exception e) {
			messaggio.setOggetto(eliminaCaratteriNonAscii(msg[i].getSubject()));
		    }
		    if (messaggio.getOggetto() == null || messaggio.getOggetto().equalsIgnoreCase("")) {
			messaggio.setOggetto("(nessun oggetto)");
		    }
		    if (messaggio.getIdentificativo() != null) {
			response.getMessaggio().add(messaggio);
		    }
		}
	    }
	    folderSession.close(false);
	    storeConnection.close();
	    response.setIdaccount(mailConfigResponse.getIdAccount());
	    return response;
	} catch (AuthenticationFailedException e) {
	    log.error("AuthenticationFailedException (Username e/o password non corretti)");
	    e.printStackTrace();
	    throw new Exception("Errore nel recupero della lista messaggi : AuthenticationFailedException (Username e/o password non corretti) : " +
				e.getMessage() + ")",
		    e);
	} catch (Exception e) {
	    log.error("Errore nel recupero della lista messaggi : " + e.getMessage());
	    throw e;
	}
    }

    private void printAllValueOfHeader(Message message, String headerName) {

	try {
	    String[] values = message.getHeader(headerName);
	    if (values != null) {
		for (int i = 0; i < values.length; i++) {
		    log.debug("-->" + values[i]);
		}
	    }
	} catch (Exception e) {
	}
    }

    private void printAll(Message message) {

	try {
	    Enumeration en = message.getAllHeaders();
	    while (en.hasMoreElements()) {
		javax.mail.Header h = (javax.mail.Header) en.nextElement();
		System.out.println(h.getName() + " : ");
		System.out.println(h.getValue());
	    }
	} catch (Exception e) {
	}
    }

    public ScaricaAllegatiMessaggioResponse getAllegatiMessaggio(String identificativoMessaggio, MailConfigResponse2 mailConfigResponse,
	    String tmpBasePath, Map<String, String> altriParametriVerticalizzazione) throws Exception {

	log.debug("(getAllegatiMessaggio)...");
	try {
	    String sessionTmpMsg = String.valueOf((new Date()).getTime());
	    String tmpPath = AllegatiUtil.buildTmpPath(tmpBasePath, sessionTmpMsg);
	    ScaricaAllegatiMessaggioResponse response = new ScaricaAllegatiMessaggioResponse();
	    Store storeConnection = getStoreConnection(mailConfigResponse);
	    Folder folderSession = getFolderSession(storeConnection, altriParametriVerticalizzazione, Folder.READ_ONLY);
	    Message[] messages = folderSession.search(new MessageIDTerm(insertQuotes(identificativoMessaggio)));
	    if (messages != null && messages.length > 0) {
		log.debug("Trovati {} messaggi, percorso temporaneo {}", messages.length, tmpPath);
		ArrayList<String> listaAllegati = AllegatiUtil.saveAttachment(messages[0], tmpPath);
		MessaggioType messaggio = new MessaggioType();
		String corpoMail = "";
		corpoMail = AllegatiUtil.getBodyTextPEC(messages[0]);
		if (corpoMail.equalsIgnoreCase("Errore nel recupero del corpo della mail")) {
		    // provo a trattarla come una mail non PEC
		    String tmpCorpoMail = AllegatiUtil.getText(messages[0]);
		    if (tmpCorpoMail != null) {
			corpoMail = tmpCorpoMail;
		    }
		}
		messaggio.setCorpo(eliminaCaratteriNonAscii(corpoMail));
		GregorianCalendar date = new GregorianCalendar();
		if (messages[0].getReceivedDate() != null) {
		    date.setTime(messages[0].getReceivedDate());
		}
		messaggio.setDataRicezione(DatatypeFactory.newInstance().newXMLGregorianCalendar(date));
		date.setTime(messages[0].getSentDate());
		messaggio.setDataSpedizione(DatatypeFactory.newInstance().newXMLGregorianCalendar(date));
		messaggio.setDimensione(new Long(messages[0].getSize()));
		messaggio.setIdentificativo(removeQuotes(messages[0].getHeader("Message-ID")[0]));
		messaggio.setLetto(messages[0].isSet(Flags.Flag.SEEN));
		String oggetto = "";
		try {
		    oggetto = javax.mail.internet.MimeUtility.decodeWord(messages[0].getSubject());
		} catch (Exception e) {
		    oggetto = messages[0].getSubject();
		}
		if (oggetto == null || oggetto.equalsIgnoreCase("")) {
		    oggetto = "(nessun oggetto)";
		}
		messaggio.setOggetto(eliminaCaratteriNonAscii(oggetto));
		if (messages[0].getRecipients(RecipientType.TO) != null) {
		    for (int j = 0; j < messages[0].getRecipients(RecipientType.TO).length; j++) {
			InternetAddress address = (InternetAddress) messages[0].getRecipients(RecipientType.TO)[j];
			messaggio.getDestinatari().add(address.getAddress());
		    }
		}
		if (messages[0].getRecipients(RecipientType.CC) != null) {
		    for (int j = 0; j < messages[0].getRecipients(RecipientType.CC).length; j++) {
			InternetAddress address = (InternetAddress) messages[0].getRecipients(RecipientType.CC)[j];
			messaggio.getDestinataricc().add(address.getAddress());
		    }
		}
		if (messages[0].getReplyTo() != null) {
		    for (int j = 0; j < messages[0].getReplyTo().length; j++) {
			InternetAddress address = (InternetAddress) messages[0].getReplyTo()[j];
			messaggio.getMittenti().add(address.getAddress());
		    }
		} else if (messages[0].getFrom() != null) {
		    for (int j = 0; j < messages[0].getFrom().length; j++) {
			InternetAddress address = (InternetAddress) messages[0].getFrom()[j];
			messaggio.getMittenti().add(address.getAddress());
		    }
		}
		for (Iterator<String> iterator = listaAllegati.iterator(); iterator.hasNext();) {		    
		    String nomeFile = (String) iterator.next();
		    log.debug("processo l'allegato {}-{}", tmpPath, nomeFile);
		    AllegatoMailType allegato = new AllegatoMailType();
		    allegato.setContent(AllegatiUtil.getDataHandler(tmpPath, nomeFile, null));
		    MimetypesFileTypeMap m = new MimetypesFileTypeMap();
		    String mimeType = m.getContentType(tmpPath + nomeFile);
		    log.debug("processo l'allegato {}, content-type {}", nomeFile, mimeType);
		    allegato.setContentType(mimeType);
		    allegato.setEncoding(null);
		    allegato.setNomeFile(sistemaNomiFile(nomeFile));
		    response.getAllegati().add(allegato);
		}
		response.setMessaggio(messaggio);
	    } else {
		log.debug("Non è stato trovato il messaggio richiesto");
	    }
	    //AllegatiUtil.deleteEntireDirectory(new File(tmpPath));
	    folderSession.close(false);
	    storeConnection.close();
	    return response;
	} catch (Exception e) {
	    log.error("Errore nel recupero degli allegati del messaggio : " + e.getMessage());
	    throw e;
	}
    }

    public static void main(String[] args) {

	GestoreCasellaMailHelper h = new GestoreCasellaMailHelper();
	System.out.println(
		h.sistemaNomiFile("DICHIARAZIONE DI CONFORMITA&#25; DELL&#25;INTERVENTO ALL&#25;ART.29 C.7 DELLE NTA DEL PO.pdf.p7m"));
    }

    public int getNumeroMessaggiNonLetti(MailConfigResponse2 mailConfigResponse, Map<String, String> altriParametriVerticalizzazione)
	    throws Exception {

	log.debug("(getNumeroMessaggiNonLetti)...");
	try {
	    Store storeConnection = getStoreConnection(mailConfigResponse);
	    Folder folderSession = getFolderSession(storeConnection, altriParametriVerticalizzazione, Folder.READ_ONLY);
	    javax.mail.Message[] msg = folderSession.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
	    folderSession.close(false);
	    storeConnection.close();
	    return msg.length;
	} catch (Exception e) {
	    log.error("Errore nel recupero del numero dei messaggi non letti : " + e.getMessage());
	    throw e;
	}
    }

    public SetFlagLetturaMessaggioResponse setFlagLetturaMessaggio(MailConfigResponse2 mailConfigResponse, String identificativoMessaggio,
	    boolean flagValue, Map<String, String> altriParametriVerticalizzazione) throws Exception {

	log.debug("(setFlagLetturaMessaggio)...");
	SetFlagLetturaMessaggioResponse response = new SetFlagLetturaMessaggioResponse();
	try {
	    Store storeConnection = getStoreConnection(mailConfigResponse);
	    Folder folderSession = getFolderSession(storeConnection, altriParametriVerticalizzazione, Folder.READ_WRITE);
	    Message[] messages = folderSession.search(new MessageIDTerm(insertQuotes(identificativoMessaggio)));
	    if (messages != null && messages.length > 0) {
		for (int i = 0; i < messages.length; i++) {
		    messages[i].setFlag(Flag.SEEN, flagValue);
		}
		response.setEsito("1");
	    } else {
		response.setEsito("0");
	    }
	    folderSession.close(false);
	    storeConnection.close();
	    return response;
	} catch (Exception e) {
	    log.error("Errore nel settaggio del flag di lettura del messaggio : " + e.getMessage());
	    throw e;
	}
    }

    public String getUrlWsService(Map<String, String> wsBaseUrlMap, String path) {

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

    private SearchTerm configuraFiltri(List<FiltroType> listaFiltri) {

	log.debug("configuraFiltri()...");
	SearchTerm filtroRicerca = null;
	ArrayList<SearchTerm> searchTermList = new ArrayList<SearchTerm>();
	SearchTerm[] searchTerm = null;
	try {
	    FlagTerm flagTermLetti = null;
	    FlagTerm flagTermNonLetti = null;
	    //ricerco una stringa all'interno del BODY della mail
	    BodyTerm bodyTerm = null;
	    for (Iterator<FiltroType> iterator = listaFiltri.iterator(); iterator.hasNext();) {
		FiltroType filtro = (FiltroType) iterator.next();
		if (filtro.getTipo().equalsIgnoreCase("DATA_DA")) {
		    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(filtro.getValore());
		    ReceivedDateTerm dateTermDataDA = new ReceivedDateTerm(ComparisonTerm.GE, date);
		    searchTermList.add(dateTermDataDA);
		} else if (filtro.getTipo().equalsIgnoreCase("DATA_A")) {
		    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(filtro.getValore());
		    ReceivedDateTerm dateTermDataA = new ReceivedDateTerm(ComparisonTerm.LE, date);
		    searchTermList.add(dateTermDataA);
		} else if (filtro.getTipo().equalsIgnoreCase("LETTI") && filtro.getValore().equalsIgnoreCase("S")) {
		    flagTermLetti = new FlagTerm(new Flags(Flags.Flag.SEEN), true);
		} else if (filtro.getTipo().equalsIgnoreCase("NON_LETTI") && filtro.getValore().equalsIgnoreCase("S")) {
		    flagTermNonLetti = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
		} else if (filtro.getTipo().equalsIgnoreCase("CORPO")) {
		    log.debug("Filtro CORPO - searching text in email body: " + filtro.getValore());
		    bodyTerm = new BodyTerm(filtro.getValore());
		}
	    }
	    if (flagTermLetti != null && flagTermNonLetti != null) {
		flagTermLetti = null;
		flagTermNonLetti = null;
	    }
	    if (flagTermLetti != null) {
		searchTermList.add(flagTermLetti);
	    }
	    if (flagTermNonLetti != null) {
		searchTermList.add(flagTermNonLetti);
	    }
	    if (bodyTerm != null) {
		searchTermList.add(bodyTerm);
	    }
	    if (searchTermList.size() > 0) {
		searchTerm = new SearchTerm[searchTermList.size()];
		int index = 0;
		for (Iterator<SearchTerm> iterator = searchTermList.iterator(); iterator.hasNext();) {
		    SearchTerm term = (SearchTerm) iterator.next();
		    searchTerm[index] = term;
		    index++;
		}
		filtroRicerca = new AndTerm(searchTerm);
		return filtroRicerca;
	    }
	} catch (Exception e) {
	    log.error("Errore nell'impostazione dei filtri di ricerca : {}", e);
	}
	return null;
    }

    private Store getStoreConnection(MailConfigResponse2 mailConfigResponse) throws Exception {

	log.debug("getStoreConnection()...");
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
	    Properties propServerMail = PECReader.populatePropertyMail(protocol, mailConfigResponse.getPort().toString(),
		    mailConfigResponse.getUrl());
	    session = getSession(propServerMail, mailConfigResponse.getUser(), mailConfigResponse.getPassword());
	    Store store = session.getStore();
	    log.debug("     --> store.connects({},{},{})", new Object[] { mailConfigResponse.getUrl(), mailConfigResponse.getUser(),
		    StringUtils.repeat("*", StringUtils.defaultIfEmpty(mailConfigResponse.getPassword(), "").length()) });
	    store.connect(mailConfigResponse.getUrl(), mailConfigResponse.getUser(), mailConfigResponse.getPassword());
	    log.info("     --> store connected.");
	    return store;
	} catch (Exception e) {
	    log.error("Errore nella connessione allo store {}", e);
	    throw e;
	}
    }

    private Folder getFolderSession(Store store, Map<String, String> altriParametriVerticalizzazione, int modalitaLetturaCasella) throws Exception {

	log.debug("getFolderSession()...");
	try {
	    String cartellaMail = mailInboxFromParametriVerticalizzazione(altriParametriVerticalizzazione);
	    log.debug("getFolderSession()... {}-{} ", CARTELLA_MAIL, cartellaMail);
	    Folder folder = store.getFolder(cartellaMail);
	    folder.open(modalitaLetturaCasella);
	    return folder;
	} catch (Exception e) {
	    String message = "Errore nell'apertura della cartella (" + altriParametriVerticalizzazione.get(CARTELLA_MAIL) + ") : " + e.getMessage();
	    log.error(message, e);
	    throw new IllegalArgumentException(message, e);
	}
    }

    public static String mailInboxFromParametriVerticalizzazione(Map<String, String> altriParametriVerticalizzazione) {

	String cartellaMail = altriParametriVerticalizzazione.get(CARTELLA_MAIL);
	if (StringUtils.isBlank(cartellaMail)) {
	    cartellaMail = INBOX_FOLDER_DEFAULT;
	}
	return cartellaMail;
    }

    /*
    public Properties populatePropertyMail(String storeProtocol, String storePort, String storeHost) {
    
    Properties props = new Properties();
    String protocollRead = "pop3";
    if (storeProtocol != null && storeProtocol.startsWith("imap")) {
        protocollRead = "imap";
    }
    if (storeProtocol.endsWith("_ssl")) {
        props.setProperty("mail." + protocollRead + ".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail." + protocollRead + ".socketFactory.fallback", "false");
        //disabilito il controllo sulla catena dei certificati
        try {
    	MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
    	socketFactory.setTrustAllHosts(true);
    	props.put("mail." + protocollRead + ".ssl.socketFactory", socketFactory);
    	props.put("mail.imaps.starttls.enable", "true");
    	props.put("mail.imap.starttls.enable", "true");
    	SSLSocket socket = (SSLSocket) socketFactory.createSocket();
    	String supportedProtocols = supportedProtocolsToString(socket.getSupportedProtocols());
    	props.put("mail.imaps.ssl.protocols", supportedProtocols);
    	props.put("mail.imap.ssl.protocols", supportedProtocols);
    	//props.put("mail.debug", "true");
        } catch (Exception e) {
    	log.error("{}", e);
        }
    }
    if (storePort != null) {
        props.setProperty("mail." + protocollRead + ".port", storePort);
        props.setProperty("mail." + protocollRead + ".socketFactory.port", storePort);
    }
    if (storeHost != null) {
        props.put("mail." + protocollRead + ".host", storeHost);//
    }
    props.put("mail.store.protocol", protocollRead);
    log.debug("populatePropertyMail: {}", props);
    return props;
    }
    */
    private String supportedProtocolsToString(String[] supportedProtocols) {

	String ret = "";
	if (supportedProtocols != null) {
	    for (int i = 0; i < supportedProtocols.length; i++) {
		ret += supportedProtocols[i] + " ";
	    }
	}
	ret = ret.trim();
	return ret;
    }

    public Session getSession(Properties propServerMail, String userId, String password) {

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

    private static DataHandler inputStream2DataHandler(InputStream inputStream) throws IOException {

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	byte[] buffer = new byte[1024];
	int length = 0;
	while ((length = inputStream.read(buffer)) != -1) {
	    baos.write(buffer, 0, length);
	}
	DataHandler dh = new DataHandler(baos.toByteArray(), "application/octet-stream");
	return dh;
    }

    public boolean isSoftwareAttivo(String software, List<String> listaSoftwareAttivi) {

	if (listaSoftwareAttivi != null) {
	    boolean trovato = false;
	    Iterator it = listaSoftwareAttivi.iterator();
	    while (it.hasNext() && !trovato) {
		String softwareAttivo = (String) it.next();
		if (softwareAttivo.equalsIgnoreCase(software)) {
		    trovato = true;
		}
	    }
	    return trovato;
	} else {
	    return false;
	}
    }

    private String sistemaNomiFile(String nomeFile) {

	log.debug("nomeFile: {}", nomeFile);
	nomeFile = eliminaCaratteriNonAscii(nomeFile.replace("&#25;", EMPTY_CACHED_FINAL_STRING));
	log.debug("nomeFile sistemato: {}", nomeFile);
	return nomeFile;
    }

    private String eliminaCaratteriNonAscii(String nomeFile) {

	return NON_ASCII_PATTERN.matcher(StringUtils.defaultString(nomeFile)).replaceAll(EMPTY_CACHED_FINAL_STRING);
    }
}
