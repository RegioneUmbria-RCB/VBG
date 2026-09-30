package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Tipiapertura;
import it.gruppoinit.pal.gp.core.domain.Tipiorario;
import it.gruppoinit.pal.gp.core.domain.Tipiorariodettaglio;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.VwAlberoproc;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeAvvisiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ScambioPosteggioHelper;
import it.gruppoinit.pal.gp.core.domain.helper.menu.v2.MenuHolder;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocCommand;
import it.gruppoinit.pal.gp.core.domain.web.AtecoCommand;
import it.gruppoinit.pal.gp.core.domain.web.JSONStradario;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.IstanzecollegateService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatoPagamentiBreveJson;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatoPagamentiDettaglioJson;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AtecoService;
import it.gruppoinit.pal.gp.core.service.ClmenuService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.TipiorarioService;
import it.gruppoinit.pal.gp.core.service.VwAlberoprocService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
public class JSONController extends BaseController {

    @Autowired(required = false)
    private CacheManager cacheManager;
    @Autowired
    private ClmenuService clmenuService;
    @Autowired
    private ResponsabilicomuniService responsabilicomuniService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private VwAlberoprocService vwAlberoprocService;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private AtecoService atecoService;
    @Autowired
    private TipiorarioService tipiorarioService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private IstanzeprocedimentiService istanzeprocedimentiService;
    @Autowired
    private Istanzedyn2modellitService istanzedyn2modellitService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private IstanzeallegatiService istanzeallegatiService;
    @Autowired
    private IstanzeoneriService istanzeoneriService;
    @Autowired
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private IstanzerichiedentiService istanzerichiedentiService;
    @Autowired
    private IstanzecollegateService istanzecollegateService;
    @Autowired
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;

