package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import java.io.InputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.BaseWsClient;

public class IstanzeStrRicalcoloRestClient extends BaseWsClient {

    private static final Logger log = LoggerFactory.getLogger(IstanzeStrRicalcoloRestClient.class);
    private long connectionTimeOut = 12000;
    private long receivetimeout = 600000;
    public static final String ROOTURL = "http://localhost:8082/rest-apiiii";
    public static final String FORMAT_DD_MM_YYYY = "dd-MM-yyyy";
    public static final String CHAR_SEPARATORE = "-";
    public static final int STOPTHREADFIRST = 1000;
    public static final int STOPTHREADLOOP = 2000;
    public static final int MAX_LOOP = 5;

    public RicalcoloMaxResponse ricalcolaArea(RicalcoloMaxRequest request, RicalcoloMaxReqParams params) {

	log.debug("ricalcolaArea called");
	StringBuilder sb = new StringBuilder();
	sb.append(getRootUrl()).append("/aree");
	sb.append("/").append(params.getToken());
	sb.append("/").append(params.getSoftware());
	sb.append("/").append("ricalcolo");
	if (params.getType() != null) {
	    sb.append("?type=").append(params.getType());
	}
	WebClient c = getRestWebClient(this.connectionTimeOut, this.receivetimeout, sb.toString());
	c.type(MediaType.APPLICATION_JSON);
	c.accept(MediaType.APPLICATION_JSON);
	try {
	    String richiesta = Utilities.marshalJsonObject(request, RicalcoloMaxRequest.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    Response response = c.post(richiesta);
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 200) {
		return Utilities.unMarshallJsonStream(is, RicalcoloMaxResponse.class, false);
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		}
		log.error("IstanzeStrRicalcoloRestClient.ricalcolaArea error: {}", s);
		throw new RuntimeException("Errore nel processo di ricalcolo . Dettaglio Errore: \n" + s);
	    }
	} catch (JAXBException e) {
	    throw new RuntimeException("Errore interno");
	}
    }

    public RespSessionData getStatusRicalcolo(String sessionid, String token) throws FunzioneBusinessRemotaException {

	log.debug("getStatusRicalcolo called");
	StringBuilder sb = new StringBuilder();
	sb.append(getRootUrl()).append("/aree");
	sb.append("/").append(token);
	sb.append("/").append(sessionid);
	WebClient c = getRestWebClient(this.connectionTimeOut, this.receivetimeout, sb.toString());
	c.type(MediaType.APPLICATION_JSON);
	c.accept(MediaType.APPLICATION_JSON);
	try {
	    //String richiesta = Utilities.marshalJsonObject(request, NotificaFirmaRequest.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    //log.debug("Notifica {}", richiesta);
	    Response response = c.get();
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 200) {
		return Utilities.unMarshallJsonStream(is, RespSessionData.class, false);
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		}
		log.error("IstanzeStrRicalcoloRestClient.ricalcolaArea error: {}", s);
		throw new FunzioneBusinessRemotaException(
			"Errore nella verifica di notifica firma per il protocollo con id " + "id" + "]. Dettaglio Errore: \n" + s);
	    }
	} catch (JAXBException e) {
	    throw new FunzioneBusinessRemotaException("id documento");
	}
    }

    private WebClient getRestWebClient(long connectionTimeOut, long receivetimeout, String url) {

	WebClient client = WebClient.create(url);
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(connectionTimeOut);
	conduit.getClient().setReceiveTimeout(receivetimeout);
	return client;
    }

    public static String getRootUrl() {

	return WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_RICALCOLOAREE);
    }
}
