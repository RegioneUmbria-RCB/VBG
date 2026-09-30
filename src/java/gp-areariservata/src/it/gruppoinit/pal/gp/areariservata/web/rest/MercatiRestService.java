package it.gruppoinit.pal.gp.areariservata.web.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.paevolution.ws.pagamenti_types.AttivaSessionePagamentoResponseType;
import com.paevolution.ws.pagamenti_types.ElencoDocumentiEsitoType;
import com.paevolution.ws.pagamenti_types.EsitoDocumentoPosizioneDebitoriaType;
import com.paevolution.ws.pagamenti_types.FormParamType;
import com.paevolution.ws.pagamenti_types.FormParametersType;
import com.paevolution.ws.pagamenti_types.InfoConnettoreType;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BollettazioneBean;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTPrenot;
import it.gruppoinit.pal.gp.core.domain.PageResult;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeDTO;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniDTO;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AnagraferestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioneRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniFrontRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniMercatiPresenzeStoricoRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniPresenzeStoricoRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.CodiceNumericoDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafeRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DocumentiRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoFaseRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoPosteggioRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoSpuntistaRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InfoAutorizzazioneRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ListaPagamentiBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.MappaMercatoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.MercatiRestConIdGiornataBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioMercatoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PresenzeStoricoGiornataRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ResponsabileRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.AppAmbulantiAutorizzazioniResponse;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.AppAmbulantiStampaPDFResponse;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.RuoloAutorizzazioneEnum;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.AttivaSessionePagamentoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.AttivaSessionePagamentoFormParamsResponseBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.AttivaSessionePagamentoResponseBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloBollettazioneService;
import it.gruppoinit.pal.gp.core.features.common.bean.BaseEsitoOperazione.ESITO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.FiltroPagamentoEnum;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.MercatiAppService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeStoricoService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeTPrenotService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniConcessioniPresenzeRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniConcessioniRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.GiorniMercatoPresenzeRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.MercatiPresenzeStoricoRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.MercatiRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatiDaEffettuareHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatiHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoGiornoRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelperV2;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelperV2Paged;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PosteggioLiberoHelper;
import it.gruppoinit.pal.gp.core.service.helper.StradarioDTO;
import it.gruppoinit.pal.gp.core.utils.SecureIdUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import net.sf.sojo.core.filter.ClassPropertyFilter;
import net.sf.sojo.core.filter.ClassPropertyFilterHandler;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

@Path("/servizi-mercati/")
public class MercatiRestService extends BaseRestService {

    private static final Logger log = LoggerFactory.getLogger(MercatiRestService.class.getName());
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private MercatiAppService mercatiAppService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private MercatipresenzeTPrenotService mercatipresenzeTPrenotService;
    @Autowired
    private MercatipresenzeStoricoService mercatipresenzeStoricoService;
    @Context
    private MessageContext context;
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService;
    @Autowired
    private CalcoloBollettazioneService calcoloBollettazioneService;

