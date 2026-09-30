package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ScadenzecategoriebaseEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazionireferenti;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Aree2;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.domain.Clmenu;
import it.gruppoinit.pal.gp.core.domain.Clpermmenu;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.Concessionitipi;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.Elenchiprofessionalibase;
import it.gruppoinit.pal.gp.core.domain.Elencocassaedilebase;
import it.gruppoinit.pal.gp.core.domain.Elencoinailbase;
import it.gruppoinit.pal.gp.core.domain.Elencoinpsbase;
import it.gruppoinit.pal.gp.core.domain.Faqclassi;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsParams;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.InteressiLegali;
import it.gruppoinit.pal.gp.core.domain.InventarioprocLeggi;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Lavoricategorie;
import it.gruppoinit.pal.gp.core.domain.Lavoritipi;
import it.gruppoinit.pal.gp.core.domain.Layouttesti;
import it.gruppoinit.pal.gp.core.domain.LayouttestiId;
import it.gruppoinit.pal.gp.core.domain.Leggi;
import it.gruppoinit.pal.gp.core.domain.Leggitipi;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Mappature;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivita;
import it.gruppoinit.pal.gp.core.domain.MercatiConti;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Messaggi;
import it.gruppoinit.pal.gp.core.domain.Messaggicfgbase;
import it.gruppoinit.pal.gp.core.domain.Naturaendobase;
import it.gruppoinit.pal.gp.core.domain.Normative;
import it.gruppoinit.pal.gp.core.domain.Oggettiinfo;
import it.gruppoinit.pal.gp.core.domain.Onericomportamento;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggitipospazio;
import it.gruppoinit.pal.gp.core.domain.ProtocolloTipidocumento;
import it.gruppoinit.pal.gp.core.domain.QrxmlBase;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.RiCariche;
import it.gruppoinit.pal.gp.core.domain.RiFormegiuridiche;
import it.gruppoinit.pal.gp.core.domain.RiTipiintervento;
import it.gruppoinit.pal.gp.core.domain.RiTipiprocedimento;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.Scadenze;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Stradariozone;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;
import it.gruppoinit.pal.gp.core.domain.Tipiapertura;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;
import it.gruppoinit.pal.gp.core.domain.Tipiaree;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipidocumento;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.Tipiorario;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Tipiunitamisura;
import it.gruppoinit.pal.gp.core.domain.Tipologiaistanza;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.Titoli;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.VwAlberoproc;
import it.gruppoinit.pal.gp.core.domain.VwEntilocali;
import it.gruppoinit.pal.gp.core.domain.VwProvince;
import it.gruppoinit.pal.gp.core.domain.helper.ClpermmenuResponsabiliComparator;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.Dyn2ModellitComparator;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.InventarioprocedimentiCommand;
import it.gruppoinit.pal.gp.core.domain.web.ResponsabiliCommand;
import it.gruppoinit.pal.gp.core.exception.OperatoreNonHaComuniConfiguratiException;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterOrder;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazionireferentiService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.Aree2Service;
import it.gruppoinit.pal.gp.core.service.AreeService;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.CittadinanzaService;
import it.gruppoinit.pal.gp.core.service.ClmenuService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ConcessionicausaliService;
import it.gruppoinit.pal.gp.core.service.ConcessionitipiService;
import it.gruppoinit.pal.gp.core.service.ConcessioniusoService;
import it.gruppoinit.pal.gp.core.service.ContiService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService;
import it.gruppoinit.pal.gp.core.service.ElenchiprofessionalibaseService;
import it.gruppoinit.pal.gp.core.service.ElencocassaedilebaseService;
import it.gruppoinit.pal.gp.core.service.ElencoinailbaseService;
import it.gruppoinit.pal.gp.core.service.ElencoinpsbaseService;
import it.gruppoinit.pal.gp.core.service.FaqclassiService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsParamsService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsTestataService;
import it.gruppoinit.pal.gp.core.service.FormegiuridicheService;
import it.gruppoinit.pal.gp.core.service.InteressiLegaliService;
import it.gruppoinit.pal.gp.core.service.InventarioprocLeggiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.LavoricategorieService;
import it.gruppoinit.pal.gp.core.service.LavoritipiService;
import it.gruppoinit.pal.gp.core.service.LayouttestiService;
import it.gruppoinit.pal.gp.core.service.LeggiService;
import it.gruppoinit.pal.gp.core.service.LeggitipiService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.MailtipoService;
import it.gruppoinit.pal.gp.core.service.MappatureService;
import it.gruppoinit.pal.gp.core.service.MercatiCfgAttivitaService;
import it.gruppoinit.pal.gp.core.service.MercatiDService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.MessaggiService;
import it.gruppoinit.pal.gp.core.service.MessaggicfgbaseService;
import it.gruppoinit.pal.gp.core.service.NaturaendobaseService;
import it.gruppoinit.pal.gp.core.service.NormativeService;
import it.gruppoinit.pal.gp.core.service.OggettiinfoService;
import it.gruppoinit.pal.gp.core.service.OnericomportamentoService;
import it.gruppoinit.pal.gp.core.service.OneritipirateizzazioneService;
import it.gruppoinit.pal.gp.core.service.PosteggitipospazioService;
import it.gruppoinit.pal.gp.core.service.ProtocolloTipidocumentoService;
import it.gruppoinit.pal.gp.core.service.QrxmlBaseService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;
import it.gruppoinit.pal.gp.core.service.RiCaricheService;
import it.gruppoinit.pal.gp.core.service.RiFormegiuridicheService;
import it.gruppoinit.pal.gp.core.service.RiTipiinterventoService;
import it.gruppoinit.pal.gp.core.service.RiTipiprocedimentoService;
import it.gruppoinit.pal.gp.core.service.RuoliService;
import it.gruppoinit.pal.gp.core.service.ScadenzeService;
import it.gruppoinit.pal.gp.core.service.SettoriService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.StradariozoneService;
import it.gruppoinit.pal.gp.core.service.TempificazioniService;
import it.gruppoinit.pal.gp.core.service.TipiaperturaService;
import it.gruppoinit.pal.gp.core.service.TipiarchivioistanzeService;
import it.gruppoinit.pal.gp.core.service.TipiareeService;
import it.gruppoinit.pal.gp.core.service.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.service.TipidocumentoService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;
import it.gruppoinit.pal.gp.core.service.TipiorarioService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;
import it.gruppoinit.pal.gp.core.service.TipiunitamisuraService;
import it.gruppoinit.pal.gp.core.service.TipologiaistanzaService;
import it.gruppoinit.pal.gp.core.service.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.service.TitoliService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.VwAlberoprocService;
import it.gruppoinit.pal.gp.core.service.VwEntilocaliService;
import it.gruppoinit.pal.gp.core.service.VwProvinceService;
import it.gruppoinit.pal.gp.core.service.helper.TipologiaregistriConfigurazioneEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.CEsportazione;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.CWSSigeproExpLocator;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.CWSSigeproExpSoap;

