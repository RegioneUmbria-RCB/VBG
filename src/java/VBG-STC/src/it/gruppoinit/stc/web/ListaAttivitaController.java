package it.gruppoinit.stc.web;

import it.gruppoinit.stc.service.AttivitaService;
import it.gruppoinit.stc.service.MessaggiattivitaService;
import it.gruppoinit.stc.service.MessaggipraticheService;
import it.gruppoinit.stc.service.PraticheService;
import it.gruppoinit.stc.service.VwMessaggiattivitaService;
import it.gruppoinit.stc.service.VwMessaggipraticheService;
import it.gruppoinit.stc.web.helper.VwMessaggiattivitaPageItems;
import it.gruppoinit.stc.web.helper.VwMessaggipratichePageItems;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.model.PageItems;
import org.jmesa.model.TableModel;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ListaAttivitaController extends BaseController {

    @Autowired
    private VwMessaggipraticheService vwMessaggipraticheService;
    @Autowired
    private VwMessaggiattivitaService vwMessaggiattivitaService;
    @Autowired
    private AttivitaService attivitaService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private MessaggiattivitaService messaggiattivitaService;
    @Autowired
    private MessaggipraticheService messaggipraticheService;

    @RequestMapping
    public String listPratiche(HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	TableModel tableModel = new TableModel("messaggipratiche_id", request, response);
	PageItems records = new VwMessaggipratichePageItems(vwMessaggipraticheService);
	tableModel.setItems(records);
	//tableModel.addFilterMatcher(new MatcherKey(Date.class, "datapratica"), new DateFilterMatcher("dd/MM/yyyy"));
	tableModel.setStateAttr("restore");
	tableModel.setTable(getPraticheHtmlTable());
	String view = tableModel.render();
	request.setAttribute("pratiche", view);
	return "pratiche/listPratiche";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String listAttivita(HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	TableModel tableModel = new TableModel("messaggiattivita_id", request, response);
	PageItems records = new VwMessaggiattivitaPageItems(vwMessaggiattivitaService);
	tableModel.setItems(records);
	//tableModel.addFilterMatcher(new MatcherKey(Date.class, "datapratica"), new DateFilterMatcher("dd/MM/yyyy"));
	tableModel.setStateAttr("restore");
	tableModel.setTable(getAttivitaHtmlTable());
	String view = tableModel.render();
	request.setAttribute("pratiche", view);
	return "pratiche/listAttivita";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String deleteMA(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	messaggiattivitaService.deleteCollegamento();
	return "redirect:listAttivita.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String deleteMP(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	messaggipraticheService.deleteCollegamento();
	return "redirect:listPratiche.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private Table getAttivitaHtmlTable() {

	// §§§BEGIN§§§
	HtmlTable htmlTable = new HtmlTable().caption("Messaggi attivita").width("100%");
	HtmlRow htmlRow = new HtmlRow();
	htmlTable.setRow(htmlRow);
	HtmlColumn id = new HtmlColumn("id").title("Id MA");
	htmlRow.addColumn(id);
	HtmlColumn idmittIdnodo = new HtmlColumn("mittIdnodo").title("Mitt Idnodo");
	htmlRow.addColumn(idmittIdnodo);
	HtmlColumn idmittIdente = new HtmlColumn("mittIdente").title("Mitt Idente");
	htmlRow.addColumn(idmittIdente);
	HtmlColumn idmittIdsportello = new HtmlColumn("mittIdsportello").title("Mitt Idsportello");
	htmlRow.addColumn(idmittIdsportello);
	HtmlColumn idmittIdpratica = new HtmlColumn("mittIdpratica").title("Mitt Idpratica");
	htmlRow.addColumn(idmittIdpratica);
	HtmlColumn idmittNumpratica = new HtmlColumn("mittNumpratica").title("Mitt Numpratica");
	htmlRow.addColumn(idmittNumpratica);
	HtmlColumn idattMittId = new HtmlColumn("mittAttId").title("Mitt Att Id");
	htmlRow.addColumn(idattMittId);
	HtmlColumn idattMittTipo = new HtmlColumn("mittAttTipo").title("Mitt Att Tipo");
	htmlRow.addColumn(idattMittTipo);
	HtmlColumn idattMittIdproc = new HtmlColumn("mittAttIdproc").title("Mitt Att Idproc");
	htmlRow.addColumn(idattMittIdproc);
	HtmlColumn idattMittData = new HtmlColumn("mittAttData").title("Mitt Att data");
	idattMittData.setCellEditor(new DateCellEditor("dd/MM/yyyy"));
	htmlRow.addColumn(idattMittData);
	HtmlColumn iddestIdnodo = new HtmlColumn("destIdnodo").title("Dest Idnodo");
	htmlRow.addColumn(iddestIdnodo);
	HtmlColumn iddestIdente = new HtmlColumn("destIdente").title("Dest Idente");
	htmlRow.addColumn(iddestIdente);
	HtmlColumn iddestIdsportello = new HtmlColumn("destIdsportello").title("Dest Idsportello");
	htmlRow.addColumn(iddestIdsportello);
	HtmlColumn iddestIdpratica = new HtmlColumn("destIdpratica").title("Dest Idpratica");
	htmlRow.addColumn(iddestIdpratica);
	HtmlColumn iddestNumpratica = new HtmlColumn("destNumpratica").title("Dest Numpratica");
	htmlRow.addColumn(iddestNumpratica);
	HtmlColumn idattDestId = new HtmlColumn("destAttId").title("Dest Att Id");
	htmlRow.addColumn(idattDestId);
	HtmlColumn idattDestTipo = new HtmlColumn("destAttTipo").title("Dest Att Tipo");
	htmlRow.addColumn(idattDestTipo);
	HtmlColumn idattDestIdproc = new HtmlColumn("destAttIdproc").title("Dest Att Idproc");
	htmlRow.addColumn(idattDestIdproc);
	HtmlColumn idattDestData = new HtmlColumn("destAttData").title("Dest Att data");
	idattDestData.setCellEditor(new DateCellEditor("dd/MM/yyyy"));
	htmlRow.addColumn(idattDestData);
	HtmlColumn elimina = new HtmlColumn("id").title("Azione");
	elimina.setCellEditor(new CellEditor() {

	    public Object getValue(Object item, String property, int rowcount) {

		Object value = new HtmlCellEditor().getValue(item, property, rowcount);
		HtmlBuilder html = new HtmlBuilder();
		HtmlBuilder img = html.img();
		img.src("../images/table/removeWorksheetRow.png");
		img.onclick("deleteMessaggioAttivita('" + value + "');");
		img.style("cursor:pointer;");
		img.title("cancella il messaggio attività " + value);
		img.close();
		return html.toString();
	    }
	});
	htmlRow.addColumn(elimina);
	return htmlTable;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private Table getPraticheHtmlTable() {

	// §§§BEGIN§§§
	HtmlTable htmlTable = new HtmlTable().caption("Messaggi Pratiche").width("100%");
	HtmlRow htmlRow = new HtmlRow();
	htmlTable.setRow(htmlRow);
	HtmlColumn id = new HtmlColumn("id").title("Id Messaggipratiche");
	htmlRow.addColumn(id);
	HtmlColumn idNodo = new HtmlColumn("mittIdnodo").title("Mitt Idnodo");
	htmlRow.addColumn(idNodo);
	HtmlColumn idmittIdente = new HtmlColumn("mittIdente").title("Mitt Idente");
	htmlRow.addColumn(idmittIdente);
	HtmlColumn idmittIdsportello = new HtmlColumn("mittIdsportello").title("Mitt Idsportello");
	htmlRow.addColumn(idmittIdsportello);
	HtmlColumn idmittIdpratica = new HtmlColumn("mittIdpratica").title("Mitt Idpratica");
	htmlRow.addColumn(idmittIdpratica);
	HtmlColumn idmittNumpratica = new HtmlColumn("mittNumpratica").title("Mitt Numpratica");
	htmlRow.addColumn(idmittNumpratica);
	HtmlColumn idmittDatapratica = new HtmlColumn("mittDatapratica").title("Mitt datapratica");
	htmlRow.addColumn(idmittDatapratica);
	idmittDatapratica.setCellEditor(new DateCellEditor("dd/MM/yyyy"));
	HtmlColumn iddestIdnodo = new HtmlColumn("destIdente").title("Dest Idnodo");
	htmlRow.addColumn(iddestIdnodo);
	HtmlColumn iddestIdente = new HtmlColumn("destIdente").title("Dest Idente");
	htmlRow.addColumn(iddestIdente);
	HtmlColumn iddestIdsportello = new HtmlColumn("destIdsportello").title("Dest Idsportello");
	htmlRow.addColumn(iddestIdsportello);
	HtmlColumn iddestIdpratica = new HtmlColumn("destIdpratica").title("Dest Idpratica");
	htmlRow.addColumn(iddestIdpratica);
	HtmlColumn iddestNumpratica = new HtmlColumn("destNumpratica").title("Dest Numpratica");
	htmlRow.addColumn(iddestNumpratica);
	HtmlColumn iddestDatapratica = new HtmlColumn("destDatapratica").title("Dest datapratica");
	htmlRow.addColumn(iddestDatapratica);
	iddestDatapratica.setCellEditor(new DateCellEditor("dd/MM/yyyy"));
	HtmlColumn elimina = new HtmlColumn("id").title("Azione");
	elimina.setCellEditor(new CellEditor() {

	    public Object getValue(Object item, String property, int rowcount) {

		Object value = new HtmlCellEditor().getValue(item, property, rowcount);
		HtmlBuilder html = new HtmlBuilder();
		HtmlBuilder img = html.img();
		img.src("../images/table/removeWorksheetRow.png");
		img.onclick("deleteMessaggioPratica('" + value + "');");
		img.style("cursor:pointer;");
		img.title("Cancella il messaggio pratica " + value);
		img.close();
		return html.toString();
	    }
	});
	htmlRow.addColumn(elimina);
	return htmlTable;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
