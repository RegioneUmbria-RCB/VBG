package org.jmesa.customColumn;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkDettaglioIstanzaInAutorizzazioni extends AbstractCellEditor {

    private static final Logger log = LoggerFactory.getLogger(LinkLabelBaseCellEditor.class);
    private HttpServletRequest request;

    // Costruttore che gestisce la history back
    public LinkDettaglioIstanzaInAutorizzazioni(HttpServletRequest request) {

	super();
	this.request = request;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	String valueItem = "";
	if (UtilityJmesa.getParametro(oggetto, "istanza.id.codice") != null) {
	    String title = getLabel("label.edit.record");
	    Integer codiceistanza = (Integer) UtilityJmesa.getParametro(oggetto, "istanza.id.codice");
	    String codicesoftware = (String) UtilityJmesa.getParametro(oggetto, "istanza.software.codice");
	    String numeroistanza = (String) UtilityJmesa.getParametro(oggetto, "istanza.numeroistanza");
	    String goTo="../history/set.htm?ReturnTo=../autorizzazioni/list.htm&GoTo=../istanze/view.htm?codice="
		    + codiceistanza
		    + "&software="
		    + codicesoftware;
	    String goToEncode="";
	    try {
		goToEncode=URLEncoder.encode(goTo, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    valueItem = "<a  href=\"javascript:doHref('"+goToEncode+"','')\" title=\""
		    + title
		    + " "
		    + numeroistanza
		    + "\"\">"
		    + numeroistanza + "</a>";
	   
//	    valueItem = "<a href=\"javascript:historySet('../autorizzazioni/list.htm&GoTo=','../istanze/view.htm?codice=" + codiceistanza + "&software=" + codicesoftware
//		    + "','')\" title=\"Dettaglio\"> " + numeroistanza +" </a>";
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
