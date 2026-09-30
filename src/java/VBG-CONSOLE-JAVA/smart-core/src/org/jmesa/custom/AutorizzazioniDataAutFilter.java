package org.jmesa.custom;

import org.jmesa.view.editor.AbstractFilterEditor;
import org.jmesa.view.html.HtmlBuilder;

public class AutorizzazioniDataAutFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	HtmlBuilder html = new HtmlBuilder();
	html.input().styleClass("dynFilter").name("filter").id("calendarAutData").onchange(
		"jQuery.jmesa.createDynFilter(this,'autorizzazioniFilter_id','autorizdata','calendarAutData')").style("width: 70px;").close();
	return html.toString();
    }
}