package org.jmesa.customColumn;

import org.jmesa.view.editor.AbstractCellEditor;

public class CheckBoxColumn extends AbstractCellEditor {

    private String id;
    private String name;
    private String path;

    public CheckBoxColumn(String id, String name, String path) {

	super();
	this.id = id;
	this.name = name;
	this.path = path;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	/*
	 * <input id="checkbox_id${movimento_var.istanza.id.codice}" type="checkbox" name="codiciMovimentiScelti" value="${movimento_var.id.codice}"  />
	 */
	// Recupero il valore da passare come parametro da passare al Controllor
	Object oggetto = (Object) item;
	// Recupera il valore per il path passato
	Object parametro = UtilityJmesa.getParametro(oggetto, path);
	// Valore di ritorno
	String valueItem = "";
	valueItem = "<input id=\"" + id + parametro + "\" type=\"checkbox\" name=\"" + name + "\" value=\"" + parametro + "\"  />";
	return valueItem;
    }
}
