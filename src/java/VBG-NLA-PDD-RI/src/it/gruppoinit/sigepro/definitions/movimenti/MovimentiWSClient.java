package it.gruppoinit.sigepro.definitions.movimenti;

import it.gruppoinit.sigepro.definitions.oggetti.OggettiWSClient;
import it.gruppoinit.sigepro.schemas.messages.movimenti.MovimentiAllegatiInsertRequest;
import it.gruppoinit.sigepro.schemas.messages.movimenti.MovimentiAllegatiInsertResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;

import java.net.URL;
import java.util.Map;

import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.soap.MTOMFeature;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MovimentiWSClient {

    private Logger log = LoggerFactory.getLogger(MovimentiWSClient.class);
    private Movimenti port;
    private String movimentiWsURL;
    private String urlServicesMovimentiWSDL;
    private SigeproSecurityWebServiceClient securityWebServiceClient;
    private boolean mtomEnabled = true;
    private long timeout;

    private Movimenti getPort() {

	if (port == null) {
	    if (this.movimentiWsURL == null) {
		Map<String, String> params = securityWebServiceClient.getParams(OggettiWSClient.SECURITY_PARAM_WSHOSTURL_JAVA);
		movimentiWsURL = params.get(OggettiWSClient.SECURITY_PARAM_WSHOSTURL_JAVA) + urlServicesMovimentiWSDL;
	    }
	    MovimentiService service = null;
	    try {
		service = new MovimentiService(new URL(this.movimentiWsURL));
		WebServiceFeature mtom = new MTOMFeature(mtomEnabled, 0);
		port = service.getMovimentiSoap11(mtom);
		Client proxy = ClientProxy.getClient(port);
		// Endpoint cxfEndpoint = proxy.getEndpoint();
		// Map<String, Object> outProps = new HashMap<String, Object>();
		HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(timeout);
		httpClientPolicy.setReceiveTimeout(timeout);
		conduit.setClient(httpClientPolicy);
	    } catch (Exception e) {
		log.error("getOggettiWsPort(): {}", e.getMessage());
		throw new RuntimeException("Errore durante l'inizializzazione della chiamata al ws getOggettiWsPort: " + e.getMessage(), e);
	    }
	}
	return this.port;
    }

    public MovimentiAllegatiInsertResponse insertMovimentiAllegati(MovimentiAllegatiInsertRequest request) {

	return getPort().movimentiAllegatiInsert(request);
    }

    public void setSecurityWebServiceClient(SigeproSecurityWebServiceClient securityWebServiceClient) {

	this.securityWebServiceClient = securityWebServiceClient;
    }

    public void setUrlServicesMovimentiWSDL(String urlServicesMovimentiWSDL) {

	this.urlServicesMovimentiWSDL = urlServicesMovimentiWSDL;
    }

    public void setMtomEnabled(boolean mtomEnabled) {

	this.mtomEnabled = mtomEnabled;
    }

    public void setTimeout(long timeout) {

	this.timeout = timeout;
    }
}
