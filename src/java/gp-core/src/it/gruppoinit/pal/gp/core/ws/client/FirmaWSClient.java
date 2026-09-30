package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.pal.firma.ws.Firma;
import it.gruppoinit.pal.firma.ws.FirmaService;

import java.net.URL;

import javax.xml.ws.soap.MTOMFeature;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class FirmaWSClient {

    private long connectionTimeout = 120000;
    private long receiveTimeout = 600000;

    public Firma getFirmaWSPort(String firmaWsUrl) throws Exception {

	FirmaService client = new FirmaService(new URL(firmaWsUrl));
	MTOMFeature mtomFeature = new MTOMFeature(true, 0);
	Firma port = client.getFirmaSoap11(mtomFeature);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	// HTTPClientPolicy - Properties used to configure a client-side HTTP port  
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(connectionTimeout); // Line #2  
	httpClientPolicy.setReceiveTimeout(receiveTimeout); // Line #3  
	conduit.setClient(httpClientPolicy);
	return port;
    }

    public long getConnectionTimeout() {

	return connectionTimeout;
    }

    public void setConnectionTimeout(long connectionTimeout) {

	this.connectionTimeout = connectionTimeout;
    }

    public long getReceiveTimeout() {

	return receiveTimeout;
    }

    public void setReceiveTimeout(long receiveTimeout) {

	this.receiveTimeout = receiveTimeout;
    }
}
