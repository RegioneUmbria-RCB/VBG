package it.gruppoinit.pal.gp.areariservata.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.AutorizzazioniConcessioniIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.EndoIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.EsitoChiamataLista;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.IstanzaLista;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.LocalizzazioneIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.MovimentoIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.NomeValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.OnereIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.SoggettoCollegatoIstanza;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.IStampaPDFDaQRCodeService;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.OggettoPdfBean;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.ParametriPerPDFHelper;
import it.gruppoinit.pal.gp.core.service.StcMobileCompatibilityService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import net.sf.sojo.interchange.Serializer;

@Path("/istanze/")
public class IstanzeRestService extends BaseRestService {

    @Autowired
    private StcMobileCompatibilityService stcMobileCompatibilityService;
    @Autowired
    private IStampaPDFDaQRCodeService stampaPDFDaQRCodeService;

    /**
     * Restituisce la lista delle istanze corrispondenti ai criteri di ricerca passati in querystring
     * 
     * @param alias
     * @param software
     * @param cfUtente
     * @param civico
     * @param indirizzo
     * @param numeroIstanza
     * @param numeroProtocollo
     * @param stato
     * @return
     */
    @GET
    @Path("alias/{alias}/software/{software}/istanze")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response ricercaIstanzePubblica(@PathParam("alias") String alias, @PathParam("software") String software,
	    @FormParam("cfUtente") String cfUtente, @FormParam("civico") String civico, @FormParam("indirizzo") String indirizzo,
	    @FormParam("numeroIstanza") String numeroIstanza, @FormParam("numeroProtocollo") String numeroProtocollo,
	    @FormParam("stato") String stato, @FormParam("dallaData") String dalladata, @FormParam("allaData") String alladata,
	    @FormParam("comune") String comune, @FormParam("referente") String referente, @FormParam("annoProtocollo") String annoProtocollo,
	    @FormParam("codiceStradario") String codiceStradario, @FormParam("tipoCatasto") String tipoCatasto, @FormParam("foglio") String foglio,
	    @FormParam("particella") String particella, @FormParam("sub") String sub, @FormParam("firstResult") Integer firstResult,
	    @FormParam("maxResults") Integer maxResults) {

	setORMHelper(alias, software);
	if (null == firstResult) {
	    firstResult = 0;
	}
	if (null == maxResults) {
	    maxResults = 150;
	}
	Integer annoProtocolloInt = null;
	if (StringUtils.isNotBlank(annoProtocollo) && Utilities.isInteger(annoProtocollo)) {
	    annoProtocolloInt = Integer.parseInt(annoProtocollo);
	}
	Integer codiceStradarioInt = null;
	if (StringUtils.isNotBlank(codiceStradario) && Utilities.isInteger(codiceStradario)) {
	    codiceStradarioInt = Integer.parseInt(codiceStradario);
	}
	EsitoChiamataLista<IstanzaLista> listaIstanze = stcMobileCompatibilityService.findListaIstanzeByParams(cfUtente, civico, indirizzo,
		numeroIstanza, numeroProtocollo, stato, dalladata, alladata, comune, referente, annoProtocolloInt, codiceStradarioInt, tipoCatasto,
		foglio, particella, sub, firstResult, maxResults);
	Serializer serializer = getSerializer();
	String str = (String) serializer.serialize(listaIstanze);
	return rispostaWs(str, Status.OK);
    }

