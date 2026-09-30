package org.jmesa.custom;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.web.SpringWebContext;

public class SelezionaAllegatiMovHeaderEditor extends AbstractContextSupport implements HeaderEditor {

    @Override
    public Object getValue() {

	//String javascript = createJavascript(corpoJavascript);
	return " <input id=\"id_check_seleziona_tot\" type=\"checkbox\" name=\"\" onclick=\"selezionaDeselezionaTutti();\" title=\""
		+ getLabel("label.seleziona") + "\">" + "</input>  <span title=\"" + getLabel("label.seleziona") + "\">" + getLabel("label.seleziona")
		+ "</span>";
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
