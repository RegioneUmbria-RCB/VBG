package it.gruppoinit.pal.gp.backoffice.web.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@Path("/configurazioni/")
public class ConfigurazioniRestService extends BaseRestService {

    private static final Logger log = LoggerFactory.getLogger(ConfigurazioniRestService.class);
    @Autowired
    private VerticalizzazioniparametriService verticalizzazioniparametriService;
    public static final String AUTHORIZATION = "Authorization";

    @PUT
    @Path("regole/{software}/{modulo}/{parametro}")
    @Descriptions({ @Description(value = "Ritorna, true se il parametro ha degli override configurati", target = DocTarget.METHOD) })
    public Response aggiorna(@HeaderParam("Authorization") String token, @PathParam("software") String software, @PathParam("modulo") String modulo,
	    @PathParam("parametro") String parametro, @FormParam("comune") String comune, @FormParam("nuovoValore") String nuovoValore)
	    throws SecurityException {

	log.debug("aggiorna# modulo={}, comune={}, parametro={}", new Object[] { modulo, comune, parametro });
	setOrmHelperFromToken(token, software);
	try {
	    verticalizzazioniparametriService.updateParametroDelModulo(modulo, parametro, comune, nuovoValore);
	    ResponseBuilder builder = this.getBuilder(false);
	    builder.status(Status.OK);
	    return builder.build();
	} catch (BusinessValidationException e) {
	    ResponseBuilder builder = this.getBuilder(false);
	    builder.entity(e);
	    builder.status(Status.INTERNAL_SERVER_ERROR);
	    return builder.build();
	} finally {
	    ORMHelper.destroyORMHelper();
	}
    }

    private ResponseBuilder getBuilder(boolean allowAuthorization) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	if (allowAuthorization) {
	    builder.header("Access-Control-Allow-Headers", "Content-Type, authorization");
	} else {
	    builder.header("Access-Control-Allow-Headers", "Content-Type");
	}
	return builder;
    }
}
