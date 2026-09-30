package it.gruppoinit.pal.gp.areariservata.web.rest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GerarchiaProcedimentiBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ProcedimentoBean;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.helper.FlagPubblicaEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Path("/bancadati/")
public class BancaDatiRestService extends BaseRestService {

    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private OggettiMetadatiService oggettiMetadatiService;

    @GET
    @Path("/procedimenti/gerarchia/{idEnte}/{software}/{identificativo}")
    @Descriptions({ @Description(value = "Recupera l'alberatura dei procedimenti regionali", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response gerarchiaProcedimenti(@PathParam(value = "idEnte") String idEnte, @PathParam(value = "software") String software,
	    @PathParam(value = "identificativo") String identificativo) {

	setORMHelper(idEnte, software);
	GerarchiaProcedimentiBean result = new GerarchiaProcedimentiBean();
	result.setLista(inventarioprocedimentiService.findListaSottonodiDi(identificativo, FlagPubblicaEnum.DA_PUBBLICARE));
	String json = null;
	try {
	    json = toJson(result, false);
	    return rispostaWs(json, Status.OK);
	} catch (JAXBException e) {
	    return rispostaWs("", Status.INTERNAL_SERVER_ERROR); // TODO
	}
    }

    @GET
    @Path("/procedimento/{identificativoEnte}/{software}/{identificativoProcedimento}")
    @Descriptions({ @Description(value = "Recupera il dettaglio del procedimento", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response dettaglioProcedimento(@PathParam(value = "identificativoEnte") String identificativoEnte,
	    @PathParam(value = "software") String software, @PathParam(value = "identificativoProcedimento") String identificativoProcedimento) {

	setORMHelper(identificativoEnte, software);
	Integer codiceProcedimento = null;
	boolean isRegionale = false;
	if (Utilities.isInteger(identificativoProcedimento)) {
	    codiceProcedimento = Integer.valueOf(identificativoProcedimento);
	} else {
	    String cod = identificativoProcedimento.replace("R-", "");
	    isRegionale = true;
	    if (Utilities.isInteger(cod)) {
		codiceProcedimento = Integer.valueOf(cod);
	    } else {
		codiceProcedimento = -99999;
	    }
	}
	ProcedimentoBean result = inventarioprocedimentiService.findProcedimentoBean(codiceProcedimento, isRegionale, identificativoEnte);
	String json;
	try {
	    json = toJson(result, false);
	    return rispostaWs(json, Status.OK);
	} catch (JAXBException e) {
	    return rispostaWs("", Status.INTERNAL_SERVER_ERROR); // TODO
	}
    }

    @GET
    @Path("/documenti/{identificativoEnte}/{idDocumento}")
    @Descriptions({ @Description(value = "Recupera il documento definito dal parametro idDocumento", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadDocumento(@PathParam(value = "identificativoEnte") String identificativoEnte,
	    @PathParam(value = "idDocumento") String guid) {

	if (guid.indexOf("___") > 0) {
	    String[] codDesc = guid.split("___");
	    String alias = codDesc[0];
	    String realUid = codDesc[1];
	    if (!alias.equalsIgnoreCase(ORMHelper.getIdcomuneAlias())) {
		throw new NotImplementedException();
		// return downloadExternal(alias, realUid, request, response); // da implementare
	    }
	    guid = guid.substring((guid.indexOf("___") + 3));
	}
	List<String> idcomunes = new ArrayList<String>();
	idcomunes.add(ORMHelper.getIdcomune());
	idcomunes.add(ORMHelper.getIdcomunebase());
	Oggetti o = oggettiMetadatiService.findByGUID(idcomunes, guid, true);
	InputStream is = oggettiService.getOggettoAsInputStream(o.getId().getIdcomune(), o.getId().getCodice());
	List<CodiceDescrizioneBean> headerAggiuntivi = new ArrayList<CodiceDescrizioneBean>();
	CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	cdb.setCodice("Content-Disposition");
	cdb.setDescrizione("attachment; filename=\"" + o.getNomefile() + "\"");
	headerAggiuntivi.add(cdb);
	cdb = new CodiceDescrizioneBean();
	cdb.setCodice("Content-Type");
	cdb.setDescrizione("application/zip");
	headerAggiuntivi.add(cdb);
	return rispostaWs(is, Status.OK, headerAggiuntivi);
    }

    @GET
    @Path("/status")
    @Descriptions({ @Description(value = "Se il servizio è raggiungibile", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response status() throws Exception {

	String result = "{";
	result += "  \"detail\": \"Service is up\",";
	result += "  \"status\": 200,";
	result += "  \"title\": \"Servizio pratiche\"";
	result += "}";
	return rispostaWs(result, Status.OK);
    }
}
