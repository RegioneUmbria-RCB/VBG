package org.jmesa.customColumn;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.view.editor.AbstractCellEditor;

public class LinkSchededinamicheCellEditor extends AbstractCellEditor {

    public LinkSchededinamicheCellEditor(HttpServletRequest request) {

    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object oggetto = (Object) item;
	BigDecimal attivita = (BigDecimal) UtilityJmesa.getParametro(oggetto, "id");
	String valueItem = "";
	BigDecimal countschede = null;
	countschede = (BigDecimal) UtilityJmesa.getParametro(oggetto, property);
	if (countschede.intValue() > 0) {
	    String linkText = getCoreContext().getMessage("label.S");
	    valueItem = "<a id=\"link_schede_id" + rowcount + "\"  href=\"javascript:showModelliDinamiciAttivita(" + attivita + ",'');\"  >"
		    + linkText;
	}
	return valueItem;
    }
}
