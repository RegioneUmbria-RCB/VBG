package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.FaultBean;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.AnnullaPosizionePPAYRestRequest;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.AnnullaPosizionePPAYRestResponse;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.DebtPositionData;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.DebtPositionReference;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.GetDebPositionPaymentDataRequest;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.GetDebtPositionPaymentDataResponse;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.GetDebtPositionStatusRequest;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.GetDebtPosizionStatusResponse;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.GetPaymentNoticeRequest;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.GetPaymentNoticeResponse;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.GetRTRequest;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.GetRTResponse;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.IDebtPositionData;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.IDebtPositionDataUpdate;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.PaymentReceipt;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.PaymentReferences;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.exception.PayException;

public class PiemontePayClient {

    private static final Logger log = LoggerFactory.getLogger(PiemontePayClient.class);
    private static final String SUCCESSO_CHIAMATA = "CHIAMATA RIUSCITA RESPONSE STATUS: {} {}";
    private static final String METODO_PAGAMENTOIUV_API_14 = "/organizations/{organization}/debtpositions/{iuv}/payment";
    private static final String METODO_GETRT = "getRT";
    private static final String METODO_ANNULLAMENTO = "/organizations/{organization}/paymenttypes/{paymentType}/debtpositions/{iuv}";
    private static final String METODO_AGGIORNAMENTO = "/organizations/{organization}/paymenttypes/{paymentType}/debtpositions/{iuv}";
    private static final String METODO_CARICAMENTO = "/organizations/{organization}/paymenttypes/{paymentType}/debtpositions";
    private static final String METODO_GET_DEBT_POSITION_STATUS = "/organizations/{organization}/paymenttypes/{paymentType}/debtpositions/{iuv}/status";
    private static final String METODO_GET_PAYMENT_NOTICE = "/organizations/{organization}/paymenttypes/{paymentType}/debtpositions/{iuv}/paymentnotice";
    private static final String METODO_GET_DEBT_POSITION_PAYMENTDATA = "/organizations/{organization}/paymenttypes/{paymentType}/debtpositions/{iuv}/paymentdata";
    private static final String METODO_GET_DEBT_POSITION_PAYMENT_RECEIPT = "/organizations/{organization}/paymenttypes/{paymentType}/debtpositions/{iuv}/paymentreceipt";
    private String utente;
    private String password;
    private String baseUrl;

    public PiemontePayClient(PayConnectorWsEndpoint wsCaricamentoConfig) {

	this.utente = wsCaricamentoConfig.getUtente();
	this.password = wsCaricamentoConfig.getPassword();
	this.baseUrl = wsCaricamentoConfig.getEndpointUrl();
    }

