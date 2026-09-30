/**
 * 
 */
package org.jmesa.custom;

import org.jmesa.view.editor.AbstractFilterEditor;
import org.jmesa.view.html.HtmlBuilder;

/**
 * @author francescop
 * 
 */
public class DataScadenzaConcessioniFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	HtmlBuilder html = new HtmlBuilder();
	html.input().styleClass("dynFilter").name("filter").id("dataScadenza_id").onchange(
		"jQuery.jmesa.createDynFilter(this, 'vwconcessionilista_id','concDatascadenza', 'dataScadenza_id')").style("width: 70px;").close();
	return html.toString();
    }
}
