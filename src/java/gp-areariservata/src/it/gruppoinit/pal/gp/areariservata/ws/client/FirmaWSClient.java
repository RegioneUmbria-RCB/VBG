package it.gruppoinit.pal.gp.areariservata.ws.client;

import it.gruppoinit.pal.firma.ws.Firma;
import it.gruppoinit.pal.firma.ws.FirmaService;
import it.gruppoinit.pal.firma.ws.schema.GetSignedFileRequest;
import it.gruppoinit.pal.firma.ws.schema.GetSignedFileResponse;
import it.gruppoinit.pal.firma.ws.schema.SetFileToSignRequest;
import it.gruppoinit.pal.firma.ws.schema.SetFileToSignResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;

import java.net.URL;

import javax.xml.ws.soap.MTOMFeature;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirmaWSClient {

    private static final Logger log = LoggerFactory.getLogger(FirmaWSClient.class);
    private int timeout = 30000;

    public SetFileToSignResponse setFileToSign(SetFileToSignRequest req) throws Exception {

	Firma port = getWsPort();
	SetFileToSignResponse resp = port.setFileToSign(req);
	return resp;
    }

    public GetSignedFileResponse getSignedFile(GetSignedFileRequest req) throws Exception {

	Firma port = getWsPort();
	GetSignedFileResponse resp = port.getSignedFile(req);
	return resp;
    }

    private Firma getWsPort() throws Exception {

	String url = WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_FIRMA);
	log.debug("getWsPort: url={}, timeout={}", url, timeout);
	FirmaService client = new FirmaService(new URL(url));
	MTOMFeature mtomFeature = new MTOMFeature(true, 0);
	Firma port = client.getFirmaSoap11(mtomFeature);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	httpClientPolicy.setConnectionTimeout(this.timeout);
	httpClientPolicy.setReceiveTimeout(this.timeout);
	conduit.setClient(httpClientPolicy);
	return port;
    }

    public void setTimeout(int timeout) {

	this.timeout = timeout;
    }
}
