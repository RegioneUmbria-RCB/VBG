package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.domain.helper.ScadenzeHelper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;

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
public class ScadenzeHelperColumnTotal extends AbstractHtmlView {

    public Object render() {

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

    @SuppressWarnings("unchecked")
    protected String totals() {

	BigDecimal totalsImporti = new BigDecimal(0);
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
	for (Object obj : items) {
	    Integer totalRow = getCoreContext().getLimit().getRowSelect().getMaxRows();
	    if (totRow < totalRow) {
		ScadenzeHelper scadenze = (ScadenzeHelper) obj;
		BigDecimal importo = scadenze.getSommaEmesso();
		totalsImporti = totalsImporti.add(importo);
		totRow++;
	    }
	}
	String total = getCoreContext().getMessage("label.totalColumn");
	if (total == null) {
	    total = "???label.totalColumn???";
	}
	NumberFormat numberFormat = new DecimalFormat("###,##0.00");
	String totalsImportiFormat = numberFormat.format(((BigDecimal) totalsImporti).doubleValue());
	HtmlBuilder html = new HtmlBuilder();
	html.tr(1).styleClass("totalRow").close();
	html.td(2).close().tdEnd();
	html.td(2).close().tdEnd();
	html.td(2).close().append(total).tdEnd();
	html.td(2).close().append(totalsImportiFormat).tdEnd();
	html.td(2).close().tdEnd();
	html.td(2).close().tdEnd();
	html.trEnd(1);
	return html.toString();
    }
}
