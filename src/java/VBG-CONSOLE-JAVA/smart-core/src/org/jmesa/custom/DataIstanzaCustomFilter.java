package org.jmesa.custom;

import org.jmesa.view.editor.AbstractFilterEditor;
import org.jmesa.view.html.HtmlBuilder;

public class DataIstanzaCustomFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	String idtabella = "istanze_id";
	HtmlBuilder html = new HtmlBuilder();
	html.input().styleClass("dynFilter").name("filter").id("calendarDataIstanza")
		.onchange("jQuery.jmesa.createDynFilter(this, '" + idtabella + "','data', 'calendarDataIstanza')").style("width: 70px;").close();
	return html.toString();
    }
}
