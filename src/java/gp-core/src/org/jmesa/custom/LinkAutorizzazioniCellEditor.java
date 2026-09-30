package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.net.URLEncoder;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.view.editor.AbstractCellEditor;

public class LinkAutorizzazioniCellEditor extends AbstractCellEditor {

    private String uriBack;

    public LinkAutorizzazioniCellEditor(HttpServletRequest request, String uriBack) {

	super();
	this.uriBack = Utilities.buildHistoryBackFromRequest(request, uriBack);
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Istanze istanza = (Istanze) item;
	Set<Autorizzazioni> auts = istanza.getAutorizzazionis();
	String valueItem = null;
	if (auts.size() > 0) {
	    String uriTo = "../autorizzazioni/create.htm?codiceIstanza=" + istanza.getId().getCodice();
	    String linkText = getCoreContext().getMessage("label.A");
	    StringBuffer titleText = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		titleText = titleText.append("- ").append(autorizzazioni.getTransientEstremiAut()).append("\n");
	    }
	    try {
		uriTo = URLEncoder.encode(uriTo, "UTF-8");
	    } catch (Exception e) {
	    }
	    String historySetUrl = "../history/set.htm?ReturnTo=" + uriBack + "&" + WebConstants.GOTO + "=" + uriTo;
	    valueItem = "<a href=\"" + historySetUrl + "\" title=\"" + titleText.toString() + "\">" + linkText + "</a>";
	}
	return valueItem;
    }
}
