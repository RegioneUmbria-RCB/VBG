package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.view.editor.AbstractCellEditor;

public class LinkEndoprocedimentiCellEditor extends AbstractCellEditor {

    private String historyBack;

    public LinkEndoprocedimentiCellEditor(HttpServletRequest request, String servletPathUriBack) {

	super();
	this.historyBack = Utilities.buildHistoryBackFromRequest(request, servletPathUriBack);
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Istanze istanza = (Istanze) item;
	String valueItem = null;
	if (istanza.getIstanzeprocedimentis().size() > 0) {
	    String goTo = "../istanzeprocedimenti/riepilogo.htm?codiceIstanza=" + istanza.getId().getCodice();
	    String linkText = getCoreContext().getMessage("label.P");
	    String titleText = getCoreContext().getMessage("label.visualizza_gli_endo_della_pratica");
	    valueItem = "<a href=\"../history/set.htm?" + WebConstants.GOTO + "=" + goTo + "&" + WebConstants.RETURNTO + "=" + historyBack
		    + "\" title=\"" + titleText + "\">" + linkText + "</a>";
	}
	return valueItem;
    }
}
