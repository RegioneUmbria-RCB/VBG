package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.nlaatti.schemas.messages.RicercaAttoRequest;
import it.gruppoinit.nlaatti.schemas.messages.RicercaAttoResponse;
import it.gruppoinit.nlaatti.schemas.messages.WsNlaAtti;
import it.gruppoinit.nlaatti.schemas.messages.WsNlaAtti_Service;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

import java.net.URL;

import javax.xml.ws.soap.MTOMFeature;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NlaAttiWsClient extends BaseWsClient {

    private static final Logger log = LoggerFactory.getLogger(NlaAttiWsClient.class);
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    public String ricercaAtto(Integer codicemovimento) {

	RicercaAttoRequest ricercaAttoRequest = new RicercaAttoRequest();
	ricercaAttoRequest.setCodice(codicemovimento);
	ricercaAttoRequest.setIdcomune(ORMHelper.getIdcomune());
	ricercaAttoRequest.setSoftware(ORMHelper.getSoftware());
	ricercaAttoRequest.setToken(ORMHelper.getToken());
	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		WebConstants.VERTICALIZZAZIONE_NLA_ATTI, WebConstants.VERTICALIZZAZIONE_NLA_ATTI_PARAMETRI_URL_NLA_ATTI_WS, ORMHelper.getIdcomune(),
		ORMHelper.getSoftware());
	//	String url = WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_MAILSERVICE);
	String url = "";
	if (verticalizzazioniparametri != null && StringUtils.isNotBlank(verticalizzazioniparametri.getValore())) {
	    url = verticalizzazioniparametri.getValore();
	} else {
	    throw new RuntimeException("Url ws nla atti non confugurata. Controllare il parametro "
		    + WebConstants.VERTICALIZZAZIONE_NLA_ATTI_PARAMETRI_URL_NLA_ATTI_WS + " la verticalizzazione NLA_ATTI");
	}
	log.debug("ricercaAtto: codicemovimento={}", new Object[] { codicemovimento });
	try {
	    WsNlaAtti port = getWsNlaAttiPort(url);
	    RicercaAttoResponse ricercaAttoResponse = port.ricercaAtto(ricercaAttoRequest);
	    if (ricercaAttoResponse.isErrore()) {
		log.error("Errore durante la ricerca atto : {}", ricercaAttoResponse.getMessage());
		throw new RuntimeException("Errore durante la ricerca atto : " + ricercaAttoResponse.getMessage());
	    }
	    log.debug("ricercaAtto: Error ={}", ricercaAttoResponse.isErrore());
	    return ricercaAttoResponse.getMessage();
	} catch (Exception e) {
	    log.error("Errore ricercaAtto: codicemovimento={}, Errore={}", new Object[] { codicemovimento, e });
	    throw new RuntimeException(e.getMessage());
	}
    }

    private WsNlaAtti getWsNlaAttiPort(String wsUrl) throws Exception {

	WsNlaAtti_Service client = new WsNlaAtti_Service(new URL(wsUrl));
	MTOMFeature mtomFeature = new MTOMFeature(false, 0);
	WsNlaAtti port = client.getWsNlaAttiSOAP(mtomFeature);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	// HTTPClientPolicy - Properties used to configure a client-side HTTP port  
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(12000); // Line #2  
	httpClientPolicy.setReceiveTimeout(360000); // Line #3  
	conduit.setClient(httpClientPolicy);
	return port;
    }
}
