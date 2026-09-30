package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.definitions.regole.Regole;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.ErroreBackofficeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.regole.ParametroRegolaRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.regole.ParametroRegolaResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.regole.ParametroType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.regole.RegolaRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.regole.RegolaResponse;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(serviceName = "RegoleWsService", portName = "RegoleWs", targetNamespace = "http://gruppoinit.it/sigepro/definitions/regole", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.regole.Regole")
public class RegoleWS extends BaseWS implements Regole {

    private static final Logger log = LoggerFactory.getLogger(RegoleWS.class);

    @Override
    @WebResult(name = "ParametroRegolaResponse", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/regole", partName = "ParametroRegolaResponse")
    @WebMethod(operationName = "GetParametroRegola", action = "getParametroRegola")
    public ParametroRegolaResponse getParametroRegola(
	    @WebParam(partName = "ParametroRegolaRequest", name = "ParametroRegolaRequest", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/regole") ParametroRegolaRequest getParametroRegolaRequest) {

	if (log.isDebugEnabled()) {
	    log.debug("getParametroRegola# entro nel metodo");
	}
	ParametroRegolaResponse result = new ParametroRegolaResponse();
	setConsoleORMHelper(getParametroRegolaRequest.getSoftware(), getParametroRegolaRequest.getToken());
	ErroreBackofficeType erroreBackofficeType = null;
	try {
	    String nomeRegola = getParametroRegolaRequest.getNomeRegola();
	    if (log.isDebugEnabled()) {
		log.debug("getRegola# verifico se attiva la regola: {}", nomeRegola);
	    }
	    Boolean isAttiva = verticalizzazioniService.isAttivaPerComuneESoftware(nomeRegola, getParametroRegolaRequest.getSoftware(),
		    getParametroRegolaRequest.getCodiceComune());
	    if (!isAttiva) {
		erroreBackofficeType = new ErroreBackofficeType();
		erroreBackofficeType.setCodice("1");
		erroreBackofficeType.setDescrizione("Regola non attiva");
		result.setErrore(erroreBackofficeType);
		return result;
	    }
	    Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    nomeRegola, getParametroRegolaRequest.getNomeParametro(), getParametroRegolaRequest.getCodiceComune(),
		    getParametroRegolaRequest.getSoftware());
	    if (verticalizzazioniparametri != null) {
		ParametroType parametroType = new ParametroType();
		parametroType.setDescrizione(verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro());
		parametroType.setValore(verticalizzazioniparametri.getValore());
		result.setParametro(parametroType);
	    } else {
		erroreBackofficeType = new ErroreBackofficeType();
		erroreBackofficeType.setCodice("2");
		erroreBackofficeType.setDescrizione("Parametro non configurato per la regola");
		result.setErrore(erroreBackofficeType);
		return result;
	    }
	} catch (Exception e) {
	    log.error("getParametroRegola# errore nel recupero della regola", e);
	    erroreBackofficeType = new ErroreBackofficeType();
	    erroreBackofficeType.setCodice("0");
	    erroreBackofficeType.setDescrizione("Errore generico :" + e.getMessage());
	    result.setErrore(erroreBackofficeType);
	    return result;
	}
	return result;
    }

    @Override
    @WebResult(name = "RegolaResponse", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/regole", partName = "RegolaResponse")
    @WebMethod(operationName = "GetRegola", action = "getRegola")
    public RegolaResponse getRegola(
	    @WebParam(partName = "RegolaRequest", name = "RegolaRequest", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/regole") RegolaRequest getRegolaRequest) {

	if (log.isDebugEnabled()) {
	    log.debug("getRegola# entro nel metodo");
	}
	RegolaResponse result = new RegolaResponse();
	setConsoleORMHelper(getRegolaRequest.getSoftware(), getRegolaRequest.getToken());
	try {
	    String nomeRegola = getRegolaRequest.getNomeRegola();
	    if (log.isDebugEnabled()) {
		log.debug("getRegola# verifico se attiva la regola: {}", nomeRegola);
	    }
	    Boolean isAttiva = verticalizzazioniService.isAttivaPerComuneESoftware(nomeRegola, getRegolaRequest.getSoftware(),
		    getRegolaRequest.getCodiceComune());
	    result.setAttiva(isAttiva);
	    if (isAttiva && getRegolaRequest.isRecuperaParametri()) {
		if (log.isDebugEnabled()) {
		    log.debug("getRegola# recupero la lista parametri per la regola: {}", nomeRegola);
		}
		List<Verticalizzazioniparametri> verticalizzazioniparametris = verticalizzazioniparametriService
			.findParametriConfiguratiByModuloAndComune(nomeRegola, getRegolaRequest.getCodiceComune());
		ParametroType parametroType = null;
		for (Verticalizzazioniparametri verticalizzazioniparametri : verticalizzazioniparametris) {
		    parametroType = new ParametroType();
		    parametroType.setDescrizione(verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro());
		    parametroType.setValore(verticalizzazioniparametri.getValore());
		    result.getListaParametri().add(parametroType);
		}
	    }
	} catch (Exception e) {
	    log.error("getParametroRegola# errore nel recupero della regola", e);
	}
	return result;
    }
}
