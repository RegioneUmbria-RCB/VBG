package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.dossier.messages.RiversaIstanzeDossier;
import it.gruppoinit.dossier.messages.RiversaIstanzeDossierService;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;

import java.net.URL;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DossierWsClient extends BaseWsClient {

    private static final Logger log = LoggerFactory.getLogger(DossierWsClient.class);
    private String wsdlPosition = "it/gruppoinit/dossier/schema/riversaIstanzeDossier.wsdl";
    private long timeout = 30000;

    public RiversaIstanzeDossier getWsPort(String dossierWsUrl) throws InvalidConfigurationException, Exception {

	log.debug("Carico il wsdl : {}", wsdlPosition);
	URL urlWsdlPosition = getWsdlURL();
	RiversaIstanzeDossierService client = new RiversaIstanzeDossierService(urlWsdlPosition);
	RiversaIstanzeDossier port = client.getRiversaIstanzeDossierSoap11();
	Client proxy = ClientProxy.getClient(port);
	BindingProvider bp = (BindingProvider) port;
	log.debug("Carico l'url esposto del ws : {}", dossierWsUrl);
	bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, dossierWsUrl);
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
	    URL wsdlURL = DossierWsClient.class.getClassLoader().getResource("it/gruppoinit/dossier/schema/riversaIstanzeDossier.wsdl");
	    return wsdlURL;
	} catch (Exception e) {
	    log.error("Non è stato configurato correttamente il wsdl all'url: {} a causa di: {}[{}] ",
		    new Object[] { wsdlPosition, e.getMessage(), e });
	    throw new RuntimeException("Non è stato configurato correttamente il wsdl all'url " + wsdlPosition + " a causa di: " + e.getMessage(), e);
	}
    }
}
