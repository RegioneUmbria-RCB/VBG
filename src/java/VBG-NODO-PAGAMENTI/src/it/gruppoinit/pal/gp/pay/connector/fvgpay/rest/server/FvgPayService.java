package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.server;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import it.gruppoinit.pal.gp.core.utils.JSONUtils;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.NotificaEsitiPagamentoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.NotificaEsitiRegistrazioneType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.NotificaEsitiRevocaType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.OkMessage;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.Problem;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.service.FvgPayBackendService;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;

@Path(value = "/fvgpay/")
@Component
public class FvgPayService extends SpringBeanAutowiringSupport {

    @Autowired
    private ConfigurazionePagamentiService configurazionePagamentiService;
    @Autowired
    private FvgPayBackendService fvgPayBackendService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/notificaEsitiRevoca")
    public Response getNotificaEsitoRevoca(InputStream json) {

	try {
	    NotificaEsitiRevocaType request = JSONUtils.unmarshal(NotificaEsitiRevocaType.class, json, false);
	    configuraRequestPerCfCodiceProfilo(request.getIdentificativoBeneficiario());
	    fvgPayBackendService.gestisciEsitoRevocaPagamento(request);
	} catch (Exception e) {
	    return erroreWs(getProblem(e, "NotificaEsitiRevocaType"));
	}
	return rispostaWs(getOkMessage(), false);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/notificaEsitiRegistrazione")
    public Response getNotificaEsitoRegistrazione(InputStream json) {

	try {
	    NotificaEsitiRegistrazioneType request = JSONUtils.unmarshal(NotificaEsitiRegistrazioneType.class, json, false);
	    configuraRequestPerCfCodiceProfilo(request.getIdentificativoBeneficiario());
	    fvgPayBackendService.gestisciNotificaEsitiRegistrazione(request);
	} catch (Exception e) {
	    return erroreWs(getProblem(e, "NotificaEsitiRegistrazioneType"));
	}
	return rispostaWs(getOkMessage(), false);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/notificaEsitiPagamento/esiti-pagamento")
    public Response getNotificaEsitoPagamento(InputStream json) {

	try {
	    NotificaEsitiPagamentoType request = JSONUtils.unmarshal(NotificaEsitiPagamentoType.class, json, false);
	    configuraRequestPerCfCodiceProfilo(request.getIdentificativoBeneficiario());
	    fvgPayBackendService.gestisciNotificaEsitiPagamento(request);
	} catch (Exception e) {
	    return erroreWs(getProblem(e, "NotificaEsitiPagamentoType"));
	}
	return rispostaWs(getOkMessage(), false);
    }

    private Problem getProblem(Exception e, String funzione) {

	Problem ret = new Problem();
	ret.setTitle("Errore nella ricezione del messaggio " + funzione);
	ret.setDetail("Si è verificato un errore nella ricezione del messaggio " + funzione + ": " + e.getMessage());
	StackTraceElement[] stackTrace = e.getStackTrace();
	if (stackTrace != null) {
	    for (StackTraceElement stackTraceElement : stackTrace) {
		ret.getErrorInfos().add(stackTraceElement.toString());
	    }
	}
	ret.setStatus(500);
	ret.setType(funzione);
	return ret;
    }

    private OkMessage getOkMessage() {

	OkMessage ret = new OkMessage();
	ret.setMessage("OK");
	return ret;
    }

    @OPTIONS
    @Path("/{var:.*}")
    public Response defaultOptions() {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
	builder.header("Access-Control-Allow-Methods", "POST, OPTIONS");
	builder.status(Status.OK);
	return builder.build();
    }

    private Response rispostaWs(Object entity, boolean jsonIncludeRoot) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
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

    private Response erroreWs(Problem messaggio) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
	builder.status(Status.INTERNAL_SERVER_ERROR);
	String ret = null;
	try {
	    ret = JSONUtils.marshal(messaggio, false);
	} catch (JAXBException ex) {
	    throw new RuntimeException(ex);
	}
	builder.entity(ret);
	return builder.build();
    }

    private void configuraRequestPerCfCodiceProfilo(String cfCodiceProfilo) throws PayConfigurationException {

	this.configurazionePagamentiService.configuraRequestPerEnteCreditore(cfCodiceProfilo);
    }
}
