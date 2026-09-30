package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.CustomHtmlBuilder;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.kdm.docer.core.authentication.AuthenticationService;
import it.kdm.docer.core.authentication.AuthenticationServicePortType;
import it.kdm.docer.core.authentication.Exception_Exception;
import it.kdm.docer.core.authentication.WrappedException;
import it.kdm.docer.sdk.classes.xsd.KeyValuePair;
import it.kdm.docer.sdk.classes.xsd.SearchItem;
import it.kdm.docer.sdk.classes.xsd.StreamDescriptor;
import it.kdm.docer.sdk.exceptions.xsd.DocerException;
import it.kdm.docer.webservices.DocerException_Exception;
import it.kdm.docer.webservices.DocerServices;
import it.kdm.docer.webservices.DocerServicesPortType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.MTOMFeature;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DOCERWSClient {

    private static final Logger log = LoggerFactory.getLogger(DOCERWSClient.class);
    private static final Integer MAX_RESULTS_RICERCHE = 20;

    public DOCERWSClient(String codiceComune, String software, VerticalizzazioniService verticalizzazioniService) {

	this.codiceComune = codiceComune;
	this.software = software;
	this.verticalizzazioniService = verticalizzazioniService;
	Verticalizzazioniparametri vpEnte = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER, WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER_CODICE_ENTE, codiceComune, software);
	if (vpEnte != null) {
	    this.codiceEnte = vpEnte.getValore();
	}
	Verticalizzazioniparametri vpAoo = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER, WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER_CODICE_AOO, codiceComune, software);
	if (vpAoo != null) {
	    this.codiceAoo = vpAoo.getValore();
	}
	Verticalizzazioniparametri vpApplicazione = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER, WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER_CODICE_AOO, codiceComune, software);
	if (vpApplicazione != null) {
	    this.applicazione = vpApplicazione.getValore();
	}
	if (StringUtils.isBlank(codiceEnte) || StringUtils.isBlank(codiceAoo) || StringUtils.isBlank(applicazione)) {
	    throw new InvalidConfigurationException("I parametri codiceEnte,codiceAoo,applicazione della regola "
		    + WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER + " non sono stati configurati correttamente");
	}
    }

    private String codiceComune;
    private String software;
    private String codiceEnte;
    private String applicazione;
    private String codiceAoo;
    private VerticalizzazioniService verticalizzazioniService;

    private String getUrlServiceWS(String parametroVert) {

	Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER, parametroVert, codiceComune, software);
	if (vp == null) {
	    throw new InvalidConfigurationException("Non è stato configurato correttamente il parametro " + parametroVert + " della regola "
		    + WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER);
	}
	if (org.apache.commons.lang.StringUtils.isBlank(vp.getValore())) {
	    throw new InvalidConfigurationException("Non è stato configurato correttamente il parametro " + parametroVert + " della regola "
		    + WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER);
	}
	return vp.getValore();
    }

    private String getUrlWSAuth() {

	return getUrlServiceWS(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER_URL_LOGIN);
    }

    private String getUrlWSGedoc() {

	return getUrlServiceWS(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER_URL_GESTIONE_DOCS);
    }

    private AuthenticationServicePortType getAuthenticationPort() throws InvalidConfigurationException {

	String urlWS = getUrlWSAuth();
	AuthenticationService s = null;
	try {
	    s = new AuthenticationService(new URL(urlWS));
	} catch (Exception e) {
	    throw new InvalidConfigurationException("Errore nella configurazione dell'URL dei servizi di autenticazione: url non corretta [" + urlWS
		    + "]");
	}
	AuthenticationServicePortType port = s.getAuthenticationServiceHttpSoap11Endpoint();
	BindingProvider bp = (BindingProvider) port;
	bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlWS);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(120000); // Line #2  
	httpClientPolicy.setReceiveTimeout(120000); // Line #3  
	conduit.setClient(httpClientPolicy);
	return port;
    }

    private DocerServicesPortType getGeDocPort() throws InvalidConfigurationException {

	String urlWS = getUrlWSGedoc();
	DocerServices s = null;
	try {
	    s = new DocerServices(new URL(urlWS));
	} catch (Exception e) {
	    throw new InvalidConfigurationException("Errore nella configurazione dell'URL dei servizi di gestione documentale: url non corretta ["
		    + urlWS + "]");
	}
	MTOMFeature mtomFeature = new MTOMFeature(true, 0);
	DocerServicesPortType port = s.getDocerServicesHttpSoap11Endpoint(mtomFeature);
	BindingProvider bp = (BindingProvider) port;
	bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlWS);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(120000); // Line #2  
	httpClientPolicy.setReceiveTimeout(300000); // Line #3  
	conduit.setClient(httpClientPolicy);
	return port;
    }

    private String recuperaDescrizioneEccezione(DocerException_Exception e) {

	it.kdm.docer.webservices.DocerException de = e.getFaultInfo();
	DocerException dec = null;
	if (de != null) {
	    dec = de.getDocerException();
	}
	String result = e.getMessage();
	if (dec != null) {
	    result += ToStringBuilder.reflectionToString(dec, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	return result;
    }

    private String recuperaDescrizioneEccezioneWrapped(Exception_Exception e) {

	it.kdm.docer.core.authentication.Exception de = e.getFaultInfo();
	WrappedException dec = null;
	if (de != null) {
	    dec = de.getException();
	}
	String result = e.getMessage();
	if (dec != null) {
	    result += ToStringBuilder.reflectionToString(dec, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	return result;
    }

    //////////////////////////////////////////////////////////
    ///////// 		AUTENTICAZIONE		//////////////
    //////////////////////////////////////////////////////////    
    public String login(String username, String password) throws InvalidConfigurationException, FunzioneBusinessRemotaException {

	AuthenticationServicePortType port = getAuthenticationPort();
	String token = "";
	try {
	    token = port.login(username, password, codiceEnte, applicazione);
	} catch (Exception_Exception e) {
	    e.printStackTrace();
	    throw new FunzioneBusinessRemotaException("Errore in fase di login ai servizi DOC-ER: " + e.getMessage(), e);
	}
	return token;
    }

    public void logout(String token) throws InvalidConfigurationException, FunzioneBusinessRemotaException {

	try {
	    AuthenticationServicePortType port = getAuthenticationPort();
	    port.logout(token);
	} catch (Exception e) {
	    log.error("errore in fase di logout dei servizi DOCER: {}", e);
	}
    }

    public boolean checkToken(String token) throws InvalidConfigurationException, FunzioneBusinessRemotaException {

	AuthenticationServicePortType port = getAuthenticationPort();
	try {
	    Boolean check = port.verifyToken(token);
	    if (check == null) {
		return false;
	    }
	    return check.booleanValue();
	} catch (Exception_Exception e) {
	    throw new FunzioneBusinessRemotaException(recuperaDescrizioneEccezioneWrapped(e), e);
	}
    }

    //////////////////////////////////////////////////////////
    ///////// 	    GESTIONE DOCUMENTALE	//////////////
    //////////////////////////////////////////////////////////
    public boolean isGroupPresent(String token, String groupId) throws FunzioneBusinessRemotaException {

	DocerServicesPortType port = getGeDocPort();
	try {
	    List<KeyValuePair> l = port.getGroup(token, groupId);
	    return (l != null && l.size() > 0);
	} catch (DocerException_Exception e) {
	    throw new FunzioneBusinessRemotaException(recuperaDescrizioneEccezione(e), e);
	}
    }

    private KeyValuePair newKVP(String key, String value) {

	KeyValuePair k = new KeyValuePair();
	k.setKey(key);
	k.setValue(value);
	return k;
    }

    public boolean createGroup(String token, String groupId, String groupName) throws FunzioneBusinessRemotaException {

	DocerServicesPortType port = getGeDocPort();
	try {
	    List<KeyValuePair> gi = new ArrayList<KeyValuePair>();
	    gi.add(newKVP("GROUP_ID", groupId));
	    gi.add(newKVP("GROUP_NAME", groupName));
	    gi.add(newKVP("PARENT_GROUP_ID", codiceEnte));
	    Boolean result = port.createGroup(token, gi);
	    if (result == null) {
		return false;
	    } else {
		return result.booleanValue();
	    }
	} catch (DocerException_Exception e) {
	    throw new FunzioneBusinessRemotaException(recuperaDescrizioneEccezione(e), e);
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e.getMessage());
	}
    }

    public boolean isUserPresent(String token, String codiceUtente) throws FunzioneBusinessRemotaException {

	DocerServicesPortType port = getGeDocPort();
	try {
	    List<KeyValuePair> l = port.getUser(token, codiceUtente);
	    return (l != null && l.size() > 0);
	} catch (DocerException_Exception e) {
	    throw new FunzioneBusinessRemotaException(recuperaDescrizioneEccezione(e), e);
	}
    }

    public List<String> getGroupsOfUser(String token, String codiceUtente) throws FunzioneBusinessRemotaException {

	DocerServicesPortType port = getGeDocPort();
	try {
	    List<String> l = port.getGroupsOfUser(token, codiceUtente);
	    return l;
	} catch (DocerException_Exception e) {
	    throw new FunzioneBusinessRemotaException(recuperaDescrizioneEccezione(e), e);
	}
    }

    public boolean rimuoviGruppoAUser(String token, String codiceUtente, String idGruppo) throws FunzioneBusinessRemotaException {

	return aggiungiRimuoviGruppoAUtente(token, codiceUtente, idGruppo, false);
    }

    private boolean aggiungiRimuoviGruppoAUtente(String token, String codiceUtente, String idGruppo, boolean isAggiungi)
	    throws FunzioneBusinessRemotaException {

	DocerServicesPortType port = getGeDocPort();
	if (StringUtils.isBlank(codiceUtente)) {
	    throw new InvalidConfigurationException("Il codice utente DOCER non è stato impostato");
	}
	if (StringUtils.isBlank(idGruppo)) {
	    throw new InvalidConfigurationException("La idGruppo DOCER del responsabile non è stata impostata");
	}
	try {
	    List<String> gruppi = new ArrayList<String>();
	    gruppi.add(idGruppo);
	    Boolean result = null;
	    List<String> empty = new ArrayList<String>();
	    empty.add("");
	    if (isAggiungi) {
		result = port.updateGroupsOfUser(token, codiceUtente, gruppi, empty);
	    } else {
		result = port.updateGroupsOfUser(token, codiceUtente, empty, gruppi);
	    }
	    if (result == null) {
		return false;
	    } else {
		return result.booleanValue();
	    }
	} catch (DocerException_Exception e) {
	    throw new FunzioneBusinessRemotaException(recuperaDescrizioneEccezione(e), e);
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e.getMessage());
	}
    }

    public boolean aggiungiGruppoAUser(String token, String codiceUtente, String idGruppo) throws FunzioneBusinessRemotaException {

	return aggiungiRimuoviGruppoAUtente(token, codiceUtente, idGruppo, true);
    }

    public boolean createUser(String token, String codiceUtente, String descrizioneUtente, String emailUtente, String passwordUtente)
	    throws FunzioneBusinessRemotaException {

	DocerServicesPortType port = getGeDocPort();
	if (StringUtils.isBlank(codiceUtente)) {
	    throw new InvalidConfigurationException("Il codice utente DOCER non è stato impostato");
	}
	if (StringUtils.isBlank(descrizioneUtente)) {
	    throw new InvalidConfigurationException("La descrizione responsabile non è stata impostata");
	}
	if (StringUtils.isBlank(emailUtente)) {
	    throw new InvalidConfigurationException("La email del responsabile non è stata impostata");
	}
	if (StringUtils.isBlank(passwordUtente)) {
	    throw new InvalidConfigurationException("La password DOCER del responsabile non è stata impostata");
	}
	try {
	    List<KeyValuePair> gi = new ArrayList<KeyValuePair>();
	    gi.add(newKVP("USER_ID", codiceUtente));
	    gi.add(newKVP("FULL_NAME", descrizioneUtente));
	    gi.add(newKVP("USER_PASSWORD", passwordUtente));
	    gi.add(newKVP("COD_ENTE", codiceEnte));
	    gi.add(newKVP("COD_AOO", codiceAoo));
	    gi.add(newKVP("EMAIL_ADDRESS", emailUtente));
	    Boolean result = port.createUser(token, gi);
	    if (result == null) {
		return false;
	    } else {
		return result.booleanValue();
	    }
	} catch (DocerException_Exception e) {
	    throw new FunzioneBusinessRemotaException(recuperaDescrizioneEccezione(e), e);
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e.getMessage());
	}
    }

    public List<ChiaveValoreBean<String, String>> getTipiDocumentoByAoo(String token) throws FunzioneBusinessRemotaException {

	DocerServicesPortType port = getGeDocPort();
	try {
	    List<ChiaveValoreBean<String, String>> out = new ArrayList<ChiaveValoreBean<String, String>>();
	    List<KeyValuePair> result = port.getDocumentTypesByAOO(token, codiceEnte, codiceAoo);
	    for (KeyValuePair kvp : result) {
		ChiaveValoreBean<String, String> cvb = new ChiaveValoreBean<String, String>();
		cvb.setChiave(kvp.getKey());
		cvb.setValore(kvp.getValue());
		out.add(cvb);
	    }
	    return out;
	} catch (DocerException_Exception e) {
	    throw new FunzioneBusinessRemotaException(recuperaDescrizioneEccezione(e), e);
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e.getMessage());
	}
    }

    public StreamDescriptor downloadFile(String token, String docId) throws FunzioneBusinessRemotaException {

	DocerServicesPortType port = getGeDocPort();
	try {
	    StreamDescriptor result = port.downloadDocument(token, docId);
	    return result;
	} catch (DocerException_Exception e) {
	    throw new FunzioneBusinessRemotaException(recuperaDescrizioneEccezione(e), e);
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e.getMessage());
	}
    }

    public List<ChiaveValoreBean<String, String>> cercaDocumenti(String token, String nomeFile, String tipoDocumento, String descrizioneFile,
	    String numProtocollo, String annoProtocollo, String registroId, List<String> keywords, List<ChiaveValoreBean<String, String>> metadatis)
	    throws FunzioneBusinessRemotaException {

	DocerServicesPortType port = getGeDocPort();
	try {
	    List<ChiaveValoreBean<String, String>> out = new ArrayList<ChiaveValoreBean<String, String>>();
	    List<KeyValuePair> wsc = new ArrayList<KeyValuePair>();
	    wsc.add(newKVP("COD_ENTE", codiceEnte));
	    wsc.add(newKVP("COD_AOO", codiceAoo));
	    if (StringUtils.isNotBlank(nomeFile)) {
		wsc.add(newKVP("DOCNAME", nomeFile));
	    }
	    if (StringUtils.isNotBlank(descrizioneFile)) {
		wsc.add(newKVP("ABSTRACT", descrizioneFile));
	    }
	    if (StringUtils.isNotBlank(tipoDocumento)) {
		wsc.add(newKVP("TYPE_ID", tipoDocumento));
	    }
	    if (StringUtils.isNotBlank(registroId)) {
		wsc.add(newKVP("REGISTRO_PG", registroId));
	    }
	    if (StringUtils.isNotBlank(numProtocollo)) {
		wsc.add(newKVP("NUM_PG", numProtocollo));
	    }
	    if (StringUtils.isNotBlank(annoProtocollo)) {
		wsc.add(newKVP("ANNO_PG", annoProtocollo));
	    }
	    if (metadatis != null) {
		for (ChiaveValoreBean<String, String> md : metadatis) {
		    wsc.add(newKVP(md.getChiave(), md.getValore()));
		}
	    }
	    List<KeyValuePair> orderby = new ArrayList<KeyValuePair>();
	    orderby.add(newKVP("ABSTRACT", "ASC"));
	    orderby.add(newKVP("DOCNAME", "ASC"));
	    if (keywords.size() == 0) {
		keywords.add("");
	    }
	    List<SearchItem> result = port.searchDocuments(token, wsc, keywords, MAX_RESULTS_RICERCHE, orderby);
	    for (SearchItem si : result) {
		ChiaveValoreBean<String, String> cvb = new ChiaveValoreBean<String, String>();
		List<KeyValuePair> md = si.getMetadata();
		String docNum = getKVPValue("DOCNUM", md);
		String docname = getKVPValue("DOCNAME", md);
		String abstractS = getKVPValue("ABSTRACT", md);
		// String typeId = getKVPValue("TYPE_ID", md);
		String numPg = getKVPValue("NUM_PG", md);
		String annoPg = getKVPValue("ANNO_PG", md);
		String registro = getKVPValue("REGISTRO_PG", md);
		String tipoDocumentoV = getKVPValue("TYPE_ID", md);
		cvb.setChiave(docNum);
		CustomHtmlBuilder html = new CustomHtmlBuilder();
		html.div().id("doc_" + docNum).styleClass("docs").close(); // <div id="doc_100">
		html.table(2).style("width: 800px;").close().tr(3).close().td(4).style("width: 650px;").close().ul().close();// <table><tr><td><ul>
		html.li().close().append("id documentale: ").bold().append(docNum).boldEnd().liEnd();// <li>id documentale: docnum</li>
		if (StringUtils.isNotBlank(abstractS)) {
		    html.li().close().append("descrizione: ").bold().append(abstractS).boldEnd().liEnd();// <li>descrizione: descrizione</li> 
		}
		html.li().close().append("nome file: ").bold().append(docname).boldEnd().liEnd();// <li>nome file: nomefile</li>
		if (StringUtils.isNotBlank(numPg)) {
		    html.li().close().append("dati protocollo: ").bold().append(numPg).append("/").append(annoPg).boldEnd().liEnd();// <li>dati protocollo: num/anno</li>
		}
		if (StringUtils.isNotBlank(registro)) {
		    html.li().close().append("registro: ").bold().append(registro).boldEnd().liEnd(); // <li>registro: registro</li>
		}
		if (StringUtils.isNotBlank(tipoDocumentoV)) {
		    html.li().close().append("tipo documento: ").bold().append(tipoDocumentoV).boldEnd().liEnd(); // <li>registro: registro</li>
		}
		html.ulEnd().tdEnd().td(4).close(); //</ul></td><td>
		html.append("<ul id=\"functions\"><li><a href=\"javascript:visualizza('" + docNum + "','" + Utilities.correggiNomeFile(docname)
			+ "')\">visualizza</a></li>");// <a href="javascript:visualizza('docnum')">visualizza</a>
		html.append("<li><a href=\"javascript:creaDocumento('" + docNum + "','" + Utilities.correggiNomeFile(docname)
			+ "')\">crea documento</a><div id=\"azioniOut" + docNum + "\"/></li></ul>");// <a href="javascript:creaDocumento('docnum')">crea documento</a>
		html.tdEnd().trEnd(3).tableEnd(2);// </td></tr></table>
		html.divEnd(); // </div>
		cvb.setValore(html.toString());
		out.add(cvb);
	    }
	    return out;
	} catch (DocerException_Exception e) {
	    throw new FunzioneBusinessRemotaException(recuperaDescrizioneEccezione(e), e);
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e.getMessage());
	}
    }

    public ChiaveValoreBean<String, String> cercaDocumento(String token, String docnum) throws FunzioneBusinessRemotaException {

	DocerServicesPortType port = getGeDocPort();
	try {
	    List<KeyValuePair> wsc = new ArrayList<KeyValuePair>();
	    wsc.add(newKVP("COD_ENTE", codiceEnte));
	    wsc.add(newKVP("COD_AOO", codiceAoo));
	    wsc.add(newKVP("DOCNUM", docnum));
	    List<KeyValuePair> orderby = new ArrayList<KeyValuePair>();
	    orderby.add(newKVP("ABSTRACT", "ASC"));
	    orderby.add(newKVP("DOCNAME", "ASC"));
	    List<String> keywords = new ArrayList<String>();
	    keywords.add("");
	    List<SearchItem> result = port.searchDocuments(token, wsc, keywords, MAX_RESULTS_RICERCHE, orderby);
	    if (result != null) {
		if (result.size() > 1) {
		    throw new RuntimeException("Trovati più documenti con il riferimento " + docnum);
		}
		if (result.size() == 1) {
		    for (SearchItem si : result) {
			ChiaveValoreBean<String, String> cvb = new ChiaveValoreBean<String, String>();
			List<KeyValuePair> md = si.getMetadata();
			String docNum = getKVPValue("DOCNUM", md);
			String docname = getKVPValue("DOCNAME", md);
			//		String abstractS = getKVPValue("ABSTRACT", md);
			//		String numPg = getKVPValue("NUM_PG", md);
			//		String annoPg = getKVPValue("ANNO_PG", md);
			//		String registro = getKVPValue("REGISTRO_PG", md);
			//		String tipoDocumentoV = getKVPValue("TYPE_ID", md);
			cvb.setChiave(docNum);
			cvb.setValore(docname);
			return cvb;
		    }
		}
	    }
	    return null;
	} catch (DocerException_Exception e) {
	    throw new FunzioneBusinessRemotaException(recuperaDescrizioneEccezione(e), e);
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e.getMessage());
	}
    }

    private String getKVPValue(String key, List<KeyValuePair> kvps) {

	for (KeyValuePair kvp : kvps) {
	    if (kvp.getKey().equals(key)) {
		return kvp.getValue();
	    }
	}
	return "";
    }
}
