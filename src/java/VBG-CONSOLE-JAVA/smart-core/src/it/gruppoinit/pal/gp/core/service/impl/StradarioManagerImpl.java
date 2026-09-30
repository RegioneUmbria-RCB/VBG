package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.StradarioManager;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.utils.BaseEnvironment;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListRequest;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListResponse;
import it.gruppoinit.sigeprosecurity.schema.SecurityListType;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StradarioManagerImpl extends BaseEnvironment implements StradarioManager {

    private static final Logger log = LoggerFactory.getLogger(StradarioManagerImpl.class);
    private SecurityWSClient securityWSClient;
    private ComuniassociatiService comuniassociatiService;
    private StradarioService stradarioService;

    @Autowired
    public void setSecurityWSClient(SecurityWSClient securityWSClient) {

	this.securityWSClient = securityWSClient;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setStradarioService(StradarioService stradarioService) {

	this.stradarioService = stradarioService;
    }

    @Override
    synchronized public void allineaStradario() {

	try {
	    GetSecurityListResponse getSecurityListResponse = securityWSClient.getWsPort().getSecurityList(new GetSecurityListRequest());
	    List<SecurityListType> securityListTypes = getSecurityListResponse.getSecurity();
	    for (SecurityListType securityListType : securityListTypes) {
		try {
		    String alias = securityListType.getAlias();
		    setORMHelper(alias);
		    Verticalizzazioniparametri urlWs = verticalizzazioniService.getVerticalizzazioniparametri(
			    WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO, WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO_URL_WSSIT);
		    if (urlWs != null) {
			log.debug("allineaStradario# Allineamento stradario per l'installazione {}", ORMHelper.getIdcomuneAlias());
			List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
			List<String> codiciComuni = new ArrayList<String>();
			for (Comuniassociati comuniassociati : comuniassociatis) {
			    codiciComuni.add(comuniassociati.getComune().getCodicecomune());
			}
			stradarioService.updateAllineaStradario(codiciComuni, null);
		    } else {
			log.debug("allineaStradario# Sit non attivo per l'installazione {}", ORMHelper.getIdcomuneAlias());
		    }
		} catch (Exception e) {
		    log.error("allineaStradario# Errore durante l'allineamento dello stradario per l'installazione {}", ORMHelper.getIdcomuneAlias(),
			    e);
		} finally {
		    resetThreadLocalVars();
		}
	    }
	    log.info("allineaStradario# termine processo di allineamento stradario");
	} catch (Exception e) {
	    log.error("allineaStradario# Errore durante il recupero della lista delle installazioni", e);
	}
    }
}
