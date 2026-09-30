package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.sigepro.backoffice.ws.sigeprorenderer.stub.Sigeprorenderer_PortType;
import it.gruppoinit.sigepro.backoffice.ws.sigeprorenderer.stub.Sigeprorenderer_ServiceLocator;

import java.net.URL;
import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SigeprorendererWSClient {

    private static final Logger log = LoggerFactory.getLogger(SigeprorendererWSClient.class);

    public static byte[] generaGraficoDaDBConToken(String token, int codiceprocedura) {

	Sigeprorenderer_PortType port = getRendererWsPort();
	try {
	    return port.generaGraficoDaDBConToken(token, codiceprocedura, "");
	} catch (RemoteException e) {
	    throw new RuntimeException(e.getMessage(), e);
	}
    }

    private static Sigeprorenderer_PortType getRendererWsPort() {

	Sigeprorenderer_ServiceLocator locator = new Sigeprorenderer_ServiceLocator();
	String urlWS = WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_RENDER);
	Sigeprorenderer_PortType port = null;
	try {
	    port = locator.getsigeprorendererSOAP(new URL(urlWS));
	} catch (Exception e) {
	    log.error("getRendererWsPort: Errore nell'inizializzazione [{}] {}", urlWS, e.getMessage());
	    throw new RuntimeException("getRendererWsPort: Errore nell'inizializzazione [" + urlWS + "]: " + e.getMessage(), e);
	}
	return port;
    }
}
