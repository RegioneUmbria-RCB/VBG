package it.gruppoinit.pdd.ri.features.registroimprese.protocollosuap;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.soap.MTOMFeature;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import it.gruppoinit.impresainungiorno.schema.suap.ri.messages.InterazioniService;
import it.gruppoinit.impresainungiorno.schema.suap.ri.messages.ProtocolloSUAP;
import it.gruppoinit.pdd.ri.decorators.DecoratorFactory;
import it.gruppoinit.pdd.ri.decorators.RIDecorator;
import it.gruppoinit.pdd.ri.features.configurazione.ConfigurazioneService;
import it.gruppoinit.pdd.ri.features.registroimprese.auth.AuthenticatorRequest;
import it.gruppoinit.pdd.ri.features.registroimprese.auth.AuthenticatorResolver;
import it.gruppoinit.pdd.ri.ws.InterazioniRIClient;
import it.gruppoinit.pdd.utils.ConfigurazioneProtocolloSUAP;

public class ProtocolloSUAPPortFactory {

    private String wsdlPosition;
    private QName serviceName;
    private Logger logger;
    private ConfigurazioneService configurazioneService;
    private Map<String, ProtocolloSUAP> protocolloSUAPPortMap = new HashMap<>();

    public ProtocolloSUAPPortFactory(Logger logger, ConfigurazioneService configurazioneService, String wsdlPosition, QName serviceName) {

	this.logger = logger;
	this.wsdlPosition = wsdlPosition;
	this.serviceName = serviceName;
	this.configurazioneService = configurazioneService;
    }

    public ProtocolloSUAP getPort(String idcomunealias, String codiceComune) {

	ProtocolloSUAP port = protocolloSUAPPortMap.get(idcomunealias);
	if (port == null) {
	    ConfigurazioneProtocolloSUAP conf = new ConfigurazioneProtocolloSUAP(idcomunealias);
	    URL wsdlURL = getWsdlURL();
	    this.logger.debug("Accedo alla porta di comunicazione all'url {}", wsdlURL);
	    InterazioniService ss = new InterazioniService(wsdlURL, this.serviceName);
	    WebServiceFeature mtom = new MTOMFeature(conf.isMtomDettaglioEnabled(), 0);
	    port = ss.getProtocolloSUAPService(mtom);
	    BindingProvider bp = (BindingProvider) port;
	    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, conf.getUrlWSPddDettaglio());
	    if (conf.isDettaglioWsAuthUseAuthentication()) {
		//factory per caricare i certificati da FS
		new AuthenticatorResolver(this.logger, this.configurazioneService,
			AuthenticatorRequest.fromConfigurazioneProtocolloSUAP(idcomunealias, codiceComune, "BASIC_AUTHENTICATION", port, conf))
				.authenticate();
	    }
	    processDecorator(port, "richiesta-dettaglio-impresa-RI", conf, idcomunealias);
	    protocolloSUAPPortMap.put(idcomunealias, port);
	}
	return port;
    }

    public void clear() {

	this.protocolloSUAPPortMap = new HashMap<>();
    }

    private URL getWsdlURL() {

	try {
	    return InterazioniRIClient.class.getClassLoader().getResource(wsdlPosition);
	} catch (Exception e) {
	    throw new RuntimeException("Non è stato configurato correttamente il wsdl all'url " + wsdlPosition + " a causa di: " + e.getMessage(), e);
	}
    }

    private void processDecorator(Object port, String nomeMetodo, ConfigurazioneProtocolloSUAP conf, String idcomunealias) {

	if (StringUtils.isBlank(conf.getDecorator())) {
	    return;
	}
	RIDecorator decoratorObj = DecoratorFactory.getDecorator(conf.getDecorator(), idcomunealias);
	if (decoratorObj != null) {
	    decoratorObj.decore(port, nomeMetodo);
	}
    }
}
