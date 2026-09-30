package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.LoginRequest;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.LoginResponse;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.PrinterLoginRequest;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.PrinterLoginResponse;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.exc.JCityGovRestException;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;

public class JCityGovClient {

    private static final Logger log = LoggerFactory.getLogger(JCityGovClient.class);
    private PayConnectorWsEndpoint eayConnectorWsEndpoint;
    private String identificativoEnte;

    public JCityGovClient(PayConnectorWsEndpoint eayConnectorWsEndpoint, String identificativoEnte) {

	this.eayConnectorWsEndpoint = eayConnectorWsEndpoint;
	this.identificativoEnte = identificativoEnte;
    }

    private WebClient createWebClient(String metodo) {

	String baseUrl = eayConnectorWsEndpoint.getEndpointUrl();
	String url = baseUrl + metodo;
	WebClient client = WebClient.create(url);
	//log.debug("createWebClient {}", url);
	client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).encoding(StandardCharsets.UTF_8.name());
	HTTPConduit conduit = null;
	conduit = WebClient.getConfig(client).getHttpConduit();
	WebClient.getConfig(client).getInInterceptors().add(new LoggingInInterceptor(10240));
	WebClient.getConfig(client).getOutInterceptors().add(new LoggingOutInterceptor(10240));
	//log.debug("basic authentication");
	//conduit.setAuthorization(basicAuthorization());
	conduit.getClient().setConnectionTimeout(eayConnectorWsEndpoint.getTimeout());
	conduit.getClient().setReceiveTimeout(eayConnectorWsEndpoint.getTimeout());
	return client;
    }

    private LoginResponse login() {

	String idMessaggio = UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis();
	String identificativoEnte = this.identificativoEnte;
	String password = eayConnectorWsEndpoint.getPassword();
	String username = eayConnectorWsEndpoint.getUtente();
	LoginRequest request = new LoginRequest();
	request.setIdMessaggio(idMessaggio);
	request.setUsername(username);
	request.setPassword(password);
	request.setIdentificativoEnte(identificativoEnte);
	WebClient client = createWebClient("/rest/login");
	StringWriter sw = new StringWriter();
	try {
	    Map<String, Object> props = new HashMap<>();
	    props.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
	    props.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
	    JAXBContext jc = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { LoginRequest.class }, props);
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.marshal(request, sw);
	} catch (JAXBException e) {
	    log.error("Errore nell' unmarshalling", e);
	    throw new RuntimeException("Errore nell' umarshalling: " + e.getMessage(), e);
	}
	//log.debug("Richiesta: {}", sw);
	Response response = client.post(sw.toString());
	Integer status = response.getStatus();
	//log.debug("creaDebtPosition login {}", status);
	if (response.getMediaType() != null) {
	    //log.debug("login response.getMediaType() {}", response.getMediaType());
	}
	if (status.equals(200) || status.equals(201)) {
	    // log.debug(SUCCESSO_CHIAMATA, status,
	    // response.getStatusInfo().getReasonPhrase());
	    // log.debug("url creaDebtPosition: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		Map<String, Object> props = new HashMap<>();
		props.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
		props.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
		JAXBContext jc = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { LoginResponse.class }, props);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		return unmarshaller.unmarshal(new StreamSource(is), LoginResponse.class).getValue();
	    } catch (JAXBException e) {
		log.error("Errore nell' umarshalling: " + e.getMessage(), e);
		throw new RuntimeException("Errore nell' umarshalling: " + e.getMessage(), e);
	    }
	} else {
	    gestisciErroreNonOk(response);
	}
	throw new RuntimeException("Errore nell'invocazione del metodo login");
    }

    private WebClient createWebClientAuthorized(String metodo) {

	WebClient client = createWebClient(metodo);
	String token = login().getToken();
	client.header("Authorization", "Bearer " + token);
	return client;
    }

    public Object pagamentiExec(Object request, String metodo, Class<?> clazz, HttpMethod httpm) {

	WebClient client = createWebClientAuthorized(metodo);
	StringWriter sw = new StringWriter();
	try {
	    Map<String, Object> props = new HashMap<>();
	    props.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
	    props.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
	    JAXBContext jc = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { request.getClass() }, props);
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.marshal(request, sw);
	} catch (JAXBException e) {
	    log.error("Errore nell' unmarshalling", e);
	    throw new RuntimeException("Errore nell' umarshalling: " + e.getMessage(), e);
	}
	log.debug("Richiesta: {}", sw);
	Response response = null;
	switch (httpm) {
	case POST:
	    String req = sw.toString();
	    response = client.post(req);
	    break;
	//	        case PUT:
	//	            response = client.put(sw.toString());
	//	            break;
	case DELETE:
	    response = client.invoke("DELETE", sw.toString());
	    break;
	default:
	    throw new IllegalArgumentException("Metodo HTTP non supportato: " + httpm);
	}
	//client.post(sw.toString());
	Integer status = response.getStatus();
	//log.debug("creaDebtPosition status {}", status);
	if (response.getMediaType() != null) {
	    //log.debug("creaDebtPosition response.getMediaType() {}", response.getMediaType());
	}
	if (status.equals(200) || status.equals(201)) {
	    // log.debug(SUCCESSO_CHIAMATA, status,
	    // response.getStatusInfo().getReasonPhrase());
	    // log.debug("url creaDebtPosition: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		Map<String, Object> props = new HashMap<>();
		props.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
		props.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
		JAXBContext jc = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { clazz }, props);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		return unmarshaller.unmarshal(new StreamSource(is), clazz).getValue();
	    } catch (JAXBException e) {
		log.error("Errore nell' umarshalling: " + e.getMessage(), e);
		throw new RuntimeException("Errore nell' umarshalling: " + e.getMessage(), e);
	    }
	} else {
	    gestisciErroreNonOk(response);
	}
	throw new RuntimeException("Errore nell'invocazione del metodo " + metodo);
    }

    private void gestisciErroreNonOk(Response response) {

	InputStream is = (InputStream) response.getEntity();
	String responseString = "";
	if (is != null) {
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
		    sb.append(line).append("\n");
		}
		responseString = sb.toString();
	    } catch (Exception e) {
		throw new RuntimeException("Errore nella lettura della response di errore: " + e.getMessage(), e);
	    }
	}
	log.error("Errore HTTP " + response.getStatus());
	log.error("Body della risposta: " + responseString);
	throw new JCityGovRestException("Errore HTTP " + response.getStatus() + ": " + responseString).errorCode(response.getStatus()).bodyResponse(responseString);
    }

    public Object printerPost(Object request, String metodo, Class<?> clazz) {

	WebClient client = createPrinterWebClientAuthorized(metodo);
	StringWriter sw = new StringWriter();
	try {
	    Map<String, Object> props = new HashMap<>();
	    props.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
	    props.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
	    JAXBContext jc = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { request.getClass() }, props);
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.marshal(request, sw);
	} catch (JAXBException e) {
	    log.error("Errore nell' unmarshalling", e);
	    throw new RuntimeException("Errore nell' umarshalling: " + e.getMessage(), e);
	}
	//log.debug("Richiesta: {}", sw);
	Response response = client.post(sw.toString());
	Integer status = response.getStatus();
	//log.debug("creaDebtPosition status {}", status);
	if (response.getMediaType() != null) {
	    //log.debug("creaDebtPosition response.getMediaType() {}", response.getMediaType());
	}
	if (status.equals(200) || status.equals(201)) {
	    // log.debug(SUCCESSO_CHIAMATA, status,
	    // response.getStatusInfo().getReasonPhrase());
	    // log.debug("url creaDebtPosition: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		Map<String, Object> props = new HashMap<>();
		props.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
		props.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
		JAXBContext jc = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { clazz }, props);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		return unmarshaller.unmarshal(new StreamSource(is), clazz).getValue();
	    } catch (JAXBException e) {
		log.error("Errore nell' umarshalling: " + e.getMessage(), e);
		throw new RuntimeException("Errore nell' umarshalling: " + e.getMessage(), e);
	    }
	} else {
	    gestisciErroreNonOk(response);
	}
	throw new RuntimeException("Errore nell'invocazione del metodo creaDebtPosition");
    }

    private WebClient createPrinterWebClientAuthorized(String metodo) {

	WebClient client = createWebClient(metodo);
	String token = printerLogin().getToken();
	client.header("Authorization", "Bearer " + token);
	return client;
    }

    private PrinterLoginResponse printerLogin() {

	String password = eayConnectorWsEndpoint.getPassword();
	String username = eayConnectorWsEndpoint.getUtente();
	PrinterLoginRequest request = new PrinterLoginRequest();
	request.setPassword(password);
	request.setUsername(username);
	WebClient client = createWebClient("/rest/printer/v2/authenticate");
	StringWriter sw = new StringWriter();
	try {
	    Map<String, Object> props = new HashMap<>();
	    props.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
	    props.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
	    JAXBContext jc = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { PrinterLoginRequest.class }, props);
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.marshal(request, sw);
	} catch (JAXBException e) {
	    log.error("Errore nell' unmarshalling", e);
	    throw new RuntimeException("Errore nell' umarshalling: " + e.getMessage(), e);
	}
	//log.debug("Richiesta: {}", sw);
	Response response = client.post(sw.toString());
	Integer status = response.getStatus();
	//log.debug("creaDebtPosition login {}", status);
	if (response.getMediaType() != null) {
	    //log.debug("login response.getMediaType() {}", response.getMediaType());
	}
	if (status.equals(200) || status.equals(201)) {
	    // log.debug(SUCCESSO_CHIAMATA, status,
	    // response.getStatusInfo().getReasonPhrase());
	    // log.debug("url creaDebtPosition: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		Map<String, Object> props = new HashMap<>();
		props.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
		props.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
		JAXBContext jc = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { PrinterLoginResponse.class }, props);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		return unmarshaller.unmarshal(new StreamSource(is), PrinterLoginResponse.class).getValue();
	    } catch (JAXBException e) {
		log.error("Errore nell' umarshalling: " + e.getMessage(), e);
		throw new RuntimeException("Errore nell' umarshalling: " + e.getMessage(), e);
	    }
	} else {
	    gestisciErroreNonOk(response);
	}
	throw new RuntimeException("Errore nell'invocazione del metodo login");
    }
}
