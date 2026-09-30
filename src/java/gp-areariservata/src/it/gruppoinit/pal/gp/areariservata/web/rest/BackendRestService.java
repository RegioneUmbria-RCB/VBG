package it.gruppoinit.pal.gp.areariservata.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.ComuniAssociatiEsclusioni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTPrenot;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ConsensoInformativoDettaglioBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ConsensoInformativoRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafeRestBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.anagrafe.model.CodiceVerificaMailAnagrafeBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoOperazioneAggiornamento;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.oneri.DecodificaCausaleOnereRequest;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.features.oneri.exceptions.DecodificaOneriExceptions;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiesclusioniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatisoftwareService;
import it.gruppoinit.pal.gp.core.service.ConsensiInformativiService;
import it.gruppoinit.pal.gp.core.service.FoConsensiInformativiService;
import it.gruppoinit.pal.gp.core.service.MercatiAppService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeStoricoService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeTPrenotService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;
import it.gruppoinit.pal.gp.core.service.exception.MercatiAppException;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniConcessioniRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.ComuniDTO;
import it.gruppoinit.pal.gp.core.service.helper.MercatiPresenzeStoricoRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.MercatiRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatiHelper;
import it.gruppoinit.pal.gp.core.service.helper.PosteggioLiberoHelper;
import it.gruppoinit.pal.gp.core.service.helper.StradarioDTO;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import net.sf.sojo.interchange.Serializer;

@Path("/backend/")
public class BackendRestService extends BaseRestService {

    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;
    @Autowired
    private MercatipresenzeStoricoService mercatipresenzeStoricoService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private MercatipresenzeTPrenotService mercatipresenzeTPrenotService;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private ComuniassociatisoftwareService comuniassociatisoftwareService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private TipimodalitapagamentoService tipimodalitapagamentoService;
    @Autowired
    private MercatiAppService mercatiAppService;
    @Autowired
    private FoConsensiInformativiService foConsensiInformativiService;
    @Autowired
    private ConsensiInformativiService consensiInformativiService;
    @Autowired
    private ComuniassociatiesclusioniService comuniassociatiesclusioniService;
    @Autowired
    private TipicausalioneriService tipicausalioneriService;
    @Autowired
    private ContiService contiService;
    @Autowired
    private StatiistanzaService statiistanzaService;
    private static final Logger log = LoggerFactory.getLogger(BackendRestService.class.getName());

