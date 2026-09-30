package org.jmesa.custom;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.web.SpringWebContext;

public class DocIstanzaEliminaHeaderEditor extends AbstractContextSupport implements HeaderEditor {

    @Override
    public Object getValue() {

	//String javascript = createJavascript(corpoJavascript);
	return " <input id=\"id_check_elimina_tot\" type=\"checkbox\" name=\"\" onclick=\"selezionaTuttiEliminaDocIstanza();\" title=\""
		+ getLabel("label.elimina") + "\">" + "</input>  <span title=\"" + getLabel("label.elimina") + "\">"
		+ getLabel("label.elimina") + "</span>";
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
