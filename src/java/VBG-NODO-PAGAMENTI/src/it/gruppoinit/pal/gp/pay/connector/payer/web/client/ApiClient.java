package it.gruppoinit.pal.gp.pay.connector.payer.web.client;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.utils.JSONUtils;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.client.ApiClient.HttpMethods;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.EsitoListaPagamenti;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.EsitoNegativo;
import it.gruppoinit.pal.gp.pay.exception.PayException;

public class ApiClient {

    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);
    private String secret;
    private String chiave;

    public ApiClient(String secret, String chiave) {

	super();
	this.secret = secret;
	this.chiave = chiave;
	//this.chiave = "uTzE5c6cXBk806xUyUb0YN41YSYFmSvd";
    }

    public <T> T call(String urlWs, String httpMethod, MediaType type, Object reqBody, Class<T> clazz) throws Exception {

	WebClient client = WebClient.create(urlWs);
	client.accept(MediaType.APPLICATION_JSON_TYPE).encoding(StandardCharsets.UTF_8.name());
	HTTPConduit conduit = null;
	conduit = WebClient.getConfig(client).getHttpConduit();
	WebClient.getConfig(client).getInInterceptors().add(new LoggingInInterceptor(10240));
	WebClient.getConfig(client).getOutInterceptors().add(new LoggingOutInterceptor(10240));
	conduit.getClient().setConnectionTimeout(600000);
	conduit.getClient().setReceiveTimeout(600000);
	AutenticazioneHeaderGenerator auth = new AutenticazioneHeaderGenerator(this.secret, this.chiave);
	auth.generateAuthHeaders();
	client.header("chiave", auth.getApiChiave());
	client.header("firma", auth.getFirma());
	client.header("Content-Type", type);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	client.header("dataora-richiesta", sdf.format(auth.getDataOraRichiesta()));
	//client.header("dataora-richiesta", "2017-04-14 15:10:53");
	Response response = null;
	switch (HttpMethods.fromValue(StringUtils.upperCase(httpMethod))) {
	case POST:
	    response = client.post(reqBody);
	    break;
	case PUT:
	    response = client.put(reqBody);
	    break;
	case DELETE:
	    response = client.delete();
	    break;
	case GET:
	    response = client.get();
	    break;
	default:
	    break;
	}
	Integer status = response.getStatus();
	if (status.equals(200)) {
	    log.debug("SUCCESSO_CHIAMATA", status, response.getStatusInfo().getReasonPhrase());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		return JSONUtils.unmarshal(clazz, is, false);
	    } catch (JAXBException e) {
		log.error("Unmarshalling fallita: {}", e.getMessage());
		throw new PayException(e);
	    }
	} else {
	    InputStream is = ((InputStream) response.getEntity());
	    log.error("gestisciErroreHttp: status {}", status);
	    try {
		String messaggioErrore = null;
		if (response.getMediaType() != null && MediaType.APPLICATION_JSON.equals(response.getMediaType().toString())) {
		    EsitoNegativo esito = JSONUtils.unmarshal(EsitoNegativo.class, is, false);
		    messaggioErrore = "Errore dal Payer " + ReflectionToStringBuilder.toString(esito, ToStringStyle.SHORT_PREFIX_STYLE);
		} else {
		    String text = null;
		    try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
			text = scanner.useDelimiter("\\A").next();
		    }
		    log.error("gestisciErroreHttp: {}", text);
		    messaggioErrore = text;
		}
		throw new PayException(messaggioErrore);
	    } catch (Exception e) {
		log.error(e.getMessage());
		throw new PayException("Errore dal Payer CODICE ERRORE :" + e.getMessage(), e);
	    }
	}
    }

    public static void main(String[] args) throws Exception {

	ApiClient client = new ApiClient("", "");
	EsitoListaPagamenti call = client.call("https://payertestapi.lepida.it/pagamenti?codice_pagatore=BCCRCR73H23G888O", "GET",
		MediaType.APPLICATION_JSON_TYPE, null, EsitoListaPagamenti.class);
	System.out.println(call);
    }
}
