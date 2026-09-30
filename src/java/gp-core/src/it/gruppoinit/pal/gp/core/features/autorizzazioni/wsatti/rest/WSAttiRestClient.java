package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest;

import java.io.InputStream;
import java.util.TreeSet;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.BaseWsClient;

public class WSAttiRestClient extends BaseWsClient {

    private static final Logger log = LoggerFactory.getLogger(WSAttiRestClient.class);
    private long connectionTimeOut = 12000;
    private long receivetimeout = 600000;
    private String url;

    public WSAttiRestClient(String url) {

	if (StringUtils.isBlank(url)) {
	    throw new InvalidConfigurationException(
		    "Url REST non confugurata; verificare la presenza del parametro WSHOSTURL_ASPNET nella security.");
	}
	this.url = url;
    }

    public LeggiDeterminaResponse leggiDetermina(LeggiDeterminaRequest request) throws FunzioneBusinessRemotaException {

	WebClient c = getRestWebClient(this.connectionTimeOut, this.receivetimeout);
	c.type(MediaType.APPLICATION_JSON);
	c.accept(MediaType.APPLICATION_JSON);
	try {
	    String richiesta = Utilities.marshalJsonObject(request, request.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    log.debug("Richiesta WSAttiRestClient.leggiDetermina {}", richiesta);
	    Response response = c.post(richiesta);
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 200) {
		return Utilities.unMarshallJsonStream(is, LeggiDeterminaResponse.class, false);
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		}
		log.error("WSAttiRestClient.leggiDetermina errore: {}", s);
		throw new FunzioneBusinessRemotaException("Errore nella lettura dei dati della determina." + " Dettaglio Errore: \n" + s);
	    }
	} catch (JAXBException e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public InserisciDeterminaResponse inserisciDetermina(InserisciDeterminaRequest request) throws FunzioneBusinessRemotaException {

	WebClient c = getRestWebClient(this.connectionTimeOut, this.receivetimeout);
	c.type(MediaType.APPLICATION_JSON);
	c.accept(MediaType.APPLICATION_JSON);
	try {
	    String richiesta = Utilities.marshalJsonObject(request, request.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    log.debug("Richiesta WSAttiRestClient.inserisciDetermina {}", richiesta);
	    Response response = c.post(richiesta);
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 200) {
		return Utilities.unMarshallJsonStream(is, InserisciDeterminaResponse.class, false);
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		}
		log.error("WSAttiRestClient.inserisciDetermina errore: {}", s);
		throw new FunzioneBusinessRemotaException("Errore nell'inserimento della determina." + " Dettaglio Errore: \n" + s);
	    }
	} catch (JAXBException e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public NumeraDeterminaResponse numeraDetermina(NumeraDeterminaRequest request) throws FunzioneBusinessRemotaException {

	WebClient c = getRestWebClient(this.connectionTimeOut, this.receivetimeout);
	c.type(MediaType.APPLICATION_JSON);
	c.accept(MediaType.APPLICATION_JSON);
	try {
	    String richiesta = Utilities.marshalJsonObject(request, request.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    log.debug("Richiesta WSAttiRestClient.numeraDetermina {}", richiesta);
	    Response response = c.post(richiesta);
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 200) {
		NumeraDeterminaResponse res = Utilities.unMarshallJsonStream(is, NumeraDeterminaResponse.class, false);
		log.debug("NumeraDeterminaResponse: {}", res);	
		if (res.getNumero() == null) {
		    String errore = "Numero determina nullo";
		    if (!res.getEsito().isOk()) {
			errore = res.getEsito().getMessaggio();
		    }
		    throw new FunzioneBusinessRemotaException("Errore nella numerazione della determina. Dettaglio Errore: \n" + errore);
		}
		return res;
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		}
		log.error("WSAttiRestClient.numeraDetermina errore: {}", s);
		throw new FunzioneBusinessRemotaException("Errore nella numerazione della determina. Dettaglio Errore: \n" + s);
	    }
	} catch (JAXBException e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public AggiungiAllegatoResponse aggiungiAllegato(AggiungiAllegatoRequest request) throws FunzioneBusinessRemotaException {

	WebClient c = getRestWebClient(this.connectionTimeOut, this.receivetimeout);
	c.type(MediaType.APPLICATION_JSON);
	c.accept(MediaType.APPLICATION_JSON);
	try {
	    String richiesta = Utilities.marshalJsonObject(request, request.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    log.debug("Richiesta WSAttiRestClient.aggiungiAllegato {}", richiesta);
	    Response response = c.post(richiesta);
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 200) {
		return Utilities.unMarshallJsonStream(is, AggiungiAllegatoResponse.class, false);
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		}
		log.error("WSAttiRestClient.aggiungiAllegato errore: {}", s);
		throw new FunzioneBusinessRemotaException("Errore nell'aggiunta di un allegato alla determina" + " Dettaglio Errore: \n" + s);
	    }
	} catch (JAXBException e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public FascicolaDeterminaResponse fascicolaDetermina(FascicolaDeterminaRequest request) throws FunzioneBusinessRemotaException {

	WebClient c = getRestWebClient(this.connectionTimeOut, this.receivetimeout);
	c.type(MediaType.APPLICATION_JSON);
	c.accept(MediaType.APPLICATION_JSON);
	try {
	    String richiesta = Utilities.marshalJsonObject(request, request.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    log.debug("Richiesta WSAttiRestClient.fascicolaDetermina {}", richiesta);
	    Response response = c.post(richiesta);
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 200) {
		return Utilities.unMarshallJsonStream(is, FascicolaDeterminaResponse.class, false);
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		}
		log.error("WSAttiRestClient.fascicolaDetermina errore: {}", s);
		throw new FunzioneBusinessRemotaException("Errore nella fascicolazione della determina." + " Dettaglio Errore: \n" + s);
	    }
	} catch (JAXBException e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public FascicolaDeterminaResponse isFascicolata(DeterminaFascicolataRequest request) throws FunzioneBusinessRemotaException {

	WebClient c = getRestWebClient(this.connectionTimeOut, this.receivetimeout);
	c.type(MediaType.APPLICATION_JSON);
	c.accept(MediaType.APPLICATION_JSON);
	try {
	    String richiesta = Utilities.marshalJsonObject(request, request.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    log.debug("Richiesta WSAttiRestClient.isFascicolata {}", richiesta);
	    Response response = c.post(richiesta);
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 200) {
		return Utilities.unMarshallJsonStream(is, FascicolaDeterminaResponse.class, false);
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		}
		log.error("WSAttiRestClient.isFascicolata errore: {}", s);
		throw new FunzioneBusinessRemotaException("Errore nella verifica fascicolazione della determina." + " Dettaglio Errore: \n" + s);
	    }
	} catch (JAXBException e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public ElencoFirmatariResponse elencoFirmatari() throws FunzioneBusinessRemotaException {

	WebClient c = getRestWebClient(this.connectionTimeOut, this.receivetimeout);
	c.type(MediaType.APPLICATION_JSON);
	c.accept(MediaType.APPLICATION_JSON);
	try {
	    Response response = c.post(null);
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 200) {
		ElencoFirmatariResponse elenco = Utilities.unMarshallJsonStream(is, ElencoFirmatariResponse.class, false);
		TreeSet<FirmatarioResponse> elencoOrdinato = new TreeSet<FirmatarioResponse>(new FirmatarioResponseComparator());
		elencoOrdinato.addAll(elenco.getFirmatari());
		return new ElencoFirmatariResponse(elencoOrdinato);
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		}
		log.error("WSAttiRestClient.elencoFirmatari errore: {}", s);
		throw new FunzioneBusinessRemotaException("Errore nella lettura dei firmatari." + " Dettaglio Errore: \n" + s);
	    }
	} catch (JAXBException e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    private WebClient getRestWebClient(long connectionTimeOut, long receivetimeout) {

	log.debug("Url integrazione ws atti: " + this.url);
	WebClient client = WebClient.create(this.url);
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(connectionTimeOut);
	conduit.getClient().setReceiveTimeout(receivetimeout);
	return client;
    }
}
