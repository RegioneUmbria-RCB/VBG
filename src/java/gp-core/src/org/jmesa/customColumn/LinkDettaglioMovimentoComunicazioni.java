package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class LinkDettaglioMovimentoComunicazioni extends AbstractCellEditor {

    private HttpServletRequest request;
    private String pathHistoryBack;

    // Costruttore che gestisce la history back
    public LinkDettaglioMovimentoComunicazioni(HttpServletRequest request, String pathHistoryBack) {

	super();
	this.request = request;
	this.pathHistoryBack = pathHistoryBack;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controller
	Object oggetto = (Object) item;
	String valueItem = "";
	String title = getLabel("label.edit.record");
	Integer codicemovimento = (Integer) UtilityJmesa.getParametro(oggetto, "movimenti");
	if (codicemovimento == null) {
	    return valueItem;
	}
	String uriTo = "../movimenti/view.htm?codice=" + codicemovimento;
	try {
	    uriTo = URLEncoder.encode(uriTo, "UTF-8");
	} catch (Exception e) {
	}
	String descMovimenti = (String) UtilityJmesa.getParametro(oggetto, "descMovimenti");
	String uriBack = Utilities.buildHistoryBackFromRequest(request, pathHistoryBack);
	String historySetUrl = "../history/set.htm?ReturnTo=" + uriBack + "&" + WebConstants.GOTO + "=" + uriTo;
	valueItem = "<a href=\"" + historySetUrl + "\" title=\"" + title + "\">" + descMovimenti + "</a>";
	//	String goTo = "../history/set.htm?ReturnTo=" + pathHistoryBack + "&GoTo=../movimenti/view.htm?codice=" + codicemovimento;
	//	String goToEncode = "";
	//	try {
	//	    goToEncode = URLEncoder.encode(goTo, "UTF-8");
	//	} catch (UnsupportedEncodingException e) {
	//	    // TODO Auto-generated catch block
	//	    e.printStackTrace();
	//	}
	//	valueItem = "<a  href=\"javascript:doHref('" + goToEncode + "','')\" title=\"" + title + " " + descMovimenti + "\"\">" + descMovimenti
	//		+ "</a>";
	//		    valueItem = "<a href=\"javascript:historySet('../autorizzazioni/list.htm&GoTo=','../istanze/view.htm?codice=" + codiceistanza + "&software=" + codicesoftware
	//			    + "','')\" title=\"Dettaglio\"> " + numeroistanza +" </a>";
	return valueItem;
    }

    // recupera la label dal CoreContext
    private String getLabel(String label) {

	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String message = messages.getMessage(label);
	if (message == null) {
	    message = "???" + label + "???";
	}
	return message;
    }
}
