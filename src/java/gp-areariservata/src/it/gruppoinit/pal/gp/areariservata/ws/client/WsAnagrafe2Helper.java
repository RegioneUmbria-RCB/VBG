package it.gruppoinit.pal.gp.areariservata.ws.client;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.ws.client.Anagrafe2WsClient;
import it.gruppoinit.wsanagrafe2.ws.WsAnagrafe2Soap;

public class WsAnagrafe2Helper {

    private static Logger log = LoggerFactory.getLogger(WsAnagrafe2Helper.class);
    @Autowired
    private Anagrafe2WsClient anagrafe2WsClient;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    public WsAnagrafe2Soap getPersonaFisicaPortWS() {

	// Stringa di connessione al WS anagarfe, di default viene impostata quella del componente
	//di .net presente sulle SigeproMSConstants
	String webServiceUrl = BackofficeNETConstants.getURL_WS_ANAGRAFE();
	// Controlliamo se la verticalizzazione ANAGRAFE WS è attiva; nel caso lo sia controlliamo se è popolato 
	// il parametro in verticalizzaione  passato (parametro_url_verticalizzazione_anagrafe_ws).
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE)) {
	    // Recupero se presente il parametro in verticalizzazione
	    Verticalizzazioniparametri ws_anagarfe_url = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_WSANAGRAFE, WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_URL_RICERCA_PF, ORMHelper.getSoftware());
	    // Se il parametro è non vuoto allora sovrascrivo l'indirizzo al WS anagrafe con quello trovato 
	    //in verticalizzazione
	    if (ws_anagarfe_url != null && StringUtils.isNotBlank(ws_anagarfe_url.getValore())) {
		webServiceUrl = ws_anagarfe_url.getValore();
	    }
	}
	WsAnagrafe2Soap port = null;
	try {
	    port = anagrafe2WsClient.getAnagrafe2WsPort(webServiceUrl);
	} catch (Exception e) {
	    log.error("Servizio non funzionante o non disponibile: " + e.getMessage());
	    throw new RuntimeException("Servizio WS temporaneamente non disponibile: " + e.getMessage());
	}
	return port;
    }

    public WsAnagrafe2Soap getPersonaGiuridicaPortWS() {

	// Stringa di connessione al WS anagrafe, di default viene impostata quella del componente
	//di .net presente sulle SigeproMSConstants
	String webServiceUrl = BackofficeNETConstants.getURL_WS_ANAGRAFE();
	// Controlliamo se la verticalizzazione ANAGRAFE WS è attiva; nel caso lo sia controlliamo se è popolato 
	// il parametro in verticalizzaione  passato (parametro_url_verticalizzazione_anagrafe_ws).
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE)) {
	    // Recupero se presente il parametro in verticalizzazione
	    Verticalizzazioniparametri ws_anagarfe_url = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_WSANAGRAFE, WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_URL_RICERCA_PG, ORMHelper.getSoftware());
	    // Se il parametro è non vuoto allora sovrascrivo l'indirizzo al WS anagrafe con quello trovato 
	    //in verticalizzazione
	    if (ws_anagarfe_url != null && StringUtils.isNotBlank(ws_anagarfe_url.getValore())) {
		webServiceUrl = ws_anagarfe_url.getValore();
	    }
	}
	WsAnagrafe2Soap port = null;
	try {
	    port = anagrafe2WsClient.getAnagrafe2WsPort(webServiceUrl);
	} catch (Exception e) {
	    log.error("Servizio non funzionante o non disponibile: " + e.getMessage());
	    throw new RuntimeException("Servizio WS temporaneamente non disponibile: " + e.getMessage());
	}
	return port;
    }
}
