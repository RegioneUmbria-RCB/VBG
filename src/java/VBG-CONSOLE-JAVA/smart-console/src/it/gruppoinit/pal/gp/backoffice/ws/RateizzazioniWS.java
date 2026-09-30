/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.definitions.rateizzazioni.Rateizzazioni;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.rateizzazioni.ImportoRateizzatoXML;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.rateizzazioni.RateizzazioniRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.rateizzazioni.RateizzazioniResponse;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.ImportiRateizzati;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.RateizzazioniHelper;
import it.gruppoinit.pal.gp.core.service.InteressiLegaliService;
import it.gruppoinit.pal.gp.core.service.OneritipirateizzazioneService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Web Service per il calcolo delle rateizzazioni.
 * 
 * @author francescop
 * 
 */
@WebService(serviceName = "RateizzazioniService", portName = "RateizzazioniSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/rateizzazioni", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.rateizzazioni.Rateizzazioni")
public class RateizzazioniWS extends BaseWS implements Rateizzazioni {

    private static final Logger log = LoggerFactory.getLogger(RateizzazioniWS.class);
    private OneritipirateizzazioneService oneritipirateizzazioneService;
    private InteressiLegaliService interessiLegaliService;

    @Autowired
    public void setInteressiLegaliService(InteressiLegaliService interessiLegaliService) {

	this.interessiLegaliService = interessiLegaliService;
    }

    @Autowired
    public void setOneritipirateizzazioneService(OneritipirateizzazioneService oneritipirateizzazioneService) {

	this.oneritipirateizzazioneService = oneritipirateizzazioneService;
    }

    @Override
    public RateizzazioniResponse rateizzazioni(RateizzazioniRequest request) {

	Date data = request.getData().toGregorianCalendar().getTime();
	Date dataInizio = null;
	if (request.getDataInizio() != null) {
	    dataInizio = request.getDataInizio().toGregorianCalendar().getTime();
	}
	if (log.isDebugEnabled()) {
	    log.debug("WS getRateizzazioni received a MessageRequest object with token=" + request.getToken() + " and codiceOneriTipiRateizzazione="
		    + request.getCodiceOneriTipiRateizzazione() + " and data=" + data + " and importo=" + request.getImporto());
	}
	setORMHelper(null, request.getToken());
	try {
	    Oneritipirateizzazione oneritipirateizzazione = oneritipirateizzazioneService
		    .findById(new PkId(request.getCodiceOneriTipiRateizzazione()));
	    RateizzazioniResponse response = new RateizzazioniResponse();
	    if (oneritipirateizzazione != null) {
		if (oneritipirateizzazione.getFlagInteressiLegali()) {
		    if (dataInizio == null) {
			throw new RuntimeException(
				"ERRORE WEB SERVICE METHOD getRateizzazioni: Data inizio &egrave; nulla per un tipo di rateizzazione che ha il flag Interessi Legali settato");
		    } else {
			if (dataInizio.compareTo(data) > 0) {
			    throw new RuntimeException(
				    "ERRORE WEB SERVICE METHOD getRateizzazioni: La data iniziale per gli Interessi legali &egrave; successiva alla data finale.");
			}
		    }
		}
		Software software = oneritipirateizzazione.getSoftware();
		ORMHelper.setSoftware(software.getCodice());
		try {
		    RateizzazioniHelper rateizzazioniHelper = new RateizzazioniHelper(oneritipirateizzazione, interessiLegaliService);
		    List<ImportiRateizzati> list = rateizzazioniHelper.rateizzaImporto(request.getImporto(), data, dataInizio);
		    for (ImportiRateizzati importiRateizzati : list) {
			ImportoRateizzatoXML imp = new ImportoRateizzatoXML();
			imp.setImportoRateizzato(importiRateizzati.getImportoRateizzato());
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(importiRateizzati.getScadenza());
			XMLGregorianCalendar calendar = Utilities.getXMLGregorianCalendar(gregorianCalendar);
			imp.setScadenza(calendar);
			response.getImportoRateizzatoXML().add(imp);
		    }
		    if (log.isDebugEnabled()) {
			StringBuffer buffer = new StringBuffer();
			for (ImportoRateizzatoXML importoRateizzatoXML : response.getImportoRateizzatoXML()) {
			    buffer.append(" importo=" + importoRateizzatoXML.getImportoRateizzato() + " scadenza="
				    + importoRateizzatoXML.getScadenza());
			}
			log.debug("WS getRateizzazioni response is: " + buffer);
		    }
		} catch (Exception e) {
		    log.error("getRateizzazioni(): {}", e.getMessage());
		    throw new RuntimeException("ERRORE WEB SERVICE METHOD getRateizzazioni: " + e.getMessage());
		}
	    } else {
		// FIXME deve tornare errore o un xml vuoto?
		ImportoRateizzatoXML imp = new ImportoRateizzatoXML();
		response.getImportoRateizzatoXML().add(imp);
	    }
	    return response;
	} finally {
	    resetThreadLocalVars();
	}
    }
}
