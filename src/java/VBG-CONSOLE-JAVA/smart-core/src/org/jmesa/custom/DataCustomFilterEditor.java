package org.jmesa.custom;

import org.jmesa.view.editor.AbstractFilterEditor;
import org.jmesa.view.html.HtmlBuilder;

public class DataCustomFilterEditor extends AbstractFilterEditor {

    private String idTable;
    private String propertyPath;
    private String idFilter;

    public DataCustomFilterEditor(String idTable, String propertyPath, String idFilter) {

	super();
	this.idTable = idTable;
	this.propertyPath = propertyPath;
	this.idFilter = idFilter;
    }

    @Override
    public Object getValue() {

	HtmlBuilder html = new HtmlBuilder();
	html.input().styleClass("dynFilter").name("filter").id(idFilter)
		.onchange("jQuery.jmesa.createDynFilter(this, '" + idTable + "','" + propertyPath + "', '" + idFilter + "')").style("width: 70px;")
		.close();
	return html.toString();
    }
}
