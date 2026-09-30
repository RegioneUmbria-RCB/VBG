/**
 * SistemaPagamentiBindingImpl.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package it.gruppoinit.regulus.gestoreincassi;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeOneriRegulus;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.VwOneriregulus;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.features.oneri.regulus.IstanzeOneriRegulusService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.VwOneriregulusService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.regulus.schema.TipoCodiceFunzione;
import it.gruppoinit.regulus.schema.billpaymentnotify.request.DATIFORNITI.RATE;
import it.gruppoinit.regulus.schema.billpaymentnotify.request.MSG;
import it.gruppoinit.regulus.schema.billpaymentnotify.request.TipoDatiPagamento;
import it.gruppoinit.regulus.schema.billpaymentnotify.request.TipoUtente;
import it.gruppoinit.regulus.schema.billpaymentnotify.response.MSG.ESITO;
import it.gruppoinit.regulus.schema.getbilldetails.response.DATIOUTPUT.RATE.RATA;
import it.gruppoinit.regulus.schema.getdebtsituation.request.MSG.RICHIESTA;
import it.gruppoinit.regulus.schema.getdebtsituation.response.DATIOUTPUT.DOCUMENTO;
import javassist.tools.rmi.RemoteException;

/**
 * WS per il Sistema di Pagamenti REGULUS
 * 
 * @author francescop
 * 
 */
public class SistemaPagamentiBindingImpl implements it.gruppoinit.regulus.gestoreincassi.SistemaPagamenti, Serializable {

    private static final long serialVersionUID = -170493866217120519L;
    private static final Logger log = LoggerFactory.getLogger(SistemaPagamentiBindingImpl.class);
    private ExternalDBResolver externalDBResolver;
    private VwOneriregulusService vwOneriregulusService;
    private IstanzeoneriService istanzeoneriService;
    private IstanzeOneriRegulusService istanzeOneriRegulusService;
    private VerticalizzazioniService verticalizzazioniService;
    private TipicausalioneriService tipicausalioneriService;
    private IstanzeService istanzeService;
    private AnagrafeService anagrafeService;

    @Autowired
    public void setExternalDBResolver(ExternalDBResolver externalDBResolver) {

	this.externalDBResolver = externalDBResolver;
    }

    @Autowired
    public void setVwOneriregulusService(VwOneriregulusService vwOneriregulusService) {

	this.vwOneriregulusService = vwOneriregulusService;
    }

