package it.gruppoinit.pal.gp.core.ws.client;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.SSLSocketFactoryGenerator;
import it.gruppoinit.pal.gp.core.ws.client.parix_cloud.DettaglioCompletoImpresaRequest;
import it.gruppoinit.pal.gp.core.ws.client.parix_cloud.DettaglioCompletoImpresaResponse;
import it.gruppoinit.pal.gp.core.ws.client.parix_cloud.GatePort;
import it.gruppoinit.pal.gp.core.ws.client.parix_cloud.ObjectFactory;
import it.gruppoinit.pal.gp.core.ws.client.parix_cloud.RicercaImpreseNonCessatePerCodiceFiscaleRequest;
import it.gruppoinit.pal.gp.core.ws.client.parix_cloud.RicercaImpreseNonCessatePerCodiceFiscaleResponse;

public class ParixGateV2WsClient {

    private static final String VERT_WSANAGRAFE_PARIX = "WSANAGRAFE_PARIX_CLOUD";
    private static final String VERT_PROXY_ADDRESS = "PROXY_ADDRESS";
    private static final String VERT_BASIC_AUTH_PASSWORD = "BASIC_AUTH_PASSWORD";
    private static final String VERT_BASIC_AUTH_USER = "BASIC_AUTH_USER";
    private static final String VERT_SWITCHCONTROL = "SWITCHCONTROL";
    private static final String VERT_USER = "USER";
    private static final String VERT_PASSWORD = "PASSWORD";
    private static final String VERT_URL = "URL";
    private static final String VERT_JAVA_PATH_CERTIFICATO = "JAVA_PATH_CERTIFICATO";
    private static final String VERT_TARGET_NAMESPACE = "TARGET_NAMESPACE";
    private static final Logger log = LoggerFactory.getLogger(ParixGateV2WsClient.class);
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    private ObjectFactory objectFactory = new ObjectFactory();

    public ParixGateV2WsClient() {

	super();
    }

    public String getDettaglioImpresa(String provinciaRea, String numeroRea) throws FunzioneBusinessRemotaException {

	if (log.isDebugEnabled()) {
	    log.debug("getDettaglioImpresa# creo la porta");
	}
	GatePort port = getPort("DettaglioCompletoImpresa");
	if (log.isDebugEnabled()) {
	    log.debug("getDettaglioImpresa# porta creata effettuo la chiamata con ProvRea: {}, NrRea: {}", provinciaRea, numeroRea);
	}
	DettaglioCompletoImpresaRequest request = new DettaglioCompletoImpresaRequest();
	request.setAbilitaBilinguismo(objectFactory.createDettaglioCompletoImpresaRequestAbilitaBilinguismo("no"));
	request.setAbilitaSoci(objectFactory.createDettaglioCompletoImpresaRequestAbilitaSoci("no"));
	request.setNReaSede(Integer.parseInt(numeroRea));
	request.setPassword(getWsPassword());
	request.setSglPrvSede(provinciaRea);
	request.setSwitchControl(objectFactory.createDettaglioCompletoImpresaRequestSwitchControl(getWsSwitchControl()));
	request.setUser(getWsUsername());
	DettaglioCompletoImpresaResponse res = port.dettaglioCompletoImpresa(request);
	String result = res.getDettaglioCompletoImpresaReturn();
	if (log.isDebugEnabled()) {
	    log.debug("getDettaglioImpresa# result: {}", result);
	}
	return result;
    }

    public String getRicercaImpreseNoncessateByCodiceFiscale(String codiceFiscale) throws FunzioneBusinessRemotaException {

	if (log.isDebugEnabled()) {
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# creo la porta");
	}
	GatePort port = getPort("RicercaImpreseNonCessatePerCodiceFiscale");
	if (log.isDebugEnabled()) {
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# porta creata effettuo la chiamata con codiceFiscale: {}", codiceFiscale);
	}
	RicercaImpreseNonCessatePerCodiceFiscaleRequest request = new RicercaImpreseNonCessatePerCodiceFiscaleRequest();
	request.setCodiceFiscale(codiceFiscale);
	request.setPassword(getWsPassword());
	request.setSwitchControl(objectFactory.createRicercaImpreseNonCessatePerCodiceFiscaleRequestSwitchControl(getWsSwitchControl()));
	request.setUser(getWsUsername());
	RicercaImpreseNonCessatePerCodiceFiscaleResponse ricercaImpreseNonCessatePerCodiceFiscale = port
		.ricercaImpreseNonCessatePerCodiceFiscale(request);
	String result = ricercaImpreseNonCessatePerCodiceFiscale.getRicercaImpreseNonCessatePerCodiceFiscaleReturn();
	if (StringUtils.contains(result, "gate:error")) {
	    result = StringUtils.replace(result, "gate:error", "error");
	}
	if (log.isDebugEnabled()) {
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# result: {}", result);
	}
	return result;
    }

