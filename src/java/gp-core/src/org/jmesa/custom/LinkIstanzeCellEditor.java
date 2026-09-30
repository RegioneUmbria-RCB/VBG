package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.view.editor.AbstractCellEditor;

public class LinkIstanzeCellEditor extends AbstractCellEditor {

    private String historyBack = "";

    public LinkIstanzeCellEditor(HttpServletRequest request, String servletPathUriBack) {

	super();
	this.historyBack = Utilities.buildHistoryBackFromRequest(request, servletPathUriBack);
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Istanze istanza = (Istanze) item;
	String goTo = "../istanze/view.htm?codice=" + istanza.getId().getCodice();
	try {
	    goTo = URLEncoder.encode(goTo, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	}
	String valueItem = "<a href=\"../history/set.htm?" + WebConstants.GOTO + "=" + goTo + "&" + WebConstants.RETURNTO + "=" + historyBack
		+ "\" title=\"" + istanza.getNumeroistanza() + "\">" + istanza.getNumeroistanza() + "</a>";
	return valueItem;
    }
}