@Controller
@SessionAttributes("istanzeprocedimentiCommand")
public class AjaxController extends BaseController<Comuni> {

    @Autowired
    private Aree2Service aree2Service;
    @Autowired
    private LeggitipiService leggitipiService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private TitoliService titoliService;
    @Autowired
    private TipifamiglieendoService tipifamiglieendoService;
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
    private AnagrafeService anagrafeService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private ResponsabiliService responsabiliService;
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
    private LayouttestiService layouttestiService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private RuoliService ruoliService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
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
    private NaturaendobaseService naturaendobaseService;
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
    private LeggiService leggiService;
    @Autowired
    private TipiorarioService tipiorarioService;
    @Autowired
    private LavoricategorieService lavoricategorieService;
    @Autowired
    private MappatureService mappatureService;
    @Autowired
    private IstanzeeventiService istanzeeventiService;
    @Autowired
    private ResponsabilisoftwareService responsabilisoftwareService;
    @Autowired
    private ProtocolloTipidocumentoService protocolloTipidocumentoService;
    @Autowired
    private RiCaricheService riCaricheService;
    @Autowired
    private RiFormegiuridicheService riFormegiuridicheService;
    @Autowired
    private RiTipiinterventoService riTipiinterventoService;
    @Autowired
    private RiTipiprocedimentoService riTipiprocedimentoService;
    @Autowired
    private VwEntilocaliService vwEntilocaliService;
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
    private QrxmlBaseService qrxmlBaseService;
    @Autowired
    private FoArjStepsService foArjStepsService;
    @Autowired
    private FoArjStepsParamsService foArjStepsParamsService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService2;

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

