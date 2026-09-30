package it.gruppoinit.service.helper;

import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;
import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.gruppoinit.domain.helper.ParametriHelper;
import it.gruppoinit.domain.nla.Allegato;
import it.gruppoinit.service.DeployProperties;
import it.gruppoinit.service.DettaglioPraticaAndAttivitaTypeHelperService;
import it.gruppoinit.service.GestioneAllegatoPraticaService;
import it.gruppoinit.service.InfocamereHelperService;
import it.gruppoinit.utilities.Utilities;
import it.init.sigepro.rte.definitions.StcWSClient;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.SportelloType;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceCooperazioneSUAPEnteRicezionePraticaServiceImpl<String> extends ServiceCooperazioneSUAPEnteService<String> {

    private static final Logger log = LoggerFactory.getLogger(ServiceCooperazioneSUAPEnteRicezionePraticaServiceImpl.class);

    public ServiceCooperazioneSUAPEnteRicezionePraticaServiceImpl(File fileComunicazione, DeployProperties deployProperties, StcWSClient stcWSClient,
	    GestioneAllegatoPraticaService gestioneAllegatoPraticaService, InfocamereHelperService infocamereHelperService,
	    ParametriHelper parametriHelper, DettaglioPraticaAndAttivitaTypeHelperService dettaglioPraticaTypeHelperService) {

	super(fileComunicazione, deployProperties, stcWSClient, gestioneAllegatoPraticaService, infocamereHelperService, parametriHelper,
		dettaglioPraticaTypeHelperService);
    }

    @Override
    protected String buildAndSendMessage(CooperazioneSUAPEnte cooperazioneSUAPEnte, RiepilogoPraticaSUAP rpsuap, File fileComunicazione,
	    File rpsuap_file, SportelloType sportelloTypeBackoffice, SportelloType sportelloNodo, List<Allegato> allegati,
	    ParametriHelper parametriHelper, DettaglioPraticaAndAttivitaTypeHelperService dettaglioPraticaTypeHelperService, StcWSClient stcWSClient)
	    throws Exception {

	log.debug("buildAndSendMessage# Creo la pratica....");
	DettaglioPraticaType dettaglioPraticaType = dettaglioPraticaTypeHelperService.getDettaglioPraticaType(cooperazioneSUAPEnte, rpsuap, allegati,
		parametriHelper, rpsuap_file);
	DocumentiType dtComunicazione = populateDocumentoComunicazione(fileComunicazione, rpsuap.getIntestazione().getCodicePratica(), true);
	if (dtComunicazione != null) {
	    dettaglioPraticaType.getDocumenti().add(dtComunicazione);
	}
	log.debug("buildAndSendMessage# Invio pratica al backoffice...");
	it.init.sigepro.rte.InserimentoPraticaResponse inserimentoPraticaResponse = stcWSClient.inserisciPratica(sportelloNodo,
		sportelloTypeBackoffice, parametriHelper.getTokenStc(), dettaglioPraticaType);
	StringBuffer b = new StringBuffer();
	if (!inserimentoPraticaResponse.getDettaglioErrore().isEmpty()) {
	    b.append("KO: ").append((String) inserimentoPraticaResponse.getDettaglioErrore().get(0).getDescrizione());
	} else {
	    b.append("OK: Numero pratica creata nel backoffice:").append((String) inserimentoPraticaResponse.getDettaglioPratica().getNumeroPratica());
	}
	try {
	    Utilities.gracefullyDeleteFiles(rpsuap_file);
	} catch (Exception e) {
	    log.error("buildAndSendMessage# Errore durante la cancellazione del file temporaneo Riepilogo pratica. Errore = {}", e);
	}
	return (String) b.toString();
    }
}
