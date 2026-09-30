package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.view.editor.AbstractCellEditor;

public class LinkMovimentiCellEditor extends AbstractCellEditor {

    private String uriBack;

    public LinkMovimentiCellEditor(HttpServletRequest request, String uriBack) {

	super();
	this.uriBack = Utilities.buildHistoryBackFromRequest(request, uriBack);
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	String linkText = getCoreContext().getMessage("label.M");
	String titleText = getCoreContext().getMessage("label.visualizza_la_lista_dei_movimenti");
	Istanze istanza = (Istanze) item;
	String uriTo = "../movimenti/list.htm?codiceIstanza=" + istanza.getId().getCodice();
	try {
	    uriTo = URLEncoder.encode(uriTo, "UTF-8");
	} catch (Exception e) {
	}
	String historySetUrl = "../history/set.htm?ReturnTo=" + uriBack + "&" + WebConstants.GOTO + "=" + uriTo;
	String valueItem = "<a href=\"" + historySetUrl + "\" title=\"" + titleText + "\">" + linkText + "</a>";
	return valueItem;
    }
}
