package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.GetLocalizzazioniRequest;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeStradarioExtendedDTO;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.istanze.temp.TmpIstanzeService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Path("/localizzazioni/")
public class LocalizzazioniRestService extends BaseRestService {

    private static final Logger log = LoggerFactory.getLogger(LocalizzazioniRestService.class);
    private IstanzestradarioService istanzestradarioService;
    private TmpIstanzeService tmpIstanzeService;

    @Autowired
    public void setIstanzestradarioService(IstanzestradarioService istanzestradarioService) {

	this.istanzestradarioService = istanzestradarioService;
    }

    @Autowired
    public void setTmpIstanzeService(TmpIstanzeService tmpIstanzeService) {

	this.tmpIstanzeService = tmpIstanzeService;
    }

    @POST
    @Path("/istanze")
    @Descriptions({ @Description(value = "Torna l'elenco dei dati localizzativi delle istanze passate", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getLocalizzazioniIstanze(String jsonRequest) throws IOException {

	try {
	    log.debug("getLocalizzazioniIstanze: Inizio chiamata con i seguenti parametri {}", jsonRequest);
	    GetLocalizzazioniRequest request;
	    request = Utilities.unMarshallJsonString(jsonRequest, GetLocalizzazioniRequest.class, Utilities.JAXB_ENCODING_UTF_8, true);
	    //1. Inserisco in una tabella temporanea gli UUID per la join della query che estrapola i dati
	    log.debug("getLocalizzazioniIstanze: Inserimento in tabella temporanea degli UUID per la join della query che estrapola i dati");
	    this.tmpIstanzeService.insert(ORMHelper.getToken(), request.getUuidIstanze());
	    //2. Recupero le informazioni necessarie
	    log.debug("getLocalizzazioniIstanze: Recupero delle informazioni necessarie");
	    List<IstanzeStradarioExtendedDTO> localizzazioni = this.istanzestradarioService.findByTmp(ORMHelper.getToken());
	    String retVal = Utilities.marshalJsonObject(localizzazioni, IstanzeStradarioExtendedDTO.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    log.debug("getLocalizzazioniIstanze: Response: {}", retVal);
	    return rispostaWs(retVal, Status.OK);
	} catch (Exception e) {
	    log.error("getLocalizzazioniIstanze: {}", e);
	    return rispostaWs("{}", Status.INTERNAL_SERVER_ERROR);
	}
    }
}
