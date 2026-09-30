package it.gruppoinit.sigepro.definitions.movimenti;

import it.gruppoinit.sigepro.schemas.messages.movimenti.MovimentiDownloadZipLogicoRequest;
import it.gruppoinit.sigepro.schemas.messages.movimenti.MovimentiDownloadZipLogicoResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;

import java.net.URL;
import java.util.Map;

import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.soap.MTOMFeature;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MovimentiWSClient {

    public static final String SECURITY_PARAM_WSHOSTURL_JAVA = "WSHOSTURL_JAVA";
    private static Logger log = LoggerFactory.getLogger(MovimentiWSClient.class);
    private String urlServicesMovimentiWSDL;
    private String MovimentiWsUrl;
    private long timeout = 120000; // defaul 120.000 ms due minuti
    private boolean mtomEnabled = true;
    private SigeproSecurityWebServiceClient securityWebServiceClient;
    private boolean attachLoggingInterceptors = false;
    private Movimenti port;
    private LoggingInInterceptor logInbound;
    private LoggingOutInterceptor logOutbound;

    private Movimenti getPort(String token) {

	log.debug("getMovimentiWsPort: url={}", MovimentiWsUrl);
	if (this.port == null) {
	    if (this.MovimentiWsUrl == null) {
		Map<String, String> params = securityWebServiceClient.getParams(SECURITY_PARAM_WSHOSTURL_JAVA);
		MovimentiWsUrl = params.get(SECURITY_PARAM_WSHOSTURL_JAVA) + urlServicesMovimentiWSDL;
	    }
	    MovimentiService MovimentiService = null;
	    try {
		MovimentiService = new MovimentiService(new URL(this.MovimentiWsUrl));
		WebServiceFeature mtom = new MTOMFeature(mtomEnabled, 0);
		port = MovimentiService.getMovimentiSoap11(mtom);
		Client proxy = ClientProxy.getClient(port);
		Endpoint cxfEndpoint = proxy.getEndpoint();
		// Map<String, Object> outProps = new HashMap<String, Object>();
		HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(timeout);
		httpClientPolicy.setReceiveTimeout(timeout);
		if (this.attachLoggingInterceptors) {
		    if (this.logInbound != null) {
			cxfEndpoint.getInInterceptors().add(this.logInbound);
		    }
		    if (this.logOutbound != null) {
			cxfEndpoint.getOutInterceptors().add(this.logOutbound);
		    }
		}
		conduit.setClient(httpClientPolicy);
	    } catch (Exception e) {
		log.error("getMovimentiWsPort(): {}", e.getMessage());
		throw new RuntimeException("Errore durante l'inizializzazione della chiamata al ws getMovimentiWsPort: " + e.getMessage(), e);
	    }
	}
	return this.port;
    }

    public MovimentiDownloadZipLogicoResponse downloadZip(String alias, int codiceMovimento, String uuidIstanza) {

	String token = securityWebServiceClient.loginAPP(alias);
	MovimentiDownloadZipLogicoRequest request = new MovimentiDownloadZipLogicoRequest();
	request.setToken(token);
	request.setCodicemovimento(codiceMovimento);
	request.setUuidIstanza(uuidIstanza);
	MovimentiDownloadZipLogicoResponse response = getPort(token).movimentiDownloadZipLogico(request);
	return response;
    }

    public void setAttachLoggingInterceptors(boolean attachLoggingInterceptors) {

	this.attachLoggingInterceptors = attachLoggingInterceptors;
    }

    public void setMtomEnabled(boolean mtomEnabled) {

	this.mtomEnabled = mtomEnabled;
    }

    public void setTimeout(long timeout) {

	this.timeout = timeout;
    }

    /**
     * ad oggi è /services/Movimenti?wsdl che insieme a SECURITY_PARAM_WSHOSTURL_JAVA formanol'url al servizio degli
     * Movimenti
     * 
     * @param urlServicesMovimentiWSDL
     */
    public void setUrlServicesMovimentiWSDL(String urlServicesMovimentiWSDL) {

	this.urlServicesMovimentiWSDL = urlServicesMovimentiWSDL;
    }

    public void setSecurityWebServiceClient(SigeproSecurityWebServiceClient securityWebServiceClient) {

	this.securityWebServiceClient = securityWebServiceClient;
    }

    public void setLogOutbound(LoggingOutInterceptor logOutbound) {

	this.logOutbound = logOutbound;
    }

    public void setLogInbound(LoggingInInterceptor logInbound) {

	this.logInbound = logInbound;
    }
}
