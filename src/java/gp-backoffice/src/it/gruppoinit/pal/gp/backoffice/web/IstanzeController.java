package it.gruppoinit.pal.gp.backoffice.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.helpers.XMLUtils;
import org.hibernate.validator.InvalidValue;
import org.jmesa.custom.CustomHeaderEditor;
import org.jmesa.custom.DataCustomFilterEditor;
import org.jmesa.custom.DateCustomFilterMatcherMap;
import org.jmesa.custom.LinkAltriIndirizziCellEditor;
import org.jmesa.custom.LinkAutorizzazioniCellEditor;
import org.jmesa.custom.LinkElaborazioneCellEditor;
import org.jmesa.custom.LinkEndoprocedimentiCellEditor;
import org.jmesa.custom.LinkIstanzeCellEditor;
import org.jmesa.custom.LinkMovimentiCellEditor;
import org.jmesa.custom.LinkSorteggiCellEditor;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.RowSelectImpl;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.ExportComponentFactory;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.csv.CsvComponentFactory;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.renderer.FilterRenderer;
import org.jmesa.web.GenerateTable;
import org.jmesa.web.HttpServletRequestSpringWebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.gruppoinit.pal.gp.backoffice.web.util.ProgressBarUtil;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.AppIoServizi;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Aree2;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.Catasto;
import it.gruppoinit.pal.gp.core.domain.Categorieeventibase;
import it.gruppoinit.pal.gp.core.domain.Chiusureistanza;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttori;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Impianti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiT;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.IstanzeareeId;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.NotificheAusl;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PecInbox;
import it.gruppoinit.pal.gp.core.domain.Permistanze;
import it.gruppoinit.pal.gp.core.domain.PermistanzeId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.PuFormati;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeIstanze;
import it.gruppoinit.pal.gp.core.domain.Ricerche;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.Settoriavvisi;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.TipiLocalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Tipologiaistanza;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeMappaliComparator;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ReportIstanzaChiusaHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoCommand;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeCommand;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeOnLineHelper;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaRigheFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.IAttivitaIstanzeService;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.ScollegamentoUnicaIstanzaException;
import it.gruppoinit.pal.gp.core.features.attivita.restrizioni.RestrizioneAttivitaEsistenteException;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.AppIoServiziConfigRestResponse;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoServiziConfigService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ComunicazioniMassUtils;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ComunicazioniUtilsGenService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IComunicazioniGenService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IComunicazioniToGenService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.QueriesConstants;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.model.ComunicazioneMassivaGenModel;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.IComunicazioniManifestazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ComunicazioniGenCommand;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ListaComunicazioniResoconti;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.ICartograficoService;
import it.gruppoinit.pal.gp.core.features.istanze.assegnazioni.CalcoloDisponibilitaRespIstruttoriaService;
import it.gruppoinit.pal.gp.core.features.istanze.assegnazioni.CalcoloDisponibilitaRespProcedimentoService;
import it.gruppoinit.pal.gp.core.features.istanze.assegnazioni.DisponibilitaResponsabile;
import it.gruppoinit.pal.gp.core.features.istanze.assegnazioni.EventoIstanzaAssegnata;
import it.gruppoinit.pal.gp.core.features.istanze.assegnazioni.IAssegnazioneGruppiDettaglioService;
import it.gruppoinit.pal.gp.core.features.istanze.assegnazioni.IAssegnazioneGruppiTestataService;
import it.gruppoinit.pal.gp.core.features.istanze.assegnazioni.IAssegnazioniService;
import it.gruppoinit.pal.gp.core.features.istanze.assegnazioni.NumeriIstanzaResponse;
import it.gruppoinit.pal.gp.core.features.istanze.assegnazioni.ResponsabileIstanzaEnum;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeStrRicalcoloRestClient;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeareeService;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RicalcoloMaxReqParams;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RicalcoloMaxRequest;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.RicalcoloAreeIstanzeDAO;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione.VerticalizzazioneQRCodeServiceImpl;
import it.gruppoinit.pal.gp.core.features.istanze.eventi.NotificaSoggettiIstanzaAggiornatiRequest;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.IstanzecollegateService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioneMassivaRigaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioniMassiveModel;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.rabbitmq.CodaMessaggiRabbitService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.jmesa.IstanzeCollegateTable;
import it.gruppoinit.pal.gp.core.jmesa.IstanzeHelperTable;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ArchiviazioniIstanzeService;
import it.gruppoinit.pal.gp.core.service.Aree2Service;
import it.gruppoinit.pal.gp.core.service.AreeService;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.CatastoService;
import it.gruppoinit.pal.gp.core.service.ChiusureistanzaService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.DomandestcService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.FoArconfigurazioneService;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriRespService;
import it.gruppoinit.pal.gp.core.service.ImpiantiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiTService;
import it.gruppoinit.pal.gp.core.service.IstanzeManager;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento;
import it.gruppoinit.pal.gp.core.service.IstanzeattivitaService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.IstanzereplicateService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.LavoritipiCausalioneriService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.MovimentiManager;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentimailService;
import it.gruppoinit.pal.gp.core.service.NlaManager;
import it.gruppoinit.pal.gp.core.service.NotificheAuslService;
import it.gruppoinit.pal.gp.core.service.PecInboxService;
import it.gruppoinit.pal.gp.core.service.PermistanzeService;
import it.gruppoinit.pal.gp.core.service.QrcodeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliAssenzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RicercheService;
import it.gruppoinit.pal.gp.core.service.RuoliUtentiEnum;
import it.gruppoinit.pal.gp.core.service.SettoriService;
import it.gruppoinit.pal.gp.core.service.SitService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.StcService;
import it.gruppoinit.pal.gp.core.service.StradariocoloreService;
import it.gruppoinit.pal.gp.core.service.TipiLocalizzazioniService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipiarchivioistanzeService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;
import it.gruppoinit.pal.gp.core.service.TipiorarioService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;
import it.gruppoinit.pal.gp.core.service.TipologiaistanzaService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.EntityValidationException;
import it.gruppoinit.pal.gp.core.service.exception.NumeroIstanzaUtilizzatoException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeFilterUtils;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.helper.QRCodeBean;
import it.gruppoinit.pal.gp.core.service.helper.QrcodeHelper;
import it.gruppoinit.pal.gp.core.service.helper.ResponsabiliAssegnazioniHelper;
import it.gruppoinit.pal.gp.core.service.helper.ResponsabiliAssegnazioniHelperBean;
import it.gruppoinit.pal.gp.core.service.helper.RiepilogoHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;
import it.gruppoinit.pal.gp.core.service.helper.TipoAuthQRcodeEnum;
import it.gruppoinit.pal.gp.core.service.rules.OperazioniAutomaticheBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.LoggerModificheIstanze;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.LDPWsClient;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloEsitatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.EnumEsitatoType;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.ldp.suolopubblico.ComplexTypeAreeUsoPubblico;
import it.ldp.suolopubblico.ComplexTypePeriodo;

@Controller
@SessionAttributes(value = { "istanzeCommand", "domandestc", "cambioInterventoCommand", "comunicazioniCommissioniCommand" })
public class IstanzeController extends BaseController<IstanzeCommand> {

    private static final String _REPORT_ISTANZE_CHIUSE = "_reportIstanzeChiuse_";
    private static final String SET_ENDO_IN_SESSION = "ISTANZE_ENDOS_IN_SESSION";
    private String INSERT_MOVIMENTI_ATTR = "INSERT_MOVIMENTI_ATTR";
    private String ISTANZE_FILTER_IN_SESSION = "ISTANZE_FILTER_IN_SESSION";
    private static final Logger log = LoggerFactory.getLogger(IstanzeController.class);
    @Autowired
    private AlberoprocEndoService alberoprocEndoService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private AreeService areeService;
    @Autowired
    private Aree2Service aree2Service;
    @Autowired
    private AttivitaService attivitaService;
    @Autowired
    private AzioniService azioniService;
    @Autowired
    private CatastoService catastoService;
    @Autowired
    private ChiusureistanzaService chiusureistanzaService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private DomandestcService domandestcService;
    @Autowired
    private FoArconfigurazioneService foArconfigurazioneService;
    @Autowired
    private IAttivitaService iAttivitaService;
    @Autowired
    private ImpiantiService impiantiService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private IstanzecollegateService istanzecollegateService;
    @Autowired
    private IstanzerichiedentiService istanzerichiedentiService;
    @Autowired
    private IstanzestradarioService istanzestradarioService;
    @Autowired
    private IstanzeattivitaService istanzeattivitaService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private Istanzedyn2datiService istanzedyn2datiService;
    @Autowired
    private IstanzereplicateService istanzereplicateService;
    @Autowired
    private LavoritipiCausalioneriService lavoritipiCausalioneriService;
    @Autowired
    private MovimentimailService movimentimailService;
    @Autowired
    private MovimentiManager movimentiManager;
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private SettoriService settoriService;
    @Autowired
    private SitService sitService;
    @Autowired
    private StatiistanzaService statiistanzaService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private StradariocoloreService stradariocoloreService;
    @Autowired
    private TipiarchivioistanzeService tipiarchivioistanzeService;
    @Autowired
    private TipifamiglieendoService tipifamiglieendoService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private TipiorarioService tipiorarioService;
    @Autowired
    private TipisoggettoService tipisoggettoService;
    @Autowired
    private TipologiaistanzaService tipologiaistanzaService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private IstanzeManager istanzeManager;
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private IstanzeareeService istanzeareeService;
    @Autowired
    private NotificheAuslService notificheAuslService;
    @Autowired
    private StcService stcService;
    @Autowired
    private TipiLocalizzazioniService tipiLocalizzazioniService;
    @Autowired
    private ArchiviazioniIstanzeService archiviazioniIstanzeService;
    @Autowired
    private NlaManager nlaManager;
    @Autowired
    private PecInboxService pecInboxService;
    @Autowired
    private LDPWsClient ldpWsClient;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private QrcodeService qrcodeService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private PermistanzeService permistanzeService;
    @Autowired
    private IstanzeeventiService istanzeeventiService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private RicercheService ricercheService;
    @Autowired
    private IstanzeFilterUtils istanzeFilterUtils;
    @Autowired
    private IstanzeAccessoAttiTService istanzeAccessoAttiTService;
    @Autowired
    private LetteretipoService letteretipoService;
    @Autowired
    private DocumentMergeService documentMergeService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private IAttivitaIstanzeService attivitaIstanzeService;
    @Autowired
    private GruppiIstruttoriRespService gruppiIstruttoriRespService;
    @Autowired
    private ResponsabiliAssenzeService responsabiliAssenzeService;
    @Autowired
    private IAssegnazioneGruppiTestataService assegnazioneGruppiTestataService;
    @Autowired
    private IAssegnazioneGruppiDettaglioService assegnazioneGruppiDettaglioService;
    @Autowired
    private IAssegnazioniService assegnazioniService;
    private IComunicazioniGenService comunicazioniMassiveMService;

    @Autowired
    public void setComunicazioniMassiveMService(@Qualifier("comunicazioniIstServiceImpl") IComunicazioniGenService comunicazioniMassiveMService) {

	this.comunicazioniMassiveMService = comunicazioniMassiveMService;
    }

    @Autowired
    private IComunicazioniManifestazioniService comunicazioniMassiveService;
    @Autowired
    private IComunicazioniToGenService comunicazioniToGenService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    ComunicazioniUtilsGenService comunicazioniUtilsGenService;
    @Autowired
    private MailtipoService mailTipoService;
    private ICartograficoService cartograficoService;

    @Autowired
    public void setCartograficoService(ICartograficoService cartograficoService) {

	this.cartograficoService = cartograficoService;
    }

    @Autowired
    private RicalcoloAreeIstanzeDAO ricalcoloAreeIstanzeDAO;
    @Autowired
    private ProtocollazioneService protocollazioneService;
    @Autowired
    private CodaMessaggiRabbitService codaMessaggiRabbitService;
    @Autowired
    private IAppIoServiziConfigService appIoServiziConfigService;

    @RequestMapping
    public String create(@RequestParam(value = "tipoInserimento", required = false) Integer tipoInserimento, HttpServletRequest request,
	    Model model) {

	request.getSession().removeAttribute(SET_ENDO_IN_SESSION);
	IstanzeCommand command = new IstanzeCommand();
	if (tipoInserimento != null) {
	    command.setTipoInserimento(tipoInserimento);
	}
	command.setDisplayMode(IstanzeCommand.NEW);
	Responsabili operatore = getCurrentlyAuthenticatedUserDetails();
	Responsabili resp = new Responsabili();
	resp.getId().setCodice(operatore.getId().getCodice());
	resp.setResponsabile(operatore.getResponsabile());
	command.getEntity().setResponsabile(resp);
	command.getEntity().setData(Calendar.getInstance().getTime());
	Software software = softwareService.findById(ORMHelper.getSoftware());
	command.getEntity().setSoftware(software);
	String progressivo = istanzeService.findProgressivoIstanza(null, false);
	command.getEntity().setNumeroistanza(progressivo);
	StatiistanzaId id = new StatiistanzaId(WebConstants.STATO_ISTANZA_APERTA_DEFAULT);
	Statiistanza chiusura = statiistanzaService.findById(id);
	command.getEntity().setChiusura(chiusura);
	model.addAttribute("istanzeCommand", command);
	prepareCreateModel(request, model);
	boolean isCartograficoAttivo = this.cartograficoService.getInfoConnettore().getUtilizzo().getAttivita().getElenco();
	model.addAttribute("cartograficoAttivo", isCartograficoAttivo);
	return "istanze/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	request.getSession().removeAttribute(SET_ENDO_IN_SESSION);
	PkId id = new PkId(codice);
	Istanze istanza = istanzeService.findById(id);
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	TipoAccessoEnum tipoAccesso = istanzeService.checkAccessoIstanza(istanza, responsabile);
	if (!(tipoAccesso.equals(TipoAccessoEnum.SOLA_LETTURA) || tipoAccesso.equals(TipoAccessoEnum.SOLA_LETTURA_MOVIMENTI_AMM_INTERNA))) {
	    istanzeService.primaElaborazione(istanza);
	}
	IstanzeCommand command = new IstanzeCommand();
	// Si deve recuperare dalla session il filtro per andarlo a settare sul command che abbiamo 
	// istanziato nuovamente. E' necessario per far funzionare l'history back nel caso sia
	// popolato il filtro per i campi dinamici.
	if (request.getSession().getAttribute(ISTANZE_FILTER_IN_SESSION) != null) {
	    IstanzeFilter filter = (IstanzeFilter) request.getSession().getAttribute(ISTANZE_FILTER_IN_SESSION);
	    command.setIstanzeFilter(filter);
	}
	List<Istanzearee> istanzearees = istanzeareeService.findByIstanza(istanza);
	if (istanzearees.size() > 0) {
	    //	    Set<Istanzearee> istanzearees = istanza.getIstanzearees();
	    for (Istanzearee istanzearee : istanzearees) {
		if (BooleanUtils.isTrue(istanzearee.getPrimario())) {
		    Istanzearee nuovaArea = new Istanzearee();
		    nuovaArea.setId(new IstanzeareeId(istanzearee.getId().getCodiceistanza(), istanzearee.getId().getCodicearea()));
		    Aree area = new Aree();
		    area.setId(new PkId(istanzearee.getId().getCodicearea()));
		    area.setDenominazione(istanzearee.getArea().getDenominazione());
		    nuovaArea.setArea(area);
		    //command.getIstanzeFilter().setIstanzearee(nuovaArea);
		    ///
		    command.setIstanzearee(nuovaArea);
		    break;
		}
	    }
	}
	command.setDisplayMode(IstanzeCommand.VIEW);
	command.setEntity(istanza);
	fixRenderEntityProperty(command);
	model.addAttribute("istanzeCommand", command);
	prepareViewModel(model, request, istanza, command);
	boolean isCartograficoAttivo = this.cartograficoService.getInfoConnettore().getUtilizzo().getAttivita().getModifica();
	model.addAttribute("cartograficoAttivo", isCartograficoAttivo);
	return "istanze/form";
    }

    @RequestMapping
    public void returnCartografico(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	response.setHeader("Location", "./view.htm?codice=" + codice.toString());
	response.setStatus(302);
    }

