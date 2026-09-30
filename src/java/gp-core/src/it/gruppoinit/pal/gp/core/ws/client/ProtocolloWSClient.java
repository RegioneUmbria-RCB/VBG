package it.gruppoinit.pal.gp.core.ws.client;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.protocollo.schemas.messages.IProtocollazioneService;

public class ProtocolloWSClient extends BaseWsClient implements IWsClient<IProtocollazioneService> {

    private static final Logger log = LoggerFactory.getLogger(ProtocolloWSClient.class);
    private String wsUrl;
    private long connectionTimeout = 20000;
    private long receiveTimeOut = 600000;

    public ProtocolloWSClient(VerticalizzazioniService service) {

	this.wsUrl = this.getUrlWsProtocollazione(service);
	Verticalizzazioniparametri p = service.getVerticalizzazioniparametri(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIMEOUT_CHIAMATA_WS_INTERNA);
	if (p != null) {
	    String timeout = StringUtils.defaultString(p.getValore());
	    if (Utilities.isInteger(timeout)) {
		this.receiveTimeOut = Long.parseLong(timeout);
	    }
	}
    }

    @Override
    public IProtocollazioneService getWsPort() throws Exception {

	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(IProtocollazioneService.class);
	factory.setAddress(this.wsUrl);
	IProtocollazioneService port = (IProtocollazioneService) factory.create();
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	BindingProvider bp = (BindingProvider) port;
	bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsUrl);
	SOAPBinding binding = (SOAPBinding) bp.getBinding();
	binding.setMTOMEnabled(true);
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(this.connectionTimeout); // Line #2  
	httpClientPolicy.setReceiveTimeout(this.receiveTimeOut); // Line #3  
	conduit.setClient(httpClientPolicy);
	return port;
    }

    /**
     * URI del web service per la protocollazione.<br />
     * L'indirizzo è composto da {@link BackofficeNETConstants#getWsHostUrl()} +
     * /webservices/wssigepro/protocollazione.asmx
     */
    private String getUrlWsProtocollazione(VerticalizzazioniService service) {

	String urlWsProtocollo = "";
	Verticalizzazioniparametri vp = service.getVerticalizzazioniparametri(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_URL_WS_PROTOCOLLO);
	if (vp != null) {
	    urlWsProtocollo = StringUtils.defaultIfEmpty(vp.getValore(), "").trim();
	}
	log.debug("getProtocolloWsPort# urlWsProtocollo da verticalizzazione {}", urlWsProtocollo);
	if (StringUtils.isBlank(urlWsProtocollo)) {
	    urlWsProtocollo = WebConstants.getSecurityParamValue(SecurityParams.WS_URL_PROTOCOLLO);
	}
	log.debug("getProtocolloWsPort# urlWsProtocollo {}", urlWsProtocollo);
	if (StringUtils.isBlank(urlWsProtocollo)) {
	    throw new InvalidConfigurationException("Attenzione! non è stato configurato nella security il parametro WS_URL_PROTOCOLLO");
	}
	return urlWsProtocollo;
    }
}
