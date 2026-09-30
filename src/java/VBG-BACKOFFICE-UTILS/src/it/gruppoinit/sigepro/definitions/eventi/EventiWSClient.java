package it.gruppoinit.sigepro.definitions.eventi;

import it.gruppoinit.sigepro.schemas.messages.base.CategorieEventiBaseType;
import it.gruppoinit.sigepro.schemas.messages.base.EsitoOperazioneType;
import it.gruppoinit.sigepro.schemas.messages.eventi.EventoBackofficeInsertRequest;
import it.gruppoinit.sigepro.schemas.messages.eventi.EventoIstanzaInsertRequest;
import it.gruppoinit.sigepro.schemas.messages.eventi.EventoMovimentoInsertRequest;

import java.math.BigInteger;
import java.net.URL;

import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.soap.MTOMFeature;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventiWSClient {

    private static Logger log = LoggerFactory.getLogger(EventiWSClient.class);
    private String eventiWsUrl;
    private long timeout = 120000; // defaul 120.000 ms due minuti
    private boolean mtomEnabled = true;
    private Eventi port;

    public EsitoOperazioneType insertEventoIstanza(String token, Integer codiceistanza, String evento, String alias) {

	return insertEvento(token, codiceistanza, null, evento, alias, null);
    }

    public EsitoOperazioneType insertEventoMovimento(String token, Integer codicemovimento, String evento, String alias) {

	return insertEvento(token, null, codicemovimento, evento, alias, null);
    }

    public EsitoOperazioneType insertEventoBackoffice(String token, String evento, String alias, String software) {

	return insertEvento(token, null, null, evento, alias, software);
    }

    private EsitoOperazioneType insertEvento(String token, Integer codiceistanza, Integer codicemovimento, String evento, String alias,
	    String software) {

	EsitoOperazioneType result = null;
	if (codicemovimento != null) {
	    EventoMovimentoInsertRequest em = new EventoMovimentoInsertRequest();
	    em.setToken(token);
	    em.setMessaggio(evento);
	    em.setCodicemovimento(BigInteger.valueOf(codicemovimento));
	    em.setCategoriaEvento(CategorieEventiBaseType.AVVERTIMENTI);
	    result = getPort().eventoMovimentoInsert(em);
	} else if (codiceistanza != null) {
	    EventoIstanzaInsertRequest em = new EventoIstanzaInsertRequest();
	    em.setToken(token);
	    em.setMessaggio(evento);
	    em.setCodiceistanza(BigInteger.valueOf(codiceistanza));
	    em.setCategoriaEvento(CategorieEventiBaseType.AVVERTIMENTI);
	    result = getPort().eventoIstanzaInsert(em);
	} else {
	    EventoBackofficeInsertRequest ebi = new EventoBackofficeInsertRequest();
	    ebi.setToken(token);
	    ebi.setCategoriaEvento(CategorieEventiBaseType.AVVERTIMENTI);
	    ebi.setMessaggio(evento);
	    ebi.setSoftware(software);
	    result = getPort().eventoBackofficeInsert(ebi);
	}
	return result;
    }

    private Eventi getPort() {

	log.debug("getOggettiWsPort: url={}", eventiWsUrl);
	if (port == null) {
	    EventiWsService oggettiService = null;
	    try {
		oggettiService = new EventiWsService(new URL(this.eventiWsUrl));
		WebServiceFeature mtom = new MTOMFeature(mtomEnabled, 0);
		port = oggettiService.getEventiSoap11(mtom);
		Client proxy = ClientProxy.getClient(port);
		HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(timeout);
		httpClientPolicy.setReceiveTimeout(timeout);
		conduit.setClient(httpClientPolicy);
	    } catch (Exception e) {
		log.error("getOggettiWsPort(): {}", e.getMessage());
		throw new RuntimeException("Errore durante l'inizializzazione della chiamata al ws getOggettiWsPort: " + e.getMessage(), e);
	    }
	}
	return port;
    }

    public void setMtomEnabled(boolean mtomEnabled) {

	this.mtomEnabled = mtomEnabled;
    }

    public void setTimeout(long timeout) {

	this.timeout = timeout;
    }

    public void setEventiWsUrl(String eventiWsUrl) {

	this.eventiWsUrl = eventiWsUrl;
    }
}