    /**
     * Tutto ciò che serve a visualizzare la pagina di create nuova istanza. Viene usato anche dopo il verificarsi degli
     * errori in insert.
     * 
     * @param request
     * @param model
     */
    private void prepareCreateModel(HttpServletRequest request, Model model) {

	if (model.containsAttribute("istanzeCommand")) {
	    Map<String, Object> m = model.asMap();
	    IstanzeCommand command = (IstanzeCommand) m.get("istanzeCommand");
	    if (command != null) {
		if (command.getIstanzeFilter() != null) {
		    if (command.getIstanzeFilter().getIstanzemappali() != null) {
			if (command.getIstanzeFilter().getIstanzemappali().getCatasto() != null) {
			    List<Catasto> cs = catastoService.findAll(0, 1);
			    if (cs.size() > 0) {
				command.getIstanzeFilter().getIstanzemappali().setCatasto(cs.get(0));
			    }
			}
		    }
		}
	    }
	}
	// IstanzeCommand cmd = model.
	//Gestione della ricerca e inserimento di un richiedente
	Configurazione configurazione = configurazioneService.findById(new ConfigurazioneId(ORMHelper.getSoftware()));
	// di deafult è imposttao che possono essere mostrate sia persone fisiche che persone giuridiche
	model.addAttribute("configurazioneRichiedentepf", "");
	// Se flagRichiedentepf dell' Oggetto Configurazione ==1 richiedente può essere solo persona fisica
	// Mostra solo i soggetti che sono persone fisiche
	if (configurazione.getFlagRichiedentepf() != null && configurazione.getFlagRichiedentepf().equals(true)) {
	    model.addAttribute("configurazioneRichiedentepf", "F");
	}
	setPageAttributes(model);
	setInserimentoRapidoPageAttributes(model, request);
	// -----INIZIO VERTICALIZZAZIONI ATTIVE----------------------
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_PEOPLE, request);
	isVerticalizzazioneAttivaPerComune(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE, request, null);
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_DBINFORMATICA, request);
	boolean isSitAttivo = isVerticalizzazioneAttivaPerComune(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO, request, null);
	// -----FINE VERTICALIZZAZIONI ATTIVE----------------------
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ISTANZE_POSIZIONE_BOTTONI, "div_bottoni_istanza_sotto", request);
	model.addAttribute("isModificaIntervento", true);
	Set<String> campiGestiti = new HashSet<String>();
	if (isSitAttivo) {
	    campiGestiti = sitService.getCampiGestiti(ORMHelper.getToken(), ORMHelper.getSoftware());
	}
	model.addAttribute("campiGestiti", campiGestiti);
	// Controlla dalla configurazione se c'è la possibilità di aggiungere un nuovo stradario
	model.addAttribute("isAddStradario", configurazione.getFlagVietainsstrdaistanze());
	List<TipiLocalizzazioni> tipiLocalizzazionis = tipiLocalizzazioniService.findAll(null, null);
	model.addAttribute("tipiLocalizzazionis", tipiLocalizzazionis);
    }

    /**
     * Tutto ciò che serve a visualizzare la pagina di create nuova istanza. Viene usato anche dopo il verificarsi degli
     * errori in update/delete.
     * 
     * @param model
     * @param request
     * @param istanza
     * @param istanzeCommand
     *            TODO
     */
    private void prepareViewModel(Model model, HttpServletRequest request, Istanze istanza, IstanzeCommand istanzeCommand) {

	setPageAttributes(model);
	// -----INIZIO VERTICALIZZAZIONI ATTIVE----------------------
	String codiceComune = istanza.getComune().getCodicecomune();
	String softwareIstanza = istanza.getSoftware().getCodice();
	Responsabili r = getCurrentlyAuthenticatedUserDetails();
	boolean canChangeResponsabileAssegnato = BooleanUtils.isTrue(r.getFlagModRespSort());
	model.addAttribute("canChangeResponsabileAssegnato", canChangeResponsabileAssegnato);
	boolean sieder = isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_SIEDER, request);
	if (sieder) {
	    Set<Domandestc> domandestcs = istanza.getDomandestcs();
	    if (!domandestcs.isEmpty()) {
		boolean urlStatoPresente = false;
		boolean urlStatiPresente = false;
		boolean urlModStatiPresente = false;
		Verticalizzazioniparametri urlStatoIstanza = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
			WebConstants.VERTICALIZZAZIONE_SIEDER, WebConstants.VERTICALIZZAZIONE_SIEDER_URL_STATO_PRATICA, codiceComune,
			softwareIstanza);
		if (urlStatoIstanza != null && StringUtils.isNotBlank(urlStatoIstanza.getValore())) {
		    urlStatoPresente = true;
		}
		Verticalizzazioniparametri urlStatiammissibili = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
			WebConstants.VERTICALIZZAZIONE_SIEDER, WebConstants.VERTICALIZZAZIONE_SIEDER_URL_STATI_AMMISSIBILI, codiceComune,
			softwareIstanza);
		if (urlStatiammissibili != null && StringUtils.isNotBlank(urlStatiammissibili.getValore())) {
		    urlStatiPresente = true;
		}
		Verticalizzazioniparametri urlModStatiammissibili = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
			WebConstants.VERTICALIZZAZIONE_SIEDER, WebConstants.VERTICALIZZAZIONE_SIEDER_URL_MODIFICA_STATO_PRATICA, codiceComune,
			softwareIstanza);
		if (urlModStatiammissibili != null && StringUtils.isNotBlank(urlModStatiammissibili.getValore())) {
		    urlModStatiPresente = true;
		}
		model.addAttribute("urlStatiPresente", urlStatiPresente);
		model.addAttribute("urlStatoPresente", urlStatoPresente);
		model.addAttribute("urlModStatiPresente", urlModStatiPresente);
	    }
	}
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_PEOPLE, request);
	boolean qrcode = isVerticalizzazioneAttiva(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE, request);
	model.addAttribute("qrcodeurlwsriepilogo", Boolean.FALSE);
	if (qrcode) {
	    Verticalizzazioniparametri urlRigeneraRiepilogo = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE,
		    VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_URL_WS_RIEPILOGO_PRATICA, codiceComune, softwareIstanza);
	    if (urlRigeneraRiepilogo != null && StringUtils.isNotBlank(urlRigeneraRiepilogo.getValore())) {
		model.addAttribute("qrcodeurlwsriepilogo", Boolean.TRUE);
	    }
	}
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_DBINFORMATICA, request);
	boolean isVerticalizzazioneProtocollo = isVerticalizzazioneAttivaPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE, request, codiceComune);
	if (isVerticalizzazioneProtocollo) {
	    boolean isProtocolloDOCER = false;
	    Verticalizzazioniparametri tipoProtocollo = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOPROTOCOLLO, codiceComune, softwareIstanza);
	    if (tipoProtocollo != null && tipoProtocollo.getValore().equals(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER)) {
		isProtocolloDOCER = true;
	    }
	    model.addAttribute("isDocEr", isProtocolloDOCER);
	    Verticalizzazioniparametri vertLeggiProtocollo = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONELEGGI, codiceComune);
	    String valore = "0";
	    if (vertLeggiProtocollo != null) {
		if (StringUtils.isNotBlank(vertLeggiProtocollo.getValore())) {
		    valore = vertLeggiProtocollo.getValore();
		}
	    }
	    model.addAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONELEGGI, valore);
	    Verticalizzazioniparametri vertBottoneStampa = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONESTAMPA, codiceComune);
	    valore = "0";
	    if (vertBottoneStampa != null) {
		if (StringUtils.isNotBlank(vertBottoneStampa.getValore())) {
		    valore = vertBottoneStampa.getValore();
		}
	    }
	    model.addAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONESTAMPA, valore);
	    Verticalizzazioniparametri gestAccettazione = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTIONE_ACCETTAZIONE, codiceComune);
	    valore = "0";
	    if (gestAccettazione != null) {
		if (StringUtils.isNotBlank(gestAccettazione.getValore())) {
		    valore = gestAccettazione.getValore();
		}
	    }
	    model.addAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTIONE_ACCETTAZIONE, valore);
	    String mostraBottoneAccettaProtocollo = "0";
	    String isEsitatoErroreMessaggio = "";
	    if ("1".equals(valore) && istanza != null && !StringUtils.isBlank(istanza.getNumeroprotocollo())) {
		try {
		    String token = ORMHelper.getToken();
		    String numeroprotocollo = istanza.getNumeroprotocollo();
		    String annoprotocollo;
		    if (istanza.getDataprotocollo() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(istanza.getDataprotocollo());
			annoprotocollo = calendar.get(Calendar.YEAR) + "";
		    } else {
			annoprotocollo = null;
		    }
		    String idprotocollo = istanza.getFkidprotocollo();
		    log.debug("sto leggendo isEsitato: {idprotocollo: " + idprotocollo + " annoprotocollo: " + annoprotocollo +
			      " numeroprotocollo: " + numeroprotocollo + "}");
		    DatiProtocolloEsitatoResponseType resp = protocollazioneService.isEsitato(token, numeroprotocollo, annoprotocollo, idprotocollo,
			    softwareIstanza, codiceComune);
		    if (EnumEsitatoType.NO == resp.getEsitato()) {
			mostraBottoneAccettaProtocollo = "1";
		    } else if (EnumEsitatoType.SI == resp.getEsitato()) {
			mostraBottoneAccettaProtocollo = "0";
		    } else {
			mostraBottoneAccettaProtocollo = "KO";
			if (resp.getErrore() != null && resp.getErrore().getDescrizione() != null) {
			    isEsitatoErroreMessaggio = "Non è stato possibile contattare il servizio di protocollazione a causa di=" +
						       new FunzioneBusinessRemotaException(resp.getErrore().getDescrizione());
			} else {
			    isEsitatoErroreMessaggio = "Non è stato possibile contattare il servizio di protocollazione, vedere log per i dettagli";
			}
			log.error("IsEsitato, errore su protocollo {idprotocollo: " + idprotocollo + " annoprotocollo: " + annoprotocollo +
				  " numeroprotocollo: " + numeroprotocollo + "}");
			if (resp.getErrore() != null && resp.getErrore().getStackTrace() != null) {
			    log.error("Errore durante la lettura dell'esito protocollo: " + resp.getErrore().getStackTrace());
			} else {
			    log.error("Errore durante la lettura dell'esito protocollo: stackTrace non definito");
			}
		    }
		} catch (Exception e) {
		    mostraBottoneAccettaProtocollo = "KO";
		    isEsitatoErroreMessaggio = e + "";
		    if (istanza != null) {
			log.error("IsEsitato, errore su protocollo {idprotocollo: " + istanza.getFkidprotocollo() + " dataprotocollo: " +
				  istanza.getDataprotocollo() + " numeroprotocollo: " + istanza.getNumeroprotocollo() + "}");
		    }
		    log.error("Errore durante la lettura dell'esito protocollo", e);
		}
	    }
	    model.addAttribute("mostraBottoneAccettaProtocollo", mostraBottoneAccettaProtocollo);
	    model.addAttribute("isEsitatoErroreMessaggio", isEsitatoErroreMessaggio);
	    Verticalizzazioniparametri gestisciFascicoloParam = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTISCI_FASCICOLAZIONE, codiceComune);
	    boolean gestiscifascicolo = false;
	    if (gestisciFascicoloParam != null) {
		if (StringUtils.defaultIfEmpty(gestisciFascicoloParam.getValore(), "0").equalsIgnoreCase("1")) {
		    gestiscifascicolo = true;
		}
	    }
	    model.addAttribute("gestisciFascicolo", Boolean.valueOf(gestiscifascicolo));
	}
	Verticalizzazioniparametri verticalizzazioniPtiposit = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO, WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT, codiceComune);
	String tipoSit = "";
	if (verticalizzazioniPtiposit != null) {
	    tipoSit = verticalizzazioniPtiposit.getValore();
	}
	model.addAttribute(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT, tipoSit);
	// FIX devo rileggere perchè in caso di errore la view da errore di lazy loading
	istanza = istanzeService.findById(istanza.getId());
	istanzeCommand.setEntity(istanza);
	fixRenderEntityProperty(istanzeCommand);
	// FIX
	// 
	List<PecInbox> pecId = pecInboxService.findByIstanza(istanza.getId().getCodice(), 0, 1);
	model.addAttribute("pecId", pecId);
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_I_ATTIVITA, request);
	boolean isStcAttiva = isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_STC, request);
	if (isStcAttiva) {
	    boolean enableSTCPraticaCollegataLink = stcService.checkSeAbilitareRichiestaPraticaCollegata(istanza.getId().getCodice());
	    model.addAttribute("enableSTCPraticaCollegataLink", enableSTCPraticaCollegataLink);
	}
	boolean isReplicaIstanze = isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_REPLICAISTANZE, request);
	boolean isSorteggioRapido = isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_SORTEGGIORAPIDO, request);
	AlberoprocHelper alberoprocHelper = alberoprocService.findAlberoprocHelper(istanza.getAlberoproc());
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC, request);
	// Gestione della visualizzazione link ritorno alla pratica GIS	
	boolean isGISLinkActive = false;
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAIONE_SIT_LDP)) {
	    Verticalizzazioniparametri nodo = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
		    WebConstants.VERTICALIZZAZIONE_SIT_LDP_NODI);
	    Verticalizzazioniparametri url = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
		    WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA);
	    Verticalizzazioniparametri urlPraticaGis = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP,
		    WebConstants.VERTICALIZZAZIONE_SIT_LDP_URL_RITORNO_PRATICA_GIS);
	    if (nodo != null && StringUtils.isNotBlank(nodo.getValore()) && url != null && StringUtils.isNotBlank(url.getValore())
		    && urlPraticaGis != null && StringUtils.isNotBlank(urlPraticaGis.getValore())) {
		String tipologiaOccupazione = "", tipologiaPeriodo = "", tipologiaGeometria = "";
		if (alberoprocHelper.getLdpGeometries() != null) {
		    tipologiaGeometria = alberoprocHelper.getLdpGeometries().getCodice();
		}
		if (alberoprocHelper.getLdpOccupazionis() != null) {
		    tipologiaOccupazione = alberoprocHelper.getLdpOccupazionis().getCodice();
		}
		if (alberoprocHelper.getLdpPeriodis() != null) {
		    tipologiaPeriodo = alberoprocHelper.getLdpPeriodis().getCodice();
		}
		if (StringUtils.isNotBlank(tipologiaGeometria) && StringUtils.isNotBlank(tipologiaOccupazione)
			&& StringUtils.isNotBlank(tipologiaPeriodo)) {
		    isGISLinkActive = true;
		}
		model.addAttribute("isGISLinkActive", isGISLinkActive);
		if (isGISLinkActive) {
		    Verticalizzazioniparametri mostraGestioneAreeLDP = verticalizzazioniService.getVerticalizzazioniparametri(
			    WebConstants.VERTICALIZZAIONE_SIT_LDP, WebConstants.VERTICALIZZAZIONE_SIT_LDP_GESTISCI_MOD_DEL_AREE);
		    if (mostraGestioneAreeLDP != null && StringUtils.defaultString(mostraGestioneAreeLDP.getValore(), "N").equalsIgnoreCase("S")) {
			model.addAttribute("mostraGestioneAreeLDP", Boolean.TRUE);
		    } else {
			model.addAttribute("mostraGestioneAreeLDP", Boolean.FALSE);
		    }
		    String _urlPraticaGis = urlPraticaGis.getValore();
		    //_urlPraticaGis = StringUtils.replace(_urlPraticaGis, "<PARAMETRI>", "#q=VBG");
		    Map<String, String> parameterAndvalue = new HashMap<String, String>();
		    // Da inserire
		    parameterAndvalue.put("{codiceistanza}", String.valueOf(istanza.getId().getCodice()));
		    parameterAndvalue.put("{token}", ORMHelper.getToken());
		    String codiceDomandaStc = String.valueOf(istanza.getId().getCodice());
		    List<Domandestc> sts = domandestcService.findByIstanza(istanza.getId().getCodice());
		    if (sts.size() > 0) {
			codiceDomandaStc = URLEncoder.encode(sts.get(0).getIdDomandamitt());
		    }
		    parameterAndvalue.put("{codicedomandastc}", codiceDomandaStc);
		    String returnTo = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
				      request.getContextPath() + "/istanze/tornaDaLDP.htm?codice=" + istanza.getId().getCodice() + "&software=" +
				      ORMHelper.getSoftware();
		    parameterAndvalue.put("{tipologiaOccupazione}", tipologiaOccupazione);
		    parameterAndvalue.put("{returnto}", URLEncoder.encode(returnTo));
		    parameterAndvalue.put("{tipologiaPeriodo}", tipologiaPeriodo);
		    parameterAndvalue.put("{tipologiaGeometria}", tipologiaGeometria);
		    List<Istanzestradario> strades = istanzestradarioService.findByIstanza(istanza.getId().getCodice());
		    String codice_strada = "";
		    String codice_civico = "";
		    if (!strades.isEmpty()) {
			codice_strada = strades.get(0).getStradario().getCodviario();
			if (StringUtils.isBlank(codice_strada)) {
			    codice_strada = String.valueOf(strades.get(0).getStradario().getId().getCodice());
			}
			if (StringUtils.isBlank(codice_civico)) {
			    codice_civico = StringUtils.defaultString(strades.get(0).getCivico(), "");
			}
		    }
		    parameterAndvalue.put("{codice_strada}", codice_strada);
		    parameterAndvalue.put("{codice_civico}", codice_civico);
		    // alberoprocHelper
		    // &tipologia_occupazione={tipologiaOccupazione}&tipologia_periodo={tipologiaPeriodo}&tipologia_geometria={tipologiaGeometria}
		    _urlPraticaGis = Utilities.createLink(parameterAndvalue, _urlPraticaGis);
		    model.addAttribute("urlPraticaGis", _urlPraticaGis);
		}
	    }
	}
	boolean isVerticalizzazioneAUTORIZACCESSIAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_AUTORIZ_ACCESSI);
	model.addAttribute("isVerticalizzazioneAUTORIZACCESSIAttiva", isVerticalizzazioneAUTORIZACCESSIAttiva);
	if (isVerticalizzazioneAUTORIZACCESSIAttiva) {
	}
	// -----FINE VERTICALIZZAZIONI ATTIVE----------------------
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ISTANZE_POSIZIONE_BOTTONI, "div_bottoni_istanza_sotto", request);
	List<Settoriavvisi> avvisi = istanzeattivitaService.findAvvisiIstanza(istanza);
	model.addAttribute("avvisi", avvisi);
	List<Movimentimail> mms = movimentimailService.findByIstanza(istanza);
	boolean isMovimentiMail = (mms.size() > 0);
	model.addAttribute("isMovimentiMailVisibile", isMovimentiMail);
	if (isReplicaIstanze) {
	    Istanze istanzapadre = istanzeService.isReplicata(istanza);
	    if (istanzapadre != null) {
		model.addAttribute("isBottoneVisualizzaReplicheVisibile", true);
		model.addAttribute("isBottoneCreaReplicheVisibile", false);
	    } else {
		model.addAttribute("isBottoneVisualizzaReplicheVisibile", false);
		model.addAttribute("isBottoneCreaReplicheVisibile", true);
	    }
	}
	boolean isModificaIstanza = istanzeService.checkModificaIstanza(istanza);
	model.addAttribute("isModificaIstanza", isModificaIstanza);
	boolean isSorteggiata = true;
	boolean isMostraSchede = true;
	/**
	 * Modulo verticalizzazione SORTEGGIORAPIDO Se attiva consente all'istanza di accedere alle funzionalità di
	 * SORTEGGIO RAPIDO, contiene dei parametri. La funzionalità nasconde tutti i pulsanti dell'istanza (tranne
	 * SALVA, SCHEDE, ELIMINA e CHIUDI) fino a che il valore del campo dinamico specificato nel parametro
	 * CAMPO_DYN_SORTEGGIATA non è diverso da "".
	 */
	if (isSorteggioRapido) {
	    Verticalizzazioniparametri campoDinamicoSorteggio = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_SORTEGGIORAPIDO, WebConstants.VERTICALIZZAZIONE_SORTEGGIORAPIDO_CAMPO_DYN_SORTEGGIATA);
	    Verticalizzazioniparametri mostraSchedeProtocollate = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_SORTEGGIORAPIDO, WebConstants.VERTICALIZZAZIONE_SORTEGGIORAPIDO_MOSTRA_SCHEDE_PROT);
	    if (mostraSchedeProtocollate != null) {
		if (StringUtils.defaultIfEmpty(mostraSchedeProtocollate.getValore(), "0").equals("0")) {
		    if (StringUtils.isBlank(istanza.getNumeroprotocollo())) {
			isMostraSchede = false;
		    }
		}
	    }
	    if (campoDinamicoSorteggio != null) {
		String nomeCampo = campoDinamicoSorteggio.getValore();
		List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService.findByIstanzaAndNomeCampo(istanza, nomeCampo);
		if (istanzedyn2datis.size() > 0) {
		    for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datis) {
			if (StringUtils.isBlank(istanzedyn2dati.getValore())) {
			    isSorteggiata = false;
			    break;
			}
		    }
		} else {
		    isSorteggiata = false;
		}
	    }
	}
	boolean isModificaIntervento = false;
	String whyCanNotModifyIntervento = istanzeService.checkModificaIntervento(istanza);
	if (StringUtils.isBlank(whyCanNotModifyIntervento)) {
	    isModificaIntervento = true;
	}
	// §§§BEGIN§§§
	// Controllo che siano presenti le notifiche ASL, in tal caso deve essere presente per ogni indirizzo
	// configurato il link alla funzionalità
	List<NotificheAusl> listNotificheAsl = notificheAuslService.findAll(null, null);
	boolean isNotificheASLPresenti = false;
	if (!listNotificheAsl.isEmpty()) {
	    isNotificheASLPresenti = true;
	}
	model.addAttribute("isNotificheASLPresenti", isNotificheASLPresenti);
	// §§§END§§§
	List<Statiistanza> statiistanzaList = statiistanzaService.findBySoftware(ORMHelper.getSoftware());
	model.addAttribute("statiistanzaList", statiistanzaList);
	List<Azioni> azioniList = azioniService.findAll(null, null);
	model.addAttribute("azioniList", azioniList);
	List<Istanzestradario> istanzestradarios = istanzestradarioService.findByIstanza(istanza.getId().getCodice());
	for (Istanzestradario istanzestradario : istanzestradarios) {
	    //////////////////////////////////////////////////////////////////////////////////////////
	    // Il metodo sort di collection prende come argomento solo la list
	    // Trasformo il set il list
	    if (!istanzestradario.getIstanzemappalis().isEmpty()) {
		List<Istanzemappali> istanzemappalisDaOrdinare = new ArrayList<Istanzemappali>(istanzestradario.getIstanzemappalis());
		// Lo ordino con il comparator creato
		Collections.sort(istanzemappalisDaOrdinare, new IstanzeMappaliComparator());
		// Lo trasformo nuovamente in Set (dobbiamo ritrasformarlo in set perchè deve essere inserito all'interno 
		//dell'oggetto istanzestradario che come variabili ha un set di istanze mappali)
		Set<Istanzemappali> istanzemappalisOrdinati = new LinkedHashSet<Istanzemappali>();
		for (Istanzemappali istanzemappali : istanzemappalisDaOrdinare) {
		    istanzemappalisOrdinati.add(istanzemappali);
		}
		istanzestradario.setIstanzemappalis(istanzemappalisOrdinati);
	    }
	}
	////////////////////////////////////////////////////////////////////////////////////////// 
	model.addAttribute("istanzestradarios", istanzestradarios);
	model.addAttribute("isModificaIntervento", isModificaIntervento);
	model.addAttribute("whyCanNotModifyIntervento", whyCanNotModifyIntervento);
	model.addAttribute("isSorteggiata", isSorteggiata);
	model.addAttribute("isMostraSchede", isMostraSchede);
	if (istanza.getCreatoDaStc() != null) {
	    if (istanza.getCreatoDaStc().booleanValue()) {
		model.addAttribute("creataDaStc", Boolean.TRUE);
	    }
	}
	List<Domandestc> domandestcs = domandestcService.findByIstanza(istanza.getId().getCodice());
	if (domandestcs.size() > 0) {
	    for (Domandestc domandestc : domandestcs) {
		istanzeCommand.setCodiceIstanzaOnline(domandestc.getIdDomandamitt());
		break;
	    }
	}
	Responsabili userlogged = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	boolean isAbilitaBloccaOneri = userlogged.getFlagBloccaOneri() == null ? false : userlogged.getFlagBloccaOneri().booleanValue();
	model.addAttribute("isAbilitaBloccaOneri", Boolean.valueOf(isAbilitaBloccaOneri));
	boolean isCancellaIstanze = userlogged.getFlagCancellaistanze() == null ? false : userlogged.getFlagCancellaistanze().booleanValue();
	model.addAttribute("isCancellaIstanzePerOperatore", Boolean.valueOf(isCancellaIstanze));
	Chiusureistanza ci = chiusureistanzaService.findById(new PkId(istanza.getId().getCodice()));
	Integer codiceIstanza = istanza.getId().getCodice();
	int isIstanzeCollegate = istanzecollegateService.countIstanzeCollegateByIstanza(codiceIstanza);
	model.addAttribute("isIstanzeCollegate", isIstanzeCollegate > 0);
	boolean isOneriBloccati = false;
	if (ci != null) {
	    isOneriBloccati = ci.getOneri() == null ? false : ci.getOneri().booleanValue();
	}
	model.addAttribute("isOneriBloccati", Boolean.valueOf(isOneriBloccati));
	//Gestione della ricerca e inserimento di un richiedente
	Configurazione configurazione = configurazioneService.findById(new ConfigurazioneId(ORMHelper.getSoftware()));
	// di deafult è imposttao che possono essere mostrate sia persone fisiche che persone giuridiche
	model.addAttribute("configurazioneRichiedentepf", "");
	// Se flagRichiedentepf dell' Oggetto Configurazione ==1 richiedente può essere solo persona fisica
	// Mostra solo i soggetti che sono persone fisiche
	if (configurazione.getFlagRichiedentepf() != null && configurazione.getFlagRichiedentepf().equals(true)) {
	    model.addAttribute("configurazioneRichiedentepf", "F");
	}
	// Gestione del recupero dei tipi soggetti necessari per l'istanza (sono impostati all'interno dell'alebero dei procedimenti
	//scelto, seguendo la regola che il figli ereda anche quelli del padre ). Se non sono popolati tutti i soggetti collegati
	// richiesti, ritorna un warning
	istanzerichiedentiService.checkTipisoggettoRichiesti(alberoprocHelper, istanza.getId().getCodice());
	// Controllo se per il software sono configurati dei tipi soggetto che hanno il flag flagMostraDettIstanza==true
	// In tal caso in view dovrà essere presnete una sezione che mostra i soggetti collegati con tipi soggetto con
	// flagMostraDettIstanza==true
	List<Tipisoggetto> tipisoggettos = tipisoggettoService.findByflagMostraDettIstanza(true);
	boolean isTipiSoggMostraDettIstanza = false;
	if (!tipisoggettos.isEmpty()) {
	    isTipiSoggMostraDettIstanza = true;
	    // ricerco anche se sono presneti nell'istanza in esame
	    List<Istanzerichiedenti> istanzerichiedentis = istanzerichiedentiService
		    .findByIstanzaAndTiposoggeettoMostraInIstanza(istanza.getId().getCodice());
	    model.addAttribute("istanzerichiedentis", istanzerichiedentis);
	}
	model.addAttribute("isTipiSoggMostraDettIstanza", isTipiSoggMostraDettIstanza);
	//. Verifico se l'utente loggato ha il compito di assegnare ad un istruttore.
	boolean isUtenteDeveAssegnareIstanza = false;
	if (EntityUtils.getNestedProperty(istanza.getGruppiIstruttori(), "id.codice") != null
		&& EntityUtils.getNestedProperty(istanza.getIstruttore(), "id.codice") == null
		&& EntityUtils.getNestedProperty(istanza.getIstruttoreTemp(), "id.codice") == null
		&& getCurrentlyAuthenticatedUserDetails().getId().getCodice().equals(istanza.getResponsabileProcedimento().getId().getCodice())) {
	    isUtenteDeveAssegnareIstanza = true;
	}
	//. Verifico se all'utente loggato è stato assegnata come resp di istruttoria per una o più istanze.
	boolean isUtenteAssegnatoAdIstanza = false;
	if (EntityUtils.getNestedProperty(istanza.getGruppiIstruttori(), "id.codice") != null
		&& EntityUtils.getNestedProperty(istanza.getIstruttore(), "id.codice") == null
		&& EntityUtils.getNestedProperty(istanza.getIstruttoreTemp(), "id.codice") != null
		&& getCurrentlyAuthenticatedUserDetails().getId().getCodice().equals(istanza.getIstruttoreTemp().getId().getCodice())) {
	    isUtenteAssegnatoAdIstanza = true;
	}
	model.addAttribute("isUtenteDeveAssegnareIstanza", isUtenteDeveAssegnareIstanza);
	model.addAttribute("isUtenteAssegnatoAdIstanza", isUtenteAssegnatoAdIstanza);
	boolean isVerticalizzazioneGOOGLE_MAPSAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_GOOGLE_MAPS);
	model.addAttribute("isVerticalizzazioneGOOGLE_MAPSAttiva", isVerticalizzazioneGOOGLE_MAPSAttiva);
	// -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_MOSTRA_SOGG_COLL_ISTANZA, "0", request);
	// -----FINE GESTIONE CONFIGURAZIONE UTENTE----------------------
    }

    // ////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping
    public String insert(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Istanze entity = istanzeCommand.getEntity();
	if (istanzeCommand.getTipoInserimento().equals(TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_RAPIDO.value())) {
	    if (!StringUtils.defaultIfEmpty(request.getParameter("entity.responsabile.id.codice"), "").equals("")) {
		Integer codice = Integer.parseInt(request.getParameter("entity.responsabile.id.codice"));
		PkId codiceId = new PkId(codice);
		Responsabili responsabile = responsabiliService.findById(codiceId);
		istanzeCommand.getEntity().setResponsabile(responsabile);
	    } else {
		LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
		Responsabili responsabile = responsabiliService.findById(new PkId(userDetail.getCodiceResponsabile()));
		istanzeCommand.getEntity().setResponsabile(responsabile);
	    }
	} else {
	    if (!StringUtils.defaultIfEmpty(request.getParameter("entity.responsabile.id.codice"), "").equals("")) {
		Integer codice = Integer.parseInt(request.getParameter("entity.responsabile.id.codice"));
		PkId codiceId = new PkId(codice);
		Responsabili responsabili = responsabiliService.findById(codiceId);
		istanzeCommand.getEntity().setResponsabile(responsabili);
	    } else {
		istanzeCommand.getEntity().setResponsabile(null);
	    }
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.istruttore.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.istruttore.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    istanzeCommand.getEntity().setIstruttore(responsabili);
	} else {
	    istanzeCommand.getEntity().setIstruttore(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.responsabileProcedimento.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.responsabileProcedimento.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    istanzeCommand.getEntity().setResponsabileProcedimento(responsabili);
	} else {
	    istanzeCommand.getEntity().setResponsabileProcedimento(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.operatoreInCarico.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.operatoreInCarico.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    istanzeCommand.getEntity().setOperatoreInCarico(responsabili);
	} else {
	    istanzeCommand.getEntity().setOperatoreInCarico(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.tipisoggetto.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.tipisoggetto.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Tipisoggetto tipisoggetto = tipisoggettoService.findById(codiceId);
	    istanzeCommand.getEntity().setTipisoggetto(tipisoggetto);
	} else {
	    istanzeCommand.getEntity().setTipisoggetto(null);
	}
	fixMergeEntityProperty(istanzeCommand);
	populateLocalizzazioniForInsert(istanzeCommand, request);
	populateEndoForInsert(istanzeCommand, request);
	try {
	    istanzeService.insert(entity, TipoInserimento.fromValue(istanzeCommand.getTipoInserimento()));
	} catch (Exception e) {
	    if (e instanceof NumeroIstanzaUtilizzatoException) { // e instanceof BusinessValidationException
		Integer alberoprocCodice = null;
		if (EntityUtils.getNestedProperty(entity, "alberoproc.id.codice") != null) {
		    alberoprocCodice = entity.getAlberoproc().getId().getCodice();
		}
		String numeroIstanza = istanzeService.findProgressivoIstanza(alberoprocCodice, false);
		model.addAttribute("nuovoNumeroIstanza", numeroIstanza);
	    }
	    entity.setRichiedentestorico(null);
	    entity.setTitolarelegalestorico(null);
	    entity.setProfessionistastorico(null);
	    copyErrorsToBindingResult(result, istanzeCommand.getEntity(), true, e);
	    fixRenderEntityProperty(istanzeCommand);
	    prepareCreateModel(request, model);
	    istanzeCommand.setDisplayMode(IstanzeCommand.NEW);
	    return "istanze/form";
	}
	status.setComplete();
	if (istanzeCommand.getTipoInserimento() != null) {
	    if (istanzeCommand.getTipoInserimento().intValue() == TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_RAPIDO.value().intValue()) {
		return "redirect:inserimentorapido.htm?codice=" + entity.getId().getCodice() + "&ts_=" + System.currentTimeMillis();
	    }
	}
	try {
	    log.debug("ricalcolo started...");
	    RicalcoloMaxRequest req = new RicalcoloMaxRequest();
	    List<String> ricalcoloAreeIdL = new ArrayList<String>();
	    if (!StringUtils.isBlank(entity.getUuid())) {
		List<RicalcoloAreeIstanze> testateId = ricalcoloAreeIstanzeDAO.findRicalcoloInProgressIst(entity.getUuid());
		if (testateId != null && !testateId.isEmpty()) {
		    for (RicalcoloAreeIstanze testataId : testateId) {
			if (testataId != null) {
			    ricalcoloAreeIdL.add(testataId.getIdRicalcoloAree());
			}
		    }
		}
	    } else {
		log.debug("no uuid istanza populated");
	    }
	    req.setRicalcoloAreeIdL(ricalcoloAreeIdL);
	    RicalcoloMaxReqParams params = new RicalcoloMaxReqParams();
	    params.setToken(ORMHelper.getToken()); //Prendo questo
	    params.setSoftware(ORMHelper.getSoftware());
	    params.setType("singleistanza");
	    new IstanzeStrRicalcoloRestClient().ricalcolaArea(req, params);
	    log.debug("ricalcolo ended...");
	} catch (Exception e) {
	    log.error("Error in ricalcolo aree", e);
	    try {
		log.info("Inserting in event table");
		istanzeeventiService.insert("Errore sul ricalcolo aree: " + e.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null,
			entity);
		log.info("Insert executed");
	    } catch (Exception e1) {
		log.error("Error in insert eventi", e1); //va reso il meno bloccante possibile
	    }
	}
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String inserimentorapido(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	Istanze istanza = istanzeService.findById(new PkId(codice));
	checkAccessoInformazioni(istanza, false);
	Movimenti movimento = movimentiService.findMovimentoAvvioIstanza(istanza);
	model.addAttribute("tipoInserimento", TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_RAPIDO.value());
	model.addAttribute("movimentoAvvio", movimento);
	model.addAttribute("istanza", istanza);
	return "istanze/inserimentorapido";
    }

    @RequestMapping
    public void ajaxBloccaSbloccaOneri(@RequestParam("codiceIstanza") Integer codiceistanza, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws UnsupportedEncodingException, IOException {

	Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	checkAccessoInformazioni(istanza, true);
	Chiusureistanza ci = chiusureistanzaService.findById(new PkId(codiceistanza));
	try {
	    boolean blocca = false;
	    if (ci == null) {
		ci = new Chiusureistanza();
		ci.getId().setCodice(codiceistanza);
		ci.setOneri(Boolean.TRUE);
		chiusureistanzaService.insert(ci);
		response.getOutputStream().write("1".getBytes("UTF-8"));
		return;
	    } else {
		blocca = ci.getOneri() == null ? false : ci.getOneri().booleanValue();
		ci.setOneri(Boolean.valueOf(!blocca));
		chiusureistanzaService.update(ci);
		if (ci.getOneri().booleanValue() == true) {
		    response.getOutputStream().write("1".getBytes("UTF-8"));
		} else {
		    response.getOutputStream().write("0".getBytes("UTF-8"));
		}
		return;
	    }
	} catch (Exception e) {
	    String errore = getMessageFromBundle("03", null);
	    errore += ": " + e.getMessage();
	    response.getOutputStream().write(errore.getBytes("UTF-8"));
	    return;
	}
    }

    @SuppressWarnings("unchecked")
    private void populateEndoForInsert(IstanzeCommand istanzeCommand, HttpServletRequest request) {

	Set<Integer> codiciEndosInSession = (Set<Integer>) request.getSession().getAttribute(SET_ENDO_IN_SESSION);
	if (codiciEndosInSession != null) {
	    if (codiciEndosInSession.size() > 0) {
		Set<Istanzeprocedimenti> istanzeprocedimentis = new HashSet<Istanzeprocedimenti>();
		for (Integer codice : codiciEndosInSession) {
		    Inventarioprocedimenti endo = inventarioprocedimentiService.findById(new PkId(codice));
		    Istanzeprocedimenti istanzeprocedimenti = new Istanzeprocedimenti();
		    istanzeprocedimenti.setInventarioprocedimenti(endo);
		    istanzeprocedimenti.getId().setCodiceinventario(endo.getId().getCodice());
		    istanzeprocedimentis.add(istanzeprocedimenti);
		}
		istanzeCommand.getEntity().setIstanzeprocedimentis(istanzeprocedimentis);
	    }
	}
    }

    private void populateLocalizzazioniForInsert(IstanzeCommand command, HttpServletRequest request) {

	IstanzeFilter filter = command.getIstanzeFilter();
	if (filter != null) {
	    Istanzearee istanzearee = filter.getIstanzearee();
	    if (EntityUtils.getNestedProperty(istanzearee, "id.codicearea") != null) {
		Set<Istanzearee> istanzearees = new HashSet<Istanzearee>();
		istanzearees.add(istanzearee);
		command.getEntity().setIstanzearees(istanzearees);
	    }
	    Istanzestradario istanzestradario = filter.getIstanzestradario();
	    if (EntityUtils.getNestedProperty(istanzestradario, "stradario.id.codice") != null) {
		// §§§BEGIN§§§
		boolean isSitAttivo = false;
		// TODO VERIFICARE
		String codiceComune = filter.getComune().getCodicecomune();
		isSitAttivo = isVerticalizzazioneAttivaPerComune(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO, request, codiceComune);
		if (isSitAttivo) {
		    try {
			boolean isValido = sitService.effettuaValidazioneFormale(ORMHelper.getToken(), ORMHelper.getSoftware(), istanzestradario);
			istanzestradario.setValido(isValido);
		    } catch (Exception e) {
			log.error("Errore nella chiamata alla funzionalità effettuaValidazioneFormale: {}", e.getMessage());
		    }
		}
		// §§§END§§§
		Set<Istanzestradario> istanzestradarios = new HashSet<Istanzestradario>();
		Istanzemappali istanzemappali = filter.getIstanzemappali();
		String catasto = (String) EntityUtils.getNestedProperty(istanzemappali, "catasto.codice");
		if (StringUtils.isNotBlank(catasto)) {
		    if (IstanzestradarioController.checkMappaleInsert(istanzemappali)) {
			istanzestradario.getIstanzemappalis().add(istanzemappali);
		    }
		}
		istanzestradarios.add(istanzestradario);
		command.getEntity().setIstanzestradarios(istanzestradarios);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    private void populateEndo(IstanzeCommand istanzeCommand, HttpServletRequest request) {

	Set<Integer> codiciEndosInSession = (Set<Integer>) request.getSession().getAttribute(SET_ENDO_IN_SESSION);
	if (codiciEndosInSession != null) {
	    if (codiciEndosInSession.size() > 0) {
		Set<Istanzeprocedimenti> istanzeprocedimentis = new HashSet<Istanzeprocedimenti>();
		for (Integer codice : codiciEndosInSession) {
		    Inventarioprocedimenti endo = inventarioprocedimentiService.findById(new PkId(codice));
		    Istanzeprocedimenti istanzeprocedimenti = new Istanzeprocedimenti();
		    istanzeprocedimenti.setInventarioprocedimenti(endo);
		    istanzeprocedimenti.getId().setCodiceinventario(endo.getId().getCodice());
		    istanzeprocedimentis.add(istanzeprocedimenti);
		}
		istanzeCommand.getEntity().setTransientIstanzeprocedimentis(istanzeprocedimentis);
	    }
	}
    }

    private void populateLocalizzazioni(IstanzeCommand command) {

	IstanzeFilter filter = command.getIstanzeFilter();
	if (filter != null) {
	    Istanzestradario istanzestradario = filter.getIstanzestradario();
	    if (EntityUtils.getNestedProperty(istanzestradario, "stradario.id.codice") != null) {
		Set<Istanzestradario> istanzestradarios = new HashSet<Istanzestradario>();
		istanzestradarios.add(istanzestradario);
		command.getEntity().setTransientIstanzeStradario(istanzestradarios);
	    }
	    Istanzemappali istanzemappali = filter.getIstanzemappali();
	    String catasto = (String) EntityUtils.getNestedProperty(istanzemappali, "catasto.codice");
	    if (StringUtils.isNotBlank(catasto)) {
		Set<Istanzemappali> istanzemappalis = new HashSet<Istanzemappali>();
		istanzemappalis.add(istanzemappali);
		command.getEntity().setTransientIstanzemappalis(istanzemappalis);
	    }
	}
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.responsabile.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.responsabile.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    istanzeCommand.getEntity().setResponsabile(responsabili);
	} else {
	    istanzeCommand.getEntity().setResponsabile(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.istruttore.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.istruttore.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    istanzeCommand.getEntity().setIstruttore(responsabili);
	} else {
	    istanzeCommand.getEntity().setIstruttore(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.responsabileProcedimento.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.responsabileProcedimento.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    istanzeCommand.getEntity().setResponsabileProcedimento(responsabili);
	} else {
	    istanzeCommand.getEntity().setResponsabileProcedimento(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.operatoreInCarico.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.operatoreInCarico.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    istanzeCommand.getEntity().setOperatoreInCarico(responsabili);
	} else {
	    istanzeCommand.getEntity().setOperatoreInCarico(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.richiedente.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.richiedente.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Anagrafe richiedente = anagrafeService.findById(codiceId);
	    istanzeCommand.getEntity().setRichiedente(richiedente);
	} else {
	    istanzeCommand.getEntity().setRichiedente(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.titolarelegale.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.titolarelegale.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Anagrafe richiedente = anagrafeService.findById(codiceId);
	    istanzeCommand.getEntity().setTitolarelegale(richiedente);
	} else {
	    istanzeCommand.getEntity().setTitolarelegale(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.professionista.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.professionista.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Anagrafe richiedente = anagrafeService.findById(codiceId);
	    istanzeCommand.getEntity().setProfessionista(richiedente);
	} else {
	    istanzeCommand.getEntity().setProfessionista(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.tipisoggetto.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.tipisoggetto.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Tipisoggetto tipisoggetto = tipisoggettoService.findById(codiceId);
	    istanzeCommand.getEntity().setTipisoggetto(tipisoggetto);
	} else {
	    istanzeCommand.getEntity().setTipisoggetto(null);
	}
	// SessionDetails sd = getSessionDetails(request);
	// String token = sd.getToken();
	Istanze entity = istanzeCommand.getEntity();
	checkAccessoInformazioni(entity, true);
	try {
	    fixMergeEntityProperty(istanzeCommand);
	    populateLocalizzazioni(istanzeCommand);
	    populateEndo(istanzeCommand, request);
	    istanzeService.update(entity);
	    //IstanzeFilter filter = istanzeCommand.getIstanzeFilter();
	    if (istanzeCommand.getIstanzearee() != null) {
		Istanzearee istanzearee = istanzeCommand.getIstanzearee();
		istanzearee.setIstanza(istanzeCommand.getEntity());
		istanzeareeService.updateIstanzaareaPrimaria(istanzearee);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeCommand.getEntity(), true, e);
	    fixRenderEntityProperty(istanzeCommand);
	    prepareViewModel(model, request, entity, istanzeCommand);
	    return "istanze/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) throws Exception {

	//FIXME se chiamo questo metodo dopo che è tornata un'eccezione si stronca hibernate.
	String redirectautorizzaCancellazione = autorizzaCancellazioneDato(WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_ISTANZE,
		"redirect:delete.htm", "../istanze/view.htm?codice=" + istanzeCommand.getEntity().getId().getCodice(), request);
	if (StringUtils.isNotBlank(redirectautorizzaCancellazione)) {
	    return redirectautorizzaCancellazione;
	}
	Responsabili userlogged = getCurrentlyAuthenticatedUserDetails();
	boolean isCancellaIstanze = userlogged.getFlagCancellaistanze() == null ? false : userlogged.getFlagCancellaistanze().booleanValue();
	if (!isCancellaIstanze) {
	    String messaggioErrore = getMessageFromBundle("javascript.alert.operatore_non_puo_cancellare_pratica", null);
	    throw new SecurityException(messaggioErrore);
	}
	Istanze entity = istanzeCommand.getEntity();
	entity = istanzeService.findById(entity.getId());
	checkAccessoInformazioni(entity, true);
	try {
	    String descrizioneIstanza = entity.toString();
	    String responsabile = (String) EntityUtils.getNestedProperty(userlogged, "responsabile");
	    istanzeService.delete(entity);
	    LoggerCancellazioni.logCancellazioneIstanza(responsabile, descrizioneIstanza);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeCommand.getEntity(), true, e);
	    entity = istanzeService.findById(entity.getId());
	    istanzeCommand.setEntity(entity);
	    fixRenderEntityProperty(istanzeCommand);
	    prepareViewModel(model, request, entity, istanzeCommand);
	    return "istanze/form";
	} finally {
	    cleanCancellazioniAttribute(WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_ISTANZE, request);
	}
	status.setComplete();
	return "redirect:searchIstanze.htm?software=" + ORMHelper.getSoftware();
    }

    // ////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping
    public String search(HttpServletRequest request, Model model) {

	IstanzeCommand command = new IstanzeCommand();
	request.getSession().removeAttribute("istanzeFilter");
	request.getSession().removeAttribute("istanzeInseriteList");
	IstanzeFilter filter = new IstanzeFilter();
	command.setIstanzeFilter(filter);
	model.addAttribute("istanzeCommand", command);
	return "istanze/search";
    }

    @RequestMapping
    public String list(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand command, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	IstanzeFilter filter = command.getIstanzeFilter();
	if (filter.getTipoMovimento().getId().getTipomovimento() == null || filter.getTipoMovimento().getId().getTipomovimento().equals("")) {
	    result.reject("form.istanze.movimentoDaInserire.alert", "Attenzione! Selezionare un movimento da inseirire.");
	}
	if (filter.getDallaData() != null && filter.getAllaData() != null) {
	    if (filter.getDallaData().after(filter.getAllaData())) {
		result.reject("form.istanze.data.alert", "Attenzione! Le date inserite non sono corrette.");
	    }
	}
	if (result.hasErrors()) {
	    return "istanze/search";
	}
	request.getSession().setAttribute("istanzeFilter", filter);
	List<Istanze> istanzeList = istanzeService.findIstanzePerInserimentoMassivo(filter);
	command.setIstanzeList(istanzeList);
	model.addAttribute("istanzeCommand", command);
	return "istanze/list";
    }

    @RequestMapping
    public String scollegaAttivita(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Istanze entity = istanzeCommand.getEntity();
	try {
	    entity = istanzeService.bindDomainObject(entity, PkId.class, "id.codice");
	    checkAccessoInformazioni(entity, true);
	    this.attivitaIstanzeService.scollegaIstanze(entity);
	} catch (ScollegamentoUnicaIstanzaException ex) {
	    String messaggio = getMessageFromBundle(ex.getMessage(), new Object[] { entity.getNumeroistanza(), ex.getDenominazioneAttivita() });
	    copyErrorsToBindingResult(result, istanzeCommand.getEntity(), true, new RuntimeException(messaggio));
	    entity = istanzeService.bindDomainObject(entity, PkId.class, "id.codice");
	    istanzeCommand.setEntity(entity);
	    fixRenderEntityProperty(istanzeCommand);
	    prepareViewModel(model, request, entity, istanzeCommand);
	    return "istanze/form";
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeCommand.getEntity(), true, e);
	    entity = istanzeService.bindDomainObject(entity, PkId.class, "id.codice");
	    istanzeCommand.setEntity(entity);
	    fixRenderEntityProperty(istanzeCommand);
	    prepareViewModel(model, request, entity, istanzeCommand);
	    return "istanze/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String creaAttivita(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Istanze entity = istanzeCommand.getEntity();
	try {
	    String skip = request.getParameter("skipCheckEsistenzaAttivita");
	    entity = istanzeService.bindDomainObject(entity, PkId.class, "id.codice");
	    checkAccessoInformazioni(entity, true);
	    boolean skipControlloEsistenza = !StringUtils.defaultIfEmpty(skip, "").equals("");
	    iAttivitaService.creaAttivita(entity, skipControlloEsistenza);
	} catch (RestrizioneAttivitaEsistenteException e) {
	    model.addAttribute("TipoEccezioneCreazioneAttivita", "RestrizioneAttivitaEsistenteException");
	    model.addAttribute("DescrizioneEccezioneCreazioneAttivita", e.getMessage());
	    model.addAttribute("forzaCreazioneAttivita", Boolean.TRUE);
	    entity = istanzeService.bindDomainObject(entity, PkId.class, "id.codice");
	    istanzeCommand.setEntity(entity);
	    fixRenderEntityProperty(istanzeCommand);
	    prepareViewModel(model, request, entity, istanzeCommand);
	    return "istanze/form";
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeCommand.getEntity(), true, e);
	    entity = istanzeService.bindDomainObject(entity, PkId.class, "id.codice");
	    istanzeCommand.setEntity(entity);
	    fixRenderEntityProperty(istanzeCommand);
	    prepareViewModel(model, request, entity, istanzeCommand);
	    return "istanze/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String infoView(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Istanze istanza = istanzeService.findById(id);
	checkAccessoInformazioni(istanza, false);
	IstanzeCommand command = new IstanzeCommand();
	command.setDisplayMode(IstanzeCommand.VIEW);
	command.setEntity(istanza);
	fixRenderEntityProperty(command);
	model.addAttribute("istanzeCommand", command);
	prepareViewModel(model, request, istanza, command);
	return "istanze/info";
    }

    @RequestMapping
    public String visualizzaRepliche(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) {

	PkId idIstanza = new PkId(codiceIstanza);
	Istanze istanza = istanzeService.findById(idIstanza);
	if (istanza == null) {
	    log.error("visualizzaRepliche: l'operatore [{}] ha cercato di recuperare informazioni sull'instanza [{}]",
		    getCurrentlyAuthenticatedUserDetails().getResponsabile(), idIstanza);
	    throw new SecurityException("L'istanza con codice=" + codiceIstanza + " non è stata trovata");
	}
	checkAccessoInformazioni(istanza, false);
	Istanze istanzaPadre = istanzeService.isReplicata(istanza);
	List<Istanze> istanzeFiglies = istanzereplicateService.findIstanzeReplicate(istanzaPadre);
	model.addAttribute("istanzaPadre", istanzaPadre);
	model.addAttribute("istanzeFiglies", istanzeFiglies);
	return "istanze/visualizzarepliche";
    }

    @RequestMapping
    public String creaReplicheView(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId idIstanza = new PkId(codiceIstanza);
	Istanze istanza = istanzeService.findById(idIstanza);
	if (istanza == null) {
	    log.error("creaReplicheView: l'operatore [{}] ha cercato di recuperare informazioni sull'instanza [{}]",
		    getCurrentlyAuthenticatedUserDetails().getResponsabile(), idIstanza);
	    throw new SecurityException("L'istanza con codice=" + codiceIstanza + " non è stata trovata");
	}
	checkAccessoInformazioni(istanza, false);
	model.addAttribute("istanzaPadre", istanza);
	return "istanze/crearepliche";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String creaRepliche(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("isGestisciAttivita") boolean isGestisciAttivita,
	    Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId idIstanza = new PkId(codiceIstanza);
	Istanze istanza = istanzeService.findById(idIstanza);
	if (istanza == null) {
	    log.error("creaReplicheView: l'operatore [{}] ha cercato di recuperare informazioni sull'instanza [{}]",
		    getCurrentlyAuthenticatedUserDetails().getResponsabile(), idIstanza);
	    throw new SecurityException("L'istanza con codice=" + codiceIstanza + " non è stata trovata");
	}
	checkAccessoInformazioni(istanza, true);
	List<Integer> codiciIntervento = new ArrayList<Integer>();
	Enumeration<String> en = request.getParameterNames();
	while (en.hasMoreElements()) {
	    String paramName = en.nextElement();
	    if (paramName.startsWith("scId_")) {
		try {
		    if (StringUtils.isNotBlank(request.getParameter(paramName))) {
			codiciIntervento.add(Integer.valueOf(request.getParameter(paramName)));
		    }
		} catch (Exception e) {
		    // non fa niente
		}
	    }
	}
	try {
	    istanzeManager.creaRepliche(istanza, codiciIntervento, isGestisciAttivita);
	} catch (Exception e) {
	    log.error("Errore in creazione delle repliche: {}", e);
	    String errorMessage = "";
	    if (e instanceof BusinessValidationException || e instanceof EntityValidationException) {
		List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
		for (InvalidValue invalidValue : ivs) {
		    errorMessage += "<br />[" + invalidValue.getBeanClass() + "],[" + invalidValue.getPropertyPath() + "]," +
				    invalidValue.getMessage();
		}
	    } else {
		errorMessage = e.getMessage();
	    }
	    log.error("Errore in creazione delle repliche: messaggio errore", errorMessage);
	    List<String> msgs = new ArrayList<String>();
	    msgs.add("Operazione non avvenuta correttamente: " + errorMessage);
	    FlashMessages.setWarnings(msgs);
	    return "redirect:creaReplicheView.htm?codiceIstanza=" + codiceIstanza + "&_ts=" + System.currentTimeMillis();
	}
	return "redirect:visualizzaRepliche.htm?codiceIstanza=" + codiceIstanza;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String infoUpdate(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Istanze entity = istanzeCommand.getEntity();
	String codiceStato = (String) EntityUtils.getNestedProperty(entity.getChiusura(), "id.codicestato");
	String azione = entity.getAzione();
	Istanze istanza = istanzeService.findById(new PkId(entity.getId().getCodice()));
	checkAccessoInformazioni(istanza, true);
	try {
	    fixMergeEntityProperty(istanzeCommand);
	    MovimentiHelper mh = istanzeService.updateStatoIstanza(istanza, codiceStato);
	    istanza.setAzione(azione);
	    istanzeService.update(istanza);
	    if (mh != null && mh.isRilascioAutorizzazione()) {
		status.setComplete();
		return "redirect:../autorizzazioni/createAutorizzazione.htm?codiceIstanza=" + istanza.getId().getCodice() + "&codiceMovimento=" +
		       mh.getMovimento().getId().getCodice() + "&codiceStato=" + codiceStato;
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeCommand.getEntity(), e);
	    fixRenderEntityProperty(istanzeCommand);
	    prepareViewModel(model, request, entity, istanzeCommand);
	    return "istanze/info";
	}
	status.setComplete();
	return "redirect:infoView.htm?codice=" + entity.getId().getCodice() + "&status_msg=02";
    }

    /**
     * 
     * @param model
     * @param command
     * @param result
     * @param status
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public String listIstanze(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand command, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	IstanzeFilter filter = command.getIstanzeFilter();
	// validazione filter
	if (result.hasErrors()) {
	    return "istanze/searchIstanze";
	}
	log.debug("command.getTipoRicerca() {}", command.getTipoRicerca());
	log.debug("command.getCodiceIstanzaAccessoAtti()) {}", command.getCodiceIstanzaAccessoAtti());
	// ////GESTIONE DELLA LISTA DELLE ISTANZE TROVATE IN SEGUITO ALLA RICERCA PER LA FUNZIONALITA DI COLLEGAMENTO
	// TRA ISTANZE////////
	// //--------------------------------------------------START-------------------------------------------------------------///////
	// 1. Se tipo ricerca = WebConstants.SEARCH_ISTANZE_COLLEGATE 		: ritorna la lista delle istanze e permetterà di selezionare 1 o n istanze da collegare all'istanza madre.
	// 2. Se tipo ricerca = WebConstants.SEARCH_ISTANZE_ACCESSO_ATTI 	: ritorna la lista delle istanze e permetterà di selezionare 1 o n istanze da collegare alla tabella 
	//    istanze atti D a partire dall'istanza salvata in istanze accesso atti T.
	// 3. Se tipo ricerca = WebConstants.SEARCH_ISTANZE 			: effettua ricerca delle istanze.
	// //--------------------------------------------------------------------------------------------------------------------////////
	// //--------------------------------------------------------------------------------------------------------------------////////
	log.debug("command.getTipoRicerca() == WebConstants.SEARCH_ISTANZE_ACCESSO_ATTI ==> {}",
		command.getTipoRicerca().equals(WebConstants.SEARCH_ISTANZE_ACCESSO_ATTI));
	log.debug("command.getTipoRicerca() == WebConstants.SEARCH_ISTANZE_COLLEGATE  ==> {}",
		(command.getTipoRicerca().equals(WebConstants.SEARCH_ISTANZE_COLLEGATE)));
	if (command.getTipoRicerca() != null
		&& (command.getTipoRicerca().equals(WebConstants.SEARCH_ISTANZE_COLLEGATE)
			|| command.getTipoRicerca().equals(WebConstants.SEARCH_ISTANZE_ACCESSO_ATTI))
		|| command.getTipoRicerca().equals(WebConstants.SEARCH_ISTANZE_ACCESSO_ANAGRAFE_TRIBUTARIA)) {
	    // Il parametro IsCollegamentoPrecedente indica che l'istanza dovrà essere collegata come precedente e non come successiva.
	    Integer codiceIstanzaDaConfigurare = null;
	    if (EntityUtils.getNestedProperty(command.getIstanzaDaConfigurare(), "id.codice") != null) {
		codiceIstanzaDaConfigurare = command.getIstanzaDaConfigurare().getId().getCodice();
	    }
	    GenerateTable<IstanzeListHelper> istanzeCollegateTable = new IstanzeCollegateTable(filter, codiceIstanzaDaConfigurare,
		    command.getIsCollegamentoPrecedente(), command.getTipoRicerca());
	    String htmlTable = istanzeCollegateTable.createJMesaList(request, response, "label.lista_istanze", "istanze_collegate_id", false);
	    if (htmlTable == null) {
		return null;
	    }
	    model.addAttribute("htmltable", htmlTable);
	    if (command.getTipoRicerca().equals(WebConstants.SEARCH_ISTANZE_COLLEGATE)) {
		model.addAttribute("istanzaDaConfigurare", command.getIstanzaDaConfigurare());
		model.addAttribute("codiceistanzaPerViewInfo", command.getIstanzaDaConfigurare().getId().getCodice());
	    } else if (command.getTipoRicerca().equals(WebConstants.SEARCH_ISTANZE_ACCESSO_ATTI)) {
		model.addAttribute("codiceIstanzaAccessoAtti", command.getCodiceIstanzaAccessoAtti());
		IstanzeAccessoAttiT iaat = istanzeAccessoAttiTService.findById(new PkId(command.getCodiceIstanzaAccessoAtti()));
		model.addAttribute("codiceistanzaPerViewInfo", iaat.getIstanze().getId().getCodice());
	    } else if (command.getTipoRicerca().equals(WebConstants.SEARCH_ISTANZE_ACCESSO_ANAGRAFE_TRIBUTARIA)) {
		model.addAttribute("codiceGruppoAnagrafetributaria", command.getCodiceGruppoAnagrafetributaria());
	    }
	    model.addAttribute("_tipoRicerca", command.getTipoRicerca());
	    log.debug(" istanze/listIstanzePercollegamento");
	    return "istanze/listIstanzePercollegamento";
	}
	log.debug("listIstanze");
	// //--------------------------------------------------------------------------------------------------------------------////////
	// //--------------------------------------------------------------------------------------------------------------------////////
	// //---------------------------------------------------END--------------------------------------------------------------////////
	// //--------------------------------------------------------------------------------------------------------------------////////
	// //--------------------------------------------------------------------------------------------------------------------////////
	// IL FILTRO VIENE PRESO DALLA SESSIONE PER RICOSTRUIRE LA RICERCA NEI VARI HISTORY BACK
	if (request.getSession().getAttribute(ISTANZE_FILTER_IN_SESSION) == null) {
	    request.getSession().setAttribute(ISTANZE_FILTER_IN_SESSION, filter);
	    Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	    if (StringUtils.defaultString(responsabile.getAmministratore(), "0").equals("0")
		    && StringUtils.defaultString(responsabile.getAmministratoresoftware(), "0").equals("0")) {
		filter.setUtenteLoggato(responsabile);
	    }
	} else {
	    filter = (IstanzeFilter) request.getSession().getAttribute(ISTANZE_FILTER_IN_SESSION);
	    command.setIstanzeFilter(filter);
	}
	if (leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISISTANZA, "1", request).equals("1")) {
	    if (StringUtils.isNotBlank(filter.getNumeroistanza())) {
		List<IstanzeListHelper> istanzeList = istanzeService.findIstanzeListHelperByFilter(filter, 0, 2);
		if (istanzeList.size() == 1) {
		    return "redirect:view.htm?codice=" + istanzeList.get(0).getCodiceistanza().intValue();
		}
	    }
	}
	// valori booleani che verificano se esiste o no rispettivamentente un record con 
	// popolato tipologia istanza e tipo archivio pratiche
	// Se non sono lavorizzate non devono essere mostrate dalla lista	
	boolean isTipologiaistanza = tipologiaistanzaService.existsRecords();
	boolean isArchiviopratiche = tipiarchivioistanzeService.existsRecords();
	boolean isCartograficoAttivo = this.cartograficoService.getInfoConnettore().getUtilizzo().getIstanze().getElenco();
	GenerateTable<IstanzeListHelper> istanzeTable = new IstanzeHelperTable(filter, responsabiliService, configurazioneutenteService,
		userSecurityService, isArchiviopratiche, isTipologiaistanza);
	String htmlTable = istanzeTable.createJMesaList(request, response, "label.lista_istanze", "istanze_id", true);
	if (htmlTable == null) {
	    return null;
	}
	setListPageAttributes(model, request);
	model.addAttribute("htmltable", htmlTable);
	model.addAttribute("istanzeCommand", command);
	model.addAttribute("cartograficoAttivo", isCartograficoAttivo);
	return "istanze/listIstanze";
    }

    @RequestMapping
    public String listIstanzeDaAssegnare(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	command.getIstanzeFilter().setIsPraticheDaAssegnareAdIstruttore(true);
	command.getIstanzeFilter().setUtenteLoggato(getCurrentlyAuthenticatedUserDetails());
	return listIstanze(model, command, result, status, request, response);
    }

    @RequestMapping
    public String listIstanzeDaAccettare(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	command.getIstanzeFilter().setIsPraticheDaAccettareComeIstruttore(true);
	command.getIstanzeFilter().setUtenteLoggato(getCurrentlyAuthenticatedUserDetails());
	return listIstanze(model, command, result, status, request, response);
    }

    @RequestMapping
    public String listIstanzeInWarning(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	command.getIstanzeFilter().setIstanzeInWarning(Boolean.TRUE);
	command.getIstanzeFilter().setUtenteLoggato(getCurrentlyAuthenticatedUserDetails());
	return listIstanze(model, command, result, status, request, response);
    }

    private void setListPageAttributes(Model model, HttpServletRequest request) {

	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISPREFERENZE_DIV, "0", request);
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISAUTORIZZAZIONI_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISAUTORIZZAZIONI, "1", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISLINKDOCISTANZA_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISLINKDOCISTANZA, "1", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISCODPRATEL_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISCODPRATEL, "1", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISNUMOPERATOREINCARICO_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISNUMOPERATOREINCARICO, "1", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISNUMPROTOCOLLO_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISNUMPROTOCOLLO, "1", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISDATAPROTOCOLLO_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISDATAPROTOCOLLO, "0", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISTIPOLOGIAISTANZA_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISTIPOLOGIAISTANZA, "0", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISRICHIEDENTE_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISRICHIEDENTE, "1", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISRICHIEDENTESTORICO_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISRICHIEDENTESTORICO, "1", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISOPERATORE_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISOPERATORE, "0", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISRESPPROC_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISRESPPROC, "0", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISISTRUTTORE_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISISTRUTTORE, "0", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISDENOMINAZIONEATTIVITA_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISDENOMINAZIONEATTIVITA, "1", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISLOCALIZZAZIONE_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISLOCALIZZAZIONE, "1", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISINDIRIZZIISTANZA_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISINDIRIZZIISTANZA, "0", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISENDOPROCEDIMENTI_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISENDOPROCEDIMENTI, "0", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISINTERVENTO_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISINTERVENTO, "1", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISPROCEDURA_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISPROCEDURA, "0", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISLAVORI_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISLAVORI, "0", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISLAVORIESTESI_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISLAVORIESTESI, "0", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISARCHIVIO_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISARCHIVIO, "0", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISPOSIZIONEARCHIVIO_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISPOSIZIONEARCHIVIO, "1", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISSORTEGGIATE_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISSORTEGGIATE, "0", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISSTATOISTANZA_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISSTATOISTANZA, "0", request)));
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISCOMUNE_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISCOMUNE, "0", request)));
    }

    private void setInserimentoRapidoPageAttributes(Model model, HttpServletRequest request) {

	gestisciParametroConfigurazioneUtente(WebConstants.ISTANZE_INSERIMENTO_RAPIDO_MOSTRATUTTI, "0", request);
    }

    private String toCheckedString(String valore) {

	String result = "";
	if (StringUtils.defaultIfEmpty(valore, "0").equalsIgnoreCase("1")) {
	    result = " checked ";
	}
	return result;
    }

    @RequestMapping
    public String listDaGraduatoria(@RequestParam("codiceGraduatoria") Integer codiceGraduatoria,
	    @RequestParam("codiceMovimento") String codiceTipoMovimento, @RequestParam("soggettoMovimento") String soggettoMovimento,
	    HttpServletRequest request, Model model) {

	IstanzeCommand command = new IstanzeCommand();
	TipimovimentoId tmId = new TipimovimentoId();
	tmId.setTipomovimento(codiceTipoMovimento);
	Tipimovimento tipoMovimento = tipiMovimentoService.findById(tmId);
	IstanzeFilter filter = new IstanzeFilter();
	filter.setTipoMovimento(tipoMovimento);
	command.setIstanzeFilter(filter);
	command.setSoggettoMovimento(soggettoMovimento);
	List<Istanze> istanzeList = istanzeService.findIstanzeDaGraduatoria(codiceGraduatoria, codiceTipoMovimento, soggettoMovimento);
	command.setIstanzeList(istanzeList);
	model.addAttribute("istanzeCommand", command);
	model.addAttribute("codiceGraduatoria", codiceGraduatoria);
	return "istanze/list";
    }

    @RequestMapping
    public String resetInsertAttribute(Model model, HttpServletRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("Chiamata a resetInsertAttribute.");
	}
	request.getSession().removeAttribute(INSERT_MOVIMENTI_ATTR);
	if (log.isDebugEnabled()) {
	    log.debug("Chiamata a resetInsertAttribute: attributo rimosso");
	}
	return null;
    }

    @RequestMapping
    public synchronized String insertMovimenti(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("Inserimento movimenti...SessionId={}", request.getSession().getId());
	    log.debug("Inserimento movimenti...remoteAddr={}", request.getRemoteAddr());
	}
	String sessInserting = (String) request.getSession().getAttribute(INSERT_MOVIMENTI_ATTR);
	if (sessInserting == null || sessInserting.equals("")) {
	    request.getSession().setAttribute(INSERT_MOVIMENTI_ATTR, "TRUE");
	    request.getSession().removeAttribute("istanzeInseriteList");
	    request.getSession().removeAttribute("istanzeNonInseriteList");
	    request.getSession().removeAttribute("erroriList");
	    List<Istanze> istanzeList = command.getIstanzeList();
	    List<Istanze> istanzeInseriteList = new ArrayList<Istanze>();
	    List<Istanze> istanzeNonInseriteList = new ArrayList<Istanze>();
	    List<String> erroriList = new ArrayList<String>();
	    ProgressBarUtil progressBar = new ProgressBarUtil(istanzeList.size());
	    Calendar oggi = GregorianCalendar.getInstance();
	    for (Istanze istanza : istanzeList) {
		progressBar.updateProgressBarValue(request);
		if (istanza.isTransientCheckMov()) {
		    Movimenti movimento = new Movimenti();
		    movimento.setIstanza(istanza);
		    movimento.setTipomovimento(command.getIstanzeFilter().getTipoMovimento());
		    movimento.setData(oggi.getTime());
		    movimento.setDatainserimento(oggi.getTime());
		    movimento.setResponsabile(getCurrentlyAuthenticatedUserDetails());
		    if (log.isDebugEnabled()) {
			log.debug("Sto inserendo il movimento...");
		    }
		    try {
			try {
			    movimentiManager.insert(movimento, BooleanUtils.toBoolean(istanza.isTransientCheckProtMov()));
			    if (log.isDebugEnabled()) {
				log.debug("Movimento inserito per l'istanza codIstanza {}", istanza.getId());
				List<String> messaggi = FlashMessages.getWarnings();
				if (messaggi.size() > 0) {
				    String errore = "";
				    for (String string : messaggi) {
					errore = errore.concat(string).concat("<br/>");
				    }
				    erroriList.add("Errore durante la protocollazione del movimento dell'istanza numero " +
						   istanza.getNumeroistanza() + ". Errore = [" + errore + "]");
				    FlashMessages.removeWarnings();
				}
			    }
			    istanza.setTransientMovimento(movimento);
			    istanzeInseriteList.add(istanza);
			} catch (Exception e) {
			    // inserimento fallito
			    istanzeNonInseriteList.add(istanza);
			    erroriList.add("Errore durante l'inserimento del movimento dell'istanza numero " + istanza.getNumeroistanza());
			} finally {
			    progressBar.removeProgressBarValue(request);
			}
		    } catch (RuntimeException e) {
			result.reject("", e.getMessage());
			model.addAttribute("error", "error");
			model.addAttribute("istanzeCommand", command);
			return "istanze/list";
		    }
		}
	    }
	    if (log.isDebugEnabled()) {
		log.debug("Inserimento movimenti...done!");
	    }
	    if (log.isDebugEnabled()) {
		log.debug("Inserimento movimenti...redirect");
	    }
	    command.setIstanzeList(istanzeInseriteList);
	    request.getSession().setAttribute("istanzeInseriteList", istanzeInseriteList);
	    request.getSession().setAttribute("istanzeNonInseriteList", istanzeNonInseriteList);
	    request.getSession().setAttribute("erroriList", erroriList);
	    String urlRedirect = "redirect:result.htm?codiceGraduatoria=" + request.getParameter("codiceGraduatoria") + "&soggettoMovimento=" +
				 request.getParameter("soggettoMovimento") + "&codiceTipoMovimento=" +
				 command.getIstanzeFilter().getTipoMovimento().getId().getTipomovimento();
	    if (erroriList.isEmpty()) {
		urlRedirect += "&status_msg=01";
	    } else {
		urlRedirect += "&status_msg=03";
	    }
	    if (log.isDebugEnabled()) {
		log.debug("Inserimento movimenti...redirect done!");
	    }
	    return urlRedirect;
	} else {
	    return "executionisrunning";
	}
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String result(Model model, @RequestParam("codiceGraduatoria") Integer codiceGraduatoria,
	    @RequestParam("codiceTipoMovimento") String codiceTipoMovimento, @RequestParam("soggettoMovimento") String soggettoMovimento,
	    HttpServletRequest request) {

	IstanzeCommand command = new IstanzeCommand();
	IstanzeFilter filter = (IstanzeFilter) request.getSession().getAttribute("istanzeFilter");
	if (filter == null) {
	    filter = new IstanzeFilter();
	}
	command.setIstanzeFilter(filter);
	command.setSoggettoMovimento(soggettoMovimento);
	List<Istanze> istanzeInseriteList = (List<Istanze>) request.getSession().getAttribute("istanzeInseriteList");
	command.setIstanzeList(istanzeInseriteList);
	TipimovimentoId tmId = new TipimovimentoId();
	tmId.setTipomovimento(codiceTipoMovimento);
	Tipimovimento tipoMovimento = tipiMovimentoService.findById(tmId);
	filter.setTipoMovimento(tipoMovimento);
	model.addAttribute("codiceGraduatoria", codiceGraduatoria);
	model.addAttribute("istanzeCommand", command);
	return "istanze/result";
    }

    /**
     * <pre>
     * 
     * @param tipoRicerca
     *            : parametro non obbligatorio, lo uso per specificare se: 1. La chiamata viene da ricerca istanza (
     *            tipoRicerca== o null) [WebConstants.SEARCH_ISTANZE] 2. La chiamata viene da ricerca istanza per
     *            funzionalità istanze collegate ( tipoRicerca== 1) [ISTANZE_COLLEGATE] 3. La chiamata viene da ricerca
     *            istanza per funzionalità accesso agli atti istanza ( tipoRicerca== 2) [SEARCH_ISTANZE_ACCESSO_ATTI]
     * 
     * @param collegamentoPrecedente
     *            : il parametro è presente ed uguale true nel caso in cui stiamo facendo un collegamento all'indietro.
     *            Cioè l'istanza scelta verrà collegata come precedente alla pratica di partenza e non come successiva
     * @id_ricerca : utilizzato per gestire il salvataggio e recupero delle ricerche salvate
     * @indice_combo : utilizzato per gestire l'impostazione dei filtri dati dinamici nella pagina di ricerca
     * @codiceIstanzaAccessoAtti : parametro utilizzato per la funzionalità accesso agli atti, per collegare le istanze
     *                           al fascicolo (ISTANZE_ACCESSO_ATTI_T)
     * @param request
     * @param model
     * @return
     * 
     *         <pre>
     */
    @RequestMapping
    public String searchIstanze(@RequestParam(value = "tipoRicerca", required = false) Integer tipoRicerca,
	    @RequestParam(value = "codiceIstanzaDaconfigurare", required = false) Integer codiceIstanzaDaconfigurare,
	    @RequestParam(value = "isCollegamentoPrecedente", required = false) Boolean isCollegamentoPrecedente,
	    @RequestParam(value = "id_ricerca", required = false) String id_ricerca,
	    @RequestParam(value = "indice_combo", required = false) String indice_combo,
	    @RequestParam(value = "codiceIstanzaAccessoAtti", required = false) Integer codiceIstanzaAccessoAtti,
	    @RequestParam(value = "codiceGruppoAnagrafetributaria", required = false) Integer codiceGruppoAnagrafetributaria,
	    HttpServletRequest request, Model model) {

	IstanzeCommand command = new IstanzeCommand();
	// configuro sul command il tipo di ricerca (RICERCA_ISTANZE,RICERCA_ISTANZE_COLLEGATE,RICERCA_ISTANZA_ACCESSO_ATTI)
	// nel caso sia null di default sarà RICERCA_ISTANZE
	if (tipoRicerca == null) {
	    tipoRicerca = WebConstants.SEARCH_ISTANZE;
	}
	log.debug("TIPO_RICERCA = {}", tipoRicerca);
	command.setTipoRicerca(tipoRicerca);
	if (tipoRicerca.equals(WebConstants.SEARCH_ISTANZE)) {
	    request.getSession().removeAttribute(ISTANZE_FILTER_IN_SESSION);
	}
	boolean istanzeCollegateRicerca = false;
	if (tipoRicerca.equals(WebConstants.SEARCH_ISTANZE_COLLEGATE)) {
	    isCollegamentoPrecedente = BooleanUtils.isTrue(isCollegamentoPrecedente);
	    istanzeCollegateRicerca = true;
	}
	// CASO - RICERCA_ISTANZE_COLLEGATE :
	// 1. Setto, se presente, la variabile che indica se l'istanza sarà collegata come precedente o successiva
	// 2. Imposto nel command l'istanza a cui collegherò le istanze scelte
	command.setIsCollegamentoPrecedente(isCollegamentoPrecedente);
	if (!istanzeCollegateRicerca || tipoRicerca.equals(WebConstants.SEARCH_ISTANZE)) {
	    request.getSession().removeAttribute(ISTANZE_FILTER_IN_SESSION);
	}
	if (codiceIstanzaDaconfigurare != null) {
	    Istanze istanze = istanzeService.findById(new PkId(codiceIstanzaDaconfigurare));
	    command.setIstanzaDaConfigurare(istanze);
	}
	// CASO - RICERCA_ISTANZA_ACCESSO_ATTI : devo tenere traccia del fascicolo a cui stiamo allegando le istanze (ISTANZE_ACCESSO_ATTI_T)
	command.setCodiceIstanzaAccessoAtti(codiceIstanzaAccessoAtti);
	command.setCodiceGruppoAnagrafetributaria(codiceGruppoAnagrafetributaria);
	// -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SEARCH_DATI_CONC_AUT, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SEARCH_DATI_LOCALIZZ, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SEARCH_DATI_PROGETTO, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CERCAISTANZA_ALTRI_INDIRIZZI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ORIDIMANENTO_ISTANZE, DAOOrderTypeEnum.ASC.toString(), request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE, "data", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VALORE_STATO_ISTANZA, "stato_tutte", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISISTANZA, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_GESTIONE_RICERCHE, "0", request);
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISISTANZA_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISISTANZA, "1", request)));
	// Recupero il vaore che indica il campo per cui ordiniamo che è stato associato 
	//al parametro di cinfigurazione e lo setto al filtro
	String orderFiled = (String) request.getAttribute(WebConstants.CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE);
	command.getIstanzeFilter().setOrderBy(orderFiled);
	// Recupero il vaore che indica il campo di ricerca stato istanza associato alla configurazione
	// utente  e lo setto al filtro
	model.addAttribute("CONF_UTENTE_NUMRECORDLISTE_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente("CONF_UTENTE_LISTISTANZA_NUMRECORDLISTE", "0", request)));
	// String statoIstanza = (String) request.getAttribute(WebConstants.CONF_UTENTE_VALORE_STATO_ISTANZA);
	// command.getIstanzeFilter().getChiusura().getId().setCodicestato(statoIstanza);
	model.addAttribute("istanzeCommand", command);
	// -----FINE GESTIONE CONFIGURAZIONE UTENTE------------------------
	// -----INIZIO VERTICALIZZAZIONI ATTIVE----------------------
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_PEOPLE, request);
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA, request);
	// -----FINE VERTICALIZZAZIONI ATTIVE------------------------
	FilterTable ftable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	ftable.addOrder(FilterUtils.orderAsc("ordine"));
	List<Statiistanza> statiistanzaList = statiistanzaService.findByFilterTable(ftable);
	model.addAttribute("statiistanzaList", statiistanzaList);
	IstanzeOnLineHelper helper = istanzeService.findIstanzeOnline();
	model.addAttribute("istanzeOnLineHelper", helper);
	// Sezione per pratiche da assegna ad istruttore
	int countIstanzeDaAssegnaAdIstruttore = istanzeService
		.countIstanzeDaAssegnareAdIstruttoreByRespProcedimento(getCurrentlyAuthenticatedUserDetails());
	model.addAttribute("countIstanzeDaAssegnaAdIstruttore", countIstanzeDaAssegnaAdIstruttore);
	// Sezione per pratiche  assegnate ad istruttore
	int countIstanzeAssegnaAdIstruttore = istanzeService
		.countIstanzeAssegnareAdIstruttoreByRespProcedimento(getCurrentlyAuthenticatedUserDetails());
	model.addAttribute("countIstanzeAssegnaAdIstruttore", countIstanzeAssegnaAdIstruttore);
	List<ChiaveValoreBean<String, Integer>> stcAn = movimentiService.countMovimentiSTCConAnomalie();
	model.addAttribute("stcConAnomalie", stcAn);
	if (StringUtils.isNotBlank(id_ricerca)) {
	    Ricerche ricerche = ricercheService.findById(new PkId(Integer.parseInt(id_ricerca)));
	    IstanzeFilter istanzeFilter = istanzeFilterUtils.createIstanzeFilter(ricerche.getFiltro());
	    command.setIstanzeFilter(istanzeFilter);
	    command.setCodiceRicerca(Integer.parseInt(indice_combo));
	}
	int countIstanzeInWarning = istanzeService.countIstanzeConStatoInWarning();
	model.addAttribute("countIstanzeInWarning", countIstanzeInWarning);
	setPageAttributes(model);
	return "istanze/searchIstanze";
    }

    /**
     * Richiama la visualizzazione grafica dell'indirizzo nel SIT attivo. TODO: implementare la logica in modo che venga
     * richiamata la pagina in dipendenza del SIT attivo Es. "istanze/GoogleLocalizzaIstanza" è per l'integrazione con
     * google. TODO: Probabilmente è corretto salvare le coordinate solo sull'indirizzo primario altrimenti
     * risulterebbero più punti per una sola istanza.
     * 
     * @param model
     * @param idStradario
     * @param request
     * @return
     */
    @RequestMapping
    public String localizzaIstanzaStradario(Model model, @RequestParam("idStradario") Integer idStradario, HttpServletRequest request) {

	Istanzestradario istanzestradario = istanzestradarioService.findById(new PkId(idStradario));
	IstanzeCommand command = new IstanzeCommand();
	IstanzeFilter filter = (IstanzeFilter) request.getSession().getAttribute("istanzeFilter");
	if (filter == null) {
	    filter = new IstanzeFilter();
	}
	command.setIstanzeFilter(filter);
	Istanze istanza = istanzeService.findById(istanzestradario.getIstanza().getId());
	command.setEntity(istanza);
	model.addAttribute("istanzeCommand", command);
	model.addAttribute("istanzestradario", istanzestradario);
	int latLng = -1;
	if (istanzestradario.getNote() != null)
	    latLng = istanzestradario.getNote().indexOf(";");
	if (latLng > -1) {
	    // TODO: i riferimenti dovranno essere riletti da due campi specifici X,Y o LATITUDE,LONGITUDES e non dal
	    // campo note
	    model.addAttribute("lat", istanzestradario.getNote().substring(0, latLng));
	    model.addAttribute("lng", istanzestradario.getNote().substring(latLng + 1));
	} else {
	    model.addAttribute("lat", "");
	    model.addAttribute("lng", "");
	}
	command.setDisplayMode(IstanzeCommand.EDIT);
	// TODO la visualizzazione da richiamare deve dipendere dal SIT attivo
	return "istanze/GoogleLocalizzaIstanza";
    }

    /**
     * Salva il punto in cui deve essere posizionato il marker che rappresenta l'indirizzo "idStradario". Viene
     * richiamato dalla pagina GoogleLocalizzaIstanza.
     * 
     * @param model
     * @param idStradario
     * @param lat
     *            latitudine dell'oggetto
     * @param lng
     *            longitudine dell'oggetto
     * @param request
     * @param response
     */
    @RequestMapping
    public void ajaxGoogleSaveIdStradario(Model model, @RequestParam("idStradario") Integer idStradario, @RequestParam("lat") Double lat,
	    @RequestParam("lng") Double lng, HttpServletRequest request, HttpServletResponse response) {

	Istanzestradario istanzestradario = istanzestradarioService.findById(new PkId(idStradario));
	// TODO: i riferimenti dovranno essere salvati su due campi specifici X,Y o LATITUDE,LONGITUDE
	istanzestradario.setNote(lat + ";" + lng);
	istanzestradarioService.update(istanzestradario);
	try {
	    response.getWriter().write("");
	} catch (IOException e) {
	}
    }

    @RequestMapping
    public void ajaxUpdateProprieta(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand,
	    @RequestParam("campoDaModificare") String campoDaModificare, @RequestParam("valore") String valore, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	Istanze istanza = istanzeCommand.getEntity();
	checkAccessoInformazioni(istanza, true);
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	try {
	    if (campoDaModificare.equalsIgnoreCase("numeroistanza")) {
		String oldNumero = istanza.getNumeroistanza();
		istanzeService.updateNumeroistanza(istanza.getId().getCodice(), valore);
		LoggerCancellazioni.log("#MODIFICANUMEROISTANZA#In data " + Utilities.formatDate(new Date(), true) + " l'operatore " +
					responsabile.getResponsabile() + " ha modificato il numero dell'istanza [" + ORMHelper.getIdcomune() + "," +
					istanza.getId().getCodice() + "]" + ". VECCHIO NUMERO: " + oldNumero + ", NUOVO NUMERO: " + valore);
	    } else if (campoDaModificare.equalsIgnoreCase("lavoriestesa")) {
		istanzeService.updateLavoriestesa(istanza.getId().getCodice(), valore);
	    }
	    istanza = istanzeService.findById(new PkId(istanza.getId().getCodice()));
	    istanzeCommand.setEntity(istanza);
	} catch (Exception e) {	    
	    response.getWriter().write(e.getMessage());
	}
	fixRenderEntityProperty(istanzeCommand);
    }

    /**
     * Permette di recuperare la lista delle istanze online mostrando solo quelle per il tipo richiesto
     * 
     * Le istanze sono divise per:
     * 
     * 1- Con Errore 2- Area riservata o People
     * 
     * @param tipo
     * @param model
     * @param istanzeCommand
     * @param result
     * @param status
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public String istanzeOnline(@RequestParam("tipo") String tipo, @RequestParam(value = "idnodo", required = false) String idnodo,
	    @RequestParam(value = "identemitt", required = false) String identemitt,
	    @RequestParam(value = "idsportellomitt", required = false) String idsportellomitt, Model model,
	    @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand, BindingResult result, SessionStatus status, HttpServletRequest request,
	    HttpServletResponse response) {

	//Se il tipo contiene la stringa errori allora andrò a fare la ricerca su DOMANDE STC andando a ricercare quelle con 
	// Flag_importate == false
	if (tipo.contains("errori")) {
	    // Creo un filtro generico con i campi comuni sia che le domande provengano dall'area riservata o people
	    Domandestc filter = new Domandestc();
	    filter.setFlagImport(false);
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    filter.setSoftware(software);
	    // Sfrutto il metodo del controller di Domandestc per mostrare la lista
	    model.addAttribute("domandestc", filter);
	    return "redirect:../domandestc/listFiltrate.htm";
	} else //SE non è presente la parola errori la ricerca deve essere fatta sulla tabella istanze con appositi filtri
	{
	    // Istanziamo un oggetto istanze filter
	    IstanzeFilter filter = new IstanzeFilter();
	    Statiistanza statoInizialeIstanzaProvenienteDaSTC = foArconfigurazioneService.findStatoInizialeIstanzaOnline();
	    if (statoInizialeIstanzaProvenienteDaSTC != null) {
		filter.setChiusura(statoInizialeIstanzaProvenienteDaSTC);
	    }
	    filter.setCercasolodomandestc(true);
	    IstanzeCommand command = new IstanzeCommand();
	    Configurazioneutente conf = configurazioneutenteService.findById(new ConfigurazioneutenteId(
		    getCurrentlyAuthenticatedUserDetails().getId().getCodice(), WebConstants.CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE));
	    String ordinamento = "";
	    if (conf != null) {
		ordinamento = conf.getValore();
	    } else {
		ordinamento = "data";
	    }
	    if (StringUtils.isNotBlank(ordinamento)) {
		filter.setOrderBy(ordinamento);
	    }
	    Configurazioneutente confAscDesc = configurazioneutenteService.findById(new ConfigurazioneutenteId(
		    getCurrentlyAuthenticatedUserDetails().getId().getCodice(), WebConstants.CONF_UTENTE_ORIDIMANENTO_ISTANZE));
	    OrderTypeEnum ascDesc = OrderTypeEnum.ASC;
	    if (confAscDesc != null) {
		if (StringUtils.defaultIfEmpty(confAscDesc.getValore(), OrderTypeEnum.ASC.name()).equalsIgnoreCase(OrderTypeEnum.DESC.name())) {
		    ascDesc = OrderTypeEnum.DESC;
		}
	    }
	    filter.setOrderAscDesc(ascDesc);
	    if (StringUtils.isNotBlank(idnodo)) {
		filter.setIdNodoStc(idnodo);
	    }
	    if (StringUtils.isNotBlank(identemitt)) {
		filter.setIdentemitt(identemitt);
	    }
	    if (StringUtils.isNotBlank(idsportellomitt)) {
		filter.setIdsportellomitt(idsportellomitt);
	    }
	    command.setIstanzeFilter(filter);
	    model.addAttribute("istanzeCommand", command);
	    return "redirect:listIstanze.htm";
	}
    }

    @RequestMapping
    public String anomalieSTC(@RequestParam("stato") String stato, Model model, @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	// Istanziamo un oggetto istanze filter
	IstanzeFilter filter = new IstanzeFilter();
	IstanzeCommand command = new IstanzeCommand();
	Configurazioneutente conf = configurazioneutenteService.findById(new ConfigurazioneutenteId(
		getCurrentlyAuthenticatedUserDetails().getId().getCodice(), WebConstants.CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE));
	String ordinamento = "";
	if (conf != null) {
	    ordinamento = conf.getValore();
	} else {
	    ordinamento = "data";
	}
	if (StringUtils.isNotBlank(ordinamento)) {
	    filter.setOrderBy(ordinamento);
	}
	Configurazioneutente confAscDesc = configurazioneutenteService.findById(new ConfigurazioneutenteId(
		getCurrentlyAuthenticatedUserDetails().getId().getCodice(), WebConstants.CONF_UTENTE_ORIDIMANENTO_ISTANZE));
	OrderTypeEnum ascDesc = OrderTypeEnum.ASC;
	if (confAscDesc != null) {
	    if (StringUtils.defaultIfEmpty(confAscDesc.getValore(), OrderTypeEnum.ASC.name()).equalsIgnoreCase(OrderTypeEnum.DESC.name())) {
		ascDesc = OrderTypeEnum.DESC;
	    }
	}
	filter.setStatomovimentoAnomaliaStc(stato);
	filter.setOrderAscDesc(ascDesc);
	command.setIstanzeFilter(filter);
	model.addAttribute("istanzeCommand", command);
	return "redirect:listIstanze.htm";
    }

    @Override
    protected void fixMergeEntityProperty(IstanzeCommand entity) {

    }

    @Override
    protected void fixRenderEntityProperty(IstanzeCommand command) {

	Istanze entity = command.getEntity();
	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
	if (EntityUtils.getNestedProperty(entity, "responsabile") == null) {
	    entity.setResponsabile(new Responsabili());
	}
	if (EntityUtils.getNestedProperty(entity, "responsabileProcedimento") == null) {
	    entity.setResponsabileProcedimento(new Responsabili());
	}
	if (EntityUtils.getNestedProperty(entity, "operatoreInCarico") == null) {
	    entity.setOperatoreInCarico(new Responsabili());
	}
	if (EntityUtils.getNestedProperty(entity, "istruttore") == null) {
	    entity.setIstruttore(new Responsabili());
	}
	if (EntityUtils.getNestedProperty(entity, "richiedente") == null) {
	    entity.setRichiedente(new Anagrafe());
	}
	if (EntityUtils.getNestedProperty(entity, "titolarelegale") == null) {
	    entity.setTitolarelegale(new Anagrafe());
	}
	if (EntityUtils.getNestedProperty(entity, "professionista") == null) {
	    entity.setProfessionista(new Anagrafe());
	}
	if (EntityUtils.getNestedProperty(entity, "procedura") == null) {
	    entity.setProcedura(new Tipiprocedure());
	}
	if (EntityUtils.getNestedProperty(entity, "impianto") == null) {
	    entity.setImpianto(new Impianti());
	}
	if (EntityUtils.getNestedProperty(entity, "tipoMovimentoAvvio") == null) {
	    entity.setTipoMovimentoAvvio(new Tipimovimento());
	}
	if (EntityUtils.getNestedProperty(entity, "tipiarchivioistanza") == null) {
	    entity.setTipiarchivioistanza(new Tipiarchivioistanze());
	}
	if (EntityUtils.getNestedProperty(entity, "comune") == null) {
	    entity.setComune(new Comuni());
	}
	if (EntityUtils.getNestedProperty(entity, "alberoproc") == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
	if (EntityUtils.getNestedProperty(entity, "tipologiaistanza") == null) {
	    entity.setTipologiaistanza(new Tipologiaistanza());
	}
	if (EntityUtils.getNestedProperty(entity, "istanzaCollegata") == null) {
	    entity.setIstanzaCollegata(new Istanze());
	}
	if (EntityUtils.getNestedProperty(entity, "formato") == null) {
	    entity.setFormato(new PuFormati());
	}
	if (EntityUtils.getNestedProperty(entity, "tipisoggetto") == null) {
	    entity.setTipisoggetto(new Tipisoggetto());
	}
	if (EntityUtils.getNestedProperty(entity, "aree2") == null) {
	    entity.setAree2(new Aree2());
	}
	if (EntityUtils.getNestedProperty(entity, "attivita") == null) {
	    entity.setAttivita(new IAttivita());
	}
	if (EntityUtils.getNestedProperty(command.getIstanzeFilter(), "istanzestradario") == null) {
	    command.getIstanzeFilter().setIstanzestradario(new Istanzestradario());
	}
	if (EntityUtils.getNestedProperty(command.getIstanzeFilter().getIstanzestradario(), "stradariocolore") == null) {
	    command.getIstanzeFilter().getIstanzestradario().setStradariocolore(new Stradariocolore());
	}
	if (EntityUtils.getNestedProperty(command.getIstanzeFilter().getIstanzestradario(), "stradario") == null) {
	    command.getIstanzeFilter().getIstanzestradario().setStradario(new Stradario());
	}
	if (EntityUtils.getNestedProperty(command.getIstanzeFilter().getIstanzestradario(), "tipiLocalizzazioni.id.codice") == null) {
	    command.getIstanzeFilter().getIstanzestradario().setTipiLocalizzazioni(new TipiLocalizzazioni());
	}
	if (EntityUtils.getNestedProperty(command.getIstanzeFilter(), "istanzearee") == null) {
	    command.getIstanzeFilter().setIstanzearee(new Istanzearee());
	}
	if (EntityUtils.getNestedProperty(command.getIstanzeFilter().getIstanzearee(), "area") == null) {
	    command.getIstanzeFilter().getIstanzearee().setArea(new Aree());
	}
	if (EntityUtils.getNestedProperty(command.getIstanzeFilter(), "istanzemappali") == null) {
	    command.getIstanzeFilter().setIstanzemappali(new Istanzemappali());
	}
	if (EntityUtils.getNestedProperty(command.getIstanzeFilter().getIstanzemappali(), "catasto") == null) {
	    command.getIstanzeFilter().getIstanzemappali().setCatasto(new Catasto());
	}
	if (EntityUtils.getNestedProperty(command.getIstanzeFilter(), "modulo") == null) {
	    entity.setSoftware(new Software());
	}
	if (EntityUtils.getNestedProperty(entity, "gruppiIstruttori") == null) {
	    entity.setGruppiIstruttori(new GruppiIstruttori());
	}
	if (EntityUtils.getNestedProperty(entity, "istruttoreTemp") == null) {
	    entity.setIstruttoreTemp(new Responsabili());
	}
	// ..nella pagina di visualizzazione istanza ho bisogno di settare alcune proprietà che stanno sul command ad
	// esempio l'area primaria
    }

    /**
     * aggiunge nel model i parametri
     * <ul>
     * <li>model.addAttribute("configurazione", configurazione);</li>
     * <li>model.addAttribute("isTipologiaistanzaVisible", isTipologiaistanza);</li>
     * <li>model.addAttribute("isArchiviopraticheVisible", isArchiviopratiche);</li>
     * <li>model.addAttribute("isStradariocoloreVisible", isStradariocolore);</li>
     * <li>model.addAttribute("isTipifamiglieendoVisible", isTipifamiglieendo);</li>
     * <li>model.addAttribute("isSettoriVisible", isSettori);</li>
     * <li>model.addAttribute("isAttivitaVisible", isAttivita);</li>
     * <li>model.addAttribute("isTipiOrarioVisibile", isTipiOrario);</li>
     * <li>model.addAttribute("isLavoritipiVisibile", isLavoritipi);</li>
     * <li>model.addAttribute("stradariocoloreList", stradariocoloreList);</li>
     * <li>model.addAttribute("catastoList", catastoList);</li>
     * </ul>
     */
    @Override
    protected void setPageAttributes(Model model) {

	Configurazione configurazione = configurazioneService.findById(new ConfigurazioneId());
	if (configurazione == null) {
	    throw new InvalidConfigurationException("Nessuna configurazione trovata per il software [" + ORMHelper.getSoftware() + "]");
	}
	model.addAttribute("configurazione", configurazione);
	boolean protocolloObbligatorio = BooleanUtils.toBoolean(configurazione.getProtgenobblig());
	model.addAttribute("protocolloObbligatorio", Boolean.valueOf(protocolloObbligatorio));
	boolean isTipologiaistanza = tipologiaistanzaService.existsRecords();
	model.addAttribute("isTipologiaistanzaVisible", isTipologiaistanza);
	boolean isArchiviopratiche = tipiarchivioistanzeService.existsRecords();
	model.addAttribute("isArchiviopraticheVisible", isArchiviopratiche);
	boolean isStradariocolore = stradariocoloreService.existsRecords();
	model.addAttribute("isStradariocoloreVisible", isStradariocolore);
	boolean isTipifamiglieendo = tipifamiglieendoService.existsRecords();
	model.addAttribute("isTipifamiglieendoVisible", isTipifamiglieendo);
	boolean isSettori = settoriService.existsRecords();
	model.addAttribute("isSettoriVisible", isSettori);
	boolean isAttivita = attivitaService.existsRecords();
	model.addAttribute("isAttivitaVisible", isAttivita);
	boolean isImpianti = impiantiService.existsRecords();
	model.addAttribute("isImpiantiVisibile", isImpianti);
	boolean isAree = areeService.existsRecords();
	model.addAttribute("isAreeVisibile", isAree);
	boolean isAree2 = aree2Service.existsRecords();
	model.addAttribute("isAree2Visibile", isAree2);
	if (isSettori) {
	    Settori settore = new Settori();
	    settore.setFlagContamqattivita(true);
	    List<Settori> settoris = settoriService.findByFilter(settore);
	    for (Settori settori : settoris) {
		model.addAttribute("LABEL_CONTA_MQ_SETTORI", settori.getSettore());
		model.addAttribute("LABEL_CONTA_MQ_UNITA_MISURA", EntityUtils.getNestedProperty(settori, "tipiunitamisura.umDescrbreve"));
	    }
	}
	boolean isTipiOrario = tipiorarioService.existsRecords();
	model.addAttribute("isTipiOrarioVisibile", isTipiOrario);
	// §§§BEGIN§§§
	boolean isLavoritipi = lavoritipiCausalioneriService.existsRecords();
	model.addAttribute("isLavoritipiVisibile", isLavoritipi);
	// §§§END§§§
	List<Stradariocolore> stradariocoloreList = stradariocoloreService.findAll();
	model.addAttribute("stradariocoloreList", stradariocoloreList);
	List<Catasto> catastoList = catastoService.findAll();
	model.addAttribute("catastoList", catastoList);
    }

    /**
     * il metodo protected boolean createJMesaExport(...) genera la "table facade" da esportare nei diversi formati
     * disponibili.
     * 
     * 
     * @param request
     *            :rappresenta la request del metodo chiamante.
     * @param response
     *            :rappresenta la response del metodo chiamante.
     * @param items
     *            :lista di valori che verranno visualizzati nella tabella esportata.
     * 
     * @return boolean export : false no export true export
     */
    protected String createJMesaListIstanze(HttpServletRequest request, HttpServletResponse response, Collection<Istanze> items, boolean isExport) {

	String output = null;
	// Ottengo dalla request l'id della table facade(JMesa)
	String idTable = "istanze_id";
	// genero una table facade
	HttpServletRequestSpringWebContext httpServletRequestSpringWebContext = new HttpServletRequestSpringWebContext(request);
	TableFacade tableFacade = TableFacadeFactory.createSpringTableFacade(idTable, httpServletRequestSpringWebContext);
	tableFacade.setStateAttr("restore");
	tableFacade.setItems(items);
	tableFacade.setExportTypes(response, ExportType.CSV, ExportType.EXCEL, ExportType.PDFP);
	//	// Gestione filterMatcher
	if (!(idTable == null)) {
	    String[] dateProperties = new String[] { "data", "dataprotocollo" };
	    tableFacade.addFilterMatcherMap(new DateCustomFilterMatcherMap(dateProperties));
	}
	// fine gestione filterMatcher
	Limit limit = tableFacade.getLimit();
	ComponentFactory factory = null;
	// Test per verificare se la tabella è stata esportata
	// Test per verificare quale tipo di export è stato scelto a
	// lato client.Crea un ComponentFactory adeguato alla scelta.
	boolean setHtmlProperties = true;
	boolean isTipologiaistanza = tipologiaistanzaService.existsRecords();
	boolean isArchiviopratiche = tipiarchivioistanzeService.existsRecords();
	if (limit.isExported()) {
	    setHtmlProperties = false;
	    if (limit.getExportType().toParam().equals("csv")) {
		factory = new CsvComponentFactory(",", tableFacade.getWebContext(), tableFacade.getCoreContext());
	    }
	    if (limit.getExportType().toParam().equals("pdfp")) {
		factory = new ExportComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());
	    }
	    if (limit.getExportType().toParam().equals("excel")) {
		factory = new ExportComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());
	    }
	} else {
	    factory = new HtmlComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());
	}
	// Generazione della tabella da esportare
	Table table = factory.createTable();
	// recupero dalla request del caption della tabella.
	// il caption è utilizzato per dare il nome al file esportato
	// e per il caption della tabella esportata
	String caption = getMessageFromBundle("label.lista_istanze", null);
	String urlBack = "../istanze/listIstanze.htm";
	table.setCaption(caption);
	CellEditor celEdit = factory.createBasicCellEditor();
	Row row = factory.createRow();
	// Determino le proprietà tramite il metodo private getProperties(HttpServletRequest request)
	// Setto le proprietà
	Column numeroistanza = null;
	if (setHtmlProperties) {
	    numeroistanza = factory.createColumn("numeroistanza", new LinkIstanzeCellEditor(request, urlBack));
	} else {
	    numeroistanza = factory.createColumn("numeroistanza", celEdit);
	}
	numeroistanza.setTitleKey("label.numeroistanza");
	row.addColumn(numeroistanza);
	if (setHtmlProperties) {
	    Column movimenti = factory.createColumn("software.codice", new LinkMovimentiCellEditor(request, urlBack));
	    movimenti.setTitleKey("label.M");
	    String title = "<span title=\"" + getMessageFromBundle("label.colonna_movimenti_help", null) + "\">" +
			   getMessageFromBundle("label.M", null) + "</span>";
	    ((HtmlColumn) movimenti).getHeaderRenderer().setHeaderEditor(new CustomHeaderEditor(title));
	    ((HtmlColumn) movimenti).setFilterable(false);
	    ((HtmlColumn) movimenti).setSortable(false);
	    row.addColumn(movimenti);
	}
	if (setHtmlProperties) {
	    Column elaborazione = factory.createColumn("software.descrizione", new LinkElaborazioneCellEditor(request, urlBack));
	    String title = "<span title=\"" + getMessageFromBundle("label.colonna_elaborazione_help", null) + "\">" +
			   getMessageFromBundle("label.E", null) + "</span>";
	    ((HtmlColumn) elaborazione).getHeaderRenderer().setHeaderEditor(new CustomHeaderEditor(title));
	    ((HtmlColumn) elaborazione).setFilterable(false);
	    ((HtmlColumn) elaborazione).setSortable(false);
	    row.addColumn(elaborazione);
	}
	if (setHtmlProperties) {
	    String visualizzaAutorizzazioni = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISAUTORIZZAZIONI, "0",
		    request);
	    if (visualizzaAutorizzazioni.equalsIgnoreCase("1")) {
		Column autorizzazioni = factory.createColumn("software.ordine", new LinkAutorizzazioniCellEditor(request, urlBack));
		String title = "<span title=\"" + getMessageFromBundle("label.colonna_autorizzazioni_help", null) + "\">" +
			       getMessageFromBundle("label.A", null) + "</span>";
		((HtmlColumn) autorizzazioni).getHeaderRenderer().setHeaderEditor(new CustomHeaderEditor(title));
		((HtmlColumn) autorizzazioni).setFilterable(false);
		((HtmlColumn) autorizzazioni).setSortable(false);
		row.addColumn(autorizzazioni);
	    }
	}
	// GIANAPOLO
	if (setHtmlProperties) {
	    String visualizzaLinkDocistanza = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISLINKDOCISTANZA, "0",
		    request);
	    if (visualizzaLinkDocistanza.equalsIgnoreCase("1")) {
		Column linkdocumenti = factory.createColumn("software.ordine", new LinkAutorizzazioniCellEditor(request, urlBack));
		String title = "<span title=\"" + getMessageFromBundle("label.colonna_autorizzazioni_help", null) + "\">" +
			       getMessageFromBundle("label.A", null) + "</span>";
		((HtmlColumn) linkdocumenti).getHeaderRenderer().setHeaderEditor(new CustomHeaderEditor(title));
		((HtmlColumn) linkdocumenti).setFilterable(false);
		((HtmlColumn) linkdocumenti).setSortable(false);
		row.addColumn(linkdocumenti);
	    }
	}
	Column dataPresentazione = factory.createColumn("data", new DateCellEditor(WebConstants.DATE_FORMAT_PATTERN));
	dataPresentazione.setTitleKey("label.data_presentazione");
	if (setHtmlProperties) {
	    FilterRenderer renderer = ((HtmlColumn) dataPresentazione).getFilterRenderer();
	    renderer.setFilterEditor(new DataCustomFilterEditor(idTable, "data", "calData"));
	    ((HtmlColumn) dataPresentazione).setFilterRenderer(renderer);
	}
	row.addColumn(dataPresentazione);
	String visualizzaProtocollo = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISNUMPROTOCOLLO, "1", request);
	if (visualizzaProtocollo.equalsIgnoreCase("1")) {
	    Column numeroprotocollo = factory.createColumn("numeroprotocollo", celEdit);
	    numeroprotocollo.setTitleKey("label.numero_protocollo");
	    row.addColumn(numeroprotocollo);
	}
	String visualizzaDataProtocollo = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISDATAPROTOCOLLO, "0", request);
	if (visualizzaDataProtocollo.equalsIgnoreCase("1")) {
	    Column dataProtocollo = factory.createColumn("dataprotocollo", new DateCellEditor(WebConstants.DATE_FORMAT_PATTERN));
	    dataProtocollo.setTitleKey("label.data_protocollo");
	    if (setHtmlProperties) {
		FilterRenderer renderer = ((HtmlColumn) dataProtocollo).getFilterRenderer();
		renderer.setFilterEditor(new DataCustomFilterEditor(idTable, "dataprotocollo", "calDataprotocollo"));
		((HtmlColumn) dataProtocollo).setFilterRenderer(renderer);
	    }
	    row.addColumn(dataProtocollo);
	}
	if (isTipologiaistanza) {
	    String visualizzaTipologiaIstanza = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISTIPOLOGIAISTANZA, "0",
		    request);
	    if (visualizzaTipologiaIstanza.equalsIgnoreCase("1")) {
		Column tipologiaistanza = factory.createColumn("tipologiaistanza.tiDescrizione", celEdit);
		tipologiaistanza.setTitleKey("label.tipologia_istanza");
		row.addColumn(tipologiaistanza);
	    }
	}
	String visualizzaRichiedente = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISRICHIEDENTE, "1", request);
	if (visualizzaRichiedente.equalsIgnoreCase("1")) {
	    Column richiedente = factory.createColumn("transientRichiedenteQualitaAzienda", celEdit);
	    richiedente.setTitleKey("label.richiedente");
	    row.addColumn(richiedente);
	}
	String visualizzaOperatore = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISOPERATORE, "0", request);
	if (visualizzaOperatore.equalsIgnoreCase("1")) {
	    Column operatore = factory.createColumn("responsabile.responsabile", celEdit);
	    operatore.setTitleKey("label.operatore");
	    row.addColumn(operatore);
	}
	String visualizzaResprocedimento = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISRESPPROC, "0", request);
	if (visualizzaResprocedimento.equalsIgnoreCase("1")) {
	    Column respProcedimento = factory.createColumn("responsabileProcedimento.responsabile", celEdit);
	    respProcedimento.setTitleKey("label.responsabile_procedimento");
	    row.addColumn(respProcedimento);
	}
	String visualizzaIstruttore = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISISTRUTTORE, "0", request);
	if (visualizzaIstruttore.equalsIgnoreCase("1")) {
	    Column istruttore = factory.createColumn("istruttore.responsabile", celEdit);
	    istruttore.setTitleKey("label.responsabile_istruttoria");
	    row.addColumn(istruttore);
	}
	String visualizzaLocalizzazione = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISLOCALIZZAZIONE, "1", request);
	if (visualizzaLocalizzazione.equalsIgnoreCase("1")) {
	    Column localizzazione = factory.createColumn("transientLocalizzazionePrimario", celEdit);
	    localizzazione.setTitleKey("label.localizzazione");
	    row.addColumn(localizzazione);
	}
	if (setHtmlProperties) {
	    String visualizzaIndirizzi = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISINDIRIZZIISTANZA, "0", request);
	    if (visualizzaIndirizzi.equalsIgnoreCase("1")) {
		Column altriind = factory.createColumn("software.moduloopzionale", new LinkAltriIndirizziCellEditor());
		((HtmlColumn) altriind).setFilterable(false);
		((HtmlColumn) altriind).setSortable(false);
		String title = "<span title=\"" + getMessageFromBundle("label.colonna_altri_indirizzi_help", null) + "\">" +
			       getMessageFromBundle("label.I", null) + "</span>";
		((HtmlColumn) altriind).getHeaderRenderer().setHeaderEditor(new CustomHeaderEditor(title));
		row.addColumn(altriind);
	    }
	}
	if (setHtmlProperties) {
	    String visualizzaEndo = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISENDOPROCEDIMENTI, "0", request);
	    if (visualizzaEndo.equalsIgnoreCase("1")) {
		Column endoattivati = factory.createColumn("software.accessorapido", new LinkEndoprocedimentiCellEditor(request, urlBack));
		String title = "<span title=\"" + getMessageFromBundle("label.colonna_endoprocedimenti_help", null) + "\">" +
			       getMessageFromBundle("label.P", null) + "</span>";
		((HtmlColumn) endoattivati).getHeaderRenderer().setHeaderEditor(new CustomHeaderEditor(title));
		((HtmlColumn) endoattivati).setFilterable(false);
		((HtmlColumn) endoattivati).setSortable(false);
		row.addColumn(endoattivati);
	    }
	}
	String visualizzaIntervento = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISINTERVENTO, "1", request);
	if (visualizzaIntervento.equalsIgnoreCase("1")) {
	    Column intervento = factory.createColumn("alberoproc.vwAlberoproc.scDescrizione", celEdit);
	    intervento.setTitleKey("label.alberoproc");
	    row.addColumn(intervento);
	}
	String visualizzaProcedura = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISPROCEDURA, "0", request);
	if (visualizzaProcedura.equalsIgnoreCase("1")) {
	    Column procedura = factory.createColumn("procedura.procedura", celEdit);
	    procedura.setTitleKey("label.procedura");
	    row.addColumn(procedura);
	}
	String visualizzaLavori = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISLAVORI, "0", request);
	if (visualizzaLavori.equalsIgnoreCase("1")) {
	    Column lavori = factory.createColumn("lavori", celEdit);
	    lavori.setTitleKey("label.lavori");
	    if (setHtmlProperties) {
		((HtmlColumn) lavori).setFilterable(false);
		((HtmlColumn) lavori).setSortable(false);
	    }
	    row.addColumn(lavori);
	}
	if (isArchiviopratiche) {
	    String visualizzaArchivio = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISARCHIVIO, "0", request);
	    if (visualizzaArchivio.equalsIgnoreCase("1")) {
		Column tipiarchivioistanza = factory.createColumn("tipiarchivioistanza.archivio", celEdit);
		tipiarchivioistanza.setTitleKey("label.archivio_pratiche");
		row.addColumn(tipiarchivioistanza);
	    }
	}
	String visualizzaPosizioneArchivio = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISPOSIZIONEARCHIVIO, "1",
		request);
	if (visualizzaPosizioneArchivio.equalsIgnoreCase("1")) {
	    Column posizioneArchivio = factory.createColumn("posizionearchivio", celEdit);
	    posizioneArchivio.setTitleKey("label.posizione_in_archivio");
	    row.addColumn(posizioneArchivio);
	}
	if (setHtmlProperties) {
	    String visualizzaSorteggi = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISSORTEGGIATE, "0", request);
	    if (visualizzaSorteggi.equalsIgnoreCase("1")) {
		Column sort = factory.createColumn("comune.cap", new LinkSorteggiCellEditor());
		sort.setTitleKey("label.S");
		String title = "<span title=\"" + getMessageFromBundle("label.colonna_sorteggi_help", null) + "\">" +
			       getMessageFromBundle("label.S", null) + "</span>";
		((HtmlColumn) sort).getHeaderRenderer().setHeaderEditor(new CustomHeaderEditor(title));
		((HtmlColumn) sort).setFilterable(false);
		((HtmlColumn) sort).setSortable(false);
		row.addColumn(sort);
	    }
	}
	String visualizzaStato = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISSTATOISTANZA, "1", request);
	if (visualizzaStato.equalsIgnoreCase("1")) {
	    Column stato = factory.createColumn("chiusura.stato", celEdit);
	    stato.setTitleKey("label.stato_istanza");
	    row.addColumn(stato);
	    // Setto il title name delle colonne
	}
	table.setRow(row);
	tableFacade.setTable(table);
	if (!limit.isExported()) {
	    output = tableFacade.render();
	} else {
	    tableFacade.render();
	}
	RowSelect rowSelect = new RowSelectImpl(1, 20, 100);
	limit.setRowSelect(rowSelect);
	return output;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public void ajaxEndoInSession(@RequestParam("codiceinventario") Integer codiceinventario, @RequestParam("selezionato") Boolean selezionato,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	Set<Integer> endosInSession = (Set<Integer>) request.getSession().getAttribute(SET_ENDO_IN_SESSION);
	if (endosInSession == null) {
	    endosInSession = new HashSet<Integer>();
	    request.getSession().setAttribute(SET_ENDO_IN_SESSION, endosInSession);
	}
	if (BooleanUtils.isTrue(selezionato)) {
	    endosInSession.add(codiceinventario);
	} else {
	    endosInSession.remove(codiceinventario);
	}
	try {
	    if (endosInSession.size() > 1) {
		inventarioprocedimentiService.checkEndoIncompatibili(endosInSession);
	    }
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    endosInSession.remove(codiceinventario);
	    String err = "";
	    if (e instanceof BaseValidationException) {
		List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
		for (InvalidValue invalidValue : ivs) {
		    err += getMessageFromBundle(invalidValue.getMessage(), new Object[] { invalidValue.getValue() }) + "\n";
		}
	    } else {
		err = this.renderErrors(e, false);
	    }
	    // response.getWriter().write(err);
	    response.setStatus(500);
	    response.getWriter().write(err);// throw new RuntimeException(err);
	}
    }

    @RequestMapping
    public void ajaxDownloadDomandaStc(HttpServletRequest request, HttpServletResponse response, @RequestParam("codiceIstanza") Integer codiceIstanza)
	    throws IOException {

	List<Domandestc> domandestcs = domandestcService.findByIstanza(codiceIstanza);
	if (!domandestcs.isEmpty()) {
	    Domandestc domandestc = domandestcs.get(0);
	    if (domandestc != null) {
		Oggetti oggetto = domandestc.getOggetti();
		if (oggetto != null) {
		    response.sendRedirect("../file/ajaxDownload.htm?fileId=" + oggetto.getId().getCodice());
		}
	    }
	}
    }

    @RequestMapping
    public String ajaxVisualizzaAttivitaEsistenti(@RequestParam("codiceistanza") Integer codiceistanza,
	    @RequestParam("tipoEccezione") String tipoEccezione, Model model, HttpServletRequest request, HttpServletResponse response) {

	List<IAttivita> iAttivitas = iAttivitaService.findListaAttivitaEsistenti(codiceistanza, tipoEccezione);
	model.addAttribute("iAttivitas", iAttivitas);
	model.addAttribute("codiceistanza", codiceistanza);
	return "istanze/ajaxVisualizzaAttivitaEsistenti";
    }

    @RequestMapping
    public ModelMap ajaxListaEndo(@RequestParam("codiceAlberoProc") Integer codiceAlberoProc, HttpServletRequest request,
	    HttpServletResponse response) {

	request.getSession().removeAttribute(SET_ENDO_IN_SESSION);
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoProc));
	List<AlberoprocEndo> alberoprocEndos = alberoprocEndoService.findEndoprocedimentiHierarchy(alberoproc);
	ModelMap model = new ModelMap(alberoprocEndos);
	Set<Integer> endosInSession = new HashSet<Integer>();
	for (AlberoprocEndo alberoprocEndo : alberoprocEndos) {
	    if (BooleanUtils.isTrue(alberoprocEndo.getFlagRichiesto())) {
		endosInSession.add(alberoprocEndo.getInventarioprocedimento().getId().getCodice());
	    }
	}
	request.getSession().setAttribute(SET_ENDO_IN_SESSION, endosInSession);
	model.addAttribute("alberoprocEndosList", alberoprocEndos);
	model.addAttribute("alberoproc", alberoproc);
	return model;
    }

    @RequestMapping
    public String modificaIntervento(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) {

	PkId id = new PkId(codiceIstanza);
	Istanze istanza = istanzeService.findById(id);
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	TipoAccessoEnum tipoAccesso = istanzeService.checkAccessoIstanza(istanza, responsabile);
	if (tipoAccesso.equals(TipoAccessoEnum.NON_CONSENTITO)) {
	    throw new SecurityException(ORMHelper.getSoftware() + ": L'operatore (" + responsabile.getResponsabile() +
					") non ha accesso all'istanza [" + istanza.getNumeroistanza() + "]");
	}
	CambioInterventoCommand command = new CambioInterventoCommand();
	command.setDisplayMode(IstanzeCommand.VIEW);
	command.setEntity(istanza);
	model.addAttribute("cambioInterventoCommand", command);
	return "istanze/modificaIntervento";
    }

    @RequestMapping
    public String modificaInterventoScegliIntervento(Model model,
	    @ModelAttribute("cambioInterventoCommand") CambioInterventoCommand cambioInterventoCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	PkId id = new PkId(cambioInterventoCommand.getEntity().getId().getCodice());
	Istanze istanza = istanzeService.findById(id);
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	TipoAccessoEnum tipoAccesso = istanzeService.checkAccessoIstanza(istanza, responsabile);
	if (tipoAccesso.equals(TipoAccessoEnum.NON_CONSENTITO)) {
	    throw new SecurityException(ORMHelper.getSoftware() + ": L'operatore (" + responsabile.getResponsabile() +
					") non ha accesso all'istanza [" + istanza.getNumeroistanza() + "]");
	}
	cambioInterventoCommand.setDisplayMode(IstanzeCommand.VIEW);
	cambioInterventoCommand.setEntity(istanza);
	istanzeService.populateCambioInterventoCommand(istanza, cambioInterventoCommand);
	model.addAttribute("cambioInterventoCommand", cambioInterventoCommand);
	return "istanze/modificaIntervento";
    }

    @RequestMapping
    public String updateModificaInterventoIstanza(Model model,
	    @ModelAttribute("cambioInterventoCommand") CambioInterventoCommand cambioInterventoCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	PkId id = new PkId(cambioInterventoCommand.getEntity().getId().getCodice());
	Istanze istanza = istanzeService.findById(id);
	String istanzaDesc = istanza.toString();
	String oldIntervento = istanza.getAlberoproc().getId().toString();
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	TipoAccessoEnum tipoAccesso = istanzeService.checkAccessoIstanza(istanza, responsabile);
	if (tipoAccesso.equals(TipoAccessoEnum.NON_CONSENTITO)) {
	    throw new SecurityException(ORMHelper.getSoftware() + ": L'operatore (" + responsabile.getResponsabile() +
					") non ha accesso all'istanza [" + istanza.getNumeroistanza() + "]");
	}
	try {
	    istanzeService.updateCambioInterventoIstanza(istanza, cambioInterventoCommand);
	    LoggerCancellazioni.log("#CAMBIOINTERVENTO#In data " + Utilities.formatDate(new Date(), true) + " l'operatore " +
				    responsabile.getResponsabile() + " ha modificato l'intervento dell'istanza " + istanzaDesc + " con intervento " +
				    oldIntervento + ". MODIFICHE: " + cambioInterventoCommand.toString());
	} catch (Exception e) {
	    log.error("updateModificaInterventoIstanza# {}", e);
	    FlashMessages.getWarnings().add("Errore durante il salvataggio della nuova configurazione: " + e.getMessage());
	    return "redirect:modificaInterventoScegliIntervento.htm";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanza.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public void ajaxResetEndo(HttpServletRequest request, HttpServletResponse response) {

	request.getSession().removeAttribute(SET_ENDO_IN_SESSION);
    }

    @RequestMapping
    public String deleteArchiviazioniIstanze(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Istanze entity = istanzeCommand.getEntity();
	try {
	    archiviazioniIstanzeService.deleteByIstanza(entity.getId().getCodice());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeCommand.getEntity(), true, e);
	    entity = istanzeService.bindDomainObject(entity, PkId.class, "id.codice");
	    istanzeCommand.setEntity(entity);
	    fixRenderEntityProperty(istanzeCommand);
	    prepareViewModel(model, request, entity, istanzeCommand);
	    return "istanze/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String rielaboraMappature(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	Istanze istanza = istanzeService.findById(new PkId(codice));
	if (istanza == null) {
	    return returnConWarnings(codice, "Nessuna istanza trovata con codice " + codice);
	}
	checkAccessoInformazioni(istanza, true);
	List<Domandestc> domandes = domandestcService.findByIstanza(codice);
	if (domandes.isEmpty() || domandes.size() > 1) {
	    return returnConWarnings(codice, "Trovate " + domandes.size() + " domandestc per l'istanza " + istanza.toString());
	}
	Domandestc dom = domandes.get(0);
	if (dom == null) {
	    return returnConWarnings(codice, "Domandestc non trovata per l'istanza " + istanza.toString());
	}
	Oggetti xml = dom.getOggetti();
	if (xml == null) {
	    return returnConWarnings(codice, "La domanda stc con codice " + dom.getId().getCodice() + " non ha definito l'xml");
	}
	xml = oggettiService.findById(new PkId(xml.getId().getCodice()));
	String istanzaStr = istanza.toString();
	try {
	    InserimentoPraticaNLARequest iprequest = (InserimentoPraticaNLARequest) Utilities.unMarshallString(new String(xml.getOggetto()),
		    InserimentoPraticaNLARequest.class);
	    nlaManager.gestioneApplicazioneMappatureSchedeDinamiche(istanza, iprequest);
	    LoggerCancellazioni.log(
		    "#APPLICAZIONE_MAPPATURE# L'utente [" + ((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails()).toString() +
				    "] ha usato la funzionalità per la pratica [" + istanzaStr + "]");
	    istanzeService.eseguiFormuleDelleSchedeDinamiche(istanza);
	} catch (Exception e) {
	    log.error("Errore nell'elaborazione delle mappature {}", e);
	    return returnConWarnings(codice, "Errore nell'elaborazione delle mappature " + e);
	}
	return "redirect:view.htm?codice=" + codice + "&status_msg=02";
    }

    private String returnConWarnings(Integer codiceIstanza, String messaggio) {

	FlashMessages.getWarnings().add(messaggio);
	return "redirect:view.htm?codice=" + codiceIstanza + "&status_msg=03";
    }

    @RequestMapping
    public String privateChiudiIstanze(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	OperazioniAutomaticheBusinessRules opautBusinessRules = (OperazioniAutomaticheBusinessRules) SigeproBusinessRules
		.getClassRules(OperazioniAutomaticheBusinessRules.class);
	opautBusinessRules.setOperazioneAutomatica(true);
	SigeproBusinessRules.setClassRules(OperazioniAutomaticheBusinessRules.class, opautBusinessRules);
	checkAccessoFunzionalitaAmministrative(request, response, "../admin/authorize.htm");
	try {
	    List<ReportIstanzaChiusaHelper> s = istanzeManager.updateProcessaIstanzedaChiudere(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware(),
		    false, false);
	    if (s == null) {
		s = new ArrayList<ReportIstanzaChiusaHelper>(0);
	    }
	    request.getSession().setAttribute(_REPORT_ISTANZE_CHIUSE, s);
	} catch (Exception e) {
	    log.error("{}", e);
	} finally {
	}
	return "redirect:reportResult.htm";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String reportResult(Model model, HttpServletRequest request, HttpServletResponse response) {

	SigeproBusinessRules.buildDefaultRules();
	List<ReportIstanzaChiusaHelper> s = (List<ReportIstanzaChiusaHelper>) request.getSession().getAttribute(_REPORT_ISTANZE_CHIUSE);
	boolean export = createJMesaExport(request, response, s);
	if (export) {
	    return null;
	}
	model.addAttribute("report", s);
	return "istanze/reportchiusura";
    }

    @RequestMapping
    public String tornaDaLDP(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response) {

	Istanze i = istanzeService.findById(new PkId(codice));
	if (i != null) {
	    ldpWsClient.getDatiOccupazioneSuoloByIdentificativo(i);
	}
	return "redirect:../istanze/view.htm?codice=" + codice;
    }

    @RequestMapping
    public String ajaxStatiIstanzaSieder(@RequestParam("codiceIstanza") Integer codiceistanza, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws UnsupportedEncodingException, IOException {

	Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	checkAccessoInformazioni(istanza, true);
	Set<Domandestc> domandestcs = istanza.getDomandestcs();
	List<CodiceDescrizioneBean> cdbs = new ArrayList<CodiceDescrizioneBean>();
	if (!domandestcs.isEmpty()) {
	    String iddomandaString = "";
	    String[] domanda = istanza.getNumeroistanza().split("/");
	    iddomandaString = domanda[2] + "-" + domanda[1] + "-" + domanda[0];
	    Verticalizzazioniparametri urlStatoIstanza = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_SIEDER,
		    WebConstants.VERTICALIZZAZIONE_SIEDER_URL_STATI_AMMISSIBILI);
	    if (urlStatoIstanza != null && StringUtils.isNotBlank(urlStatoIstanza.getValore())) {
		String urlStato = urlStatoIstanza.getValore();
		urlStato = replaceSiederVariables(urlStato, istanza, iddomandaString, "");
		HttpClient cli = new HttpClient();
		HttpMethod method = null;
		int status = 0;
		try {
		    method = new GetMethod(urlStato);
		    status = cli.executeMethod(method);
		} catch (Exception e) {
		    log.error("ERRORE nell'invocazione del servizio stato istanza sieder", e);
		}
		if (status == 200) {
		    InputStream inputStream = method.getResponseBodyAsStream();
		    // Trasformo lo stream in una stringa (conterrà solo il codice)
		    //InputStream inputStream = method.getResponseBodyAsStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		    StringBuilder sb = new StringBuilder();
		    String line = null;
		    while ((line = reader.readLine()) != null) {
			sb.append(line);
		    }
		    inputStream.close();
		    String co = sb.toString();
		    log.debug("chiamata a stato istanza sieder {}", co);
		    try {
			Document doc = XMLUtils.parse(co);
			NodeList codiciList = doc.getElementsByTagName("codice");
			NodeList descrizioniList = doc.getElementsByTagName("descrizione");
			for (int i = 0; i < codiciList.getLength(); i++) {
			    Node node = codiciList.item(i);
			    Node desc = descrizioniList.item(i);
			    if (node.getNodeType() == Node.ELEMENT_NODE) {
				CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
				cdb.setCodice(node.getFirstChild().getNodeValue());
				cdb.setDescrizione(desc.getFirstChild().getNodeValue());
				cdbs.add(cdb);
			    }
			}
		    } catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
	    }
	}
	model.addAttribute("statiIstanzaSieder", cdbs);
	return "istanze/ajaxStatiIstanzaSieder";
    }

    @RequestMapping
    public String ajaxStatoIstanzaSieder(@RequestParam("codiceIstanza") Integer codiceistanza, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws UnsupportedEncodingException, IOException {

	Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	checkAccessoInformazioni(istanza, true);
	Set<Domandestc> domandestcs = istanza.getDomandestcs();
	if (!domandestcs.isEmpty()) {
	    String iddomandaString = "";
	    iddomandaString = istanza.getNumeroistanza().replace("/", "-");
	    Verticalizzazioniparametri urlStatoIstanza = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_SIEDER,
		    WebConstants.VERTICALIZZAZIONE_SIEDER_URL_STATO_PRATICA);
	    if (urlStatoIstanza != null && StringUtils.isNotBlank(urlStatoIstanza.getValore())) {
		String urlStato = urlStatoIstanza.getValore();
		urlStato = replaceSiederVariables(urlStato, istanza, iddomandaString, null);
		HttpClient cli = new HttpClient();
		HttpMethod method = null;
		int status = 0;
		try {
		    method = new GetMethod(urlStato);
		    status = cli.executeMethod(method);
		} catch (Exception e) {
		    log.error("ERRORE nell'invocazione del servizio stato istanza sieder", e);
		}
		if (status == 200) {
		    InputStream inputStream = method.getResponseBodyAsStream();
		    // Trasformo lo stream in una stringa (conterrà solo il codice)
		    //InputStream inputStream = method.getResponseBodyAsStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		    StringBuilder sb = new StringBuilder();
		    String line = null;
		    while ((line = reader.readLine()) != null) {
			sb.append(line);
		    }
		    inputStream.close();
		    String co = sb.toString();
		    log.debug("chiamata a stato istanza sieder {}", co);
		    try {
			Document doc = XMLUtils.parse(co);
			String nodeValue = doc.getFirstChild().getFirstChild().getNodeValue();
			log.debug(nodeValue);
			//			o RICEV - Ricevuta
			//			 o SOSPE - Sospesa
			//			 o RETTI - Rettificata
			//			 o DINIE - Diniegata
			//			 o EFFIC - Rilasciata
			//			 o RINUN - Rinunciata
			//			 o DECAD - Decaduta
			//			 o SOSTI - Sostituita
			//			 o INIZL - Inizio lavori
			//			 o BLOCL - Blocco
			//			 lavori
			//			 o FNLVP - Fine lavori parziale
			//			 o FINEL - Fine lavori
			if ("DEPOS".equalsIgnoreCase(nodeValue)) {
			    nodeValue = "Depositata (DEPOS)";
			}
			if ("RICEV".equalsIgnoreCase(nodeValue)) {
			    nodeValue = "Ricevuta (RICEV)";
			}
			if ("SOSPE".equalsIgnoreCase(nodeValue)) {
			    nodeValue = "Sospesa (SOSPE)";
			}
			if ("RETTI".equalsIgnoreCase(nodeValue)) {
			    nodeValue = "Rettificata (RETTI)";
			}
			if ("DINIE".equalsIgnoreCase(nodeValue)) {
			    nodeValue = "Diniegata (DINIE)";
			}
			if ("EFFIC".equalsIgnoreCase(nodeValue)) {
			    nodeValue = "Efficace (EFFIC)";
			}
			if ("RINUN".equalsIgnoreCase(nodeValue)) {
			    nodeValue = "Rinunciata (RINUN)";
			}
			if ("DECAD".equalsIgnoreCase(nodeValue)) {
			    nodeValue = "Decaduta (DECAD)";
			}
			if ("SOSTI".equalsIgnoreCase(nodeValue)) {
			    nodeValue = "Sostituita (SOSTI)";
			}
			if ("INIZL".equalsIgnoreCase(nodeValue)) {
			    nodeValue = "Inizio lavori (INIZL)";
			}
			if ("BLOCL".equalsIgnoreCase(nodeValue)) {
			    nodeValue = "Blocco lavori (BLOCL)";
			}
			if ("FNLVP".equalsIgnoreCase(nodeValue)) {
			    nodeValue = "Fine lavori parziale (FNLVP)";
			}
			if ("FINEL".equalsIgnoreCase(nodeValue)) {
			    nodeValue = "Fine lavori (FINEL)";
			}
			model.addAttribute("statoistanza", nodeValue);
		    } catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
	    }
	}
	//	List<Statiistanza> statis = statiistanzaService.findBySoftware(istanza.getSoftware());
	//	model.addAttribute("statistanza", statis);
	return "istanze/ajaxStatoIstanzaSieder";
    }

    private String replaceSiederVariables(String url, Istanze istanza, String iddomanda, String nuovostato) {

	//	{alias}/software/{software}/comune/{comune}/istanza/{istanza}/statiammissibili
	//	/alias/{alias}/software/{software}/comune/{comune}/istanza/{istanza}/stato
	url = url.replace("{alias}", ORMHelper.getIdcomuneAlias());
	url = url.replace("{software}", istanza.getSoftware().getCodice());
	url = url.replace("{comune}", istanza.getComune().getCodicecomune());
	url = url.replace("{istanza}", iddomanda);
	url = url.replace("{codiceistanza}", String.valueOf(istanza.getId().getCodice()));
	url = url.replace("{stato}", StringUtils.defaultString(nuovostato));
	return url;
    }

    @RequestMapping
    public void qrcode(@RequestParam("uuid") String uuid, HttpServletRequest request, HttpServletResponse response) throws IOException {

	QrcodeHelper qrcodeHelper = new QrcodeHelper();
	qrcodeHelper.setAuthQRcodeEnum(TipoAuthQRcodeEnum.GUEST);
	Istanze i = istanzeService.findByUiid(uuid);
	QRCodeBean b = qrcodeService.visurapratica(qrcodeHelper, i.getId().getCodice());
	if (b != null) {
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    if (request.getParameter("no_dialog") == null) {
		// BOCCI 2012-08-13
		// Nel mostrare gli allegati togliere gli spazi (https://support.mozilla.org/it/questions/724438) altrimenti firefox non va.
		// When a user clicks on an attachment with spaces, the filename is truncated to the first whitespace. 
		// While IE, Chrome & Safari handle this, Firefox refuses to accept mime headers with unquoted filename parameters. 
		// According to Firefox's bugzilla/knowledgebase, Firefox's behavior is the correct behavior and it's a problem with 
		// most webservers or web applications. This problem can be easily corrected by surrounding the filename parameter with double quotes.
		// Eg	Response.AddHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.setHeader("Content-Disposition", "inline; filename=\"qrcode_istanza_" + i.getId().getCodice() + ".png");
	    }
	    response.setHeader("Content-transfer-encoding", "binary");
	    response.setContentType("image/png");
	    response.setContentLength(b.getImage().length);
	    ServletOutputStream out = response.getOutputStream();
	    out.write(b.getImage());
	    out.flush();
	} else {
	}
    }

    @RequestMapping
    public void updateRigeneraRiepilogo(@RequestParam("codiceistanza") Integer codiceistanza,
	    @RequestParam("rigeneraRiepilogo") Boolean rigeneraRiepilogo, HttpServletRequest request, HttpServletResponse response) throws Exception {

	Istanze i = istanzeService.findById(new PkId(codiceistanza));
	RiepilogoHelper rigeneraRiepilogoHlp = documentiistanzaService.rigeneraRiepilogo(codiceistanza);
	if (rigeneraRiepilogoHlp != null) {
	    if (BooleanUtils.isTrue(rigeneraRiepilogo)) {
		documentiistanzaService.updateAggiornaRiepilogo(i, rigeneraRiepilogoHlp.getContent(), rigeneraRiepilogoHlp.getNomeFile());
	    }
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    if (request.getParameter("no_dialog") == null) {
		// BOCCI 2012-08-13
		// Nel mostrare gli allegati togliere gli spazi (https://support.mozilla.org/it/questions/724438) altrimenti firefox non va.
		// When a user clicks on an attachment with spaces, the filename is truncated to the first whitespace. 
		// While IE, Chrome & Safari handle this, Firefox refuses to accept mime headers with unquoted filename parameters. 
		// According to Firefox's bugzilla/knowledgebase, Firefox's behavior is the correct behavior and it's a problem with 
		// most webservers or web applications. This problem can be easily corrected by surrounding the filename parameter with double quotes.
		// Eg	Response.AddHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + rigeneraRiepilogoHlp.getNomeFile() + "\"");
	    }
	    response.setHeader("Content-transfer-encoding", "binary");
	    response.setContentType(rigeneraRiepilogoHlp.getMimeType());
	    response.setContentLength(rigeneraRiepilogoHlp.getContent().length);
	    ServletOutputStream out = response.getOutputStream();
	    out.write(rigeneraRiepilogoHlp.getContent());
	    out.flush();
	}
    }

    @RequestMapping
    public String ajaxAssegnaOperatori(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("tipo") String tipo, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	String assegnaGruppo = "";
	Verticalizzazioniparametri param = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI,
		WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI_ASSEGNAZIONE_CAPIENZA_GRUPPO, istanza.getComune().getCodicecomune(),
		istanza.getSoftware().getCodice());
	if (param != null && StringUtils.isNotBlank(param.getValore())) {
	    assegnaGruppo = param.getValore().trim();
	}
	boolean mostraBottoneAssegna = userHasRole(false, RuoliUtentiEnum.ATTIVA_CHIUSURA_MANUALE_GRUPPI_ISTRUTTORI.name());
	model.addAttribute("mostraBottoneAssegna", mostraBottoneAssegna);
	model.addAttribute("assegnaGruppo", assegnaGruppo);
	List<DisponibilitaResponsabile> listaDisponibilita = new ArrayList<DisponibilitaResponsabile>();
	if (assegnaGruppo.equalsIgnoreCase("S")) {
	    if (tipo.equalsIgnoreCase("responsabile")) {
		listaDisponibilita = new CalcoloDisponibilitaRespProcedimentoService(istanzeService, alberoprocService, gruppiIstruttoriRespService,
			responsabiliAssenzeService, responsabiliService, assegnazioneGruppiTestataService, assegnazioneGruppiDettaglioService)
				.calcola(codiceIstanza);
	    } else if (tipo.equalsIgnoreCase("istruttore")) {
		listaDisponibilita = new CalcoloDisponibilitaRespIstruttoriaService(istanzeService, verticalizzazioniService,
			gruppiIstruttoriRespService, alberoprocService, responsabiliAssenzeService, responsabiliService,
			assegnazioneGruppiTestataService, assegnazioneGruppiDettaglioService).calcola(codiceIstanza);
	    } else {
		throw new RuntimeException("Tipo " + tipo + " non Implementato");
	    }
	} else {
	    ResponsabiliAssegnazioniHelper rs = null;
	    if (tipo.equalsIgnoreCase("responsabile")) {
		rs = istanzeService.calcolaAssegnazioneResponsabiliProc(codiceIstanza);
	    } else if (tipo.equalsIgnoreCase("istruttore")) {
		rs = istanzeService.calcolaAssegnazioneIstruttori(codiceIstanza);
	    } else {
		throw new RuntimeException("Tipo " + tipo + " non Implementato");
	    }
	    model.addAttribute("responsabiliHelper", rs);
	    List<ResponsabiliAssegnazioniHelperBean> listaResponsabiliOrdinata = rs.getListaResponsabiliOrdinata();
	    if (listaResponsabiliOrdinata.size() > 0) {
		model.addAttribute("respSorteggiato", listaResponsabiliOrdinata.get(0).getCodiceResponsabile());
	    }
	    listaDisponibilita = trasformaInNuovaLista(listaResponsabiliOrdinata, istanza);
	}
	Responsabili r = getCurrentlyAuthenticatedUserDetails();
	model.addAttribute("canModifyAssegnazione", BooleanUtils.isTrue(r.getFlagModRespSort()));
	model.addAttribute("listaResponsabiliOrdinata", listaDisponibilita);
	return "istanze/ajaxAssegnazioneOperatori";
    }

    private List<DisponibilitaResponsabile> trasformaInNuovaLista(List<ResponsabiliAssegnazioniHelperBean> listaResponsabiliOrdinata,
	    Istanze istanza) {

	List<DisponibilitaResponsabile> ret = new ArrayList<DisponibilitaResponsabile>();
	for (ResponsabiliAssegnazioniHelperBean rah : listaResponsabiliOrdinata) {
	    DisponibilitaResponsabile dr = new DisponibilitaResponsabile(rah.getCodiceResponsabile(), rah.getResponsabile().getResponsabile(),
		    rah.getPeso(), null, istanza, null);
	    ret.add(dr);
	}
	return ret;
    }

    @RequestMapping
    public void ajaxDettaglioOperatori(@RequestParam("codiceResponsabile") Integer codiceResponsabile, @RequestParam("idTestata") Integer idTestata,
	    @RequestParam("tipo") String tipo, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, JAXBException {

	NumeriIstanzaResponse nir = null;
	if (tipo.equalsIgnoreCase("responsabile")) {
	    nir = new CalcoloDisponibilitaRespProcedimentoService(istanzeService, alberoprocService, gruppiIstruttoriRespService,
		    responsabiliAssenzeService, responsabiliService, assegnazioneGruppiTestataService, assegnazioneGruppiDettaglioService)
			    .dettaglio(codiceResponsabile, idTestata);
	} else {
	    nir = new CalcoloDisponibilitaRespIstruttoriaService(istanzeService, verticalizzazioniService, gruppiIstruttoriRespService,
		    alberoprocService, responsabiliAssenzeService, responsabiliService, assegnazioneGruppiTestataService,
		    assegnazioneGruppiDettaglioService).dettaglio(codiceResponsabile, idTestata);
	}
	String risposta = Utilities.marshalJsonObject(nir, NumeriIstanzaResponse.class, true, Utilities.JAXB_ENCODING_UTF_8);
	response.setContentType("application/json");
	response.getOutputStream().write(risposta.getBytes());
    }

    @RequestMapping
    public void ajaxChiusuraGruppo(@RequestParam("idTestata") Integer idTestata, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException, JAXBException {

	assegnazioneGruppiTestataService.chiudiAssegnazione(idTestata);
	response.setContentType("application/json");
	response.getOutputStream().write("{\"result\":\"OK\"}".getBytes());
    }

    @RequestMapping
    public String updateOperatore(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("tipo") String tipo,
	    @RequestParam("respSorteggiato") Integer respSorteggiato, HttpServletRequest request) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	try {
	    Responsabili r = responsabiliService.findById(new PkId(respSorteggiato));
	    Responsabili old = null;
	    Integer rimuoviPermesso = null;
	    if (tipo.equalsIgnoreCase("operatore")) {
		old = istanza.getResponsabile();
		rimuoviPermesso = old.getId().getCodice();
		if ((istanza.getIstruttore() != null && istanza.getIstruttore().getId().getCodice() != null)
			&& (rimuoviPermesso != null && rimuoviPermesso.equals(istanza.getIstruttore().getId().getCodice()))) {
		    rimuoviPermesso = null;
		}
		if ((istanza.getResponsabileProcedimento() != null && istanza.getResponsabileProcedimento().getId().getCodice() != null)
			&& (rimuoviPermesso != null && rimuoviPermesso.equals(istanza.getResponsabileProcedimento().getId().getCodice()))) {
		    rimuoviPermesso = null;
		}
		istanza.setResponsabile(r);
	    } else if (tipo.equalsIgnoreCase("istruttore")) {
		// verificare se verticalizzazione assegna operatore attiva
		String assegnaOperatori = verticalizzazioniService.getString(WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI,
			WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI_ASSEGNAZIONE_CAPIENZA_GRUPPO);
		if ("S".equalsIgnoreCase(assegnaOperatori)) {
		    // se attiva richiamare l'assegna pratica al dettaglio
		    assegnazioniService.assegnaPratica(codiceIstanza, ResponsabileIstanzaEnum.RESPONSABILE_ISTRUTTORIA, respSorteggiato);
		}
		// aggiorno l'istruttore
		old = istanza.getIstruttore();
		if (old != null) {
		    rimuoviPermesso = old.getId().getCodice();
		    if ((istanza.getResponsabile() != null && istanza.getResponsabile().getId().getCodice() != null)
			    && (rimuoviPermesso != null && rimuoviPermesso.equals(istanza.getResponsabile().getId().getCodice()))) {
			rimuoviPermesso = null;
		    }
		    if ((istanza.getResponsabileProcedimento() != null && istanza.getResponsabileProcedimento().getId().getCodice() != null)
			    && (rimuoviPermesso != null && rimuoviPermesso.equals(istanza.getResponsabileProcedimento().getId().getCodice()))) {
			rimuoviPermesso = null;
		    }
		}
		istanza.setIstruttore(r);
		EventoIstanzaAssegnata.fromResponsabileIstruttoria(codiceIstanza);
	    } else if (tipo.equalsIgnoreCase("responsabile")) {
		//  verificare se verticalizzazione assegna operatore attiva
		String assegnaOperatori = verticalizzazioniService.getString(WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI,
			WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI_ASSEGNAZIONE_CAPIENZA_GRUPPO);
		if (assegnaOperatori.equalsIgnoreCase("S")) {
		    // se attiva richiamare l'assegna pratica al dettaglio
		    assegnazioniService.assegnaPratica(codiceIstanza, ResponsabileIstanzaEnum.RESPONSABILE_PROCEDIMENTO, respSorteggiato);
		}
		// aggiorno il responsabile
		old = istanza.getResponsabileProcedimento();
		if (old != null) {
		    rimuoviPermesso = old.getId().getCodice();
		}
		if ((istanza.getIstruttore() != null && istanza.getIstruttore().getId().getCodice() != null)
			&& (rimuoviPermesso != null && rimuoviPermesso.equals(istanza.getIstruttore().getId().getCodice()))) {
		    rimuoviPermesso = null;
		}
		if ((istanza.getResponsabile() != null && istanza.getResponsabile().getId().getCodice() != null)
			&& (rimuoviPermesso != null && rimuoviPermesso.equals(istanza.getResponsabile().getId().getCodice()))) {
		    rimuoviPermesso = null;
		}
		istanza.setResponsabileProcedimento(r);
		EventoIstanzaAssegnata.fromResponsabileProcedimento(codiceIstanza);
	    }
	    istanzeService.update(istanza);
	    String messaggio = "In data " + Utilities.formatDate(new Date(), true) + " l'operatore " + responsabile.getResponsabile() +
			       " ha modificato l'" + tipo + " dell'istanza " + istanza.toString() + " con l'operatore " + r;
	    if (request.getParameter("check") != null) {
		istanzeeventiService.insert(messaggio, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	    }
	    if (rimuoviPermesso != null) {
		Permistanze pi = permistanzeService.findById(new PermistanzeId(codiceIstanza, rimuoviPermesso));
		permistanzeService.delete(pi);
	    }
	    LoggerCancellazioni.log("#ASSEGNAZIONEOPERATORE#" + messaggio);
	} catch (Exception e) {
	    log.error("ASSEGNAZIONEOPERATORE# {}", e);
	    FlashMessages.getWarnings().add("Errore durante il salvataggio del nuovo " + tipo + ": " + e.getMessage());
	    return "redirect:view.htm?codice=" + istanza.getId().getCodice() + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + istanza.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String changeScheda(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand, HttpServletRequest request) {

	// §§§BEGIN§§§
	SchedaDinamicaFilter sf = istanzeCommand.getIstanzeFilter().getSchedaDinamicaFilter();
	if (sf.getScheda() != null) {
	    if (sf.getScheda().getId() != null) {
		if (sf.getScheda().getId().getCodice() != null) {
		    Dyn2Modellit scheda = dyn2ModellitService.findById(new PkId(sf.getScheda().getId().getCodice()));
		    List<Dyn2Campi> d2cs = dyn2CampiService.findByDescrizioneAndSoftwareAndModello("", sf.getScheda().getId().getCodice(), null);
		    sf.getListaCampiModello().addAll(d2cs);
		    sf.setScheda(scheda);
		} else {
		    sf.setScheda(new Dyn2Modellit());
		}
	    } else {
		sf.getScheda().setId(new PkId());
	    }
	} else {
	    sf.setScheda(new Dyn2Modellit());
	}
	List<SchedaDinamicaRigheFilter> righe = sf.getRighe();
	SchedaDinamicaRigheFilter riga = new SchedaDinamicaRigheFilter();
	righe.add(riga);
	sf.setRighe(righe);
	setModelSearch(request, istanzeCommand, model);
	setPageAttributes(model);
	return "istanze/searchIstanze";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String addCampoAScheda(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand, HttpServletRequest request) {

	// §§§BEGIN§§§
	SchedaDinamicaFilter sf = istanzeCommand.getIstanzeFilter().getSchedaDinamicaFilter();
	//	if (sf.getScheda() != null) {
	//	    if (sf.getScheda().getId() != null) {
	//		if (sf.getScheda().getId().getCodice() != null) {
	//		    SchedaDinamicaRigheFilter riga = new SchedaDinamicaRigheFilter();
	//		    sf.getRighe().add(riga);
	//		}
	//	    }
	//	}
	SchedaDinamicaRigheFilter riga = new SchedaDinamicaRigheFilter();
	sf.getRighe().add(riga);
	setModelSearch(request, istanzeCommand, model);
	setPageAttributes(model);
	return "istanze/searchIstanze";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String removeCampoScheda(@RequestParam(value = "idx", required = true) Integer idx, Model model,
	    @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand, HttpServletRequest request) {

	// §§§BEGIN§§§
	SchedaDinamicaFilter sf = istanzeCommand.getIstanzeFilter().getSchedaDinamicaFilter();
	if (sf != null) {
	    List<SchedaDinamicaRigheFilter> righe = sf.getRighe();
	    if (righe != null) {
		for (int i = 0; i < righe.size(); i++) {
		    if (i == idx.intValue()) {
			righe.remove(i);
			break;
		    }
		}
	    }
	    List<SchedaDinamicaRigheFilter> ricalcoloIndici = new ArrayList<SchedaDinamicaRigheFilter>(righe.size());
	    int i = 0;
	    for (SchedaDinamicaRigheFilter schedaDinamicaRigheFilter : righe) {
		ricalcoloIndici.add(i, schedaDinamicaRigheFilter);
		i++;
	    }
	    sf.setRighe(ricalcoloIndici);
	}
	setModelSearch(request, istanzeCommand, model);
	setPageAttributes(model);
	return "istanze/searchIstanze";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private void setModelSearch(HttpServletRequest request, IstanzeCommand istanzeCommand, Model model) {

	// -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SEARCH_DATI_CONC_AUT, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SEARCH_DATI_LOCALIZZ, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SEARCH_DATI_PROGETTO, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CERCAISTANZA_ALTRI_INDIRIZZI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ORIDIMANENTO_ISTANZE, DAOOrderTypeEnum.ASC.toString(), request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE, "data", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VALORE_STATO_ISTANZA, "stato_tutte", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISISTANZA, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_GESTIONE_RICERCHE, "0", request);
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISISTANZA_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISISTANZA, "1", request)));
	// Recupero il vaore che indica il campo per cui ordiniamo che è stato associato 
	//al parametro di cinfigurazione e lo setto al filtro
	String orderFiled = (String) request.getAttribute(WebConstants.CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE);
	istanzeCommand.getIstanzeFilter().setOrderBy(orderFiled);
	// Recupero il vaore che indica il campo di ricerca stato istanza associato alla configurazione
	// utente  e lo setto al filtro
	String statoIstanza = (String) request.getAttribute(WebConstants.CONF_UTENTE_VALORE_STATO_ISTANZA);
	model.addAttribute("CONF_UTENTE_NUMRECORDLISTE_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente("CONF_UTENTE_LISTISTANZA_NUMRECORDLISTE", "0", request)));
	istanzeCommand.getIstanzeFilter().getChiusura().getId().setCodicestato(statoIstanza);
	model.addAttribute("istanzeCommand", istanzeCommand);
	// -----FINE GESTIONE CONFIGURAZIONE UTENTE------------------------
	// -----INIZIO VERTICALIZZAZIONI ATTIVE----------------------
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_PEOPLE, request);
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA, request);
	// -----FINE VERTICALIZZAZIONI ATTIVE------------------------
	FilterTable ftable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	ftable.addOrder(FilterUtils.orderAsc("ordine"));
	List<Statiistanza> statiistanzaList = statiistanzaService.findByFilterTable(ftable);
	model.addAttribute("statiistanzaList", statiistanzaList);
	IstanzeOnLineHelper helper = istanzeService.findIstanzeOnline();
	model.addAttribute("istanzeOnLineHelper", helper);
	// Sezione per pratiche da assegna ad istruttore
	int countIstanzeDaAssegnaAdIstruttore = istanzeService
		.countIstanzeDaAssegnareAdIstruttoreByRespProcedimento(getCurrentlyAuthenticatedUserDetails());
	model.addAttribute("countIstanzeDaAssegnaAdIstruttore", countIstanzeDaAssegnaAdIstruttore);
	// Sezione per pratiche  assegnate ad istruttore
	int countIstanzeAssegnaAdIstruttore = istanzeService
		.countIstanzeAssegnareAdIstruttoreByRespProcedimento(getCurrentlyAuthenticatedUserDetails());
	model.addAttribute("countIstanzeAssegnaAdIstruttore", countIstanzeAssegnaAdIstruttore);
	List<ChiaveValoreBean<String, Integer>> stcAn = movimentiService.countMovimentiSTCConAnomalie();
	model.addAttribute("stcConAnomalie", stcAn);
    }

    @RequestMapping
    public void ajaxCambioStatoSieder(@RequestParam("codiceIstanza") Integer codiceistanza, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws UnsupportedEncodingException, IOException {

	Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	checkAccessoInformazioni(istanza, true);
	Set<Domandestc> domandestcs = istanza.getDomandestcs();
	boolean risultato = false;
	String messaggio = "";
	if (!domandestcs.isEmpty()) {
	    String iddomandaString = "";
	    iddomandaString = istanza.getNumeroistanza().replace("/", "-");
	    Verticalizzazioniparametri urlStatoIstanza = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_SIEDER,
		    WebConstants.VERTICALIZZAZIONE_SIEDER_URL_MODIFICA_STATO_PRATICA);
	    if (urlStatoIstanza != null && StringUtils.isNotBlank(urlStatoIstanza.getValore())) {
		String urlStato = urlStatoIstanza.getValore();
		urlStato = replaceSiederVariables(urlStato, istanza, iddomandaString, "RICEV");
		HttpClient cli = new HttpClient();
		HttpMethod method = null;
		int status = 0;
		try {
		    method = new GetMethod(urlStato);
		    status = cli.executeMethod(method);
		} catch (Exception e) {
		    log.error("ERRORE nell'invocazione del servizio stato istanza sieder", e);
		}
		if (status == 200) {
		    InputStream inputStream = method.getResponseBodyAsStream();
		    // Trasformo lo stream in una stringa (conterrà solo il codice)
		    //InputStream inputStream = method.getResponseBodyAsStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		    StringBuilder sb = new StringBuilder();
		    String line = null;
		    while ((line = reader.readLine()) != null) {
			sb.append(line);
		    }
		    inputStream.close();
		    String co = sb.toString();
		    log.debug("chiamata a stato istanza sieder {}", co);
		    try {
			Document doc = XMLUtils.parse(co);
			String nodeValue = doc.getFirstChild().getFirstChild().getNodeValue();
			log.debug(nodeValue);
			String codici[] = null;
			String codice = "KO";
			String descrizione = "KO";
			if (nodeValue.indexOf("###") >= 0) {
			    codici = nodeValue.split("###");
			    codice = codici[0];
			    descrizione = codici[1];
			} else {
			    codice = nodeValue;
			}
			if ("OK".equalsIgnoreCase(codice)) {
			    risultato = true;
			} else {
			    messaggio = "Errore: " + descrizione;
			}
		    } catch (ParserConfigurationException e) {
			e.printStackTrace();
		    } catch (SAXException e) {
			e.printStackTrace();
		    }
		}
	    }
	}
	if (!risultato) {
	    response.getOutputStream().write(("<b class=\"error_header\">L'operazione non ha avuto successo<p/>" + messaggio + "</b>").getBytes());
	} else {
	    response.getOutputStream().write("<b>Operazione avvenuta correttamente</b>".getBytes());
	}
    }

    @RequestMapping
    public String visualizzaAreeLDP(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response) {

	Istanze i = istanzeService.findById(new PkId(codice));
	checkAccessoInformazioni(i, false);
	ComplexTypeAreeUsoPubblico s = ldpWsClient.getStruttutaDatiOccupazioneSuoloByIdentificativo(i);
	// MOCK
	//	s = new ComplexTypeAreeUsoPubblico();
	//	s.setTipologia("tipologia");
	//	s.setGiorniSettimana("giorni settimana");
	//	s.setRipetizione("ripetizione");
	//	ArrayOfComplexTypePeriodo pas = new ArrayOfComplexTypePeriodo();
	//	ComplexTypePeriodo cp1 = new ComplexTypePeriodo();
	//	cp1.setInizio("19/01/2017");
	//	cp1.setFine("31/01/2017");
	//	ArrayOfComplexTypeArea ars = new ArrayOfComplexTypeArea();
	//	ComplexTypeArea cpa = new ComplexTypeArea();
	//	cpa.setDescrizione("Descrizione 1");
	//	cpa.setIdentificativo("identificativo 1");
	//	cpa.setMetriQuadrati("mq");
	//	ars.getComplexTypeArea().add(cpa);
	//	ComplexTypeArea cpa1 = new ComplexTypeArea();
	//	cpa1.setDescrizione("Descrizione 2");
	//	cpa1.setIdentificativo("identificativo 2");
	//	cpa1.setMetriQuadrati("mq");
	//	ars.getComplexTypeArea().add(cpa1);
	//	cp1.setAAree(ars);
	//	pas.getComplexTypePeriodo().add(cp1);
	//	ComplexTypePeriodo cp2 = new ComplexTypePeriodo();
	//	cp2.setInizio("19/01/2017");
	//	cp2.setFine("31/01/2017");
	//	ArrayOfComplexTypeArea ars2 = new ArrayOfComplexTypeArea();
	//	ComplexTypeArea cap22 = new ComplexTypeArea();
	//	cap22.setDescrizione("Descrizione 1");
	//	cap22.setIdentificativo("identificativo 1");
	//	cap22.setMetriQuadrati("mq");
	//	ars2.getComplexTypeArea().add(cap22);
	//	ComplexTypeArea cap221 = new ComplexTypeArea();
	//	cap221.setDescrizione("Descrizione 2");
	//	cap221.setIdentificativo("identificativo 2");
	//	cap221.setMetriQuadrati("mq");
	//	ars2.getComplexTypeArea().add(cap221);
	//	cp2.setAAree(ars2);
	//	s.setAPeriodi(pas);
	//	pas.getComplexTypePeriodo().add(cp2);
	if (s != null) {
	    model.addAttribute("dettaglio", s);
	    if (s.getAPeriodi() != null && s.getAPeriodi().getComplexTypePeriodo() != null && s.getAPeriodi().getComplexTypePeriodo().size() > 0) {
		List<ComplexTypePeriodo> periodis = s.getAPeriodi().getComplexTypePeriodo();
		model.addAttribute("periodis", periodis);
	    }
	}
	model.addAttribute("istanza", i);
	return "istanze/visualizzaAreeLDP";
    }

    @RequestMapping
    public String eliminaAreaLDP(@RequestParam("codiceIstanza") Integer codice, @RequestParam("identificativo") String identificativo,
	    @RequestParam("inizio") String inizio, @RequestParam("fine") String fine, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	Istanze i = istanzeService.findById(new PkId(codice));
	checkAccessoInformazioni(i, false);
	boolean ok = ldpWsClient.deleteAreaOnPeriodo(i, identificativo, inizio, fine);
	ldpWsClient.getDatiOccupazioneSuoloByIdentificativo(i);
	String msg = "02";
	if (!ok) {
	    msg = "03";
	}
	return "redirect:visualizzaAreeLDP.htm?codice=" + codice + "&status_msg=" + msg;
    }

    @RequestMapping
    public String updatePrendiIncarico(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Integer codiceIstanza = istanzeCommand.getEntity().getId().getCodice();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	try {
	    if (istanza.getOperatoreInCarico() != null) {
		throw new SecurityException("La pratica è stata già presa in carico!");
	    }
	    istanzeService.updatePrendiInCarico(codiceIstanza, responsabile.getId().getCodice());
	    String messaggio = "L'operatore " + responsabile + " ha preso in carico la pratica " + istanza;
	    Istanzeeventi evt = new Istanzeeventi();
	    evt.setFlagLetto(Boolean.TRUE);
	    evt.setIstanze(istanza);
	    evt.setDescrizione(messaggio);
	    evt.setSoftware(istanza.getSoftware());
	    Categorieeventibase ceb = new Categorieeventibase();
	    ceb.setId(IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI);
	    evt.setCategorieeventibase(ceb);
	    istanzeeventiService.insert(evt);
	    LoggerModificheIstanze.log("#PRESAINCARICO#" + messaggio);
	} catch (Exception e) {
	    log.error("#PRESAINCARICO# {}", e);
	    FlashMessages.getWarnings().add("Errore durante l'operazione: " + e.getMessage());
	    return "redirect:view.htm?codice=" + istanza.getId().getCodice() + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + istanza.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String updateRimuoviPresaIncarico(Model model, @ModelAttribute("istanzeCommand") IstanzeCommand istanzeCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Integer codiceIstanza = istanzeCommand.getEntity().getId().getCodice();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	try {
	    istanzeService.updatePrendiInCarico(codiceIstanza, null);
	    String messaggio = "L'operatore " + responsabile + " ha rimosso lo stato di presa in carico della pratica " + istanza;
	    Istanzeeventi evt = new Istanzeeventi();
	    evt.setFlagLetto(Boolean.TRUE);
	    evt.setIstanze(istanza);
	    evt.setDescrizione(messaggio);
	    evt.setSoftware(istanza.getSoftware());
	    Categorieeventibase ceb = new Categorieeventibase();
	    ceb.setId(IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI);
	    evt.setCategorieeventibase(ceb);
	    istanzeeventiService.insert(evt);
	    LoggerModificheIstanze.log("#PRESAINCARICO#" + messaggio);
	} catch (Exception e) {
	    log.error("#PRESAINCARICO# {}", e);
	    FlashMessages.getWarnings().add("Errore durante l'operazione: " + e.getMessage());
	    return "redirect:view.htm?codice=" + istanza.getId().getCodice() + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + istanza.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String popupViewPercorsoMaps(@RequestParam(value = "codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) {

	return "";
    }

    @RequestMapping
    public String stampe(@RequestParam(value = "codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	PkId id = new PkId(codiceIstanza);
	Istanze istanza = istanzeService.findById(id);
	IstanzeCommand command = new IstanzeCommand();
	command.setEntity(istanza);
	model.addAttribute("istanzeCommand", command);
	return "istanze/stampe";
    }

    @RequestMapping
    public void createLetteraTipo(@RequestParam(value = "codiceLetteraTipo") Integer codicelettera,
	    @RequestParam(value = "codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	byte[] out = null;
	Date date = new Date();
	Letteretipo letteretipo = letteretipoService.findById(new PkId(codicelettera));
	String cType = contenttypesService.findMimeTypeByFileName(letteretipo.getFile().getNomefile());
	DocumentMergeHelper userData = populateFromRequest(request);
	out = documentMergeService.eseguiSostituzioniBaseDocumento(codicelettera, codiceIstanza, codiceMovimento, userData);
	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
	response.setHeader("Content-transfer-encoding", "binary");
	response.setContentLength(out.length);
	response.setContentType(cType);
	if (letteretipo.getFile().getNomefile().toLowerCase().endsWith(".odt")) {
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + date.getTime() + "_" + letteretipo.getDescrizione() + ".odt");
	} else if (letteretipo.getFile().getNomefile().toLowerCase().endsWith(".rtf")) {
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + date.getTime() + "_" + letteretipo.getDescrizione() + ".rtf");
	}
	ServletOutputStream outStream = response.getOutputStream();
	outStream.write(out);
	outStream.flush();
    }

    @SuppressWarnings("unchecked")
    private DocumentMergeHelper populateFromRequest(HttpServletRequest request) {

	DocumentMergeHelper dmh = new DocumentMergeHelper();
	Map<String, String[]> allMap = request.getParameterMap();
	for (String key : allMap.keySet()) {
	    String valore = request.getParameter(key);
	    dmh.addParam(key.toUpperCase(), valore);
	}
	return dmh;
    }

    @Autowired
    private LDPWsClient LDPWsClient;

    @RequestMapping
    public void ajaxSetNumeroPraticaLDP(@RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	Istanze entity = istanzeService.findById(new PkId(codiceIstanza));
	List<Domandestc> findByIstanza = domandestcService.findByIstanza(codiceIstanza);
	LDPWsClient.setNumeroPratica(entity, findByIstanza.get(0));
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping
    public String listComunicazioniM(Model model, HttpServletRequest request) {

	ComunicazioniMassiveModel dettaglioComunicazioni = new ComunicazioniMassiveModel();
	dettaglioComunicazioni.setAlias(ORMHelper.getIdcomuneAlias());
	dettaglioComunicazioni.setSoftware(ORMHelper.getSoftware());
	dettaglioComunicazioni.setManifestazione("Comunicazioni istanze");
	List<ListaComunicazioniResoconti> elenco = this.comunicazioniMassiveMService.creaListaTestataGen(QueriesConstants.ISTANZECOMSELECT,
		QueriesConstants.toArray(ORMHelper.getIdcomune(), ORMHelper.getSoftware()), "fkid_testata");
	dettaglioComunicazioni.setComunicazioni(elenco);
	model.addAttribute("schedulerAttivo", true);
	model.addAttribute("dettaglio", dettaglioComunicazioni);
	model.addAttribute("urlnuovacomunicazione", "''");
	return "istanze/listComunicazioniM";
    }

    @RequestMapping
    public String viewComunicazioneM(@RequestParam("idcomunicazione") Integer idComunicazione, Model model, HttpServletRequest request) {

	ComunicazioneMassivaGenModel comunicazione = this.comunicazioniMassiveService.getComunicazioneByIdGenTestata(idComunicazione);
	if (!StringUtils.isBlank(comunicazione.getServizioAppio())) {
	    AppIoServizi servizio = comunicazioniUtilsGenService.findAppIoServizioById(comunicazione.getServizioAppio());
	    if (servizio != null) {
		comunicazione.setServizioAppio(servizio.getDescrizione());
	    }
	}
	//Così manteniamo l'ordine
	if (comunicazione.getDestinatari() != null && !comunicazione.getDestinatari().isEmpty()) {
	    Collections.sort(comunicazione.getDestinatari(), new Comparator<ComunicazioneMassivaRigaModel>() {

		@Override
		public int compare(ComunicazioneMassivaRigaModel o1, ComunicazioneMassivaRigaModel o2) {

		    String n1 = o1.getNominativo() != null ? o1.getNominativo() : "";
		    String n2 = o2.getNominativo() != null ? o2.getNominativo() : "";
		    return n1.compareTo(n2);
		}
	    });
	}
	comunicazione.setContesto("istanze");
	model.addAttribute("manifestazione", comunicazione.getDescrizione());
	model.addAttribute("dettaglio", comunicazione);
	model.addAttribute("schedulerAttivo", true);
	return "istanze/viewComunicazioneM";
    }

    @RequestMapping
    public String createComunicazioneMa(Model model, HttpServletRequest request, HttpServletResponse response) {

	ComunicazioniGenCommand cmd = new ComunicazioniGenCommand();
	//	CommissioneModel testata = commissioniService.getCommissione(idCommissione);
	//	cmd.setCommissione(testata);  POTREBBE NON SERVIRCI IN QUESTO CASO
	List<IParametriProtocolloPerEnteHelper> helps = cmd.getProtocollaParametriCommand().getParametriPerEnte();
	Set<ISoftwareComuneData> softwareAndComune = null;
	cmd.setCodiciComuneUguale(false);
	if (helps.isEmpty()) {
	    ConfigurazioniComunicazioneGen configurazioniComunicazioneGen = new ConfigurazioniComunicazioneGen(ContestoComunicazioneEnum.ISTANZE);
	    configurazioniComunicazioneGen.setFilter((IstanzeFilter) request.getSession().getAttribute(ISTANZE_FILTER_IN_SESSION));
	    List<ISoftwareComuneData> softCom = comunicazioniToGenService.getSoftwareAndComune(configurazioniComunicazioneGen);
	    helps = comunicazioniToGenService.popolaParametriProtocollazione(configurazioniComunicazioneGen, softCom);
	    softwareAndComune = new HashSet<ISoftwareComuneData>(softCom);
	    if (softwareAndComune != null && !softwareAndComune.isEmpty() && softwareAndComune.size() == 1) {
		ISoftwareComuneData next = softwareAndComune.iterator().next();
		cmd.setCodicecomuneComunicazione(next.getCodiceComune());
		cmd.setCodiciComuneUguale(true);
	    }
	}
	cmd.getProtocollaParametriCommand().setParametriPerEnte(helps);
	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	Set<Responsabilicomuni> responsabilicomuni = responsabiliService.findListResponsabilicomuni(responsabili);
	int i = 0;
	String[] codiceComuni = new String[responsabilicomuni.size()];
	for (Responsabilicomuni responsabilicomune : responsabilicomuni) {
	    codiceComuni[i] = responsabilicomune.getComune().getCodicecomune();
	    i++;
	}
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		codiceComuni);
	model.addAttribute("listMailConfig", listMailConfig);
	model.addAttribute("comunicazioniCommissioniCommand", cmd);
	model.addAttribute("sceltaTipoMailAnagrafeList", SceltaTipoMailAnagrafeEnum.asList());
	setParametriInModel(model, request, null, null);
	
	List<Map<String, String>> appioservizilist;
	if(cmd.isCodiciComuneUguale()){
	    appioservizilist = findAppioServiziList(cmd.getCodicecomuneComunicazione());
	}else{
	    appioservizilist = findAppioServiziList(null);
	}
	
	model.addAttribute("appioservizilist", appioservizilist);
	cmd.setRichiedente(true);
	cmd.setTipoInvioIstanze("anagrafe");
	cmd.setScegliMailChckN(true);
	return "istanze/createM";
    }
    
    private List<Map<String, String>> findAppioServiziList(String codicecomunecomunicazione){
	List<AppIoServizi> appioservizi = comunicazioniUtilsGenService.findAllAppIoServizi();
	List<Map<String, String>> appioservizilist = new ArrayList<Map<String, String>>();
	if (appioservizi != null) {
	    
	    if(codicecomunecomunicazione == null){
		Map<String, String> m = new HashMap<String, String>();
		m.put("id", "");
		m.put("descrizione", "");
		m.put("noservizidesc", "Non ci sono servizi disponibili, il codicecomune risulta null");
		appioservizilist.add(m);
		return appioservizilist;
	    }
	    
	    Set<String> codiciComune = new HashSet<String>();
	    codiciComune.add(codicecomunecomunicazione);
	    String currentSoftware = ORMHelper.getSoftware();
	    for (AppIoServizi servizi : appioservizi) {
		String idServizio = servizi.getId().getIdentificativoServizio();
		Map<String, AppIoServiziConfigRestResponse> configs = appIoServiziConfigService.findByIdServizioAndComuni(idServizio, codiciComune);
		for (Map.Entry<String, AppIoServiziConfigRestResponse> entry : configs.entrySet()) {
		    AppIoServiziConfigRestResponse config = entry.getValue();
		    if (config != null && StringUtils.equals(currentSoftware, config.getSoftware())) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", idServizio);
			m.put("descrizione", servizi.getDescrizione());
			appioservizilist.add(m);
		    }
		}
	    }
	    if(appioservizilist.isEmpty()){
		Map<String, String> m = new HashMap<String, String>();
		m.put("id", "");
		m.put("descrizione", "");
		m.put("noservizidesc", "Non ci sono servizi disponibili, per il comune " + codicecomunecomunicazione + " e software " + currentSoftware);
		appioservizilist.add(m);
		return appioservizilist;
	    }
	}
	return appioservizilist;
    }

    protected void setParametriInModel(Model model, HttpServletRequest request, String codiceComune, String software) {

	/*
	 * Controllo se è attiva la verticalizzazione con il modulo PROTOCOLLO_ATTIVO
	 */
	if (verticalizzazioniService.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE)) {
	    model.addAttribute("vert_prot_attivo", true);
	} else {
	    model.addAttribute("vert_prot_attivo", false);
	}
    }

    @RequestMapping
    public String insertComunicazioneMa(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniGenCommand comunicazioniCommissioniCommand, BindingResult result,
	    HttpServletRequest request, HttpServletResponse response) {

	try {
	    if (!comunicazioniCommissioniCommand.isScegliMailChckN() && !comunicazioniCommissioniCommand.isScegliAppioChckN()
		    && !comunicazioniCommissioniCommand.getProtocollaParametriCommand().isProtocolla()) {
		throw new Exception("Tipo comunicazione non valorizzato");
	    }
	    if (!comunicazioniCommissioniCommand.isRichiedente() && !comunicazioniCommissioniCommand.isIntermediario()) {
		throw new Exception("Tipo anagrafica non valorizzato");
	    }
	    String sceltaRaggruppamento = comunicazioniCommissioniCommand.getTipoInvioIstanze();
	    boolean isIstanzeGroup = false;
	    if (StringUtils.isBlank(sceltaRaggruppamento)) {
		throw new Exception("Raggruppamento non valorizzato");
	    } else {
		if ("istanze".equals(sceltaRaggruppamento)) {
		    isIstanzeGroup = true;
		}
	    }
	    Map<String, String> altriparametri = new HashMap<String, String>();
	    if (comunicazioniCommissioniCommand.isScegliMailChckN() || comunicazioniCommissioniCommand.isScegliAppioChckN()) {
		if (StringUtils.isBlank(comunicazioniCommissioniCommand.getOggettoEmail())) {
		    throw new RuntimeException("oggetto non valorizzato");
		}
		if (StringUtils.isBlank(comunicazioniCommissioniCommand.getBodyEmail())) {
		    throw new RuntimeException("body non valorizzato");
		}
		if (comunicazioniCommissioniCommand.isScegliAppioChckN()) {
		    try {
			if (comunicazioniCommissioniCommand.getOggettoEmail().getBytes("UTF-8").length < 10) {
			    throw new RuntimeException("L'oggetto non può avere lunghezza inferiore a 10");
			}
			if (comunicazioniCommissioniCommand.getOggettoEmail().getBytes("UTF-8").length > 120) {
			    throw new RuntimeException("L'oggetto non può avere lunghezza superiore a 120");
			}
			if (comunicazioniCommissioniCommand.getBodyEmail().getBytes("UTF-8").length < 80) {
			    throw new RuntimeException("Il corpo non può avere lunghezza inferiore a 80");
			}
		    } catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		    }
		}
		altriparametri.put("OGGETTOMAIL_NAME", comunicazioniCommissioniCommand.getOggettoEmail());
		altriparametri.put("BODYMAIL_NAME", comunicazioniCommissioniCommand.getBodyEmail());
	    }
	    if (!StringUtils.isBlank(request.getParameter("scegliMailChckN"))) {
		altriparametri.put(ConfigurazioneComunicazioniCommissioni.GESTIONE_SCELTA_MAIL_ANAGRAFE,
			comunicazioniCommissioniCommand.getConfiguraParametriMailCommand().getSceltaMailAnagrafe().getCodice());
	    }
	    if (!StringUtils.isBlank(request.getParameter("scegliAppioChckN"))) {
		altriparametri.put("APPIO_SERVIZIO", request.getParameter("servizioappio"));
	    }
	    if (!isIstanzeGroup) {
		comunicazioniCommissioniCommand.getAllegaticompilabili().clear();
		comunicazioniCommissioniCommand.setConvertiPDF(false);
	    }
	    if (comunicazioniCommissioniCommand.isScegliAppioChckN() && !comunicazioniCommissioniCommand.isScegliMailChckN()
		    && !comunicazioniCommissioniCommand.getProtocollaParametriCommand().isProtocolla()) {
		comunicazioniCommissioniCommand.getAllegatiFissi().clear();
		comunicazioniCommissioniCommand.getAllegaticompilabili().clear();
		comunicazioniCommissioniCommand.getFirmatari().clear();
		comunicazioniCommissioniCommand.setConvertiPDF(false);
	    }
	    ConfigurazioniComunicazioneGen c = ComunicazioniMassUtils.popolaConfigurazioneComunicazioniCommissioni(comunicazioniCommissioniCommand,
		    ContestoComunicazioneEnum.ISTANZE, 0, altriparametri);
	    c.setIstanzeGroup(isIstanzeGroup);
	    if (!"0".equals(comunicazioniCommissioniCommand.getScegliMovimento())) {
		c.setMovimenti(true);
		c.setTipomovimento(comunicazioniCommissioniCommand.getMovimento().getTipomovimento().getId().getTipomovimento());
		c.setAmministrazione(comunicazioniCommissioniCommand.getMovimento().getAmministrazioni().getId().getCodice());
		if (StringUtils.isBlank(c.getTipomovimento())) {
		    throw new RuntimeException("Tipo movimento deve essere validato");
		}
		if (c.getAmministrazione() == null) {
		    throw new RuntimeException("Amministrazione deve essere validato");
		}
	    }
	    c.setFilter((IstanzeFilter) request.getSession().getAttribute(ISTANZE_FILTER_IN_SESSION));
	    c.setRichiedente(comunicazioniCommissioniCommand.isRichiedente());
	    c.setIntermediario(comunicazioniCommissioniCommand.isIntermediario());
	    int idTestata = comunicazioniMassiveMService.creaNuovaComunicazione(c);
	    return "redirect:../istanze/viewComunicazioneM.htm?idcomunicazione=" + idTestata;
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, comunicazioniCommissioniCommand, e);
	    popolaModelMa(model, comunicazioniCommissioniCommand);
	    if (!StringUtils.isBlank(comunicazioniCommissioniCommand.getMovimento().getTipomovimento().getId().getTipomovimento())) {
		comunicazioniCommissioniCommand.getMovimento().setTipomovimento(tipiMovimentoService
			.findById(new TipimovimentoId(comunicazioniCommissioniCommand.getMovimento().getTipomovimento().getId().getTipomovimento())));
	    }
	    return "istanze/createM";
	}
    }

    private void popolaModelMa(Model model, ComunicazioniGenCommand cmd) {

	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	Set<Responsabilicomuni> responsabilicomuni = responsabiliService.findListResponsabilicomuni(responsabili);
	int i = 0;
	String[] codiceComuni = new String[responsabilicomuni.size()];
	for (Responsabilicomuni responsabilicomune : responsabilicomuni) {
	    codiceComuni[i] = responsabilicomune.getComune().getCodicecomune();
	    i++;
	}
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		codiceComuni);
	model.addAttribute("listMailConfig", listMailConfig);
	model.addAttribute("sceltaTipoMailAnagrafeList", SceltaTipoMailAnagrafeEnum.asList());
	if (verticalizzazioniService.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE)) {
	    model.addAttribute("vert_prot_attivo", true);
	} else {
	    model.addAttribute("vert_prot_attivo", false);
	}
	for (IParametriProtocolloPerEnteHelper param : cmd.getProtocollaParametriCommand().getParametriPerEnte()) {
	    if (param.getAmmMittente() != null && param.getAmmMittente().getId() != null) {
		Amministrazioni ammMitente = amministrazioniService.findById(new PkId(param.getAmmMittente().getId()));
		if (ammMitente == null || ammMitente.getId() == null || ammMitente.getId().getCodice() == null
			|| StringUtils.isBlank(ammMitente.getAmministrazione())) {
		    continue;
		}
		param.setAmmMittente(new IdentificativoDescrizioneBean(ammMitente.getId().getCodice(), ammMitente.getAmministrazione()));
	    }
	}
	List<Map<String, String>> appioservizilist;
	if(cmd.isCodiciComuneUguale()){
	    appioservizilist = findAppioServiziList(cmd.getCodicecomuneComunicazione());
	}else{
	    appioservizilist = findAppioServiziList(null);
	}
	model.addAttribute("appioservizilist", appioservizilist);
    }

    @RequestMapping
    public void ajaxAggiungiAllegatiCompilabili(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniGenCommand comunicazioniCommissioniCommand,
	    @RequestParam("codiceLetteretipo") Integer codiceLetteretipo, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	comunicazioniUtilsGenService.ajaxAggiungiAllegatiCompilabili(model, comunicazioniCommissioniCommand, codiceLetteretipo, request, response);
	return;
    }

    @RequestMapping
    public void ajaxRimuoviAllegatoCompilabile(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniGenCommand comunicazioniCommissioniCommand,
	    @RequestParam("codiceLetteretipo") Integer codiceLetteretipo, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	comunicazioniUtilsGenService.ajaxRimuoviAllegatoCompilabile(model, comunicazioniCommissioniCommand, codiceLetteretipo, request, response);
    }

    @RequestMapping
    public void ajaxAggiungiFirmatario(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniGenCommand comunicazioniCommissioniCommand,
	    @RequestParam("codiceFirmatario") Integer codiceFirmatario, HttpServletRequest request, HttpServletResponse response) throws IOException {

	comunicazioniUtilsGenService.ajaxAggiungiFirmatario(model, comunicazioniCommissioniCommand, codiceFirmatario, request, response);
	return;
    }

    @RequestMapping
    public void ajaxRimuoviFirmatario(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniGenCommand comunicazioniCommissioniCommand,
	    @RequestParam("codiceFirmatario") Integer codiceFirmatario, HttpServletRequest request, HttpServletResponse response) throws IOException {

	comunicazioniUtilsGenService.ajaxRimuoviFirmatario(model, comunicazioniCommissioniCommand, codiceFirmatario, request, response);
    }

    @RequestMapping
    public void ajaxSetParametriProtocollazione(
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniGenCommand comunicazioniCommissioniCommand, HttpServletRequest request,
	    HttpServletResponse response) throws IOException, JAXBException {

	comunicazioniUtilsGenService.ajaxSetParametriProtocollazione(comunicazioniCommissioniCommand, request, response);
    }

    @RequestMapping
    public void getDataMailFromMailTipoById(@RequestParam("mailTipoId") String mailTipoId, HttpServletRequest request, HttpServletResponse response) {

	comunicazioniUtilsGenService.getDataMailFromMailTipoById(mailTipoService, mailTipoId, request, response);
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxGetRigaDettagliata(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	comunicazioniUtilsGenService.ajaxGetRigaDettagliata(model, request, response, ContestoComunicazioneEnum.ISTANZE);
    }

    @RequestMapping
    public String messaggiRabbit(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	Istanze i = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(i, false);
	userHasRole(true, "MESSAGGI_RABBIT_ISTANZE");
	model.addAttribute("istanza", i);
	return "istanze/messaggirabbit";
    }

    @RequestMapping
    public void ajaxInviaMessaggiorabbit(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("azione") String azione,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, "MESSAGGI_RABBIT_ISTANZE");
	Istanze entity = istanzeService.findById(new PkId(codiceIstanza));
	//	<li class="invia_messaggio" data-azione="nuova_pratica">Nuova pratica</li>
	//	<li class="invia_messaggio" data-azione="soggetti_aggiornati">Soggetti aggiornati</li>
	//	<li class="invia_messaggio" data-azione="cambio_stato">Cambio stato</li>
	if (azione.equalsIgnoreCase("cambio_stato")) {
	    codaMessaggiRabbitService.insertCambioStato(codiceIstanza);
	} else if (azione.equalsIgnoreCase("nuova_pratica")) {
	    codaMessaggiRabbitService.insertNotificaPraticaNuova(codiceIstanza);
	} else if (azione.equalsIgnoreCase("soggetti_aggiornati")) {
	    NotificaSoggettiIstanzaAggiornatiRequest req = new NotificaSoggettiIstanzaAggiornatiRequest(codiceIstanza,
		    entity.getSoftware().getCodice(), entity.getUuid());
	    codaMessaggiRabbitService.insertSoggettiAggiornati(req);
	}
    }
}
