package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.ws.client;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.ws.model.EsitoElaborazioneMassivaSchede;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.ws.model.IElaborazioneMassivaSchedeIstanza;

public class ElaborazioneMassivaWsClient {

    private String urlWs;
    private long connectionTimeOut = 12000;
    private long readTimeOut = 600000;

    /**
     * <pre>
     * Default Timeout <b>{@link #connectionTimeOut} 12000</b> 
     * Default ReadTimeOut <b>{@link #readTimeOut} 600000</b>
     * Default urlWS <b>{@link BackofficeNETConstants#getUrlWsElaborazioneMassiva()}</b>
     * </pre>
     */
    public ElaborazioneMassivaWsClient() {

	super();
	this.urlWs = BackofficeNETConstants.getUrlWsElaborazioneMassiva();
    }

    /**
     * @see ElaborazioneMassivaWsClient#ElaborazioneMassivaWsClient()
     * @param connectionTimeOut
     * @param readTimeOut
     */
    public ElaborazioneMassivaWsClient(long connectionTimeOut, long readTimeOut) {

	this();
	this.connectionTimeOut = connectionTimeOut;
	this.readTimeOut = readTimeOut;
    }

    public ElaborazioneMassivaWsClient(String urlWs, long connectionTimeOut, long readTimeOut) {

	this.urlWs = urlWs;
	this.connectionTimeOut = connectionTimeOut;
	this.readTimeOut = readTimeOut;
    }

    /**
     * Invoca il metodo elabora
     * 
     * @param token
     *            token valido per l'utente che invoca l'operazione
     * @param idElaborazione
     *            identificativo dell'elaborazione da processare (>0)
     * @return {@link EsitoElaborazioneMassivaSchede}
     * @throws IllegalArgumentException
     *             nel caso che token sia nullo o elaborazione <1
     */
    public EsitoElaborazioneMassivaSchede elabora(String token, int idElaborazione) {

	if (StringUtils.isBlank(token) || idElaborazione < 1) {
	    throw new IllegalArgumentException(
		    "Non è impossibile invocare il metodo con i parametri token: " + token + ", idElaborazione: " + idElaborazione);
	}
	return getNlaWsPort().elabora(token, idElaborazione);
    }

    private IElaborazioneMassivaSchedeIstanza getNlaWsPort() {

	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(IElaborazioneMassivaSchedeIstanza.class);
	factory.setAddress(urlWs);
	IElaborazioneMassivaSchedeIstanza port = (IElaborazioneMassivaSchedeIstanza) factory.create();
	Client client = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) client.getConduit();
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	httpClientPolicy.setConnectionTimeout(connectionTimeOut);
	httpClientPolicy.setReceiveTimeout(readTimeOut);
	conduit.setClient(httpClientPolicy);
	return port;
    }
}
