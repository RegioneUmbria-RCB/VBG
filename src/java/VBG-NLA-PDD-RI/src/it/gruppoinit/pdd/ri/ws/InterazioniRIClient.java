package it.gruppoinit.pdd.ri.ws;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.impresainungiorno.schema.suap.ri.messages.ProtocolloSUAP;
import it.gruppoinit.impresainungiorno.schema.suap.ri.messages.RichiestaIscrizioneImpresaRiSPC;
import it.gruppoinit.nla.pdd.interfaces.Reloadable;
import it.gruppoinit.pdd.ri.features.configurazione.ConfigurazioneService;
import it.gruppoinit.pdd.ri.features.registroimprese.protocollosuap.ProtocolloSUAPPortFactory;
import it.gruppoinit.pdd.ri.features.registroimprese.richiestaiscrizione.RichiestaIscrizioneImpresaPortFactory;

/**
 * @author riccardob
 * 
 */
@Component
public class InterazioniRIClient {

    private static final QName SERVICE_NAME = new QName("http://www.impresainungiorno.gov.it/schema/suap/ri", "interazioniService");
    private static final String WSDL_POSITION = "wsdl/registroimprese/comunicazioniRea_logico.wsdl";
    private static Logger log = LoggerFactory.getLogger(InterazioniRIClient.class);
    private RichiestaIscrizioneImpresaPortFactory richiestaIscrizionePortFactory;
    private ProtocolloSUAPPortFactory protocolloSUAPPortFactory;

    @Autowired
    public InterazioniRIClient(ConfigurazioneService configurazioneService) {

	this.richiestaIscrizionePortFactory = new RichiestaIscrizioneImpresaPortFactory(log, configurazioneService, WSDL_POSITION, SERVICE_NAME);
	this.protocolloSUAPPortFactory = new ProtocolloSUAPPortFactory(log, configurazioneService, WSDL_POSITION, SERVICE_NAME);
    }

    public ProtocolloSUAP getProtocolloSUAPPort(String idcomunealias, String idcomune, String codiceComune) {

	return this.protocolloSUAPPortFactory.getPort(idcomunealias, codiceComune);
    }

    public RichiestaIscrizioneImpresaRiSPC getRichiestaIscrizionePort(String idcomunealias, String idComune) {

	return this.richiestaIscrizionePortFactory.getPort(idcomunealias);
    }

    @Reloadable
    public void reloadPort() {

	this.richiestaIscrizionePortFactory.clear();
	this.protocolloSUAPPortFactory.clear();
    }
}
