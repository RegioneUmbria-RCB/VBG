package it.gruppoinit.pal.gp.gestionecalendari.web;

import it.gruppoinit.pal.gp.core.domain.AnagrafeImpresa;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.domain.ComuniItaliani;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AnagrafeImpresaService;
import it.gruppoinit.pal.gp.core.service.CittadinanzaService;
import it.gruppoinit.pal.gp.core.service.ComuniItalianiService;
import it.gruppoinit.pal.gp.core.service.MailService;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.AnagrafeImpresaCommand;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.AnagrafeImpresaFilter;
import it.gruppoinit.pal.gp.gestionecalendari.web.validator.AnagrafeImpresaValidator;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jmesa.model.TableModel;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("anagrafeimpresaCommand")
public class AnagrafeImpresaController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ManifAreePubblicheController.class);
    @Autowired
    private AnagrafeImpresaService anagrafeimpresaService;
    @Autowired
    private ComuniItalianiService comuniItalianiService;
    @Autowired
    private CittadinanzaService cittadinanzaService;
    @Autowired
    private MailService mailService;

    @RequestMapping
    public String startSearch(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("AnagrafeImpresaController#startSearch");
	AnagrafeImpresaFilter filter = new AnagrafeImpresaFilter();
	//	Responsabili responsabileLoggato = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	//	List<Responsabilicomuni> comuni = responsabilicomuniService.findByResponsabile(responsabileLoggato);
	//	removeREGU(comuni);
	//	model.addAttribute("comuni", comuni);
	request.getSession().removeAttribute("mfs");
	model.addAttribute("filter", filter);
	model.addAttribute("c_menu", "cerca");
	return "anagrafeimpresa/search";
    }

    @RequestMapping
    public String search(Model model, @ModelAttribute("filter") AnagrafeImpresaFilter command, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	log.info("search");
	//Responsabili responsabileLoggato = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	try {
	    manageFilter(request, command);
	    if (result.hasErrors()) {
		return "anagrafeimpresa/search";
	    }
	    List<AnagrafeImpresa> anagrafeImpresas = anagrafeimpresaService.findByFilter(command);
	    boolean export = getAnagrafeimpresaServiceExportTable(request, response, anagrafeImpresas);
	    if (export)
		return null;
	    model.addAttribute("anagrafeImpresaList", anagrafeImpresas);
	} catch (Exception e) {
	    log.error("search", e);
	    addErrors(model, e);
	    return "anagrafeimpresa/search";
	}
	return "anagrafeimpresa/list";
    }

    @RequestMapping
    public String view(Model model, @RequestParam(value = "codice", required = false) Integer codice,
	    @RequestParam(value = "returnto", required = false) String returnto, HttpServletRequest request, HttpServletResponse response) {

	log.info("view:  codice={}", codice);
	AnagrafeImpresaCommand command = new AnagrafeImpresaCommand();
	if (codice != null) {
	    AnagrafeImpresa entity = anagrafeimpresaService.findById(new PkId(codice));
	    fixRendererEntiry(entity);
	    command.setEntity(entity);
	}
	//	command.init();
	model.addAttribute("returnto", returnto);
	model.addAttribute("anagrafeImpresaCommand", command);
	model.addAttribute("codice", codice);
	return "anagrafeimpresa/form";
    }

    @RequestMapping
    public String salva(@RequestParam(value = "codice", required = false) Integer codice, Model model,
	    @ModelAttribute("anagrafeImpresaCommand") AnagrafeImpresaCommand command, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "returnto", required = false) String returnto) {

	log.info("salva");
	AnagrafeImpresa anagrafeImpresa = command.getEntity();
	try {
	    new AnagrafeImpresaValidator().validate(anagrafeImpresa, result);
	    if (result.hasErrors()) {
		fixRendererEntiry(anagrafeImpresa);
		model.addAttribute("codice", codice);
		model.addAttribute("returnto", returnto);
		return "anagrafeimpresa/form";
	    }
	    populateEntity(anagrafeImpresa);
	    if (codice != null) {
		anagrafeImpresa.setId(new PkId(codice));
		anagrafeimpresaService.update(anagrafeImpresa);
	    } else {
		anagrafeimpresaService.insert(anagrafeImpresa);
	    }
	    codice = anagrafeImpresa.getId().getCodice();
	    status.setComplete();
	    return "redirect:view.htm?codice=" + codice + "&returnto=" + returnto + "&ret=0";
	} catch (Exception e) {
	    log.error("salva", e);
	    model.addAttribute("codice", codice);
	    fixRendererEntiry(anagrafeImpresa);
	    addErrors(model, e);
	    return "anagrafeimpresa/form";
	}
    }

    @RequestMapping
    public String delete(Model model, HttpServletRequest request, @RequestParam("codice") Integer codice, HttpServletResponse response) {

	log.info("delete: codice={}", codice);
	AnagrafeImpresa objectDelete = anagrafeimpresaService.findById(new PkId(codice));
	anagrafeimpresaService.delete(objectDelete);
	return "redirect:startSearch.htm";
    }

    private void fixRendererEntiry(AnagrafeImpresa anagrafeImpresa) {

	if (anagrafeImpresa.getComuneNascita() == null) {
	    anagrafeImpresa.setComuneNascita(new ComuniItaliani());
	}
	if (anagrafeImpresa.getComuneResidenza() == null) {
	    anagrafeImpresa.setComuneResidenza(new ComuniItaliani());
	}
	if (anagrafeImpresa.getComuneSedeLegale() == null) {
	    anagrafeImpresa.setComuneSedeLegale(new ComuniItaliani());
	}
	if (anagrafeImpresa.getCittadinanza() == null) {
	    anagrafeImpresa.setCittadinanza(new Cittadinanza());
	}
    }

    private void populateEntity(AnagrafeImpresa anagrafeImpresa) {

	if (anagrafeImpresa.getComuneNascita() != null && StringUtils.isNotBlank(anagrafeImpresa.getComuneNascita().getCodicecomune())) {
	    ComuniItaliani comuniItaliani = comuniItalianiService.findById(anagrafeImpresa.getComuneNascita().getCodicecomune());
	    anagrafeImpresa.setComuneNascita(comuniItaliani);
	} else {
	    anagrafeImpresa.setComuneNascita(null);
	}
	if (anagrafeImpresa.getComuneResidenza() != null && StringUtils.isNotBlank(anagrafeImpresa.getComuneResidenza().getCodicecomune())) {
	    ComuniItaliani comuniItaliani = comuniItalianiService.findById(anagrafeImpresa.getComuneResidenza().getCodicecomune());
	    anagrafeImpresa.setComuneResidenza(comuniItaliani);
	} else {
	    anagrafeImpresa.setComuneResidenza(null);
	}
	if (anagrafeImpresa.getComuneSedeLegale() != null && StringUtils.isNotBlank(anagrafeImpresa.getComuneSedeLegale().getCodicecomune())) {
	    ComuniItaliani comuniItaliani = comuniItalianiService.findById(anagrafeImpresa.getComuneSedeLegale().getCodicecomune());
	    anagrafeImpresa.setComuneSedeLegale(comuniItaliani);
	} else {
	    anagrafeImpresa.setComuneSedeLegale(null);
	}
	if (anagrafeImpresa.getCittadinanza() != null && anagrafeImpresa.getCittadinanza().getCodice() != null) {
	    Cittadinanza c = cittadinanzaService.findById(anagrafeImpresa.getCittadinanza().getCodice());
	    anagrafeImpresa.setCittadinanza(c);
	} else {
	    anagrafeImpresa.setCittadinanza(null);
	}
    }

    private boolean getAnagrafeimpresaServiceExportTable(HttpServletRequest request, HttpServletResponse response, Collection<AnagrafeImpresa> items) {

	String id = "anagrafeimpresaTableId";
	TableModel tableModel = new TableModel(id, request, response);
	if (tableModel.isExporting()) {
	    tableModel.setItems(items);
	    Table table = new Table().caption(getMessageFromBundle("report.impresa-areepubblica.titolo"));
	    Row row = new Row();
	    table.setRow(row);
	    //	    Column dataInserimento = new Column("dataInserimento").title(getMessageFromBundle("report.feste-sagre.data-inserimento")).cellEditor(
	    //		    new DateCellEditor("dd/MM/yyyy"));
	    Column denominazione = new Column("denominazione").title(getMessageFromBundle("label.denominazione"));
	    Column tipologia = new Column("tipologia").title(getMessageFromBundle("label.tipologia"));
	    Column cadenza = new Column("cadenza").title(getMessageFromBundle("label.cadenza"));
	    Column pSvolgimento = new Column("periodoSvolgimento").title(getMessageFromBundle("label.periodo-svolgimento"));
	    Column numeroPosteggiAssegnati = new Column("numeroPosteggiAssegnati").title(getMessageFromBundle("label.numero-posteggi-assegnati"));
	    Column numeroPosteggiSpuntisti = new Column("numeroPosteggiSpuntisti").title(getMessageFromBundle("label.numero-posteggi-spuntisti"));
	    Column comuneSvolgimento = new Column("comuni.comune").title(getMessageFromBundle("label.comune-svolgimento"));
	    row.addColumn(denominazione);
	    row.addColumn(tipologia);
	    row.addColumn(cadenza);
	    row.addColumn(pSvolgimento);
	    row.addColumn(numeroPosteggiAssegnati);
	    row.addColumn(numeroPosteggiSpuntisti);
	    row.addColumn(comuneSvolgimento);
	    tableModel.setTable(table);
	    tableModel.render();
	    return true;
	}
	return false;
    }

    private void manageFilter(HttpServletRequest request, AnagrafeImpresaFilter filter) {

	//	ManifestazioniAreePubblicheSearchFilter _filter = (ManifestazioniAreePubblicheSearchFilter) request.getSession().getAttribute("mfs");
	//	filter.setCodicecomune(_filter.getCodicecomune());
	//	filter.setComune(_filter.getComune());
	//	filter.setDenominazione(_filter.getDenominazione());
	//	filter.setTipologia(_filter.getTipologia());
	//	filter.setCadenza(_filter.getCadenza());
	//	filter.setGioni(_filter.getGioni());
	request.getSession().setAttribute("mfs", filter);
    }

    private void addErrors(Model model, Exception e) {

	model.addAttribute("error", StringUtils.defaultIfEmpty(e.getMessage(), getMessageFromBundle("error.exception")));
    }
}
