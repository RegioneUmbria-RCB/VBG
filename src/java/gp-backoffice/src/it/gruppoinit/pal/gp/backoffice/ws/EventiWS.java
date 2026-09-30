package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.definitions.eventi.Eventi;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.ErroreBackofficeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.eventi.EventoBackofficeInsertRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.eventi.EventoIstanzaInsertRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.eventi.EventoMovimentoInsertRequest;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@WebService(serviceName = "EventiWsService", portName = "EventiSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/eventi", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.eventi.Eventi")
public class EventiWS extends BaseWS implements Eventi {

    private static final Logger log = LoggerFactory.getLogger(EventiWS.class);
    private IstanzeeventiService istanzeeventiService;
    private IstanzeService istanzeService;
    private MovimentiService movimentiService;
    private SoftwareService softwareService;

    @Override
    @WebResult(name = "EventoInsertResponse", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/eventi", partName = "EventoInsertResponse")
    @WebMethod(operationName = "EventoBackofficeInsert", action = "EventoBackofficeInsert")
    public EsitoOperazioneType eventoBackofficeInsert(
	    @WebParam(partName = "EventoBackofficeInsertRequest", name = "EventoBackofficeInsertRequest", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/eventi") EventoBackofficeInsertRequest eventoBackofficeInsertRequest) {

	if (log.isDebugEnabled()) {
	    log.debug("eventoBackofficeInsert# entro nel metodo");
	}
	EsitoOperazioneType result = new EsitoOperazioneType();
	result.setEsito(0);
	setORMHelper(eventoBackofficeInsertRequest.getSoftware(), eventoBackofficeInsertRequest.getToken());
	String s = WebConstants.SOFTWARE_TT;
	Software software = softwareService.findById(eventoBackofficeInsertRequest.getSoftware());
	if (software != null) {
	    s = eventoBackofficeInsertRequest.getSoftware();
	}
	try {
	    istanzeeventiService.insertEventoBackoffice(eventoBackofficeInsertRequest.getMessaggio(), eventoBackofficeInsertRequest
		    .getCategoriaEvento().value(), s);
	} catch (Exception e) {
	    log.error("eventoBackofficeInsert# errore in inserimento evento {}", e);
	    result.setEsito(500);
	    ErroreBackofficeType ex = new ErroreBackofficeType();
	    ex.setCodice("ISTANZEEVENTISERVICE#eventoBackofficeInsert#500");
	    ex.setDescrizione("Errore in inserimento evento backoffice: " + e.getMessage());
	    result.getListaErrori().add(ex);
	}
	return result;
    }

    @Override
    @WebResult(name = "EventoInsertResponse", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/eventi", partName = "EventoInsertResponse")
    @WebMethod(operationName = "EventoIstanzaInsert", action = "EventoIstanzaInser")
    public EsitoOperazioneType eventoIstanzaInsert(
	    @WebParam(partName = "EventoIstanzaInsertRequest", name = "EventoIstanzaInsertRequest", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/eventi") EventoIstanzaInsertRequest eventoIstanzaInsertRequest) {

	if (log.isDebugEnabled()) {
	    log.debug("eventoIstanzaInsert# entro nel metodo");
	}
	EsitoOperazioneType result = new EsitoOperazioneType();
	result.setEsito(0);
	setORMHelper(null, eventoIstanzaInsertRequest.getToken());
	try {
	    Istanze istanza = istanzeService.findById(new PkId(eventoIstanzaInsertRequest.getCodiceistanza().intValue()));
	    if (istanza != null) {
		ORMHelper.setSoftware(istanza.getSoftware().getCodice());
		istanzeeventiService.insert(eventoIstanzaInsertRequest.getMessaggio(), eventoIstanzaInsertRequest.getCategoriaEvento().value(), null,
			istanza);
	    } else {
		result.setEsito(404);
		ErroreBackofficeType ex = new ErroreBackofficeType();
		ex.setCodice("ISTANZEEVENTISERVICE#eventoIstanzaInsert#404");
		ex.setDescrizione("Errore in inserimento evento backoffice: non è stata trovata la pratica con codice ["
			+ eventoIstanzaInsertRequest.getCodiceistanza().intValue() + "-" + ORMHelper.getIdcomuneAlias() + "]");
		result.getListaErrori().add(ex);
	    }
	} catch (Exception e) {
	    log.error("eventoIstanzaInsert# errore in inserimento evento {}", e);
	    result.setEsito(500);
	    ErroreBackofficeType ex = new ErroreBackofficeType();
	    ex.setCodice("ISTANZEEVENTISERVICE#eventoIstanzaInsert#500");
	    ex.setDescrizione("Errore in inserimento evento istanza: " + e.getMessage());
	    result.getListaErrori().add(ex);
	}
	return result;
    }

    @Override
    @WebResult(name = "EventoInsertResponse", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/eventi", partName = "EventoInsertResponse")
    @WebMethod(operationName = "EventoMovimentoInsert", action = "EventoMovimentoInsert")
    public EsitoOperazioneType eventoMovimentoInsert(
	    @WebParam(partName = "EventoMovimentoInsertRequest", name = "EventoMovimentoInsertRequest", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/eventi") EventoMovimentoInsertRequest eventoMovimentoInsertRequest) {

	if (log.isDebugEnabled()) {
	    log.debug("eventoMovimentoInsert# entro nel metodo");
	}
	EsitoOperazioneType result = new EsitoOperazioneType();
	result.setEsito(0);
	setORMHelper(WebConstants.SOFTWARE_TT, eventoMovimentoInsertRequest.getToken());
	try {
	    Movimenti movimento = movimentiService.findById(new PkId(eventoMovimentoInsertRequest.getCodicemovimento().intValue()));
	    if (movimento != null) {
		Integer codiceistanza = movimento.getIstanza().getId().getCodice();
		Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
		ORMHelper.setSoftware(istanza.getSoftware().getCodice());
		istanzeeventiService.insert(eventoMovimentoInsertRequest.getMessaggio(), eventoMovimentoInsertRequest.getCategoriaEvento().value(),
			movimento, null);
	    } else {
		result.setEsito(404);
		ErroreBackofficeType ex = new ErroreBackofficeType();
		ex.setCodice("ISTANZEEVENTISERVICE#eventoMovimentoInsert#404");
		ex.setDescrizione("Errore in inserimento evento backoffice: non è stato trovato il movimento con codice ["
			+ eventoMovimentoInsertRequest.getCodicemovimento().intValue() + "-" + ORMHelper.getIdcomuneAlias() + "]");
		result.getListaErrori().add(ex);
	    }
	} catch (Exception e) {
	    log.error("eventoMovimentoInsert# errore in inserimento evento {}", e);
	    result.setEsito(500);
	    ErroreBackofficeType ex = new ErroreBackofficeType();
	    ex.setCodice("ISTANZEEVENTISERVICE#eventoMovimentoInsert#500");
	    ex.setDescrizione("Errore in inserimento evento movimento: " + e.getMessage());
	    result.getListaErrori().add(ex);
	}
	return result;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }
}
