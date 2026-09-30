package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.dossier.messages.RiversaIstanzeDossier;
import it.gruppoinit.dossier.messages.RiversaIstanzeRequest;
import it.gruppoinit.dossier.messages.RiversaIstanzeResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.DossierService;
import it.gruppoinit.pal.gp.core.ws.client.DossierWsClient;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DossierServiceImpl implements DossierService {

    private static final Logger log = LoggerFactory.getLogger(DossierWsClient.class);
    private DossierWsClient dossierWsClient;
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setDossierWsClient(DossierWsClient dossierWsClient) {

	this.dossierWsClient = dossierWsClient;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    public RiversaIstanzeResponse uploadIstanzeInDossier(RiversaIstanzeRequest riversaIstanzeRequest) {

	String urlws = getUrlWs();
	riversaIstanzeRequest.setToken(ORMHelper.getToken());
	RiversaIstanzeResponse riversaIstanzeResponse = null;
	try {
	    RiversaIstanzeDossier riversaIstanzeDossier = dossierWsClient.getWsPort(urlws);
	    riversaIstanzeResponse = riversaIstanzeDossier.riversaIstanze(riversaIstanzeRequest);
	} catch (InvalidConfigurationException e) {
	    log.error("Non è stato possibile completare l'operazione a causa dell' errore : {}[{}]", new Object[] { e.getMessage(), e });
	    throw new RuntimeException("Non è stato possibile completare l'operazione a causa dell' errore :" + e.getMessage() + "[" + e + "]");
	} catch (Exception e) {
	    log.error("Non è stato possibile completare l'operazione a causa dell' errore : {}[{}]", new Object[] { e.getMessage(), e });
	    throw new RuntimeException("Non è stato possibile completare l'operazione a causa dell' errore :" + e.getMessage() + "[" + e + "]");
	}
	return riversaIstanzeResponse;
    }

    private String getUrlWs() {

	boolean isAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_DOSSIER);
	String urlws = "";
	if (isAttiva) {
	    Verticalizzazioniparametri urlNodoDossier = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_DOSSIER, WebConstants.VERTICALIZZAZIONE_DOSSIER_URL_NODO_DOSSIER);
	    if (urlNodoDossier != null) {
		if (StringUtils.isNotBlank(urlNodoDossier.getValore())) {
		    //if (urlNodoDossier.getValore().equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_DOSSIER_URL_NODO_DOSSIER)) {
		    urlws = urlNodoDossier.getValore();
		    // }
		}
	    }
	} else {
	    throw new RuntimeException("Attenzione l'operazione non può essere completata, la verticalizzazione : "
		    + WebConstants.VERTICALIZZAZIONE_DOSSIER + " non è attiva");
	}
	if (StringUtils.isBlank(urlws)) {
	    throw new RuntimeException("Attenzione l'operazione non può essere completata, parametro : "
		    + WebConstants.VERTICALIZZAZIONE_DOSSIER_URL_NODO_DOSSIER + " non presente");
	}
	return urlws;
    }
}
