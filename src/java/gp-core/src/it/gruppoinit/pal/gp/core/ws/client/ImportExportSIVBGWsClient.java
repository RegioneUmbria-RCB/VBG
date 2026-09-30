package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.DownloadRepositoryByVers;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.ObjectFactory;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.RepertorioWSPortType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.RepositoryVersion;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.ws.soap.MTOMFeature;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ImportExportSIVBGWsClient extends BaseWsClient {

    private static final Logger log = LoggerFactory.getLogger(ImportExportSIVBGWsClient.class);

    public boolean creaNuovoRepository(String url, String bustaXMLString) {

	try {
	    log.debug("creaNuovoRepository: url={}, bustaXMLString={}", new Object[] { url, bustaXMLString });
	    Boolean response = getRepertorioWsPort(url).creaNuovoRepository(bustaXMLString);
	    if (response == null || response.booleanValue() == false) {
		log.error("creaNuovoRepository: {}", new Object[] { "messageResponse is NULL or FALSE" });
		throw new Exception("L'operazione di creaNuovoRepository non è andata a buon fine (WS MessageResponse is NULL or FALSE)");
	    } else {
		log.debug("creaNuovoRepository: response={}", response.booleanValue());
	    }
	    return response.booleanValue();
	} catch (Exception e) {
	    log.error("creaNuovoRepository: {}", new Object[] { e });
	    throw new RuntimeException(e.getMessage());
	}
    }

    public boolean uploadProcedimento(String url, String bustaXMLString) {

	try {
	    log.debug("uploadProcedimento: url={}, bustaXMLString={}", new Object[] { url, bustaXMLString });
	    Boolean messageResponse = getRepertorioWsPort(url).uploadProcedimento(bustaXMLString); // TODOCXF (UploadProcedimentoResponse) webServiceTemplate.marshalSendAndReceive(url, messageRequest);
	    if (messageResponse == null || messageResponse.booleanValue() == false) {
		log.error("uploadProcedimento: {}", new Object[] { "messageResponse is NULL or FALSE" });
		throw new Exception("L'operazione di export dell'endo non è andata a buon fine (WS MessageResponse is NULL or FALSE)");
	    } else {
		log.debug("uploadProcedimento: response={}", messageResponse.booleanValue());
	    }
	    return messageResponse.booleanValue();
	} catch (Exception e) {
	    log.error("exportSIVBG: {}", new Object[] { e });
	    throw new RuntimeException(e.getMessage());
	}
    }

    public List<RepositoryVersion> getVersione(String url, String nome_servizio) {

	try {
	    log.debug("getVersione: url={}, servizio={}", new Object[] { url, nome_servizio });
	    List<RepositoryVersion> messageResponse = getRepertorioWsPort(url).getVersione(nome_servizio);
	    if (messageResponse == null) {
		log.warn("getVersione: {}", new Object[] { "messageResponse is NULL" });
		return (List) new ArrayList<RepositoryVersion>();
	    } else {
		log.debug("getVersione: Numero versioni locali disponibili {}", messageResponse.size());
	    }
	    return messageResponse;
	} catch (Exception e) {
	    log.error("getVersione: {}", new Object[] { e });
	    throw new RuntimeException(e.getMessage());
	}
    }

    public List<RepositoryVersion> getVersioneRemota(String url, String nome_servizio) {

	try {
	    log.debug("getVersioneRemota: url={}, servizio={}", new Object[] { url, nome_servizio });
	    List<RepositoryVersion> messageResponse = getRepertorioWsPort(url).getVersioneRemota(nome_servizio);
	    if (messageResponse == null) {
		log.error("getVersione: {}", new Object[] { "messageResponse is NULL" });
		throw new Exception(
			"L'operazione per recuperare la lista delle versioni disponibili non è andata a buon fine (WS MessageResponse is NULL)");
	    } else {
		log.debug("getVersione: Numero versioni remote disponibili {}", messageResponse.size());
	    }
	    return messageResponse;
	} catch (Exception e) {
	    log.error("getVersioneRemota: {}", new Object[] { e });
	    throw new RuntimeException(e.getMessage());
	}
    }

    public String downloadRepository(String url, String servizio) {

	try {
	    log.debug("downloadRepository: url{}, servizio={}", new Object[] { url, servizio });
	    String messageResponse = getRepertorioWsPort(url).downloadRepository(servizio);
	    if (StringUtils.isBlank(messageResponse)) {
		log.error("downloadRepository: {}", new Object[] { "messageResponse is NULL" });
		throw new Exception("L'operazione di download dal repository non è andata a buon fine (WS MessageResponse is NULL)");
	    } else {
		log.debug("downloadRepository: response={}", messageResponse);
	    }
	    return messageResponse;
	} catch (Exception e) {
	    log.error("downloadRepository: {}", new Object[] { e });
	    throw new RuntimeException(e.getMessage());
	}
    }

    public String downloadRepositoryByVersion(String url, RepositoryVersion versione) {

	DownloadRepositoryByVers messageRequest = new DownloadRepositoryByVers();
	ObjectFactory factory = new ObjectFactory();
	JAXBElement<RepositoryVersion> downloadRepByVersion = factory.createDownloadRepositoryByVersRepVers(versione);
	messageRequest.setRepVers(downloadRepByVersion);
	try {
	    log.debug("downloadRepositoryByVersion: url={}, versione.local={}, versione.note={},versione.remoteVersion={},versione.servizio={}",
		    new Object[] { url, versione.getLocalVers().getValue(), versione.getNote().getValue(), versione.getRemoteVers().getValue(),
			    versione.getServizio().getValue() });
	    String messageResponse = getRepertorioWsPort(url).downloadRepositoryByVers(versione);
	    if (StringUtils.isBlank(messageResponse)) {
		log.error("downloadRepositoryByVersion: {}", new Object[] { "messageResponse is NULL" });
		throw new Exception("L'operazione per recuperare il repository Regionale non è andata a buon fine (WS MessageResponse is NULL)");
	    } else {
		log.debug("downloadRepositoryByVersion: response={}", messageResponse);
	    }
	    return messageResponse;
	} catch (Exception e) {
	    log.error("downloadRepositoryByVersion: {}", new Object[] { e });
	    throw new RuntimeException(e.getMessage());
	}
    }

    public String downloadRepositoryLocale(String url, String servizio) {

	try {
	    log.debug("downloadRepositoryLocale: url={}, versione.servizio={}", new Object[] { url, servizio });
	    String messageResponse = getRepertorioWsPort(url).downloadRepositoryLocale(servizio);
	    if (StringUtils.isBlank(messageResponse)) {
		log.error("downloadRepositoryLocale: {}", new Object[] { "messageResponse is NULL" });
		throw new Exception("L'operazione per recuperare il repository locale non è andata a buon fine (WS MessageResponse is NULL)");
	    } else {
		log.debug("downloadRepositoryLocale: response={}", messageResponse);
	    }
	    return messageResponse;
	} catch (Exception e) {
	    log.error("downloadRepositoryLocale: {}", new Object[] { e });
	    throw new RuntimeException(e.getMessage());
	}
    }

    public String downloadRepositoryByVersionLocale(String url, RepositoryVersion versione) {

	try {
	    log.debug(
		    "downloadRepositoryByVersionLocale: url={}, versione.local={}, versione.note={},versione.remoteVersion={},versione.servizio={}",
		    new Object[] { url, versione.getLocalVers().getValue(), versione.getNote().getValue(), versione.getRemoteVers().getValue(),
			    versione.getServizio().getValue() });
	    String messageResponse = getRepertorioWsPort(url).downloadRepositoryByVersLocale(versione);
	    if (StringUtils.isBlank(messageResponse)) {
		log.error("downloadRepositoryByVersionLocale: {}", new Object[] { "messageResponse is NULL" });
		throw new Exception(
			"L'operazione per recuperare il repository locale (by version) non è andata a buon fine (WS MessageResponse is NULL)");
	    } else {
		log.debug("downloadRepositoryByVersionLocale: response={}", messageResponse);
	    }
	    return messageResponse;
	} catch (Exception e) {
	    log.error("downloadRepositoryByVersionLocale: {}", new Object[] { e });
	    throw new RuntimeException(e.getMessage());
	}
    }

    private RepertorioWSPortType getRepertorioWsPort(String wsUrl) throws Exception {

	RepertorioWS client = new RepertorioWS(new URL(wsUrl));
	MTOMFeature mtomFeature = new MTOMFeature(false, 0);
	RepertorioWSPortType port = client.getRepertorioWSHttpSoap11Endpoint(mtomFeature);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	// HTTPClientPolicy - Properties used to configure a client-side HTTP port  
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(120000); // Line #2  
	httpClientPolicy.setReceiveTimeout(120000); // Line #3  
	conduit.setClient(httpClientPolicy);
	return port;
    }
}
