package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class LinkDettaglioConcessioniComunicazioni extends AbstractCellEditor {

    private HttpServletRequest request;
    private String pathIstanza;
    private String pathConcessione;
    private String pathHistoryBack;

    // Costruttore che gestisce la history back
    public LinkDettaglioConcessioniComunicazioni(HttpServletRequest request, String pathConcessione, String pathIstanza, String pathHistoryBack) {

	super();
	this.request = request;
	this.pathIstanza = pathIstanza;
	this.pathHistoryBack = pathHistoryBack;
	this.pathConcessione = pathConcessione;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	//String codice = (String) request.getParameter("codice");
	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	String valueItem = "";
	if (UtilityJmesa.getParametro(oggetto, pathIstanza + ".id.codice") != null) {
	    String title = getLabel("label.edit.record");
	    Integer codiceconc = (Integer) UtilityJmesa.getParametro(oggetto, pathConcessione + ".id.codice");
	    String numeroconc = (String) UtilityJmesa.getParametro(oggetto, pathConcessione + ".autoriznumero");
	    Integer codiceistanza = (Integer) UtilityJmesa.getParametro(oggetto, pathIstanza + ".id.codice");
	    // String contextPath = request.getContextPath();
	    String uriTo = "../autorizzazioni/viewConcessione.htm?codiceAutorizzazione=" + codiceconc + "&codiceIstanza=" + codiceistanza;
	    try {
		uriTo = URLEncoder.encode(uriTo, "UTF-8");
	    } catch (Exception e) {
	    }
	    String uriBack = Utilities.buildHistoryBackFromRequest(request, pathHistoryBack);
	    String historySetUrl = "../history/set.htm?ReturnTo=" + uriBack + "&" + WebConstants.GOTO + "=" + uriTo;
	    valueItem = "<a href=\"" + historySetUrl + "\" title=\"" + title + "\">" + numeroconc + "</a>";
	    //	    String goTo = "../history/set.htm?ReturnTo=" + pathHistoryBack + "&GoTo=../istanze/view.htm?codice=" + codiceistanza + "&software="
	    //		    + codicesoftware;
	    //	    String goToEncode = "";
	    //	    try {
	    //		goToEncode = URLEncoder.encode(goTo, "UTF-8");
	    //	    } catch (UnsupportedEncodingException e) {
	    //		// TODO Auto-generated catch block
	    //		e.printStackTrace();
	    //	    }
	    //	    valueItem = "<a  href=\"javascript:doHref('" + goToEncode + "','')\" title=\"" + title + " " + numeroistanza + "\"\">" + numeroistanza
	    //		    + "</a>";
	    //		    valueItem = "<a href=\"javascript:historySet('../autorizzazioni/list.htm&GoTo=','../istanze/view.htm?codice=" + codiceistanza + "&software=" + codicesoftware
	    //			    + "','')\" title=\"Dettaglio\"> " + numeroistanza +" </a>";
	}
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
