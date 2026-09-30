package it.gruppoinit.pal.gp.pay.connector.payer.web;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.utils.JSONUtils;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.payer.PayerConnector;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.EsitoRispostaNotifica;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.ResponseDTO;
import it.gruppoinit.pal.gp.pay.connector.payer.web.service.PayerNotificaPagamentiService;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;

@Path(value = "/payer")
@Component
public class PayerNotificaPagamentiRestService {

    private static final Logger log = LoggerFactory.getLogger(PayerNotificaPagamentiRestService.class);
    @Autowired
    private ConfigurazionePagamentiService configurazionePagamentiService;
    @Autowired
    private PayConnectorService payConnectorService;
    @Autowired
    private PayerNotificaPagamentiService payerNotificaPagamentiService;

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/notifica/{profile}")
    public Response getNotifica(@PathParam("profile") String connector, @FormParam("rt") String rt, @FormParam("iuv") String iuv,
	    @HeaderParam("chiave") String chiave, @HeaderParam("firma") String firma, @HeaderParam("dataora-richiesta") String dataOra)
	    throws JAXBException, IOException {

	connector = connector.toUpperCase();
	String stato = "OK";
	String descrizione = "RT Ricevuta correttamente";
	try {
	    PayProfiliEntiCreditori ctorCfg = this.configurazionePagamentiService.configuraRequestPerEnteCreditore(connector);
	    if (ctorCfg == null) {
		throw new PayException("Profilo " + connector + " non trovato");
	    }
	    IPayConnector payConnectorInstance = payConnectorService.getPayConnectorInstance();
	    ((PayerConnector) payConnectorInstance).notificaPagamentoDaPayer(rt, iuv, chiave, firma, dataOra);
	} catch (Exception e) {
	    stato = "KO";
	    descrizione = e.getMessage();
	    log.error("notificaPagamenti " + connector + " errore : {}", e.getMessage(), e);
	}
	EsitoRispostaNotifica esito = new EsitoRispostaNotifica();
	esito.setCodiceRisposta(stato);
	esito.setDescrizione(descrizione);
	return rispostaWs(esito, false);
    }

    @OPTIONS
    @Path("/{var:.*}")
    public Response defaultOptions() {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "*");
	builder.header("Access-Control-Allow-Methods", "POST, OPTIONS");
	builder.status(Status.OK);
	return builder.build();
    }
    
    @GET
    @Path("/sincronizza_debiti/{cf_ente_creditore}/{cf_debitore}")
    public Response sincronizzaDebitiPerCF2(@PathParam("cf_ente_creditore") String cfEnteCreditore, @PathParam("cf_debitore") String cfDebitore)
	{    	    	
    	payerNotificaPagamentiService.avviaSincronizzazionePerCf(cfEnteCreditore, cfDebitore);    	
    	return getResponseOk200("Success");    	
    }

    private Response rispostaWs(Object entity, boolean jsonIncludeRoot) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "*");
	if (entity != null) {
	    String ret = null;
	    try {
		ret = JSONUtils.marshal(entity, jsonIncludeRoot);
	    } catch (JAXBException ex) {
		throw new RuntimeException(ex);
	    }
	    builder.entity(ret);
	}
	builder.status(Status.OK);
	return builder.build();
    }
    
    private Response getResponseOk200(String stato) {

    	ResponseDTO responseDTO = new ResponseDTO();
    	responseDTO.setCodice(String.valueOf(HttpStatus.OK.value()));
    	responseDTO.setStato(stato);
    	return rispostaWs(responseDTO, false);
    }
}
