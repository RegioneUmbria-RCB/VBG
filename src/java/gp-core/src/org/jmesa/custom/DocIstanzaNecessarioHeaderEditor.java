package org.jmesa.custom;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.web.SpringWebContext;

public class DocIstanzaNecessarioHeaderEditor extends AbstractContextSupport implements HeaderEditor {

    @Override
    public Object getValue() {

	//String javascript = createJavascript(corpoJavascript);
	//	return " <input id=\"id_check_necessario_tot\" type=\"checkbox\" name=\"\" onclick=\"selezionaTuttiNecessarioDocIstanza();\" title=\""
	//		+ getLabel("label.richiesto") + "\">" + "</input>  <span title=\"" + getLabel("label.richiesto") + "\">"
	//		+ getLabel("label.richiesto") + "</span>";
	return "<span>"
		+ "<img id=\"id_check_necessario_tot\" align=\"left\" src=\"../images/piu.gif\" onclick=\"selezionaDeselezionaTuttiNecessarioDocIstanza(true);\" title=\"Seleziona tutti\" /> " +
		"<img id=\"id_check_necessario_tot\" align=\"left\" src=\"../images/meno.gif\" onclick=\"selezionaDeselezionaTuttiNecessarioDocIstanza(false);\" title=\"Deseleziona tutti\" />" + getLabel("label.richiesto")
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