    @RequestMapping
    public ModelAndView getComboComuni(@RequestParam(value = "comune") String comune, @RequestParam("mostraTutti") Boolean mostraTutti,
	    @RequestParam("readOnly") Boolean readOnly, HttpServletResponse response) throws IOException {

	Map model = new HashMap();
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	if (null == responsabile) {
	    throw new SecurityException("L'utente non è loggato");
	}
	List<Responsabilicomuni> listaComuni = responsabilicomuniService.findByOperatore(responsabile);
	StringBuilder _tuttiIComuni = new StringBuilder("");
	String tuttiIComuni = "";
	List<Comuni> comunis = new ArrayList<Comuni>(0);
	String codiceComune = "";
	if (mostraTutti.booleanValue()) {
	    for (Responsabilicomuni responsabilicomuni : listaComuni) {
		codiceComune = responsabilicomuni.getId().getCodicecomune();
		_tuttiIComuni.append(codiceComune).append(",");
	    }
	    tuttiIComuni = _tuttiIComuni.toString();
	    if (!tuttiIComuni.equals("")) {
		tuttiIComuni = tuttiIComuni.substring(0, tuttiIComuni.length() - 1);
	    }
	}
	for (Responsabilicomuni responsabilicomuni : listaComuni) {
	    codiceComune = responsabilicomuni.getId().getCodicecomune();
	    Comuni comuneBean = comuniService.findById(codiceComune);
	    comunis.add(comuneBean);
	}
	List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	boolean operatoreHaComuniConfigurati = true;
	if (!(comuniassociatis == null || comuniassociatis.size() == 0)) {
	    if (listaComuni.size() == 0) {
		operatoreHaComuniConfigurati = false;
	    }
	}
	model.put("operatoreHaComuniConfigurati", operatoreHaComuniConfigurati);
	model.put("tuttiIComuni", tuttiIComuni);
	model.put("listaComuni", comunis);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getAlberoproc() {

	Map model = new HashMap();
	List<AlberoprocCommand> list = alberoprocService.findAlberoprocHierarchy(null);
	model.put("identifier", "id");
	model.put("label", "name");
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getAlberoprocPerRuoli() {

	Map model = new HashMap();
	Integer codiceResponsabile = getCurrentlyAuthenticatedUserDetails().getId().getCodice();
	List<AlberoprocCommand> list = alberoprocService.findAlberoprocHierarchyPerRuoli(codiceResponsabile);
	model.put("identifier", "id");
	model.put("label", "name");
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getAlberoprocMini(@RequestParam("id") Integer id, HttpServletResponse response) {

	Map model = new HashMap();
	List<AlberoprocCommand> list = alberoprocService.findAlberoprocHierarchy(id);
	model.put("identifier", "id");
	model.put("label", "name");
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getAlberoprocPec() {

	Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_COD_ALBERO_ROOT_PROTOCOLL);
	Integer codiceAlberoproc = null;
	if (vp != null) {
	    String valore = StringUtils.defaultString(vp.getValore()).trim();
	    if (StringUtils.isNotBlank(valore)) {
		if (Utilities.isInteger(valore)) {
		    codiceAlberoproc = Integer.valueOf(valore);
		}
	    }
	}
	Map model = new HashMap();
	List<AlberoprocCommand> list = alberoprocService.findAlberoprocHierarchy(codiceAlberoproc);
	model.put("identifier", "id");
	model.put("label", "name");
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getAlberoprocHelper(@RequestParam("id") Integer id,
	    @RequestParam(value = "hideDisabled", required = false) Boolean hideDisabled, HttpServletResponse response) throws IOException {

	Map model = new HashMap();
	if (id != null) {
	    Alberoproc alberoproc = alberoprocService.findByIdAndCurrentSoftware(id, hideDisabled);
	    if (alberoproc != null) {
		VwAlberoproc vwalberoproc = vwAlberoprocService.findById(alberoproc.getId());
		AlberoprocHelper alberoprocHelper = alberoprocService.findAlberoprocHelper(alberoproc);
		model.put("id", id);
		model.put("desc", vwalberoproc.getScDescrizione());
		model.put("padre", BooleanUtils.isTrue(alberoproc.getScPadre()));
		model.put("resp_proc_id", EntityUtils.getNestedProperty(alberoprocHelper.getResponsabile(), "id.codice"));
		model.put("resp_proc_desc", EntityUtils.getNestedProperty(alberoprocHelper.getResponsabile(), "responsabile"));
		// Se nella voce dell'albero (foglia o risalendo) troviamo la proprietà GruppiIstruttori non dobbiamo pre poplare
		// il campo responsabile istruttori
		if (EntityUtils.getNestedProperty(alberoprocHelper.getGruppiIstruttori(), "id.codice") == null) {
		    model.put("resp_istr_id", EntityUtils.getNestedProperty(alberoprocHelper.getRespistruttoria(), "id.codice"));
		    model.put("resp_istr_desc", EntityUtils.getNestedProperty(alberoprocHelper.getRespistruttoria(), "responsabile"));
		}
		model.put("procedura_id", EntityUtils.getNestedProperty(alberoprocHelper.getTipoProcedura(), "id.codice"));
		model.put("procedura_desc", EntityUtils.getNestedProperty(alberoprocHelper.getTipoProcedura(), "procedura"));
		model.put("mov_avv_id", EntityUtils.getNestedProperty(alberoprocHelper.getMovAvvio(), "id.tipomovimento"));
		model.put("mov_avv_desc", EntityUtils.getNestedProperty(alberoprocHelper.getMovAvvio(), "descrizioneEstesa"));
		model.put("endo_presenti", (alberoprocHelper.getAlberoprocEndos() != null && !alberoprocHelper.getAlberoprocEndos().isEmpty()));
		model.put("scCodice", alberoproc.getScCodice());
	    }
	}
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getInventarioprocedimento(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {

	Map model = new HashMap();
	if (id != null) {
	    Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(id));
	    if (inventarioprocedimenti != null) {
		if (EntityUtils.getNestedProperty(inventarioprocedimenti.getTipoendo(), "id.codice") != null) {
		    model.put("codiceTipoendo", inventarioprocedimenti.getTipoendo().getId().getCodice());
		    if (EntityUtils.getNestedProperty(inventarioprocedimenti.getTipoendo().getTipifamiglieendo(), "id.codice") != null) {
			model.put("codiceFamigliaendo", inventarioprocedimenti.getTipoendo().getTipifamiglieendo().getId().getCodice());
		    } else {
			model.put("codiceFamigliaendo", "");
		    }
		} else {
		    model.put("codiceTipoendo", "0");
		    model.put("codiceFamigliaendo", "");
		}
	    }
	}
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getAltreOperazioniDisponibili(HttpServletResponse response) throws IOException {

	Map model = new HashMap();
	List<String> list = new ArrayList<String>();
	list.add("Seleziona");
	// Verifico se la veritcalizzazione INFODURC è attiva, nel caso imposto le due oprazioni possibili
	// Verifica Durc e richiedi Durc
	boolean isAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSDURC);
	if (isAttiva) {
	    list.add("Verifica Durc");
	    list.add("Richiedi Durc");
	}
	model.put("item", list);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getAltreOperazioniDisponibiliMovimentoSTC(HttpServletResponse response) throws IOException {

	Map model = new HashMap();
	List<String> list = new ArrayList<String>();
	list.add("Seleziona");
	list.add("Disattiva Notifica");
	model.put("item", list);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getStradario(@RequestParam("codiceStradario") Integer codiceStradario) {

	Map<String, Object> model = new HashMap<String, Object>();
	Stradario stradario = stradarioService.findById(new PkId(codiceStradario));
	JSONStradario jstradario = new JSONStradario();
	if (null != stradario) {
	    jstradario.setCodice(String.valueOf(stradario.getId().getCodice()));
	    jstradario.setCap(StringUtils.defaultIfEmpty(stradario.getCap(), ""));
	    if (EntityUtils.getNestedProperty(stradario.getStradariozone(), "id.codice") != null) {
		jstradario.setCircoscrizione(StringUtils.defaultIfEmpty(stradario.getStradariozone().getZona(), ""));
	    } else {
		jstradario.setCircoscrizione("");
	    }
	    if (EntityUtils.getNestedProperty(stradario, "comune") != null) {
		jstradario.setComune(StringUtils.defaultIfEmpty(stradario.getComune().getCodicecomune(), ""));
	    } else {
		jstradario.setComune("");
	    }
	    jstradario.setPrefisso(StringUtils.defaultIfEmpty(stradario.getPrefisso(), ""));
	    jstradario.setDescrizione(StringUtils.defaultIfEmpty(stradario.getDescrizione(), ""));
	    jstradario.setLocfraz(StringUtils.defaultIfEmpty(stradario.getLocfraz(), ""));
	    jstradario.setDescrizioneCompleta(StringUtils.defaultIfEmpty(stradario.getDescrizioneCompleta(), ""));
	}
	model.put("stradario", jstradario);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getProvincia(@RequestParam("codiceComune") String codiceComune) {

	Map<String, Object> model = new HashMap<String, Object>();
	Comuni comune = comuniService.findById(codiceComune);
	model.put("comune", comune);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getAteco(@RequestParam(value = "inspectProperties", required = false) Boolean inspectProperties) {

	Map model = new HashMap();
	if (inspectProperties == null) {
	    inspectProperties = false;
	}
	List<AtecoCommand> list = null;
	if (cacheManager != null) {
	    Cache cache = cacheManager.getCache(WebConstants.CACHE_CLASSIFICAZIONE_ATECO_KEY);
	    Element obj = cache.get(WebConstants.CACHE_CLASSIFICAZIONE_ATECO_LIST);
	    if (obj != null) {
		list = (List<AtecoCommand>) obj.getObjectValue();
	    } else {
		list = atecoService.findAtecoHierarchy(inspectProperties);
		Element element = new Element(WebConstants.CACHE_CLASSIFICAZIONE_ATECO_LIST, list);
		cache.put(element);
		cache.flush();
	    }
	} else {
	    list = atecoService.findAtecoHierarchy(inspectProperties);
	}
	model.put("identifier", "id");
	model.put("label", "codiceDescrizione");
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getTipiorario(@RequestParam("codiceTipiorario") Integer codiceTipiorario) {

	Map<String, Object> model = new HashMap<String, Object>();
	Tipiorario tipiorario = tipiorarioService.findById(new PkId(codiceTipiorario));
	Tipiorario jsonTipiorario = new Tipiorario();
	jsonTipiorario.setId(tipiorario.getId());
	jsonTipiorario.setSoftware(tipiorario.getSoftware());
	jsonTipiorario.setToDescrizione(tipiorario.getToDescrizione());
	jsonTipiorario.setToPeriodoa(tipiorario.getToPeriodoa());
	jsonTipiorario.setToPeriododa(tipiorario.getToPeriododa());
	model.put("tipiorario", jsonTipiorario);
	List<Tipiorariodettaglio> tipiorariodettaglios = new ArrayList<Tipiorariodettaglio>(0);
	Set<Tipiorariodettaglio> todet = tipiorario.getTipiorariodettaglios();
	for (Tipiorariodettaglio tipiorariodettaglio : todet) {
	    Tipiorariodettaglio jsonTipiorariodettaglio = new Tipiorariodettaglio();
	    jsonTipiorariodettaglio.setId(tipiorariodettaglio.getId());
	    jsonTipiorariodettaglio.setGiornisectimana(tipiorariodettaglio.getGiornisectimana());
	    jsonTipiorariodettaglio.setOaAlleore(tipiorariodettaglio.getOaAlleore());
	    jsonTipiorariodettaglio.setOaAlleorepom(tipiorariodettaglio.getOaAlleorepom());
	    jsonTipiorariodettaglio.setOaDalleore(tipiorariodettaglio.getOaDalleore());
	    jsonTipiorariodettaglio.setOaDalleorepom(tipiorariodettaglio.getOaDalleorepom());
	    if (EntityUtils.getNestedProperty(tipiorariodettaglio.getTipiapertura(), "id.codice") != null) {
		Tipiapertura jsonTipiapertura = new Tipiapertura();
		jsonTipiapertura.setId(tipiorariodettaglio.getTipiapertura().getId());
		jsonTipiapertura.setTaDescrizione(tipiorariodettaglio.getTipiapertura().getTaDescrizione());
		jsonTipiorariodettaglio.setTipiapertura(jsonTipiapertura);
	    }
	    tipiorariodettaglios.add(jsonTipiorariodettaglio);
	}
	model.put("tipiorariodettaglios", tipiorariodettaglios);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getConcessioneByPosteggio(@RequestParam("codicePosteggio") Integer codicePosteggio,
	    @RequestParam("codiceUso") Integer codiceUso, @RequestParam("codiceMercato") Integer codiceMercato) {

	Map<String, Object> model = new HashMap<String, Object>();
	AutorizzazioniConcessioni ac = autorizzazioniConcessioniService.findConcessioneAttualeByMercatoAndUsoAndPosteggio(codiceMercato, codiceUso,
		codicePosteggio);
	ScambioPosteggioHelper scambioPosteggioHelper = new ScambioPosteggioHelper();
	if (ac != null) {
	    Mercati m = ac.getMercati();
	    MercatiUso u = ac.getMercatiUso();
	    MercatiD p = ac.getMercatiD();
	    scambioPosteggioHelper.setDescrizioneMercato(m.getDescrizione());
	    scambioPosteggioHelper.setDescrizioneUso(u.getDescrizione());
	    scambioPosteggioHelper.setDescrizionePosteggio(p.getCodiceposteggio());
	    scambioPosteggioHelper.setCodiceMercato(m.getId().getCodice());
	    scambioPosteggioHelper.setCodiceUso(u.getId().getCodice());
	    scambioPosteggioHelper.setCodicePosteggio(p.getId().getCodice());
	    if (EntityUtils.getNestedProperty(ac.getAutorizzazioniByFkAutconcAutatt(), "id.codice") != null) {
		scambioPosteggioHelper.setAutorizdata(ac.getAutorizzazioniByFkAutconcAutatt().getAutorizdata());
		scambioPosteggioHelper.setAutoriznumero(ac.getAutorizzazioniByFkAutconcAutatt().getAutoriznumero());
		scambioPosteggioHelper
			.setDecsrizioneTipologiaregistro(ac.getAutorizzazioniByFkAutconcAutatt().getTipologiaregistro().getTrDescrizioneCompleta());
		Anagrafe tit = anagrafeService.findById(new PkId(ac.getAutorizzazioniByFkAutconcAutatt().getAnagrafe().getId().getCodice()));
		scambioPosteggioHelper.setTitolare(tit.getDescrizioneRichiedente());
		if (EntityUtils.getNestedProperty(ac.getAutorizzazioniByFkAutconcAutatt().getConcessionicausaliByFkAutConccausAcq(),
			"id.codice") != null) {
		    scambioPosteggioHelper.setConcessionicausaliAcq(
			    ac.getAutorizzazioniByFkAutconcAutatt().getConcessionicausaliByFkAutConccausAcq().getDescrizione());
		}
		scambioPosteggioHelper.setCodiceAutorizzazioneAttuale(ac.getAutorizzazioniByFkAutconcAutatt().getId().getCodice());
		//scambioPosteggioHelper.setAutorizzazioniAttuale(ac.getAutorizzazioniByFkAutconcAutatt());
		scambioPosteggioHelper.setCodiceAutorizzazioneConcessione(ac.getId().getCodice());
	    }
	    if (ac.getConcessionitipi() != null) {
		scambioPosteggioHelper.setDescrizioneConcessionitipi(ac.getConcessionitipi().getDescrizione());
		scambioPosteggioHelper.setDescrizioneCodiceConcessioni(ac.getConcessionitipi().getTipoconcessione());
	    }
	    if (ac.getStagionalea() != null) {
		scambioPosteggioHelper.setStagionalea(ac.getStagionalea());
	    }
	    if (ac.getStagionaleda() != null) {
		scambioPosteggioHelper.setStagionaleda(ac.getStagionaleda());
	    }
	    if (ac.getDatascadenza() != null) {
		scambioPosteggioHelper.setDatascadenza(ac.getDatascadenza());
	    }
	} else {
	    scambioPosteggioHelper.setAutoriznumero("-1");
	}
	model.put("scambioPosteggioHelper", scambioPosteggioHelper);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView checkOpenTab(@RequestParam("windowName") String windowName, HttpServletResponse response, HttpServletRequest request)
	    throws IOException {

	Map model = new HashMap();
	String sessParamName = "_checkOpenTab_";
	Boolean sessioneSettata = (Boolean) request.getSession().getAttribute(sessParamName);
	if (sessioneSettata != null) {
	    if (windowName == null) {
		model.put("errore", "true");
	    } else {
		model.put("windowName", windowName);
	    }
	} else {
	    model.put("windowName", request.getSession().getId());
	    request.getSession().setAttribute(sessParamName, Boolean.TRUE);
	}
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView findAnagrafeAvvisi(@RequestParam("codiceAnagrafe") Integer codiceAnagrafe) {

	Map model = new HashMap();
	AnagrafeAvvisiHelper h = anagrafeService.findAvvisiAnagrafe(codiceAnagrafe);
	model.put("anagrafeAvvisi", h);
	return new ModelAndView("jsonView", model);
    }

    /**
     * <pre>
     * Cerca se per i vari tab presenti nella pagina dell'istanza sono presenti dei dati.
     * Il metodo crea una struttura json [chiave , valore]; valore può assumere true e false
     * Ricerche effettuate:
     * 		1. Endoprocedimenti 
     * 		2. Modellit
     * 		3. Doc istanze
     * 		4. Oneri
     * 		5. Istanze coll
     * 		6. Sogg coll
     * 		7.Aut Coll
     * &#64;return
     * </pre>
     */
    @RequestMapping
    public ModelAndView getTabFunzioniIstanzaPopolati(@RequestParam("codiceIstanza") Integer codiceIstanza) {

	Map model = new HashMap();
	if (codiceIstanza != null) {
	    boolean endo = istanzeprocedimentiService.countByIstanza(codiceIstanza) > 0 ? true : false;
	    boolean schede = istanzedyn2modellitService.countByIstanza(codiceIstanza) > 0 ? true : false;
	    boolean docIstanze = documentiistanzaService.countByIstanza(codiceIstanza) > 0 ? true : false;
	    if (!docIstanze) {
		docIstanze = istanzeallegatiService.countByIstanza(codiceIstanza) > 0 ? true : false;
	    }
	    boolean autConc = autorizzazioniService.countByIstanza(codiceIstanza) > 0 ? true : false;
	    //.
	    boolean oneri = istanzeoneriService.countByIstanza(codiceIstanza) > 0 ? true : false;
	    boolean istanzeColl = istanzecollegateService.countIstanzeCollegateByIstanza(codiceIstanza) > 0 ? true : false;
	    boolean soggColl = istanzerichiedentiService.countSoggettiCollegatiByIstanza(codiceIstanza) > 0 ? true : false;
	    boolean isAutAccessi = autorizzazioniService.existAutorizzazioniInIstanzeCollegate(codiceIstanza);
	    model.put("endo", endo);
	    model.put("schede", schede);
	    model.put("docIstanze", docIstanze);
	    model.put("oneri", oneri);
	    model.put("operazioniColl", isAutAccessi);
	    model.put("istanzeColl", istanzeColl);
	    model.put("soggColl", soggColl);
	    model.put("autConc", autConc);
	}
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView menuv2(HttpServletResponse response, HttpServletRequest request) {

	Map model = new HashMap();
	MenuHolder menu = clmenuService.getMenuV2(request.getContextPath(), true);
	model.put("menu", menu.getMenu());
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getStatoPosizioneDebitoria(@RequestParam("id") Integer id) {

	Map model = new HashMap();
	StatoPagamentiBreveJson stato = dettPosizioneDebitoriaService.getStatoBreveById(id);
	model.put("stato", stato);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView getDettaglioStatoPosizioneDebitoria(@RequestParam("id") Integer id) {

	Map model = new HashMap();
	StatoPagamentiDettaglioJson stato = dettPosizioneDebitoriaService.getStatoDettagliatoById(id);
	model.put("stato", stato);
	return new ModelAndView("jsonView", model);
    }

    @Override
    protected void fixMergeEntityProperty(Object entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }
}
