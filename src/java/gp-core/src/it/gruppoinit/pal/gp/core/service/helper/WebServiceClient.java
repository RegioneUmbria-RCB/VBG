package it.gruppoinit.pal.gp.core.service.helper;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.WsSitLocator;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.WsSitSoap;

public class WebServiceClient {

    private static final Logger log = LoggerFactory.getLogger(WebServiceClient.class);

    public static WsSitSoap getWsSITPort() {

	WsSitLocator locator = new WsSitLocator();
	String urlWSSIT = WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_SIT);
	log.debug("urlWSSIT: {}", urlWSSIT);
	try {
	    return locator.getWsSitSoap(new URL(urlWSSIT));
	} catch (Exception e) {
	    log.error("getWsSITPort: Errore nell'inizializzazione [{}] {}", urlWSSIT, e.getMessage());
	    throw new RuntimeException("getWsSITPort: Errore nell'inizializzazione [" + urlWSSIT + "]: " + e.getMessage(), e);
	}
    }

    public static WsSitSoap getWsSITPort(String url) {

	WsSitLocator locator = new WsSitLocator();
	log.debug("urlWSSIT: {}", url);
	try {
	    return locator.getWsSitSoap(new URL(url));
	} catch (Exception e) {
	    log.error("getWsSITPort: Errore nell'inizializzazione [{}] {}", url, e.getMessage());
	    throw new RuntimeException("getWsSITPort: Errore nell'inizializzazione [" + url + "]: " + e.getMessage(), e);
	}
    }
}
