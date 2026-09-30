package it.gruppoinit.stc.web.helper;

import it.gruppoinit.stc.domain.Configurazione;

import org.jmesa.model.TableModel;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.worksheet.editor.AbstractWorksheetEditor;
import org.jmesa.worksheet.editor.CheckboxWorksheetEditor;
import org.jmesa.worksheet.editor.RemoveRowWorksheetEditor;

public class WorksheetConfigurazione {

    public static void setTableProperties(TableModel tableModel) {

	tableModel.setEditable(true);
	tableModel.setStateAttr("restore");
	tableModel.setTable(getWorksheetTable());
    }

    private static Table getWorksheetTable() {

	HtmlTable worksheetTable = new HtmlTable().caption("Configurazione nodi NLA").width("100%");
	HtmlRow htmlRow = new HtmlRow().uniqueProperty("idnodo");
	worksheetTable.setRow(htmlRow);
	HtmlColumn remove = new HtmlColumn("remove");
	remove.setWorksheetEditor(new RemoveRowWorksheetEditor());
	remove.setTitle("&nbsp;");
	remove.setFilterable(Boolean.FALSE);
	remove.setSortable(Boolean.FALSE);
	htmlRow.addColumn(remove);
	HtmlColumn chkbox = new HtmlColumn("selected").title("&nbsp;");
	chkbox.filterable(Boolean.FALSE).sortable(Boolean.FALSE);
	chkbox.setWorksheetEditor(new CheckboxWorksheetEditor());
	HtmlColumn idNodo = new HtmlColumn("idnodo").title("Id Nodo");
	htmlRow.addColumn(idNodo);
	HtmlColumn descrizione = new HtmlColumn("descrizione").title("Descrizione");
	htmlRow.addColumn(descrizione);
	HtmlColumn idDirezione = new HtmlColumn("iddirezione").title("Id Direzione");
	htmlRow.addColumn(idDirezione);
	HtmlColumn direzione = new HtmlColumn("direzione").title("Direzione");
	htmlRow.addColumn(direzione);
	HtmlColumn wsUrl = new HtmlColumn("wsurl").title("URL");
	htmlRow.addColumn(wsUrl);
	HtmlColumn userid = new HtmlColumn("userid").title("Utente");
	htmlRow.addColumn(userid);
	//userid.addWorksheetValidation(new WorksheetValidation(WorksheetValidationType.REQUIRED, WorksheetValidation.TRUE));
	HtmlColumn password = new HtmlColumn("password").title("Password");
	htmlRow.addColumn(password);
	HtmlColumn test = new HtmlColumn("test");
	test.setWorksheetEditor(new AbstractWorksheetEditor() {

	    @Override
	    public Object getValue(Object item, String property, int rowcount) {

		if (item instanceof Configurazione) {
		    Configurazione conf = (Configurazione) item;
		    String goTo = "../worksheetconfigurazione/list.htm?test=true&nodoDaTestare=" + conf.getIdnodo();
		    String valueItem = "<input type=\"button\" value=\"test\" name=\"test" + conf.getIdnodo()
			    + "\" onclick=\"document.location.href='" + goTo + "'\" title=\"esegui il test del nodo\" />";
		    return valueItem;
		}
		return "";
	    }
	});
	test.setTitle("Test nodo");
	test.setFilterable(Boolean.FALSE);
	test.setSortable(Boolean.FALSE);
	htmlRow.addColumn(test);
	return worksheetTable;
    }
}
