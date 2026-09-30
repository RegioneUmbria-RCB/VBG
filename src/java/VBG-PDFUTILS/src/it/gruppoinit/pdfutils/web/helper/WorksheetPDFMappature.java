package it.gruppoinit.pdfutils.web.helper;

import it.gruppoinit.pdfutils.domain.PDFMappature;

import org.jmesa.model.TableModel;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.worksheet.editor.AbstractWorksheetEditor;

public class WorksheetPDFMappature {

    public static void setTableProperties(TableModel tableModel) {

	tableModel.setEditable(true);
	tableModel.setStateAttr("restore");
	tableModel.setTable(getWorksheetTable());
    }

    private static Table getWorksheetTable() {

	HtmlTable worksheetTable = new HtmlTable().caption("Configurazione Mappature Campi PDF").width("100%");
	HtmlRow htmlRow = new HtmlRow().uniqueProperty("label");
	worksheetTable.setRow(htmlRow);
	HtmlColumn label = new HtmlColumn("label").title("Etichetta");
	htmlRow.addColumn(label);
	HtmlColumn descrizione = new HtmlColumn("ambito").title("Ambito");
	htmlRow.addColumn(descrizione);
	HtmlColumn xpath = new HtmlColumn("xpath").title("Percorso Xpath");
	htmlRow.addColumn(xpath);
	htmlRow.addColumn(new HtmlColumn("decodTipo").title("Tipo decodifica"));
	htmlRow.addColumn(new HtmlColumn("decodFormatoInput").title("Input"));
	htmlRow.addColumn(new HtmlColumn("decodFormatoOutput").title("Output"));
	HtmlColumn remove = new HtmlColumn("remove");
	remove.setWorksheetEditor(new AbstractWorksheetEditor() {

	    @Override
	    public Object getValue(Object item, String property, int rowcount) {

		if (item instanceof PDFMappature) {
		    PDFMappature conf = (PDFMappature) item;
		    String goTo = "../pdfmapping/delete.htm?id=" + conf.getLabel();
		    String valueItem = "<input type=\"button\" value=\"elimina\" name=\"elimina" + conf.getLabel()
			    + "\" onclick=\"if(confirm('Attenzione! Cancellare il dato?')){document.location.href='" + goTo + "';}\" title=\"Elimina la voce corrente\" />";
		    return valueItem;
		}
		return "";
	    }
	});
	htmlRow.addColumn(remove);
	return worksheetTable;
    }

    public static void setTableExportingProperties(TableModel tableModel) {

	Table table = new Table().caption("Configurazione Mappature Campi PDF");
	Row row = new Row();
	table.setRow(row);
	Column label = new Column("label").title("Etichetta");
	row.addColumn(label);
	Column ambito = new Column("ambito").title("Ambito");
	row.addColumn(ambito);
	Column xpath = new Column("xpath").title("XPath");
	row.addColumn(xpath);
	row.addColumn(new Column("decodTipo").title("Tipo decodifica"));
	row.addColumn(new Column("decodFormatoInput").title("Input"));
	row.addColumn(new Column("decodFormatoOutput").title("Output"));
	tableModel.setTable(table);
    }
}
