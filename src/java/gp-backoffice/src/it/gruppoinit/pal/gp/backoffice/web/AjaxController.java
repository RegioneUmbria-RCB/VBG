package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeRicercaBean;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ScadenzecategoriebaseEnum;
import it.gruppoinit.pal.gp.core.domain.*;
import it.gruppoinit.pal.gp.core.domain.helper.ClpermmenuResponsabiliComparator;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.Dyn2ModellitComparator;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzaAutConcHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloContestoEnum;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciCampoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioImportoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioMercatiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.RataHelper;
import it.gruppoinit.pal.gp.core.domain.helper.TipimovimentoComparator;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaCommand;
import it.gruppoinit.pal.gp.core.domain.web.InventarioprocedimentiCommand;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeeventiFilter;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeprocedimentiCommand;
import it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.domain.web.RegistrazioniInOutCommand;
import it.gruppoinit.pal.gp.core.domain.web.ResponsabiliCommand;
import it.gruppoinit.pal.gp.core.exception.OperatoreNonHaComuniConfiguratiException;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.AmministrazioniCollegateService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService.TIPO_RICERCA;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.NumerazioneEnum;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.ProcBollElaborazioneInvioNodo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.ProcBollElaborazioneInvioNodoBean;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi.LayouttestiService;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.IstanzecollegateService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calcolo.ICalcoloCostoPosteggiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoDTO;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.OneritipirateizzazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.sorteggi.categorie.SorteggiCategorieService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterOrder;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.*;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.ProprietaCampi;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;
import it.gruppoinit.pal.gp.core.service.helper.SchedeDinamicheTL;
import it.gruppoinit.pal.gp.core.service.helper.TempisticaIstanzaHelper;
import it.gruppoinit.pal.gp.core.utils.CustomHtmlBuilder;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.CEsportazione;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.CWSSigeproExpLocator;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.CWSSigeproExpSoap;

@Controller
@SessionAttributes(value = { "istanzeprocedimentiCommand", "protocolloCommand" })
public class AjaxController extends BaseController<Comuni> {

    @Autowired
    private Aree2Service aree2Service;
    @Autowired
    private BandiService bandiService;
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private LeggitipiService leggitipiService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private GraduatorietService graduatorietService;
    @Autowired
    private TitoliService titoliService;
    @Autowired
    private TipifamiglieendoService tipifamiglieendoService;
    @Autowired
    private TipimodelliService tipimodelliService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private TipiunitamisuraService tipiunitamisuraService;
    @Autowired
    private SettoriService settoriService;
    @Autowired
    private TipiareeService tipiareeService;
    @Autowired
    private StradariozoneService stradariozoneService;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private AreeService areeService;
    @Autowired
    private VwAlberoprocService vwAlberoprocService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private AttivitaService attivitaService;
    @Autowired
    private ContiService contiService;
    @Autowired
    private RegistrazioniCausaliService registrazioniCausaliService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private ImpiantiService impiantiService;
    @Autowired
    private IstanzeprocedimentiService istanzeprocedimentiService;
    @Autowired
    private TipimodalitapagamentoService tipimodalitapagamentoService;
    @Autowired
    private MercatiCfgAttivitaService mercatiCfgAttivitaService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private TipologiaregistriService tipologiaregistriService;
    @Autowired
    private ConcessionicausaliService concessionicausaliService;
    @Autowired
    private ConcessionitipiService concessionitipiService;
    @Autowired
    private LetteretipoService letteretipoService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private DocumentiHelperService documentiHelperService;
    @Autowired
    private RegistrazioniInOutService registrazioniInOutService;
    @Autowired
    private RegistrazioniImportiService registrazioniImportiService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private ResponsabiliTmAvvService responsabiliTmAvvService;
    @Autowired
    private ResponsabiliTmScaService responsabiliTmScaService;
    @Autowired
    private RegistrazioniService registrazioniService;
    @Autowired
    private OneritipirateizzazioneService oneritipirateizzazioneService;
    @Autowired
    private InteressiLegaliService interessiLegaliService;
    @Autowired
    private OggettiinfoService oggettiinfoService;
    @Autowired
    private MessaggicfgbaseService messaggicfgbaseService;
    @Autowired
    private AmministrazionireferentiService amministrazionireferentiService;
    @Autowired
    private AlboPubblicazioniService alboPubblicazioniService;
    @Autowired
    private LayouttestiService layouttestiService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private RuoliService ruoliService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private ConcessioniusoService concessioniusoService;
    @Autowired
    private PosteggitipospazioService posteggitipospazioService;
    @Autowired
    private TipicausalioneriService tipicausalioneriService;
    @Autowired
    private LavoritipiService lavoritipiService;
    @Autowired
    private TipiaperturaService tipiaperturaService;
    @Autowired
    private TipiprocedureService tipiprocedureService;
    @Autowired
    private TipiendoService tipiendoService;
    @Autowired
    private TipologiaistanzaService tipologiaistanzaService;
    @Autowired
    private OnericomportamentoService onericomportamentoService;
    @Autowired
    private FaqclassiService faqclassiService;
    @Autowired
    private NaturaendoService naturaendoService;
    @Autowired
    private MessaggiService messaggiService;
    @Autowired
    private TempificazioniService tempificazioniService;
    @Autowired
    private NormativeService normativeService;
    @Autowired
    private TipiarchivioistanzeService tipiarchivioistanzeService;
    @Autowired
    private FormegiuridicheService formegiuridicheService;
    @Autowired
    private CittadinanzaService cittadinanzaService;
    @Autowired
    private VwProvinceService vwProvinceService;
    @Autowired
    private TipidocumentoService tipidocumentoService;
    @Autowired
    private TipisoggettoService tipisoggettoService;
    @Autowired
    private ElenchiprofessionalibaseService elenchiprofessionalibaseService;
    @Autowired
    private ScadenzeService scadenzeService;
    @Autowired
    private IstanzestradarioService istanzestradarioService;
    @Autowired
    private ClmenuService clmenuService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AllegatiService allegatiService;
    @Autowired
    private InventarioprocLeggiService inventarioprocLeggiService;
    @Autowired
    private IstanzecollegateService istanzecollegateService;
    @Autowired
    private LeggiService leggiService;
    @Autowired
    private TipiorarioService tipiorarioService;
    @Autowired
    private LavoricategorieService lavoricategorieService;
    @Autowired
    private CommedilizieTipologieService commedilizieTipologieService;
    @Autowired
    private SorteggiCategorieService sorteggiCategorieService;
    @Autowired
    private MappatureService mappatureService;
    @Autowired
    private IstanzeeventiService istanzeeventiService;
    @Autowired
    private ResponsabilisoftwareService responsabilisoftwareService;
    @Autowired
    private ProtocolloTipidocumentoService protocolloTipidocumentoService;
    @Autowired
    private CcValiditacoefficientiService ccValiditacoefficientiService;
    @Autowired
    private RiCaricheService riCaricheService;
    @Autowired
    private RiFormegiuridicheService riFormegiuridicheService;
    @Autowired
    private RiTipiinterventoService riTipiinterventoService;
    @Autowired
    private RiTipiprocedimentoService riTipiprocedimentoService;
    @Autowired
    private DocumentiContabilitaService documentiContabilitaService;
    @Autowired
    private VwEntilocaliService vwEntilocaliService;
    @Autowired
    private MovimentiNoSecurityService movimentiNoSecurityService;
    private static final Logger log = LoggerFactory.getLogger(AjaxController.class);
    @Autowired
    private ElencoinpsbaseService elencoinpsbaseService;
    @Autowired
    private ElencoinailbaseService elencoinailbaseService;
    @Autowired
    private ElencocassaedilebaseService elencocassaedilebaseService;
    @Autowired
    private Dyn2RegoleService dyn2RegoleService;
    @Autowired
    private GruppiIstruttoriService gruppiIstruttoriService;
    @Autowired
    private MovimentiallegatiService movimentiallegatiService;
    @Autowired
    private GruppiEndoprocedimentiTService gruppiEndoprocedimentiTService;
    @Autowired
    private MercatiDattivitaistatService mercatiDattivitaistatService;
    @Autowired
    private MercatiResponsabiliService mercatiResponsabiliService;
    @Autowired
    private ComuniassociatisoftwareService comuniassociatisoftwareService;
    @Autowired
    private PosteggiSettoriService posteggiSettoriService;
    @Autowired
    private Istanzedyn2modellitService istanzedyn2modellitService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private MercatiCategorieService mercatiCategorieService;
    @Autowired
    private MovimentiZipLogicoService movimentiZipLogicoService;
    @Autowired
    private LivelloServizioService livelloServizioService;
    @Autowired
    private MercatiLivelloServizioService mercatiLivelloServizioService;
    @Autowired
    private ICalcoloCostoPosteggiService calcoloCostoPosteggiService;
    @Autowired
    private AmministrazioniCollegateService amministrazioniCollegateService;

    @RequestMapping
    public void findCategorieMercato(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<MercatiCategorie> list = mercatiCategorieService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findLivelloServizio(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<LivelloServizio> list = livelloServizioService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findSediINPS(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Elencoinpsbase> list = elencoinpsbaseService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "codice", "descrizioneEstesa");
    }

    @RequestMapping
    public void findSediINAIL(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Elencoinailbase> list = elencoinailbaseService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "codice", "descrizioneEstesa");
    }

