package it.alveo.firmaremota.aruba.client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import javax.net.ssl.TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.alveo.firmaremota.aruba.configurazione.ArubaParams;
import it.alveo.firmaremota.aruba.configurazione.ConfigurazioneBean;
import it.alveo.firmaremota.aruba.exception.GenericException;
import it.alveo.firmaremota.aruba.firma.ChiaveValoreBean;
import it.alveo.firmaremota.aruba.firma.DocumentoBean;
import it.alveo.firmaremota.aruba.firma.ProcessoBean;
import it.arubapec.arubasignservice.ArssReturn;
import it.arubapec.arubasignservice.ArubaSignService;
import it.arubapec.arubasignservice.Auth;
import it.arubapec.arubasignservice.CredentialsType;
import it.arubapec.arubasignservice.DictionarySignedAttributes;
import it.arubapec.arubasignservice.PdfProfile;
import it.arubapec.arubasignservice.PdfSignApparence;
import it.arubapec.arubasignservice.SignRequestV2;
import it.arubapec.arubasignservice.SignReturnV2;
import it.arubapec.arubasignservice.TypeTransport;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.xml.ws.BindingProvider;

public class ArubaClient {

    private static final Logger logger = LoggerFactory.getLogger(ArubaClient.class);
    private static final String PROFILO_FIRMA_PADES_BASIC = "BASIC";
    private ArubaSignService port;
    private ConfigurazioneBean config;
    private ArubaParams arubaParams;

    public ArubaClient(ArubaParams arubaParams, ConfigurazioneBean config) {

	this.config = config;
	this.arubaParams = arubaParams;
    }

    public String opensession(Auth auth) {

	try {
	    String retVal = getWsArubaARSSPort().opensession(auth);
	    switch (retVal.substring(3, 7)) {
	    case "0001": {
		throw new GenericException("0001: Errore generico nel processo di firma");
	    }
	    case "0002": {
		throw new GenericException("0002: Parametri non corretti per il tipo di trasporto indicato");
	    }
	    case "0003": {
		throw new GenericException("0003: Errore in fase di verifica delle credenziali");
	    }
	    case "0004": {
		throw new GenericException("0004: Errore nel PIN");
	    }
	    case "0005": {
		throw new GenericException("0005: Tipo di trasporto non valido");
	    }
	    case "0006": {
		throw new GenericException("0006: Tipo di trasporto non autorizzato");
	    }
	    case "0007": {
		throw new GenericException("0007: Profilo Di firma PDF non valido");
	    }
	    case "0008": {
		throw new GenericException(
			"0008: Impossibile completare l'operazione di marcatura temporale (es irraggiungibilità del servizio, marche residue terminate, etc..)");
	    }
	    case "0009": {
		throw new GenericException("0009: Credenziali di delega non valide");
	    }
	    case "00010": {
		throw new GenericException("0010: Lo stato dell'utente non è valido (es. utente sospeso)");
	    }
	    default: {
		break;
	    }
	    }
	    return retVal;
	} catch (Exception e) {
	    throw new GenericException(e.getMessage(), e.getCause());
	}
    }

    public String closesession(Auth auth, String idSessione) {

	try {
	    return getWsArubaARSSPort().closesession(auth, idSessione);
	} catch (Exception e) {
	    throw new GenericException(e.getMessage(), e.getCause());
	}
    }

    public ArssReturn sendCredential(CredentialsType credentialsType) {

	try {
	    Auth authWithoutOtp = this.getAuthWithoutOTP();
	    return getWsArubaARSSPort().sendCredential(authWithoutOtp, credentialsType);
	} catch (Exception e) {
	    throw new GenericException(e.getMessage(), e.getCause());
	}
    }

    public FirmaResponse firma(FirmaRequest request) {

	try {
	    FirmaResponse response;
	    switch (request.getTipoFirma()) {
	    case "CADES": {
		response = firmaCades(request);
		break;
	    }
	    case "PADES": {
		response = firmaPades(request);
		break;
	    }
	    default: {
		throw new GenericException("E' stata richiesta una firma che non è di tipo CADES/PADES e non è supportata");
	    }
	    }
	    //Setto l'esito
	    var docKO = response.getDocumentiFirmati().stream().filter(d -> "KO".equalsIgnoreCase(d.getEsito())).toList();
	    if (!docKO.isEmpty()) {
		var messaggio = docKO //
			.stream() //
			.collect(Collectors.groupingBy(doc -> new ImmutablePair<String, String>(doc.getReturnCode(), doc.getDescription()))).keySet() //
			.stream() //
			.map(err -> err.getKey() + ": " + err.getValue()) //
			.collect(Collectors.joining(","));
		response.setEsito(Esito.fromKO(messaggio));
	    } else {
		response.setEsito(Esito.fromOK());
	    }
	    return response;
	} catch (Exception ex) {
	    return FirmaResponse.fromKO(ex);
	}
    }

