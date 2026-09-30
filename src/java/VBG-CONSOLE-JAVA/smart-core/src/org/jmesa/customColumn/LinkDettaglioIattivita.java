package org.jmesa.customColumn;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class LinkDettaglioIattivita extends AbstractCellEditor {

    private boolean isDettaglioWithIcon;

    public LinkDettaglioIattivita(boolean isDettaglioWithIcon) {

	super();
	this.isDettaglioWithIcon = isDettaglioWithIcon;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	Object parametro = null;
	parametro = UtilityJmesa.getParametro(oggetto, property);
	String valueItem = "";
	if (isDettaglioWithIcon) {
	    String message = getLabel("label.edit.record.image");
	    valueItem = "<a class=\"dettaglioColumn\" href=\"javascript:viewOrAssign(" + parametro + ");\" title=\"" + message + "\"> <label>"
		    + message + "</label></a>";
	} else {
	    valueItem = "<a href=\"javascript:viewOrAssign(" + parametro + ");\" title=\"" + parametro + "\"> " + parametro + "</a>";
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
