package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.definitions.eventi.Eventi;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.ErroreBackofficeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.eventi.EventoBackofficeInsertRequest;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
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
	Software software = softwareService.findById(eventoBackofficeInsertRequest.getSoftware());
	if (software == null) {
	    software = softwareService.findById(WebConstants.SOFTWARE_TT);
	}
	try {
	    istanzeeventiService.insertEventoBackoffice(eventoBackofficeInsertRequest.getMessaggio(), eventoBackofficeInsertRequest
		    .getCategoriaEvento().value(), software);
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

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }
}