    private FirmaResponse firmaCades(FirmaRequest request) {

	try {
	    FirmaResponse response = new FirmaResponse();
	    //1. Autenticazione
	    Auth auth = this.getAuth();
	    //2 Avvio la sessione di firma
	    String sessionId = this.opensession(auth);
	    //3. Ciclo i file
	    for (ArubaDocumentoBean documento : request.getDocumenti()) {
		//3.1 Recupero il file da firmare
		DataSource fileDaFirmare = new FileDataSource(Paths.get(request.getFilesPath().toString(), documento.getNomeFile()).toFile());
		//3.2 Chiamata di firma
		var signRequest = this.getSignRequestV2(auth, sessionId, documento.getId(), new DataHandler(fileDaFirmare),
			request.isMarcaTemporaleRichiesta(), request.getEmailNotifica(), request.getCertId()).getValore();
		SignReturnV2 returnV2 = null;
		logger.debug("Creazione request di firma per il documento con guid {}", documento.getId());
		if (request.isFirmaCongiuntaCADES() && documento.getNomeFile().endsWith(".p7m")) {
		    returnV2 = this.addpkcs7SignV2(signRequest, request.isDetached());
		} else if (request.isFirmaCongiuntaCADES() && !documento.getNomeFile().endsWith(".p7m")) {
		    throw new RuntimeException(
			    "La firma congiunta richiede solo file con estensione .p7m, il file trovato non rispetta questa condizione: " +
					       documento.getNomeFile());
		} else {
		    returnV2 = this.pkcs7SignV2(signRequest, request.isDetached(), request.isReturnDER());
		}
		var docFirmato = mappingArubaResponse(request.getInfoProcesso(), documento.getId(), returnV2);
		logger.debug("Esito firma documento con guid {}: {} {}", documento.getId(), docFirmato.getReturnCode(), docFirmato.getDescription());
		response.getDocumentiFirmati().add(docFirmato);
	    }
	    //4 Chiudo la sessione
	    this.closesession(auth, sessionId);
	    return response;
	} catch (Exception e) {
	    throw new GenericException(e.getMessage(), e.getCause());
	}
    }

    private FirmaResponse firmaPades(FirmaRequest request) {

	try {
	    FirmaResponse response = new FirmaResponse();
	    //1. Autenticazione
	    Auth auth = this.getAuth();
	    PdfSignApparence pdfSignApparence = getPdfSignApparence(request);
	    PdfProfile pdfProfile = getPdfProfile(request);
	    PDFUtilitiesService pdfUtilities = new PDFUtilitiesService();
	    //2. Apro la sessione di firma
	    String sessionId = this.opensession(auth);
	    //3. Ciclo i file
	    for (ArubaDocumentoBean documento : request.getDocumenti()) {
		//3.1 Recupero il file da firmare
		DataSource fileDaFirmare = new FileDataSource(Paths.get(request.getFilesPath().toString(), documento.getNomeFile()).toFile());
		//3.2 Calcolo il numero di pagine
		var file = Paths.get(request.getFilesPath().toString(), documento.getNomeFile()).toFile();
		var byteDoc = Files.readAllBytes(file.toPath());
		Integer numPage = pdfUtilities.getNumberPagePdf(byteDoc);
		setPdfSignApparence(pdfSignApparence, numPage, request);
		//3.3 Chiamata di firma
		var signRequest = this.getSignRequestV2(auth, sessionId, documento.getId(), new DataHandler(fileDaFirmare),
			request.isMarcaTemporaleRichiesta(), request.getEmailNotifica(), request.getCertId()).getValore();
		SignReturnV2 returnV2 = this.pdfsignatureV2(signRequest, pdfSignApparence, pdfProfile, null, null);
		//3.4 Lo aggiungo alla lista dei file firmati
		response.getDocumentiFirmati().add(mappingArubaResponse(request.getInfoProcesso(), documento.getId(), returnV2));
	    }
	    //4 Chiudo la sessione
	    this.closesession(auth, sessionId);
	    return response;
	} catch (Exception e) {
	    throw new GenericException(e.getMessage(), e.getCause());
	}
    }

