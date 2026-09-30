package it.gruppoinit.service;

import it.gruppoinit.domain.AmministrazioniBean;
import it.gruppoinit.domain.CampiDinamiciBean;
import it.gruppoinit.domain.InformazioniAlberoInterventiBean;
import it.gruppoinit.domain.InventarioprocedimentoBean;
import it.gruppoinit.domain.ListaSchedeDinamicheBean;
import it.gruppoinit.domain.TipiCausaliOneriBean;
import it.gruppoinit.domain.TipiEndoBean;
import it.gruppoinit.domain.TipiFamiglieEndoBean;
import it.gruppoinit.domain.TipiSoggettoBean;
import it.gruppoinit.service.helper.CodiceDescrizioneBean;
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
import org.springframework.beans.factory.annotation.Autowired;

@Path("/console/")
public class ConsoleRestService extends BaseRestService {

    @Autowired
    private SigeproSecurityWSClient sigeproSecurityWSClient;

    @GET
    @Path("{aliasOrigine}/{softwareOrigine}/tipicausalioneri")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tipicausalioneri(@PathParam("aliasOrigine") String aliasOrigine, @PathParam("softwareOrigine") String softwareOrigine)
	    throws Exception {

	ConsoleOFFLineService consoleOFFLineService = new ConsoleOFFLineService(aliasOrigine, softwareOrigine, sigeproSecurityWSClient);
	List<TipiCausaliOneriBean> tcbs = consoleOFFLineService.getTipicausalioneri();
	return rispostaWs(tcbs, Status.OK);
    }

    @GET
    @Path("{aliasOrigine}/{softwareOrigine}/tipisoggetto")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tipisoggetto(@PathParam("aliasOrigine") String aliasOrigine, @PathParam("softwareOrigine") String softwareOrigine)
	    throws Exception {

	ConsoleOFFLineService consoleOFFLineService = new ConsoleOFFLineService(aliasOrigine, softwareOrigine, sigeproSecurityWSClient);
	List<TipiSoggettoBean> tcbs = consoleOFFLineService.getTipiSoggetto();
	return rispostaWs(tcbs, Status.OK);
    }

    @GET
    @Path("{aliasOrigine}/amministrazioni")
    @Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    public Response amministrazioni(@PathParam("aliasOrigine") String aliasOrigine, @QueryParam("scCodice") String scCodice,
	    @QueryParam("softwareOrigine") String softwareOrigine) throws Exception {

	ConsoleOFFLineService consoleOFFLineService = new ConsoleOFFLineService(aliasOrigine, softwareOrigine, sigeproSecurityWSClient);
	List<CodiceDescrizioneBean> amministrazionis = consoleOFFLineService.getAmministrazioni(scCodice);
	StringBuffer str = new StringBuffer();
	for (CodiceDescrizioneBean cdb : amministrazionis) {
	    if (StringUtils.isNotBlank(cdb.getCodice())) {
		str.append(cdb.getCodice()).append("|").append(cdb.getDescrizione()).append("\n");
	    }
	}
	return rispostaWs(str.toString(), Status.OK);
    }

    @GET
    @Path("{aliasOrigine}/lista-amministrazioni")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response listaAmministrazioni(@PathParam("aliasOrigine") String aliasOrigine, @QueryParam("softwareOrigine") String softwareOrigine,
	    @QueryParam("codiciAmministrazioni") List<Integer> codiciAmministrazioni) throws Exception {

	ConsoleOFFLineService consoleOFFLineService = new ConsoleOFFLineService(aliasOrigine, softwareOrigine, sigeproSecurityWSClient);
	List<AmministrazioniBean> amministrazionis = consoleOFFLineService.getAmministrazioni(codiciAmministrazioni);
	return rispostaWs(amministrazionis, Status.OK);
    }