    /**
     * Restituisce la lista delle istanze corrispondenti ai criteri di ricerca passati in querystring (compatibile con
     * STC_MOBILE_SERVICES
     * 
     * @param alias
     * @param software
     * @param cfUtente
     * @param civico
     * @param indirizzo
     * @param numeroIstanza
     * @param numeroProtocollo
     * @param stato
     * @return
     */
    @GET
    @Path("alias/{alias}/software/{software}/istanze_legacy")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response ricercaIstanzePubblicaLegacy(@PathParam("alias") String alias, @PathParam("software") String software,
	    @FormParam("cfUtente") String cfUtente, @FormParam("civico") String civico, @FormParam("indirizzo") String indirizzo,
	    @FormParam("numeroIstanza") String numeroIstanza, @FormParam("numeroProtocollo") String numeroProtocollo,
	    @FormParam("stato") String stato, @FormParam("dallaData") String dalladata, @FormParam("allaData") String alladata,
	    @FormParam("comune") String comune, @FormParam("referente") String referente, @FormParam("annoProtocollo") String annoProtocollo,
	    @FormParam("codiceStradario") String codiceStradario, @FormParam("tipoCatasto") String tipoCatasto, @FormParam("foglio") String foglio,
	    @FormParam("particella") String particella, @FormParam("sub") String sub, @FormParam("firstResult") Integer firstResult,
	    @FormParam("maxResults") Integer maxResults) {

	setORMHelper(alias, software);
	if (null == firstResult) {
	    firstResult = 0;
	}
	if (null == maxResults) {
	    maxResults = 150;
	}
	Integer annoProtocolloInt = null;
	if (StringUtils.isNotBlank(annoProtocollo) && Utilities.isInteger(annoProtocollo)) {
	    annoProtocolloInt = Integer.parseInt(annoProtocollo);
	}
	Integer codiceStradarioInt = null;
	if (StringUtils.isNotBlank(codiceStradario) && Utilities.isInteger(codiceStradario)) {
	    codiceStradarioInt = Integer.parseInt(codiceStradario);
	}
	EsitoChiamataLista<IstanzaLista> listaIstanze = stcMobileCompatibilityService.findListaIstanzeByParams(cfUtente, civico, indirizzo,
		numeroIstanza, numeroProtocollo, stato, dalladata, alladata, comune, referente, annoProtocolloInt, codiceStradarioInt, tipoCatasto,
		foglio, particella, sub, firstResult, maxResults);
	Serializer serializer = getSerializer();
	String str = (String) serializer.serialize(listaIstanze.getLista());
	List<CodiceDescrizioneBean> cdbs = new ArrayList<CodiceDescrizioneBean>(1);
	cdbs.add(newNVBean(listaIstanze.getNumero_record() + "", "numero_record"));
	return rispostaWs(str, Status.OK, cdbs);
    }

    /**
     * Legge i dati generali dell’istanza con l’id passato
     * 
     * @param alias
     * @param id
     * @return
     */
    @GET
    @Path("alias/{alias}/software/{software}/istanze/{id}/dati-generali")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response leggiDatiGeneraliIstanza(@PathParam("alias") String alias, @PathParam("software") String software, @PathParam("id") String id) {

	setORMHelper(alias, software);
	List<NomeValoreBean> dg = stcMobileCompatibilityService.getDatiGeneraliIstanzaByUid(id);
	Serializer serializer = getSerializer();
	String str = (String) serializer.serialize(dg);
	return rispostaWs(str, Status.OK);
    }

    /**
     * Legge la lista dei soggetti dell’istanza con l’id passato
     * 
     * @param alias
     * @param id
     * @return
     */
    @GET
    @Path("alias/{alias}/software/{software}/istanze/{id}/soggetti")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response leggiSoggettiCollegatiIstanza(@PathParam("alias") String alias, @PathParam("software") String software,
	    @PathParam("id") String id) {

	setORMHelper(alias, software);
	List<SoggettoCollegatoIstanza> movs = stcMobileCompatibilityService.getSoggettiCollegatiPraticaByUId(id);
	Serializer serializer = getSerializer();
	String str = (String) serializer.serialize(movs);
	return rispostaWs(str, Status.OK);
    }

    /**
     * Legge la lista di localizzazioni dell’istanza con l’id passato
     * 
     * @param alias
     * @param id
     * @return
     */
    @GET
    @Path("alias/{alias}/software/{software}/istanze/{id}/localizzazioni")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response leggiLocalizzazioniIstanza(@PathParam("alias") String alias, @PathParam("software") String software, @PathParam("id") String id) {

	setORMHelper(alias, software);
	List<LocalizzazioneIstanza> addrs = stcMobileCompatibilityService.getLocalizzazioniPraticabyUid(id);
	Serializer serializer = getSerializer();
	String str = (String) serializer.serialize(addrs);
	return rispostaWs(str, Status.OK);
    }

