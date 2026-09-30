package it.gruppoinit.pal.gp.gestionecalendari.web;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.FesteSagre;
import it.gruppoinit.pal.gp.core.domain.FiereMostre;
import it.gruppoinit.pal.gp.core.domain.FiereMostreMerceologie;
import it.gruppoinit.pal.gp.core.domain.FiereMostrePeriodi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.helper.EntityUtils;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.FesteSagreService;
import it.gruppoinit.pal.gp.core.service.FiereMostreService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.ManifestazioniCommand;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.ManifestazioniSearchFilter;
import it.gruppoinit.pal.gp.gestionecalendari.web.validator.FesteSagreValidator;
import it.gruppoinit.pal.gp.gestionecalendari.web.validator.FiereMostreValidator;
import it.gruppoinit.pal.gp.gestionecalendari.web.validator.ManifestazioniSearchFilterValidator;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jmesa.model.TableModel;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
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

@Controller
@SessionAttributes("manifestazioniCommand")
public class ManifestazioniController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ManifestazioniController.class);
    @Autowired
    private FiereMostreService fiereMostreService;
    @Autowired
    private FesteSagreService festeSagreService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private ResponsabilicomuniService responsabilicomuniService;
    @Autowired
    private ComuniService comuniService;

    @RequestMapping
    public String startSearch(Model model, HttpServletRequest request, @RequestParam("tipo") String tipo, HttpServletResponse response) {

	log.info("startSearch: tipo={}", tipo);
	ManifestazioniSearchFilter filter = new ManifestazioniSearchFilter(tipo);
	Responsabili responsabileLoggato = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	List<Responsabilicomuni> comuni = responsabilicomuniService.findByResponsabile(responsabileLoggato);
	if ("FS".equals(tipo)) {
	    removeREGU(comuni);
	}
	model.addAttribute("comuni", comuni);
	request.getSession().removeAttribute("mfs");
	model.addAttribute("filter", filter);
	model.addAttribute("c_menu", "cerca");
	return "manifestazioni/search";
    }

    @RequestMapping
    public String search(Model model, @ModelAttribute("filter") ManifestazioniSearchFilter command, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	log.info("search");
	Responsabili responsabileLoggato = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	try {
	    manageFilter(request, command);
	    new ManifestazioniSearchFilterValidator().validate(command, result);
	    if (result.hasErrors()) {
		List<Responsabilicomuni> comuni = responsabilicomuniService.findByResponsabile(responsabileLoggato);
		model.addAttribute("comuni", comuni);
		return "manifestazioni/search";
	    }
	    if (StringUtils.isNotBlank(command.getCodicecomune())) {
		command.setComune(comuniService.findByCodiceComune(new Comuni(command.getCodicecomune())).getComune());
	    }
	    if (command.getTipoManifestazione().equalsIgnoreCase("FM")) {
		List<FiereMostre> fiereMostreList = fiereMostreService.findByFilter(command);
		boolean export = getFiereMostreExportTable(request, response, fiereMostreList);
		if (export)
		    return null;
		model.addAttribute("fiereMostreList", fiereMostreList);
	    }
	    if (command.getTipoManifestazione().equalsIgnoreCase("FS")) {
		List<FesteSagre> festeSagreList = festeSagreService.findByFilter(command);
		boolean export = getFesteSagreExportTable(request, response, festeSagreList);
		if (export)
		    return null;
		model.addAttribute("festeSagreList", festeSagreList);
	    }
	} catch (Exception e) {
	    log.error("search", e);
	    List<Responsabilicomuni> comuni = responsabilicomuniService.findByResponsabile(responsabileLoggato);
	    model.addAttribute("comuni", comuni);
	    addErrors(model, e);
	    return "manifestazioni/search";
	}
	return "manifestazioni/list";
    }

    @RequestMapping
    public String salva(Model model, @ModelAttribute("manifestazioniCommand") ManifestazioniCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response,
	    @RequestParam(value = "returnto", required = false) String returnto) {

	log.info("salva");
	try {
	    Responsabili responsabileLoggato = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    List<Responsabilicomuni> comuni = responsabilicomuniService.findByResponsabile(responsabileLoggato);
	    model.addAttribute("comuni", comuni);
	    String tipo = command.getTipoManifestazione();
	    Integer codice = null;
	    if (StringUtils.isBlank(tipo)) {
		result.rejectValue("tipoManifestazione", "validation.campo-obbligatorio");
		return "manifestazioni/form";
	    }
	    if (tipo.equalsIgnoreCase("FS")) {
		new FesteSagreValidator().validate(command.getFesteSagre(), result);
		if (result.hasErrors()) {
		    model.addAttribute("returnto", returnto);
		    return "manifestazioni/form";
		}
		command.getFesteSagre().setDataInserimento(new Date());
		if (EntityUtils.isNestedPropertyBlank(command.getFesteSagre(), "id.codice")) {
		    festeSagreService.insert(command.getFesteSagre());
		} else {
		    festeSagreService.update(command.getFesteSagre());
		}
		codice = command.getFesteSagre().getId().getCodice();
	    } else {
		new FiereMostreValidator().validate(command, result);
		if (result.hasErrors()) {
		    model.addAttribute("returnto", returnto);
		    return "manifestazioni/form";
		}
		FiereMostreMerceologie merceologia = command.getMerceologia();
		if ("(28) Altro (specificare sotto il settore)".equals(merceologia.getMerceologia())) {
		    merceologia = command.getMerceologiaAltro();
		}
		command.getFiereMostre().setDataInserimento(new Date());
		if (EntityUtils.isNestedPropertyBlank(command.getFiereMostre(), "id.codice")) {
		    fiereMostreService.insert(command.getFiereMostre(), command.getPeriodo(), merceologia);
		} else {
		    fiereMostreService.update(command.getFiereMostre(), command.getPeriodi(), command.getPeriodo(), command.getMerceologie(),
			    merceologia);
		}
		codice = command.getFiereMostre().getId().getCodice();
	    }
	    status.setComplete();
	    return "redirect:view.htm?tipo=" + tipo + "&codice=" + codice + "&returnto=" + returnto + "&ret=0";
	} catch (Exception e) {
	    log.error("salva", e);
	    addErrors(model, e);
	    return "manifestazioni/form";
	}
    }

    @RequestMapping
    public String view(Model model, @RequestParam("tipo") String tipo, @RequestParam(value = "codice", required = false) Integer codice,
	    @RequestParam(value = "returnto", required = false) String returnto, HttpServletRequest request, HttpServletResponse response) {

	log.info("view: tipo={}, codice={}", tipo, codice);
	Responsabili responsabileLoggato = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	List<Responsabilicomuni> comuni = responsabilicomuniService.findByResponsabile(responsabileLoggato);
	if ("FS".equals(tipo)) {
	    removeREGU(comuni);
	}
	model.addAttribute("comuni", comuni);
	ManifestazioniCommand command = new ManifestazioniCommand();
	command.init();
	model.addAttribute("returnto", returnto);
	if (tipo.equals("FS")) {
	    command.setTipoManifestazione("FS");
	    if (codice != null) {
		command.setFesteSagre(festeSagreService.findById(new PkId(codice)));
	    } else {
		command.setFesteSagre(new FesteSagre());
	    }
	    model.addAttribute("manifestazioniCommand", command);
	    return "manifestazioni/form";
	}
	if (tipo.equals("FM")) {
	    command.setTipoManifestazione("FM");
	    if (codice != null) {
		FiereMostre fm = fiereMostreService.findById(new PkId(codice));
		command.setFiereMostre(fm);
		command.populatePeriodi(fm.getFiereMostrePeriodis());
		command.populateMerceologie(fm.getFiereMostreMerceologies());
	    } else {
		command.setFiereMostre(new FiereMostre());
	    }
	    model.addAttribute("manifestazioniCommand", command);
	    return "manifestazioni/form";
	}
	log.error("view: parametro tipo non presente nella request");
	throw new RuntimeException(getMessageFromBundle("error.exception"));
    }

    @RequestMapping
    public String delete(Model model, HttpServletRequest request, @RequestParam("tipo") String tipo, @RequestParam("codice") Integer codice,
	    HttpServletResponse response) {

	log.info("delete: tipo={}, codice={}", tipo, codice);
	if (tipo.equalsIgnoreCase("FS")) {
	    FesteSagre fs = festeSagreService.findById(new PkId(codice));
	    festeSagreService.delete(fs);
	}
	if (tipo.equalsIgnoreCase("FM")) {
	    FiereMostre fm = fiereMostreService.findById(new PkId(codice));
	    fiereMostreService.delete(fm);
	}
	return "redirect:startSearch.htm?tipo=" + tipo;
    }

    private void addErrors(Model model, Exception e) {

	model.addAttribute("error", StringUtils.defaultIfEmpty(e.getMessage(), getMessageFromBundle("error.exception")));
    }

    private void removeREGU(List<Responsabilicomuni> comuni) {

	int idx = -1;
	for (int i = 0; i < comuni.size(); i++) {
	    if (comuni.get(i).getComune().getCodicecomune().equals("REGU")) {
		idx = i;
		break;
	    }
	}
	if (idx != -1) {
	    comuni.remove(idx);
	}
    }

    private boolean getFiereMostreExportTable(HttpServletRequest request, HttpServletResponse response, Collection<FiereMostre> items) {

	String id = "fiereMostreTableId";
	TableModel tableModel = new TableModel(id, request, response);
	if (tableModel.isExporting()) {
	    tableModel.setItems(items);
	    Table table = new Table().caption(getMessageFromBundle("report.fiere-mostre.titolo"));
	    Row row = new Row();
	    table.setRow(row);
	    Column denominazione = new Column("denominazione").title(getMessageFromBundle("report.fiere-mostre.denominazione"));
	    Column periodi = new Column().title(getMessageFromBundle("report.fiere-mostre.periodi"));
	    periodi.setCellEditor(new CellEditor() {

		public Object getValue(Object item, String property, int rowcount) {

		    if (item instanceof FiereMostre) {
			FiereMostre fm = (FiereMostre) item;
			StringBuffer sb = new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			for (FiereMostrePeriodi fmp : fm.getFiereMostrePeriodisTransient()) {
			    sb.append("[");
			    sb.append(sdf.format(fmp.getDal()));
			    sb.append(" - ");
			    sb.append(sdf.format(fmp.getAl()));
			    sb.append("] ");
			}
			return sb.toString();
		    } else {
			return new BasicCellEditor().getValue(item, property, rowcount);
		    }
		}
	    });
	    Column luogo = new Column("luogoSvolgimento").title(getMessageFromBundle("report.fiere-mostre.luogo-svolgimento"));
	    Column dataInserimento = new Column("dataInserimento").title(getMessageFromBundle("label.data-inserimento")).cellEditor(
		    new DateCellEditor("dd/MM/yyyy"));
	    Column organizzatore = new Column("organizzatore").title(getMessageFromBundle("report.fiere-mostre.organizzatore"));
	    //Column comune = new Column("comune.comune").title(getMessageFromBundle("report.fiere-mostre.comune-presentazione"));
	    Column comuneSvolgimento = new Column("comuneSvolgimento.comune").title(getMessageFromBundle("report.fiere-mostre.comune-svolgimento"));
	    Column merceologie = new Column().title(getMessageFromBundle("report.fiere-mostre.merceologie"));
	    merceologie.setCellEditor(new CellEditor() {

		public Object getValue(Object item, String property, int rowcount) {

		    if (item instanceof FiereMostre) {
			FiereMostre fm = (FiereMostre) item;
			StringBuffer sb = new StringBuffer("- ");
			for (FiereMostreMerceologie fmm : fm.getFiereMostreMerceologiesTransient()) {
			    sb.append(fmm.getMerceologia());
			    sb.append(" - ");
			}
			return sb.toString();
		    } else {
			return new BasicCellEditor().getValue(item, property, rowcount);
		    }
		}
	    });
	    Column classificazione = new Column("classificazione").title(getMessageFromBundle("report.fiere-mostre.classificazione"));
	    Column qualifica = new Column("qualifica").title(getMessageFromBundle("report.fiere-mostre.qualifica"));
	    Column tipologia = new Column("tipologia").title(getMessageFromBundle("report.fiere-mostre.tipologia"));
	    row.addColumn(dataInserimento);
	    row.addColumn(periodi);
	    row.addColumn(denominazione);
	    row.addColumn(comuneSvolgimento);
	    row.addColumn(luogo);
	    row.addColumn(merceologie);
	    row.addColumn(organizzatore);
	    row.addColumn(classificazione);
	    row.addColumn(qualifica);
	    row.addColumn(tipologia);
	    //row.addColumn(comune);	 
	    tableModel.setTable(table);
	    tableModel.render();
	    return true;
	}
	return false;
    }

    private void manageFilter(HttpServletRequest request, ManifestazioniSearchFilter filter) {

	if (StringUtils.isBlank(filter.getTipoManifestazione())) {
	    ManifestazioniSearchFilter _filter = (ManifestazioniSearchFilter) request.getSession().getAttribute("mfs");
	    //	    filter.setDataInserimento(_filter.getDataInserimento());
	    filter.setAl(_filter.getAl());
	    filter.setCodicecomune(_filter.getCodicecomune());
	    filter.setComune(_filter.getComune());
	    filter.setDal(_filter.getDal());
	    filter.setDenominazione(_filter.getDenominazione());
	    filter.setLuogoSvolgimento(_filter.getLuogoSvolgimento());
	    filter.setOrganizzatore(_filter.getOrganizzatore());
	    filter.setTipologia(_filter.getTipologia());
	    filter.setTipoManifestazione(_filter.getTipoManifestazione());
	} else {
	    request.getSession().setAttribute("mfs", filter);
	}
    }

    private boolean getFesteSagreExportTable(HttpServletRequest request, HttpServletResponse response, Collection<FesteSagre> items) {

	String id = "festeSagreTableId";
	TableModel tableModel = new TableModel(id, request, response);
	if (tableModel.isExporting()) {
	    tableModel.setItems(items);
	    Table table = new Table().caption(getMessageFromBundle("report.feste-sagre.titolo"));
	    Row row = new Row();
	    table.setRow(row);
	    //	    Column dataInserimento = new Column("dataInserimento").title(getMessageFromBundle("report.feste-sagre.data-inserimento")).cellEditor(
	    //		    new DateCellEditor("dd/MM/yyyy"));
	    Column dataInserimento = new Column("dataInserimento").title(getMessageFromBundle("label.data-inserimento")).cellEditor(
		    new DateCellEditor("dd/MM/yyyy"));
	    Column dal = new Column("dal").title(getMessageFromBundle("report.feste-sagre.dal")).cellEditor(new DateCellEditor("dd/MM/yyyy"));
	    Column al = new Column("al").title(getMessageFromBundle("report.feste-sagre.al")).cellEditor(new DateCellEditor("dd/MM/yyyy"));
	    Column denominazione = new Column("denominazione").title(getMessageFromBundle("report.feste-sagre.denominazione"));
	    Column tipologia = new Column("tipologia").title(getMessageFromBundle("report.feste-sagre.tipologia"));
	    Column comuneSvolgimento = new Column("comuneSvolgimento.comune").title(getMessageFromBundle("report.feste-sagre.comune-svolgimento"));
	    Column luogo = new Column("luogoSvolgimento").title(getMessageFromBundle("report.feste-sagre.luogo-svolgimento"));
	    Column organizzatore = new Column("organizzatore").title(getMessageFromBundle("report.feste-sagre.organizzatore"));
	    //Column comune = new Column("comune.comune").title(getMessageFromBundle("report.feste-sagre.comune-presentazione"));	 
	    row.addColumn(dataInserimento);
	    row.addColumn(dal);
	    row.addColumn(al);
	    row.addColumn(denominazione);
	    row.addColumn(tipologia);
	    row.addColumn(comuneSvolgimento);
	    row.addColumn(luogo);
	    row.addColumn(organizzatore);
	    //row.addColumn(comune);	
	    tableModel.setTable(table);
	    tableModel.render();
	    return true;
	}
	return false;
    }
}