    private PdfSignApparence setPdfSignApparence(PdfSignApparence pdfSignApparence, Integer numeroPaginePdf, FirmaRequest request) {

	logger.debug("setPdfSignApparence# set pagina firma ....");
	if (request.getNumeroPaginaFirma() == null) {
	    logger.debug("getPdfSignApparence# Applico la firma sull'ultima pagina");
	    pdfSignApparence.setPage(numeroPaginePdf);
	} else {
	    if (request.getNumeroPaginaFirma() > numeroPaginePdf) {
		logger.debug("getPdfSignApparence# Pagina numero: {} > Numero pagine ({}). Applico la firma sull'ultima pagina",
			request.getNumeroPaginaFirma(), numeroPaginePdf);
		pdfSignApparence.setPage(numeroPaginePdf);
	    } else {
		logger.debug("getPdfSignApparence# Pagina numero: {}. Applico la firma sulla pagina indicata", request.getNumeroPaginaFirma());
		pdfSignApparence.setPage(request.getNumeroPaginaFirma());
	    }
	}
	pdfSignApparence.setLeftx(request.getPossXRettLeft());
	pdfSignApparence.setLefty(request.getPossYRettLeft());
	pdfSignApparence.setRightx(request.getPossXRettRight());
	pdfSignApparence.setRighty(request.getPossYRettRight());
	return pdfSignApparence;
    }

    private PdfProfile getPdfProfile(FirmaRequest request) {

	if (StringUtils.isNotBlank(request.getProfiloFirmaPades()) && request.getProfiloFirmaPades().equalsIgnoreCase(PROFILO_FIRMA_PADES_BASIC)) {
	    return PdfProfile.BASIC;
	}
	return PdfProfile.PADESBES;
    }

    private PdfSignApparence getPdfSignApparence(FirmaRequest request) {

	PdfSignApparence pdfSignApparence = new PdfSignApparence();
	pdfSignApparence.setLeftx(request.getPossXRettLeft());
	pdfSignApparence.setLefty(request.getPossYRettLeft());
	pdfSignApparence.setRightx(request.getPossXRettRight());
	pdfSignApparence.setRighty(request.getPossYRettRight());
	pdfSignApparence.setTesto(StringUtils.isBlank(request.getTestoFirmaPades()) ? null : request.getTestoFirmaPades());
	pdfSignApparence.setReason(StringUtils.isBlank(request.getMotivoFirmaPades()) ? null : request.getMotivoFirmaPades());
	return pdfSignApparence;
    }

    private DocumentoFirmatoBean mappingArubaResponse(ProcessoBean infoProcesso, String guid, SignReturnV2 signReturnV2)
	    throws FileNotFoundException, IOException {

	if (signReturnV2 == null) {
	    throw new GenericException(
		    "Impossibile utilizzare il metodo mappingArubaResponse senza passare la response della firma da parte di Aruba");
	}
	DocumentoFirmatoBean f = new DocumentoFirmatoBean();
	f.setGuid(guid);
	f.setDescription(StringUtils.defaultIfEmpty(signReturnV2.getDescription(), ""));
	f.setReturnCode(StringUtils.defaultIfEmpty(signReturnV2.getReturnCode(), ""));
	f.setStatus(StringUtils.defaultIfEmpty(signReturnV2.getStatus(), ""));
	if ("OK".equals(signReturnV2.getStatus())) {
	    f.setEsito("OK");
	    f.setNomeFile(this.salvaFileFirmato(infoProcesso, guid, signReturnV2));
	} else {
	    f.setEsito("KO");
	}
	return f;
    }

    private String salvaFileFirmato(ProcessoBean infoProcesso, String guidDocumento, SignReturnV2 signReturnV2)
	    throws FileNotFoundException, IOException {

	var pathProcessati = Paths.get(this.arubaParams.getTempPath(), infoProcesso.getSessionId(), this.arubaParams.getProcessatiPath());
	DocumentoBean documento = infoProcesso.getDocumenti().stream().filter(d -> d.getGuid().equalsIgnoreCase(guidDocumento)).findFirst().get();
	logger.debug("Salvataggio del file con guid {} firmato digitalmente", documento.getGuid());
	var nomeFile = documento.getNuovoNome() + ".p7m";
	var filePath = Paths.get(pathProcessati.toString(), nomeFile);
	try (InputStream inputStream = signReturnV2.getStream().getInputStream()) {
	    try (OutputStream outputStream = new FileOutputStream(filePath.toFile())) {
		logger.debug("Inizio scrittura file");
		byte[] b = new byte[512];
		int length;
		while ((length = inputStream.read(b)) != -1) {
		    outputStream.write(b, 0, length);
		    outputStream.flush();
		}
		logger.debug("Fine scrittura file");
	    }
	}
	return nomeFile;
    }

