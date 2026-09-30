package org.jmesa.customColumn;

import org.jmesa.view.editor.AbstractCellEditor;

public class CheckBoxAjaxUpdateMovimenti extends AbstractCellEditor {

    private String propertyToUpdate;
    private String codiceMovimentoPropertyName;

    private CheckBoxAjaxUpdateMovimenti() {

	super();
	this.codiceMovimentoPropertyName = "id.codice";
    }

    public CheckBoxAjaxUpdateMovimenti(String propertyToUpdate, String codiceMovimentoPropertyName) {

	this();
	this.propertyToUpdate = propertyToUpdate;
	this.codiceMovimentoPropertyName = codiceMovimentoPropertyName;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object oggetto = (Object) item;
	Integer codicemovimento = (Integer) UtilityJmesa.getParametro(oggetto, codiceMovimentoPropertyName);
	String valueItem = "";
	String idInput = propertyToUpdate + "flagLettoId" + codicemovimento;
	valueItem = "<input id=\"" + idInput + "\" type=\"checkbox\" " + "onclick=\"changeCheckboxValue('" + idInput
		+ "','../movimenti/ajaxUpdateProperty.htm?codice=" + codicemovimento + "&propertyToUpdate=" + propertyToUpdate + "')\" />";
	return valueItem;
    }
}
