package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.dao.helper.RaggruppamentoEnum;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;

import org.jmesa.view.component.Column;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.GroupCellEditor;
import org.jmesa.view.html.AbstractHtmlView;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlSnippets;

/**
 * 
 * Custom Total Editor. Editor per visualizzare la somma dei valori di una colonna.
 * 
 * @author francescop
 * 
 */
public class RegistrazioniFilterColumnTotal extends AbstractHtmlView {

    public Object render() {

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
	html.append(totals());
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
	    if (column.getProperty().equals("mercati.descrizione") || column.getProperty().equals("mercatiUso.descrizione")) {
		CellEditor decoratedCellEditor = column.getCellRenderer().getCellEditor();
		GroupCellEditor groupCellEditor = new GroupCellEditor(decoratedCellEditor);
		column.getCellRenderer().setCellEditor(groupCellEditor);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    protected String totals() {

	BigDecimal totalsEmesso = new BigDecimal(0);
	BigDecimal totalsIncassato = new BigDecimal(0);
	BigDecimal totalsSaldo = new BigDecimal(0);
	int totRow = 0;
	Collection items = null;
	if (getCoreContext().getLimit().getFilterSet().isFiltered()) {
	    items = getCoreContext().getFilteredItems();
	}
	if (getCoreContext().getLimit().getSortSet().isSorted()) {
	    items = getCoreContext().getSortedItems();
	}
	if (!getCoreContext().getLimit().getFilterSet().isFiltered() && !getCoreContext().getLimit().getSortSet().isSorted()) {
	    items = getCoreContext().getAllItems();
	}
	boolean columnAnno = false;
	RegistrazioniFilter registrazioniFilter = new RegistrazioniFilter();
	for (Object obj : items) {
	    Integer totalRow = getCoreContext().getLimit().getRowSelect().getMaxRows();
	    if (totRow < totalRow) {
		registrazioniFilter = (RegistrazioniFilter) obj;
		BigDecimal emesso = registrazioniFilter.getEmesso();
		BigDecimal incassato = registrazioniFilter.getIncassato();
		BigDecimal saldo = registrazioniFilter.getSaldo();
		totalsEmesso = totalsEmesso.add(emesso);
		totalsIncassato = totalsIncassato.add(incassato);
		totalsSaldo = totalsSaldo.add(saldo);
		totRow++;
	    }
	}
	if (registrazioniFilter.getAnno() != 0) {
	    columnAnno = true;
	}
	boolean ragrMercati = false;
	if (registrazioniFilter.getRaggruppamentoEnum() != null && registrazioniFilter.getRaggruppamentoEnum().equals(RaggruppamentoEnum.MERCATO)) {
	    ragrMercati = true;
	}
	HtmlBuilder html = new HtmlBuilder();
	String total = getCoreContext().getMessage("label.totalColumn");
	if (total == null) {
	    total = "???label.totalColumn???";
	}
	html.tr(1).styleClass("totalRow").close();
	if (columnAnno) {
	    html.td(2).close().tdEnd();
	}
	if (ragrMercati) {
	    html.td(2).close().tdEnd();
	    html.td(2).close().tdEnd();
	}
	NumberFormat numberFormat = new DecimalFormat("###,##0.00");
	String totalsEmessoFormat = numberFormat.format(((BigDecimal) totalsEmesso).doubleValue());
	String totalsIncassatoFormat = numberFormat.format(((BigDecimal) totalsIncassato).doubleValue());
	String totalsSaldoFormat = numberFormat.format(((BigDecimal) totalsSaldo).doubleValue());
	html.td(2).style("text-align: right;").close().append(total).tdEnd();
	html.td(2).style("text-align: right;").close().append(totalsEmessoFormat).tdEnd();
	html.td(2).style("text-align: right;").close().append(totalsIncassatoFormat).tdEnd();
	html.td(2).style("text-align: right;").close().append(totalsSaldoFormat).tdEnd();
	html.td(2).close().tdEnd();
	html.td(2).close().tdEnd();
	html.trEnd(1);
	return html.toString();
    }
}
