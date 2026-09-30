package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.ws.client.BaseWsClient;

public class CartograficoClient extends BaseWsClient {

    private static final Logger logger = LoggerFactory.getLogger(CartograficoClient.class);
    private long connectionTimeOut = 20000;
    private long receivetimeout = 600000;
    private String url;

    public CartograficoClient(String url) {

	this.url = url;
    }

    public GetInfoResponse getInfo() throws FunzioneBusinessRemotaException {

	logger.debug("getInfo: Inizio recupero informazioni sul cartografico attivo chiamando la url {}", this.url);
	WebClient client = this.getRestWebClient("/" + ORMHelper.getSoftware() + "/info");
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	Response response = client.get();
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() != 200) {
	    String messaggio = "";
	    try {
		messaggio = IOUtils.toString(is);
	    } catch (IOException e) {
		messaggio = e.getMessage();
	    }
	    logger.error("getInfo: {}", messaggio);
	    throw new FunzioneBusinessRemotaException(
		    "Errore nella fase di recupero informazioni sul cartografico attivo tramite il componente configurato all'endpoint [" +
			    this.url +
			    "]. Dettaglio Errore: \n" +
			    messaggio);
	}
	try {
	    GetInfoResponse getInfoResponse = objectMapper.readValue(is, new TypeReference<GetInfoResponse>() {
	    });
	    String jsonResponse = objectMapper.writeValueAsString(getInfoResponse);
	    logger.debug("getInfo: chiamata terminata con successo {}", jsonResponse);
	    return getInfoResponse;
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public InnescoResponse getUrlInnescoAttivita(InnescoRequest request) throws FunzioneBusinessRemotaException {

	logger.debug("getUrlInnescoAttivita: Inizio recupero url di innesco chiamando il componente {}", this.url);
	WebClient client = this.getRestWebClient("/" + ORMHelper.getSoftware() + "/attivita/innesco");
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	String postData = null;
	try {
	    postData = objectMapper.writeValueAsString(request);
	} catch (JsonProcessingException e) {
	    throw new RuntimeException(e);
	}
	Response response = client.post(postData);
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() != 200) {
	    String messaggio = "";
	    try {
		messaggio = IOUtils.toString(is);
	    } catch (IOException e) {
		messaggio = e.getMessage();
	    }
	    logger.error("getUrlInnescoAttivita: {}", messaggio);
	    throw new FunzioneBusinessRemotaException("Errore nella fase di innesco tramite il componente configurato all'endpoint [" +
		    this.url +
		    "]. Dettaglio Errore: \n" +
		    messaggio);
	}
	try {
	    InnescoResponse innescoResponse = objectMapper.readValue(is, new TypeReference<InnescoResponse>() {
	    });
	    String jsonResponse = objectMapper.writeValueAsString(innescoResponse);
	    logger.debug("getUrlInnescoAttivita: chiamata terminata con successo {}", jsonResponse);
	    return innescoResponse;
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public InnescoResponse getUrlInnescoAutorizzazioni(InnescoRequest request) throws FunzioneBusinessRemotaException {

	logger.debug("getUrlInnescoAutorizzazioni: Inizio recupero url di innesco chiamando il componente {}", this.url);
	WebClient client = this.getRestWebClient("/" + ORMHelper.getSoftware() + "/autorizzazioni/innesco");
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	String postData = null;
	try {
	    postData = objectMapper.writeValueAsString(request);
	} catch (JsonProcessingException e) {
	    throw new RuntimeException(e);
	}
	Response response = client.post(postData);
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() != 200) {
	    String messaggio = "";
	    try {
		messaggio = IOUtils.toString(is);
	    } catch (IOException e) {
		messaggio = e.getMessage();
	    }
	    logger.error("getUrlInnescoAutorizzazioni: {}", messaggio);
	    throw new FunzioneBusinessRemotaException("Errore nella fase di innesco tramite il componente configurato all'endpoint [" +
		    this.url +
		    "]. Dettaglio Errore: \n" +
		    messaggio);
	}
	try {
	    InnescoResponse innescoResponse = objectMapper.readValue(is, new TypeReference<InnescoResponse>() {
	    });
	    String jsonResponse = objectMapper.writeValueAsString(innescoResponse);
	    logger.debug("getUrlInnescoAutorizzazioni: chiamata terminata con successo {}", jsonResponse);
	    return innescoResponse;
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public InnescoResponse getUrlInnescoIstanze(InnescoRequest request) throws FunzioneBusinessRemotaException {

	logger.debug("getUrlInnescoIstanze: Inizio recupero url di innesco chiamando il componente {}", this.url);
	WebClient client = this.getRestWebClient("/" + ORMHelper.getSoftware() + "/istanze/innesco");
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	String postData = null;
	try {
	    postData = objectMapper.writeValueAsString(request);
	} catch (JsonProcessingException e) {
	    throw new RuntimeException(e);
	}
	Response response = client.post(postData);
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() != 200) {
	    String messaggio = "";
	    try {
		messaggio = IOUtils.toString(is);
	    } catch (IOException e) {
		messaggio = e.getMessage();
	    }
	    logger.error("getUrlInnescoIstanze: {}", messaggio);
	    throw new FunzioneBusinessRemotaException("Errore nella fase di innesco tramite il componente configurato all'endpoint [" +
		    this.url +
		    "]. Dettaglio Errore: \n" +
		    messaggio);
	}
	try {
	    InnescoResponse innescoResponse = objectMapper.readValue(is, new TypeReference<InnescoResponse>() {
	    });
	    String jsonResponse = objectMapper.writeValueAsString(innescoResponse);
	    logger.debug("getUrlInnescoIstanze: chiamata terminata con successo {}", jsonResponse);
	    return innescoResponse;
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public ParametriResponse getParametri(String uuidLocalizzazione) throws FunzioneBusinessRemotaException {

	logger.debug("getParametri: Inizio recupero parametri chiamando il componente {}", this.url);
	WebClient client = this.getRestWebClient("/" + ORMHelper.getSoftware() + "/parametri/" + uuidLocalizzazione);
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	Response response = client.get();
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() != 200) {
	    String messaggio = "";
	    try {
		messaggio = IOUtils.toString(is);
	    } catch (IOException e) {
		messaggio = e.getMessage();
	    }
	    logger.error("getParametri: {}", messaggio);
	    throw new FunzioneBusinessRemotaException("Errore nella fase di recupero parametri tramite il componente configurato all'endpoint [" +
		    this.url +
		    "]. Dettaglio Errore: \n" +
		    messaggio);
	}
	try {
	    ParametriResponse parametriResponse = objectMapper.readValue(is, new TypeReference<ParametriResponse>() {
	    });
	    String jsonResponse = objectMapper.writeValueAsString(parametriResponse);
	    logger.debug("getParametri: chiamata terminata con successo {}", jsonResponse);
	    return parametriResponse;
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    private WebClient getRestWebClient(String serviceUrl) {

	if (StringUtils.isBlank(this.url)) {
	    throw new InvalidConfigurationException(
		    "Endpoint del servizio di integrazione con il gestore dei cartografici non configurata. Controllare il parametro WSHOSTURL_CARTOGRAFICO della security");
	}
	WebClient client = WebClient.create(this.url + serviceUrl);
	// connection timeout
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(connectionTimeOut);
	conduit.getClient().setReceiveTimeout(receivetimeout);
	client.accept(MediaType.APPLICATION_JSON);
	client.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
	client.header(HttpHeaders.AUTHORIZATION, "Bearer " + ORMHelper.getToken());
	return client;
    }
}
