package org.jmesa.customColumn;

import java.math.BigDecimal;

import org.jmesa.view.editor.AbstractCellEditor;

public class LinkAltriIndirizziHelperCellEditor extends AbstractCellEditor {

    public LinkAltriIndirizziHelperCellEditor() {

	super();
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object oggetto = (Object) item;
	BigDecimal codiceistanza = (BigDecimal) UtilityJmesa.getParametro(oggetto, "codiceistanza");
	BigDecimal countstradari = null;
	countstradari = (BigDecimal) UtilityJmesa.getParametro(oggetto, property);
	String valueItem = "";
	if (countstradari.intValue() > 1) {
	    String linkText = "";
	    linkText = getCoreContext().getMessage("label.I");
	    valueItem = "<a id=\"link_indirizzi_id" + rowcount
		    + "\"  href=\"javascript:void(0)\" onmouseover=\"ajaxCall('link_indirizzi_id" + rowcount
		    + "','../istanzestradario/ajaxFindAltriIndirizziByIstanza.htm?codiceIstanza=" + codiceistanza + "')\" >" + linkText;
	}
	return valueItem;
    }
}