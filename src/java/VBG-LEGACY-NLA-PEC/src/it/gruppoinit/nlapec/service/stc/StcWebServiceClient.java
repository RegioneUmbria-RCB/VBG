package it.gruppoinit.nlapec.service.stc;

import it.gruppoinit.nlapec.util.InfoIstanzaBean;
import it.gruppoinit.nlapec.util.PECMessage;
import it.init.sigepro.rte.AllegatoBinarioRequest;
import it.init.sigepro.rte.AllegatoBinarioResponse;
import it.init.sigepro.rte.CheckTokenRequest;
import it.init.sigepro.rte.CheckTokenResponse;
import it.init.sigepro.rte.InserimentoPraticaRequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.LoginRequest;
import it.init.sigepro.rte.LoginResponse;
import it.init.sigepro.rte.NotificaAttivitaRequest;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.RiferimentiAllegatoType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.TipoAttivitaType;
import it.init.sigepro.rte.types.ValoreParametroType;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.dom.DOMResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class StcWebServiceClient {

    private static final Logger log = LoggerFactory.getLogger(StcWebServiceClient.class);
    private String stcWsUrl;
    private String idEnteDestinatario;
    private String idSportelloDestinatario;
    private String nlaUserId;
    private String nlaPassword;
    private String idEnteMittente;
    private String idNodoMittente;
    private String idNodoDestinatario;
    private String idSportelloMittente;
    private String codiceComuneAssociato;
    private WebServiceTemplate webServiceTemplate;

    public String login() throws Exception {

	log.debug("login STC()...");
	LoginRequest request = new LoginRequest();
	request.setUsername(getNlaUserId());
	log.debug("NlaUser : " + getNlaUserId());
	request.setPassword(getNlaPassword());
	log.debug("Password : " + getNlaPassword());
	log.debug("Url STC : " + getStcWsUrl());
	LoginResponse response = (LoginResponse) webServiceTemplate.marshalSendAndReceive(getStcWsUrl(), request);
	String token = "";
	if (response.isResult()) {
	    token = response.getToken();
	}
	if (StringUtils.isBlank(token)) {
	    log.error("Errore nel metodo login(): token nullo");
	    throw new Exception("Errore durante la login a STC: token nullo");
	}
	log.debug("login()...done! token={}", token);
	return token;
    }

    public void checkToken(String token) {

	CheckTokenRequest request = new CheckTokenRequest();
	request.setToken(token);
	try {
	    CheckTokenResponse response = (CheckTokenResponse) webServiceTemplate.marshalSendAndReceive(getStcWsUrl(), request);
	    if (!response.isResult()) {
		log.error("checkToken({}): Errore durante la validazione del token: Token non valido", token);
		throw new RuntimeException("Errore durante la validazione del token: Token non valido");
	    }
	} catch (Exception e) {
	    log.error("checkToken({}): Errore durante la validazione del token: {}", token, e.getMessage());
	    throw new RuntimeException("Errore durante la validazione del token: " + e.getMessage());
	}
    }

    public InserimentoPraticaResponse inserisciPratica(String token, DettaglioPraticaType pratica) throws Exception {

	log.debug("inserisciPratica()...");
	InserimentoPraticaResponse response = null;
	InserimentoPraticaRequest request = new InserimentoPraticaRequest();
	request.setSportelloMittente(getSportelloMittente());
	request.setSportelloDestinatario(getSportelloDestinatario());
	checkComuneAssociato(pratica);
	request.setDettaglioPratica(pratica);
	request.setToken(token);
	try {
	    response = (InserimentoPraticaResponse) webServiceTemplate.marshalSendAndReceive(getStcWsUrl(), request);
	    if (response.getDettaglioErrore() != null && !response.getDettaglioErrore().isEmpty()) {
		log.error("inserisciPratica(): la response del WS STC InserimentoPratica contiene il seguente errore: {}",
			getMessaggioErrore(response.getDettaglioErrore()));
		throw new Exception("Errore ritornato dalla chiamata al WS STC InserimentoPratica: "
			+ getMessaggioErrore(response.getDettaglioErrore()));
	    } else {
		log.debug("inserisciPratica(): inserita pratica con id={}", response.getDettaglioPratica().getIdPratica());
	    }
	} catch (Exception e) {
	    String soapFaultDetails = getSOAPFAULT(e);
	    if (StringUtils.isNotBlank(soapFaultDetails)) {
		soapFaultDetails = ", dettaglio: " + soapFaultDetails;
	    }
	    log.error("inserisciPratica(): Errore ritornato dalla chiamata al WS STC InserimentoPratica: {}{}", e.getMessage(), soapFaultDetails);
	    throw new Exception("Errore ritornato dalla chiamata al WS STC InserimentoPratica: " + e.getMessage() + soapFaultDetails);
	}
	log.debug("inserisciPratica()...done!");
	return response;
    }

    public AllegatoBinarioResponse getAllegatoBinario(String token, RiferimentiAllegatoType rifAllegato) throws Exception {

	AllegatoBinarioResponse response = new AllegatoBinarioResponse();
	AllegatoBinarioRequest request = new AllegatoBinarioRequest();
	request.setSportelloMittente(getSportelloMittente());
	request.setSportelloDestinatario(getSportelloDestinatario());
	request.setRiferimentiAllegato(rifAllegato);
	request.setToken(token);
	try {
	    response = (AllegatoBinarioResponse) webServiceTemplate.marshalSendAndReceive(getStcWsUrl(), request);
	} catch (Exception e) {
	    String soapFaultDetails = getSOAPFAULT(e);
	    if (StringUtils.isNotBlank(soapFaultDetails)) {
		soapFaultDetails = ", dettaglio: " + soapFaultDetails;
	    }
	    log.error("getAllegatoBinario(): Errore ritornato dalla chiamata al WS STC allegatoBinario: {}{}", e.getMessage(), soapFaultDetails);
	    throw new Exception("Errore ritornato dalla chiamata al WS STC allegatoBinario: " + e.getMessage() + soapFaultDetails);
	}
	return response;
    }

    public NotificaAttivitaResponse notificaAttivita(String tokenSTC, InfoIstanzaBean infoIstanza, PECMessage pecMessage,
	    ArrayList<String> listaFileAttachment, ArrayList<String> idOggettiList) throws Exception {

	NotificaAttivitaResponse response = new NotificaAttivitaResponse();
	NotificaAttivitaRequest request = new NotificaAttivitaRequest();
	//
	request.setToken(tokenSTC);
	//
	SportelloType sportelloMittente = new SportelloType();
	sportelloMittente.setIdEnte(getIdEnteMittente());
	sportelloMittente.setIdNodo(getIdNodoMittente());
	sportelloMittente.setIdSportello(getIdSportelloMittente());
	//
	SportelloType sportelloDestinatario = new SportelloType();
	sportelloDestinatario.setIdEnte(infoIstanza.getIdcomune());
	sportelloDestinatario.setIdNodo(getIdNodoDestinatario());
	sportelloDestinatario.setIdSportello(infoIstanza.getSoftware());
	//
	request.setSportelloMittente(sportelloMittente);
	request.setSportelloDestinatario(sportelloDestinatario);
	//
	DettaglioAttivitaType dettaglioAttivita = new DettaglioAttivitaType();
	GregorianCalendar gc = new GregorianCalendar();
	gc.setTimeInMillis(pecMessage.getDate().getTime());
	dettaglioAttivita.setDataAttivita(DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
	dettaglioAttivita.setIdAttivita(infoIstanza.getCodiceMovimento());
	dettaglioAttivita.setIdPratica(infoIstanza.getCodiceIstanza());
	// dettaglioAttivita.setParere(pecMessage.getBody());
	dettaglioAttivita.setEsito(true);
	TipoAttivitaType tipoAttivita = new TipoAttivitaType();
	tipoAttivita.setCodice("");
	tipoAttivita.setDescrizione("");
	ParametroType parametro = new ParametroType();
	parametro.setNome("$ESEGUI_CONTROMOVIMENTO_DI$");
	ValoreParametroType valore = new ValoreParametroType();
	valore.setCodice(infoIstanza.getCodiceMovimento());
	valore.setDescrizione("");
	parametro.getValore().add(valore);
	dettaglioAttivita.getAltriDati().add(parametro);
	dettaglioAttivita.setTipoAttivita(tipoAttivita);
	ProcedimentoType proc = new ProcedimentoType();
	proc.setCodice(infoIstanza.getCodiceInventarioProcedimento());
	dettaglioAttivita.getProcedimenti().add(proc);
	//
	request.setDatiAttivita(dettaglioAttivita);
	//
	RiferimentiPraticaType rifPratica = new RiferimentiPraticaType();
	GregorianCalendar c = new GregorianCalendar();
	c.setTime(infoIstanza.getDataPratica());
	XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	rifPratica.setDataPratica(date2);
	rifPratica.setNumeroPratica(infoIstanza.getNumeroIstanza());
	rifPratica.setIdPratica(infoIstanza.getCodiceIstanza());
	request.setRifPraticaDestinatario(rifPratica);
	//
	if (listaFileAttachment != null && listaFileAttachment.size() > 0) {
	    for (int i = 0; i < listaFileAttachment.size(); i++) {
		String nomeFile = listaFileAttachment.get(i);
		DocumentiType documenti = new DocumentiType();
		documenti.setId(nomeFile);
		documenti.setDocumento(nomeFile);
		documenti.setTipoDocumento("Altro");
		AllegatiType allegati = new AllegatiType();
		allegati.setId("CODICEOGGETTO:" + idOggettiList.get(i));
		allegati.setAllegato(nomeFile);
		documenti.setAllegati(allegati);
		request.getDatiAttivita().getDocumenti().add(documenti);
	    }
	}
	try {
	    response = (NotificaAttivitaResponse) webServiceTemplate.marshalSendAndReceive(getStcWsUrl(), request);
	} catch (Exception e) {
	    String soapFaultDetails = getSOAPFAULT(e);
	    if (StringUtils.isNotBlank(soapFaultDetails)) {
		soapFaultDetails = ", dettaglio: " + soapFaultDetails;
	    }
	    log.error("getAllegatoBinario(): Errore ritornato dalla chiamata al WS STC allegatoBinario: {}{}", e.getMessage(), soapFaultDetails);
	    throw new Exception("Errore ritornato dalla chiamata al WS STC allegatoBinario: " + e.getMessage() + soapFaultDetails);
	}
	return response;
    }

    private SportelloType getSportelloMittente() {

	SportelloType mittente = new SportelloType();
	mittente.setIdEnte(getIdEnteMittente());
	mittente.setIdSportello(getIdSportelloMittente());
	mittente.setIdNodo(getIdNodoMittente());
	return mittente;
    }

    private SportelloType getSportelloDestinatario() {

	SportelloType destinatario = new SportelloType();
	destinatario.setIdEnte(getIdEnteDestinatario());
	destinatario.setIdSportello(getIdSportelloDestinatario());
	destinatario.setIdNodo(getIdNodoDestinatario());
	return destinatario;
    }

    private void checkComuneAssociato(DettaglioPraticaType pratica) {

	if (pratica.getCodiceComune() == null || StringUtils.isBlank(pratica.getCodiceComune().getCodiceCatastale())) {
	    if (StringUtils.isBlank(getCodiceComuneAssociato())) {
		codiceComuneAssociato = idEnteDestinatario;
	    }
	    ComuneType comuneAssociato = new ComuneType();
	    comuneAssociato.setCodiceCatastale(codiceComuneAssociato);
	    pratica.setCodiceComune(comuneAssociato);
	}
    }

    private String getMessaggioErrore(List<ErroreType> errors) {

	StringBuffer errorString = new StringBuffer();
	if (errors != null) {
	    for (ErroreType error : errors) {
		errorString.append("[errCod: ").append(error.getNumeroErrore()).append(", errDesc:").append(error.getDescrizione()).append("] ");
	    }
	}
	return errorString.toString();
    }

    @SuppressWarnings("rawtypes")
    private String getSOAPFAULT(Exception e) {

	StringBuffer details = new StringBuffer();
	if (e instanceof SoapFaultClientException) {
	    SoapFaultClientException we = (SoapFaultClientException) e;
	    SoapFaultDetail detail = we.getSoapFault().getFaultDetail();
	    if (detail != null) {
		for (Iterator iterator = detail.getDetailEntries(); iterator.hasNext();) {
		    SoapFaultDetailElement el = (SoapFaultDetailElement) iterator.next();
		    if (el.getResult() != null && el.getResult() instanceof DOMResult) {
			DOMResult res = (DOMResult) el.getResult();
			if (res.getNode() != null) {
			    details.append("\n").append(res.getNode().getTextContent());
			}
		    }
		}
	    }
	}
	return details.toString();
    }

    public String getStcWsUrl() {

	return stcWsUrl;
    }

    public void setStcWsUrl(String stcWsUrl) {

	this.stcWsUrl = stcWsUrl;
    }

    public String getIdEnteDestinatario() {

	return idEnteDestinatario;
    }

    public void setIdEnteDestinatario(String idEnteDestinatario) {

	this.idEnteDestinatario = idEnteDestinatario;
    }

    public String getIdSportelloDestinatario() {

	return idSportelloDestinatario;
    }

    public void setIdSportelloDestinatario(String idSportelloDestinatario) {

	this.idSportelloDestinatario = idSportelloDestinatario;
    }

    public String getIdEnteMittente() {

	return idEnteMittente;
    }

    public void setIdEnteMittente(String idEnteMittente) {

	this.idEnteMittente = idEnteMittente;
    }

    public String getIdSportelloMittente() {

	return idSportelloMittente;
    }

    public void setIdSportelloMittente(String idSportelloMittente) {

	this.idSportelloMittente = idSportelloMittente;
    }

    public WebServiceTemplate getWebServiceTemplate() {

	return webServiceTemplate;
    }

    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {

	this.webServiceTemplate = webServiceTemplate;
    }

    public String getNlaUserId() {

	return nlaUserId;
    }

    public void setNlaUserId(String nlaUserId) {

	this.nlaUserId = nlaUserId;
    }

    public String getNlaPassword() {

	return nlaPassword;
    }

    public void setNlaPassword(String nlaPassword) {

	this.nlaPassword = nlaPassword;
    }

    public String getCodiceComuneAssociato() {

	return codiceComuneAssociato;
    }

    public void setCodiceComuneAssociato(String codiceComuneAssociato) {

	this.codiceComuneAssociato = codiceComuneAssociato;
    }

    public String getIdNodoMittente() {

	return idNodoMittente;
    }

    public void setIdNodoMittente(String idNodoMittente) {

	this.idNodoMittente = idNodoMittente;
    }

    public String getIdNodoDestinatario() {

	return idNodoDestinatario;
    }

    public void setIdNodoDestinatario(String idNodoDestinatario) {

	this.idNodoDestinatario = idNodoDestinatario;
    }

    public InserimentoPraticaResponse inserisciPratica2(InserimentoPraticaRequest request) throws Exception {

	log.debug("inserisciPratica2()...");
	InserimentoPraticaResponse response = null;
	request.getSportelloMittente().setIdNodo(getIdNodoMittente());
	request.getSportelloMittente().setIdEnte(getIdEnteMittente());
	request.getSportelloMittente().setIdSportello(getIdSportelloMittente());
	request.getSportelloDestinatario().setIdNodo(getIdNodoDestinatario());
	try {
	    response = (InserimentoPraticaResponse) webServiceTemplate.marshalSendAndReceive(getStcWsUrl(), request);
	    if (response.getDettaglioErrore() != null && !response.getDettaglioErrore().isEmpty()) {
		log.error("inserisciPratica(): la response del WS STC InserimentoPratica contiene il seguente errore: {}",
			getMessaggioErrore(response.getDettaglioErrore()));
		throw new Exception("Errore ritornato dalla chiamata al WS STC InserimentoPratica: "
			+ getMessaggioErrore(response.getDettaglioErrore()));
	    } else {
		log.debug("inserisciPratica(): inserita pratica con id={}", response.getDettaglioPratica().getIdPratica());
	    }
	} catch (Exception e) {
	    String soapFaultDetails = getSOAPFAULT(e);
	    if (StringUtils.isNotBlank(soapFaultDetails)) {
		soapFaultDetails = ", dettaglio: " + soapFaultDetails;
	    }
	    log.error("inserisciPratica(): Errore ritornato dalla chiamata al WS STC InserimentoPratica: {}{}", e.getMessage(), soapFaultDetails);
	    throw new Exception("Errore ritornato dalla chiamata al WS STC InserimentoPratica: " + e.getMessage() + soapFaultDetails);
	}
	log.debug("inserisciPratica()...done!");
	return response;
    }
}
