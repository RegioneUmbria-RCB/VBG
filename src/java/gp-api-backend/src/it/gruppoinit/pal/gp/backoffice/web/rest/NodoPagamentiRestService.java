package it.gruppoinit.pal.gp.backoffice.web.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.paevolution.ws.pagamenti_types.VerificaStatoPosizioniResponseType;

import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

// http://10.10.45.64:8080/api-backend/services/rest-auth-token/nodo-pagamenti/
@Path("/nodo-pagamenti/")
public class NodoPagamentiRestService extends BaseRestService {

    private static final Logger log = LoggerFactory.getLogger(NodoPagamentiRestService.class);
    @Autowired
    private DettPosizioneDebitoriaService posDebitoriaService;

    @POST
    @Path("/posizione-debitoria/aggiorna-stato/{cf_ente_creditore}")
    @Descriptions({
	    @Description(value = "Produce, a partire dall'identificativo dell posizione debitoria del sistema di pagamenti la lettera di accompagnamento, se configurata per la tipologia di bollettazione", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response aggiornaStatoPosizioneDebitoria(String jsonStatoPosizione, @PathParam("cf_ente_creditore") String cf_ente_creditore) {

	try {
	    log.debug("aggiorna-stato: accedo il metodo {}, {}", cf_ente_creditore, jsonStatoPosizione);
	    VerificaStatoPosizioniResponseType verificaStato = Utilities.unMarshallJsonString(jsonStatoPosizione,
		    VerificaStatoPosizioniResponseType.class, Utilities.JAXB_ENCODING_UTF_8, true);
	    log.debug("aggiorno lo stato");
	    this.posDebitoriaService.updateDaRiferimentoNodoPagamenti(cf_ente_creditore, verificaStato);
	    return rispostaWs("", Status.OK);
	} catch (Exception e) {
	    log.error("errore nell'aggiornamento stato della posizione debitoria {}-{}: {}",
		    new Object[] { cf_ente_creditore, jsonStatoPosizione, e.getMessage(), e });
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }
}
