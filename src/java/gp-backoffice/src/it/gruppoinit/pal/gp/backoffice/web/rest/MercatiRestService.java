package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiConcessioniHelper;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSoggetti;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeDTO;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniCsiRestBean;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniDTO;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiAssenzeTipoCalcoloEnum;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioImportoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.RigaImporto;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AnagraferestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AnnullaGiornataRequest;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioneRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackList;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackListDettaglio;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackListIdentificativi;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackListOggetti;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackListOggettoDettaglio;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackListWrapper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.CodiceNumericoDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ConcessionarioRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DocumentiRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.FasciaMercatoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoFaseRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoPosteggioRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoSpuntistaRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InfoAutorizzazioneRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.MappaMercatoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PercentualeAssenzeBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PostRestNuovoSpuntistaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioInfoRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioMercatoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ResponsabileRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.StatoPagamentoSpuntistaRestHelper;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi.LayouttestiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlackListContestoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlacklistAutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.StatiPosizioniDebitorieGiornataMercatoBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.ImpostaStatoPosteggioFlyweight;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDDisabilitatiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ImpostaStatoGiornataRequest;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ImpostaStatoPosteggioRequest;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.PosteggiDisabilitatiResponse;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EtichettaApp;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.PagamentoModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.PosizioneDebitoriaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.PosizioneDebitoriaModelEsteso;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.PosizioneDebitoriaResponseType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.StatoResponseType;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.MercatiAppService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.MercatiAppException;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniConcessioniPresenzeRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniConcessioniRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.GiorniMercatoPresenzeRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.MercatiPresenzeStoricoRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.MercatiRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatiDaEffettuareHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatiHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PosteggioLiberoHelper;
import it.gruppoinit.pal.gp.core.service.helper.RestBeanHelper;
import it.gruppoinit.pal.gp.core.service.helper.StradarioDTO;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import net.sf.sojo.core.filter.ClassPropertyFilter;
import net.sf.sojo.core.filter.ClassPropertyFilterHandler;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

@Path("/app/")
public class MercatiRestService {

    private static final String UNIQUE_ID = "~unique-id~";
    private static final String CLASS = "class";
    private static final String CONTENT_TYPE_AUTHORIZATION = "Content-Type, Authorization";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private Map<String, LockingObject> lockMap = new HashMap<String, LockingObject>();
    private static final Logger log = LoggerFactory.getLogger(MercatiRestService.class);
    @Autowired
    private AttivitaService attivitaService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;
    @Autowired
    private MercatiAppService mercatiAppService;
    @Autowired
    private ExternalDBResolver externalDBResolver;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private OggettiMetadatiService oggettiMetadatiService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private BlacklistAutorizzazioniService blacklistAutorizzazioniService;
    private MercatiDDisabilitatiService mercatiDDisabilitatiService;
    private IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private LayouttestiService layouttestiService;

    @Autowired
    public void setMercatiDDisabilitatiService(MercatiDDisabilitatiService mercatiDDisabilitatiService) {

	this.mercatiDDisabilitatiService = mercatiDDisabilitatiService;
    }

    @Autowired
    public void setComportamentiMercatiService(IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService) {

	this.comportamentiMercatiService = comportamentiMercatiService;
    }

