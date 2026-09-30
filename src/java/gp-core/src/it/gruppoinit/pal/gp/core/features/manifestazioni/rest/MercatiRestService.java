package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.Mercatistradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggitipospazio;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatistradarioService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Path("/configurazione/manifestazioni/")
public class MercatiRestService {

    private static final Logger log = LoggerFactory.getLogger(MercatiRestService.class);
    private MercatiService mercatiService;
    private MercatistradarioService mercatistradarioService;
    private MercatiDService mercatiDService;
    private AutorizzazioniConcessioniService concessioniService;

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setMercatistradarioService(MercatistradarioService mercatistradarioService) {

	this.mercatistradarioService = mercatistradarioService;
    }

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Autowired
    public void setConcessioniService(AutorizzazioniConcessioniService concessioniService) {

	this.concessioniService = concessioniService;
    }

    @POST
    @Path("/mercati")
    @Descriptions({ @Description(value = "Inserisce un nuovo mercato", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response inserisciMercato(String json) {

	log.debug("inserisciMercato: accedo il metodo");
	try {
	    InserisciMercatoRequest request = Utilities.unMarshallJsonString(json, InserisciMercatoRequest.class, Utilities.JAXB_ENCODING_UTF_8,
		    true);
	    request.valida();
	    Mercati mercato = Mercati.FromInserisciMercatoRequest(request);
	    this.mercatiService.insert(mercato);
	    InserisciMercatoResponse response = InserisciMercatoResponse.FromMercati(mercato);
	    log.debug("inserisciMercato: fine metodo");
	    return rispostaJson(response, Status.OK);
	} catch (Exception e) {
	    log.error("Errore durante la chiamata a inserisciMercato: {}", e.getMessage(), e);
	    return rispostaJson(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    @PUT
    @Path("/mercati/{codicemercato}")
    @Descriptions({ @Description(value = "Aggiorna i dati di un mercato esistente", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response aggiornaMercato(@PathParam("codicemercato") int codiceMercato, String json) {

	log.debug("aggiornaMercato: accedo il metodo");
	try {
	    AggiornaMercatoRequest request = Utilities.unMarshallJsonString(json, AggiornaMercatoRequest.class, Utilities.JAXB_ENCODING_UTF_8, true);
	    request.valida();
	    Mercati mercato = this.mercatiService.findById(new PkId(ORMHelper.getIdcomune(), codiceMercato));
	    mercato.setDescrizione(request.getDescrizione());
	    mercato.setAttivo(request.isAttivo());
	    this.mercatiService.update(mercato);
	    AggiornaMercatoResponse response = AggiornaMercatoResponse.FromMercati(mercato);
	    log.debug("aggiornaMercato: fine metodo");
	    return rispostaJson(response, Status.OK);
	} catch (Exception e) {
	    log.error("Errore durante la chiamata a aggiornaMercato: {}", e.getMessage(), e);
	    return rispostaJson(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    @POST
    @Path("/mercati/{codicemercato}/stradario")
    @Descriptions({ @Description(value = "Inserisce una via in un mercato esistente", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response inserisciStradario(@PathParam("codicemercato") int codiceMercato, String json) {

	log.debug("inserisciStradario: accedo il metodo");
	try {
	    InserisciStradarioRequest request = Utilities.unMarshallJsonString(json, InserisciStradarioRequest.class, Utilities.JAXB_ENCODING_UTF_8,
		    true);
	    request.setCodiceMercato(codiceMercato);
	    request.valida();
	    Mercatistradario stradario = Mercatistradario.FromInserisciStradarioRequest(request);
	    this.mercatistradarioService.insert(stradario);
	    InserisciStradarioResponse response = InserisciStradarioResponse.FromMercatistradario(stradario);
	    log.debug("inserisciStradario: fine metodo");
	    return rispostaJson(response, Status.OK);
	} catch (Exception e) {
	    log.error("Errore durante la chiamata a inserisciStradario: {}", e.getMessage(), e);
	    return rispostaJson(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    @POST
    @Path("/mercati/{codicemercato}/posteggio")
    @Descriptions({ @Description(value = "Inserisce un posteggio in un mercato esistente", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response inserisciPosteggio(@PathParam("codicemercato") int codiceMercato, String json) {

	log.debug("inserisciPosteggio: accedo il metodo");
	try {
	    InserisciPosteggioRequest request = Utilities.unMarshallJsonString(json, InserisciPosteggioRequest.class, Utilities.JAXB_ENCODING_UTF_8,
		    true);
	    request.setCodiceMercato(codiceMercato);
	    request.valida();
	    MercatiD posteggio = MercatiD.FromInserisciPosteggioRequest(request);
	    this.mercatiDService.insert(posteggio);
	    InserisciPosteggioResponse response = InserisciPosteggioResponse.FromMercatiD(posteggio);
	    log.debug("inserisciPosteggio: fine metodo");
	    return rispostaJson(response, Status.OK);
	} catch (Exception e) {
	    log.error("Errore durante la chiamata a inserisciPosteggio: {}", e.getMessage(), e);
	    return rispostaJson(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    @PUT
    @Path("/mercati/{codicemercato}/posteggio/{idposteggio}")
    @Descriptions({ @Description(value = "Aggiorna un posteggio esistente", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response aggiornaPosteggio(@PathParam("codicemercato") int codiceMercato, @PathParam("idposteggio") int idPosteggio, String json) {

	log.debug("aggiornaPosteggio: accedo il metodo");
	try {
	    AggiornaPosteggioRequest request = Utilities.unMarshallJsonString(json, AggiornaPosteggioRequest.class, Utilities.JAXB_ENCODING_UTF_8,
		    true);
	    request.valida();
	    MercatiD posteggio = this.mercatiDService.findById(new PkId(idPosteggio));
	    posteggio.setCodiceposteggio(request.getCodicePosteggio());
	    posteggio.setDisabilitato(request.isDisabilitato());
	    posteggio.setFlgTemporaneo(request.isDisabilitato());
	    posteggio.setLarghezza(request.getLarghezza());
	    posteggio.setLunghezza(request.getLunghezza());
	    posteggio.setNote(request.getNote());
	    posteggio.setStradario(null);
	    if (request.getCodiceStradario() != null) {
		posteggio.setStradario(new Stradario(request.getCodiceStradario()));
	    }
	    posteggio.setSuperficie(request.getSuperficieComplessiva());
	    posteggio.setTipoSpazio(null);
	    if (request.getCodiceTipoSpazio() != null) {
		posteggio.setTipoSpazio(new Posteggitipospazio(request.getCodiceTipoSpazio()));
	    }
	    this.mercatiDService.update(posteggio);
	    AggiornaPosteggioResponse response = AggiornaPosteggioResponse.FromMercatiD(posteggio);
	    log.debug("aggiornaPosteggio: fine metodo");
	    return rispostaJson(response, Status.OK);
	} catch (Exception e) {
	    log.error("Errore durante la chiamata a aggiornaPosteggio: {}", e.getMessage(), e);
	    return rispostaJson(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    @PUT
    @Path("/mercati/{codicemercato}/posteggio/{idposteggio}/cessaposteggio")
    @Descriptions({ @Description(value = "Cessa un posteggio esistente senza cambiare altre informazioni", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response cessaPosteggio(@PathParam("codicemercato") int codiceMercato, @PathParam("idposteggio") int idPosteggio) {

	log.debug("cessaPosteggio: accedo il metodo");
	try {
	    MercatiD posteggio = this.mercatiDService.findById(new PkId(idPosteggio));
	    posteggio.setDisabilitato(Boolean.TRUE);
	    this.mercatiDService.update(posteggio);
	    log.debug("cessaPosteggio: fine metodo");
	    return rispostaJson("", Status.OK);
	} catch (Exception e) {
	    log.error("Errore durante la chiamata a cessaPosteggio: {}", e.getMessage(), e);
	    return rispostaJson(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    @PUT
    @Path("/mercati/{codicemercato}/posteggio/{idposteggio}/cessaconcessioni")
    @Descriptions({ @Description(value = "Cessa le concessioni attive presenti in un posteggio esistente", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response cessaConcessioni(@PathParam("codicemercato") int codiceMercato, @PathParam("idposteggio") int idPosteggio, String json) {

	log.debug("cessaConcessioni: accedo il metodo");
	try {
	    CessaConcessioniRequest request = Utilities.unMarshallJsonString(json, CessaConcessioniRequest.class, Utilities.JAXB_ENCODING_UTF_8,
		    true);
	    request.valida();
	    int cessate = this.concessioniService.cessaConcessioniByIdPosteggio(idPosteggio, request.getDataCessazione(),
		    request.getIdCausaleCessazione());
	    CessaConcessioniResponse response = CessaConcessioniResponse.FromTotaleCessate(cessate);
	    log.debug("cessaConcessioni: fine metodo");
	    return rispostaJson(response, Status.OK);
	} catch (Exception e) {
	    log.error("Errore durante la chiamata a cessaConcessioni: {}", e.getMessage(), e);
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
