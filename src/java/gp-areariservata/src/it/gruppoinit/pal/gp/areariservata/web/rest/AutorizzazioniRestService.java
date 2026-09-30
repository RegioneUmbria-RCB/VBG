package it.gruppoinit.pal.gp.areariservata.web.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.AutorizzazioniMercatoSrv;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.MercatoSrvRequest;
import it.gruppoinit.pal.gp.core.exception.SecurityException;
import it.gruppoinit.pal.gp.core.service.MercatiAppService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Path("/autorizzazioni/")
public class AutorizzazioniRestService extends BaseRestService {

    @Autowired
    private MercatiAppService mercatiAppService;
    private static final Logger log = LoggerFactory.getLogger(AutorizzazioniRestService.class.getName());

    @GET
    @Path("/{alias}/{software}/elenco")
    @Descriptions({ @Description(value = "Ottiene la lista delle autorizzazioni associate all'utente loggato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getElencoAutorizzazioniUtente(@HeaderParam(AUTHORIZATION) String auth, @PathParam("alias") String alias,
	    @PathParam("software") String software, @QueryParam("codiceFiscale") List<String> codiceFiscale,
	    @QueryParam("autorizzazione") List<String> autorizzazione, @QueryParam("comuni") String comuni,
	    @QueryParam("soloAttivi") Boolean soloAttivi) {

	try {
	    log.debug("Prima dell'autenticazione");
	    authenticateBasic(auth);
	    log.debug("autenticazione ok setto ORMHelper per {}-{}", alias, software);
	    setORMHelper(alias, software);
	    MercatoSrvRequest req = new MercatoSrvRequest(codiceFiscale, autorizzazione, comuni, BooleanUtils.toBoolean(soloAttivi));
	    if (req.validaRequest().isEmpty()) {
		log.debug("Validazione request ok richiedo le autorizzazioni");
		List<AutorizzazioniMercatoSrv> output = mercatiAppService.findAutorizzazioniMercatoSrvByRequest(req, true);
		if (log.isDebugEnabled()) {
		    log.debug("autorizzazioni richieste {}", output.size());
		}
		String ret = Utilities.marshalJsonObject(output, AutorizzazioniMercatoSrv.class, false, Utilities.JAXB_ENCODING_UTF_8);
		log.debug("Ritorno le autorizzazioni {}", ret);
		ORMHelper.destroyORMHelper();
		return rispostaWs(ret, Status.OK);
	    } else {
		return rispostaWs(req.getErroriToString(), Status.INTERNAL_SERVER_ERROR);
	    }
	} catch (Exception e1) {
	    log.error("Errore in invocazione API", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    public static void main(String[] args) throws SecurityException {

	AutorizzazioniRestService aut = new AutorizzazioniRestService();
	aut.authenticateBasic("dXRlbnRlOnB3ZA");
    }

    private void authenticateBasic(String auth) throws SecurityException {

	if (StringUtils.isBlank(auth)) {
	    log.error("Errore in autenticazione 001 {}", auth);
	    throw new SecurityException("Errore in autenticazione 001");
	}
	String datiUtente = new String(Base64.decodeBase64(auth.replace("Basic ", "").getBytes()));
	if (datiUtente.indexOf(":") < 0) {
	    log.error("Errore in autenticazione 002 {}", auth);
	    throw new SecurityException("Errore in autenticazione 002");
	}
	String[] userNamePassword = datiUtente.split("\\:");
	String username = System.getenv("AREARISERVATA2_REST_API_USERNAME");
	String password = System.getenv("AREARISERVATA2_REST_API_PASSWORD");
	if (StringUtils.isBlank(password) || StringUtils.isBlank(username)) {
	    log.error("Errore in autenticazione 003 {}", auth);
	    throw new SecurityException("Errore in autenticazione 003");
	}
	if (!(username.equalsIgnoreCase(userNamePassword[0]) && password.equalsIgnoreCase(userNamePassword[1]))) {
	    log.error("Errore in autenticazione 004 {}", auth);
	    throw new SecurityException("Errore in autenticazione 004");
	}
    }
}
