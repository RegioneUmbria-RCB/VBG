package it.gruppoinit.sigepro.definitions.oggetti;

import it.gruppoinit.sigepro.schemas.messages.oggetti.OggettiFindByUidRequest;
import it.gruppoinit.sigepro.schemas.messages.oggetti.OggettiFindRequest;
import it.gruppoinit.sigepro.schemas.messages.oggetti.OggettiFindResponse;
import it.gruppoinit.sigepro.schemas.messages.oggetti.OggettiInsertRequest;
import it.gruppoinit.sigepro.schemas.messages.oggetti.OggettiInsertResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;

import java.math.BigInteger;
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

public class OggettiWSClient {

    public static final String SECURITY_PARAM_WSHOSTURL_JAVA = "WSHOSTURL_JAVA";
    private static Logger log = LoggerFactory.getLogger(OggettiWSClient.class);
    private String urlServicesOggettiWSDL;
    private String oggettiWsUrl;
    private long timeout = 120000; // defaul 120.000 ms due minuti
    private boolean mtomEnabled = true;
    private SigeproSecurityWebServiceClient securityWebServiceClient;
    private boolean attachLoggingInterceptors = false;
    private Oggetti port;
    private LoggingInInterceptor logInbound;
    private LoggingOutInterceptor logOutbound;

    private Oggetti getPort(String token) {

	log.debug("getOggettiWsPort: url={}", oggettiWsUrl);
	if (this.port == null) {
	    if (this.oggettiWsUrl == null) {
		Map<String, String> params = securityWebServiceClient.getParams(SECURITY_PARAM_WSHOSTURL_JAVA);
		oggettiWsUrl = params.get(SECURITY_PARAM_WSHOSTURL_JAVA) + urlServicesOggettiWSDL;
	    }
	    OggettiService oggettiService = null;
	    try {
		oggettiService = new OggettiService(new URL(this.oggettiWsUrl));
		WebServiceFeature mtom = new MTOMFeature(mtomEnabled, 0);
		port = oggettiService.getOggettiSoap11(mtom);
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
		log.error("getOggettiWsPort(): {}", e.getMessage());
		throw new RuntimeException("Errore durante l'inizializzazione della chiamata al ws getOggettiWsPort: " + e.getMessage(), e);
	    }
	}
	return this.port;
    }

    public OggettiInsertResponse insert(OggettiInsertRequest oggettiInsertRequest) {

	OggettiInsertResponse response = getPort(oggettiInsertRequest.getToken()).oggettiInsert(oggettiInsertRequest);
	return response;
    }

    public OggettiFindResponse find(BigInteger codiceOggetto, String alias) {

	String token = securityWebServiceClient.loginAPP(alias);
	OggettiFindRequest req = new OggettiFindRequest();
	req.setToken(token);
	req.setId(codiceOggetto);
	OggettiFindResponse response = getPort(token).oggettiFind(req);
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
     * ad oggi è /services/oggetti?wsdl che insieme a SECURITY_PARAM_WSHOSTURL_JAVA formanol'url al servizio degli
     * oggetti
     * 
     * @param urlServicesOggettiWSDL
     */
    public void setUrlServicesOggettiWSDL(String urlServicesOggettiWSDL) {

	this.urlServicesOggettiWSDL = urlServicesOggettiWSDL;
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

    public OggettiFindResponse findbyuid(String uid, String token, String software) {

	log.debug("findbyuid: url={}, uid={}, token={}, software={}, creo il client", new Object[] { oggettiWsUrl, uid, token, software });
	OggettiFindByUidRequest req = new OggettiFindByUidRequest();
	req.setToken(token);
	req.setSoftware(software);
	req.setUid(uid);
	OggettiFindResponse response = getPort(token).oggettiFindByUid(req);
	log.debug("findbyuid: url={}, uid={}, token={}, software={}, chiamata effettuata", new Object[] { oggettiWsUrl, uid, token, software });
	return response;
    }
}
