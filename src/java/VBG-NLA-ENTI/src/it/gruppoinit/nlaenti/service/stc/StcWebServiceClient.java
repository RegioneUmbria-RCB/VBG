package it.gruppoinit.nlaenti.service.stc;

import it.init.sigepro.rte.AllegatoBinarioRequest;
import it.init.sigepro.rte.AllegatoBinarioResponse;
import it.init.sigepro.rte.CheckTokenRequest;
import it.init.sigepro.rte.CheckTokenResponse;
import it.init.sigepro.rte.LoginRequest;
import it.init.sigepro.rte.LoginResponse;
import it.init.sigepro.rte.RichiestaPraticaRequest;
import it.init.sigepro.rte.RichiestaPraticaResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;

public class StcWebServiceClient {

    private static final Logger log = LoggerFactory.getLogger(StcWebServiceClient.class);
    private String stcWsUrl;
    private String nlaUserId;
    private String nlaPassword;
    private WebServiceTemplate webServiceTemplate;

    public String login() throws Exception {

	log.debug("login()...");
	String token = "";
	LoginRequest request = new LoginRequest();
	request.setUsername(getNlaUserId());
	request.setPassword(getNlaPassword());
	LoginResponse response = (LoginResponse) webServiceTemplate.marshalSendAndReceive(getStcWsUrl(), request);
	if (response.isResult()) {
	    token = response.getToken();
	}
	if (StringUtils.isBlank(token)) {
	    log.error("login(): token nullo");
	    throw new Exception("Errore durante la login a STC: token nullo");
	}
	log.debug("login()...done! token={}", token);
	return token;
    }

    public boolean checkToken(String token) {

	CheckTokenRequest request = new CheckTokenRequest();
	request.setToken(token);
	CheckTokenResponse response = (CheckTokenResponse) webServiceTemplate.marshalSendAndReceive(getStcWsUrl(), request);
	return response.isResult();
    }

    public RichiestaPraticaResponse richiestaPratica(RichiestaPraticaRequest request) {

	RichiestaPraticaResponse response = (RichiestaPraticaResponse) webServiceTemplate.marshalSendAndReceive(getStcWsUrl(), request);
	return response;
    }

    public AllegatoBinarioResponse allegatoBinario(AllegatoBinarioRequest request) {

	AllegatoBinarioResponse response = (AllegatoBinarioResponse) webServiceTemplate.marshalSendAndReceive(getStcWsUrl(), request);
	return response;
    }

    public String getStcWsUrl() {

	return stcWsUrl;
    }

    public void setStcWsUrl(String stcWsUrl) {

	this.stcWsUrl = stcWsUrl;
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
}
