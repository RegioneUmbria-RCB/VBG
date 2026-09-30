package it.gruppoinit.stc.web;

import it.gruppoinit.stc.domain.Attivita;
import it.gruppoinit.stc.domain.Messaggiattivita;
import it.gruppoinit.stc.domain.Messaggipratiche;
import it.gruppoinit.stc.domain.Pratiche;
import it.gruppoinit.stc.service.AttivitaService;
import it.gruppoinit.stc.service.MessaggiattivitaService;
import it.gruppoinit.stc.service.MessaggipraticheService;
import it.gruppoinit.stc.service.PraticheService;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.model.TableModel;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
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
public class PraticheController extends BaseController {

    @Autowired
    private PraticheService praticheService;
    @Autowired
    private AttivitaService attivitaService;
    @Autowired
    private MessaggipraticheService messaggipraticheService;
    @Autowired
    private MessaggiattivitaService messaggiattivitaService;

    @RequestMapping
    public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	TableModel tableModel = new TableModel("pratiche_id", request, response);
	List<Messaggipratiche> messaggipratiche = messaggipraticheService.findAll(null, null);
	tableModel.setItems(messaggipratiche);
	//tableModel.addFilterMatcher(new MatcherKey(Date.class, "datapratica"), new DateFilterMatcher("dd/MM/yyyy"));
	tableModel.setStateAttr("restore");
	tableModel.setTable(getPraticheHtmlTable());
	String view = tableModel.render();
	request.setAttribute("pratiche", view);
	return "pratiche/list";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@RequestParam(value = "codicePratica") Integer id) throws Exception {

	// §§§BEGIN§§§
	Pratiche pratica = praticheService.findById(id);
	//cancello tutti i record in messaggiattivita delle attivita della pratica mittente
	Set<Attivita> attsP = pratica.getAttivitas();
	for (Attivita attivita : attsP) {
	    Set<Messaggiattivita> msgsAr = attivita.getMessaggiattivitasForFkidrichiesta();
	    for (Messaggiattivita messaggiattivita : msgsAr) {
		//del mgs att
		messaggiattivitaService.delete(messaggiattivita);
	    }
	    Set<Messaggiattivita> msgsAris = attivita.getMessaggiattivitasForFkidrisposta();
	    for (Messaggiattivita messaggiattivita : msgsAris) {
		//del mgs att
		messaggiattivitaService.delete(messaggiattivita);
	    }
	    //del att
	    attivitaService.delete(attivita);
	}
	//flush?
	Set<Messaggipratiche> msgsP = pratica.getMessaggipratichesForFkidrichiesta();
	for (Messaggipratiche messaggipratiche : msgsP) {
	    Pratiche praticaColl = messaggipratiche.getPraticheByFkidrisposta();
	    //cancello tutti i record in messaggiattivita delle attivita della pratica collegata
	    Set<Attivita> atts = praticaColl.getAttivitas();
	    for (Attivita attivita : atts) {
		Set<Messaggiattivita> msgsAr = attivita.getMessaggiattivitasForFkidrichiesta();
		for (Messaggiattivita messaggiattivita : msgsAr) {
		    //del
		    messaggiattivitaService.delete(messaggiattivita);
		}
		Set<Messaggiattivita> msgsAris = attivita.getMessaggiattivitasForFkidrisposta();
		for (Messaggiattivita messaggiattivita : msgsAris) {
		    //del
		    messaggiattivitaService.delete(messaggiattivita);
		}
		//del att
		attivitaService.delete(attivita);
	    }
	    //del msg pra
	    messaggipraticheService.delete(messaggipratiche);
	    //del pra coll
	    praticheService.delete(praticaColl);
	}
	//del pra
	praticheService.delete(pratica);
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private Table getPraticheHtmlTable() {

	// §§§BEGIN§§§
	HtmlTable htmlTable = new HtmlTable().caption("Pratiche").width("100%");
	HtmlRow htmlRow = new HtmlRow();
	htmlTable.setRow(htmlRow);
	HtmlColumn idNodoMitt = new HtmlColumn("praticheByFkidrichiesta.configurazioneByFkidnodo.idnodo").title("Id Nodo Mitt.");
	htmlRow.addColumn(idNodoMitt);
	HtmlColumn idEnteMitt = new HtmlColumn("praticheByFkidrichiesta.idente").title("Id Ente Mitt.");
	htmlRow.addColumn(idEnteMitt);
	HtmlColumn idSportelloMitt = new HtmlColumn("praticheByFkidrichiesta.idsportello").title("Id Sportello Mitt.");
	htmlRow.addColumn(idSportelloMitt);
	HtmlColumn idPraticaMitt = new HtmlColumn("praticheByFkidrichiesta.idpratica").title("Id Pratica Mitt.");
	htmlRow.addColumn(idPraticaMitt);
	HtmlColumn numPraticaMitt = new HtmlColumn("praticheByFkidrichiesta.numpratica").title("Num. Pratica Mitt.");
	htmlRow.addColumn(numPraticaMitt);
	HtmlColumn attivitaPraticaMitt = new HtmlColumn("").title("Attività Mitt.");
	attivitaPraticaMitt.setStyle("vertical-align:top");
	attivitaPraticaMitt.setCellEditor(new CellEditor() {

	    public Object getValue(Object item, String property, int rowcount) {

		HtmlBuilder html = new HtmlBuilder();
		Set<Attivita> attivita = ((Messaggipratiche) item).getPraticheByFkidrichiesta().getAttivitas();
		if (attivita != null && !attivita.isEmpty()) {
		    HtmlBuilder table = html.table(0);
		    //table.border("1");
		    table.width("100%").close();
		    table.thead(0).close();
		    table.tr(0).close();
		    table.td(0).close().append("<b>Id</b>");
		    table.tdEnd();
		    table.td(0).close().append("<b>Proc.</b>");
		    table.tdEnd();
		    table.td(0).close().append("<b>Tipo</b>");
		    table.tdEnd();
		    table.td(0).close().append("<b>Dest.</b>");
		    table.tdEnd();
		    table.trEnd(0);
		    table.theadEnd(0);
		    table.tbody(0).close();
		    for (Attivita att : attivita) {
			table.tr(0).close();
			table.td(0).close().append(att.getIdattivita());
			table.tdEnd();
			table.td(0).close().append(att.getIdprocedimento());
			table.tdEnd();
			table.td(0).close().append(att.getTipoattivita());
			table.tdEnd();
			HtmlBuilder hb = table.td(0).close();
			Set<Messaggiattivita> msgs = att.getMessaggiattivitasForFkidrichiesta();
			if (!msgs.isEmpty()) {
			    Messaggiattivita msg = msgs.iterator().next();
			    hb.append("(" + msg.getAttivitaByFkidrisposta().getIdattivita() + ")");
			}
			table.tdEnd();
			table.trEnd(0);
		    }
		    table.tbodyEnd(0);
		    table.tableEnd(0);
		}
		return html.toString();
	    }
	});
	htmlRow.addColumn(attivitaPraticaMitt);
	HtmlColumn idNodoDest = new HtmlColumn("praticheByFkidrisposta.configurazioneByFkidnodo.idnodo").title("Id Nodo Dest.");
	htmlRow.addColumn(idNodoDest);
	HtmlColumn idEnteDest = new HtmlColumn("praticheByFkidrisposta.idente").title("Id Ente Dest.");
	htmlRow.addColumn(idEnteDest);
	HtmlColumn idSportelloDest = new HtmlColumn("praticheByFkidrisposta.idsportello").title("Id Sportello Dest.");
	htmlRow.addColumn(idSportelloDest);
	HtmlColumn idPraticaDest = new HtmlColumn("praticheByFkidrisposta.idpratica").title("Id Pratica Dest.");
	htmlRow.addColumn(idPraticaDest);
	HtmlColumn numPraticaDest = new HtmlColumn("praticheByFkidrisposta.numpratica").title("Num. Pratica Dest.");
	htmlRow.addColumn(numPraticaDest);
	HtmlColumn attivitaPraticaDest = new HtmlColumn("").title("Attività Dest.");
	attivitaPraticaDest.setStyle("vertical-align:top");
	attivitaPraticaDest.setCellEditor(new CellEditor() {

	    public Object getValue(Object item, String property, int rowcount) {

		HtmlBuilder html = new HtmlBuilder();
		Set<Attivita> attivita = ((Messaggipratiche) item).getPraticheByFkidrisposta().getAttivitas();
		if (attivita != null && !attivita.isEmpty()) {
		    HtmlBuilder table = html.table(0);
		    //table.border("1");
		    table.width("100%").close();
		    table.thead(0).close();
		    table.tr(0).close();
		    table.td(0).close().append("<b>Id</b>");
		    table.tdEnd();
		    table.td(0).close().append("<b>Proc.</b>");
		    table.tdEnd();
		    table.td(0).close().append("<b>Tipo</b>");
		    table.tdEnd();
		    table.td(0).close().append("<b>Dest.</b>");
		    table.tdEnd();
		    table.trEnd(0);
		    table.theadEnd(0);
		    table.tbody(0).close();
		    for (Attivita att : attivita) {
			table.tr(0).close();
			table.td(0).close().append(att.getIdattivita());
			table.tdEnd();
			table.td(0).close().append(att.getIdprocedimento());
			table.tdEnd();
			table.td(0).close().append(att.getTipoattivita());
			table.tdEnd();
			HtmlBuilder hb = table.td(0).close();
			Set<Messaggiattivita> msgs = att.getMessaggiattivitasForFkidrichiesta();
			if (!msgs.isEmpty()) {
			    Messaggiattivita msg = msgs.iterator().next();
			    hb.append("(" + msg.getAttivitaByFkidrisposta().getIdattivita() + ")");
			}
			table.trEnd(0);
		    }
		    table.tbodyEnd(0);
		    table.tableEnd(0);
		}
		return html.toString();
	    }
	});
	htmlRow.addColumn(attivitaPraticaDest);
	HtmlColumn elimina = new HtmlColumn("praticheByFkidrichiesta.id").title("Azione");
	elimina.setCellEditor(new CellEditor() {

	    public Object getValue(Object item, String property, int rowcount) {

		Object value = new HtmlCellEditor().getValue(item, property, rowcount);
		HtmlBuilder html = new HtmlBuilder();
		HtmlBuilder img = html.img();
		img.src("../images/table/removeWorksheetRow.png");
		img.onclick("deletePratica('" + value + "');");
		img.style("cursor:pointer;");
		img.title("Cancella pratica " + value);
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
