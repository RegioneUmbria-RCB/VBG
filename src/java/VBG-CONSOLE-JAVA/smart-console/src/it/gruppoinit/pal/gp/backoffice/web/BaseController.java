package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.backoffice.web.util.CustomStringTrimmerEditor;
import it.gruppoinit.pal.gp.backoffice.web.util.IntegerToCollectionPropertyEditor;
import it.gruppoinit.pal.gp.backoffice.web.util.ResponsabiliClpermmenuPropertyEditor;
import it.gruppoinit.pal.gp.backoffice.web.util.SoftwareClassEditor;
import it.gruppoinit.pal.gp.backoffice.web.util.StringToSetPropertyEditor;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Clmenu;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.TipoDownload;
import it.gruppoinit.pal.gp.core.domain.web.SessionDetails;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.EntityValidationException;
import it.gruppoinit.pal.gp.core.service.exception.ExceptionHelper;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.jmesa.custom.AllegatoCommisFilterMatcherMap;
import org.jmesa.custom.AmministrazioniFilterMatcherMap;
import org.jmesa.custom.AnagarfeinterdetteFilterMatcherMap;
import org.jmesa.custom.AnagrafeFilterMatcherMap;
import org.jmesa.custom.AreedettagliFilterMatcherMap;
import org.jmesa.custom.AttivitaFilterMatcherMap;
import org.jmesa.custom.BachecalavorocercaFilterMatcherMap;
import org.jmesa.custom.BachecalavorooffroFilterMatcherMap;
import org.jmesa.custom.CommissioniediliziaRFilterMatcherMap;
import org.jmesa.custom.CommissioniediliziaTFilterMatcherMap;
import org.jmesa.custom.ConcessionicausaliFilterMatcherMap;
import org.jmesa.custom.DataAlbopubblicazioniFilterMatcherMap;
import org.jmesa.custom.DataConcessioniFilterMatcherMap;
import org.jmesa.custom.DateBandiFilterMatcherMap;
import org.jmesa.custom.DateNotificheFilterMatcherMap;
import org.jmesa.custom.DateRateNonPagateFilterMatcherMap;
import org.jmesa.custom.DateRegImportiFilterMatcherMap;
import org.jmesa.custom.DateRegistrazioniFilterMatcherMap;
import org.jmesa.custom.DocumentiAnagrafeFilterMatcherMap;
import org.jmesa.custom.DocumentiistanzaFilterMatcherMap;
import org.jmesa.custom.EmailAnagrafeFilterMatcherMap;
import org.jmesa.custom.FisicaGiuridicaCellEditor;
import org.jmesa.custom.ForumFilterMatcherMap;
import org.jmesa.custom.MenufoFilterMatcherMap;
import org.jmesa.custom.MercatiFilterMatcherMap;
import org.jmesa.custom.MercatidattivitaistatFilterMatcherMap;
import org.jmesa.custom.MessaggiFilterMatcherMap;
import org.jmesa.custom.MovimentimailFilterMatcherMap;
import org.jmesa.custom.PariDispariCellEditor;
import org.jmesa.custom.QuesitiFilterMatcherMap;
import org.jmesa.custom.RegistrazioneInOutFilterMatcherMap;
import org.jmesa.custom.ResponsabiliCellEditor;
import org.jmesa.custom.ResponsabiliFilterMatcherMap;
import org.jmesa.custom.RuoliFilterMatcherMap;
import org.jmesa.custom.ScadenzaAvvisoCellEditor;
import org.jmesa.custom.ScadenzeFilterMatcherMap;
import org.jmesa.custom.SettoriFilterMatcherMap;
import org.jmesa.custom.SiNoCellEditor;
import org.jmesa.custom.SiNoFilterAlberoProcDocCatMatcherMap;
import org.jmesa.custom.SiNoFilterMatcherMap;
import org.jmesa.custom.SiNoTecnicoCellEditor;
import org.jmesa.custom.SoggettiAnagraficaFilterMatcherMap;
import org.jmesa.custom.SorteggidettaglioFilterMatcherMap;
import org.jmesa.custom.SorteggitestataFilterMatcherMap;
import org.jmesa.custom.StatiistanzaFilterMatcherMap;
import org.jmesa.custom.StatoApertaChiusaCellEditor;
import org.jmesa.custom.TaskschedulerFilterMatcherMap;
import org.jmesa.custom.TipibandoCellEditor;
import org.jmesa.custom.TipicausalioneriFilterMatcherMap;
import org.jmesa.custom.TipoEntrataUscitaCellEditor;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Limit;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.ExportComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.csv.CsvComponentFactory;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.editor.DateWithTimeCellEditor;
import org.jmesa.view.editor.GroupCellEditor;
import org.jmesa.view.editor.NumberCellEditor;
import org.jmesa.web.HttpServletRequestSpringWebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

/**
 * Classe con metodi di base per i controller
 * 
 * 
 * @author Francesco Palenga
 * @author Fabrizio Corsetti
 */
public abstract class BaseController<E> {

