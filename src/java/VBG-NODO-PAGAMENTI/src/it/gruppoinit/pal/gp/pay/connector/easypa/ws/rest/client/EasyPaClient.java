package it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Scanner;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.utils.IOAuth2Params;
import it.gruppoinit.pal.gp.core.utils.OAuth2SecurityRestTokenManager;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.modellazione.DettaglioPosizioneResponse;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.modellazione.RispostaRicevutaTelematica;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.security.EasyPaSecurityRequestParams;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.FaultBean;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.helper.RTHelper;

public class EasyPaClient {

    private static final int CONNECTION_TIMEOUT = 12000;
    private static final int READ_TIMEOUT = 50000;
    private static final Logger log = LoggerFactory.getLogger(EasyPaClient.class);
    private String bearerName;
    private String token;
    private String urlWs;

    public EasyPaClient(String token, String bearerName, PayConnectorWsEndpoint payConnectorWsEndpoint) {

	this.token = token;
	this.bearerName = bearerName;
	this.urlWs = payConnectorWsEndpoint.getEndpointUrl();
    }

    public static void main(String[] args) throws PayException {

	PayConnectorWsEndpoint tokenWs = new PayConnectorWsEndpoint();
	tokenWs.setEndpointUrl("");
	tokenWs.setUtente("");
	tokenWs.setPassword("");
	String grantType = "";
	String codiceIstituto = "";
	String codiceEnte = "";
	String idEnte = "";
	String idDominio = "";
	IOAuth2Params params = new EasyPaSecurityRequestParams(grantType, codiceIstituto, codiceEnte, idEnte, idDominio);
	OAuth2SecurityRestTokenManager mgr = new OAuth2SecurityRestTokenManager(tokenWs, params);
	String token = mgr.getSecurityToken();
	PayConnectorWsEndpoint endpoint = new PayConnectorWsEndpoint();
	endpoint.setEndpointUrl("https://web.pasemplice.eu/connettorenodo/services/rest/inc/dettaglio");
	EasyPaClient client = new EasyPaClient(token, "Bearer", endpoint);
	DettaglioPosizioneResponse res = client.getDettaglioPosizione("RF05022021102030005001043");
	System.out.println(res.getStato());
    }

    public DettaglioPosizioneResponse getDettaglioPosizione(String iuv) throws PayException {

	log.debug("creo il client a url {}", urlWs);
	WebClient client = WebClient.create(urlWs);
	client.type(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON).encoding("UTF-8").header("Authorization",
		this.bearerName + " " + this.token);
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(CONNECTION_TIMEOUT);
	conduit.getClient().setReceiveTimeout(READ_TIMEOUT);
	log.debug("prima di effettuare la chiamata al client {}", client);
	Response response = client.post(new Form().param("iuv", iuv));
	Integer status = response.getStatus();
	String errMessage = "";
	if (status.equals(200)) {
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(DettaglioPosizioneResponse.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), DettaglioPosizioneResponse.class).getValue();
	    } catch (JAXBException e) {
		errMessage = "Errore nel recupero del dettaglio della posizione " + e.getMessage();
		log.debug("[getDettaglioPosizione] Errore nel umarshalling: {}", e.getMessage(), e);
	    }
	} else {
	    log.debug("uri ws [dettaglio pagamento]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "getDettaglioPosizione");
	}
	throw new PayException(errMessage);
    }

    public RispostaRicevutaTelematica getRicevutaTelematicaXML(String iuv) throws PayException {

	log.debug("creo il client a url {}", urlWs);
	WebClient client = WebClient.create(urlWs);
	client.type(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_XML).encoding("UTF-8").header("Authorization",
		this.bearerName + " " + this.token);
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(CONNECTION_TIMEOUT);
	conduit.getClient().setReceiveTimeout(READ_TIMEOUT);
	log.debug("prima di effettaure la chiamata al client {}", client);
	Response response = client.post(new Form().param("iuv", iuv).param("filename", "Ricevuta_" + iuv + ".xml"));
	Integer status = response.getStatus();
	if (status.equals(200)) {
	    InputStream is = (InputStream) response.getEntity();
	    try {
		String rt = IOUtils.toString(is, StandardCharsets.UTF_8.name());
		log.debug("RT {}", rt);
		return new RispostaRicevutaTelematica(RTHelper.parseRicevutaTelematica(rt), rt);
	    } catch (IOException e) {
		log.error("Errore nel recupero della ricevuta telematica {}", e.getMessage(), e);
		throw new PayException("Errore nel recupero della ricevuta telematica " + e.getMessage(), e);
	    } finally {
		if (is != null) {
		    try {
			is.close();
		    } catch (Exception e) {
			// do nothing
		    }
		}
	    }
	}
	throw gestisciErroreHttp(response, iuv);
    }

    private PayException gestisciErroreHttp(Response response, String methodName) {

	String errorMessage = MessageFormat.format("[{0}]CHIAMATA NON RIUSCITA RESPONSE STATUS: {1}", methodName,
		response.getStatusInfo().getReasonPhrase());
	PayException payException = null;
	InputStream is = ((InputStream) response.getEntity());
	if (response.getMediaType() != null && MediaType.APPLICATION_JSON.equals(response.getMediaType().toString())) {
	    FaultBean errore = null;
	    try {
		JAXBContext jc = JAXBContext.newInstance(FaultBean.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		errore = unmarshaller.unmarshal(new StreamSource(is), FaultBean.class).getValue();
		log.error(errorMessage);
		log.error(errore.getDettaglio());
		StringBuilder s = new StringBuilder();
		s.append(errore.getDescrizione());
		if (StringUtils.isNotEmpty(errore.getDettaglio())) {
		    s.append(" : ").append(errore.getDettaglio());
		}
		payException = new PayException(s.toString());
		payException.setErrorCode(errore.getCodice());
		return payException;
	    } catch (JAXBException e) {
		return new PayException("Errore nel umarshalling dell' oggetto FaultBean:" + methodName, e);
	    }
	} else {
	    String text = null;
	    try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
		text = scanner.useDelimiter("\\A").next();
	    }
	    log.error("gestisciErroreHttp: {}", text);
	    payException = new PayException(text);
	    payException.setErrorCode(String.valueOf(response.getStatus()));
	    return payException;
	}
    }
}
