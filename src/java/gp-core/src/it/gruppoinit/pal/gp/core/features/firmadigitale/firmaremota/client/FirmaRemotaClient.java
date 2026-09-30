package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.ws.client.BaseWsClient;

public class FirmaRemotaClient extends BaseWsClient {

    private static final Logger logger = LoggerFactory.getLogger(FirmaRemotaClient.class);
    private long connectionTimeOut = 20000;
    private long receivetimeout = 600000;
    private String wsUrl;

    public FirmaRemotaClient(String urlWs) {

	this.wsUrl = urlWs;
    }

    public ConfigurazioneWsResponse getConfigurazione() throws FunzioneBusinessRemotaException {

	logger.debug("Inizio recupero configurazione alla url {}", this.wsUrl);
	WebClient client = this.getRestWebClient("configurazione");
	client.accept(MediaType.APPLICATION_JSON);
	Response response = client.get();
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() != 200) {
	    String messaggio = "";
	    try {
		messaggio = IOUtils.toString(is);
	    } catch (IOException e) {
		messaggio = e.getMessage();
	    }
	    logger.error("getConfigurazione: {}", messaggio);
	    throw new FunzioneBusinessRemotaException(
		    "Errore nel recupero della configurazione del componente di firma remota configurato all'endpoint [" +
			    this.wsUrl +
			    "]. Dettaglio Errore: \n" +
			    messaggio);
	}
	try {
	    ObjectMapper objectMapper = new ObjectMapper();
	    List<ConfigurazioneParametroWs> parametri = objectMapper.readValue(is, new TypeReference<List<ConfigurazioneParametroWs>>() {
	    });
	    ConfigurazioneWsResponse config = new ConfigurazioneWsResponse();
	    config.setParametri(parametri);
	    logger.debug("Fine recupero configurazione alla url {}", this.wsUrl);
	    return config;
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public NuovoProcessoResponse avviaProcesso(ConfigurazioneWs configurazione) throws FunzioneBusinessRemotaException {

	logger.debug("Inizio avvio del processo di firma alla url {}", this.wsUrl);
	WebClient client = this.getRestWebClient("processi");
	client.accept(MediaType.APPLICATION_JSON);
	client.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
	String postData = null;
	try {
	    postData = objectMapper.writeValueAsString(configurazione);
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
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
	    logger.error("avviaProcesso: {}", messaggio);
	    throw new FunzioneBusinessRemotaException("Errore nell'avviare il processo di firma remota configurato all'endpoint [" +
		    this.wsUrl +
		    "]. Dettaglio Errore: \n" +
		    messaggio);
	}
	try {
	    NuovoProcessoResponse nuovoProcesso = objectMapper.readValue(is, new TypeReference<NuovoProcessoResponse>() {
	    });
	    logger.debug("Processo {} avviato correttamente", nuovoProcesso.getSessionId());
	    return nuovoProcesso;
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public void aggiungiDocumento(String sessionId, String guid, String nomeFile, InputStream documento) throws FunzioneBusinessRemotaException {

	logger.debug("Inizio caricamento documento {} {} per la sessione di firma {}", new Object[] { guid, nomeFile, sessionId });
	WebClient client = this.getRestWebClient("processi/" + sessionId + "/documenti");
	client.accept(MediaType.WILDCARD);
	client.header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA);
	List<Attachment> atts = new LinkedList<Attachment>();
	ContentDisposition cdGuid = new ContentDisposition("form-data; name=\"guid\"");
	atts.add(new Attachment("guid", new ByteArrayInputStream(guid.getBytes()), cdGuid));
	ContentDisposition cdNome = new ContentDisposition("form-data; name=\"nome\"");
	atts.add(new Attachment("nome", new ByteArrayInputStream(nomeFile.getBytes()), cdNome));
	ContentDisposition cdContent = new ContentDisposition("form-data; name=\"content\"; filename=\"" + nomeFile + "\"");
	atts.add(new Attachment("content", documento, cdContent));
	Response response = client.put(new MultipartBody(atts));
	if (response.getStatus() != 200) {
	    InputStream is = ((InputStream) response.getEntity());
	    String messaggio = "";
	    try {
		messaggio = IOUtils.toString(is);
	    } catch (IOException e) {
		messaggio = e.getMessage();
	    }
	    logger.error("aggiungiDocumento: {}", messaggio);
	    throw new FunzioneBusinessRemotaException("Errore durante la trasmissione del file con guid " +
		    guid +
		    " per la firma remota configurata all'endpoint [" +
		    this.wsUrl +
		    "]. Dettaglio Errore: \n" +
		    messaggio);
	}
	logger.debug("Fine caricamento documento {} {} per la sessione di firma {}", new Object[] { guid, nomeFile, sessionId });
    }

    public void firmaDocumento(String sessionId, ConfigurazioneWs configurazione, List<String> guidDocumenti) throws FunzioneBusinessRemotaException {

	logger.debug("Inizio processo di firma per la sessione {}", sessionId);
	WebClient client = this.getRestWebClient("processi/" + sessionId + "/firma");
	client.accept(MediaType.WILDCARD);
	client.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
	FirmaWsRequest request = new FirmaWsRequest(configurazione, guidDocumenti);
	ObjectMapper objectMapper = new ObjectMapper();
	String postData = null;
	try {
	    postData = objectMapper.writeValueAsString(request);
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
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
	    logger.error("avviaProcesso: {}", messaggio);
	    throw new FunzioneBusinessRemotaException("Errore nell'avviare il processo di firma remota configurato all'endpoint [" +
		    this.wsUrl +
		    "]. Dettaglio Errore: \n" +
		    messaggio);
	}
	logger.debug("Fine processo di firma per la sessione {}", sessionId);
    }

    public VerificaStatoWSResponse verificaStato(String sessionId) throws FunzioneBusinessRemotaException {

	logger.debug("Inizio processo di verifica stato per la sessione {}", sessionId);
	WebClient client = this.getRestWebClient("processi/" + sessionId);
	client.accept(MediaType.WILDCARD);
	client.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
	Response response = client.get();
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() != 200) {
	    String messaggio = "";
	    try {
		messaggio = IOUtils.toString(is);
	    } catch (IOException e) {
		messaggio = e.getMessage();
	    }
	    logger.error("avviaProcesso: {}", messaggio);
	    throw new FunzioneBusinessRemotaException("Errore durante la verifica del processo di firma remota configurato all'endpoint [" +
		    this.wsUrl +
		    "]. Dettaglio Errore: \n" +
		    messaggio);
	}
	try {
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
	    VerificaStatoWSResponse verificaStato = objectMapper.readValue(is, new TypeReference<VerificaStatoWSResponse>() {
	    });
	    logger.debug("Fine processo di verifica stato per la sessione {}", sessionId);
	    return verificaStato;
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public RecuperaFileFirmatoWsResponse recuperaFileFirmato(String sessionId, String guid) throws FunzioneBusinessRemotaException {

	try {
	    logger.debug("Inizio processo di download del file firmato con guid {} per la sessione {} ", new Object[] { guid, sessionId });
	    WebClient client = this.getRestWebClient("processi/" + sessionId + "/documenti/" + guid);
	    client.accept(MediaType.WILDCARD);
	    client.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
	    Response response = client.get();
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() != 200) {
		String messaggio = "";
		try {
		    messaggio = IOUtils.toString(is);
		} catch (IOException e) {
		    messaggio = e.getMessage();
		}
		logger.error("avviaProcesso: {}", messaggio);
		throw new FunzioneBusinessRemotaException("Errore durante la verifica del processo di firma remota configurato all'endpoint [" +
			this.wsUrl +
			"]. Dettaglio Errore: \n" +
			messaggio);
	    }
	    String nomeFile = response.getMetadata().getFirst("X-Nome-File").toString();
	    String tipoFirma = response.getMetadata().getFirst("X-Tipo-Firma").toString();
	    logger.debug("Fine processo di download del file firmato con guid {} per la sessione {} ", new Object[] { guid, sessionId });
	    return new RecuperaFileFirmatoWsResponse(guid, tipoFirma, nomeFile, is);
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    private WebClient getRestWebClient(String serviceUrl) {

	if (StringUtils.isBlank(wsUrl)) {
	    throw new InvalidConfigurationException(
		    "Endpoint del servizio di firma remota non configurata. Controllare il parametro ENDPOINT della verticalizzazione riferita al connettore utilizzato");
	}
	WebClient client = WebClient.create(wsUrl + serviceUrl);
	// connection timeout
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(connectionTimeOut);
	conduit.getClient().setReceiveTimeout(receivetimeout);
	return client;
    }
}
