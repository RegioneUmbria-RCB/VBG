package it.gruppoinit.service;

import it.gruppoinit.service.helper.CodiceDescrizioneBean;
import it.gruppoinit.service.helper.StatusHelper;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import it.gruppoinit.sigeprosecurity.schema.TokenInfoType;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWSClient;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/configurazioni/")
public class ConfigurazioniRestService extends BaseRestService {

    private static final Logger activityLog = LoggerFactory.getLogger("registrazione_attivita");
    @Autowired
    private SigeproSecurityWSClient sigeproSecurityWSClient;

    @GET
    @Path("token/{token}/{aliasOrigine}/{softwareOrigine}/{aliasDestinazione}/{softwareDestinazione}/copia/{idoperazione}/escludiDisabilitati/{escludiDisabilitati}")
    @Produces(MediaType.APPLICATION_XML + "; charset=UTF-8")
    public Response copia(@PathParam("token") String token, @PathParam("aliasOrigine") String aliasOrigine,
	    @PathParam("softwareOrigine") String softwareOrigine, @PathParam("aliasDestinazione") String aliasDestinazione,
	    @PathParam("softwareDestinazione") String softwareDestinazione, @PathParam("idoperazione") String idoperazione,
	    @PathParam("escludiDisabilitati") Boolean escludiDisabilitati, @QueryParam("scCodice") String scCodice) throws Exception {

	String esito = verificaToken(token, aliasDestinazione);
	if (StringUtils.isNotBlank(esito)) {
	    return rispostaWs(esito, Status.UNAUTHORIZED);
	}
	BackendService bs = new BackendService(aliasOrigine, aliasDestinazione, softwareOrigine, softwareDestinazione, sigeproSecurityWSClient,
		idoperazione);
	if (StringUtils.isBlank(scCodice)) {
	    bs.doWork(escludiDisabilitati);
	} else {
	    bs.copiaVoceAlbero(escludiDisabilitati, scCodice);
	}
	String str = "<status>operazione terminata</status>";
	return rispostaWs(str, Status.OK);
    }

    @GET
    @Path("token/{token}/verifica/{idoperazione}")
    @Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    public Response verificastatus(@PathParam("token") String token, @PathParam("idoperazione") String idoperazione) throws Exception {

	String attivita = StatusHelper.attivita(idoperazione);
	String str = attivita;
	return rispostaWs(str, Status.OK);
    }

    @GET
    @Path("token/{token}/{aliasOrigine}/amministrazioni")
    @Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    public Response amministrazioni(@PathParam("token") String token, @PathParam("aliasOrigine") String aliasOrigine,
	    @QueryParam("scCodice") String scCodice, @QueryParam("softwareOrigine") String softwareOrigine) throws Exception {

	if (StringUtils.isBlank(softwareOrigine)) {
	    softwareOrigine = null;
	}
	BackendService bs = new BackendService(aliasOrigine, null, softwareOrigine, null, sigeproSecurityWSClient, null);
	List<CodiceDescrizioneBean> amministrazionis = bs.getAmministrazioni(aliasOrigine, scCodice);
	StringBuffer str = new StringBuffer();
	for (CodiceDescrizioneBean cdb : amministrazionis) {
	    if (StringUtils.isNotBlank(cdb.getCodice())) {
		str.append(cdb.getCodice()).append("|").append(cdb.getDescrizione()).append("\n");
	    }
	}
	return rispostaWs(str.toString(), Status.OK);
    }

    private String verificaToken(String token, String aliasDestinazione) {

	TokenInfoType tokenInfo = sigeproSecurityWSClient.getTokenInfo(token);
	if (!tokenInfo.getAlias().equalsIgnoreCase(aliasDestinazione)) {
	    return "Operazione non consentita";
	}
	if (!tokenInfo.getContesto().equals(ContestoType.OPE)) {
	    return "Operazione non consentita";
	}
	return null;
    }
}
