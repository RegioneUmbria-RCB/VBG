package org.jmesa.custom;

import org.jmesa.view.editor.AbstractFilterEditor;
import org.jmesa.view.html.HtmlBuilder;

public class DataRilascioConcessioniFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	HtmlBuilder html = new HtmlBuilder();
	html.input().styleClass("dynFilter").name("filter").id("dataRilascio_id").onchange(
		"jQuery.jmesa.createDynFilter(this, 'vwconcessionilista_id','concDatarilascio', 'dataRilascio_id')").style("width: 70px;").close();
	return html.toString();
    }
}
