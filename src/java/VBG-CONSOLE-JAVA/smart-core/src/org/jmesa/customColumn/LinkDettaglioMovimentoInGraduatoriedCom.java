package org.jmesa.customColumn;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class LinkDettaglioMovimentoInGraduatoriedCom extends AbstractCellEditor {

    private HttpServletRequest request;

    // Costruttore che gestisce la history back
    public LinkDettaglioMovimentoInGraduatoriedCom(HttpServletRequest request) {

	super();
	this.request = request;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	String codiceGraduatoriat = (String) request.getParameter("codice");
	// Recupero il valore da passare come parametro da passare al controller
	Object oggetto = (Object) item;
	String valueItem = "";
	String title = getLabel("label.edit.record");
	Integer codicemovimento = (Integer) UtilityJmesa.getParametro(oggetto, "movimenti");
	String descMovimenti = (String) UtilityJmesa.getParametro(oggetto, "descMovimenti");
	String goTo = "../history/set.htm?ReturnTo=../graduatorietcom/view.htm?codice=" + codiceGraduatoriat + "&GoTo=../movimenti/view.htm?codice="
		+ codicemovimento;
	String goToEncode = "";
	try {
	    goToEncode = URLEncoder.encode(goTo, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	valueItem = "<a  href=\"javascript:doHref('" + goToEncode + "','')\" title=\"" + title + " " + descMovimenti + "\"\">" + descMovimenti
		+ "</a>";
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