    private static final String AUTORIZZATO_ALLA_CANCELLAZIONE_SESSION_PARAM = "AUTORIZZATO_ALLA_CANCELLAZIONE";
    private static final Logger log = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private ComuniassociatiService comuniassociatiService;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	dateFormat.setLenient(false);
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	binder.registerCustomEditor(String.class, "scFascclassifica", new CustomStringTrimmerEditor(true, false));
	binder.registerCustomEditor(String.class, "scProtclassifica", new CustomStringTrimmerEditor(true, false));
	binder.registerCustomEditor(String.class, "classifica", new CustomStringTrimmerEditor(true, false));
	binder.registerCustomEditor(String.class, "classificaFascicolo", new CustomStringTrimmerEditor(true, false));
	binder.registerCustomEditor(String.class, "protUo", new CustomStringTrimmerEditor(true, false));
	binder.registerCustomEditor(String.class, "protRuolo", new CustomStringTrimmerEditor(true, false));
	NumberFormat format = NumberFormat.getInstance(Locale.ITALY);
	DecimalFormat decFormat = (DecimalFormat) format;
	decFormat.applyPattern("#0.00###");
	decFormat.setGroupingUsed(false);
	binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, decFormat, true));
	// to actually be able to convert Multipart instance to byte[]
	// we have to register a custom editor
	binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	binder.registerCustomEditor(Set.class, "entity.softwareAbilitati", new StringToSetPropertyEditor<Responsabilisoftware, Software>(
		Responsabilisoftware.class, Software.class, "software.codice"));
	binder.registerCustomEditor(Set.class, "entity.responsabilicomunis", new StringToSetPropertyEditor<Responsabilicomuni, Comuni>(
		Responsabilicomuni.class, Comuni.class, "comune.codicecomune"));
	binder.registerCustomEditor(Software.class, "entity.scadSoftware", new SoftwareClassEditor());
	binder.registerCustomEditor(Set.class, "nuoviPermessi", new ResponsabiliClpermmenuPropertyEditor());
	binder.registerCustomEditor(Set.class, "protocolloFlussos", new StringToSetPropertyEditor<ProtocolloFlusso, String>(ProtocolloFlusso.class,
		null, "codice"));
	binder.registerCustomEditor(Set.class, "tipoDownloads", new StringToSetPropertyEditor<TipoDownload, String>(TipoDownload.class, null,
		"codice"));
	binder.registerCustomEditor(ArrayList.class, "menuAbilitati", new IntegerToCollectionPropertyEditor<Clmenu>(Clmenu.class, "id"));
    }

    /**
     * Metodo da utilizzare nei controller che devono scrivere sulle impostazioni utente.<br>
     * Come esempio vedere la jsp /WEB-INF/schedacontabile/form.jsp la funzione javascript
     * saveUserPreference(nomeparametro, valore)
     * 
     * <PRE>
     * 
     * function saveUserPreference(nomeparametro, valore){
     * 		new Ajax.Request('&lt;%=request.getContextPath()%&gt;/schedacontabile/salvaPreferenza.htm', {
     * 			  method: 'post',
     * 			  parameters: {nomeparametro: nomeparametro,valore: valore},
     * 			  onSuccess: function(transport){ },
     * 			  onFailure: function(transport){ 
     * 				var response = transport.responseText;
     * 			    alert(response); }						    		 
     * 			  });			
     * 	}
     * </PRE>
     * 
     * siccome schedacontabile è il controller che estende basecontroller l'Ajax.Request è vincolato a chiamare il path
     * 
     * <pre>
     * '&lt;%=request.getContextPath()%&gt;/schedacontabile/salvaPreferenza.htm'
     * </pre>
     * 
     * quindi se ad esempio si vuole salvare una preferenza utente per il controller assenzeController nella funzione
     * javascript è necessario che il controller estenda <b>BaseController</b> e nella funzione javascript sostituire
     * <i><b>schedacontabile</b></i> con <i><b>assenze</b></i>.<br>
     * 
     * 
     * 
     * @param nomeparametro
     *            il nome parametro per cui salvare il valore per l'utente corrente
     * @param valore
     *            il valore da salvare
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     */
    @RequestMapping
    public void salvaPreferenza(@RequestParam("nomeparametro") String nomeparametro, @RequestParam("valore") String valore,
	    HttpServletRequest request, HttpServletResponse response) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	// configurazioneutenteService.setBindingResult(result);
	ConfigurazioneutenteId showConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(), nomeparametro);
	Configurazioneutente parametroConfigurazioneUtente = configurazioneutenteService.findById(showConfId);
	if (parametroConfigurazioneUtente == null) {
	    // .. inserisco i valori di default
	    parametroConfigurazioneUtente = new Configurazioneutente();
	    parametroConfigurazioneUtente.setId(showConfId);
	    parametroConfigurazioneUtente.setResponsabile(responsabile);
	    parametroConfigurazioneUtente.setValore(valore);
	    configurazioneutenteService.insert(parametroConfigurazioneUtente);
	} else {
	    parametroConfigurazioneUtente.setValore(valore);
	    configurazioneutenteService.update(parametroConfigurazioneUtente);
	}
	try {
	    if (null != response) {
		response.getWriter().write("");
	    }
	} catch (IOException e) {
	    log.error("Errore durante il salvataggio della preferenza:{}", e.getMessage());
	}
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
    protected boolean createJMesaExport(HttpServletRequest request, HttpServletResponse response, Collection<?> items) {

	boolean export = false;
	// Ottengo dalla request l'id della table facade(JMesa)
	String idTag = request.getParameter("table_id");
	// genero una table facade
	HttpServletRequestSpringWebContext httpServletRequestSpringWebContext = new HttpServletRequestSpringWebContext(request);
	TableFacade tableFacade = TableFacadeFactory.createSpringTableFacade(idTag, httpServletRequestSpringWebContext);
	tableFacade.setStateAttr("restore");
	tableFacade.setItems(items);
	tableFacade.setExportTypes(response, ExportType.CSV, ExportType.EXCEL, ExportType.PDFP);
	// Gestione filterMatcher
	if (!(idTag == null)) {
	    if (idTag.equals("bandi_id")) {
		tableFacade.addFilterMatcherMap(new DateBandiFilterMatcherMap());
	    }
	    if (idTag.equals("registrazioni_id")) {
		tableFacade.addFilterMatcherMap(new DateRegistrazioniFilterMatcherMap());
	    }
	    if (idTag.equals("rateNonPagate_id")) {
		tableFacade.addFilterMatcherMap(new DateRateNonPagateFilterMatcherMap());
	    }
	    if (idTag.equals("registrazioniImporti_id")) {
		tableFacade.addFilterMatcherMap(new DateRegImportiFilterMatcherMap());
	    }
	    if (idTag.equals("registrazioniInOut_id")) {
		tableFacade.addFilterMatcherMap(new RegistrazioneInOutFilterMatcherMap());
	    }
	    if (idTag.equals("vwconcessionilista_id")) {
		tableFacade.addFilterMatcherMap(new DataConcessioniFilterMatcherMap());
	    }
	    if (idTag.equals("notiche_id")) {
		tableFacade.addFilterMatcherMap(new DateNotificheFilterMatcherMap());
	    }
	    if (idTag.equals("amministrazioni_id")) {
		tableFacade.addFilterMatcherMap(new AmministrazioniFilterMatcherMap());
	    }
	    if (idTag.equals("registrazioniCausali_id")) {
		tableFacade.addFilterMatcherMap(new SiNoFilterMatcherMap());
	    }
	    if (idTag.equals("areedettagli_id")) {
		tableFacade.addFilterMatcherMap(new AreedettagliFilterMatcherMap());
	    }
	    if (idTag.equals("alberoprocdocumenticat_id")) {
		tableFacade.addFilterMatcherMap(new SiNoFilterAlberoProcDocCatMatcherMap());
	    }
	    if (idTag.equals("bandiallegati_id")) {
		tableFacade.addFilterMatcherMap(new DateBandiFilterMatcherMap());
	    }
	    if (idTag.equals("alboPubblicazioni_id")) {
		tableFacade.addFilterMatcherMap(new DataAlbopubblicazioniFilterMatcherMap());
	    }
	    if (idTag.equals("ruoli_id")) {
		tableFacade.addFilterMatcherMap(new RuoliFilterMatcherMap());
	    }
	    if (idTag.equals("statiistanza_id")) {
		tableFacade.addFilterMatcherMap(new StatiistanzaFilterMatcherMap());
	    }
	    if (idTag.equals("attivita_id")) {
		tableFacade.addFilterMatcherMap(new AttivitaFilterMatcherMap());
	    }
	    if (idTag.equals("settori_id")) {
		tableFacade.addFilterMatcherMap(new SettoriFilterMatcherMap());
	    }
	    if (idTag.equals("movimentimail_id")) {
		tableFacade.addFilterMatcherMap(new MovimentimailFilterMatcherMap());
	    }
	    if (idTag.equals("mercati_id")) {
		tableFacade.addFilterMatcherMap(new MercatiFilterMatcherMap());
	    }
	    if (idTag.equals("mercatidattivitaistat_id")) {
		tableFacade.addFilterMatcherMap(new MercatidattivitaistatFilterMatcherMap());
	    }
	    if (idTag.equals("tipicausalioneri_id")) {
		tableFacade.addFilterMatcherMap(new TipicausalioneriFilterMatcherMap());
	    }
	    if (idTag.equals("resp_id")) {
		tableFacade.addFilterMatcherMap(new ResponsabiliFilterMatcherMap());
	    }
	    if (idTag.equals("forum_id")) {
		tableFacade.addFilterMatcherMap(new ForumFilterMatcherMap());
	    }
	    if (idTag.equals("messaggi_id")) {
		tableFacade.addFilterMatcherMap(new MessaggiFilterMatcherMap());
	    }
	    if (idTag.equals("quesiti_id")) {
		tableFacade.addFilterMatcherMap(new QuesitiFilterMatcherMap());
	    }
	    if (idTag.equals("oneri_id")) {
		tableFacade.addFilterMatcherMap(new QuesitiFilterMatcherMap());
	    }
	    if (idTag.equals("bachecalavorooffro_id")) {
		tableFacade.addFilterMatcherMap(new BachecalavorooffroFilterMatcherMap());
	    }
	    if (idTag.equals("bachecalavorocerca_id")) {
		tableFacade.addFilterMatcherMap(new BachecalavorocercaFilterMatcherMap());
	    }
	    if (idTag.equals("scadenze_id")) {
		tableFacade.addFilterMatcherMap(new ScadenzeFilterMatcherMap());
	    }
	    if (idTag.equals("emailanagr_id")) {
		tableFacade.addFilterMatcherMap(new EmailAnagrafeFilterMatcherMap());
	    }
	    if (idTag.equals("anagrafedocumenti_id")) {
		tableFacade.addFilterMatcherMap(new DocumentiAnagrafeFilterMatcherMap());
	    }
	    if (idTag.equals("anagrafe_id")) {
		tableFacade.addFilterMatcherMap(new AnagrafeFilterMatcherMap());
	    }
	    if (idTag.equals("documentiistanza_id")) {
		tableFacade.addFilterMatcherMap(new DocumentiistanzaFilterMatcherMap());
	    }
	    if (idTag.equals("commissioniediliziet_id")) {
		tableFacade.addFilterMatcherMap(new CommissioniediliziaTFilterMatcherMap());
	    }
	    if (idTag.equals("commissioniedilizie_id")) {
		tableFacade.addFilterMatcherMap(new CommissioniediliziaTFilterMatcherMap());
	    }
	    if (idTag.equals("commedilizieallegati_id")) {
		tableFacade.addFilterMatcherMap(new AllegatoCommisFilterMatcherMap());
	    }
	    //"commissioniediliziet/listCommissioniedilizieR.jsp"
	    if (idTag.equals("commissioniedilizier_id")) {
		tableFacade.addFilterMatcherMap(new CommissioniediliziaRFilterMatcherMap());
	    }
	    // "menufo/list.jsp"
	    if (idTag.equals("menu_id")) {
		tableFacade.addFilterMatcherMap(new MenufoFilterMatcherMap());
	    }
	    // "menuinfo/list.jsp"
	    if (idTag.equals("menuinfo_id")) {
		tableFacade.addFilterMatcherMap(new MenufoFilterMatcherMap());
	    }
	    // "anagrafeinterdetti/list.jsp"
	    if (idTag.equals("anagarfeinterdetti_id")) {
		tableFacade.addFilterMatcherMap(new AnagarfeinterdetteFilterMatcherMap());
	    }
	    // "sorteggitestata/list.jsp"
	    if (idTag.equals("sorteggitestata_id")) {
		tableFacade.addFilterMatcherMap(new SorteggitestataFilterMatcherMap());
	    }
	    // "sorteggitestata/form.jsp"
	    if (idTag.equals("sorteggidettaglio_id")) {
		tableFacade.addFilterMatcherMap(new SorteggidettaglioFilterMatcherMap());
	    }
	    // "taskscheduler/list.jsp"
	    if (idTag.equals("taskscheduler_id")) {
		tableFacade.addFilterMatcherMap(new TaskschedulerFilterMatcherMap());
	    }
	    // concessionicausali/list.jsp
	    if (idTag.equals("concessionicausali_id")) {
		tableFacade.addFilterMatcherMap(new ConcessionicausaliFilterMatcherMap());
	    }
	    // tipisoggetto/list.jsp
	    if (idTag.equals("tipisoggetto_id")) {
		tableFacade.addFilterMatcherMap(new SoggettiAnagraficaFilterMatcherMap());
	    }
	}
	// fine gestione filterMatcher
	Limit limit = tableFacade.getLimit();
	ComponentFactory factory = null;
	// Test per verificare se la tabella è stata esportata
	if (limit.isExported()) {
	    // Test per verificare quale tipo di export è stato scelto a
	    // lato client.Crea un ComponentFactory adeguato alla scelta.
	    if (limit.getExportType().toParam().equals("csv")) {
		factory = new CsvComponentFactory(",", tableFacade.getWebContext(), tableFacade.getCoreContext());
	    }
	    if (limit.getExportType().toParam().equals("pdfp")) {
		factory = new ExportComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());
	    }
	    if (limit.getExportType().toParam().equals("excel")) {
		factory = new ExportComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());
	    }
	    // Generazione della tabella da esportare
	    Table table = factory.createTable();
	    // recupero dalla request del caption della tabella.
	    // il caption è utilizzato per dare il nome al file esportato
	    // e per il caption della tabella esportata
	    String caption = this.getCaption(limit, request, idTag);
	    table.setCaption(caption);
	    CellEditor celEdit = factory.createBasicCellEditor();
	    Row row = factory.createRow();
	    // Determino le proprietà tramite il metodo private getProperties(HttpServletRequest request)
	    String[] properties = getProperties(request);
	    // Setto le proprietà
	    for (int i = 0; i < properties.length; i++) {
		System.out.println(properties[i]);
		if (!properties[i].equals("undefined") && !properties[i].toLowerCase().equals("azioni")) {
		    // proprietà di tipo data
		    if (properties[i].toLowerCase().contains("data")) {
			row.addColumn(factory.createColumn(properties[i], new DateCellEditor()));
		    } else {
			if (properties[i].equals("scadenza")) {
			    if (idTag.equalsIgnoreCase("scadenze_id")) {
				row.addColumn(factory.createColumn(properties[i], celEdit));
			    } else {
				row.addColumn(factory.createColumn(properties[i], new DateCellEditor()));
			    }
			}
			if (properties[i].equals("readonly")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("validaAl")) {
			    row.addColumn(factory.createColumn(properties[i], new DateCellEditor()));
			}
			if (properties[i].equals("paridispari")) {
			    row.addColumn(factory.createColumn(properties[i], new PariDispariCellEditor()));
			}
			if (properties[i].equals("richiedePosteggio")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("richiedeEndo")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("abilitato")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("coSerichiedeendo")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("modificaistanza")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("nonPrevedeIncassi")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("soloImportiNegativi")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("flagAmministrazioneinterna")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("foRichiedefirma")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("moderato")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("letto")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("flagDisabilitato")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("flagContamqattivita")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("attivo")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("attivita.flagDisabilitato")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("flagPagato")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (idTag.equals("registrazioniInOut_id")) {
			    if (properties[i].equals("tipo")) {
				row.addColumn(factory.createColumn(properties[i], new TipoEntrataUscitaCellEditor()));
			    }
			}
			if (properties[i].equals("concAttiva")) {
			    row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			}
			if (properties[i].equals("amministratore")) {
			    row.addColumn(factory.createColumn(properties[i], new ResponsabiliCellEditor()));
			}
			if (properties[i].equals("amministratoresoftware")) {
			    row.addColumn(factory.createColumn(properties[i], new ResponsabiliCellEditor()));
			}
			if (idTag.equals("tipibando_id")) {
			    if (properties[i].equals("attivo")) {
				row.addColumn(factory.createColumn(properties[i], new TipibandoCellEditor()));
			    }
			}
			if (idTag.equals("mercatidattivitaistat_id")) {
			    if (properties[i].equals("attivita.flagDisabilitato")) {
				row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			    }
			}
			if (idTag.equals("scadenze_id")) {
			    if (properties[i].equals("categoria")) {
				row.addColumn(factory.createColumn(properties[i], new ScadenzaAvvisoCellEditor()));
			    }
			}
			if (idTag.equals("stampaiva_id")) {
			    if (properties[i].equals("anagrafe.descrizioneRichiedente")) {
				row.addColumn(factory.createColumn(properties[i], new GroupCellEditor(celEdit)));
			    }
			    if (properties[i].equals("mercati.descrizione")) {
				row.addColumn(factory.createColumn(properties[i], new GroupCellEditor(celEdit)));
			    }
			    if (properties[i].equals("mercatiUso.descrizione")) {
				row.addColumn(factory.createColumn(properties[i], new GroupCellEditor(celEdit)));
			    }
			    if (properties[i].equals("posteggio.codiceposteggio")) {
				row.addColumn(factory.createColumn(properties[i], new GroupCellEditor(celEdit)));
			    }
			    if (properties[i].equals("importo")) {
				row.addColumn(factory.createColumn(properties[i], new NumberCellEditor(WebConstants.NUMBER_FORMAT_PATTERN)));
			    }
			    if (properties[i].equals("imponibile")) {
				row.addColumn(factory.createColumn(properties[i], new NumberCellEditor(WebConstants.NUMBER_FORMAT_PATTERN)));
			    }
			}
			if (idTag.equals("anagrafe_id")) {
			    if (properties[i].equals("tipoanagrafe")) {
				row.addColumn(factory.createColumn(properties[i], new FisicaGiuridicaCellEditor()));
			    }
			    if (properties[i].equals("tipologia")) {
				row.addColumn(factory.createColumn(properties[i], new SiNoTecnicoCellEditor()));
			    }
			    // già presnete 
			    //			    if (properties[i].equals("flagDisabilitato")) {
			    //				row.addColumn(factory.createColumn(properties[i], new StatoanagrafeCellEditor()));
			    //			    }
			}
			if (idTag.equals("documentiistanza_id")) {
			    if (properties[i].equals("necessario")) {
				row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			    }
			    if (properties[i].equals("presente")) {
				row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			    }
			}
			// tabella jmesa commissioniediliziat/listCommissioniedilizeRDiscutibili.jsp
			if (idTag.equals("commissioniediliziet_id")) {
			    if (properties[i].equals("istanza.data")) {
				row.addColumn(factory.createColumn(properties[i], new DateCellEditor()));
			    }
			    //			    if (properties[i].equals("istanza.dataprotocollo")) {
			    //				row.addColumn(factory.createColumn(properties[i], new DateCellEditor()));
			    //			    }
			    // la data del movimento non viene  registrata in quanto la proprietà è "data"
			    // e il campo generico data è gestito all'inizio
			}
			if (idTag.equals("commedilizieallegati_id")) {
			    if (properties[i].equals("dataregistrazione")) {
				row.addColumn(factory.createColumn(properties[i], new DateCellEditor()));
			    }
			}
			// tabella jmesa commedilizieappello/list.jsp
			if (idTag.equals("commedilizieappello_id")) {
			    if (properties[i].equals("presente")) {
				row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			    }
			}
			// tabella jmesa commissioniediliziat/listCommissioniedilizeRDiscutibili.jsp
			if (idTag.equals("commissioniedilizie_id")) {
			    if (properties[i].equals("flagaperta")) {
				row.addColumn(factory.createColumn(properties[i], new StatoApertaChiusaCellEditor()));
			    }
			}
			// tabella jmesa menufo/list.jsp
			if (idTag.equals("menu_id")) {
			    if (properties[i].equals("flagAttivo")) {
				row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			    }
			}
			// tabella jmesa menuinfofo/list.jsp
			if (idTag.equals("menuinfo_id")) {
			    if (properties[i].equals("flagAttivo")) {
				row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			    }
			}
			// tabella jmesa sorteggitestata/form.jsp
			if (idTag.equals("sorteggidettaglio_id")) {
			    if (properties[i].equals("sorteggiata")) {
				row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			    }
			}
			// tabella jmesa taskscheduler/list.jsp
			if (idTag.equals("taskscheduler_id")) {
			    if (properties[i].equals("attivo")) {
				row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			    }
			    if (properties[i].equals("inesecuzione")) {
				row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			    }
			    if (properties[i].equals("prossimaesecuzione")) {
				row.addColumn(factory.createColumn(properties[i], new DateWithTimeCellEditor()));
			    }
			}
			// tabella jmesa tipisoggetto/list.jsp
			if (idTag.equals("tipisoggetto_id")) {
			    if (properties[i].equals("flagqualita")) {
				row.addColumn(factory.createColumn(properties[i], new SiNoCellEditor()));
			    }
			}
			if (!properties[i].equals("paridispari") && !properties[i].equals("richiedePosteggio")
				&& !properties[i].equals("richiedeEndo") && !properties[i].equals("abilitato") && !properties[i].equals("readonly")
				&& !properties[i].equals("nonPrevedeIncassi") && !properties[i].equals("soloImportiNegativi")
				&& !properties[i].equals("scadenza") && !properties[i].equals("validaAl") && !properties[i].equals("concAttiva")
				&& !properties[i].equals("foRichiedefirma") && !properties[i].equals("modificaistanza")
				&& !properties[i].equals("flagAmministrazioneinterna") && !properties[i].equals("flagDisabilitato")
				&& !properties[i].equals("flagContamqattivita") && !properties[i].equals("attivo")
				&& !properties[i].equals("attivita.flagDisabilitato") && !properties[i].equals("coSerichiedeendo")
				&& !properties[i].equals("amministratore") && !properties[i].equals("amministratoresoftware")
				&& !properties[i].equals("moderato") && !properties[i].equals("letto") && !properties[i].equals("moderato")
				&& !properties[i].equals("flagPagato") && !properties[i].equals("categoria") && !properties[i].equals("necessario")
				&& !properties[i].equals("presente") && !properties[i].equals("flagqualita")) {
			    if (idTag.equals("stampaiva_id")) {
				if (!properties[i].equals("anagrafe.descrizioneRichiedente") && !properties[i].equals("mercati.descrizione")
					&& !properties[i].equals("mercatiUso.descrizione") && !properties[i].equals("posteggio.codiceposteggio")
					&& !properties[i].equals("importo") && !properties[i].equals("imponibile")) {
				    row.addColumn(factory.createColumn(properties[i], celEdit));
				}
			    } else if (idTag.equals("tipibando_id")) {
				if (!properties[i].equals("attivo")) {
				    row.addColumn(factory.createColumn(properties[i], celEdit));
				}
				//			    } 
				//			    else if (idTag.equals("anagrafe_id")) {
				//				if (!properties[i].equals("tipoanagrafe")) {
				//				    row.addColumn(factory.createColumn(properties[i], celEdit));
				//				}
				//				if (!properties[i].equals("tipologia")) {
				//				    row.addColumn(factory.createColumn(properties[i], celEdit));
				//				}
				//				if (!properties[i].equals("flagDisabilitato")) {
				//				    row.addColumn(factory.createColumn(properties[i], celEdit));
				//				}
			    } else if (idTag.equals("registrazioniInOut_id")) {
				if (!properties[i].equals("tipo")) {
				    row.addColumn(factory.createColumn(properties[i], celEdit));
				}
			    } else if (idTag.equals("commissioniedilizie_id")) {
				if (!properties[i].equals("flagaperta")) {
				    row.addColumn(factory.createColumn(properties[i], celEdit));
				}
				// tabella jmesa commedilizieappello/list.jsp
			    } else if (idTag.equals("commedilizieappello_id")) {
				if (!properties[i].equals("presente")) {
				    row.addColumn(factory.createColumn(properties[i], celEdit));
				}
				// tabella jmesa menufo/list.jsp
			    } else if (idTag.equals("menu_id")) {
				if (!properties[i].equals("flagAttivo")) {
				    row.addColumn(factory.createColumn(properties[i], celEdit));
				}
				// tabella jmesa menuinfo/list.jsp
			    } else if (idTag.equals("menuinfo_var")) {
				if (!properties[i].equals("flagAttivo")) {
				    row.addColumn(factory.createColumn(properties[i], celEdit));
				}
				// tabella jmesa sorteggitestata/form.jsp
			    } else if (idTag.equals("sorteggidettaglio_id")) {
				if (!properties[i].equals("sorteggiata")) {
				    row.addColumn(factory.createColumn(properties[i], celEdit));
				}
				// tabella jmesa taskscheduler/list.jsp
			    } else if (idTag.equals("taskscheduler_id")) {
				if (!properties[i].equals("attivo") && !properties[i].equals("inesecuzione")
					&& !properties[i].equals("prossimaesecuzione")) {
				    row.addColumn(factory.createColumn(properties[i], celEdit));
				}
			    } else {
				row.addColumn(factory.createColumn(properties[i], celEdit));
			    }
			}
		    }
		}
	    }
	    // Setto il title name delle colonne
	    int j = 0;
	    String col;
	    // Lista contenente il nome delle colonne del Table Facade
	    List<String> listTitleKey = getTitleColumn(request);
	    Iterator<String> it = listTitleKey.iterator();
	    while (it.hasNext()) {
		col = (String) it.next();
		row.getColumn(j).setTitle(col);
		j++;
	    }
	    table.setRow(row);
	    tableFacade.setTable(table);
	    tableFacade.render();
	    export = true;
	}
	return export;
    }

    /**
     * recupero dalla request del caption della tabella. il caption è utilizzato per dare il nome al file esportato e
     * per il caption della tabella esportata.
     * 
     * @param limit
     * @param request
     * @return
     */
    private String getCaption(Limit limit, HttpServletRequest request, String idTag) {

	String caption = request.getParameter("caption");
	if (limit.getExportType().toParam().equals("excel")) {
	    if (idTag.equals("presenzeAnagrafe") || idTag.equals("presenzeAnagrafeMercato") || idTag.equals("presenzeAnagrafeMercatoUsoAnno")
		    || idTag.equals("presenzeAnagrafeAnno")) {
		caption = caption.replace(":", "");
		// Se la lunghezza del caption è maggiore di 100 allora viene utilizzato il nome dell'id della tabella
		// come caption.
		if (caption.length() > 100) {
		    caption = idTag;
		}
	    }
	}
	return caption;
    }

    /**
     * @param request
     * @return Lista contenente Il nome delle colonne della tabella (JMesa)
     */
    private List<String> getTitleColumn(HttpServletRequest request) {

	// lista restituita dal metodo
	List<String> listColumnKey = new ArrayList<String>();
	// recupero dalla request il parametro colName.
	// il valore di colName è una stringa contenente il nome delle colonne separate da virgole
	String colName = request.getParameter("colName");
	colName = StringUtils.replace(colName, " ", "");
	String[] arrayCol = colName.split(",");
	// recupero dal ResourceBundle l'etichetta label.edit.record per escluderla dall'export
	// ResourceBundle resource = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
	String detail = getMessageFromBundle("label.edit.record", null);
	String azioni = getMessageFromBundle("label.azioni", null);
	String registrazioni = getMessageFromBundle("label.edit.registrazioni", null);
	String scadenze = getMessageFromBundle("label.edit.scadenze", null);
	String disabilita = getMessageFromBundle("label.disabilita", null);
	String oggetto = getMessageFromBundle("documentiistanza.label.oggetto", null);
	String elimina = getMessageFromBundle("label.elimina", null).trim();
	String parere = getMessageFromBundle("label.tipologia_parere", null);
	String errori = getMessageFromBundle("label.errori", null);
	String stampa = getMessageFromBundle("label.stampa", null);
	String richiesto = getMessageFromBundle("label.richiesto", null);
	String presente = getMessageFromBundle("label.presente", null);
	String controllook = getMessageFromBundle("label.valido", null);
	String undefined = "";
	for (int k = 0; k < arrayCol.length; k++) {
	    if (!(arrayCol[k].equals(detail) || arrayCol[k].equals(azioni) || arrayCol[k].equals(undefined) || arrayCol[k].equals(registrazioni)
		    || arrayCol[k].equals(scadenze) || arrayCol[k].equals(disabilita) || arrayCol[k].equals(oggetto) || arrayCol[k].equals(elimina)
		    || arrayCol[k].equals(parere) || arrayCol[k].equals(errori) || arrayCol[k].equals(stampa) || arrayCol[k].equals(richiesto)
		    || arrayCol[k].equals(presente) || arrayCol[k].equals(controllook))) {
		listColumnKey.add(arrayCol[k]);
	    }
	}
	return listColumnKey;
    }

    /**
     * Metodo per determinare le proprietà del table facade tramite la request
     * 
     * @param request
     * @return Array di String.Array contenente le proprietà.
     */
    private String[] getProperties(HttpServletRequest request) {

	// recupero dalla request una stringa contenente le proprietà separate da virgole
	String props = request.getParameter("properties");
	String[] properties = props.split(",");
	for (int i = 0; i < properties.length; i++) {
	    // dalla request ottengo le proprietà racchiuse tra apici.
	    // elimino gli apici delle proprietà.
	    properties[i] = properties[i].replace("'", "");
	}
	return properties;
    }

    /**
     * metodo per aggiungere al model passato bean e collection non presente nell'entity di riferimento
     * 
     * @param model
     */
    protected abstract void setPageAttributes(Model model);

    /**
     * metodo da utilizzare prima delle insert e update per settare a null le property che contengono istanze vuote
     * (senza chiave primaria).
     * 
     * @param entity
     */
    protected abstract void fixMergeEntityProperty(E entity);

    /**
     * metodo da utilizzare prima della view per associare oggetti vuoti a property nulle
     * 
     * @param entity
     */
    protected abstract void fixRenderEntityProperty(E entity);

    /**
     * metodo per il recupero delle info principali dell'utente loggato
     * 
     * @return
     */
    protected UserDetails getCurrentlyAuthenticatedUser() {

	return userSecurityService.getCurrentlyAuthenticatedUser();
    }

    /**
     * metodo per il recupero dei dettagli dell'utente loggato
     * 
     * @return
     */
    protected Responsabili getCurrentlyAuthenticatedUserDetails() {

	return (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
    }

    /**
     * metodo per la lettura di un file da un url esterno tramite HttpClient
     * 
     * @param url
     * @return un array di byte con il contenuto del file
     */
    protected byte[] getContentFromHttpClientCall(String url) {

	byte[] responseBody = null;
	// Create an instance of HttpClient.
	HttpClient client = new HttpClient();
	// Create a method instance.
	GetMethod method = new GetMethod(url);
	// Provide custom retry handler is necessary
	method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
	try {
	    // Execute the method.
	    int statusCode = client.executeMethod(method);
	    if (statusCode != HttpStatus.SC_OK) {
		log.error("Fatal http client return code: " + statusCode);
		return null;
	    }
	    // Read the response body.
	    responseBody = method.getResponseBody();
	} catch (HttpException e) {
	    log.error("Fatal protocol violation: " + e.getMessage());
	} catch (IOException e) {
	    log.error("Fatal transport error: " + e.getMessage());
	} finally {
	    // Release the connection.
	    method.releaseConnection();
	}
	return responseBody;
    }

    /**
     * Torna dal ResourceBundle il messaggio ricercato
     * 
     * @param messageCode
     *            la chiave del messaggio
     * @return il messaggio della chiave cercata nel ResourceBundle
     * 
     * @throws NullPointerException
     *             if key is null
     * @throws MissingResourceException
     *             if no object for the given key can be found
     * @throws ClassCastException
     *             if the object found for the given key is not a string
     */
    // protected String getMessageFromBundle(String messageCode) {
    //
    // ResourceBundle resource = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
    // String message = resource.getString(messageCode);
    // return message;
    // }
    /**
     * metodo per il recupero dell'etichetta associata alla chiave passata come argomento.
     * 
     * @see ApplicationContext#getMessage(String, Object[], java.util.Locale)
     * @param chiave
     * @param args
     *            lista di valori da sostituire nel caso in cul l'etichetta associata alla chiave presenti dei
     *            segnaposto es.{0}
     * @return
     */
    protected String getMessageFromBundle(String chiave, Object[] args) {

	return Utilities.getMessageFromBundle(context, chiave, args);
    }

    protected void copyErrorsToFlashMessages(Object entity, boolean isCommand, String entityName, Exception e) {

	String errorMessage = getMessageFromBundle("04", new Object[] { this.renderErrors(e, true) });
	// ciclo tutti gli InvalidValues della validation
	List<InvalidValue> validationMessages = null;
	if (e instanceof BusinessValidationException) {
	    validationMessages = ((BusinessValidationException) e).getInvalidValues();
	}
	if (e instanceof EntityValidationException) {
	    validationMessages = ((EntityValidationException) e).getInvalidValues();
	}
	if (validationMessages != null) {
	    for (InvalidValue invalidValue : validationMessages) {
		if (invalidValue.getBeanClass() == null) {
		    // il messaggio non è associato ad una entity allora rappresenta un messaggio di business
		    // validation
		    // e
		    // lo visualizzo tra i messaggi globali
		    errorMessage += "<br />\n" + getMessageFromBundle(invalidValue.getMessage(), new Object[] { invalidValue.getValue() });
		    // result.reject(invalidValue.getMessage(), new Object[] { invalidValue.getValue() }, invalidValue.getMessage());
		} else if (isCommand) {
		    // la entity è contenuta in un bean command
		    if (invalidValue.getBeanClass().equals(entity.getClass())) {
			// la entity coincide con quella del form quindi aggiungo il prefisso entity ed eseguo il bind
			// dei
			// messaggi con i campi del form
			// String prefissoEntity = entityName + ".";
			// result.rejectValue(prefissoEntity + invalidValue.getPropertyPath(), invalidValue.getMessage(), invalidValue.getMessage());
			errorMessage += "<br />\n" + invalidValue.getPropertyPath() + ": "
				+ getMessageFromBundle(invalidValue.getMessage(), new Object[] {});
		    } else if (invalidValue.getRootBean() != null && invalidValue.getRootBean().getClass().equals(entity.getClass())) {
			// la declaring class coincide con la entity (caso dell'id interno alla entity)
			// String prefissoEntity = entityName + ".";
			// result.rejectValue(prefissoEntity + invalidValue.getPropertyPath(), invalidValue.getMessage(), invalidValue.getMessage());
			errorMessage += "<br />\n" + invalidValue.getPropertyPath() + ": "
				+ getMessageFromBundle(invalidValue.getMessage(), new Object[] {});
		    } else {
			// la entity non è quella del form quindi visualizzo i messaggi come globali
			errorMessage += "<br />\n" + getMessageFromBundle(invalidValue.getMessage(), new Object[] { invalidValue.getValue() });
			// result.reject(invalidValue.getMessage(), new Object[] { invalidValue.getValue() }, invalidValue.getMessage());
		    }
		} else if (invalidValue.getBeanClass().equals(entity.getClass())) {
		    // la entity non è contenuta in un bean command quindi eseguo il bind diretto dei messaggi con i
		    // campi
		    // del form
		    // result.rejectValue(invalidValue.getPropertyPath(), invalidValue.getMessage(), invalidValue.getMessage());
		    errorMessage += "<br />\n" + invalidValue.getPropertyPath() + ": "
			    + getMessageFromBundle(invalidValue.getMessage(), new Object[] {});
		} else if (invalidValue.getRootBean() != null && invalidValue.getRootBean().getClass().equals(entity.getClass())) {
		    // la entity è root della entity del form quindi eseguo il bind diretto dei messaggi con i
		    // campi
		    // del form
		    // result.rejectValue(invalidValue.getPropertyPath(), invalidValue.getMessage(), invalidValue.getMessage());
		    errorMessage += "<br />\n" + invalidValue.getPropertyPath() + ": "
			    + getMessageFromBundle(invalidValue.getMessage(), new Object[] {});
		} else {
		    // la entity non è contenuta in un bean command ma non è la entity del form quindi visualizzo i
		    // messaggi
		    // come globali
		    // result.reject(invalidValue.getMessage(), new Object[] { invalidValue.getValue() }, invalidValue.getMessage());
		    errorMessage += "<br />\n" + getMessageFromBundle(invalidValue.getMessage(), new Object[] { invalidValue.getValue() });
		}
	    }
	}
	FlashMessages.getWarnings().add(errorMessage);
    }

    /**
     * metodo per la copia dei messaggi tornati dalla validazione dei service nell'oggetto BindingResult dividendoli in
     * messaggi del form e messaggi globali
     * 
     * @param result
     *            oggetto BindingResult associato al metodo del controller
     * @param entity
     *            la entity principale
     * @param isCommand
     *            true se la entity è contenuta in un bean command
     * @param e
     *            TODO
     */
    protected void copyErrorsToBindingResult(BindingResult result, Object entity, boolean isCommand, String entityName, Exception e) {

	result.reject("04", new Object[] { this.renderErrors(e, true) }, "");
	// ciclo tutti gli InvalidValues della validation
	List<InvalidValue> validationMessages = null;
	if (e instanceof BusinessValidationException) {
	    validationMessages = ((BusinessValidationException) e).getInvalidValues();
	}
	if (e instanceof EntityValidationException) {
	    validationMessages = ((EntityValidationException) e).getInvalidValues();
	}
	if (validationMessages != null) {
	    for (InvalidValue invalidValue : validationMessages) {
		if (invalidValue.getBeanClass() == null) {
		    // il messaggio non è associato ad una entity allora rappresenta un messaggio di business
		    // validation
		    // e
		    // lo visualizzo tra i messaggi globali
		    result.reject(invalidValue.getMessage(), new Object[] { invalidValue.getValue() }, invalidValue.getMessage());
		} else if (isCommand) {
		    // la entity è contenuta in un bean command
		    if (invalidValue.getBeanClass().equals(entity.getClass())) {
			// la entity coincide con quella del form quindi aggiungo il prefisso entity ed eseguo il bind
			// dei
			// messaggi con i campi del form
			String prefissoEntity = StringUtils.isNotBlank(entityName) ? entityName + "." : "";
			result.rejectValue(prefissoEntity + invalidValue.getPropertyPath(), invalidValue.getMessage(), invalidValue.getMessage());
		    } else if (invalidValue.getRootBean() != null && invalidValue.getRootBean().getClass().equals(entity.getClass())) {
			// la declaring class coincide con la entity (caso dell'id interno alla entity)
			String prefissoEntity = StringUtils.isNotBlank(entityName) ? entityName + "." : "";
			;
			result.rejectValue(prefissoEntity + invalidValue.getPropertyPath(), invalidValue.getMessage(), invalidValue.getMessage());
		    } else {
			// la entity non è quella del form quindi visualizzo i messaggi come globali
			result.reject(invalidValue.getMessage(), new Object[] { invalidValue.getValue() }, invalidValue.getMessage());
		    }
		} else if (invalidValue.getBeanClass().equals(entity.getClass())) {
		    // la entity non è contenuta in un bean command quindi eseguo il bind diretto dei messaggi con i
		    // campi
		    // del form
		    result.rejectValue(invalidValue.getPropertyPath(), invalidValue.getMessage(), invalidValue.getMessage());
		} else if (invalidValue.getRootBean() != null && invalidValue.getRootBean().getClass().equals(entity.getClass())) {
		    // la entity è root della entity del form quindi eseguo il bind diretto dei messaggi con i
		    // campi
		    // del form
		    result.rejectValue(invalidValue.getPropertyPath(), invalidValue.getMessage(), invalidValue.getMessage());
		} else {
		    // la entity non è contenuta in un bean command ma non è la entity del form quindi visualizzo i
		    // messaggi
		    // come globali
		    result.reject(invalidValue.getMessage(), new Object[] { invalidValue.getValue() }, invalidValue.getMessage());
		}
	    }
	}
    }

    /**
     * metodo per la copia dei messaggi tornati dalla validazione dei service nell'oggetto BindingResult dividendoli in
     * errori del form e in errori globali
     * 
     * NON UTILIZZARE SE LA ENTITY E' CONTENUTA IN UN BEAN COMMAND. UTILIZZARE LA VERSIONE CON PARAMETRO 'ISCOMMAND'
     * 
     * @param result
     *            oggetto BindingResult associato al metodo del controller
     * @param entity
     *            la entity principale
     * @param e
     *            TODO
     */
    protected void copyErrorsToBindingResult(BindingResult result, Object entity, Exception e) {

	this.copyErrorsToBindingResult(result, entity, false, "entity", e);
    }

    /**
     * 
     * @param result
     * @param entity
     * @param isCommand
     * @param e
     *            TODO
     */
    protected void copyErrorsToBindingResult(BindingResult result, Object entity, boolean isCommand, Exception e) {

	this.copyErrorsToBindingResult(result, entity, isCommand, "entity", e);
    }

    protected String renderErrors(Exception e, boolean outputHtml) {

	StringBuffer buf = new StringBuffer();
	if (e instanceof UncategorizedSQLException) {
	    if (e.getMessage() != null && (e.getMessage().indexOf("Connection has timed out") > -1)) {
		buf.append(this.getMessageFromBundle("error.db_connection_timeout", null));
	    } else {
		buf.append(e.getMessage());
		buf.append(getRootCause(e));
	    }
	} else {
	    buf.append(e.getMessage());
	    buf.append(getRootCause(e));
	}
	if (outputHtml) {
	    String mailAssistenza = this.getMessageFromBundle("mail.assistenza.applicativo", null);
	    if (StringUtils.isNotBlank(mailAssistenza)) {
		if (!mailAssistenza.startsWith("?")) {
		    buf.append("<br />");
		    String messaggioAssistenza = this.getMessageFromBundle("messaggio.mail.assistenza.applicativo", null);
		    buf.append(messaggioAssistenza).append(" <b>").append(mailAssistenza).append("</b>");
		}
	    }
	    buf.append("<fieldset id=\"error_panel\" class=\"error_panel\" style=\"display: none;\"><legend>");
	}
	buf.append(this.getMessageFromBundle("errors.display_extended_desc_legend", null));
	if (outputHtml) {
	    buf.append("</legend>");
	}
	if (e instanceof BaseValidationException) {
	    Set<ExceptionHelper> listExc = ((BaseValidationException) e).getListaErrori();
	    if (outputHtml) {
		buf.append("<div><ul>");
	    }
	    for (ExceptionHelper exceptionHelper : listExc) {
		if (outputHtml) {
		    buf.append("<li>");
		} else {
		    buf.append("\n");
		}
		buf.append(exceptionHelper.toString());
		if (outputHtml) {
		    buf.append("</li>");
		}
	    }
	    if (outputHtml) {
		buf.append("</ul></div>");
	    }
	} else {
	    if (outputHtml) {
		buf.append("<div><ul>");
	    }
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    e.printStackTrace(pw);
	    if (outputHtml) {
		buf.append("<li><pre>");
	    }
	    buf.append(sw.toString());
	    if (outputHtml) {
		buf.append("</pre></li>");
		buf.append("</ul></div>");
	    }
	}
	if (outputHtml) {
	    buf.append("</fieldset>");
	}
	return buf.toString();
    }

    private String getRootCause(Exception e) {

	String rootCause = "";
	if (e instanceof DataAccessException) {
	    DataAccessException dae = (DataAccessException) e;
	    Throwable t = dae.getRootCause();
	    if (t != null) {
		rootCause = t.getMessage();
		if (StringUtils.isNotBlank(rootCause)) {
		    rootCause = " Causato da: " + rootCause;
		}
	    }
	}
	return rootCause;
    }

    /**
     * metodo per il recupero delle informazioni di sessione
     * 
     * @param request
     * @return
     */
    protected SessionDetails getSessionDetails(HttpServletRequest request) {

	SessionDetails sessionDetails = new SessionDetails();
	String token = (String) request.getSession().getAttribute(WebConstants.TOKEN);
	sessionDetails.setResponsabile(getCurrentlyAuthenticatedUserDetails());
	sessionDetails.setToken(token);
	return sessionDetails;
    }

    /**
     * Gestisce i parametri di configurazione utente, permette di leggere un parametro di configurazione (impostando un
     * valore di default se non trovato). Mette in request con attributo = nomeParametro e valore quello preso da DB o
     * valore di default specificato
     * 
     * @param nomeParametro
     * @param valorePredefinito
     * @param request
     */
    protected void gestisciParametroConfigurazioneUtente(String nomeParametro, String valorePredefinito, HttpServletRequest request) {

	// -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(), nomeParametro);
	Configurazioneutente configurazioneUtente = configurazioneutenteService.findById(confUteId);
	if (configurazioneUtente == null) {
	    // .. inserisco i valori di default
	    configurazioneUtente = new Configurazioneutente();
	    configurazioneUtente.setId(confUteId);
	    configurazioneUtente.setResponsabile(responsabile);
	    configurazioneUtente.setValore(valorePredefinito);
	    configurazioneutenteService.insert(configurazioneUtente);
	} else {
	    valorePredefinito = StringUtils.defaultIfEmpty(configurazioneUtente.getValore(), "");
	}
	// -----FINE GESTIONE CONFIGURAZIONE UTENTE----------------------
	// mette sulla request le informazioni recuperate dalla configurazione e le usa per gestire le regole di
	// visualizzazione sulla jsp
	request.setAttribute(nomeParametro, valorePredefinito);
    }

    /**
     * Controlla se la verticalizzazione è attiva o meno e mette in request un attributo con nome pari al nome della
     * verticalizzazione e valore true o false a seconda che sia attivo o meno
     * 
     * @param vertName
     *            nome della verticalizzazione
     * @param request
     */
    protected boolean isVerticalizzazioneAttiva(String vertName, HttpServletRequest request) {

	boolean isAttiva = verticalizzazioniService.isAttiva(vertName);
	request.setAttribute(vertName, isAttiva);
	return isAttiva;
    }

    protected boolean isVerticalizzazioneAttivaPerComune(String vertName, HttpServletRequest request, String codiceComune) {

	boolean isAttiva = verticalizzazioniService.isAttiva(vertName);
	request.setAttribute(vertName, isAttiva);
	return isAttiva;
    }

    /**
     * Permette di leggere un parametro di configurazione (Impostando un valore di default se non trovato). Mette in
     * request con attributo = nomeParametro e valore quello preso da DB o valore di default specificato
     * 
     * @param nomeParametro
     * @param valorePredefinito
     * @param request
     */
    protected String leggiParametroConfigurazioneUtente(String nomeParametro, String valorePredefinito, HttpServletRequest request) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(), nomeParametro);
	Configurazioneutente configurazioneUtente = configurazioneutenteService.findById(confUteId);
	if (configurazioneUtente != null) {
	    valorePredefinito = configurazioneUtente.getValore();
	}
	request.setAttribute(nomeParametro, valorePredefinito);
	return valorePredefinito;
    }

    public static String buildHistoryBackFromRequest(HttpServletRequest request, String servletPathUriBack) {

	String urlBack = "";
	try {
	    StringBuffer qs = new StringBuffer("");
	    @SuppressWarnings("unchecked")
	    Enumeration<String> requestParam = request.getParameterNames();
	    while (requestParam.hasMoreElements()) {
		String parametro = requestParam.nextElement();
		if (StringUtils.isNotBlank(request.getParameter(parametro))) {
		    qs.append(parametro).append("=").append(StringUtils.defaultIfEmpty(request.getParameter(parametro), "")).append("&");
		}
	    }
	    String queryString = qs.toString();
	    if (queryString.endsWith("&")) {
		queryString = queryString.concat("1=1");
	    }
	    urlBack = servletPathUriBack;
	    if (StringUtils.isNotBlank(queryString)) {
		urlBack += "?" + queryString;
	    }
	    if (log.isDebugEnabled()) {
		log.debug("buildHistoryBackFromRequest: urlBack={}", urlBack);
	    }
	    urlBack = URLEncoder.encode(urlBack, "UTF-8");
	    urlBack = URLEncoder.encode(urlBack, "UTF-8");
	} catch (java.io.UnsupportedEncodingException e) {
	}
	return urlBack;
    }

    /**
     * restiruisce la stringa 'redirect:../history/back.htm?GoTo=%2F'
     * 
     * @param goTo
     *            sostituisce %2F con l'encoded della stringa passata come argomento
     * @return
     */
    protected String getHistoryBack(String goTo) {

	try {
	    goTo = StringUtils.isBlank(goTo) ? "%2F" : URLEncoder.encode(goTo, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    log.error(e.getMessage());
	}
	String historyBack = "redirect:../history/back.htm?" + WebConstants.GOTO + "=" + goTo;
	return historyBack;
    }

    /**
     * vedi {@link BaseController#getHistoryBack(String)}
     * 
     * @return
     */
    protected String getHistoryBack() {

	return this.getHistoryBack(null);
    }

    public ApplicationContext getContext() {

	return context;
    }

    public String autorizzaCancellazioneDato(String nomeParametro, String goTo, String returnTo, HttpServletRequest request) throws Exception {

	if (request.getSession().getAttribute(AUTORIZZATO_ALLA_CANCELLAZIONE_SESSION_PARAM + "_" + nomeParametro) == null) {
	    if (isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI, request)) {
		Verticalizzazioniparametri parametro = verticalizzazioniService.getVerticalizzazioniparametri(
			WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI, nomeParametro);
		if (parametro != null) {
		    if (StringUtils.isNotBlank(parametro.getValore())) {
			return "redirect:passwordCancellazioniView.htm?goTo=" + URLEncoder.encode(goTo, "UTF-8") + "&returnTo="
				+ URLEncoder.encode(returnTo, "UTF-8") + "&nomeParametro=" + nomeParametro;
		    }
		}
	    }
	}
	return "";
    }

    @RequestMapping
    public String passwordCancellazioniView(@RequestParam("goTo") String goTo, @RequestParam("returnTo") String returnTo,
	    @RequestParam("nomeParametro") String nomeParametro, HttpServletRequest request, HttpServletResponse response) {

	String messaggiocancellazione = "È stata richiesta la cancellazione di un dato. Procedere?";
	if (WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_ISTANZE.equalsIgnoreCase(nomeParametro)) {
	    messaggiocancellazione = getMessageFromBundle("label.messaggio_cancellazione_istanza", null);
	} else if (WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_SORTEGGI.equalsIgnoreCase(nomeParametro)) {
	    messaggiocancellazione = getMessageFromBundle("label.messaggio_cancellazione_sorteggi", null);
	}
	request.setAttribute("messaggioUtente", messaggiocancellazione);
	return "welcome/passwordCancellazioni";
    }

    @RequestMapping
    public String controllaPasswordCancellazioni(@RequestParam("password") String password, @RequestParam("goTo") String goTo,
	    @RequestParam("returnTo") String returnTo, @RequestParam("nomeParametro") String nomeParametro, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	if (isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI, request)) {
	    Verticalizzazioniparametri parametro = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI, nomeParametro);
	    if (parametro != null) {
		if (StringUtils.isNotBlank(parametro.getValore())) {
		    if (password.equalsIgnoreCase(parametro.getValore())) {
			request.getSession().setAttribute(AUTORIZZATO_ALLA_CANCELLAZIONE_SESSION_PARAM + "_" + nomeParametro, Boolean.TRUE);
			return goTo;
		    }
		}
	    }
	}
	List<String> warnings = new ArrayList<String>();
	warnings.add("La password digitata non è corretta");
	FlashMessages.setWarnings(warnings);
	long timestamp = System.currentTimeMillis();
	return "redirect:passwordCancellazioniView.htm?goTo=" + URLEncoder.encode(goTo, "UTF-8") + "&returnTo="
		+ URLEncoder.encode(returnTo, "UTF-8") + "&nomeParametro=" + nomeParametro + "&ts_=" + timestamp;
    }

    protected void cleanCancellazioniAttribute(String nomeParametro, HttpServletRequest request) {

	request.getSession().removeAttribute(AUTORIZZATO_ALLA_CANCELLAZIONE_SESSION_PARAM + "_" + nomeParametro);
    }

    @RequestMapping
    public String ajaxerroresessioneduplicata(HttpServletRequest request, HttpServletResponse response) throws Exception {

	return "bugsessionecondivisa";
    }

    protected final String UTENTE_AUTORIZZATO_AMMINISTRAZIONE = "UTENTE_AUTORIZZATO_AMMINISTRAZIONE";

    protected void checkAccessoFunzionalitaAmministrative(HttpServletRequest request, HttpServletResponse response) throws IOException {

	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	if (StringUtils.defaultIfEmpty(responsabili.getAmministratore(), "0").equals("0")) {
	    throw new SecurityException("Utente non abilitato alla funzionalità");
	}
	if (request.getSession().getAttribute(UTENTE_AUTORIZZATO_AMMINISTRAZIONE) == null) {
	    response.sendRedirect("../admin/authorize.htm");
	}
    }

    protected void checkAccessoAmministratore(HttpServletRequest request, HttpServletResponse response) throws IOException {

	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	if (StringUtils.defaultIfEmpty(responsabili.getAmministratore(), "0").equals("0")
		&& StringUtils.defaultIfEmpty(responsabili.getAmministratoresoftware(), "0").equals("0")) {
	    throw new SecurityException("Utente non abilitato alla funzionalità");
	}
    }

    protected void renderHTMLException(String message, HttpServletResponse response) throws IOException {

	StringBuilder sb = new StringBuilder("<ul><li class='li_error'></li></ul><span class='error'>");
	sb.append(message);
	sb.append("</span>");
	response.setContentType("text/plain");
	response.getWriter().write(sb.toString());
    }

    /**
     * metodo per la creazione ed invio sulla response della lista degli oggetti passati come argomento.<br />
     * l'html inviato nella response è del tipo:
     * 
     * <pre>
     * {@code
     * <ul>
     * 	<li id="codice">descrizione</li>
     * 	<li id="codice">descrizione</li>
     * </ul>
     * }
     * </pre>
     * 
     * @param response
     *            oggetto HttpServletResponse dove scrivere l'html
     * @param list
     *            lista contentente i bean da visualizzare
     * @param codePath
     *            path (anche nested) della proprietà da utilizzare come codice. es id.codice
     * @param descriptionPath
     *            path (anche nested) della proprietà da utilizzare come descrizione.
     * @throws IOException
     */
    protected void renderHTMLResponse(HttpServletResponse response, List<?> list, String codePath, String descriptionPath, boolean showSpanConteggio)
	    throws IOException {

	StringBuffer buffer = new StringBuffer("<ul>");
	int i = 0;
	int size = 0;
	try {
	    if (list == null || list.isEmpty()) {
		buffer.append("<li id=''>").append(" ").append("</li>");
	    } else {
		size = list.size();
		for (Object obj : list) {
		    if (obj instanceof String) {
			buffer.append("<li id='").append(obj).append("'>").append(obj).append("</li>");
		    } else {
			buffer.append("<li id='").append(PropertyUtils.getProperty(obj, codePath)).append("'>")
				.append(PropertyUtils.getProperty(obj, descriptionPath)).append("</li>");
		    }
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

    protected void renderHTMLResponse(HttpServletResponse response, List<?> list, String codePath, String descriptionPath) throws IOException {

	renderHTMLResponse(response, list, codePath, descriptionPath, true);
    }

    public void modificaAbilitata(PkId pkOggettoDaVerificare, String oggettoDominio) {

	if (ORMHelper.isConsoleLocale()) {
	    if (pkOggettoDaVerificare != null) {
		String idcomune = StringUtils.defaultString(pkOggettoDaVerificare.getIdcomune());
		if (idcomune.equalsIgnoreCase(ORMHelper.getIdcomunebase())) {
		    String message = "L'operatore " + getCurrentlyAuthenticatedUserDetails().getResponsabile()
			    + " ha tentato di modificare/cancellare " + oggettoDominio;
		    LoggerCancellazioni.log(message);
		    throw new SecurityException("Attenzione!! Non è possibile modificare il dato.");
		}
	    }
	}
    }

    public void modificaAbilitata(String idcomune, String oggettoDominio) {

	if (ORMHelper.isConsoleLocale()) {
	    if (StringUtils.isNotBlank(idcomune)) {
		if (idcomune.equalsIgnoreCase(ORMHelper.getIdcomunebase())) {
		    String message = "L'operatore " + getCurrentlyAuthenticatedUserDetails().getResponsabile()
			    + " ha tentato di modificare/cancellare " + oggettoDominio;
		    LoggerCancellazioni.log(message);
		    throw new SecurityException("Attenzione!! Non è possibile modificare il dato.");
		}
	    }
	}
    }

    public void modificaAbilitataPerComune(Comuni c, boolean isBDR) {

	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	if (isComuniAssociati) { // solo in caso di comuniassociati
	    String codiceComune = "";
	    if (c != null) {
		codiceComune = c.getCodicecomune();
	    }
	    if (StringUtils.isNotBlank(codiceComune)) {
		Responsabili r = getCurrentlyAuthenticatedUserDetails();
		r = responsabiliService.findById(new PkId(r.getId().getCodice()));
		Set<Responsabilicomuni> rcs = r.getResponsabilicomunis();
		boolean canAcces = false;
		if (!rcs.isEmpty()) {
		    for (Responsabilicomuni rc : rcs) {
			if (rc.getId().getCodicecomune().equalsIgnoreCase(codiceComune)) {
			    canAcces = true;
			    break;
			}
		    }
		}
		if (!canAcces) {
		    String message = "L'operatore " + r + " non ha accesso all'informazione.";
		    LoggerCancellazioni.log(message);
		    throw new SecurityException("Attenzione!! Non è possibile modificare il dato.");
		}
	    } else {
		if (!isBDR) { // devo verificare se l'utente può gestire tutti i comuni (tutti i comuni = codicecomune vuoto)
		    Responsabili r = getCurrentlyAuthenticatedUserDetails();
		    r = responsabiliService.findById(new PkId(r.getId().getCodice()));
		    if (!org.apache.commons.lang.BooleanUtils.isTrue(r.getFlagGesttutticomuni())) {
			String message = "L'operatore " + r + " non ha accesso all'informazione.";
			LoggerCancellazioni.log(message);
			throw new SecurityException("Attenzione!! Non è possibile modificare il dato.");
		    }
		}
	    }
	}
    }

    public void checkFunzionalitaConsolleRegionale(boolean verificaSeAmministratore) {

	if (verificaSeAmministratore) {
	    Responsabili r = getCurrentlyAuthenticatedUserDetails();
	    if (!StringUtils.defaultIfEmpty(r.getAmministratore(), "0").equalsIgnoreCase("1")) {
		throw new SecurityException("Utente non abilitato alla gestione dell'informazione");
	    }
	}
	if (!ORMHelper.isConsoleRegionale()) {
	    throw new SecurityException("Utente non abilitato alla gestione dell'informazione");
	}
    }
}
