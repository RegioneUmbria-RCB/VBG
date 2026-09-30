package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.dss.wsclient.ValidationService;
import it.gruppoinit.dss.wsclient.ValidationService_Service;
import it.gruppoinit.dss.wsclient.WsDocument;
import it.gruppoinit.dss.wsclient.WsValidationReport;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;

import java.net.URL;

import javax.activation.DataHandler;
import javax.xml.ws.soap.MTOMFeature;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DSSWSClient {

    private static final Logger log = LoggerFactory.getLogger(DSSWSClient.class);

    public static WsValidationReport validateDocument(DataHandler signedFileContent, String fileName, boolean returnClearFile) {

	if (log.isDebugEnabled()) {
	    log.debug("validateDocument#Inizio invocazione servizio del WS di verifica firma DSS...");
	}
	WsValidationReport result = null;
	try {
	    ValidationService validation = getWsFirmaDigitalePort();
	    WsDocument document = new WsDocument();
	    document.setBinary(signedFileContent);
	    document.setName(fileName);
	    result = validation.validateDocument(document, null, returnClearFile);
	} catch (Exception e) {
	    log.error("validateDocument: {}", e.getMessage());
	    throw new RuntimeException(e.getMessage(), e.getCause());
	}
	if (log.isDebugEnabled()) {
	    log.debug("validateDocument#Fine invocazione servizio del WS di verifica firma DSS...");
	}
	return result;
    }

    private static ValidationService getWsFirmaDigitalePort() throws Exception {

	String indirizzo = WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_FIRMADIGITALE);
	if (log.isDebugEnabled()) {
	    log.debug("validateDocument#Recupero la porta del servizio all'indirizzo {}", indirizzo);
	}
	ValidationService_Service client = new ValidationService_Service(new URL(indirizzo));
	MTOMFeature mtomFeature = new MTOMFeature(true, 0);
	ValidationService port = client.getValidationServiceImplPort(mtomFeature);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	// HTTPClientPolicy - Properties used to configure a client-side HTTP port  
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(120000); // Line #2  
	httpClientPolicy.setReceiveTimeout(600000); // Line #3  
	conduit.setClient(httpClientPolicy);
	return port;
    }
}