    @GET
    @Path("/mercati/presenze/elenco")
    @Descriptions({ @Description(value = "Ottiene la lista delle presenze sui mercati associate all'utente loggato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getElencoPresenzeUtente(@HeaderParam(AUTHORIZATION) String auth) {

	try {
	    authenticate(auth);
	    Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    List<AutorizzazioniPresenzeStoricoRestHelper> result = mercatipresenzeStoricoService.findSommaDellePresenzeSpuntisti(r);
	    Serializer serializer = getSerializer();
	    String output = (String) serializer.serialize(result);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(output, Status.OK);
	} catch (Exception e1) {
	    log.error("getElencoPresenzeUtente", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/mercati/presenze/conteggio")
    @Descriptions({ @Description(value = "Ottiene la lista delle presenze sui mercati associate all'utente loggato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getConteggioPresenzeUtente(@HeaderParam(AUTHORIZATION) String auth) throws Exception {

	try {
	    authenticate(auth);
	    Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    Integer conteggio = mercatipresenzeStoricoService.findConteggioUltimoAnnoDellePresenzeSpuntisti(r);
	    String output = "{\n\"conteggio\" : " + conteggio.intValue() + "\n}";
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(output, Status.OK);
	} catch (Exception e1) {
	    log.error("getConteggioPresenzeUtente", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/mercati/v2/autorizzazioni/elenco")
    @Descriptions({ @Description(value = "Ottiene la lista delle autorizzazioni associate all'utente loggato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getElencoAutorizzazioniUtenteV2(@HeaderParam(AUTHORIZATION) String auth) {

	try {
	    authenticate(auth);
	    Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    AppAmbulantiAutorizzazioniResponse result = mercatiAppService.findAutorizzazioniAppAmbulantiByUtente(r);
	    String ret = Utilities.marshalJsonObject(result, AppAmbulantiAutorizzazioniResponse.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(ret, Status.OK);
	} catch (Exception e1) {
	    log.error("getElencoAutorizzazioniUtenteV2", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/mercati/v2/autorizzazioni/elenco/stampa/{cf}")
    @Descriptions({ @Description(value = "La stampa del PDF", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response stampaElencoAutorizzazioniUtenteV2(@HeaderParam(AUTHORIZATION) String auth, @PathParam("cf") String cf) {

	try {
	    authenticate(auth);
	    Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    AppAmbulantiStampaPDFResponse result = mercatiAppService.updateStampaAutorizzazioniAppAmbulantiByUtente(cf, r.getId().getCodice());
	    if (result.getEsito().getEsito().equals(ESITO.ERROR.name())) {
		String errore = "Si sono verificati i seguenti errori per l'operazione: " + result.getEsito().restituisciErroriComeString("\n-");
		return rispostaWs(errore, Status.INTERNAL_SERVER_ERROR);
	    }
	    List<CodiceDescrizioneBean> headerAggiuntivi = new ArrayList<CodiceDescrizioneBean>();
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice("Content-Disposition");
	    cdb.setDescrizione("attachment; filename=\"" + result.getNomeFile() + "\"");
	    headerAggiuntivi.add(cdb);
	    cdb = new CodiceDescrizioneBean();
	    cdb.setCodice("Content-Type");
	    cdb.setDescrizione(contenttypesService.findMimeTypeByFileName(result.getNomeFile()));
	    headerAggiuntivi.add(cdb);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(result.getContenuto(), Status.OK, headerAggiuntivi);
	} catch (Exception e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/mercati/manifestazioni")
    @Descriptions({
	    @Description(value = "Effettua una ricerca sui mercati/fiere configurati. Per delimitare la ricerca sul nome impostare un parametro con nome filtroDescrizione", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response listaManifestazioni(@HeaderParam(AUTHORIZATION) String auth, @FormParam("filtroDescrizione") String filtroDescrizione) {

	try {
	    authenticate(auth);
	    Serializer serializer = getSerializer();
	    CheckTokenResponse ti = getTokenInfo(ORMHelper.getToken());
	    checkrequest(serializer, ti);
	    setORMHelper(ti.getTokenInfo().getAlias(), ORMHelper.getSoftware());
	    List<MercatiRestHelper> auts = mercatiService.findAttiviByDescrizione(filtroDescrizione, null, null);
	    String str = (String) serializer.serialize(auts);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(str, Status.OK);
	} catch (Exception e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/mercati/manifestazioni-odierne")
    @Descriptions({
	    @Description(value = "Effettua una ricerca sui mercati/fiere configurati e per le quali esiste un giorno nella data odierna. Per delimitare la ricerca sul nome impostare un parametro con nome filtroDescrizione", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response listaManifestazioniOdierne(@HeaderParam(AUTHORIZATION) String auth, @FormParam("filtroDescrizione") String filtroDescrizione) {

	try {
	    authenticate(auth);
	    Serializer serializer = getSerializer();
	    CheckTokenResponse ti = getTokenInfo(ORMHelper.getToken());
	    checkrequest(serializer, ti);
	    setORMHelper(ti.getTokenInfo().getAlias(), ORMHelper.getSoftware());
	    List<MercatiRestConIdGiornataBean> auts = mercatiService.findAttiviOggi(filtroDescrizione);
	    String str = (String) serializer.serialize(auts);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(str, Status.OK);
	} catch (Exception e1) {
	    log.error("listaManifestazioniOdierne", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/mercati/mappa-mercato/{idGiornata}")
    @Descriptions({
	    @Description(value = "Il metodo ritorna un base64 immagine ed eventuali altre info (es coordinate per posteggio)", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response mappaMercato(@HeaderParam(AUTHORIZATION) String auth, @PathParam("idGiornata") Integer idGiornata) {

	try {
	    authenticate(auth);
	    Serializer serialize = getSerializer();
	    MappaMercatoBean result = mercatiAppService.getMappaMercato(idGiornata);
	    String output = (String) serialize.serialize(result);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(output, Status.OK);
	} catch (Exception e1) {
	    log.error("mappaMercato", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/mercati/posteggiliberi/{codiceGiornata}")
    @Descriptions({ @Description(value = "Cerca i posteggi non occupati per la giornata di mercato.", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response listaPosteggi(@HeaderParam(AUTHORIZATION) String auth, @PathParam("codiceGiornata") Integer codiceGiornata) {

	try {
	    authenticate(auth);
	    Serializer serializer = getSerializer();
	    CheckTokenResponse ti = getTokenInfo(ORMHelper.getToken());
	    checkrequest(serializer, ti);
	    setORMHelper(ti.getTokenInfo().getAlias(), ORMHelper.getSoftware());
	    List<PosteggioLiberoHelper> ret = mercatipresenzeDService.findListaPosteggiLiberi(codiceGiornata);
	    String str = (String) serializer.serialize(ret);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(str, Status.OK);
	} catch (Exception e1) {
	    log.error("listaPosteggi", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/mercati/salvainteresseposteggio/{codiceGiornata}/{idPosteggio}")
    @Descriptions({ @Description(value = "Il metodo permette di salvare la preferenza per un posteggio.", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response salvapreferenza(@HeaderParam(AUTHORIZATION) String auth, @PathParam("codiceGiornata") Integer codiceGiornata,
	    @PathParam("idPosteggio") Integer idPosteggio, @FormParam("annotazioni") String annotazioni) {

	try {
	    authenticate(auth);
	    Serializer serializer = getSerializer();
	    CheckTokenResponse ti = getTokenInfo(ORMHelper.getToken());
	    checkrequest(serializer, ti);
	    setORMHelper(ti.getTokenInfo().getAlias(), ORMHelper.getSoftware());
	    String cf = ti.getTokenInfo().getUserid();
	    Anagrafe rich = null;
	    List<Anagrafe> anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_FISICA, true);
	    if (anags.isEmpty()) {
		anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_GIURIDICA, true);
	    }
	    if (anags.isEmpty()) {
		throw new RuntimeException("Richiedente non trovato");
	    }
	    rich = anags.get(0);
	    List<MercatipresenzeTPrenot> prefs = mercatipresenzeTPrenotService.findByMercatipresenzeTAndPosteggioAndAnagrafe(codiceGiornata,
		    idPosteggio, rich.getId().getCodice());
	    CodiceDescrizioneBean b = newNVBean("Preferenza salvata", "000");
	    if (prefs.isEmpty()) {
		MercatipresenzeT mercatipresenzeT = mercatipresenzeTService.findById(new PkId(codiceGiornata));
		List<MercatipresenzeDDTO> listaPosteggi = mercatipresenzeDService.findByMercatipresenzaT(codiceGiornata);
		boolean trovato = false;
		for (MercatipresenzeDDTO mpd : listaPosteggi) {
		    if (mpd.getPosteggio() != null && mpd.getPosteggio().getId() != null && mpd.getPosteggio().getId().getCodice() != null) {
			if (idPosteggio.equals(mpd.getPosteggio().getId().getCodice())) {
			    trovato = true;
			    break;
			}
		    }
		}
		if (!trovato) {
		    b = newNVBean("Errore nella validazione dei dati. Riprovare piu' tardi", "997");
		} else {
		    MercatiD mercatiD = mercatiDService.findById(new PkId(idPosteggio));
		    MercatipresenzeTPrenot entity = new MercatipresenzeTPrenot();
		    entity.setAnagrafe(rich);
		    entity.setMercatiD(mercatiD);
		    entity.setMercatipresenzeT(mercatipresenzeT);
		    entity.setNote(annotazioni);
		    try {
			mercatipresenzeTPrenotService.insert(entity);
		    } catch (Exception e) {
			b = newNVBean("Si e' verificato un errore nel salvataggio. Riprovare piu' tardi", "998");
		    }
		}
	    } else {
		b = newNVBean("La preferenza e' già stata inserita per il posteggio", "999");
	    }
	    String str = (String) serializer.serialize(b);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(str, Status.OK);
	} catch (Exception e1) {
	    log.error("salvapreferenza", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/mercati/eliminainteresseposteggio/{codiceGiornata}/{idPosteggio}")
    @Descriptions({ @Description(value = "Il metodo permette di eliminare la preferenza per un posteggio", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response rimuovipreferenza(@HeaderParam(AUTHORIZATION) String auth, @PathParam("codiceGiornata") Integer codiceGiornata,
	    @PathParam("idPosteggio") Integer idPosteggio) {

	try {
	    authenticate(auth);
	    Serializer serializer = getSerializer();
	    CheckTokenResponse ti = getTokenInfo(ORMHelper.getToken());
	    checkrequest(serializer, ti);
	    setORMHelper(ti.getTokenInfo().getAlias(), ORMHelper.getSoftware());
	    String cf = ti.getTokenInfo().getUserid();
	    Anagrafe rich = null;
	    List<Anagrafe> anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_FISICA, true);
	    if (anags.isEmpty()) {
		anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_GIURIDICA, true);
	    }
	    if (anags.isEmpty()) {
		throw new RuntimeException("Richiedente non trovato");
	    }
	    rich = anags.get(0);
	    List<MercatipresenzeTPrenot> prefs = mercatipresenzeTPrenotService.findByMercatipresenzeTAndPosteggioAndAnagrafe(codiceGiornata,
		    idPosteggio, rich.getId().getCodice());
	    CodiceDescrizioneBean b = newNVBean("Preferenze eliminate", "000");
	    if (prefs.isEmpty()) {
		b = newNVBean("Preferenza non trovata", "999");
	    } else {
		for (MercatipresenzeTPrenot mercatipresenzeTPrenot : prefs) {
		    mercatipresenzeTPrenotService.delete(mercatipresenzeTPrenot);
		}
	    }
	    String str = (String) serializer.serialize(b);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(str, Status.OK);
	} catch (Exception e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/mercati/interesseposteggio/{codiceGiornata}/{idPosteggio}")
    @Descriptions({ @Description(value = "Il metodo permette di visualizzare le preferenze per un posteggio", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response visualizzapreferenze(@HeaderParam(AUTHORIZATION) String auth, @PathParam("codiceGiornata") Integer codiceGiornata,
	    @PathParam("idPosteggio") Integer idPosteggio) {

	try {
	    authenticate(auth);
	    Serializer serializer = getSerializer();
	    CheckTokenResponse ti = getTokenInfo(ORMHelper.getToken());
	    checkrequest(serializer, ti);
	    setORMHelper(ti.getTokenInfo().getAlias(), ORMHelper.getSoftware());
	    String cf = ti.getTokenInfo().getUserid();
	    Anagrafe rich = null;
	    List<Anagrafe> anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_FISICA, true);
	    if (anags.isEmpty()) {
		anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_GIURIDICA, true);
	    }
	    if (anags.isEmpty()) {
		throw new RuntimeException("Richiedente non trovato");
	    }
	    rich = anags.get(0);
	    List<CodiceDescrizioneBean> listaPreferenze = new ArrayList<CodiceDescrizioneBean>();
	    List<MercatipresenzeTPrenot> prefs = mercatipresenzeTPrenotService.findByMercatipresenzeTAndPosteggioAndAnagrafe(codiceGiornata,
		    idPosteggio, rich.getId().getCodice());
	    for (MercatipresenzeTPrenot mp : prefs) {
		listaPreferenze.add(newNVBean(mp.getNote(), mp.getId().getCodice().toString()));
	    }
	    String str = (String) serializer.serialize(listaPreferenze);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(str, Status.OK);
	} catch (Exception e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @POST
    @Path("/mercati/pagamenti/{alias}/{software}/ricerca-per-riferimenti-aut")
    @Descriptions({
	    @Description(value = "Ottiene la lista dei pagamenti dati i riferimenti numero autorizzazione / comune", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getElencoPagamentiPerRiferimenti(@PathParam("alias") String alias, @PathParam("software") String software, String json)
	    throws Exception {

	setORMHelper(alias, software);
	CodiceDescrizioneBean bean = (CodiceDescrizioneBean) getSerializer().deserialize(json, CodiceDescrizioneBean.class);
	String numeroAutorizzazione = bean.getCodice();
	String codiceComune = bean.getDescrizione();
	List<PagamentiMercatoRestHelper> result = nodoPagamentiService.getPagamentoByAutorizzazioneComune(this.mercatiService, numeroAutorizzazione,
		codiceComune);
	Serializer serializer = getSerializer();
	String output = (String) serializer.serialize(result);
	ORMHelper.destroyORMHelper();
	return rispostaWs(output, Status.OK);
    }

    @POST
    @Path("/mercati/verifica-autorizzazioni/{alias}/{software}/riferimenti-aut")
    @Descriptions({
	    @Description(value = "Ottiene la lista dei pagamenti dati i riferimenti numero autorizzazione / comune", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getVerificaAutorizzazionePerRiferimenti(@PathParam("alias") String alias, @PathParam("software") String software, String json)
	    throws Exception {

	setORMHelper(alias, software);
	CodiceDescrizioneBean bean = (CodiceDescrizioneBean) getSerializer().deserialize(json, CodiceDescrizioneBean.class);
	String numeroAutorizzazione = bean.getCodice();
	String codiceComune = bean.getDescrizione();
	IdentificativoDescrizioneBean result = new IdentificativoDescrizioneBean();
	Autorizzazioni aut = autorizzazioniService.findByNumeroAndComune(numeroAutorizzazione, codiceComune);
	Status status = Status.OK;
	if (aut != null) {
	    result.setId(aut.getId().getCodice());
	    result.setDescrizione(aut.getTransientEstremiAut());
	} else {
	    status = Status.INTERNAL_SERVER_ERROR;
	    result.setDescrizione(
		    "Autorizzazione non trovata per i riferimenti numero: " + numeroAutorizzazione + " e identificativo Comune: " + codiceComune);
	}
	Serializer serializer = getSerializer();
	String output = (String) serializer.serialize(result);
	ORMHelper.destroyORMHelper();
	return rispostaWs(output, status);
    }

    @POST
    @Path("/mercati/dettaglioutente/update")
    @Descriptions({
	    @Description(value = "Il metodo aggiorna le informazioni num telefono / email dell'utente collegato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateinfoutentedettaglio(@HeaderParam(AUTHORIZATION) String auth, String json) {

	try {
	    authenticate(auth);
	    Serializer serializer = getSerializer();
	    CheckTokenResponse ti = getTokenInfo(ORMHelper.getToken());
	    String cf = ti.getTokenInfo().getUserid();
	    DettaglioAnagrafeRestBean bean = (DettaglioAnagrafeRestBean) getSerializer().deserialize(json, DettaglioAnagrafeRestBean.class);
	    String cfBean = "";
	    if (bean.getPersona_fisica() != null && StringUtils.isNotBlank(bean.getPersona_fisica().getCodice_fiscale())) {
		cfBean = bean.getPersona_fisica().getCodice_fiscale();
	    } else if (bean.getPersona_giuridica() != null && StringUtils.isNotBlank(bean.getPersona_giuridica().getCodice_fiscale())) {
		cfBean = bean.getPersona_giuridica().getCodice_fiscale();
	    }
	    if (StringUtils.isBlank(cfBean)) {
		throw new RuntimeException();
	    }
	    if (!cf.equalsIgnoreCase(bean.getPersona_fisica().getCodice_fiscale())) {
		throw new RuntimeException();
	    }
	    CodiceDescrizioneBean esito = mercatiAppService.updateInfoUtente(bean);
	    Status status = Status.OK;
	    if (!esito.getCodice().equalsIgnoreCase("200")) {
		status = Status.INTERNAL_SERVER_ERROR;
	    }
	    String str = (String) serializer.serialize(esito);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(str, status);
	} catch (Exception e1) {
	    log.error("updateinfoutentedettaglio", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/mercati/pagamenti/utente")
    @Descriptions({ @Description(value = "Ottiene la lista dei pagamenti di un utente collegato", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getElencoPagamentiUtente(@HeaderParam(AUTHORIZATION) String auth) {

	try {
	    authenticate(auth);
	    Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    RuoloAutorizzazioneEnum role = RuoloAutorizzazioneEnum.TitolareEOccupante;
	    FiltroPagamentoEnum statePagamento = FiltroPagamentoEnum.TUTTE;
	    GregorianCalendar consideraIPagamentiDallaData = Utilities.getDate("01/01/2010");
	    List<PagamentiMercatoRestHelper> result = nodoPagamentiService.getPagamentiAttiviByUtente(this.mercatiService, r, false, true, role,
		    statePagamento, consideraIPagamentiDallaData.getTime(), false);
	    Serializer serializer = getSerializer();
	    String output = (String) serializer.serialize(result);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(output, Status.OK);
	} catch (Exception e1) {
	    log.error("getElencoPagamentiUtente", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/mercati/v2/pagamenti/utente")
    @Descriptions({ @Description(value = "Ottiene la lista dei pagamenti di un utente collegato", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getElencoPagamentiUtenteV2(@HeaderParam(AUTHORIZATION) String auth, @QueryParam("ruolo") String ruolo,
	    @QueryParam("statoPagamento") String statoPagamento) {

	try {
	    authenticate(auth);
	    boolean verificaStatoPosizioni = false;
	    Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    RuoloAutorizzazioneEnum role = defaultRuolo(ruolo);
	    FiltroPagamentoEnum statePagamento = defaultPagamento(statoPagamento);
	    Date consideraIPagamentiDallaData = getFiltroDataRicercaPagamentiAmbulanti();
	    List<PagamentiMercatoPosizDebRestHelperV2> result = nodoPagamentiService.getPagamentiAttiviByUtenteV2(this.mercatiService, r,
		    verificaStatoPosizioni, true, role, statePagamento, consideraIPagamentiDallaData, false);
	    Serializer serializer = getSerializer();
	    String output = (String) serializer.serialize(result);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(output, Status.OK);
	} catch (Exception e1) {
	    log.error("getElencoPagamentiUtenteV2", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    //TODO: modifica API 1 -> paginazione -> non modifichiamo il DAO
    @GET
    @Path("/mercati/v2/pagamenti/utente/posteggi")
    @Descriptions({ @Description(value = "Ottiene la lista dei pagamenti di un utente collegato", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getElencoPagamentiUtenteV2Paged(@HeaderParam(AUTHORIZATION) String auth, @QueryParam("ruolo") String ruolo,
	    @QueryParam("statoPagamento") String statoPagamento, @QueryParam("pagina") int pagina) {

	try {
	    authenticate(auth);
	    log.debug("getElencoPagamentiUtenteV2Paged ruolo: {}, statoPagamento: {}", ruolo, statoPagamento);
	    boolean verificaStatoPosizioni = false;
	    Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    RuoloAutorizzazioneEnum role = defaultRuolo(ruolo);
	    FiltroPagamentoEnum statePagamento = defaultPagamento(statoPagamento);
	    log.debug("getElencoPagamentiUtenteV2Paged ruolo calcolato: {}, statoPagamentoCalcolato: {}", role, statePagamento);
	    Date consideraIPagamentiDallaData = getFiltroDataRicercaPagamentiAmbulanti();
	    log.debug("getElencoPagamentiUtenteV2Paged consideraIPagamentiDallaData: {}, pagina: {}", consideraIPagamentiDallaData, pagina);
	    PagamentiMercatoPosizDebRestHelperV2Paged result = nodoPagamentiService.getPagamentiAttiviByUtenteV2Paged(this.mercatiService, r,
		    verificaStatoPosizioni, true, role, statePagamento, consideraIPagamentiDallaData, false, pagina, 20);
	    String output = (String) getSerializer().serialize(result);
	    log.debug("getElencoPagamentiUtenteV2Paged risposta {}", output);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(output, Status.OK);
	} catch (Exception e1) {
	    log.error("getElencoPagamentiUtenteV2", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/mercati/v2/pagamenti/utente/bollettazione")
    @Descriptions({ @Description(value = "Ottiene la lista delle bollettazioni", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getBollettazionePaged(@HeaderParam(AUTHORIZATION) String auth, @QueryParam("statoPagamento") String statoPagamento,
	    @QueryParam("pagina") int pagina) {

	try {
	    authenticate(auth);
	    Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    List<AutorizzazioniFrontRestBean> autorizzazioni = mercatiAppService.findAutorizzazioniHelperByUtente(r, true, null, false);
	    Integer[] autorizzazioniIds = new Integer[autorizzazioni.size()];
	    int i = 0;
	    for (AutorizzazioniFrontRestBean afb : autorizzazioni) {
		autorizzazioniIds[i] = afb.getId();
		i++;
	    }
	    FiltroPagamentoEnum statePagamento = defaultPagamento(statoPagamento);
	    PageResult<BollettazioneBean> result = nodoPagamentiService.getPosizioneDebitorieBollettazione(autorizzazioniIds, true, statePagamento,
		    pagina, 20);
	    for (BollettazioneBean boll : result.getItems()) {
		boll.setIdBollettino(nodoPagamentiService.codificaIdPagamento(boll.getIdPagamento(), statoPagamento));
		boolean effettuato = nodoPagamentiService.isPagamentoEffettuato(boll.getIdPagamento());
		boll.setEffettuato(effettuato);
		if (effettuato) {
		    String[] info = boll.getIdBollettino().split("\\.");
		    boll.setIdRicevuta(nodoPagamentiService.codificaIdPagamento(boll.getIdPagamento(), info[1]));
		}
		String identificativoBollettazione = SecureIdUtils.encode(boll.getIdBollettazione(), Utilities.getToday(false),
			String.valueOf(boll.getCodiceAnagrafe()));
		String url = "/servizi-mercati/bollettazione/" + ORMHelper.getIdcomuneAlias() + "/" + ORMHelper.getSoftware() +
			     "/dettaglio/pdf/" + identificativoBollettazione;
		log.debug("getBollettazionePaged url {}", url);
		boll.setUrlDettaglio(url);
	    }
	    log.debug("getBollettazionePaged: posizioniDebitorie  {}", result);
	    String output = Utilities.marshalJsonObject(result, PageResult.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(output, Status.OK);
	} catch (Exception e1) {
	    log.error("getBollettazionePaged", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    private Date getFiltroDataRicercaPagamentiAmbulanti() {

	return comportamentiMercatiService.dataInizioRicercaPagamentiAppAmbulanti();
    }

    @GET
    @Path("/mercati/old/pagamenti/utente/aggiorna")
    @Descriptions({ @Description(value = "Ottiene la lista dei pagamenti di un utente collegato", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getElencoPagamentiUtenteV2AggiornaOld(@HeaderParam(AUTHORIZATION) String auth, @QueryParam("ruolo") String ruolo,
	    @QueryParam("statoPagamento") String statoPagamento) {

	try {
	    authenticate(auth);
	    boolean verificaStatoPosizioni = true;
	    Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    RuoloAutorizzazioneEnum role = defaultRuolo(ruolo);
	    FiltroPagamentoEnum statePagamento = defaultPagamento(statoPagamento);
	    Date consideraIPagamentiDallaData = getFiltroDataRicercaPagamentiAmbulanti();
	    List<PagamentiMercatoPosizDebRestHelperV2> result = nodoPagamentiService.getPagamentiAttiviByUtenteV2(this.mercatiService, r,
		    verificaStatoPosizioni, true, role, statePagamento, consideraIPagamentiDallaData, false);
	    Serializer serializer = getSerializer();
	    String output = (String) serializer.serialize(result);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(output, Status.OK);
	} catch (Exception e1) {
	    log.error("getElencoPagamentiUtenteV2Aggiorna", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @POST
    @Path("/mercati/v2/pagamenti/utente/aggiorna")
    @Descriptions({ @Description(value = "Ottiene la lista dei pagamenti di un utente collegato", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response aggiornaPagamenti(@HeaderParam(AUTHORIZATION) String auth, String json) {

	try {
	    authenticate(auth);
	    ListaPagamentiBean lista = (ListaPagamentiBean) getSerializer().deserialize(json, ListaPagamentiBean.class);
	    for (Integer idDettPos : lista.asIntegerArray()) {
		try {
		    nodoPagamentiService.aggiornaStatoPagamentoByIdDettPosizioneDebitoria(idDettPos);
		} catch (Exception e1) {
		    log.error("aggiornaPagamenti posizione debitoria " + idDettPos, e1);
		}
	    }
	    Serializer serializer = getSerializer();
	    String output = (String) serializer.serialize(new CodiceDescrizioneBean("200", null));
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(output, Status.OK);
	} catch (Exception e1) {
	    log.error("aggiornaPagamenti", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    private FiltroPagamentoEnum defaultPagamento(String statoPagamento) {

	if (StringUtils.isBlank(statoPagamento)) {
	    return FiltroPagamentoEnum.DA_PAGARE;
	}
	return FiltroPagamentoEnum.valueOf(statoPagamento);
    }

    private RuoloAutorizzazioneEnum defaultRuolo(String ruolo) {

	if (StringUtils.isBlank(ruolo)) {
	    return RuoloAutorizzazioneEnum.TitolareEOccupante;
	}
	return RuoloAutorizzazioneEnum.valueOf(ruolo);
    }

    @GET
    @Path("/mercati/pagamenti/{alias}/{software}/utente/ricevute/{idRicevutaCodificata}")
    @Descriptions({ @Description(value = "Scarica la ricevuta PDF se prevista dal nodo dei pagamenti", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getRicevutaPDF(@PathParam("alias") String alias, @PathParam("software") String software,
	    @PathParam("idRicevutaCodificata") String idRicevutaCodificata) {

	Serializer serializer = getSerializer();
	try {
	    setORMHelper(alias, software);
	    if (StringUtils.isBlank(idRicevutaCodificata)) {
		log.error("getRicevutaPDF: Non è stato passato il riferimento della ricevuta da scaricare");
		throw new RuntimeException("Non è stato passato il riferimento della ricevuta da scaricare");
	    }
	    log.debug("idRicevutaCodificata {}", idRicevutaCodificata);
	    String[] info = idRicevutaCodificata.split("\\.");
	    int idPosizioneDebitoria = Integer.parseInt(info[0]);
	    String codiceComune = info[1];
	    log.debug("idPosizioneDebitoria: {}, codiceComune: {}", idRicevutaCodificata, codiceComune);
	    String newHash = SecureIdUtils.encode(idPosizioneDebitoria, Utilities.getToday(false), codiceComune);
	    log.debug("newHash: {}", newHash);
	    if (!idRicevutaCodificata.equalsIgnoreCase(newHash)) {
		log.error("Non è stato fornito l'hash corretto per la ricevuta da scaricare");
		throw new RuntimeException("Non è stato fornito l'hash corretto per la ricevuta da scaricare");
	    }
	    log.debug("chiamo infoconnettore: {}", idRicevutaCodificata);
	    InfoConnettoreType infoConnettore = this.nodoPagamentiService.getInfoConnettore(codiceComune);
	    if (!infoConnettore.isSupportaDownloadRicevuta()) {
		log.error("L'attuale PSP integrato non supporta il download della ricevuta PDF");
		throw new RuntimeException("L'attuale PSP integrato non supporta il download della ricevuta PDF");
	    }
	    log.debug("chiamo scaricaRicevutaTelematica: {}", idRicevutaCodificata);
	    ElencoDocumentiEsitoType esito = this.nodoPagamentiService.scaricaRicevutaTelematica(idPosizioneDebitoria);
	    if (esito == null || esito.getEsitoPosizione() == null || esito.getEsitoPosizione().isEmpty()) {
		return null;
	    }
	    if (esito.getEsitoPosizione().size() != 1) {
		log.error("Impossibile individuare univocamente la ricevuta richiesta {}", idRicevutaCodificata);
		throw new RuntimeException("Impossibile individuare univocamente la ricevuta richiesta");
	    }
	    EsitoDocumentoPosizioneDebitoriaType posizione = esito.getEsitoPosizione().get(0);
	    List<CodiceDescrizioneBean> headerAggiuntivi = new ArrayList<CodiceDescrizioneBean>();
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice("Content-Disposition");
	    cdb.setDescrizione("attachment; filename=\"" + posizione.getNomeDocumento() + "\"");
	    log.debug("torno il documento: {}", cdb);
	    headerAggiuntivi.add(cdb);
	    cdb = new CodiceDescrizioneBean();
	    cdb.setCodice("Content-Type");
	    cdb.setDescrizione("application/pdf");
	    headerAggiuntivi.add(cdb);
	    return rispostaWs(posizione.getDocumento().getInputStream(), Status.OK, headerAggiuntivi);
	} catch (Exception e) {
	    log.error("getRicevutaPDF", e);
	    CodiceDescrizioneBean b = new CodiceDescrizioneBean();
	    b.setCodice("500");
	    b.setDescrizione(e.getMessage());
	    String output = (String) serializer.serialize(b);
	    return rispostaWs(output, Status.INTERNAL_SERVER_ERROR);
	}
    }

    @GET
    @Path("/mercati/pagamenti/{alias}/{software}/utente/bollettini/{idBollettinoCodificato}")
    @Descriptions({ @Description(value = "Scarica il modell 3 PDF se prevista dal nodo dei pagamenti", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getBollettinoPDF(@PathParam("alias") String alias, @PathParam("software") String software,
	    @PathParam("idBollettinoCodificato") String idBollettinoCodificato) {

	try {
	    setORMHelper(alias, software);
	    if (StringUtils.isBlank(idBollettinoCodificato)) {
		throw new RuntimeException("Non è stato passato il riferimento del bollettino da scaricare");
	    }
	    String[] info = idBollettinoCodificato.split("\\.");
	    int idPosizioneDebitoria = Integer.parseInt(info[0]);
	    String codiceComune = info[1];
	    String newHash = SecureIdUtils.encode(idPosizioneDebitoria, Utilities.getToday(false), codiceComune);
	    if (!idBollettinoCodificato.equalsIgnoreCase(newHash)) {
		throw new RuntimeException("Non è stato fornito l'hash corretto per il bollettino da scaricare");
	    }
	    InfoConnettoreType infoConnettore = this.nodoPagamentiService.getInfoConnettore(codiceComune);
	    if (!infoConnettore.isSupportaInvioAvviso()) {
		throw new RuntimeException("L'attuale PSP integrato non supporta il download del bollettino PDF");
	    }
	    ElencoDocumentiEsitoType esito = this.nodoPagamentiService.inviaAvviso(idPosizioneDebitoria);
	    if (esito == null || esito.getEsitoPosizione() == null || esito.getEsitoPosizione().isEmpty()) {
		return null;
	    }
	    if (esito.getEsitoPosizione().size() != 1) {
		throw new RuntimeException("Impossibile individuare univocamente il bollettino richiesto");
	    }
	    EsitoDocumentoPosizioneDebitoriaType posizione = esito.getEsitoPosizione().get(0);
	    List<CodiceDescrizioneBean> headerAggiuntivi = new ArrayList<CodiceDescrizioneBean>();
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice("Content-Disposition");
	    cdb.setDescrizione("attachment; filename=\"" + posizione.getNomeDocumento() + "\"");
	    headerAggiuntivi.add(cdb);
	    cdb = new CodiceDescrizioneBean();
	    cdb.setCodice("Content-Type");
	    cdb.setDescrizione("application/pdf");
	    headerAggiuntivi.add(cdb);
	    return rispostaWs(posizione.getDocumento().getInputStream(), Status.OK, headerAggiuntivi);
	} catch (Exception e) {
	    log.error("getBollettinoPDF", e);
	    CodiceDescrizioneBean b = new CodiceDescrizioneBean();
	    b.setCodice("500");
	    b.setDescrizione(e.getMessage());
	    String output = (String) serializer.serialize(b);
	    return rispostaWs(output, Status.INTERNAL_SERVER_ERROR);
	}
    }

    @POST
    @Path("/mercati/pagamenti/{alias}/{software}/attiva-sessione")
    @Descriptions({
	    @Description(value = "Ottiene la lista dei pagamenti dati i riferimenti numero autorizzazione / comune", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response attivaSessionePagamento(@PathParam("alias") String alias, @PathParam("software") String software, String json) throws Exception {

	setORMHelper(alias, software);
	AttivaSessionePagamentoBean bean = (AttivaSessionePagamentoBean) getSerializer().deserialize(json, AttivaSessionePagamentoBean.class);
	AttivaSessionePagamentoResponseBean result = new AttivaSessionePagamentoResponseBean();
	Status res = Status.OK;
	try {
	    result = nodoPagamentiService.attivaSessionPagamento(bean);
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("attivaSessionePagamento", e);
	    res = Status.INTERNAL_SERVER_ERROR;
	    result.setEsito(false);
	    result.setDescEsito(e.getMessage());
	}
	Serializer serializer = getSerializer();
	String output = (String) serializer.serialize(result);
	ORMHelper.destroyORMHelper();
	return rispostaWs(output, res);
    }

    @POST
    @Path("/download/{alias}/{software}/qrcode-posizione-debitoria")
    @Descriptions({ @Description(value = "Scarica il QRCODE del pagamento PAGO PA", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response qrcode(@PathParam("alias") String alias, @PathParam("software") String software, String json) throws Exception {

	setORMHelper(alias, software);
	IdentificativoDescrizioneBean bean = (IdentificativoDescrizioneBean) getSerializer().deserialize(json, IdentificativoDescrizioneBean.class);
	Integer dettPosizioneDebitoriaId = bean.getId();
	byte[] b = nodoPagamentiService.getQrCodePagamento(dettPosizioneDebitoriaId);
	Base64 codec = new Base64();
	String encoded = codec.encodeToString(b);
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	result.setCodice(String.valueOf(b.length));
	result.setDescrizione(encoded);
	String output = (String) serializer.serialize(result);
	ORMHelper.destroyORMHelper();
	return rispostaWs(output, Status.OK);
    }

    @GET
    @Path("/bollettazione/{alias}/{software}/dettaglio/pdf/{identificativoCodificato}")
    @Descriptions({ @Description(value = "Restituisce il dettaglio bollettazione in PDF per alias e software indicati", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getBollettazionePdf(@HeaderParam(AUTHORIZATION) String auth, @PathParam("alias") String alias,
	    @PathParam("software") String software, @PathParam("identificativoCodificato") String identificativoCodificato) throws IOException {

	try {
	    authenticate(auth);
	    String[] info = identificativoCodificato.split("\\.");
	    Integer idBollettazione = Integer.parseInt(info[0]);
	    Integer codiceAnagrafe = Integer.parseInt(info[1]);
	    String newHash = SecureIdUtils.encode(idBollettazione, Utilities.getToday(false), info[1]);
	    if (!identificativoCodificato.equalsIgnoreCase(newHash)) {
		log.error("getBollettazionePdf Non è stato fornito l'hash corretto per il dettaglio {},{}", identificativoCodificato, newHash);
		throw new RuntimeException("Non è stato fornito l'hash corretto per il dettaglio");
	    }
	    byte[] pdfBytes = calcoloBollettazioneService.getPdfReportForBollettazione(idBollettazione, codiceAnagrafe, alias, software, true);
	    List<CodiceDescrizioneBean> headerAggiuntivi = new ArrayList<CodiceDescrizioneBean>();
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice("Content-Disposition");
	    cdb.setDescrizione("attachment; filename=\"dettaglio.pdf\"");
	    headerAggiuntivi.add(cdb);
	    cdb = new CodiceDescrizioneBean();
	    cdb.setCodice("Content-Type");
	    cdb.setDescrizione("application/pdf");
	    headerAggiuntivi.add(cdb);
	    return rispostaWs(new ByteArrayInputStream(pdfBytes), Status.OK, headerAggiuntivi);
	} catch (Exception e) {
	    log.error("Errore durante la generazione del PDF di bollettazione", e);
	    throw new RuntimeException("Errore durante la generazione del PDF: " + e.getMessage(), e);
	}
    }

    @OPTIONS
    @Path("/{var:.*}")
    public Response defaultOptions() {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
	builder.header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
	builder.status(Status.OK);
	ORMHelper.destroyORMHelper();
	return builder.build();
    }

    protected Response rispostaWs(Object entity, Status returnStatus) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
	ORMHelper.destroyORMHelper();
	return builder.build();
    }

    private Serializer serializer;

    protected Serializer getSerializer() {

	this.serializer = new JsonSerializer();
	serializer.setClassPropertyFilterHandler(new ClassPropertyFilterHandler() {

	    @Override
	    public ClassPropertyFilter getClassPropertyFilterByClass(Class arg0) {

		if (arg0 == ListaPagamentiBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(ListaPagamentiBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == PagamentiMercatoPosizDebRestHelperV2.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PagamentiMercatoPosizDebRestHelperV2.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == PresenzeStoricoGiornataRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PresenzeStoricoGiornataRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == AutorizzazioniMercatiPresenzeStoricoRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniMercatiPresenzeStoricoRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == AutorizzazioniPresenzeStoricoRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniPresenzeStoricoRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == AttivaSessionePagamentoResponseBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AttivaSessionePagamentoResponseBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == AttivaSessionePagamentoFormParamsResponseBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AttivaSessionePagamentoFormParamsResponseBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == FormParametersType.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(FormParametersType.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == FormParamType.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(FormParamType.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == AttivaSessionePagamentoResponseType.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AttivaSessionePagamentoResponseType.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == PagamentiMercatoRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PagamentiMercatoRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == PagamentiMercatoGiornoRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PagamentiMercatoGiornoRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == PagamentiMercatoPosizDebRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PagamentiMercatoPosizDebRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == AutorizzazioniRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == AutorizzazioniFrontRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniFrontRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == CodiceDescrizioneBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(CodiceDescrizioneBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == GiornataMercatoFaseRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(GiornataMercatoFaseRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == GiornataMercatoSpuntistaRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(GiornataMercatoSpuntistaRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == AutorizzazioneRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioneRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == ResponsabileRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(ResponsabileRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == AnagraferestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AnagraferestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == GiornataMercatoPosteggioRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(GiornataMercatoPosteggioRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == CodiceNumericoDescrizioneBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(CodiceNumericoDescrizioneBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == AutorizzazioniConcessioniRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniConcessioniRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == ChiaveValoreBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(ChiaveValoreBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == MercatiRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MercatiRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == AnagrafeDTO.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AnagrafeDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == MercatiDDTO.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MercatiDDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == AutorizzazioniDTO.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == MercatipresenzeDDTO.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MercatipresenzeDDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == PkId.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PkId.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == MercatiPresenzeStoricoRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MercatiPresenzeStoricoRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == GiorniMercatoPresenzeRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(GiorniMercatoPresenzeRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == AutorizzazioniConcessioniPresenzeRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniConcessioniPresenzeRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == PosteggioLiberoHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PosteggioLiberoHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == PagamentiMercatiHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PagamentiMercatiHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == PagamentiMercatiDaEffettuareHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PagamentiMercatiDaEffettuareHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == StradarioDTO.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(StradarioDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == DocumentiRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(DocumentiRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == InfoAutorizzazioneRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(InfoAutorizzazioneRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == MappaMercatoBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MappaMercatoBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == PosteggioMercatoBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PosteggioMercatoBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == MercatiRestConIdGiornataBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MercatiRestConIdGiornataBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == PageResult.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PageResult.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == PagamentiMercatoPosizDebRestHelperV2Paged.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PagamentiMercatoPosizDebRestHelperV2Paged.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		return null;
	    }
	});
	return this.serializer;
    }
}
