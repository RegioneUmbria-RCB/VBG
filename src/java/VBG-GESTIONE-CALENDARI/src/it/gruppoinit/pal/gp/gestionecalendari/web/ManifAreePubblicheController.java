package it.gruppoinit.pal.gp.gestionecalendari.web;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.FiereMostre;
import it.gruppoinit.pal.gp.core.domain.FiereMostreMerceologie;
import it.gruppoinit.pal.gp.core.domain.FiereMostrePeriodi;
import it.gruppoinit.pal.gp.core.domain.ManifAreePubbliche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ManifAreePubblicheService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.ManifareepubblicheCommand;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.ManifestazioniAreePubblicheSearchFilter;
import it.gruppoinit.pal.gp.gestionecalendari.web.validator.ManifAreePubblicheValidator;

import java.text.SimpleDateFormat;
import java.util.Collection;
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
@SessionAttributes("manifareepubblicheCommand")
public class ManifAreePubblicheController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ManifAreePubblicheController.class);
    @Autowired
    private ManifAreePubblicheService manifareepubblicheService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private ResponsabilicomuniService responsabilicomuniService;
    @Autowired
    private ComuniService comuniService;

    @RequestMapping
    public String startSearch(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("ManifAreePubblicheController#startSearch");
	ManifestazioniAreePubblicheSearchFilter filter = new ManifestazioniAreePubblicheSearchFilter();
	Responsabili responsabileLoggato = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	List<Responsabilicomuni> comuni = responsabilicomuniService.findByResponsabile(responsabileLoggato);
	removeREGU(comuni);
	model.addAttribute("comuni", comuni);
	request.getSession().removeAttribute("mfs");
	model.addAttribute("filter", filter);
	model.addAttribute("c_menu", "cerca");
	return "manifareepubbliche/search";
    }

    @RequestMapping
    public String search(Model model, @ModelAttribute("filter") ManifestazioniAreePubblicheSearchFilter command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	log.info("search");
	Responsabili responsabileLoggato = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	try {
	    manageFilter(request, command);
	    //new ManifestazioniSearchFilterValidator().validate(command, result);
	    if (result.hasErrors()) {
		List<Responsabilicomuni> comuni = responsabilicomuniService.findByResponsabile(responsabileLoggato);
		model.addAttribute("comuni", comuni);
		return "manifareepubbliche/search";
	    }
	    if (StringUtils.isNotBlank(command.getCodicecomune())) {
		command.setComune(comuniService.findByCodiceComune(new Comuni(command.getCodicecomune())).getComune());
	    }
	    List<ManifAreePubbliche> manifAreePubblicheList = manifareepubblicheService.findByFilter(command);
	    boolean export = getManifestazioniAreePubblicheExportTable(request, response, manifAreePubblicheList);
	    if (export)
		return null;
	    model.addAttribute("manifAreePubblicheList", manifAreePubblicheList);
	} catch (Exception e) {
	    log.error("search", e);
	    List<Responsabilicomuni> comuni = responsabilicomuniService.findByResponsabile(responsabileLoggato);
	    model.addAttribute("comuni", comuni);
	    addErrors(model, e);
	    return "manifareepubbliche/search";
	}
	return "manifareepubbliche/list";
    }

    @RequestMapping
    public String salva(Model model, @ModelAttribute("manifareepubblicheCommand") ManifareepubblicheCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response,
	    @RequestParam(value = "returnto", required = false) String returnto) {

	log.info("salva");
	Responsabili responsabileLoggato = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	List<Responsabilicomuni> comuni = responsabilicomuniService.findByResponsabile(responsabileLoggato);
	try {
	    model.addAttribute("comuni", comuni);
	    Integer codice = null;
	    ManifAreePubbliche manifAreePubbliche = command.getEntity();
	    new ManifAreePubblicheValidator().validate(manifAreePubbliche, result);
	    if (result.hasErrors()) {
		model.addAttribute("returnto", returnto);
		removeREGU(comuni);
		return "manifareepubbliche/form";
	    }
	    if (command.getEntity().getId() != null && command.getEntity().getId().getCodice() != null) {
		manifareepubblicheService.update(manifAreePubbliche);
	    } else {
		manifareepubblicheService.insert(manifAreePubbliche);
	    }
	    codice = manifAreePubbliche.getId().getCodice();
	    status.setComplete();
	    return "redirect:view.htm?codice=" + codice + "&returnto=" + returnto + "&ret=0";
	} catch (Exception e) {
	    log.error("salva", e);
	    removeREGU(comuni);
	    addErrors(model, e);
	    return "manifareepubbliche/form";
	}
    }

    @RequestMapping
    public String view(Model model, @RequestParam(value = "codice", required = false) Integer codice,
	    @RequestParam(value = "returnto", required = false) String returnto, HttpServletRequest request, HttpServletResponse response) {

	log.info("view:  codice={}", codice);
	Responsabili responsabileLoggato = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	List<Responsabilicomuni> comuni = responsabilicomuniService.findByResponsabile(responsabileLoggato);
	removeREGU(comuni);
	model.addAttribute("comuni", comuni);
	ManifareepubblicheCommand command = new ManifareepubblicheCommand();
	if (codice != null) {
	    ManifAreePubbliche entity = manifareepubblicheService.findById(new PkId(codice));
	    command.setEntity(entity);
	}
	//	command.init();
	model.addAttribute("returnto", returnto);
	model.addAttribute("manifareepubblicheCommand", command);
	return "manifareepubbliche/form";
    }

    @RequestMapping
    public String delete(Model model, HttpServletRequest request, @RequestParam("codice") Integer codice, HttpServletResponse response) {

	log.info("delete: codice={}", codice);
	ManifAreePubbliche objectDelete = manifareepubblicheService.findById(new PkId(codice));
	manifareepubblicheService.delete(objectDelete);
	return "redirect:startSearch.htm";
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

    private void manageFilter(HttpServletRequest request, ManifestazioniAreePubblicheSearchFilter filter) {

	//	ManifestazioniAreePubblicheSearchFilter _filter = (ManifestazioniAreePubblicheSearchFilter) request.getSession().getAttribute("mfs");
	//	filter.setCodicecomune(_filter.getCodicecomune());
	//	filter.setComune(_filter.getComune());
	//	filter.setDenominazione(_filter.getDenominazione());
	//	filter.setTipologia(_filter.getTipologia());
	//	filter.setCadenza(_filter.getCadenza());
	//	filter.setGioni(_filter.getGioni());
	request.getSession().setAttribute("mfs", filter);
    }

    private boolean getManifestazioniAreePubblicheExportTable(HttpServletRequest request, HttpServletResponse response,
	    Collection<ManifAreePubbliche> items) {

	String id = "manifestazioniAreepubblicheTableId";
	TableModel tableModel = new TableModel(id, request, response);
	if (tableModel.isExporting()) {
	    tableModel.setItems(items);
	    Table table = new Table().caption(getMessageFromBundle("report.manifestazioni-aree-pubbliche.titolo"));
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
}
