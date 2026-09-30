/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.backoffice.definitions.albopretorio.AlboPretorio;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.DettaglioPubblicazioneRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.DettaglioPubblicazioneResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaCategorie;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaCategorieRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaCategorieResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaPubblicazioniAllegati;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaPubblicazioniFiltri;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaPubblicazioniFiltriRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaPubblicazioniFiltriResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaPubblicazioniMax;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaPubblicazioniMaxRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaPubblicazioniMaxResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaPubblicazioniTot;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaPubblicazioniTotRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaPubblicazioniTotResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ListaPubblicazioniValideAl;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.Oggetto;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.PubblicazioniAllegati;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.PubblicazioniValideAlRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.PubblicazioniValideAlResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ScaricaAllegatoPubblicazioneRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio.ScaricaAllegatoPubblicazioneResponse;
import it.gruppoinit.pal.gp.core.domain.AlboCategorie;
import it.gruppoinit.pal.gp.core.domain.AlboPretorioFilter;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioniAllegati;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.AlboCategorieService;
import it.gruppoinit.pal.gp.core.service.AlboPubblicazioniAllegatiService;
import it.gruppoinit.pal.gp.core.service.AlboPubblicazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

@WebService(serviceName = "AlboPretorioService", portName = "AlboPretorioSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/albopretorio", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.albopretorio.AlboPretorio")
public class AlboPretorioWS extends BaseWS implements AlboPretorio {

    private static final Logger log = LoggerFactory.getLogger(AlboPretorioWS.class);
    private AlboCategorieService alboCategorieService;

    @Autowired
    public void setAlboCategorieService(AlboCategorieService alboCategorieService) {

	this.alboCategorieService = alboCategorieService;
    }

    private AlboPubblicazioniService alboPubblicazioniService;

    @Autowired
    public void setAlboPubblicazioniService(AlboPubblicazioniService alboPubblicazioniService) {

	this.alboPubblicazioniService = alboPubblicazioniService;
    }

    private AlboPubblicazioniAllegatiService alboPubblicazioniAllegatiService;

    @Autowired
    public void setAlboPubblicazioniAllegatiService(AlboPubblicazioniAllegatiService alboPubblicazioniAllegatiService) {

	this.alboPubblicazioniAllegatiService = alboPubblicazioniAllegatiService;
    }

    private ContenttypesService contenttypesService;

    @Autowired
    public void setContenttypesService(ContenttypesService contenttypesService) {

	this.contenttypesService = contenttypesService;
    }

    private AmministrazioniService amministrazioniService;

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    private OggettiService oggettiService;

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    public ListaCategorieResponse listaCategorie(ListaCategorieRequest request) {

	// §§§BEGIN§§§
	log.debug("WS listaCategorie received a MessageRequest object with token={} and software={}", request.getToken(), request.getSoftware());
	setORMHelper(request.getSoftware(), request.getToken());
	ListaCategorieResponse response = new ListaCategorieResponse();
	try {
	    List<AlboCategorie> list = alboCategorieService.findAll(null, null);
	    List<ListaCategorie> listaCategorie = new ArrayList<ListaCategorie>();
	    for (AlboCategorie alboCategorie : list) {
		ListaCategorie categorie = new ListaCategorie();
		categorie.setDESCRIZIONE(alboCategorie.getDescrizione());
		categorie.setID(alboCategorie.getId().getCodice());
		Short ordine = alboCategorie.getOrdine();
		ordine = (ordine == null) ? 0 : ordine;
		categorie.setORDINE(alboCategorie.getOrdine());
		listaCategorie.add(categorie);
	    }
	    response.getListaCategorie().addAll(listaCategorie);
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw new RuntimeException("ERRORE WEB SERVICE AlboPretorioWS METHOD listaCategorie: " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	return response;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public ListaPubblicazioniFiltriResponse listaPubblicazioniFiltri(ListaPubblicazioniFiltriRequest request) {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("WS listaPubblicazioniFiltri received a MessageRequest object with token=" +
		    request.getToken() +
		    " and software=" +
		    request.getSoftware() +
		    " and da=" +
		    request.getDa() +
		    " and a=" +
		    request.getA() +
		    " and oggetto=" +
		    request.getOggetto() +
		    " and validoAl=" +
		    request.getValidoAl().toGregorianCalendar().getTime() +
		    " and validoAl=" +
		    request.getValidoDal().toGregorianCalendar().getTime());
	}
	setORMHelper(request.getSoftware(), request.getToken());
	ListaPubblicazioniFiltriResponse response = new ListaPubblicazioniFiltriResponse();
	try {
	    Integer a = request.getA();
	    Integer codicecategoria = request.getCategoria();
	    Integer da = request.getDa();
	    String oggetto = request.getOggetto();
	    Date validoAl = null;
	    if (request.getValidoAl() != null) {
		validoAl = request.getValidoAl().toGregorianCalendar().getTime();
	    }
	    Date validoDal = null;
	    if (request.getValidoDal() != null) {
		validoDal = request.getValidoDal().toGregorianCalendar().getTime();
	    }
	    AlboPretorioFilter alboPretorioFilter = new AlboPretorioFilter();
	    alboPretorioFilter.setA(a);
	    alboPretorioFilter.setCodicecategoria(codicecategoria);
	    alboPretorioFilter.setDa(da);
	    alboPretorioFilter.setOggetto(oggetto);
	    alboPretorioFilter.setValidoAl(validoAl);
	    alboPretorioFilter.setValidoDal(validoDal);
	    List<AlboPubblicazioni> alboPubblicazionis = alboPubblicazioniService.findAllFilter(alboPretorioFilter);
	    List<ListaPubblicazioniFiltri> listaPubblicazioniFilterList = new ArrayList<ListaPubblicazioniFiltri>();
	    for (AlboPubblicazioni alboPubblicazioni : alboPubblicazionis) {
		ListaPubblicazioniFiltri listaPubblicazioniFiltri = new ListaPubblicazioniFiltri();
		listaPubblicazioniFiltri.setALBOCATEGORIEID(alboPubblicazioni.getAlboCategorie().getId().getCodice());
		listaPubblicazioniFiltri.setCODICEAMMINISTRAZIONE(alboPubblicazioni.getAmministrazioni().getId().getCodice());
		AlboCategorie alboCategorie = alboCategorieService.findById(alboPubblicazioni.getAlboCategorie().getId());
		listaPubblicazioniFiltri.setALBOCATEGORIEDESCR(alboCategorie.getDescrizione());
		Amministrazioni amministrazioni = amministrazioniService.findById(alboPubblicazioni.getAmministrazioni().getId());
		listaPubblicazioniFiltri.setAMMINISTRAZIONEDESCR(amministrazioni.getAmministrazione());
		listaPubblicazioniFiltri.setCODICERESPONSABILE(alboPubblicazioni.getResponsabili().getId().getCodice());
		if (alboPubblicazioni.getAmministrazionireferenti() != null && alboPubblicazioni.getAmministrazionireferenti().getId() != null
			&& alboPubblicazioni.getAmministrazionireferenti().getId().getCodice() != null) {
		    listaPubblicazioniFiltri.setCODICEUFFICIO(alboPubblicazioni.getAmministrazionireferenti().getId().getCodice());
		}
		GregorianCalendar dataCreazioneCalendar = new GregorianCalendar();
		dataCreazioneCalendar.setTime(alboPubblicazioni.getDataCreazione());
		XMLGregorianCalendar dataCreazione = Utilities.getXMLGregorianCalendar(dataCreazioneCalendar);
		listaPubblicazioniFiltri.setDATACREAZIONE(dataCreazione);
		if (alboPubblicazioni.getDataProtocollo() != null) {
		    GregorianCalendar dataProtocolloCalendar = new GregorianCalendar();
		    dataProtocolloCalendar.setTime(alboPubblicazioni.getDataProtocollo());
		    XMLGregorianCalendar dataProtocollo = Utilities.getXMLGregorianCalendar(dataProtocolloCalendar);
		    listaPubblicazioniFiltri.setDATAPROTOCOLLO(dataProtocollo);
		} else {
		    listaPubblicazioniFiltri.setDATAPROTOCOLLO(null);
		}
		GregorianCalendar dataPubblicazioneCalendar = new GregorianCalendar();
		dataPubblicazioneCalendar.setTime(alboPubblicazioni.getDataPubblicazione());
		XMLGregorianCalendar dataPubblicazione = Utilities.getXMLGregorianCalendar(dataPubblicazioneCalendar);
		listaPubblicazioniFiltri.setDATAPUBBLICAZIONE(dataPubblicazione);
		listaPubblicazioniFiltri.setDESCRIZIONE(alboPubblicazioni.getDescrizione());
		listaPubblicazioniFiltri.setID(alboPubblicazioni.getId().getCodice());
		if (alboPubblicazioni.getNote() == null) {
		    listaPubblicazioniFiltri.setNOTE(alboPubblicazioni.getNote());
		} else {
		    listaPubblicazioniFiltri.setNOTE("");
		}
		if (alboPubblicazioni.getNumeroProtocollo() != null) {
		    listaPubblicazioniFiltri.setNUMEROPROTOCOLLO(alboPubblicazioni.getNumeroProtocollo());
		} else {
		    listaPubblicazioniFiltri.setNUMEROPROTOCOLLO("");
		}
		listaPubblicazioniFiltri.setNUMEROPUBBLICAZIONE(alboPubblicazioni.getNumeroPubblicazione());
		if (alboPubblicazioni.getValidaAl() != null) {
		    GregorianCalendar validaalCalendar = new GregorianCalendar();
		    validaalCalendar.setTime(alboPubblicazioni.getValidaAl());
		    XMLGregorianCalendar validaAl = Utilities.getXMLGregorianCalendar(validaalCalendar);
		    listaPubblicazioniFiltri.setVALIDAAL(validaAl);
		} else {
		    listaPubblicazioniFiltri.setVALIDAAL(null);
		}
		if (alboPubblicazioni.getValidaDal() != null) {
		    GregorianCalendar validaDAlCalendar = new GregorianCalendar();
		    validaDAlCalendar.setTime(alboPubblicazioni.getValidaDal());
		    XMLGregorianCalendar validaDAl = Utilities.getXMLGregorianCalendar(validaDAlCalendar);
		    listaPubblicazioniFiltri.setVALIDADAL(validaDAl);
		} else {
		    listaPubblicazioniFiltri.setVALIDADAL(null);
		}
		listaPubblicazioniFilterList.add(listaPubblicazioniFiltri);
	    }
	    response.getListaPubblicazioniFiltri().addAll(listaPubblicazioniFilterList);
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw new RuntimeException("ERRORE WEB SERVICE AlboPretorioWS METHOD listaPubblicazioniFiltri: " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	return response;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public ListaPubblicazioniMaxResponse listaPubblicazioniMax(ListaPubblicazioniMaxRequest request) {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("WS listaPubblicazioniMax received a MessageRequest object with token=" +
		    request.getToken() +
		    " and software=" +
		    request.getSoftware() +
		    " and da=" +
		    request.getDa() +
		    " and a=" +
		    request.getA() +
		    " and maxresult=" +
		    request.getMaxResult());
	}
	setORMHelper(request.getSoftware(), request.getToken());
	ListaPubblicazioniMaxResponse response = new ListaPubblicazioniMaxResponse();
	try {
	    AlboPretorioFilter alboPretorioFilter = new AlboPretorioFilter();
	    alboPretorioFilter.setMaxresult(request.getMaxResult());
	    alboPretorioFilter.setDa(request.getDa());
	    alboPretorioFilter.setA(request.getA());
	    List<AlboPubblicazioni> alboPubblicazionis = alboPubblicazioniService.findAllMaxResult(alboPretorioFilter);
	    List<ListaPubblicazioniMax> listaPubblicazioniMaxList = new ArrayList<ListaPubblicazioniMax>();
	    for (AlboPubblicazioni alboPubblicazioni : alboPubblicazionis) {
		ListaPubblicazioniMax listaPubblicazioniMax = new ListaPubblicazioniMax();
		listaPubblicazioniMax.setALBOCATEGORIEID(alboPubblicazioni.getAlboCategorie().getId().getCodice());
		listaPubblicazioniMax.setCODICEAMMINISTRAZIONE(alboPubblicazioni.getAmministrazioni().getId().getCodice());
		AlboCategorie alboCategorie = alboCategorieService.findById(alboPubblicazioni.getAlboCategorie().getId());
		listaPubblicazioniMax.setALBOCATEGORIEDESCR(alboCategorie.getDescrizione());
		Amministrazioni amministrazioni = amministrazioniService.findById(alboPubblicazioni.getAmministrazioni().getId());
		listaPubblicazioniMax.setAMMINISTRAZIONEDESCR(amministrazioni.getAmministrazione());
		listaPubblicazioniMax.setCODICERESPONSABILE(alboPubblicazioni.getResponsabili().getId().getCodice());
		if (alboPubblicazioni.getAmministrazionireferenti() != null && alboPubblicazioni.getAmministrazionireferenti().getId() != null
			&& alboPubblicazioni.getAmministrazionireferenti().getId().getCodice() != null) {
		    listaPubblicazioniMax.setCODICEUFFICIO(alboPubblicazioni.getAmministrazionireferenti().getId().getCodice());
		}
		GregorianCalendar dataCreazioneCalendar = new GregorianCalendar();
		dataCreazioneCalendar.setTime(alboPubblicazioni.getDataCreazione());
		XMLGregorianCalendar dataCreazione = Utilities.getXMLGregorianCalendar(dataCreazioneCalendar);
		listaPubblicazioniMax.setDATACREAZIONE(dataCreazione);
		if (alboPubblicazioni.getDataProtocollo() != null) {
		    GregorianCalendar dataProtocolloCalendar = new GregorianCalendar();
		    dataProtocolloCalendar.setTime(alboPubblicazioni.getDataProtocollo());
		    XMLGregorianCalendar dataProtocollo = Utilities.getXMLGregorianCalendar(dataProtocolloCalendar);
		    listaPubblicazioniMax.setDATAPROTOCOLLO(dataProtocollo);
		} else {
		    listaPubblicazioniMax.setDATAPROTOCOLLO(null);
		}
		GregorianCalendar dataPubblicazioneCalendar = new GregorianCalendar();
		dataPubblicazioneCalendar.setTime(alboPubblicazioni.getDataPubblicazione());
		XMLGregorianCalendar dataPubblicazione = Utilities.getXMLGregorianCalendar(dataPubblicazioneCalendar);
		listaPubblicazioniMax.setDATAPUBBLICAZIONE(dataPubblicazione);
		listaPubblicazioniMax.setDESCRIZIONE(alboPubblicazioni.getDescrizione());
		listaPubblicazioniMax.setID(alboPubblicazioni.getId().getCodice());
		listaPubblicazioniMax.setNOTE(alboPubblicazioni.getNote());
		if (alboPubblicazioni.getNumeroProtocollo() != null) {
		    listaPubblicazioniMax.setNUMEROPROTOCOLLO(alboPubblicazioni.getNumeroProtocollo());
		} else {
		    listaPubblicazioniMax.setNUMEROPROTOCOLLO("");
		}
		listaPubblicazioniMax.setNUMEROPUBBLICAZIONE(alboPubblicazioni.getNumeroPubblicazione());
		if (alboPubblicazioni.getValidaDal() != null) {
		    GregorianCalendar validaDalCalendar = new GregorianCalendar();
		    validaDalCalendar.setTime(alboPubblicazioni.getValidaDal());
		    XMLGregorianCalendar validaDAl = Utilities.getXMLGregorianCalendar(validaDalCalendar);
		    listaPubblicazioniMax.setVALIDADAL(validaDAl);
		} else {
		    listaPubblicazioniMax.setVALIDADAL(null);
		}
		if (alboPubblicazioni.getValidaAl() != null) {
		    GregorianCalendar validaAlCalendar = new GregorianCalendar();
		    validaAlCalendar.setTime(alboPubblicazioni.getValidaAl());
		    XMLGregorianCalendar validaAl = Utilities.getXMLGregorianCalendar(validaAlCalendar);
		    listaPubblicazioniMax.setVALIDAAL(validaAl);
		} else {
		    listaPubblicazioniMax.setVALIDAAL(null);
		}
		listaPubblicazioniMaxList.add(listaPubblicazioniMax);
	    }
	    response.getListaPubblicazioniMax().addAll(listaPubblicazioniMaxList);
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw new RuntimeException("ERRORE WEB SERVICE AlboPretorioWS METHOD listaPubblicazioniMax: " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	return response;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public ListaPubblicazioniTotResponse listaPubblicazioniTot(ListaPubblicazioniTotRequest request) {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("WS listaPubblicazioniTot received a MessageRequest object with token=" +
		    request.getToken() +
		    " and software=" +
		    request.getSoftware());
	}
	setORMHelper(request.getSoftware(), request.getToken());
	ListaPubblicazioniTotResponse response = new ListaPubblicazioniTotResponse();
	try {
	    List<AlboPubblicazioni> alboPubblicazionis = alboPubblicazioniService.findAll(null, null);
	    List<ListaPubblicazioniTot> listaPubblicazioniTotList = new ArrayList<ListaPubblicazioniTot>();
	    for (AlboPubblicazioni alboPubblicazioni : alboPubblicazionis) {
		ListaPubblicazioniTot listaPubblicazioniTot = new ListaPubblicazioniTot();
		listaPubblicazioniTot.setALBOCATEGORIEID(alboPubblicazioni.getAlboCategorie().getId().getCodice());
		listaPubblicazioniTot.setCODICEAMMINISTRAZIONE(alboPubblicazioni.getAmministrazioni().getId().getCodice());
		AlboCategorie alboCategorie = alboCategorieService.findById(alboPubblicazioni.getAlboCategorie().getId());
		listaPubblicazioniTot.setALBOCATEGORIEDESCR(alboCategorie.getDescrizione());
		Amministrazioni amministrazioni = amministrazioniService.findById(alboPubblicazioni.getAmministrazioni().getId());
		listaPubblicazioniTot.setAMMINISTRAZIONEDESCR(amministrazioni.getAmministrazione());
		listaPubblicazioniTot.setCODICERESPONSABILE(alboPubblicazioni.getResponsabili().getId().getCodice());
		if (alboPubblicazioni.getAmministrazionireferenti() != null && alboPubblicazioni.getAmministrazionireferenti().getId() != null
			&& alboPubblicazioni.getAmministrazionireferenti().getId().getCodice() != null) {
		    listaPubblicazioniTot.setCODICEUFFICIO(alboPubblicazioni.getAmministrazionireferenti().getId().getCodice());
		}
		GregorianCalendar dataCreazioneCalendar = new GregorianCalendar();
		dataCreazioneCalendar.setTime(alboPubblicazioni.getDataCreazione());
		XMLGregorianCalendar dataCreazione = Utilities.getXMLGregorianCalendar(dataCreazioneCalendar);
		listaPubblicazioniTot.setDATACREAZIONE(dataCreazione);
		if (alboPubblicazioni.getDataProtocollo() != null) {
		    GregorianCalendar dataProtocolloCalendar = new GregorianCalendar();
		    dataProtocolloCalendar.setTime(alboPubblicazioni.getDataProtocollo());
		    XMLGregorianCalendar dataProtocollo = Utilities.getXMLGregorianCalendar(dataProtocolloCalendar);
		    listaPubblicazioniTot.setDATAPROTOCOLLO(dataProtocollo);
		} else {
		    listaPubblicazioniTot.setDATAPROTOCOLLO(null);
		}
		GregorianCalendar dataPubblicazioneCalendar = new GregorianCalendar();
		dataPubblicazioneCalendar.setTime(alboPubblicazioni.getDataPubblicazione());
		XMLGregorianCalendar dataPubblicazione = Utilities.getXMLGregorianCalendar(dataPubblicazioneCalendar);
		listaPubblicazioniTot.setDATAPUBBLICAZIONE(dataPubblicazione);
		listaPubblicazioniTot.setDESCRIZIONE(alboPubblicazioni.getDescrizione());
		listaPubblicazioniTot.setID(alboPubblicazioni.getId().getCodice());
		listaPubblicazioniTot.setNOTE(alboPubblicazioni.getNote());
		if (alboPubblicazioni.getNumeroProtocollo() != null) {
		    listaPubblicazioniTot.setNUMEROPROTOCOLLO(alboPubblicazioni.getNumeroProtocollo());
		} else {
		    listaPubblicazioniTot.setNUMEROPROTOCOLLO("");
		}
		listaPubblicazioniTot.setNUMEROPUBBLICAZIONE(alboPubblicazioni.getNumeroPubblicazione());
		if (alboPubblicazioni.getValidaDal() != null) {
		    GregorianCalendar validaDalCalendar = new GregorianCalendar();
		    validaDalCalendar.setTime(alboPubblicazioni.getValidaDal());
		    XMLGregorianCalendar validaDAl = Utilities.getXMLGregorianCalendar(validaDalCalendar);
		    listaPubblicazioniTot.setVALIDADAL(validaDAl);
		} else {
		    listaPubblicazioniTot.setVALIDADAL(null);
		}
		if (alboPubblicazioni.getValidaAl() != null) {
		    GregorianCalendar validaAlCalendar = new GregorianCalendar();
		    validaAlCalendar.setTime(alboPubblicazioni.getValidaAl());
		    XMLGregorianCalendar validaAl = Utilities.getXMLGregorianCalendar(validaAlCalendar);
		    listaPubblicazioniTot.setVALIDAAL(validaAl);
		} else {
		    listaPubblicazioniTot.setVALIDAAL(null);
		}
		listaPubblicazioniTotList.add(listaPubblicazioniTot);
	    }
	    response.getListaPubblicazioniTot().addAll(listaPubblicazioniTotList);
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw new RuntimeException("ERRORE WEB SERVICE AlboPretorioWS METHOD listaPubblicazioniTot: " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	return response;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public DettaglioPubblicazioneResponse dettaglioPubblicazione(DettaglioPubblicazioneRequest request) {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("WS dettaglioPubblicazione received a MessageRequest object with token=" +
		    request.getToken() +
		    " and software=" +
		    request.getSoftware() +
		    " and codicepubblicazione=" +
		    request.getIdPubblicazione());
	}
	setORMHelper(request.getSoftware(), request.getToken());
	DettaglioPubblicazioneResponse response = new DettaglioPubblicazioneResponse();
	try {
	    int codicepubblicazione = request.getIdPubblicazione();
	    AlboPubblicazioni alboPubblicazioni = alboPubblicazioniService.findById(new PkId(codicepubblicazione));
	    if (alboPubblicazioni != null) {
		response.setALBOCATEGORIEID(alboPubblicazioni.getAlboCategorie().getId().getCodice());
		response.setCODICEAMMINISTRAZIONE(alboPubblicazioni.getAmministrazioni().getId().getCodice());
		AlboCategorie alboCategorie = alboCategorieService.findById(alboPubblicazioni.getAlboCategorie().getId());
		response.setALBOCATEGORIEDESCR(alboCategorie.getDescrizione());
		Amministrazioni amministrazioni = amministrazioniService.findById(alboPubblicazioni.getAmministrazioni().getId());
		response.setAMMINISTRAZIONEDESCR(amministrazioni.getAmministrazione());
		response.setCODICERESPONSABILE(alboPubblicazioni.getResponsabili().getId().getCodice());
		if (alboPubblicazioni.getAmministrazionireferenti() != null && alboPubblicazioni.getAmministrazionireferenti().getId() != null
			&& alboPubblicazioni.getAmministrazionireferenti().getId().getCodice() != null) {
		    response.setCODICEUFFICIO(alboPubblicazioni.getAmministrazionireferenti().getId().getCodice());
		}
		GregorianCalendar dataCreazioneCalendar = new GregorianCalendar();
		dataCreazioneCalendar.setTime(alboPubblicazioni.getDataCreazione());
		XMLGregorianCalendar dataCreazione = Utilities.getXMLGregorianCalendar(dataCreazioneCalendar);
		response.setDATACREAZIONE(dataCreazione);
		if (alboPubblicazioni.getDataProtocollo() != null) {
		    GregorianCalendar dataProtocolloCalendar = new GregorianCalendar();
		    dataProtocolloCalendar.setTime(alboPubblicazioni.getDataProtocollo());
		    XMLGregorianCalendar dataProtocollo = Utilities.getXMLGregorianCalendar(dataProtocolloCalendar);
		    response.setDATAPROTOCOLLO(dataProtocollo);
		} else {
		    response.setDATAPROTOCOLLO(null);
		}
		GregorianCalendar dataPubblicazioneCalendar = new GregorianCalendar();
		dataPubblicazioneCalendar.setTime(alboPubblicazioni.getDataPubblicazione());
		XMLGregorianCalendar dataPubblicazione = Utilities.getXMLGregorianCalendar(dataPubblicazioneCalendar);
		response.setDATAPUBBLICAZIONE(dataPubblicazione);
		response.setDESCRIZIONE(alboPubblicazioni.getDescrizione());
		response.setID(alboPubblicazioni.getId().getCodice());
		if (alboPubblicazioni.getNote() != null) {
		    response.setNOTE(alboPubblicazioni.getNote());
		} else {
		    response.setNOTE("");
		}
		if (alboPubblicazioni.getNumeroProtocollo() != null) {
		    response.setNUMEROPROTOCOLLO(alboPubblicazioni.getNumeroProtocollo());
		} else {
		    response.setNUMEROPROTOCOLLO("");
		}
		response.setNUMEROPUBBLICAZIONE(alboPubblicazioni.getNumeroPubblicazione());
		if (alboPubblicazioni.getValidaDal() != null) {
		    GregorianCalendar validaDalCalendar = new GregorianCalendar();
		    validaDalCalendar.setTime(alboPubblicazioni.getValidaDal());
		    XMLGregorianCalendar validaDAl = Utilities.getXMLGregorianCalendar(validaDalCalendar);
		    response.setVALIDADAL(validaDAl);
		} else {
		    response.setVALIDADAL(null);
		}
		if (alboPubblicazioni.getValidaAl() != null) {
		    GregorianCalendar validaAlCalendar = new GregorianCalendar();
		    validaAlCalendar.setTime(alboPubblicazioni.getValidaAl());
		    XMLGregorianCalendar validaAl = Utilities.getXMLGregorianCalendar(validaAlCalendar);
		    response.setVALIDAAL(validaAl);
		} else {
		    response.setVALIDAAL(null);
		}
		List<PubblicazioniAllegati> pubblicazioniAllegatis = new ArrayList<PubblicazioniAllegati>();
		List<AlboPubblicazioniAllegati> alboPubblicazioniAllegatis = alboPubblicazioniAllegatiService.findOrderByOrdine(alboPubblicazioni);
		for (AlboPubblicazioniAllegati alboPubblicazioniAllegati : alboPubblicazioniAllegatis) {
		    PubblicazioniAllegati pubblicazioniAllegati = new PubblicazioniAllegati();
		    pubblicazioniAllegati.setDIMENSIONEFILE("");
		    pubblicazioniAllegati.setNOMEFILE("");
		    if (alboPubblicazioniAllegati.getOggetti() != null && alboPubblicazioniAllegati.getOggetti().getId().getCodice() != null) {
			pubblicazioniAllegati.setCODICEOGGETTO(alboPubblicazioniAllegati.getOggetti().getId().getCodice());
			Integer codice = alboPubblicazioniAllegati.getOggetti().getId().getCodice();
			Oggetti oggetti = oggettiService.findById(new PkId(codice));
			pubblicazioniAllegati.setDIMENSIONEFILE(oggetti.getDimensioneFileLeggibile());
			pubblicazioniAllegati.setNOMEFILE(oggetti.getNomefile());
		    }
		    pubblicazioniAllegati.setDESCRIZIONE(alboPubblicazioniAllegati.getDescrizione());
		    pubblicazioniAllegati.setORDINE(alboPubblicazioniAllegati.getOrdine());
		    pubblicazioniAllegati.setCODICEALLEGATO(alboPubblicazioniAllegati.getId().getCodice());
		    pubblicazioniAllegatis.add(pubblicazioniAllegati);
		}
		ListaPubblicazioniAllegati listaPubblicazioniAllegati = new ListaPubblicazioniAllegati();
		listaPubblicazioniAllegati.getPubblicazioniAllegati().addAll(pubblicazioniAllegatis);
		response.setListaPubblicazioniAllegati(listaPubblicazioniAllegati);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw new RuntimeException("ERRORE WEB SERVICE AlboPretorioWS METHOD dettaglioPubblicazione: " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	return response;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public ScaricaAllegatoPubblicazioneResponse scaricaAllegatoPubblicazione(ScaricaAllegatoPubblicazioneRequest request) {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("WS scaricaAllegatoPubblicazione received a MessageRequest object with token=" +
		    request.getToken() +
		    " and software=" +
		    request.getSoftware() +
		    " and codicepubblicazione=" +
		    request.getCodicePubblicazione() +
		    " and codiceoggetto=" +
		    request.getCodiceOggetto());
	}
	setORMHelper(request.getSoftware(), request.getToken());
	ScaricaAllegatoPubblicazioneResponse response = new ScaricaAllegatoPubblicazioneResponse();
	try {
	    int codiceoggetto = request.getCodiceOggetto();
	    int codicepubblicazione = request.getCodicePubblicazione();
	    Oggetti oggetti = alboPubblicazioniAllegatiService.findByPubblicazioneEOggetti(codiceoggetto, codicepubblicazione);
	    Oggetto oggetto = new Oggetto();
	    if (oggetti != null) {
		oggetto.setFilecontent(Utilities.bytesToDataHandler(oggetti.getOggetto()));
		String mimeTypes = contenttypesService.findMimeTypeByFileName(oggetti.getNomefile());
		oggetto.setMime(mimeTypes);
		oggetto.setNomefile(oggetti.getNomefile());
		oggetto.setDimensioneFile(oggetti.getDimensioneFileLeggibile());
		response.setOggetto(oggetto);
	    } else {
		byte[] filecontent = new byte[0];
		oggetto.setFilecontent(new DataHandler(new ByteArrayDataSource(filecontent, "text/plain")));
		oggetto.setMime("");
		oggetto.setNomefile("");
		oggetto.setDimensioneFile("");
		response.setOggetto(oggetto);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw new RuntimeException("ERRORE WEB SERVICE AlboPretorioWS METHOD scaricaAllegatoPubblicazione: " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	return response;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public PubblicazioniValideAlResponse pubblicazioniValideAl(PubblicazioniValideAlRequest request) {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("WS listaPubblicazioniFiltri received a MessageRequest object with token=" +
		    request.getToken() +
		    " and software=" +
		    request.getSoftware() +
		    " and oggetto=" +
		    request.getOggetto());
	}
	setORMHelper(request.getSoftware(), request.getToken());
	PubblicazioniValideAlResponse response = new PubblicazioniValideAlResponse();
	try {
	    Integer codicecategoria = request.getCategoria();
	    String oggetto = request.getOggetto();
	    Date datavalidaAl = null;
	    if (request.getDataValiditaAl() != null) {
		datavalidaAl = request.getDataValiditaAl().toGregorianCalendar().getTime();
	    }
	    AlboPretorioFilter alboPretorioFilter = new AlboPretorioFilter();
	    alboPretorioFilter.setCodicecategoria(codicecategoria);
	    alboPretorioFilter.setOggetto(oggetto);
	    alboPretorioFilter.setDataValidaAl(datavalidaAl);
	    List<AlboPubblicazioni> alboPubblicazionis = alboPubblicazioniService.findPublicazioniValideAL(alboPretorioFilter);
	    List<ListaPubblicazioniValideAl> listaPubblicazioniValideAls = new ArrayList<ListaPubblicazioniValideAl>();
	    for (AlboPubblicazioni alboPubblicazioni : alboPubblicazionis) {
		ListaPubblicazioniValideAl listaPubblicazioniValideAl = new ListaPubblicazioniValideAl();
		listaPubblicazioniValideAl.setALBOCATEGORIEID(alboPubblicazioni.getAlboCategorie().getId().getCodice());
		listaPubblicazioniValideAl.setCODICEAMMINISTRAZIONE(alboPubblicazioni.getAmministrazioni().getId().getCodice());
		AlboCategorie alboCategorie = alboCategorieService.findById(alboPubblicazioni.getAlboCategorie().getId());
		listaPubblicazioniValideAl.setALBOCATEGORIEDESCR(alboCategorie.getDescrizione());
		Amministrazioni amministrazioni = amministrazioniService.findById(alboPubblicazioni.getAmministrazioni().getId());
		listaPubblicazioniValideAl.setAMMINISTRAZIONEDESCR(amministrazioni.getAmministrazione());
		listaPubblicazioniValideAl.setCODICERESPONSABILE(alboPubblicazioni.getResponsabili().getId().getCodice());
		if (alboPubblicazioni.getAmministrazionireferenti() != null && alboPubblicazioni.getAmministrazionireferenti().getId() != null
			&& alboPubblicazioni.getAmministrazionireferenti().getId().getCodice() != null) {
		    listaPubblicazioniValideAl.setCODICEUFFICIO(alboPubblicazioni.getAmministrazionireferenti().getId().getCodice());
		}
		GregorianCalendar dataCreazioneCalendar = new GregorianCalendar();
		dataCreazioneCalendar.setTime(alboPubblicazioni.getDataCreazione());
		XMLGregorianCalendar dataCreazione = Utilities.getXMLGregorianCalendar(dataCreazioneCalendar);
		listaPubblicazioniValideAl.setDATACREAZIONE(dataCreazione);
		if (alboPubblicazioni.getDataProtocollo() != null) {
		    GregorianCalendar dataProtocolloCalendar = new GregorianCalendar();
		    dataProtocolloCalendar.setTime(alboPubblicazioni.getDataProtocollo());
		    XMLGregorianCalendar dataProtocollo = Utilities.getXMLGregorianCalendar(dataProtocolloCalendar);
		    listaPubblicazioniValideAl.setDATAPROTOCOLLO(dataProtocollo);
		} else {
		    listaPubblicazioniValideAl.setDATAPROTOCOLLO(null);
		}
		GregorianCalendar dataPubblicazioneCalendar = new GregorianCalendar();
		dataPubblicazioneCalendar.setTime(alboPubblicazioni.getDataPubblicazione());
		XMLGregorianCalendar dataPubblicazione = Utilities.getXMLGregorianCalendar(dataPubblicazioneCalendar);
		listaPubblicazioniValideAl.setDATAPUBBLICAZIONE(dataPubblicazione);
		listaPubblicazioniValideAl.setDESCRIZIONE(alboPubblicazioni.getDescrizione());
		listaPubblicazioniValideAl.setID(alboPubblicazioni.getId().getCodice());
		if (alboPubblicazioni.getNote() == null) {
		    listaPubblicazioniValideAl.setNOTE(alboPubblicazioni.getNote());
		} else {
		    listaPubblicazioniValideAl.setNOTE("");
		}
		if (alboPubblicazioni.getNumeroProtocollo() != null) {
		    listaPubblicazioniValideAl.setNUMEROPROTOCOLLO(alboPubblicazioni.getNumeroProtocollo());
		} else {
		    listaPubblicazioniValideAl.setNUMEROPROTOCOLLO("");
		}
		listaPubblicazioniValideAl.setNUMEROPUBBLICAZIONE(alboPubblicazioni.getNumeroPubblicazione());
		if (alboPubblicazioni.getValidaAl() != null) {
		    GregorianCalendar validaalCalendar = new GregorianCalendar();
		    validaalCalendar.setTime(alboPubblicazioni.getValidaAl());
		    XMLGregorianCalendar validaAl = Utilities.getXMLGregorianCalendar(validaalCalendar);
		    listaPubblicazioniValideAl.setVALIDAAL(validaAl);
		} else {
		    listaPubblicazioniValideAl.setVALIDAAL(null);
		}
		if (alboPubblicazioni.getValidaDal() != null) {
		    GregorianCalendar validaDAlCalendar = new GregorianCalendar();
		    validaDAlCalendar.setTime(alboPubblicazioni.getValidaDal());
		    XMLGregorianCalendar validaDAl = Utilities.getXMLGregorianCalendar(validaDAlCalendar);
		    listaPubblicazioniValideAl.setVALIDADAL(validaDAl);
		} else {
		    listaPubblicazioniValideAl.setVALIDADAL(null);
		}
		listaPubblicazioniValideAls.add(listaPubblicazioniValideAl);
	    }
	    response.getListaPubblicazioniValideAl().addAll(listaPubblicazioniValideAls);
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw new RuntimeException("ERRORE WEB SERVICE AlboPretorioWS METHOD listaPubblicazioniValideAl: " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	return response;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