	List<Comuni> list = comuniService.findByDescrizione(textToSearch, WebConstants.NUM_MAX_RESULTS);
	renderHTMLResponse(response, list, "codicecomune", "descrizioneEstesa");
    }

    @RequestMapping
    public void findComuniItaliani(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Comuni> list = comuniService.findComuniItalianiByDescrizione(textToSearch, WebConstants.NUM_MAX_RESULTS);
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
	List<Tipifamiglieendo> list = tipifamiglieendoService.findByFilter(entity, null);
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesa");
    }

    /**
     * Il metodo ritorna la lista delle tipo famiglie filtrando per il softeare corrente, la descrizione e per idcomune.
     * idcomune se nullo viene passato quello della sull'ORMHelper, se invece è diverso da null allora viene usato
     * quello passato in request.
     * 
     * @param textToSearch
     * @param codiceidcomunebase
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void findTipifamiglieendoAndIdcomune(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "isIdcomunebase", required = false) String isIdcomunebase,
	    @RequestParam(value = "cercaSoftwareTT", required = false) Boolean cercaSoftwareTT, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	if (cercaSoftwareTT == null) {
	    cercaSoftwareTT = Boolean.FALSE;
	}
	String[] software = new String[2];
	software[0] = ORMHelper.getSoftware();
	software[1] = ORMHelper.getSoftware();
	if (cercaSoftwareTT.booleanValue()) {
	    software[1] = WebConstants.SOFTWARE_TT;
	}
	List<Tipifamiglieendo> list = new ArrayList<Tipifamiglieendo>();
	if (StringUtils.isNotBlank(isIdcomunebase) && isIdcomunebase.equalsIgnoreCase("1")) {
	    list = tipifamiglieendoService.findByDescAndSW(textToSearch, software, ORMHelper.getIdcomunebase());
	} else {
	    list = tipifamiglieendoService.findByDescAndSW(textToSearch, software, ORMHelper.getIdcomune());
	}
	renderHTMLResponse(response, list, "id.codice", "tipo");
    }

    @RequestMapping
    public void findTipiendoAndIdcomune(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceFamiglia", required = false) Integer codiceFamiglia,
	    @RequestParam(value = "isIdcomunebase", required = false) String isIdcomunebase,
	    @RequestParam(value = "cercaSoftwareTT", required = false) Boolean cercaSoftwareTT, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	if (cercaSoftwareTT == null) {
	    cercaSoftwareTT = Boolean.FALSE;
	}
	String[] software = new String[2];
	software[0] = ORMHelper.getSoftware();
	software[1] = ORMHelper.getSoftware();
	if (cercaSoftwareTT.booleanValue()) {
	    software[1] = WebConstants.SOFTWARE_TT;
	}
	List<Tipiendo> list = new ArrayList<Tipiendo>();
	if (StringUtils.isNotBlank(isIdcomunebase) && isIdcomunebase.equalsIgnoreCase("1")) {
	    list = tipiendoService.findByDescAndSW(textToSearch, codiceFamiglia, software, ORMHelper.getIdcomunebase());
	} else {
	    list = tipiendoService.findByDescAndSW(textToSearch, codiceFamiglia, software, null);
	}
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesa");
    }

    @RequestMapping
    public void findInventarioprocByFamigliaAndCategoriaEndoAndIdComune(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceFamiglia", required = false) Integer codiceFamiglia,
	    @RequestParam(value = "codiceTipologia", required = false) Integer codiceTipologia,
	    @RequestParam(value = "isIdcomunebase", required = false) String isIdcomunebase,
	    @RequestParam(value = "tipoEndo", required = false) String tipoEndo,
	    @RequestParam(value = "cercaSoftwareTT", required = false) Boolean cercaSoftwareTT, HttpServletResponse response) throws IOException {

	if (cercaSoftwareTT == null) {
	    cercaSoftwareTT = Boolean.TRUE;
	}
	String[] software = new String[2];
	software[0] = ORMHelper.getSoftware();
	software[1] = ORMHelper.getSoftware();
	if (cercaSoftwareTT.booleanValue()) {
	    software[1] = WebConstants.SOFTWARE_TT;
	}
	List<Inventarioprocedimenti> list = new ArrayList<Inventarioprocedimenti>();
	if (StringUtils.isNotBlank(isIdcomunebase) && isIdcomunebase.equalsIgnoreCase("1")) {
	    list = inventarioprocedimentiService.findByDescrizioneFamigliaendoETipologiaAndSoftware(textToSearch, codiceFamiglia, codiceTipologia,
		    software, ORMHelper.getIdcomunebase(), tipoEndo);
	} else {
	    list = inventarioprocedimentiService.findByDescrizioneFamigliaendoETipologiaAndSoftware(textToSearch, codiceFamiglia, codiceTipologia,
		    software, null, tipoEndo);
	}
	StringBuffer sbuf = new StringBuffer("<ul>");
	for (Inventarioprocedimenti tipicausalioneri : list) {
	    sbuf.append("<li id=\"").append(tipicausalioneri.getId().getCodice()).append("#").append(tipicausalioneri.getId().getIdcomune())
		    .append("\">").append(tipicausalioneri.getTransientDescrizioneWithComune()).append("</li>");
	}
	sbuf.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(sbuf.toString());
	// renderHTMLResponse(response, list, "id.codice", "procedimento");
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

	String[] software = new String[1];
	software[0] = codicesoftware;
	List<Tipiendo> list = new ArrayList<Tipiendo>();
	if (StringUtils.isNotBlank(codicesoftware)) {
	    list = tipiendoService.findByDescAndSW(textToSearch, codiceFamiglia, software, null);
	} else {
	    list = tipiendoService.findByDescSWeTT(textToSearch, codiceFamiglia);
	}
	List<CodiceDescrizioneBean> cdbs = new ArrayList<CodiceDescrizioneBean>();
	for (Tipiendo te : list) {
	    String descrizione = "";
	    if (te.getTipifamiglieendo() != null) {
		descrizione = "<b>" + te.getTipifamiglieendo().getTipo() + "</b> - ";
	    } else {
	    }
	    descrizione += te.getTipo();
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice(String.valueOf(te.getId().getCodice()));
	    cdb.setDescrizione(descrizione);
	    cdbs.add(cdb);
	}
	Collections.sort(cdbs);
	renderHTMLResponse(response, cdbs, "codice", "descrizione");
    }

    @RequestMapping
    public void findTipiendo(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codiceFamiglia", required = false) Integer codiceFamiglia,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	String[] software = new String[1];
	software[0] = codicesoftware;
	List<Tipiendo> list = tipiendoService.findByDescAndSW(textToSearch, codiceFamiglia, software, null);
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
	    renderHTMLResponse(response, list, "id.codice", "descrizioneCompleta");
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
    public void findAree2(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Aree2> list = aree2Service.findByDescrizione(textToSearch);
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
	List<Dyn2Modellit> list = dyn2ModellitService.findByDescrizione(entity, false);
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
    public void ajaxFindQuadro(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<QrxmlBase> list = qrxmlBaseService.findByDescrizione(textToSearch);
	renderHTMLResponse(response, list, "id.codice", "descrizioneEstesa");
    }

    @RequestMapping
    public void findDyn2ModelliCurretSoftwareOrTT(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware,
	    @RequestParam(value = "isComuneBase", required = false) Boolean isComuneBase, HttpServletResponse response) throws IOException {

	Dyn2Modellit entity = new Dyn2Modellit();
	if (textToSearch != null) {
	    entity.setDescrizione(textToSearch);
	}
	List<Dyn2Modellit> list;
	if (StringUtils.isNotBlank(codicesoftware)) {
	    list = dyn2ModellitService.findByDescrizioneAndSoftware(entity, codicesoftware);
	} else {
	    if (isComuneBase == null) {
		isComuneBase = Boolean.FALSE;
	    }
	    list = dyn2ModellitService.findByDescrizione(entity, isComuneBase);
	}
	renderHTMLResponse(response, list, "id.codice", "descrizionedaRicerca");
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
	    @RequestParam(value = "codiceModello", required = false) Integer codiceModello,
	    @RequestParam(value = "isComuneBase") Boolean isComuneBase, HttpServletResponse response) throws IOException {

	if (isComuneBase == null) {
	    isComuneBase = Boolean.FALSE;
	}
	String idcomune = isComuneBase ? ORMHelper.getIdcomunebase() : ORMHelper.getIdcomune();
	List<Dyn2Campi> list = dyn2CampiService.findByDescrizioneAndSoftwareAndModello(textToSearch, idcomune, codiceModello, codiceSoftware);
	renderHTMLResponse(response, list, "id.codice", "nomecampo");
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
		//iva = WebConstants.CONST_IVA;
		throw new RuntimeException("Errore nella configurazione dei conti della contabilita'. Non e' stata definita l'iva per il conto " +
					   conti.getDescrizione() + "(" + conti.getId() + ")");
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

	List<Anagrafe> list = anagrafeService.findRichiedentiByIstanza(textToSearch, codiceIstanza, tipoAnagrafe);
	renderHTMLResponse(response, list, "id.codice", "descrizioneRichiedente");
    }

    @RequestMapping
    public String dettaglioAnagrafe(@RequestParam("codiceAnagrafe") Integer codiceAnagrafe,
	    @RequestParam(value = "showParametriDiv", required = false) Boolean showParametriDiv, Model model, HttpServletResponse response)
	    throws IOException {

	// faccio lo split della stringa inserita
	PkId idAnagrafe = new PkId(codiceAnagrafe);
	Anagrafe anagrafe = anagrafeService.findById(idAnagrafe);
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
    public void findMercati(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Mercati> list = mercatiService.findByDescrizione(textToSearch, MercatiEnum.ACTIVE);
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
    @SuppressWarnings("rawtypes")
    @RequestMapping
    public void findPosteggiNonAssegnati(@RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam(value = "codiceMercatiUso", required = false) Integer codiceMercatiUso, HttpServletResponse response) throws IOException {

	PkId id = new PkId(codiceMercato);
	Mercati mercati = new Mercati();
	mercati.setId(id);
	List<CodiceDescrizioneBean> mercatiDList = mercatiDService.findPosteggiNonAssegnatiByMercato(codiceMercato, codiceMercatiUso,
		PosteggiEnum.ACTIVE);
	StringBuffer buffer = new StringBuffer("");
	for (CodiceDescrizioneBean cdb : mercatiDList) {
	    buffer.append(cdb.getCodice() + "-SEP-" + cdb.getDescrizione() + "-SEP-");
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call findPosteggiNonAssegnati with codiceMercato: '" + String.valueOf(codiceMercato) + "', codiceMercatiUso: " +
		      String.valueOf(codiceMercatiUso) + " return: '" + buffer.toString() + "'");
    }

    @RequestMapping
    public void findResponsabili(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	Responsabili entity = new Responsabili();
	if (textToSearch != null) {
	    entity.setResponsabile(textToSearch);
	}
	List<Responsabili> list = responsabiliService.findByFilter(entity);
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
		ORMHelper.getIdcomune(), codiceTipologia, ORMHelper.getIdcomune(), escludiDis);
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
	//renderHTMLResponse(response, list, "id.codice", "procedimento");
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
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware,
	    @RequestParam(value = "tipoEndo", required = false) String tipoEndo, HttpServletResponse response) throws IOException {

	String[] software = new String[1];
	software[0] = codicesoftware;
	List<Inventarioprocedimenti> list = inventarioprocedimentiService.findByDescrizioneFamigliaendoETipologiaAndSoftware(textToSearch,
		codiceFamiglia, codiceTipologia, software, null, tipoEndo);
	renderHTMLResponse(response, list, "id.codice", "procedimento");
    }

    @RequestMapping
    public void findInventarioprocedimentoAndSoftware(@RequestParam("textToSearch") String textToSearch,
	    @RequestParam(value = "codicesoftware", required = false) String codicesoftware, HttpServletResponse response) throws IOException {

	List<Inventarioprocedimenti> list;
	if (StringUtils.isNotBlank(StringUtils.defaultString(codicesoftware).trim())) {
	    list = inventarioprocedimentiService.findByDescrizioneFamigliaendoAndSoftware(textToSearch, codicesoftware);
	} else {
	    list = inventarioprocedimentiService.findByDescrizioneFamigliaendoAndSoftware(textToSearch, null);
	}
	renderHTMLResponse(response, list, "id.codice", "procedimento");
    }

    @RequestMapping
    public void findTipimodalitapagamento(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	List<Tipimodalitapagamento> list = tipimodalitapagamentoService.findByMpDescrestesa(textToSearch);
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
    public void findTipologiaRegistri(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	StringBuffer buffer = new StringBuffer("<ul>");
	Tipologiaregistri tipologiaregistri = new Tipologiaregistri();
	tipologiaregistri.setTrDescrizione(textToSearch);
	List<Tipologiaregistri> list = tipologiaregistriService.findByDescrizione(tipologiaregistri);
	for (Tipologiaregistri tipologiaregistri2 : list) {
	    Integer codiceRegistro = tipologiaregistri2.getId().getCodice();
	    buffer.append("<li id='").append(codiceRegistro).append("'");
	    // verifica del registro per la numerazione
	    TipologiaregistriConfigurazioneEnum tipoRegConf = tipologiaregistriService.getImpostazioniRegistro(codiceRegistro);
	    if (tipoRegConf != TipologiaregistriConfigurazioneEnum.NUMERO_REGISTRO_MANUALE) {
		// registro automatico
		buffer.append(" name='").append(true).append("'");
	    } else {
		buffer.append(" name='").append(false).append("'");
	    }
	    buffer.append(">").append(tipologiaregistri2.getTrDescrizione()).append("</li>");
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
	    @RequestParam("mercatiUso.id.codice") Integer codiceUsoMercato, HttpServletResponse response) throws IOException {

	Anagrafe entity = new Anagrafe();
	entity.setNominativo(textToSearch);
	List<Anagrafe> listSpuntistiMercati = null;
	//List<AnagrafeFiere> listSpuntistiFiere = null;
	StringBuffer buffer = new StringBuffer("<ul>");
	//	if (mercati != null && mercati.getManifestazione().getCodice().intValue() == WebConstants.MANIFESTAZIONE_FIERA) {
	//	    // FIXME manca il legame tra bando e calendari per questo se apro una giornata vecchia e cerco gli spuntisti
	//	    // non trovo quelli corretti
	//	    // Alberoproc alberoproc = mercatipresenzeTService.findInterventoConcessionari(mercati, mercatiUso);
	//	    try {
	//		listSpuntistiFiere = anagrafeService.findAnagrafeSpuntistiFiere(mercati, mercatiUso, entity);
	//	    } catch (Exception e) {
	//		renderHTMLException(e.getMessage(), response);
	//		return;
	//	    }
	//	    for (AnagrafeFiere anagrafeFiere : listSpuntistiFiere) {
	//		// fix per non passare un null come valore in query string
	//		String idAut = "";
	//		if (anagrafeFiere.getAutorizzazioni() != null && anagrafeFiere.getAutorizzazioni().getId().getCodice() != null) {
	//		    idAut = anagrafeFiere.getAutorizzazioni().getId().getCodice().toString();
	//		}
	//		// end fix
	//		buffer.append("<li lang='").append(idAut).append("'").append(" id='").append(anagrafeFiere.getAnagrafe().getId().getCodice())
	//			.append("'").append(" title='").append(anagrafeFiere.getCatMerc()).append("'>")
	//			.append(anagrafeFiere.getAnagrafe().getDescrizioneRichiedente());
	//		if (StringUtils.isNotBlank(idAut)) {
	//		    buffer.append(" [").append(anagrafeFiere.getAutorizzazioni().getTransientEstremiAut()).append("] ");
	//		}
	//		buffer.append(anagrafeFiere.getCatMerc()).append("</li>");
	//	    }
	//	} else {
	listSpuntistiMercati = anagrafeService.findByDescrizione(textToSearch, null, AnagrafeEnum.ACTIVE, 0, WebConstants.NUM_MAX_RESULTS);
	for (Anagrafe anagrafe : listSpuntistiMercati) {
	    buffer.append("<li id='").append(anagrafe.getId().getCodice()).append("'>").append(anagrafe.getDescrizioneRichiedente()).append("</li>");
	}
	//	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled())
	    log.debug("call anagrafe with textToSearch: '" + textToSearch + "' return: '" + buffer.toString() + "'");
    }

    //    /**
    //     * metodo per la ricerca tipoMovimento. restituisce i record trovati per il software corrente e per il software TT
    //     * 
    //     * @param textToSearch
    //     * @param response
    //     * @throws IOException
    //     */
    //    @RequestMapping
    //    public void findTipiMovimento(@RequestParam("textToSearch") String textToSearch,
    //	    @RequestParam(value = "includiDisabilitate", required = false) Boolean includiDisabilitate, HttpServletResponse response)
    //	    throws IOException {
    //
    //	if (includiDisabilitate == null) {
    //	    includiDisabilitate = Boolean.FALSE;
    //	}
    //	StringBuffer buffer = new StringBuffer("<ul>");
    //	Tipimovimento tipoMovimento = new Tipimovimento();
    //	tipoMovimento.setMovimento(textToSearch);
    //	List<Tipimovimento> list = tipiMovimentoService.findByDescrizione(tipoMovimento, includiDisabilitate);
    //	for (Tipimovimento tipoMovimento2 : list) {
    //	    buffer.append("<li id='").append(tipoMovimento2.getId().getTipomovimento()).append("'>")
    //		    .append(tipoMovimento2.getId().getTipomovimento()).append(" - ").append(tipoMovimento2.getMovimento()).append(" (")
    //		    .append(tipoMovimento2.getSoftware().getDescrizione()).append(")").append("</li>");
    //	}
    //	buffer.append("</ul>");
    //	response.getWriter().write(buffer.toString());
    //	if (log.isDebugEnabled())
    //	    log.debug("call findTipiMovimento with textToSearch: '" + textToSearch + "' return: '" + buffer.toString() + "'");
    //    }
    //    @RequestMapping
    //    public void findTipiMovimentoForSoftware(@RequestParam("textToSearch") String textToSearch,
    //	    @RequestParam(value = "codice", required = false) String codice,
    //	    @RequestParam(value = "includiDisabilitate", required = false) Boolean includiDisabilitate, HttpServletResponse response)
    //	    throws IOException {
    //
    //	if (includiDisabilitate == null) {
    //	    includiDisabilitate = Boolean.FALSE;
    //	}
    //	Tipimovimento tipoMovimento = new Tipimovimento();
    //	tipoMovimento.getId().setTipomovimento(textToSearch);
    //	tipoMovimento.setMovimento(textToSearch);
    //	List<Tipimovimento> list = tipiMovimentoService.findTipimovimentoByDescrizioneAndSoftware(tipoMovimento, codice, includiDisabilitate);
    //	renderHTMLResponse(response, list, "id.tipomovimento", "descrizioneEstesa");
    //    }
    //
    //    protected void renderMovimentiProtocolloHTMLResponse(HttpServletResponse response, Map<String, Boolean> tmovs, boolean showSpanConteggio)
    //	    throws IOException {
    //
    //	StringBuffer buffer = new StringBuffer("<ul>");
    //	int i = 0;
    //	int size = 0;
    //	List<Tipimovimento> tmlist = new ArrayList<Tipimovimento>();
    //	Iterator<?> it = tmovs.entrySet().iterator();
    //	while (it.hasNext()) {
    //	    Map.Entry<String, Boolean> pairs = (Map.Entry<String, Boolean>) it.next();
    //	    TipimovimentoId idt = new TipimovimentoId(pairs.getKey());
    //	    Tipimovimento tm = tipiMovimentoService.findById(idt);
    //	    tmlist.add(tm);
    //	}
    //	Collections.sort(tmlist, new TipimovimentoComparator());
    //	try {
    //	    if (tmlist == null || tmlist.size() == 0) {
    //		buffer.append("<li id=''>").append(" ").append("</li>");
    //	    } else {
    //		size = tmlist.size();
    //		for (Tipimovimento tm : tmlist) {
    //		    String tipomovimento = tm.getId().getTipomovimento();
    //		    Boolean isMovimentoDaEseguire = tmovs.get(tipomovimento);
    //		    String cssClassName = "";
    //		    if (isMovimentoDaEseguire.booleanValue()) {
    //			cssClassName = "movimentoDaEffettuare";
    //		    }
    //		    buffer.append("<li class=\"" + cssClassName + "\" id='").append(PropertyUtils.getProperty(tm, "id.tipomovimento")).append("'>")
    //			    .append(PropertyUtils.getProperty(tm, "descrizioneEstesa")).append("</li>");
    //		    i++;
    //		    if (i == WebConstants.NUM_MAX_RESULTS) {
    //			break;
    //		    }
    //		}
    //	    }
    //	    buffer.append("</ul>");
    //	    if (showSpanConteggio) {
    //		buffer.append("<span style='font-weight: bold;'>").append("Visualizzati ").append(i).append(" risultati di ").append(size)
    //			.append("</span>");
    //	    }
    //	    response.setContentType("text/plain");
    //	    response.getWriter().write(buffer.toString());
    //	} catch (Exception e) {
    //	    log.error("Ajax search error: {}", e.getMessage());
    //	    throw new IOException(e);
    //	}
    //	if (log.isDebugEnabled()) {
    //	    log.debug("Ajax search result: {}", buffer.toString());
    //	}
    //    }
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
	    buffer = new StringBuffer(StringUtils.defaultIfEmpty(messaggicfgbase.getContesto(), "") + "," +
				      StringUtils.defaultIfEmpty(messaggicfgbase.getOggetto(), "") + "," +
				      StringUtils.defaultIfEmpty(messaggicfgbase.getCorpo(), "") + "," + messaggicfgbase.getFlgInvio() + "," +
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
	LayouttestiId id = new LayouttestiId();
	id.setCodicetesto(key);
	id.setSoftware(ORMHelper.getSoftware());
	Layouttesti layouttesti = layouttestiService.findById(id);
	if (layouttesti == null) {
	    Layouttesti layouttestiInsert = new Layouttesti();
	    layouttestiInsert.setId(id);
	    layouttestiInsert.setNuovotesto(value);
	    layouttestiService.insert(layouttestiInsert);
	} else {
	    layouttesti.setNuovotesto(value);
	    layouttestiService.update(layouttesti);
	}
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
    public String dettaglioPosteggio(@RequestParam("codicePosteggio") Integer codicePosteggio, Model model, HttpServletResponse response)
	    throws IOException {

	// faccio lo split della stringa inserita
	PkId id = new PkId(codicePosteggio);
	MercatiD posteggio = mercatiDService.findById(id);
	model.addAttribute("posteggio", posteggio);
	if (log.isDebugEnabled())
	    log.debug("call dettaglioPosteggio with codicePosteggio: " + codicePosteggio);
	response.setContentType("text/plain");
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
     * Viene utilizzato nel formOneri degli endoprocedimenti per caricare l'elenco delle causali
     * 
     * @param textToSearch
     * @param codice
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void findTipicausalioneriForInventarioprocedimentioneri(@RequestParam("textToSearch") String textToSearch,
	    //@RequestParam("idcomendo") String idComEndo, @RequestParam("idendo") Integer idEndo,
	    @RequestParam(value = "codicesw", required = false) String codiceSw, HttpServletResponse response) throws IOException {

	List<Tipicausalioneri> list = tipicausalioneriService.findByDescrizione(textToSearch, codiceSw);
	StringBuffer sbuf = new StringBuffer("<ul>");
	for (Tipicausalioneri tipicausalioneri : list) {
	    sbuf.append("<li id=\"").append(tipicausalioneri.getId().getCodice()).append("\">").append(tipicausalioneri.getCoDescrizione())
		    .append("</li>");
	}
	sbuf.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(sbuf.toString());
    }

    @RequestMapping
    public void findTipicausalioneriFilterByFlagEndo(@RequestParam("idcomune") String idComune, @RequestParam("textToSearch") String textToSearch,
	    @RequestParam("flagEndo") Boolean flagEndo, @RequestParam("codicesoftware") String codicesoftware, HttpServletResponse response)
	    throws IOException {

	//TODO aggiungere argomento idcomune al metodo service qui sotto
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

	Naturaendobase entity = new Naturaendobase();
	if (textToSearch != null) {
	    entity.setNatura(textToSearch);
	}
	List<Naturaendobase> list = naturaendobaseService.findBydescrizione(entity.getNatura());
	renderHTMLResponse(response, list, "id", "descrizioneEstesa");
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
	//Tolto dopo i problemi avuti con Assisi.
	//	criterio.addFilterField(FilterUtils.equals("trFlagresponsabile", true, "tiporesponsabile", Boolean.class));
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

    //    @RequestMapping()
    //    public ModelAndView jsonFindMovimentoAvvioProcedura(@RequestParam(value = "codice", required = false) Integer codice, HttpServletResponse response)
    //	    throws IOException {
    //
    //	Tipiprocedure procedura = tipiprocedureService.findById(new PkId(codice));
    //	if (null == procedura) {
    //	    throw new RuntimeException("La procedura con codice " + codice + " non è stata trovata.");
    //	}
    //	Tipimovimento tm = new Tipimovimento();
    //	Map<String, Object> model = new HashMap<String, Object>();
    //	model.put("tipoMovimento", tm);
    //	return new ModelAndView("jsonView", model);
    //    }
    //
    //    @RequestMapping
    //    public void findMovimentiAvvioProcedura(@RequestParam("textToSearch") String textToSearch,
    //	    @RequestParam("codiceProcedura") Integer codiceProcedura, HttpServletResponse response) throws IOException {
    //
    //	Tipiprocedure procedura = tipiprocedureService.findById(new PkId(codiceProcedura));
    //	if (null == procedura) {
    //	    renderHTMLException("E' necessario specificare una procedura", response);
    //	    return;
    //	    // throw new RuntimeException("La procedura con codice " + codiceProcedura + " non è stata trovata.");
    //	}
    //	// renderHTMLResponse(response, list, "id.tipomovimento", "movimento");
    //    }
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
	    @RequestParam("status_msg") String status_msg, Model model, HttpServletResponse response) throws IOException {

	PkId id = new PkId(codice);
	Responsabili entity = responsabiliService.findById(id);
	ResponsabiliCommand responsabile = new ResponsabiliCommand();
	responsabile.setEntity(entity);
	List<Clpermmenu> clpermmenuList = new ArrayList<Clpermmenu>();
	List<Clmenu> clmenuList = clmenuService.findTreeBySoftware(permessisoftware);
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
		int menulinkLen = clmenu.getMenulink().length();
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
		    _clmenu.setMenulink(clmenu.getMenulink());
		    _clmenu.setPagina(clmenu.getPagina());
		    _clmenu.setSoftware(clmenu.getSoftware());
		    _clmenu.setSoftwareesclusi(clmenu.getSoftwareesclusi());
		    _clmenu.setVerticalizzazione(clmenu.getVerticalizzazione());
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

    //    @RequestMapping
    //    public String dettaglioRespTipimov(@RequestParam("tipo") String tipo, @RequestParam("codiceResponsabile") Integer codiceResponsabile,
    //	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
    //
    //	if (codiceResponsabile == null) {
    //	    throw new RuntimeException("Il parametro codiceresponsabile non può essere nullo");
    //	}
    //	if (StringUtils.defaultIfEmpty(tipo, "sca").equalsIgnoreCase("avv")) {
    //	    List<ResponsabiliTmAvv> rtms = responsabiliTmAvvService.findByResponsabile(codiceResponsabile);
    //	    model.addAttribute("listaMov", rtms);
    //	} else {
    //	    List<ResponsabiliTmSca> rtms = responsabiliTmScaService.findByResponsabile(codiceResponsabile);
    //	    model.addAttribute("listaMov", rtms);
    //	}
    //	model.addAttribute("tipo", tipo);
    //	response.setContentType("text/plain");
    //	return "ajax/dettaglioRespTipimov";
    //    }
    //    @RequestMapping
    //    public void findTipiMovPerResponsabile(@RequestParam("textToSearch") String textToSearch, @RequestParam("tipo") String tipo,
    //	    @RequestParam("codiceResponsabile") Integer codiceResponsabile,
    //	    @RequestParam(value = "includiDisabilitate", required = false) Boolean includiDisabilitate, Model model, HttpServletRequest request,
    //	    HttpServletResponse response) throws IOException {
    //
    //	if (includiDisabilitate == null) {
    //	    includiDisabilitate = Boolean.FALSE;
    //	}
    //	if (codiceResponsabile == null) {
    //	    throw new RuntimeException("Il parametro codiceresponsabile non può essere nullo");
    //	}
    //	List<Tipimovimento> rtms = tipiMovimentoService.findByTipimovAndResponsabile(textToSearch, codiceResponsabile, includiDisabilitate);
    //	renderHTMLResponse(response, rtms, "id.tipomovimento", "descrizioneEstesa");
    //    }
    //
    //    @RequestMapping
    //    public void insertRespTipimov(@RequestParam("tipomovimento") String tipomovimento, @RequestParam("tipo") String tipo,
    //	    @RequestParam("codiceResponsabile") Integer codiceResponsabile, Model model, HttpServletRequest request, HttpServletResponse response)
    //	    throws IOException {
    //
    //	if (codiceResponsabile == null) {
    //	    throw new RuntimeException("Il parametro codiceresponsabile non può essere nullo");
    //	}
    //	if (StringUtils.isBlank(tipomovimento)) {
    //	    throw new RuntimeException("Il parametro tipomovimento non può essere nullo");
    //	}
    //	Responsabili resp = responsabiliService.findById(new PkId(codiceResponsabile));
    //	if (resp == null) {
    //	    throw new RuntimeException("Non è stato trovato nessun operatore con codice " + String.valueOf(codiceResponsabile));
    //	}
    //	Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(tipomovimento));
    //	if (tm == null) {
    //	    throw new RuntimeException("Non è stato trovato nessun tipomovimento con codice " + tipomovimento);
    //	}
    //	if (StringUtils.defaultIfEmpty(tipo, "sca").equalsIgnoreCase("avv")) {
    //	    ResponsabiliTmAvvId id = new ResponsabiliTmAvvId(codiceResponsabile, tipomovimento);
    //	    ResponsabiliTmAvv obj = responsabiliTmAvvService.findById(id);
    //	    if (obj == null) {
    //		obj = new ResponsabiliTmAvv();
    //		obj.setId(id);
    //		responsabiliTmAvvService.insert(obj);
    //	    }
    //	} else if (StringUtils.defaultIfEmpty(tipo, "sca").equalsIgnoreCase("sca")) {
    //	    ResponsabiliTmScaId id = new ResponsabiliTmScaId(codiceResponsabile, tipomovimento);
    //	    ResponsabiliTmSca obj = responsabiliTmScaService.findById(id);
    //	    if (obj == null) {
    //		obj = new ResponsabiliTmSca();
    //		obj.setId(id);
    //		responsabiliTmScaService.insert(obj);
    //	    }
    //	}
    //    }
    //
    //    @RequestMapping
    //    public void deleteRespTipimov(@RequestParam("tipomovimento") String tipomovimento, @RequestParam("tipo") String tipo,
    //	    @RequestParam("codiceResponsabile") Integer codiceResponsabile, Model model, HttpServletRequest request, HttpServletResponse response)
    //	    throws IOException {
    //
    //	if (codiceResponsabile == null) {
    //	    throw new RuntimeException("Il parametro codiceresponsabile non può essere nullo");
    //	}
    //	if (StringUtils.isBlank(tipomovimento)) {
    //	    throw new RuntimeException("Il parametro tipomovimento non può essere nullo");
    //	}
    //	Responsabili resp = responsabiliService.findById(new PkId(codiceResponsabile));
    //	if (resp == null) {
    //	    throw new RuntimeException("Non è stato trovato nessun operatore con codice " + String.valueOf(codiceResponsabile));
    //	}
    //	Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(tipomovimento));
    //	if (tm == null) {
    //	    throw new RuntimeException("Non è stato trovato nessun tipomovimento con codice " + tipomovimento);
    //	}
    //	if (StringUtils.defaultIfEmpty(tipo, "sca").equalsIgnoreCase("avv")) {
    //	    ResponsabiliTmAvvId id = new ResponsabiliTmAvvId(codiceResponsabile, tipomovimento);
    //	    ResponsabiliTmAvv obj = responsabiliTmAvvService.findById(id);
    //	    if (obj != null) {
    //		responsabiliTmAvvService.delete(obj);
    //	    }
    //	} else if (StringUtils.defaultIfEmpty(tipo, "sca").equalsIgnoreCase("sca")) {
    //	    ResponsabiliTmScaId id = new ResponsabiliTmScaId(codiceResponsabile, tipomovimento);
    //	    ResponsabiliTmSca obj = responsabiliTmScaService.findById(id);
    //	    if (obj != null) {
    //		responsabiliTmScaService.delete(obj);
    //	    }
    //	}
    //    }
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

	//Metto sul model il responsabile loggato
	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	model.addAttribute("responsabile", responsabili);
	boolean iscontestoExportPresente = true;
	if (StringUtils.isBlank(contestoExport)) {
	    iscontestoExportPresente = false;
	    contestoExport = "CON";
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
	    List<ChiaveValoreBean<String, String>> list = dyn2CampiService.findValoriPerCampo(textToSearch, ORMHelper.getIdcomune(), codiceCampo,
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
	List<ChiaveValoreBean<String, String>> list = dyn2CampiService.findValoriPerCampo(term, ORMHelper.getIdcomune(), codiceCampo,
		WebConstants.NUM_MAX_RESULTS);
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
    public void findRiTipiintervento(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	try {
	    List<RiTipiintervento> list = riTipiinterventoService.findByDescrizione(textToSearch, null, null);
	    renderHTMLResponse(response, list, "codice", "descrizioneEstesa");
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
	    sbuf.append("<div id=\"warning_msg\" class=\"warning_header\" ><div>&nbsp;</div>");
	    for (String warn : warnings) {
		sbuf.append("<div>").append(warn).append("</div>");
	    }
	    sbuf.append("</div>");
	}
	List<String> infos = (List<String>) request.getSession().getAttribute("___infos");
	if (infos != null && infos.size() > 0) {
	    String message = getMessageFromBundle("02", null);
	    sbuf.append("<div id=\"info_msg\" class=\"success_header\" ><div>&nbsp;</div>");
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
    public void ajaxVisualizzaParametroStep(@RequestParam("codice") Integer codice, Model model, HttpServletResponse response) throws IOException {

	FoArjStepsParams s = foArjStepsParamsService.findById(new PkId(codice));
	String valore = StringUtils.defaultString(s.getValore()).trim();
	if (StringUtils.isNotBlank(valore)) {
	    if (Utilities.isInteger(valore)) {
		Integer codicedato = Integer.parseInt(valore);
		if (s.getFoArjSteps().getFoArjStepsBase().getNomeStep().equalsIgnoreCase("QUADRO_XML")) {
		    QrxmlBase s2 = qrxmlBaseService.findById(new PkId(codicedato));
		    if (s2 != null) {
			valore = s2.getCodice();
			if (StringUtils.isNotBlank(s2.getHelp())) {
			    valore += " (" + s2.getHelp() + ")";
			}
		    }
		} else if (s.getFoArjSteps().getFoArjStepsBase().getNomeStep().equalsIgnoreCase("QUADRO")) {
		    Dyn2Modellit s2 = dyn2ModellitService.findById(new PkId(codicedato));
		    if (s2 != null) {
			valore = s2.getDescrizioneEstesa();
		    }
		}
	    }
	}
	response.setContentType("text/html");
	response.getWriter().write(valore.toString());
    }

    @RequestMapping
    public void ajaxDisponibilitaDizionario(Model model, HttpServletResponse response) throws IOException {

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
