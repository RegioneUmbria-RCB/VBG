package it.gruppoinit.wsanagrafe2.ws;

import java.net.URL;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class WsAnagrafe2CxfClient {

    public WsAnagrafe2CxfClient() {

	super();
    }

    private String wsdlPosition = "wsdl/wsanagrafe2/WsAnagrafe2.wsdl";
    private long timeout = 30000;

    public long getTimeout() {

	return timeout;
    }

    public void setTimeout(long timeout) {

	this.timeout = timeout;
    }

    public WsAnagrafe2Soap getAnagrafe2WsPort(String anagrafeWsUrl) throws Exception {

	URL wsdlURL = getWsdlURL();
	WsAnagrafe2 client = new WsAnagrafe2(wsdlURL);
	WsAnagrafe2Soap port = client.getWsAnagrafe2();
	Client proxy = ClientProxy.getClient(port);
	BindingProvider bp = (BindingProvider) port;
	bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, anagrafeWsUrl);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	// HTTPClientPolicy - Properties used to configure a client-side HTTP port  
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(this.timeout); // Line #2  
	httpClientPolicy.setReceiveTimeout(this.timeout); // Line #3  
	conduit.setClient(httpClientPolicy);
	return port;
    }

    private URL getWsdlURL() {

	try {
	    URL wsdlURL = WsAnagrafe2CxfClient.class.getClassLoader().getResource(wsdlPosition);
	    return wsdlURL;
	} catch (Exception e) {
	    throw new RuntimeException("Non è stato configurato correttamente il wsdl all'url " + wsdlPosition + " a causa di: " + e.getMessage(), e);
	}
    }
}
