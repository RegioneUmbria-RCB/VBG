package org.jmesa.customColumn;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkCreaCollegamentoTraIstanzeCellEditor extends AbstractCellEditor {

    private static final Logger log = LoggerFactory.getLogger(LinkLabelBaseCellEditor.class);
    private String urlGoTo = "";
    private Integer codiceIstanzaDaconfigurare;

    public LinkCreaCollegamentoTraIstanzeCellEditor(String urlGoTo, Integer codiceIstanzaDaconfigurare) {

	super();
	this.urlGoTo = urlGoTo;
	this.codiceIstanzaDaconfigurare = codiceIstanzaDaconfigurare;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	String goTo = "";
	Object parametro = null;
	parametro = UtilityJmesa.getParametro(oggetto, "codiceistanza");
	try {
	    goTo = urlGoTo + "?codiceIstanzaDaCollegare=" + parametro + "&codiceIstanza=" + codiceIstanzaDaconfigurare;
	    goTo = urlGoTo + "?codiceIstanzaDaCollegare=" + parametro + "&codiceIstanza=" + codiceIstanzaDaconfigurare;
	    goTo = URLEncoder.encode(goTo, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    log.debug("UnsupportedEncodingException: " + e);
	    e.printStackTrace();
	}
	String valueItem = "";
	String message = getLabel("label.crea_collegamento");
	valueItem = "<a class=\"addColumn\" href=\"javascript:doHref('" + goTo + "','')\" title=\"" + message + "\"> <label>" + message
		+ "</label></a>";
	return valueItem;
    }

    // recupera la label dal CoreContext
    private String getLabel(String label) {

	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String message = messages.getMessage(label);
	if (message == null) {
	    message = "???" + label + "???";
	}
	//	String message = getCoreContext().getMessage(label);
	//	if (message == null) {
	//	    message = "???" + label + "???";
	//	}
	return message;
    }
}
