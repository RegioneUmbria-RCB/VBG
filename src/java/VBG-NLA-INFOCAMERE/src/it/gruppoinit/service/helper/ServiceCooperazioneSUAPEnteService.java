package it.gruppoinit.service.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AllegatoBinarioType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.SportelloType;

public abstract class ServiceCooperazioneSUAPEnteService<E> {

    private static final Logger log = LoggerFactory.getLogger(ServiceCooperazioneSUAPEnteService.class);
    private DeployProperties deployProperties;
    private StcWSClient stcWSClient;
    private GestioneAllegatoPraticaService gestioneAllegatoPraticaService;
    private InfocamereHelperService infocamereHelperService;
    private ParametriHelper parametriHelper;
    private DettaglioPraticaAndAttivitaTypeHelperService dettaglioPraticaTypeHelperService;
    private File fileComunicazione;

    public ServiceCooperazioneSUAPEnteService(File fileComunicazione, DeployProperties deployProperties, StcWSClient stcWSClient,
	    GestioneAllegatoPraticaService gestioneAllegatoPraticaService, InfocamereHelperService infocamereHelperService,
	    ParametriHelper parametriHelper, DettaglioPraticaAndAttivitaTypeHelperService dettaglioPraticaTypeHelperService) {

	this.deployProperties = deployProperties;
	this.stcWSClient = stcWSClient;
	this.gestioneAllegatoPraticaService = gestioneAllegatoPraticaService;
	this.infocamereHelperService = infocamereHelperService;
	this.dettaglioPraticaTypeHelperService = dettaglioPraticaTypeHelperService;
	this.parametriHelper = parametriHelper;
	this.fileComunicazione = fileComunicazione;
    }

    public E sendMessage(CooperazioneSUAPEnte cooperazioneSUAPEnte) throws Exception {

	SportelloType sportellobackoffice = getSportelloBackoffice(parametriHelper.getEnteBackoffice(), parametriHelper.getSportelloBackoffice());
	log.debug("buildAndSendMessage# Sportello backoffice. IdEnte = {}, IdNodo = {}, IdSportello = {} ", sportellobackoffice.getIdEnte(),
		sportellobackoffice.getIdNodo(), sportellobackoffice.getIdSportello());
	SportelloType sportelloNodo = getSportelloInfocamere();
	log.debug("buildAndSendMessage# Sportello nodo Infocamer. IdEnte = {}, IdNodo = {}, IdSportello = {} ", sportelloNodo.getIdEnte(),
		sportelloNodo.getIdNodo(), sportelloNodo.getIdSportello());
	log.debug("buildAndSendMessage# Carico tutti gli allegati presenti nella pratica...");
	//	List<AllegatoCooperazione> _listAllegati = new ArrayList<AllegatoCooperazione>();
	//	_listAllegati = cooperazioneSUAPEnte.getAllegato();
	String intestazione = cooperazioneSUAPEnte.getIntestazione().getCodicePratica();
	List<Allegato> listAllegati = new ArrayList<Allegato>();
	listAllegati = gestioneAllegatoPraticaService.loadFileS(cooperazioneSUAPEnte.getAllegato());
	Allegato a = gestioneAllegatoPraticaService.findFileSUAP_XML_From_Allegati(listAllegati, intestazione);
	if (a == null) {
	    log.error("buildAndSendMessage#Non è stato trovato il file = {}", intestazione);
	    throw new RuntimeException("Non è stato trovato il file = " + intestazione);
	}
	log.debug("buildAndSendMessage# trasformo allegato in RiepilogoPratica. Allegato nome = {} ", a.getNomeFile());
	File rpsuap_file = null;
	try {
	    rpsuap_file = File.createTempFile("temp", "-rpsuap_file-" + cooperazioneSUAPEnte.getIntestazione().getCodicePratica() + ".xml");
	    FileOutputStream fileOutputStream = new FileOutputStream(rpsuap_file);
	    a.getEmbeddedFileRef().writeTo(fileOutputStream);
	    fileOutputStream.flush();
	    fileOutputStream.close();
	} catch (IOException e) {
	    log.error("inviaSUAPEnte# Errore durante la creazione del file xml Riepilogo pratica suap , della pratica = {} ",
		    cooperazioneSUAPEnte.getIntestazione().getCodicePratica());
	    throw new RuntimeException("Errore durante la creazione del file xml Riepilogo pratica suap , della pratica = " +
				       cooperazioneSUAPEnte.getIntestazione().getCodicePratica() + ". Errore = " + e);
	}
	RiepilogoPraticaSUAP rpsuap = infocamereHelperService.getRiepilogoPraticaSUAP(rpsuap_file, a.getNomeFile());
	E result = buildAndSendMessage(cooperazioneSUAPEnte, rpsuap, fileComunicazione, rpsuap_file, sportellobackoffice, sportelloNodo, listAllegati,
		parametriHelper, dettaglioPraticaTypeHelperService, stcWSClient);
	return result;
    }

    protected abstract E buildAndSendMessage(CooperazioneSUAPEnte cooperazioneSUAPEnte, RiepilogoPraticaSUAP rpsuap, File fileComunicazione,
	    File rpsuap_file, SportelloType sportelloTypeBackoffice, SportelloType sportelloNodo, List<Allegato> allegati,
	    ParametriHelper parametriHelper, DettaglioPraticaAndAttivitaTypeHelperService dettaglioPraticaTypeHelperService, StcWSClient stcWSClient)
	    throws Exception;

    public DocumentiType populateDocumentoComunicazione(File _filecomunicazione, String codicePratica, Boolean isNuovaPratica) {

	DocumentiType dt = null;
	try {
	    if (_filecomunicazione != null) {
		dt = new DocumentiType();
		dt.setDocumento("Allegato della comunicazione (generato automaticamente)");
		dt.setId("Allegato della comunicazione (generato automaticamente)");
		dt.setData(Utilities.getToday());
		AllegatiType allegatiType = new AllegatiType();
		allegatiType.setAllegato(_filecomunicazione.getName());
		allegatiType.setId(_filecomunicazione.getName());
		AllegatoBinarioType abt = new AllegatoBinarioType();
		DataSource fds = new FileDataSource(_filecomunicazione);
		DataHandler handler = new DataHandler(fds);
		abt.setBinaryData(handler);
		abt.setFileName(_filecomunicazione.getName());
		allegatiType.setFile(abt);
		dt.setAllegati(allegatiType);
	    }
	} catch (Exception e) {
	    if (isNuovaPratica) {
		log.error(
			"buildAndSendMessage# errore durante la creazione del documento type che rappresenta la comunicazione invio pratica. Pratica = {} ",
			codicePratica);
	    } else {
		log.error(
			"buildAndSendMessage# errore durante la creazione del documento type che rappresenta la comunicazione invio pratica. Pratica = {} ",
			codicePratica);
	    }
	}
	return dt;
    }

    private SportelloType getSportelloBackoffice(String idente, String idSportello) {

	SportelloType s = new SportelloType();
	s.setIdEnte(idente);
	s.setIdNodo(deployProperties.getStcIdNodoDestinatario());
	s.setIdSportello(idSportello);
	return s;
    }

    private SportelloType getSportelloInfocamere() {

	SportelloType s = new SportelloType();
	s.setIdEnte(deployProperties.getStcIdEnteMittente());
	s.setIdNodo(deployProperties.getStcIdNodoMittente());
	s.setIdSportello(deployProperties.getStcIdSportelloMittente());
	return s;
    }
}
