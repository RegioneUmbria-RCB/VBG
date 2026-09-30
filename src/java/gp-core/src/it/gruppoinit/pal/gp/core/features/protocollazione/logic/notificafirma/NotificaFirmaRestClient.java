package it.gruppoinit.pal.gp.core.features.protocollazione.logic.notificafirma;

import java.io.InputStream;

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

public class NotificaFirmaRestClient extends BaseWsClient {

    private static final Logger log = LoggerFactory.getLogger(NotificaFirmaRestClient.class);
    private long connectionTimeOut = 12000;
    private long receivetimeout = 600000;
    private String url;

    public NotificaFirmaRestClient(String url) {

	if (StringUtils.isBlank(url)) {
	    throw new InvalidConfigurationException(
		    "Url REST non confugurata; verificare la presenza del parametro WSHOSTURL_ASPNET nella security.");
	}
	this.url = url;
    }

    public NotificaFirmaResponse notifica(NotificaFirmaRequest request) throws FunzioneBusinessRemotaException {

	WebClient c = getRestWebClient(this.connectionTimeOut, this.receivetimeout);
	c.type(MediaType.APPLICATION_JSON);
	c.accept(MediaType.APPLICATION_JSON);
	try {
	    String richiesta = Utilities.marshalJsonObject(request, NotificaFirmaRequest.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    log.debug("Notifica {}", richiesta);
	    Response response = c.post(richiesta);
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 200) {
		return Utilities.unMarshallJsonStream(is, NotificaFirmaResponse.class, false);
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		}
		log.error("NotificaFirmaRestClient.notifica error: {}", s);
		throw new FunzioneBusinessRemotaException("Errore nella verifica di notifica firma per il protocollo con id " +
			request.getIdDocumento() +
			"]. Dettaglio Errore: \n" +
			s);
	    }
	} catch (JAXBException e) {
	    throw new FunzioneBusinessRemotaException(request.getIdDocumento());
	}
    }

    private WebClient getRestWebClient(long connectionTimeOut, long receivetimeout) {

	WebClient client = WebClient.create(this.url);
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(connectionTimeOut);
	conduit.getClient().setReceiveTimeout(receivetimeout);
	return client;
    }
}
