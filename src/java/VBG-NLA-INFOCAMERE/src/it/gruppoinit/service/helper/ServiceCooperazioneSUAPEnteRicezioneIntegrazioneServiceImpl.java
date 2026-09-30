package it.gruppoinit.service.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import it.gov.impresainungiorno.schema.suap.ente.AllegatoCooperazione;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;
import it.gov.impresainungiorno.schema.suap.pratica.AdempimentoSUAP;
import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.gruppoinit.dao.QuerybackofficeDAO;
import it.gruppoinit.domain.helper.ModuloHelper;
import it.gruppoinit.domain.helper.ParametriHelper;
import it.gruppoinit.domain.nla.Allegato;
import it.gruppoinit.service.DeployProperties;
import it.gruppoinit.service.DettaglioPraticaAndAttivitaTypeHelperService;
import it.gruppoinit.service.GestioneAllegatoPraticaService;
import it.gruppoinit.service.InfocamereHelperService;
import it.gruppoinit.utilities.Utilities;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.definitions.StcWSClient;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.SportelloType;

public class ServiceCooperazioneSUAPEnteRicezioneIntegrazioneServiceImpl<String> extends ServiceCooperazioneSUAPEnteService<String> {

    private static final Logger log = LoggerFactory.getLogger(ServiceCooperazioneSUAPEnteRicezioneIntegrazioneServiceImpl.class);
    private DettaglioPraticaType dettaglioPraticaType;

    public ServiceCooperazioneSUAPEnteRicezioneIntegrazioneServiceImpl(File fileComunicazione, DeployProperties deployProperties,
	    StcWSClient stcWSClient, GestioneAllegatoPraticaService gestioneAllegatoPraticaService, InfocamereHelperService infocamereHelperService,
	    ParametriHelper parametriHelper, DettaglioPraticaAndAttivitaTypeHelperService dettaglioPraticaTypeHelperService,
	    DettaglioPraticaType dettaglioPraticaType) {

	super(fileComunicazione, deployProperties, stcWSClient, gestioneAllegatoPraticaService, infocamereHelperService, parametriHelper,
		dettaglioPraticaTypeHelperService);
	this.dettaglioPraticaType = dettaglioPraticaType;
    }

    @Override
    protected String buildAndSendMessage(CooperazioneSUAPEnte cooperazioneSUAPEnte, RiepilogoPraticaSUAP rpsuap, File fileComunicazione,
	    File rpsuap_file, SportelloType sportelloTypeBackoffice, SportelloType sportelloNodo, List<Allegato> allegati,
	    ParametriHelper parametriHelper, DettaglioPraticaAndAttivitaTypeHelperService dettaglioPraticaTypeHelperService, StcWSClient stcWSClient)
	    throws Exception {

	log.debug("buildAndSendMessage# Richiesta pratica. Codice pratica = {}", cooperazioneSUAPEnte.getIntestazione().getCodicePratica());
	DettaglioAttivitaType datiAttivita = dettaglioPraticaTypeHelperService.getDettaglioAttivitaType(cooperazioneSUAPEnte, rpsuap_file, allegati,
		parametriHelper);
	DocumentiType dtComunicazione = populateDocumentoComunicazione(fileComunicazione, rpsuap.getIntestazione().getCodicePratica(), false);
	if (dtComunicazione != null) {
	    datiAttivita.getDocumenti().add(dtComunicazione);
	}
	List<AllegatoCooperazione> allegato = cooperazioneSUAPEnte.getAllegato();
	// cooperazioneSUAPEnte o da fileComunicazione estrai lista codici
	List<String> codici = popolaListaCodici(rpsuap);
	QuerybackofficeDAO dao = (QuerybackofficeDAO) ContextLoader.getCurrentWebApplicationContext().getBean("querybackofficeDAOImpl",
		QuerybackofficeDAO.class);
	List<ModuloHelper> codiciList = dao.findCodici(sportelloTypeBackoffice.getIdEnte(), (List<java.lang.String>) codici);
	//for con codiceinventario
	NotificaAttivitaResponse notificaAttivitaResponse = new NotificaAttivitaResponse();
	RiferimentiPraticaType rp = new RiferimentiPraticaType();
	rp.setIdPratica(dettaglioPraticaType.getIdPratica());
	for (ModuloHelper mh : codiciList) {
	    datiAttivita.getProcedimenti().clear();
	    ProcedimentoType pt = new ProcedimentoType();
	    pt.setCodice(mh.getCodProcPeople());
	    pt.setDescrizione(mh.getProcedimento());
	    pt.setPrincipale(false);
	    datiAttivita.getProcedimenti().add(pt);
	    notificaAttivitaResponse = stcWSClient.notificaAttivita(sportelloNodo, sportelloTypeBackoffice, datiAttivita,
		    parametriHelper.getTokenStc(), rp);
	}
	StringBuffer b = new StringBuffer();
	if (!notificaAttivitaResponse.getDettaglioErrore().isEmpty()) {
	    b.append("KO: ").append((String) notificaAttivitaResponse.getDettaglioErrore().get(0).getDescrizione());
	} else {
	    b.append("OK: L'integrazione è stata creata correttamente. Codice attività:")
		    .append((String) notificaAttivitaResponse.getDettaglioattivita().getIdAttivita());
	}
	try {
	    Utilities.gracefullyDeleteFiles(rpsuap_file);
	} catch (Exception e) {
	    log.error("buildAndSendMessage# Errore durante la cancellazione del file temporaneo Riepilogo pratica. Errore = {}", e);
	}
	return (String) b.toString();
    }

    @SuppressWarnings("unchecked")
    private List<String> popolaListaCodici(RiepilogoPraticaSUAP rpsuap) {

	if (rpsuap == null) {
	    throw new RuntimeException("Il riepilogo pratica non può essere nullo.");
	}
	List<String> codici = new ArrayList<String>();
	if (rpsuap.getStruttura() != null && rpsuap.getStruttura().getModulo() != null) {
	    for (AdempimentoSUAP as : rpsuap.getStruttura().getModulo()) {
		codici.add((String) as.getCod());
	    }
	}
	return codici;
    }
}