    private ChiaveValoreBean<String, SignRequestV2> getSignRequestV2(Auth auth, String sessionId, String guidDocumento, DataHandler documento,
	    boolean marcaTemporaleRichiesta, String emailNotifica, String certId) {

	ChiaveValoreBean<String, SignRequestV2> bean = new ChiaveValoreBean<String, SignRequestV2>();
	SignRequestV2 requestV2 = new SignRequestV2();
	requestV2.setTransport(TypeTransport.STREAM);
	requestV2.setStream(documento);
	requestV2.setRequiredmark(marcaTemporaleRichiesta);
	requestV2.setIdentity(auth);
	requestV2.setNotifymail(emailNotifica);
	requestV2.setSessionId(sessionId);
	requestV2.setCertID(certId);
	bean.setChiave(guidDocumento);
	bean.setValore(requestV2);
	return bean;
    }

    private SignReturnV2 pdfsignatureV2(SignRequestV2 signRequestV2, PdfSignApparence pdfSignApparence, PdfProfile pdfProfile, String password,
	    DictionarySignedAttributes dictionarySignedAttributes) {

	if (logger.isDebugEnabled()) {
	    logger.debug("pdfsignatureV2#Inizio invocazione servizio del WS di firma pdfsignatureV2 (PAdES)...");
	}
	SignReturnV2 result = null;
	try {
	    result = getWsArubaARSSPort().pdfsignatureV2(signRequestV2, pdfSignApparence, null, pdfProfile, password, dictionarySignedAttributes);
	} catch (Exception e) {
	    throw new GenericException(e.getMessage(), e.getCause());
	}
	if (logger.isDebugEnabled()) {
	    logger.debug("pdfsignatureV2#Fine invocazione servizio del WS di firma pdfsignatureV2 (PAdES)...");
	}
	return result;
    }

    private SignReturnV2 addpkcs7SignV2(SignRequestV2 signRequestV2, boolean detached) {

	try {
	    return getWsArubaARSSPort().addpkcs7Sign(signRequestV2, detached);
	} catch (Exception e) {
	    throw new GenericException(e.getMessage(), e.getCause());
	}
    }

    private SignReturnV2 pkcs7SignV2(SignRequestV2 signRequestV2, boolean detached, boolean returnder) {

	try {
	    return getWsArubaARSSPort().pkcs7SignV2(signRequestV2, detached, returnder);
	} catch (Exception e) {
	    throw new GenericException(e.getMessage(), e.getCause());
	}
    }

    private Auth getAuthWithoutOTP() {

	Auth auth = new Auth();
	auth.setTypeHSM(this.config.getTypeHSM());
	auth.setTypeOtpAuth(this.config.getProfiloFirma());
	auth.setUser(this.config.getUsername());
	auth.setUserPWD(this.config.getDecryptedPassword());
	return auth;
    }

    private Auth getAuth() {

	Auth auth = new Auth();
	auth.setTypeHSM(this.config.getTypeHSM());
	auth.setTypeOtpAuth(this.config.getProfiloFirma());
	auth.setUser(this.config.getUsername());
	auth.setUserPWD(this.config.getDecryptedPassword());
	auth.setOtpPwd(this.config.getOtp());
	return auth;
    }

    private ArubaSignService getWsArubaARSSPort() throws Exception {

	if (this.port == null) {
	    try {
		if (StringUtils.isBlank(this.config.getUrl())) {
		    logger.error("Non è stata valorizzata la url del servizio di firma esposto da Aruba");
		}
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ArubaSignService.class);
		factory.setAddress(this.config.getUrl());
		port = (ArubaSignService) factory.create();
		Client proxy = ClientProxy.getClient(port);
		LoggingFeature feature = new LoggingFeature();
		feature.setPrettyLogging(true);
		feature.setLimit(-1); // nessun limite
		feature.initialize(proxy, proxy.getBus());
		HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
		if (Boolean.TRUE.equals(this.config.getRelaxSSL())) {
		    //disable ssl cert verification
		    TLSClientParameters params = new TLSClientParameters();
		    TrustManager[] trustManagers = new TrustManager[] { new TrustAllX509TrustManager() };
		    params.setTrustManagers(trustManagers);
		    params.setDisableCNCheck(true);
		    conduit.setTlsClientParameters(params);
		}
		BindingProvider bp = (BindingProvider) port;
		jakarta.xml.ws.soap.SOAPBinding soapBinding = (jakarta.xml.ws.soap.SOAPBinding) bp.getBinding();
		soapBinding.setMTOMEnabled(true);
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.config.getUrl());
		// HTTPClientPolicy - Properties used to configure a client-side HTTP port  
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
		httpClientPolicy.setConnectionTimeout(120000); // Line #2  
		httpClientPolicy.setReceiveTimeout(600000); // Line #3  
		conduit.setClient(httpClientPolicy);
	    } catch (RuntimeException e) {
		throw new GenericException("Errore durante l'inizializzazione della porta " + e);
	    }
	}
	return this.port;
    }
}