    /**
     * Legge la lista di endo dell’istanza
     * 
     * @param alias
     * @param id
     * @return
     */
    @GET
    @Path("alias/{alias}/software/{software}/istanze/{id}/endo")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response leggiEndoIstanza(@PathParam("alias") String alias, @PathParam("software") String software, @PathParam("id") String id) {

	setORMHelper(alias, software);
	List<EndoIstanza> endos = stcMobileCompatibilityService.getEndoPraticaByUid(id);
	Serializer serializer = getSerializer();
	String str = (String) serializer.serialize(endos);
	return rispostaWs(str, Status.OK);
    }

    /**
     * Legge la lista degli oneri dell’istanza
     * 
     * @param alias
     * @param id
     * @return
     */
    @GET
    @Path("alias/{alias}/software/{software}/istanze/{id}/oneri")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response leggiOneriIstanza(@PathParam("alias") String alias, @PathParam("software") String software, @PathParam("id") String id) {

	setORMHelper(alias, software);
	List<OnereIstanza> oneris = stcMobileCompatibilityService.getOneriPraticaByUid(id);
	Serializer serializer = getSerializer();
	String str = (String) serializer.serialize(oneris);
	return rispostaWs(str, Status.OK);
    }

    /**
     * Legge la lista dei movimenti dell’istanza
     * 
     * @param alias
     * @param id
     * @return
     */
    @GET
    @Path("alias/{alias}/software/{software}/istanze/{id}/movimenti")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response leggiMovimentiIstanza(@PathParam("alias") String alias, @PathParam("software") String software, @PathParam("id") String id) {

	setORMHelper(alias, software);
	List<MovimentoIstanza> movs = stcMobileCompatibilityService.getMovimentiPraticaByUid(id);
	Serializer serializer = getSerializer();
	String str = (String) serializer.serialize(movs);
	return rispostaWs(str, Status.OK);
    }

    /**
     * Legge la lista delle autorizzazioni dell’istanza
     * 
     * @param alias
     * @param id
     * @return
     */
    @GET
    @Path("alias/{alias}/software/{software}/istanze/{id}/autorizzazioni")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response leggiAutorizzazioniIstanza(@PathParam("alias") String alias, @PathParam("software") String software, @PathParam("id") String id) {

	setORMHelper(alias, software);
	List<AutorizzazioniConcessioniIstanza> addrs = stcMobileCompatibilityService.getAutorizzazioniConcessioniPraticaByUid(id);
	Serializer serializer = getSerializer();
	String str = (String) serializer.serialize(addrs);
	return rispostaWs(str, Status.OK);
    }

    /**
     * Restituisce un documento contenente le informazioni riguardanti una pratica
     * 
     * @param alias
     * @param software
     * @param guid
     * @param idtemplate
     * @param chiaveMac
     * @return
     */
    @GET
    @Path("{alias}/{software}/{guid}/generatemplate/{idtemplate}/{mac}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generaQRCode(@PathParam("alias") String alias, @PathParam("software") String software, @PathParam("guid") String guid,
	    @PathParam("idtemplate") Integer idtemplate, @PathParam("mac") String chiaveMac) {

	setORMHelper(alias, software);
	ParametriPerPDFHelper p = new ParametriPerPDFHelper(alias, software, guid, idtemplate, chiaveMac);
	Serializer serializer = getSerializer();
	try {
	    OggettoPdfBean resp = stampaPDFDaQRCodeService.stampaPDFDaQr(p);
	    if (resp.getOggetto() != null) {
		return rispostaWsFile(resp.getOggetto().getOggetto(), Status.OK, resp.getOggetto().getNomefile());
	    } else {
		CodiceDescrizioneBean b = new CodiceDescrizioneBean();
		b.setCodice("403");
		b.setDescrizione("Parametri della richiesta non corretti");
		String output = (String) serializer.serialize(b);
		return rispostaWs(output, Status.FORBIDDEN);
	    }
	} catch (Exception e) {
	    CodiceDescrizioneBean b = new CodiceDescrizioneBean();
	    b.setCodice("404");
	    b.setDescrizione(e.getMessage());
	    String output = (String) serializer.serialize(b);
	    return rispostaWs(output, Status.INTERNAL_SERVER_ERROR);
	}
    }
}