    public PaymentReceipt getPaymentReceipt(String organization, String paymentType, String iuv) throws PayException {

	String metodo = popolaUrlPaymentReceipt(organization, paymentType, iuv);
	log.debug("getPaymentReceipt {}", metodo);
	WebClient client = createWebClient(metodo);
	Response response = client.get();
	Integer status = response.getStatus();
	log.debug("getPaymentReceipt status {}", status);
	if (response.getMediaType() != null) {
	    log.debug("getPaymentReceipt response.getMediaType() {}", response.getMediaType());
	}
	if (status.equals(200) || status.equals(201)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url getPaymentReceipt: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(PaymentReceipt.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), PaymentReceipt.class).getValue();
	    } catch (JAXBException e) {
		log.error("Errore nel umarshalling: " + e.getMessage(), e);
		throw new PayException("Errore nel umarshalling: " + e.getMessage(), e);
	    }
	} else {
	    log.debug("uri ws getPaymentReceipt: {} ", client.getCurrentURI());
	    this.gestisciErroreHttpAPIRestV103(response, "getPaymentReceipt");
	}
	throw new PayException("Errore nell'invocazione del metodo getPaymentReceipt");
    }

    private String popolaUrlPaymentReceipt(String organization, String paymentType, String iuv) {

	return METODO_GET_DEBT_POSITION_PAYMENT_RECEIPT.replace("{organization}", organization).replace("{paymentType}", paymentType).replace("{iuv}",
		iuv);
    }
    
    public DebtPositionReference creaDebtPosition(DebtPositionData data, String organization, String paymentType) throws PayException {
    	return eseguiDebtPosition(data, organization, paymentType, "POST", popolaUrlDebPosition(organization, paymentType));
    }
    
    public DebtPositionReference putDebtPosition(IDebtPositionDataUpdate data, String organization, String paymentType, String iuv) throws PayException {
    	return eseguiDebtPosition(data, organization, paymentType, "PUT", popolaUrlAggiornamentoDebPosition(organization, paymentType, iuv) );
    }

    private DebtPositionReference eseguiDebtPosition(IDebtPositionData data, String organization, String paymentType, String methodType, String metodo) throws PayException {

	log.debug("eseguiDebtPosition {}", metodo);
	WebClient client = createWebClient(metodo);
	StringWriter sw = new StringWriter();
	try {
	    JAXBContext jc = JAXBContext.newInstance(data.getClass());
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	    marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.FALSE);
	    marshaller.marshal(data, sw);
	} catch (JAXBException e) {
	    log.error("Errore nell' unmarshalling", e);
	    throw new PayException("Errore nell' umarshalling: " + e.getMessage(), e);
	}
	log.debug("Richiesta: {}", sw);
	
	Response response;
	if("POST".equals(methodType)) {
		response = client.post(sw.toString());
	}else {
		response = client.put(sw.toString());
	}

	Integer status = response.getStatus();
	log.debug("eseguiDebtPosition methodType {}", methodType);
	log.debug("eseguiDebtPosition status {}", status);
	if (response.getMediaType() != null) {
	    log.debug("eseguiDebtPosition response.getMediaType() {}", response.getMediaType());
	}
	if (status.equals(200) || status.equals(201)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url eseguiDebtPosition: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(DebtPositionReference.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), DebtPositionReference.class).getValue(); //DebtPositionReference si può usare come response
	    } catch (JAXBException e) {
		log.error("Errore nell' umarshalling: " + e.getMessage(), e);
		throw new PayException("Errore nell' umarshalling: " + e.getMessage(), e);
	    }
	} else {
	    log.debug("uri ws eseguiDebtPosition: {} ", client.getCurrentURI());
	    this.gestisciErroreHttpAPIRestV103(response, "eseguiDebtPosition");
	}
	throw new PayException("Errore nell'invocazione del metodo eseguiDebtPosition");
    }

    private String popolaUrlDebPosition(String organization, String paymentType) {

	return METODO_CARICAMENTO.replace("{organization}", organization).replace("{paymentType}", paymentType);
    }
    
    private String popolaUrlAggiornamentoDebPosition(String organization, String paymentType, String iuv) {

    return METODO_AGGIORNAMENTO.replace("{organization}", organization).replace("{paymentType}", paymentType).replace("{iuv}", iuv);
    }

    public GetRTResponse getRT(GetRTRequest request) throws PayException {

	WebClient client = createWebClient(PiemontePayClient.METODO_GETRT);
	StringWriter sw = new StringWriter();
	try {
	    JAXBContext jc = JAXBContext.newInstance(GetRTRequest.class);
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	    marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.FALSE);
	    marshaller.marshal(request, sw);
	} catch (JAXBException e) {
	    log.debug("Errore nel marshalling del oggetto request", e);
	}
	log.debug("Richiesta: {}", sw);
	Response response = client.post(sw.toString());
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201) || status.equals(500) || status.equals(400)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws getRT: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(GetRTResponse.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), GetRTResponse.class).getValue();
	    } catch (JAXBException e) {
		log.debug("[nuova Pendenze] Errore nel umarshalling: {}", e.getMessage());
	    }
	} else {
	    log.debug("uri ws wsgetIUVChiamanteEsterno: {} ", client.getCurrentURI());
	    this.gestisciErroreHttp(response, "getRT");
	}
	return null;
    }

    public PaymentReferences debtPositionPayment(String organization, String iuv) throws PayException {

	String metodo = popolaUrlDebPositionPayment(organization, iuv);
	WebClient client = createWebClient(metodo);
	Response response = client.post(null);
	Integer status = response.getStatus();
	log.debug("status{}", status);
	if (status.equals(200) || status.equals(201) || status.equals(500) || status.equals(400)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url wsgetIUVChiamanteEsterno: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(PaymentReferences.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), PaymentReferences.class).getValue();
	    } catch (JAXBException e) {
		log.debug("Errore nel umarshalling: {}", e.getMessage());
	    }
	} else {
	    log.debug("uri ws pagamentoIUV: {} ", client.getCurrentURI());
	    this.gestisciErroreHttp(response, "debtPositionPayment");
	}
	return null;
    }

    private String popolaUrlDebPositionPayment(String organization, String iuv) {

	return METODO_PAGAMENTOIUV_API_14.replace("{organization}", organization).replace("{iuv}", iuv);
    }

    private WebClient createWebClient(String metodo) {

	String url = this.baseUrl + metodo;
	WebClient client = WebClient.create(url);
	log.debug("createWebClient {}", url);
	client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).encoding(StandardCharsets.UTF_8.name());
	HTTPConduit conduit = null;
	conduit = WebClient.getConfig(client).getHttpConduit();
	WebClient.getConfig(client).getInInterceptors().add(new LoggingInInterceptor(10240));
	WebClient.getConfig(client).getOutInterceptors().add(new LoggingOutInterceptor(10240));
	log.debug("basic authentication");
	conduit.setAuthorization(basicAuthorization());
	conduit.getClient().setConnectionTimeout(8000);
	conduit.getClient().setReceiveTimeout(600000);
	return client;
    }

    private AuthorizationPolicy basicAuthorization() {

	AuthorizationPolicy authPolicy = new AuthorizationPolicy();
	authPolicy.setUserName(this.utente);
	authPolicy.setPassword(this.password);
	authPolicy.setAuthorizationType("Basic");
	return authPolicy;
    }

    private void gestisciErroreHttp(Response response, String methodName) throws PayException {

	String errorMessage = MessageFormat.format("[{0}]CHIAMATA NON RIUSCITA RESPONSE STATUS: {1} ({2})", methodName,
		response.getStatusInfo().getReasonPhrase(), response.getStatus());
	PayException payException = null;
	log.error(errorMessage);
	InputStream is = ((InputStream) response.getEntity());
	if (response.getMediaType() != null && MediaType.APPLICATION_JSON.equals(response.getMediaType().toString())) {
	    FaultBean errore = null;
	    try {
		JAXBContext jc = JAXBContext.newInstance(FaultBean.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		if (is != null) {
		    log.error("is not null");
		    StreamSource src = new StreamSource(is);
		    JAXBElement<FaultBean> unmarshal = unmarshaller.unmarshal(src, FaultBean.class);
		    if (unmarshal != null) {
			log.error("unmarshal not null");
			errore = unmarshal.getValue();
			if (errore != null) {
			    log.error("errore not null");
			    log.error(errore.getDettaglio());
			    StringBuilder s = new StringBuilder();
			    s.append(errore.getDescrizione());
			    if (StringUtils.isNotEmpty(errore.getDettaglio())) {
				s.append(" : ").append(errore.getDettaglio());
			    }
			    payException = new PayException(s.toString());
			    payException.setErrorCode(errore.getCodice());
			}
		    }
		}
		if (payException == null) {
		    payException = new PayException("Errore (" + response.getStatus() + ")\"" + response.getStatusInfo().getReasonPhrase() +
						    "\"  nell'invocazione del servizio " + methodName + " ");
		}
		throw payException;
	    } catch (JAXBException e) {
		throw new PayException(" Errore nel umarshalling del oggetto FaultBean:" + methodName, e);
	    }
	} else {
	    String text = null;
	    try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
		text = scanner.useDelimiter("\\A").next();
	    }
	    log.error("gestisciErroreHttp: {}", text);
	    payException = new PayException(text);
	    payException.setErrorCode(String.valueOf(response.getStatus()));
	    throw payException;
	}
    }

    public GetDebtPosizionStatusResponse getDebPositionStatus(GetDebtPositionStatusRequest request) throws PayException {

	String metodo = popolaUrlDebPositionStatus(request);
	log.debug("getDebPositionStatus {}", metodo);
	WebClient client = createWebClient(metodo);
	Response response = client.get();
	Integer status = response.getStatus();
	log.debug("getDebPositionStatus status {}", status);
	if (response.getMediaType() != null) {
	    log.debug("getDebPositionStatus response.getMediaType() {}", response.getMediaType());
	}
	if (status.equals(200) || status.equals(201)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url getDebPositionStatus: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(GetDebtPosizionStatusResponse.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), GetDebtPosizionStatusResponse.class).getValue();
	    } catch (JAXBException e) {
		log.error("Errore nel umarshalling: " + e.getMessage(), e);
		throw new PayException("Errore nel umarshalling: " + e.getMessage(), e);
	    }
	} else {
	    log.debug("uri ws getDebPositionStatus: {} ", client.getCurrentURI());
	    this.gestisciErroreHttpAPIRestV103(response, "getDebPositionStatus");
	}
	throw new PayException("Errore nell'invocazione del metodo getDebPositionStatus");
    }

    private String popolaUrlDebPositionStatus(GetDebtPositionStatusRequest req) {

	return METODO_GET_DEBT_POSITION_STATUS.replace("{organization}", req.getCfEnte()).replace("{paymentType}", req.getCodiceVersamento())
		.replace("{iuv}", req.getIuv());
    }

    public GetPaymentNoticeResponse getPaymentNotice(GetPaymentNoticeRequest request) throws PayException {

	String metodo = popolaUrlPaymentNotice(request);
	log.debug("getDebPositionStatus {}", metodo);
	WebClient client = createWebClient(metodo);
	Response response = client.get();
	Integer status = response.getStatus();
	log.debug("getDebPositionStatus status {}", status);
	if (response.getMediaType() != null) {
	    log.debug("getDebPositionStatus response.getMediaType() {}", response.getMediaType());
	}
	if (status.equals(200) || status.equals(201)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url getDebPositionStatus: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(GetPaymentNoticeResponse.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), GetPaymentNoticeResponse.class).getValue();
	    } catch (JAXBException e) {
		log.error("Errore nel umarshalling: " + e.getMessage(), e);
		throw new PayException("Errore nel umarshalling: " + e.getMessage(), e);
	    }
	} else {
	    log.debug("uri ws getDebPositionStatus: {} ", client.getCurrentURI());
	    this.gestisciErroreHttpAPIRestV103(response, "getDebPositionStatus");
	}
	throw new PayException("Errore nell'invocazione del metodo getDebPositionStatus");
    }

    private String popolaUrlPaymentNotice(GetPaymentNoticeRequest req) {

	return METODO_GET_PAYMENT_NOTICE.replace("{organization}", req.getCfEnte()).replace("{paymentType}", req.getCodiceVersamento())
		.replace("{iuv}", req.getIuv());
    }

    public GetDebtPositionPaymentDataResponse getDebtPositionPaymentData(GetDebPositionPaymentDataRequest request) throws PayException, IOException {

	String metodo = popolaUrlDebtPositionData(request);
	log.debug("getDebPositionStatus {}", metodo);
	WebClient client = createWebClient(metodo);
	Response response = client.get();
	Integer status = response.getStatus();
	log.debug("getDebtPositionPaymentData status {}", status);
	if (response.getMediaType() != null) {
	    log.debug("getDebtPositionPaymentData response.getMediaType() {}", response.getMediaType());
	}
	if (status.equals(200) || status.equals(201)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url getDebtPositionPaymentData: {} ", client.getCurrentURI());
	    try {
		InputStream is = ((InputStream) response.getEntity());
		String contentReader = IOUtils.toString(is, StandardCharsets.UTF_8);
		log.debug("getDebtPositionPaymentData-response: {} ", contentReader);
		StringReader stringReader = new StringReader(contentReader);
		JAXBContext jc = JAXBContext.newInstance(GetDebtPositionPaymentDataResponse.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(stringReader), GetDebtPositionPaymentDataResponse.class).getValue();
	    } catch (JAXBException | IOException e) {
		log.error("Errore nel umarshalling: " + e.getMessage(), e);
		throw new PayException("Errore nel umarshalling: " + e.getMessage(), e);
	    }
	} else {
	    log.debug("uri ws getDebtPositionPaymentData: {} ", client.getCurrentURI());
	    this.gestisciErroreHttpAPIRestV103(response, "getDebtPositionPaymentData");
	}
	throw new PayException("Errore nell'invocazione del metodo getDebtPositionPaymentData");
    }

    private String popolaUrlDebtPositionData(GetDebPositionPaymentDataRequest req) {

	return METODO_GET_DEBT_POSITION_PAYMENTDATA.replace("{organization}", req.getCfEnte()).replace("{paymentType}", req.getCodiceVersamento())
		.replace("{iuv}", req.getIuv());
    }

    public AnnullaPosizionePPAYRestResponse annullaPosizioneDebitoria(AnnullaPosizionePPAYRestRequest req) throws PayException {

	String metodoAnnullamento = popolaUrlAnnullamento(req);
	log.debug("annullaPosizioneDebitoria {}", metodoAnnullamento);
	WebClient client = createWebClient(metodoAnnullamento);
	Response response = client.delete();
	Integer status = response.getStatus();
	log.debug("annullaPosizioneDebitoria status {}", status);
	if (response.getMediaType() != null) {
	    log.debug("annullaPosizioneDebitoria response.getMediaType() {}", response.getMediaType());
	}
	if (status.equals(200) || status.equals(201)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url annullaPosizioneDebitoria: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(AnnullaPosizionePPAYRestResponse.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
		return unmarshaller.unmarshal(new StreamSource(is), AnnullaPosizionePPAYRestResponse.class).getValue();
	    } catch (JAXBException e) {
		log.error("Errore nel umarshalling: " + e.getMessage(), e);
		throw new PayException("Errore nel umarshalling: " + e.getMessage(), e);
	    }
	} else if (status.equals(400)) {
	    InputStream is = ((InputStream) response.getEntity());
	    log.error("uri ws annullaPosizioneDebitoria: {} ", client.getCurrentURI());
	    try {
		JAXBContext jc = JAXBContext.newInstance(AnnullaPosizionePPAYRestResponse.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
		AnnullaPosizionePPAYRestResponse ret = unmarshaller.unmarshal(new StreamSource(is), AnnullaPosizionePPAYRestResponse.class)
			.getValue();
		log.error("Esito annullamento {}", ret);
		if (ret.getCode().equals("300") && ret.getDescription().indexOf(" scaduto") >= 0) {
		    return ret;
		} else {
		    throw new PayException("Errore nell'annullamento: " + ret);
		}
	    } catch (JAXBException e) {
		log.error("Errore nel umarshalling: " + e.getMessage(), e);
		throw new PayException("Errore nel umarshalling: " + e.getMessage(), e);
	    }
	} else {
	    log.error("uri ws annullaPosizioneDebitoria: {} ", client.getCurrentURI());
	    this.gestisciErroreHttpAPIRestV103(response, "annullaPosizioneDebitoria");
	}
	throw new PayException("Errore nell'invocazione del metodo annullaPosizioneDebitoria");
    }

    private void gestisciErroreHttpAPIRestV103(Response response, String methodName) throws PayException {

	String errorMessage = MessageFormat.format("[{0}]CHIAMATA NON RIUSCITA RESPONSE STATUS: {1} ({2})", methodName,
		response.getStatusInfo().getReasonPhrase(), response.getStatus());
	PayException payException = null;
	log.error(errorMessage);
	InputStream is = ((InputStream) response.getEntity());
	String text = null;
	try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
	    text = scanner.useDelimiter("\\A").next();
	}
	log.error("gestisciErroreHttp: {}", text);
	payException = new PayException(text);
	payException.setErrorCode(String.valueOf(response.getStatus()));
	throw payException;
    }

    private String popolaUrlAnnullamento(AnnullaPosizionePPAYRestRequest req) {

	return METODO_ANNULLAMENTO.replace("{organization}", req.getCfEnte()).replace("{paymentType}", req.getCodiceVersamento()).replace("{iuv}",
		req.getIuv());
    }

    public static void main(String[] args) throws Exception {

	PayConnectorWsEndpoint wsCaricamentoConfig = new PayConnectorWsEndpoint();
	wsCaricamentoConfig.setEndpointUrl("");
	wsCaricamentoConfig.setUtente("");
	wsCaricamentoConfig.setPassword("");
	PiemontePayClient c = new PiemontePayClient(wsCaricamentoConfig);
	String cfEnte = "";
	String codiceTributo = "";
	String iuv = "";
	GetDebtPosizionStatusResponse ret0 = c.getDebPositionStatus(new GetDebtPositionStatusRequest(cfEnte, codiceTributo, iuv));
	System.out.println(ret0);
	GetDebtPositionPaymentDataResponse ret1 = c.getDebtPositionPaymentData(new GetDebPositionPaymentDataRequest(cfEnte, codiceTributo, iuv));
	System.out.println(ret1);
	GetPaymentNoticeResponse ret2 = c.getPaymentNotice(new GetPaymentNoticeRequest(cfEnte, codiceTributo, iuv));
	System.out.println(ret2);
    }
}
