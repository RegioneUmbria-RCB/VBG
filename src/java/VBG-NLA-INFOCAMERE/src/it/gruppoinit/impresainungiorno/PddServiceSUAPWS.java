package it.gruppoinit.impresainungiorno;

import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP;
import it.gov.impresainungiorno.suap.scrivania.PddServiceSUAP;
import it.gov.impresainungiorno.suap.scrivania.PddServiceSUAP_Service;
import it.gruppoinit.domain.helper.VerticalizzazioniHelper;
import it.gruppoinit.utilities.TrustAllX509TrustManager;

import java.net.URL;

import javax.net.ssl.TrustManager;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.soap.MTOMFeature;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PddServiceSUAPWS {

    private static Logger log = LoggerFactory.getLogger(PddServiceSUAPWS.class);
    private String wsdlPosition = "wsdl/PddServiceSuap.wsdl";
    //private PddServiceSUAP port;
    private boolean mtomEnabled = true;
    private long timeout = 150000; // defaul 150.000 ms due minuti

    public String inviaEnteSUAP(VerticalizzazioniHelper verticalizzazioniHelper, CooperazioneEnteSUAP cooperazioneEnteSUAP) {

	String result = "";
	try {
	    PddServiceSUAP port = getPort(verticalizzazioniHelper.getURL_WS(), verticalizzazioniHelper.getUSER_WS(),
		    verticalizzazioniHelper.getPSW_WS());
	    result = port.inviaEnteSUAP(cooperazioneEnteSUAP);
	} catch (Exception e) {
	    log.error("inviaEnteSUAP# Errore durante la chiamata al servizio inviaEnteSUAP. E= {}", e);
	}
	return result;
    }

    private PddServiceSUAP getPort(String url, String user, String password) {

	log.debug("getPort: url={}", url);
	//if (this.port == null) {
	PddServiceSUAP_Service pddServiceSUAP_Service = null;
	PddServiceSUAP _port = null;
	try {
	    URL u = getWsdlURL();
	    pddServiceSUAP_Service = new PddServiceSUAP_Service(u);
	    WebServiceFeature mtom = new MTOMFeature(mtomEnabled, 0);
	    //	    this.port = pddServiceSUAP_Service.getPddPortSUAP(mtom);
	    _port = pddServiceSUAP_Service.getPddPortSUAP(mtom);
	    Client proxy = ClientProxy.getClient(_port);
	    HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(timeout);
	    httpClientPolicy.setReceiveTimeout(timeout);
	    conduit.setClient(httpClientPolicy);
	    AuthorizationPolicy authorizationPolicy = new AuthorizationPolicy();
	    authorizationPolicy.setUserName(user);
	    authorizationPolicy.setPassword(password);
	    authorizationPolicy.setAuthorizationType("Basic");
	    conduit.setAuthorization(authorizationPolicy);
	    if (true) {
		//disable ssl cert verification
		TLSClientParameters params = new TLSClientParameters();
		TrustManager[] trustManagers = new TrustManager[] { new TrustAllX509TrustManager() };
		params.setTrustManagers(trustManagers);
		params.setDisableCNCheck(true);
		conduit.setTlsClientParameters(params);
	    }
	    //Endpoint securityEndpoint = proxy.getEndpoint();
	    BindingProvider bp = (BindingProvider) _port;
	    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
	    //	    Map<String, Object> outProps = new HashMap<String, Object>();
	    //	    outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN); // Signature
	    //	    outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT); // WSConstants.PASSWORD_DIGEST ???
	    //	    outProps.put(WSHandlerConstants.USER, "suap");
	    //	    outProps.put(WSHandlerConstants.PW_CALLBACK_REF, new SecurityPwdCallBackHandler("suap", "suap2011"));
	    //	    WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
	    //	    securityEndpoint.getOutInterceptors().add(wssOut);
	} catch (Exception e) {
	    log.error("getPort(): {}", e.getMessage());
	    throw new RuntimeException("Errore durante l'inizializzazione della chiamata al ws pddServiceSUAP_Service: " + e.getMessage(), e);
	}
	//}
	return _port;
    }

    private URL getWsdlURL() {

	try {
	    URL wsdlURL = PddServiceSUAPWS.class.getClassLoader().getResource(wsdlPosition);
	    return wsdlURL;
	} catch (Exception e) {
	    throw new RuntimeException("Non è stato configurato correttamente il wsdl all'url " + wsdlPosition + " a causa di: " + e.getMessage(), e);
	}
    }

    public void setMtomEnabled(boolean mtomEnabled) {

	this.mtomEnabled = mtomEnabled;
    }

    public void setTimeout(long timeout) {

	this.timeout = timeout;
    }
}