    @GET
    @Path("{aliasOrigine}/lista-tipi-famiglie-endo")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response listaTipiFamiglieEndo(@PathParam("aliasOrigine") String aliasOrigine, @QueryParam("softwareOrigine") String softwareOrigine,
	    @QueryParam("codiciFamiglieEndo") List<Integer> codiciFamiglieEndo) throws Exception {

	ConsoleOFFLineService consoleOFFLineService = new ConsoleOFFLineService(aliasOrigine, softwareOrigine, sigeproSecurityWSClient);
	List<TipiFamiglieEndoBean> amministrazionis = consoleOFFLineService.getTipiFamiglieEndo(codiciFamiglieEndo);
	return rispostaWs(amministrazionis, Status.OK);
    }

    @GET
    @Path("{aliasOrigine}/lista-tipi-endo")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response listaTipiEndo(@PathParam("aliasOrigine") String aliasOrigine, @QueryParam("softwareOrigine") String softwareOrigine,
	    @QueryParam("codiciTipiEndo") List<Integer> codiciTipiEndo) throws Exception {

	ConsoleOFFLineService consoleOFFLineService = new ConsoleOFFLineService(aliasOrigine, softwareOrigine, sigeproSecurityWSClient);
	List<TipiEndoBean> amministrazionis = consoleOFFLineService.getTipiEndo(codiciTipiEndo);
	return rispostaWs(amministrazionis, Status.OK);
    }

    @GET
    @Path("{aliasOrigine}/lista-endo-procedimenti")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response listaEndoprocedimenti(@PathParam("aliasOrigine") String aliasOrigine, @QueryParam("softwareOrigine") String softwareOrigine,
	    @QueryParam("codiciEndoProcedimenti") List<Integer> codiciEndoProcedimenti) throws Exception {

	ConsoleOFFLineService consoleOFFLineService = new ConsoleOFFLineService(aliasOrigine, softwareOrigine, sigeproSecurityWSClient);
	List<InventarioprocedimentoBean> amministrazionis = consoleOFFLineService.getEndoprocedimenti(codiciEndoProcedimenti);
	return rispostaWs(amministrazionis, Status.OK);
    }

    @GET
    @Path("{aliasOrigine}/informazioni-albero-interventi")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response informazioniInterventi(@PathParam("aliasOrigine") String aliasOrigine, @QueryParam("softwareOrigine") String softwareOrigine,
	    @QueryParam("escludiDisabilitati") Boolean escludiDisabilitati, @QueryParam("scCodice") String scCodice) throws Exception {

	ConsoleOFFLineService consoleOFFLineService = new ConsoleOFFLineService(aliasOrigine, softwareOrigine, sigeproSecurityWSClient);
	InformazioniAlberoInterventiBean result = consoleOFFLineService.getInformazioniInterventi(escludiDisabilitati, scCodice);
	return rispostaWs(result, Status.OK);
    }

    @GET
    @Path("{aliasOrigine}/lista-schede-dinamiche")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response listaSchedeDinamiche(@PathParam("aliasOrigine") String aliasOrigine, @QueryParam("softwareOrigine") String softwareOrigine,
	    @QueryParam("codiciSchedeEndo") List<Integer> codiciSchedeEndo) throws Exception {

	ConsoleOFFLineService consoleOFFLineService = new ConsoleOFFLineService(aliasOrigine, softwareOrigine, sigeproSecurityWSClient);
	ListaSchedeDinamicheBean result = consoleOFFLineService.getListaSchedeDinamiche(codiciSchedeEndo);
	return rispostaWs(result, Status.OK);
    }

    @GET
    @Path("{aliasOrigine}/lista-campi-dinamici")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response listaCampiDinamici(@PathParam("aliasOrigine") String aliasOrigine, @QueryParam("softwareOrigine") String softwareOrigine,
	    @QueryParam("codiciCampi") List<Integer> codiciCampi) throws Exception {

	ConsoleOFFLineService consoleOFFLineService = new ConsoleOFFLineService(aliasOrigine, softwareOrigine, sigeproSecurityWSClient);
	List<CampiDinamiciBean> result = consoleOFFLineService.getListaCampiDinamici(codiciCampi);
	return rispostaWs(result, Status.OK);
    }
}
