/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.infocamera.schema.serviziocommercio.Risposta;
import it.gruppoinit.infocamera.schema.serviziocommercio.Risposta.Messaggio;
import it.gruppoinit.infocamera.schema.serviziocommercio.Risposta.Messaggio.Errore;
import it.gruppoinit.infocamera.schema.sigeproexport.InfoCameraConstans;
import it.gruppoinit.infocamera.schema.sigeproexport.LISTAISTANZE;
import it.gruppoinit.infocamera.schema.sigeproexport.LISTAISTANZE.ISTANZA;
import it.gruppoinit.infocamera.stub.servizicommercio.ServiziCommercioWS;
import it.gruppoinit.infocamera.stub.servizicommercio.ServiziCommercioWSServiceLocator;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.InfoCameraService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.ws.client.SigeproExportWsClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.rpc.ServiceException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service per l'integrazione Info Camera
 * 
 * @author francescop
 * 
 */
@Service
public class InfoCameraServiceImpl implements InfoCameraService {

    private static final Logger log = LoggerFactory.getLogger(InfoCameraServiceImpl.class);
    private VerticalizzazioniService verticalizzazioniService;
    private ComuniassociatiService comuniassociatiService;
    private MovimentiService movimentiService;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Override
    public Messaggio invioInfocameraWS(String sToken, String codice, String data, String codicemovimento) {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("WS INFOCAMERA: \nTOKEN : " + sToken + "\nCODICEISTANZA : " + codice + "\nDATA : " + data + "\nCODICEMOVIMENTO :"
		    + codicemovimento);
	}
	/*
	 * RECUPERO URLEXPORT DEL WEBSERVICE SIGEPROEXPORT
	 */
	//	Verticalizzazioniparametri verticalizzazioniparametriURLEXPORT = verticalizzazioniService.getVerticalizzazioniparametri(
	//		InfoCameraConstans.VERTICALIZZAZIONE_INFOCAMERA, InfoCameraConstans.VERTICALIZZAZIONE_INFOCAMERA_PARAMETRO_URLEXPORT);
	//	if (verticalizzazioniparametriURLEXPORT == null) {
	//	    log.error("VERTICALIZZAZIONE INFOCAMERA NON ATTIVA : PARAMETRO URLEXPORT");
	//	    throw new RuntimeException("VERTICALIZZAZIONE INFOCAMERA NON ATTIVA : PARAMETRO URLEXPORT");
	//	}
	//	String urlexport = verticalizzazioniparametriURLEXPORT.getValore();
	//	if (log.isDebugEnabled()) {
	//	    log.debug("WS INFOCAMERA - VERTICALIZZAZIONE PARAMETRO URLEXPORT : " + urlexport);
	//	}
	/*
	 * RECUPERO URL DEL WEBSERVICE INFOCAMERA
	 */
	Verticalizzazioniparametri verticalizzazioniparametriURL = verticalizzazioniService.getVerticalizzazioniparametri(
		InfoCameraConstans.VERTICALIZZAZIONE_INFOCAMERA, InfoCameraConstans.VERTICALIZZAZIONE_INFOCAMERA_PARAMETRO_URL);
	if (verticalizzazioniparametriURL == null) {
	    log.error("VERTICALIZZAZIONE INFOCAMERA NON ATTIVA : PARAMETRO URL");
	    throw new RuntimeException("VERTICALIZZAZIONE INFOCAMERA NON ATTIVA : PARAMETRO URL");
	}
	String url = verticalizzazioniparametriURL.getValore();
	if (log.isDebugEnabled()) {
	    log.debug("WS INFOCAMERA - VERTICALIZZAZIONE PARAMETRO URL : " + url);
	}
	/*
	 * COMUNICAZIONE WEBSERVICE SIGEPROEXPORT
	 */
	// RECUPERO IDESPORTAZIONE
	int iIDExp = 0;
	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(
		InfoCameraConstans.VERTICALIZZAZIONE_INFOCAMERA, InfoCameraConstans.VERTICALIZZAZIONE_INFOCAMERA_PARAMETRO_IDESPORTAZIONE);
	if (verticalizzazioniparametri == null) {
	    log.error("VERTICALIZZAZIONE INFOCAMERA NON ATTIVA : PARAMETRO IDESPORTAZIONE");
	    throw new RuntimeException("VERTICALIZZAZIONE INFOCAMERA NON ATTIVA : PARAMETRO IDESPORTAZIONE");
	}
	String idesportazione = verticalizzazioniparametri.getValore();
	if (log.isDebugEnabled()) {
	    log.debug("WS INFOCAMERA - VERTICALIZZAZIONE PARAMETRO IDESPORTAZIONE : " + idesportazione);
	}
	try {
	    iIDExp = Integer.parseInt(idesportazione);
	} catch (NumberFormatException e) {
	    log.error("WS INFOCAMERA - ERRORE CONVERSIONE idesportazione:" + e.getMessage());
	    throw new RuntimeException("WS INFOCAMERA - ERRORE CONVERSIONE idesportazione:" + e.getMessage());
	}
	// //////////////////////////
	LISTAISTANZE listaistanze = new LISTAISTANZE();
	ISTANZA istanza = new ISTANZA();
	istanza.setIDCOMUNE(ORMHelper.getIdcomune());
	String codiceistanza = codice;
	istanza.setCODICE(codiceistanza);
	istanza.setDATA(data);
	List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	if (!(comuniassociatis == null || comuniassociatis.size() == 0)) {
	    istanza.setCODICECOMUNE(comuniassociatis.get(0).getId().getCodicecomune());
	}
	listaistanze.getISTANZA().add(istanza);
	// /////////////////////////
	String responseSigeproExport = "";
	try {
	    // Recuperare wsdl dalla configurazione
	    // CHIAMO IL METODO EXPORT DEL WEBSERVICE SIGEPROEXPORT
	    byte[] responseByte = SigeproExportWsClient.export(sToken, listaistanze, iIDExp, ORMHelper.getIdcomune(), null);
	    try {
		responseSigeproExport = new String(responseByte, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
		throw new RuntimeException("WS INFOCAMERA - ERRORE CODIFICA RESPONSE SIGEPRO EXPORT:" + e.getMessage());
	    }
	} catch (Exception e) {
	    log.error("WS INFOCAMERA - ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	    throw new RuntimeException("WS INFOCAMERA - ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	}
	/*
	 * FINE COMUNICAZIONE WEBSERVICE SIGEPROEXPORT
	 */
	/*
	 * COMUNICAZIONE WEBSERVICE INFOCAMERA
	 */
	String modello_com = responseSigeproExport;
	if (log.isDebugEnabled()) {
	    log.debug("WS INFOCAMERA - MODELLO CAMCOM : " + modello_com);
	}
	Verticalizzazioniparametri verticalizzazioniparametriInfocameraUSER = verticalizzazioniService.getVerticalizzazioniparametri(
		InfoCameraConstans.VERTICALIZZAZIONE_INFOCAMERA, InfoCameraConstans.VERTICALIZZAZIONE_INFOCAMERA_PARAMETRO_USER);
	if (verticalizzazioniparametriInfocameraUSER == null) {
	    log.error("VERTICALIZZAZIONE INFOCAMERA NON ATTIVA : PARAMETRO USER");
	    throw new RuntimeException("VERTICALIZZAZIONE INFOCAMERA NON ATTIVA : PARAMETRO USER");
	}
	String user = verticalizzazioniparametriInfocameraUSER.getValore();
	if (log.isDebugEnabled()) {
	    log.debug("WS INFOCAMERA - VERTICALIZZAZIONE PARAMETRO USER : " + user);
	}
	Verticalizzazioniparametri verticalizzazioniparametriInfocameraPWD = verticalizzazioniService.getVerticalizzazioniparametri(
		InfoCameraConstans.VERTICALIZZAZIONE_INFOCAMERA, InfoCameraConstans.VERTICALIZZAZIONE_INFOCAMERA_PARAMETRO_PWD);
	if (verticalizzazioniparametriInfocameraPWD == null) {
	    log.error("VERTICALIZZAZIONE INFOCAMERA NON ATTIVA : PARAMETRO PASSWORD");
	    throw new RuntimeException("VERTICALIZZAZIONE INFOCAMERA NON ATTIVA : PARAMETRO PASSWORD");
	}
	String password = verticalizzazioniparametriInfocameraPWD.getValore();
	if (log.isDebugEnabled()) {
	    log.debug("WS INFOCAMERA - VERTICALIZZAZIONE PARAMETRO PASSWORD : " + password);
	}
	ServiziCommercioWSServiceLocator serviceInfocamera = new ServiziCommercioWSServiceLocator();
	String resultInfoCamera = "";
	try {
	    ServiziCommercioWS port = serviceInfocamera.getServiziCommercioWS(new URL(url));
	    resultInfoCamera = port.trasferimentoModelloCom(user, password, modello_com);
	    if (log.isDebugEnabled()) {
		log.debug("WS INFOCAMERA - Risposta WebService ServiziCommercioWS : " + resultInfoCamera);
	    }
	} catch (MalformedURLException e) {
	    log.error("WS INFOCAMERA - ERRORE WEBSERVICE SERVIZIOCOMMERCIOWS(url webservice error):" + e.getMessage());
	    throw new RuntimeException("WS INFOCAMERA - ERRORE WEBSERVICE SERVIZIOCOMMERCIOWS(url webservice error):" + e.getMessage());
	} catch (ServiceException e) {
	    log.error("WS INFOCAMERA - ERRORE WEBSERVICE SERVIZIOCOMMERCIOWS(service error):" + e.getMessage());
	    throw new RuntimeException("WS INFOCAMERA - ERRORE WEBSERVICE SERVIZIOCOMMERCIOWS(service error):" + e.getMessage());
	} catch (RemoteException e) {
	    log.error("WS INFOCAMERA - ERRORE WEBSERVICE SERVIZIOCOMMERCIOWS(remote error):" + e.getMessage());
	    throw new RuntimeException("WS INFOCAMERA - ERRORE WEBSERVICE SERVIZIOCOMMERCIOWS(remote error):" + e.getMessage());
	}
	Risposta risposta;
	try {
	    JAXBContext context = JAXBContext.newInstance("it.gruppoinit.infocamera.schema.serviziocommercio");
	    Unmarshaller um = context.createUnmarshaller();
	    InputStream bis = new ByteArrayInputStream(resultInfoCamera.getBytes());
	    XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	    XMLStreamReader reader = inputFactory.createXMLStreamReader(bis);
	    risposta = (Risposta) um.unmarshal(reader);
	    if (log.isDebugEnabled()) {
		log.debug("WS INFOCAMERA - Risposta Unmarshal ServiziCommercioWS : " + risposta);
	    }
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	} catch (FactoryConfigurationError e) {
	    throw new RuntimeException(e);
	} catch (XMLStreamException e) {
	    throw new RuntimeException(e);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	/*
	 * FINE COMUNICAZIONE WEBSERVICE INFOCAMERA
	 */
	/*
	 * GESTIONE ESITO DELLA RISPOSTA DEL WEBSERVICE INFOCAMERA
	 */
	String esito = risposta.getMessaggio().getEsito().value();
	Errore errore = risposta.getMessaggio().getErrore();
	if (log.isDebugEnabled()) {
	    log.debug("WS INFOCAMERA - ESITO : " + esito);
	    if (errore != null) {
		log.debug("WS INFOCAMERA - ERRORE CODICE : " + errore.getCodice());
		log.debug("WS INFOCAMERA - ERRORE DESCRIZIONE : " + errore.getDescrizione());
	    }
	}
	if (esito.contains(InfoCameraConstans.INFOCAMERA_RISPOSTA_ESITO_OK)) {
	    Integer codiceMov = 0;
	    try {
		codiceMov = Integer.parseInt(codicemovimento);
	    } catch (NumberFormatException e) {
		throw new RuntimeException(e);
	    }
	    PkId id = new PkId(codiceMov);
	    Movimenti movimenti = movimentiService.findById(id);
	    movimenti.setInviatoACamcom(true);
	    movimentiService.update(movimenti);
	}
	return risposta.getMessaggio();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
