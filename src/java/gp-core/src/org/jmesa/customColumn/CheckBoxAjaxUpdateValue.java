package org.jmesa.customColumn;

import org.jmesa.view.editor.AbstractCellEditor;

public class CheckBoxAjaxUpdateValue extends AbstractCellEditor {

    private String idProperty;
    private String ajaxUpdatePath;
    private String tableName;

    private CheckBoxAjaxUpdateValue() {

	super();
    }

    public CheckBoxAjaxUpdateValue(String tableName, String idProperty, String ajaxUpdatePath) {

	this();
	this.idProperty = idProperty;
	this.ajaxUpdatePath = ajaxUpdatePath;
	this.tableName = tableName;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object oggetto = (Object) item;
	Integer codice = (Integer) UtilityJmesa.getParametro(oggetto, idProperty);
	String valueItem = "";
	String idInput = tableName + "_fLettoId" + codice;
	valueItem = "<input id=\"" + idInput + "\" type=\"checkbox\" " + "onclick=\"changeCheckboxValue('" + idInput + "','" + ajaxUpdatePath
		+ codice + "')\" />";
	return valueItem;
    }
}