    @RequestMapping
    public void findSediCassaedile(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Elencocassaedilebase> list = elencocassaedilebaseService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "codice", "descrizioneEstesa");
    }

    @RequestMapping
    public void findLeggitipi(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Leggitipi entity = new Leggitipi();
	entity.setLtDescrizione(textToSearch);
	List<Leggitipi> list = leggitipiService.findByFilter(entity);
	renderHTMLResponse(response, list, "id.codice", "ltDescrizione");
    }

    @RequestMapping
    public void findComuni(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Comuni> list = comuniService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "codicecomune", "descrizioneEstesa");
    }

    @Autowired
    private FoArjStepsTestataService foArjStepsTestataService;

    @RequestMapping
    public void findFoArjStepsTestata(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<FoArjStepsTestata> list = foArjStepsTestataService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findEnteautorizzazione(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<VwEntilocali> list = vwEntilocaliService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "codicecomune", "descrizioneEstesa");
    }

    @RequestMapping
    public void findTitoli(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Titoli> list = titoliService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "titolo");
    }

    @RequestMapping
    public void findTipifamiglieendo(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Tipifamiglieendo entity = new Tipifamiglieendo();
	if (textToSearch != null) {
	    entity.setTipo(textToSearch);
	    if (StringUtils.isNotBlank(codicesoftware)) {
		Software software = new Software();
		software.setCodice(codicesoftware);
		entity.setSoftware(software);
	    }
	}
	List<Tipifamiglieendo> list = tipifamiglieendoService.findByFilter(entity);
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesa");
    }

    @RequestMapping
    public void findTipifamiglieendoSWeTT(@RequestParam("textToSearch") String textToSearch, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	List<Tipifamiglieendo> list = tipifamiglieendoService.findByDescSWeTT(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "tipo");
    }

    @RequestMapping
    public void findTipiendoSWeTT(@RequestParam("textToSearch") String textToSearch, HttpServletRequest request,
	    @RequestParam(value = "codiceFamiglia", required = false) Integer codiceFamiglia,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware, HttpServletResponse response) throws IOException {

	List<Tipiendo> list = new ArrayList<Tipiendo>();
	if (StringUtils.isNotBlank(codicesoftware)) {
	    list = tipiendoService.findByDescAndSW(textToSearch, codiceFamiglia, codicesoftware);
	} else {
	    list = tipiendoService.findByDescSWeTT(textToSearch, codiceFamiglia);
	}
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesa");
    }

    @RequestMapping
    public void findTipiendo(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceFamiglia", required = false) Integer codiceFamiglia,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	List<Tipiendo> list = tipiendoService.findByDescAndSW(textToSearch, codiceFamiglia, codicesoftware);
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesa");
    }

    @RequestMapping
    public void findSoftware(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Software entity = new Software();
	if (textToSearch != null) {
	    entity.setDescrizione(textToSearch);
	}
	List<Software> list = softwareService.findByFilter(entity);
	renderHTMLResponse(response, list, "codice", "descrizione");
    }

    @RequestMapping
    public void findCurrentSoftware(HttpServletResponse response) throws IOException {

	String code = ORMHelper.getSoftware();
	String descrizione = "";
	Software software = softwareService.findById(code);
	if (software != null) {
	    descrizione = software.getDescrizione();
	}
	response.setContentType("text/plain");
	response.getWriter().write(descrizione);
	if (log.isDebugEnabled())
	    log.debug("call findCurrentSoftware return: '" + descrizione + "'");
    }

    @RequestMapping
    public void findLogo(HttpServletResponse response) throws IOException {

	//	ConfigurazioneId id = new ConfigurazioneId();
	//	id.setIdcomune(ORMHelper.getIdcomune());
	//	id.setSoftware(ORMHelper.getSoftware());
	//	ConfigurazioneId idTT = new ConfigurazioneId();
	//	idTT.setIdcomune(ORMHelper.getIdcomune());
	//	idTT.setSoftware(WebConstants.SOFTWARE_TT);
	//	Configurazione c = configurazioneService.findById(id);
	//	String urlBackoffice = "";
	//	if (c != null && StringUtils.isNotBlank(c.getUrlLogoBackoffice())) {
	//	    urlBackoffice = c.getUrlLogoBackoffice();
	//	} else {
	//	    Configurazione c1 = configurazioneService.findById(idTT);
	//	    if (c1 != null) {
	//		urlBackoffice = StringUtils.defaultIfEmpty(c1.getUrlLogoBackoffice(), "");
	//	    }
	//	}
	String urlBackoffice = configurazioneService.getLogoBo();
	response.setContentType("text/plain");
	response.getWriter().write(urlBackoffice);
    }

    @RequestMapping
    public void findTipiunitamisura(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Tipiunitamisura entity = new Tipiunitamisura();
	if (textToSearch != null) {
	    entity.setUmDescrbreve(textToSearch);
	}
	List<Tipiunitamisura> list = tipiunitamisuraService.findByFilter(entity);
	renderHTMLResponse(response, list, "id.codice", "umDescrbreve");
    }

    @RequestMapping
    public void findSettori(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "flagDisabilitato", required = false) Boolean flagDisabilitato, HttpServletResponse response) throws IOException {

	Settori entity = new Settori();
	if (textToSearch != null) {
	    entity.setSettore(textToSearch);
	    entity.getId().setCodicesettore(textToSearch);
	}
	if (flagDisabilitato != null) {
	    entity.setFlagDisabilitato(flagDisabilitato);
	}
	List<Settori> list = settoriService.findByFilter(entity);
	renderHTMLResponse(response, list, "id.codicesettore", "descrizioneEstesa");
    }

    @RequestMapping
    public void findTipiaree(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Tipiaree entity = new Tipiaree();
	if (textToSearch != null) {
	    entity.setTipoarea(textToSearch);
	}
	List<Tipiaree> list = tipiareeService.findByFilter(entity);
	renderHTMLResponse(response, list, "id.codice", "tipoarea");
    }

    @RequestMapping
    public void findTipiorario(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Tipiorario entity = new Tipiorario();
	if (textToSearch != null) {
	    entity.setToDescrizione(textToSearch);
	}
	List<Tipiorario> list = tipiorarioService.findByFilter(entity);
	renderHTMLResponse(response, list, "id.codice", "toDescrizione");
    }

    @RequestMapping
    public void findStradariozone(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Stradariozone entity = new Stradariozone();
	if (textToSearch != null) {
	    entity.setZona(textToSearch);
	}
	List<Stradariozone> list = stradariozoneService.findByFilter(entity);
	renderHTMLResponse(response, list, "id.codice", "zona");
    }

    @RequestMapping
    public void findStradario(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceComune", required = false) String codiceComune,
	    @RequestParam(value = "searchDisabilitati", required = false) String searchDisabilitati, HttpServletResponse response)
	    throws IOException {

	List<Stradario> list = null;
	try {
	    if (StringUtils.isNotBlank(searchDisabilitati) && searchDisabilitati.equals("false")) {
		list = stradarioService.findByDescrizione(textToSearch, codiceComune, 0, WebConstants.NUM_MAX_RESULTS, false);
	    } else {
		list = stradarioService.findByDescrizione(textToSearch, codiceComune, 0, WebConstants.NUM_MAX_RESULTS, true);
	    }
	    StringBuffer buffer = new StringBuffer("<ul>");
	    int i = 0;
	    int size = 0;
	    try {
		if (list == null || list.isEmpty()) {
		    buffer.append("<li id=''>").append(" ").append("</li>");
		} else {
		    size = list.size();
		    for (Stradario obj : list) {
			Stradario s = stradarioService.findById(new PkId(obj.getId().getCodice()));
			buffer.append("<li id='").append(s.getId().getCodice().toString()).append("'>").append(s.getDescrizioneCompleta())
				.append("</li>");
			i++;
			if (i == WebConstants.NUM_MAX_RESULTS) {
			    break;
			}
		    }
		}
		buffer.append("</ul>");
		if (true) {
		    buffer.append("<span style='font-weight: bold;'>").append("Visualizzati ").append(i).append(" risultati di ").append(size)
			    .append("</span>");
		}
		response.setContentType("text/plain");
		response.getWriter().write(buffer.toString());
	    } catch (Exception e) {
		log.error("Ajax search error: {}", e.getMessage());
		throw new IOException(e);
	    }
	    if (log.isDebugEnabled()) {
		log.debug("Ajax search result: {}", buffer.toString());
	    }
	    // renderHTMLResponse(response, list, "id.codice", "descrizioneCompleta");
	} catch (OperatoreNonHaComuniConfiguratiException e) {
	    log.error("findStradario: {}", e.getMessage());
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public void findAree(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceComune", required = false) String codiceComune, HttpServletResponse response) throws IOException {

	List<Aree> list = areeService.findByDescrizione(textToSearch, codiceComune);
	renderHTMLResponse(response, list, "id.codice", "denominazione");
    }

    @RequestMapping
    public void findAreeDehors(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceComune", required = false) String codiceComune, HttpServletResponse response) throws IOException {

	List<Aree> list = areeService.findByDescrizioneDehors(textToSearch, codiceComune);
	renderHTMLResponse(response, list, "id.codice", "denominazione");
    }

    @RequestMapping
    public void findAree2(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Aree2> list = aree2Service.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "denominazione");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public void findAreeNoninserite(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceComune", required = false) String codiceComune,
	    @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza, HttpServletResponse response) throws IOException {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	List<Aree> areeInserite = areeService.findAreeByIstanza(istanza);
	List<Aree> list = areeService.findByDescrizione(textToSearch, codiceComune);
	list = ListUtils.subtract(list, areeInserite);
	renderHTMLResponse(response, list, "id.codice", "denominazione");
    }

    /**
     * Determino tramite il metodo findByFilter la descrizione e l'id di VwAlberoproc. La descrizione e l'id di
     * VwAlberoproc viene settatto nell'oggetto del dominio Alberoproc. Tale metodo viene utilizzato per effettuare la
     * ricerca tramite Ajax
     * 
     * @param textToSearch
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void findAlberoproc(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	// ///////////////////////
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	if (StringUtils.isNotBlank(textToSearch)) {
	    String[] params = StringUtils.split(textToSearch);
	    FilterRestriction criterio = new FilterRestriction();
	    for (String param : params) {
		criterio.addFilterField(new FilterField<String>("scDescrizione", FieldOperationsEnum.CONTAINS, new String[] { param }, String.class));
	    }
	    ft.addRestriction(criterio);
	}
	FilterOrder<String> orderByScDescrizione = new FilterOrder<String>(new FilterField<String>("scDescrizione", null, String.class));
	ft.addOrder(orderByScDescrizione);
	// /////////////////////
	List<VwAlberoproc> list = vwAlberoprocService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "scDescrizione");
    }

    @RequestMapping
    public void findDyn2Modelli(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Dyn2Modellit entity = new Dyn2Modellit();
	if (textToSearch != null) {
	    entity.setDescrizione(textToSearch);
	}
	List<Dyn2Modellit> list = dyn2ModellitService.findByDescrizione(entity);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findAllDyn2Modelli(@RequestParam("textToSearch") String textToSearch, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	List<Dyn2Modellit> listaModelli = dyn2ModellitService.findAllByDescrizione(textToSearch);
	StringBuffer sbuf = new StringBuffer("<ul>");
	for (Dyn2Modellit dyn2Modellit : listaModelli) {
	    sbuf.append("<li id='").append(dyn2Modellit.getId().getCodice()).append("'><b>").append(dyn2Modellit.getSoftware().getDescrizione())
		    .append("</b> - ").append(dyn2Modellit.getDescrizione()).append("</li>");
	}
	sbuf.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(sbuf.toString());
	if (log.isDebugEnabled()) {
	    log.debug("call findAllDyn2Modelli with textToSearch: '" + textToSearch + "' return: '" + sbuf.toString() + "'");
	}
    }

    @RequestMapping
    public void findAllDyn2Campi(@RequestParam("textToSearch") String textToSearch, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	List<Dyn2Campi> listaCampi = dyn2CampiService.findAllByDescrizione(textToSearch);
	StringBuffer sbuf = new StringBuffer("<ul>");
	for (Dyn2Campi dyn2Campi : listaCampi) {
	    sbuf.append("<li id='").append(dyn2Campi.getId().getCodice()).append("'><b>").append(dyn2Campi.getSoftware().getDescrizione())
		    .append("</b> - ").append(dyn2Campi.getNomecampo()).append("</li>");
	}
	sbuf.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(sbuf.toString());
	if (log.isDebugEnabled()) {
	    log.debug("call findAllDyn2Campi with textToSearch: '" + textToSearch + "' return: '" + sbuf.toString() + "'");
	}
    }

    @RequestMapping
    public void findDyn2ModelliCurretSoftwareOrTT(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware, HttpServletResponse response) throws IOException {

	Dyn2Modellit entity = new Dyn2Modellit();
	if (textToSearch != null) {
	    entity.setDescrizione(textToSearch);
	}
	List<Dyn2Modellit> list;
	if (StringUtils.isNotBlank(codicesoftware)) {
	    list = dyn2ModellitService.findByDescrizioneAndSoftware(entity, codicesoftware);
	} else {
	    list = dyn2ModellitService.findByDescrizione(entity);
	}
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findNewDyn2ModelliCurretSoftwareOrTT(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware, @RequestParam("idIstanza") Integer idIstanza,
	    HttpServletResponse response) throws IOException {

	Dyn2Modellit entity = new Dyn2Modellit();
	if (textToSearch != null) {
	    entity.setDescrizione(textToSearch);
	}
	List<Istanzedyn2modellit> schedeIstanza = this.istanzedyn2modellitService.findByIstanza(new PkId(idIstanza));
	List<Integer> idSchedeIstanza = new ArrayList<Integer>();
	for (Istanzedyn2modellit scheda : schedeIstanza) {
	    idSchedeIstanza.add(scheda.getId().getFkD2mtId());
	}
	List<Dyn2Modellit> list;
	if (StringUtils.isNotBlank(codicesoftware)) {
	    list = dyn2ModellitService.findByDescrizioneAndSoftware(entity, codicesoftware);
	} else {
	    list = dyn2ModellitService.findByDescrizione(entity);
	}
	for (int i = list.size() - 1; i > -1; i--) {
	    Dyn2Modellit scheda = list.get(i);
	    if (idSchedeIstanza.contains(scheda.getId().getCodice())) {
		list.remove(i);
	    }
	}
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findDyn2ModelliAttivitaCurretSoftwareOrTT(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware, HttpServletResponse response) throws IOException {

	// E' possibile che più codici vengano accodati, nel caso significa significa che cerchiamo il codice
	// software prensente alla fine. (Es SSTT --> TT)
	if (StringUtils.isNotBlank(codicesoftware) && codicesoftware.length() > 2) {
	    codicesoftware = StringUtils.substring(codicesoftware, codicesoftware.length() - 2, codicesoftware.length());
	}
	List<Dyn2Modellit> list = dyn2ModellitService.findByDescrizioneAndSoftwareAndContesto(textToSearch, codicesoftware, "AT");
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findDyn2Campi(@RequestParam("textToSearch") String textToSearch, @RequestParam("idModello") Integer idModello,
	    HttpServletResponse response) throws IOException {

	Dyn2Campi entity = new Dyn2Campi();
	if (textToSearch != null) {
	    entity.setDescrizione(textToSearch);
	    entity.setNomecampo(textToSearch);
	}
	List<Dyn2Campi> list = dyn2CampiService.findByFilterAndIdModello(entity, idModello);
	renderHTMLResponse(response, list, "id.codice", "nomecampo");
    }

    @RequestMapping
    public void findDyn2CampiCurrentSoftwareOrTT(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceSoftware", required = false) String codiceSofware, HttpServletResponse response) throws IOException {

	List<Dyn2Campi> list = dyn2CampiService.findByDescrizioneAndSoftware(textToSearch, codiceSofware);
	renderHTMLResponse(response, list, "id.codice", "nomecampo");
    }

    @RequestMapping
    public void findDyn2CampiByModelloAndCurrentSoftwareOrTT(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceSoftware", required = false) String codiceSoftware,
	    @RequestParam(value = "codiceModello", required = false) Integer codiceModello, HttpServletResponse response) throws IOException {

	List<Dyn2Campi> list = dyn2CampiService.findByDescrizioneAndSoftwareAndModello(textToSearch, codiceModello, codiceSoftware);
	renderHTMLResponse(response, list, "id.codice", "nomeCampoEtichetta");
    }

    @RequestMapping
    public void findDyn2CampiByMercato(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceMercato", required = false) Integer codiceMercato, HttpServletResponse response) throws IOException {

	List<Dyn2Campi> list = dyn2CampiService.findByDescrizioneAndMercato(textToSearch, codiceMercato);
	renderHTMLResponse(response, list, "id.codice", "nomecampo");
    }

    @RequestMapping
    public void findDyn2CampiNumerici(@RequestParam("textToSearch") String textToSearch, @RequestParam("idModello") Integer idModello,
	    HttpServletResponse response) throws IOException {

	Dyn2Campi entity = new Dyn2Campi();
	if (textToSearch != null) {
	    entity.setDescrizione(textToSearch);
	    entity.setNomecampo(textToSearch);
	}
	List<Dyn2Campi> list = dyn2CampiService.findByFilterAndIdModelloForNumeric(entity, idModello);
	renderHTMLResponse(response, list, "id.codice", "nomecampo");
    }

    @RequestMapping
    public void findAttivita(@RequestParam("textToSearch") String textToSearch, @RequestParam("codicesettore") String codicesettore,
	    @RequestParam(value = "flagDisabilitato", required = false) Boolean flagDisabilitato, HttpServletResponse response) throws IOException {

	Attivita entity = new Attivita();
	entity.setFlagDisabilitato(false);
	Settori settori = null;
	if (StringUtils.isNotBlank(codicesettore)) {
	    settori = new Settori();
	    settori.getId().setCodicesettore(codicesettore);
	}
	if (textToSearch != null) {
	    entity.setIstat(textToSearch);
	    entity.getId().setCodiceistat(textToSearch);
	    entity.setSettori(settori);
	}
	if (flagDisabilitato != null) {
	    entity.setFlagDisabilitato(flagDisabilitato);
	}
	List<Attivita> list = attivitaService.findByFilter(entity);
	renderHTMLResponse(response, list, "id.codiceistat", "descrizioneEstesa");
    }

    @RequestMapping
    public void findConti(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Conti> list = contiService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "descrizioneConto");
    }

    @RequestMapping
    public void findContieIva(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Conti> list = contiService.findByDescrizione(textToSearch);
	StringBuffer buffer = new StringBuffer("<ul>");
	Integer iva = 0;
	for (Conti conti : list) {
	    iva = conti.getIva();
	    if (iva == null) {
		// iva = WebConstants.CONST_IVA;
		throw new RuntimeException("Errore nella configurazione dei conti della contabilita'. Non e' stata definita l'iva per il conto " +
			conti.getDescrizione() +
			"(" +
			conti.getId() +
			")");
	    }
	    buffer.append("<li id='").append(conti.getId().getCodice()).append(WebConstants.ITEM_SEPARATOR).append(String.valueOf(iva)).append("'>")
		    .append(conti.getDescrizioneConto()).append("</li>");
	}
	buffer.append("</ul>");
	// lista del tipo <ul><li id='1|20'>TOSAP</li></ul> dove id='1|20' 1 è codice conto e 20 è iva
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call conti with textToSearch: '" + textToSearch + "' return: '" + buffer.toString() + "'");
    }

    @RequestMapping
    public void findRegistrazioniCausali(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<RegistrazioniCausali> list = registrazioniCausaliService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findRegistrazioniCausaliEscluseRiduzioni(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response)
	    throws IOException {

	List<RegistrazioniCausali> list = registrazioniCausaliService.findByDescrizioneEscluseRiduzioni(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "descrizioneConto");
    }

    @RequestMapping
    public void findAnagrafe(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "tipoAnagrafe", required = false) String tipoAnagrafe,
	    @RequestParam(value = "statoAnagrafe", required = false) AnagrafeEnum statoAnagrafe, HttpServletResponse response) throws IOException {

	if (statoAnagrafe == null) {
	    statoAnagrafe = AnagrafeEnum.ACTIVE;
	}
	List<Anagrafe> list = anagrafeService.findByDescrizione(textToSearch, tipoAnagrafe, statoAnagrafe, 0, WebConstants.NUM_MAX_RESULTS);
	renderHTMLResponse(response, list, "id.codice", "descrizioneRichiedente");
    }

    @RequestMapping
    public void findRichiedentiIstanza(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceIstanza", required = true) Integer codiceIstanza,
	    @RequestParam(value = "tipoAnagrafe", required = true) String tipoAnagrafe, HttpServletResponse response) throws IOException {

	List<AnagrafeRicercaBean> list = anagrafeService.findRichiedentiByIstanza(textToSearch, codiceIstanza, tipoAnagrafe);
	renderHTMLResponse(response, list, "codiceanagrafe", "descrizioneRichiedente");
    }

    @RequestMapping
    public String dettaglioAnagrafe(@RequestParam("codiceAnagrafe") Integer codiceAnagrafe,
	    @RequestParam(value = "showParametriDiv", required = false) Boolean showParametriDiv, Model model, HttpServletResponse response)
	    throws IOException {

	// faccio lo split della stringa inserita
	PkId idAnagrafe = new PkId(codiceAnagrafe);
	Anagrafe anagrafe = anagrafeService.findById(idAnagrafe);
	List<Scadenze> scadenzes = scadenzeService.findAvvisiForAnagrafe(anagrafe, true);
	model.addAttribute("scadenzes", scadenzes);
	model.addAttribute("anagrafe", anagrafe);
	response.setContentType("text/plain");
	if (log.isDebugEnabled()) {
	    log.debug("call dettaglioAnagrafe with codiceAnagrafe: " + codiceAnagrafe);
	}
	if (BooleanUtils.isTrue(showParametriDiv)) {
	    return "ajax/dettaglioAnagrafeParametri";
	} else {
	    return "ajax/dettaglioAnagrafe";
	}
    }

    @RequestMapping
    public void checkAnagrafeAvvisi(@RequestParam("codiceAnagrafe") Integer codiceAnagrafe, HttpServletResponse response) throws IOException {

	boolean isAvvisi = scadenzeService.findExistsAvvisiForAnagrafe(codiceAnagrafe, true);
	response.setContentType("text/plain");
	if (isAvvisi) {
	    response.getWriter().write("true");
	}
    }

    @RequestMapping
    public String dettaglioAnagrafeAvvisi(@RequestParam("codiceAnagrafe") Integer codiceAnagrafe, Model model, HttpServletResponse response)
	    throws IOException {

	PkId idAnagrafe = new PkId(codiceAnagrafe);
	Anagrafe anagrafe = anagrafeService.findById(idAnagrafe);
	List<Scadenze> scadenzes = scadenzeService.findAvvisiForAnagrafe(anagrafe, true);
	model.addAttribute("anagrafe", anagrafe);
	model.addAttribute("scadenzes", scadenzes);
	response.setContentType("text/plain");
	return "ajax/dettaglioAnagrafeAvvisi";
    }

    @RequestMapping
    public void isAnagrafeInterdetta(@RequestParam("codiceAnagrafe") Integer codiceAnagrafe, Model model, HttpServletResponse response)
	    throws IOException {

	// PkId idAnagrafe = new PkId(codiceAnagrafe);
	response.setContentType("text/plain");
	// Anagrafe anagrafe = anagrafeService.findById(idAnagrafe);
	List<Scadenze> scadenzes = scadenzeService.findAvvisiForAnagrafe(codiceAnagrafe, ScadenzecategoriebaseEnum.INTERDIZIONE);
	if (!scadenzes.isEmpty()) {
	    String soggettoInterdetto = this.getMessageFromBundle("label.soggetto_interdetto_fino_al", null);
	    response.getWriter().write(soggettoInterdetto + " " + Utilities.formatDate(scadenzes.get(0).getDatascadenza(), false));
	}
    }

    @RequestMapping
    public String dettaglioIstanza(@RequestParam("codIstanza") Integer codIstanza, Model model, HttpServletResponse response) throws IOException {

	// faccio lo split della stringa inserita
	TempisticaIstanzaHelper istanzaT = istanzeService.tempisticaDettaglio(codIstanza);
	model.addAttribute("tempisticaHelper", istanzaT);
	// Variabile di controllo che gestisce la visualizzazione del Comune nella sezione dettaglio della pratica.
	// Se il Comune della pratica appartiene ad un gruppo di Comuni, allora viene mostrato il Comune di riferimento della pratica.
	model.addAttribute("isComuniAssociati", comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune()));
	// Variabili di controllo per la gestione della visualizzazione del bottone 'OPERAZIONI' nell'header di dettaglio dell'istanza.
	// 1. Controllo se è attiva la verticalizzazione
	model.addAttribute("isVerticalizzazioneAUTORIZACCESSIAttiva",
		verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_AUTORIZ_ACCESSI));
	// 2. il bottone diventa visibile nelle pagine accessibili dall'istanza (es. elaborazione, movimenti ecc.) se la pratica 
	// possiede almeno un'autorizzazione o se le istanze collegate (precedenti) ad'essa possiedono almeno un'autorizzazione.
	boolean isExistAutAccessoCollegate = false;
	if (codIstanza != null) {
	    isExistAutAccessoCollegate = autorizzazioniService.existAutorizzazioniInIstanzeCollegate(codIstanza);
	}
	model.addAttribute("isExistAutAccessoCollegate", isExistAutAccessoCollegate);
	response.setContentType("text/plain");
	log.debug("call dettaglioIstanza with codiceIstanza: {}", codIstanza);
	return "ajax/dettaglioIstanza";
    }

    @RequestMapping
    public String dettaglioZipLogico(@RequestParam("codMovimento") Integer codMovimento, Model model, HttpServletResponse response)
	    throws IOException {

	List<MovimentiZipLogicoDTO> zipLogicoDTO = movimentiZipLogicoService.findMovimentiZipLogicoDTOByMovimento(codMovimento);
	MovimentiZipLogicoTestata testata = movimentiZipLogicoService.findTestataByCodiceMovimento(codMovimento);
	model.addAttribute("ziplogico", zipLogicoDTO);
	model.addAttribute("codiceoggettoDocAll", testata.getCodiceoggettoDocAll());
	response.setContentType("text/plain");
	return "ajax/dettaglioZipLogico";
    }

    @RequestMapping
    public String dettaglioRegistrazioniIO(@RequestParam("codiceRegIO") Integer codiceRegIO, Model model, HttpServletResponse response)
	    throws IOException {

	RegistrazioniInOutCommand command = new RegistrazioniInOutCommand();
	PkId id = new PkId(codiceRegIO);
	RegistrazioniInOut registrazioniInOut = registrazioniInOutService.findById(id);
	if (registrazioniInOut == null) {
	    command.setEntity(new RegistrazioniInOut());
	} else {
	    command.setEntity(registrazioniInOut);
	}
	// recupero le modalità di pagamento
	List<Tipimodalitapagamento> tipimodalitapagamentoList = tipimodalitapagamentoService.findAll(null, null, false);
	model.addAttribute("tipimodalitapagamentoList", tipimodalitapagamentoList);
	model.addAttribute("entity", command.getEntity());
	if (log.isDebugEnabled())
	    log.debug("call dettaglioRegistrazioniIO with codiceRegIO: " + codiceRegIO);
	response.setContentType("text/plain");
	return "ajax/dettaglioRegistrazioniIO";
    }

    @RequestMapping
    public void findMercatiEndo(@RequestParam("code") String code, HttpServletResponse response) throws IOException {

	String codice = "0";
	if (StringUtils.isNotBlank(code)) {
	    PkId id = new PkId(Integer.valueOf(code));
	    RegistrazioniCausali registrazioniCausali = registrazioniCausaliService.findById(id);
	    if (registrazioniCausali.getRichiedePosteggio()) {
		codice = "1";
	    }
	    if (registrazioniCausali.getRichiedeEndo()) {
		codice = "2";
	    }
	}
	StringBuffer buffer = new StringBuffer(codice);
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call MercatiEndo with textToSearch: '" + code + "' return: '" + buffer.toString() + "'");
    }

    @RequestMapping
    public void findAmministrazioni(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam("tutteLeAmministrazioni") boolean tutteLeAmministrazioni,
	    @RequestParam(value = "includiDisabilitate", required = false) Boolean includiDisabilitate,
	    @RequestParam(value = "visualizzaPerProtocollo", required = false) Boolean visualizzaPerProtocollo,
	    @RequestParam(value = "codiciAmministrazioneEsclusi", required = false) String codiciAmministrazioneEsclusi, HttpServletResponse response)
	    throws IOException {

	if (includiDisabilitate == null) {
	    includiDisabilitate = Boolean.FALSE;
	}
	if (visualizzaPerProtocollo == null) {
	    visualizzaPerProtocollo = Boolean.FALSE;
	}
	String propertyDesc = "descrizioneEstesa";
	if (visualizzaPerProtocollo.booleanValue()) {
	    propertyDesc = "descrizioneEstesa";
	}
	Integer[] iCodiciAmministrazioneEsclusi = null;
	if (StringUtils.isNotBlank(codiciAmministrazioneEsclusi)) {
	    String[] codamm = codiciAmministrazioneEsclusi.split(",");
	    List<Integer> codAmmList = new ArrayList<Integer>();
	    for (String c : codamm) {
		String codice = StringUtils.defaultString(c).trim();
		if (StringUtils.isNotBlank(codice)) {
		    try {
			Integer camm = Integer.valueOf(codice);
			codAmmList.add(camm);
		    } catch (Exception e) {
		    }
		}
	    }
	    if (codAmmList.size() > 0) {
		iCodiciAmministrazioneEsclusi = codAmmList.toArray(new Integer[codAmmList.size()]);
	    }
	}
	List<Amministrazioni> list = amministrazioniService.findByAmministrazione(textToSearch, tutteLeAmministrazioni, includiDisabilitate,
		iCodiciAmministrazioneEsclusi);
	renderHTMLResponse(response, list, "id.codice", propertyDesc);
    }

    @RequestMapping
    public void findAmministrazioniDaCollegare(Model model, @RequestParam("idAmministrazione") Integer idAmministrazione,
	    @RequestParam("textToSearch") String textToSearch, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, JAXBException {

	List<Integer> list = amministrazioniCollegateService.findCodiciAmmCollegate(idAmministrazione);
	String codiceAmmEsclusi = null;
	if (list != null && list.size() > 0) {
	    codiceAmmEsclusi = String.valueOf(idAmministrazione); // escludo se stessa
	    for (Integer integer : list) {
		codiceAmmEsclusi += "," + String.valueOf(integer); // escludo quelle già collegate
	    }
	}
	this.findAmministrazioni(textToSearch, true, false, false, codiceAmmEsclusi, response);
    }

    @RequestMapping
    public void findAmministrazioniForProtocolloRegistri(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam("codiceComune") String codiceComune,
	    @RequestParam(value = "includiDisabilitate", required = false) Boolean includiDisabilitate, HttpServletResponse response)
	    throws IOException {

	if (includiDisabilitate == null) {
	    includiDisabilitate = Boolean.FALSE;
	}
	List<Amministrazioni> list = amministrazioniService.findAmministrazioniByDescrizioneForProtocolloRegistri(textToSearch, includiDisabilitate,
		codiceComune, ORMHelper.getSoftware());
	renderHTMLResponse(response, list, "id.codice", "amministrazione");
    }

    @RequestMapping
    public void findMercatiForAnagrafe(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Mercati> list = mercatiService.findByDescrizione(textToSearch, MercatiEnum.ACTIVE);
	StringBuffer buffer = new StringBuffer("<ul>");
	for (Mercati mercati : list) {
	    buffer.append("<li id='").append(mercati.getId().getCodice()).append("'>").append(mercati.getDescrizione()).append("</li>");
	    List<Registrazioni> list2 = registrazioniService.findAnagrafeByRegistrazioniAndMercati(mercati);
	    for (Registrazioni registrazioni : list2) {
		// l'id è costruito come concatenazione degli id del mercato, mercatoUso , posteggio e anagrafe.
		// in questo caso:
		// mercato_id#mercato_descrizione#anagrafe_id#mercatouso_id#mercatouso_descrizione#posteggio_id#
		// posteggio_codiceposteggio
		buffer.append("<li id='").append(mercati.getId().getCodice()).append("#").append(mercati.getDescrizione()).append("#")
			.append(registrazioni.getAnagrafe().getId().getCodice()).append("#").append(registrazioni.getMercatiUso().getId().getCodice())
			.append("#").append(registrazioni.getMercatiUso().getDescrizione()).append("#")
			.append(registrazioni.getMercatiD().getId().getCodice()).append("#").append(registrazioni.getMercatiD().getCodiceposteggio())
			.append("'>").append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").append(registrazioni.getAnagrafe().getDescrizioneRichiedente())
			.append("-").append(registrazioni.getMercatiUso().getDescrizione()).append("-")
			.append(registrazioni.getMercatiD().getCodiceposteggio()).append("</li>");
	    }
	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call mercati with textToSearch: '" + textToSearch + "' return: '" + buffer.toString() + "'");
    }

    @RequestMapping
    public void findMercati(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Mercati> list = mercatiService.findByDescrizione(textToSearch, MercatiEnum.ACTIVE);
	Responsabili r = getCurrentlyAuthenticatedUserDetails();
	int c = mercatiResponsabiliService.countByResponsabile(r.getId().getCodice());
	if (c > 0) {
	    List<Mercati> list2 = mercatiService.findByDescrizioneAndResponsabile(textToSearch, r.getId().getCodice(), MercatiEnum.ACTIVE, null,
		    null);
	    renderHTMLResponse(response, list2, "id.codice", "descrizione");
	    return;
	}
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findPosteggioMercato(@RequestParam("code") String code, HttpServletResponse response) throws IOException {

	PkId id = new PkId(Integer.valueOf(code));
	Mercati mercati = new Mercati();
	mercati.setId(id);
	List<MercatiD> mercatiDList = mercatiDService.findByMercato(mercati, PosteggiEnum.ACTIVE);
	StringBuffer buffer = new StringBuffer("");
	for (MercatiD mercatiD : mercatiDList) {
	    buffer.append("" + mercatiD.getId().getCodice() + "," + mercatiD.getCodiceposteggio() + ",");
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call findPosteggioMercato with textToSearch: '" + code + "' return: '" + buffer.toString() + "'");
    }

    /**
     * Trova la lista dei posteggi che non hanno una concessione attiva
     * 
     * @param codiceMercato
     *            il codice del mercato deove effettuare la ricerca
     * @param codiceMercatiUso
     *            il giorno di mercato (opzionale) se non indicato cerca tra tutti i giorni del mercato
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void findPosteggiNonAssegnati(@RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam(value = "codiceMercatiUso", required = false) Integer codiceMercatiUso, HttpServletResponse response) throws IOException {

	PkId id = new PkId(codiceMercato);
	Mercati mercati = new Mercati();
	mercati.setId(id);
	List<CodiceDescrizioneBean> mercatiDList = mercatiDService.findPosteggiNonAssegnatiByMercato(codiceMercato, codiceMercatiUso, null,
		PosteggiEnum.ACTIVE);
	StringBuffer buffer = new StringBuffer("");
	for (CodiceDescrizioneBean cdb : mercatiDList) {
	    buffer.append(cdb.getCodice() + "-SEP-" + cdb.getDescrizione() + "-SEP-");
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call findPosteggiNonAssegnati with codiceMercato: '" +
		    String.valueOf(codiceMercato) +
		    "', codiceMercatiUso: " +
		    String.valueOf(codiceMercatiUso) +
		    " return: '" +
		    buffer.toString() +
		    "'");
    }

    @RequestMapping
    public void findPosteggiSettore(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<PosteggiSettori> list = posteggiSettoriService.findByDescrizione(textToSearch);
	StringBuffer buffer = new StringBuffer("<ul>");
	for (PosteggiSettori ps : list) {
	    buffer.append("<li id='").append(ps.getId().getCodice()).append("'>(").append(ps.getCodicesettore()).append(") ").append(ps.getSettore())
		    .append("</li>");
	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled()) {
	    log.debug("call findPosteggiSettore with textToSearch: '" + textToSearch + "' return: '" + buffer.toString() + "'");
	}
    }

    @RequestMapping
    public void findIstanze(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Istanze> list = istanzeService.findByNumeroistanzaOrRichiedente(textToSearch);
	StringBuffer buffer = new StringBuffer("<ul>");
	for (Istanze istanze : list) {
	    buffer.append("<li id='").append(istanze.getId().getCodice()).append("'>(").append(istanze.getNumeroistanza()).append(") ")
		    .append(istanze.getRichiedente().getDescrizioneRichiedente()).append("</li>");
	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call istanze with textToSearch: '" + textToSearch + "' return: '" + buffer.toString() + "'");
    }

    @RequestMapping
    public void findIstanzeExtended(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(required = false, value = "protInMovimenti") Boolean protInMovimenti, HttpServletResponse response) throws IOException {

	boolean searchProtocolloInMovimenti = BooleanUtils.isTrue(protInMovimenti);
	List<Istanze> list = istanzeService.findByProtocolloNumeroRichiedenteAziendaOrPEC(textToSearch, searchProtocolloInMovimenti);
	String space = "&nbsp;&nbsp;&nbsp;";
	StringBuffer buffer = new StringBuffer("<ul>");
	for (Istanze istanze : list) {
	    buffer.append("<li id='").append(istanze.getId().getCodice()).append("'><span id='show_").append(istanze.getId().getCodice())
		    .append("'>(").append(istanze.getNumeroistanza()).append(") ");
	    if (StringUtils.isNotEmpty(istanze.getNumeroprotocollo())) {
		buffer.append("Prot.").append(istanze.getNumeroprotocollo()).append(" ");
	    }
	    buffer.append(StringUtils.defaultString(istanze.getDomicilioElettronico(), "")).append("<br/>");
	    buffer.append(space).append(istanze.getRichiedente().getDescrizioneRichiedente());
	    if (StringUtils.isNotEmpty(istanze.getRichiedente().getPec())) {
		buffer.append(" - ").append(istanze.getRichiedente().getPec());
	    }
	    buffer.append("</span>");
	    if (istanze.getTitolarelegale() != null && istanze.getTitolarelegale().getId() != null
		    && istanze.getTitolarelegale().getId().getCodice() != null) {
		buffer.append("<br/>").append(space).append("<span>").append(istanze.getTitolarelegale().getDescrizioneRichiedente());
		if (StringUtils.isNotEmpty(istanze.getTitolarelegale().getPec())) {
		    buffer.append(" - ").append(istanze.getTitolarelegale().getPec());
		}
		buffer.append("<span>");
	    }
	    buffer.append("</li>");
	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call istanze with textToSearch: '" + textToSearch + "' return: '" + buffer.toString() + "'");
    }

    @RequestMapping
    public void findResponsabili(@RequestParam(required = false, value = "tipologiaResponsabilie") String tipologiaResponsabilie,
	    @RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Responsabili entity = new Responsabili();
	if (textToSearch != null) {
	    entity.setResponsabile(textToSearch);
	}
	List<Responsabili> list = new ArrayList<Responsabili>();
	if (StringUtils.isBlank(tipologiaResponsabilie)) {
	    list = responsabiliService.findByFilter(entity);
	} else {
	    if (tipologiaResponsabilie.equalsIgnoreCase("P")) {
		list = responsabiliService.findResponsabiliProcedimento(entity);
	    } else if (tipologiaResponsabilie.equalsIgnoreCase("I")) {
		list = responsabiliService.findResponsabiliIstruttoria(entity);
	    }
	}
	renderHTMLResponse(response, list, "id.codice", "responsabile");
    }

    @RequestMapping
    public void findResponsabiliProcedimento(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Responsabili entity = new Responsabili();
	if (textToSearch != null) {
	    entity.setResponsabile(textToSearch);
	}
	List<Responsabili> list = responsabiliService.findResponsabiliProcedimento(entity);
	renderHTMLResponse(response, list, "id.codice", "responsabile");
    }

    @RequestMapping
    public void findResponsabiliIstruttoria(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Responsabili entity = new Responsabili();
	if (textToSearch != null) {
	    entity.setResponsabile(textToSearch);
	}
	List<Responsabili> list = responsabiliService.findResponsabiliIstruttoria(entity);
	renderHTMLResponse(response, list, "id.codice", "responsabile");
    }

    @RequestMapping
    public void findProcedimentiPrincipali(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Inventarioprocedimenti> list = this.inventarioprocedimentiService.findProcedimentiPrincipaliByFilter(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "procedimento");
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping
    public void findInventarioEndo(@RequestParam("code") String code, HttpServletResponse response) throws IOException {

	PkId id = new PkId(Integer.valueOf(code));
	Istanze istanze = istanzeService.findById(id);
	List<Istanzeprocedimenti> istanzeprocedimentiList = istanzeprocedimentiService.findByIstanze(istanze);
	StringBuffer buffer = new StringBuffer("");
	for (Iterator iterator = istanzeprocedimentiList.iterator(); iterator.hasNext();) {
	    Istanzeprocedimenti istanzeprocedimenti = (Istanzeprocedimenti) iterator.next();
	    buffer.append("" +
		    istanzeprocedimenti.getInventarioprocedimenti().getId().getCodice() +
		    "," +
		    istanzeprocedimenti.getInventarioprocedimenti().getProcedimento() +
		    ",");
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call inventarioprocedimenti with textToSearch: '" + code + "' return: '" + buffer.toString() + "'");
    }

    /**
     * Metodo che ricerca per descrizione e se è passato anche per codice famiglia endo
     * 
     * @param textToSearch
     * @param codiceFamiglia
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void findInventarioprocedimento(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceFamiglia", required = false) Integer codiceFamiglia,
	    @RequestParam(value = "codiceTipologia", required = false) Integer codiceTipologia,
	    @RequestParam(value = "escludiDisabilitati", required = false) Boolean escludiDisabilitati, HttpServletResponse response)
	    throws IOException {

	boolean escludiDis = escludiDisabilitati == null ? Boolean.FALSE : escludiDisabilitati.booleanValue();
	List<Inventarioprocedimenti> list = inventarioprocedimentiService.findByDescrizioneFamigliaendoETipologia(textToSearch, codiceFamiglia,
		codiceTipologia, escludiDis);
	renderHTMLResponse(response, list, "id.codice", "procedimento");
    }

    @RequestMapping
    public void findInventarioprocedimentoGruppiEndo(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceFamiglia", required = false) Integer codiceFamiglia,
	    @RequestParam(value = "codiceTipologia", required = false) Integer codiceTipologia,
	    @RequestParam(value = "escludiDisabilitati", required = false) Boolean escludiDisabilitati, HttpServletResponse response)
	    throws IOException {

	boolean escludiDis = escludiDisabilitati == null ? Boolean.FALSE : escludiDisabilitati.booleanValue();
	List<Inventarioprocedimenti> list = inventarioprocedimentiService.findByDescrizioneFamigliaendoETipologiaGruppiEndo(textToSearch,
		codiceFamiglia, codiceTipologia, escludiDis);
	renderHTMLResponse(response, list, "id.codice", "procedimento");
    }

    /**
     * Metodo che ricerca per descrizione (con una like )e filtra anche per famigliaendo, tipoendo e software se
     * passati.
     * 
     * @param textToSearch
     * @param codiceFamiglia
     *            :opzionale
     * @param codiceTipologia
     *            :opzionale
     * @param codiceSoftware
     *            :opzionale
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void findInventarioprocByFamigliaAndCategoriaEndoAndSoftwareAndNatureEndo(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceFamiglia", required = false) Integer codiceFamiglia,
	    @RequestParam(value = "codiceTipologia", required = false) Integer codiceTipologia,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware,
	    @RequestParam(value = "codiciNaturaEndo", required = true) String codiceNatureEndo,
	    @RequestParam(value = "codiciEndoAttivati", required = false) String codiciEndoAttivati, HttpServletResponse response)
	    throws IOException {

	List<Integer> listCodicinature = new ArrayList<Integer>();
	// Dalla stringa codiceNatureEndo recupero i codici delle nature endo selezionate
	String[] field = StringUtils.split(codiceNatureEndo, ",");
	for (int i = 0; i < field.length; i++) {
	    listCodicinature.add(Integer.parseInt(field[i]));
	}
	List<Integer> listCodiciEndoAttivati = new ArrayList<Integer>();
	// Dalla stringa codiciEndoAttivati recupero i codici degli endo procedimenti attivati
	String[] fieldEndoProcedimenti = StringUtils.split(codiciEndoAttivati, ",");
	for (int i = 0; i < fieldEndoProcedimenti.length; i++) {
	    listCodiciEndoAttivati.add(Integer.parseInt(fieldEndoProcedimenti[i]));
	}
	List<Inventarioprocedimenti> list = inventarioprocedimentiService.findByDescrizioneFamigliaendoETipologiaAndSoftwareAndNatureEndo(
		textToSearch, codiceFamiglia, codiceTipologia, codicesoftware, listCodicinature, listCodiciEndoAttivati);
	// renderHTMLResponse(response, list, "id.codice", "procedimento");
	renderHTMLResponse(response, list, "id.codice", "transientDescrizioneWithSoftware");
    }

    /**
     * Metodo che ricerca per descrizione (con una like )e filtra anche per famigliaendo, tipoendo e software se
     * passati.
     * 
     * @param textToSearch
     * @param codiceFamiglia
     *            :opzionale
     * @param codiceTipologia
     *            :opzionale
     * @param codiceSoftware
     *            :opzionale
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void findInventarioprocByFamigliaAndCategoriaEndoAndSoftware(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceFamiglia", required = false) Integer codiceFamiglia,
	    @RequestParam(value = "codiceTipologia", required = false) Integer codiceTipologia,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware, HttpServletResponse response) throws IOException {

	List<Inventarioprocedimenti> list = inventarioprocedimentiService.findByDescrizioneFamigliaendoETipologiaAndSoftware(textToSearch,
		codiceFamiglia, codiceTipologia, codicesoftware);
	renderHTMLResponse(response, list, "id.codice", "procedimento");
    }

    @RequestMapping
    public void findInventarioprocedimentoAndSoftware(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware, HttpServletResponse response) throws IOException {

	List<Inventarioprocedimenti> list;
	if (codicesoftware != null) {
	    list = inventarioprocedimentiService.findByDescrizioneFamigliaendoAndSoftware(textToSearch, codicesoftware);
	} else {
	    list = inventarioprocedimentiService.findByDescrizioneFamigliaendoAndSoftware(textToSearch, null);
	}
	renderHTMLResponse(response, list, "id.codice", "procedimento");
    }

    @RequestMapping
    public void findByTipiendoAndNonAttivatiPerIstanzaAndDescrizione(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam(value = "codiceFamiglia", required = false) Integer codiceFamiglia,
	    @RequestParam(value = "codiceTipologia", required = false) Integer codiceTipologia,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware,
	    @RequestParam(value = "codiciNaturaEndo", required = true) String codiceNatureEndo, HttpServletResponse response) throws IOException {

	Tipifamiglieendo tipifamiglieendo = null;
	if (codiceFamiglia != null) {
	    tipifamiglieendo = tipifamiglieendoService.findById(new PkId(codiceFamiglia));
	}
	Tipiendo tipiendo = null;
	if (codiceTipologia != null) {
	    tipiendo = tipiendoService.findById(new PkId(codiceTipologia));
	}
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	List<String> listaCodiciNaturaAmmissibili = new ArrayList<String>();
	if (codiceNatureEndo != null) {
	    listaCodiciNaturaAmmissibili = Arrays.asList(codiceNatureEndo.split(","));
	}
	List<Inventarioprocedimenti> list = inventarioprocedimentiService.findByTipiendoAndNonAttivatiPerIstanza(tipiendo, tipifamiglieendo, istanza,
		listaCodiciNaturaAmmissibili, textToSearch, false, null);
	renderHTMLResponse(response, list, "id.codice", "procedimento");
    }

    @RequestMapping
    public void findIstanzeprocedimentiEndo(@RequestParam("textToSearch") String textToSearch, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    HttpServletResponse response) throws IOException {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceistanza", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.like("procedimento", textToSearch, "inventarioprocedimenti"));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("procedimento", "inventarioprocedimenti"));
	List<Istanzeprocedimenti> list = istanzeprocedimentiService.findByFilterTable(filterTable);
	renderHTMLResponse(response, list, "id.codiceinventario", "descrizioneEstesa");
    }

    @RequestMapping
    public void findTipimodalitapagamento(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(required = false, value = "mostraDisabilitati") Boolean mostraDisabilitati, HttpServletResponse response)
	    throws IOException {

	mostraDisabilitati = BooleanUtils.toBoolean(mostraDisabilitati);
	List<Tipimodalitapagamento> list = tipimodalitapagamentoService.findByMpDescrestesa(textToSearch, mostraDisabilitati);
	renderHTMLResponse(response, list, "id.codice", "mpDescrestesa");
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping
    public void findAttivitaPerMercato(@RequestParam("textToSearch") String textToSearch, @RequestParam("codicesettore") String codicesettore,
	    HttpServletResponse response) throws IOException {

	List<Attivita> attivitaPerMercatiList = new ArrayList<Attivita>();
	Attivita entity = new Attivita();
	Settori settori = new Settori();
	settori.getId().setCodicesettore(codicesettore);
	if (textToSearch != null) {
	    entity.setIstat(textToSearch);
	    entity.setSettori(settori);
	}
	List<Attivita> list = attivitaService.findByFilter(entity);
	List<MercatiCfgAttivita> mercatiCfgAttivitaList = mercatiCfgAttivitaService.findAll(null, null);
	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	    boolean trovato = true;
	    Attivita attivita = (Attivita) iterator.next();
	    for (Iterator iterator2 = mercatiCfgAttivitaList.iterator(); iterator2.hasNext();) {
		MercatiCfgAttivita mercatiCfgAttivita = (MercatiCfgAttivita) iterator2.next();
		if (mercatiCfgAttivita.getAttivita().getId().getCodiceistat().equals(attivita.getId().getCodiceistat())) {
		    trovato = false;
		    break;
		}
	    }
	    if (trovato) {
		attivitaPerMercatiList.add(attivita);
	    }
	}
	renderHTMLResponse(response, attivitaPerMercatiList, "id.codiceistat", "istat");
    }

    @RequestMapping
    public void findContiMercato(@RequestParam("textToSearch") String textToSearch, @RequestParam("mercati.id.codice") Integer codiceMercato,
	    HttpServletResponse response) throws IOException {

	PkId mercatoId = new PkId(codiceMercato);
	Mercati mercato = mercatiService.findById(mercatoId);
	Set<MercatiConti> mercatiContiSet = mercato.getMercatiContis();
	Set<Conti> uniqueConti = new HashSet<Conti>(0);
	for (MercatiConti mercatiConti : mercatiContiSet) {
	    Conti conto = mercatiConti.getConti();
	    if (!(textToSearch == null || textToSearch.equals("") || textToSearch.equals("%"))) {
		Pattern p = Pattern.compile(textToSearch, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(conto.getDescrizione());
		if (m.find()) {
		    uniqueConti.add(conto);
		}
	    } else {
		uniqueConti.add(conto);
	    }
	}
	StringBuffer buffer = new StringBuffer("<ul>");
	for (Conti conto : uniqueConti) {
	    buffer.append("<li id='").append(conto.getId().getCodice()).append("'>").append(conto.getDescrizioneConto()).append("</li>");
	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call conti with textToSearch: '" + textToSearch + "' return: '" + buffer.toString() + "'");
    }

    @RequestMapping
    public void findRegistrazioniCausaliByPosteggio(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response)
	    throws IOException {

	List<RegistrazioniCausali> list = registrazioniCausaliService.findByDescrizioneMercati(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping
    public void findMercatiUso(@RequestParam("code") String code, HttpServletResponse response) throws IOException {

	StringBuffer buffer = new StringBuffer("");
	if (!(code == null || code.equals(""))) {
	    PkId id = new PkId(Integer.valueOf(code));
	    Mercati mercati = new Mercati();
	    mercati.setId(id);
	    List<MercatiUso> list = mercatiUsoService.findByMercato(mercati);
	    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
		MercatiUso mercatiUso = (MercatiUso) iterator.next();
		buffer.append("" + mercatiUso.getId().getCodice() + "," + mercatiUso.getDescrizione() + ",");
	    }
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void findMercatiLivelloServizio(@RequestParam("textToSearch") String textToSearch, @RequestParam("codiceUso") Integer codiceUso,
	    HttpServletResponse response) throws IOException {

	List<MercatiLivelloServizio> list = mercatiLivelloServizioService.findByDescrizioneAndUso(textToSearch, codiceUso);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findTipologiaRegistriPerManifestazioni(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codicecomune", required = false) String codicecomune, HttpServletResponse response) throws IOException {

	findTipologiaRegistriInternal(textToSearch, codicecomune, TIPO_RICERCA.SOLO_MANIFESTAZIONI, response);
    }

    @RequestMapping
    public void findTipologiaRegistri(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codicecomune", required = false) String codicecomune, HttpServletResponse response) throws IOException {

	findTipologiaRegistriInternal(textToSearch, codicecomune, TIPO_RICERCA.TUTTI, response);
    }

    private void findTipologiaRegistriInternal(String textToSearch, String codicecomune, TIPO_RICERCA tipoRicerca, HttpServletResponse response)
	    throws IOException {

	StringBuffer buffer = new StringBuffer("<ul>");
	Tipologiaregistri tipologiaregistri = new Tipologiaregistri();
	tipologiaregistri.setTrDescrizione(textToSearch);
	List<Tipologiaregistri> list = new ArrayList<Tipologiaregistri>();
	if (StringUtils.isBlank(codicecomune)) {
	    list = tipologiaregistriService.findByDescrizione(tipologiaregistri, tipoRicerca);
	} else {
	    list = tipologiaregistriService.findByDescrizioneAndComune(tipologiaregistri, codicecomune, tipoRicerca);
	}
	for (Tipologiaregistri tipologiaregistri2 : list) {
	    Integer codiceRegistro = tipologiaregistri2.getId().getCodice();
	    buffer.append("<li id='").append(codiceRegistro).append("'");
	    // verifica del registro per la numerazione
	    NumerazioneEnum tipoRegConf = NumerazioneEnum.daRegistro(tipologiaregistri2);
	    if (!NumerazioneEnum.VUOTO.equals(tipoRegConf)) {
		// registro automatico
		buffer.append(" name='").append(true).append("'");
	    } else {
		buffer.append(" name='").append(false).append("'");
	    }
	    buffer.append(">").append(tipologiaregistri2.getTrDescrizioneCompleta()).append("</li>");
	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call findTipologiaRegistri with textToSearch: '" + textToSearch + "' return: '" + buffer.toString() + "'");
    }

    @RequestMapping
    public void findConcessioniCausali(@RequestParam("textToSearch") String textToSearch, @RequestParam("flagStorico") boolean flagStorico,
	    HttpServletResponse response) throws IOException {

	Concessionicausali concessionicausali = new Concessionicausali();
	concessionicausali.setDescrizione(textToSearch);
	concessionicausali.setCausalestorico(flagStorico);
	List<Concessionicausali> list = concessionicausaliService.findByDescrizioneAndFlagStorico(concessionicausali);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findConcessioniTipi(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Concessionitipi concessionitipi = new Concessionitipi();
	if (textToSearch != null) {
	    concessionitipi.setDescrizione(textToSearch);
	}
	List<Concessionitipi> list = concessionitipiService.findByDescrizione(concessionitipi);
	renderHTMLResponse(response, list, "tipoconcessione", "descrizione");
    }

    @RequestMapping
    public void findLettereTipo(@RequestParam(value = "codicesoftware", required = false) String software,
	    @RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "includiDisabilitate", required = false) Boolean includiDisabilitate, HttpServletResponse response)
	    throws IOException {

	Letteretipo letteretipo = new Letteretipo();
	letteretipo.setDescrizione(textToSearch);
	List<Letteretipo> list = null;
	if (includiDisabilitate == null) {
	    includiDisabilitate = Boolean.FALSE;
	}
	if (software != null) {
	    list = letteretipoService.findByDescrizioneAndSoftware(letteretipo.getDescrizione(), software, includiDisabilitate.booleanValue());
	} else {
	    list = letteretipoService.findByDescrizione(letteretipo, includiDisabilitate.booleanValue());
	}
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesa");
    }

    @RequestMapping
    public void findAnagrafeSpuntisti(@RequestParam("textToSearch") String textToSearch, @RequestParam("mercati.id.codice") Integer codiceMercato,
	    @RequestParam("mercatiUso.id.codice") Integer codiceUsoMercato, @RequestParam(required = false, value = "cercaTutti") Boolean cercaTutti,
	    HttpServletResponse response) throws IOException {

	Anagrafe entity = new Anagrafe();
	entity.setNominativo(textToSearch);
	List<Anagrafe> listSpuntistiMercati = null;
	// List<AnagrafeFiere> listSpuntistiFiere = null;
	StringBuffer buffer = new StringBuffer("<ul>");
	if (cercaTutti != null && cercaTutti.booleanValue()) {
	    listSpuntistiMercati = anagrafeService.findByDescrizione(textToSearch, null, AnagrafeEnum.ACTIVE, 0, WebConstants.NUM_MAX_RESULTS);
	} else {
	    listSpuntistiMercati = anagrafeService.findAnagraficheConAutorizzazione(textToSearch, null, AnagrafeEnum.ACTIVE, 0,
		    WebConstants.NUM_MAX_RESULTS);
	}
	for (Anagrafe anagrafe : listSpuntistiMercati) {
	    buffer.append("<li id='").append(anagrafe.getId().getCodice()).append("'>").append(anagrafe.getDescrizioneRichiedente()).append("</li>");
	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call anagrafe with textToSearch: '" + textToSearch + "' return: '" + buffer.toString() + "'");
    }

    @RequestMapping
    public String dettaglioRiferimento(@RequestParam("codiceImporto") Integer codiceImporto, Model model, HttpServletResponse response)
	    throws IOException {

	PkId idRegImporto = new PkId(codiceImporto);
	RegistrazioniImporti importi = registrazioniImportiService.findById(idRegImporto);
	Set<RegIoAssegnazioni> reIoAssSet = importi.getRegIoAssegnazionis();
	List<RegistrazioniInOut> regInOutList = new ArrayList<RegistrazioniInOut>();
	for (RegIoAssegnazioni regIoAssegnazioni : reIoAssSet) {
	    RegistrazioniInOut registrazioniInOut = regIoAssegnazioni.getRegistrazioniInOut();
	    regInOutList.add(registrazioniInOut);
	}
	model.addAttribute("regInOutList", regInOutList);
	if (log.isDebugEnabled())
	    log.debug("call dettaglioRiferimento with idRegistrazioniImporto: " + idRegImporto);
	response.setContentType("text/plain");
	return "ajax/dettaglioRiferimento";
    }

    @RequestMapping
    public String dettaglioRiferimentoRata(@RequestParam("codiceImporto") Integer codiceImporto, @RequestParam("numeroRata") Integer numeroRata,
	    Model model, HttpServletResponse response) throws IOException {

	PkId idRegImporto = new PkId(codiceImporto);
	RegistrazioniImporti importi = registrazioniImportiService.findById(idRegImporto);
	Registrazioni reg = importi.getRegistrazioni();
	List<RataHelper> rh = reg.getListaRate();
	RataHelper rhp = null;
	for (RataHelper rataHelper : rh) {
	    if (rataHelper.getNumeroRata().equals(numeroRata)) {
		rhp = rataHelper;
		break;
	    }
	}
	List<RegistrazioniImporti> list = rhp.getRegistrazioniImportiList();
	Set<RegistrazioniInOut> result = new HashSet<RegistrazioniInOut>();
	for (RegistrazioniImporti registrazioniImporti : list) {
	    Set<RegIoAssegnazioni> reIoAssSet = registrazioniImporti.getRegIoAssegnazionis();
	    for (RegIoAssegnazioni regIoAssegnazioni : reIoAssSet) {
		result.add(regIoAssegnazioni.getRegistrazioniInOut());
	    }
	}
	model.addAttribute("regInOutList", result);
	if (log.isDebugEnabled()) {
	    log.debug("call dettaglioRiferimento with idRegistrazioniImporto: " + idRegImporto);
	}
	response.setContentType("text/plain");
	return "ajax/dettaglioRiferimento";
    }

    @RequestMapping
    public void findAnagrafeRegistrazioniForMercati(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response)
	    throws IOException {

	Anagrafe entity = new Anagrafe();
	entity.setNominativo(textToSearch);
	List<Anagrafe> list = anagrafeService.findAnagrafeByRegistrazioni(entity);
	StringBuffer buffer = new StringBuffer("<ul>");
	if (list.isEmpty()) {
	    list = anagrafeService.findAllByFilter(entity);
	}
	for (Anagrafe anagrafe : list) {
	    buffer.append("<li id='").append(anagrafe.getId().getCodice()).append("'>").append(anagrafe.getDescrizioneRichiedente()).append("</li>");
	    List<Registrazioni> list2 = registrazioniService.findMercatiByRegistrazioniAndAnagrafe(anagrafe);
	    for (Registrazioni registrazioni : list2) {
		// l'id è costituito dalla concatenazione degli id di anagrafe,mercato,mercatouso e posteggio
		// in questo caso: anagrafe_id#anagrafe_descrizione#mercato_id#mercatouso_id#posteggio_id
		buffer.append("<li id='").append(anagrafe.getId().getCodice()).append("#").append(anagrafe.getDescrizioneRichiedente()).append("#")
			.append(registrazioni.getMercatiD().getMercati().getId().getCodice()).append("#")
			.append(registrazioni.getMercatiUso().getId().getCodice()).append("#").append(registrazioni.getMercatiD().getId().getCodice())
			.append("'>").append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").append(registrazioni.getMercatiD().getMercati().getDescrizione())
			.append("-").append(registrazioni.getMercatiUso().getDescrizione()).append("-")
			.append(registrazioni.getMercatiD().getCodiceposteggio()).append("</li>");
	    }
	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call anagrafe with textToSearch: '" + textToSearch + "' return: '" + buffer.toString() + "'");
    }

    @RequestMapping
    public void findAnagrafeRegistrazioni(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Anagrafe entity = new Anagrafe();
	entity.setNominativo(textToSearch);
	List<Anagrafe> list = anagrafeService.findAnagrafeByRegistrazioni(entity);
	renderHTMLResponse(response, list, "id.codice", "descrizioneRichiedente");
    }

    /**
     * metodo per la ricerca tipoMovimento. restituisce i record trovati indipendentemente dal software
     * 
     * @param textToSearch
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void findMovimentiTuttiSoftware(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "includiDisabilitate", required = false) Boolean includiDisabilitate, HttpServletResponse response)
	    throws IOException {

	if (includiDisabilitate == null) {
	    includiDisabilitate = Boolean.FALSE;
	}
	StringBuffer buffer = new StringBuffer("<ul>");
	Tipimovimento tipoMovimento = new Tipimovimento();
	tipoMovimento.setMovimento(textToSearch);
	List<Tipimovimento> list = tipiMovimentoService.findByDescrizionePerTuttiISoftware(tipoMovimento, includiDisabilitate);
	for (Tipimovimento tipoMovimento2 : list) {
	    buffer.append("<li id='").append(tipoMovimento2.getId().getTipomovimento()).append("'>").append(tipoMovimento2.getId().getTipomovimento())
		    .append(" - ").append(tipoMovimento2.getMovimento()).append(" (<b>").append(tipoMovimento2.getSoftware().getDescrizione())
		    .append("</b>)").append("</li>");
	}
	buffer.append("</ul>");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call findTipiMovimento with textToSearch: '" + textToSearch + "' return: '" + buffer.toString() + "'");
    }

    /**
     * metodo per la ricerca tipoMovimento. restituisce i record trovati per il software corrente e per il software TT
     * 
     * @param textToSearch
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void findTipiMovimento(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "includiDisabilitate", required = false) Boolean includiDisabilitate, HttpServletResponse response)
	    throws IOException {

	if (includiDisabilitate == null) {
	    includiDisabilitate = Boolean.FALSE;
	}
	StringBuffer buffer = new StringBuffer("<ul>");
	Tipimovimento tipoMovimento = new Tipimovimento();
	tipoMovimento.setMovimento(textToSearch);
	List<Tipimovimento> list = tipiMovimentoService.findByDescrizione(tipoMovimento, includiDisabilitate);
	for (Tipimovimento tipoMovimento2 : list) {
	    buffer.append("<li id='").append(tipoMovimento2.getId().getTipomovimento()).append("'>").append(tipoMovimento2.getId().getTipomovimento())
		    .append(" - ").append(tipoMovimento2.getMovimento()).append(" (").append(tipoMovimento2.getSoftware().getDescrizione())
		    .append(")").append("</li>");
	}
	buffer.append("</ul>");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call findTipiMovimento with textToSearch: '" + textToSearch + "' return: '" + buffer.toString() + "'");
    }

    @RequestMapping
    public void findTipiMovimentoForSoftware(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codice", required = false) String codice,
	    @RequestParam(value = "includiDisabilitate", required = false) Boolean includiDisabilitate, HttpServletResponse response)
	    throws IOException {

	if (includiDisabilitate == null) {
	    includiDisabilitate = Boolean.FALSE;
	}
	Tipimovimento tipoMovimento = new Tipimovimento();
	tipoMovimento.getId().setTipomovimento(textToSearch);
	tipoMovimento.setMovimento(textToSearch);
	List<Tipimovimento> list = tipiMovimentoService.findTipimovimentoByDescrizioneAndSoftware(tipoMovimento, codice, includiDisabilitate);
	renderHTMLResponse(response, list, "id.tipomovimento", "descrizioneEstesa");
    }

    @RequestMapping
    public void findTipiMovimentoUsatiDalProtocollo(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codice", required = false) String codice,
	    @RequestParam(value = "includiDisabilitate", required = false) Boolean includiDisabilitate,
	    @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza, HttpServletResponse response) throws IOException {

	if (includiDisabilitate == null) {
	    includiDisabilitate = Boolean.FALSE;
	}
	Tipimovimento tipoMovimento = new Tipimovimento();
	tipoMovimento.getId().setTipomovimento(textToSearch);
	tipoMovimento.setMovimento(textToSearch);
	List<Tipimovimento> list = tipiMovimentoService.findTipimovimentoByDescrizioneAndSoftware(tipoMovimento, codice, includiDisabilitate, true);
	Map<String, Boolean> tmovs = new HashMap<String, Boolean>(0);
	for (Tipimovimento tipimovimento : list) {
	    tmovs.put(tipimovimento.getId().getTipomovimento(), Boolean.FALSE);
	}
	if (codiceIstanza != null) {
	    Istanze istanza = new Istanze();
	    istanza.setId(new PkId(codiceIstanza));
	    List<Movimenti> movs = movimentiNoSecurityService.findDaEseguireByIstanza(istanza);
	    for (Movimenti mov : movs) {
		String ricerca = StringUtils.defaultString(textToSearch).trim().toLowerCase().replaceAll("%", "");
		Tipimovimento tm = mov.getTipomovimento();
		String tipomovcodice = StringUtils.defaultString(tm.getId().getTipomovimento()).trim().toLowerCase();
		String tipomovdescrizione = StringUtils.defaultString(tm.getMovimento()).trim().toLowerCase();
		if (StringUtils.defaultString(tipomovdescrizione).toLowerCase().startsWith(ricerca)
			|| StringUtils.defaultString(tipomovcodice).toLowerCase().startsWith(ricerca)) {
		    tmovs.put(tm.getId().getTipomovimento(), Boolean.TRUE);
		}
	    }
	}
	renderMovimentiProtocolloHTMLResponse(response, tmovs, true);
    }

    @SuppressWarnings("unchecked")
    protected void renderMovimentiProtocolloHTMLResponse(HttpServletResponse response, Map<String, Boolean> tmovs, boolean showSpanConteggio)
	    throws IOException {

	StringBuffer buffer = new StringBuffer("<ul>");
	int i = 0;
	int size = 0;
	List<Tipimovimento> tmlist = new ArrayList<Tipimovimento>();
	Iterator<?> it = tmovs.entrySet().iterator();
	while (it.hasNext()) {
	    Map.Entry<String, Boolean> pairs = (Map.Entry<String, Boolean>) it.next();
	    TipimovimentoId idt = new TipimovimentoId(pairs.getKey());
	    Tipimovimento tm = tipiMovimentoService.findById(idt);
	    tmlist.add(tm);
	}
	Collections.sort(tmlist, new TipimovimentoComparator());
	try {
	    if (tmlist == null || tmlist.size() == 0) {
		buffer.append("<li id=''>").append(" ").append("</li>");
	    } else {
		size = tmlist.size();
		for (Tipimovimento tm : tmlist) {
		    String tipomovimento = tm.getId().getTipomovimento();
		    Boolean isMovimentoDaEseguire = tmovs.get(tipomovimento);
		    String cssClassName = "";
		    if (isMovimentoDaEseguire.booleanValue()) {
			cssClassName = "movimentoDaEffettuare";
		    }
		    buffer.append("<li class=\"" + cssClassName + "\" id='").append(PropertyUtils.getProperty(tm, "id.tipomovimento")).append("'>")
			    .append(PropertyUtils.getProperty(tm, "descrizioneEstesa")).append("</li>");
		    i++;
		    if (i == WebConstants.NUM_MAX_RESULTS) {
			break;
		    }
		}
	    }
	    buffer.append("</ul>");
	    if (showSpanConteggio) {
		buffer.append("<span style='font-weight: bold;'>").append("Visualizzati ").append(i).append(" risultati di ").append(size)
			.append("</span>");
	    }
	    response.setContentType("text/plain");
	    response.getWriter().write(buffer.toString());
	} catch (Exception e) {
	    log.error("Ajax search error: {}", e.getMessage());
	    throw new IOException(e);
	}
	if (log.isDebugEnabled()) {
	    log.debug("Ajax search result: {}", buffer.toString());
	}
    }

    /**
     * Metodo per recuperare le anagrafiche e le presenze per la ricerca ajax. La ricerca ajax viene utilizzata per il
     * dettaglio delle presenze in mercati presenze storico per le fiere
     * 
     * @param identAut
     * @param codicemercato
     * @param codiceuso
     * @return
     */
    @RequestMapping
    public void getProgressBarValue(HttpServletRequest request, HttpServletResponse response) {

	response.setContentType("text/plain");
	try {
	    Integer value = (Integer) request.getSession().getAttribute(WebConstants.PROGRESS_BAR);
	    if (value == null) {
		value = 0;
	    }
	    response.getWriter().write(value.toString());
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
    }

    @RequestMapping
    public void findTipiRateizzazioni(@RequestParam("code") String code, HttpServletResponse response) throws IOException {

	PkId id = new PkId(Integer.valueOf(code));
	Oneritipirateizzazione oneritipirateizzazione = oneritipirateizzazioneService.findById(id);
	StringBuffer buffer = new StringBuffer("");
	buffer.append("" + oneritipirateizzazione.getNrorate() + ",");
	buffer.append("" + oneritipirateizzazione.getRipartizionerate() + ",");
	buffer.append("" + oneritipirateizzazione.getFrequenzarate() + ",");
	buffer.append("" + oneritipirateizzazione.getScadenzarate().getId() + ",");
	buffer.append("" + oneritipirateizzazione.getInteressirate() + ",");
	buffer.append("" + oneritipirateizzazione.getFlagInteressiLegali());
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public String dettaglioInteressiLegali(Model model, HttpServletResponse response) throws IOException {

	List<InteressiLegali> list = interessiLegaliService.findAll(null, null);
	model.addAttribute("interessiLegaliList", list);
	if (log.isDebugEnabled())
	    log.debug("call InteressiLegali");
	response.setContentType("text/plain");
	return "ajax/dettaglioInteressiLegali";
    }

    @RequestMapping
    public void findOggettiinfo(@RequestParam("textToSearch") String textToSearch, @RequestParam("tipologiaOggetto") Integer tipologiaOggetto,
	    HttpServletResponse response) throws IOException {

	List<Oggettiinfo> list = oggettiinfoService.findByDescrizioneAndTipologia(textToSearch, tipologiaOggetto);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findMessaggiBase(@RequestParam("code") String code, HttpServletResponse response) throws IOException {

	Messaggicfgbase messaggicfgbase = new Messaggicfgbase();
	if (!code.equals("")) {
	    messaggicfgbase = messaggicfgbaseService.findById(code);
	}
	StringBuffer buffer = new StringBuffer("");
	if (messaggicfgbase != null) {
	    buffer = new StringBuffer(StringUtils.defaultIfEmpty(messaggicfgbase.getContesto(), "") +
		    "," +
		    StringUtils.defaultIfEmpty(messaggicfgbase.getOggetto(), "") +
		    "," +
		    StringUtils.defaultIfEmpty(messaggicfgbase.getCorpo(), "") +
		    "," +
		    messaggicfgbase.getFlgInvio() +
		    "," +
		    messaggicfgbase.getFlgTipoinvio());
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call MessaggiBase with textToSearch: '" + code + "' return: '" + buffer.toString() + "'");
    }

    @RequestMapping
    public void findAmmnistrazionireferenti(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam("codiceAmministrazione") Integer codiceAmministrazione, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	if (textToSearch != null) {
	    if (StringUtils.isNotBlank(textToSearch.replaceAll("%", ""))) {
		FilterRestriction descrizioneFilter = new FilterRestriction();
		descrizioneFilter.addFilterField(FilterUtils.like("ufficio", textToSearch));
		ft.addRestriction(descrizioneFilter);
	    }
	}
	FilterRestriction amministrazioneFilter = new FilterRestriction();
	amministrazioneFilter.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	ft.addRestriction(amministrazioneFilter);
	ft.addOrder(FilterUtils.orderAsc("ufficio"));
	List<Amministrazionireferenti> list = amministrazionireferentiService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "ufficio");
    }

    @RequestMapping
    public void findUffici(@RequestParam("codiceAmministrazione") String codiceAmministrazione, HttpServletResponse response) throws IOException {

	List<Amministrazionireferenti> list = new ArrayList<Amministrazionireferenti>();
	if (!(codiceAmministrazione == null || codiceAmministrazione.equals(""))) {
	    PkId id = new PkId(Integer.valueOf(codiceAmministrazione));
	    Amministrazioni amministrazioni = amministrazioniService.findById(id);
	    list = amministrazionireferentiService.findByAmministrazioni(amministrazioni);
	}
	renderHTMLResponse(response, list, "id.codice", "ufficio");
    }

    /**
     * recupera l'ufficio già selezionato e i gli altri possibili uffici che si possono selezionare in modifica per
     * quella amministrazione
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping
    public void findUfficio(@RequestParam("code") String code, HttpServletResponse response) throws IOException {

	StringBuffer buffer = new StringBuffer("");
	if (!(code == null || code.equals(""))) {
	    PkId id = new PkId(Integer.valueOf(code));
	    AlboPubblicazioni alboPubblicazioni = alboPubblicazioniService.findById(id);
	    if (alboPubblicazioni.getAmministrazionireferenti() != null
		    && alboPubblicazioni.getAmministrazionireferenti().getId().getCodice() != null) {
		Amministrazionireferenti amministrazionireferenti = amministrazionireferentiService
			.findById(new PkId(alboPubblicazioni.getAmministrazionireferenti().getId().getCodice()));
		if (EntityUtils.getNestedProperty(amministrazionireferenti, "id.codice") != null)
		    buffer.append("" + amministrazionireferenti.getId().getCodice() + "," + amministrazionireferenti.getUfficio() + ",");
		Set<Amministrazionireferenti> ufficiPossibili = alboPubblicazioni.getAmministrazioni().getAmministrazionireferentis();
		ufficiPossibili.remove(amministrazionireferenti);
		for (Iterator iterator = ufficiPossibili.iterator(); iterator.hasNext();) {
		    Amministrazionireferenti amministrazionireferenti1 = (Amministrazionireferenti) iterator.next();
		    buffer.append("" + amministrazionireferenti1.getId().getCodice() + "," + amministrazionireferenti1.getUfficio() + ",");
		}
	    }
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    /**
     * Metodo Ajax per cambiare le etichette
     * 
     * @param key
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void changeLabel(@RequestParam("key") String key, HttpServletRequest request, HttpServletResponse response) throws IOException {

	String value = request.getParameter("value");
	layouttestiService.overrideCode(key, value);
	LoggerUpdaterecord.log("modificata la label " + key + " con il nuovo valore " + value, getCurrentlyAuthenticatedUserDetails());
	StringBuffer buffer = new StringBuffer(value);
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void findRuoli(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Ruoli entity = new Ruoli();
	if (textToSearch != null) {
	    entity.setRuolo(textToSearch);
	}
	List<Ruoli> list = ruoliService.findByFilter(entity);
	renderHTMLResponse(response, list, "id.codice", "ruolo");
    }

    @RequestMapping
    public void findMailtipo(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(required = false, value = "codicesoftware") String codicesoftware,
	    @RequestParam(required = false, value = "ambito") String ambito, HttpServletResponse response) throws IOException {

	Mailtipo entity = new Mailtipo();
	if (textToSearch != null) {
	    entity.setDescrizione(textToSearch);
	}
	if (StringUtils.isNotBlank(codicesoftware)) {
	    Software software = softwareService.findById(codicesoftware);
	    entity.setSoftware(software);
	} else {
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    entity.setSoftware(software);
	}
	if (StringUtils.isNotBlank(ambito)) {
	    entity.setAmbito(ambito);
	}
	List<Mailtipo> list = mailtipoService.findByFilter(entity);
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesa");
    }
    
    @RequestMapping
    public void findMailtipo2(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(required = false, value = "codicesoftware") String codicesoftware,
	    @RequestParam(required = false, value = "ambito") String ambito, HttpServletResponse response) throws IOException {

	Mailtipo entity = new Mailtipo();
	if (textToSearch != null) {
	    entity.setDescrizione(textToSearch);
	}
	if (StringUtils.isNotBlank(ambito)) {
	    entity.setAmbito(ambito);
	}
	
	List<Mailtipo> list;
	String currentSoftware = ORMHelper.getSoftware();
	
	try{
	    if (StringUtils.isNotBlank(codicesoftware)) {
		    list = new ArrayList<Mailtipo>();
		    Software software = softwareService.findById(codicesoftware);
		    entity.setSoftware(software);
		    ORMHelper.setSoftware(codicesoftware);
		    list.addAll(mailtipoService.findByFilter(entity));
		    
		    if(!"TT".equals(codicesoftware)){
			Software softwareTT = softwareService.findById("TT");
			entity.setSoftware(softwareTT);
			ORMHelper.setSoftware("TT");
			list.addAll(mailtipoService.findByFilter(entity));		
		    }
	    }else{
		    list = mailtipoService.findByFilter(entity);
	    }
	}finally{
	    ORMHelper.setSoftware(currentSoftware);
	}		
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesa");
    }

    @RequestMapping
    public void findMailtipoMultiambito(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(required = false, value = "codicesoftware") String codicesoftware, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Mailtipo entity = new Mailtipo();
	if (textToSearch != null) {
	    entity.setDescrizione(textToSearch);
	}
	if (StringUtils.isNotBlank(codicesoftware)) {
	    Software software = softwareService.findById(codicesoftware);
	    entity.setSoftware(software);
	} else {
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    entity.setSoftware(software);
	}
	String[] ambiti = request.getParameterValues("ambito");
	List<Mailtipo> list = new ArrayList<Mailtipo>();
	if (ambiti != null) {
	    for (String ambito : ambiti) {
		entity.setAmbito(ambito);
		list.addAll(mailtipoService.findByFilter(entity));
	    }
	}
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesa");
    }

    /**
     * Trova le Autorizzazioni dell'istanza che non sono già collegate ad una concessione
     * 
     * @param codiceIstanza
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void findAutorizzazioniIstanza(@RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletResponse response) throws IOException {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	IstanzaAutConcHelper istanzaAutConcHelper = autorizzazioniService.findByIstanza(istanza);
	StringBuffer buffer = new StringBuffer("<ul>");
	for (Autorizzazioni autorizzazione : istanzaAutConcHelper.getAutorizzazioni()) {
	    if (autorizzazione.getAutorizzazioniConcessionisForFkAutconcAutcoll().size() == 0) {
		buffer.append("<li id='").append(autorizzazione.getId().getCodice()).append("'>").append(autorizzazione.getTransientEstremiAut())
			.append("</li>");
	    }
	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled()) {
	    log.debug("call findAutorizzazioniIstanza with codiceIstanza: '{}' return: '{}'", codiceIstanza, buffer.toString());
	}
    }

    @RequestMapping
    public void findAutorizzazioniDaEstremi(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "limit", required = false) Integer limit, HttpServletResponse response) throws IOException {

	if (log.isDebugEnabled()) {
	    log.debug("call findAutorizzazioniDaEstremi with textToSearch: {}", textToSearch);
	}
	List<Autorizzazioni> autorizzazioniList = autorizzazioniService.findByEstremi(textToSearch, limit);
	StringBuffer buffer = new StringBuffer("<ul>");
	for (Autorizzazioni autorizzazione : autorizzazioniList) {
	    buffer.append("<li id='").append(autorizzazione.getId().getCodice());
	    Anagrafe richiedente = autorizzazione.getAnagrafe();
	    String descrizioneRichiedente = "";
	    if (richiedente != null && richiedente.getId().getCodice() != null) {
		Integer richiedenteId = richiedente.getId().getCodice();
		buffer.append("' name='").append(richiedenteId).append("'>");
		descrizioneRichiedente = richiedente.getDescrizioneRichiedente();
	    } else {
		buffer.append("'>");
	    }
	    buffer.append(autorizzazione.getTransientEstremiAut()).append(",").append(descrizioneRichiedente).append("</li>");
	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call findAutorizzazioniDaEstremi with textToSearch: '{}' return: '{}'", textToSearch, buffer.toString());
    }

    @RequestMapping
    public void recuperaMailRichiedente(@RequestParam("codiceistanza") Integer codiceistanza,
	    @RequestParam("codicemovimento") Integer codicemovimento, HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	Istanze istanza = null;
	Movimenti movimento = null;
	if (codiceistanza != null) {
	    istanza = istanzeService.findById(new PkId(codiceistanza));
	    if (istanza.getRichiedente() != null) {
		Integer idRichiedente = istanza.getRichiedente().getId().getCodice();
		if (idRichiedente != null) {
		    Anagrafe richiedente = anagrafeService.findById(new PkId(idRichiedente));
		    if (StringUtils.isNotBlank(richiedente.getEmail())) {
			buff.append(richiedente.getEmail() + ";");
		    }
		    if (StringUtils.isNotBlank(richiedente.getPec())) {
			buff.append(richiedente.getPec() + ";");
		    }
		}
	    }
	} else if (codicemovimento != null) {
	    movimento = movimentiService.findById(new PkId(codicemovimento));
	    Integer idIstanza = movimento.getIstanza().getId().getCodice();
	    Istanze istanzaTemp = null;
	    if (idIstanza != null) {
		istanzaTemp = istanzeService.findById(new PkId(idIstanza));
		if (istanzaTemp.getRichiedente() != null) {
		    Integer idRich = istanzaTemp.getRichiedente().getId().getCodice();
		    if (idRich != null) {
			Anagrafe richiedente = anagrafeService.findById(new PkId(idRich));
			if (StringUtils.isNotBlank(richiedente.getEmail())) {
			    buff.append(richiedente.getEmail() + ";");
			}
			if (StringUtils.isNotBlank(richiedente.getPec())) {
			    buff.append(richiedente.getPec() + ";");
			}
		    }
		}
	    }
	}
	response.setContentType("text/plain");
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void recuperaMailAzienda(@RequestParam("codiceistanza") Integer codiceistanza, @RequestParam("codicemovimento") Integer codicemovimento,
	    HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	Istanze istanza = null;
	Movimenti movimento = null;
	if (codiceistanza != null) {
	    istanza = istanzeService.findById(new PkId(codiceistanza));
	    if (istanza.getTitolarelegale() != null) {
		Integer idazienda = istanza.getTitolarelegale().getId().getCodice();
		if (idazienda != null) {
		    Anagrafe azienda = anagrafeService.findById(new PkId(idazienda));
		    if (StringUtils.isNotBlank(azienda.getEmail())) {
			buff.append(azienda.getEmail() + ";");
		    }
		    if (StringUtils.isNotBlank(azienda.getPec())) {
			buff.append(azienda.getPec() + ";");
		    }
		}
	    }
	} else if (codicemovimento != null) {
	    movimento = movimentiService.findById(new PkId(codicemovimento));
	    Integer idIstanza = movimento.getIstanza().getId().getCodice();
	    Istanze istanzaTemp = null;
	    if (idIstanza != null) {
		istanzaTemp = istanzeService.findById(new PkId(idIstanza));
		if (istanzaTemp.getTitolarelegale() != null) {
		    Integer idAzienda = istanzaTemp.getTitolarelegale().getId().getCodice();
		    if (idAzienda != null) {
			Anagrafe azienda = anagrafeService.findById(new PkId(idAzienda));
			if (StringUtils.isNotBlank(azienda.getEmail())) {
			    buff.append(azienda.getEmail() + ";");
			}
			if (StringUtils.isNotBlank(azienda.getPec())) {
			    buff.append(azienda.getPec() + ";");
			}
		    }
		}
	    }
	}
	response.setContentType("text/plain");
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void recuperaMailTecnico(@RequestParam("codiceistanza") Integer codiceistanza, @RequestParam("codicemovimento") Integer codicemovimento,
	    HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	Istanze istanza = null;
	Movimenti movimento = null;
	if (codiceistanza != null) {
	    istanza = istanzeService.findById(new PkId(codiceistanza));
	    if (istanza.getProfessionista() != null) {
		Integer idProfessionista = istanza.getProfessionista().getId().getCodice();
		if (idProfessionista != null) {
		    Anagrafe professionista = anagrafeService.findById(new PkId(idProfessionista));
		    if (StringUtils.isNotBlank(professionista.getEmail())) {
			buff.append(professionista.getEmail() + ";");
		    }
		    if (StringUtils.isNotBlank(professionista.getPec())) {
			buff.append(professionista.getPec() + ";");
		    }
		}
	    }
	} else if (codicemovimento != null) {
	    movimento = movimentiService.findById(new PkId(codicemovimento));
	    Integer idIstanza = movimento.getIstanza().getId().getCodice();
	    Istanze istanzaTemp = null;
	    if (idIstanza != null) {
		istanzaTemp = istanzeService.findById(new PkId(idIstanza));
		if (istanzaTemp.getProfessionista() != null) {
		    Integer idprofessionista = istanzaTemp.getProfessionista().getId().getCodice();
		    if (idprofessionista != null) {
			Anagrafe professionista = anagrafeService.findById(new PkId(idprofessionista));
			if (StringUtils.isNotBlank(professionista.getEmail())) {
			    buff.append(professionista.getEmail() + ";");
			}
			if (StringUtils.isNotBlank(professionista.getPec())) {
			    buff.append(professionista.getPec() + ";");
			}
		    }
		}
	    }
	}
	response.setContentType("text/plain");
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void recuperaMailSoggettiCollegati(@RequestParam("codiceistanza") Integer codiceistanza,
	    @RequestParam("codicemovimento") Integer codicemovimento, HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	Istanze istanza = null;
	Movimenti movimento = null;
	if (codiceistanza != null) {
	    istanza = istanzeService.findById(new PkId(codiceistanza));
	    if (!istanza.getIstanzerichiedentis().isEmpty()) {
		Set<Istanzerichiedenti> istanzerichiedentis = istanza.getIstanzerichiedentis();
		for (Istanzerichiedenti istanzerichiedenti : istanzerichiedentis) {
		    if (istanzerichiedenti.getRichiedente() != null) {
			Integer idric = istanzerichiedenti.getRichiedente().getId().getCodice();
			if (idric != null) {
			    Anagrafe ric = anagrafeService.findById(new PkId(idric));
			    if (StringUtils.isNotBlank(ric.getEmail())) {
				buff.append(ric.getEmail() + ";");
			    }
			    if (StringUtils.isNotBlank(ric.getPec())) {
				buff.append(ric.getPec() + ";");
			    }
			}
		    }
		    if (istanzerichiedenti.getAnagrafeCollegata() != null) {
			Integer idanacol = istanzerichiedenti.getAnagrafeCollegata().getId().getCodice();
			if (idanacol != null) {
			    Anagrafe ric = anagrafeService.findById(new PkId(idanacol));
			    if (StringUtils.isNotBlank(ric.getEmail())) {
				buff.append(ric.getEmail() + ";");
			    }
			    if (StringUtils.isNotBlank(ric.getPec())) {
				buff.append(ric.getPec() + ";");
			    }
			}
		    }
		}
	    }
	} else if (codicemovimento != null) {
	    movimento = movimentiService.findById(new PkId(codicemovimento));
	    Integer idIstanza = movimento.getIstanza().getId().getCodice();
	    Istanze istanzaTemp = null;
	    if (idIstanza != null) {
		istanzaTemp = istanzeService.findById(new PkId(idIstanza));
		if (!istanzaTemp.getIstanzerichiedentis().isEmpty()) {
		    Set<Istanzerichiedenti> istanzerichiedentis = istanzaTemp.getIstanzerichiedentis();
		    for (Istanzerichiedenti istanzerichiedenti : istanzerichiedentis) {
			if (istanzerichiedenti.getRichiedente() != null) {
			    Integer idric = istanzerichiedenti.getRichiedente().getId().getCodice();
			    if (idric != null) {
				Anagrafe ric = anagrafeService.findById(new PkId(idric));
				if (StringUtils.isNotBlank(ric.getEmail())) {
				    buff.append(ric.getEmail() + ";");
				}
				if (StringUtils.isNotBlank(ric.getPec())) {
				    buff.append(ric.getPec() + ";");
				}
			    }
			}
			if (istanzerichiedenti.getAnagrafeCollegata() != null) {
			    Integer idanacol = istanzerichiedenti.getAnagrafeCollegata().getId().getCodice();
			    if (idanacol != null) {
				Anagrafe ric = anagrafeService.findById(new PkId(idanacol));
				if (StringUtils.isNotBlank(ric.getEmail())) {
				    buff.append(ric.getEmail() + ";");
				}
				if (StringUtils.isNotBlank(ric.getPec())) {
				    buff.append(ric.getPec() + ";");
				}
			    }
			}
		    }
		}
	    }
	}
	response.setContentType("text/plain");
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void recuperaIndirizzoElettronico(@RequestParam("codiceistanza") Integer codiceistanza, HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	Istanze istanza = null;
	if (codiceistanza != null) {
	    istanza = istanzeService.findById(new PkId(codiceistanza));
	    if (istanza != null && StringUtils.isNotBlank(istanza.getDomicilioElettronico())) {
		buff.append(istanza.getDomicilioElettronico() + ";");
	    }
	}
	response.setContentType("text/plain");
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void recuperaEnteSoftware(@RequestParam("codiceistanza") Integer idIstanza, HttpServletResponse response) throws IOException, Exception {

	StringBuffer buffer = new StringBuffer();
	Istanze istanza = null;
	Comuniassociatisoftware csw = null;
	Configurazione conf = null;
	String email = "";
	istanza = istanzeService.findById(new PkId(idIstanza));
	if (EntityUtils.getNestedProperty(istanza, "id.codice") != null) {
	    csw = comuniassociatisoftwareService.findByCodiceComune(istanza.getComune().getCodicecomune());
	    if (EntityUtils.getNestedProperty(csw, "id.codice") != null) {
		email = StringUtils.isNotBlank(csw.getMailpec()) ? csw.getMailpec() : StringUtils.defaultIfEmpty(csw.getMail(), "");
		if (StringUtils.isEmpty(email)) {
		    conf = csw.getConfigurazione();
		    if (EntityUtils.getNestedProperty(conf, "id.idcomune") != null) {
			email = StringUtils.isNotBlank(conf.getEmailresponsabilepec()) ? conf.getEmailresponsabilepec()
				: StringUtils.defaultIfEmpty(conf.getEmailresponsabile(), "");
			if (StringUtils.isEmpty(email)) {
			    csw = comuniassociatisoftwareService.findByComuneAndSoftware(istanza.getComune().getCodicecomune(),
				    WebConstants.SOFTWARE_TT);
			    if (EntityUtils.getNestedProperty(csw, "id.codice") != null) {
				email = StringUtils.isNotBlank(csw.getMailpec()) ? csw.getMailpec() : StringUtils.defaultIfEmpty(csw.getMail(), "");
				if (StringUtils.isEmpty(email)) {
				    conf = csw.getConfigurazione();
				    if (EntityUtils.getNestedProperty(conf, "id") != null) {
					email = StringUtils.isNotBlank(conf.getEmailresponsabilepec()) ? conf.getEmailresponsabilepec()
						: StringUtils.defaultIfEmpty(conf.getEmailresponsabile(), "");
				    }
				}
			    }
			}
		    }
		}
		if (StringUtils.isNotEmpty(email)) {
		    buffer.append(email + ";");
		}
	    }
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public String listaAltreIstanzeStradario(@RequestParam("codiceStradario") Integer codiceStradario, @RequestParam("isCivico") Boolean isCivico,
	    @RequestParam("isEsponente") Boolean isEsponente, @RequestParam("isColore") Boolean isColore, @RequestParam("civico") String civico,
	    @RequestParam("esponente") String esponente, @RequestParam("colore") String colore,
	    @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "urlBack", required = false) String urlBack,
	    @RequestParam(value = "showLinkIstanze", required = false) Boolean showLinkIstanze, Model model, HttpServletResponse response) {

	// §§§BEGIN§§§
	Stradario istanzestradario = stradarioService.findById(new PkId(codiceStradario));
	List<IstanzeListHelper> istanzes = null;
	Integer firstResult = Integer.valueOf(0);
	Integer maxResult = Integer.valueOf(100);
	if (istanzestradario != null) {
	    civico = BooleanUtils.toBoolean(isCivico) ? civico : null;
	    esponente = BooleanUtils.toBoolean(isEsponente) ? esponente : null;
	    colore = BooleanUtils.toBoolean(isColore) ? colore : null;
	    istanzes = istanzeService.findIstanzaLocalizzazioneSimile(codiceStradario, civico, esponente, colore, codiceIstanza, firstResult,
		    maxResult);
	} else {
	    istanzes = new ArrayList<IstanzeListHelper>();
	}
	model.addAttribute("istanzes", istanzes);
	model.addAttribute("maxResult", maxResult);
	model.addAttribute("urlBack", urlBack);
	if (showLinkIstanze == null) {
	    showLinkIstanze = Boolean.FALSE;
	}
	model.addAttribute("showLinkIstanze", showLinkIstanze);
	response.setContentType("text/plain");
	// §§§END§§§
	return "ajax/listaAltreIstanzeStradario";
    }

    @RequestMapping
    public void recuperaMailAmministrazioni(@RequestParam("codiceistanza") Integer codiceistanza,
	    @RequestParam("codicemovimento") Integer codicemovimento, HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	Movimenti movimento = null;
	if (codicemovimento != null) {
	    movimento = movimentiService.findById(new PkId(codicemovimento));
	    if (EntityUtils.getNestedProperty(movimento.getAmministrazioni(), "id.codice") != null) {
		Integer idamministrazione = movimento.getAmministrazioni().getId().getCodice();
		Amministrazioni amministrazioni = null;
		if (idamministrazione != null) {
		    amministrazioni = amministrazioniService.findById(new PkId(idamministrazione));
		    if (StringUtils.isNotBlank(amministrazioni.getEmail())) {
			buff.append(amministrazioni.getEmail() + ";");
		    }
		    if (StringUtils.isNotBlank(amministrazioni.getPec())) {
			buff.append(amministrazioni.getPec() + ";");
		    }
		}
	    }
	}
	response.setContentType("text/plain");
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void findconcessioniuso(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Concessioniuso entity = new Concessioniuso();
	if (textToSearch != null) {
	    entity.setDescrizione(textToSearch);
	}
	List<Concessioniuso> list = concessioniusoService.findByFilter(entity);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findtipospazio(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Posteggitipospazio entity = new Posteggitipospazio();
	if (textToSearch != null) {
	    entity.setTipospazio(textToSearch);
	}
	List<Posteggitipospazio> list = posteggitipospazioService.findByTipoSpazio(entity);
	renderHTMLResponse(response, list, "id.codice", "tipospazio");
    }

    @RequestMapping
    public String dettaglioPosteggio(@RequestParam(value = "idMercatipresenze", required = true) Integer idMercatipresenze,
	    @RequestParam("codicePosteggio") Integer codicePosteggio, @RequestParam(value = "codiceUso", required = false) Integer codiceUso,
	    @RequestParam(required = false, value = "showInfo") Boolean showInfo, Model model, HttpServletResponse response) throws IOException {

	PkId id = new PkId(codicePosteggio);
	MercatiD posteggio = mercatiDService.findById(id);
	model.addAttribute("posteggio", posteggio);
	List<MercatiDattivitaistat> attivitas = mercatiDattivitaistatService.findAttivitaPosteggio(codicePosteggio);
	model.addAttribute("attivitas", attivitas);
	List<PosteggioMercatiHelper> _mercatidList = null;
	if (codiceUso != null) {
	    _mercatidList = mercatiDService.findMercatiDWithConcessioniAndAvvisi(posteggio.getMercati().getId().getCodice(), posteggio, true,
		    codiceUso);
	} else {
	    _mercatidList = mercatiDService.findMercatiDWithConcessioniAndAvvisi(posteggio.getMercati().getId().getCodice(), posteggio, true);
	}
	model.addAttribute("_mercatidList", _mercatidList);
	List<MercatiCfgAttivita> listMcfgAttivita = mercatiCfgAttivitaService.findAll(null, null);
	Calendar c = Calendar.getInstance();
	Integer anno = c.get(Calendar.YEAR);
	MercatipresenzeD presenza = null;
	Date dataRiferimento = c.getTime();
	if (idMercatipresenze != null) {
	    presenza = mercatipresenzeDService.findById(new PkId(idMercatipresenze));
	    dataRiferimento = presenza.getMercatiPresenzeT().getDataRegistrazione();
	}
	PosteggioImportoHelper costoPosteggioSpuntista = calcoloCostoPosteggiService.calcolaCostoPosteggio(presenza, posteggio, listMcfgAttivita,
		anno, 1, null, WebConstants.MERCATO_CONTESTO_SPUNTISTI, null, codiceUso, dataRiferimento, MercatiFormuleCalcoloContestoEnum.PRESENZA);
	BigDecimal importoSpuntista = BigDecimal.ZERO;
	String format = "";
	if (costoPosteggioSpuntista != null) {
	    importoSpuntista = costoPosteggioSpuntista.getImporto();
	    format = "Per l'anno " +
		    anno.intValue() +
		    " l'importo per gli spuntisti e' di " +
		    NumberFormat.getCurrencyInstance(Locale.ITALY).format(importoSpuntista);
	}
	model.addAttribute("importoSpuntista", format);
	response.setContentType("text/html");
	return "ajax/dettaglioPosteggio";
    }

    @RequestMapping
    public void findLavoritipi(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Lavoritipi> list = lavoritipiService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "lavoro");
    }

    @RequestMapping
    public void findTipicausalioneri(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codice", required = false) String codice, HttpServletResponse response) throws IOException {

	List<Tipicausalioneri> list = tipicausalioneriService.findByDescrizione(textToSearch, codice);
	renderHTMLResponse(response, list, "id.codice", "coDescrizione");
    }

    @RequestMapping
    public void findTipicausalioneriMora(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware, HttpServletResponse response) throws IOException {

	List<Tipicausalioneri> list = tipicausalioneriService.findTipicausalioneriMoraByDescrizione(textToSearch, codicesoftware);
	renderHTMLResponse(response, list, "id.codice", "coDescrizione");
    }

    /**
     * Viene utilizzato nel formOneri degli endoprocedimenti per gestire la visualizzazione del campo importo
     * istruttoria
     * 
     * @param textToSearch
     * @param codice
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void findTipicausalioneriForInventarioprocedimentioneri(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codice", required = false) String codice, HttpServletResponse response) throws IOException {

	List<Tipicausalioneri> list = tipicausalioneriService.findByDescrizione(textToSearch, codice);
	StringBuffer sbuf = new StringBuffer("<ul>");
	Boolean isImportoIstruttoriaImpostabile = Boolean.FALSE;
	for (Tipicausalioneri tipicausalioneri : list) {
	    isImportoIstruttoriaImpostabile = tipicausalioneriService.isImportoIstruttoriaImpostabile(tipicausalioneri);
	    sbuf.append("<li id=\"").append(tipicausalioneri.getId().getCodice()).append("#")
		    .append(BooleanUtils.toBoolean(isImportoIstruttoriaImpostabile)).append("\">").append(tipicausalioneri.getCoDescrizione())
		    .append("</li>");
	}
	sbuf.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(sbuf.toString());
    }

    @RequestMapping
    public void findTipicausalioneriFilterByFlagEndo(@RequestParam("textToSearch") String textToSearch, @RequestParam("flagEndo") Boolean flagEndo,
	    @RequestParam("codicesoftware") String codicesoftware, HttpServletResponse response) throws IOException {

	List<Tipicausalioneri> list = tipicausalioneriService.findByDescrizioneAndFlagEndo(textToSearch, flagEndo, codicesoftware);
	StringBuffer sbuf = new StringBuffer("<ul>");
	Boolean isImportoIstruttoriaImpostabile = Boolean.FALSE;
	for (Tipicausalioneri tipicausalioneri : list) {
	    isImportoIstruttoriaImpostabile = tipicausalioneriService.isImportoIstruttoriaImpostabile(tipicausalioneri);
	    sbuf.append("<li id=\"").append(tipicausalioneri.getId().getCodice()).append("#")
		    .append(BooleanUtils.toBoolean(isImportoIstruttoriaImpostabile)).append("\">").append(tipicausalioneri.getCoDescrizione())
		    .append("</li>");
	}
	sbuf.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(sbuf.toString());
    }

    @RequestMapping
    public void findDyn2RegoleAttivazione(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	String _codicesoftware = ORMHelper.getSoftware();
	// Nel caso è stato passato sulla request lo sovrascriaviamo
	if (StringUtils.isNotBlank(codicesoftware)) {
	    _codicesoftware = codicesoftware;
	}
	List<Dyn2Regole> list = dyn2RegoleService.findByDescrizioneAndSoftware(textToSearch, _codicesoftware);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findTipiapertura(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Tipiapertura entity = new Tipiapertura();
	if (textToSearch != null) {
	    entity.setTaDescrizione(textToSearch);
	}
	List<Tipiapertura> list = tipiaperturaService.findByDescrizione(entity);
	renderHTMLResponse(response, list, "id.codice", "taDescrizione");
    }

    @RequestMapping
    public void findTipiprocedure(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "includiDisabilitate", required = false) Boolean includiDisabilitate,
	    @RequestParam(value = "soloConMovimentoAvvio", required = false) Boolean soloConMovimentoAvvio, HttpServletResponse response)
	    throws IOException {

	if (includiDisabilitate == null) {
	    includiDisabilitate = Boolean.FALSE;
	}
	if (soloConMovimentoAvvio == null) {
	    soloConMovimentoAvvio = Boolean.FALSE;
	}
	List<Tipiprocedure> list = tipiprocedureService.findByCodiceODescrizione(textToSearch, includiDisabilitate, soloConMovimentoAvvio);
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesa");
    }

    @RequestMapping
    public void findonericomportamento(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Onericomportamento> list = onericomportamentoService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "codicecomportamento", "comportamento");
    }

    @RequestMapping
    public void findFaqclassi(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Faqclassi> list = faqclassiService.findByFaqclasse(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "faqclasse");
    }

    @RequestMapping
    public void findNormative(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Normative> list = normativeService.findByNormativa(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "normativa");
    }

    @RequestMapping
    public void findSoftwareAbilitatiResponsabile(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "escludiNonOpzionali", required = false) Boolean escludiNonOpzionali,
	    @RequestParam(value = "codiceResponsabile", required = false) Integer codiceResponsabile, HttpServletResponse response)
	    throws IOException {

	if (escludiNonOpzionali == null) {
	    escludiNonOpzionali = Boolean.FALSE;
	}
	Software entity = new Software();
	if (textToSearch != null) {
	    entity.setDescrizione(textToSearch);
	}
	Responsabili responsabile = null;
	if (codiceResponsabile != null) {
	    responsabile = responsabiliService.findById(new PkId(codiceResponsabile));
	} else {
	    LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	    responsabile = responsabiliService.findById(new PkId(userDetail.getCodiceResponsabile()));
	}
	List<Software> softwareList = new ArrayList<Software>();
	List<Responsabilisoftware> responsabilisoftwares = responsabilisoftwareService.findBySoftware(responsabile, entity);
	for (Responsabilisoftware responsabilisoftware : responsabilisoftwares) {
	    if (escludiNonOpzionali && BooleanUtils.isTrue(responsabilisoftware.getSoftware().getModuloopzionale())) {
		softwareList.add(responsabilisoftware.getSoftware());
	    }
	    if (!escludiNonOpzionali) {
		softwareList.add(responsabilisoftware.getSoftware());
	    }
	}
	renderHTMLResponse(response, softwareList, "codice", "descrizione");
    }

    @RequestMapping
    public void findNaturaEndo(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Naturaendo entity = new Naturaendo();
	if (textToSearch != null) {
	    entity.setNatura(textToSearch);
	}
	List<Naturaendo> list = naturaendoService.findBydescrizione(entity.getNatura());
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesa");
    }

    @RequestMapping
    public void findStradarioMercato(@RequestParam("codicemercato") Integer codicemercato,
	    @RequestParam(value = "searchDisabilitati", required = false) String searchDisabilitati,
	    @RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Stradario> list = new ArrayList<Stradario>();
	if (StringUtils.isNotBlank(searchDisabilitati) && searchDisabilitati.equals("false")) {
	    list = stradarioService.findByMercatoAndDescrizione(codicemercato, textToSearch, false);
	} else {
	    list = stradarioService.findByMercatoAndDescrizione(codicemercato, textToSearch, true);
	}
	renderHTMLResponse(response, list, "id.codice", "descrizioneCompleta");
    }

    @RequestMapping
    public String dettaglioMessaggio(@RequestParam("codiceMessaggio") Integer codiceMessaggio, Model model, HttpServletResponse response)
	    throws IOException {

	PkId id = new PkId(codiceMessaggio);
	Messaggi messaggio = messaggiService.findById(id);
	model.addAttribute("messaggio", messaggio);
	if (log.isDebugEnabled())
	    log.debug("call dettaglioMessaggio with codiceMessaggio: " + codiceMessaggio);
	response.setContentType("text/plain");
	return "ajax/dettaglioMessaggio";
    }

    @RequestMapping
    public void findTempificazioni(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Tempificazioni entity = new Tempificazioni();
	if (textToSearch != null) {
	    entity.setTempificazione(textToSearch);
	}
	List<Tempificazioni> list = tempificazioniService.findByDescrizione(entity.getTempificazione());
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesa");
    }

    @RequestMapping
    public void findTipiarchivioistanze(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	if (StringUtils.isNotBlank(textToSearch)) {
	    FilterRestriction criterio = new FilterRestriction();
	    criterio.addFilterField(FilterUtils.like("archivio", textToSearch));
	    ft.addRestriction(criterio);
	}
	ft.addOrder(FilterUtils.orderAsc("archivio"));
	List<Tipiarchivioistanze> list = tipiarchivioistanzeService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "archivio");
    }

    @RequestMapping
    public void findcittadinanze(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	// ///////////////////////
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	if (StringUtils.isNotBlank(textToSearch)) {
	    String[] params = StringUtils.split(textToSearch);
	    FilterRestriction criterio = new FilterRestriction();
	    for (String param : params) {
		criterio.addFilterField(new FilterField<String>("cittadinanza", FieldOperationsEnum.CONTAINS, new String[] { param }, String.class));
	    }
	    ft.addRestriction(criterio);
	}
	FilterOrder<String> orderByScDescrizione = new FilterOrder<String>(new FilterField<String>("cittadinanza", null, String.class));
	ft.addOrder(orderByScDescrizione);
	// /////////////////////
	List<Cittadinanza> list = cittadinanzaService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "codice", "cittadinanza");
    }

    @RequestMapping
    public void findFormegiuridiche(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	// ///////////////////////
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	if (StringUtils.isNotBlank(textToSearch)) {
	    String[] params = StringUtils.split(textToSearch);
	    FilterRestriction criterio = new FilterRestriction();
	    for (String param : params) {
		criterio.addFilterField(
			new FilterField<String>("formagiuridica", FieldOperationsEnum.CONTAINS, new String[] { param }, String.class));
	    }
	    ft.addRestriction(criterio);
	}
	FilterOrder<String> orderByScDescrizione = new FilterOrder<String>(new FilterField<String>("formagiuridica", null, String.class));
	ft.addOrder(orderByScDescrizione);
	// /////////////////////
	List<Formegiuridiche> list = formegiuridicheService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "formagiuridica");
    }

    @RequestMapping
    public void findVwprovince(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	// ///////////////////////
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	if (StringUtils.isNotBlank(textToSearch)) {
	    String[] params = StringUtils.split(textToSearch);
	    FilterRestriction criterio = new FilterRestriction();
	    for (String param : params) {
		criterio.addFilterField(new FilterField<String>("provincia", FieldOperationsEnum.CONTAINS, new String[] { param }, String.class));
	    }
	    ft.addRestriction(criterio);
	}
	FilterOrder<String> orderByScDescrizione = new FilterOrder<String>(new FilterField<String>("siglaprovincia", null, String.class));
	ft.addOrder(orderByScDescrizione);
	// /////////////////////
	List<VwProvince> list = vwProvinceService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "siglaprovincia", "provincia");
    }

    @RequestMapping
    public void findTipologiaistanze(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	if (StringUtils.isNotBlank(textToSearch)) {
	    FilterRestriction criterio = new FilterRestriction();
	    criterio.addFilterField(FilterUtils.like("tiDescrizione", textToSearch));
	    ft.addRestriction(criterio);
	}
	ft.addOrder(FilterUtils.orderAsc("tiDescrizione"));
	List<Tipologiaistanza> list = tipologiaistanzaService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "tiDescrizione");
    }

    @RequestMapping
    public void findtipodocumento(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	if (StringUtils.isNotBlank(textToSearch)) {
	    FilterRestriction criterio = new FilterRestriction();
	    criterio.addFilterField(FilterUtils.like("documento", textToSearch));
	    ft.addRestriction(criterio);
	}
	ft.addOrder(FilterUtils.orderAsc("documento"));
	List<Tipidocumento> list = tipidocumentoService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "documento");
    }

    /**
     * Il metodo recupera tutti i responsabili filtrando per :
     * 
     * responsabile : ilike match.ANYWHERE software : il software corrente disabilitato : false
     * tiporesponsabile.trFlagresponsabile : true
     * 
     * Nel caso non esistano responsabili secondo questi filtri la ricerca viene ripetuta toglendo il filtro --->
     * tiporesponsabile.trFlagresponsabile : true
     * 
     * @param textToSearch
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void findResponsabiliBySoftware(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	if (StringUtils.isNotBlank(textToSearch)) {
	    criterio.addFilterField(FilterUtils.like("responsabile", textToSearch));
	    ft.addRestriction(criterio);
	}
	criterio.addFilterField(FilterUtils.equals("id.software", ORMHelper.getSoftware(), "softwareAbilitati", String.class));
	// Tolto dopo i problemi avuti con Assisi.
	// criterio.addFilterField(FilterUtils.equals("trFlagresponsabile", true, "tiporesponsabile", Boolean.class));
	criterio.addFilterField(FilterUtils.equals("disabilitato", false, Boolean.class));
	ft.addOrder(FilterUtils.orderAsc("responsabile"));
	List<Responsabili> list = responsabiliService.findByFilterTable(ft);
	if (list.isEmpty()) {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    criterio = new FilterRestriction();
	    if (StringUtils.isNotBlank(textToSearch)) {
		criterio.addFilterField(FilterUtils.like("responsabile", textToSearch));
		ft.addRestriction(criterio);
	    }
	    criterio.addFilterField(FilterUtils.equals("id.software", ORMHelper.getSoftware(), "softwareAbilitati", String.class));
	    criterio.addFilterField(FilterUtils.equals("disabilitato", false, Boolean.class));
	    ft.addOrder(FilterUtils.orderAsc("responsabile"));
	    list = responsabiliService.findByFilterTable(ft);
	}
	renderHTMLResponse(response, list, "id.codice", "responsabile");
    }

    @RequestMapping
    public void findTipisoggetto(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "flagQualita", required = false) Boolean flagQualita, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	if (StringUtils.isNotBlank(textToSearch)) {
	    FilterRestriction criterio = new FilterRestriction();
	    criterio.addFilterField(FilterUtils.like("tiposoggetto", textToSearch));
	    ft.addRestriction(criterio);
	}
	if (flagQualita != null) {
	    FilterRestriction qualita = new FilterRestriction();
	    qualita.addFilterField(FilterUtils.equals("flagqualita", flagQualita, Boolean.class));
	    ft.addRestriction(qualita);
	}
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("tiposoggetto"));
	List<Tipisoggetto> list = tipisoggettoService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "tiposoggetto");
    }

    @RequestMapping
    public void findTipisoggettoAndSpecificadescrizione(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "flagQualita", required = false) Boolean flagQualita, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	if (StringUtils.isNotBlank(textToSearch)) {
	    FilterRestriction criterio = new FilterRestriction();
	    criterio.addFilterField(FilterUtils.like("tiposoggetto", textToSearch));
	    ft.addRestriction(criterio);
	}
	if (flagQualita != null) {
	    FilterRestriction qualita = new FilterRestriction();
	    qualita.addFilterField(FilterUtils.equals("flagqualita", flagQualita, Boolean.class));
	    ft.addRestriction(qualita);
	}
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("tiposoggetto"));
	List<Tipisoggetto> list = tipisoggettoService.findByFilterTable(ft);
	StringBuffer sbuf = new StringBuffer("<ul>");
	for (Tipisoggetto tipisoggetto : list) {
	    sbuf.append("<li id=\"").append(tipisoggetto.getId().getCodice()).append("#")
		    .append(BooleanUtils.toBoolean(tipisoggetto.getFlgSpecificadescrizione())).append("\">").append(tipisoggetto.getTiposoggetto())
		    .append("</li>");
	}
	sbuf.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(sbuf.toString());
    }

    @RequestMapping
    public void findTipisoggettoAndSpecificadescrizioneAndRichiedianagrafecoll(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "flagQualita", required = false) Boolean flagQualita, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	if (StringUtils.isNotBlank(textToSearch)) {
	    FilterRestriction criterio = new FilterRestriction();
	    criterio.addFilterField(FilterUtils.like("tiposoggetto", textToSearch));
	    ft.addRestriction(criterio);
	}
	if (flagQualita != null) {
	    FilterRestriction qualita = new FilterRestriction();
	    qualita.addFilterField(FilterUtils.equals("flagqualita", flagQualita, Boolean.class));
	    ft.addRestriction(qualita);
	}
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("tiposoggetto"));
	List<Tipisoggetto> list = tipisoggettoService.findByFilterTable(ft);
	StringBuffer sbuf = new StringBuffer("<ul>");
	for (Tipisoggetto tipisoggetto : list) {
	    sbuf.append("<li id=\"").append(tipisoggetto.getId().getCodice()).append("#")
		    .append(BooleanUtils.toBoolean(tipisoggetto.getFlgSpecificadescrizione())).append("#")
		    .append(BooleanUtils.toBoolean(tipisoggetto.getRichiedianagrafecoll())).append("\">").append(tipisoggetto.getTiposoggetto())
		    .append("</li>");
	}
	sbuf.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(sbuf.toString());
    }

    @RequestMapping
    public void findImpianti(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Impianti> list = impiantiService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "impianto");
    }

    @RequestMapping()
    public ModelAndView jsonFindMovimentoAvvioProcedura(@RequestParam(value = "codice", required = false) Integer codice,
	    HttpServletResponse response) throws IOException {

	Tipiprocedure procedura = tipiprocedureService.findById(new PkId(codice));
	if (null == procedura) {
	    throw new RuntimeException("La procedura con codice " + codice + " non è stata trovata.");
	}
	Set<Tipiprocedureavvio> tipiprocedureavvios = procedura.getTipiProcedureavvios();
	Tipimovimento tm = new Tipimovimento();
	for (Tipiprocedureavvio tma : tipiprocedureavvios) {
	    boolean isDefault = tma.getDefaultsn() == null ? false : tma.getDefaultsn().booleanValue();
	    if (isDefault) {
		tm.getId().setTipomovimento(tma.getId().getTipomovimento());
		tm.setMovimento(tma.getTipoMovimento().getMovimento());
	    }
	}
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("tipoMovimento", tm);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public void findMovimentiAvvioProcedura(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam("codiceProcedura") Integer codiceProcedura, HttpServletResponse response) throws IOException {

	Tipiprocedure procedura = tipiprocedureService.findById(new PkId(codiceProcedura));
	if (null == procedura) {
	    renderHTMLException("E' necessario specificare una procedura", response);
	    return;
	    // throw new RuntimeException("La procedura con codice " + codiceProcedura + " non è stata trovata.");
	}
	Set<Tipiprocedureavvio> tipiprocedureavvios = procedura.getTipiProcedureavvios();
	if (tipiprocedureavvios.size() == 0) {
	    renderHTMLException("La procedura \"" + procedura.getProcedura() + "\" [" + codiceProcedura + "] non ha movimenti di avvio configurati.",
		    response);
	    return;
	}
	List<Tipimovimento> list = new ArrayList<Tipimovimento>();
	for (Tipiprocedureavvio tipiprocedureavvio : tipiprocedureavvios) {
	    Tipimovimento tipimovimento = tipiprocedureavvio.getTipoMovimento();
	    if (StringUtils.isBlank(textToSearch) || textToSearch.equalsIgnoreCase("%")) {
		list.add(tipimovimento);
	    } else {
		String movimento = tipimovimento.getMovimento();
		if (movimento.toLowerCase().contains(textToSearch.toLowerCase())) {
		    list.add(tipimovimento);
		}
	    }
	}
	renderHTMLResponse(response, list, "id.tipomovimento", "movimento");
    }

    @RequestMapping
    public void findElenchiprofessionalibase(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	if (StringUtils.isNotBlank(textToSearch)) {
	    FilterRestriction criterio = new FilterRestriction();
	    criterio.addFilterField(
		    new FilterField<String>("epDescrizione", FieldOperationsEnum.CONTAINS, new String[] { textToSearch }, String.class));
	    ft.addRestriction(criterio);
	}
	FilterOrder<String> orderByScDescrizione = new FilterOrder<String>(new FilterField<String>("epDescrizione", null, String.class));
	ft.addOrder(orderByScDescrizione);
	List<Elenchiprofessionalibase> list = elenchiprofessionalibaseService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id", "epDescrizione");
    }

    @RequestMapping
    public void findAllegatiByProcedimento(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceInventario", required = false) Integer codiceinventario, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	if (StringUtils.isNotBlank(textToSearch)) {
	    FilterRestriction criterio = new FilterRestriction();
	    criterio.addFilterField(FilterUtils.equals("inventarioprocedimento.id.codice", codiceinventario, Integer.class));
	    criterio.addFilterField(FilterUtils.like("allegato", textToSearch));
	    ft.addRestriction(criterio);
	}
	FilterOrder<String> orderByScDescrizione = new FilterOrder<String>(new FilterField<String>("allegato", null, String.class));
	ft.addOrder(orderByScDescrizione);
	List<Allegati> list = allegatiService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "allegato");
    }

    @RequestMapping
    public void findIstanzestradario(@RequestParam("textToSearch") String textToSearch, @RequestParam("codiceIstanza") Integer codice,
	    HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	Istanze istanza = istanzeService.findById(new PkId(codice));
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("istanza", istanza, Istanze.class));
	if (StringUtils.isNotBlank(textToSearch)) {
	    criterio.addFilterField(FilterUtils.like("descrizione", textToSearch, "stradario"));
	    ft.addRestriction(criterio);
	}
	ft.addOrder(FilterUtils.orderAsc("descrizione", "stradario"));
	List<Istanzestradario> list = istanzestradarioService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesaTransient");
    }

    @RequestMapping
    public void findLavoricategorie(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction criterio = new FilterRestriction();
	if (StringUtils.isNotBlank(textToSearch)) {
	    criterio.addFilterField(FilterUtils.like("categoria", textToSearch));
	    ft.addRestriction(criterio);
	}
	ft.addOrder(FilterUtils.orderAsc("categoria"));
	List<Lavoricategorie> list = lavoricategorieService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "categoria");
    }

    @RequestMapping
    public void findLavoritipiAndCategoria(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceCategoriaLavoro", required = false) Integer codiceCategoriaLavoro, HttpServletResponse response)
	    throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction criterio = new FilterRestriction();
	if (codiceCategoriaLavoro != null) {
	    criterio.addFilterField(FilterUtils.equals("id.codice", codiceCategoriaLavoro, "lavoricategorie", Integer.class));
	}
	if (StringUtils.isNotBlank(textToSearch)) {
	    criterio.addFilterField(FilterUtils.like("lavoro", textToSearch));
	    ft.addRestriction(criterio);
	}
	ft.addOrder(FilterUtils.orderAsc("lavoro"));
	List<Lavoritipi> list = lavoritipiService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "lavoro");
    }

    /**
     * metodo per il reload dei parametri di sigeprosecurity
     * 
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping
    public void reloadSigeproSecurityParams(HttpServletResponse response) throws IOException {

	Map<String, String> map = WebConstants.reloadSecurityParamsMap();
	Set set = map.entrySet();
	StringBuffer buf = new StringBuffer();
	for (Iterator iterator = set.iterator(); iterator.hasNext();) {
	    Map.Entry entry = (Map.Entry) iterator.next();
	    buf.append(entry.getKey()).append(" = ").append(entry.getValue()).append("<br />");
	}
	response.setContentType("text/plain");
	response.getWriter().write(buf.toString());
    }

    @RequestMapping
    public String dettaglioResponsabiliPermessi(@RequestParam("codice") Integer codice, @RequestParam("permessisoftware") String permessisoftware,
	    @RequestParam("status_msg") String status_msg, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	String sitemesh_configfile = ORMHelper.getProductStyle();// request.getSession().getServletContext().getInitParameter("sitemesh.configfile");
	boolean v2 = false;
	if (StringUtils.defaultString(sitemesh_configfile).indexOf("v2") >= 0
		|| StringUtils.defaultString(sitemesh_configfile).toLowerCase().indexOf("sporvic3") >= 0) {
	    v2 = true;
	}
	PkId id = new PkId(codice);
	Responsabili entity = responsabiliService.findById(id);
	ResponsabiliCommand responsabile = new ResponsabiliCommand();
	responsabile.setEntity(entity);
	List<Clpermmenu> clpermmenuList = new ArrayList<Clpermmenu>();
	List<Clmenu> clmenuList = clmenuService.findTreeBySoftware(permessisoftware, v2);
	Software software = softwareService.findById(permessisoftware);
	// clmenuList = clmenuService.findTreeBySoftware(permessisoftware);
	// software = softwareService.findById(permessisoftware);
	for (Clmenu clmenu : clmenuList) {
	    Boolean escluso = false;
	    String softwareEsclusi = clmenu.getSoftwareesclusi();
	    if (softwareEsclusi != null) {
		StringTokenizer st = new StringTokenizer(softwareEsclusi, ",");
		while (st.hasMoreTokens() && escluso == false) {
		    String token = st.nextToken();
		    if (token.equals(permessisoftware)) {
			escluso = true;
			break;
		    }
		}
	    }
	    if (!escluso) {
		if (StringUtils.isNotBlank(clmenu.getVerticalizzazione())) {
		    Verticalizzazioni verticalizzazioni = verticalizzazioniService.findByModulo(clmenu.getVerticalizzazione());
		    if (verticalizzazioni != null && verticalizzazioni.getAttivo() == 0) {
			escluso = true;
		    }
		}
		int menulinkLen = 0;
		if (v2) {
		    menulinkLen = clmenu.getMenulinkV2().length();
		} else {
		    menulinkLen = clmenu.getMenulink().length();
		}
		if (menulinkLen > 1) {
		    if (BooleanUtils.isFalse(software.getModuloopzionale())) {
			if (clmenu.getSoftware().equalsIgnoreCase("*")) {
			    escluso = true;
			}
		    }
		}
		if (!escluso) {
		    Clpermmenu clpermmenu = new Clpermmenu();
		    // creo un nuovo oggetto clmenu perchè devo modificare una prop della entity recuperata da hibernate.
		    // non posso modificarla direttamente perchè hibernate la aggiornerebbe su db!!
		    Clmenu _clmenu = new Clmenu();
		    _clmenu.setId(clmenu.getId());
		    _clmenu.setJsp(clmenu.getJsp());
		    _clmenu.setLayouttesti(clmenu.getLayouttesti());
		    if (v2) {
			_clmenu.setMenulink(clmenu.getMenulinkV2());
		    } else {
			_clmenu.setMenulink(clmenu.getMenulink());
		    }
		    _clmenu.setPagina(clmenu.getPagina());
		    _clmenu.setSoftware(clmenu.getSoftware());
		    _clmenu.setSoftwareesclusi(clmenu.getSoftwareesclusi());
		    _clmenu.setVerticalizzazione(clmenu.getVerticalizzazione());
		    _clmenu.setMenulinkV2(clmenu.getMenulinkV2());
		    String descrizione = clmenu.getDescrizione();
		    String descrizioneDecodificata = descrizione.replaceAll("SOFTWARE", software.getDescrizione());
		    _clmenu.setDescrizione(descrizioneDecodificata);
		    // clmenu.setDescrizione(clmenu.getDescrizione().replaceAll("SOFTWARE", software.getDescrizione()));
		    clpermmenu.setMenu(_clmenu);
		    clpermmenu.setResponsabile(entity);
		    clpermmenu.setSoftware(software);
		    clpermmenuList.add(clpermmenu);
		}
	    }
	}
	Collections.sort(clpermmenuList, new ClpermmenuResponsabiliComparator());
	responsabile.setClpermmenuList(clpermmenuList);
	model.addAttribute("permessisoftware", permessisoftware);
	model.addAttribute("responsabile", responsabile);
	model.addAttribute("status_msg", status_msg);
	if (log.isDebugEnabled())
	    log.debug("call schedaPermessiMenu with permessisoftware: " + permessisoftware + " and codice: " + codice);
	response.setContentType("text/plain");
	return "ajax/dettaglioResponsabiliPermessi";
    }

    @RequestMapping
    public String dettaglioIstanzaprocedimento(@RequestParam("codiceinventario") String codiceinventario,
	    @RequestParam("codiceistanza") String codiceistanza,
	    @RequestParam(required = false, value = "isCallFromMovimenti") String isCallFromMovimenti,
	    @RequestParam(required = false, value = "codiceMovimento") Integer codiceMovimento, Model model, HttpServletResponse response)
	    throws IOException {

	IstanzeprocedimentiId id = new IstanzeprocedimentiId(Integer.parseInt(codiceistanza), Integer.parseInt(codiceinventario));
	Istanzeprocedimenti entity = istanzeprocedimentiService.findById(id);
	IstanzeprocedimentiCommand istanzeprocedimenti = new IstanzeprocedimentiCommand();
	istanzeprocedimenti.setEntity(entity);
	model.addAttribute("istanzeprocedimentiCommand", istanzeprocedimenti);
	boolean isIstanzaModificabile = istanzeService.checkModificaIstanza(entity.getIstanza());
	model.addAttribute("isModificaIstanza", isIstanzaModificabile);
	model.addAttribute("isCallFromMovimenti", BooleanUtils.toBoolean(isCallFromMovimenti));
	if (codiceMovimento != null) {
	    model.addAttribute("codiceMovimento", codiceMovimento);
	}
	if (log.isDebugEnabled())
	    log.debug("call dettaglio istanze procedimenti with codiceIstanzaprocedimento: " + codiceinventario);
	response.setContentType("text/plain");
	return "ajax/dettaglioIstanzaprocedimento";
    }

    @RequestMapping
    public String dettaglioAlberoprocateco(@RequestParam("codiceAlberoproc") Integer codiceAlberoproc, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	PkId id = new PkId(codiceAlberoproc);
	Alberoproc alberoproc = alberoprocService.findById(id);
	model.addAttribute("alberoproc", alberoproc);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ALBEROPROC_LIST_ATECO, "0", request);
	model.addAttribute("CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE, "1", request)));
	model.addAttribute("CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE",
		leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE, "0", request));
	if (log.isDebugEnabled())
	    log.debug("call DettaglioAlberoprocateco with codiceAlberoproc: " + codiceAlberoproc);
	response.setContentType("text/plain");
	return "ajax/dettaglioAlberoprocateco";
    }

    @RequestMapping
    public String dettaglioRespTipimov(@RequestParam("tipo") String tipo, @RequestParam("codiceResponsabile") Integer codiceResponsabile, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (codiceResponsabile == null) {
	    throw new RuntimeException("Il parametro codiceresponsabile non può essere nullo");
	}
	if (StringUtils.isNotEmpty(tipo)) {
	    if (tipo.equalsIgnoreCase("avv")) {
		List<ResponsabiliTmAvv> rtms = responsabiliTmAvvService.findByResponsabile(codiceResponsabile, Boolean.FALSE);
		model.addAttribute("listaMov", rtms);
	    }
	    if (tipo.equalsIgnoreCase("sca")) {
		List<ResponsabiliTmSca> rtms = responsabiliTmScaService.findByResponsabile(codiceResponsabile, Boolean.FALSE);
		model.addAttribute("listaMov", rtms);
	    }
	    if (tipo.equalsIgnoreCase("avv_esclude")) {
		List<ResponsabiliTmAvv> rtms = responsabiliTmAvvService.findByResponsabile(codiceResponsabile, Boolean.TRUE);
		model.addAttribute("listaMov", rtms);
	    }
	    if (tipo.equalsIgnoreCase("sca_esclude")) {
		List<ResponsabiliTmSca> rtms = responsabiliTmScaService.findByResponsabile(codiceResponsabile, Boolean.TRUE);
		model.addAttribute("listaMov", rtms);
	    }
	}
	model.addAttribute("tipo", tipo);
	response.setContentType("text/plain");
	return "ajax/dettaglioRespTipimov";
    }

    @RequestMapping
    public void findTipiMovPerResponsabile(@RequestParam("textToSearch") String textToSearch, @RequestParam("tipo") String tipo,
	    @RequestParam("codiceResponsabile") Integer codiceResponsabile,
	    @RequestParam(value = "includiDisabilitate", required = false) Boolean includiDisabilitate, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	if (includiDisabilitate == null) {
	    includiDisabilitate = Boolean.FALSE;
	}
	if (codiceResponsabile == null) {
	    throw new RuntimeException("Il parametro codiceresponsabile non può essere nullo");
	}
	List<Tipimovimento> rtms = tipiMovimentoService.findByTipimovAndResponsabile(textToSearch, codiceResponsabile, includiDisabilitate);
	renderHTMLResponse(response, rtms, "id.tipomovimento", "descrizioneEstesa");
    }

    @RequestMapping
    public void insertRespTipimov(@RequestParam("tipomovimento") String tipomovimento, @RequestParam("tipo") String tipo,
	    @RequestParam("codiceResponsabile") Integer codiceResponsabile, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	String result = "";
	if (codiceResponsabile == null) {
	    throw new RuntimeException("Il parametro codiceresponsabile non può essere nullo");
	}
	if (StringUtils.isBlank(tipomovimento)) {
	    throw new RuntimeException("Il parametro tipomovimento non può essere nullo");
	}
	Responsabili resp = responsabiliService.findById(new PkId(codiceResponsabile));
	if (resp == null) {
	    throw new RuntimeException("Non è stato trovato nessun operatore con codice " + String.valueOf(codiceResponsabile));
	}
	Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(tipomovimento));
	if (tm == null) {
	    throw new RuntimeException("Non è stato trovato nessun tipomovimento con codice " + tipomovimento);
	}
	if (StringUtils.isNotEmpty(tipo)) {
	    if (tipo.equalsIgnoreCase("avv")) {
		ResponsabiliTmAvvId id = new ResponsabiliTmAvvId(codiceResponsabile, tipomovimento);
		ResponsabiliTmAvv obj = responsabiliTmAvvService.findById(id);
		if (obj == null) {
		    obj = new ResponsabiliTmAvv();
		    obj.setId(id);
		    obj.setFlagEsclude(Boolean.FALSE);
		    responsabiliTmAvvService.insert(obj);
		} else {
		    // Poiché il tipomovimento è già presente, invio il seguente messaggio per informare l'utente 
		    // che non può inserire lo stesso tipomovimento se è già limitato o escluso dalla ricerca.
		    result = "IS_PRESENT|Il tipomovimento [<b>" +
			    tm.getDescrizioneEstesa() +
			    "</b>] o &eacute; stato escluso dalla ricerca dei movimenti da \"DA VISIONARE\" e \"NON NOTIFICATI\" o gi&agrave; ne limita la ricerca.";
		    response.getOutputStream().write(result.getBytes());
		    log.debug("insertRespTipimov# tipo: avv, response: {}", response);
		}
	    }
	    if (tipo.equalsIgnoreCase("sca")) {
		ResponsabiliTmScaId id = new ResponsabiliTmScaId(codiceResponsabile, tipomovimento);
		ResponsabiliTmSca obj = responsabiliTmScaService.findById(id);
		if (obj == null) {
		    obj = new ResponsabiliTmSca();
		    obj.setId(id);
		    obj.setFlagEsclude(Boolean.FALSE);
		    responsabiliTmScaService.insert(obj);
		} else {
		    // Poiché il tipomovimento è già presente, invio il seguente messaggio per informare l'utente 
		    // che non può inserire lo stesso tipomovimento se è già limitato o escluso dalla ricerca.
		    result = "IS_PRESENT|Il tipomovimento [<b>" +
			    tm.getDescrizioneEstesa() +
			    "</b>] o &eacute; stato escluso dalla ricerca delle scadenze \"DA EFFETTUARE\" o gi&agrave; ne limita la ricerca.";
		    response.getOutputStream().write(result.getBytes());
		    log.debug("insertRespTipimov# tipo: sca, response: {}", response);
		}
	    }
	    if (tipo.equalsIgnoreCase("avv_esclude")) {
		ResponsabiliTmAvvId id = new ResponsabiliTmAvvId(codiceResponsabile, tipomovimento);
		ResponsabiliTmAvv obj = responsabiliTmAvvService.findById(id);
		if (obj == null) {
		    obj = new ResponsabiliTmAvv();
		    obj.setId(id);
		    obj.setFlagEsclude(Boolean.TRUE);
		    responsabiliTmAvvService.insert(obj);
		} else {
		    // Poiché il tipomovimento è già presente, invio il seguente messaggio per informare l'utente 
		    // che non può inserire lo stesso tipomovimento se è già limitato o escluso dalla ricerca.
		    result = "IS_PRESENT|Il tipomovimento [<b>" +
			    tm.getDescrizioneEstesa() +
			    "</b>] o limita la ricerca dei movimenti da \"DA VISIONARE\" e \"NON NOTIFICATI\" o &eacute; gi&agrave; escluso dalla ricerca.";
		    response.getOutputStream().write(result.getBytes());
		    log.debug("Valore di insertRespTipimov# tipo: avv_esclude, response: {}", response);
		}
	    }
	    if (tipo.equalsIgnoreCase("sca_esclude")) {
		ResponsabiliTmScaId id = new ResponsabiliTmScaId(codiceResponsabile, tipomovimento);
		ResponsabiliTmSca obj = responsabiliTmScaService.findById(id);
		if (obj == null) {
		    obj = new ResponsabiliTmSca();
		    obj.setId(id);
		    obj.setFlagEsclude(Boolean.TRUE);
		    responsabiliTmScaService.insert(obj);
		} else {
		    // Poiché il tipomovimento è già presente, invio il seguente messaggio per informare l'utente 
		    // che non può inserire lo stesso tipomovimento se è già limitato o escluso dalla ricerca.
		    result = "IS_PRESENT|Il tipomovimento [<b>" +
			    tm.getDescrizioneEstesa() +
			    "</b>] o limita la ricerca delle scadenze da \"EFFETTUARE\" o &eacute; gi&agrave; escluso dalla ricerca.";
		    response.getOutputStream().write(result.getBytes());
		    log.debug("insertRespTipimov# tipo: sca_esclude, response: {}", response);
		}
	    }
	}
    }

    @RequestMapping
    public void deleteRespTipimov(@RequestParam("tipomovimento") String tipomovimento, @RequestParam("tipo") String tipo,
	    @RequestParam("codiceResponsabile") Integer codiceResponsabile, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	if (codiceResponsabile == null) {
	    throw new RuntimeException("Il parametro codiceresponsabile non può essere nullo");
	}
	if (StringUtils.isBlank(tipomovimento)) {
	    throw new RuntimeException("Il parametro tipomovimento non può essere nullo");
	}
	Responsabili resp = responsabiliService.findById(new PkId(codiceResponsabile));
	if (resp == null) {
	    throw new RuntimeException("Non è stato trovato nessun operatore con codice " + String.valueOf(codiceResponsabile));
	}
	Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(tipomovimento));
	if (tm == null) {
	    throw new RuntimeException("Non è stato trovato nessun tipomovimento con codice " + tipomovimento);
	}
	if (StringUtils.isNotEmpty(tipo)) {
	    if (tipo.equalsIgnoreCase("avv") || tipo.equalsIgnoreCase("avv_esclude")) {
		ResponsabiliTmAvvId id = new ResponsabiliTmAvvId(codiceResponsabile, tipomovimento);
		ResponsabiliTmAvv obj = responsabiliTmAvvService.findById(id);
		if (obj != null) {
		    responsabiliTmAvvService.delete(obj);
		}
	    }
	    if (tipo.equalsIgnoreCase("sca") || tipo.equalsIgnoreCase("sca_esclude")) {
		ResponsabiliTmScaId id = new ResponsabiliTmScaId(codiceResponsabile, tipomovimento);
		ResponsabiliTmSca obj = responsabiliTmScaService.findById(id);
		if (obj != null) {
		    responsabiliTmScaService.delete(obj);
		}
	    }
	}
    }

    @RequestMapping
    public String listainventarioprocleggi(@RequestParam("codiceendo") Integer codiceEndo, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	// Lista delle normative configurate (leggi inserite nella tabella inventarioproc_leggi)
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codiceEndo));
	Set<InventarioprocLeggi> inventarioprocLeggis = inventarioprocedimenti.getInventarioprocLeggis();
	model.addAttribute("inventarioprocLeggis", inventarioprocLeggis);
	if (log.isDebugEnabled())
	    log.debug("call Listinvetarioprocendo with codiceEndo: " + codiceEndo);
	response.setContentType("text/plain");
	return "ajax/listinventarioprocleggi";
    }

    @RequestMapping
    public String ajaxDettaglioInvetarioprocLeggi(@RequestParam("codice") String codice, Model model, HttpServletResponse response)
	    throws IOException {

	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	InventarioprocLeggi entity = inventarioprocLeggiService.findById(new PkId(Integer.parseInt(codice)));
	inventarioprocedimenti.setInventarioprocLeggi(entity);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	if (log.isDebugEnabled())
	    log.debug("call dettaglio invetario procedimenti leggi with codice: " + codice);
	response.setContentType("text/plain");
	return "ajax/dettaglioInventarioprocleggi";
    }

    @RequestMapping
    public String dettaglioEndoprocedimento(@RequestParam("codice") Integer codice, @RequestParam("field") String field, Model model,
	    HttpServletResponse response) throws IOException {

	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(codice));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(entity);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	model.addAttribute("field", field);
	if (log.isDebugEnabled())
	    log.debug("call dettaglio endo procedimento with codice endoprocedimento: " + codice);
	response.setContentType("text/plain");
	return "ajax/dettaglioEndoprocedimento";
    }

    @RequestMapping
    public String infoLeggi(@RequestParam("codice") String codice, Model model, HttpServletResponse response) throws IOException {

	Leggi leggi = leggiService.findById(new PkId(Integer.parseInt(codice)));
	model.addAttribute("leggi", leggi);
	if (log.isDebugEnabled())
	    log.debug("call leggi with codice: " + codice);
	response.setContentType("text/plain");
	return "ajax/infoleggi";
    }

    @RequestMapping
    public void findCommedilizieTipologia(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	if (StringUtils.isNotBlank(textToSearch)) {
	    criterio.addFilterField(FilterUtils.like("descrizione", textToSearch));
	    criterio.addFilterField(FilterUtils.equals("flagDisabilita", Boolean.FALSE, Boolean.class));
	    ft.addRestriction(criterio);
	}
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	List<CommedilizieTipologie> list = commedilizieTipologieService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findCcValiditaCoefficienti(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceSoftware", required = false) String codiceSoftware, HttpServletResponse response) throws IOException {

	FilterTable ft = null;
	FilterRestriction criterio = new FilterRestriction();
	if (StringUtils.isNotBlank(codiceSoftware)) {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    criterio.addFilterField(FilterUtils.equals("codice", codiceSoftware, "ccValiditacoefficienti.software", String.class));
	} else {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	}
	if (StringUtils.isNotBlank(textToSearch)) {
	    criterio.addFilterField(FilterUtils.like("descrizione", textToSearch));
	    ft.addRestriction(criterio);
	}
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	List<CcValiditacoefficienti> list = ccValiditacoefficientiService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findSorteggiCategorie(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	if (StringUtils.isNotBlank(textToSearch)) {
	    FilterRestriction criterio = new FilterRestriction();
	    criterio.addFilterField(FilterUtils.like("descrizione", textToSearch));
	    ft.addRestriction(criterio);
	}
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	List<SorteggiCategorie> list = sorteggiCategorieService.findByFilterTable(ft);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findNometagpeople(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<String> list = mappatureService.findByNometagpeopleDistinct(textToSearch);
	renderHTMLResponse(response, list, "nometagpeople", "nometagpeople_id");
    }

    @RequestMapping
    public void findSoftwareByNometagpeople(@RequestParam("textToSearch") String textToSearch, @RequestParam("nometagpeople") String nometagpeople,
	    HttpServletResponse response) throws IOException {

	List<Software> list = mappatureService.findSoftwareByNometagpeople(textToSearch, nometagpeople);
	renderHTMLResponse(response, list, "codice", "descrizione");
    }

    @RequestMapping
    public void findSchedaByTagAndSoftware(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "nometagpeople", required = false) String nometagpeople, HttpServletResponse response) throws IOException {

	List<Dyn2Modellit> list = mappatureService.findSchedaByTagAndSoftware(textToSearch, nometagpeople, ORMHelper.getSoftware());
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findMercatiUsoAndMercato(@RequestParam("textToSearch") String textToSearch, @RequestParam("codiceMercato") Integer codiceMercato,
	    HttpServletResponse response) throws IOException {

	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	List<MercatiUso> list = mercatiUsoService.findDescrizioneAndMercato(textToSearch, mercati);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findPosteggioAndMercato(@RequestParam("textToSearch") String textToSearch, @RequestParam("codiceMercato") Integer codiceMercato,
	    HttpServletResponse response) throws IOException {

	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	List<MercatiD> list = mercatiDService.findByCodicePosteggioAndMercato(textToSearch, mercati);
	renderHTMLResponse(response, list, "id.codice", "codiceposteggio");
    }

    @RequestMapping
    public void findProtocolloTipidocumento(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<ProtocolloTipidocumento> list = protocolloTipidocumentoService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    /**
     * Recupera le istanze collegate all'istanza passata
     * 
     * @param codice
     * @param model
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String istanzeCollegate(@RequestParam("codice") String codice, @RequestParam(required = false, value = "contesto") String contesto,
	    Model model, HttpServletResponse response) throws IOException {

	Istanze istanza = istanzeService.findById(new PkId(Integer.parseInt(codice)));
	List<Istanzecollegate> listaIstanzecollegateFkIstanza = istanzecollegateService.findIstanzeCollegateByIstanza(istanza);
	model.addAttribute("istanzaDestinatario", istanza);
	model.addAttribute("listaIstanzecollegate", listaIstanzecollegateFkIstanza);
	model.addAttribute("contesto", contesto);
	if (log.isDebugEnabled())
	    log.debug("call lista istanze collegate with codice: " + codice);
	response.setContentType("text/plain");
	return "ajax/listaIstanzeCollegate";
    }

    /**
     * Crea il pannello per la scelta delle opzioni per l'export
     * 
     * @param model
     * @param contestoExport
     *            parametro che indica di tipo di contesto, se non passato di default prede il valore "ATT"
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String exportIAttivita(Model model, @RequestParam(required = false, value = "contestoExport") String contestoExport,
	    HttpServletResponse response) throws IOException {

	boolean iscontestoExportPresente = true;
	if (StringUtils.isBlank(contestoExport)) {
	    iscontestoExportPresente = false;
	    contestoExport = "ATT";
	}
	if (!Utilities.validaTestoAlfanumerico(contestoExport)) {
	    log.error("ERRORE SICUREZZA:exportConcessioni.htm Parametro: contestoExport, valore: " + contestoExport);
	    throw new SecurityException("Intercettato parametro di input non corretto");
	}
	CWSSigeproExpLocator service = new CWSSigeproExpLocator();
	response.setContentType("text/plain");
	try {
	    // Recuperare wsdl dalla configurazione
	    CWSSigeproExpSoap port = service.getCWSSigeproExpSoap(new URL(WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_EXPORT)));
	    // CHIAMO IL METODO LISTEXPCONTEXT DEL WEBSERVICE SIGEPROEXPORT
	    CEsportazione[] lista = port.listExpContext(ORMHelper.getToken(), contestoExport);
	    model.addAttribute("listaEsportazioni", lista);
	} catch (ServiceException e) {
	    log.error("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	    throw new RuntimeException("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	} catch (RemoteException e) {
	    log.error("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(remote error):" + e.getMessage());
	    throw new RuntimeException("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(remote error):" + e.getMessage());
	} catch (MalformedURLException e) {
	    log.error("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(url webservice error):" + e.getMessage());
	    throw new RuntimeException("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(url webservice error):" + e.getMessage());
	}
	// Se il contesto è presente si verifica quale contesto è stato scelto
	if (iscontestoExportPresente) {
	    if (contestoExport.equals("ATS")) {
		return "ajax/exportIAttivitaInData";
	    } else {
		log.error("ATTENZIONE: non è stato scelto nessun contetso di esportazione. Contattare l'assistenza");
		throw new RuntimeException("ATTENZIONE: non è stato scelto nessun contetso di esportazione. Contattare l'assistenza");
	    }
	} else// Se non è passoto di default si va alla jsp che gestisce il contesto di tipo "ATT"
	{
	    return "ajax/exportIAttivita";
	}
    }

    //    /**
    //     * Crea il pannello per la scelta delle opzioni per l'export per l'esportazine delle attività tramite il componente
    //     * esterno Pentaho
    //     * 
    //     * @param model
    //     * @param contestoExport
    //     *            parametro che indica di tipo di contesto, se non passato di default prede il valore "ATT"
    //     * @param response
    //     * @return
    //     * @throws IOException
    //     */
    //    @RequestMapping
    //    public String exportIAttivitaPentaho(Model model, @RequestParam(required = false, value = "contestoExport") String contestoExport,
    //	    @RequestParam(required = false, value = "codiceExpAndcomune") String codiceExpAndcomune,
    //	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result, SessionStatus status,
    //	    HttpServletRequest request, HttpServletResponse response) throws IOException {
    //
    //	boolean iscontestoExportPresente = true;
    //	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
    //	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
    //	Responsabili responsabile = responsabiliService.findById(idResponsabile);
    //	iattivitaCommand.setResponsabile(responsabile);
    //	if (StringUtils.isBlank(contestoExport)) {
    //	    iscontestoExportPresente = false;
    //	    contestoExport = "ATT";
    //	}
    //	response.setContentType("text/plain");
    //	List<Esportazioni> listaEsportazioni = new ArrayList<Esportazioni>();
    //	if (StringUtils.isBlank(codiceExpAndcomune)) {
    //	    listaEsportazioni = esportazioniService.findEsportazioni(TipicontestoesportazioniEnum.ATTIVITA);
    //	    iattivitaCommand.setEsportazioni(listaEsportazioni.get(0));
    //	} else {
    //	    String campi[] = StringUtils.split(codiceExpAndcomune, "@");
    //	    List<PkId> ids = new ArrayList<PkId>();
    //	    PkId id = new PkId(campi[1], Integer.parseInt(campi[0]));
    //	    ids.add(id);
    //	    listaEsportazioni = esportazioniService.findEsportazioniEscludiRecord(TipicontestoesportazioniEnum.ATTIVITA, ids);
    //	    Esportazioni esportazioni = esportazioniService.findById(id);
    //	    listaEsportazioni.add(0, esportazioni);
    //	    iattivitaCommand.setEsportazioni(esportazioni);
    //	}
    //	model.addAttribute("listaEsportazioni", listaEsportazioni);
    //	model.addAttribute("iattivitaCommand", iattivitaCommand);
    //	// Se il contesto è presente si verifica quale contesto è stato scelto
    //	if (iscontestoExportPresente) {
    //	    if (contestoExport.equals("ATS")) {
    //		return "ajax/exportIAttivitaInData";
    //	    } else {
    //		log.error("ATTENZIONE: non è stato scelto nessun contetso di esportazione. Contattare l'assistenza");
    //		throw new RuntimeException("ATTENZIONE: non è stato scelto nessun contetso di esportazione. Contattare l'assistenza");
    //	    }
    //	} else// Se non è passoto di default si va alla jsp che gestisce il contesto di tipo "ATT"
    //	{
    //	    return "ajax/exportIAttivitaPentaho";
    //	}
    //    }
    /**
     * Crea il pannello per la scelta delle opzioni per l'export
     * 
     * @param model
     * @param contestoExport
     *            parametro che indica di tipo di contesto, se non passato di default prede il valore "ATT"
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String exportConcessioni(Model model, @RequestParam(required = false, value = "contestoExport") String contestoExport,
	    HttpServletResponse response) throws IOException {

	// Metto sul model il responsabile loggato
	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	model.addAttribute("responsabile", responsabili);
	boolean iscontestoExportPresente = true;
	if (StringUtils.isBlank(contestoExport)) {
	    iscontestoExportPresente = false;
	    contestoExport = "CON";
	}
	if (!Utilities.validaTestoAlfanumerico(contestoExport)) {
	    log.error("ERRORE SICUREZZA:exportConcessioni.htm Parametro: contestoExport, valore: " + contestoExport);
	    throw new SecurityException("Intercettato parametro di input non corretto");
	}
	CWSSigeproExpLocator service = new CWSSigeproExpLocator();
	response.setContentType("text/plain");
	try {
	    // Recuperare wsdl dalla configurazione
	    CWSSigeproExpSoap port = service.getCWSSigeproExpSoap(new URL(WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_EXPORT)));
	    // CHIAMO IL METODO LISTEXPCONTEXT DEL WEBSERVICE SIGEPROEXPORT
	    CEsportazione[] lista = port.listExpContext(ORMHelper.getToken(), contestoExport);
	    model.addAttribute("listaEsportazioni", lista);
	} catch (ServiceException e) {
	    log.error("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	    throw new RuntimeException("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	} catch (RemoteException e) {
	    log.error("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(remote error):" + e.getMessage());
	    throw new RuntimeException("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(remote error):" + e.getMessage());
	} catch (MalformedURLException e) {
	    log.error("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(url webservice error):" + e.getMessage());
	    throw new RuntimeException("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(url webservice error):" + e.getMessage());
	}
	// Se il contesto è presente si verifica quale contesto è stato scelto
	if (iscontestoExportPresente) {
	    if (contestoExport.equals("CON")) {
		return "ajax/exportConcessioni";
	    } else {
		log.error("ATTENZIONE: non è stato scelto nessun contetso di esportazione. Contattare l'assistenza");
		throw new RuntimeException("ATTENZIONE: non è stato scelto nessun contetso di esportazione. Contattare l'assistenza");
	    }
	} else// Se non è passoto di default si va alla jsp che gestisce il contesto di tipo "ATT"
	{
	    return "ajax/exportConcessioni";
	}
    }

    @RequestMapping
    public void findRicercaDyncampi(@RequestParam("textToSearch") String textToSearch, @RequestParam("codiceCampo") Integer codiceCampo,
	    HttpServletResponse response) throws IOException {

	try {
	    List<ChiaveValoreBean<String, String>> list = dyn2CampiService.findValoriPerCampo(textToSearch, codiceCampo,
		    WebConstants.NUM_MAX_RESULTS);
	    renderHTMLResponse(response, list, "chiave", "valore");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public void findRicercaDyn2Campi(@RequestParam("term") String term, @RequestParam("codiceCampo") Integer codiceCampo,
	    HttpServletResponse response) throws IOException {

	log.debug("findRicercaDyn2Campi({})", term);
	List<ChiaveValoreBean<String, String>> list = dyn2CampiService.findValoriPerCampo(term, codiceCampo, WebConstants.NUM_MAX_RESULTS);
	StringBuffer buffer = new StringBuffer("[");
	if (list != null && !list.isEmpty()) {
	    for (ChiaveValoreBean<String, String> bean : list) {
		buffer.append("{\"id\":\"").append(bean.getChiave()).append("\",\"label\":\"").append(bean.getValore()).append("\",\"value\":\"")
			.append(bean.getValore()).append("\"},");
	    }
	    buffer.deleteCharAt(buffer.length() - 1);
	}
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void findRiCariche(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	try {
	    List<RiCariche> list = riCaricheService.findByDescrizione(textToSearch, null, null);
	    renderHTMLResponse(response, list, "codice", "descrizioneEstesa");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public void findRiFormegiuridiche(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	try {
	    List<RiFormegiuridiche> list = riFormegiuridicheService.findByDescrizione(textToSearch, null, null);
	    renderHTMLResponse(response, list, "codice", "descrizioneEstesa");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public void findRiTipiprocedimento(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	try {
	    List<RiTipiprocedimento> list = riTipiprocedimentoService.findByDescrizione(textToSearch, null, null);
	    renderHTMLResponse(response, list, "codice", "descrizioneEstesa");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public void findDocumentiContabilita(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	try {
	    List<DocumentiContabilita> list = documentiContabilitaService.findByDescrizione(textToSearch, null, null);
	    renderHTMLResponse(response, list, "id.codice", "nomedocumento");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public void findRiTipiintervento(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	try {
	    List<RiTipiintervento> list = riTipiinterventoService.findByDescrizione(textToSearch, null, null);
	    renderHTMLResponse(response, list, "codice", "descrizioneEstesa");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public void findBandi(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	try {
	    List<Bandi> list = bandiService.findByDescrizione(textToSearch);
	    renderHTMLResponse(response, list, "id.codice", "descrizione");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public void findGraduatoriet(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(required = false, value = "codicebando") Integer codicebando, HttpServletResponse response) throws IOException {

	try {
	    List<Graduatoriet> list = graduatorietService.findByAndBandoDescrizione(textToSearch, codicebando);
	    renderHTMLResponse(response, list, "id.codice", "descrizione");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public void findTipimodelli(@RequestParam("textToSearch") String textToSearch, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	List<Tipifamiglieendo> list = tipimodelliService.findByDescrizione(textToSearch, null, null);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public void findGruppiIstruttori(@RequestParam("textToSearch") String textToSearch, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	try {
	    List<GruppiIstruttori> list = gruppiIstruttoriService.findByDescrizione(textToSearch);
	    renderHTMLResponse(response, list, "id.codice", "descrizione");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public void findEmailByAmministrazioneDescrizione(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response)
	    throws IOException {

	try {
	    List<Amministrazioni> list = amministrazioniService.findAmministrazioniWithEmailByDescrizione(textToSearch);
	    List<Amministrazioni> risultato = new ArrayList<Amministrazioni>();
	    for (Amministrazioni amministrazioni : list) {
		Amministrazioni amministrazioniTemp = null;
		if (StringUtils.isNotBlank(amministrazioni.getPec())) {
		    amministrazioniTemp = new Amministrazioni();
		    amministrazioniTemp.setAmministrazione(amministrazioni.getAmministrazione());
		    amministrazioniTemp.setPec(amministrazioni.getPec());
		    amministrazioniTemp.setEmail(null);
		    amministrazioniTemp.setEmailOrPec(amministrazioni.getPec());
		    risultato.add(amministrazioniTemp);
		}
		if (StringUtils.isNotBlank(amministrazioni.getEmail())) {
		}
		amministrazioniTemp = new Amministrazioni();
		amministrazioniTemp.setAmministrazione(amministrazioni.getAmministrazione());
		amministrazioniTemp.setEmail(amministrazioni.getEmail());
		amministrazioniTemp.setPec(null);
		amministrazioniTemp.setEmailOrPec(amministrazioni.getEmail());
		risultato.add(amministrazioniTemp);
	    }
	    renderHTMLResponse(response, risultato, "emailOrPec", "descrizioneMail");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public void findEmailAmministrazione(@RequestParam("codiceAmministrazione") Integer codiceAmministrazione, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	Amministrazioni amm = amministrazioniService.findById(new PkId(codiceAmministrazione));
	if (amm == null) {
	    return;
	}
	response.setContentType("text/html");
	response.getWriter().write(StringUtils.defaultIfEmpty(amm.getPec(), amm.getEmail()));
    }

    @RequestMapping
    public void findEmailAnagrafe(@RequestParam("codiceAnagrafe") Integer codiceAnagrafe,
	    @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String email = istanzeService.findEmailSoggettoPratica(codiceAnagrafe, codiceIstanza, codiceMovimento);
	response.setContentType("text/html");
	response.getWriter().write(StringUtils.defaultIfEmpty(email, ""));
    }

    @RequestMapping
    public String ajaxDettaglioStoricoAnagrafe(@RequestParam("codice") String codice, Model model, HttpServletResponse response) throws IOException {

	Anagrafestorico anagrafestorico = anagrafeService.findAnagrafeStoricoById(new PkId(Integer.parseInt(codice)));
	if (anagrafestorico != null) {
	    Anagrafe anagrafeattuale = anagrafeService.findById(new PkId(anagrafestorico.getAnagrafe().getId().getCodice()));
	    model.addAttribute("anagrafeattuale", anagrafeattuale);
	}
	model.addAttribute("anagrafestorico", anagrafestorico);
	if (log.isDebugEnabled()) {
	    log.debug("call ajaxDettaglioStoricoAnagrafe() with codice: {}", codice);
	}
	response.setContentType("text/plain");
	return "ajax/dettaglioAnagrafeStorico";
    }

    private String toCheckedString(String valore) {

	String result = "";
	if (StringUtils.defaultIfEmpty(valore, "0").equalsIgnoreCase("1")) {
	    result = " checked ";
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public void getFlashMessages(HttpServletRequest request, HttpServletResponse response) throws IOException {

	StringBuffer sbuf = new StringBuffer("");
	List<String> warnings = (List<String>) request.getSession().getAttribute("___warnings");
	if (warnings != null && warnings.size() > 0) {
	    sbuf.append("<div id=\"warning_msg\" class=\"warning_header alert alert-warning\" >");
	    for (String warn : warnings) {
		sbuf.append("<div>").append(warn).append("</div>");
	    }
	    sbuf.append("</div>");
	}
	List<String> infos = (List<String>) request.getSession().getAttribute("___infos");
	if (infos != null && infos.size() > 0) {
	    sbuf.append("<div id=\"info_msg\" class=\"success_header alert alert-success\" >");
	    for (String info : infos) {
		sbuf.append("<div>").append(info).append("</div>");
	    }
	    sbuf.append("</div>");
	}
	request.getSession().removeAttribute("___infos");
	request.getSession().removeAttribute("___warnings");
	response.setContentType("text/plain");
	response.getWriter().write(sbuf.toString());
    }

    @RequestMapping
    public void findEventiNonLetti(@RequestParam(value = "codIstanza", required = false) Integer codIstanza,
	    @RequestParam(value = "codMov", required = false) Integer codMov, HttpServletResponse response) throws IOException {

	if (codIstanza == null && codMov == null) {
	    throw new RuntimeException("Per la ricerca degli eventi specificare un'istanza o un movimento.");
	}
	IstanzeeventiFilter filter = new IstanzeeventiFilter();
	Istanze istanza = new Istanze();
	istanza.getId().setCodice(codIstanza);
	Movimenti mov = new Movimenti();
	mov.getId().setCodice(codMov);
	filter.setIstanze(istanza);
	filter.setMovimenti(mov);
	filter.setFlagLetto(Boolean.FALSE);
	List<Istanzeeventi> list = null;
	if (codIstanza == null && codMov == null) {
	    list = istanzeeventiService.findByFilter(filter, 0, 50);
	} else {
	    list = istanzeeventiService.findByFilter(filter, 0, 150);
	}
	StringBuffer buffer = new StringBuffer();
	if (!list.isEmpty()) {
	    buffer.append("<ul style=\"list-style-type: none;\">");
	    for (Istanzeeventi ie : list) {
		buffer.append("<li id='").append(ie.getId().getCodice()).append("'>");
		int i = ie.getId().getCodice().intValue();
		buffer.append("<table><tr>");
		buffer.append("<td valign=\"top\"><input id=\"flagLettoId" +
			i +
			"\" type=\"checkbox\" onclick=\"changeCheckboxValue('flagLettoId" +
			i +
			"','../istanzeeventi/ajaxChangeFlagLetto.htm?codice=" +
			i +
			"')\" /></td>");
		buffer.append("<td>");
		if (!EntityUtils.isNestedPropertyBlank(ie.getCategorieeventibase(), "id")) {
		    buffer.append("<label title=\"").append(ie.getCategorieeventibase().getDescrizione()).append("\">(<b>")
			    .append(ie.getCategorieeventibase().getId()).append("</b>) </label>");
		}
		buffer.append(ie.getDescrizione());
		buffer.append("</td>");
		buffer.append("</tr></table>");
		buffer.append("</li>");
	    }
	    buffer.append("</ul>");
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public String dettaglioAltreSchede(@RequestParam("codiceCampo") Integer codiceCampo, @RequestParam("codiceScheda") Integer codiceScheda,
	    @RequestParam(value = "conMappatureConfigurate", required = false) Boolean conMappatureConfigurate, Model model,
	    HttpServletResponse response) throws IOException {

	PkId idCampo = new PkId(codiceCampo);
	Dyn2Campi campo = dyn2CampiService.findById(idCampo);
	PkId idScheda = new PkId(codiceScheda);
	Dyn2Modellit scheda = dyn2ModellitService.findById(idScheda);
	List<Dyn2Modellit> listaSchede = new LinkedList<Dyn2Modellit>();
	if (conMappatureConfigurate) {
	    // Lista schede che utilizzano quel campo e per le quali esistono mappature
	    for (Mappature mappatura : campo.getMappatures()) {
		if (!mappatura.getDyn2Modellit().equals(scheda)) {
		    if (!listaSchede.contains(mappatura.getDyn2Modellit())) {
			listaSchede.add(mappatura.getDyn2Modellit());
		    }
		}
	    }
	} else {
	    // Lista schede che utilizzano quel campo
	    for (Dyn2Modellid dyn2Modellid : campo.getDyn2Modellids()) {
		if (!dyn2Modellid.getDyn2Modellit().equals(scheda)) {
		    listaSchede.add(dyn2Modellid.getDyn2Modellit());
		}
	    }
	}
	Collections.sort(listaSchede, new Dyn2ModellitComparator());
	model.addAttribute("campo", campo);
	model.addAttribute("scheda", scheda);
	model.addAttribute("listaSchede", listaSchede);
	if (log.isDebugEnabled())
	    log.debug("call dettaglioAltreSchede with codiceCampo: " + codiceCampo);
	response.setContentType("text/plain");
	return "ajax/dettaglioAltreSchede";
    }

    @RequestMapping
    public String listaEventiNonLettiMovimento(@RequestParam("codiceMov") Integer codiceMov, Model model, HttpServletResponse response)
	    throws IOException {

	if (codiceMov != null) {
	    IstanzeeventiFilter filter = new IstanzeeventiFilter();
	    Istanze istanze = new Istanze();
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMov));
	    movimento.getId().setCodice(codiceMov);
	    filter.setIstanze(istanze);
	    filter.setMovimenti(movimento);
	    filter.setFlagLetto(false);
	    List<Istanzeeventi> istanzeeventiList = istanzeeventiService.findByFilter(filter, 0, 150);
	    model.addAttribute("istanzeeventiList", istanzeeventiList);
	    model.addAttribute("codIstanza", movimento.getIstanza().getId().getCodice());
	}
	if (log.isDebugEnabled())
	    log.debug("call lista eventi non letti per il movimento con codice: " + codiceMov);
	response.setContentType("text/plain");
	return "ajax/listaEventiMovimentoNonLetti";
    }

    @RequestMapping
    public String addDocumentiProtocollo(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("codiceMovimento") Integer codiceMovimento, Model model, HttpServletResponse response) throws IOException {

	DocumentiHelper documentiHelper = new DocumentiHelper();
	ProtocollazioneCommand protocolloCommand = new ProtocollazioneCommand();
	Movimenti movimento = null;
	Istanze istanza = null;
	if (codiceMovimento != null) {
	    log.debug("addDocumentiProtocollo# leggi protocollo da movimento");
	    movimento = movimentiService.findById(new PkId(codiceMovimento));
	    documentiHelper = documentiHelperService.findDocumentiInvioDocumentiProtocollo(codiceMovimento, codiceIstanza, true);
	} else {
	    log.debug("addDocumentiProtocollo# leggi protocollo da istanza");
	    istanza = istanzeService.findById(new PkId(codiceIstanza));
	    documentiHelper = documentiHelperService.findDocumentiInvioDocumentiProtocollo(null, codiceIstanza, false);
	}
	if (log.isDebugEnabled())
	    log.debug("call addDocumentiProtocollo");
	response.setContentType("text/plain");
	protocolloCommand.setDocumentiHelper(documentiHelper);
	protocolloCommand.setMovimento(movimento);
	protocolloCommand.setEntity(istanza);
	model.addAttribute("protocolloCommand", protocolloCommand);
	return "ajax/listaDocumentiDaInviare";
    }

    @RequestMapping
    public void findGruppiendoprocedimentiT(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<GruppiEndoprocedimentiT> list = gruppiEndoprocedimentiTService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @RequestMapping
    public String showDownloadEml(@RequestParam("codiceAllegato") Integer codiceAllegato, Model model, HttpServletResponse response)
	    throws IOException {

	Movimentiallegati movimentiallegati = movimentiallegatiService.findById(new PkId(codiceAllegato));
	boolean isDownloadEml = false;
	if (EntityUtils.getNestedProperty(movimentiallegati, "id.codice") != null
		&& EntityUtils.getNestedProperty(movimentiallegati.getOggetto(), "id.codice") == null
		&& StringUtils.isNotBlank(movimentiallegati.getMessageId())) {
	    isDownloadEml = true;
	}
	model.addAttribute("isDownloadEml", isDownloadEml);
	model.addAttribute("message_id", movimentiallegati.getMessageId());
	model.addAttribute("codAllegato", codiceAllegato);
	return "ajax/downloadEml";
    }

    @RequestMapping
    public void ajaxRenderCampo(@ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand,
	    @RequestParam(value = "codiceCampo") Integer codiceCampo, @RequestParam(value = "idx") Integer idx,
	    @RequestParam(value = "nomeElementoValore") String nomeElementoValore, @RequestParam(value = "valore") String valore,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	String campo = createCampoValore(codiceCampo, valore, valore, nomeElementoValore);
	// campo = campo.replaceAll("name=\"[A-Z_a-z_0-9]*\"", "name=\"" + nomeElementoValore + "\"");
	// campo = campo.replaceAll("id=\"[A-Z_a-z_0-9]*\"", "id=\"" + nomeElementoValore + "_id\"");
	byte[] out = campo.getBytes("UTF-8");
	response.getOutputStream().write(out);
    }

    /**
     * 
     * @param cfImpresa
     * @param tipoOutput
     *            default HTML da implementare PDF con fileconverter
     * @param contentDisposition
     *            default inline altrimenti response.setHeader("Content-Disposition", "attachment; filename="nomefile");
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping
    public void visuraImpresa(@RequestParam(value = "cfImpresa") String cfImpresa,
	    @RequestParam(value = "tipoOutput", required = false) String tipoOutput,
	    @RequestParam(value = "contentDisposition", required = false) String contentDisposition, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	tipoOutput = StringUtils.defaultIfEmpty(tipoOutput, "html");
	contentDisposition = StringUtils.defaultIfEmpty(tipoOutput, "inline");
	String visuraParixHTML = anagrafeService.visuraParixHTML(cfImpresa);
	String filename = "";
	byte[] contentBinary = null;
	String cType = "";
	if (tipoOutput.equalsIgnoreCase("html")) {
	    filename = "visura.htm";
	    contentBinary = visuraParixHTML.getBytes("UTF-8");
	    cType = "text/html";
	} else {
	    // non implementato
	    filename = "visura.pdf";
	    cType = "text/html";
	    contentDisposition += "; filename=\"" + filename + "\"";
	}
	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
	response.setHeader("Content-Disposition", contentDisposition);
	response.setHeader("Content-transfer-encoding", "binary");
	response.setContentType(cType);
	response.setContentLength(contentBinary.length);
	ServletOutputStream cout = response.getOutputStream();
	cout.write(contentBinary);
	cout.flush();
    }

    @RequestMapping
    public void ajaxBollettazioneVerificaElaborazioni(@RequestParam("idBollettazioneTestata") Integer[] idBollettazioneTestata, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	Set<Integer> elaborazioni = ProcBollElaborazioneInvioNodo.getElaborazioniInCorso(idBollettazioneTestata);
	String json = "{\"elaborazioni_in_corso\": [";
	for (Integer e : elaborazioni) {
	    json += e + ",";
	}
	if (json.indexOf(",") > 0) {
	    json = json.substring(0, json.length() - 1);
	}
	json += "]}";
	response.setContentType("application/json");
	response.getOutputStream().write(json.getBytes());
    }

    @RequestMapping
    public void ajaxRimuoviBollettazioneVerificaElaborazioni(@RequestParam("idBollettazioneTestata") Integer idBollettazioneTestata, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	ProcBollElaborazioneInvioNodo.rimuovi(idBollettazioneTestata);
    }

    @RequestMapping
    public void ajaxBollettazioneVerificaElaborazioneNodoPagamenti(@RequestParam("idBollettazioneTestata") Integer idBollettazioneTestata,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	ProcBollElaborazioneInvioNodoBean statoElaborazione = ProcBollElaborazioneInvioNodo.getStatoElaborazione(idBollettazioneTestata);
	boolean incorso = false;
	int totale = 0;
	int elaborati = 0;
	if (statoElaborazione != null) {
	    incorso = true;
	    totale = statoElaborazione.getTotaleRecord();
	    elaborati = statoElaborazione.getTotaleRecordElaborati();
	}
	String json = "{\"elaborazione_in_corso\": " + incorso + ",\"totale\": " + totale + ",\"elaborati\": " + elaborati + "}";
	response.setContentType("application/json");
	response.getOutputStream().write(json.getBytes());
    }

    @RequestMapping
    public void checkProtocolloAttivo(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	response.setContentType("text/plain");
	response.getWriter().write(chekProtocolloIsAttivo().toString());
    }

    private StringBuffer chekProtocolloIsAttivo() {

	Verticalizzazioniparametri tipoProtocollo = verticalizzazioniService.getVerticalizzazioniparametri(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOPROTOCOLLO);
	StringBuffer sbuf = new StringBuffer("");
	if (tipoProtocollo != null && tipoProtocollo.getValore() != null) {
	    log.debug("ajaxCheckProtocolloAttivo# Protocollo configuarto");
	} else {
	    String warn = "Attenzione non sarà possibile effettuare la protocollazione automatica dei movimenti, in quanto non è configutato nessun tipo di protocollazione";
	    log.debug("ajaxCheckProtocolloAttivo# Protocollo non configuarto, non è possibile getsire la protocllazione automatica dei movimenti");
	    sbuf.append("<div id=\"warning_msg\" class=\"warning_header\" ><div>&nbsp;</div>");
	    sbuf.append("<div>").append(warn).append("</div>");
	}
	return sbuf;
    }

    private String createCampoValore(Integer codiceCampo, String valore, String valoreDecodificato, String nomeElementoValore) {

	StringBuffer buffer = new StringBuffer();
	// faccio il render del campo dinamico
	Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(codiceCampo));
	ModellidinamiciCampoHelper campo = new ModellidinamiciCampoHelper();
	campo.setDyn2Campi(dyn2Campi);
	campo.setValore(valore);
	campo.setValoreDecodificato(valoreDecodificato);
	String campoHtml = this.renderCampo(TipoControlloEnum.valueOf(dyn2Campi.getTipodato()), dyn2Campi, campo, nomeElementoValore);
	buffer.append(campoHtml);
	return buffer.toString();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String renderCampo(TipoControlloEnum tipodato, Dyn2Campi d2c, ModellidinamiciCampoHelper campo, String nomeElementoValore) {

	Set<Dyn2Campiproprieta> proprietaCampo = d2c.getDyn2Campiproprietas();
	switch (tipodato) {
	    case Lista:
	    case MultiLista:
		return renderLista(d2c, proprietaCampo, false, campo, nomeElementoValore);
	    case Checkbox:
		return renderCheckBox(d2c, proprietaCampo, campo, nomeElementoValore);
	    case Data:
		return renderData(d2c, proprietaCampo, campo, nomeElementoValore);
	    case NumericoDouble:
		return renderNumerico(d2c, proprietaCampo, true, campo, nomeElementoValore);
	    case NumericoIntero:
		return renderNumerico(d2c, proprietaCampo, false, campo, nomeElementoValore);
	    default:
		return renderTesto(d2c, proprietaCampo, campo, nomeElementoValore);
	}
    }

    private String renderLista(Dyn2Campi d2c, Set<Dyn2Campiproprieta> proprietaCampo, boolean isMultiSelect, ModellidinamiciCampoHelper campo,
	    String nomeElementoValore) {

	String valoriLista = getProprietaCampo(proprietaCampo, ProprietaCampi.ElementiLista, "");
	// String obbligatorio = getProprietaCampo(proprietaCampo, ProprietaCampi.Obbligatorio, "");
	String[] valori = null;
	if (StringUtils.isNotBlank(valoriLista)) {
	    valori = valoriLista.split(";");
	}
	CustomHtmlBuilder result = new CustomHtmlBuilder();
	String elementId = getNameOrIdFromCampo(nomeElementoValore, true);
	String elementName = getNameOrIdFromCampo(nomeElementoValore, false);
	result.select().name(elementName).id(elementId).append(validationFX(elementId, false));
	List<String> valoriSelezionati = new ArrayList<String>();
	String valoreSelezionato = StringUtils.defaultIfEmpty(campo.getValore(), "");
	if (isMultiSelect) {
	    result.append(" multiple=\"multiple\"");
	    String[] valoriSelezionatiAr = null;
	    if (StringUtils.isNotBlank(valoriLista)) {
		valoriSelezionatiAr = valoreSelezionato.split(";");
	    }
	    if (valoriSelezionatiAr != null && valoriSelezionatiAr.length > 0) {
		for (String val : valoriSelezionatiAr) {
		    if (StringUtils.isNotBlank(val)) {
			valoriSelezionati.add(val);
		    }
		}
	    }
	    if (valori != null) {
		result.size(String.valueOf(valori.length));
	    }
	} else {
	    valoriSelezionati.add(valoreSelezionato);
	}
	String errMessage = "";//validaLista(d2c, proprietaCampo, isMultiSelect, campo.getValore());
	result.close();
	if (!isMultiSelect) {
	    result.option().value("").close().append("").optionEnd();
	}
	if (valori != null) {
	    for (String val : valori) {
		if (StringUtils.isNotBlank(val)) {
		    int sepIdx = val.indexOf('$');
		    if (sepIdx > -1) {
			val = val.substring(sepIdx + 1, val.length());
		    }
		    result.newline();
		    result.option().value(val);
		    if (valoriSelezionati != null) {
			if (valoriSelezionati.contains(StringUtils.defaultIfEmpty(val, "").trim())) {
			    result.selected();
			}
		    }
		    result.close().append(val).optionEnd();
		}
	    }
	}
	result.selectEnd().append(spanErrors(elementId, elementName, errMessage));
	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    StringBuffer resultBuffer = new StringBuffer();
	    for (String val : valoriSelezionati) {
		resultBuffer.append(val);
		resultBuffer.append(", ");
	    }
	    return getPrintValue(resultBuffer.toString());
	} else {
	    return result.toString();
	}
    }

    private String renderTesto(Dyn2Campi d2c, Set<Dyn2Campiproprieta> proprietaCampo, ModellidinamiciCampoHelper campo, String nomeElementoValore) {

	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    return getPrintValue(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
	} else {
	    CustomHtmlBuilder result = new CustomHtmlBuilder();
	    String maxLength = getProprietaCampo(proprietaCampo, ProprietaCampi.MaxLength, "");
	    String readonly = getProprietaCampo(proprietaCampo, ProprietaCampi.ReadOnly, "");
	    String size = getProprietaCampo(proprietaCampo, ProprietaCampi.Columns, "40");
	    String textarea = getProprietaCampo(proprietaCampo, ProprietaCampi.MultiLine, "");
	    String rows = getProprietaCampo(proprietaCampo, ProprietaCampi.Rows, "");
	    if (textarea.equalsIgnoreCase("true")) {
		result.textarea();
		if (StringUtils.isNotBlank(size)) {
		    result.cols(size);
		}
		if (StringUtils.isNotBlank(rows)) {
		    result.rows(rows);
		}
	    } else {
		result.input().type("text");
		if (StringUtils.isNotBlank(size)) {
		    result.size(size);
		}
	    }
	    String elementId = getNameOrIdFromCampo(nomeElementoValore, true);
	    String elementName = getNameOrIdFromCampo(nomeElementoValore, false);
	    result.id(elementId).name(elementName).append(validationFX(elementId, false));
	    if (StringUtils.isNotBlank(maxLength)) {
		result.maxlength(maxLength);
	    }
	    if (StringUtils.defaultIfEmpty(readonly, "false").equalsIgnoreCase("true")) {
		result.readonly();
	    }
	    if (textarea.equalsIgnoreCase("true")) {
		result.close();
		result.append(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
		result.textareaEnd();
	    } else {
		result.value(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
		result.end();
	    }
	    String errMessage = "";//validaTesto(d2c, proprietaCampo, campo.getValoreDecodificato());
	    result.append(spanErrors(elementId, elementName, errMessage));
	    return result.toString();
	}
    }

    private String renderNumerico(Dyn2Campi d2c, Set<Dyn2Campiproprieta> proprietaCampo, boolean isDouble, ModellidinamiciCampoHelper campo,
	    String nomeElementoValore) {

	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    return getPrintValue(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
	} else {
	    CustomHtmlBuilder result = new CustomHtmlBuilder();
	    String maxLength = getProprietaCampo(proprietaCampo, ProprietaCampi.MaxLength, "");
	    String readonly = getProprietaCampo(proprietaCampo, ProprietaCampi.ReadOnly, "");
	    String size = getProprietaCampo(proprietaCampo, ProprietaCampi.Columns, "10");
	    String elementId = getNameOrIdFromCampo(nomeElementoValore, true);
	    String elementName = getNameOrIdFromCampo(nomeElementoValore, false);
	    result.input().style("text-align: right;").type("text").id(elementId).name(elementName).append(validationFX(elementId, false));
	    if (StringUtils.isNotBlank(size)) {
		result.size(size);
	    }
	    if (StringUtils.isNotBlank(maxLength)) {
		result.maxlength(maxLength);
	    }
	    if (StringUtils.defaultIfEmpty(readonly, "false").equalsIgnoreCase("true")) {
		result.readonly();
	    }
	    result.value(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
	    String errMessg = "";//validaNumerico(d2c, proprietaCampo, isDouble, campo.getValoreDecodificato());
	    result.end().append(spanErrors(elementId, elementName, errMessg));
	    return result.toString();
	}
    }

    protected String renderData(Dyn2Campi d2c, Set<Dyn2Campiproprieta> proprietaCampo, ModellidinamiciCampoHelper campo, String nomeElementoValore) {

	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    String result = getPrintValue(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
	    return result;
	} else {
	    CustomHtmlBuilder result = new CustomHtmlBuilder();
	    String elementId = getNameOrIdFromCampo(nomeElementoValore, true);
	    String elementName = getNameOrIdFromCampo(nomeElementoValore, false);
	    String readonly = getProprietaCampo(proprietaCampo, ProprietaCampi.ReadOnly, "");
	    result.input().type("text").id(elementId).onblur("isValidDate(this,true);").name(elementName).size("10");
	    if (readonly.equalsIgnoreCase("true")) {
		result.readonly();
	    }
	    result.value(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), "")).end();
	    // String calName = "CAL_" + elementId;
	    // result.a().append(" href=\"\" ").id(calName).title("Calendario").close();
	    // result.img().alt("Calendario").src("../images/cal.gif").close().aEnd();
	    String errMessg = "";// validaData(d2c, proprietaCampo, campo.getValoreDecodificato());
	    //result.script().type("text/javascript").close().append(calendarString(elementId, calName)).scriptEnd()
	    result.append(spanErrors(elementId, elementName, errMessg));
	    return result.toString();
	}
    }

    private String renderCheckBox(Dyn2Campi d2c, Set<Dyn2Campiproprieta> proprietaCampo, ModellidinamiciCampoHelper campo,
	    String nomeElementoValore) {

	String valoreTrue = getProprietaCampo(proprietaCampo, ProprietaCampi.ValoreTrue, "1");
	String valoreFalse = getProprietaCampo(proprietaCampo, ProprietaCampi.ValoreFalse, "0");
	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    String result = getPrintValue("[ ]");
	    if (campo != null) {
		if (StringUtils.defaultIfEmpty(campo.getValore(), "").equalsIgnoreCase(valoreTrue)) {
		    result = getPrintValue("[x]");
		}
	    }
	    return result;
	} else {
	    CustomHtmlBuilder result = new CustomHtmlBuilder();
	    String elementId = getNameOrIdFromCampo(nomeElementoValore, true);
	    String elementName = getNameOrIdFromCampo(nomeElementoValore, false);
	    String errMessage = "";
	    result.input().type("checkbox").value(valoreTrue).name("TMP_" + elementName).id("TMP_" + elementId);
	    String valoreDefault = valoreFalse;
	    if (campo != null) {
		if (StringUtils.defaultIfEmpty(campo.getValore(), "").equalsIgnoreCase(valoreTrue)) {
		    result.checked();
		    valoreDefault = valoreTrue;
		}
		errMessage = validaCheckBox(d2c, proprietaCampo, campo.getValore());
	    }
	    result.onclick(
		    "if(this.checked){$('" + elementId + "').value='" + valoreTrue + "';}else{$('" + elementId + "').value='" + valoreFalse + "';}")
		    .end().append(spanErrors(elementId, elementName, errMessage));
	    result.input().type("hidden").name(elementName).id(elementId).value(valoreDefault).end();
	    return result.toString();
	}
    }

    private String validationFX(String elementId, boolean isCheckbox) {

	return "";//isCheckbox ? " onclick=\"validaCampo(this);\" " : " onchange=\"validaCampo(this);\" ";
    }

    protected String calendarString(String id, String name) {

	return "jQuery(document).ready(function(){Calendar.setup({inputField     :    \"" + id + "\",    button         :    \"" + name + "\" });});";
    }

    protected String getProprietaCampo(Set<Dyn2Campiproprieta> proprietaCampo, ProprietaCampi proprieta, String defaultValue) {

	String result = StringUtils.defaultIfEmpty(defaultValue, "");
	for (Dyn2Campiproprieta dyn2Campiproprieta : proprietaCampo) {
	    if (dyn2Campiproprieta.getId().getProprieta().equalsIgnoreCase(proprieta.name())) {
		result = dyn2Campiproprieta.getValore();
	    }
	}
	return result;
    }

    protected String getPrintValue(String value) {

	return "<b>" + value + "</b>";
    }

    private String validaCheckBox(Dyn2Campi d2c, Set<Dyn2Campiproprieta> proprietaCampo, String valore) {

	String obbligatorio = getProprietaCampo(proprietaCampo, ProprietaCampi.Obbligatorio, "false");
	String errMessg = "";
	if (StringUtils.isNotBlank(obbligatorio)) {
	    if (obbligatorio.equalsIgnoreCase("true")) {
		if (StringUtils.defaultIfEmpty(valore, "0").equalsIgnoreCase("0")) {
		    errMessg = "È obbligatorio spuntare la checkbox." /*TODO METTERE LABEL*/;
		}
	    }
	}
	return errMessg;
    }

    public String getNameOrIdFromCampo(String nomeCampo, boolean isId) {

	String result = nomeCampo;
	if (isId) {
	    result = result.replaceAll("\\.", "_").replaceAll("\\[", "").replaceAll("\\]", "") + "_id";
	} else {
	    return nomeCampo;
	}
	return result;
    }

    protected Object spanErrors(String elementId, String elementName, String errMessage) {

	String display = "display: none;";
	if (StringUtils.isNotBlank(errMessage)) {
	    display = "";
	}
	return "<span id=\"" +
		elementId +
		"_ERRORS\" style=\"clear: left;" +
		display +
		"\" class=\"error\">" +
		StringUtils.defaultIfEmpty(errMessage, "") +
		"</span>";
    }

    @Override
    protected void fixMergeEntityProperty(Comuni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Comuni entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
