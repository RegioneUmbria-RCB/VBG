package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaMassiveDDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveServiceFactory;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ListaComunicazioniResoconti;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioneMassivaRigaModel;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Path("/comunicazioni/")
public class ComunicazioniRestService {

    private static final Logger log = LoggerFactory.getLogger(ComunicazioniRestService.class);
    private IComunicazioniMassiveServiceFactory serviceFactory;
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private UserSecurityService userSecurityService;
    IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO;

    @Autowired
    public void setServiceFactory(IComunicazioniMassiveServiceFactory serviceFactory) {

	this.serviceFactory = serviceFactory;
    }

    @Autowired
    public void setComunicazioniMassiveDettaglioDAO(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO) {

	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setAppIoCodaMassiveDDAO(IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO) {

	this.appIoCodaMassiveDDAO = appIoCodaMassiveDDAO;
    }

    @SuppressWarnings("rawtypes")
    @POST
    @Path("{idcomunicazione}/righe/{idriga}/elabora")
    @Descriptions({ @Description(value = "Elabora la riga di comunicazione passata", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response elaboraRiga(@PathParam("idcomunicazione") Integer idComunicazione, @PathParam("idriga") Integer idRiga) {

	try {
	    log.debug("elaboraRiga: inizio metodo");
	    IComunicazioniMassiveService comunicazioniService = this.serviceFactory.getService(idComunicazione);
	    comunicazioniService.elaboraRiga(idRiga);
	    ComunicazioneMassivaRigaModel riga = ComunicazioneMassivaRigaModel.fromMassiveDettaglio(
		    this.comunicazioniMassiveDettaglioDAO.getById(idRiga), comunicazioniService.getWorkFlowService(), appIoCodaMassiveDDAO);
	    log.debug("elaboraRiga: fine metodo");
	    return rispostaJson(riga, Status.OK);
	} catch (Exception e) {
	    log.error("Errore durante la chiamata a elaboraRiga: {}", e.getMessage(), e);
	    return rispostaJson(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    @SuppressWarnings("rawtypes")
    @DELETE
    @Path("{idcomunicazione}")
    @Descriptions({ @Description(value = "Elimina l'intera comunicazione passata", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response eliminaComunicazione(@PathParam("idcomunicazione") Integer idComunicazione) {

	try {
	    log.debug("eliminaComunicazione: inizio metodo");
	    IComunicazioniMassiveService service = this.serviceFactory.getService(idComunicazione);
	    service.eliminaMassiva(idComunicazione, (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails());
	    log.debug("eliminaComunicazione: fine metodo");
	    return rispostaJson("", Status.OK);
	} catch (Exception e) {
	    log.error("Errore durante la chiamata a eliminaComunicazione: {}", e.getMessage(), e);
	    return rispostaJson(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    @SuppressWarnings("rawtypes")
    @POST
    @Path("{idcomunicazione}/elabora")
    @Descriptions({ @Description(value = "Elabora l'intera comunicazione passata", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response elaboraComunicazione(@PathParam("idcomunicazione") Integer idComunicazione) {

	try {
	    log.debug("elaboraComunicazione: inizio metodo");
	    IComunicazioniMassiveService service = this.serviceFactory.getService(idComunicazione);
	    service.elabora(idComunicazione);
	    ListaComunicazioniResoconti resoconto = service.getResoconto(idComunicazione);
	    log.debug("elaboraComunicazione: fine metodo");
	    return rispostaJson(resoconto, Status.OK);
	} catch (Exception e) {
	    log.error("Errore durante la chiamata a elaboraComunicazione: {}", e.getMessage(), e);
	    return rispostaJson(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    private Response rispostaJson(Object entity, Status returnStatus) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
	return builder.build();
    }
}