    @Deprecated
    @GET
    @Path("/areepubbliche/{token}/{software}/autorizzazioni")
    @Descriptions({ @Description(value = "Effettua una ricerca sulle autorizzazioni/concessioni dell'utente loggato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response ricercaIstanzePubblica(@PathParam("token") String token, @PathParam("software") String software) throws Exception {

	Serializer serializer = getSerializer();
	CheckTokenResponse ti = getTokenInfo(token);
	checkrequest(serializer, ti);
	setORMHelper(ti.getTokenInfo().getAlias(), software);
	String cf = ti.getTokenInfo().getUserid();
	List<AutorizzazioniConcessioniRestHelper> auts = autorizzazioniService.findByCodiceFiscaleAnagrafe(cf, null, null);
	String str = (String) serializer.serialize(auts);
	ORMHelper.destroyORMHelper();
	return rispostaWs(str, Status.OK);
    }

    @Deprecated
    @GET
    @Path("/areepubbliche/{token}/{software}/manifestazioni")
    @Descriptions({
	    @Description(value = "Effettua una ricerca sui mercati/fiere configurati. Per delimitare la ricerca sul nome impostare un parametro con nome filtroDescrizione", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response listaManifestazioni(@PathParam("token") String token, @PathParam("software") String software,
	    @FormParam("filtroDescrizione") String filtroDescrizione) throws Exception {

	Serializer serializer = getSerializer();
	CheckTokenResponse ti = getTokenInfo(token);
	checkrequest(serializer, ti);
	setORMHelper(ti.getTokenInfo().getAlias(), software);
	List<MercatiRestHelper> auts = mercatiService.findAttiviByDescrizione(filtroDescrizione, null, null);
	String str = (String) serializer.serialize(auts);
	ORMHelper.destroyORMHelper();
	return rispostaWs(str, Status.OK);
    }

    @Deprecated
    @GET
    @Path("/areepubbliche/{token}/{software}/posteggiliberi/{codiceGiornata}")
    @Descriptions({ @Description(value = "Cerca i posteggi non occupati per la giornata di mercato.", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response listaPosteggi(@PathParam("token") String token, @PathParam("software") String software,
	    @PathParam("codiceGiornata") Integer codiceGiornata) throws Exception {

	Serializer serializer = getSerializer();
	CheckTokenResponse ti = getTokenInfo(token);
	checkrequest(serializer, ti);
	setORMHelper(ti.getTokenInfo().getAlias(), software);
	List<PosteggioLiberoHelper> ret = mercatipresenzeDService.findListaPosteggiLiberi(codiceGiornata);
	String str = (String) serializer.serialize(ret);
	ORMHelper.destroyORMHelper();
	return rispostaWs(str, Status.OK);
    }

    @Deprecated
    @GET
    @Path("/areepubbliche/{token}/{software}/presenze")
    @Descriptions({
	    @Description(value = "Il metodo ritorna il numero di presenze per le autorizzazioni dell'anagrafica collegata.", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response presenze(@PathParam("token") String token, @PathParam("software") String software) throws Exception {

	Serializer serializer = getSerializer();
	CheckTokenResponse ti = getTokenInfo(token);
	checkrequest(serializer, ti);
	setORMHelper(ti.getTokenInfo().getAlias(), software);
	String cf = ti.getTokenInfo().getUserid();
	List<MercatiPresenzeStoricoRestHelper> listPresenze = mercatipresenzeStoricoService.findSommaDellePresenze(cf);
	String str = (String) serializer.serialize(listPresenze);
	ORMHelper.destroyORMHelper();
	return rispostaWs(str, Status.OK);
    }

    @Deprecated
    @GET
    @Path("/areepubbliche/{token}/{software}/pagamentispuntista")
    @Descriptions({
	    @Description(value = "Il metodo ritorna il numero di presenze per le autorizzazioni dell'anagrafica collegata.", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response pagamentispuntista(@PathParam("token") String token, @PathParam("software") String software) throws Exception {

	Serializer serializer = getSerializer();
	CheckTokenResponse ti = getTokenInfo(token);
	checkrequest(serializer, ti);
	setORMHelper(ti.getTokenInfo().getAlias(), software);
	String cf = ti.getTokenInfo().getUserid();
	PagamentiMercatiHelper listPresenze = mercatipresenzeDService.findPagamentiByCf(cf);
	String str = (String) serializer.serialize(listPresenze);
	ORMHelper.destroyORMHelper();
	return rispostaWs(str, Status.OK);
    }

    @Deprecated
    @GET
    @Path("/areepubbliche/{token}/{software}/salvainteresseposteggio/{codiceGiornata}/{idPosteggio}")
    @Descriptions({ @Description(value = "Il metodo permette di salvare la preferenza per un posteggio.", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response salvapreferenza(@PathParam("token") String token, @PathParam("software") String software,
	    @PathParam("codiceGiornata") Integer codiceGiornata, @PathParam("idPosteggio") Integer idPosteggio,
	    @FormParam("annotazioni") String annotazioni) throws Exception {

	Serializer serializer = getSerializer();
	CheckTokenResponse ti = getTokenInfo(token);
	checkrequest(serializer, ti);
	setORMHelper(ti.getTokenInfo().getAlias(), software);
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
	List<MercatipresenzeTPrenot> prefs = mercatipresenzeTPrenotService.findByMercatipresenzeTAndPosteggioAndAnagrafe(codiceGiornata, idPosteggio,
		rich.getId().getCodice());
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
    }

    @Deprecated
    @GET
    @Path("/areepubbliche/{token}/{software}/eliminainteresseposteggio/{codiceGiornata}/{idPosteggio}")
    @Descriptions({ @Description(value = "Il metodo permette di eliminare la preferenza per un posteggio", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response rimuovipreferenza(@PathParam("token") String token, @PathParam("software") String software,
	    @PathParam("codiceGiornata") Integer codiceGiornata, @PathParam("idPosteggio") Integer idPosteggio) throws Exception {

	Serializer serializer = getSerializer();
	CheckTokenResponse ti = getTokenInfo(token);
	checkrequest(serializer, ti);
	setORMHelper(ti.getTokenInfo().getAlias(), software);
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
	List<MercatipresenzeTPrenot> prefs = mercatipresenzeTPrenotService.findByMercatipresenzeTAndPosteggioAndAnagrafe(codiceGiornata, idPosteggio,
		rich.getId().getCodice());
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
    }

    @Deprecated
    @GET
    @Path("/areepubbliche/{token}/{software}/interesseposteggio/{codiceGiornata}/{idPosteggio}")
    @Descriptions({ @Description(value = "Il metodo permette di visualizzare le preferenze per un posteggio", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response visualizzapreferenze(@PathParam("token") String token, @PathParam("software") String software,
	    @PathParam("codiceGiornata") Integer codiceGiornata, @PathParam("idPosteggio") Integer idPosteggio) throws Exception {

	Serializer serializer = getSerializer();
	CheckTokenResponse ti = getTokenInfo(token);
	checkrequest(serializer, ti);
	setORMHelper(ti.getTokenInfo().getAlias(), software);
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
	List<MercatipresenzeTPrenot> prefs = mercatipresenzeTPrenotService.findByMercatipresenzeTAndPosteggioAndAnagrafe(codiceGiornata, idPosteggio,
		rich.getId().getCodice());
	for (MercatipresenzeTPrenot mp : prefs) {
	    listaPreferenze.add(newNVBean(mp.getNote(), mp.getId().getCodice().toString()));
	}
	String str = (String) serializer.serialize(listaPreferenze);
	ORMHelper.destroyORMHelper();
	return rispostaWs(str, Status.OK);
    }

    @GET
    @Path("/generici/{token}/infoutente")
    @Descriptions({ @Description(value = "Il metodo torna le informazioni dell'utente collegato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response infoutente(@PathParam("token") String token) {

	try {
	    Serializer serializer = getSerializer();
	    CheckTokenResponse ti = getTokenInfo(token);
	    checkrequest(serializer, ti);
	    setORMHelper(ti.getTokenInfo().getAlias(), WebConstants.SOFTWARE_TT);
	    String cf = ti.getTokenInfo().getUserid();
	    String descrizione = "", codice = "";
	    if (ti.getTokenInfo().getContesto().equals(ContestoType.UTE) || ti.getTokenInfo().getContesto().equals(ContestoType.UTEG)) {
		Anagrafe rich = null;
		List<Anagrafe> anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_FISICA, true);
		if (anags.isEmpty()) {
		    anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_GIURIDICA, true);
		}
		if (anags.isEmpty()) {
		    throw new RuntimeException("Richiedente non trovato");
		}
		rich = anags.get(0);
		codice = rich.getId().getCodice().toString();
		if (StringUtils.isNotBlank(rich.getNome())) {
		    descrizione = rich.getNome() + " ";
		}
		if (StringUtils.isNotBlank(rich.getNominativo())) {
		    descrizione += rich.getNominativo();
		}
	    } else {
		throw new RuntimeException("Utente non trovato");
	    }
	    CodiceDescrizioneBean cdb = newNVBean(descrizione, codice);
	    String str = (String) serializer.serialize(cdb);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(str, Status.OK);
	} catch (Exception e1) {
	    log.error("infoutente", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @POST
    @Path("/generici/genera-codice-verifica-mail")
    @Descriptions({ @Description(value = "Il metodo torna le informazioni dell'utente collegato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response generaCodiceVerificaMail(@HeaderParam(AUTHORIZATION) String auth, @FormParam("nuova_email") String nuovaEmail) {

	try {
	    authenticate(auth);
	    CheckTokenResponse ti = getTokenInfo(ORMHelper.getToken());
	    String cf = ti.getTokenInfo().getUserid();
	    Anagrafe rich = null;
	    if (ti.getTokenInfo().getContesto().equals(ContestoType.UTE) || ti.getTokenInfo().getContesto().equals(ContestoType.UTEG)) {
		List<Anagrafe> anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_FISICA, true);
		if (anags.isEmpty()) {
		    anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_GIURIDICA, true);
		}
		if (anags.isEmpty()) {
		    throw new RuntimeException("Richiedente non trovato");
		}
		rich = anags.get(0);
	    } else {
		throw new RuntimeException("Utente non trovato");
	    }
	    EsitoOperazioneAggiornamento esito = new EsitoOperazioneAggiornamento(true, null);
	    try {
		CodiceVerificaMailAnagrafeBean result = anagrafeService.generaCodiceVerificaMail(rich.getId().getCodice(), nuovaEmail);
		log.debug("anagrafeService.generaCodiceVerificaMail() ==> {}", result);
	    } catch (FunzioneBusinessRemotaException e) {
		esito = new EsitoOperazioneAggiornamento(false, e.getMessage());
	    } catch (InvalidConfigurationException e) {
		esito = new EsitoOperazioneAggiornamento(false, e.getMessage());
	    }
	    String str = Utilities.marshalJsonObject(esito, esito.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(str, Status.OK);
	} catch (Exception e1) {
	    log.error("generaCodiceVerificaMail", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @POST
    @Path("/generici/verifica-mail")
    @Descriptions({ @Description(value = "Il metodo torna le informazioni dell'utente collegato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response verificaMail(@HeaderParam(AUTHORIZATION) String auth, @FormParam("codice_verifica") String codiceVerifica) {

	try {
	    authenticate(auth);
	    CheckTokenResponse ti = getTokenInfo(ORMHelper.getToken());
	    String cf = ti.getTokenInfo().getUserid();
	    Anagrafe rich = null;
	    if (ti.getTokenInfo().getContesto().equals(ContestoType.UTE) || ti.getTokenInfo().getContesto().equals(ContestoType.UTEG)) {
		List<Anagrafe> anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_FISICA, true);
		if (anags.isEmpty()) {
		    anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_GIURIDICA, true);
		}
		if (anags.isEmpty()) {
		    throw new RuntimeException("Richiedente non trovato");
		}
		rich = anags.get(0);
	    } else {
		throw new RuntimeException("Utente non trovato");
	    }
	    EsitoOperazioneAggiornamento result = anagrafeService.updateVerificaMail(rich.getId().getCodice(), codiceVerifica);
	    if (!result.isEsito()) {
		log.error("anagrafeService.generaCodiceVerificaMail() ==> esito: {}, messaggio: {}", result.isEsito(), result.getMessaggio());
	    } else {
		log.debug("anagrafeService.generaCodiceVerificaMail() ==> {}", result);
	    }
	    String str = Utilities.marshalJsonObject(result, result.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(str, Status.OK);
	} catch (Exception e1) {
	    log.error("verificaMail", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/generici/dettaglioutente")
    @Descriptions({ @Description(value = "Il metodo torna le informazioni dell'utente collegato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response infoutentedettaglio(@HeaderParam(AUTHORIZATION) String auth) {

	try {
	    authenticate(auth);
	    Serializer serializer = getSerializer();
	    CheckTokenResponse ti = getTokenInfo(ORMHelper.getToken());
	    String cf = ti.getTokenInfo().getUserid();
	    Anagrafe rich = null;
	    if (ti.getTokenInfo().getContesto().equals(ContestoType.UTE) || ti.getTokenInfo().getContesto().equals(ContestoType.UTEG)) {
		List<Anagrafe> anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_FISICA, true);
		if (anags.isEmpty()) {
		    anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_GIURIDICA, true);
		}
		if (anags.isEmpty()) {
		    throw new RuntimeException("Richiedente non trovato");
		}
		rich = anags.get(0);
	    } else {
		throw new RuntimeException("Utente non trovato");
	    }
	    DettaglioAnagrafeRestBean cdb = anagrafeService.populateDettaglioAnagrafeRestBean(rich);
	    String str = (String) serializer.serialize(cdb);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(str, Status.OK);
	} catch (Exception e1) {
	    log.error("infoutentedettaglio", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/generici/{token}/infotoken")
    @Descriptions({ @Description(value = "Il metodo torna le informazioni dell'utente collegato", target = DocTarget.METHOD) })
    @Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    public Response infotoken(@PathParam("token") String token) throws Exception {

	Serializer serializer = getSerializer();
	CheckTokenResponse ti = getTokenInfo(token);
	checkrequest(serializer, ti);
	setORMHelper(ti.getTokenInfo().getAlias(), WebConstants.SOFTWARE_TT);
	String cf = ti.getTokenInfo().getUserid();
	ORMHelper.destroyORMHelper();
	return rispostaWs(cf, Status.OK);
    }

    @GET
    @Path("/generici/{alias}/stradario")
    @Descriptions({ @Description(value = "Ricerca per stradario", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response cercaStradario(@PathParam("alias") String alias, @FormParam("descrizione") String filtroDescrizione,
	    @FormParam("codiceComune") String codiceComune, @FormParam("limitaRicerca") Boolean limitaRicerca) throws Exception {

	Serializer serializer = getSerializer();
	setORMHelper(alias, WebConstants.SOFTWARE_TT);
	Integer firstResult = null;
	Integer maxResults = null;
	if (limitaRicerca != null && limitaRicerca.booleanValue()) {
	    firstResult = 0;
	    maxResults = 20;
	}
	List<StradarioDTO> strads = stradarioService.findByMatchParzialeToDTO(filtroDescrizione, codiceComune, firstResult, maxResults);
	String str = (String) serializer.serialize(strads);
	ORMHelper.destroyORMHelper();
	return rispostaWs(str, Status.OK);
    }

    @GET
    @Path("/generici/{alias}/stradario/id/{id}")
    @Descriptions({ @Description(value = "Ricerca per stradario per id", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response cercaStradarioPerId(@PathParam("alias") String alias, @PathParam("id") Integer id) throws Exception {

	Serializer serializer = getSerializer();
	setORMHelper(alias, WebConstants.SOFTWARE_TT);
	Stradario strads = stradarioService.findById(new PkId(id));
	String str = "";
	if (strads != null) {
	    StradarioDTO out = stradarioService.stradarioToDTO(strads);
	    str = (String) serializer.serialize(out);
	} else {
	    log.error("Stradario non trovato per alias {} e identificativo {}", alias, id);
	}
	ORMHelper.destroyORMHelper();
	return rispostaWs(str, Status.OK);
    }

    @GET
    @Path("/generici/{alias}/comuniassociati")
    @Descriptions({ @Description(value = "Lista dei comuniassociati", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response comuniassociati(@PathParam("alias") String alias) throws Exception {

	Serializer serializer = getSerializer();
	setORMHelper(alias, WebConstants.SOFTWARE_TT);
	List<ComuniDTO> dtos = new ArrayList<ComuniDTO>();
	List<Comuniassociati> cass = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	for (Comuniassociati comuniassociati : cass) {
	    if (comuniassociati.getComune() != null) {
		ComuniDTO c = comuniService.comuniToDTO(comuniassociati.getComune());
		dtos.add(c);
	    }
	}
	String str = (String) serializer.serialize(dtos);
	ORMHelper.destroyORMHelper();
	return rispostaWs(str, Status.OK);
    }

    @GET
    @Path("/generici/{alias}/comuniassociati/{software}")
    @Descriptions({ @Description(value = "Lista dei comuniassociati", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response comuniassociati2(@PathParam("alias") String alias, @PathParam("software") String software) throws Exception {

	Serializer serializer = getSerializer();
	setORMHelper(alias, software);
	List<ComuniDTO> dtos = new ArrayList<ComuniDTO>();
	List<ComuniAssociatiEsclusioni> comuniAssociatiEsclusioni = comuniassociatiesclusioniService
		.findByIdComuneandSoftware(ORMHelper.getIdcomune(), software);
	String[] codicicomune = new String[comuniAssociatiEsclusioni.size()];
	int i = 0;
	for (ComuniAssociatiEsclusioni comAssEscl : comuniAssociatiEsclusioni) {
	    codicicomune[i] = comAssEscl.getId().getCodicecomune();
	    i++;
	}
	List<Comuniassociati> comuniassociati = comuniassociatiService.findByComuniEsclusioni(codicicomune);
	for (Comuniassociati comass : comuniassociati) {
	    if (comass.getComune() != null) {
		ComuniDTO c = comuniService.comuniToDTO(comuniService.findByCodiceComune(comass.getComune()));
		dtos.add(c);
	    }
	}
	String str = (String) serializer.serialize(dtos);
	ORMHelper.destroyORMHelper();
	return rispostaWs(str, Status.OK);
    }

    @GET
    @Path("/generici/{alias}/comuniassociati/{codicecomune}/{software}/pec")
    @Descriptions({ @Description(value = "Torna la PEC ", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response peccomuniassociati(@PathParam("alias") String alias, @PathParam("software") String software,
	    @PathParam("codicecomune") String codicecomune) throws Exception {

	Serializer serializer = getSerializer();
	setORMHelper(alias, software);
	CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	Comuni com = comuniService.findById(codicecomune);
	if (com != null) {
	    String mailpec = "";
	    Comuniassociatisoftware cass = comuniassociatisoftwareService.findByComune(com);
	    if (cass != null) {
		mailpec = cass.getMailpec();
	    }
	    cdb.setCodice(com.getComune());
	    cdb.setDescrizione(mailpec);
	}
	String str = (String) serializer.serialize(cdb);
	ORMHelper.destroyORMHelper();
	return rispostaWs(str, Status.OK);
    }

    @GET
    @Path("/generici/{alias}/modalitapagamento")
    @Descriptions({ @Description(value = "Torna la PEC ", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response modalitapagamento(@PathParam("alias") String alias) throws Exception {

	Serializer serializer = getSerializer();
	setORMHelper(alias, WebConstants.SOFTWARE_TT);
	List<CodiceDescrizioneBean> cdbs = new ArrayList<CodiceDescrizioneBean>();
	List<Tipimodalitapagamento> tmdps = tipimodalitapagamentoService.findAll(null, null, false);
	for (Tipimodalitapagamento tm : tmdps) {
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice(String.valueOf(tm.getId().getCodice()));
	    cdb.setDescrizione(String.valueOf(tm.getMpDescrestesa()));
	    cdbs.add(cdb);
	}
	String str = (String) serializer.serialize(cdbs);
	ORMHelper.destroyORMHelper();
	return rispostaWs(str, Status.OK);
    }

    @GET
    @Path("/generici/ops/{alias}/ricerca-comuni/{testo}")
    @Descriptions({ @Description(value = "Ricerca nella tabella comuni", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response ricercaComuni(@PathParam("alias") String alias, @PathParam("testo") String testo) throws Exception {

	setORMHelper(alias, WebConstants.SOFTWARE_TT);
	Serializer serializer = getSerializer();
	Status retVal = Status.OK;
	List<CodiceDescrizioneBean> result = new ArrayList<CodiceDescrizioneBean>();
	try {
	    result = mercatiAppService.ricercaComuni(testo);
	} catch (MercatiAppException e) {
	    log.error("ricercaComuni", e);
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    //result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	ORMHelper.destroyORMHelper();
	return rispostaWs(output, retVal);
    }

    @GET
    @Path("/generici/ops/{alias}/tutti-comuni")
    @Descriptions({ @Description(value = "Ricerca nella tabella comuni", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response ricercaTuttiComuni(@PathParam("alias") String alias) throws Exception {

	setORMHelper(alias, WebConstants.SOFTWARE_TT);
	Serializer serializer = getSerializer();
	Status retVal = Status.OK;
	List<CodiceDescrizioneBean> result = new ArrayList<CodiceDescrizioneBean>();
	try {
	    result = mercatiAppService.ricercaTuttiComuni();
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    //result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	ORMHelper.destroyORMHelper();
	return rispostaWs(output, retVal);
    }

    @GET
    @Path("/generici/ops/lista-consensi-informativi")
    @Descriptions({ @Description(value = "Ottiene la lista dei contesti informativi", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response listaConsensiInformativi(@HeaderParam(AUTHORIZATION) String auth) {

	try {
	    authenticate(auth);
	    Serializer serializer = getSerializer();
	    CheckTokenResponse tokenInfo = getTokenInfo(ORMHelper.getToken());
	    List<ConsensoInformativoDettaglioBean> bean = consensiInformativiService.findConsensiDaApprovare(tokenInfo.getTokenInfo().getUserid());
	    String output = (String) serializer.serialize(bean);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(output, Status.OK);
	} catch (Exception e1) {
	    log.error("listaConsensiInformativi", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @POST
    @Path("/generici/ops/imposta-consenso/{identificativo_consenso}")
    @Descriptions({ @Description(value = "imposta il consenso per un utente ed il contesto specificato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response impostaConsenso(@HeaderParam(AUTHORIZATION) String auth, @PathParam("identificativo_consenso") Integer identificativoConsenso) {

	try {
	    authenticate(auth);
	    Serializer serializer = getSerializer();
	    CheckTokenResponse tokenInfo = getTokenInfo(ORMHelper.getToken());
	    foConsensiInformativiService.inserisciConsenso(tokenInfo.getTokenInfo().getUserid(), identificativoConsenso);
	    CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	    result.setCodice("OK");
	    String output = (String) serializer.serialize(result);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(output, Status.OK);
	} catch (Exception e1) {
	    log.error("impostaConsenso", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/generici/ops/verifica-consenso/{identificativo_consenso}")
    @Descriptions({ @Description(value = "Ottiene la lista dei mercati associati ad un utente", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response verificaConsenso(@HeaderParam(AUTHORIZATION) String auth, @PathParam("identificativo_consenso") Integer identificativoConsenso) {

	Serializer serializer = getSerializer();
	CheckTokenResponse tokenInfo = null;
	try {
	    authenticate(auth);
	    tokenInfo = getTokenInfo(ORMHelper.getToken());
	    ConsensoInformativoRestBean bean = foConsensiInformativiService.leggiConsenso(tokenInfo.getTokenInfo().getUserid(),
		    identificativoConsenso);
	    String output = (String) serializer.serialize(bean);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(output, Status.OK);
	} catch (Exception e1) {
	    log.error("verificaConsenso", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/generici/ops/refresh-token")
    @Descriptions({ @Description(value = "Ottiene la lista dei mercati associati ad un utente", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response sostituisciToken(@HeaderParam(AUTHORIZATION) String auth) {

	try {
	    authenticate(auth);
	    String nuovoToken = refreshToken(ORMHelper.getToken());
	    Serializer serializer = getSerializer();
	    ORMHelper.setToken(nuovoToken);
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice(nuovoToken);
	    cdb.setDescrizione(nuovoToken);
	    String output = (String) serializer.serialize(cdb);
	    ORMHelper.destroyORMHelper();
	    return rispostaWs(output, Status.OK);
	} catch (Exception e1) {
	    log.error("sostituisciToken", e1);
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
    }

    @GET
    @Path("/generici/{alias}/{software}/conti/{codice_comune}/bycausaleonere/{codicecausale}/{causale}")
    @Descriptions({ @Description(value = "Ottiene il conto associato ad una causale onere", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getConto(@PathParam("alias") String alias, @PathParam("software") String software,
	    @PathParam("codice_comune") String codice_comune, @PathParam("codicecausale") String idCausale, @PathParam("causale") String causale) {

	Serializer serializer = getSerializer();
	setORMHelper(alias, software);
	DecodificaCausaleOnereRequest request = new DecodificaCausaleOnereRequest(idCausale, causale, software);
	Tipicausalioneri tco = null;
	CodiceDescrizioneBean cdb = new CodiceDescrizioneBean("500", "");
	String causaleErr = causale.replace("\"", " ") + "(" + idCausale + ")";
	try {
	    tco = this.tipicausalioneriService.decodeCausaleonere(request);
	} catch (DecodificaOneriExceptions e) {
	    cdb.setDescrizione("Errata configurazione del backend: causale  " + causaleErr + " non valida. Dettaglio " + e.getMessage());
	    return rispostaWs(serializer.serialize(cdb), Status.INTERNAL_SERVER_ERROR);
	}
	Conti conto = this.contiService.findContoAttivoByIdCausaleOnere(tco.getId().getCodice());
	if (conto == null) {
	    cdb.setDescrizione("Errata configurazione del backend: nessun conto trovato per la causale  " + causaleErr);
	    return rispostaWs(serializer.serialize(cdb), Status.INTERNAL_SERVER_ERROR);
	}
	if (StringUtils.isBlank(conto.getMappaturanodopag())) {
	    cdb.setDescrizione("Errata configurazione del backend: causale  " + causaleErr + " non valida.");
	    return rispostaWs(serializer.serialize(cdb), Status.INTERNAL_SERVER_ERROR);
	}
	ContoResponseType response = new ContoResponseType(conto, tco);
	String str = (String) serializer.serialize(response);
	ORMHelper.destroyORMHelper();
	return rispostaWs(str, Status.OK);
    }

    @GET
    @Path("/generici/{alias}/{software}/stati_istanza")
    @Descriptions({ @Description(value = "Ottiene la lista degli stati di una istanza filtrati per software", target = DocTarget.METHOD) })
    @Consumes(value = { MediaType.APPLICATION_JSON + "; charset=utf-8", MediaType.APPLICATION_JSON + "; charset=UTF-8" })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getStatiIstanza(@PathParam("alias") String alias, @PathParam("software") String software) {

	Serializer serializer = getSerializer();
	setORMHelper(alias, software);
	List<Statiistanza> stati = statiistanzaService.findStati(software);
	List<StatiistanzaType> response = popolaListaStatiistanza(stati, software);
	String str = (String) serializer.serialize(response);
	return rispostaWs(str, Status.OK);
    }

    private List<StatiistanzaType> popolaListaStatiistanza(List<Statiistanza> stati, String software) {

	List<StatiistanzaType> ret = new ArrayList<StatiistanzaType>();
	ret.add(createStatiistanzaType("Tutte", "stato_tutte", software));
	ret.add(createStatiistanzaType("Tutte le istanze non chiuse", "stato_aperte", software));
	ret.add(createStatiistanzaType("Tutte le istanze chiuse", "stato_chiuse", software));
	if (stati != null) {
	    for (Statiistanza statiistanza : stati) {
		StatiistanzaType sit = new StatiistanzaType(statiistanza);
		ret.add(sit);
	    }
	}
	return ret;
    }

    private StatiistanzaType createStatiistanzaType(String stato, String id, String software) {

	StatiistanzaType sit = new StatiistanzaType();
	sit.setStato(stato);
	sit.setId(id);
	sit.setSoftware(software);
	return sit;
    }
}
