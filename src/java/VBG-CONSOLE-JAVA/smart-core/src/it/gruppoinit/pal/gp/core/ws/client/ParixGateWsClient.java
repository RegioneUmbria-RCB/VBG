package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.sigepro.cart.service.impl.AppProxySelector;
import it.infocamere.parixgate.services.gate.GateSoapBindingStub;
import it.infocamere.parixgate.services.gate.ICRSimpleWSImplService;
import it.infocamere.parixgate.services.gate.ICRSimpleWSImplServiceLocator;

import java.net.MalformedURLException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.Security;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ParixGateWsClient {

    private static final Logger log = LoggerFactory.getLogger(ParixGateWsClient.class);
    private String parixgateWsUrl;
    private String parixgateWsUser;
    private String parixgateWsPassword;
    private String parixgateWsSwitchcontroll;
    private String trustStore;
    private String trustStorePassword;
    private String keyStore;
    private String keyStorePassword;

    public ParixGateWsClient() {

	super();
    }

    public void setParixgateWsUrl(String parixgateWsUrl) {

	this.parixgateWsUrl = parixgateWsUrl;
    }

    public void setParixgateWsUser(String parixgateWsUser) {

	this.parixgateWsUser = parixgateWsUser;
    }

    public void setParixgateWsPassword(String parixgateWsPassword) {

	this.parixgateWsPassword = parixgateWsPassword;
    }

    public void setParixgateWsSwitchcontroll(String parixgateWsSwitchcontroll) {

	this.parixgateWsSwitchcontroll = parixgateWsSwitchcontroll;
    }

    public void setTrustStore(String trustStore) {

	this.trustStore = trustStore;
    }

    public void setKeyStorePassword(String keyStorePassword) {

	this.keyStorePassword = keyStorePassword;
    }

    public void setKeyStore(String keyStore) {

	this.keyStore = keyStore;
    }

    public void setTrustStorePassword(String trustStorePassword) {

	this.trustStorePassword = trustStorePassword;
    }

    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    public String getDettaglioImpresa(String provinciaRea, String numeroRea) throws RemoteException, MalformedURLException, ServiceException {

	if (log.isDebugEnabled()) {
	    log.debug("getDettaglioImpresa# creo la porta");
	}
	GateSoapBindingStub port = getWsPort();
	if (log.isDebugEnabled()) {
	    log.debug("getDettaglioImpresa# porta creata effettuo la chiamata con ProvRea: {}, NrRea: {}", provinciaRea, numeroRea);
	}
	String result = port.dettaglioCompletoImpresa(provinciaRea, numeroRea, parixgateWsSwitchcontroll, parixgateWsUser, parixgateWsPassword);
	if (log.isDebugEnabled()) {
	    log.debug("getDettaglioImpresa# result: {}", result);
	}
	return result;
    }

    public String getDettaglioImpresaRidotto(String provinciaRea, String numeroRea) throws RemoteException, MalformedURLException, ServiceException {

	if (log.isDebugEnabled()) {
	    log.debug("getDettaglioImpresa# creo la porta");
	}
	GateSoapBindingStub port = getWsPort();
	if (log.isDebugEnabled()) {
	    log.debug("getDettaglioImpresa# porta creata effettuo la chiamata con ProvRea: {}, NrRea: {}", provinciaRea, numeroRea);
	}
	String result = port.dettaglioRidottoImpresa(provinciaRea, numeroRea, parixgateWsSwitchcontroll, parixgateWsUser, parixgateWsPassword);
	if (log.isDebugEnabled()) {
	    log.debug("getDettaglioImpresa# result: {}", result);
	}
	return result;
    }

    public String getRicercaImpreseNoncessateByCodiceFiscale(String codiceFiscale) throws RemoteException, MalformedURLException, ServiceException {

	if (log.isDebugEnabled()) {
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# creo la porta");
	}
	GateSoapBindingStub port = getWsPort();
	if (log.isDebugEnabled()) {
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# porta creata effettuo la chiamata con codiceFiscale: {}, NrRea: {}", codiceFiscale);
	}
	String result = port.ricercaImpreseNonCessatePerCodiceFiscale(codiceFiscale, parixgateWsSwitchcontroll, parixgateWsUser, parixgateWsPassword);
	if (log.isDebugEnabled()) {
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# result: {}", result);
	}
	return result;
    }

    private GateSoapBindingStub getWsPort() throws MalformedURLException, ServiceException {

	ICRSimpleWSImplService service = new ICRSimpleWSImplServiceLocator();
	GateSoapBindingStub port = (GateSoapBindingStub) service.getgate(new URL(parixgateWsUrl));
	String basicAuthUSer = getWsBasicAuthUsername();
	// Codice per caricare il certificato p12 per l'autenticazione al WS
	//	System.setProperty("javax.net.ssl.trustStore", trustStore);
	//	System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
	//	System.setProperty("javax.net.ssl.trustStoreType", "jks");
	//	System.setProperty("javax.net.ssl.keyStore", keyStore);
	//	System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
	//	System.setProperty("javax.net.ssl.keyStoreType", "jks");
	//	System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
	// Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	try {
	    setProxy();
	} catch (Exception e) {
	    log.error("getWsPort# Errore nell'impostazione del Proxy {}", e);
	}
	if (StringUtils.isNotBlank(basicAuthUSer)) {
	    port.setUsername(basicAuthUSer);
	    String basicAuthPassword = getWsBasicAuthPassword();
	    port.setPassword(basicAuthPassword);
	}
	return port;
    }

    private void setProxy() throws URISyntaxException {

	String proxy = getProxyAddress();
	if (StringUtils.isNotBlank(proxy)) {
	    if (log.isDebugEnabled()) {
		log.debug("getIntegrationManagerPort: setto le impostazioni del proxy [{}]", new Object[] { proxy });
	    }
	    URI proxyToURI = new URI(proxy);
	    AppProxySelector proxySelector = new AppProxySelector(true, proxyToURI.getHost(), proxyToURI.getPort(), getWsUrl());
	    ProxySelector.setDefault(proxySelector);
	}
    }

    private String getParametroVerticalizzazione(String nomeParametro, boolean throwExceptionIfNull) {

	Verticalizzazioniparametri urlParix = verticalizzazioniService.getVerticalizzazioniparametri("WSANAGRAFE_PARIX", nomeParametro);
	if (urlParix != null) {
	    String result = StringUtils.defaultString(urlParix.getValore()).trim();
	    if (StringUtils.isNotBlank(result)) {
		return result;
	    }
	}
	if (throwExceptionIfNull) {
	    throw new RuntimeException("Il parametro " + nomeParametro
		    + " della verticalizzazione WSANAGRAFE_PARIX non è stato configurato correttamente");
	}
	return "";
    }

    private String getWsUrl() {

	if (StringUtils.isBlank(parixgateWsUrl)) {
	    return getParametroVerticalizzazione("URL", true);
	} else {
	    return parixgateWsUrl;
	}
    }

    private String getWsPassword() {

	if (StringUtils.isBlank(parixgateWsPassword)) {
	    return getParametroVerticalizzazione("PASSWORD", true);
	} else {
	    return parixgateWsPassword;
	}
    }

    private String getWsUsername() {

	if (StringUtils.isBlank(parixgateWsUser)) {
	    return getParametroVerticalizzazione("USER", true);
	} else {
	    return parixgateWsUser;
	}
    }

    private String getWsSwitchControl() {

	if (StringUtils.isBlank(parixgateWsSwitchcontroll)) {
	    return getParametroVerticalizzazione("SWITCHCONTROL", false);
	} else {
	    return parixgateWsSwitchcontroll;
	}
    }

    private String getWsBasicAuthUsername() {

	return getParametroVerticalizzazione("BASIC_AUTH_USER", false);
    }

    private String getWsBasicAuthPassword() {

	return getParametroVerticalizzazione("BASIC_AUTH_PASSWORD", false);
    }

    private String getProxyAddress() {

	return getParametroVerticalizzazione("PROXY_ADDRESS", false);
    }
}
