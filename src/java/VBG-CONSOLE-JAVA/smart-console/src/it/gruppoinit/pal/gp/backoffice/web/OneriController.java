package it.gruppoinit.pal.gp.backoffice.web;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jmesa.customColumn.ImportiLocalizzatiOnereCellEditor;
import org.jmesa.customColumn.ImportoOnereMulticomuneCellRenderer;
import org.jmesa.customColumn.LinkDettaglioOnereCellEditor;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.RowSelect;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.renderer.CellRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.helper.OneriPerCausaleHelper;
import it.gruppoinit.pal.gp.core.domain.web.OneriCommand;
import it.gruppoinit.pal.gp.core.domain.web.StarBaseCommand;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentioneriService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author francol
 */
@Controller
//@SessionAttributes(value = { "oneriCommand" })
public class OneriController extends BaseController<Inventarioprocedimentioneri> {

    public static final String ONERI_LIST_SESSION_KEY = "ONERI_LIST";
    public static final Logger log = LoggerFactory.getLogger(OneriController.class);
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private InventarioprocedimentioneriService inventarioprocedimentioneriService;
    @Autowired
    private TipicausalioneriService tipicausalioneriService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private ApplicationContext context;

    //@Autowired
    //private SpringWebContext springJmesaContext;
    @RequestMapping
    public String list(Model model, @RequestParam("codiceendo") Integer codiceEndo,
	    @RequestParam(required = true, value = "idcomendo") String idComune, @ModelAttribute(value = "oneriCommand") OneriCommand cmd,
	    HttpServletRequest request, HttpServletResponse response) {

	if (cmd == null) {
	    cmd = new OneriCommand();
	}
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(idComune, codiceEndo));
	//List<Inventarioprocedimentioneri> inventarioprocedimentioneriList = inventarioprocedimentioneriService.findByCodiceInventario(codiceendo, false, codicecomune);
	List<OneriPerCausaleHelper> oneri = inventarioprocedimentioneriService.findByCodiceInventarioGroupByCausale(codiceEndo, idComune, null);
	cmd.setEndo(inventarioprocedimenti);
	cmd.getOneri().addAll(oneri);
	setPageData(cmd);
	TableFacade tf = buildOneriTable(request, cmd);
	String tabHtml = tf.render();
	request.setAttribute("oneriTable", tabHtml);
	boolean export = createJMesaExport(request, response, oneri);
	if (export) {
	    return null;
	}
	model.addAttribute("oneriCommand", cmd);
	//model.addAttribute("oneriRows", attributeValue)
	//request.getSession().setAttribute(ONERI_LIST_SESSION_KEY, cmd);
	return "oneri/list";
    }

    @RequestMapping
    public String addCausale(Model model, @RequestParam(value = "codiceendo", required = true) Integer codiceEndo,
	    @RequestParam(required = true, value = "idcomendo") String idComune, @ModelAttribute("oneriCommand") OneriCommand cmd,
	    BindingResult result, HttpServletRequest request) {

	if (cmd == null) {
	    cmd = new OneriCommand();
	}
	Inventarioprocedimentioneri newOnere = new Inventarioprocedimentioneri();
	Inventarioprocedimenti endow = this.inventarioprocedimentiService.findById(new PkId(idComune, codiceEndo));
	if (endow != null) {
	    newOnere.setInventarioprocedimenti(endow);
	    cmd.setOnere(newOnere);
	} else {
	    String msg = MessageFormat.format("Impossibile individuare l'''endoprocedimento (id={0}, idcomune={1}) per cui creare l'''onere",
		    new Object[] { codiceEndo, idComune });
	    log.error("addCausale - Errore: " + msg);
	    copyErrorsToBindingResult(result, cmd, new RuntimeException(msg));
	}
	setPageData(cmd);
	model.addAttribute("oneriCommand", cmd);
	return "oneri/form";
    }

    @RequestMapping
    public String view(@RequestParam("idonere") Integer idOnere, Model model, HttpServletRequest request) {

	Inventarioprocedimentioneri onere = this.inventarioprocedimentioneriService.findById(new PkId(idOnere));
	OneriCommand cmd = new OneriCommand();
	cmd.setOnere(onere);
	cmd.setComuneLocalizzazione(onere.getComune());
	setPageData(cmd);
	fixRenderEntityProperty(onere);
	model.addAttribute("oneriCommand", cmd);
	setPageAttributes(model);
	return "oneri/form";
    }

    @RequestMapping
    public String create(@RequestParam(value = "idendo", required = true) Integer idEndo,
	    @RequestParam(value = "idcomendo", required = true) String idComuneEndo,
	    @RequestParam(value = "idcausale", required = true) Integer idCausale,
	    @RequestParam(value = "codicecomune", required = false) String codiceComune,
	    @RequestParam(value = "multi", required = false) Boolean multicomune, Model model, HttpServletRequest request) {

	Inventarioprocedimentioneri onere = new Inventarioprocedimentioneri();
	Inventarioprocedimenti endo = this.inventarioprocedimentiService.findById(new PkId(idComuneEndo, idEndo));
	onere.setInventarioprocedimenti(endo);
	Tipicausalioneri causale = this.tipicausalioneriService.findById(new PkId(ORMHelper.getIdcomunebase(), idCausale));
	onere.setTipicausalioneri(causale);
	OneriCommand cmd = new OneriCommand();
	cmd.setOnere(onere);
	setPageData(cmd);
	fixRenderEntityProperty(onere);
	List<Responsabilicomuni> resCom = cmd.getComuniResponsabile();
	Comuni com = new Comuni();
	for (Responsabilicomuni rc : resCom) {
	    if (rc.getComune() != null && rc.getComune().getCodicecomune().equals(codiceComune)) {
		com = rc.getComune();
		break;
	    }
	}
	cmd.setComuneLocalizzazione(com);
	onere.setComune(com);
	model.addAttribute("oneriCommand", cmd);
	setPageAttributes(model);
	return "oneri/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("oneriCommand") OneriCommand cmd, BindingResult result) {

	Inventarioprocedimentioneri onereInput = cmd.getOnere();
	//verifico se esiste già un'onere per la causale e il codice comune specificati
	String codComune = onereInput.getComune() != null ? onereInput.getComune().getCodicecomune() : "";
	Integer idEndo = onereInput.getInventarioprocedimenti().getId().getCodice();
	String codComuneEndo = onereInput.getInventarioprocedimenti().getId().getIdcomune();
	Integer idCausale = onereInput.getTipicausalioneri().getId().getCodice();
	Inventarioprocedimentioneri onereDb = inventarioprocedimentioneriService.findByCodiceInventarioCausaleCodiceComune(idEndo, codComuneEndo,
		idCausale, codComune);
	if (onereDb != null) {
	    onereInput.getId().setCodice(onereDb.getId().getCodice());
	}
	String statusMsg = "01";
	fixMergeEntityProperty(cmd.getOnere());
	try {
	    if (onereDb != null) {
		inventarioprocedimentioneriService.update(onereInput);
		statusMsg = "02";
	    } else {
		inventarioprocedimentioneriService.insert(onereInput);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cmd, e);
	    setPageAttributes(model);
	    fixRenderEntityProperty(cmd.getOnere());
	    return "oneri/form";
	}
	fixRenderEntityProperty(cmd.getOnere());
	return "redirect:view.htm?idonere=" + cmd.getOnere().getId().getCodice() + "&status_msg=" + statusMsg;
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("oneriCommand") OneriCommand cmd, BindingResult result, HttpServletRequest request) {

	Inventarioprocedimentioneri onere = cmd.getOnere();
	fixMergeEntityProperty(onere);
	try {
	    inventarioprocedimentioneriService.update(onere);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cmd, e);
	    setPageData(cmd);
	    return "oneri/form";
	}
	fixRenderEntityProperty(onere);
	return "redirect:view.htm?idonere=" + cmd.getOnere().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @RequestParam("idonere") Integer idOnere, @ModelAttribute("oneriCommand") OneriCommand cmd,
	    BindingResult result, HttpServletRequest request) {

	Inventarioprocedimentioneri onere = this.inventarioprocedimentioneriService.findById(new PkId(ORMHelper.getIdcomune(), idOnere));
	if (cmd == null) {
	    cmd = new OneriCommand();
	}
	if (onere == null) {
	    String msg = MessageFormat.format("Impossibile individuare l'''onere (id={0}, idcomune={1}) da cancellare",
		    new Object[] { idOnere, ORMHelper.getIdcomune() });
	    log.error("addCausale - Errore: " + msg);
	    copyErrorsToBindingResult(result, null, new RuntimeException(msg));
	}
	String retPath = "";
	try {
	    inventarioprocedimentioneriService.delete(onere);
	    retPath = "redirect:list.htm?codiceendo=" + onere.getInventarioprocedimenti().getId().getCodice() + "&idcomendo=" +
		      onere.getInventarioprocedimenti().getId().getIdcomune();
	} catch (Exception e) {
	    copyErrorsToFlashMessages(cmd, true, "onere", e);
	    cmd.setOnere(onere);
	    return "redirect:view.htm?idonere=" + cmd.getOnere().getId().getCodice() + "&status_msg=03";
	}
	setPageData(cmd);
	model.addAttribute("oneriCommand", cmd);
	return retPath;
    }

    @Override
    protected void fixMergeEntityProperty(Inventarioprocedimentioneri entity) {

	if (entity.getComune() != null && StringUtils.isEmpty(entity.getComune().getCodicecomune())) {
	    entity.setComune(null);
	}
	if (entity.getTipicausalioneri() == null) {
	    entity.setTipicausalioneri(new Tipicausalioneri());
	}
	if (entity.getInventarioprocedimenti() == null) {
	    entity.setInventarioprocedimenti(new Inventarioprocedimenti());
	}
	if (entity.getDyn2Campi() == null) {
	    entity.setDyn2Campi(new Dyn2Campi());
	}
	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
	//TODO: verificare 
	if (entity.getDyn2Campi().getId() != null) {
	    entity.getDyn2Campi().getId().setIdcomune(ORMHelper.getIdcomunebase());
	}
    }

    @Override
    protected void fixRenderEntityProperty(Inventarioprocedimentioneri entity) {

	if (entity.getComune() == null) {
	    entity.setComune(new Comuni());
	}
	if (entity.getDyn2Campi() == null) {
	    entity.setDyn2Campi(new Dyn2Campi());
	}
	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	/*
	 * Controllo se è attiva la VERTICALIZZAZIONE PEOPLE
	 */
	Verticalizzazioni verticalizzazioni_PEOPLE = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_PEOPLE);
	if (verticalizzazioni_PEOPLE != null) {
	    if (verticalizzazioni_PEOPLE.getAttivo() == 1) {
		model.addAttribute("vert_people_attivo", true);
	    } else {
		model.addAttribute("vert_people_attivo", false);
	    }
	} else {
	    model.addAttribute("vert_people_attivo", false);
	}
	/*
	 * Controllo se è attiva la VERTICALIZZAZIONE PEOPLE
	 */
	Verticalizzazioni verticalizzazioni_SISTEMAPAGAMENTI_ATTIVO = verticalizzazioniService
		.findByModulo(WebConstants.VERTICALIZZAZIONE_SISTEMAPAGAMENTI_ATTIVO);
	if (verticalizzazioni_SISTEMAPAGAMENTI_ATTIVO != null) {
	    if (verticalizzazioni_SISTEMAPAGAMENTI_ATTIVO.getAttivo() == 1) {
		model.addAttribute("vert_pagamenti_attivo", true);
	    } else {
		model.addAttribute("vert_pagamenti_attivo", false);
	    }
	} else {
	    model.addAttribute("vert_pagamenti_attivo", false);
	}
    }

    private void setPageData(OneriCommand cmd) {

	//imposto la lista dei comuni del responsabile nel command
	List<Responsabilicomuni> comResp = this.comuniassociatiService.checkComuniAbilitatiPerResponsabile();
	cmd.setComuniResponsabile(comResp);
	//imposto a new Comuni() il riferimento all'fk comune se i dati del db contengono un valore null
	Inventarioprocedimentioneri onere = cmd.getOnere();
	if (onere != null) {
	    if (onere.getComune() == null) {
		onere.setComune(new Comuni());
	    }
	    if (comResp.size() == 1) {
		if (StringUtils.isBlank(onere.getComune().getCodicecomune())) {
		    onere.getComune().setCodicecomune(comResp.get(0).getComune().getCodicecomune());
		}
	    }
	}
    }

    private TableFacade buildOneriTable(HttpServletRequest request, OneriCommand command) {

	//TableFacade tf = TableFacadeFactory.createTableFacade("oneri_table", request);
	TableFacade tf = TableFacadeFactory.createSpringTableFacade("oneri_table", request);
	//TableFacadeFactory.createTableFacade("oneri_table", springJmesaContext);
	int start = 0;
	int limit = 100;
	RowSelect rowSelect = tf.getLimit().getRowSelect();
	if (null != rowSelect) {
	    start = rowSelect.getRowStart();
	    limit = rowSelect.getMaxRows();
	}
	tf.setTotalRows(command.getOneri().size());
	tf.setItems(command.getOneri(start, limit));
	List<String> columnProps = new ArrayList<String>();
	columnProps.add("causale.coDescrizione");
	columnProps.add("onereComuneBase.importo");
	if (ORMHelper.isConsoleLocale()) {
	    columnProps.add("oneriPerComune");
	} else {
	    columnProps.add("onereComuneBase.id.codice");
	}
	tf.setColumnProperties(columnProps.toArray(new String[columnProps.size()]));
	//colonna descrizione onere
	HtmlColumn col = (HtmlColumn) tf.getTable().getRow().getColumn(columnProps.get(0));
	//col.setTitleKey("inventarioprocedimenti.label.tipo_causale");
	col.setTitle(Utilities.getMessageFromBundle(context, "inventarioprocedimenti.label.tipo_causale", null));
	col.setFilterable(true);
	col.setEditable(false);
	col.setWidth("40%");
	//colonna importo onere
	col = (HtmlColumn) tf.getTable().getRow().getColumn(columnProps.get(1));
	col.setTitle(Utilities.getMessageFromBundle(context, "inventarioprocedimenti.importo_onere_regionale", null));
	col.setFilterable(false);
	col.setEditable(false);
	col.setWidth("20%");
	col.getCellRenderer().setStyle("text-align:right;");
	col.getHeaderRenderer().setStyle("text-align:right;");
	//colonna per link al dettaglio dell'onere o ai dettagli degli oneri dei singoli comuni di un gruppo
	col = (HtmlColumn) tf.getTable().getRow().getColumn(columnProps.get(2));
	col.setFilterable(false);
	col.setEditable(false);
	if (ORMHelper.isConsoleLocale()) {
	    columnProps.add("oneriPerComune");
	    col.setTitle(Utilities.getMessageFromBundle(context, "inventarioprocedimenti.importo_onere_locale", null));
	    col.setWidth("40%");
	    CellRenderer cr = new ImportoOnereMulticomuneCellRenderer(command.getComuniResponsabile());
	    //cr.setCellEditor(new ImportiLocalizzatiOnereCellEditor());
	    col.setCellRenderer(cr);
	    col.getCellRenderer().setCellEditor(new ImportiLocalizzatiOnereCellEditor());
	} else {
	    columnProps.add("onereComuneBase.id.codice");
	    col.setTitleKey("label.edit.record");
	    col.setFilterable(false);
	    col.setEditable(false);
	    col.setWidth("7%");
	    CellEditor hce = new LinkDettaglioOnereCellEditor(command.getEndo());
	    col.getCellRenderer().setCellEditor(hce);
	}
	return tf;
    }

    private StarBaseCommand currentOneriData(HttpServletRequest request) {

	return (StarBaseCommand) request.getSession().getAttribute(ONERI_LIST_SESSION_KEY);
    }
}