    @Autowired
    public void setIstanzeoneriService(IstanzeoneriService istanzeoneriService) {

	this.istanzeoneriService = istanzeoneriService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setTipicausalioneriService(TipicausalioneriService tipicausalioneriService) {

	this.tipicausalioneriService = tipicausalioneriService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    /**
     * Metodo WebService.<br/>
     * Il metodo prende in ingresso un xml in formato stringa. Effettuo l'unmarshall della stringa nell'oggetto
     * it.gruppoinit.regulus.schema.MSG da cui ricavo il tipo funzione. A seconda della funzione viene effettuato
     * l'unmarshall dell'oggetto MSG corrispondente. Per ogni funzione c'è un metodo privato che si occupa
     * dell'elaborazione.<br/>
     * I metodi sono:<br/>
     * 1)getDebtSituation<br/>
     * 2)getBillDetails<br/>
     * 3)getBillPaymentNotify<br/>
     * 
     * 
     */
    public java.lang.String richiesta(java.lang.String xmlInput) throws java.rmi.RemoteException {

	// §§§BEGIN§§§
	String result = "";
	if (log.isDebugEnabled()) {
	    log.debug("WS Sistema Pagamenti Regulus received a MessageRequest: " + xmlInput);
	}
	if (StringUtils.isNotBlank(xmlInput)) {
	    try {
		/*
		 * Faccio l'unmarshal della stringa ricevuta. L'unmarshal mi produrrà un oggetto di base MSG. L'oggetto
		 * MSG contiene un elemento TipoCodiceFunzione che mi descrimina il contenuto dell'elemento DATIFORNITI.
		 */
		JAXBContext context = JAXBContext.newInstance("it.gruppoinit.regulus.schema");
		Unmarshaller um = context.createUnmarshaller();
		InputStream bis = new ByteArrayInputStream(xmlInput.getBytes());
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader reader = inputFactory.createXMLStreamReader(bis);
		it.gruppoinit.regulus.schema.MSG msgRequestTemp = (it.gruppoinit.regulus.schema.MSG) um.unmarshal(reader);
		TipoCodiceFunzione tipofunzione = msgRequestTemp.getCodiceFunzione();
		if (log.isDebugEnabled()) {
		    log.debug("WS Sistema Pagamenti Regulus. MSG request :\nTitolo= " +
			    msgRequestTemp.getTITOLO() +
			    "\nData Richiesta= " +
			    msgRequestTemp.getRICHIESTA().getDataRichiesta() +
			    "\nIdentificativo Richiesta= " +
			    msgRequestTemp.getRICHIESTA().getIdentificativoRichiesta() +
			    "\nVersione messaggio= " +
			    msgRequestTemp.getVersioneMessaggio() +
			    "\nCodice Utente= " +
			    msgRequestTemp.getCANALE().getCodiceUtente() +
			    "\nIdentificativo Canale= " +
			    msgRequestTemp.getCANALE().getIdentificativoCanale() +
			    "\nTipo codice funzione: " +
			    tipofunzione.value());
		}
		if (tipofunzione != null) {
		    if (tipofunzione.equals(TipoCodiceFunzione.GET_DEBT_SITUATION)) {
			/*
			 * Effettuo l'unmarshall della stringa xmlInput nell'oggetto MSG di GetDebtSituation
			 */
			JAXBContext contextdebtSituation = JAXBContext.newInstance("it.gruppoinit.regulus.schema.getdebtsituation.request");
			Unmarshaller umdebtSituation = contextdebtSituation.createUnmarshaller();
			InputStream bisdebtSituation = new ByteArrayInputStream(xmlInput.getBytes());
			XMLInputFactory inputFactorydebtSituation = XMLInputFactory.newInstance();
			XMLStreamReader readerdebtSituation = inputFactorydebtSituation.createXMLStreamReader(bisdebtSituation);
			// Unmarshaller umdebtSituationVal = this.validateGetDebtSituationReq(umdebtSituation);
			it.gruppoinit.regulus.schema.getdebtsituation.request.MSG msgRequest = (it.gruppoinit.regulus.schema.getdebtsituation.request.MSG) umdebtSituation
				.unmarshal(readerdebtSituation);
			result = this.getDebtSituation(msgRequest);
		    } else if (tipofunzione.equals(TipoCodiceFunzione.GET_BILL_DETAILS)) {
			/*
			 * Effettuo l'unmarshall della stringa xmlInput nell'oggetto MSG di GetBillDetails
			 */
			JAXBContext contextBillDetails = JAXBContext.newInstance("it.gruppoinit.regulus.schema.getbilldetails.request");
			Unmarshaller umBillDetails = contextBillDetails.createUnmarshaller();
			InputStream bisBillDetails = new ByteArrayInputStream(xmlInput.getBytes());
			XMLInputFactory inputFactoryBillDetails = XMLInputFactory.newInstance();
			XMLStreamReader readerBillDetails = inputFactoryBillDetails.createXMLStreamReader(bisBillDetails);
			// Unmarshaller umbillDetailsVal = this.validateGetBillDetailsReq(umBillDetails);
			it.gruppoinit.regulus.schema.getbilldetails.request.MSG msgRequest = (it.gruppoinit.regulus.schema.getbilldetails.request.MSG) umBillDetails
				.unmarshal(readerBillDetails);
			result = this.getBillDetails(msgRequest);
		    } else if (tipofunzione.equals(TipoCodiceFunzione.BILL_PAYMENT_NOTIFY)) {
			/*
			 * Effettuo l'unmarshall della stringa xmlInput nell'oggetto MSG di BillPaymentNotify
			 */
			JAXBContext contextPayment = JAXBContext.newInstance("it.gruppoinit.regulus.schema.billpaymentnotify.request");
			Unmarshaller umPayment = contextPayment.createUnmarshaller();
			InputStream bisPayment = new ByteArrayInputStream(xmlInput.getBytes());
			XMLInputFactory inputFactoryPayment = XMLInputFactory.newInstance();
			XMLStreamReader readerPayment = inputFactoryPayment.createXMLStreamReader(bisPayment);
			MSG msgRequest = (MSG) umPayment.unmarshal(readerPayment);
			result = this.getBillPaymentNotify(msgRequest);
			return result;
		    }
		}
	    } catch (Exception e) {
		if (log.isDebugEnabled()) {
		    log.error("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS (214 - errore interno unmarshaller xmlInput): " + e.getMessage());
		}
		throw new RemoteException(e);
	    }
	}
	return result;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Metodo per il recupero delle proprietà di connessione
     * 
     * @param idcomune_alias
     */
    private void getConnection(String idcomune_alias) {

	// §§§BEGIN§§§
	Properties dbProps = externalDBResolver.getConnectionProperties(idcomune_alias);
	Properties props = externalDBResolver.checkToken(dbProps.getProperty(WebConstants.TOKEN));
	ORMHelper.setIdcomune(props.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(props.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(dbProps));
	ORMHelper.setToken(dbProps.getProperty(WebConstants.TOKEN));
	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService
		.getVerticalizzazioniparametri(RegulusConstants.VERTICALIZZAZIONE_REGULUS, RegulusConstants.VERTICALIZZAZIONE_REGULUS_PARAMETRO);
	if (verticalizzazioniparametri == null) {
	    throw new RuntimeException("VERTICALIZZAZIONE SISTEMA PAGAMENTI NON ATTIVA");
	} else {
	    if (!verticalizzazioniparametri.getValore().equals(RegulusConstants.VERTICALIZZAZIONE_REGULUS_PARAMETRO_VALORE)) {
		throw new RuntimeException("VERTICALIZZAZIONE SISTEMA PAGAMENTI REGULUS NON ATTIVA");
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("\nIDCOMUNE= " +
		    ORMHelper.getIdcomune() +
		    "\nIDCOMUNEALIAS= " +
		    ORMHelper.getIdcomuneAlias() +
		    "\nVerticalizzazione attiva= " +
		    verticalizzazioniparametri.getValore() +
		    "\n");
	}
	// §§§END§§§
    }

    /*
     * Validazione della richiesta : GetDebtSituation
     */
    @SuppressWarnings("unused")
    private Unmarshaller validateGetDebtSituationReq(Unmarshaller umdebtSituation) {

	// §§§BEGIN§§§
	SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	Schema messageSchema = null;
	try {
	    DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
	    Resource resource = resourceLoader.getResource("../regulus/XSD-GestoreIncassi/WM_GetDebtSituationReq.xsd");
	    messageSchema = sf.newSchema(resource.getFile());
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw new RuntimeException("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS (Errore validazione GetDebtSituationReq): " +
		    RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_FILE_NOT_FOUND));
	}
	umdebtSituation.setSchema(messageSchema);
	return umdebtSituation;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /*
     * Validazione della richiesta : GetBillDetails
     */
    @SuppressWarnings("unused")
    private Unmarshaller validateGetBillDetailsReq(Unmarshaller umBillDetails) {

	// §§§BEGIN§§§
	SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	Schema messageSchema = null;
	try {
	    DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
	    Resource resource = resourceLoader.getResource("../regulus/XSD-GestoreIncassi/WM_GetBillDetailsReq.xsd");
	    messageSchema = sf.newSchema(resource.getFile());
	} catch (Exception e) {
	    throw new RuntimeException("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS (Errore validazione GetBillDetailsReq): " +
		    RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_FILE_NOT_FOUND));
	}
	umBillDetails.setSchema(messageSchema);
	return umBillDetails;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /*
     * Validazione della richiesta : BillPaymentNotify
     */
    @SuppressWarnings("unused")
    private Unmarshaller validateBillPaymentNotifyReq(Unmarshaller umBillPayment) {

	// §§§BEGIN§§§
	SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	Schema messageSchema = null;
	try {
	    DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
	    Resource resource = resourceLoader.getResource("../regulus/XSD-GestoreIncassi/WM_BillPaymentNotifyReq.xsd");
	    messageSchema = sf.newSchema(resource.getFile());
	} catch (Exception e) {
	    throw new RuntimeException("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS (Errore validazione BillPaymentNotifyReq): " +
		    RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_FILE_NOT_FOUND));
	}
	umBillPayment.setSchema(messageSchema);
	return umBillPayment;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * WM_GetDebtSituation
     * 
     * Questo metodo si occupa di restituire la situazione contabile di una Anagrafica a partire dal codicefiscale o
     * partitaiva. Gli oneri vengono raggruppati per numero documento. Ogni documento quindi avrà delle rate che sono i
     * singoli Oneri. Oltre al codicefiscale/partitaiva possono essere passati altri parametri come il codice tributo
     * che corrisponde al codice delle Causalioneri.
     */
    private String getDebtSituation(it.gruppoinit.regulus.schema.getdebtsituation.request.MSG msgRequest) {

	// §§§BEGIN§§§
	boolean codiceFiscaleValidate = true;
	String result = "";
	/*
	 * ESITO SENZA ERRORE
	 */
	it.gruppoinit.regulus.schema.getdebtsituation.response.MSG.ESITO esito = new it.gruppoinit.regulus.schema.getdebtsituation.response.MSG.ESITO();
	esito.setRC(RegulusEsitoConstants.I_NO_ERROR.toString());
	esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.I_NO_ERROR).toString());
	esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.I_NO_ERROR));
	esito.setRCADD("");
	/*
	 * Dati Forniti da Regulus
	 */
	RICHIESTA richiesta = msgRequest.getRICHIESTA();
	it.gruppoinit.regulus.schema.getdebtsituation.request.DATIFORNITI datiforniti = msgRequest.getDATIFORNITI();
	String codiceFiscale = datiforniti.getCODFISC();
	// Validazione CODICEFISCALE
	if (codiceFiscale == null || codiceFiscale.equals("")) {
	    codiceFiscaleValidate = false;
	    esito.setRC(RegulusEsitoConstants.E_CAMPO_OBBLIGATORIO_MANCANTE.toString());
	    esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_CAMPO_OBBLIGATORIO_MANCANTE).toString());
	    esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_CAMPO_OBBLIGATORIO_MANCANTE));
	}
	String annoDocumento = datiforniti.getANNODOC();
	String codiceEnte = datiforniti.getCODENTE();
	String codiceTributo = datiforniti.getCODTRIBUTO();
	String tipoCodiceEnte = datiforniti.getTIPOCODENTE();
	XMLGregorianCalendar datafine = datiforniti.getDATAFINE();
	XMLGregorianCalendar datainizio = datiforniti.getDATAINIZIO();
	Date DATAINIZIO = null;
	Date DATAFINE = null;
	if (datainizio != null) {
	    DATAINIZIO = datainizio.toGregorianCalendar().getTime();
	}
	if (datafine != null) {
	    DATAFINE = datafine.toGregorianCalendar().getTime();
	}
	if (log.isDebugEnabled()) {
	    log.debug("WS Sistema Pagamenti Regulus. getDebtSituation REQUEST:\nCodiceFiscale= " +
		    codiceFiscale +
		    "\nAnno Documento= " +
		    annoDocumento +
		    "\nCodice Ente= " +
		    codiceEnte +
		    "\nTipo Codice Ente= " +
		    tipoCodiceEnte +
		    "\nCodice Tributo= " +
		    codiceTributo +
		    "\nData Inizio= " +
		    datainizio +
		    "\nData Fine= " +
		    datafine +
		    "\n");
	}
	/*
	 * Inizio Risposta
	 */
	it.gruppoinit.regulus.schema.getdebtsituation.response.MSG msg = new it.gruppoinit.regulus.schema.getdebtsituation.response.MSG();
	try {
	    /*
	     * AUTENTICAZIONE -- RECUPERO TOKEN
	     */
	    String idcomune_alias = codiceEnte;
	    this.getConnection(idcomune_alias);
	    /*
	     * 
	     */
	    /*
	     * Recupero dati da ISTANZEONERI
	     */
	    List<VwOneriregulus> vwoneriList = new ArrayList<VwOneriregulus>();
	    List<Istanzeoneri> istanzeOneriList = new ArrayList<Istanzeoneri>();
	    Set<String> nrDocumentiSet = new HashSet<String>();
	    // Controllo che il codice fiscale è stato inserito correttamente.
	    if (codiceFiscaleValidate) {
		// Controllo se il campo codiceFiscale inviato ha una lunghezza di 16(LUNGHEZZA_CODICEFISCALE) o
		// 11(LUNGHEZZA_PARTITAIVA)
		if (codiceFiscale.length() != RegulusConstants.LUNGHEZZA_CODICEFISCALE
			&& codiceFiscale.length() != RegulusConstants.LUNGHEZZA_PARTITAIVA) {
		    esito.setRC(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO.toString());
		    esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO).toString());
		    esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO));
		} else {
		    vwoneriList = vwOneriregulusService.getDebtSituationOneriRegulus(codiceFiscale);
		    // RECUPERO LE ISTANZEONERI
		    istanzeOneriList = istanzeoneriService.getDebtSituationIstanzeOneri(codiceFiscale, DATAINIZIO, DATAFINE, annoDocumento,
			    codiceTributo);
		    nrDocumentiSet = new HashSet<String>();
		    List<Istanzeoneri> istanzeoneriBolli = new ArrayList<Istanzeoneri>();
		    for (Istanzeoneri oneriTemp : istanzeOneriList) {
			// creo un set di NR_DOCUMENTO per controllare quanti documenti sono presenti
			nrDocumentiSet.add(oneriTemp.getNrDocumento());
			// Determino quali istanzeoneri è un bollo
			// Aggiunto controllo che il tipo causala onere non sia nullo (Gianpaolo)
			Integer codiceCausale = null;
			if (EntityUtils.getNestedProperty(oneriTemp.getTipicausalioneri(), "id.codice") != null) {
			    codiceCausale = oneriTemp.getTipicausalioneri().getId().getCodice();
			}
			if (codiceTributo == null || codiceTributo.equals("")) {
			    if (codiceCausale != null) {
				Istanzeoneri istanzeoneriBollo = istanzeoneriService.getOnereBolloByOnere(codiceCausale, oneriTemp.getIstanza());
				if (EntityUtils.getNestedProperty(istanzeoneriBollo, "id.codice") != null) {
				    istanzeoneriBolli.add(oneriTemp);
				}
			    }
			}
		    }
		    istanzeOneriList.removeAll(istanzeoneriBolli);
		}
	    }
	    /*
	     * FINE
	     */
	    /*
	     * Popolo il messaggio di risposta
	     */
	    msg.setCodiceFunzione(TipoCodiceFunzione.GET_DEBT_SITUATION.value());
	    msg.setNomeFunzione(TipoCodiceFunzione.GET_DEBT_SITUATION.value());
	    it.gruppoinit.regulus.schema.getdebtsituation.response.MSG.RISPOSTA risposta = new it.gruppoinit.regulus.schema.getdebtsituation.response.MSG.RISPOSTA();
	    GregorianCalendar dataRispostaCalendar = new GregorianCalendar();
	    XMLGregorianCalendar dataRisposta = Utilities.getXMLGregorianCalendar(dataRispostaCalendar);
	    dataRisposta.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
	    dataRisposta.setFractionalSecond(new BigDecimal(0));
	    risposta.setDataRisposta(dataRisposta.toString());
	    risposta.setIdentificativoRisposta(richiesta.getIdentificativoRichiesta());
	    msg.setRISPOSTA(risposta);
	    msg.setESITO(esito);
	    msg.setTITOLO(TipoCodiceFunzione.GET_DEBT_SITUATION.value());
	    msg.setVersioneMessaggio(RegulusConstants.VERSIONE_MESSAGGIO);
	    // DATIOUTPUT
	    it.gruppoinit.regulus.schema.getdebtsituation.response.DATIOUTPUT datioutput = new it.gruppoinit.regulus.schema.getdebtsituation.response.DATIOUTPUT();
	    // Controllo se ci sono campi mancanti.
	    if (codiceFiscaleValidate) {
		// CONTROLLO CHE LA LISTA NON SIA VUOTA
		if (!istanzeOneriList.isEmpty() && !vwoneriList.isEmpty()) {
		    // NUMERO DEI DOCUMENTI
		    datioutput.setNUMDOCUMENTI(((Integer) nrDocumentiSet.size()).toString());
		    List<DOCUMENTO> list = new ArrayList<DOCUMENTO>();
		    String nrDoc = "";
		    List<Integer> causaliOneriBolli = new ArrayList<Integer>();
		    for (Istanzeoneri oneri : istanzeOneriList) {
			// CONTROLLO SE E' GIA' STATO AGGIUNTA UNA ISTANZAONERE PER UN DETERMINATO DOCUMENTO
			if (!nrDoc.equals(oneri.getNrDocumento())) {
			    Tipicausalioneri tipicausalioneri = tipicausalioneriService.findById(oneri.getTipicausalioneri().getId());
			    nrDoc = oneri.getNrDocumento();
			    VwOneriregulus vwOneriregulus = new VwOneriregulus();
			    // RECUPERO LA RIGA DELLA VISTA CHE HA UN DETERMINATO NR_DOCUMENTO
			    for (VwOneriregulus vwOneriregulusTemp : vwoneriList) {
				if (vwOneriregulusTemp.getId().getNrDocumento().equals(nrDoc)) {
				    vwOneriregulus = vwOneriregulusTemp;
				    break;
				}
			    }
			    DOCUMENTO documento = new DOCUMENTO();
			    Calendar data = GregorianCalendar.getInstance();
			    data.setTime(oneri.getData());
			    documento.setANNODOC(((Integer) data.get(Calendar.YEAR)).toString());
			    documento.setCODENTE(codiceEnte);
			    documento.setTIPOCODENTE(RegulusConstants.TIPO_CODICE_ENTE_ISTAT);
			    documento.setCODTIPODOC("");
			    documento.setCODTRIBUTO(tipicausalioneri.getId().getCodice().toString());
			    documento.setDESCRENTE(RegulusConstants.DESCRIZIONE_ENTE);
			    documento.setDESCRTIPODOC("");
			    documento.setDESCRTRIBUTO(tipicausalioneri.getCoDescrizione());
			    // DATAEMISSIONE
			    GregorianCalendar dataEmissione = new GregorianCalendar();
			    dataEmissione.setTime(oneri.getData());
			    XMLGregorianCalendar DATAEMISSIONE = Utilities.getXMLGregorianCalendar(dataEmissione);
			    DATAEMISSIONE.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			    DATAEMISSIONE.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED,
				    DatatypeConstants.FIELD_UNDEFINED);
			    documento.setDATAEMISSIONE(DATAEMISSIONE);
			    // DATASCADENZA
			    GregorianCalendar dataScadenza = new GregorianCalendar();
			    if (oneri.getDatascadenza() != null) {
				dataScadenza.setTime(oneri.getDatascadenza());
				XMLGregorianCalendar DATASCADENZA = Utilities.getXMLGregorianCalendar(dataScadenza);
				DATASCADENZA.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
				DATASCADENZA.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED,
					DatatypeConstants.FIELD_UNDEFINED);
				documento.setDATASCADENZA(DATASCADENZA);
			    } else {
				documento.setDATASCADENZA(null);
			    }
			    // Aggiunto la verifica che tipicausalioneri.getCausalebollo() non sia nullo
			    //(Gianpaolo)
			    Integer codiceCausaleBollo = null;
			    if (EntityUtils.getNestedProperty(tipicausalioneri.getCausalebollo(), "id.codice") != null) {
				codiceCausaleBollo = tipicausalioneri.getCausalebollo().getId().getCodice();
			    }
			    Istanze istanza = oneri.getIstanza();
			    List<Istanzeoneri> istanzeoneriBolloList = new ArrayList<Istanzeoneri>();
			    BigDecimal importoBollo = new BigDecimal(0);
			    if (codiceCausaleBollo != null) {
				BigDecimal importBolloTotale = new BigDecimal(0);
				istanzeoneriBolloList = istanzeoneriService.getOnereBollo(codiceCausaleBollo, istanza);
				for (Istanzeoneri istanzeoneri : istanzeoneriBolloList) {
				    // Aggiunto controllo che il prezzo non sia null
				    //(Gianpaolo)
				    if (istanzeoneri.getPrezzo() != null) {
					importBolloTotale = importBolloTotale.add(istanzeoneri.getPrezzo());
				    } else {
					importBolloTotale = importBolloTotale.add(new BigDecimal(0));
				    }
				}
				if (istanzeoneriBolloList != null && istanzeoneriBolloList.size() != 0) {
				    importoBollo = importoBollo.add(importBolloTotale);
				    causaliOneriBolli.add(codiceCausaleBollo);
				}
			    }
			    // L'importo deve essere inviato senza virgola. Le ultime due cifre vengono assunte come
			    // decimali.
			    // Sommo il valore del BOLLO. Se non è presente sommo 0.
			    //Aggiunto controllo se l'importo è diverso da null
			    // (Gianpaolo)
			    BigDecimal importoOnereRegulus = new BigDecimal(0);
			    if (vwOneriregulus.getImporto() != null) {
				importoOnereRegulus = vwOneriregulus.getImporto();
			    }
			    BigDecimal importo = importoOnereRegulus.add(importoBollo);
			    importo = importo.setScale(RegulusConstants.NUMERO_CIFRE_DECIMALI_REGULUS);
			    String prezzo = importo.toString();
			    prezzo = prezzo.replace(".", "");
			    documento.setIMPTOTDOC(prezzo);
			    Integer numerorateInScadenza = 0;
			    BigDecimal importoResiduo = new BigDecimal(0);
			    // SCORRO LA LISTA DI ISTANZEONERI PER RECUPERARE IL NUMERO DI RATE IN SCADENZA E
			    // L'IMPORTO
			    // RESIDUO PER UN DOCUMENTO.
			    for (Istanzeoneri oneriTemp : istanzeOneriList) {
				if (nrDoc.equals(oneriTemp.getNrDocumento())) {
				    numerorateInScadenza++;
				    // Aggiunto controllo che "prezzo sia diverso da null"
				    //(Gianpaolo)
				    if (oneriTemp.getPrezzo() != null) {
					importoResiduo = importoResiduo.add(oneriTemp.getPrezzo());
				    } else {
					importoResiduo = importoResiduo.add(new BigDecimal(0));
				    }
				}
			    }
			    // L'importo deve essere inviato senza virgola. Le ultime due cifre vengono assunte come
			    // decimali.
			    importoResiduo = importoResiduo.add(importoBollo);
			    importoResiduo = importoResiduo.setScale(RegulusConstants.NUMERO_CIFRE_DECIMALI_REGULUS);
			    String prezzoResiduo = importoResiduo.toString();
			    prezzoResiduo = prezzoResiduo.replace(".", "");
			    documento.setIMPTOTRESIDDOC(prezzoResiduo);
			    documento.setNUMDOC(oneri.getNrDocumento());
			    documento.setNUMRATE((vwOneriregulus.getNumerorate()).shortValue());
			    documento.setNUMRATEINSCAD(numerorateInScadenza.shortValue());
			    documento.setSTATOPAG(RegulusConstants.STATO_DEL_PAGAMENTO_DA_PAGARE);
			    list.add(documento);
			}
		    }
		    if (codiceTributo == null || codiceTributo.equals("")) {
			Integer numerodocumenti = Integer.parseInt(datioutput.getNUMDOCUMENTI()) - causaliOneriBolli.size();
			datioutput.setNUMDOCUMENTI(numerodocumenti.toString());
		    }
		    // datioutput.setNUMDOCUMENTI(new Integer(istanzeOneriList.size()).toString());
		    datioutput.getDOCUMENTO().addAll(list);
		} else {
		    if ((Integer.valueOf(codiceFiscale.length())).compareTo(RegulusConstants.LUNGHEZZA_CODICEFISCALE) != 0
			    && (Integer.valueOf(codiceFiscale.length())).compareTo(RegulusConstants.LUNGHEZZA_PARTITAIVA) != 0) {
			esito.setRC(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO.toString());
			esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO).toString());
			esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO));
		    } else {
			// ESITO: DOCUMENTO NON TROVATO
			esito.setRC(RegulusEsitoConstants.E_DOCUMENTO_NON_TROVATO.toString());
			esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_DOCUMENTO_NON_TROVATO).toString());
			esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_DOCUMENTO_NON_TROVATO));
		    }
		}
	    }
	    msg.setDATIOUTPUT(datioutput);
	    /*
	     * Effettuo il marshalling del messaggio
	     */
	    try {
		JAXBContext jc = JAXBContext.newInstance("it.gruppoinit.regulus.schema.getdebtsituation.response");
		Marshaller m = jc.createMarshaller();
		/*
		 * VALIDAZIONE RESPONSE
		 */
		// SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		// Schema messageSchema = null;
		// try {
		// DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
		// Resource resource = resourceLoader
		// .getResource("../regulus/XSD-GestoreIncassi/WM_GetDebtSituationResp.xsd");
		// messageSchema = sf.newSchema(resource.getFile());
		// } catch (SAXException e) {
		// esito.setRC(RegulusEsitoConstants.E_SAX_PARSE_EXCEPTION.toString());
		// esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_SAX_PARSE_EXCEPTION)
		// .toString());
		// esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_SAX_PARSE_EXCEPTION));
		// esito.setRCADD("");
		// // Setto l'oggetto ESITO
		// msg.setESITO(esito);
		// // Poichè si è verificato un errore setto a MSG un oggetto DATIOUTPUT vuoto.
		// datioutput = new it.gruppoinit.regulus.schema.getdebtsituation.response.DATIOUTPUT();
		// msg.setDATIOUTPUT(datioutput);
		// }
		// m.setSchema(messageSchema);
		/*
		 * Proprietà per formattare l'output.
		 */
		// m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter stringWriter = new StringWriter();
		m.marshal(msg, stringWriter);
		stringWriter.flush();
		result = stringWriter.toString();
	    } catch (JAXBException e) {
		if (log.isDebugEnabled()) {
		    log.error("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getDebtSituation [Marshall]: " + e.getMessage());
		}
		throw new RuntimeException("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getDebtSituation [Marshall]: " + e.getMessage());
	    }
	} catch (Exception e) {
	    // Controllo di che tipo è l'errore
	    // Generic Exception
	    if (e instanceof Exception) {
		esito.setRC(RegulusEsitoConstants.E_GENERIC_EXCEPTION.toString());
		esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_GENERIC_EXCEPTION).toString());
		esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_GENERIC_EXCEPTION));
	    }
	    // IOException
	    if (e instanceof IOException) {
		esito.setRC(RegulusEsitoConstants.E_IO_EXCEPTION.toString());
		esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_IO_EXCEPTION).toString());
		esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_IO_EXCEPTION));
	    }
	    // SQLException
	    if (e instanceof SQLException) {
		esito.setRC(RegulusEsitoConstants.E_SQL_EXCEPTION.toString());
		esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_SQL_EXCEPTION).toString());
		esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_SQL_EXCEPTION));
	    }
	    if (e instanceof NumberFormatException) {
		esito.setRC(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO.toString());
		esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO).toString());
		esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO));
	    }
	    // Setto l'oggetto ESITO
	    msg.setESITO(esito);
	    // Poichè si è verificato un errore setto a MSG un oggetto DATIOUTPUT vuoto.
	    it.gruppoinit.regulus.schema.getdebtsituation.response.DATIOUTPUT datioutput = new it.gruppoinit.regulus.schema.getdebtsituation.response.DATIOUTPUT();
	    msg.setDATIOUTPUT(datioutput);
	    JAXBContext jc;
	    try {
		jc = JAXBContext.newInstance("it.gruppoinit.regulus.schema.getdebtsituation.response");
		Marshaller m = jc.createMarshaller();
		StringWriter stringWriter = new StringWriter();
		m.marshal(msg, stringWriter);
		stringWriter.flush();
		result = stringWriter.toString();
	    } catch (JAXBException e1) {
		if (log.isDebugEnabled()) {
		    log.error("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getDebtSituation [Marshall - ERROR]: " + e1.toString());
		}
		throw new RuntimeException("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS.. getDebtSituation [Marshall - ERROR]: " + e1.toString());
	    }
	    if (log.isDebugEnabled()) {
		log.error("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getDebtSituation: " + e.toString());
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("WS Sistema Pagamenti Regulus. getDebtSituation RESPONSE (604): " + result);
	}
	return result;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * WM_GetBillDetails
     * 
     * Metodo che restituisce il dettaglio di un documento.
     */
    private String getBillDetails(it.gruppoinit.regulus.schema.getbilldetails.request.MSG msgRequest) {

	// §§§BEGIN§§§
	boolean codiceFiscaleValidate = true;
	boolean codiceTributoValidate = true;
	boolean annoDocumentoValidate = true;
	boolean numeroDocValidate = true;
	boolean codiceEnteValidate = true;
	boolean tipoCodiceEnteValidate = true;
	boolean numDocValidateInteger = true;
	String result = "";
	/*
	 * ESITO
	 */
	it.gruppoinit.regulus.schema.getbilldetails.response.MSG.ESITO esito = new it.gruppoinit.regulus.schema.getbilldetails.response.MSG.ESITO();
	esito.setRC(RegulusEsitoConstants.I_NO_ERROR.toString());
	esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.I_NO_ERROR).toString());
	esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.I_NO_ERROR));
	esito.setRCADD("");
	/*
	 * Dati Forniti da Regulus
	 */
	it.gruppoinit.regulus.schema.getbilldetails.request.MSG.RICHIESTA richiesta = msgRequest.getRICHIESTA();
	it.gruppoinit.regulus.schema.getbilldetails.request.DATIFORNITI datiforniti = msgRequest.getDATIFORNITI();
	String codiceFiscale = datiforniti.getCODFISC();
	String annoDocumento = datiforniti.getANNODOC();
	String codiceEnte = datiforniti.getCODENTE();
	String codiceTributo = datiforniti.getCODTRIBUTO();
	String numDoc = datiforniti.getNUMDOC();
	String tipoCodiceEnte = datiforniti.getTIPOCODENTE();
	if (log.isDebugEnabled()) {
	    log.debug("WS Sistema Pagamenti Regulus. getBillDetails REQUEST:\nCodiceFiscale= " +
		    codiceFiscale +
		    "\nAnno Documento= " +
		    annoDocumento +
		    "\nCodice Ente= " +
		    codiceEnte +
		    "\nTipo Codice Ente= " +
		    tipoCodiceEnte +
		    "\nCodice Tributo= " +
		    codiceTributo +
		    "\nNumero Documento= " +
		    numDoc +
		    "\n");
	}
	// Validazione
	if (annoDocumento.equals("") || codiceFiscale.equals("") || codiceEnte.equals("") || codiceTributo.equals("") || numDoc.equals("")
		|| tipoCodiceEnte.equals("")) {
	    codiceFiscaleValidate = false;
	    codiceTributoValidate = false;
	    annoDocumentoValidate = false;
	    numeroDocValidate = false;
	    codiceEnteValidate = false;
	    tipoCodiceEnteValidate = false;
	    numDocValidateInteger = false;
	    esito.setRC(RegulusEsitoConstants.E_CAMPO_OBBLIGATORIO_MANCANTE.toString());
	    esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_CAMPO_OBBLIGATORIO_MANCANTE).toString());
	    esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_CAMPO_OBBLIGATORIO_MANCANTE));
	}
	/*
	 * Inizio Risposta
	 */
	it.gruppoinit.regulus.schema.getbilldetails.response.MSG msg = new it.gruppoinit.regulus.schema.getbilldetails.response.MSG();
	try {
	    /*
	     * AUTENTICAZIONE -- RECUPERO TOKEN
	     */
	    String idcomune_alias = codiceEnte;
	    this.getConnection(idcomune_alias);
	    /*
	     * 
	     */
	    /*
	     * Popolo il messaggio di risposta
	     */
	    msg.setCodiceFunzione(TipoCodiceFunzione.GET_BILL_DETAILS.value());
	    msg.setNomeFunzione(TipoCodiceFunzione.GET_BILL_DETAILS.value());
	    it.gruppoinit.regulus.schema.getbilldetails.response.MSG.RISPOSTA risposta = new it.gruppoinit.regulus.schema.getbilldetails.response.MSG.RISPOSTA();
	    GregorianCalendar dataRispostaCalendar = new GregorianCalendar();
	    XMLGregorianCalendar dataRisposta = Utilities.getXMLGregorianCalendar(dataRispostaCalendar);
	    dataRisposta.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
	    dataRisposta.setFractionalSecond(new BigDecimal(0));
	    risposta.setDataRisposta(dataRisposta.toString());
	    risposta.setIdentificativoRisposta(richiesta.getIdentificativoRichiesta());
	    msg.setRISPOSTA(risposta);
	    msg.setESITO(esito);
	    msg.setTITOLO(TipoCodiceFunzione.GET_BILL_DETAILS.value());
	    msg.setVersioneMessaggio(RegulusConstants.VERSIONE_MESSAGGIO);
	    it.gruppoinit.regulus.schema.getbilldetails.response.DATIOUTPUT datioutput = new it.gruppoinit.regulus.schema.getbilldetails.response.DATIOUTPUT();
	    // Controllo se ci sono campi mancanti.
	    if (codiceFiscaleValidate && codiceTributoValidate && annoDocumentoValidate && numeroDocValidate && codiceEnteValidate
		    && tipoCodiceEnteValidate && numDocValidateInteger) {
		/*
		 * Recupero dati da ISTANZEONERI
		 */
		List<Istanzeoneri> istanzeOneriList = new ArrayList<Istanzeoneri>();
		if (codiceFiscale.length() != RegulusConstants.LUNGHEZZA_CODICEFISCALE
			&& codiceFiscale.length() != RegulusConstants.LUNGHEZZA_PARTITAIVA) {
		    esito.setRC(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO.toString());
		    esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO).toString());
		    esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO));
		} else {
		    istanzeOneriList = istanzeoneriService.getBillDetailsIstanzeOneri(numDoc, codiceFiscale, annoDocumento, codiceTributo);
		}
		/*
		 * Popolo l'elemento DATIOUTPUT
		 */
		if (!istanzeOneriList.isEmpty()) {
		    Istanze istanze = istanzeService.findById(new PkId(istanzeOneriList.get(0).getIstanza().getId().getCodice()));
		    /*
		     * Recupero dati del richiedente dell'istanze e eventualmente il titolare legale
		     */
		    Anagrafe richiedente = null;
		    if (istanze.getRichiedente() != null && istanze.getRichiedente().getId().getCodice() != null) {
			richiedente = anagrafeService.findById(istanze.getRichiedente().getId());
		    }
		    Anagrafe titolarelegale = null;
		    if (istanze.getTitolarelegale() != null && istanze.getTitolarelegale().getId().getCodice() != null) {
			titolarelegale = anagrafeService.findById(istanze.getTitolarelegale().getId());
		    }
		    // Popolo l'oggetto TIPOUTENTE
		    it.gruppoinit.regulus.schema.getbilldetails.response.TipoUtente utente = new it.gruppoinit.regulus.schema.getbilldetails.response.TipoUtente();
		    utente.setCODICEFISCALE(codiceFiscale);
		    String codicefiscaleUpperCase = StringUtils.upperCase(codiceFiscale);
		    // Controllo se il richiedente è diverso da null.
		    if (EntityUtils.getNestedProperty(richiedente, "id.codice") != null
			    && (StringUtils.isNotBlank(richiedente.getCodicefiscale()) || StringUtils.isNotBlank(richiedente.getPartitaiva()))) {
			String codiceFiscaleUpperRichiedente = "";
			String partitaivaUpperRichiedente = "";
			if (StringUtils.isNotBlank(richiedente.getCodicefiscale())) {
			    codiceFiscaleUpperRichiedente = StringUtils.upperCase(richiedente.getCodicefiscale());
			}
			if (StringUtils.isNotBlank(richiedente.getPartitaiva())) {
			    partitaivaUpperRichiedente = StringUtils.upperCase(richiedente.getPartitaiva());
			}
			// Controllo se il codice fiscale o partita iva è uguale a quella passata da Regulus.
			if (codiceFiscaleUpperRichiedente.equals(codicefiscaleUpperCase)
				|| partitaivaUpperRichiedente.equals(codicefiscaleUpperCase)) {
			    String formagiuridica = richiedente.getTipoanagrafe();
			    utente.setCOGNOME(richiedente.getNominativo());
			    // CONTROLLO SE E' UNA PERSONA FISICA O GIURIDICA
			    if (formagiuridica.equals("F")) {
				utente.setNOME(richiedente.getNome());
			    } else {
				utente.setNOME("");
			    }
			    if (formagiuridica.equals("G")) {
				utente.setRAGIONESOCIALE(richiedente.getNominativo());
			    } else {
				utente.setRAGIONESOCIALE("");
			    }
			} else {// Utilizzato per popolare comunque la persona fisica dell'oggetto tipoutente.
			    String formagiuridica = richiedente.getTipoanagrafe();
			    utente.setCOGNOME(richiedente.getNominativo());
			    // CONTROLLO SE E' UNA PERSONA FISICA O GIURIDICA
			    if (formagiuridica.equals("F")) {
				utente.setNOME(richiedente.getNome());
			    } else {
				utente.setNOME("");
			    }
			}
		    }
		    // Controllo se il titolarelegale è diverso da null.
		    if (EntityUtils.getNestedProperty(titolarelegale, "id.codice") != null
			    && (StringUtils.isNotBlank(titolarelegale.getCodicefiscale())
				    || StringUtils.isNotBlank(titolarelegale.getPartitaiva()))) {
			String codiceFiscaleUpperTitolareLegale = "";
			String partitaIvaUpperTitolareLegale = "";
			if (StringUtils.isNotBlank(titolarelegale.getCodicefiscale())) {
			    codiceFiscaleUpperTitolareLegale = StringUtils.upperCase(titolarelegale.getCodicefiscale());
			}
			if (StringUtils.isNotBlank(titolarelegale.getPartitaiva())) {
			    partitaIvaUpperTitolareLegale = StringUtils.upperCase(titolarelegale.getPartitaiva());
			}
			// Controllo se il codice fiscale o partita iva è uguale a quella passata da Regulus.
			if (codiceFiscaleUpperTitolareLegale.equals(codicefiscaleUpperCase)
				|| partitaIvaUpperTitolareLegale.equals(codicefiscaleUpperCase)) {
			    String formagiuridica = titolarelegale.getTipoanagrafe();
			    utente.setCOGNOME(titolarelegale.getNominativo());
			    // CONTROLLO SE E' UNA PERSONA FISICA O GIURIDICA
			    if (formagiuridica.equals("F")) {
				utente.setNOME(titolarelegale.getNome());
			    } else {
				utente.setNOME("");
			    }
			    if (formagiuridica.equals("G")) {
				utente.setRAGIONESOCIALE(titolarelegale.getNominativo());
			    } else {
				utente.setRAGIONESOCIALE("");
			    }
			} else {// Utilizzato per popolare comunque la persona giuridica dell'oggetto tipoutente.
			    String formagiuridica = titolarelegale.getTipoanagrafe();
			    if (formagiuridica.equals("G")) {
				utente.setRAGIONESOCIALE(titolarelegale.getNominativo());
			    } else {
				utente.setRAGIONESOCIALE("");
			    }
			}
		    }
		    short numRateDaPagare = 0;
		    BigDecimal importtot = new BigDecimal(0);
		    // CONTROLLO IL NUMERO DI RATE DA PAGARE
		    for (Istanzeoneri istanzeoneri : istanzeOneriList) {
			if (istanzeoneri.getDatapagamento() == null) {
			    numRateDaPagare++;
			}
			//aggiunto controllo che prezzo non sia null 
			//Gianpaolo
			if (istanzeoneri.getPrezzo() != null) {
			    importtot = importtot.add(istanzeoneri.getPrezzo());
			} else {
			    importtot = importtot.add(new BigDecimal(0));
			}
		    }
		    importtot = importtot.setScale(RegulusConstants.NUMERO_CIFRE_DECIMALI_REGULUS);
		    datioutput.setINTESTATARIO(utente);
		    datioutput.setANNODOC(annoDocumento);
		    datioutput.setCODENTE(codiceEnte);
		    datioutput.setCODTIPODOC("");
		    datioutput.setCODTRIBUTO(codiceTributo);
		    GregorianCalendar dataEmis = new GregorianCalendar();
		    // E' gia stato controllato che la lista non sia vuota
		    dataEmis.setTime(istanzeOneriList.get(0).getData());
		    XMLGregorianCalendar DATAEMIS = Utilities.getXMLGregorianCalendar(dataEmis);
		    DATAEMIS.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		    DATAEMIS.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
		    datioutput.setDATAEMISSIONE(DATAEMIS);
		    GregorianCalendar dataScad = new GregorianCalendar();
		    if (istanzeOneriList.get(0).getDatascadenza() != null) {
			dataScad.setTime(istanzeOneriList.get(0).getDatascadenza());
			XMLGregorianCalendar DATASCAD = Utilities.getXMLGregorianCalendar(dataScad);
			DATASCAD.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			DATASCAD.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
			datioutput.setDATASCADENZA(DATASCAD);
		    } else {
			datioutput.setDATASCADENZA(null);
		    }
		    datioutput.setDESCRENTE(RegulusConstants.DESCRIZIONE_ENTE);
		    datioutput.setDESCRTIPODOC("");
		    if (StringUtils.isNotBlank(codiceTributo)) {
			Tipicausalioneri tipicausalioneri = tipicausalioneriService.findById(new PkId(Integer.parseInt(codiceTributo)));
			if (EntityUtils.getNestedProperty(tipicausalioneri, "id.codice") != null) {
			    datioutput.setDESCRTRIBUTO(tipicausalioneri.getCoDescrizione());
			} else {
			    datioutput.setDESCRTRIBUTO("");
			}
		    } else {
			datioutput.setDESCRTRIBUTO("");
		    }
		    // L'importo deve essere inviato senza virgola. Le ultime due cifre vengono assunte come
		    // decimali.
		    String IMPORTOTOT = importtot.toString().replace(".", "");
		    datioutput.setIMPTOTDOC(new BigInteger(IMPORTOTOT));
		    datioutput.setMESSAGGIUTENTE(null);
		    datioutput.setNUMDOC(numDoc);
		    datioutput.setNUMRATE(((Integer) istanzeOneriList.size()).shortValue());
		    datioutput.setNUMRATEINSCAD(numRateDaPagare);
		    // GESTIONE IMPORTOBOLLO
		    Istanzeoneri istanzeoneriForBollo = istanzeOneriList.get(0);
		    Tipicausalioneri tipicausalioneri = tipicausalioneriService.findById(istanzeoneriForBollo.getTipicausalioneri().getId());
		    // Aggiunto controllo se causalebollo!=null 
		    List<Istanzeoneri> istanzeoneriBolloList = new ArrayList<Istanzeoneri>();
		    if (EntityUtils.getNestedProperty(tipicausalioneri.getCausalebollo(), "id.codice") != null) {
			istanzeoneriBolloList = istanzeoneriService.getOnereBollo(tipicausalioneri.getCausalebollo().getId().getCodice(), istanze);
		    }
		    BigDecimal importBolloTotale = new BigDecimal(0.00);
		    for (Istanzeoneri istanzeoneri : istanzeoneriBolloList) {
			importBolloTotale = importBolloTotale.add(istanzeoneri.getPrezzo());
		    }
		    if (istanzeoneriBolloList != null && !istanzeoneriBolloList.isEmpty()) {
			importBolloTotale = importBolloTotale.setScale(RegulusConstants.NUMERO_CIFRE_DECIMALI_REGULUS);
			String IMPORTOBOLLO = importBolloTotale.toString().replace(".", "");
			datioutput.setIMPBOLLO(new BigInteger(IMPORTOBOLLO));
			datioutput.setSOGLIABOLLO(new BigInteger("1"));
		    }
		    // fine
		    datioutput.setSTATOPAG(RegulusConstants.STATO_DEL_PAGAMENTO_DA_PAGARE);
		    datioutput.setTIPOCODENTE(RegulusConstants.TIPO_CODICE_ENTE_ISTAT);
		    it.gruppoinit.regulus.schema.getbilldetails.response.DATIOUTPUT.RATE rate = new it.gruppoinit.regulus.schema.getbilldetails.response.DATIOUTPUT.RATE();
		    for (Istanzeoneri istanzeoneri : istanzeOneriList) {
			if (istanzeoneri.getDatapagamento() == null) {
			    RATA rata = new RATA();
			    GregorianCalendar dataScadenza = new GregorianCalendar();
			    if (istanzeoneri.getDatascadenza() != null) {
				dataScadenza.setTime(istanzeoneri.getDatascadenza());
				XMLGregorianCalendar DATASCADENZA = Utilities.getXMLGregorianCalendar(dataScadenza);
				DATASCADENZA.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
				DATASCADENZA.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED,
					DatatypeConstants.FIELD_UNDEFINED);
				rata.setDATASCADENZA(DATASCADENZA);
			    } else {
				rata.setDATASCADENZA(null);
			    }
			    if (istanzeoneri.getNumerorata() != null) {
				rata.setPROGRESSIVO(istanzeoneri.getNumerorata().toString());
			    }
			    BigDecimal prezzo = new BigDecimal(0);
			    if (istanzeoneri.getPrezzo() != null) {
				prezzo = istanzeoneri.getPrezzo();
			    }
			    prezzo = prezzo.setScale(RegulusConstants.NUMERO_CIFRE_DECIMALI_REGULUS);
			    // L'importo deve essere inviato senza virgola. Le ultime due cifre vengono assunte come
			    // decimali.
			    String IMPDAPAGARE = prezzo.toString().replace(".", "");
			    rata.setIMPDAPAGARE(new BigInteger(IMPDAPAGARE));
			    rata.setDESCRRATA("");
			    rata.setDESCRSPESESUPPL("");
			    rata.setIMPSPESESUPPL(new BigInteger("0"));
			    rata.setIMPNOMINALE(new BigInteger(IMPDAPAGARE));
			    rate.getRATA().add(rata);
			}
		    }
		    if (rate.getRATA().isEmpty()) {
			esito.setRC(RegulusEsitoConstants.W_FATTURA_PAGATA.toString());
			esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.W_FATTURA_PAGATA).toString());
			esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.W_FATTURA_PAGATA));
		    }
		    datioutput.setRATE(rate);
		} else {
		    if (codiceFiscale.length() != RegulusConstants.LUNGHEZZA_CODICEFISCALE
			    || codiceFiscale.length() != RegulusConstants.LUNGHEZZA_PARTITAIVA) {
			esito.setRC(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO.toString());
			esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO).toString());
			esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO));
		    } else {
			esito.setRC(RegulusEsitoConstants.E_DOCUMENTO_NON_TROVATO.toString());
			esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_DOCUMENTO_NON_TROVATO).toString());
			esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_DOCUMENTO_NON_TROVATO));
		    }
		}
	    }
	    msg.setDATIOUTPUT(datioutput);
	    /*
	     * Effettuo il marshalling del messaggio
	     */
	    try {
		JAXBContext jc = JAXBContext.newInstance("it.gruppoinit.regulus.schema.getbilldetails.response");
		Marshaller m = jc.createMarshaller();
		/*
		 * VALIDAZIONE RESPONSE
		 */
		// SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		// Schema messageSchema = null;
		// try {
		// DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
		// Resource resource = resourceLoader
		// .getResource("../regulus/XSD-GestoreIncassi/WM_GetBillDetailsResp.xsd");
		// messageSchema = sf.newSchema(resource.getFile());
		// } catch (SAXException e) {
		// esito.setRC(RegulusEsitoConstants.E_SAX_PARSE_EXCEPTION.toString());
		// esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_SAX_PARSE_EXCEPTION)
		// .toString());
		// esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_SAX_PARSE_EXCEPTION));
		// esito.setRCADD("");
		// // Setto l'oggetto ESITO
		// msg.setESITO(esito);
		// // Poichè si è verificato un errore setto a MSG un oggetto DATIOUTPUT vuoto.
		// datioutput = new it.gruppoinit.regulus.schema.getbilldetails.response.DATIOUTPUT();
		// msg.setDATIOUTPUT(datioutput);
		// }
		// m.setSchema(messageSchema);
		/*
		 * Proprietà per formattare l'output.
		 */
		// m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter stringWriter = new StringWriter();
		m.marshal(msg, stringWriter);
		stringWriter.flush();
		result = stringWriter.toString();
	    } catch (JAXBException e) {
		if (log.isDebugEnabled()) {
		    log.error("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getBillDetails [Marshall]: " + e.getMessage());
		}
		throw new RuntimeException("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getBillDetails [Marshall]: " + e.getMessage());
	    }
	} catch (Exception e) {
	    // Controllo di che tipo è l'errore
	    // Generic Exception
	    if (e instanceof Exception) {
		esito.setRC(RegulusEsitoConstants.E_GENERIC_EXCEPTION.toString());
		esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_GENERIC_EXCEPTION).toString());
		esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_GENERIC_EXCEPTION));
	    }
	    // IOException
	    if (e instanceof IOException) {
		esito.setRC(RegulusEsitoConstants.E_IO_EXCEPTION.toString());
		esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_IO_EXCEPTION).toString());
		esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_IO_EXCEPTION));
	    }
	    // SQLException
	    if (e instanceof SQLException) {
		esito.setRC(RegulusEsitoConstants.E_SQL_EXCEPTION.toString());
		esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_SQL_EXCEPTION).toString());
		esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_SQL_EXCEPTION));
	    }
	    if (e instanceof NumberFormatException) {
	    }
	    // Setto l'oggetto ESITO
	    msg.setESITO(esito);
	    // Poichè si è verificato un errore setto a MSG un oggetto DATIOUTPUT vuoto.
	    it.gruppoinit.regulus.schema.getbilldetails.response.DATIOUTPUT datioutput = new it.gruppoinit.regulus.schema.getbilldetails.response.DATIOUTPUT();
	    msg.setDATIOUTPUT(datioutput);
	    JAXBContext jc;
	    try {
		jc = JAXBContext.newInstance("it.gruppoinit.regulus.schema.getbilldetails.response");
		Marshaller m = jc.createMarshaller();
		StringWriter stringWriter = new StringWriter();
		m.marshal(msg, stringWriter);
		stringWriter.flush();
		result = stringWriter.toString();
	    } catch (JAXBException e1) {
		if (log.isDebugEnabled()) {
		    log.error("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getBillDetails [Marshall - Error]: " + e1.getMessage());
		}
		throw new RuntimeException("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getBillDetails [Marshall - Error]: " + e.getMessage());
	    }
	    if (log.isDebugEnabled()) {
		log.error("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getBillDetails [Marshall - catch block]: " + e.getMessage());
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("WS Sistema Pagamenti Regulus. getBillDetails OUTPUT: \n" + result);
	}
	return result;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * WM_BillPaymentNotify
     * 
     * Metodo che restituisce solo un esito. Questo metodo viene chiamato da Regulus per inviare l'esito del pagamento.
     */
    private String getBillPaymentNotify(MSG msgRequest) {

	// §§§BEGIN§§§
	String result = "";
	/*
	 * ESITO
	 */
	it.gruppoinit.regulus.schema.billpaymentnotify.response.MSG.ESITO esito = new it.gruppoinit.regulus.schema.billpaymentnotify.response.MSG.ESITO();
	esito.setRC(RegulusEsitoConstants.I_NO_ERROR.toString());
	esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.I_NO_ERROR).toString());
	esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.I_NO_ERROR));
	esito.setRCADD("");
	/*
	 * Dati Forniti da Regulus -- i campi commentati non sono attualmente utilizzati --
	 */
	it.gruppoinit.regulus.schema.billpaymentnotify.request.MSG.RICHIESTA richiesta = msgRequest.getRICHIESTA();
	it.gruppoinit.regulus.schema.billpaymentnotify.request.DATIFORNITI datiforniti = msgRequest.getDATIFORNITI();
	// TipoAttestazione tipoAttestazione = datiforniti.getATTESTAZIONE();
	// XMLGregorianCalendar dataDoc = datiforniti.getDATADOC();
	TipoDatiPagamento tipoDatiPagamento = datiforniti.getDATIPAGAMENTO();
	// TipoDatiSpontanei tipoDatiSpontanei = datiforniti.getDATISPONTANEI();
	// String idPrenotazione = datiforniti.getIDPRENOTAZIONE();
	BigInteger impBollo = datiforniti.getIMPBOLLO();
	BigInteger impCommissioni = datiforniti.getIMPCOMMISSIONI();
	// IMPORTIDOC importiDoc = datiforniti.getIMPORTIDOC();
	BigInteger impRatePagate = datiforniti.getIMPRATEPAG();
	// BigInteger impSpeseAttest = datiforniti.getIMPSPESEATTEST();
	// BigInteger impSpeseVarie = datiforniti.getIMPSPESEVARIE();
	BigInteger impTotale = datiforniti.getIMPTOTALE();
	// TipoUtente intestatario = datiforniti.getINTESTATARIO();
	String numDoc = datiforniti.getNUMDOC();
	short numRatePag = datiforniti.getNUMRATEPAG();
	TipoUtente pagante = datiforniti.getPAGANTE();
	RATE rate = datiforniti.getRATE();
	// String stato = datiforniti.getSTATO();
	String tipoCodEnte = datiforniti.getTIPOCODENTE();
	String tipoPag = datiforniti.getTIPOPAG();
	String annoDocumento = datiforniti.getANNODOC();
	String codiceEnte = datiforniti.getCODENTE();
	String codiceTributo = datiforniti.getCODTRIBUTO();
	String canale = datiforniti.getCANALE();
	XMLGregorianCalendar DATAORDINE = datiforniti.getDATIPAGAMENTO().getDATAORDINE();
	if (log.isDebugEnabled()) {
	    if (pagante != null) {
		log.debug("WS Sistema Pagamenti Regulus. getBillPaymentNotify REQUEST:\nCOGNOME PAGANTE= " +
			pagante.getCOGNOME() +
			"\nNOME PAGANTE= " +
			pagante.getNOME() +
			"\nCODICEFISCALE= " +
			pagante.getCODICEFISCALE() +
			"\nNUMERO DOCUMENTO= " +
			numDoc +
			"\nNUMERO RATE PAGATE= " +
			numRatePag +
			"\nTIPO CODICE ENTE= " +
			tipoCodEnte +
			"\nCODICE ENTE= " +
			codiceEnte +
			"\nCODICE TRIBUTO= " +
			codiceTributo +
			"\n");
	    }
	}
	/*
	 * Controllo che il numero documento è un Integer. In Sigepro il numero documento è sempre un integer. Potrebbe
	 * essere inviato un valore non numerico che comunque non è presente.
	 */
	boolean numDocValidateInteger = true;
	try {
	    /*
	     * AUTENTICAZIONE -- RECUPERO TOKEN
	     */
	    String idcomune_alias = codiceEnte;
	    this.getConnection(idcomune_alias);
	    /*
	     * 
	     */
	    it.gruppoinit.regulus.schema.billpaymentnotify.response.MSG msg = new it.gruppoinit.regulus.schema.billpaymentnotify.response.MSG();
	    try {
		/*
		 * Salvo i dati nella tabella ONERIREGULUS
		 */
		/*
		 * Inserimento in ONERIREGULUS la ricevuta del pagamento
		 */
		/*
		 * Importo totale delle rate in IstanzeOneri.Utilizzato per controllare se l'importo pagato corrisponde
		 * all'importo presente in IstanzeOneri.
		 */
		BigDecimal importoRatePagateOneri = new BigDecimal(0);
		List<IstanzeOneriRegulus> oneriRegulusList = new ArrayList<IstanzeOneriRegulus>();
		if (numDocValidateInteger) {
		    for (Integer nrRata : rate.getRATA()) {
			Istanzeoneri istanzeOneri = istanzeoneriService.getOneriByNrDocRata(numDoc, nrRata.shortValue());
			if (EntityUtils.getNestedProperty(istanzeOneri, "id.codice") != null) {
			    if (istanzeOneri.getPrezzo() != null) {
				importoRatePagateOneri = importoRatePagateOneri.add(istanzeOneri.getPrezzo());
			    }
			    IstanzeOneriRegulus oneriregulus = new IstanzeOneriRegulus();
			    oneriregulus.setAnnodocumento(annoDocumento);
			    oneriregulus.setNumdocumento(numDoc);
			    oneriregulus.setDataordine(DATAORDINE.toGregorianCalendar().getTime());
			    if (EntityUtils.getNestedProperty(istanzeOneri, "id.codice") != null)
				oneriregulus.setIstanzeoneri(istanzeOneri);
			    PkId id = new PkId();
			    oneriregulus.setId(id);
			    oneriregulus.setIdentificativorata(nrRata.byteValue());
			    // L'importo viene ricevuto senza virgola. Le ultime due cifre vengono assunte come
			    // decimali.
			    String impTot = impTotale.toString();
			    CharSequence importoPrimaDellaVirgola = impTot.subSequence(0, impTot.length() - 2);
			    CharSequence importoPrimaDopoVirgola = impTot.subSequence(impTot.length() - 2, impTot.length());
			    impTot = importoPrimaDellaVirgola + "." + importoPrimaDopoVirgola;
			    oneriregulus.setImportototpagamento(new BigDecimal(impTot));
			    oneriregulus.setCodiceente(codiceEnte);
			    oneriregulus.setTipocodiceente(tipoCodEnte);
			    oneriregulus.setCodicetributo(istanzeOneri.getTipicausalioneri().getId().getCodice().toString());
			    oneriregulus.setCanaleriscossione(canale);
			    if (tipoPag.equals(RegulusConstants.TIPO_PAGAMENTO_DOC_REGISTRATO_CODICE)) {
				oneriregulus.setTipopagamento(RegulusConstants.TIPO_PAGAMENTO_DOC_REGISTRATO);
			    }
			    if (tipoPag.equals(RegulusConstants.TIPO_PAGAMENTO_SPONTANEO_CODICE)) {
				oneriregulus.setTipopagamento(RegulusConstants.TIPO_PAGAMENTO_SPONTANEO);
			    }
			    oneriregulus.setNrratepagate(((Short) numRatePag).intValue());
			    String impRatePag = impRatePagate.toString();
			    if (impRatePag.length() >= 2) {
				// L'importo viene ricevuto senza virgola. Le ultime due cifre vengono assunte come
				// decimali.
				CharSequence impRatePagatePrimaDellaVirgola = impRatePag.subSequence(0, impRatePag.length() - 2);
				CharSequence impRatePagatePrimaDopoVirgola = impRatePag.subSequence(impRatePag.length() - 2, impRatePag.length());
				impRatePag = impRatePagatePrimaDellaVirgola + "." + impRatePagatePrimaDopoVirgola;
			    }
			    oneriregulus.setImportototratepagate(new BigDecimal(impRatePag));
			    oneriregulus.setMetodopagamento(tipoDatiPagamento.getMETODO().value());
			    oneriregulus.setSistpagamento(tipoDatiPagamento.getSISTEMA().value());
			    oneriregulus.setIdordine(tipoDatiPagamento.getIDORDINE());
			    if (pagante != null) {
				oneriregulus.setNominativopagante(pagante.getCOGNOME() + " " + pagante.getNOME());
				oneriregulus.setCodicefiscalepagante(pagante.getCODICEFISCALE());
				oneriregulus.setRagionesocialepagante(pagante.getRAGIONESOCIALE());
			    }
			    String impCommissioniString = impCommissioni.toString();
			    if (impCommissioniString.length() >= 2) {
				// L'importo viene ricevuto senza virgola. Le ultime due cifre vengono assunte come
				// decimali.
				CharSequence impCommissioniPrimaDellaVirgola = impCommissioniString.subSequence(0, impCommissioniString.length() - 2);
				CharSequence impCommissioniDopoVirgola = impCommissioniString.subSequence(impCommissioniString.length() - 2,
					impCommissioniString.length());
				impCommissioniString = impCommissioniPrimaDellaVirgola + "." + impCommissioniDopoVirgola;
			    }
			    oneriregulus.setImpcommissioni(new BigDecimal(impCommissioniString));
			    oneriRegulusList.add(oneriregulus);
			}
		    }
		}
		String impRatePag = impRatePagate.toString();
		// L'importo viene ricevuto senza virgola. Le ultime due cifre vengono assunte come
		// decimali.
		if (impRatePag.length() >= 2) {
		    CharSequence impRatePagatePrimaDellaVirgola = impRatePag.subSequence(0, impRatePag.length() - 2);
		    CharSequence impRatePagatePrimaDopoVirgola = impRatePag.subSequence(impRatePag.length() - 2, impRatePag.length());
		    impRatePag = impRatePagatePrimaDellaVirgola + "." + impRatePagatePrimaDopoVirgola;
		}
		// Controllo se il totale delle rate pagate trasmesso da regulus corrisponde al totale delle rispettive
		// rate in IstanzeOneri.
		if (importoRatePagateOneri.compareTo(new BigDecimal(impRatePag)) == 0) {
		    try {
			if (!oneriRegulusList.isEmpty()) {
			    ESITO esitoBollo = istanzeOneriRegulusService.insertOneriRegulus(oneriRegulusList, impBollo);
			    if (esitoBollo != null) {
				esito = esitoBollo;
			    }
			} else {
			    // ESITO: NESSUN ELEMENTO TROVATO
			    esito.setRC(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO.toString());
			    esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO).toString());
			    esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO));
			}
		    } catch (Exception e) {
			// ESITO: SQL INSERT ERROR
			esito.setRC(RegulusEsitoConstants.E_SQL_INSERT_ERROR.toString());
			esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_SQL_INSERT_ERROR).toString());
			esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_SQL_INSERT_ERROR));
		    }
		} else {
		    esito.setRC(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO.toString());
		    esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO).toString());
		    esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO));
		}
		/*
		 * Popolo il messaggio di risposta
		 */
		msg.setCodiceFunzione(TipoCodiceFunzione.BILL_PAYMENT_NOTIFY.value());
		msg.setNomeFunzione(TipoCodiceFunzione.BILL_PAYMENT_NOTIFY.value());
		it.gruppoinit.regulus.schema.billpaymentnotify.response.MSG.RISPOSTA risposta = new it.gruppoinit.regulus.schema.billpaymentnotify.response.MSG.RISPOSTA();
		GregorianCalendar dataRispostaCalendar = new GregorianCalendar();
		XMLGregorianCalendar dataRisposta = Utilities.getXMLGregorianCalendar(dataRispostaCalendar);
		dataRisposta.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		dataRisposta.setFractionalSecond(new BigDecimal(0));
		risposta.setDataRisposta(dataRisposta.toString());
		risposta.setIdentificativoRisposta(richiesta.getIdentificativoRichiesta());
		msg.setESITO(esito);
		msg.setTITOLO(TipoCodiceFunzione.BILL_PAYMENT_NOTIFY.value());
		msg.setVersioneMessaggio(RegulusConstants.VERSIONE_MESSAGGIO);
		msg.setDATIOUTPUT(null);
		/*
		 * Effettuo il marshalling del messaggio
		 */
		try {
		    JAXBContext jc = JAXBContext.newInstance("it.gruppoinit.regulus.schema.billpaymentnotify.response");
		    Marshaller m = jc.createMarshaller();
		    /*
		     * Proprietà per formattare l'output.
		     */
		    // m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		    StringWriter stringWriter = new StringWriter();
		    m.marshal(msg, stringWriter);
		    stringWriter.flush();
		    result = stringWriter.toString();
		} catch (JAXBException e) {
		    if (log.isDebugEnabled()) {
			log.error("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getBillPaymentNotify [Marshall]: " + e.getMessage());
		    }
		    throw new RuntimeException("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getBillPaymentNotify [Marshall]: " + e.getMessage());
		}
	    } catch (Exception e) {
		// Controllo di che tipo è l'errore
		// Generic Exception
		if (e instanceof Exception) {
		    esito.setRC(RegulusEsitoConstants.E_GENERIC_EXCEPTION.toString());
		    esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_GENERIC_EXCEPTION).toString());
		    esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_GENERIC_EXCEPTION));
		}
		// IOException
		if (e instanceof IOException) {
		    esito.setRC(RegulusEsitoConstants.E_IO_EXCEPTION.toString());
		    esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_IO_EXCEPTION).toString());
		    esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_IO_EXCEPTION));
		}
		// SQLException
		if (e instanceof SQLException) {
		    esito.setRC(RegulusEsitoConstants.E_SQL_EXCEPTION.toString());
		    esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.E_SQL_EXCEPTION).toString());
		    esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.E_SQL_EXCEPTION));
		}
		// Setto l'oggetto ESITO
		msg.setESITO(esito);
		JAXBContext jc;
		try {
		    jc = JAXBContext.newInstance("it.gruppoinit.regulus.schema.billpaymentnotify.response");
		    Marshaller m = jc.createMarshaller();
		    StringWriter stringWriter = new StringWriter();
		    m.marshal(msg, stringWriter);
		    stringWriter.flush();
		    result = stringWriter.toString();
		} catch (JAXBException e1) {
		    if (log.isDebugEnabled()) {
			log.error("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getBillPaymentNotify [Marshall - Error]: " + e1.getMessage());
		    }
		    throw new RuntimeException(
			    "ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getBillPaymentNotify [Marshall - Error]: " + e.getMessage());
		}
		if (log.isDebugEnabled()) {
		    log.error("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getBillPaymentNotify [Marshall - catch block]: " + e.getMessage());
		}
	    }
	    if (log.isDebugEnabled()) {
		log.debug("WS Sistema Pagamenti Regulus. getBillPaymentNotify OUTPUT: \n" + result);
	    }
	} catch (RuntimeException e2) {
	    throw new RuntimeException(e2);
	}
	/*
	 * 
	 */
	return result;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
