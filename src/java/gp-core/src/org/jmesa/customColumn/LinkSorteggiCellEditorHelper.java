package org.jmesa.customColumn;

import java.math.BigDecimal;

import org.jmesa.view.editor.AbstractCellEditor;

public class LinkSorteggiCellEditorHelper extends AbstractCellEditor {

    public LinkSorteggiCellEditorHelper() {

	super();
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object oggetto = (Object) item;
	BigDecimal codiceistanza = (BigDecimal) UtilityJmesa.getParametro(oggetto, "codiceistanza");
	BigDecimal countsorteggiata = null;
	countsorteggiata = (BigDecimal) UtilityJmesa.getParametro(oggetto, property);
	String valueItem = "";
	if (countsorteggiata.intValue() > 0) {
	    String linkText = "";
	    linkText = getCoreContext().getMessage("label.S");
	    valueItem = "<a id=\"link_sorteggi_id" + rowcount + "\"  href=\"javascript:void(0)\" onmouseover=\"javascript:ajaxCall('link_sorteggi_id"
		    + rowcount + "','../sorteggitestata/ajaxFindSorteggidettaglioByIstanza.htm?codiceIstanza=" + codiceistanza + "')\" >" + linkText
		    + "</a><div id=\"link_sorteggi_id_\"></div>";
	}
	return valueItem;
    }
}