    private GatePort getPort(String azione) throws FunzioneBusinessRemotaException {

	try {
	    String url = getWsUrl();
	    QName serviceName = new QName(getTargetNameSpace(), "GatePortService");
	    URL wsdlUrl = new URL(url);
	    Service service = Service.create(wsdlUrl, serviceName);
	    QName portName = new QName(getTargetNameSpace(), "GatePortSoap11");
	    GatePort port = service.getPort(portName, GatePort.class);
	    Client proxy = ClientProxy.getClient(port);
	    HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	    BindingProvider bp = (BindingProvider) port;
	    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	    httpClientPolicy.setConnectionTimeout(20000); // Line #2  
	    httpClientPolicy.setReceiveTimeout(120000); // Line #3  
	    conduit.setClient(httpClientPolicy);
	    setProxy(conduit);
	    certificateAuth(conduit);
	    return port;
	} catch (MalformedURLException e) {
	    log.error("GatePorrt {}", e);
	}
	return null;
    }

    private void setProxy(HTTPConduit conduit) throws FunzioneBusinessRemotaException {

	String proxy = getProxyAddress();
	if (StringUtils.isNotBlank(proxy)) {
	    URI proxyToURI = null;
	    try {
		proxyToURI = new URI(proxy);
	    } catch (URISyntaxException e) {
		throw new FunzioneBusinessRemotaException(e);
	    }
	    conduit.getClient().setProxyServer(proxyToURI.getHost());
	    conduit.getClient().setProxyServerPort(proxyToURI.getPort());
	}
    }

    private void certificateAuth(HTTPConduit conduit) throws FunzioneBusinessRemotaException {

	SSLSocketFactory sslSocketFactory;
	sslSocketFactory = getSSLSocketFactory();
	if (sslSocketFactory != null) {
	    TLSClientParameters params = new TLSClientParameters();
	    params.setSSLSocketFactory(sslSocketFactory);
	    conduit.setTlsClientParameters(params);
	} else {
	    // BASIC AUTHENTICATION
	    basicAuth(conduit);
	}
    }

    private void basicAuth(HTTPConduit conduit) {

	String basicAuthUser = getWsBasicAuthUsername();
	if (StringUtils.isNotBlank(basicAuthUser)) {
	    AuthorizationPolicy authorizationPolicy = new AuthorizationPolicy();
	    authorizationPolicy.setUserName(basicAuthUser);
	    authorizationPolicy.setPassword(getWsBasicAuthPassword());
	    authorizationPolicy.setAuthorizationType("Basic");
	    conduit.setAuthorization(authorizationPolicy);
	}
    }

    private SSLSocketFactory getSSLSocketFactory() throws FunzioneBusinessRemotaException {

	String sslAttributes = getJavaPathCertificato();
	if (StringUtils.isBlank(sslAttributes)) {
	    return null;
	}
	String[] attrs = sslAttributes.split("\\|");
	if (attrs.length != 5) {
	    throw new InvalidConfigurationException(
		    "Parametro " + VERT_JAVA_PATH_CERTIFICATO + " della regola WSANAGRAFE_PARIX non configurato correttamente ");
	}
	String alias = attrs[0];
	String keystore = attrs[1];
	String truststore = attrs[2];
	String keystorePwd = attrs[3];
	String truststorePwd = attrs[4];
	try {
	    return SSLSocketFactoryGenerator.getSSLSocketFactory(alias, keystore, truststore, keystorePwd, truststorePwd, "jks");
	} catch (Exception e) {
	    log.error("Errore nella creazione della soketfactory {}", e.getMessage(), e);
	    throw new FunzioneBusinessRemotaException("Errore nella creazione della SSLSocketFactory: " + e.getMessage(), e);
	}
    }

    private String getParametroVerticalizzazione(String nomeParametro, boolean throwExceptionIfNull) {

	Verticalizzazioniparametri urlParix = verticalizzazioniService.getVerticalizzazioniparametri(VERT_WSANAGRAFE_PARIX, nomeParametro);
	if (urlParix != null) {
	    String result = StringUtils.defaultString(urlParix.getValore()).trim();
	    if (StringUtils.isNotBlank(result)) {
		return result;
	    }
	}
	if (throwExceptionIfNull) {
	    throw new RuntimeException(
		    "Il parametro " + nomeParametro + " della verticalizzazione " + VERT_WSANAGRAFE_PARIX + " non è stato configurato correttamente");
	}
	return "";
    }

    private String getJavaPathCertificato() {

	return getParametroVerticalizzazione(VERT_JAVA_PATH_CERTIFICATO, false);
    }

    private String getWsUrl() {

	return getParametroVerticalizzazione(VERT_URL, true);
    }

    private String getWsPassword() {

	return getParametroVerticalizzazione(VERT_PASSWORD, true);
    }

    private String getWsUsername() {

	return getParametroVerticalizzazione(VERT_USER, true);
    }

    private String getWsSwitchControl() {

	return getParametroVerticalizzazione(VERT_SWITCHCONTROL, false);
    }

    private String getWsBasicAuthUsername() {

	return getParametroVerticalizzazione(VERT_BASIC_AUTH_USER, false);
    }

    private String getWsBasicAuthPassword() {

	return getParametroVerticalizzazione(VERT_BASIC_AUTH_PASSWORD, false);
    }

    private String getProxyAddress() {

	return getParametroVerticalizzazione(VERT_PROXY_ADDRESS, false);
    }

    private String getTargetNameSpace() {

	return getParametroVerticalizzazione(VERT_TARGET_NAMESPACE, false);
    }
}
