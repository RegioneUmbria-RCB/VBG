package org.jmesa.custom;

import java.util.List;

import org.jmesa.view.component.Column;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.GroupCellEditor;
import org.jmesa.view.html.AbstractHtmlView;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlSnippets;

public class GroupRegistrazioniInOutView extends AbstractHtmlView {

    public String render() {

	setCustomCellEditors();
	HtmlSnippets snippets = getHtmlSnippets();
	HtmlBuilder html = new HtmlBuilder();
	html.append(snippets.themeStart());
	html.append(snippets.tableStart());
	html.append(snippets.theadStart());
	html.append(snippets.toolbar());
	html.append(snippets.filter());
	html.append(snippets.header());
	html.append(snippets.theadEnd());
	html.append(snippets.tbodyStart());
	html.append(snippets.body());
	html.append(snippets.tbodyEnd());
	html.append(snippets.footer());
	html.append(snippets.statusBar());
	html.append(snippets.tableEnd());
	html.append(snippets.themeEnd());
	html.append(snippets.initJavascriptLimit());
	return html.toString();
    }

    /**
     * Raggruppo per numero rata e scadenza
     */
    private void setCustomCellEditors() {

	List<Column> columns = getTable().getRow().getColumns();
	for (Column column : columns) {
	    if (column.getProperty().equals("anagrafe.descrizioneRichiedente") || column.getProperty().equals("mercati.descrizione")
		    || column.getProperty().equals("mercatiUso.descrizione") || column.getProperty().equals("posteggio.codiceposteggio")) {
		CellEditor decoratedCellEditor = column.getCellRenderer().getCellEditor();
		GroupCellEditor groupCellEditor = new GroupCellEditor(decoratedCellEditor);
		column.getCellRenderer().setCellEditor(groupCellEditor);
	    }
	}
    }
}
