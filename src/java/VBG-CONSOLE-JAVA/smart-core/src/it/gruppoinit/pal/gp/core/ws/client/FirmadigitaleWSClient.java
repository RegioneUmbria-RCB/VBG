package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.firmadigitaleweb.services.FirmaDigitale;
import it.gruppoinit.firmadigitaleweb.services.FirmaDigitaleService;
import it.gruppoinit.firmadigitaleweb.services.messages.FileContent;
import it.gruppoinit.firmadigitaleweb.services.messages.InfoCertificatoBean;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.net.URL;

import javax.xml.ws.soap.MTOMFeature;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class FirmadigitaleWSClient {

    private static final Logger log = LoggerFactory.getLogger(FirmadigitaleWSClient.class);

    public static InfoCertificatoBean getInfoFile(byte[] signedFileContent) {

	InfoCertificatoBean result = null;
	try {
	    FirmaDigitale fd = getWsFirmaDigitalePort();
	    FileContent f = new FileContent();
	    f.setDataFile(Utilities.bytesToDataHandler(signedFileContent));
	    result = fd.getInfoFile(f);
	} catch (Exception e) {
	    log.error("getInfoFile: {}", e.getMessage());
	    throw new RuntimeException(e.getMessage(), e.getCause());
	}
	return result;
    }

    private static FirmaDigitale getWsFirmaDigitalePort() throws Exception {

	FirmaDigitaleService client = new FirmaDigitaleService(new URL(
		WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_FIRMADIGITALE)));
	MTOMFeature mtomFeature = new MTOMFeature(true, 0);
	FirmaDigitale port = client.getFirmaDigitaleWSA(mtomFeature);
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
