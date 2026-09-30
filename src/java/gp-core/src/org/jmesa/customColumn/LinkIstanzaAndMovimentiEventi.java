package org.jmesa.customColumn;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class LinkIstanzaAndMovimentiEventi extends AbstractCellEditor {

    private HttpServletRequest request;

    // Costruttore che gestisce la history back
    public LinkIstanzaAndMovimentiEventi(HttpServletRequest request) {

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
	Integer codiceIstanza = (Integer) UtilityJmesa.getParametro(oggetto, "graduatoried.istanza.id.codice");
	String descEvento = (String) UtilityJmesa.getParametro(oggetto, "istanzeeventi.descrizione");
	Integer codiceEvento = (Integer) UtilityJmesa.getParametro(oggetto, "istanzeeventi.id.codice");
	String goTo = "";
	if (codiceEvento != null) {
	    if (codicemovimento != null) {
		goTo = "../history/set.htm?ReturnTo=../graduatorietcom/view.htm?codice=" + codiceGraduatoriat
			+ "&GoTo=../istanzeeventi/list.htm?codicemovimento=" + codicemovimento;
	    } else {
		goTo = "../history/set.htm?ReturnTo=../graduatorietcom/view.htm?codice=" + codiceGraduatoriat
			+ "&GoTo=../istanzeeventi/list.htm?.htm?codiceIstanza=" + codiceIstanza;
	    }
	    String goToEncode = "";
	    try {
		goToEncode = URLEncoder.encode(goTo, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    }
	    valueItem = "<a  href=\"javascript:doHref('" + goToEncode + "','')\" title=\"" + title + "\"\">" + descEvento + "</a>";
	    return valueItem;
	} else {
	    return "";
	}
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
