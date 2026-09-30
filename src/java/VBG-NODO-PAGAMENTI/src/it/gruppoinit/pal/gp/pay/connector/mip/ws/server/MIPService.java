package it.gruppoinit.pal.gp.pay.connector.mip.ws.server;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import it.gruppoinit.pal.gp.core.utils.JSONUtils;
import it.gruppoinit.pal.gp.pay.connector.mip.service.MIPBackendService;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.EsitoOperazione;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.NotificaStatoPagamentoDati;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.NotificaStatoPagamentoResp;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;

@Path(value = "/mip")
@Component
public class MIPService extends SpringBeanAutowiringSupport {

    private static final Logger log = LoggerFactory.getLogger(MIPService.class);
    @Autowired
    private ConfigurazionePagamentiService configurazionePagamentiService;
    @Autowired
    private MIPBackendService mipBackendService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/notificaStatoPagamento")
    public Response notificaStatoPagamento(InputStream json) {

	NotificaStatoPagamentoResp resp = new NotificaStatoPagamentoResp();
	try {
	    NotificaStatoPagamentoDati request = JSONUtils.unmarshal(NotificaStatoPagamentoDati.class, json, false);
	    configuraRequestPerCfCodiceProfilo(request.getIdEnte());
	    boolean res = mipBackendService.gestisciNotificaStatoPagamento(request);
	    if (res) {
		resp.setIdEnte(request.getIdEnte());
		resp.setCodiceAvviso(request.getCodiceAvviso());
		EsitoOperazione esitoOperazione = new EsitoOperazione();
		esitoOperazione.setCodice("00");
		esitoOperazione.setDescrizione("esito positivo");
		resp.setEsitoOperazione(esitoOperazione);
	    } else {
		log.error("Errore su notificaStatoPagamento: posizione non trovato oppure errore nel'registrazione dell'avvenuto pagamento");
		EsitoOperazione esitoOperazione = new EsitoOperazione();
		esitoOperazione.setCodice("01");
		esitoOperazione.setDescrizione("errore");
		resp.setEsitoOperazione(esitoOperazione);
	    }
	} catch (Exception e) {
	    log.error("Errore su notificaStatoPagamento {}", e);
	    EsitoOperazione esitoOperazione = new EsitoOperazione();
	    esitoOperazione.setCodice("01");
	    esitoOperazione.setDescrizione("errore");
	    resp.setEsitoOperazione(esitoOperazione);
	}
	return rispostaWs(resp, false);
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

    private void configuraRequestPerCfCodiceProfilo(String cfCodiceProfilo) throws PayConfigurationException {

	this.configurazionePagamentiService.configuraRequestPerEnteCreditore(cfCodiceProfilo);
    }
}
