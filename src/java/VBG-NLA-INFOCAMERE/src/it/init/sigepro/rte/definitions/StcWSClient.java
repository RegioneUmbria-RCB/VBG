package it.init.sigepro.rte.definitions;

import java.net.URL;
import java.util.List;

import javax.xml.ws.soap.MTOMFeature;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import it.init.sigepro.rte.RichiestaPraticaCollegataRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;
import it.init.sigepro.rte.RichiestaPraticaRequest;
import it.init.sigepro.rte.RichiestaPraticaResponse;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.RiferimentiAllegatoType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.SportelloType;

public class StcWSClient {

    private static final Logger log = LoggerFactory.getLogger(StcWSClient.class);
    private String stcWsUrl;
    private String idNodoDestinatario;
    //private String idEnteDestinatario;
    //private String idSportelloDestinatario;
    private String nlaUserId;
    private String nlaPassword;
    private String idNodoMittente;
    private String idEnteMittente;
    private String idSportelloMittente;
    private boolean mtom;
    private long timeout = 120000;

    public String login() throws Exception {

	log.debug("login()...");
	LoginRequest request = new LoginRequest();
	request.setUsername(getNlaUserId());
	request.setPassword(getNlaPassword());
	LoginResponse response = getStcWsPort().login(request);
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

    public boolean checkToken(String token) throws Exception {

	log.debug("checkToken({})...", token);
	CheckTokenRequest request = new CheckTokenRequest();
	request.setToken(token);
	CheckTokenResponse response = getStcWsPort().checkToken(request);
	log.debug("checkToken({})...done! result={}", token, response.isResult());
	return response.isResult();
    }

    public InserimentoPraticaResponse inserisciPratica(SportelloType mittente, SportelloType destinatario, String token, DettaglioPraticaType pratica)
	    throws Exception {

	log.debug("inserisciPratica()...");
	InserimentoPraticaResponse response = null;
	InserimentoPraticaRequest request = new InserimentoPraticaRequest();
	request.setSportelloMittente(mittente);
	request.setSportelloDestinatario(destinatario);
	request.setDettaglioPratica(pratica);
	request.setToken(token);
	try {
	    response = getStcWsPort().inserimentoPratica(request);
	    if (response.getDettaglioErrore() != null && !response.getDettaglioErrore().isEmpty()) {
		log.error("inserisciPratica(): la response del WS STC InserimentoPratica contiene il seguente errore: {}",
			getMessaggioErrore(response.getDettaglioErrore()));
		throw new Exception(
			"Errore ritornato dalla chiamata al WS STC InserimentoPratica: " + getMessaggioErrore(response.getDettaglioErrore()));
	    } else {
		log.debug("inserisciPratica(): inserita pratica con id={}", response.getDettaglioPratica().getIdPratica());
	    }
	} catch (Exception e) {
	    log.error("inserisciPratica(): Errore ritornato dalla chiamata al WS STC InserimentoPratica: {}", e);
	    throw new Exception("Errore ritornato dalla chiamata al WS STC InserimentoPratica: " + e.getMessage());
	}
	log.debug("inserisciPratica()...done!");
	return response;
    }

    public RichiestaPraticaCollegataResponse richiestaPraticaCollegata(SportelloType mitt, SportelloType dest, String idPraticaMitt, String token)
	    throws Exception {

	RichiestaPraticaCollegataRequest request = new RichiestaPraticaCollegataRequest();
	request.setSportelloMittente(mitt);
	request.setSportelloDestinatario(dest);
	request.setIdPraticaMitt(idPraticaMitt);
	request.setToken(token);
	RichiestaPraticaCollegataResponse response = getStcWsPort().richiestaPraticaCollegata(request);
	return response;
    }

    public RichiestaPraticaResponse richiestaPratica(SportelloType sportellomitente, SportelloType sportelloDestinatario, String idPraticaMitt,
	    String token, List<ParametroType> altridati) throws Exception {

	RichiestaPraticaRequest request = new RichiestaPraticaRequest();
	request.setSportelloMittente(sportellomitente);
	request.setSportelloDestinatario(sportelloDestinatario);
	request.setToken(token);
	RiferimentiPraticaType rifPraticatType = new RiferimentiPraticaType();
	rifPraticatType.setIdPratica(idPraticaMitt);
	if (altridati != null && !altridati.isEmpty()) {
	    rifPraticatType.getAltriDati().addAll(altridati);
	}
	request.setRifPratica(rifPraticatType);
	RichiestaPraticaResponse response = getStcWsPort().richiestaPratica(request);
	return response;
    }

    public NotificaAttivitaResponse notificaAttivita(SportelloType mittente, SportelloType destinatario, DettaglioAttivitaType datiAttivita,
	    String token, RiferimentiPraticaType riferimenti) throws Exception {

	NotificaAttivitaRequest request = new NotificaAttivitaRequest();
	request.setSportelloMittente(mittente);
	request.setSportelloDestinatario(destinatario);
	request.setRifPraticaDestinatario(riferimenti); // TODO copiare il giro fatto su inserimentoAttivitaNLA della PORT NLA
	request.setDatiAttivita(datiAttivita);
	request.setToken(token);
	try {
	    NotificaAttivitaResponse response = getStcWsPort().notificaAttivita(request);
	    return response;
	} catch (Exception e) {
	    log.error("notificaAttivita", e);
	    throw e;
	}
    }

    public AllegatoBinarioResponse allegatoBinario(SportelloType mittente, SportelloType destinatario,
	    RiferimentiAllegatoType riferimentiAllegatoType, String token) throws Exception {

	AllegatoBinarioRequest allegatoBinarioRequest = new AllegatoBinarioRequest();
	allegatoBinarioRequest.setSportelloDestinatario(destinatario);
	allegatoBinarioRequest.setSportelloMittente(mittente);
	allegatoBinarioRequest.setToken(token);
	allegatoBinarioRequest.setRiferimentiAllegato(riferimentiAllegatoType);
	try {
	    AllegatoBinarioResponse response = getStcWsPort().allegatoBinario(allegatoBinarioRequest);
	    return response;
	} catch (Exception e) {
	    log.error("allegatoBinario", e);
	    throw e;
	}
    }

    public String getMessaggioErrore(List<ErroreType> errors) {

	StringBuffer errorString = new StringBuffer();
	if (errors != null) {
	    for (ErroreType error : errors) {
		errorString.append("[errCod:").append(error.getNumeroErrore()).append(", errDesc:").append(error.getDescrizione()).append("] ");
	    }
	}
	return errorString.toString();
    }

    private Stc getStcWsPort() throws Exception {

	log.debug("getStcWsPort: url={}", stcWsUrl);
	StcService client = null;
	client = new StcService(new URL(this.stcWsUrl));
	MTOMFeature mtomFeature = new MTOMFeature(mtom, 0);
	Stc port = client.getStcSoap11(mtomFeature);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	// HTTPClientPolicy - Properties used to configure a client-side HTTP port
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1
	httpClientPolicy.setConnectionTimeout(this.timeout); // Line #2
	httpClientPolicy.setReceiveTimeout(this.timeout); // Line #3
	conduit.setClient(httpClientPolicy);
	return port;
    }

    public void setTimeout(long timeout) {

	this.timeout = timeout;
    }

    public String getStcWsUrl() {

	return stcWsUrl;
    }

    public void setStcWsUrl(String stcWsUrl) {

	this.stcWsUrl = stcWsUrl;
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

    public String getIdNodoDestinatario() {

	return idNodoDestinatario;
    }

    public void setIdNodoDestinatario(String idNodoDestinatario) {

	this.idNodoDestinatario = idNodoDestinatario;
    }

    public String getIdNodoMittente() {

	return idNodoMittente;
    }

    public void setIdNodoMittente(String idNodoMittente) {

	this.idNodoMittente = idNodoMittente;
    }

    public boolean isMtom() {

	return mtom;
    }

    public void setMtom(boolean mtom) {

	this.mtom = mtom;
    }
}
