package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.DomandestcService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.ldp.suolopubblico.ComplexTypeArea;
import it.ldp.suolopubblico.ComplexTypeAreeUsoPubblico;
import it.ldp.suolopubblico.ComplexTypePeriodo;
import it.ldp.suolopubblico.ComplexTypeStringa;
import it.ldp.suolopubblico.PresentazioneAreeUsoPubblico;
import it.ldp.suolopubblico.PresentazioneAreeUsoPubblicoSoap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

public class LDPWsClient {

    private static final Logger log = LoggerFactory.getLogger(LDPWsClient.class);
    @Autowired
    private DomandestcService domandestcService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private IstanzeeventiService istanzeeventiService;
    @Autowired
    private Istanzedyn2datiService istanzedyn2datiService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private Istanzedyn2modellitService istanzedyn2modellitService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private StatiistanzaService statiistanzaService;

    public void setNumeroPratica(Istanze istanza, Domandestc domandestc) {

	// Recupero praametri necessari per la chiamata al ws
	log.debug("Recupero i parametri necessari per la chiamata al ws dalla verticalizzazione {}", WebConstants.VERTICALIZZAIONE_SIT_LDP);
	String _url = "";
	String _user = "";
	String _pwd = "";
	String _nodo = "";
	Source response = null;
	try {
	    if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAIONE_SIT_LDP)) {
		Verticalizzazioniparametri nodo = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
			WebConstants.VERTICALIZZAZIONE_SIT_LDP_NODI);
		if (nodo != null && StringUtils.isNotBlank(nodo.getValore())) {
		    _nodo = nodo.getValore();
		} else {
		    log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato", WebConstants.VERTICALIZZAZIONE_SIT_LDP_NODI);
		    throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro " + WebConstants.VERTICALIZZAZIONE_SIT_LDP_NODI
			    + " non settato ");
		}
		log.debug("setNumeroPratica# Nodo per cui le pratiche devono essere notificate : {}", _nodo);
		// Verifico se la pratica deve essere notificata al servizio 
		log.debug("setNumeroPratica# Verifico se la pratica deve essere notificata al servizio");
		if (domandestc != null && domandestc.getIdNodo().equals(_nodo)) {
		    log.debug("setNumeroPratica# E' stata trovata una pratica in Domande stc con idNodo uguale a {}", _nodo);
		    // recupero url da chiamare
		    Verticalizzazioniparametri url = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
			    WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA);
		    if (url != null && StringUtils.isNotBlank(url.getValore())) {
			_url = url.getValore();
		    } else {
			log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato",
				WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA);
			throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro "
				+ WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA + " non settato ");
		    }
		    // recupero user name
		    Verticalizzazioniparametri user = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
			    WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME);
		    if (user != null && StringUtils.isNotBlank(user.getValore())) {
			_user = user.getValore();
		    } else {
			log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato", WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME);
			throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro "
				+ WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME + " non settato ");
		    }
		    // recupero password
		    Verticalizzazioniparametri pwd = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
			    WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD);
		    if (pwd != null && StringUtils.isNotBlank(pwd.getValore())) {
			_pwd = pwd.getValore();
		    } else {
			log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato", WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD);
			throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro "
				+ WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD + " non settato ");
		    }
		    String serviceName = getServiceName();
		    String portName = getPortName();
		    String namespace = getNameSpace();
		    String methodName = getMethodName();
		    CodiceDescrizioneBean identificativi = getIdentificativiPratica(istanza.getId().getCodice());
		    log.debug("setNumeroPratica# Recupero le informazioni per l'invocazione del ws");
		    String numeroPratica = identificativi.getDescrizione(); //istanza.getId().getCodice().toString();
		    String idMittente = identificativi.getCodice(); //StringUtils.defaultIfEmpty(domandestc.getIdDomandamitt(), "");
		    String reqe = "<ws:" + methodName + " xmlns:ws=\"https://ws.ldpgis.it/\"><identificativo_temporaneo>" + idMittente
			    + "</identificativo_temporaneo><numero_pratica>" + numeroPratica + "</numero_pratica></ws:" + methodName + ">";
		    log.debug("setNumeroPratica# Request: {}", reqe);
		    Verticalizzazioniparametri componente = verticalizzazioniService.getVerticalizzazioniparametri(
			    WebConstants.VERTICALIZZAIONE_SIT_LDP, "LDP_COMPONENTE");
		    String componenteStr = "LIVORNO_AREE_PUBBLICHE";
		    if (componente != null && StringUtils.isNotBlank(componente.getValore())) {
			componenteStr = componente.getValore();
		    }
		    //		    else {
		    //			log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato", "LDP_COMPONENTE");
		    //			throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro " + "LDP_COMPONENTE" + " non settato ");
		    //		    }
		    URL wsdlURL = null;
		    if (componenteStr.equalsIgnoreCase("LIVORNO_AREE_PUBBLICHE")) {
			wsdlURL = LDPWsClient.class.getClassLoader().getResource("ldp_livorno_presentazione_aree_uso_pubblico.wsdl");
		    } else if (componenteStr.equalsIgnoreCase("SIENA_EDILIZIA")) {
			wsdlURL = LDPWsClient.class.getClassLoader().getResource("ldp_siena_presentazione_pratiche_edilizie..wsdl");
		    }
		    log.debug("setNumeroPratica# ws url : {}", _url);
		    // wsdlURL = new URL(_url);
		    QName SERVICE_NAME = new QName(namespace, serviceName);
		    Service service = Service.create(wsdlURL, SERVICE_NAME);
		    Dispatch<Source> disp = service.createDispatch(new QName(namespace, portName), Source.class, Service.Mode.PAYLOAD);
		    disp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, _url);
		    disp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, _user);
		    disp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, _pwd);
		    ByteArrayInputStream bis = new ByteArrayInputStream(reqe.getBytes());
		    Source request = new StreamSource(bis);
		    response = disp.invoke(request);
		}
	    }
	    //	}catch (MalformedURLException e) {
	    //	    log.error("Errore durante la chiamata al ws LDP...: {}", e);
	    //	    istanzeeventiService.insert("Errore durante la chiamata al ws LDP. Il numeropratica non è stato settato: " + e.getMessage(),
	    //		    IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	} catch (Exception e) {
	    log.error("Errore durante la chiamata al ws LDP...: {}", e);
	    istanzeeventiService.insert("Errore durante la chiamata al ws LDP. Il numeropratica non è stato settato: " + e.getMessage(),
		    IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	}
	//TODO
	// che fare con la response??????
	//return response.getSystemId();
    }

    /**
     * SU CODICE CI STA L'IDENTIFICATIVO TEMPORANEO, SU DESCRIZIONE L'IDENTIFICATIVO FINALE. IN CASO DI PRATICA DI
     * BACKOFFICE NON CREATA DA STC L'IDENTIFICATIVO TEMPORANEO E FINALE COINCIDONO CON CODICEISTANZA
     * 
     * @param codiceistanza
     * @return
     */
    public CodiceDescrizioneBean getIdentificativiPratica(Integer codiceistanza) {

	CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	Istanze i = istanzeService.findById(new PkId(codiceistanza));
	if (i == null) {
	    throw new RuntimeException("Istanza con codice " + codiceistanza + " non trovata.");
	}
	cdb.setCodice(String.valueOf(codiceistanza)); // Se non ci sono Domandestc l'identificativo temporaneo è il codice istanza  
	cdb.setDescrizione(String.valueOf(codiceistanza));
	List<Domandestc> dsts = domandestcService.findByIstanza(codiceistanza);
	if (dsts != null && dsts.size() > 0) {
	    cdb.setCodice(dsts.get(0).getIdDomandamitt()); // Se ci sono Domandestc l'identificativo temporaneo è IDDOMANDAMITT
	}
	return cdb;
    }

    private String getNameSpace() {

	String result = "https://ws.ldpgis.it/";
	Verticalizzazioniparametri vert = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
		WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA_NSPACE);
	if (vert != null && StringUtils.isNotBlank(vert.getValore())) {
	    result = vert.getValore();
	    log.debug("setNumeroPratica# NameSpace ==> {}", result);
	}
	return result;
    }

    private String getPortName() {

	String result = "PresentazionePraticheEdilizieSoap";
	Verticalizzazioniparametri vert = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
		WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA_PNAME);
	if (vert != null && StringUtils.isNotBlank(vert.getValore())) {
	    result = vert.getValore();
	    log.debug("setNumeroPratica# PortName ==> {}", result);
	}
	return result;
    }

    private String getServiceName() {

	String result = "PresentazionePraticheEdilizie";
	Verticalizzazioniparametri vert = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
		WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA_SNAME);
	if (vert != null && StringUtils.isNotBlank(vert.getValore())) {
	    result = vert.getValore();
	    log.debug("setNumeroPratica# ServiceName ==> {}", result);
	}
	return result;
    }

    private String getMethodName() {

	String result = "ComplexTypePraticaIdentificativi";
	Verticalizzazioniparametri vert = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
		WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA_MNAME);
	if (vert != null && StringUtils.isNotBlank(vert.getValore())) {
	    result = vert.getValore();
	    log.debug("setNumeroPratica# MethodName ==> {}", result);
	}
	return result;
    }

    public static void main2(String[] args) throws HttpException, IOException {

	HttpClient c = new HttpClient();
	GetMethod get = new GetMethod("https://ws.ldpgis.it");
	int executeMethod = c.executeMethod(get);
	System.out.println(executeMethod);
	String reqe = "<ws:ComplexTypePraticaIdentificativi xmlns:ws=\"https://ws.ldpgis.it/\"><identificativo_temporaneo>E625_SP_FRRMSM71E29E975N_32463</identificativo_temporaneo>"
		+ "<numero_pratica>90115</numero_pratica></ws:ComplexTypePraticaIdentificativi>";
	URL wsdlURL = null;
	String _url = "https://ws.ldpgis.it/livornosit/presentazione_aree_uso_pubblico.php?wsdl";
	String filewsdl = "file:///D:/sviluppo/Frameworks/axis1.4/presentazione_aree_uso_pubblico.wsdl";
	log.debug("setNumeroPratica# ws url : {}", _url);
	wsdlURL = new URL(_url);
	QName SERVICE_NAME = new QName("https://ws.ldpgis.it/", "PresentazioneAreeUsoPubblico");
	Service service = Service.create(wsdlURL, SERVICE_NAME);
	Dispatch<Source> disp = service.createDispatch(new QName("https://ws.ldpgis.it/", "PresentazioneAreeUsoPubblicoSoap"), Source.class,
		Service.Mode.PAYLOAD);
	disp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, _url);
	disp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "");
	disp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "");
	ByteArrayInputStream bis = new ByteArrayInputStream(reqe.getBytes());
	Source request = new StreamSource(bis);
	Source response = disp.invoke(request);
	// System.out.println(response);
    }

    public static void main(String[] args) throws HttpException, IOException, TransformerException, ParserConfigurationException, SAXException {

	String reqe = "<ns1:ComplexTypeAreeUsoPubblico xmlns:ns1=\"https://ws.ldpgis.it/\"><tipologia>permanente</tipologia><giorni_settimana/><ripetizione xsi:nil=\"true\"/><a_periodi><ComplexTypePeriodo><inizio>2017-11-21 00:00:00</inizio><fine>2017-11-21 23:59:00</fine><a_aree><ComplexTypeArea><identificativo>Disegno 1</identificativo><metri_quadrati>6.00</metri_quadrati></ComplexTypeArea></a_aree></ComplexTypePeriodo></a_periodi></ns1:ComplexTypeAreeUsoPubblico>";
	//	URL wsdlURL = null;
	//	String _url = "https://ws.ldpgis.it/livornosit/presentazione_aree_uso_pubblico.php?wsdl";
	//	log.debug("setNumeroPratica# ws url : {}", _url);
	//	wsdlURL = new URL(_url);
	//	QName SERVICE_NAME = new QName("https://ws.ldpgis.it/", "PresentazioneAreeUsoPubblico");
	//	Service service = Service.create(wsdlURL, SERVICE_NAME);
	//	Dispatch<Source> disp = service.createDispatch(new QName("https://ws.ldpgis.it/", "PresentazioneAreeUsoPubblicoSoap"), Source.class,
	//		Service.Mode.PAYLOAD);
	//	disp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, _url);
	//	disp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "");
	//	disp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "");
	//	// disp.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "https://ws.ldpgis.it/getDatiOccupazioneSuoloByIdentificativoTemporaneo");
	//	ByteArrayInputStream bis = new ByteArrayInputStream(reqe.getBytes());
	//	Source request = new StreamSource(bis);
	//	Source response = disp.invoke(request);
	//	StringWriter writer = new StringWriter();
	//	TransformerFactory tFactory = TransformerFactory.newInstance();
	//	Transformer transformer = tFactory.newTransformer();
	//	transformer.transform(response, new javax.xml.transform.stream.StreamResult(writer));
	//	String result = writer.toString();
	reqe = reqe.replaceAll("xsi:nil=\"true\"", "");
	ComplexTypeAreeUsoPubblico aup = (ComplexTypeAreeUsoPubblico) Utilities.unMarshallString(reqe, ComplexTypeAreeUsoPubblico.class);
	if (aup != null) {
	    if (aup.getAPeriodi() != null && aup.getAPeriodi().getComplexTypePeriodo() != null
		    && !aup.getAPeriodi().getComplexTypePeriodo().isEmpty()) {
		List<ComplexTypePeriodo> periodis = aup.getAPeriodi().getComplexTypePeriodo();
		for (ComplexTypePeriodo p : periodis) {
		    String inizio = p.getInizio();
		    String fine = p.getFine();
		    GregorianCalendar dinizio = Utilities.getDate(inizio, "yyyy-MM-dd hh:mm:ss");
		    GregorianCalendar dfine = Utilities.getDate(fine, "yyyy-MM-dd hh:mm:ss");
		    String datainizio = Utilities.formatDate(dinizio.getTime(), "dd/MM/yyyy");
		    String oraInizio = Utilities.formatDate(dinizio.getTime(), "HH:mm:ss");
		    String datafine = Utilities.formatDate(dfine.getTime(), "dd/MM/yyyy");
		    String oraFine = Utilities.formatDate(dfine.getTime(), "HH:mm:ss");
		    System.out.println(datainizio + "+" + oraInizio + "-" + datafine + "+" + oraFine);
		}
	    }
	}
    }

    public static void main1(String[] args) throws HttpException, IOException, TransformerException, ParserConfigurationException, SAXException {

	String _url = "https://ws.ldpgis.it/livornosit/presentazione_aree_uso_pubblico.php?wsdl";
	URL wsdlURL = new URL(_url);
	PresentazioneAreeUsoPubblicoSoap port = getPresentazioneAreeUsoPubblico(wsdlURL);
	ComplexTypeStringa identificativoTemporaneo = new ComplexTypeStringa();
	identificativoTemporaneo.setTesto("E625_SP_FRRMSM71E29E975N_37683");
	ComplexTypeAreeUsoPubblico aup = port.getDatiOccupazioneSuoloByIdentificativoTemporaneo(identificativoTemporaneo);
	if (aup != null) {
	    if (aup.getAPeriodi() != null && aup.getAPeriodi().getComplexTypePeriodo() != null
		    && !aup.getAPeriodi().getComplexTypePeriodo().isEmpty()) {
		List<ComplexTypePeriodo> periodis = aup.getAPeriodi().getComplexTypePeriodo();
		for (ComplexTypePeriodo p : periodis) {
		    String inizio = p.getInizio();
		    String fine = p.getFine();
		    GregorianCalendar dinizio = Utilities.getDate(inizio, "yyyy-MM-dd hh:mm:ss");
		    GregorianCalendar dfine = Utilities.getDate(fine, "yyyy-MM-dd hh:mm:ss");
		    String datainizio = Utilities.formatDate(dinizio.getTime(), "dd/MM/yyyy");
		    String oraInizio = Utilities.formatDate(dinizio.getTime(), "HH:mm:ss");
		    String datafine = Utilities.formatDate(dfine.getTime(), "dd/MM/yyyy");
		    String oraFine = Utilities.formatDate(dfine.getTime(), "HH:mm:ss");
		    System.out.println(datainizio + "+" + oraInizio + "-" + datafine + "+" + oraFine);
		}
	    }
	}
    }

    private static PresentazioneAreeUsoPubblicoSoap getPresentazioneAreeUsoPubblico(URL wsdlURL) {

	PresentazioneAreeUsoPubblico client = new PresentazioneAreeUsoPubblico(wsdlURL);
	PresentazioneAreeUsoPubblicoSoap port = client.getPresentazioneAreeUsoPubblicoSoap();
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	// HTTPClientPolicy - Properties used to configure a client-side HTTP port  
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(240000); // Line #2  
	httpClientPolicy.setReceiveTimeout(240000); // Line #3  
	conduit.setClient(httpClientPolicy);
	Endpoint securityEndpoint = proxy.getEndpoint();
	Map<String, Object> outProps = new HashMap<String, Object>();
	outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
	outProps.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordDigest"); // WSConstants.PASSWORD_DIGEST ???
	outProps.put(WSHandlerConstants.USER, "");
	outProps.put(WSHandlerConstants.PW_CALLBACK_REF, new UsernamePasswordCallback("", ""));
	WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
	securityEndpoint.getOutInterceptors().add(wssOut);
	return port;
    }

    public void setStatoOccupazione(Integer codiceIstanza, Statiistanza s) throws OperazioniAutomaticheException {

	// Recupero praametri necessari per la chiamata al ws
	log.debug("setStatoOccupazione# Recupero i parametri necessari per la chiamata al ws dalla verticalizzazione {}",
		WebConstants.VERTICALIZZAIONE_SIT_LDP);
	try {
	    if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAIONE_SIT_LDP)) {
		Integer inizioora = getCampo(WebConstants.VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_INIZIO_ORA);
		if (inizioora != null) { // se il campo non è attivato la funzionalità occupazione suolo pubblico non è attiva
		    String stato = "";
		    if (s != null) {
			stato = s.getOspldpStatoPratica();
		    }
		    if (StringUtils.isNotBlank(stato)) {
			Source response = null;
			String _url = "";
			String _user = "";
			String _pwd = "";
			//			String _nodo = "";
			//			Verticalizzazioniparametri nodo = verticalizzazioniService.getVerticalizzazioniparametri(
			//				WebConstants.VERTICALIZZAIONE_SIT_LDP, WebConstants.VERTICALIZZAZIONE_SIT_LDP_NODI);
			//			if (nodo != null && StringUtils.isNotBlank(nodo.getValore())) {
			//			    _nodo = nodo.getValore();
			//			} else {
			//			    log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato", WebConstants.VERTICALIZZAZIONE_SIT_LDP_NODI);
			//			    throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro "
			//				    + WebConstants.VERTICALIZZAZIONE_SIT_LDP_NODI + " non settato ");
			//			}
			//			log.debug("setNumeroPratica# Nodo per cui le pratiche devono essere notificate : {}", _nodo);
			// Verifico se la pratica deve essere notificata al servizio 
			// recupero url da chiamare
			Verticalizzazioniparametri url = verticalizzazioniService.getVerticalizzazioniparametri(
				WebConstants.VERTICALIZZAIONE_SIT_LDP, WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA);
			if (url != null && StringUtils.isNotBlank(url.getValore())) {
			    _url = url.getValore();
			} else {
			    log.error("setStatoOccupazione# Notifica al sistema LDP non avvenuto.Parametro {} non settato",
				    WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA);
			    throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro "
				    + WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA + " non settato ");
			}
			// recupero user name
			Verticalizzazioniparametri user = verticalizzazioniService.getVerticalizzazioniparametri(
				WebConstants.VERTICALIZZAIONE_SIT_LDP, WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME);
			if (user != null && StringUtils.isNotBlank(user.getValore())) {
			    _user = user.getValore();
			} else {
			    log.error("setStatoOccupazione# Notifica al sistema LDP non avvenuto.Parametro {} non settato",
				    WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME);
			    throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro "
				    + WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME + " non settato ");
			}
			// recupero password
			Verticalizzazioniparametri pwd = verticalizzazioniService.getVerticalizzazioniparametri(
				WebConstants.VERTICALIZZAIONE_SIT_LDP, WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD);
			if (pwd != null && StringUtils.isNotBlank(pwd.getValore())) {
			    _pwd = pwd.getValore();
			} else {
			    log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato",
				    WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD);
			    throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro "
				    + WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD + " non settato ");
			}
			String serviceName = getServiceName();
			String portName = getPortName();
			String namespace = getNameSpace();
			CodiceDescrizioneBean identificativiPratica = getIdentificativiPratica(codiceIstanza);
			log.debug("setStatoOccupazione# Recupero le informazioni per l'invocazione del ws");
			String idMittente = identificativiPratica.getCodice(); // StringUtils.defaultIfEmpty(domandestc.getIdDomandamitt(), "");
			String reqe = "<ws:ComplexTypeStatoOccupazione xmlns:ws=\"https://ws.ldpgis.it/\"><identificativo_temporaneo>" + idMittente
				+ "</identificativo_temporaneo><stato_occupazione>" + stato + "</stato_occupazione></ws:ComplexTypeStatoOccupazione>";
			log.debug("setStatoOccupazione# Request: {}", reqe);
			URL wsdlURL = null;
			log.debug("setStatoOccupazione# ws url : {}", _url);
			wsdlURL = new URL(_url);
			QName SERVICE_NAME = new QName(namespace, serviceName);
			Service service = Service.create(wsdlURL, SERVICE_NAME);
			Dispatch<Source> disp = service.createDispatch(new QName(namespace, portName), Source.class, Service.Mode.PAYLOAD);
			disp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, _url);
			disp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, _user);
			disp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, _pwd);
			ByteArrayInputStream bis = new ByteArrayInputStream(reqe.getBytes());
			Source request = new StreamSource(bis);
			response = disp.invoke(request);
		    }
		}
	    }
	} catch (MalformedURLException e) {
	    log.error("setStatoOccupazione# Errore durante la chiamata al ws LDP...: {}", e.getMessage());
	    // istanzeeventiService.insert(e.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	} catch (Exception e) {
	    log.error("setStatoOccupazione# Errore durante la chiamata al ws LDP...: {}", e.getMessage());
	    // istanzeeventiService.insert(e.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	}
    }

    public ComplexTypeAreeUsoPubblico getStruttutaDatiOccupazioneSuoloByIdentificativo(Istanze istanza) {

	try {
	    String _url = "";
	    String _user = "";
	    String _pwd = "";
	    Verticalizzazioniparametri url = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
		    WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA);
	    if (url != null && StringUtils.isNotBlank(url.getValore())) {
		_url = url.getValore();
	    } else {
		log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato",
			WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA);
		throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro "
			+ WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA + " non settato ");
	    }
	    // recupero user name
	    Verticalizzazioniparametri user = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
		    WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME);
	    if (user != null && StringUtils.isNotBlank(user.getValore())) {
		_user = user.getValore();
	    } else {
		log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato", WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME);
		throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro " + WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME
			+ " non settato ");
	    }
	    // recupero password
	    Verticalizzazioniparametri pwd = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
		    WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD);
	    if (pwd != null && StringUtils.isNotBlank(pwd.getValore())) {
		_pwd = pwd.getValore();
	    } else {
		log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato", WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD);
		throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro " + WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD
			+ " non settato ");
	    }
	    String serviceName = getServiceName();
	    String portName = getPortName();
	    String namespace = getNameSpace();
	    URL wsdlURL = null;
	    log.debug("setNumeroPratica# ws url : {}", _url);
	    wsdlURL = new URL(_url);
	    QName SERVICE_NAME = new QName(namespace, serviceName);
	    Service service = Service.create(wsdlURL, SERVICE_NAME);
	    Dispatch<Source> disp = service.createDispatch(new QName(namespace, portName), Source.class, Service.Mode.PAYLOAD);
	    disp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, _url);
	    disp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, _user);
	    disp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, _pwd);
	    CodiceDescrizioneBean identificativiPratica = getIdentificativiPratica(istanza.getId().getCodice());
	    String idMittente = identificativiPratica.getCodice(); // StringUtils.defaultIfEmpty(domandestc.getIdDomandamitt(), "");
	    String reqe = "<ws:ComplexTypeStringa xmlns:ws=\"https://ws.ldpgis.it/\"><testo>" + idMittente + "</testo></ws:ComplexTypeStringa>";
	    log.debug("setNumeroPratica# Request: {}", reqe);
	    ByteArrayInputStream bis = new ByteArrayInputStream(reqe.getBytes());
	    Source request = new StreamSource(bis);
	    Source response = disp.invoke(request);
	    StringWriter writer = new StringWriter();
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer = tFactory.newTransformer();
	    transformer.transform(response, new javax.xml.transform.stream.StreamResult(writer));
	    String result = writer.toString();
	    result = result.replaceAll("xsi:nil=\"true\"", "");
	    ComplexTypeAreeUsoPubblico aup = (ComplexTypeAreeUsoPubblico) Utilities.unMarshallString(result, ComplexTypeAreeUsoPubblico.class);
	    return aup;
	} catch (MalformedURLException e) {
	    log.error("Errore durante la chiamata al ws LDP...: {}", e.getMessage());
	    istanzeeventiService.insert(e.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	} catch (Exception e) {
	    log.error("Errore durante la chiamata al ws LDP...: {}", e.getMessage());
	    istanzeeventiService.insert(e.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	}
	return null;
    }

    public void getDatiOccupazioneSuoloByIdentificativo(Istanze istanza) {

	ComplexTypeAreeUsoPubblico aup = getStruttutaDatiOccupazioneSuoloByIdentificativo(istanza);
	if (aup != null) {
	    if (aup.getAPeriodi() != null && aup.getAPeriodi().getComplexTypePeriodo() != null
		    && !aup.getAPeriodi().getComplexTypePeriodo().isEmpty()) {
		log.debug("getDatiOccupazioneSuoloByIdentificativo# prima di salvare i dati dinamici");
		salvadatiDinamici(istanza, aup);
	    }
	}
    }

    private void salvadatiDinamici(Istanze istanza, ComplexTypeAreeUsoPubblico aup) {

	boolean isRipetizioni = false;
	if (aup.getAPeriodi() != null && aup.getAPeriodi().getComplexTypePeriodo() != null && !aup.getAPeriodi().getComplexTypePeriodo().isEmpty()) {
	    if (StringUtils.defaultString(aup.getTipologia()).equalsIgnoreCase("temporanea_ripetizioni")) {
		isRipetizioni = true;
	    }
	    List<ComplexTypePeriodo> periodis = aup.getAPeriodi().getComplexTypePeriodo();
	    Verticalizzazioniparametri ciniziodata = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
		    WebConstants.VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_INIZIO_DATA);
	    Integer campoiniziodata = null;
	    log.debug("Cerco il campo inizio data dalle verticalizzazioni");
	    if (ciniziodata != null) {
		if (StringUtils.isNotBlank(ciniziodata.getValore())) {
		    if (Utilities.isInteger(ciniziodata.getValore().trim())) {
			campoiniziodata = Integer.parseInt(ciniziodata.getValore().trim());
		    }
		}
		if (null != campoiniziodata) {
		    log.debug("il campo inizio data dalle verticalizzazioni ha il valore {}", campoiniziodata);
		    Integer dyn2modellit = null;
		    if (isRipetizioni) {
			dyn2modellit = getCampo(WebConstants.VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_MODELLIT_RIPET);
		    }
		    if (dyn2modellit == null) {
			dyn2modellit = getCampo(WebConstants.VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_MODELLIT);
		    }
		    if (dyn2modellit != null) {
			log.debug("il campo modelli dalle verticalizzazioni ha il valore {}, verifico se inserire il modello", dyn2modellit);
			Istanzedyn2modellit dymodt = istanzedyn2modellitService.findById(new Istanzedyn2modellitId(istanza.getId().getCodice(),
				dyn2modellit));
			if (dymodt == null) {
			    log.debug("il modello deve essere inserito in istanzedyn2modellit");
			    Dyn2Modellit scheda = dyn2ModellitService.findById(new PkId(dyn2modellit));
			    if (scheda != null) {
				log.debug("inserisco il modello");
				Istanzedyn2modellit entity = new Istanzedyn2modellit();
				entity.setId(new Istanzedyn2modellitId(istanza.getId().getCodice(), dyn2modellit));
				entity.setIstanza(istanza);
				entity.setDyn2Modellit(scheda);
				istanzedyn2modellitService.insert(entity);
			    }
			}
		    }
		    log.debug("Verifico se già sono presenti valori in istanzedyn2dati");
		    // rimuovo sempre i dati dinamici
		    List<Istanzedyn2dati> d2ids = istanzedyn2datiService.findByIstanzaAndDyn2Campi(istanza.getId().getCodice(), campoiniziodata);
		    for (Istanzedyn2dati istanzedyn2dati : d2ids) {
			istanzedyn2datiService.delete(istanzedyn2dati);
		    }
		    Integer freq = getCampo(WebConstants.VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_OSP_FREQ_OCCUPAZ);
		    d2ids = istanzedyn2datiService.findByIstanzaAndDyn2Campi(istanza.getId().getCodice(), freq);
		    for (Istanzedyn2dati istanzedyn2dati : d2ids) {
			istanzedyn2datiService.delete(istanzedyn2dati);
		    }
		    Integer inizioora = getCampo(WebConstants.VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_INIZIO_ORA);
		    d2ids = istanzedyn2datiService.findByIstanzaAndDyn2Campi(istanza.getId().getCodice(), inizioora);
		    for (Istanzedyn2dati istanzedyn2dati : d2ids) {
			istanzedyn2datiService.delete(istanzedyn2dati);
		    }
		    Integer finedata = getCampo(WebConstants.VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_FINE_DATA);
		    d2ids = istanzedyn2datiService.findByIstanzaAndDyn2Campi(istanza.getId().getCodice(), finedata);
		    for (Istanzedyn2dati istanzedyn2dati : d2ids) {
			istanzedyn2datiService.delete(istanzedyn2dati);
		    }
		    Integer fineora = getCampo(WebConstants.VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_FINE_ORA);
		    d2ids = istanzedyn2datiService.findByIstanzaAndDyn2Campi(istanza.getId().getCodice(), fineora);
		    for (Istanzedyn2dati istanzedyn2dati : d2ids) {
			istanzedyn2datiService.delete(istanzedyn2dati);
		    }
		    Integer areeOccupate = getCampo(WebConstants.VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_DETT_AREE_OCCUPATE);
		    d2ids = istanzedyn2datiService.findByIstanzaAndDyn2Campi(istanza.getId().getCodice(), areeOccupate);
		    for (Istanzedyn2dati istanzedyn2dati : d2ids) {
			istanzedyn2datiService.delete(istanzedyn2dati);
		    }
		    if (istanzedyn2datiService.findByIstanzaAndDyn2Campi(istanza.getId().getCodice(), campoiniziodata).isEmpty()) {
			log.debug("Non sono presenti valori in istanzedyn2dati li inserisco");
			//		    Integer inizioora = getCampo(WebConstants.VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_INIZIO_ORA);
			//		    Integer finedata = getCampo(WebConstants.VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_FINE_DATA);
			//		    Integer fineora = getCampo(WebConstants.VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_FINE_ORA);
			int index = 0;
			if (isRipetizioni) {
			    String frequenzaOccupazione = aup.getRipetizione();
			    if (StringUtils.isNotBlank(aup.getGiorniSettimana())) {
				frequenzaOccupazione += " (" + aup.getGiorniSettimana() + ")";
			    }
			    Istanzedyn2dati entity = new Istanzedyn2dati();
			    entity.setIstanza(istanza);
			    Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(freq));
			    entity.setDyn2Campi(dyn2Campi);
			    Istanzedyn2datiId id = new Istanzedyn2datiId(istanza.getId().getCodice(), freq, 0, 0);
			    entity.setId(id);
			    entity.setValore(frequenzaOccupazione);
			    entity.setValoredecodificato(frequenzaOccupazione);
			    istanzedyn2datiService.insert(entity);
			}
			for (ComplexTypePeriodo p : periodis) {
			    String inizio = p.getInizio();
			    String fine = p.getFine();
			    GregorianCalendar dinizio = Utilities.getDate(inizio, "yyyy-MM-dd hh:mm:ss");
			    GregorianCalendar dfine = Utilities.getDate(fine, "yyyy-MM-dd hh:mm:ss");
			    String datainizio = Utilities.formatDate(dinizio.getTime(), "dd/MM/yyyy");
			    String datainizioargirata = Utilities.formatDate(dinizio.getTime(), "yyyyMMdd");
			    String oraInizio = Utilities.formatDate(dinizio.getTime(), "HH:mm:ss");
			    String datafine = Utilities.formatDate(dfine.getTime(), "dd/MM/yyyy");
			    String datafineargirata = Utilities.formatDate(dfine.getTime(), "yyyyMMdd");
			    String oraFine = Utilities.formatDate(dfine.getTime(), "HH:mm:ss");
			    String areaOccupazione = "";
			    if (p.getAAree() != null) {
				if (p.getAAree().getComplexTypeArea() != null) {
				    if (p.getAAree().getComplexTypeArea().size() > 0) {
					List<ComplexTypeArea> l = p.getAAree().getComplexTypeArea();
					for (ComplexTypeArea cpa : l) {
					    areaOccupazione += "- " + cpa.getDescrizione() + " [" + cpa.getIdentificativo() + "] ("
						    + cpa.getMetriQuadrati() + ")\n";
					}
				    }
				}
			    }
			    Istanzedyn2dati entity = new Istanzedyn2dati();
			    entity.setIstanza(istanza);
			    Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(campoiniziodata));
			    entity.setDyn2Campi(dyn2Campi);
			    Istanzedyn2datiId id = new Istanzedyn2datiId(istanza.getId().getCodice(), campoiniziodata, 0, index);
			    entity.setId(id);
			    entity.setValore(datainizioargirata);
			    entity.setValoredecodificato(datainizio);
			    istanzedyn2datiService.insert(entity);
			    Istanzedyn2dati entity2 = new Istanzedyn2dati();
			    entity2.setIstanza(istanza);
			    dyn2Campi = dyn2CampiService.findById(new PkId(inizioora));
			    entity2.setDyn2Campi(dyn2Campi);
			    Istanzedyn2datiId id2 = new Istanzedyn2datiId(istanza.getId().getCodice(), inizioora, 0, index);
			    entity2.setId(id2);
			    entity2.setValore(oraInizio);
			    entity2.setValoredecodificato(oraInizio);
			    istanzedyn2datiService.insert(entity2);
			    Istanzedyn2dati entity3 = new Istanzedyn2dati();
			    entity3.setIstanza(istanza);
			    dyn2Campi = dyn2CampiService.findById(new PkId(finedata));
			    entity3.setDyn2Campi(dyn2Campi);
			    Istanzedyn2datiId id3 = new Istanzedyn2datiId(istanza.getId().getCodice(), finedata, 0, index);
			    entity3.setId(id3);
			    entity3.setValore(datafineargirata);
			    entity3.setValoredecodificato(datafine);
			    istanzedyn2datiService.insert(entity3);
			    //   istanzedyn2datiService.findByIstanzaAndDyn2Campi(istanza.getId().getCodice(), codiceCampo);
			    Istanzedyn2dati entity4 = new Istanzedyn2dati();
			    entity4.setIstanza(istanza);
			    dyn2Campi = dyn2CampiService.findById(new PkId(fineora));
			    entity4.setDyn2Campi(dyn2Campi);
			    Istanzedyn2datiId id4 = new Istanzedyn2datiId(istanza.getId().getCodice(), fineora, 0, index);
			    entity4.setId(id4);
			    entity4.setValore(oraFine);
			    entity4.setValoredecodificato(oraFine);
			    istanzedyn2datiService.insert(entity4);
			    if (StringUtils.isNotBlank(areaOccupazione)) {
				Istanzedyn2dati entity5 = new Istanzedyn2dati();
				entity5.setIstanza(istanza);
				dyn2Campi = dyn2CampiService.findById(new PkId(areeOccupate));
				entity5.setDyn2Campi(dyn2Campi);
				Istanzedyn2datiId id5 = new Istanzedyn2datiId(istanza.getId().getCodice(), areeOccupate, 0, index);
				entity5.setId(id5);
				entity5.setValore(areaOccupazione);
				entity5.setValoredecodificato(areaOccupazione);
				istanzedyn2datiService.insert(entity5);
			    }
			    index++;
			}
		    }
		}
	    }
	}
    }

    private Integer getCampo(String name) {

	Verticalizzazioniparametri vcampo = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP, name);
	if (vcampo != null) {
	    if (StringUtils.isNotBlank(vcampo.getValore())) {
		if (Utilities.isInteger(vcampo.getValore().trim())) {
		    Integer ret = Integer.parseInt(vcampo.getValore().trim());
		    return ret;
		}
	    }
	}
	return null;
    }

    public boolean deleteOccupazioneSuoloByIdentificativo(CodiceDescrizioneBean identificativi, Istanze istanza) {

	// Recupero praametri necessari per la chiamata al ws
	log.debug("Recupero i parametri necessari per la chiamata al ws dalla verticalizzazione {}", WebConstants.VERTICALIZZAIONE_SIT_LDP);
	String _url = "";
	String _user = "";
	String _pwd = "";
	String _nodo = "";
	Source response = null;
	try {
	    if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAIONE_SIT_LDP)) {
		Verticalizzazioniparametri nodo = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
			WebConstants.VERTICALIZZAZIONE_SIT_LDP_NODI);
		if (nodo != null && StringUtils.isNotBlank(nodo.getValore())) {
		    _nodo = nodo.getValore();
		} else {
		    log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato", WebConstants.VERTICALIZZAZIONE_SIT_LDP_NODI);
		    throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro " + WebConstants.VERTICALIZZAZIONE_SIT_LDP_NODI
			    + " non settato ");
		}
		log.debug("deleteOccupazioneSuoloByIdentificativo# Nodo per cui le pratiche devono essere notificate : {}", _nodo);
		// Verifico se la pratica deve essere notificata al servizio 
		log.debug("deleteOccupazioneSuoloByIdentificativo# Verifico se la pratica deve essere notificata al servizio");
		log.debug("deleteOccupazioneSuoloByIdentificativo# E' stata trovata una pratica in Domande stc con idNodo uguale a {}", _nodo);
		// recupero url da chiamare
		Verticalizzazioniparametri url = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
			WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA);
		if (url != null && StringUtils.isNotBlank(url.getValore())) {
		    _url = url.getValore();
		} else {
		    log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato",
			    WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA);
		    throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro "
			    + WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA + " non settato ");
		}
		// recupero user name
		Verticalizzazioniparametri user = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
			WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME);
		if (user != null && StringUtils.isNotBlank(user.getValore())) {
		    _user = user.getValore();
		} else {
		    log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato", WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME);
		    throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro " + WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME
			    + " non settato ");
		}
		// recupero password
		Verticalizzazioniparametri pwd = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
			WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD);
		if (pwd != null && StringUtils.isNotBlank(pwd.getValore())) {
		    _pwd = pwd.getValore();
		} else {
		    log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato", WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD);
		    throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro " + WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD
			    + " non settato ");
		}
		String serviceName = getServiceName();
		String portName = getPortName();
		String namespace = getNameSpace();
		String methodName = getMethodName();
		log.debug("deleteOccupazioneSuoloByIdentificativo# Recupero le informazioni per l'invocazione del ws");
		String numeroPratica = identificativi.getDescrizione(); //istanza.getId().getCodice().toString();
		String idMittente = identificativi.getCodice(); //StringUtils.defaultIfEmpty(domandestc.getIdDomandamitt(), "");
		String reqe = "<ws:ComplexTypePraticaIdentificativiDelete xmlns:ws=\"https://ws.ldpgis.it/\"><identificativo_temporaneo>"
			+ idMittente + "</identificativo_temporaneo><numero_pratica>" + numeroPratica
			+ "</numero_pratica></ws:ComplexTypePraticaIdentificativiDelete>";
		log.debug("deleteOccupazioneSuoloByIdentificativo# Request: {}", reqe);
		URL wsdlURL = null;
		log.debug("deleteOccupazioneSuoloByIdentificativo# ws url : {}", _url);
		wsdlURL = new URL(_url);
		QName SERVICE_NAME = new QName(namespace, serviceName);
		Service service = Service.create(wsdlURL, SERVICE_NAME);
		Dispatch<Source> disp = service.createDispatch(new QName(namespace, portName), Source.class, Service.Mode.PAYLOAD);
		disp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, _url);
		disp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, _user);
		disp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, _pwd);
		ByteArrayInputStream bis = new ByteArrayInputStream(reqe.getBytes());
		Source request = new StreamSource(bis);
		response = disp.invoke(request);
		return true;
	    }
	} catch (MalformedURLException e) {
	    log.error("Errore durante la chiamata al ws LDP...: {}", e.getMessage());
	    if (istanza != null) {
		istanzeeventiService.insert(e.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	    }
	} catch (Exception e) {
	    log.error("Errore durante la chiamata al ws LDP...: {}", e.getMessage());
	    if (istanza != null) {
		istanzeeventiService.insert(e.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	    }
	}
	return false;
    }

    public boolean deleteAreaOnPeriodo(Istanze istanza, String identificativo, String inizio, String fine) {

	// Recupero praametri necessari per la chiamata al ws
	log.debug("Recupero i parametri necessari per la chiamata al ws dalla verticalizzazione {}", WebConstants.VERTICALIZZAIONE_SIT_LDP);
	String _url = "";
	String _user = "";
	String _pwd = "";
	String _nodo = "";
	Source response = null;
	try {
	    if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAIONE_SIT_LDP)) {
		Verticalizzazioniparametri nodo = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
			WebConstants.VERTICALIZZAZIONE_SIT_LDP_NODI);
		if (nodo != null && StringUtils.isNotBlank(nodo.getValore())) {
		    _nodo = nodo.getValore();
		} else {
		    log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato", WebConstants.VERTICALIZZAZIONE_SIT_LDP_NODI);
		    throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro " + WebConstants.VERTICALIZZAZIONE_SIT_LDP_NODI
			    + " non settato ");
		}
		log.debug("deleteAreaOnPeriodo# Nodo per cui le pratiche devono essere notificate : {}", _nodo);
		// Verifico se la pratica deve essere notificata al servizio 
		log.debug("deleteAreaOnPeriodo# Verifico se la pratica deve essere notificata al servizio");
		log.debug("deleteAreaOnPeriodo# E' stata trovata una pratica in Domande stc con idNodo uguale a {}", _nodo);
		// recupero url da chiamare
		Verticalizzazioniparametri url = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
			WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA);
		if (url != null && StringUtils.isNotBlank(url.getValore())) {
		    _url = url.getValore();
		} else {
		    log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato",
			    WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA);
		    throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro "
			    + WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA + " non settato ");
		}
		// recupero user name
		Verticalizzazioniparametri user = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
			WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME);
		if (user != null && StringUtils.isNotBlank(user.getValore())) {
		    _user = user.getValore();
		} else {
		    log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato", WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME);
		    throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro " + WebConstants.VERTICALIZZAZIONE_SIT_LDP_USERNAME
			    + " non settato ");
		}
		// recupero password
		Verticalizzazioniparametri pwd = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
			WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD);
		if (pwd != null && StringUtils.isNotBlank(pwd.getValore())) {
		    _pwd = pwd.getValore();
		} else {
		    log.error("Notifica al sistema LDP non avvenuto.Parametro {} non settato", WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD);
		    throw new RuntimeException("Notifica al sistema LDP non avvenuto.Parametro " + WebConstants.VERTICALIZZAZIONE_SIT_LDP_PASSWORD
			    + " non settato ");
		}
		String serviceName = getServiceName();
		String portName = getPortName();
		String namespace = getNameSpace();
		String methodName = getMethodName();
		CodiceDescrizioneBean identificativi = getIdentificativiPratica(istanza.getId().getCodice());
		log.debug("deleteAreaOnPeriodo# Recupero le informazioni per l'invocazione del ws");
		String numeroPratica = identificativi.getDescrizione(); //istanza.getId().getCodice().toString();
		String idMittente = identificativi.getCodice(); //StringUtils.defaultIfEmpty(domandestc.getIdDomandamitt(), "");
		String reqe = "<ws:ComplexTypePeriodo2Wkt xmlns:ws=\"https://ws.ldpgis.it/\"><id_temporaneo>" + idMittente
			+ "</id_temporaneo><num_pratica>" + numeroPratica + "</num_pratica><inizio_periodo>" + inizio
			+ "</inizio_periodo><fine_periodo>" + fine + "</fine_periodo><nome_disegno>" + identificativo
			+ "</nome_disegno></ws:ComplexTypePeriodo2Wkt>";
		log.debug("deleteAreaOnPeriodo# Request: {}", reqe);
		URL wsdlURL = null;
		log.debug("deleteAreaOnPeriodo# ws url : {}", _url);
		wsdlURL = new URL(_url);
		QName SERVICE_NAME = new QName(namespace, serviceName);
		Service service = Service.create(wsdlURL, SERVICE_NAME);
		Dispatch<Source> disp = service.createDispatch(new QName(namespace, portName), Source.class, Service.Mode.PAYLOAD);
		disp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, _url);
		disp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, _user);
		disp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, _pwd);
		ByteArrayInputStream bis = new ByteArrayInputStream(reqe.getBytes());
		Source request = new StreamSource(bis);
		response = disp.invoke(request);
		return true;
	    }
	} catch (MalformedURLException e) {
	    log.error("Errore durante la chiamata al ws LDP...: {}", e.getMessage());
	    istanzeeventiService.insert(e.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	} catch (Exception e) {
	    log.error("Errore durante la chiamata al ws LDP...: {}", e.getMessage());
	    istanzeeventiService.insert(e.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	}
	return false;
    }
}
