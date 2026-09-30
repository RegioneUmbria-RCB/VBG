package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.protocollo.schemas.messages.IProtocollazioneService;
import it.gruppoinit.protocollo.schemas.messages.ProtocollazioneService;

import java.net.URL;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.MTOMFeature;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtocolloWSClient extends BaseWsClient {

    private static final Logger log = LoggerFactory.getLogger(ProtocolloWSClient.class);

    public static IProtocollazioneService getProtocolloWsPort() throws Exception {

	String webServiceUrl = BackofficeNETConstants.getURL_WS_PROTOCOLLAZIONE();
	log.debug("getProtocolloWsPort# cerco di instanziare il client all'url {}", webServiceUrl);
	ProtocollazioneService ss = new ProtocollazioneService(new URL(webServiceUrl));
	MTOMFeature mtomFeature = new MTOMFeature(true, 0);
	IProtocollazioneService port = ss.getBasicHttpBindingIProtocollazioneService(mtomFeature);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	// HTTPClientPolicy - Properties used to configure a client-side HTTP port  
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1
	httpClientPolicy.setConnectionTimeout(20000);
	httpClientPolicy.setReceiveTimeout(600000);
	conduit.setClient(httpClientPolicy);
	((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, webServiceUrl);
	return port;
    }
}
