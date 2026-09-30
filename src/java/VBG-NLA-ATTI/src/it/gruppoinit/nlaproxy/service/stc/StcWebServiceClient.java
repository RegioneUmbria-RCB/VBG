package it.gruppoinit.nlaproxy.service.stc;

import it.init.sigepro.rte.CheckTokenRequest;
import it.init.sigepro.rte.CheckTokenResponse;
import it.init.sigepro.rte.InserimentoPraticaRequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.LoginRequest;
import it.init.sigepro.rte.LoginResponse;
import it.init.sigepro.rte.RichiestaPraticaCollegataRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;
import it.init.sigepro.rte.RichiestaPraticaRequest;
import it.init.sigepro.rte.RichiestaPraticaResponse;
import it.init.sigepro.rte.definitions.Stc;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.SportelloType;

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
import org.springframework.stereotype.Component;

@Component
public class StcWebServiceClient {

    private static final Logger log = LoggerFactory.getLogger(StcWebServiceClient.class);
    private String stcWsUrl;
    private String idNodo;
    private String nlaUserId;
    private String nlaPassword;
    private boolean mtom;
    private long timeout = 120000;

    private Stc getStcWsPort() throws Exception {

	log.debug("getStcWsPort: url={}", stcWsUrl);
	StcService client = new StcService(new URL(this.stcWsUrl));
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

    public InserimentoPraticaResponse inserisciPratica(String token, InserimentoPraticaRequest ipr, boolean sendToSportelloDestinatarioEdilizia)
	    throws Exception {

	// DOVREBBE TORNARE I DETTAGLI DELLA PRATICA MITTENTE
	log.debug("inserisciPratica()...");
	DettaglioPraticaType pratica = ipr.getDettaglioPratica();
	InserimentoPraticaResponse response = new InserimentoPraticaResponse();
	RiferimentiPraticaType value = new RiferimentiPraticaType();
	value.setDataPratica(pratica.getDataPratica());
	value.setIdPratica(pratica.getIdPratica());
	value.setNumeroPratica(pratica.getNumeroPratica());
	value.setDataProtocolloGenerale(pratica.getDataProtocolloGenerale());
	value.setNumeroProtocolloGenerale(value.getNumeroProtocolloGenerale());
	response.setDettaglioPratica(value);
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

    public RichiestaPraticaResponse richiestaPratica(RichiestaPraticaRequest request) throws Exception {

	RichiestaPraticaResponse response = getStcWsPort().richiestaPratica(request);
	return response;
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

    public void setTimeout(long timeout) {

	this.timeout = timeout;
    }

    public String getStcWsUrl() {

	return stcWsUrl;
    }

    public void setStcWsUrl(String stcWsUrl) {

	this.stcWsUrl = stcWsUrl;
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

    public boolean isMtom() {

	return mtom;
    }

    public void setMtom(boolean mtom) {

	this.mtom = mtom;
    }

    public void setIdNodo(String idNodo) {

	this.idNodo = idNodo;
    }
}
