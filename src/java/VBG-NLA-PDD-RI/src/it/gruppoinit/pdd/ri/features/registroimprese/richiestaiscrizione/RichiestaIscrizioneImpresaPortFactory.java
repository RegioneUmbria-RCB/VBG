package it.gruppoinit.pdd.ri.features.registroimprese.richiestaiscrizione;

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
import it.gruppoinit.impresainungiorno.schema.suap.ri.messages.RichiestaIscrizioneImpresaRiSPC;
import it.gruppoinit.pdd.ri.decorators.DecoratorFactory;
import it.gruppoinit.pdd.ri.decorators.RIDecorator;
import it.gruppoinit.pdd.ri.features.configurazione.ConfigurazioneService;
import it.gruppoinit.pdd.ri.features.registroimprese.auth.AuthenticatorRequest;
import it.gruppoinit.pdd.ri.features.registroimprese.auth.AuthenticatorResolver;
import it.gruppoinit.pdd.ri.ws.InterazioniRIClient;
import it.gruppoinit.pdd.utils.ConfigurazioneRichiestaIscrizioneImpresaRiSPC;

public class RichiestaIscrizioneImpresaPortFactory {

    private String wsdlPosition;
    private QName serviceName;
    private Logger logger;
    private ConfigurazioneService configurazioneService;
    private Map<String, RichiestaIscrizioneImpresaRiSPC> impresaRiSPCPortMap = new HashMap<>();

    public RichiestaIscrizioneImpresaPortFactory(Logger logger, ConfigurazioneService configurazioneService, String wsdlPosition, QName serviceName) {

	this.logger = logger;
	this.serviceName = serviceName;
	this.wsdlPosition = wsdlPosition;
	this.configurazioneService = configurazioneService;
    }

    public RichiestaIscrizioneImpresaRiSPC getPort(String idComuneAlias) {

	RichiestaIscrizioneImpresaRiSPC port = impresaRiSPCPortMap.get(idComuneAlias);
	if (port == null) {
	    ConfigurazioneRichiestaIscrizioneImpresaRiSPC conf = new ConfigurazioneRichiestaIscrizioneImpresaRiSPC(idComuneAlias);
	    URL wsdlURL = getWsdlURL();
	    this.logger.debug("Accedo alla porta di comunicazione all'url {}", wsdlURL);
	    InterazioniService ss = new InterazioniService(wsdlURL, this.serviceName);
	    WebServiceFeature mtom = new MTOMFeature(conf.isMtomIscrizioneEnabled(), 0);
	    port = ss.getIscrizioneImpreseService(mtom);
	    BindingProvider bp = (BindingProvider) port;
	    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, conf.getUrlWSPddIscrizione());
	    if (conf.isIscrizioneWsAuthUseAuthentication()) {
		//factory per caricare i certificati da FS
		new AuthenticatorResolver(this.logger, this.configurazioneService,
			AuthenticatorRequest.fromConfigurazioneRichiestaIscrizione(idComuneAlias, "BASIC_AUTHENTICATION", port, conf)).authenticate();
	    }
	    processDecorator(port, "richiesta-iscrizione-impresa-RI", conf, idComuneAlias);
	    impresaRiSPCPortMap.put(idComuneAlias, port);
	}
	return port;
    }

    public void clear() {

	this.impresaRiSPCPortMap = new HashMap<>();
    }

    private URL getWsdlURL() {

	try {
	    return InterazioniRIClient.class.getClassLoader().getResource(wsdlPosition);
	} catch (Exception e) {
	    throw new RuntimeException("Non è stato configurato correttamente il wsdl all'url " + wsdlPosition + " a causa di: " + e.getMessage(), e);
	}
    }

    private void processDecorator(Object port, String nomeMetodo, ConfigurazioneRichiestaIscrizioneImpresaRiSPC conf, String idcomunealias) {

	if (StringUtils.isBlank(conf.getDecorator())) {
	    return;
	}
	RIDecorator decoratorObj = DecoratorFactory.getDecorator(conf.getDecorator(), idcomunealias);
	if (decoratorObj != null) {
	    decoratorObj.decore(port, nomeMetodo);
	}
    }
}
