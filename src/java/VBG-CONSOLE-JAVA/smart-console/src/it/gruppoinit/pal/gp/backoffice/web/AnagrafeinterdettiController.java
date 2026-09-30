package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.FileUpload;
import it.gruppoinit.pal.gp.core.domain.VwAnagrafeinterdetti;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeInterdettiHelper;
import it.gruppoinit.pal.gp.core.domain.web.AnagrafeInterdettiCommand;
import it.gruppoinit.pal.gp.core.service.AnagrafeinterdettiCommandService;
import it.gruppoinit.pal.gp.core.service.VwAnagrafeinterdettiService;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Limit;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.ExportComponentFactory;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.csv.CsvComponentFactory;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.web.HttpServletRequestSpringWebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes(value = { "anagrafeinterdetti", "file", "updateDati", "anagrafeInterdettiHelper" })
public class AnagrafeinterdettiController extends BaseController<AnagrafeInterdettiCommand> {

    @Autowired
    private VwAnagrafeinterdettiService anagrafeinterdettiService;
    @Autowired
    private AnagrafeinterdettiCommandService anagrafeinterdettiCommandService;

    /**
     * Ritorna la lista di tutte le anagrafe interdette
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<VwAnagrafeinterdetti> anagrafeInterdettiList = anagrafeinterdettiService.findAll(null, null);
	ModelMap model = new ModelMap(anagrafeInterdettiList);
	boolean export = createJMesaExport(request, response, anagrafeInterdettiList);
	if (export) {
	    return null;
	}
	model.addAttribute("anagrafeInterdettiList", anagrafeInterdettiList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Crea la maschera che permette di scegliere il file .xls da importare.
     * 
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public String createImport(Model model, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	// creo la jsp che mi permette di inserire il file Excel delle anagrafe interdette
	FileUpload fileUpload = new FileUpload();
	model.addAttribute("file", fileUpload);
	// §§§END§§§
	return "anagrafeinterdetti/importExcel";
    }

    @RequestMapping
    public String uploadExcel(Model model, @ModelAttribute("file") FileUpload file, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	try {
	    AnagrafeInterdettiHelper anagrafeInterdettiHelper = anagrafeinterdettiCommandService.insertAnagrafeInterdetti(file);
	    model.addAttribute("file", file);
	    boolean export = createJMesaExport(request, response, anagrafeInterdettiHelper.getListaAnagrafeInterdettiIncongruenti());
	    if (export) {
		return null;
	    }
	    model.addAttribute("anagrafeInterdettiHelper", anagrafeInterdettiHelper);
	    model.addAttribute("listaAnagrafeincongruenti", anagrafeInterdettiHelper.getListaAnagrafeInterdettiIncongruenti());
	} catch (Exception e) {
	    String errorString = "Errore nel upload del file excel :" + " Controllare che il file importato sia corretto.";
	    model.addAttribute("file", new FileUpload());
	    model.addAttribute("errorString", errorString);
	    return "anagrafeinterdetti/importExcel";
	}
	// §§§END§§§
	return "redirect:listIncongruenti.htm";
    }

    @RequestMapping
    public String listIncongruenti(Model model, @ModelAttribute("anagrafeInterdettiHelper") AnagrafeInterdettiHelper anagrafeInterdettiHelper,
	    HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	String htmlTable = createJMesaListIstanze(request, response, anagrafeInterdettiHelper.getListaAnagrafeInterdettiIncongruenti(), false);
	if (htmlTable == null) {
	    return null;
	}
	model.addAttribute("htmltable", htmlTable);
	model.addAttribute("listaAnagrafeincongruenti", anagrafeInterdettiHelper.getListaAnagrafeInterdettiIncongruenti());
	// §§§END§§§
	return "anagrafeinterdetti/listIncongruenti";
    }

    /**
     * Il metodo protected boolean createJMesaExport(...) genera la "table facade" da esportare nei diversi formati
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
    protected String createJMesaListIstanze(HttpServletRequest request, HttpServletResponse response, Collection<AnagrafeInterdettiCommand> items,
	    boolean isExport) {

	String output = null;
	// §§§BEGIN§§§
	// Ottengo dalla request l'id della table facade(JMesa)
	String idTable = "interdetti_id";
	// genero una table facade
	HttpServletRequestSpringWebContext httpServletRequestSpringWebContext = new HttpServletRequestSpringWebContext(request);
	TableFacade tableFacade = TableFacadeFactory.createSpringTableFacade(idTable, httpServletRequestSpringWebContext);
	tableFacade.setStateAttr("restore");
	tableFacade.setItems(items);
	tableFacade.setExportTypes(response, ExportType.CSV, ExportType.EXCEL, ExportType.PDFP);
	// fine gestione filterMatcher
	Limit limit = tableFacade.getLimit();
	ComponentFactory factory = null;
	// Test per verificare se la tabella è stata esportata
	// Test per verificare quale tipo di export è stato scelto a
	// lato client.Crea un ComponentFactory adeguato alla scelta.
	boolean setHtmlProperties = true;
	if (limit.isExported()) {
	    setHtmlProperties = false;
	    if (limit.getExportType().toParam().equals("pdfp")) {
		factory = new ExportComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());
	    }
	    if (limit.getExportType().toParam().equals("excel")) {
		factory = new ExportComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());
	    }
	    if (limit.getExportType().toParam().equals("csv")) {
		factory = new CsvComponentFactory(",", tableFacade.getWebContext(), tableFacade.getCoreContext());
	    }
	} else {
	    factory = new HtmlComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());
	}
	// Generazione della tabella da esportare
	Table table = factory.createTable();
	// recupero dalla request del caption della tabella.
	// il caption è utilizzato per dare il nome al file esportato
	// e per il caption della tabella esportata
	String caption = getMessageFromBundle("label.lista_soggetti_scartati", null);
	table.setCaption(caption);
	CellEditor celEdit = factory.createBasicCellEditor();
	Row row = factory.createRow();
	// ((HtmlColumn) cognome).getHeaderRenderer().setHeaderEditor(new CustomHeaderEditor(title));
	// Determino le proprietà tramite il metodo private getProperties(HttpServletRequest request)
	// Setto le proprietà
	Column cognome = null;
	cognome = factory.createColumn("cognome", celEdit);
	cognome.setTitleKey("label.cognome");
	row.addColumn(cognome);
	if (setHtmlProperties) {
	    ((HtmlColumn) cognome).setFilterable(false);
	    ((HtmlColumn) cognome).setSortable(false);
	}
	Column nome = null;
	nome = factory.createColumn("nome", celEdit);
	nome.setTitleKey("label.nome");
	row.addColumn(nome);
	if (setHtmlProperties) {
	    ((HtmlColumn) nome).setFilterable(false);
	    ((HtmlColumn) nome).setSortable(false);
	}
	Column dataNascita = null;
	dataNascita = factory.createColumn("dataNascita", new DateCellEditor(WebConstants.DATE_FORMAT_PATTERN));
	dataNascita.setTitleKey("label.data_nascita");
	row.addColumn(dataNascita);
	if (setHtmlProperties) {
	    ((HtmlColumn) dataNascita).setFilterable(false);
	    ((HtmlColumn) dataNascita).setSortable(false);
	}
	Column idComune = null;
	idComune = factory.createColumn("idComune", celEdit);
	idComune.setTitleKey("label.codice_comune");
	row.addColumn(idComune);
	if (setHtmlProperties) {
	    ((HtmlColumn) idComune).setFilterable(false);
	    ((HtmlColumn) idComune).setSortable(false);
	}
	Column sesso = null;
	sesso = factory.createColumn("sesso", celEdit);
	sesso.setTitleKey("label.sesso");
	row.addColumn(sesso);
	if (setHtmlProperties) {
	    ((HtmlColumn) sesso).setFilterable(false);
	    ((HtmlColumn) sesso).setSortable(false);
	}
	Column dataInizioInterdizione = null;
	dataInizioInterdizione = factory.createColumn("dataInizioInterdizione", new DateCellEditor(WebConstants.DATE_FORMAT_PATTERN));
	dataInizioInterdizione.setTitleKey("label.data_inizio_interdizione");
	row.addColumn(dataInizioInterdizione);
	if (setHtmlProperties) {
	    ((HtmlColumn) dataInizioInterdizione).setFilterable(false);
	    ((HtmlColumn) dataInizioInterdizione).setSortable(false);
	}
	Column dataFineInterdizione = null;
	dataFineInterdizione = factory.createColumn("dataFineInterdizione", new DateCellEditor(WebConstants.DATE_FORMAT_PATTERN));
	dataFineInterdizione.setTitleKey("label.data_fine_interdizione");
	row.addColumn(dataFineInterdizione);
	if (setHtmlProperties) {
	    ((HtmlColumn) dataFineInterdizione).setFilterable(false);
	    ((HtmlColumn) dataFineInterdizione).setSortable(false);
	}
	Column codiceFiscale = null;
	codiceFiscale = factory.createColumn("codiceFiscale", celEdit);
	codiceFiscale.setTitleKey("label.codice_fiscale");
	row.addColumn(codiceFiscale);
	if (setHtmlProperties) {
	    ((HtmlColumn) codiceFiscale).setFilterable(false);
	    ((HtmlColumn) codiceFiscale).setSortable(false);
	}
	if (setHtmlProperties) {
	    Column erroriIncongruenze = null;
	    BasicCellEditor editor = new BasicCellEditor();
	    erroriIncongruenze = factory.createColumn("erroriIncongruenze", editor);
	    erroriIncongruenze.setTitleKey("label.errori");
	    ((HtmlColumn) erroriIncongruenze).setFilterable(false);
	    ((HtmlColumn) erroriIncongruenze).setSortable(false);
	    row.addColumn(erroriIncongruenze);
	}
	table.setRow(row);
	tableFacade.setTable(table);
	if (!limit.isExported()) {
	    output = tableFacade.render();
	} else {
	    tableFacade.render();
	}
	// §§§END§§§
	return output;
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(AnagrafeInterdettiCommand entity) {

    }

    @Override
    protected void fixRenderEntityProperty(AnagrafeInterdettiCommand entity) {

    }
}
