package org.jmesa.customColumn;

import java.math.BigDecimal;

import org.jmesa.view.editor.AbstractCellEditor;

public class CheckBoxAjaxUpdateIstanzeeventi extends AbstractCellEditor {

    private CheckBoxAjaxUpdateIstanzeeventi() {

	super();
    }

    public CheckBoxAjaxUpdateIstanzeeventi(String propertyToUpdate) {

	this();
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object oggetto = (Object) item;
	BigDecimal codiceevento = null;
	if (UtilityJmesa.getParametro(oggetto, property) != null) {
	    boolean isInteger = UtilityJmesa.getParametro(oggetto, property).getClass().isInstance(new Integer(0));
	    if (isInteger) {
		codiceevento = new BigDecimal((Integer) UtilityJmesa.getParametro(oggetto, property));
	    } else {
		codiceevento = (BigDecimal) UtilityJmesa.getParametro(oggetto, property);
	    }
	    String valueItem = "";
	    String idInput = "flagLettoId" + codiceevento;
	    valueItem = "<input id=\"" + idInput + "\" type=\"checkbox\" " + "onclick=\"changeCheckboxValue('" + idInput
		    + "','../istanzeeventi/ajaxChangeFlagLetto.htm?codice=" + codiceevento + "')\" />";
	    return valueItem;
	} else {
	    return "";
	}
    }
}