    @GET
    @Path("/mercati/")
    @Descriptions({ @Description(value = "Ottiene la lista dei mercati associati ad un utente", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getListaMercati() {

	Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	String today = Utilities.getToday(false);
	Date dateToday = Utilities.parseDateString(today, false);
	log.debug("listMercatiPerResponsabileEData# Date={}", dateToday);
	List<IdentificativoDescrizioneBean> result = mercatiAppService.findGiornateByResponsabileDallaDataAllaData(r.getId().getCodice(), dateToday,
		dateToday);
	Serializer serializer = getSerializer();
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, Status.OK);
    }

    @GET
    @Path("/mercati/{dallaData}/{allaData}")
    @Descriptions({ @Description(value = "Ottiene la lista dei mercati associati ad un utente", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getListaMercatiConData(@PathParam("dallaData") String dallaData, @PathParam("allaData") String allaData) {

	Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	String today = Utilities.getToday(false);
	Date dateToday = Utilities.parseDateString(today, false);
	log.debug("listMercatiPerResponsabileEData# Date={}", dateToday);
	Date dallaDataD = Utilities.parseDateString(dallaData, "yyyyMMdd");
	Date allaDataD = Utilities.parseDateString(allaData, "yyyyMMdd");
	List<IdentificativoDescrizioneBean> result = mercatiAppService.findGiornateByResponsabileDallaDataAllaData(r.getId().getCodice(), dallaDataD,
		allaDataD);
	Serializer serializer = getSerializer();
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, Status.OK);
    }

    @POST
    @Path("/mercati/ops/passa-a-fase")
    @Descriptions({ @Description(value = "Ottiene la lista dei mercati associati ad un utente", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response passaAFase(String json) throws MercatiAppException {

	Serializer serializer = getSerializer();
	GiornataFaseRequestBean bean = (GiornataFaseRequestBean) serializer.deserialize(json, GiornataFaseRequestBean.class);
	Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	mercatiAppService.updatePassaAFase(r.getId().getCodice(), bean.getIdGiornata(), bean.getIdFase());
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, Status.OK);
    }

    @GET
    @Path("/mercati/{idGiornata}")
    @Descriptions({ @Description(value = "Ottiene lo stato attuale della giornata di mercato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getStatoGiornata(@PathParam("idGiornata") Integer idGiornata) throws MercatiAppException {

	Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	boolean bloccoAccessoFuturo = this.comportamentiMercatiService.bloccaAccessoMercNelFuturo();
	log.debug("getStatoGiornata# Blocco accesso giornate nel futuro {}", bloccoAccessoFuturo);
	if (Boolean.TRUE.equals(bloccoAccessoFuturo) && mercatiAppService.isGiornataNelFuturo(idGiornata)) {
	    log.debug("getStatoGiornata# Giornata scelta ha data futura rispetto alla data di sistema..");
	    CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	    result.setCodice("OPS-0001");
	    result.setDescrizione("Non è possibile accedere alla giornata. Giornata scelta ha data futura rispetto alla data di sistema...");
	    String output = (String) this.getSerializer().serialize(result);
	    return rispostaWs(output, Status.INTERNAL_SERVER_ERROR);
	}
	if (!mercatiAppService.verificaMercatoPerOperatore(idGiornata, r.getId().getCodice())) {
	    CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	    result.setCodice("SEC-0001");
	    result.setDescrizione("Utente non abilitato a visualizzare l'informazione");
	    String output = (String) this.getSerializer().serialize(result);
	    return rispostaWs(output, Status.INTERNAL_SERVER_ERROR);
	}
	List<EtichettaApp> etichetteBlackList = layouttestiService.findEtichetteConPrefisso("BLACKLIST_");
	String key = String.valueOf(idGiornata);
	LockingObject lock = lockMap.get(key);
	if (null == lock) {
	    lock = new LockingObject(key);
	    lockMap.put(key, lock);
	}
	synchronized (lock) {
	    GiornataMercatoRestBean giornata = mercatiAppService.getGiornataMercatoRestBean(idGiornata);
	    if (!etichetteBlackList.isEmpty()) {
		giornata.setEtichettaApps(etichetteBlackList);
	    }
	    Serializer serializer = getSerializer();
	    String output = (String) serializer.serialize(giornata);
	    return rispostaWs(output, Status.OK);
	}
    }

    @POST
    @Path("/mercati/{idGiornata}/stato")
    @Descriptions({ @Description(value = "Apre o chiude la giornata passata", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response impostaStatoGiornata(@PathParam("idGiornata") Integer idGiornata, String jsonRequest) throws MercatiAppException {

	CodiceDescrizioneBean verifica = this.verificaAccessoAllaGiornata(idGiornata);
	if (verifica != null) {
	    String output = (String) this.getSerializer().serialize(verifica);
	    return rispostaWs(output, Status.INTERNAL_SERVER_ERROR);
	}
	String key = String.valueOf(idGiornata);
	LockingObject lock = lockMap.get(key);
	if (null == lock) {
	    lock = new LockingObject(key);
	    lockMap.put(key, lock);
	}
	Serializer serializer = getSerializer();
	synchronized (lock) {
	    ImpostaStatoGiornataRequest statoGiornata = (ImpostaStatoGiornataRequest) serializer.deserialize(jsonRequest,
		    ImpostaStatoGiornataRequest.class);
	    try {
		MercatipresenzeT giornata = this.mercatipresenzeTService.findById(new PkId(idGiornata));
		if (statoGiornata.getGiornataChiusa()) {
		    this.mercatipresenzeTService.closeMarketDay(giornata);
		} else {
		    this.mercatipresenzeTService.apriGiornoMercato(giornata);
		}
		return rispostaWs("", Status.OK);
	    } catch (Exception e) {
		return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	    }
	}
    }

    @POST
    @Path("/mercati/{idGiornata}/stato-posteggi/{idposteggio}")
    @Descriptions({ @Description(value = "Abilita o disabilita il posteggio per la giornata passata", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response impostaStatoPosteggio(@PathParam("idGiornata") Integer idGiornata, @PathParam("idposteggio") Integer idPosteggio,
	    String jsonRequest) throws MercatiAppException {

	CodiceDescrizioneBean verifica = this.verificaAccessoAllaGiornata(idGiornata);
	if (verifica != null) {
	    String output = (String) this.getSerializer().serialize(verifica);
	    return rispostaWs(output, Status.INTERNAL_SERVER_ERROR);
	}
	String key = String.valueOf(idGiornata);
	LockingObject lock = lockMap.get(key);
	if (null == lock) {
	    lock = new LockingObject(key);
	    lockMap.put(key, lock);
	}
	Serializer serializer = getSerializer();
	synchronized (lock) {
	    ImpostaStatoPosteggioRequest statoPosteggio = (ImpostaStatoPosteggioRequest) serializer.deserialize(jsonRequest,
		    ImpostaStatoPosteggioRequest.class);
	    ImpostaStatoPosteggioFlyweight request = new ImpostaStatoPosteggioFlyweight();
	    request.setIdGiornata(idGiornata);
	    request.setIdPosteggio(idPosteggio);
	    request.isAbilitato(statoPosteggio.getAbilitato());
	    if (Boolean.FALSE.equals(statoPosteggio.getAbilitato())) {
		request.setAnnotazione("Posteggio disabilitato automaticamente dall'app di gestione delle presenze");
	    }
	    this.mercatiDDisabilitatiService.impostaStatoPosteggio(request);
	    return rispostaWs("", Status.OK);
	}
    }

    @GET
    @Path("/mercati/{idGiornata}/stato-posteggi/disabilitati")
    @Descriptions({
	@Description(value = "Ritorna la lista dei posteggi disabilitati temporaneamente per quella giornata", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getPosteggiDisabilitati(@PathParam("idGiornata") Integer idGiornata) throws MercatiAppException {

	CodiceDescrizioneBean verifica = this.verificaAccessoAllaGiornata(idGiornata);
	if (verifica != null) {
	    String output = (String) this.getSerializer().serialize(verifica);
	    return rispostaWs(output, Status.INTERNAL_SERVER_ERROR);
	}
	String key = String.valueOf(idGiornata);
	LockingObject lock = lockMap.get(key);
	if (null == lock) {
	    lock = new LockingObject(key);
	    lockMap.put(key, lock);
	}
	synchronized (lock) {
	    PosteggiDisabilitatiResponse response = new PosteggiDisabilitatiResponse(this.mercatiDDisabilitatiService.findByIdGiornata(idGiornata));
	    Serializer serializer = getSerializer();
	    String output = (String) serializer.serialize(response);
	    return rispostaWs(output, Status.OK);
	}
    }

    private CodiceDescrizioneBean verificaAccessoAllaGiornata(int idGiornata) {

	Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	boolean bloccoAccessoFuturo = this.comportamentiMercatiService.bloccaAccessoMercNelFuturo();
	log.debug("Accesso bloccato alle giornate nel futuro? {}", bloccoAccessoFuturo);
	if (bloccoAccessoFuturo && this.mercatiAppService.isGiornataNelFuturo(idGiornata)) {
	    log.debug("La giornata scelta ha data futura rispetto alla data di sistema...");
	    CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	    result.setCodice("OPS-0001");
	    result.setDescrizione("Non è possibile accedere alla giornata. La giornata scelta ha data futura rispetto alla data di sistema...");
	    return result;
	}
	if (!mercatiAppService.verificaMercatoPerOperatore(idGiornata, r.getId().getCodice())) {
	    CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	    result.setCodice("SEC-0001");
	    result.setDescrizione("Utente non abilitato a visualizzare l'informazione");
	    return result;
	}
	return null;
    }

    @GET
    @Path("/mercati/costo-posteggio-spuntista/{idGiornata}/{idPosteggio}/{idAutorizzazione}")
    @Descriptions({ @Description(value = "Ottiene il costo calcolato del posteggio per lo spuntista", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getImportoPosteggioSpuntista(@PathParam("idGiornata") Integer idGiornata, @PathParam("idPosteggio") Integer idPosteggio,
	    @PathParam("idAutorizzazione") Integer idAutorizzazione) {

	Status retVal = Status.OK;
	CodiceDescrizioneBean result = null;
	try {
	    PosteggioInfoRestBean hlp = mercatiAppService.calcolaCostoPosteggio(idGiornata, idAutorizzazione, idPosteggio);
	    String output = (String) this.getSerializer().serialize(hlp);
	    return rispostaWs(output, retVal);
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	Serializer serializer = getSerializer();
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @GET
    @Path("/mercati/stato-pagamento-posteggio-spuntista/{idGiornata}/{idPosteggio}/{idAutorizzazione}")
    @Descriptions({ @Description(value = "Ottiene lo stato attuale della giornata di mercato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getStatoPagamentoPosteggioSpuntista(@PathParam("idGiornata") Integer idGiornata, @PathParam("idPosteggio") Integer idPosteggio,
	    @PathParam("idAutorizzazione") Integer idAutorizzazione) {

	Status retVal = Status.OK;
	CodiceDescrizioneBean result = null;
	try {
	    StatoPagamentoSpuntistaRestHelper hlp = mercatiAppService.verificaStatoPagamentoSpuntista(idGiornata, idAutorizzazione, idPosteggio);
	    String output = (String) this.getSerializer().serialize(hlp);
	    return rispostaWs(output, retVal);
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	Serializer serializer = getSerializer();
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/spuntista-presente")
    @Descriptions({ @Description(value = "Imposta uno spuntista (autorizzazione) come presente", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response spuntistaPresente(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	String s = "";
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    List<String> warning = mercatiAppService.spuntistaPresenteAndAggiungiCatMerceologicaReturnMessage(bean.getIdGiornata(),
		    bean.getIdAutorizzazione(), bean.getIdCategoria());
	    if (warning != null && !warning.isEmpty()) {
		s = StringUtils.join(warning.toArray(), "; ");
	    }
	    if (StringUtils.isNotBlank(s)) {
		result.setDescrizione(s);
	    }
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/spuntista-assente")
    @Descriptions({ @Description(value = "Imposta uno spuntista (autorizzazione) come assente (stato di default)", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response spuntistaAssente(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    mercatiAppService.spuntistaAssente(bean.getIdGiornata(), bean.getIdAutorizzazione());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/collega-posteggio-a-spuntista")
    @Descriptions({ @Description(value = "Collega un posteggio ad uno spuntista", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response collegaPosteggioASPuntista(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	String key = String.valueOf("collega-posteggio-a-spuntista-" + bean.getIdGiornata() + "-" + bean.getIdPosteggio());
	log.debug("collega-posteggio-a-spuntista {}", key);
	LockingObject lock = lockMap.get(key);
	if (null == lock) {
	    lock = new LockingObject(key);
	    lockMap.put(key, lock);
	}
	synchronized (lock) {
	    try {
		log.debug("collega-posteggio-a-spuntista prima di verificare giornata chiusa {}", key);
		checkIsGiornataChiusa(bean.getIdGiornata(), true);
		log.debug("collega-posteggio-a-spuntista giornata chiusa verificata - prima di collegare {}", key);
		Attivita catMerceologica = null;
		if (StringUtils.isNotBlank(bean.getIdCategoria())) {
		    AttivitaId id = new AttivitaId(bean.getIdCategoria());
		    catMerceologica = attivitaService.findById(id);
		    log.debug("collega-posteggio-a-spuntista giornata chiusa verificata - categoriamercaeologica {}", catMerceologica);
		}
		mercatiAppService.collegaPosteggioASpuntista(bean.getIdGiornata(), bean.getIdPosteggio(), bean.getIdAutorizzazione(),
			catMerceologica);
		log.debug("collega-posteggio-a-spuntista giornata chiusa verificata - collegato {}", key);
	    } catch (MercatiAppException e) {
		retVal = Status.INTERNAL_SERVER_ERROR;
		result = e.getErrore();
	    }
	}
	String output = (String) serializer.serialize(result);
	log.debug("collega-posteggio-a-spuntista giornata esco {}", key);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/pagamento-offline-posizione-debitoria")
    @Descriptions({ @Description(value = "Segna la posizione debitoria come pagata", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updatePosizioneDebitoriaASPuntistaSegnaPagato(String json) {

	IdentificativoDescrizioneBean bean = (IdentificativoDescrizioneBean) this.getSerializer().deserialize(json,
		IdentificativoDescrizioneBean.class);
	Status retVal = Status.OK;
	try {
	    nodoPagamentiService.updatePosizioneDebitoriaSegnaPagataOfflineSenzaRiferimentiPagamento(bean.getId());
	} catch (FunzioneBusinessRemotaException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	}
	return rispostaWs("", retVal);
    }

    @POST
    @Path("/mercati/ops/scollega-posteggio-da-spuntista")
    @Descriptions({ @Description(value = "scollega un posteggio da uno spuntista", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response scollegaPosteggioDaSPuntista(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    mercatiAppService.scollegaPosteggioDaSPuntista(bean.getIdGiornata(), bean.getIdPosteggio(), bean.getIdAutorizzazione());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/concessionario-presente")
    @Descriptions({ @Description(value = "Marca il concessionario del posteggio come presente", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response concessionarioPresente(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    checkAssegnazioniConcessionari(bean.getIdGiornata());
	    MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(bean.getIdGiornata()));
	    Map<Integer, PosteggiConcessioniHelper> pchs = mercatiService.findPosteggiMercatoAllaData(giorno.getMercato().getId().getCodice(),
		    giorno.getMercatoUso().getId().getCodice(), giorno.getDataRegistrazione());
	    mercatiAppService.concessionarioPresente(bean.getIdGiornata(), bean.getIdPosteggio(), pchs.get(bean.getIdPosteggio()));
	} catch (MercatiAppException e) {
	    log.error("ERRORE NEL METODO CONCESSIONARIO PRESENTE " + e.getMessage(), e);
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	} catch (Exception e) {
	    log.error("ERRORE GENERICO NEL METODO CONCESSIONARIO PRESENTE " + e.getMessage(), e);
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = new CodiceDescrizioneBean();
	    result.setCodice("CONC_PRES_GENERICO");
	    result.setDescrizione("Errore generico nel metodo concessionario presente");
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/concessionario-assente")
    @Descriptions({
	@Description(value = "Marca il concessionario del posteggio come assente. Il posteggio torna libero", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response concessionarioAssente(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    checkAssegnazioniConcessionari(bean.getIdGiornata());
	    mercatiAppService.concessionarioAssente(bean.getIdGiornata(), bean.getIdPosteggio());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/concessionari-presenti")
    @Descriptions({ @Description(value = "Marca il concessionario del posteggio come presente", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response concessionariPresenti(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    checkAssegnazioniConcessionari(bean.getIdGiornata());
	    mercatiAppService.concessionariPresenti(bean.getIdGiornata(), bean.getIdPosteggi());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/concessionari-assenti")
    @Descriptions({
	@Description(value = "Marca il concessionario del posteggio come assente. Il posteggio torna libero", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response concessionariAssenti(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    checkAssegnazioniConcessionari(bean.getIdGiornata());
	    mercatiAppService.concessionariAssenti(bean.getIdGiornata(), bean.getIdPosteggi());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/termina-appello")
    @Descriptions({ @Description(value = "Termina la fase di appello", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response terminaAppello(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    mercatiAppService.terminaAppello(bean.getIdGiornata());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/riapri-appello")
    @Descriptions({ @Description(value = "Riapre l'appello", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response riapriAppello(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    mercatiAppService.riapriAppello(bean.getIdGiornata());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/imposta-categoria-merceologica")
    @Descriptions({ @Description(value = "Imposta la categoria merceologica di uno spuntista per il m ercato indicato", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response impostaCategoriaMerceologica(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    mercatiAppService.impostaCategoriaMerceologica(bean.getIdGiornata(), bean.getIdAutorizzazione(), bean.getIdCategoria());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/rifiuta-posteggio")
    @Descriptions({ @Description(value = "Indica che lo spuntista ha riufiutato un posteggio", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response rifiutaPosteggio(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    mercatiAppService.rifiutaPosteggio(bean.getIdGiornata(), bean.getIdAutorizzazione(), bean.getIdPosteggio());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/annulla-rifiuta-posteggio")
    @Descriptions({ @Description(value = "annulla il rifiuto di un posteggio", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response annullaRifiutaPosteggio(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    mercatiAppService.annullaRifiutaPosteggio(bean.getIdGiornata(), bean.getIdAutorizzazione());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/rollback-posteggio-a-spuntista")
    @Descriptions({ @Description(value = "annulla il rifiuto di un posteggio", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response rollbackPosteggioASPuntista(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    mercatiAppService.rollbackPosteggioASPuntista(bean.getIdGiornata(), bean.getIdPosteggio());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/rollback-posteggio-a-spuntista-precedente")
    @Descriptions({ @Description(value = "rollback-posteggio-a-spuntista-precedente", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response rollbackPosteggioASPuntistaPrecedente(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    mercatiAppService.rollbackPosteggioASPuntistaPrecedente(bean.getIdGiornata(), bean.getIdPosteggio(), bean.getIdAutorizzazione());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/ricerca")
    @Descriptions({ @Description(value = "ricerca anagrafe", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response ricerca(String json) {

	Serializer serializer = getSerializer();
	RicercaAnagrafeRestBean bean = (RicercaAnagrafeRestBean) serializer.deserialize(json, RicercaAnagrafeRestBean.class);
	Status retVal = Status.OK;
	List<GiornataMercatoSpuntistaRestBean> spuntisti = new ArrayList<GiornataMercatoSpuntistaRestBean>();
	try {
	    spuntisti = mercatiAppService.ricercaAnagrafe(bean.getIdGiornata(), bean.getTesto(), bean.getNumMaxRecords());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	}
	String output = (String) serializer.serialize(spuntisti);
	return rispostaWs(output, retVal);
    }

    @GET
    @Path("/mercati/ricerca-cf/{testo}")
    @Descriptions({ @Description(value = "ricerca anagrafe per codice fiscale", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response ricercaCf(@PathParam("testo") String testo) {

	Serializer serializer = getSerializer();
	Status retVal = Status.OK;
	AnagraferestBean anagraferestBean = new AnagraferestBean();
	try {
	    anagraferestBean = mercatiAppService.ricercaAnagrafeCFI(testo);
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	}
	String output = (String) serializer.serialize(anagraferestBean);
	return rispostaWs(output, retVal);
    }

    @GET
    @Path("/mercati/ricerca-comuni/{testo}")
    @Descriptions({ @Description(value = "ricerca comuni", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response ricercaComuni(@PathParam("testo") String testo) {

	Serializer serializer = getSerializer();
	Status retVal = Status.OK;
	List<CodiceDescrizioneBean> result = new ArrayList<CodiceDescrizioneBean>();
	try {
	    result = mercatiAppService.ricercaComuni(testo);
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/insert-nuovo-spuntista")
    @Descriptions({ @Description(value = "annulla il rifiuto di un posteggio", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response inserisceNuovoSpuntista(String json) {

	Serializer serializer = getSerializer();
	PostRestNuovoSpuntistaBean postRest = (PostRestNuovoSpuntistaBean) serializer.deserialize(json, PostRestNuovoSpuntistaBean.class);
	Status retVal = Status.OK;
	GiornataMercatoSpuntistaRestBean result = new GiornataMercatoSpuntistaRestBean();
	try {
	    checkIsGiornataChiusa(postRest.getIdGiornata(), true);
	    Integer idAut = mercatiAppService.inserisceNuovoSpuntista(postRest);
	    result = mercatipresenzeDService.findSpuntistiGiornataMercatoRest(postRest.getIdGiornata(), idAut);
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    String output = (String) serializer.serialize(e.getErrore());
	    return rispostaWs(output, retVal);
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/pagamento-verificato")
    @Descriptions({ @Description(value = "aggiorna a true il pagamento", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response pagamentoVerificato(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    mercatiAppService.updatePagato(bean.getIdGiornata(), bean.getIdAutorizzazione(), true);
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/pagamento-non-verificato")
    @Descriptions({ @Description(value = "aggiorna a false il pagamento", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response pagamentoNonVerificato(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    mercatiAppService.updatePagato(bean.getIdGiornata(), bean.getIdAutorizzazione(), false);
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/aggiorna-note-autorizzazione")
    @Descriptions({ @Description(value = "aggiorna le note di una autorizzazione", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response aggiornaNoteAutorizzazione(String json) {

	Serializer serializer = getSerializer();
	AutorizzazionePostRestBean bean = (AutorizzazionePostRestBean) serializer.deserialize(json, AutorizzazionePostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    //checkIsGiornataChiusa(bean.getIdGiornata(), true); non c'è bisogno di controllare la chiusura della giornata in questo caso 
	    mercatiAppService.updateNoteAutorizzazione(bean.getIdAutorizzazione(), bean.getNote());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/imposta-incaricato-vendita")
    @Descriptions({ @Description(value = "Imposta il collaboratore sulla presenza dello spuntista", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response impostaIncaricatoVendita(String json) {

	Serializer serializer = getSerializer();
	PostRestBean bean = (PostRestBean) serializer.deserialize(json, PostRestBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    mercatiAppService.impostaIncaricatoVendita(bean.getIdGiornata(), bean.getIdAutorizzazione(), bean.getIdAnagrafe());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/modifica-fascia-giornata")
    @Descriptions({ @Description(value = "Modifica la fascia giornaliera impostata per la giornata", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateModificaFascia(String json) {

	Serializer serializer = getSerializer();
	ModificaFasciaMercatoBean bean = (ModificaFasciaMercatoBean) serializer.deserialize(json, ModificaFasciaMercatoBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    mercatiAppService.modificaFasciaGiornata(bean.getIdGiornata(), bean.getIdFasciaMercato());
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = e.getErrore();
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @GET
    @Path("/mercati/{idGiornata}/info-autorizzazioni/{idAutorizzazione}")
    @Descriptions({ @Description(value = "Ottiene le informazioni su autorizzazione passata", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getInfoAutorizzazione(@PathParam("idGiornata") Integer idGiornata, @PathParam("idAutorizzazione") Integer idAutorizzazione) {

	Serializer serializer = getSerializer();
	Status retVal = Status.OK;
	InfoAutorizzazioneRestBean result = new InfoAutorizzazioneRestBean();
	try {
	    result = mercatiAppService.infoAutorizzazione(idAutorizzazione, idGiornata);
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @GET
    @Path("/mercati/{idgiornata}/blacklists")
    @Descriptions({
	@Description(value = "Restituisce le lista degli identificativi delle autorizzazioni presenti nella blacklist", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response blacklistByIdGiornata(@PathParam("idgiornata") Integer idGiornata) {

	BlackListWrapper result = this.blacklistAutorizzazioniService.findAutorizzazioniInBlackListAttivePerGiornata(idGiornata,
		filtroBlackListDefault());
	String output = (String) getSerializer().serialize(result);
	return rispostaWs(output, Status.OK);
    }

    @GET
    @Path("/mercati/info-autorizzazioni/{idAutorizzazione}/blacklists")
    @Descriptions({
	@Description(value = "Restituisce le lista degli identificativi delle autorizzazioni presenti nella blacklist", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response blacklistByIdAutorizzazione(@PathParam("idAutorizzazione") Integer idAutorizzazione) {

	Serializer serializer = getSerializer();
	BlackListDettaglio result = mercatiAppService.findDettaglioBlackListAutorizzazioneEGiornata(idAutorizzazione, null, filtroBlackListDefault());
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, Status.OK);
    }

    @GET
    @Path("/mercati/{idgiornata}/info-autorizzazioni/{idAutorizzazione}/blacklists")
    @Descriptions({
	@Description(value = "Restituisce le lista degli identificativi delle autorizzazioni presenti nella blacklist", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response blacklistByIdGiornataEAutorizzazione(@PathParam("idgiornata") Integer idgiornata,
	    @PathParam("idAutorizzazione") Integer idAutorizzazione) {

	Serializer serializer = getSerializer();
	BlackListDettaglio result = mercatiAppService.findDettaglioBlackListAutorizzazioneEGiornata(idAutorizzazione, idgiornata,
		filtroBlackListDefault());
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, Status.OK);
    }

    private BlackListContestoEnum[] filtroBlackListDefault() {

	BlackListContestoEnum[] filtro = new BlackListContestoEnum[1];
	filtro[0] = BlackListContestoEnum.PRESENZE; // NON VOGLIONO LA BLACKLIST PER LA BOLLETTAZIONE
	return filtro;
    }

    @POST
    @Path("/mercati/ops/nuovo-incaricato-vendita")
    @Descriptions({ @Description(value = "", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response nuovoIncaricatoVendita(String json) {

	Serializer serializer = getSerializer();
	PostAnagrafeRestBean bean = (PostAnagrafeRestBean) serializer.deserialize(json, PostAnagrafeRestBean.class);
	Status retVal = Status.OK;
	String output = "";
	try {
	    checkIsGiornataChiusa(bean.getIdGiornata(), true);
	    AutorizzazioniSoggetti autSogg = mercatiAppService.insertAnagrafeInAutorizzazioniSoggettiAndUpdateSuPosteggio(bean.getCodiceFiscale(),
		    bean.getNome(), bean.getCognome(), bean.getIdAutorizzazione(), bean.getIdGiornata());
	    AnagraferestBean result = new AnagraferestBean();
	    RestBeanHelper.populateAnagrafeRestBeanDaAnagrafe(result, autSogg.getAnagrafe());
	    output = (String) serializer.serialize(result);
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    output = (String) serializer.serialize(e.getErrore());
	}
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/logout")
    @Descriptions({ @Description(value = "", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response logout() {

	log.debug("logout# start.....");
	CodiceDescrizioneBean bean = new CodiceDescrizioneBean();
	Status retVal = Status.OK;
	String output = "";
	try {
	    externalDBResolver.invalidateToken(ORMHelper.getToken());
	} catch (Exception e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    bean.setDescrizione(e.getMessage());
	}
	bean.setCodice(retVal.name());
	output = (String) this.getSerializer().serialize(bean);
	log.debug("logout# end.....");
	return rispostaWs(output, retVal);
    }

    @POST
    @Path("/mercati/ops/upload")
    @Descriptions({ @Description(value = "", target = DocTarget.METHOD) })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response uploadFile(MultipartBody body) {

	log.debug("uploadFile# start.....");
	List<IdentificativoDescrizioneBean> result = new ArrayList<IdentificativoDescrizioneBean>();
	Status retVal = Status.OK;
	String output = "";
	try {
	    result = mercatiAppService.uploadFile(body.getAllAttachments());
	    output = (String) this.getSerializer().serialize(result);
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    output = (String) this.getSerializer().serialize(e.getErrore());
	}
	log.debug("uploadFile# end.....");
	return rispostaWs(output, retVal);
    }

    @GET
    @Path("/mercati/info-utente")
    @Descriptions({ @Description(value = "Ottiene le informazioni dell'utente loggato", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getInfoResponsabili() {

	Serializer serializer = getSerializer();
	Status retVal = Status.OK;
	ResponsabileRestBean result = new ResponsabileRestBean();
	try {
	    result = mercatiAppService.infoUtente();
	} catch (MercatiAppException e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @GET
    @Path("/download/{uuid}")
    @Descriptions({ @Description(value = "Scarica l'allegato identificato dal uuid", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_OCTET_STREAM + "; charset=UTF-8")
    public Response download(@PathParam("uuid") String uuid) {

	Integer codiceOggetto = oggettiMetadatiService.findByChiaveEValore(WebConstants.OGGETTI_FILE_UID, uuid);
	if (codiceOggetto == null) {
	    ResponseBuilder responseBuilder = Response.serverError();
	    return responseBuilder.build();
	}
	InputStream is = oggettiService.getOggettoAsInputStream(codiceOggetto);
	Oggetti objLazy = oggettiService.findByIdLazy(new PkId(codiceOggetto));
	String cType = contenttypesService.findMimeTypeByFileName(objLazy.getNomefile());
	ResponseBuilder responseBuilder = Response.ok(is);
	if (StringUtils.isNotBlank(cType)) {
	    responseBuilder.header("Content-Type", cType);
	}
	responseBuilder.header("Pragma", "public");
	responseBuilder.header("Cache-Control", "max-age=0");
	responseBuilder.header("Content-transfer-encoding", "binary");
	responseBuilder.header(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	responseBuilder.header(ACCESS_CONTROL_ALLOW_HEADERS, CONTENT_TYPE_AUTHORIZATION);
	return responseBuilder.build();
    }

    @POST
    @Path("/anagrafe/ops/update-cf-anagrafe")
    @Descriptions({ @Description(value = "aggiorna a true il pagamento", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateCfAnagrafe(String json) {

	Serializer serializer = getSerializer();
	IdentificativoDescrizioneBean bean = (IdentificativoDescrizioneBean) serializer.deserialize(json, IdentificativoDescrizioneBean.class);
	Status retVal = Status.OK;
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	try {
	    Integer idAutSpuntista = bean.getId();
	    String cf = bean.getDescrizione();
	    mercatiAppService.updateCfAnagrafeSpuntista(idAutSpuntista, cf);
	} catch (Exception e) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    @GET
    @Path("/mercati/{id_giornata}/pagamenti")
    @Descriptions({
	@Description(value = "Il metodo verifica gli stati dei pagamenti di tutti i soggetti presenti sulla giornata di mercato passata", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response verificaPagamentiByIdGiornata(@PathParam("id_giornata") Integer idgiornata) throws JAXBException {

	StatiPosizioniDebitorieGiornataMercatoBean stati = this.mercatipresenzeDService.verificaStatoPosizioniDebitoriePerGiornata(idgiornata);
	String str = Utilities.marshalJsonObject(stati, stati.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	return rispostaWs(str, Status.OK);
    }

    @GET
    @Path("/mercati/{id_giornata}/pagamenti/{id_autorizzazione}")
    @Descriptions({
	@Description(value = "Il metodo verifica gli stati dei pagamenti di tutti dei pagamenti dell'autorizzazione segnata presente sulla giornata di mercato passata", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response verificaPagamentiByIdGiornataAndAutorizzazione(@PathParam("id_giornata") Integer idgiornata,
	    @PathParam("id_autorizzazione") Integer idautorizzazione) throws JAXBException {

	List<Integer> auts = new ArrayList<Integer>();
	auts.add(idautorizzazione);
	StatiPosizioniDebitorieGiornataMercatoBean stati = this.mercatipresenzeDService.verificaStatoPosizioniDebitoriePerAutorizzazioni(idgiornata,
		auts);
	String str = null;
	Status retVal = Status.OK;
	if (stati.getConcessionari().isEmpty() && stati.getSpuntisti().isEmpty()) {
	    // Non ho trovato pagamenti 
	    str = "{}"; // empty object
	} else {
	    List<PagamentoModel> ret = new ArrayList<PagamentoModel>();
	    ret.addAll(stati.getConcessionari());
	    ret.addAll(stati.getSpuntisti());
	    if (ret.size() > 1) {
		// errore??
		retVal = Status.INTERNAL_SERVER_ERROR;
		EsitoRestBean esito = new EsitoRestBean();
		esito.setCodice("500");
		esito.setDescrizione("Sono presenti più stati di pagamento");
		str = Utilities.marshalJsonObject(ret.get(0), EsitoRestBean.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    } else {
		// ne ho solo uno dovrebbe essere la situazione
		str = Utilities.marshalJsonObject(ret.get(0), PagamentoModel.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    }
	}
	return rispostaWs(str, retVal);
    }

    @GET
    @Path("/info-posizione-debitoria/{idPagamento}")
    @Descriptions({
	@Description(value = "Il metodo torna il dettaglio di una posizione debitoria, se presente, associata all'id della posizione debitoria", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response verificaDettaglioPosizioneDebitoria(@PathParam("idPagamento") Integer idDettPosizioneDebitoria)
	    throws FunzioneBusinessRemotaException, JAXBException {

	Status retVal = Status.OK;
	try {
	    this.nodoPagamentiService.aggiornaStatoPagamentoByIdDettPosizioneDebitoria(idDettPosizioneDebitoria);
	} catch (Exception e) {
	    log.error("#verificaDettaglioPosizioneDebitoria: errore nella verifica della posizione debitoria.  {}" + e.getMessage(), e);
	}
	PosizioneDebitoriaModelEsteso posDebResponse = nodoPagamentiService.getPosizioneDebitoriaModelEsteso(idDettPosizioneDebitoria);
	String str = Utilities.marshalJsonObject(posDebResponse, PosizioneDebitoriaModelEsteso.class, false, Utilities.JAXB_ENCODING_UTF_8);
	return rispostaWs(str, retVal);
    }

    @GET
    @Path("/mercati/mappa-mercato/{idGiornata}")
    @Descriptions({
	@Description(value = "Il metodo ritorna un base64 immagine ed eventuali altre info (es coordinate per posteggio)", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response mappaMercato(@PathParam("idGiornata") Integer idGiornata) throws MercatiAppException {

	MappaMercatoBean result = mercatiAppService.getMappaMercato(idGiornata);
	Serializer serialize = getSerializer();
	String output = (String) serialize.serialize(result);
	return rispostaWs(output, Status.OK);
    }

    @GET
    @Path("/mercati/verfica-assenze-giornata/{idGiornata}")
    @Descriptions({ @Description(value = "Il metodo ritorna il percentuale di assenze", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response verificaAssenzeGiornata(@PathParam("idGiornata") Integer idGiornata) throws MercatiAppException {

	MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(idGiornata));
	List<MercatipresenzeD> mercatiPresD = mercatipresenzeDService.findByMercatiPresenzeT(giorno.getId().getCodice());
	List<MercatipresenzeDDTO> listaPosteggi = mercatipresenzeDService.findListaPosteggi(giorno);
	Mercati mercato = mercatiService.findById(giorno.getMercato().getId());
	BigDecimal total = new BigDecimal(0);
	BigDecimal total2 = new BigDecimal(listaPosteggi.size());
	boolean attivaGiornateNulle = comportamentiMercatiService.isAttivaGiornateNulle();
	BigDecimal percentualeAssenze = new BigDecimal(100);
	if (attivaGiornateNulle) {
	    String assenzeTipoCalcolo = mercato.getAssenzeTipoCalcolo();
	    if (assenzeTipoCalcolo != null) {
		BigDecimal one = new BigDecimal(1);
		BigDecimal numeroAssenze = new BigDecimal(0);
		switch (MercatiAssenzeTipoCalcoloEnum.fromValue(assenzeTipoCalcolo)) {
		    case TOTALITA_POSTEGGI:
			total = new BigDecimal(mercatiPresD.size());
			numeroAssenze = total;
			for (MercatipresenzeD mercatipresenzeD : mercatiPresD) {
			    if ((mercatipresenzeD.getOccupante() != null && mercatipresenzeD.getOccupante().getId().getCodice() != null)
				    || mercatipresenzeD.isSpuntista()) {
				numeroAssenze = numeroAssenze.subtract(one);
			    }
			}
			if (numeroAssenze.intValue() > 0) {
			    percentualeAssenze = numeroAssenze.divide(total2, 4, RoundingMode.HALF_UP).scaleByPowerOfTen(2);
			} else if (numeroAssenze.intValue() == 0) {
			    percentualeAssenze = new BigDecimal(0);
			}
			break;
		    case SOLO_POSTEGGI_IN_CONCESSIONE:
			int i = 0;
			// for (MercatipresenzeD mercatipresenzeD : mercatiPresD) {
			//			if (mercatipresenzeD.getId() != null && mercatipresenzeD.getId().getCodice() != null
			//				&& mercatipresenzeD.getConcessionario() != null && mercatipresenzeD.getConcessionario().getId() != null
			//				&& mercatipresenzeD.getRegistrazioneConcessionario() != null
			//				&& mercatipresenzeD.getConcessionario().getId().getCodice() != null) {
			//			    i++;
			//			}
			for (MercatipresenzeDDTO mercatipresenzeDDTO : listaPosteggi) {
			    if (mercatipresenzeDDTO.getConcessionario() != null && mercatipresenzeDDTO.getConcessionario().getId() != null
				    && mercatipresenzeDDTO.getConcessionario().getId().getCodice() != null) {
				i++;
			    }
			}
			// }
			int posteggiConsessione = i;
			total = new BigDecimal(posteggiConsessione);
			numeroAssenze = total;
			for (MercatipresenzeD mercatipresenzeD : mercatiPresD) {
			    if (mercatipresenzeD.getOccupante() != null && mercatipresenzeD.getOccupante().getId().getCodice() != null
				    && mercatipresenzeD.getConcessionario() != null && mercatipresenzeD.getConcessionario().getId() != null
				    && mercatipresenzeD.getConcessionario().getId().getCodice() != null
				    && mercatipresenzeD.isConcessionarioPresente()) {
				numeroAssenze = numeroAssenze.subtract(one);
			    }
			}
			if (numeroAssenze.intValue() > 0) {
			    percentualeAssenze = numeroAssenze.divide(total, 4, RoundingMode.HALF_UP).scaleByPowerOfTen(2);
			} else if (numeroAssenze.intValue() == 0) {
			    percentualeAssenze = new BigDecimal(0);
			}
			break;
		    default:
			break;
		}
	    } else {
		log.error("Errore giornate nulle comportamenti: tipo calcolo non configurato");
	    }
	}
	Serializer serialize = getSerializer();
	PercentualeAssenzeBean res = new PercentualeAssenzeBean(percentualeAssenze, mercato.getAssenzePercentuale());
	String output = (String) serialize.serialize(res);
	return rispostaWs(output, Status.OK);
    }

    @POST
    @Path("/mercati/giornata-nulla/{idGiornata}")
    @Descriptions({ @Description(value = "Il metodo segna il girono mercato come nullo", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response giornataNulla(@PathParam("idGiornata") Integer idGiornata, String json) throws MercatiAppException {

	Serializer serialize = getSerializer();
	AnnullaGiornataRequest req = (AnnullaGiornataRequest) serialize.deserialize(json, AnnullaGiornataRequest.class);
	boolean isGiornataNulla;
	if (req.getIsisAnnulla()) {
	    isGiornataNulla = mercatipresenzeTService.segnaGiornataNulla(idGiornata, req.getNote());
	} else {
	    isGiornataNulla = mercatipresenzeTService.segnaGiornataNonNulla(idGiornata);
	}
	return rispostaWs("{\"status\":\"" + isGiornataNulla + "\"}", Status.OK);
    }

    @GET
    @Path("/mercati/verifica-giornata-nulla/{idGiornata}")
    @Descriptions({ @Description(value = "Il metodo segna il girono mercato come nullo", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response verificaGiornataNulla(@PathParam("idGiornata") Integer idGiornata) throws MercatiAppException {

	MercatipresenzeT mercatiPresenzeT = mercatipresenzeTService.findById(new PkId(idGiornata));
	boolean isGiornataNulla = (mercatiPresenzeT.getFlagGiornataNulla() != null && mercatiPresenzeT.getFlagGiornataNulla() == 1) ? true : false;
	return rispostaWs("{\"status\":\"" + isGiornataNulla + "\"}", Status.OK);
    }

    @GET
    @Path("/mercati/verifica-attiva-regola-giornata-nulla/{idGiornata}")
    @Descriptions({ @Description(value = "Il metodo verifica se attiva la regola per giornate nulle", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response verificaAttivaRegolaGiornataNulla(@PathParam("idGiornata") Integer idGiornata) throws MercatiAppException {

	boolean attivaGiornateNulle = comportamentiMercatiService.isAttivaGiornateNulle();
	MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(idGiornata));
	Mercati mercato = mercatiService.findById(giorno.getMercato().getId());
	String assenzeTipoCalcolo = mercato.getAssenzeTipoCalcolo();
	if (attivaGiornateNulle && mercato.getAssenzePercentuale() != null && !StringUtils.equals(assenzeTipoCalcolo, "Seleziona")) {
	    return rispostaWs("{\"isAttivaRegola\":\"" + true + "\"}", Status.OK);
	} else {
	    return rispostaWs("{\"isAttivaRegola\":\"" + false + "\"}", Status.OK);
	}
    }

    @OPTIONS
    @Path("/{var:.*}")
    public Response defaultOptions() {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	builder.header(ACCESS_CONTROL_ALLOW_HEADERS, CONTENT_TYPE_AUTHORIZATION);
	builder.header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
	builder.status(Status.OK);
	return builder.build();
    }

    protected Response rispostaWs(Object entity, Status returnStatus) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	builder.header(ACCESS_CONTROL_ALLOW_HEADERS, CONTENT_TYPE_AUTHORIZATION);
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
	return builder.build();
    }

    protected Serializer getSerializer() {

	Serializer serializer = new JsonSerializer();
	serializer.setClassPropertyFilterHandler(new ClassPropertyFilterHandler() {

	    @Override
	    public ClassPropertyFilter getClassPropertyFilterByClass(@SuppressWarnings("rawtypes") Class arg0) {

		if (arg0 == BlackList.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(BlackList.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == FasciaMercatoBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(FasciaMercatoBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == BlackListOggetti.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(BlackListOggetti.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == BlackListOggettoDettaglio.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(BlackListOggettoDettaglio.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == BlackListDettaglio.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(BlackListDettaglio.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == BlackListIdentificativi.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(BlackListIdentificativi.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == RigaImporto.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(RigaImporto.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == PosteggioImportoHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PosteggioImportoHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == Conti.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(Conti.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == PosteggioInfoRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PosteggioInfoRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == AutorizzazioniCsiRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniCsiRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == PagamentiMercatoPosizDebRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PagamentiMercatoPosizDebRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == CodiceDescrizioneBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(CodiceDescrizioneBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == GiornataMercatoFaseRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(GiornataMercatoFaseRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == GiornataMercatoSpuntistaRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(GiornataMercatoSpuntistaRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == AutorizzazioneRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioneRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == ResponsabileRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(ResponsabileRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == AnagraferestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AnagraferestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == GiornataMercatoRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(GiornataMercatoRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == GiornataMercatoPosteggioRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(GiornataMercatoPosteggioRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == CodiceNumericoDescrizioneBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(CodiceNumericoDescrizioneBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == AutorizzazioniConcessioniRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniConcessioniRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == ChiaveValoreBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(ChiaveValoreBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == MercatiRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MercatiRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == AnagrafeDTO.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AnagrafeDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == MercatiDDTO.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MercatiDDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == AutorizzazioniDTO.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == MercatipresenzeDDTO.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MercatipresenzeDDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == PkId.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PkId.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == MercatiPresenzeStoricoRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MercatiPresenzeStoricoRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == GiorniMercatoPresenzeRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(GiorniMercatoPresenzeRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == AutorizzazioniConcessioniPresenzeRestHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniConcessioniPresenzeRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == PosteggioLiberoHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PosteggioLiberoHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == PagamentiMercatiHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PagamentiMercatiHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == PagamentiMercatiDaEffettuareHelper.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PagamentiMercatiDaEffettuareHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == StradarioDTO.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(StradarioDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == DocumentiRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(DocumentiRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == InfoAutorizzazioneRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(InfoAutorizzazioneRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == MappaMercatoBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MappaMercatoBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == PosteggioMercatoBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PosteggioMercatoBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == ConcessionarioRestBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(ConcessionarioRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == StatiPosizioniDebitorieGiornataMercatoBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(StatiPosizioniDebitorieGiornataMercatoBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == StatoResponseType.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(StatoResponseType.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == PosizioneDebitoriaResponseType.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PosizioneDebitoriaResponseType.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == PosizioneDebitoriaModel.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PosizioneDebitoriaModel.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == PosteggiDisabilitatiResponse.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PosteggiDisabilitatiResponse.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == ImpostaStatoPosteggioRequest.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PosteggiDisabilitatiResponse.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == ImpostaStatoGiornataRequest.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PosteggiDisabilitatiResponse.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		if (arg0 == PercentualeAssenzeBean.class) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PercentualeAssenzeBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { CLASS, UNIQUE_ID });
		    return cpf;
		}
		return null;
	    }
	});
	return serializer;
    }

    private boolean checkIsGiornataChiusa(Integer idGiornata, boolean rilanciaEccezione) throws MercatiAppException {

	boolean giornataMercatoChiusa = mercatipresenzeTService.isGiornataMercatoChiusa(idGiornata);
	if (giornataMercatoChiusa && rilanciaEccezione) {
	    throw new MercatiAppException("999", "Non è possibile apportare modifiche ad una giornata chiusa");
	}
	return giornataMercatoChiusa;
    }

    private boolean checkAssegnazioniConcessionari(Integer idGiornata) throws MercatiAppException {

	IdentificativoDescrizioneBean check = mercatipresenzeTService
		.checkInserimentoConcessionariPerGiornata(mercatipresenzeTService.findById(new PkId(idGiornata)));
	if (check.getId().equals(200)) {
	    return true;
	}
	throw new MercatiAppException(String.valueOf(check.getId()), check.getDescrizione());
    }
}
